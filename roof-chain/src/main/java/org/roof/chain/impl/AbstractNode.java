package org.roof.chain.impl;

import org.roof.chain.api.Node;
import org.roof.chain.valuestack.ValueStack;

import java.util.Map;


/**
 * @author liuxin
 */
public abstract class AbstractNode implements Node {
    protected Map<String, String> forwards;
    protected String name;
    protected static final String NEXT = "next";
    protected static final String SUCCESS = "success";
    protected static final String ERROR = "error";

    /**
     * 获得指定节点设置的值
     *
     * @param expr
     * @param valueStack
     * @return
     */
    public Object geNodeValue(String nodeName, String expr, ValueStack valueStack) {
        return valueStack.getValue(nodeName + "." + expr);
    }

    /**
     * 设置当前节点出参
     *
     * @param expr
     * @param value
     * @param valueStack
     */
    public void setNodeValue(String expr, Object value, ValueStack valueStack) {
        valueStack.insertOrUpdate(name + "." + expr, value);
    }

    public Map<String, String> getForwards() {
        return forwards;
    }

    public void setForwards(Map<String, String> forwards) {
        this.forwards = forwards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
