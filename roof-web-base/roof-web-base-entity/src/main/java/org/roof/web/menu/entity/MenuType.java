package org.roof.web.menu.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializable;
import com.alibaba.fastjson.serializer.JSONSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

public enum MenuType implements JSONSerializable {

	Front("前台", "Front"), BackGround("后台", "BackGround"), Mobile("手机", "Mobile");

	private String display;
	private String code;

	private MenuType(String display, String code) {
		this.display = display;
		this.code = code;
	}

	public String getDisplay() {
		return display;
	}

	public String getCode() {
		return code;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public void write(JSONSerializer serializer, Object fieldName, Type fieldType, int features) throws IOException {

		JSONObject object = new JSONObject();
		object.put("code",code);
		object.put("display",display);
		serializer.write(object);

	}

}
