package com.ghy.common.excel;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

@SuppressWarnings("unchecked")
public interface PoiWriteExcelBean {

	/**
	 * 创建普通报表
	 * 
	 * @param em
	 *            报表模板对象
	 * @param wb
	 *            当前工作簿
	 * @param fialList
	 *            报表头部内容列表
	 * @param clist
	 *            报表数据列表
	 * @throws Exception
	 */
	public void writePoiExcel(ExcelModel em, Workbook wb, List fialList, List clist) throws Exception;

	/**
	 * 创建多sheet不同格式excel文件
	 * 
	 * @param em
	 *            报表模板对象
	 * @param wb
	 *            当前工作簿
	 * @throws Exception
	 */
	public void writeManySheet(ExcelModel em, Workbook wb) throws Exception;

	/**
	 * 多报表合并
	 * 
	 * @param em
	 *            报表模板对象
	 * @param wb
	 *            当前工作簿
	 * @param clist
	 *            数据集合
	 * @param fialList
	 *            报表头部内容列表
	 * @throws Exception
	 */
	public void writeExcelUnite(ExcelModel em, Workbook wb, List clist, List fialList) throws Exception;

	/**
	 * 初始化excel文件
	 * 
	 * @param excelName
	 *            文件路径
	 * @throws Exception
	 * @return
	 */
	public Workbook JudgeExcelType(String excelName) throws Exception;

	/**
	 * 生成excel文件
	 * 
	 * @param excelName
	 *            文件路径
	 * @param wb
	 *            当前工作簿
	 * @throws Exception
	 */
	public void closeWorkbook(String excelName, Workbook workbook) throws Exception;

	/**
	 * 表头合并单元格excel文件
	 * 
	 * @param em
	 *            报表模板对象
	 * @param wb
	 *            当前工作簿
	 * @throws Exception
	 */
	public void UniteHeadSheet(ExcelModel em, Workbook wb, List fialList, List uniteRowList) throws Exception;
}
