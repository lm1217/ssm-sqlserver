package com.iyeed.core.excel;

import com.iyeed.core.entity.excel.ReceivedExcel;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportExcelFileUtil {
    private static final Logger log = LoggerFactory.getLogger(ImportExcelFileUtil.class);
    private final static String excel2003L = ".xls";    //2003- 版本的excel
    private final static String excel2007U = ".xlsx";   //2007+ 版本的excel

    /**
     * 拼装单个obj  通用
     *
     * @param obj
     * @param row
     * @return
     * @throws Exception
     */
    private static Map<String, Object> dataObj(Object obj, Row row) throws Exception {
        Class<?> rowClazz = obj.getClass();
        Field[] fields = FieldUtils.getAllFields(rowClazz);
        if (fields == null || fields.length < 1) {
            return null;
        }

        //容器
        Map<String, Object> map = new HashMap<String, Object>();

        //注意excel表格字段顺序要和obj字段顺序对齐 （如果有多余字段请另作特殊下标对应处理）
        for (int j = 0; j < fields.length; j++) {
            map.put(fields[j].getName(), getVal(row.getCell(j)));
        }
        return map;
    }

    /**
     * 处理val
     *
     * @param cell
     * @return
     */
    public static String getVal(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");  //格式化字符类型的数字
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字
        switch (cell.getCellType()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value.toString();
    }

    /**
     * * 读取出filePath中的所有数据信息
     *
     * @param filePath excel文件的绝对路径
     * @param obj
     * @return
     */
    public static List<Map<String, Object>> getDataFromExcel(String filePath, Object obj) {
        if (null == obj) {
            return null;
        }

        List<Map<String, Object>> ret = null;
        FileInputStream fis = null;
        Workbook wookbook = null;
        int lineNum = 0;
        Sheet sheet = null;
        try {
            //获取一个绝对地址的流
            fis = new FileInputStream(filePath);
            wookbook = getWorkbook(fis, filePath);
            //得到一个工作表
            sheet = wookbook.getSheetAt(0);
            //获得表头
            Row rowHead = sheet.getRow(0);
            //列数
            int rows = rowHead.getPhysicalNumberOfCells();
            //行数
            lineNum = sheet.getLastRowNum();
            if (0 == lineNum) {
                log.info("ImportExcelFileUtil中的getDataFromExcel方法导入的Excel内没有数据！");
                return null;
            }
            ret = getData(sheet, lineNum, rows, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 封装获取的对象信息
     *
     * @param sheet
     * @param lineNum
     * @param rowNum
     * @param obj
     * @return
     */
    public static List<Map<String, Object>> getData(Sheet sheet, int lineNum, int rowNum, Object obj) {
        List<Map<String, Object>> ret = null;
        try {
            //容器
            ret = new ArrayList<Map<String, Object>>();
            //获得所有数据
            for (int i = 1; i <= lineNum; i++) {
                //获得第i行对象
                Row row = sheet.getRow(i);
                if (row != null) {
                    //装载obj
                    ret.add(dataObj(obj, row));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr);  //2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr);  //2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    public static void main(String[] args) throws Exception{
        ReceivedExcel receivedExcel = new ReceivedExcel();
        //实体类的属性要与Excel中的行数保持一致。本人之前就栽在这，
        //我在实体类里实现Serializable并生成了serialVersionUID，
        //和excel中的行数不一致。
        List<Map<String, Object>> dataFromExcel = getDataFromExcel("D:\\TCT\\received.xlsx", receivedExcel);
        for (int i = 0; i < dataFromExcel.size(); i++) {
            for (Map.Entry<String, Object> entry : dataFromExcel.get(i).entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }
        }
        System.out.println(dataFromExcel);
    }
}