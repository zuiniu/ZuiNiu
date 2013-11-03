package com.zuiniuwang.android.guardthief.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

/**
 * json工具类
 * 
 * @author guoruiliang
 * 
 */
public class JsonUtil {

	/**
	 * 把对象转换为json
	 * <P>
	 * 目前是public字段
	 * 
	 * @param c
	 *            类的类型
	 * @param object
	 *            对象
	 * @return json字符
	 */
	public static String toJson(Class c, Object object) {
		String result = "";
		try {
			Field[] fields = c.getDeclaredFields();
			JSONObject jsonObject = new JSONObject();
			for (Field field : fields) {
				// 必须为public
				if (field.getModifiers() == Modifier.PUBLIC) {
					// 如果是list,特殊处理
					if (field.getType().getName()
							.equalsIgnoreCase("java.util.List")) {
						JSONArray jsonArray = new JSONArray();
						List<String> values = (List<String>) field.get(object);
						if (values != null) {
							for (String v : values) {
								jsonArray.put(v);
							}
						}
						jsonObject.put(field.getName(), jsonArray);
					} else {
						jsonObject.put(field.getName(), field.get(object));
					}

				}

			}
			return jsonObject.toString();
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 从json中获取对象
	 * 
	 * @param c
	 *            类的类型
	 * @param json
	 *            json字符串
	 * @return 对象
	 */
	public static <T> T fromJson(Class<T> c, String json) {
		T object = null;
		try {
			if (!TextUtils.isEmpty(json)) {
				JSONObject jsonObject = new JSONObject(json);
				object = c.newInstance();

				Field[] fields = c.getDeclaredFields();
				for (Field field : fields) {
					if (field.getModifiers() == Modifier.PUBLIC) {
						if (field.getType().getName()
								.equalsIgnoreCase("java.util.List")) {
							JSONArray jsonArray=jsonObject.optJSONArray(field.getName());
							if(jsonArray!=null){
								int count=jsonArray.length();
								List<String> names=new ArrayList<String>();
								
								for(int i=0;i<count;i++){
									names.add(jsonArray.getString(i));
								}
								field.set(object, names);	
							}
							
						}else{
							field.set(object, jsonObject.opt(field.getName()));	
						}
						
					}
				}
			}
		} catch (Exception e) {
			Log.e("yy", "",e);
		}

		return object;
	}

	/**
	 * 转换成String,查看信息用
	 * 
	 * @param c
	 *            类的类型
	 * @param object
	 *            对象
	 * @return toString字符串
	 */
	public static String toString(Class c, Object object) {
		StringBuffer result = new StringBuffer();
		result.append(c.getSimpleName() + " [");
		try {
			Field[] fields = c.getDeclaredFields();
			for (Field field : fields) {
				// 必须为public
				if (field.getModifiers() == Modifier.PUBLIC) {
					result.append(field.getName() + " = "
							+ String.valueOf(field.get(object) + ", "));
				}

			}
		} catch (Exception e) {
		}

		result.append("]");
		return result.toString();
	}

}
