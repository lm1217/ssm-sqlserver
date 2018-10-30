package com.iyeed.api.controller.report;

import com.iyeed.api.controller.BaseController;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.core.common.emuns.RespCode;
import com.iyeed.core.common.model.AjaxResponse;
import com.iyeed.core.entity.form.vo.ExceptionReportBean;
import com.iyeed.core.entity.form.vo.FormReportForm;
import com.iyeed.core.entity.stock.vo.GetStockInvReportListBean;
import com.iyeed.core.entity.stock.vo.GetStockInvReportListForm;
import com.iyeed.core.entity.store.MdStore;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.entity.user.MdUserStore;
import com.iyeed.core.excel.ExportExcel;
import com.iyeed.core.utils.Identities;
import com.iyeed.service.form.IBdFormService;
import com.iyeed.service.stock.IBdStockInvService;
import com.iyeed.service.store.IMdStoreService;
import com.iyeed.service.user.IMdUserService;
import com.iyeed.service.user.IMdUserStoreService;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
@RequestMapping(value = "api/report")
public class ReportController extends BaseController {

    @SystemControllerLog(module = "报表管理", businessDesc = "根据条件获取库存报表列表")
    @RequestMapping(value = "getStockInvReportList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getStockInvReportList(@RequestBody GetStockInvReportListForm form) {
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
        String filename = "StockReport_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + extName;
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
        ServiceResult<List<GetStockInvReportListBean>> serviceResult = bdStockInvService.exportStockInvReportExcel(queryMap);
        if (!serviceResult.getSuccess()) {
            return null;
        }
        List<GetStockInvReportListBean> reportList = serviceResult.getResult();
        if (isNull(reportList) || reportList.size() == 0) {
            return null;
        }

        ExportExcel<GetStockInvReportListBean> ex = new ExportExcel<>();
        String[] excelHeaders = {"ID", "门店号", "门店名称", "品牌名称", "SKU_CODE", "SKU名称", "店仓库存", "店仓可用库存", "店仓可用库存冻结", "柜面库存", "柜面可用库存", "柜面可用库存冻结", "总库存"};

        OutputStream out = new FileOutputStream(targetDirectory);
        String title = "仓库报表";
        ex.exportExcel(title, excelHeaders, reportList, out);
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
        String filename = "StockReport_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + extName;
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
        ServiceResult<List<ExceptionReportBean>> serviceResult = bdFormService.exportExceptionReportExcel(queryMap);
        if (!serviceResult.getSuccess()) {
            return null;
        }
        List<ExceptionReportBean> reportList = serviceResult.getResult();
        if (isNull(reportList) || reportList.size() == 0) {
            return null;
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

}
