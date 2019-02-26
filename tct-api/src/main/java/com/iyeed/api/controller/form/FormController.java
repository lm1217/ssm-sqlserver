package com.iyeed.api.controller.form;

import com.iyeed.api.controller.BaseController;
import com.iyeed.core.ConstantsEJS;
import com.iyeed.core.PagerInfo;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.core.common.emuns.RespCode;
import com.iyeed.core.common.model.AjaxResponse;
import com.iyeed.core.entity.express.MdExpress;
import com.iyeed.core.entity.form.BdForm;
import com.iyeed.core.entity.form.BdFormImage;
import com.iyeed.core.entity.form.BdFormSku;
import com.iyeed.core.entity.form.vo.*;
import com.iyeed.core.entity.sku.MdSku;
import com.iyeed.core.entity.store.MdStore;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.system.SystemUserBrand;
import com.iyeed.core.entity.system.SystemUserStore;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.entity.user.MdUserStore;
import com.iyeed.core.mail.MailInfo;
import com.iyeed.core.mail.MailSender;
import com.iyeed.core.utils.CommonUtil;
import com.iyeed.service.express.IMdExpressService;
import com.iyeed.service.form.IBdFormDisposeService;
import com.iyeed.service.form.IBdFormImageService;
import com.iyeed.service.form.IBdFormService;
import com.iyeed.service.form.IBdFormSkuService;
import com.iyeed.service.sku.IMdSkuService;
import com.iyeed.service.store.IMdStoreService;
import com.iyeed.service.user.IMdUserService;
import com.iyeed.service.user.IMdUserStoreService;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
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
@CrossOrigin(origins = "*", maxAge = 3600) //解决跨域问题
@RequestMapping(value = "api/form")
public class FormController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(FormController.class);

    @SystemControllerLog(module = "表单管理", businessDesc = "初始化表单申请")
    @RequestMapping(value = "saveFormInit.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveFormInit(@RequestBody SaveApplyForm form) {
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (form.getApplyNo().isEmpty() || form.getApplyDate().isEmpty()
                || form.getApplicant().isEmpty() || form.getApplicantTel().isEmpty()
                || form.getStoreNo().isEmpty() || form.getStoreName().isEmpty()) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        form.setFormType(1);//库存初始化

        AjaxResponse checkSku = checkSkuList(form.getFormSkuList(), form.getFormType());
        if (checkSku.getCode() != 0) {
            return checkSku;
        }

        return saveForm(form);
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "销毁表单申请")
    @RequestMapping(value = "saveFormDestroy.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveFormDestroy(@RequestBody SaveApplyForm form) {
        if (isNull(form)) {
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

        if (form.getDestroyType() != 4) {//销毁单原因不为失窃的要检查是否有上传图片
            if (form.getFormImageList().isEmpty()) {
                return AjaxResponse.failure("上传图片列表为空");
            } else {
                for (BdFormImage image : form.getFormImageList()) {
                    if (image.getImageUrl().isEmpty()) {
                        return AjaxResponse.failure("上传图片Url为空");
                    }
                }
            }
        }

        AjaxResponse checkSku = checkSkuList(form.getFormSkuList(), form.getFormType());
        if (checkSku.getCode() != 0) {
            return checkSku;
        }

        return saveForm(form);
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "调拨表单申请")
    @RequestMapping(value = "saveFormAllot.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveFormAllot(@RequestBody SaveApplyForm form) {
        if (isNull(form)) {
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

        AjaxResponse checkSku = checkSkuList(form.getFormSkuList(), form.getFormType());
        if (checkSku.getCode() != 0) {
            return checkSku;
        }

        return saveForm(form);
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "异常表单申请")
    @RequestMapping(value = "saveFormException.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveFormException(@RequestBody SaveApplyForm form) {
        if (isNull(form)) {
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

        AjaxResponse checkSku = checkSkuList(form.getFormSkuList(), form.getFormType());
        if (checkSku.getCode() != 0) {
            return checkSku;
        }

        return saveForm(form);
    }

    private AjaxResponse saveForm(SaveApplyForm form) {
        SystemUser systemUser = this.getSystemUser();
        //0-admin 2-审批人 5-品牌帐号
        if (systemUser.getUserType() == 0 || systemUser.getUserType() == 2 || systemUser.getUserType() == 5) {
            return AjaxResponse.failure("非门店帐号不能进行此操作");
        }
        form.setUserName(systemUser.getUserNo());
        form.setUserNo(systemUser.getUserNo());
        ServiceResult<BdForm> formResult = bdFormService.getBdFormByApplyNo(form.getApplyNo());
        if (!isNull(formResult.getResult())) {
            BdForm bdForm = formResult.getResult();
            if (bdForm.getDisposeStatus() > 0 && bdForm.getDisposeStatus() != 3 && bdForm.getDisposeResult() != 3) {
                return AjaxResponse.failure("非法操作，不得重复提交");
            }
        }
        ServiceResult<Integer> serviceResult = bdFormService.saveForm(form);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        // 多线程解决邮件发送超时阻塞主线程执行时间慢的问题
        Thread thread = new Thread() {
            public void run() {
                // 邮箱发送
                String storeNo = null;
                if (systemUser.getUserType() == 1) {
                    storeNo = systemUser.getUserNo();
                } else if (systemUser.getUserType() == 3 || systemUser.getUserType() == 4) {
                    SystemUserStore userStore = systemUserStoreService.getSystemUserStoreByUserNo(systemUser.getUserNo()).getResult();
                    if (!isNull(userStore)) {
                        storeNo = userStore.getStoreNo();
                    }
                }
                ServiceResult<MdStore> storeResult = mdStoreService.getMdStoreByStoreNo(storeNo);
                if (!storeResult.getSuccess() || isNull(storeResult.getResult())) {
                    log.error("邮件发送失败");
                }
                MdStore store = storeResult.getResult();
                ServiceResult<MdUser> userResult = mdUserService.getMdUserByUserNo(store.getUserNo());
                if (!userResult.getSuccess() || isNull(userResult.getResult())) {
                    log.error("邮件发送失败");
                }
                MdUser user = userResult.getResult();

                try {
                    MailSender mailSender = MailSender.getInstance();
                    MailInfo mailInfo = new MailInfo();
                    mailInfo.setNotifyTo(user.getUserEmail());
//                    mailInfo.setNotifyCc("112538201@qq.com");
                    String strHtml = "";
                    if (form.getFormType() == 1) {
                        //发送邮件-审批提醒-初始化
                        mailInfo.setSubject("[Tester Control Tower]库存初始化\"" + form.getApplyNo() + "\"审批");
                        strHtml = "尊敬的用户您好：<br/>" +
                                "&nbsp;&nbsp;&nbsp;&nbsp;门店“" + store.getStoreName() + "”，店号：" + store.getStoreNo() + "，品牌：" + store.getBrandName() + " 正在做tester库存初始化，需要您审批，" +
                                "请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
                    } else if (form.getFormType() == 2) {
                        //发送邮件-审批提醒-销毁单
                        mailInfo.setSubject("[Tester Control Tower]销毁申请单\"" + form.getApplyNo() + "\"审批");
                        strHtml = "尊敬的用户您好：<br/>" +
                                "&nbsp;&nbsp;&nbsp;&nbsp;门店“" + store.getStoreName() + "”，店号：" + store.getStoreNo() + "，品牌：" + store.getBrandName() + " 提交了tester销毁申请，需要您审批，" +
                                "请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
                    } else if (form.getFormType() == 3) {
                        //发送邮件-审批提醒-调拨单
                        mailInfo.setSubject("[Tester Control Tower]调拨申请单\"" + form.getApplyNo() + "\"审批");
                        strHtml = "尊敬的用户您好：<br/>" +
                                "&nbsp;&nbsp;&nbsp;&nbsp;门店“" + store.getStoreName() + "”，店号：" + store.getStoreNo() + "，品牌：" + store.getBrandName() + " 提交了tester调拨申请，需要您审批，" +
                                "请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
                    } else if (form.getFormType() == 4) {
                        //发送邮件-审批提醒-异常单
                        mailInfo.setSubject("[Tester Control Tower]异常申请单\"" + form.getApplyNo() + "\"审批");
                        strHtml = "尊敬的用户您好：<br/>" +
                                "&nbsp;&nbsp;&nbsp;&nbsp;门店“" + store.getStoreName() + "”，店号：" + store.getStoreNo() + "，品牌：" + store.getBrandName() + " 提交了tester异常申请，需要您审批，" +
                                "请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
                    }
                    mailInfo.setContent(strHtml);
                    mailInfo.setAttachFileNames(new String[]{});//添加附件
                    mailSender.sendHtmlMail(mailInfo, 3);
                } catch (Exception e) {
                    log.error("邮件发送失败", e.getMessage());
                }
            }
        };
        thread.start();

        return AjaxResponse.success("提交表单成功,等待门店主管审批");
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "审批退回")
    @RequestMapping(value = "backFormDispose.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse backFormDispose(@RequestBody ExecDisposeForm form) {
        SystemUser systemUser = this.getSystemUser();
        ServiceResult<BdForm> serviceResult = bdFormService.getBdFormById(form.getId());

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        BdForm bdForm = serviceResult.getResult();

        if (bdForm.getIsBack() == 1) {
            return AjaxResponse.failure("该处理流程不能退回");
        }
        if (systemUser.getUserType() != 5 && !form.getUserNo().equals(bdForm.getDisposeUserNo())) {
            return AjaxResponse.failure("操作人与指定人员不相符");
        }
        if (form.getType() != 3) {
            return AjaxResponse.failure("错误的操作");
        }

        if (systemUser.getUserType() == 5) {
            form.setIsBrandAdmin(1);//品牌管理帐号标识
        }

        ServiceResult<Integer> result = bdFormService.backFormDispose(bdForm, form);
        if (!result.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, result.getMessage());
        }

        return AjaxResponse.success();
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
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }
        SystemUser systemUser = this.getSystemUser();
        String[] storeNoArr = getStoreNoArr();
        PagerInfo pagerInfo = new PagerInfo(form.getPageSize(), form.getPageIndex());

        Map<String, Object> queryMap = new HashMap<>();
        if (!isNull(form.getFormType())) {
            if (form.getFormType() != 0)
                queryMap.put("q_formType", String.valueOf(form.getFormType()));//表单类型（）
        }
        queryMap.put("q_storeNo", form.getStoreNo());//搜索门店号
        queryMap.put("q_applyNo", form.getApplyNo());//搜索申请号
        queryMap.put("q_starttime", CommonUtil.startTime(form.getStarttime()));//搜索开始时间
        queryMap.put("q_endtime", CommonUtil.endTime(form.getEndtime()));//搜索结束时间


        if (form.getDisposeStatus() == 1 && systemUser.getUserType() != 5) {
            queryMap.put("q_disposeUserNo", form.getUserNo());
        }

        if (systemUser.getUserType() != 0 && systemUser.getUserType() != 2 && systemUser.getUserType() != 5) {
            queryMap.put("q_userNo", systemUser.getUserNo());
        }

        queryMap.put("q_storeNoArr", storeNoArr);
        queryMap.put("q_disposeStatus", String.valueOf(form.getDisposeStatus()));

        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);

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
        BdForm bdForm = serviceResult.getResult();
        dataMap.put("bdForm", bdForm);


        ServiceResult<List<BdFormSku>> serviceResultSkuList = bdFormSkuService.getBdFormSkuListByApplyNo(bdForm.getApplyNo());
        if (!serviceResultSkuList.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResultSkuList.getMessage());
        }
        dataMap.put("bdFormSkuList", serviceResultSkuList.getResult());

        ServiceResult<List<BdFormImage>> serviceResultImageList = bdFormImageService.getBdFormImageListByApplyNo(bdForm.getApplyNo(), 1);
        if (!serviceResultImageList.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResultImageList.getMessage());
        }
        dataMap.put("bdFormImageList", serviceResultImageList.getResult());

        serviceResultImageList = bdFormImageService.getBdFormImageListByApplyNo(bdForm.getApplyNo(), 2);
        if (!serviceResultImageList.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResultImageList.getMessage());
        }
        dataMap.put("bdFormSuperiorImageList", serviceResultImageList.getResult());

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
        SystemUser systemUser = this.getSystemUser();
        ServiceResult<BdForm> serviceResult = bdFormService.getBdFormById(form.getId());

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        BdForm bdForm = serviceResult.getResult();

        if (bdForm.getDisposeStatus() == 0) {
            return AjaxResponse.failure("处理流程已经结束");
        }

        if (systemUser.getUserType() != 5 && !form.getUserNo().equals(bdForm.getDisposeUserNo())) {
            return AjaxResponse.failure("操作人与指定人员不相符");
        }

        if (form.getType() != 1 && form.getType() != 2) {
            return AjaxResponse.failure("错误的操作");
        }

        if (systemUser.getUserType() == 5) {
            form.setIsBrandAdmin(1);//品牌管理帐号标识
        }

        ServiceResult<Integer> result = bdFormService.execDisposeFormDetails(bdForm, form);
        if (!result.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, result.getMessage());
        }

        return AjaxResponse.success();
    }

    /**
     * 方法描述:批量处理申请表单（同意 or 拒绝）
     *
     * @param
     * @Author guanghua.deng
     * @Date 2018/8/22 9:57
     */
    @SystemControllerLog(module = "表单管理", businessDesc = "批量处理申请表单(同意/拒绝)")
    @RequestMapping(value = "execDisposeBatchForm.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse execDisposeBatchForm(@RequestBody ExecDisposeForm form) {
        SystemUser systemUser = this.getSystemUser();
        if (form.getType() != 1 && form.getType() != 2) {
            return AjaxResponse.failure("错误的操作");
        }
        String[] idArr = form.getIdStr().split(",");
        if (!isNull(idArr)) {
            for (String str : idArr) {
                form.setId(Integer.parseInt(str));
                execDisposeFormDetails(form);
            }
        }
        return AjaxResponse.success();
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "根据门店号获取待处理与处理中的角标总数")
    @RequestMapping(value = "getDisposeCount.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getDisposeCount(@RequestParam String storeNo, @RequestParam String userNo) {
        String[] storeNoArr = getStoreNoArr();
        SystemUser systemUser = this.getSystemUser();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("q_disposeStatus", "2");//处理中
        queryMap.put("q_storeNoArr", storeNoArr);

        if (systemUser.getUserType() != 0 && systemUser.getUserType() != 2 && systemUser.getUserType() != 5) {
            queryMap.put("q_userNo", systemUser.getUserNo());
        }

        String brandNo = this.getBrandNo();
        queryMap.put("q_brandNo", brandNo);

        //获取处理中的数量
        ServiceResult<Integer> disposeIngResult = bdFormService.getBdFormListCount(queryMap);
        if (!disposeIngResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, disposeIngResult.getMessage());
        }

        queryMap.put("q_disposeStatus", "1");//待处理

        if (systemUser.getUserType() != 5) {
            queryMap.put("q_disposeUserNo", userNo);//用户编号
        }

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

    @SystemControllerLog(module = "表单管理", businessDesc = "删除保存或退回的申请单")
    @RequestMapping(value = "delForm.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse delForm(Integer id) {
        if (isNull(id)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }
        SystemUser systemUser = this.getSystemUser();
        if (systemUser.getUserType() == 0 || systemUser.getUserType() == 2 || systemUser.getUserType() == 5) {
            return AjaxResponse.failure("非门店帐号不能进行此操作");
        }

        BdForm bdForm = bdFormService.getBdFormById(id).getResult();

        if (isNull(bdForm) || isNull(bdForm.getId())) {
            return AjaxResponse.failure("该表单不存在");
        } else if (bdForm.getIsBack() != 1 && bdForm.getDisposeStatus() != 3) {
            return AjaxResponse.failure("该表单不能删除");
        }

        ServiceResult<Integer> serviceResult = bdFormService.delBdForm(bdForm);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        return AjaxResponse.success();
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "初始化表单保存")
    @RequestMapping(value = "saveLocalFormInit.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveLocalFormInit(@RequestBody SaveApplyForm form) {
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (form.getApplyNo().isEmpty() || form.getApplyDate().isEmpty()) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        form.setFormType(1);//库存初始化

        return saveLocalForm(form);
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "销毁表单保存")
    @RequestMapping(value = "saveLocalFormDestroy.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveLocalFormDestroy(@RequestBody SaveApplyForm form) {
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (form.getApplyNo().isEmpty() || form.getApplyDate().isEmpty()) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        form.setFormType(2);//销毁表单

        return saveLocalForm(form);
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "调拨表单保存")
    @RequestMapping(value = "saveLocalFormAllot.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveLocalFormAllot(@RequestBody SaveApplyForm form) {
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (form.getApplyNo().isEmpty() || form.getApplyDate().isEmpty()) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        form.setFormType(3);//调拨表单

        return saveLocalForm(form);
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "异常表单保存")
    @RequestMapping(value = "saveLocalFormException.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse saveLocalFormException(@RequestBody SaveApplyForm form) {
        if (isNull(form)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (form.getApplyNo().isEmpty() || form.getApplyDate().isEmpty()) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        form.setFormType(4);//异常表单

        return saveLocalForm(form);
    }

    private AjaxResponse saveLocalForm(SaveApplyForm form) {
        SystemUser systemUser = this.getSystemUser();
        if (systemUser.getUserType() == 0 || systemUser.getUserType() == 2 || systemUser.getUserType() == 5) {
            return AjaxResponse.failure("非门店帐号不能进行此操作");
        }

        form.setUserName(systemUser.getUserNo());
        form.setUserNo(systemUser.getUserNo());
        ServiceResult<Integer> serviceResult = bdFormService.saveLocalForm(form);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        ServiceResult<BdForm> bdFormServiceResult = bdFormService.getBdFormByApplyNo(form.getApplyNo());

        if (!bdFormServiceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, bdFormServiceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", bdFormServiceResult.getResult().getId());

        return AjaxResponse.success("保存表单成功", dataMap);
    }
}
