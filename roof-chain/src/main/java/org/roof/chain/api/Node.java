package org.roof.chain.api;


import org.roof.chain.valuestack.ValueStack;

import java.util.Map;

/**
 * 责任链节点
 *
 * @author liuxin
 */
public interface Node {
    String getName(); // 名称

    void setName(String name);

    Object doNode(ValueStack valueStack) throws Exception;

    Map<String, String> getForwards(); // 流向

    void setForwards(Map<String, String> forwards);
}
