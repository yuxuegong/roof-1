package org.roof.chain.impl;

import org.apache.log4j.Logger;
import org.roof.chain.api.Chain;
import org.roof.chain.api.Node;
import org.roof.chain.valuestack.ValueStack;
import org.springframework.beans.factory.InitializingBean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxin
 */
public class DefaultChain implements Chain, InitializingBean {
    private Collection<Node> nodes;
    private String first;
    private Map<String, Node> nodesMap;
    private static final Logger LOG = Logger.getLogger(DefaultChain.class);
    private String defaultErrorNodeName = "error";


    @Override
    public void afterPropertiesSet() throws Exception {
        if (nodesMap == null) {
            initNodesMap();
        }
    }

    @Override
    public Object doChain(ValueStack valueStack) {
        Node node = nodesMap.get(getFirst());
        if (node == null) {
            throw new IllegalArgumentException("首节点(first)未找到");
        }
        try {
            while (node != null) {
                Object result = node.doNode(valueStack);
                if (result == null) {
                    return null;
                }
                if (!(result instanceof String)) {
                    return result;
                }
                String r = result.toString();

                Map<String, String> fwds = node.getForwards();
                if (fwds == null) {
                    return r;
                }
                String fwd = fwds.get(r);
                if (fwd == null) {
                    return r;
                }
                node = nodesMap.get(fwd);
                if (node == null) {
                    return r;
                }
            }
        } catch (Throwable e) {
            LOG.error(e.getMessage(), e);
            Node defaultErrorNode = nodesMap.get(defaultErrorNodeName);
            if (defaultErrorNode == null) {
                throw new IllegalArgumentException("默认error节点(" + defaultErrorNodeName + ")未找到");
            }
            try {
                return defaultErrorNode.doNode(valueStack);
            } catch (Exception e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }
        return null;
    }

    private void initNodesMap() {
        nodesMap = new HashMap<String, Node>();
        for (Node node : nodes) {
            if (nodesMap.containsKey(node.getName())) {
                throw new IllegalArgumentException("节点(" + node.getName() + ")重复");
            }
            nodesMap.put(node.getName(), node);
        }
    }

    @Override
    public Collection<Node> getNodes() {
        return nodes;
    }

    @Override
    public void setNodes(Collection<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String getFirst() {
        return first;
    }

    @Override
    public void setFirst(String first) {
        this.first = first;
    }

    public String getDefaultErrorNodeName() {
        return defaultErrorNodeName;
    }

    public void setDefaultErrorNodeName(String defaultErrorNodeName) {
        this.defaultErrorNodeName = defaultErrorNodeName;
    }
}
