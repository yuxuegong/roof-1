package org.roof.chain.valuestack;

import org.roof.chain.exceptions.ValueExistsException;
import org.roof.chain.exceptions.ValueNotExistsException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 值栈的默认实现 <br>
 * <b>非线程安全</b>
 * 
 * @author liuxin
 *
 */
public class DefaultValueStack implements Serializable, ValueStack {

	private static final long serialVersionUID = 6050299171843288821L;

	protected Map<String, Object> context;

	public DefaultValueStack() {
		this.context = new HashMap<String, Object>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.netease.urs.chain.valuestack.ValueStack#insertOrUpdate(java.lang.
	 * String, java.lang.Object)
	 */
	@Override
	public void insertOrUpdate(String expr, Object value) {
		context.put(expr, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.netease.urs.chain.valuestack.ValueStack#insertOrUpdateAll(java.util.
	 * Map)
	 */
	@Override
	public void insertOrUpdateAll(Map<String, ?> map) {
		if (map != null) {
			context.putAll(map);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netease.urs.chain.valuestack.ValueStack#insert(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void insert(String expr, Object value) throws ValueExistsException {
		if (existsValue(expr)) {
			throw new ValueExistsException(expr + "值已经存在!");
		}
		insertOrUpdate(expr, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netease.urs.chain.valuestack.ValueStack#update(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void update(String expr, Object value) throws ValueNotExistsException {
		if (!existsValue(expr)) {
			throw new ValueNotExistsException(expr + "值不存在!");
		}
		context.put(expr, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.netease.urs.chain.valuestack.ValueStack#existsValue(java.lang.String)
	 */
	@Override
	public boolean existsValue(String expr) {
		return !(context.get(expr) == null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.netease.urs.chain.valuestack.ValueStack#getString(java.lang.String)
	 */
	@Override
	public String getString(String expr) {
		return this.getValue(expr, String.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.netease.urs.chain.valuestack.ValueStack#getStringIfExists(java.lang.
	 * String)
	 */
	@Override
	public String getStringIfExists(String expr) throws ValueNotExistsException {
		return this.getValueIfExists(expr, String.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.netease.urs.chain.valuestack.ValueStack#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String expr) {
		return context.get(expr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.netease.urs.chain.valuestack.ValueStack#getValueIfExists(java.lang.
	 * String)
	 */
	@Override
	public Object getValueIfExists(String expr) throws ValueNotExistsException {
		if (!existsValue(expr)) {
			throw new ValueNotExistsException(expr + "值不存在!");
		}
		return getValue(expr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.netease.urs.chain.valuestack.ValueStack#getValue(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getValue(String expr, Class<T> asType) {
		Object v = this.getValue(expr);
		if (v == null || (v.getClass() != asType && v.getClass().isAssignableFrom(asType))) {
			return null;
		}
		return (T) v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.netease.urs.chain.valuestack.ValueStack#getValueIfExists(java.lang.
	 * String, java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getValueIfExists(String expr, Class<T> asType) throws ValueNotExistsException {
		Object v = this.getValueIfExists(expr);
		if (v == null || (v.getClass() != asType && v.getClass().isAssignableFrom(asType))) {
			throw new ValueNotExistsException("类型为 " + asType.getName() + " 的 " + expr + " 值不存在!");
		}
		return (T) v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netease.urs.chain.valuestack.ValueStack#clear()
	 */
	@Override
	public void clear() {
		context.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netease.urs.chain.valuestack.ValueStack#getContext()
	 */
	@Override
	public Map<String, Object> getContext() {
		return context;
	}

}
