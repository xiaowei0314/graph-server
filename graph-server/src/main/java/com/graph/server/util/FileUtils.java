package com.graph.server.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件处理工具类
 *
 */
public class FileUtils {

    /**
     * 在某一目录下递归查找excel文件
     *
     * @param pathName
     * @param fileList
     */
    public static void exec(String pathName, List<File> fileList) {
        File file = new File(pathName);

        if (file.isDirectory()) {
            File[] childs = file.listFiles();
            for (File child : childs) {
                exec(child.getPath(), fileList);
            }
        } else if (file.getName().endsWith(".xls")
                || file.getName().endsWith(".xlsx")) {
            System.out.println("需要导入的文件:" + file.getName());
            if(fileList != null){
                fileList.add(file);
            }
        } else {
            System.out.println("不需要导入的文件:" + file.getName());
        }
    }

    /**
     * 读取并导入excel文件
     * @param file
     * @return
     */
    public static List<Object> readExcel(File file){
        return readExcel(file, 0);
    }

    /**
     * 读取并导入excel文件
     * @param file
     * @param headerCatchNum
     * @return
     */
    public static List<Object> readExcel(File file, int headerCatchNum) {
        List<Object> results = null;
        Workbook workBook = null;
        try {
            if (file.getName().endsWith(".xls")) {
                workBook = new HSSFWorkbook(new FileInputStream(file));
            } else if (file.getName().endsWith(".xlsx")) {
                workBook = new XSSFWorkbook(new FileInputStream(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int numberOfSheets = workBook.getNumberOfSheets();
        // 一次读取3000条数据
        int pageSize = 3000;
        // 依次导入每个sheet里面的数据
        if (numberOfSheets > 0) {
            results = new ArrayList<Object>();
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workBook.getSheetAt(i);
//                results.add(getSheetDatas(sheet, pageSize));
                results.add(getSheetDatas(sheet, pageSize, headerCatchNum));
            }
        }
        return results;
    }

    /**
     * 获取sheet的数据
     * @param sheet
     * @param pageSize
     * @return
     */
    private static List<Object> getSheetDatas(Sheet sheet, int pageSize){
        return FileUtils.getSheetDatas(sheet, pageSize, 0);
    }

    /**
     * 获取sheet的数据
     * @param sheet
     * @param pageSize
     * @param headerCatchNum
     * @return
     */
    private static List<Object> getSheetDatas(Sheet sheet, int pageSize, int headerCatchNum) {
        // 算出总记录数
        int totalCount = sheet.getLastRowNum();
        // 算出总页数
        int totalPage = getTotalPage(totalCount, pageSize);
        //sheets中数据的集合
        List<Object> datas = null;
        //  寻找header
        Row header = null;
        for(int i=0; i<headerCatchNum; i++){
            header = sheet.getRow(i);
            if(header != null){
                break;
            }
        }
        if (header != null) {
            //Excel列数
            int celNum = header.getPhysicalNumberOfCells();
            datas = new ArrayList<Object>();
            int firstRowNum = header.getRowNum();
            for (int j = 1; j <= totalPage; j++) {
                int firstResult = j == 1 ? firstRowNum : getFirstResult(j,
                        pageSize) + 1;
                int lastResult = pageSize * j > totalCount ? totalCount
                        : pageSize * j;
                for (int k = firstResult; k <= lastResult; k++) {
                    Row row = sheet.getRow(k);
                    //将每一行数据放入datas中
                    datas.add(getRowData(row, celNum));
                }
            }
        }
        return datas;
    }

    /**
     * 获取没row的数据
     *
     * @param row
     * @param celNum
     * @return
     */
    private static List<Object> getRowData(Row row, int celNum) {
        if (row != null) {
            List<Object> data = new ArrayList<Object>();
            for (int t = 0; t < celNum; t++) {
                Cell cell = row.getCell(t);
                if (cell == null) {
                    data.add(null);
                } else {
                    String value = getCellValue(cell);
                    data.add(value);
                }
            }
            return data;
        } else
            return null;
    }


    /**
     * 根据Cell的类型格式化cell数据并返回
     *
     * @param cell
     * @return
     */
    private static String getCellValue(Cell cell) {
        String value = "";
        DecimalFormat df = new DecimalFormat("#");
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                value = df.format(cell.getNumericCellValue()).toString();
                break;
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
            case BLANK:
                value = "";
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue()).trim();
                break;
            default:
                value = cell.toString();
                break;
        }
        return value;
    }

    /**
     * @param totalCount
     * @param pageSize
     * @return
     */
    private static int getTotalPage(int totalCount, int pageSize) {
        return (totalCount + pageSize - 1) / pageSize;
    }

    /**
     * @param pageNo
     * @param pageSize
     * @return
     */
    private static int getFirstResult(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }


    /**
     * 测试主方法
     *
     * @param args
     */
    public static void main(String[] args) {

//        String filePath = "C:\\工作空间\\02 政通集团\\冬奥通\\08 日报与周报\\技术部日报\\10月\\10月7日\\资料\\冬奥使用情况-部门累计统计.xlsx";
        String filePath = "C:\\工作空间\\02 政通集团\\冬奥通\\08 日报与周报\\技术部日报\\10月\\10月7日\\资料";
        List<File> files = new ArrayList<File>();
        FileUtils.exec(filePath,files);
        System.out.println(files);

//        File file = new File(filePath);
//        List<Object> datas = FileUtils.readExcel(file, 3);
//        JSONArray array = new JSONArray();

//        System.out.println(datas);

    }


}
