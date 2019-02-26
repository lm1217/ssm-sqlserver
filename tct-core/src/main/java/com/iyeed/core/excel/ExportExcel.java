package com.iyeed.core.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExportExcel<T> {
    public void exportExcel(String title, Collection<T> dataset, OutputStream out) {
        exportExcel(title, null, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String title, String[] headers, Collection<T> dataset, OutputStream out) {
        exportExcel(title, headers, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String title, String[] headers,Collection<T> dataset, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value instanceof Integer) {
                        int intValue = (Integer) value;
                        cell.setCellValue(intValue);
                    }
                    else if (value instanceof Long) {
                        long longValue = (Long) value;
                        cell.setCellValue(longValue);
                    }
                    else if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        textValue = "1";
                        if (!bValue) {
                            textValue = "0";
                        }
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if(value == null)
                        {
                            textValue = "";
                        }
                        else {
                            textValue = value.toString();
                        }

                    }

                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(
                                    textValue);
                            richString.applyFont(font);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }

        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportForStockReportExcel(String title, Collection<T> dataset, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        // 单元格
        HSSFCell cell = null;
        // 单元格内容
        HSSFRichTextString text = null;
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0,0,20,22));
        cell = row.createCell(20);
        cell.setCellStyle(style);
        text = new HSSFRichTextString("总库存");
        cell.setCellValue(text);
        sheet.addMergedRegion(new CellRangeAddress(0,0,17,19));
        cell = row.createCell(17);
        cell.setCellStyle(style);
        text = new HSSFRichTextString("店仓库存");
        cell.setCellValue(text);
        sheet.addMergedRegion(new CellRangeAddress(0,0,14,16));
        cell = row.createCell(14);
        cell.setCellStyle(style);
        text = new HSSFRichTextString("柜面库存");
        cell.setCellValue(text);

        row = sheet.createRow(1);
        String[] headers = {"品牌", "品牌使用店号", "门店号", "门店名称", "门店属性", "SKU_CODE", "SKU_NAME", "区域", "城市", "渠道", "门店类型", "ASM", "AC", "门店帐号类型", "柜面总库存", "可用库存", "冻结数量", "店仓总库存", "可用库存", "冻结数量", "总库存", "总可用库存", "总冻结数量"};
        for (short i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
            text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 1;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value instanceof Integer) {
                        int intValue = (Integer) value;
                        cell.setCellValue(intValue);
                    }
                    else if (value instanceof Long) {
                        long longValue = (Long) value;
                        cell.setCellValue(longValue);
                    }
                    else if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        textValue = "1";
                        if (!bValue) {
                            textValue = "0";
                        }
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if(value == null)
                        {
                            textValue = "";
                        }
                        else {
                            textValue = value.toString();
                        }

                    }

                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(
                                    textValue);
                            richString.applyFont(font);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }

        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportForStockSynthesizeReportExcel(String title, Collection<T> dataset, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        // 单元格
        HSSFCell cell = null;
        // 单元格内容
        HSSFRichTextString text = null;
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0,0,11,12));
        cell = row.createCell(11);
        cell.setCellStyle(style);
        text = new HSSFRichTextString("异常调整");
        cell.setCellValue(text);
        sheet.addMergedRegion(new CellRangeAddress(0,0,6,9));
        cell = row.createCell(6);
        cell.setCellStyle(style);
        text = new HSSFRichTextString("销毁");
        cell.setCellValue(text);
        sheet.addMergedRegion(new CellRangeAddress(0,0,4,5));
        cell = row.createCell(4);
        cell.setCellStyle(style);
        text = new HSSFRichTextString("未收货");
        cell.setCellValue(text);
        sheet.addMergedRegion(new CellRangeAddress(0,0,2,3));
        cell = row.createCell(2);
        cell.setCellStyle(style);
        text = new HSSFRichTextString("已收货");
        cell.setCellValue(text);

        row = sheet.createRow(1);
        String[] headers = {"店铺", "期初库存", "大仓", "调拨", "大仓", "调拨", "正常使用完毕", "破损", "过期", "失窃", "调出", "差异调整-增加", "差异调整-减少", "期末库存", "区域", "城市", "渠道", "门店类型", "ASM", "AC"};
        for (short i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
            text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 1;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value instanceof Integer) {
                        int intValue = (Integer) value;
                        cell.setCellValue(intValue);
                    }
                    else if (value instanceof Long) {
                        long longValue = (Long) value;
                        cell.setCellValue(longValue);
                    }
                    else if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        textValue = "1";
                        if (!bValue) {
                            textValue = "0";
                        }
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if(value == null)
                        {
                            textValue = "";
                        }
                        else {
                            textValue = value.toString();
                        }

                    }

                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(
                                    textValue);
                            richString.applyFont(font);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }

        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadExcel(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            String name = file.getName();
            // 取得文件名。
            String filename = java.net.URLEncoder.encode(name,"utf-8");
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(
                    response.getOutputStream());
            response.setContentType("application/ms-excel;charset=gb2312");
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}