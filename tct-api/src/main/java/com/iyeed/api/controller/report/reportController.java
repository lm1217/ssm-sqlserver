package com.iyeed.api.controller.report;

import com.iyeed.api.controller.BaseController;
import com.iyeed.api.controller.common.emuns.RespCode;
import com.iyeed.api.controller.common.model.AjaxResponse;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.core.entity.stock.vo.GetStockInvReportListBean;
import com.iyeed.core.entity.stock.vo.GetStockInvReportListForm;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.service.stock.IBdStockInvService;
import com.iyeed.service.user.IMdUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "api/report")
public class reportController extends BaseController {
    @Resource
    private IBdStockInvService bdStockInvService;
    @Resource
    private IMdUserService mdUserService;

    @SystemControllerLog(module = "报表管理", businessDesc = "根据条件获取库存报表列表")
    @RequestMapping(value = "getStockInvReportList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getStockInvReportList(@RequestBody GetStockInvReportListForm form) {
        if (form == null) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (isNull(form.getPageIndex())) form.setPageIndex(1);
        if (isNull(form.getPageSize())) form.setPageSize(10);

        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_skuCode", form.getSkuCode());//搜索SKU编码
        queryMap.put("q_skuName", form.getSkuName());//搜索SKU名称

        Subject subject = SecurityUtils.getSubject();
        SystemUser systemUser = (SystemUser) subject.getPrincipal();
        MdUser user = mdUserService.getMdUserByUserNo(systemUser.getUserNo()).getResult();
        if (isNull(user) || isNull(user.getStoreNo())) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }
        queryMap.put("storeNo", user.getStoreNo());//搜索门店号

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
}
