package testpoi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 对xlsx表格进行操作 
 * 1.XSSFXLSX(String filePath) // 构造函数 传入文件绝对地址，构建一个对象，用来控制一个表格文件
 * 2.reReadXLSX() // 这个函数也是读取表格内容，用于打开表格之后，再重读表格内容的
 * 3.writeXLSX(Elempty data) // 写数据：传入此次点名的数据，那些学生被点名了，那些没有 
 * 4.getRowNum(String filePath) // 获取表格的行数 
 * 5.getColNum() // 获取列数 
 * 6.getAllValue() // 获取整个表格的内容
 * 7.getStuInformation() // 获取学生信息的基本信息
 * 8.getCellValue(XSSFCell cell) // 判断单元的数据类型，将转化为字符串类型，并返回数据
 * @author ydduong
 */
public class XSSFXLSX {

    private static XSSFWorkbook workbook; //Excel表格
    private static XSSFSheet sheet; //表
    private static int rows, cols; //行数和列数
    private static String[][] value; //整张表的内容
    private static String initFilePath;
    private static boolean isFirstWrite = true;
    private static String[] stuLeaveNum, stuNotArrivedNum;

    /**
     * 1.XSSFXLSX(String filePath) // 构造函数 传入文件绝对地址，构建一个对象，用来控制一个表格文件
     *
     * @param filePath 传进的是文件的绝对路径（也可以是相对路径）
     * @throws IOException
     */
    public XSSFXLSX(String filePath) throws IOException {
        super();
        initFilePath = filePath; // 保存文件路径
        FileInputStream inputStream = new FileInputStream(new File(initFilePath)); // 创建一个输入流读取单元格
        workbook = new XSSFWorkbook(inputStream);
        sheet = workbook.getSheetAt(0); // 规定一个文件了只有一个工作表

        rows = sheet.getLastRowNum() + 1; // 获取行数
        XSSFRow rowFirst = sheet.getRow(0); // 获取第一行
        cols = rowFirst.getPhysicalNumberOfCells(); // 获取列数

        stuLeaveNum = new String[rows];
        stuNotArrivedNum = new String[rows];
        String[][] temp = new String[rows][cols];
        for (int i = 0; i < rows; i++) { // 从行开始读
            XSSFRow row = sheet.getRow(i);
            for (int j = 0; j < cols; j++) {
                temp[i][j] = getCellValue(row.getCell(j)); //  获取单元格数据，这里用二维数组来保存表格的数据
            }
            stuLeaveNum[i] = getCellValue(row.getCell(cols - 3));
            stuNotArrivedNum[i] = getCellValue(row.getCell(cols - 2));
        }
        value = temp;
    }

    /**
     * 2.reReadXLSX() // 这个函数也是读取表格内容，用于打开表格之后，再重读表格内容的
     */
    public static void reReadXLSX() throws FileNotFoundException, IOException {
        FileInputStream inputStream = new FileInputStream(new File(initFilePath));//创建一个输入流读取单元格
        workbook = new XSSFWorkbook(inputStream);
        sheet = workbook.getSheetAt(0); //规定一个文件了只有一个工作表

        rows = sheet.getLastRowNum() + 1; //获取行数
        XSSFRow rowFirst = sheet.getRow(0); //获取第一行
        cols = rowFirst.getPhysicalNumberOfCells(); //获取列数
        String[][] temp = new String[rows][cols];
        for (int i = 0; i < rows; i++) { //从第二行开始
            XSSFRow row = sheet.getRow(i);
            for (int j = 0; j < cols; j++) {
                temp[i][j] = getCellValue(row.getCell(j));
            }
        }
        value = temp;
    }

    /**
     * 3.writeXLSX(Elempty data) // 写数据：传入此次点名的数据，那些学生被点名了，那些没有
     */
    public static void writeXLSX(int[] luckyDog) throws IOException {
        XSSFWorkbook newWorkbook = new XSSFWorkbook(); // 创建工作薄

        XSSFCellStyle cellStyle = newWorkbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 单元格居中
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框  
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框  
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框  
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框

        XSSFSheet newSheet = newWorkbook.createSheet("sheet"); // 创建工作表

        if (isFirstWrite) { // isFirstWrite初始值为true
            // 第一次写数据，需要添加新的列，之后的只需要修改数据
            isFirstWrite = false;
            for (int i = 0; i < rows; i++) {
                XSSFRow row = newSheet.createRow(i); // 创建第i行
                for (int j = 0; j <= cols; j++) {
                    newSheet.setColumnWidth(j, 4500); // 设置第j列的列宽，普遍设置为4500
                    if (j == 2) { // 单独设置第二列宽2600
                        newSheet.setColumnWidth(j, 2600);
                    }

                    XSSFCell cell = row.createCell(j); // 创建第i行第j列单元格

                    if (j < cols - 3) { // 0 到 cols - 3列 重写以前的数据
                        cell.setCellValue(value[i][j]);
                    }

                    if (j == cols - 3) { // 添加的新列
                        if (i == 0) {  // 第一行
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                            String data = dateFormat.format(calendar.getTime());
                            cell.setCellValue(data); // 第一行设置为当天的日期
                        } else {
                            switch (luckyDog[i]) {
                                case 0: // 没有点到
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

                    if (j == cols - 2) { // 请假次数统计
                        newSheet.setColumnWidth(j, 2600);
                        if (i == 0) {
                            cell.setCellValue(stuLeaveNum[i]);
                        } else {
                            if (luckyDog[i] == 2) { // 如果有请假，缺勤次数 + 1
                                String temp = stuLeaveNum[i]; // 获取以前的缺勤的次数
                                if (temp.equals("")) {
                                    temp = "0";
                                }
                                temp = String.valueOf(Integer.valueOf(temp).intValue() + 1); // 缺勤次数 + 1
                                cell.setCellValue(temp); // 回写
                            } else { // 如果没有请假，还写以前的数据
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
        } else { // 如果不是第一次写数据就需要添加一个新的列，否则就像下面代码一样，直接更新列
            for (int i = 0; i < rows; i++) {
                XSSFRow row = newSheet.createRow(i);
                for (int j = 0; j < cols; j++) {
                    newSheet.setColumnWidth(j, 4500);
                    if (j == 2) {
                        newSheet.setColumnWidth(j, 2600);
                    }
                    XSSFCell cell = row.createCell(j);

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

        File xlsFile = new File(initFilePath); // 读取文件对象
        FileOutputStream xlsStream = new FileOutputStream(xlsFile);
        newWorkbook.write(xlsStream); // 写数据
        System.out.println("ok");

        reReadXLSX(); //写完之后，再重读表格内容的，更新类的一些变量的值，比如列数，缺勤次数之类的
    }

    /**
     * 4.getRowNum() // 获取表格的行数
     */
    public static int getRowNum() { // 获取表格的列数,用来简单判断表格是否为标准格式
        return rows; //获取工作表的行数
    }

    /**
     * 5.getColNum() // 获取列数
     */
    public int getColNum() { // 获取表格的列数,用来简单判断表格是否为标准格式
        return cols; //获取工作表的行数
    }

    /**
     * 6.getAllValue() // 获取整个表格的内容
     */
    public String[][] getAllValue() {
        return value;
    }

    /**
     * 7.getStuInformation() // 获取学生信息的基本信息
     */
    public static String[][] getStuInformation() {
        String[][] stuInformation = new String[rows - 1][6];
        for (int i = 1; i < rows; i++) { //从第二行开始
            XSSFRow row = sheet.getRow(i);
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
     * 这个很重要，防止POI报指针异常错误
     */
    public static String getCellValue(XSSFCell cell) {
        String str = "";
        if (cell == null) {
            return str; // 如果指针为空，返回空字符串
        }
        switch (cell.getCellType()) {  // 简单的查检列类型 
            case XSSFCell.CELL_TYPE_STRING: // 字符串  
                str = cell.getRichStringCellValue().toString();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC: // 数字  
                long dd = (long) cell.getNumericCellValue();
                str = dd + "";
                break;
            case XSSFCell.CELL_TYPE_BLANK:
                str = "";
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                str = String.valueOf(cell.getCellFormula());
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN: // boolean型值  
                str = String.valueOf(cell.getBooleanCellValue());
                break;
            case XSSFCell.CELL_TYPE_ERROR:
                str = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                break;
        }
        return str;
    }
}
