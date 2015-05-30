package com.ghy.common.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("unchecked")
public interface PoiReadExcelBean {

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
	public List readExcel(String filePaths, int readRowNum) throws FileNotFoundException, IOException;

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
			throws FileNotFoundException, IOException;

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
	public List readExcel(String filePaths, int readRowNum, List rowNumList) throws FileNotFoundException, IOException;

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
			throws FileNotFoundException, IOException;
}
