package com.iyeed.core.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wangpeng
 * 分页公共方法
 */
public class PaginationUtil implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private int               num              = 1;   // 当前页号, 采用自然数计数 1,2,3,...
    private int               size             = 4;   // 页面大小:一个页面显示多少个数据
    private int               rowCount;               // 数据总数：一共有多少个数据
    private int               pageCount;              // 页面总数
    private int               startRow;               // 当前页面开始行, 第一行是0行
    private int               first            = 1;   // 第一页 页号
    private int               last;                   // 最后页 页号
    private int               next;                   // 下一页 页号
    private int               prev;                   // 前页 页号
    private int               start;                  // 页号式导航, 起始页号
    private int               end;                    // 页号式导航, 结束页号
    private int               numCount         = 9;   // 页号式导航, 最多显示页号数量为numCount+1;这里显示11页。
    private String            url;                    //链接地址

    private String            pageHtml;               //生成html
    private List<String>      pageNumbers      = null;

    public PaginationUtil(int num, int size) {
        this.num = num;
        this.size = size;
    }

    public void createPagination(int rowCount) {
        this.rowCount = rowCount;
        this.pageCount = (int) Math.ceil((double) rowCount / size);

        this.num = Math.min(this.num, pageCount);
        this.num = Math.max(1, this.num);

        this.startRow = (this.num - 1) * size;
        this.last = this.pageCount;
        this.next = Math.min(this.pageCount, this.num + 1);
        this.prev = Math.max(1, this.num - 1);

        // 计算page 控制
        start = Math.max(this.num - numCount / 2, first);
        end = Math.min(start + numCount, last);
        if (end - start < numCount) {
            start = Math.max(end - numCount, 1);
        }

        pageNumbers = new ArrayList<String>();
        for (int i = start; i <= end; i++) {
            pageNumbers.add(i + "");
        }
    }

    public PaginationUtil(int size, String str_num, int rowCount) {
        int num = 1;
        if (str_num != null) {
            try {
                num = Integer.parseInt(str_num);
            } catch (Exception e) {
                num = 1;
            }
        }
        this.num = num;
        this.size = size;
        this.rowCount = rowCount;
        this.pageCount = (int) Math.ceil((double) rowCount / size);

        this.num = Math.min(this.num, pageCount);
        this.num = Math.max(1, this.num);

        this.startRow = (this.num - 1) * size;
        this.last = this.pageCount;
        this.next = Math.min(this.pageCount, this.num + 1);
        this.prev = Math.max(1, this.num - 1);

        // 计算page 控制
        start = Math.max(this.num - numCount / 2, first);
        end = Math.min(start + numCount, last);
        if (end - start < numCount) {
            start = Math.max(end - numCount, 1);
        }
    }

    public PaginationUtil(int size, String str_num, int rowCount, String url) {
        int num = 1;
        if (str_num != null) {
            try {
                num = Integer.parseInt(str_num);
            } catch (Exception e) {
                num = 1;
            }
        }
        this.url = url;
        this.num = num;
        this.size = size;
        this.rowCount = rowCount;
        this.pageCount = (int) Math.ceil((double) rowCount / size);

        this.num = Math.min(this.num, pageCount);
        this.num = Math.max(1, this.num);

        this.startRow = (this.num - 1) * size;
        this.last = this.pageCount;
        this.next = Math.min(this.pageCount, this.num + 1);
        this.prev = Math.max(1, this.num - 1);

        // 计算page 控制
        start = Math.max(this.num - numCount / 2, first);
        end = Math.min(start + numCount, last);
        if (end - start < numCount) {
            start = Math.max(end - numCount, 1);
        }
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getNumCount() {
        return numCount;
    }

    public void setNumCount(int numCount) {
        this.numCount = numCount;
    }

    public List<String> getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(List<String> pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    /**
     * 从多少页开始查起
     * @return
     */
    public int getStartNumber() {
        return (this.num - 1) * this.size;
    }

    public String getPageHtml() {
        StringBuffer stb = new StringBuffer();

        if (url.indexOf("?") != -1) {
            url = url + "&page=";
        } else {
            url = url + "?page=";
        }

        if (num != 1) {
            stb.append("<a href='" + (url + 1) + "'>首页</a><a href='" + (url + prev) + "'>上一页</a>");
        } else {
            stb.append("<span class='prev-disabled'> 上一页</span> ");
        }

        int beginPage = num - 3 < 1 ? 1 : num - 3;
        if (beginPage <= pageCount) {
            int i = beginPage;
            for (int j = 0; (i <= pageCount) && (j < 6); j++) {
                if (num != i) {
                    stb.append("<a href='" + (url + i) + "'>" + i + "</a>");
                } else {
                    stb.append("<span style='color:red'>" + i + "</span>");
                }
                i++;
            }
        }

        if (num != 0 && pageCount != 0 && num != pageCount) {
            stb.append("<a href='" + (url + next) + "'>下一页</a><a href='" + (url + last)
                       + "'>尾页</a>");
        } else {
            stb.append("<span class='prev-disabled'> 下一页</span> ");
        }

        return stb.toString();
    }

    public void setPageHtml(String pageHtml) {
        this.pageHtml = pageHtml;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
