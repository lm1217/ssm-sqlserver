package com.iyeed.api.controller.form;

import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.api.controller.BaseController;
import com.iyeed.api.controller.common.emuns.RespCode;
import com.iyeed.api.controller.common.model.AjaxResponse;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.entity.express.MdExpress;
import com.iyeed.core.entity.form.BdForm;
import com.iyeed.core.entity.form.BdFormImage;
import com.iyeed.core.entity.form.BdFormSku;
import com.iyeed.core.entity.form.vo.*;
import com.iyeed.core.entity.sku.MdSku;
import com.iyeed.core.entity.store.MdStore;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.utils.CommonUtil;
import com.iyeed.service.express.IMdExpressService;
import com.iyeed.service.form.IBdFormDisposeService;
import com.iyeed.service.form.IBdFormImageService;
import com.iyeed.service.form.IBdFormService;
import com.iyeed.service.form.IBdFormSkuService;
import com.iyeed.service.sku.IMdSkuService;
import com.iyeed.service.store.IMdStoreService;
import com.iyeed.service.user.IMdUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
 * @Date 2018/8/15 16:04
 */
@Controller
@RequestMapping(value = "api/form")
public class FormController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(FormController.class);

    @Resource
    private IBdFormService bdFormService;
    @Resource
    private IBdFormSkuService bdFormSkuService;
    @Resource
    private IBdFormImageService bdFormImageService;
    @Resource
    private IBdFormDisposeService bdFormDisposeService;
    @Resource
    private IMdExpressService mdExpressService;
    @Resource
    private IMdSkuService mdSkuService;
    @Resource
    private IMdStoreService mdStoreService;
    @Resource
    private IMdUserService mdUserService;

    @SystemControllerLog(module = "表单管理", businessDesc = "初始化表单申请")
    @RequestMapping(value = "saveFormInit.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveFormInit(@RequestBody SaveApplyForm form) {
        if (form == null) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (form.getApplyNo().isEmpty() || form.getApplyDate().isEmpty()
                || form.getApplicant().isEmpty() || form.getApplicantTel().isEmpty()
                || form.getStoreNo().isEmpty() || form.getStoreName().isEmpty()) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        form.setFormType(1);//库存初始化

        if (form.getFormSkuList().isEmpty()) {
            return AjaxResponse.failure("申请的SKU列表为空");
        } else {
            for (BdFormSku sku : form.getFormSkuList()) {
                if (sku.getStockDepotTotal() == null || sku.getStockCounterTotal() == null) {
                    return AjaxResponse.failure("申请数量不能为空");
                } else if (sku.getStockDepotTotal() < 0 || sku.getStockCounterTotal() < 0) {
                    return AjaxResponse.failure("申请数量不能为负数");
                } else if (sku.getStockDepotTotal() == 0 && sku.getStockCounterTotal() == 0) {
                    return AjaxResponse.failure("申请数量不能同时为0");
                }
            }
        }

        return saveForm(form);
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "销毁表单申请")
    @RequestMapping(value = "saveFormDestroy.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveFormDestroy(@RequestBody SaveApplyForm form) {
        if (form == null) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (form.getApplyNo().isEmpty() || form.getApplyDate().isEmpty()
                || form.getApplicant().isEmpty() || form.getApplicantTel().isEmpty()
                || form.getStoreNo().isEmpty() || form.getStoreName().isEmpty()) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        form.setFormType(2);//销毁表单

        if (isNull(form.getDestroyType())) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (form.getFormSkuList().isEmpty()) {
            return AjaxResponse.failure("申请的SKU列表为空");
        } else {
            for (BdFormSku sku : form.getFormSkuList()) {
                if (sku.getChangeTotal() <= 0) {
                    return AjaxResponse.failure("销毁数量参数错误");
                } else if (sku.getChangeTotal() > sku.getStockCounterTotal()) {
                    return AjaxResponse.failure("销毁数量超出柜面可用数量");
                }
            }
        }

        return saveForm(form);
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "调拨表单申请")
    @RequestMapping(value = "saveFormAllot.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveFormAllot(@RequestBody SaveApplyForm form) {
        if (form == null) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (form.getApplyNo().isEmpty() || form.getApplyDate().isEmpty()
                || form.getApplicant().isEmpty() || form.getApplicantTel().isEmpty()
                || form.getStoreNo().isEmpty() || form.getStoreName().isEmpty()) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        form.setFormType(3);//调拨表单

        if (form.getReceiveStoreNo().isEmpty()
                || form.getReceiveStoreName().isEmpty() || isNull(form.getAllotType())
                || form.getExpressCode().isEmpty() || form.getExpressName().isEmpty()
                || form.getExpressDate().isEmpty() || form.getOrderNo().isEmpty()) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (form.getFormSkuList().isEmpty()) {
            return AjaxResponse.failure("申请的SKU列表为空");
        } else {
            for (BdFormSku sku : form.getFormSkuList()) {
                if (sku.getChangeTotal() <= 0) {
                    return AjaxResponse.failure("调拨数量参数错误");
                } else if (sku.getChangeTotal() > sku.getStockDepotTotal()) {
                    return AjaxResponse.failure("销调拨数量超出店仓可用数量");
                }
            }
        }

        return saveForm(form);
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "异常表单申请")
    @RequestMapping(value = "saveFormException.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveFormException(@RequestBody SaveApplyForm form) {
        if (form == null) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (form.getApplyNo().isEmpty() || form.getApplyDate().isEmpty()
                || form.getApplicant().isEmpty() || form.getApplicantTel().isEmpty()
                || form.getStoreNo().isEmpty() || form.getStoreName().isEmpty()) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        form.setFormType(4);//异常表单

        if (isNull(form.getExceptionType())) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (form.getFormSkuList().isEmpty()) {
            return AjaxResponse.failure("申请的SKU列表为空");
        } else {
            for (BdFormSku sku : form.getFormSkuList()) {
                if (sku.getChangeTotal() <= 0) {
                    return AjaxResponse.failure("调整数量参数错误");
                } else if (sku.getChangeType() == 2 && sku.getChangeTotal() > sku.getStockDepotTotal()) {
                    return AjaxResponse.failure("调整数量超出店仓可用数量");
                }
            }
        }

        return saveForm(form);
    }

    private AjaxResponse saveForm(SaveApplyForm form) {
        Subject subject = SecurityUtils.getSubject();
        SystemUser systemUser = (SystemUser) subject.getPrincipal();
        MdUser user = mdUserService.getMdUserByUserNo(systemUser.getUserNo()).getResult();
        form.setUserName(user.getUserName());
        form.setUserNo(user.getUserNo());
        ServiceResult<Integer> serviceResult = bdFormService.saveForm(form);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        MdStore store = mdStoreService.getMdStoreByStoreNo(form.getStoreNo()).getResult();
        return AjaxResponse.success("提交表单成功,等待" + store.getPost() + "审核");
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "获取快递公司列表")
    @RequestMapping(value = "getExpressList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getExpressList() {
        ServiceResult<List<MdExpress>> serviceResult = mdExpressService.getExpressList(null);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("expressList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "根据申请号获取处理步骤列表")
    @RequestMapping(value = "getDisposeListByApplyNo.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getDisposeListByApplyNo(@RequestParam String applyNo) {
        if (isNull(applyNo)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        ServiceResult<List<GetDisposeListBean>> serviceResult = bdFormDisposeService.getDisposeListByApplyNo(applyNo);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("disposeList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "根据条件获取处理表单列表")
    @RequestMapping(value = "getDisposeFormList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getDisposeFormList(@RequestBody GetDisposeFormListForm form) {

        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());

        Map<String, String> queryMap = new HashMap<>();
        if (!isNull(form.getFormType())) {
            if (form.getFormType() != 0)
                queryMap.put("q_formType", String.valueOf(form.getFormType()));//表单类型（）
        }
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        queryMap.put("q_applyNo", form.getApplyNo());//搜索申请号
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间

        if (form.getDisposeStatus() == 1) {
            queryMap.put("q_disposeUserNo", form.getUserNo());
        }

        queryMap.put("q_disposeStatus", String.valueOf(form.getDisposeStatus()));


        ServiceResult<List<GetDisposeFormListBean>> serviceResult = bdFormService.getBdFormList(queryMap, pagerInfo);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        form.setTotalPage(pagerInfo.getTotalPage());

        dataMap.put("form", form);
        dataMap.put("bdFormList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    /**
     * 方法描述:根据申请号获取处理订单详情
     *
     * @param
     * @Author guanghua.deng
     * @Date 2018/8/22 9:33
     */
    @SystemControllerLog(module = "表单管理", businessDesc = "根据表单ID获取表单详情")
    @RequestMapping(value = "getDisposeFormDetailsById.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getDisposeFormDetailsById(@RequestParam Integer id) {
        Map<String, Object> dataMap = new HashMap<>();
        ServiceResult<BdForm> serviceResult = bdFormService.getBdFormById(id);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        dataMap.put("bdForm", serviceResult.getResult());

        ServiceResult<List<BdFormSku>> serviceResultSkuList = bdFormSkuService.getBdFormSkuListByApplyNo(serviceResult.getResult().getApplyNo());
        if (!serviceResultSkuList.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResultSkuList.getMessage());
        }
        dataMap.put("bdFormSkuList", serviceResultSkuList.getResult());

        ServiceResult<List<BdFormImage>> serviceResultImageList = bdFormImageService.getBdFormImageListByApplyNo(serviceResult.getResult().getApplyNo());
        if (!serviceResultImageList.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResultImageList.getMessage());
        }
        dataMap.put("bdFormImageList", serviceResultImageList.getResult());

        ServiceResult<List<GetDisposeListBean>> serviceResultDisposeList = bdFormDisposeService.getDisposeListByApplyNo(serviceResult.getResult().getApplyNo());

        if (!serviceResultDisposeList.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResultDisposeList.getMessage());
        }
        dataMap.put("bdFormDisposeList", serviceResultDisposeList.getResult());

        return AjaxResponse.success(dataMap);
    }

    /**
     * 方法描述:处理申请表单（同意 or 拒绝）
     *
     * @param
     * @Author guanghua.deng
     * @Date 2018/8/22 9:57
     */
    @SystemControllerLog(module = "表单管理", businessDesc = "处理申请表单(同意/拒绝)")
    @RequestMapping(value = "execDisposeFormDetails.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse execDisposeFormDetails(@RequestBody ExecDisposeForm form) {
        ServiceResult<BdForm> serviceResult = bdFormService.getBdFormById(form.getId());

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        BdForm bdForm = serviceResult.getResult();

        if (bdForm.getDisposeStatus() == 0) {
            return AjaxResponse.failure("处理流程已经结束");
        }
        if (!form.getUserNo().equals(bdForm.getDisposeUserNo())) {
            return AjaxResponse.failure("操作人与指定人员不相符");
        }
        if (form.getType() != 1 && form.getType() != 2) {
            return AjaxResponse.failure("错误的操作");
        }

        ServiceResult<Integer> result = bdFormService.execDisposeFormDetails(bdForm, form);
        if (!result.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, result.getMessage());
        }

        return AjaxResponse.success();
    }

    @RequestMapping(value = "getDisposeCount.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getDisposeCount(@RequestParam String storeNo, @RequestParam String userNo) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("q_disposeStatus", "2");//处理中
        queryMap.put("q_storeNo", storeNo);//门店号
        //获取处理中的数量
        ServiceResult<Integer> disposeIngResult = bdFormService.getBdFormListCount(queryMap);
        if (!disposeIngResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, disposeIngResult.getMessage());
        }

        queryMap.put("q_disposeStatus", "1");//待处理
        queryMap.put("q_disposeUserNo", userNo);//用户编号

        ServiceResult<Integer> disposeWaitResult = bdFormService.getBdFormListCount(queryMap);
        if (!disposeWaitResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, disposeWaitResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("ingTotal", disposeIngResult.getResult());
        dataMap.put("waitTotal", disposeWaitResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "根据品牌号获取SKU列表")
    @RequestMapping(value = "getSkuListByBrandNo.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getSkuListByBrandNo(@RequestParam String brandNo) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("brandNo", brandNo);//品牌号

        ServiceResult<List<MdSku>> serviceResult = mdSkuService.getSkuList(queryMap);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("skuList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }
}
