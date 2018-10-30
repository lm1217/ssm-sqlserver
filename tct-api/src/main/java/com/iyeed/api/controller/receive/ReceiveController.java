package com.iyeed.api.controller.receive;

import com.iyeed.api.controller.BaseController;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.core.common.emuns.RespCode;
import com.iyeed.core.common.model.AjaxResponse;
import com.iyeed.core.entity.receive.BdReceiving;
import com.iyeed.core.entity.receive.BdReceivingSku;
import com.iyeed.core.entity.receive.vo.GetReceivingListForm;
import com.iyeed.core.entity.receive.vo.UpdateReceiveForm;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.entity.user.MdUserStore;
import com.iyeed.core.utils.CommonUtil;
import com.iyeed.service.receive.IBdReceivingService;
import com.iyeed.service.receive.IBdReceivingSkuService;
import com.iyeed.service.store.IMdStoreService;
import com.iyeed.service.user.IMdUserService;
import com.iyeed.service.user.IMdUserStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_status", String.valueOf(form.getStatus()));//收货类型,默认1.待收货
        queryMap.put("q_erpNo", form.getErpNo());//搜索ERP_NO
        queryMap.put("q_orderNo", form.getOrderNo());//搜索ORDER_NO
        queryMap.put("q_storeNo", form.getStoreNo());//收货门店
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间
        queryMap.put("q_storeNoArr", storeNoArr);
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
        if (systemUser.getUserType() != 1) {
            return AjaxResponse.failure("非门店帐号不能进行此操作");
        }
        if (form.getBdReceivingSkuList() == null || form.getBdReceivingSkuList().isEmpty()) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

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
        ServiceResult<Integer> serviceResult = bdReceivingService.getBdReceivingListCount(queryMap);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("total", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }
}
