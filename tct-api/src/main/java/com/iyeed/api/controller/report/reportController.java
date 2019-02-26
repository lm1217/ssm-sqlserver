package com.iyeed.api.controller.report;

import com.iyeed.api.controller.BaseController;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.core.common.emuns.RespCode;
import com.iyeed.core.common.model.AjaxResponse;
import com.iyeed.core.entity.form.vo.*;
import com.iyeed.core.entity.receive.vo.ReceiveTesterBean;
import com.iyeed.core.entity.receive.vo.WaitReceiveTesterBean;
import com.iyeed.core.entity.stock.vo.GetStockInvReportListBean;
import com.iyeed.core.entity.stock.vo.GetStockInvReportListForm;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.excel.ExportExcel;
import com.iyeed.core.utils.CommonUtil;
import org.apache.commons.io.FileUtils;
import com.iyeed.core.utils.DateUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600) //解决跨域问题
@RequestMapping(value = "api/report")
public class ReportController extends BaseController {

    @SystemControllerLog(module = "报表管理", businessDesc = "根据条件获取库存报表列表")
    @RequestMapping(value = "getStockInvReportList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getStockInvReportList(@RequestBody GetStockInvReportListForm form) {
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }
        SystemUser systemUser = this.getSystemUser();
        if (isNull(form.getPageIndex())) form.setPageIndex(1);
        if (isNull(form.getPageSize())) form.setPageSize(10);

        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_skuCode", form.getSkuCode().toUpperCase());//搜索SKU编码
        queryMap.put("q_skuName", form.getSkuName());//搜索SKU名称
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        if (systemUser.getUserType() != 0 && systemUser.getUserType() != 2 && systemUser.getUserType() != 5) {
            queryMap.put("q_userNo", systemUser.getUserNo());
        }

        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<GetStockInvReportListBean>> serviceResult = bdStockInvService.getStockInvReportList(queryMap, pagerInfo);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        form.setTotalPage(pagerInfo.getTotalPage());

        dataMap.put("form", form);
        dataMap.put("stockInvReportList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @RequestMapping(value = "reportDownload.json", method = { RequestMethod.POST })
    public ResponseEntity<byte[]> reportDownload(@RequestBody GetStockInvReportListForm form,HttpServletRequest request) throws IOException {
        // 获取物理路径
        String targetDirectory = request.getSession().getServletContext().getRealPath("/");
        String contextPath = request.getContextPath().replace("/", "");
        String[] strArray = targetDirectory.split(contextPath);
        targetDirectory = strArray[0] + "/tct-tp";
        // 保存文件位置
        String extName = ".xls";
        String filename = "StockReport_" + DateUtils.dateToStr(new Date(), "yyyyMMddHHmmss") + extName;
        String path = "/excel/" + filename;

        targetDirectory = targetDirectory + path;
        // 判断路径是否存在，如果不存在就创建一个
        File filePath = new File(targetDirectory);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        HttpHeaders httpHeaders = new HttpHeaders();//http头信息
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");//设置编码
        httpHeaders.setContentDispositionFormData("attachment", downloadFileName);
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if (isNull(form)) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NOT_FOUND);
        }
        SystemUser systemUser = this.getSystemUser();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_skuCode", form.getSkuCode().toUpperCase());//搜索SKU编码
        queryMap.put("q_skuName", form.getSkuName());//搜索SKU名称
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        if (systemUser.getUserType() != 0 && systemUser.getUserType() != 2 && systemUser.getUserType() != 5) {
            queryMap.put("q_userNo", systemUser.getUserNo());
        }
        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<GetStockInvReportListBean>> serviceResult = bdStockInvService.exportStockInvReportExcel(queryMap);
        if (!serviceResult.getSuccess()) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<GetStockInvReportListBean> reportList = serviceResult.getResult();
        if (isNull(reportList) || reportList.size() == 0) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NO_CONTENT);
        }

        ExportExcel<GetStockInvReportListBean> ex = new ExportExcel<>();

        OutputStream out = new FileOutputStream(targetDirectory);
        String title = "仓库报表";
        ex.exportForStockReportExcel(title, reportList, out, "yyyy-MM-dd");
        out.close();
        File file = new File(targetDirectory);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.OK);
    }

    @SystemControllerLog(module = "报表管理", businessDesc = "根据条件获取异常调整单报表列表")
    @RequestMapping(value = "getExceptionReportList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getExceptionReportList(@RequestBody FormReportForm form) {
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (isNull(form.getPageIndex())) form.setPageIndex(1);
        if (isNull(form.getPageSize())) form.setPageSize(10);

        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_skuCode", form.getSkuCode().toUpperCase());//搜索SKU编码
        queryMap.put("q_skuName", form.getSkuName());//搜索SKU名称
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号

        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<ExceptionReportBean>> serviceResult = bdFormService.getExceptionReportList(queryMap, pagerInfo);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        form.setTotalPage(pagerInfo.getTotalPage());

        dataMap.put("form", form);
        dataMap.put("exceptionReportList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @RequestMapping(value = "exceptionReportDownload.json", method = { RequestMethod.POST })
    public ResponseEntity<byte[]> exceptionReportDownload(@RequestBody FormReportForm form, HttpServletRequest request) throws IOException {
        // 获取物理路径
        String targetDirectory = request.getSession().getServletContext().getRealPath("/");
        String contextPath = request.getContextPath().replace("/", "");
        String[] strArray = targetDirectory.split(contextPath);
        targetDirectory = strArray[0] + "/tct-tp";
        // 保存文件位置
        String extName = ".xls";
        String filename = "ExceptionReport_" + DateUtils.dateToStr(new Date(), "yyyyMMddHHmmss") + extName;
        String path = "/excel/" + filename;

        targetDirectory = targetDirectory + path;
        // 判断路径是否存在，如果不存在就创建一个
        File filePath = new File(targetDirectory);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        HttpHeaders httpHeaders = new HttpHeaders();//http头信息
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");//设置编码
        httpHeaders.setContentDispositionFormData("attachment", downloadFileName);
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if (isNull(form)) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NOT_FOUND);
        }
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_skuCode", form.getSkuCode().toUpperCase());//搜索SKU编码
        queryMap.put("q_skuName", form.getSkuName());//搜索SKU名称
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<ExceptionReportBean>> serviceResult = bdFormService.exportExceptionReportExcel(queryMap);
        if (!serviceResult.getSuccess()) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<ExceptionReportBean> reportList = serviceResult.getResult();
        if (isNull(reportList) || reportList.size() == 0) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NO_CONTENT);
        }

        ExportExcel<ExceptionReportBean> ex = new ExportExcel<>();
        String[] excelHeaders = {"ID", "门店号", "门店名称", "申请单号", "申请日期", "差异原因", "申请单状态", "品牌名称", "SKU_CODE", "SKU名称", "调整类型", "调整数量"};

        OutputStream out = new FileOutputStream(targetDirectory);
        String title = "异常调整报表";
        ex.exportExcel(title, excelHeaders, reportList, out);
        out.close();
        File file = new File(targetDirectory);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.OK);
    }

    @SystemControllerLog(module = "报表管理", businessDesc = "根据条件获取销毁单报表列表")
    @RequestMapping(value = "getDestroyReportList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getDestroyReportList(@RequestBody FormReportForm form) {
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (isNull(form.getPageIndex())) form.setPageIndex(1);
        if (isNull(form.getPageSize())) form.setPageSize(10);

        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间

        if (isNull(queryMap.get("q_starttime"))) {
            queryMap.put("q_starttime", DateUtils.initDateStrByMonthFirstDay());//默认当前月的1号开始
        }
        if (isNull(queryMap.get("q_endtime"))) {
            queryMap.put("q_endtime", DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));//默认当前时间结束
        }

        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<GetDestroyFormListBean>> serviceResult = bdFormService.getDestroyFormList(queryMap, pagerInfo);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        form.setTotalPage(pagerInfo.getTotalPage());

        dataMap.put("form", form);
        dataMap.put("destroyReportList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @RequestMapping(value = "destroyReportDownload.json", method = { RequestMethod.POST })
    public ResponseEntity<byte[]> destroyReportDownload(@RequestBody FormReportForm form, HttpServletRequest request) throws IOException {
        // 获取物理路径
        String targetDirectory = request.getSession().getServletContext().getRealPath("/");
        String contextPath = request.getContextPath().replace("/", "");
        String[] strArray = targetDirectory.split(contextPath);
        targetDirectory = strArray[0] + "/tct-tp";
        // 保存文件位置
        String extName = ".xls";
        String filename = "DestroyReport_" + DateUtils.dateToStr(new Date(), "yyyyMMddHHmmss") + extName;
        String path = "/excel/" + filename;

        targetDirectory = targetDirectory + path;
        // 判断路径是否存在，如果不存在就创建一个
        File filePath = new File(targetDirectory);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        HttpHeaders httpHeaders = new HttpHeaders();//http头信息
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");//设置编码
        httpHeaders.setContentDispositionFormData("attachment", downloadFileName);
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if (isNull(form)) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NOT_FOUND);
        }
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间

        if (isNull(queryMap.get("q_starttime"))) {
            queryMap.put("q_starttime", DateUtils.initDateStrByMonthFirstDay());//默认当前月的1号开始
        }
        if (isNull(queryMap.get("q_endtime"))) {
            queryMap.put("q_endtime", DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));//默认当前时间结束
        }

        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<GetDestroyFormListBean>> serviceResult = bdFormService.exportDestroyReportExcel(queryMap);
        if (!serviceResult.getSuccess()) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<GetDestroyFormListBean> reportList = serviceResult.getResult();
        if (isNull(reportList) || reportList.size() == 0) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NO_CONTENT);
        }

        ExportExcel<GetDestroyFormListBean> ex = new ExportExcel<>();
        String[] excelHeaders = {"品牌", "品牌使用店号", "门店号", "门店名称", "门店属性", "SKU_CODE", "SKU_NAME", "当月销毁件数", "CYTD销毁件数", "FYTD销毁件数", "当月主管销毁频次", "CYTD主管销毁频率", "FYTD主管销毁频率", "区域", "城市", "渠道", "门店类型", "ASM", "AC"};

        OutputStream out = new FileOutputStream(targetDirectory);
        String starttime = DateUtils.dateToStr(DateUtils.strToDate(queryMap.get("q_starttime").toString(), "yyyy-mm-dd"), "mmdd");
        String endtime = DateUtils.dateToStr(DateUtils.strToDate(queryMap.get("q_endtime").toString(), "yyyy-mm-dd"), "mmdd");
        String title = "销毁单报表(" + starttime + "-" + endtime + ")";
        ex.exportExcel(title, excelHeaders, reportList, out);
        out.close();
        File file = new File(targetDirectory);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.OK);
    }

    @SystemControllerLog(module = "报表管理", businessDesc = "根据条件获取销毁单-盗损率报表列表")
    @RequestMapping(value = "getDestroyTheftReportList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getDestroyTheftReportList(@RequestBody FormReportForm form) {
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (isNull(form.getPageIndex())) form.setPageIndex(1);
        if (isNull(form.getPageSize())) form.setPageSize(10);

        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间

        if (isNull(queryMap.get("q_starttime"))) {
            queryMap.put("q_starttime", DateUtils.initDateStrByMonthFirstDay());//默认当前月的1号开始
        }
        if (isNull(queryMap.get("q_endtime"))) {
            queryMap.put("q_endtime", DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));//默认当前时间结束
        }

        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<GetDestroyTheftFormListBean>> serviceResult = bdFormService.getDestroyTheftFormList(queryMap, pagerInfo);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        form.setTotalPage(pagerInfo.getTotalPage());

        dataMap.put("form", form);
        dataMap.put("destroyTheftReportList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @RequestMapping(value = "destroyTheftReportDownload.json", method = { RequestMethod.POST })
    public ResponseEntity<byte[]> destroyTheftReportDownload(@RequestBody FormReportForm form, HttpServletRequest request) throws IOException {
        // 获取物理路径
        String targetDirectory = request.getSession().getServletContext().getRealPath("/");
        String contextPath = request.getContextPath().replace("/", "");
        String[] strArray = targetDirectory.split(contextPath);
        targetDirectory = strArray[0] + "/tct-tp";
        // 保存文件位置
        String extName = ".xls";
        String filename = "DestroyTheftReport_" + DateUtils.dateToStr(new Date(), "yyyyMMddHHmmss") + extName;
        String path = "/excel/" + filename;

        targetDirectory = targetDirectory + path;
        // 判断路径是否存在，如果不存在就创建一个
        File filePath = new File(targetDirectory);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        HttpHeaders httpHeaders = new HttpHeaders();//http头信息
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");//设置编码
        httpHeaders.setContentDispositionFormData("attachment", downloadFileName);
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if (isNull(form)) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NOT_FOUND);
        }
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间

        if (isNull(queryMap.get("q_starttime"))) {
            queryMap.put("q_starttime", DateUtils.initDateStrByMonthFirstDay());//默认当前月的1号开始
        }
        if (isNull(queryMap.get("q_endtime"))) {
            queryMap.put("q_endtime", DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));//默认当前时间结束
        }

        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<GetDestroyTheftFormListBean>> serviceResult = bdFormService.exportDestroyTheftReportExcel(queryMap);
        if (!serviceResult.getSuccess()) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<GetDestroyTheftFormListBean> reportList = serviceResult.getResult();
        if (isNull(reportList) || reportList.size() == 0) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NO_CONTENT);
        }

        ExportExcel<GetDestroyTheftFormListBean> ex = new ExportExcel<>();
        String[] excelHeaders = {"品牌", "品牌使用店号", "门店号", "门店名称", "门店属性", "SKU_CODE", "SKU_NAME", "当月盗损件数", "当月库存件数", "CYTD盗损件数", "CYTD库存件数", "FYTD盗损件数", "FYTD库存件数", "当月盗损率", "CYTD盗损率", "FYTD盗损率", "区域", "城市", "渠道", "门店类型", "ASM", "AC"};

        OutputStream out = new FileOutputStream(targetDirectory);
        String starttime = DateUtils.dateToStr(DateUtils.strToDate(queryMap.get("q_starttime").toString(), "yyyy-mm-dd"), "mmdd");
        String endtime = DateUtils.dateToStr(DateUtils.strToDate(queryMap.get("q_endtime").toString(), "yyyy-mm-dd"), "mmdd");
        String title = "盗损率报表(" + starttime + "-" + endtime + ")";
        ex.exportExcel(title, excelHeaders, reportList, out);
        out.close();
        File file = new File(targetDirectory);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.OK);
    }

    @SystemControllerLog(module = "报表管理", businessDesc = "根据条件获取收货单报表列表")
    @RequestMapping(value = "getReceiveTesterReportList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getReceiveTesterReportList(@RequestBody FormReportForm form) {
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (isNull(form.getPageIndex())) form.setPageIndex(1);
        if (isNull(form.getPageSize())) form.setPageSize(10);

        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间

        if (isNull(queryMap.get("q_starttime"))) {
            queryMap.put("q_starttime", DateUtils.initDateStrByMonthFirstDay());//默认当前月的1号开始
        }
        if (isNull(queryMap.get("q_endtime"))) {
            queryMap.put("q_endtime", DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));//默认当前时间结束
        }

        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<ReceiveTesterBean>> serviceResult = bdReceivingService.getReceiveTesterList(queryMap, pagerInfo);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        form.setTotalPage(pagerInfo.getTotalPage());

        dataMap.put("form", form);
        dataMap.put("receiveTesterReportList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @RequestMapping(value = "receiveTesterReportDownload.json", method = { RequestMethod.POST })
    public ResponseEntity<byte[]> receiveTesterReportDownload(@RequestBody FormReportForm form, HttpServletRequest request) throws IOException {
        // 获取物理路径
        String targetDirectory = request.getSession().getServletContext().getRealPath("/");
        String contextPath = request.getContextPath().replace("/", "");
        String[] strArray = targetDirectory.split(contextPath);
        targetDirectory = strArray[0] + "/tct-tp";
        // 保存文件位置
        String extName = ".xls";
        String filename = "ReceiveTesterReport_" + DateUtils.dateToStr(new Date(), "yyyyMMddHHmmss") + extName;
        String path = "/excel/" + filename;

        targetDirectory = targetDirectory + path;
        // 判断路径是否存在，如果不存在就创建一个
        File filePath = new File(targetDirectory);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        HttpHeaders httpHeaders = new HttpHeaders();//http头信息
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");//设置编码
        httpHeaders.setContentDispositionFormData("attachment", downloadFileName);
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if (isNull(form)) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NOT_FOUND);
        }
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间

        if (isNull(queryMap.get("q_starttime"))) {
            queryMap.put("q_starttime", DateUtils.initDateStrByMonthFirstDay());//默认当前月的1号开始
        }
        if (isNull(queryMap.get("q_endtime"))) {
            queryMap.put("q_endtime", DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));//默认当前时间结束
        }

        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<ReceiveTesterBean>> serviceResult = bdReceivingService.exportReceiveTesterReportExcel(queryMap);
        if (!serviceResult.getSuccess()) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<ReceiveTesterBean> reportList = serviceResult.getResult();
        if (isNull(reportList) || reportList.size() == 0) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NO_CONTENT);
        }

        ExportExcel<ReceiveTesterBean> ex = new ExportExcel<>();
        String[] excelHeaders = {"品牌", "品牌使用店号", "门店号", "门店名称", "门店属性", "SKU_CODE", "SKU_NAME", "日常试用装", "VM试用装", "新开柜试用装", "活动试用装", "美容坊试用装", "Aerin试用装", "总试用装", "区域", "城市", "渠道", "门店类型", "ASM", "AC"};

        OutputStream out = new FileOutputStream(targetDirectory);
        String starttime = DateUtils.dateToStr(DateUtils.strToDate(queryMap.get("q_starttime").toString(), "yyyy-mm-dd"), "mmdd");
        String endtime = DateUtils.dateToStr(DateUtils.strToDate(queryMap.get("q_endtime").toString(), "yyyy-mm-dd"), "mmdd");
        String title = "收货单报表(" + starttime + "-" + endtime + ")";
        ex.exportExcel(title, excelHeaders, reportList, out);
        out.close();
        File file = new File(targetDirectory);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.OK);
    }

    @SystemControllerLog(module = "报表管理", businessDesc = "根据条件获取周转天数报表列表")
    @RequestMapping(value = "getDSIReportList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getDSIReportList(@RequestBody FormReportForm form) {
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (isNull(form.getPageIndex())) form.setPageIndex(1);
        if (isNull(form.getPageSize())) form.setPageSize(10);

        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间

        if (isNull(queryMap.get("q_starttime"))) {
            queryMap.put("q_starttime", DateUtils.initDateStrByMonthFirstDay());//默认当前月的1号开始
        }
        if (isNull(queryMap.get("q_endtime"))) {
            queryMap.put("q_endtime", DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));//默认当前时间结束
        }

        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<GetDSIFormListBean>> serviceResult = bdFormService.getDSIFormList(queryMap, pagerInfo);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        form.setTotalPage(pagerInfo.getTotalPage());

        dataMap.put("form", form);
        dataMap.put("dsiReportList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @RequestMapping(value = "disReportDownload.json", method = { RequestMethod.POST })
    public ResponseEntity<byte[]> disReportDownload(@RequestBody FormReportForm form, HttpServletRequest request) throws IOException {
        // 获取物理路径
        String targetDirectory = request.getSession().getServletContext().getRealPath("/");
        String contextPath = request.getContextPath().replace("/", "");
        String[] strArray = targetDirectory.split(contextPath);
        targetDirectory = strArray[0] + "/tct-tp";
        // 保存文件位置
        String extName = ".xls";
        String filename = "DSIReport_" + DateUtils.dateToStr(new Date(), "yyyyMMddHHmmss") + extName;
        String path = "/excel/" + filename;

        targetDirectory = targetDirectory + path;
        // 判断路径是否存在，如果不存在就创建一个
        File filePath = new File(targetDirectory);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        HttpHeaders httpHeaders = new HttpHeaders();//http头信息
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");//设置编码
        httpHeaders.setContentDispositionFormData("attachment", downloadFileName);
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if (isNull(form)) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NOT_FOUND);
        }
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间

        if (isNull(queryMap.get("q_starttime"))) {
            queryMap.put("q_starttime", DateUtils.initDateStrByMonthFirstDay());//默认当前月的1号开始
        }
        if (isNull(queryMap.get("q_endtime"))) {
            queryMap.put("q_endtime", DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));//默认当前时间结束
        }

        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<GetDSIFormListBean>> serviceResult = bdFormService.exportDSIReportExcel(queryMap);
        if (!serviceResult.getSuccess()) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<GetDSIFormListBean> reportList = serviceResult.getResult();
        if (isNull(reportList) || reportList.size() == 0) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NO_CONTENT);
        }

        ExportExcel<GetDSIFormListBean> ex = new ExportExcel<>();
        String[] excelHeaders = {"品牌", "单号", "品牌使用店号", "门店号", "门店名称", "门店属性", "SKU_CODE", "SKU_NAME", "销毁件数", "周转天数", "区域", "城市", "渠道", "门店类型", "ASM", "AC"};

        OutputStream out = new FileOutputStream(targetDirectory);
        String starttime = DateUtils.dateToStr(DateUtils.strToDate(queryMap.get("q_starttime").toString(), "yyyy-mm-dd"), "mmdd");
        String endtime = DateUtils.dateToStr(DateUtils.strToDate(queryMap.get("q_endtime").toString(), "yyyy-mm-dd"), "mmdd");
        String title = "周转天数报表(" + starttime + "-" + endtime + ")";
        ex.exportExcel(title, excelHeaders, reportList, out);
        out.close();
        File file = new File(targetDirectory);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.OK);
    }

    // 导出待收货报表
    @RequestMapping(value = "waitReceiveReportDownload.json", method = { RequestMethod.POST })
    public ResponseEntity<byte[]> waitReceiveReportDownload(@RequestBody FormReportForm form, HttpServletRequest request) throws IOException {
        // 获取物理路径
        String targetDirectory = request.getSession().getServletContext().getRealPath("/");
        String contextPath = request.getContextPath().replace("/", "");
        String[] strArray = targetDirectory.split(contextPath);
        targetDirectory = strArray[0] + "/tct-tp";
        // 保存文件位置
        String extName = ".xls";
        String filename = "WaitReceiveReport_" + DateUtils.dateToStr(new Date(), "yyyyMMddHHmmss") + extName;
        String path = "/excel/" + filename;

        targetDirectory = targetDirectory + path;
        // 判断路径是否存在，如果不存在就创建一个
        File filePath = new File(targetDirectory);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        HttpHeaders httpHeaders = new HttpHeaders();//http头信息
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");//设置编码
        httpHeaders.setContentDispositionFormData("attachment", downloadFileName);
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if (isNull(form)) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NOT_FOUND);
        }
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间
        queryMap.put("q_erpNo", form.getErpNo());//erpNo
        queryMap.put("q_orderNo", form.getOrderNo());//单号

        if (isNull(queryMap.get("q_endtime"))) {
            queryMap.put("q_endtime", DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));//默认当前时间结束
        }

        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<WaitReceiveTesterBean>> serviceResult = bdReceivingService.exportWaitReceiveTesterReportExcel(queryMap);
        if (!serviceResult.getSuccess()) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<WaitReceiveTesterBean> reportList = serviceResult.getResult();
        if (isNull(reportList) || reportList.size() == 0) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NO_CONTENT);
        }

        ExportExcel<WaitReceiveTesterBean> ex = new ExportExcel<>();
        String[] excelHeaders = {"PKG_NO", "品牌", "发货类型", "发货方店号", "发货方门店名称", "收货方品牌使用店号", "收货方门店号", "收货方门店名称", "收货方门店属性", "发货总件数", "发货时间"};

        String starttime = "";
        OutputStream out = new FileOutputStream(targetDirectory);
        if (isNull(queryMap.get("q_starttime"))) {
            starttime = "noTime";
        } else {
            starttime = DateUtils.dateToStr(DateUtils.strToDate(queryMap.get("q_starttime").toString(), "yyyy-mm-dd"), "mmdd");
        }
        String endtime = DateUtils.dateToStr(DateUtils.strToDate(queryMap.get("q_endtime").toString(), "yyyy-mm-dd"), "mmdd");
        String title = "待收货报表(" + starttime + "-" + endtime + ")";
        ex.exportExcel(title, excelHeaders, reportList, out);
        out.close();
        File file = new File(targetDirectory);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.OK);
    }

    @SystemControllerLog(module = "报表管理", businessDesc = "根据条件获取综合库存报表列表")
    @RequestMapping(value = "getStockReportList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getStockReportList(@RequestBody FormReportForm form) {
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (isNull(form.getPageIndex())) form.setPageIndex(1);
        if (isNull(form.getPageSize())) form.setPageSize(10);

        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间

        if (isNull(queryMap.get("q_starttime"))) {
            queryMap.put("q_starttime", DateUtils.initDateStrByMonthFirstDay());//默认当前月的1号开始
        }
        if (isNull(queryMap.get("q_endtime"))) {
            queryMap.put("q_endtime", DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));//默认当前时间结束
        }

        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<StockReportBean>> serviceResult = bdFormService.getStockReportList(queryMap, pagerInfo);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        form.setTotalPage(pagerInfo.getTotalPage());

        dataMap.put("form", form);
        dataMap.put("stockReportList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @RequestMapping(value = "stockReportDownload.json", method = { RequestMethod.POST })
    public ResponseEntity<byte[]> stockReportDownload(@RequestBody FormReportForm form, HttpServletRequest request) throws IOException {
        // 获取物理路径
        String targetDirectory = request.getSession().getServletContext().getRealPath("/");
        String contextPath = request.getContextPath().replace("/", "");
        String[] strArray = targetDirectory.split(contextPath);
        targetDirectory = strArray[0] + "/tct-tp";
        // 保存文件位置
        String extName = ".xls";
        String filename = "StockReport_" + DateUtils.dateToStr(new Date(), "yyyyMMddHHmmss") + extName;
        String path = "/excel/" + filename;

        targetDirectory = targetDirectory + path;
        // 判断路径是否存在，如果不存在就创建一个
        File filePath = new File(targetDirectory);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        HttpHeaders httpHeaders = new HttpHeaders();//http头信息
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");//设置编码
        httpHeaders.setContentDispositionFormData("attachment", downloadFileName);
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if (isNull(form)) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NOT_FOUND);
        }
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间

        if (isNull(queryMap.get("q_starttime"))) {
            queryMap.put("q_starttime", DateUtils.initDateStrByMonthFirstDay());//默认当前月的1号开始
        }
        if (isNull(queryMap.get("q_endtime"))) {
            queryMap.put("q_endtime", DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));//默认当前时间结束
        }

        String[] storeNoArr = getStoreNoArr();
        queryMap.put("q_storeNoArr", storeNoArr);//搜索门店号
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<StockReportBean>> serviceResult = bdFormService.exportStockReportExcel(queryMap);
        if (!serviceResult.getSuccess()) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<StockReportBean> reportList = serviceResult.getResult();
        if (isNull(reportList) || reportList.size() == 0) {
            return new ResponseEntity<byte[]>(null, httpHeaders, HttpStatus.NO_CONTENT);
        }

        ExportExcel<StockReportBean> ex = new ExportExcel<>();

        OutputStream out = new FileOutputStream(targetDirectory);
        String starttime = DateUtils.dateToStr(DateUtils.strToDate(queryMap.get("q_starttime").toString(), "yyyy-mm-dd"), "mmdd");
        String endtime = DateUtils.dateToStr(DateUtils.strToDate(queryMap.get("q_endtime").toString(), "yyyy-mm-dd"), "mmdd");
        String title = "仓库综合报表(" + starttime + "-" + endtime + ")";
        ex.exportForStockSynthesizeReportExcel(title, reportList, out, "yyyy-MM-dd");
        out.close();
        File file = new File(targetDirectory);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.OK);
    }

}
