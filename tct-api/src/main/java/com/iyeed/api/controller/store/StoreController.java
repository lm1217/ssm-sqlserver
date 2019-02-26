package com.iyeed.api.controller.store;

import com.iyeed.api.controller.BaseController;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.core.common.emuns.RespCode;
import com.iyeed.core.common.model.AjaxResponse;
import com.iyeed.core.entity.excel.ReceivedExcel;
import com.iyeed.core.entity.excel.StoreExcel;
import com.iyeed.core.entity.store.MdStore;
import com.iyeed.core.entity.store.vo.GetStoreListBean;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.system.SystemUserBrand;
import com.iyeed.core.entity.system.SystemUserStore;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.excel.ImportExcelFileUtil;
import com.iyeed.core.utils.Identities;
import com.iyeed.service.store.IMdStoreService;
import com.iyeed.service.user.IMdUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/16 16:40
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600) //解决跨域问题
@RequestMapping(value = "api/store")
public class StoreController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(StoreController.class);

    @SystemControllerLog(module = "表单管理", businessDesc = "获取门店列表")
    @RequestMapping(value = "getStoreList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getStoreList() {
        SystemUser systemUser = this.getSystemUser();
        MdStore store = null;
        if (systemUser.getUserType() == 1) {
            store = mdStoreService.getMdStoreByStoreNo(systemUser.getUserNo()).getResult();
        } else if (systemUser.getUserType() == 3 || systemUser.getUserType() == 4) {
            SystemUserStore userStore = systemUserStoreService.getSystemUserStoreByUserNo(systemUser.getUserNo()).getResult();
            store = mdStoreService.getMdStoreByStoreNo(userStore.getStoreNo()).getResult();
        } else {
            return AjaxResponse.failure("非门店帐号不允许此操作");
        }

        ServiceResult<List<GetStoreListBean>> serviceResult = mdStoreService.getStoreListByBrandNo(store.getBrandNo());

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("storeList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "根据品牌获取门店列表")
    @RequestMapping(value = "getBrandStoreList.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse getBrandStoreList() {
        SystemUser systemUser = this.getSystemUser();
        SystemUserBrand systemUserBrand = systemUserService.getSystemUserBrandByUsername(systemUser.getUsername()).getResult();
        String brandNo = null;
        if (!isNull(systemUserBrand)) {
            brandNo = systemUserBrand.getBrandNo();
        } else {
            return AjaxResponse.failure(RespCode.FAILED, "非法操作");
        }

        ServiceResult<List<GetStoreListBean>> serviceResult = mdStoreService.getBrandStoreListByBrandNo(brandNo);

        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("storeList", serviceResult.getResult());
        return AjaxResponse.success(dataMap);
    }

    @SystemControllerLog(module = "表单管理", businessDesc = "门店额外数据Excel上传导入")
    @RequestMapping(value = "storeExcelUpload.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse storeExcelUpload(@RequestParam MultipartFile file, HttpServletRequest request) {
        if (file == null && StringUtils.isNotBlank(file.getOriginalFilename())) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        SystemUser systemUser = this.getSystemUser();
        if (systemUser.getUserType() != 5) {
            return AjaxResponse.failure("非品牌帐号不能进行此操作");
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
        String path = "/uploads/store/excel/" + Identities.uuid2() + extName;

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

        StoreExcel storeExcel = new StoreExcel();
        List<Map<String, Object>> dataFromExcel = ImportExcelFileUtil.getDataFromExcel(targetDirectory, storeExcel);
        ServiceResult<Integer> serviceResult = importExcelService.updateStore(dataFromExcel);
        if (!serviceResult.getSuccess()) {
            return AjaxResponse.failure(RespCode.FAILED, serviceResult.getMessage());
        }
        return AjaxResponse.success("处理完成");
    }
}
