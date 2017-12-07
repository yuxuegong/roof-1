package org.roof.oauth2.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

/**
 * Created by zhenglt on 2017/11/15.
 */
@FrameworkEndpoint
public class TokenController implements InitializingBean {

    protected final Log logger = LogFactory.getLog(getClass());

    private WebResponseExceptionTranslator providerExceptionHandler = new DefaultWebResponseExceptionTranslator();

    @Autowired
    private TokenGranter tokenGranter;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private OAuth2RequestFactory oAuth2RequestFactory;
    @Autowired
    private OAuth2RequestFactory defaultOAuth2RequestFactory;
    @Autowired
    private ClientRegistrationService clientRegistrationService;


    public void afterPropertiesSet() throws Exception {
        Assert.state(tokenGranter != null, "TokenGranter must be provided");
        Assert.state(clientDetailsService != null, "ClientDetailsService must be provided");
        defaultOAuth2RequestFactory = new DefaultOAuth2RequestFactory(getClientDetailsService());
        if (oAuth2RequestFactory == null) {
            oAuth2RequestFactory = defaultOAuth2RequestFactory;
        }
    }




    private OAuth2RequestValidator oAuth2RequestValidator = new DefaultOAuth2RequestValidator();

    private Set<HttpMethod> allowedRequestMethods = new HashSet<HttpMethod>(Arrays.asList(HttpMethod.POST,HttpMethod.GET));

    @RequestMapping(value = "/token", method= RequestMethod.GET)
    public ResponseEntity<OAuth2AccessToken> getAccessToken(
            TokenDto parameters) throws HttpRequestMethodNotSupportedException {
        if (!allowedRequestMethods.contains(HttpMethod.GET)) {
            throw new HttpRequestMethodNotSupportedException("GET");
        }
        return postAccessToken(parameters);
    }

    @RequestMapping(value = "/token", method=RequestMethod.POST)
    public ResponseEntity<OAuth2AccessToken> postAccessToken( @RequestBody TokenDto tokenDto) throws HttpRequestMethodNotSupportedException {
        Map<String,String> parameters = tokenDto.getMap();


        /*if (!(principal instanceof Authentication)) {
            throw new InsufficientAuthenticationException(
                    "There is no client authentication. Try adding an appropriate authentication filter.");
        }*/
        //UsernamePasswordAuthenticationToken principal1 = new UsernamePasswordAuthenticationToken("admin","DF10EF8509DC176D733D59549E7DBFAF");

        String clientId = parameters.get("client_id");//getClientId(principal);
        if (!StringUtils.hasText(clientId)) {
            throw new InvalidRequestException("Missing username || client_id");
        }
        ClientDetails authenticatedClient = null;
        try{
            authenticatedClient = getClientDetailsService().loadClientByClientId(clientId);
        }catch (NoSuchClientException e){
            ClientDetails clientDetails = new BaseClientDetails(clientId, null, tokenDto.getScope(), tokenDto.getGrant_type(), null);
            clientRegistrationService.addClientDetails(clientDetails);
            authenticatedClient = getClientDetailsService().loadClientByClientId(clientId);
        }

        TokenRequest tokenRequest = getOAuth2RequestFactory().createTokenRequest(parameters, authenticatedClient);

        if (clientId != null && !clientId.equals("")) {
            // Only validate the client details if a client authenticated during this
            // request.
            if (!clientId.equals(tokenRequest.getClientId())) {
                // double check to make sure that the client ID in the token request is the same as that in the
                // authenticated client
                throw new InvalidClientException("Given client ID does not match authenticated client");
            }
        }
        /*if (authenticatedClient != null) {
            oAuth2RequestValidator.validateScope(tokenRequest, authenticatedClient);
        }*/
        if (!StringUtils.hasText(tokenRequest.getGrantType())) {
            throw new InvalidRequestException("Missing grant type");
        }
        if (tokenRequest.getGrantType().equals("implicit")) {
            throw new InvalidGrantException("Implicit grant type not supported from token endpoint");
        }

        if (isAuthCodeRequest(parameters)) {
            // The scope was requested or determined during the authorization step
            if (!tokenRequest.getScope().isEmpty()) {
                logger.debug("Clearing scope of incoming token request");
                tokenRequest.setScope(Collections.<String> emptySet());
            }
        }

        if (isRefreshTokenRequest(parameters)) {
            // A refresh token has its own default scopes, so we should ignore any added by the factory here.
            tokenRequest.setScope(OAuth2Utils.parseParameterList(parameters.get(OAuth2Utils.SCOPE)));
        }

        OAuth2AccessToken token = getTokenGranter().grant(tokenRequest.getGrantType(), tokenRequest);
        if (token == null) {
            throw new UnsupportedGrantTypeException("Unsupported grant type: " + tokenRequest.getGrantType());
        }

        return getResponse(token);

    }

    /**
     * @param principal the currently authentication principal
     * @return a client id if there is one in the principal
     */
    protected String getClientId(Principal principal) {
        Authentication client = (Authentication) principal;
        if (!client.isAuthenticated()) {
            throw new InsufficientAuthenticationException("The client is not authenticated.");
        }
        String clientId = client.getName();
        if (client instanceof OAuth2Authentication) {
            // Might be a client and user combined authentication
            clientId = ((OAuth2Authentication) client).getOAuth2Request().getClientId();
        }
        return clientId;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<OAuth2Exception> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) throws Exception {
        logger.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        return getExceptionTranslator().translate(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<OAuth2Exception> handleException(Exception e) throws Exception {
        logger.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        return getExceptionTranslator().translate(e);
    }

    @ExceptionHandler(ClientRegistrationException.class)
    public ResponseEntity<OAuth2Exception> handleClientRegistrationException(Exception e) throws Exception {
        logger.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        return getExceptionTranslator().translate(new BadClientCredentialsException());
    }

    @ExceptionHandler(OAuth2Exception.class)
    public ResponseEntity<OAuth2Exception> handleException(OAuth2Exception e) throws Exception {
        logger.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        return getExceptionTranslator().translate(e);
    }

    private ResponseEntity<OAuth2AccessToken> getResponse(OAuth2AccessToken accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        return new ResponseEntity<OAuth2AccessToken>(accessToken, headers, HttpStatus.OK);
    }

    private boolean isRefreshTokenRequest(Map<String, String> parameters) {
        return "refresh_token".equals(parameters.get("grant_type")) && parameters.get("refresh_token") != null;
    }

    private boolean isAuthCodeRequest(Map<String, String> parameters) {
        return "authorization_code".equals(parameters.get("grant_type")) && parameters.get("code") != null;
    }

    public void setOAuth2RequestValidator(OAuth2RequestValidator oAuth2RequestValidator) {
        this.oAuth2RequestValidator = oAuth2RequestValidator;
    }

    public void setAllowedRequestMethods(Set<HttpMethod> allowedRequestMethods) {
        this.allowedRequestMethods = allowedRequestMethods;
    }


    public void setProviderExceptionHandler(WebResponseExceptionTranslator providerExceptionHandler) {
        this.providerExceptionHandler = providerExceptionHandler;
    }

    public void setTokenGranter(TokenGranter tokenGranter) {
        this.tokenGranter = tokenGranter;
    }

    protected TokenGranter getTokenGranter() {
        return tokenGranter;
    }

    protected WebResponseExceptionTranslator getExceptionTranslator() {
        return providerExceptionHandler;
    }

    protected OAuth2RequestFactory getOAuth2RequestFactory() {
        return oAuth2RequestFactory;
    }

    protected OAuth2RequestFactory getDefaultOAuth2RequestFactory() {
        return defaultOAuth2RequestFactory;
    }

    public void setOAuth2RequestFactory(OAuth2RequestFactory oAuth2RequestFactory) {
        this.oAuth2RequestFactory = oAuth2RequestFactory;
    }

    protected ClientDetailsService getClientDetailsService() {
        return clientDetailsService;
    }

    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }




}
