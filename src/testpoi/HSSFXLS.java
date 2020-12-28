/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testpoi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 对xls表格进行操作（和xlsx类的操作方法一样）
 * 1.HSSFXLS(String filePath) // 构造函数 传入文件绝对地址，构建一个对象，用来控制一个表格文件
 * 2.reReadXLS() // 这个函数也是读取表格内容，用于打开表格之后，再重读表格内容的
 * 3.writeXLS(Elempty data) // 写数据：传入此次点名的数据，那些学生被点名了，那些没有 
 * 4.getRowNum(String filePath) // 获取表格的行数 
 * 5.getColNum() // 获取列数 
 * 6.getAllValue() // 获取整个表格的内容
 * 7.getStuInformation() // 获取学生信息的基本信息
 * 8.getCellValue(XSSFCell cell) // 判断单元的数据类型，将转化为字符串类型，并返回数据
 * @author ydduong
 */
public class HSSFXLS {

    private static HSSFWorkbook workbook;
    private static HSSFSheet sheet;
    private static int rows, cols;
    private static String[][] value;
    private static String initFilePath;
    private static boolean isFirstWrite = true;
    private static String[] stuLeaveNum, stuNotArrivedNum;

    /**
     * 1.HSSFXLS(String filePath) // 构造函数 传入文件绝对地址，构建一个对象，用来控制一个表格文件
     *
     * @param filePath
     * @throws IOException
     */
    public HSSFXLS(String filePath) throws IOException {
        super();
        initFilePath = filePath;
        InputStream inputStream = new FileInputStream(filePath);// 创建一个输入流读取单元格
        POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);// 包装类，将读取的内容放入内存中
        workbook = new HSSFWorkbook(fileSystem);
        sheet = workbook.getSheetAt(0); // 规定一个文件了只有一个工作表

        rows = sheet.getLastRowNum() + 1; // 获取行数
        HSSFRow rowFirst = sheet.getRow(0); // 获取第一行
        cols = rowFirst.getPhysicalNumberOfCells(); // 获取列数

        stuLeaveNum = new String[rows]; // 用来记录打开表格时的  一些数据；主要用来写数据
        stuNotArrivedNum = new String[rows];
        String[][] temp = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            HSSFRow row = sheet.getRow(i);
            for (int j = 0; j < cols; j++) {
                temp[i][j] = getCellValue(row.getCell(j));
            }
            stuLeaveNum[i] = getCellValue(row.getCell(cols - 3));
            stuNotArrivedNum[i] = getCellValue(row.getCell(cols - 2));
        }
        value = temp;
    }

    /**
     * 2.reReadXLS() // 这个函数也是读取表格内容，用于打开表格之后，再重读表格内容的
     */
    public static void reReadXLS() throws FileNotFoundException, IOException {
        FileInputStream inputStream = new FileInputStream(new File(initFilePath));//创建一个输入流读取单元格
        workbook = new HSSFWorkbook(inputStream);
        sheet = workbook.getSheetAt(0); //规定一个文件了只有一个工作表

        rows = sheet.getLastRowNum() + 1; //获取行数
        HSSFRow rowFirst = sheet.getRow(0); //获取第一行
        cols = rowFirst.getPhysicalNumberOfCells(); //获取列数
        String[][] temp = new String[rows][cols];
        for (int i = 0; i < rows; i++) { //从第二行开始
            HSSFRow row = sheet.getRow(i);
            for (int j = 0; j < cols; j++) {
                temp[i][j] = getCellValue(row.getCell(j));
            }
        }
        value = temp;
    }

    /**
     * 3.writeXLS(Elempty data) // 写数据：传入此次点名的数据，那些学生被点名了，那些没有
     */
    public static void writeXLS(int[] luckyDog) throws IOException {
        HSSFWorkbook newWorkbook = new HSSFWorkbook(); // 创建工作薄

        HSSFCellStyle cellStyle = newWorkbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //单元格居中
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        HSSFSheet newSheet = newWorkbook.createSheet("sheet"); // 创建工作表

        if (isFirstWrite) {
            //第一次写数据，需要添加新的列，之后的只需要修改数据
            isFirstWrite = false;
            for (int i = 0; i < rows; i++) {
                HSSFRow row = newSheet.createRow(i);
                for (int j = 0; j <= cols; j++) {
                    newSheet.setColumnWidth(j, 4500);
                    if (j == 2) {
                        newSheet.setColumnWidth(j, 2600);
                    }
                    HSSFCell cell = row.createCell(j);

                    if (j < cols - 3) {
                        cell.setCellValue(value[i][j]);
                    }

                    if (j == cols - 3) { //添加的新列
                        if (i == 0) {  //第一行
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                            String data = dateFormat.format(calendar.getTime());
                            cell.setCellValue(data);
                        } else {
                            switch (luckyDog[i]) {
                                case 0: //没有点到
                                    cell.setCellValue("");
                                    break;
                                case 1: //
                                    cell.setCellValue("已到");
                                    break;
                                case 2: //
                                    cell.setCellValue("请假");
                                    break;
                                case 3: //
                                    cell.setCellValue("缺勤");
                                    break;
                            }
                        }
                    }

                    if (j == cols - 2) { //请假次数统计
                        newSheet.setColumnWidth(j, 2600);
                        if (i == 0) {
                            cell.setCellValue(stuLeaveNum[i]);
                        } else {
                            if (luckyDog[i] == 2) {
                                String temp = stuLeaveNum[i];
                                if (temp.equals("")) {
                                    temp = "0";
                                }
                                temp = String.valueOf(Integer.valueOf(temp).intValue() + 1);
                                cell.setCellValue(temp);
                            } else {
                                cell.setCellValue(stuLeaveNum[i]);
                            }
                        }
                    }

                    if (j == cols - 1) { //缺勤次数统计
                        newSheet.setColumnWidth(j, 2600);
                        if (i == 0) {
                            cell.setCellValue(stuNotArrivedNum[i]);
                        } else {
                            if (luckyDog[i] == 3) {
                                String temp = stuNotArrivedNum[i];
                                if (temp.equals("")) {
                                    temp = "0";
                                }
                                temp = String.valueOf(Integer.valueOf(temp).intValue() + 1);
                                cell.setCellValue(temp);
                            } else {
                                cell.setCellValue(stuNotArrivedNum[i]);
                            }
                        }
                    }

                    if (j == cols) { //统计总评
                        newSheet.setColumnWidth(j, 2600);
                        if (i == 0) {
                            cell.setCellValue(value[i][cols - 1]);
                        } else {
                            String temp = stuNotArrivedNum[i]; //缺勤次数
                            if (temp.equals("")) {
                                temp = "0";
                            }
                            int sum = Integer.valueOf(temp).intValue();
                            if (luckyDog[i] == 3) {
                                sum++;
                            }
                            int total = 100 - sum * 5;
                            if (total == 0) {
                                temp = "0";
                            } else {
                                temp = String.valueOf(total);
                            }
                            cell.setCellValue(temp);
                        }
                    }
                    cell.setCellStyle(cellStyle);
                }
            }
        } else {
            for (int i = 0; i < rows; i++) {
                HSSFRow row = newSheet.createRow(i);
                for (int j = 0; j < cols; j++) {
                    newSheet.setColumnWidth(j, 4500);
                    if (j == 2) {
                        newSheet.setColumnWidth(j, 2600);
                    }
                    HSSFCell cell = row.createCell(j);

                    if (j < cols - 4) {
                        cell.setCellValue(value[i][j]);
                    }

                    if (j == cols - 4) { //不添加的新列，将最后一列的考勤情况重写
                        if (i == 0) {  //第一行
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                            String data = dateFormat.format(calendar.getTime());
                            cell.setCellValue(data);
                        } else {
                            switch (luckyDog[i]) {
                                case 0: //没有点到
                                    cell.setCellValue("");
                                    break;
                                case 1: //
                                    cell.setCellValue("已到");
                                    break;
                                case 2: //
                                    cell.setCellValue("请假");
                                    break;
                                case 3: //
                                    cell.setCellValue("缺勤");
                                    break;
                            }
                        }
                    }

                    if (j == cols - 3) { //重新统计请假次数
                        newSheet.setColumnWidth(j, 2600);
                        if (i == 0) {
                            cell.setCellValue(stuLeaveNum[i]);
                        } else {
                            if (luckyDog[i] == 2) {
                                String temp = stuLeaveNum[i];
                                if (temp.equals("")) {
                                    temp = "0";
                                }
                                temp = String.valueOf(Integer.valueOf(temp).intValue() + 1);
                                cell.setCellValue(temp);
                            } else {
                                cell.setCellValue(stuLeaveNum[i]);
                            }
                        }
                    }

                    if (j == cols - 2) { //重新统计缺勤次数
                        newSheet.setColumnWidth(j, 2600);
                        if (i == 0) {
                            cell.setCellValue(stuNotArrivedNum[i]);
                        } else {
                            if (luckyDog[i] == 3) {
                                String temp = stuNotArrivedNum[i];
                                if (temp.equals("")) {
                                    temp = "0";
                                }
                                temp = String.valueOf(Integer.valueOf(temp).intValue() + 1);
                                cell.setCellValue(temp);
                            } else {
                                cell.setCellValue(stuNotArrivedNum[i]);
                            }
                        }
                    }

                    if (j == cols - 1) { //统计总评
                        newSheet.setColumnWidth(j, 2600);
                        if (i == 0) {
                            cell.setCellValue(value[i][cols - 1]);
                        } else {
                            String temp = stuNotArrivedNum[i]; //缺勤次数
                            if (temp.equals("")) {
                                temp = "0";
                            }
                            int sum = Integer.valueOf(temp).intValue();
                            if (luckyDog[i] == 3) {
                                sum++;
                            }
                            int total = 100 - sum * 5;
                            if (total == 0) {
                                temp = "0";
                            } else {
                                temp = String.valueOf(total);
                            }
                            cell.setCellValue(temp);
                        }
                    }
                    cell.setCellStyle(cellStyle);
                }
            }
        }

        File xlsFile = new File(initFilePath);
        FileOutputStream xlsStream = new FileOutputStream(xlsFile);
        newWorkbook.write(xlsStream);
        System.out.println("okok");

        reReadXLS();
    }

    /**
     * 4.getRowNum(String filePath) // 获取表格的行数
     */
    public static int getRowNum() { //获取表格的列数,用来简单判断表格是否为标准格式
        return rows; //获取工作表的行数
    }

    /**
     * 5.getColNum()//获取列数
     */
    public int getColNum() { // 获取表格的列数,用来简单判断表格是否为标准格式
        return cols; // 获取工作表的行数
    }

    /**
     * 6.getAllValue() // 获取整个表格的内容
     *
     * @return
     */
    public String[][] getAllValue() {
        return value;
    }

    /**
     * 7.getStuInformation() // 获取学生信息的基本信息
     */
    public static String[][] getStuInformation() {
        String[][] stuInformation = new String[rows - 1][6];
        for (int i = 1; i < rows; i++) { // 从第二行开始
            HSSFRow row = sheet.getRow(i);
            for (int j = 0; j < 3; j++) {
                stuInformation[i - 1][j] = getCellValue(row.getCell(j));
            }
            stuInformation[i - 1][3] = getCellValue(row.getCell(cols - 3));
            stuInformation[i - 1][4] = getCellValue(row.getCell(cols - 2));
            stuInformation[i - 1][5] = getCellValue(row.getCell(cols - 1));
        }
        return stuInformation;
    }

    /**
     * 8.getCellValue(XSSFCell cell) // 判断单元的数据类型，将转化为字符串类型，并返回数据
     *
     * @param cell
     * @return
     */
    public static String getCellValue(HSSFCell cell) {
        String key = "";
        if (cell == null) {
            return key; // 如果指针为空，返回空字符串
        }
        switch (cell.getCellType()) {  // 简单的查检列类型 
            case HSSFCell.CELL_TYPE_STRING: // 字符串  
                key = cell.getRichStringCellValue().toString();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC: // 数字  
                long dd = (long) cell.getNumericCellValue();
                key = dd + "";
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                key = "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                key = String.valueOf(cell.getCellFormula());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN: // boolean型值  
                key = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                key = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                break;
        }
        return key;
    }

}
