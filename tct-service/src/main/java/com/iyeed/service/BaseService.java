package com.iyeed.service;

import com.iyeed.model.express.MdExpressModel;
import com.iyeed.model.form.BdFormDisposeModel;
import com.iyeed.model.form.BdFormImageModel;
import com.iyeed.model.form.BdFormModel;
import com.iyeed.model.form.BdFormSkuModel;
import com.iyeed.model.receive.BdReceivingModel;
import com.iyeed.model.receive.BdReceivingSkuModel;
import com.iyeed.model.sku.MdSkuModel;
import com.iyeed.model.stock.BdStockInvLogModel;
import com.iyeed.model.stock.BdStockInvModel;
import com.iyeed.model.store.MdBrandModel;
import com.iyeed.model.store.MdStoreModel;
import com.iyeed.model.system.*;
import com.iyeed.model.user.MdUserModel;
import com.iyeed.model.user.MdUserStoreModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BaseService {
    @Resource
    protected MdUserModel mdUserModel;
    @Resource
    protected MdUserStoreModel mdUserStoreModel;
    @Resource
    protected BdReceivingSkuModel bdReceivingSkuModel;
    @Resource
    protected BdReceivingModel bdReceivingModel;
    @Resource
    protected MdExpressModel mdExpressModel;
    @Resource
    protected BdFormModel bdFormModel;
    @Resource
    protected BdFormSkuModel bdFormSkuModel;
    @Resource
    protected BdFormImageModel bdFormImageModel;
    @Resource
    protected BdFormDisposeModel bdFormDisposeModel;
    @Resource
    protected MdSkuModel mdSkuModel;
    @Resource
    protected BdStockInvLogModel bdStockInvLogModel;
    @Resource
    protected BdStockInvModel bdStockInvModel;
    @Resource
    protected MdBrandModel mdBrandModel;
    @Resource
    protected MdStoreModel mdStoreModel;
    @Resource
    protected SystemLogModel systemLogModel;
    @Resource
    protected SystemResourceModel systemResourceModel;
    @Resource
    protected SystemRoleResourceModel systemRoleResourceModel;
    @Resource
    protected SystemRoleModel systemRoleModel;
    @Resource
    protected SystemUserModel systemUserModel;
}
