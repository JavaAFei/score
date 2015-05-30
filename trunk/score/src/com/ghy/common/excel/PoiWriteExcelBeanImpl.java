package com.ghy.common.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ghy.common.util.StringUtil;
 

@SuppressWarnings( { "unchecked", "unused", "static-access" })
public class PoiWriteExcelBeanImpl implements PoiWriteExcelBean {
	private static final String LIGHT_YELLOW = "LIGHT_YELLOW";
	private static final String YELLOW = "YELLOW";

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
	public void writePoiExcel(ExcelModel em, Workbook wb, List fialList, List clist) throws Exception {
		// 声明变量，用于计算创建sheet数量
		int sheetNum = 0;
		// 设定每sheet最大存储行数
		int num = 65000;
		// 定义百分数格式
		HSSFCellStyle cellPercentStyleStr = (HSSFCellStyle) wb.createCellStyle();
		HSSFCellStyle cellPercentStyle = (HSSFCellStyle) wb.createCellStyle();
		cellPercentStyle.setDataFormat((short) 9);// 百分数
		if (em.getCountNum() != 0 && em.getCountNum() <= num)
			num = em.getCountNum();
		// 计算创建sheet数量
		if (clist.size() <= num)
			sheetNum = 1;
		else {
			if (clist.size() % num == 0)
				sheetNum = clist.size() / num;
			else
				sheetNum = clist.size() / num + 1;
		}
		// 声明sheet，cell，row对象
		Sheet sheet;
		Cell cell;
		Row row;
		/* ===========haog=========== */
		// 声明报表数据样式
		CellStyle style;
		Font font;
		style = wb.createCellStyle();
		font = wb.createFont();
		// 字体颜色
		font.setColor(HSSFFont.COLOR_RED);
		style.setFont(font);

		// 按字符串处理的列的List
		List strList = em.getStrList();
		/* ===========haog=========== */
		// 循环创建sheet
		for (int c = 0; c < sheetNum; c++) {
			// 在Excel工作簿中建一sheet并指定名称
			if (c == 0)
				sheet = wb.createSheet(em.getSheetName());

			else
				sheet = wb.createSheet(em.getSheetName() + "_" + c);
			// haog 冻结窗口
			// sheet.createFreezePane(0, 3);
			// 创建一行，存放报表头部
			row = sheet.createRow((short) 0);
			// 创建报表头部
			writeExcelHead(fialList, wb, row, em, sheet);
			// 声明变量z
			int z = 1;
			// 填充单元格数据
			for (int i = c * num; i < clist.size(); i++) {
				if (i < num * (c + 1)) {
					Map map = (Map) clist.get(i);
					// 创建一行
					row = sheet.createRow(z);
					// 填充数据
					for (int j = 0; j < map.size(); j++) {
						cell = row.createCell((short) j);
						if (null != strList) {
							if (strList.contains(j)) {
								this.formatCellStr(cell, getValue(map.get(j)), cellPercentStyleStr);
							} else {
								this.formatCell(cell, getValue(map.get(j)), cellPercentStyle);
							}
						} else {
							// 赋值
							this.formatCell(cell, getValue(map.get(j)), cellPercentStyle);
						}

						// 赋值
						// this.formatCell(cell, getValue(map.get(j)),
						// cellPercentStyle);

						/*
						 * if (!isNumber(em, j) ||
						 * "-".equals(getValue(map.get(j)))) { if
						 * (StringUtil.contains(getValue(map.get(j)), "%")) {
						 * String s =
						 * getValue(map.get(j)).substring(0,getValue(map.get(j)).length()-1);
						 * cell.setCellValue(Double.parseDouble(s)/100);
						 * cell.setCellStyle(cellPercentStyle); }else {
						 * cell.setCellValue(getValue(map.get(j))); } }else { //
						 * 数字列
						 * cell.setCellValue(Double.parseDouble(getValue(map.get(j)))); }
						 */
						/* ===========haog=========== */
						if (em.getStyleCell() != null) {// 填充数据的样式
							for (int s = 0; s < em.getStyleCell().size(); s++) {
								if (Integer.parseInt(em.getStyleCell().get(s).toString()) == j) {
									cell.setCellStyle(style);
								}
							}
						}
						/* ===========haog=========== */
						/*
						 * // 自动调整列宽 if (em.getAutoWidth() == 0)
						 * sheet.autoSizeColumn((short) j);
						 */
					}
					z++;
				} else {
					break;
				}
			}
		}
	}

	/**
	 * 创建多sheet不同格式excel文件
	 * 
	 * @param em
	 *            报表模板对象
	 * @param wb
	 *            当前工作簿
	 * @throws Exception
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.oppo.common.excel.PoiWriteExcelBean#writeManySheet(com.oppo.common.excel.ExcelModel,
	 *      org.apache.poi.ss.usermodel.Workbook)
	 */
	public void writeManySheet(ExcelModel em, Workbook wb) throws Exception {
		// 声明sheet，cell，row对象
		Sheet sheet;
		sheet = wb.createSheet(em.getSheetName());
		Cell cell = null;
		Row row = null;
		// 表头列表
		List titleList = em.getTitleList();
		// 数据列表
		List dataList = em.getDataList();
		// 表头起始行
		int cellNum = em.getCellNum();
		// 创建报表头部
		writeExcelHead(titleList, wb, sheet, cellNum, em);

		// 定义百分数格式
		HSSFCellStyle cellPercentStyle = (HSSFCellStyle) wb.createCellStyle();
		cellPercentStyle.setDataFormat((short) 9);// 百分数

		// 填充数据
		for (int i = 0; i < dataList.size(); i++) {
			// 数据开始行数
			int dataCellNum = i + cellNum + titleList.size();
			// 创建一行
			row = sheet.createRow(dataCellNum);
			// 填充数据
			Map dataMap = (Map) dataList.get(i);
			for (int j = 0; j < dataMap.size(); j++) {
				cell = row.createCell((short) j);
				// 赋值
				this.formatCell(cell, getValue(dataMap.get(j)), cellPercentStyle);

				// 忘记了 暂时注销
				/*
				 * if (!isNumber(em, j) || "-".equals(getValue(dataMap.get(j)))) {
				 * if (StringUtil.contains(getValue(dataMap), "%")) { String s =
				 * getValue(dataMap.get(j)).substring(0,getValue(dataMap.get(j)).length()-1);
				 * cell.setCellValue(Double.parseDouble(s)/100);
				 * cell.setCellStyle(cellPercentStyle); }else {
				 * cell.setCellValue(getValue(dataMap.get(j))); } }else { // 数字列
				 * cell.setCellValue(Double.parseDouble(getValue(dataMap.get(j)))); }
				 */
				/*
				 * // 自动调整列宽 if(em.getAutoWidth() == 0)
				 * sheet.autoSizeColumn((short) j);
				 */
			}
		}
	}

	/**
	 * 表头合并单元格excel文件
	 * 
	 * @param em
	 *            报表模板对象
	 * @param wb
	 *            当前工作簿
	 * @throws Exception
	 */
	public void UniteHeadSheet(ExcelModel em, Workbook wb, List fialList, List uniteRowList) throws Exception {
		// 声明sheet，cell，row对象
		Sheet sheet;
		sheet = wb.createSheet(em.getSheetName());
		Cell cell = null;
		Row row = null;
		// 表头列表
		List titleList = em.getTitleList();
		// 数据列表
		List dataList = em.getDataList();
		// 表头起始行
		int cellNum = em.getCellNum();
		// 创建报表头部
		writeUniteHead(fialList, em, wb, sheet, uniteRowList);
		writeExcelHead(titleList, wb, sheet, cellNum, em);
		// 声明报表数据样式
		CellStyle style;
		Font font;
		style = wb.createCellStyle();
		font = wb.createFont();
		// 字体颜色
		font.setColor(HSSFFont.COLOR_RED);
		style.setFont(font);
		// 定义百分数格式
		HSSFCellStyle cellPercentStyle = (HSSFCellStyle) wb.createCellStyle();
		cellPercentStyle.setDataFormat((short) 9);// 百分数
		// 填充数据
		for (int i = 0, n = dataList.size(); i < n; i++) {
			// 数据开始行数
			int dataCellNum = i + cellNum + titleList.size();
			// 创建一行
			row = sheet.createRow(dataCellNum);
			// 填充数据
			Map dataMap = (Map) dataList.get(i);
			for (int j = 0, m = dataMap.size(); j < m; j++) {
				cell = row.createCell((short) j);
				// cell.setCellValue(getValue(dataMap.get(j)));
				// 赋值
				this.formatCell(cell, getValue(dataMap.get(j)), cellPercentStyle);

				// 忘记了 暂时注销
				/*
				 * if (!isNumber(em, j) || "-".equals(getValue(dataMap.get(j)))) {
				 * if (StringUtil.contains(getValue(dataMap.get(j)), "%")) {
				 * String s =
				 * getValue(dataMap.get(j)).substring(0,getValue(dataMap.get(j)).length()-1);
				 * cell.setCellValue(Double.parseDouble(s)/100);
				 * cell.setCellStyle(cellPercentStyle); }else {
				 * cell.setCellValue(getValue(dataMap.get(j))); } }else { // 数字列
				 * cell.setCellValue(Double.parseDouble(getValue(dataMap.get(j)))); }
				 */
				if (em.getStyleCell() != null) {
					for (int s = 0; s < em.getStyleCell().size(); s++) {
						if (Integer.parseInt(em.getStyleCell().get(s).toString()) == j) {
							cell.setCellStyle(style);
						}
					}
				}
				// 自动调整列宽
				/*
				 * if(em.getAutoWidth() == 0) sheet.autoSizeColumn((short) j);
				 */
			}
		}
	}

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
	 *            头部内容列表
	 * @throws Exception
	 */
	public void writeExcelUnite(ExcelModel em, Workbook wb, List clist, List fialList) throws Exception {
		// 声明sheet，cell，row对象
		Sheet sheet;
		Cell cell;
		Row row;
		sheet = wb.createSheet(em.getSheetName());
		// 表头列表
		List titleList = em.getTitleList();
		// 数据列表
		List dataList = em.getDataList();
		// 写入表头开始行数
		int cellNum = em.getCellNum();
		// 声明样式对象和设置字体
		CellStyle style = this.setStyle(wb);
		// 在指定行创建第一个报表头部
		int titleNum = em.getCellNum();
		// 定义百分数格式
		HSSFCellStyle cellPercentStyle = (HSSFCellStyle) wb.createCellStyle();
		cellPercentStyle.setDataFormat((short) 9);// 百分数

		/*
		 * row = sheet.createRow((short) titleNum); row.setHeightInPoints(30);
		 */
		int z = titleNum + 1;
		for (int i = 0; fialList != null && i < fialList.size(); i++) {
			/*
			 * if (i != 0) { z = z + em.getSpaceNum(); // 创建一行，存放表头 row =
			 * sheet.createRow(z); row.setHeightInPoints(30); z++; }
			 */
			// 创建报表头部数据
			List fList = (List) fialList.get(i);
			for (int f = 0; f < fList.size(); f++) {
				if (i == 0) {
					row = sheet.createRow((short) f + titleNum);
					z = z + f;
				} else {
					if (f == 0)
						z = z + em.getSpaceNum();
					// 创建一行，存放表头
					row = sheet.createRow(z);
					row.setHeightInPoints(em.getTitleH());
					z++;
				}
				row.setHeightInPoints(em.getTitleH());
				List list = (List) fList.get(f);
				for (int b = 0; b < list.size(); b++) {
					cell = row.createCell((short) b);
					cell.setCellValue(getValue(list.get(b)));
					cell.setCellStyle(style);
				}
			}
			/*
			 * List list = (List) fialList.get(i); for (int b = 0; b <
			 * list.size(); b++) { cell = row.createCell((short) b);
			 * cell.setCellValue(list.get(b).toString());
			 * cell.setCellStyle(style); }
			 */
			// 生成业务数据
			List contentList = (List) clist.get(i);
			for (int cm = 0; contentList != null && cm < contentList.size(); cm++) {
				// 创建一行
				row = sheet.createRow(z);
				// 得到excel内容
				Map contentMap = (Map) contentList.get(cm);
				// 填充单元格数据
				for (int j = 0; j < contentMap.size(); j++) {
					cell = row.createCell((short) j);
					// 赋值
					this.formatCell(cell, getValue(contentMap.get(j)), cellPercentStyle);

					/*
					 * if (!isNumber(em, j) ||
					 * "-".equals(getValue(contentMap.get(j)))) {
					 * 
					 * }else { // 数字列
					 * cell.setCellValue(Double.parseDouble(getValue(contentMap.get(j)))); }
					 */
					/*
					 * // 自动调整列宽 // if (em.getAutoWidth() == 0) //
					 * sheet.autoSizeColumn((short) j);
					 */
				}
				z++;
			}
		}
	}

	/**
	 * 创建简单报表头部
	 * 
	 * @param fialList
	 *            报表头部
	 * @param row
	 *            当前行
	 * @throws Exception
	 */
	public void writeExcelHead(List fialList, Workbook wb, Row row, Sheet sheet, ExcelModel em) throws Exception {
		List<Integer> colorList = em.getTitleColor(); // 颜色list
		try {
			// 声明样式对象和设置字体
			CellStyle style = this.setStyle(wb);
			row.setHeightInPoints(em.getTitleH());
			// 创建报表头部
			for (int i = 0; i < fialList.size(); i++) {
				Cell cell = row.createCell((short) i);
				cell.setCellValue(getValue(fialList.get(i)));
				cell.setCellStyle(style);
				// 自动调整列宽
				if (em.getAutoWidth() == 0)
					sheet.autoSizeColumn((short) i);
				// 设定字体红色
				if (null != colorList) {
					if (colorList.contains(i)) {
						cell.setCellStyle(this.setTitleRead(wb));
						sheet.setColumnWidth(i, 2048);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建简单报表头部
	 * 
	 * @param fialList
	 *            报表头部
	 * @param row
	 *            当前行
	 * @throws Exception
	 */
	public void writeExcelHead(List fialList, Workbook wb, Row row, ExcelModel em, Sheet sheet) throws Exception {
		List<Integer> colorList = em.getTitleColor(); // 颜色list
		List<Integer> bgcolorList = em.getTitleBgColor(); // 得到背景色
		try {
			// 声明样式对象和设置字体
			CellStyle style = this.setStyle(wb);
			row.setHeightInPoints(em.getTitleH());
			// 创建报表头部
			for (int i = 0; i < fialList.size(); i++) {
				Cell cell = row.createCell((short) i);
				cell.setCellValue(getValue(fialList.get(i)));
				cell.setCellStyle(style);
				// 自动调整列宽
				if (em.getAutoWidth() == 0)
					sheet.autoSizeColumn((short) i);
				// 设定字体红色
				if (null != colorList) {
					if (colorList.contains(i)) {
						cell.setCellStyle(this.setTitleRead(wb));
						sheet.setColumnWidth(i, 2048);
					}
				}
				// 设定背景黄色
				if (null != bgcolorList && null != colorList) {
					boolean bb = false;
					if (bgcolorList.contains(i) && !colorList.contains(i)) {
						cell.setCellStyle(this.setTitleBgColor(wb, YELLOW, bb));
					} else if (bgcolorList.contains(i) && colorList.contains(i)) {
						bb = true;
						cell.setCellStyle(this.setTitleBgColor(wb, YELLOW, bb));
					}
				} else if (null != bgcolorList && null == colorList) {
					cell.setCellStyle(this.setTitleBgColor(wb, YELLOW, false));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 指定行创建报表头部
	 * 
	 * @param titleList
	 *            报表头部
	 * @param wb
	 *            当前工作簿
	 * @param sheet
	 *            当前表
	 * @param cellNum
	 *            表头起始行
	 * @throws Exception
	 */
	/*
	 * public void writeExcelHead(List titleList,Workbook wb, Sheet sheet,int
	 * cellNum) throws Exception { try { // 声明样式对象和设置字体 CellStyle style =
	 * this.setStyle(wb); // 创建报表头部 Cell cell; for(int t =0;t <
	 * titleList.size();t++){ Row row = sheet.createRow((short)(t+cellNum));
	 * row.setHeightInPoints(30); List nextList = (List)titleList.get(t);
	 * for(int i =0; i < nextList.size(); i++){ cell =
	 * row.createCell((short)(i)); cell.setCellValue(getValue(nextList.get(i)));
	 * cell.setCellStyle(style); } } } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	/**
	 * 指定行创建报表头部
	 * 
	 * @param titleList
	 *            报表头部
	 * @param wb
	 *            当前工作簿
	 * @param sheet
	 *            当前表
	 * @param cellNum
	 *            表头起始行
	 * @throws Exception
	 */
	public void writeExcelHead(List titleList, Workbook wb, Sheet sheet, int cellNum, ExcelModel em) throws Exception {
		List<Integer> colorList = em.getTitleColor(); // 颜色list
		try {
			// 声明样式对象和设置字体
			CellStyle style = this.setStyle(wb);
			// 创建报表头部
			Cell cell;
			for (int t = 0; t < titleList.size(); t++) {
				Row row = sheet.createRow((short) (t + cellNum));
				row.setHeightInPoints(em.getTitleH());
				List nextList = (List) titleList.get(t);
				for (int i = 0; i < nextList.size(); i++) {
					cell = row.createCell((short) (i));
					cell.setCellValue(getValue(nextList.get(i)));
					cell.setCellStyle(style);
					// 自动调整列宽
					if (em.getAutoWidth() == 0)
						sheet.autoSizeColumn((short) i);
					// 设定字体红色
					if (null != colorList) {
						if (colorList.contains(i)) {
							cell.setCellStyle(this.setTitleRead(wb));
							sheet.setColumnWidth(i, 2048);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建合并单元格报表头部
	 * 
	 * @param fialList
	 *            报表头部
	 * @param em
	 *            报表模板对象
	 * @param wb
	 *            当前工作簿
	 * @param uniteRowList
	 *            需合并单元格集合
	 * @throws Exception
	 */
	private void writeUniteHead(List fialList, ExcelModel em, Workbook wb, Sheet sheet, List uniteRowList)
			throws Exception {
		// 声明样式对象和设置字体
		CellStyle style = this.setStyle(wb);
		// 声明变量
		int endTemp = 0;
		int num = 0;
		int fnum = 0;
		int unum = 0;
		List uniteRowListTemp = new ArrayList();
		// 生成新的合并单元格集合
		for (int u = 0; u < uniteRowList.size(); u++) {
			Map uniteRowMap = new HashMap();
			Map mapTemp = (Map) uniteRowList.get(u);
			if (u != 0) {
				if (Integer.parseInt(mapTemp.get(0).toString()) - (endTemp + 1) == 0) {
					uniteRowMap = mapTemp;
					uniteRowListTemp.add(uniteRowMap);
				} else {
					int et = endTemp + 1;
					for (int t = 0; t < Integer.parseInt(mapTemp.get(0).toString()) - (endTemp + 1); t++) {
						Map unMap = new HashMap();
						unMap.put(0, et);
						unMap.put(1, et);
						uniteRowListTemp.add(unMap);
						et++;
					}
					uniteRowMap = mapTemp;
					uniteRowListTemp.add(uniteRowMap);
				}
				endTemp = Integer.parseInt(uniteRowMap.get(1).toString());
			} else {
				if (Integer.parseInt(mapTemp.get(0).toString()) > 0) {
					for (int t = 0; t < Integer.parseInt(mapTemp.get(0).toString()); t++) {
						Map unMap = new HashMap();
						unMap.put(0, t);
						unMap.put(1, t);
						uniteRowListTemp.add(unMap);
					}
					uniteRowMap = mapTemp;
					uniteRowListTemp.add(uniteRowMap);
				} else {
					uniteRowMap = mapTemp;
					uniteRowListTemp.add(uniteRowMap);
				}
				endTemp = Integer.parseInt(uniteRowMap.get(1).toString());
			}
		}
		// 如果新合并单元格集合小于报表头部数量，累加。
		fnum = fialList.size();
		unum = uniteRowListTemp.size();
		if (fnum - unum > 0) {
			for (int j = 0; j < fnum - unum; j++) {
				if (j == 0) {
					Map uMap = (Map) uniteRowListTemp.get(unum - 1);
					num = Integer.parseInt(uMap.get(1).toString()) + 1;
				}
				Map uMapTemp = new HashMap();
				uMapTemp.put(0, num);
				uMapTemp.put(1, num);
				uniteRowListTemp.add(uMapTemp);
				num++;
			}
		}
		// 调用合并单元格方法
		merged(sheet, 0, uniteRowListTemp);
		// 创建报表头部
		Row row = sheet.createRow((short) 0);
		row.setHeightInPoints(em.getTitleH());
		List<Integer> colorList = em.getTitleBgColor(); // 得到背景色
		for (int i = 0; i < fialList.size(); i++) {
			if (i < uniteRowListTemp.size()) {
				Map fialMap = (Map) uniteRowListTemp.get(i);
				Cell cell = row.createCell((short) Integer.parseInt(fialMap.get(0).toString()));
				cell.setCellValue(getValue(fialList.get(i)));
				cell.setCellStyle(style);
				// 设定背景黄色
				if (null != colorList) {
					if (colorList.contains(i)) {
						cell.setCellStyle(this.setTitleBgYellow(wb));
					}
				}
			} else {
				Cell cell = row.createCell((short) i);
				cell.setCellValue(getValue(fialList.get(i)));
				cell.setCellStyle(style);
				// 设定背景黄色
				if (null != colorList) {
					if (colorList.contains(i)) {
						cell.setCellStyle(this.setTitleBgYellow(wb));
					}
				}
			}
			// 自动调整列宽
			if (em.getAutoWidth() == 0)
				sheet.autoSizeColumn((short) i);

		}
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 *            当前工作表
	 * @param rowNum
	 *            在第rowNum行合并
	 * @param uniteRowList
	 *            需合并单元格集合
	 * @throws Exception
	 */
	private static void merged(Sheet sheet, int rowNum, List uniteRowList) throws Exception {
		int start = 0;
		int end = 0;
		for (int m = 0; m < uniteRowList.size(); m++) {
			Map map = (Map) uniteRowList.get(m);
			start = Integer.parseInt(map.get(0).toString());
			end = Integer.parseInt(map.get(1).toString());
			CellRangeAddress cellRangeAddress = new CellRangeAddress(rowNum, rowNum, (short) (start), (short) (end));
			sheet.addMergedRegion(cellRangeAddress);
		}
	}

	/**
	 * 初始化excel文件
	 * 
	 * @param excelName
	 *            文件路径
	 * @throws Exception
	 * @return
	 */
	public Workbook JudgeExcelType(String excelName) throws Exception {
		Workbook workbook = null;
		try {
			String filePath = excelName.substring(0, excelName.lastIndexOf("\\") + 1);
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
			String excelType = excelName.substring(excelName.lastIndexOf(".") + 1);
			if (excelType.equals("xls") || excelType.equals("xlsx")) {
				if (excelType.equals("xls"))
					workbook = new HSSFWorkbook();
				else
					workbook = new XSSFWorkbook();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	/**
	 * 生成excel文件
	 * 
	 * @param excelName
	 *            文件路径
	 * @param wb
	 *            当前工作簿
	 * @throws Exception
	 */
	public void closeWorkbook(String excelName, Workbook wb) throws Exception {
		// 生成文件
		FileOutputStream fileOut = new FileOutputStream(excelName);
		wb.write(fileOut);
		fileOut.close();
	}

	/**
	 * 表头变红色
	 * 
	 * @param wb
	 * @return
	 */
	public CellStyle setTitleRead(Workbook wb) {
		CellStyle style;
		Font font;
		style = wb.createCellStyle();
		font = wb.createFont();
		// 垂直对齐
		style.setVerticalAlignment((short) 2);
		// 自动换行
		style.setWrapText(true);
		// 字体颜色
		font.setColor(HSSFFont.COLOR_RED);
		// 字号
		font.setFontHeightInPoints((short) 10);
		// 粗体
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设定样式的的背景颜色
		// style.setFillForegroundColor(HSSFColor.YELLOW.index);
		// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFont(font);
		return style;
	}

	/**
	 * 表头背景变黄色
	 * 
	 * @param wb
	 * @return
	 */
	public CellStyle setTitleBgYellow(Workbook wb) {
		CellStyle style;
		Font font;
		style = wb.createCellStyle();
		// font = wb.createFont();
		// 垂直对齐
		style.setVerticalAlignment((short) 2);
		// 自动换行
		style.setWrapText(true);
		// 字体颜色
		// font.setColor(HSSFFont.COLOR_RED);
		// 字号
		// font.setFontHeightInPoints((short) 10);
		// 粗体
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设定样式的的背景颜色
		style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style.setFillForegroundColor(HSSFColor.YELLOW.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		return style;
	}

	/**
	 * 表头背景变黄色
	 * 
	 * @param wb
	 * @return
	 */
	public CellStyle setTitleBgColor(Workbook wb, String str, boolean bb) {
		CellStyle style;
		Font font;
		style = wb.createCellStyle();

		// 垂直对齐
		style.setVerticalAlignment((short) 2);
		// 自动换行
		style.setWrapText(true);
		// 设置边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);

		// 设置一个单元格边框颜色
		style.setRightBorderColor(IndexedColors.LIME.getIndex());
		style.setLeftBorderColor(IndexedColors.LIME.getIndex());
		style.setBottomBorderColor(IndexedColors.LIME.getIndex());
		style.setTopBorderColor(IndexedColors.LIME.getIndex());
		if (bb) {
			font = wb.createFont();
			// 字体颜色
			font.setColor(HSSFFont.COLOR_RED);
			// 字号
			// font.setFontHeightInPoints((short) 10);
			// 粗体
			// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
		}

		// 设定样式的的背景颜色
		if (str.equals("LIGHT_YELLOW")) {
			style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		}
		if (str.equals("YELLOW")) {
			style.setFillForegroundColor(HSSFColor.YELLOW.index);
		}
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		return style;
	}

	/**
	 * 表头样式
	 * 
	 * @param wb
	 *            当前工作簿
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public CellStyle setStyle(Workbook wb) throws Exception {
		CellStyle style;
		Font font;
		style = wb.createCellStyle();
		font = wb.createFont();
		// 垂直对齐
		style.setVerticalAlignment((short) 2);
		// 自动换行
		style.setWrapText(true);
		// 字体颜色
		// font.setColor(HSSFFont.COLOR_RED);
		// 字号
		font.setFontHeightInPoints((short) 10);
		// 粗体
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设定样式的的背景颜色
		// style.setFillForegroundColor(HSSFColor.YELLOW.index);
		// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFont(font);
		return style;
	}

	/**
	 * 判断传入是否为null 如果是 则替换成空字符串
	 * 
	 * @param obj
	 * @return
	 */
	public String getValue(Object obj) {
		return null == obj ? "" : obj.toString();
	}

	/**
	 * 判断所传列是否为数字列
	 * 
	 * @param list
	 * @param i
	 * @return
	 */
	public boolean isNumber(ExcelModel em, int i) {
		return null != em.getNumberRow() ? em.getNumberRow().contains(i) : false;
	}

	/**
	 * 根据数据填写cell
	 * 
	 * @param cell
	 * @param value
	 * @param cellPercentStyle
	 */
	public static void formatCell(Cell cell, String value, HSSFCellStyle cellPercentStyle) {
		if (StringUtil.isNumberAll(value)) { // 如果为数字型
			// 转数字类型
			// double d = Double.parseDouble(value);
			cell.setCellValue(value);
		} else {
			if (StringUtil.contains(value, "%")) {
				String strTemp = value.replace("%", "");
				if (StringUtil.isNumber(strTemp)) { // 去掉%后 如果全为数字，则转成百分数格式
					cell.setCellValue(Double.parseDouble(strTemp) / 100);
					cell.setCellStyle(cellPercentStyle);
				} else {
					cell.setCellValue(value);
				}
			} else {
				cell.setCellValue(value);
			}
		}
	}

	/**
	 * 根据数据填写cell
	 * 
	 * @param cell
	 * @param value
	 * @param cellPercentStyle
	 */
	public static void formatCellStr(Cell cell, String value, HSSFCellStyle cellPercentStyle) {
		cellPercentStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		if (value != null && !"".equals(value) && !"".equals(value.trim())) {
			value = value.trim();
		}
		if (StringUtil.isNumberAll(value)) { // 如果为数字型
			// 转数字类型
			double d = Double.parseDouble(value);
			cell.setCellValue(d);
			cell.setCellStyle(cellPercentStyle);
		} else {
			if (StringUtil.contains(value, "%")) {
				String strTemp = value.replace("%", "");
				if (StringUtil.isNumber(strTemp)) { // 去掉%后 如果全为数字，则转成百分数格式
					cell.setCellValue(Double.parseDouble(strTemp) / 100);
					// cell.setCellValue(strTemp);
					cell.setCellStyle(cellPercentStyle);
				} else {
					cell.setCellValue(value);
				}
			} else {
				cell.setCellValue(value);
				cell.setCellStyle(cellPercentStyle);
			}
		}

	}

}
