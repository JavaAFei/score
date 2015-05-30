package com.ghy.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;


public class JsonUtils {

	public JsonUtils() {
		super();
	}

	

	/**
	 * 由对象内容转换成JSON格式
	 * @param obj
	 * @return
	 */
	public static String getJson(Object obj){
		
		ObjectMapper mapper = new ObjectMapper();
		
		/*
		 * 
		 * 处理空字段内容信�?
		 * 
		 */
		mapper.getSerializationConfig().setSerializationInclusion(
				Inclusion.NON_NULL);
		mapper.getSerializationConfig().disable(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_MAP_VALUES);
		mapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		String str = null;
		try {
			str = mapper.writeValueAsString(obj);
			
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		return str;
	}
	
	
	/**
	 * 由JSON格式转换成对象内�?
	 * @param obj 
	 * @param jp
	 * @return
	 */
	public static Object parsentJson(Object obj,String jp){
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		/*
		 * 
		 * 处理JSON字段名称不在对象中的问题
		 * 
		 */
		mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
		mapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		try {
			obj = mapper.readValue(jp, obj.getClass());
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		return obj;
	}
	
	/**
	 * 将一个JAVA对象转换成JSON格式的字符串
	 *
	 * @param target 转换的目标对�?
	 * @return
	 */
	public static String toJSON(final Object target) {
		return toJSON(target, null, null);
	}

	/**
	 * 将一个JAVA对象转换成JSON格式的JSON对象
	 *
	 * @param target
	 * @return
	 */
	public static Object toJsonObject(final Object target) {
		return toJsonObject(target, null, null);
	}

	/**
	 * 将一个JAVA对象转换成JSON格式的字符串
	 *
	 * @param target
	 *            转换的目标对�?
	 * @param datePattern
	 *            转换使用的日期格�?
	 * @return
	 */
	public static String toJSON(final Object target, final String datePattern) {
		return toJSON(target, null, datePattern);
	}

	/**
	 * 将一个JAVA对象转换成JSON格式的字符串
	 *
	 * @param target
	 *            转换的目标对�?
	 * @param excludes
	 *            转换排除的属性列�?
	 * @return
	 */
	public static String toJSON(final Object target, final String[] excludes) {
		return toJSON(target, excludes, null);
	}

	/**
	 * 将一个JAVA对象转换成JSON格式的字符串
	 *
	 * @param target 转换的目标对�?
	 * @param excludes  转换排除的属性列�?
	 * @param datePattern 转换使用的日期格�?
	 * @return
	 */
	public static String toJSON(final Object target, final String[] excludes, final String datePattern) {
		Object jsonObj = toJsonObject(target, excludes, datePattern);
		if (jsonObj == null) {
			return null;
		}
		return jsonObj.toString();
	}

	/**
	 * 将一个JAVA对象转换成JSON格式的JSON对象
	 *
	 * @param target 转换的目标对�?
	 * @param excludes  转换排除的属性列�?
	 * @param datePattern 转换使用的日期格�?
	 * @return
	 */
	public static Object toJsonObject(final Object target, final String[] excludes, final String datePattern) {
		if (target == null) {
			return null;
		}

		JsonConfig config = new JsonConfig();
		if (null != excludes && 0 != excludes.length) {// 排除指定的属�?
			config.setExcludes(excludes);
		}

		processDate(config, datePattern);
		processCalendar(config, datePattern);

		if (target instanceof Collection || target instanceof Object[]) {// 数组、List、Set的转�?
			return JSONArray.fromObject(target, config);
		} else {// Map、Bean的转�?
			return JSONObject.fromObject(target, config);
		}
	}

	/**
	 *
	 * @param s
	 * @param cls
	 * @return
	 */
	public  static Object toBean(String s, Class cls) {
		JSONObject jsonBean = JSONObject.fromObject(s);
		return JSONObject.toBean(jsonBean, cls);
	}

	/**
	 * 处理Calendar
	 *
	 * @param config
	 * @param datePattern
	 */
	private static void processCalendar(JsonConfig config, final String datePattern) {
		config.registerJsonValueProcessor(java.util.Calendar.class, new JsonValueProcessor() {
			@Override
			public Object processArrayValue(Object obj, JsonConfig jsonconfig) {
				return this.process(obj);
			}

			// 此方法将java.util.Date类型的属性转换成 JSON字符�?
			@Override
			public Object processObjectValue(String s, Object obj, JsonConfig jsonconfig) {
				return this.process(obj);
			}

			private String process(Object obj) {
				if (null == obj) {// 时间为null返回"";
					return "";
				}
				java.util.Calendar cal = (java.util.Calendar) obj;
				Date date = cal.getTime();
				java.text.SimpleDateFormat format = null;
				if (StringUtil.isNotBlank(datePattern)) {
					format = new java.text.SimpleDateFormat(datePattern.trim());
				} else {
					format = new java.text.SimpleDateFormat(DateUtil.yyyyMMddHHmmssSpt);
				}
				return format.format(date);
			}
		});
	}

	/**
	 * 处理Date
	 *
	 * @param config
	 * @param datePattern
	 */
	private static void processDate(JsonConfig config, final String datePattern) {
		config.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessor() {
			@Override
			public Object processArrayValue(Object obj, JsonConfig jsonconfig) {
				return this.process(obj);
			}

			// 此方法将java.util.Date类型的属性转换成 JSON字符�?
			@Override
			public Object processObjectValue(String s, Object obj, JsonConfig jsonconfig) {
				return this.process(obj);
			}

			private String process(Object obj) {
				if (null == obj) {// 时间为null返回"";
					return "";
				}
				java.util.Date date = (java.util.Date) obj;
				java.text.SimpleDateFormat format = null;
				if (StringUtil.isNotBlank(datePattern)) {
					format = new java.text.SimpleDateFormat(datePattern.trim());
				} else {
					format = new java.text.SimpleDateFormat(DateUtil.yyyyMMddHHmmssSpt);
				}
				return format.format(date);
			}
		});
	}

	/**
	 *
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> json2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// �?��层解�?
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解�?
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(json2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	
}
