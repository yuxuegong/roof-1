package org.roof.web.user.action;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import org.roof.commons.Md5Generator;
import org.roof.commons.SysConstants;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageUtils;
import org.roof.spring.Result;
import org.roof.web.core.TreeDate;
import org.roof.web.org.dao.api.IOrgDao;
import org.roof.web.org.service.api.IOrgService;
import org.roof.web.resource.entity.Module;
import org.roof.web.role.entity.BaseRole;
import org.roof.web.role.entity.Role;
import org.roof.web.role.service.api.IRoleService;
import org.roof.web.user.entity.User;
import org.roof.web.user.entity.UserVo;
import org.roof.web.user.service.api.BaseUserContext;
import org.roof.web.user.service.api.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONPath;

@Controller
@RequestMapping("userAction")
public class UserAction {

	// private IMenuFilter menuFilter;
	private IUserService userService;
	private IRoleService roleService;

	@Autowired
	private IOrgService orgService;

	@RequestMapping("/goLogin")
	public String goLogin(String errorCode, Model model) {
		model.addAttribute("errorCode", errorCode == null ? "null" : "'" + errorCode + "'");
		return "/roof-web/web/user/user_goLogin.jsp";
	}

	@RequestMapping("/goMain")
	public String goMain(Model model, HttpServletRequest request, HttpSession httpSession) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// Menu menu = menuFilter.doFilter(1L, authentication);
		Map<Class<?>, String[]> map = new HashMap<Class<?>, String[]>();
		// map.put(Menu.class, new String[] { "parent" });
		map.put(Module.class, new String[] { "parent", "baseRole", "parameters", "returnFields" });
		// String s = JSONObject.fromObject(menu,
		// Result.createJsonConfig(map)).toString();
		// model.addAttribute("menuJson", s);
		return "/roof-web/web/user/user_goMain.jsp";
	}

	/**
	 * 登录验证
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public @ResponseBody Result login(User user, Model model) {
		try {
			if (userService.isLoginError(user.getUsername())) {
				return new Result(Result.FAIL, "30分钟之内登录失败次数过多，请稍后再试");
			}
			User loginUser = userService.login(user);
			if (loginUser == null) {
				return new Result(Result.FAIL, "用户名或者密码错误，30分钟之内剩余"
						+ (SysConstants.getAllowableErrorCount() - 1 - userService.findErrorCount(user.getUsername()))
						+ "次!");
			} else {
				return new Result("用户登录成功!");
			}
			// TODO userService.saveLoginLog(loginUser, user);
		} catch (Exception e) {
			return new Result(Result.FAIL, e.getMessage());
		}
	}

	@RequestMapping("/list")
	public String list(User user, HttpServletRequest request, Model model) {
		Page page = PageUtils.createPage(request);
		page = userService.page(page, user);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		model.addAttribute("page", page);
		model.addAttribute("pageBar", pageBar);
		return "/roof-web/web/user/user_list.jsp";
	}

	@RequestMapping("/list_select")
	public String list_select(User user, HttpServletRequest request, Model model) {
		Page page = PageUtils.createPage(request);
		page = userService.page(page, user);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		model.addAttribute("page", page);
		model.addAttribute("pageBar", pageBar);
		Md5Generator md5Generator = new Md5Generator();
		model.addAttribute("defPwd", md5Generator.calcmd5(SysConstants.DEFAULT_PWD).toUpperCase());
		return "/roof-web/web/user/user_list_select.jsp";
	}

	public String detail() {
		return null;
	}

	@RequestMapping(value = "user/{id}", method = {RequestMethod.PUT})
	public @ResponseBody Result update(@PathVariable Long id ,@RequestBody UserVo userVo) {
		if (userVo != null) {
			User user = new User();
			BeanUtils.copyProperties(userVo,user);
			user.setDtype(User.class.getSimpleName());
			Long [] rolesIds = userVo.getRolesIds();
			user.setPassword(null);
			if (rolesIds != null) {
				Set<BaseRole> roles = new HashSet<BaseRole>();
				for (Long roleId : rolesIds) {
					roles.add(new Role(roleId, null));
				}
				user.setRoles(roles);
			}
			if (!JSONPath.contains(user, "$.org.id")) {
				user.setOrg(null);
			}
			userService.update(user);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/update_pwd")
	public @ResponseBody Result update_pwd(User user, Model model) {
		if (user != null) {
			userService.updateIgnoreNull(user);
			return new Result("修改成功!");
		} else {
			return new Result("修改失败!");
		}
	}

	@RequestMapping("/update_pwd_page")
	public String update_pwd_page(HttpServletRequest request, Model model) {
		UserDetails user = (UserDetails) BaseUserContext.getCurrentUser(request);
		model.addAttribute("user", user);
		return "/roof-web/web/user/user_update_pwd_page.jsp";
	}

	@RequestMapping("/update_page")
	public String update_page(Long id, Model model) {
		User user = userService.load(id);
		List<Role> roleses = roleService.loadAll();
		model.addAttribute("roleses", roleses);
		model.addAttribute("user", user);
		return "/roof-web/web/user/user_update_page.jsp";
	}

	@RequestMapping(value = "user", method = {RequestMethod.POST})
	public @ResponseBody Result create(@RequestBody UserVo userVo) {
		if (userVo != null) {
			User user = new User();
			BeanUtils.copyProperties(userVo,user);
			user.setDtype(User.class.getSimpleName());
			Long [] rolesIds = userVo.getRolesIds();
			if (rolesIds != null) {
				Set<BaseRole> roles = new HashSet<BaseRole>();
				for (Long roleId : rolesIds) {
					roles.add(new Role(roleId, null));
				}
				user.setRoles(roles);
			}
			user.setCreate_date(new Date());
			if (!JSONPath.contains(user, "$.org.id")) {
				user.setOrg(null);
			}
			userService.save(user);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/create_page")
	public String create_page(Long id, Model model) {
		List<Role> roleses = roleService.loadAll();
		model.addAttribute("roleses", roleses);
		return "/roof-web/web/user/user_create_page.jsp";
	}



	@ApiOperation(value = "获得s_user基础信息")
	@RequestMapping(value = "user/base", method = {RequestMethod.GET})
	public @ResponseBody Result<Map<String,Object>> base(HttpServletRequest request) {
		Map<String,Object> map = Maps.newHashMap();
		List<Role> roleses = roleService.loadAll();
		map.put("roleses",roleses);
		List<TreeDate> orgs = orgService.readToTree(1L);
		map.put("orgs",orgs);

		return new Result(Result.SUCCESS, map);
	}

	@ApiOperation(value = "获得s_user分页列表")
	@RequestMapping(value = "user", method = {RequestMethod.GET})
	public @ResponseBody Result<Page> list(User user, HttpServletRequest request) {
		Page page = PageUtils.createPage(request);
		page = userService.page(page, user);
		return new Result(Result.SUCCESS, page);
	}


	/*@ApiOperation(value = "新增s_user")
	@RequestMapping(value = "user", method = {RequestMethod.POST})
	public @ResponseBody Result create(@RequestBody User user) {
		if (user != null) {
			userService.save(user);
			return new Result("保存成功!");
		} else {
			return new Result(Result.FAIL,"数据传输失败!");
		}
	}*/

	@ApiOperation(value = "根据ID加载s_user")
	@RequestMapping(value = "user/{id}", method = {RequestMethod.GET})
	public @ResponseBody Result<User> load(@PathVariable Long id) {
		//JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();

		User userVo = userService.load(id);
		return new Result(Result.SUCCESS,userVo);
	}

	/*@ApiOperation(value = "根据ID更新s_user")
	@RequestMapping(value = "user/{id}", method = {RequestMethod.PUT})
	public @ResponseBody Result update(@PathVariable Long id ,@RequestBody User user) {
		if (id != null && user != null) {
			user.setId(id);
			userService.updateIgnoreNull(user);
			return new Result("保存成功!");
		} else {
			return new Result(Result.FAIL,"数据传输失败!");
		}
	}*/

	@ApiOperation(value = "根据ID删除s_user")
	@RequestMapping(value = "user/{id}", method = {RequestMethod.DELETE})
	public @ResponseBody Result delete(@PathVariable Long id ) {
		// TODO 有些关键数据是不能物理删除的，需要改为逻辑删除
		userService.delete(new User(id));
		return new Result("删除成功!");
	}

	@RequestMapping("/sameUsername")
	public @ResponseBody Boolean sameUsername(String username, Long id) {
		return !userService.sameUsername(id, username);
	}

	@Autowired(required = true)
	public void setUserService(@Qualifier("userService") IUserService userService) {
		this.userService = userService;
	}

	@Autowired(required = true)
	public void setRoleService(@Qualifier("roleService") IRoleService roleService) {
		this.roleService = roleService;
	}
}
