package com.iyeed.api.controller;

import com.iyeed.core.StringUtil;
import com.iyeed.core.common.model.AjaxResponse;
import com.iyeed.core.entity.form.BdFormSku;
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.system.SystemUserBrand;
import com.iyeed.core.entity.system.SystemUserStore;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.entity.user.MdUserStore;
import com.iyeed.service.excel.IImportExcelService;
import com.iyeed.service.express.IMdExpressService;
import com.iyeed.service.form.IBdFormDisposeService;
import com.iyeed.service.form.IBdFormImageService;
import com.iyeed.service.form.IBdFormService;
import com.iyeed.service.form.IBdFormSkuService;
import com.iyeed.service.receive.IBdReceivingService;
import com.iyeed.service.receive.IBdReceivingSkuService;
import com.iyeed.service.sku.IMdSkuService;
import com.iyeed.service.stock.IBdStockInvLogService;
import com.iyeed.service.stock.IBdStockInvService;
import com.iyeed.service.store.IMdBrandService;
import com.iyeed.service.store.IMdStoreService;
import com.iyeed.service.system.*;
import com.iyeed.service.user.IMdUserService;
import com.iyeed.service.user.IMdUserStoreService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 控制层基类， 所有controller类都需要继承此类        
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600) //解决跨域问题
public class BaseController {
    private static Logger log = LoggerFactory.getLogger(BaseController.class);

    @Resource
    protected ISystemUserService systemUserService;
    @Resource
    protected ISystemRoleService systemRoleService;
    @Resource
    protected ISystemResourceService systemResourceService;
    @Resource
    protected ISystemRoleResourceService systemRoleResourceService;
    @Resource
    protected ISystemUserStoreService systemUserStoreService;
    @Resource
    protected IMdUserService mdUserService;
    @Resource
    protected IMdUserStoreService mdUserStoreService;
    @Resource
    protected IMdStoreService mdStoreService;
    @Resource
    protected IMdBrandService mdBrandService;
    @Resource
    protected IMdSkuService mdSkuService;
    @Resource
    protected IMdExpressService mdExpressService;
    @Resource
    protected IBdFormService bdFormService;
    @Resource
    protected IBdFormSkuService bdFormSkuService;
    @Resource
    protected IBdFormImageService bdFormImageService;
    @Resource
    protected IBdFormDisposeService bdFormDisposeService;
    @Resource
    protected IBdReceivingService bdReceivingService;
    @Resource
    protected IBdReceivingSkuService bdReceivingSkuService;
    @Resource
    protected IBdStockInvService bdStockInvService;
    @Resource
    protected IBdStockInvLogService bdStockInvLogService;
    @Resource
    protected IImportExcelService importExcelService;

    protected boolean isNull(Object... argumets) {
        for (Object obj : argumets) {
            if (obj == null || "".equals(obj)) {
                return true;
            }
        }
        return false;
    }

    protected SystemUser getSystemUser() {
        Subject subject = SecurityUtils.getSubject();
        SystemUser systemUser = (SystemUser) subject.getPrincipal();
        return systemUser;
    }

    protected String getBrandNo() {
        String brandNo = null;
        SystemUser systemUser = getSystemUser();
        if (systemUser.getUserType() == 5) {
            SystemUserBrand systemUserBrand = systemUserService.getSystemUserBrandByUsername(systemUser.getUsername()).getResult();
            if (!isNull(systemUserBrand)) {
                brandNo = systemUserBrand.getBrandNo();
            }
        }
        return brandNo;
    }

    protected String[] getStoreNoArr() {
        SystemUser systemUser = getSystemUser();
        String[] storeNoArr = null;
        String[] userNoArr = null;
        if (systemUser.getUserType() == 1) {
            storeNoArr = new String[]{systemUser.getUserNo()};
        } else if (systemUser.getUserType() == 3 || systemUser.getUserType() == 4) {
            SystemUserStore userStore = systemUserStoreService.getSystemUserStoreByUserNo(systemUser.getUserNo()).getResult();
            if (!isNull(userStore)) {
                storeNoArr = new String[]{userStore.getStoreNo()};
            }
        } else if (systemUser.getUserType() == 2) {
            List<MdUser> userList = mdUserService.getUserListByUserNo(systemUser.getUserNo()).getResult();
            List<MdUserStore> userStoreList = null;
            if (isNull(userList) || userList.size() == 0) {
                userStoreList = mdUserStoreService.getMdUserStoreListByUserNo(systemUser.getUserNo()).getResult();
            } else {
                int i = 0;
                userNoArr = new String[userList.size() + 1];
                for (MdUser user : userList) {
                    userNoArr[i] = user.getUserNo();
                    i++;
                }
                userNoArr[i] = systemUser.getUserNo();
                userStoreList = mdUserStoreService.getMdUserStoreListByUserNoArr(userNoArr).getResult();
            }

            if (isNull(userStoreList) || userStoreList.size() == 0) {
                return null;
            } else {
                storeNoArr = new String[userStoreList.size()];
                int i = 0;
                for(MdUserStore userStore : userStoreList) {
                    storeNoArr[i] = userStore.getStoreNo();
                    i++;
                }
            }
        }
        return storeNoArr;
    }

    protected boolean checkImageList() {
        return true;
    }

    protected AjaxResponse checkSkuList(List<BdFormSku> skuList, Integer formType) {
        if (skuList.isEmpty()) {
            return AjaxResponse.failure("申请的SKU列表为空");
        } else {
            if (formType == 1) {
                for (BdFormSku sku : skuList) {
                    if (sku.getStockDepotTotal() == null || sku.getStockCounterTotal() == null) {
                        return AjaxResponse.failure("申请数量不能为空");
                    } else if (sku.getStockDepotTotal() < 0 || sku.getStockCounterTotal() < 0) {
                        return AjaxResponse.failure("申请数量不能为负数");
                    } else if (sku.getStockDepotTotal() == 0 && sku.getStockCounterTotal() == 0) {
                        return AjaxResponse.failure("申请数量不能同时为0");
                    }
                }
            } else if (formType == 2) {
                for (BdFormSku sku : skuList) {
                    if (sku.getChangeTotal() <= 0) {
                        return AjaxResponse.failure("销毁数量参数错误");
                    } else if (sku.getChangeTotal() > sku.getStockCounterTotal()) {
                        return AjaxResponse.failure("销毁数量超出柜面可用数量");
                    }
                }
            } else if (formType == 3) {
                for (BdFormSku sku : skuList) {
                    if (sku.getChangeTotal() <= 0) {
                        return AjaxResponse.failure("调拨数量参数错误");
                    } else if (sku.getChangeTotal() > sku.getStockDepotTotal()) {
                        return AjaxResponse.failure("销调拨数量超出店仓可用数量");
                    }
                }
            } else if (formType == 4) {
                for (BdFormSku sku : skuList) {
                    if (sku.getChangeTotal() <= 0) {
                        return AjaxResponse.failure("调整数量参数错误");
                    } else if (sku.getChangeType() == 2 && sku.getChangeTotal() > sku.getStockDepotTotal()) {
                        return AjaxResponse.failure("调整数量超出店仓可用数量");
                    }
                }
            }
        }
        return AjaxResponse.success();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //date,datetime
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                if (StringUtil.isEmpty(value)) {
                    setValue(null);
                    return;
                }
                try {
                    if (value.length() == 10) {
                        setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                    } else if (value.length() == 8) {
                        setValue(new SimpleDateFormat("HH:mm:ss").parse(value));
                    } else if (value.length() == 16) {
                        setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(value));
                    } else {
                        setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
                    }

                } catch (ParseException e) {
                    log.error("Can not convert '" + value + "' to java.util.Date", e);
                }
            }

            @Override
            public String getAsText() {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) getValue());
            }

        });
        //int
        binder.registerCustomEditor(Integer.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                if (StringUtil.isEmpty(value)) {
                    setValue(0);
                    return;
                }
                setValue(Integer.parseInt(value));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }

        });

        //long
        binder.registerCustomEditor(Long.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                if (StringUtil.isEmpty(value)) {
                    setValue(0);
                    return;
                }
                setValue(Long.parseLong(value));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }

        });

        //double
        binder.registerCustomEditor(Double.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                if (StringUtil.isEmpty(value)) {
                    setValue(0);
                    return;
                }
                setValue(Double.parseDouble(value));
            }

            @Override
            public String getAsText() {
                return getValue().toString();
            }

        });
    }

    public ModelAndView redirectHandlerFor301(String sURL) {
        RedirectView rv = new RedirectView(sURL);
        rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        rv.setUrl(sURL);
        ModelAndView mv = new ModelAndView(rv);
        return mv;
    }

    /**
     * 调用此方法说明你的controller方法将要转到显示系统消息页面
     * @param stack
     * @param message
     * @param links
     */
    public void showMessage(Map<String, Object> stack, String message,
                            List<Map<String, String>> links) {
        if (links == null || links.size() == 0) {
            links = new ArrayList<Map<String, String>>();
            Map<String, String> link = new HashMap<String, String>();
            link.put("text", "返回上一页");
            link.put("url", "javascript:history.back()");
            links.add(link);
        }
        stack.put("messageInfo", message);
        stack.put("links", links);
        stack.put("msgType", "message");
    }
}
