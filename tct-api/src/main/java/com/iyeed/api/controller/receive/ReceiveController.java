package com.iyeed.api.controller.receive;

import com.iyeed.api.controller.BaseController;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.core.common.emuns.RespCode;
import com.iyeed.core.common.model.AjaxResponse;
import com.iyeed.core.entity.excel.ReceiveTypeExcel;
import com.iyeed.core.entity.excel.ReceivedExcel;
import com.iyeed.core.entity.receive.BdReceiving;
import com.iyeed.core.entity.receive.BdReceivingSku;
import com.iyeed.core.entity.receive.vo.GetReceivingListForm;
import com.iyeed.core.entity.receive.vo.UpdateReceiveForm;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.entity.user.MdUserStore;
import com.iyeed.core.excel.ImportExcelFileUtil;
import com.iyeed.core.utils.CommonUtil;
import com.iyeed.core.utils.Identities;
import com.iyeed.service.receive.IBdReceivingService;
import com.iyeed.service.receive.IBdReceivingSkuService;
import com.iyeed.service.store.IMdStoreService;
import com.iyeed.service.user.IMdUserService;
import com.iyeed.service.user.IMdUserStoreService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:收货管理
 *
 * @Auther guanghua.deng
 * @Date 2018/8/14 17:26
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600) //解决跨域问题
@RequestMapping(value = "api/receive")
public class ReceiveController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ReceiveController.class);

    /**
     * 方法描述:通过erpNo, orderNo, status, starttime, endtime
     *        分页获取收货列表信息
     *
     * @param
     * @Author guanghua.deng
     * @Date 2018/8/15 12:07
     */
    @SystemControllerLog(module = "收货管理", businessDesc = "根据条件获取收货列表")
    @RequestMapping(value = "getReceivingList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getReceivingList(@RequestBody GetReceivingListForm form) {
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }
        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());
        String[] storeNoArr = getStoreNoArr();
        SystemUser systemUser = this.getSystemUser();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_status", String.valueOf(form.getStatus()));//收货类型,默认1.待收货
//        queryMap.put("q_erpNo", form.getErpNo());//搜索ERP_NO
        queryMap.put("q_pkgNo", form.getErpNo());//搜索ERP_NO
        queryMap.put("q_orderNo", form.getOrderNo());//搜索ORDER_NO
        queryMap.put("q_storeNo", form.getStoreNo());//收货门店
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间
        queryMap.put("q_storeNoArr", storeNoArr);
        if (systemUser.getUserType() == 3 || systemUser.getUserType() == 4) {
            queryMap.put("q_storeNoTS", systemUser.getUserNo());
        }
        if (form.getStatus() == 2 && systemUser.getUserType() != 0 && systemUser.getUserType() != 2 && systemUser.getUserType() != 5) {
            queryMap.put("q_userNo", systemUser.getUserNo());
        }
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        ServiceResult<List<BdReceiving>> serviceResult = bdReceivingService.getBdReceivingList(queryMap, pagerInfo);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        form.setTotalPage(pagerInfo.getTotalPage());

        dataMap.put("form", form);
        dataMap.put("bdReceivingList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    /**
     * 方法描述:通过ReceiveId获取收货信息明细（包含SKU列表信息）
     *
     * @param
     * @Author guanghua.deng
     * @Date 2018/8/15 12:09
     */
    @SystemControllerLog(module = "收货管理", businessDesc = "根据Id获取收货详情")
    @RequestMapping(value = "getReceivingDetailsById.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getReceivingDetailsById(@RequestParam Integer id) {
        if (isNull(id)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }
        Map<String, Object> dataMap = new HashMap<>();
        ServiceResult<BdReceiving> serviceResult = bdReceivingService.getBdReceivingById(id);
        dataMap.put("bdReceiving", serviceResult.getResult());
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        ServiceResult<List<BdReceivingSku>> serviceResultList = bdReceivingSkuService.getBdReceivingSkuListByErpNo(serviceResult.getResult().getErpNo());
        if (!serviceResultList.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResultList.getMessage());
        }
        dataMap.put("bdReceivingSkuList", serviceResultList.getResult());
        return AjaxResponse.success(dataMap);
    }

    /**
     * 方法描述:确认收货，更新收货信息明细（包含SKU列表信息）
     *
     * @param
     * @Author guanghua.deng
     * @Date 2018/8/15 12:09
     */
    @SystemControllerLog(module = "收货管理", businessDesc = "确认收货-更新收货信息")
    @RequestMapping(value = "updateReceiving.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse updateReceiving(@RequestBody UpdateReceiveForm form) {
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }
        SystemUser systemUser = this.getSystemUser();
        if (systemUser.getUserType() == 0 || systemUser.getUserType() == 2 || systemUser.getUserType() == 5) {
            return AjaxResponse.failure("非门店帐号不能进行此操作");
        }
        if (form.getBdReceivingSkuList() == null || form.getBdReceivingSkuList().isEmpty()) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        form.setUserNo(systemUser.getUserNo());
        form.setUserType(systemUser.getUserType());
        form.setStatus(2);// 确认收货后变更收货状态(1.待收货 2.已收货)
        ServiceResult<Integer> serviceResult = bdReceivingService.updateBdReceiving(form);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        return AjaxResponse.success();
    }

    @SystemControllerLog(module = "收货管理", businessDesc = "根据门店号获取待收货总数")
    @RequestMapping(value = "getReceiveCount.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getReceiveCount(String storeNo) {
        String[] storeNoArr = getStoreNoArr();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_status", String.valueOf(1));//收货类型,默认1.待收货
        queryMap.put("q_storeNo", storeNo);//storeNo
        queryMap.put("q_storeNoArr", storeNoArr);
        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);
        SystemUser systemUser = this.getSystemUser();
        if (systemUser.getUserType() == 3 || systemUser.getUserType() == 4) {
            queryMap.put("q_storeNoTS", systemUser.getUserNo());
        }
        ServiceResult<Integer> serviceResult = bdReceivingService.getBdReceivingListCount(queryMap);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("total", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @SystemControllerLog(module = "收货管理", businessDesc = "收货Excel上传导入处理已取消收货单")
    @RequestMapping(value = "receiveExcelUpload.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse receiveExcelUpload(@RequestParam MultipartFile file, HttpServletRequest request) {
        if (file == null && StringUtils.isNotBlank(file.getOriginalFilename())) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        SystemUser systemUser = this.getSystemUser();

        // 获取物理路径
        String targetDirectory = request.getSession().getServletContext().getRealPath("/");
        String contextPath = request.getContextPath().replace("/", "");
        String[] strArray = targetDirectory.split(contextPath);
        targetDirectory = strArray[0] + "/tct-tp";

        // 截取文件的扩展名（如.jpg）
        String oriName = file.getOriginalFilename();
        String extName = oriName.substring(oriName.lastIndexOf("."));

        // 保存文件位置
        String path = "/uploads/receive/excel/" + Identities.uuid2() + extName;

        targetDirectory = targetDirectory + path;
        // 判断路径是否存在，如果不存在就创建一个
        File filePath = new File(targetDirectory);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        try {
            log.info("上传文件开始: " + System.currentTimeMillis());
            file.transferTo(new File(targetDirectory));
            log.info("上传文件结束: " + System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        ReceivedExcel receivedExcel = new ReceivedExcel();
        List<Map<String, Object>> dataFromExcel = ImportExcelFileUtil.getDataFromExcel(targetDirectory, receivedExcel);
        ServiceResult<Integer> serviceResult = importExcelService.updateReceiving(dataFromExcel, systemUser.getUsername());
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        return AjaxResponse.success("处理完成");
    }

    @SystemControllerLog(module = "收货管理", businessDesc = "收货Excel上传导入处理更新收货单类型")
    @RequestMapping(value = "receiveTypeExcelUpload.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse receiveTypeExcelUpload(@RequestParam MultipartFile file, HttpServletRequest request) {
        if (file == null && StringUtils.isNotBlank(file.getOriginalFilename())) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        // 获取物理路径
        String targetDirectory = request.getSession().getServletContext().getRealPath("/");
        String contextPath = request.getContextPath().replace("/", "");
        String[] strArray = targetDirectory.split(contextPath);
        targetDirectory = strArray[0] + "/tct-tp";

        // 截取文件的扩展名（如.jpg）
        String oriName = file.getOriginalFilename();
        String extName = oriName.substring(oriName.lastIndexOf("."));

        // 保存文件位置
        String path = "/uploads/receive/excel/" + Identities.uuid2() + extName;

        targetDirectory = targetDirectory + path;
        // 判断路径是否存在，如果不存在就创建一个
        File filePath = new File(targetDirectory);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        try {
            log.info("上传文件开始: " + System.currentTimeMillis());
            file.transferTo(new File(targetDirectory));
            log.info("上传文件结束: " + System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        ReceiveTypeExcel receiveTypeExcel = new ReceiveTypeExcel();
        List<Map<String, Object>> dataFromExcel = ImportExcelFileUtil.getDataFromExcel(targetDirectory, receiveTypeExcel);
        ServiceResult<Integer> serviceResult = importExcelService.updateTypeReceiving(dataFromExcel);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        return AjaxResponse.success("处理完成");
    }
}
