package com.ghy.common.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@SuppressWarnings( { "deprecation", "unchecked" })
public class PoiReadExcelBeanImpl implements PoiReadExcelBean {

	static Log log = LogFactory.getLog(PoiReadExcelBeanImpl.class);

	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final static String LINE_KEY = "line-_-Key";

	private final static int LINE_NUMBER = 100;
	private final static boolean IS_MANY_SHEET = true;

	/**
	 * 读取Excel文件，无判断结束符
	 * 
	 * @param filePaths
	 *            传入文件路径
	 * @param readRowNum
	 *            从readRowNum行读取Excel内容
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List readExcel(String filePaths, int readRowNum) throws FileNotFoundException, IOException {
		List<Map> resultList = new ArrayList<Map>();
		if (filePaths != null && !filePaths.equals("")) {
			// 获得文件后缀名，判断文件类型
			String filetype = filePaths.substring(filePaths.lastIndexOf(".") + 1);
			if (filetype.equals("xlsx")) {
				// 调用解析excel2007文件方法
				resultList = readPoiExcel2007(filePaths, readRowNum, LINE_NUMBER, LINE_KEY);
			} else if (filetype.equals("xls")) {
				// 调用解析excel2003文件方法
				resultList = readPoiExcel2003(filePaths, readRowNum, LINE_NUMBER, LINE_KEY);
			}
		}
		return resultList;
	}

	/**
	 * 读取Excel文件且只读第一个Sheet无判断结束符
	 * 
	 * @param filePaths
	 *            传入文件路径
	 * @param readRowNum
	 *            从readRowNum行读取Excel内容
	 * @param sheetNum
	 *            从第一个Sheet读起读取Sheet的个数
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List readExcelSheet(String filePaths, int readRowNum, int sheetNum) throws FileNotFoundException,
			IOException {
		List<Map> resultList = new ArrayList<Map>();
		if (filePaths != null && !filePaths.equals("")) {
			// 获得文件后缀名，判断文件类型
			String filetype = filePaths.substring(filePaths.lastIndexOf(".") + 1);
			if (filetype.equals("xlsx")) {
				// 调用解析excel2007文件方法
				resultList = readPoiExcel2007Sheet(filePaths, readRowNum, sheetNum, LINE_NUMBER, LINE_KEY);
			} else if (filetype.equals("xls")) {
				// 调用解析excel2003文件方法
				resultList = readPoiExcel2003Sheet(filePaths, readRowNum, sheetNum, LINE_NUMBER, LINE_KEY);
			}
		}
		return resultList;
	}

	/**
	 * 读取Excel文件的列头，无判断结束符
	 * 
	 * @param filePaths
	 *            传入文件路径
	 * @param readRowNum
	 *            列头所在行
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List readExcelTitle(String filePaths, int readRowNum) throws FileNotFoundException, IOException {
		List<Map> resultList = new ArrayList<Map>();
		if (filePaths != null && !filePaths.equals("")) {
			// 获得文件后缀名，判断文件类型
			String filetype = filePaths.substring(filePaths.lastIndexOf(".") + 1);
			if (filetype.equals("xlsx")) {
				// 调用解析excel2007文件方法
				resultList = readPoiExcel2007Title(filePaths, readRowNum);
			} else if (filetype.equals("xls")) {
				// 调用解析excel2003文件方法
				resultList = readPoiExcel2003Title(filePaths, readRowNum);
			}
		}
		return resultList;
	}

	/**
	 * 读取Excel文件，并判断结束符
	 * 
	 * @param filePaths
	 *            传入文件路径
	 * @param readRowNum
	 *            从readRowNum行读取Excel内容
	 * @param lineNum
	 *            结束符所在列数
	 * @param lineKey
	 *            结束符
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List readExcelKey(String filePaths, int readRowNum, int lineNum, String lineKey)
			throws FileNotFoundException, IOException {
		List<Map> resultList = new ArrayList<Map>();
		if (filePaths != null && !filePaths.equals("")) {
			// 获得文件后缀名，判断文件格式
			String filetype = filePaths.substring(filePaths.lastIndexOf(".") + 1);
			if (filetype.equals("xlsx")) {
				// 调用解析excel2007文件方法
				resultList = readPoiExcel2007(filePaths, readRowNum, lineNum, lineKey);
			} else if (filetype.equals("xls")) {
				// 调用解析excel2003文件方法
				resultList = readPoiExcel2003(filePaths, readRowNum, lineNum, lineKey);
			}
		}
		return resultList;
	}

	/**
	 * 读取Excel文件指定列，无判断结束符
	 * 
	 * @param filePaths
	 *            传入文件路径
	 * @param readRowNum
	 *            从readRowNum行读取Excel内容
	 * @param rowNumList
	 *            指定读取的列集合
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List readExcel(String filePaths, int readRowNum, List rowNumList) throws FileNotFoundException, IOException {
		List<Map> resultList = new ArrayList<Map>();
		if (filePaths != null && !filePaths.equals("")) {
			// 获得文件后缀名，判断文件格式
			String filetype = filePaths.substring(filePaths.lastIndexOf(".") + 1);
			if (filetype.equals("xlsx")) {
				// 调用解析excel2007文件方法
				resultList = readPoiExcel2007(filePaths, readRowNum, LINE_NUMBER, LINE_KEY, rowNumList);
			} else if (filetype.equals("xls")) {
				// 调用解析excel2003文件方法
				resultList = readPoiExcel2003(filePaths, readRowNum, LINE_NUMBER, LINE_KEY, rowNumList);
			}
		}
		return resultList;
	}

	/**
	 * 读取Excel文件指定列，并判断结束符
	 * 
	 * @param filePaths
	 *            传入文件路径
	 * @param readRowNum
	 *            从readRowNum行读取Excel内容
	 * @param lineNum
	 *            结束符所在列数
	 * @param lineKey
	 *            结束符
	 * @param rowNumList
	 *            指定读取的列集合
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List readExcelKey(String filePaths, int readRowNum, int lineNum, String lineKey, List rowNumList)
			throws FileNotFoundException, IOException {
		List<Map> resultList = new ArrayList<Map>();
		if (filePaths != null && !filePaths.equals("")) {
			// 获得文件后缀名，判断文件格式
			String filetype = filePaths.substring(filePaths.lastIndexOf(".") + 1);
			if (filetype.equals("xlsx")) {
				// 调用解析excel2007文件方法
				resultList = readPoiExcel2007(filePaths, readRowNum, lineNum, lineKey, rowNumList);
			} else if (filetype.equals("xls")) {
				// 调用解析excel2003文件方法
				resultList = readPoiExcel2003(filePaths, readRowNum, lineNum, lineKey, rowNumList);
			}
		}
		return resultList;
	}

	/**
	 * 解析excel2007
	 * 
	 * @param filePaths
	 *            传入文件路径
	 * @param readRowNum
	 *            从readRowNum行读取Excel内容
	 * @param lineNum
	 *            结束符所在列数
	 * @param lineKey
	 *            结束符
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List readPoiExcel2007(String filePaths, int readRowNum, int lineNum, String lineKey)
			throws FileNotFoundException, IOException {
		// 声明存放map的list,map用来存放单元格内容
		List<Map> excel07List = new ArrayList<Map>();
		try {
			// 构造 XSSFWorkbook 对象
			XSSFWorkbook xwb = new XSSFWorkbook(filePaths);
			// sheet数量
			int numOfSheets = xwb.getNumberOfSheets();
			// 循环遍历sheet
			labelSheet: for (int s = 0; s < numOfSheets; s++) {
				// 读取sheet表格内容
				XSSFSheet sheet = xwb.getSheetAt(s);
				// 获取该sheet的行数
				int rows = sheet.getPhysicalNumberOfRows();
				if (rows > 0) {
					// 定义 row
					XSSFRow row;
					// 循环输出表格中的内容
					for (int i = readRowNum; i < sheet.getPhysicalNumberOfRows(); i++) {
						// 声明map对象
						Map map = new HashMap();
						// 读取sheet的一行
						row = sheet.getRow(i);
						for (int j = 0; j < row.getLastCellNum(); j++) {
							// 发现关键字，跳出所有循环
							if (row.getCell(lineNum) != null && cellTypeExcel2007(row.getCell(lineNum)).equals(lineKey)) {
								break labelSheet;
							} else {
								// 获取单元格内容，并放入map中
								if (row.getCell(j) != null) {
									// 判断是否为公式类型
									if (row.getCell(j).getCellType() != XSSFCell.CELL_TYPE_FORMULA) {
										map.put(j, cellTypeExcel2007(row.getCell(j)));
									} else
										map.put(j, row.getCell(j));
								} else
									map.put(j, "");
							}
						}
						// 将map放入list
						excel07List.add(map);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excel07List;
	}

	/**
	 * 解析excel2007
	 * 
	 * @param filePaths
	 *            传入文件路径
	 * @param readRowNum
	 *            从readRowNum行读取Excel内容
	 * @param lineNum
	 *            结束符所在列数
	 * @param lineKey
	 *            结束符
	 * @param sheetNum
	 *            读取Sheet的个数
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List readPoiExcel2007Sheet(String filePaths, int readRowNum, int sheetNum, int lineNum, String lineKey)
			throws FileNotFoundException, IOException {
		// 声明存放map的list,map用来存放单元格内容
		List<Map> excel07List = new ArrayList<Map>();
		try {
			// 构造 XSSFWorkbook 对象
			XSSFWorkbook xwb = new XSSFWorkbook(filePaths);
			// sheet数量
			int numOfSheets = 0;
			if (xwb.getNumberOfSheets() > sheetNum && sheetNum > 0) {
				numOfSheets = sheetNum;
			} else {
				numOfSheets = xwb.getNumberOfSheets();
			}

			// 循环遍历sheet
			labelSheet: for (int s = 0; s < numOfSheets; s++) {
				// 读取sheet表格内容
				XSSFSheet sheet = xwb.getSheetAt(s);
				// 获取该sheet的行数
				int rows = sheet.getPhysicalNumberOfRows();
				if (rows > 0) {
					// 定义 row
					XSSFRow row;
					// 循环输出表格中的内容
					for (int i = readRowNum; i < sheet.getPhysicalNumberOfRows(); i++) {
						// 声明map对象
						Map map = new HashMap();
						// 读取sheet的一行
						row = sheet.getRow(i);
						for (int j = 0; j < row.getLastCellNum(); j++) {
							// 发现关键字，跳出所有循环
							if (row.getCell(lineNum) != null && cellTypeExcel2007(row.getCell(lineNum)).equals(lineKey)) {
								break labelSheet;
							} else {
								// 获取单元格内容，并放入map中
								if (row.getCell(j) != null) {
									// 判断是否为公式类型
									if (row.getCell(j).getCellType() != XSSFCell.CELL_TYPE_FORMULA) {
										map.put(j, cellTypeExcel2007(row.getCell(j)));
									} else
										map.put(j, row.getCell(j));
								} else
									map.put(j, "");
							}
						}
						// 将map放入list
						excel07List.add(map);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excel07List;
	}

	/**
	 * 解析excel2007
	 * 
	 * @param filePaths
	 *            传入文件路径
	 * @param readRowNum
	 *            从readRowNum行读取Excel内容
	 * @param lineNum
	 *            结束符所在列数
	 * @param lineKey
	 *            结束符
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List readPoiExcel2007Title(String filePaths, int readRowNum) throws FileNotFoundException, IOException {
		// 声明存放map的list,map用来存放单元格内容
		List<Map> excel07List = new ArrayList<Map>();
		try {
			// 构造 XSSFWorkbook 对象
			XSSFWorkbook xwb = new XSSFWorkbook(filePaths);
			// sheet数量
			int numOfSheets = xwb.getNumberOfSheets();
			// 读取sheet表格内容
			XSSFSheet sheet = xwb.getSheetAt(0);
			// 定义 row
			XSSFRow row = sheet.getRow(readRowNum);
			// 声明map对象
			Map map = new HashMap();
			for (int j = 0; j < row.getLastCellNum(); j++) {
				// 获取单元格内容，并放入map中
				if (row.getCell(j) != null) {
					// 判断是否为公式类型
					if (row.getCell(j).getCellType() != XSSFCell.CELL_TYPE_FORMULA) {
						map.put(j, cellTypeExcel2007(row.getCell(j)));
					} else
						map.put(j, row.getCell(j));
				} else
					map.put(j, "");

			}
			// 将map放入list
			excel07List.add(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excel07List;
	}

	/**
	 * 解析excel2007，指定读取列
	 * 
	 * @param filePaths
	 *            传入文件路径
	 * @param readRowNum
	 *            从readRowNum行读取Excel内容
	 * @param lineNum
	 *            结束符所在列数
	 * @param lineKey
	 *            结束符
	 * @param rowNumList
	 *            指定读取的列集合
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List readPoiExcel2007(String filePaths, int readRowNum, int lineNum, String lineKey, List rowNumList)
			throws FileNotFoundException, IOException {
		// 声明存放map的list,map用来存放单元格内容
		List<Map> excel07List = new ArrayList<Map>();
		try {
			// 构造 XSSFWorkbook 对象
			XSSFWorkbook xwb = new XSSFWorkbook(filePaths);
			// sheet数量
			int numOfSheets = xwb.getNumberOfSheets();
			// 循环遍历sheet
			labelSheet: for (int s = 0; s < numOfSheets; s++) {
				// 读取sheet表格内容
				XSSFSheet sheet = xwb.getSheetAt(s);
				// 获取该sheet的行数
				int rows = sheet.getPhysicalNumberOfRows();
				if (rows > 0) {
					// 定义 row
					XSSFRow row;
					// 循环输出表格中的内容
					for (int i = readRowNum; i < sheet.getPhysicalNumberOfRows(); i++) {
						// 声明map对象
						Map map = new HashMap();
						// 读取sheet的一行
						row = sheet.getRow(i);
						for (int j = 0; j < rowNumList.size(); j++) {
							int rn = Integer.parseInt(rowNumList.get(j).toString());
							// 发现关键字，跳出所有循环
							if (row.getCell(lineNum) != null && cellTypeExcel2007(row.getCell(lineNum)).equals(lineKey)) {
								break labelSheet;
							} else {
								// 获取单元格内容，并放入map中
								if (row.getCell(rn) != null) {
									// 判断是否为公式类型
									if (row.getCell(rn).getCellType() != XSSFCell.CELL_TYPE_FORMULA)
										map.put(rn, cellTypeExcel2007(row.getCell(rn)));
									else
										map.put(rn, row.getCell(rn));
								} else
									map.put(rn, "");
							}
						}
						// 将map放入list
						excel07List.add(map);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excel07List;
	}

	/**
	 * 解析excel2003
	 * 
	 * @param filePaths
	 *            传入文件路径
	 * @param readRowNum
	 *            从readRowNum行读取Excel内容
	 * @param lineNum
	 *            结束符所在列数
	 * @param lineKey
	 *            结束符
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List readPoiExcel2003(String filePaths, int readRowNum, int lineNum, String lineKey)
			throws FileNotFoundException, IOException {
		// 声明存放map的list,map用来存放单元格内容
		List<Map> excel03List = new ArrayList<Map>();
		try {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePaths));
			// sheet数量
			int numOfSheets = workbook.getNumberOfSheets();
			// 循环遍历sheet
			labelSheet: for (int s = 0; s < numOfSheets; s++) {
				// 创建对工作表的引用
				HSSFSheet sheet = workbook.getSheetAt(s);
				// 获取该工作表的行数
				int rows = sheet.getPhysicalNumberOfRows();
				// 判断sheet中是否存在数据
				if (rows > 0) {
					// 定义row
					HSSFRow row;
					// 循环输出表格中的内容
					for (int i = readRowNum; i < rows; i++) {
						// 声明map对象
						Map excel03Map = new HashMap();
						// 读取sheet的一行
						row = sheet.getRow(i);
						int z = 0;
						for (short j = 0; j < row.getLastCellNum(); j++) {
							// 发现关键字，跳出所有循环
							if (row.getCell((short) lineNum) != null
									&& cellTypeExcel2003(row.getCell((short) lineNum)).equals(lineKey)) {
								break labelSheet;
							} else {
								// 获取单元格内容，并放入excel03Map中
								if (row.getCell(j) != null) {
									// 判断是否为公式类型
									if (row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_FORMULA)
										excel03Map.put(z, cellTypeExcel2003(row.getCell(j)));
									else
										excel03Map.put(z, row.getCell(j));
								} else
									excel03Map.put(z, "");
							}
							z++;
						}
						// 将map放入list
						excel03List.add(excel03Map);
					}
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return excel03List;
	}

	/**
	 * 解析excel2003
	 * 
	 * @param filePaths
	 *            传入文件路径
	 * @param readRowNum
	 *            从readRowNum行读取Excel内容
	 * @param lineNum
	 *            结束符所在列数
	 * @param lineKey
	 *            结束符
	 * @param 从第一个Sheet读起总读取SheetNum个Sheet
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List readPoiExcel2003Sheet(String filePaths, int readRowNum, int sheetNum, int lineNum, String lineKey)
			throws FileNotFoundException, IOException {
		// 声明存放map的list,map用来存放单元格内容
		List<Map> excel03List = new ArrayList<Map>();
		try {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePaths));
			// sheet数量
			int numOfSheets = 0;
			if (workbook.getNumberOfSheets() > sheetNum && sheetNum > 0) {
				numOfSheets = sheetNum;
			} else {
				numOfSheets = workbook.getNumberOfSheets();
			}
			// 循环遍历sheet
			labelSheet: for (int s = 0; s < numOfSheets; s++) {
				// 创建对工作表的引用
				HSSFSheet sheet = workbook.getSheetAt(s);
				// 获取该工作表的行数
				int rows = sheet.getPhysicalNumberOfRows();
				// 判断sheet中是否存在数据
				if (rows > 0) {
					// 定义row
					HSSFRow row;
					// 循环输出表格中的内容
					for (int i = readRowNum; i < rows; i++) {
						// 声明map对象
						Map excel03Map = new HashMap();
						// 读取sheet的一行
						row = sheet.getRow(i);
						int z = 0;
						for (short j = 0; j < row.getLastCellNum(); j++) {
							// 发现关键字，跳出所有循环
							if (row.getCell((short) lineNum) != null
									&& cellTypeExcel2003(row.getCell((short) lineNum)).equals(lineKey)) {
								break labelSheet;
							} else {
								// 获取单元格内容，并放入excel03Map中
								if (row.getCell(j) != null) {
									// 判断是否为公式类型
									if (row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_FORMULA)
										excel03Map.put(z, cellTypeExcel2003(row.getCell(j)));
									else
										excel03Map.put(z, row.getCell(j));
								} else
									excel03Map.put(z, "");
							}
							z++;
						}
						// 将map放入list
						excel03List.add(excel03Map);
					}
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return excel03List;
	}

	/**
	 * 解析excel2003
	 * 
	 * @param filePaths
	 *            传入文件路径
	 * @param readRowNum
	 *            从readRowNum行读取Excel内容
	 * @param lineNum
	 *            结束符所在列数
	 * @param lineKey
	 *            结束符
	 * @param b
	 *            是否为多Sheet
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List readPoiExcel2003Title(String filePaths, int readRowNum) throws FileNotFoundException, IOException {
		// 声明存放map的list,map用来存放单元格内容
		List<Map> excel03List = new ArrayList<Map>();
		try {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePaths));
			// 创建对工作表的引用
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow row = sheet.getRow(readRowNum);
			Map excel03Map = new HashMap();
			int z = 0;
			for (short j = 0; j < row.getLastCellNum(); j++) {
				if (row.getCell(j) != null) {
					// 判断是否为公式类型
					if (row.getCell(j).getCellType() != HSSFCell.CELL_TYPE_FORMULA) {
						excel03Map.put(z, cellTypeExcel2003(row.getCell(j)));
					} else
						excel03Map.put(z, row.getCell(j));
				} else
					excel03Map.put(z, "");
				z++;
			}
			// 将map放入list
			excel03List.add(excel03Map);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return excel03List;
	}

	/**
	 * 解析excel2003，指定读取列
	 * 
	 * @param filePaths
	 *            传入文件路径
	 * @param readRowNum
	 *            从readRowNum行读取Excel内容
	 * @param lineNum
	 *            结束符所在列数
	 * @param lineKey
	 *            结束符
	 * @param rowNumList
	 *            指定读取的列集合
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List readPoiExcel2003(String filePaths, int readRowNum, int lineNum, String lineKey, List rowNumList)
			throws FileNotFoundException, IOException {
		// 声明存放map的list,map用来存放单元格内容
		List<Map> excel03List = new ArrayList<Map>();
		try {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePaths));
			// sheet数量
			int numOfSheets = workbook.getNumberOfSheets();
			// 循环遍历sheet
			labelSheet: for (int s = 0; s < numOfSheets; s++) {
				// 创建对工作表的引用
				HSSFSheet sheet = workbook.getSheetAt(s);
				// 获取该工作表的行数
				int rows = sheet.getPhysicalNumberOfRows();
				// 判断sheet中是否存在数据
				if (rows > 0) {
					// 定义row
					HSSFRow row;
					// 循环输出表格中的内容
					for (int i = readRowNum; i < rows; i++) {
						// 声明map对象
						Map excel03Map = new HashMap();
						// 读取sheet的一行
						row = sheet.getRow(i);
						for (short j = 0; j < rowNumList.size(); j++) {
							int rn = Integer.parseInt(rowNumList.get(j).toString());
							// 发现关键字，跳出所有循环
							if (row.getCell((short) lineNum) != null
									&& cellTypeExcel2003(row.getCell((short) lineNum)).equals(lineKey)) {
								break labelSheet;
							} else {
								// 获取单元格内容，并放入excel03Map中
								if (row.getCell((short) rn) != null) {
									// 判断是否为公式类型
									if (row.getCell((short) rn).getCellType() != HSSFCell.CELL_TYPE_FORMULA)
										excel03Map.put(rn, cellTypeExcel2003(row.getCell((short) rn)));
									else
										excel03Map.put(rn, row.getCell((short) rn));
								} else
									excel03Map.put(rn, "");
							}
						}
						// 将map放入list
						excel03List.add(excel03Map);
					}
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return excel03List;
	}

	/**
	 * Excel2003转换字符串
	 * 
	 * @param cell
	 * @return
	 */
	public String cellTypeExcel2003(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型，取得该Cell的Date值
					Date date = cell.getDateCellValue();
					// 把Date转换成本地格式的字符串
					cellvalue = DATE_FORMAT.format(date);
				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					long num = Long.valueOf(((long) cell.getNumericCellValue()));
					cellvalue = String.valueOf(num);
				}
				break;
			}
				// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().toString().replaceAll("'", "''");
				break;
			// 默认的Cell值
			default:
				cellvalue = "";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	/**
	 * Excel2007转换字符串
	 * 
	 * @param cell
	 * @return
	 */
	public String cellTypeExcel2007(XSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case XSSFCell.CELL_TYPE_NUMERIC: {
				// 判断当前的cell是否为Date
				XSSFCellStyle style = cell.getCellStyle();
				int t = style.getDataFormat();
				if (isInternalDateFormat(t)) {
					// 如果是Date类型，取得该Cell的Date值
					Date date = cell.getDateCellValue();
					// 把Date转换成本地格式的字符串
					cellvalue = DATE_FORMAT.format(date);
				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					long num = Long.valueOf(((long) cell.getNumericCellValue()));
					cellvalue = String.valueOf(num);
				}
				break;
			}
				// 如果当前Cell的Type为STRIN
			case XSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().toString().replaceAll("'", "''");
				break;
			// 默认的Cell值
			default:
				cellvalue = "";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	/**
	 * 格式化日期
	 * 
	 * @param format
	 * @return
	 */
	public boolean isInternalDateFormat(int format) {
		boolean retval = false;
		switch (format) {
		case 0x0e:
		case 0x0f:
		case 0x10:
		case 0x11:
		case 0x12:
		case 0x13:
		case 0x14:
		case 0x15:
		case 0x16:
		case 0x2d:
		case 0x2e:
		case 0x2f:
			retval = true;
			break;
		default:
			retval = false;
			break;
		}
		return retval;
	}
}
