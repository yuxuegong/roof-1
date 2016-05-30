package org.roof.chain.api;


import org.roof.chain.valuestack.ValueStack;

import java.util.Collection;

/**
 * 责任链
 *
 * @author liuxin
 */
public interface Chain {

    Object doChain(ValueStack valueStack);

    /**
     * 获取责任链的第一个节点
     *
     * @return
     */
    String getFirst();

    void setFirst(String first);

    Collection<Node> getNodes();

    void setNodes(Collection<Node> nodes);

}
