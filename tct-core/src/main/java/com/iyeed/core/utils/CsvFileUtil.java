package com.iyeed.core.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvFileUtil {  
    // CSV文件编码  
    public static final String ENCODE = "GB18030";
  
    private FileInputStream fis = null;  
    private InputStreamReader isw = null;  
    private BufferedReader br = null;  
    private InputStreamReader fr = null;  
  
  
     
    public CsvFileUtil(String filename) throws Exception { 
    	fis = new FileInputStream(filename); 
    	isw = new InputStreamReader(fis, ENCODE); 
        br = new BufferedReader(isw);  
        fr = new InputStreamReader(new FileInputStream(filename), ENCODE);  
    }  
  
    // ==========以下是公开方法============================= 
    /** 
     * 解析csv文件 到一个list中 每个单元个为一个String类型记录，每一行为一个list。 再将所有的行放到一个总list中 
     */ 
    public List<List<String>> readCSVFile() throws IOException {  
        br = new BufferedReader(fr);  
        String rec = null;// 一行  
        String str;// 一个单元格  
        List<List<String>> listFile = new ArrayList<List<String>>();  
        try {  
            // 读取一行  
            while ((rec = br.readLine()) != null) {  
            	int index=0;
                Pattern pCells = Pattern.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");  
                Matcher mCells = pCells.matcher(rec);  
                List<String> cells = new ArrayList<String>();// 每行记录一个list  
                // 读取每个单元格  
                while (mCells.find()) {  
                    str = mCells.group();
                    str = str.replaceAll("\u0000\"", "\"");
                    str = str.replaceAll("(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1");  
                    str = str.replaceAll("(?sm)(\"(\"))", "$2");  
                    cells.add(str);  
                    index = mCells.end();  
                }
                str = rec.substring(index).concat(",");
                str = str.replaceAll("(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1");
                str = str.replaceAll("(?sm)(\"(\"))", "$2");
                cells.add(str);
                listFile.add(cells);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (fr != null) {  
                fr.close();  
            }  
            if (br != null) {  
                br.close();  
            }
            if (isw != null) {  
            	isw.close();  
            }  
            if (fis != null) {  
            	fis.close();  
            }  
        }  
        return listFile;  
    } 
    
    // ==========以下是公开方法============================= 
    /** 
     * 解析txt文件, 按照分号";"分割 到一个list中 每个单元个为一个String类型记录，每一行为一个list。 再将所有的行放到一个总list中 
     */ 
    public List<List<String>> readFile() throws IOException {  
        br = new BufferedReader(fr);  
        String rec = null;// 一行  
        String str;// 一个单元格  
        List<List<String>> listFile = new ArrayList<List<String>>();  
        try {  
            // 读取一行  
            while ((rec = br.readLine()) != null) {  
            	int index=0;
                Pattern pCells = Pattern.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^;]*;");  
                Matcher mCells = pCells.matcher(rec);  
                List<String> cells = new ArrayList<String>();// 每行记录一个list  
                // 读取每个单元格  
                while (mCells.find()) {  
                    str = mCells.group();  
                    str = str.replaceAll("(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*;", "$1");  
                    str = str.replaceAll("(?sm)(\"(\"))", "$2");  
                    cells.add(str);  
                    index = mCells.end();  
                }  
                cells.add(rec.substring(index).equals("\"\"")? "":rec.substring(index).replaceAll("\"",""));
                listFile.add(cells);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (fr != null) {  
                fr.close();  
            }  
            if (br != null) {  
                br.close();  
            } 
           
            if (isw != null) {  
            	isw.close();  
            }  
            if (fis != null) {  
            	fis.close();  
            }  
        }  
        return listFile;  
    }
    
    public static void main(String args []){
    	String json = "\"客户"; 
    	String t = json.replaceAll("\"",""); 
    	System.out.println(t);
    }
}  