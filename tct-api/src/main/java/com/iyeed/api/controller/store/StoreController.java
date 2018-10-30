package com.iyeed.api.controller.store;

import com.iyeed.api.controller.BaseController;
import com.iyeed.core.ServiceResult;
import com.iyeed.core.annotation.SystemControllerLog;
import com.iyeed.core.common.emuns.RespCode;
import com.iyeed.core.common.model.AjaxResponse;
import com.iyeed.core.entity.store.MdStore;
import com.iyeed.core.entity.store.vo.GetStoreListBean;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.service.store.IMdStoreService;
import com.iyeed.service.user.IMdUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
}
