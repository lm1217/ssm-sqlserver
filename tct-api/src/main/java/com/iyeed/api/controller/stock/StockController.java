package com.iyeed.api.controller.stock;

import com.iyeed.api.controller.BaseController;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.core.common.emuns.RespCode;
import com.iyeed.core.common.model.AjaxResponse;
import com.iyeed.core.entity.stock.BdStockInvLog;
import com.iyeed.core.entity.stock.vo.GetBdStockInvLogListForm;
import com.iyeed.core.entity.stock.vo.GetStockInvSkuListBean;
import com.iyeed.core.entity.stock.vo.StockInvSkuForm;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.service.stock.IBdStockInvLogService;
import com.iyeed.service.stock.IBdStockInvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/16 14:54
 */
@Controller
@RequestMapping(value = "api/stock")
public class StockController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(StockController.class);

    @SystemControllerLog(module = "库存管理", businessDesc = "根据门店号获取库存SKU列表")
    @RequestMapping(value = "getSkuListByStoreNo.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getSkuListByStoreNo(@RequestParam String storeNo) {

        ServiceResult<List<GetStockInvSkuListBean>> serviceResult = bdStockInvService.getStockInvSkuListByStoreNo(storeNo);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("stockInvSkuList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @SystemControllerLog(module = "库存管理", businessDesc = "根据条件获取库存变更记录日志列表")
    @RequestMapping(value = "getBdStockInvLogList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getBdStockInvLogList(@RequestBody GetBdStockInvLogListForm form) {
        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        queryMap.put("q_skuCode", form.getSkuCode().toUpperCase());//搜索skuCode
        queryMap.put("q_type", form.getType());//搜索类型（1.店仓转柜面  2.柜面转店仓）
        ServiceResult<List<BdStockInvLog>> serviceResult = bdStockInvLogService.getBdStockInvLogList(queryMap, pagerInfo);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        form.setTotalPage(pagerInfo.getTotalPage());
        dataMap.put("form", form);
        dataMap.put("stockInvLogList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @SystemControllerLog(module = "库存管理", businessDesc = "店仓转柜面")
    @RequestMapping(value = "updateDepotToCounter.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse updateDepotToCounter(@RequestBody StockInvSkuForm skuForm) {
        SystemUser systemUser = this.getSystemUser();
        if (systemUser.getUserType() != 1) {
            return AjaxResponse.failure("非门店帐号不能进行此操作");
        }
        ServiceResult<Integer> serviceResult = bdStockInvService.updateDepotToCounter(skuForm);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        return AjaxResponse.success();
    }

    @SystemControllerLog(module = "库存管理", businessDesc = "柜面转库存")
    @RequestMapping(value = "updateCounterToDepot.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse updateCounterToDepot(@RequestBody StockInvSkuForm skuForm) {
        SystemUser systemUser = this.getSystemUser();
        if (systemUser.getUserType() != 1) {
            return AjaxResponse.failure("非门店帐号不能进行此操作");
        }
        ServiceResult<Integer> serviceResult = bdStockInvService.updateCounterToDepot(skuForm);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        return AjaxResponse.success();
    }
}
