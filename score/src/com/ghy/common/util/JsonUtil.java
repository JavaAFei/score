package com.ghy.common.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * ���ڰ���Ͱ͵Ŀ�Դ��ĿFastJson��Json������
 * �й�����һЩ˵����
 * SerializeWriter���൱��StringBuffer
 * JSONArray���൱��List<Object>
 * JSONObject���൱��Map<String, Object>
 * @author zhoukun@sinovatech.com
 * @since 2013-05-13
 */
public class JsonUtil {
	/**
	 * ������ת����Json�ַ���,֧��JavaBea�����Ϻ�Map����
	 * @param <T>
	 * @param obj
	 * @return
	 */
	public static <T> String object2Json(T obj){
		return JSON.toJSONString(obj);
	}
	/**
	 * ��JSON�ַ���ת���ɶ���
	 * @param <T>
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Object(String text, Class<T> clazz){
		return JSON.parseObject(text, clazz);
	}
	/**
	 * ��JSON�ı�ת��ΪJSONObject����JSONArray 
	 * @param str
	 * @return
	 */
	public static Object str2JSONObject(String str){
		return JSON.parse(str);
	}
	/**
	 * ��JSON�ı�ת����JSONObject
	 * @param str
	 * @return
	 */
	public static JSONObject parseObject(String str){
		return JSON.parseObject(str);
	}
	/**
	 * ��JSON�ı�ת����JSONArray
	 * @param str
	 * @return
	 */
	public static JSONArray str2JSONArray(String str){
		return JSON.parseArray(str);
	}
	/**
	 * ��JSON�ı�parse��JavaBean���� 
	 * @param <T>
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> str2List(String str, Class<T> clazz){
		return JSON.parseArray(str, clazz);
	}
	/**
	 * ��JavaBean���л�Ϊ����ʽ��JSON�ı� 
	 * @param object
	 * @param prettyFormat
	 * @return
	 */
	public static <T> String toJSONString(T obj, boolean prettyFormat){
		return JSON.toJSONString(obj,prettyFormat);
	}
	/**
	 * ��JavaBeanת��ΪJSONObject����JSONArray��
	 * @param <T>
	 * @param obj
	 * @return
	 */
	public static <T> Object toJSON(T obj){
		return JSON.toJSON(obj);
	}
	public static void main(String[] args) {
		boolean result = true;
		System.out.println(object2Json(result));
	}
}
