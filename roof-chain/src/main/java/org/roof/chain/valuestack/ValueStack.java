package org.roof.chain.valuestack;


import org.roof.chain.exceptions.ValueExistsException;
import org.roof.chain.exceptions.ValueNotExistsException;

import java.util.Map;

/**
 * 值栈
 * 
 * @author liuxin
 *
 */
public interface ValueStack {
	/**
	 * 插入一个值,如果该值存在则覆盖
	 * 
	 * @param expr
	 *            值名称
	 * @param value
	 *            值对象
	 */
	void insertOrUpdate(String expr, Object value);

	/**
	 * 插入多个值,如果该值存在则覆盖
	 * 
	 * @param map
	 *            值对象map
	 */
	void insertOrUpdateAll(Map<String, ?> map);

	/**
	 * 插入一个值,如果该值存在则抛出异常
	 * 
	 * @param expr
	 *            值名称
	 * @param value
	 *            值对象
	 * @throws ValueExistsException
	 *             值存在时抛出
	 */
	void insert(String expr, Object value) throws ValueExistsException;

	/**
	 * 更新一个值,如果该值不存在则抛出异常
	 * 
	 * @param expr
	 *            值名称
	 * @param value
	 *            值对象
	 * @throws ValueNotExistsException
	 *             值不存在时抛出
	 */
	void update(String expr, Object value) throws ValueNotExistsException;

	/**
	 * 某个值是否存在
	 * 
	 * @param expr
	 *            值名称
	 * @return true:存在,false:不存在
	 */
	boolean existsValue(String expr);

	/**
	 * 获得一个String类型的值
	 * 
	 * @param expr
	 *            值名称
	 * @return 获得的值,不存在返回<code>null</code>
	 */
	String getString(String expr);

	/**
	 * 获得一个String类型的值,值名称不存在则抛出异常
	 * 
	 * @param expr
	 *            值名称
	 * @return 获得的值
	 * @throws ValueNotExistsException
	 *             值名称不存在抛出异常
	 */
	String getStringIfExists(String expr) throws ValueNotExistsException;

	/**
	 * 获得一个值
	 * 
	 * @param expr
	 *            值名称
	 * @return 获得的值,不存在返回<code>null</code>
	 */
	Object getValue(String expr);

	/**
	 * 获得一个值,值名称不存在则抛出异常
	 * 
	 * @param expr
	 *            值名称
	 * @return 获得的值
	 * @throws ValueNotExistsException
	 *             值名称不存在抛出异常
	 */
	Object getValueIfExists(String expr) throws ValueNotExistsException;

	/**
	 * 获得一个指定类型值
	 * 
	 * @param expr
	 *            值名称
	 * @return 获得的值,不存在返回<code>null</code>
	 */
	<T> T getValue(String expr, Class<T> asType);

	/**
	 * 获得一个指定类型值,值名称不存在则抛出异常
	 * 
	 * @param expr
	 *            值名称
	 * @return 获得的值
	 * @throws ValueNotExistsException
	 *             值名称不存在抛出异常
	 */
	<T> T getValueIfExists(String expr, Class<T> asType) throws ValueNotExistsException;

	/**
	 * 清除所有的值
	 */
	void clear();

	/**
	 * 获得原始存放的数据结构
	 * 
	 * @return
	 */
	Map<String, Object> getContext();

}