package com.iyeed.core.excel;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

import com.iyeed.core.entity.user.MdUser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


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

    public static void main(String[] args)
    {
        // 测试学生
        ExportExcel<MdUser> ex = new ExportExcel<MdUser>();
        String[] headers = { "ID", "姓名", "Email", "日期" };
        List<MdUser> dataset = new ArrayList<MdUser>();
        MdUser user1 = new MdUser();
        user1.setUserId("1");
        user1.setUserName("张三");
        user1.setEmail("111@qq.com");
        user1.setInputDate(new Date());
        MdUser user2 = new MdUser();
        user2.setUserId("2");
        user2.setUserName("李四");
        user2.setEmail("222@qq.com");
        user2.setInputDate(new Date());
        MdUser user3 = new MdUser();
        user3.setUserId("3");
        user3.setUserName("王五");
        user3.setEmail("333@qq.com");
        user3.setInputDate(new Date());
        dataset.add(user1);
        dataset.add(user2);
        dataset.add(user3);
        try
        {

            OutputStream out = new FileOutputStream("E://a.xls");

            ex.exportExcel("测试", headers, dataset, out);
            out.close();
            JOptionPane.showMessageDialog(null, "导出成功!");
            System.out.println("excel导出成功！");
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}