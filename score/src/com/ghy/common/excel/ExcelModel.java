package com.ghy.common.excel;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings( { "unchecked", "serial" })
public class ExcelModel implements Serializable {

	/**
	 * 表头列表，由N个List组成，List内存放报表头部内容。
	 */
	public List titleList;

	/**
	 * 数据列表，由N个Map组成。
	 */
	public List dataList;

	/**
	 * 表头起始行，默认值为"0"。
	 */
	public int cellNum = 0;

	/**
	 * sheet名称。
	 */
	public String sheetName;

	/**
	 * 是否自动调整列宽，值为"0"时自动调整列宽，默认值为"0"。
	 */
	public int autoWidth = 0;

	/**
	 * 数据之间间隔数，默认值为"0"。
	 */
	public int spaceNum = 0;

	/**
	 * 每sheet最大数据量，默认值为"0"。
	 */
	public int countNum = 0;

	/**
	 * 需加样式列集合
	 */
	public List styleCell;

	/**
	 * 指定数字列
	 */
	public List<Integer> numberRow;

	/**
	 * 每sheet最大数据量，默认值为"0"。
	 */
	public int titleH = 30;

	/**
	 * 表头变颜色
	 */
	public List<Integer> titleColor;

	/**
	 * 某列按字符串处理
	 */
	public List<Integer> strList;

	/**
	 * 背景变颜色
	 */
	public List<Integer> titleBgColor;

	public List getTitleList() {
		return titleList;
	}

	public void setTitleList(List titleList) {
		this.titleList = titleList;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public int getCellNum() {
		return cellNum;
	}

	public void setCellNum(int cellNum) {
		this.cellNum = cellNum;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public int getAutoWidth() {
		return autoWidth;
	}

	public void setAutoWidth(int autoWidth) {
		this.autoWidth = autoWidth;
	}

	public int getSpaceNum() {
		return spaceNum;
	}

	public void setSpaceNum(int spaceNum) {
		this.spaceNum = spaceNum;
	}

	public int getCountNum() {
		return countNum;
	}

	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}

	public List getStyleCell() {
		return styleCell;
	}

	public void setStyleCell(List styleCell) {
		this.styleCell = styleCell;
	}

	/**
	 * @return the titleH
	 */
	public int getTitleH() {
		return titleH;
	}

	/**
	 * @param titleH
	 *            the titleH to set
	 */
	public void setTitleH(int titleH) {
		this.titleH = titleH;
	}

	/**
	 * @return the numberRow
	 */
	public List<Integer> getNumberRow() {
		return numberRow;
	}

	/**
	 * @param numberRow
	 *            the numberRow to set
	 */
	public void setNumberRow(List<Integer> numberRow) {
		this.numberRow = numberRow;
	}

	/**
	 * @return the titleColor
	 */
	public List<Integer> getTitleColor() {
		return titleColor;
	}

	/**
	 * @param titleColor
	 *            the titleColor to set
	 */
	public void setTitleColor(List<Integer> titleColor) {
		this.titleColor = titleColor;
	}

	/**
	 * @return the titleBgColor
	 */
	public List<Integer> getTitleBgColor() {
		return titleBgColor;
	}

	/**
	 * @param titleBgColor
	 *            the titleBgColor to set
	 */
	public void setTitleBgColor(List<Integer> titleBgColor) {
		this.titleBgColor = titleBgColor;
	}

	public List<Integer> getStrList() {
		return strList;
	}

	public void setStrList(List<Integer> strList) {
		this.strList = strList;
	}
}
