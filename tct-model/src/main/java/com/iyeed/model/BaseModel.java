package com.iyeed.model;

import com.iyeed.core.entity.form.BdFormDispose;
import com.iyeed.core.entity.stock.BdStockInv;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.dao.db.read.xzn.system.SystemUserReadDao;
import com.iyeed.dao.db.write.xzn.express.MdExpressWriteDao;
import com.iyeed.dao.db.write.xzn.form.BdFormDisposeWriteDao;
import com.iyeed.dao.db.write.xzn.form.BdFormImageWriteDao;
import com.iyeed.dao.db.write.xzn.form.BdFormSkuWriteDao;
import com.iyeed.dao.db.write.xzn.form.BdFormWriteDao;
import com.iyeed.dao.db.write.xzn.receive.BdReceivingSkuWriteDao;
import com.iyeed.dao.db.write.xzn.receive.BdReceivingWriteDao;
import com.iyeed.dao.db.write.xzn.sku.MdSkuWriteDao;
import com.iyeed.dao.db.write.xzn.stock.BdStockInvLogWriteDao;
import com.iyeed.dao.db.write.xzn.stock.BdStockInvWriteDao;
import com.iyeed.dao.db.write.xzn.store.MdBrandWriteDao;
import com.iyeed.dao.db.write.xzn.store.MdStoreWriteDao;
import com.iyeed.dao.db.write.xzn.system.*;
import com.iyeed.dao.db.write.xzn.user.MdUserStoreWriteDao;
import com.iyeed.dao.db.write.xzn.user.MdUserWriteDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BaseModel {
    @Resource
    protected MdStoreWriteDao mdStoreWriteDao;
    @Resource
    protected MdUserWriteDao mdUserWriteDao;
    @Resource
    protected MdUserStoreWriteDao mdUserStoreWriteDao;
    @Resource
    protected MdSkuWriteDao mdSkuWriteDao;
    @Resource
    protected MdBrandWriteDao mdBrandWriteDao;
    @Resource
    protected MdExpressWriteDao mdExpressWriteDao;
    @Resource
    protected BdReceivingWriteDao bdReceivingWriteDao;
    @Resource
    protected BdReceivingSkuWriteDao bdReceivingSkuWriteDao;
    @Resource
    protected BdFormWriteDao bdFormWriteDao;
    @Resource
    protected BdFormSkuWriteDao bdFormSkuWriteDao;
    @Resource
    protected BdFormImageWriteDao bdFormImageWriteDao;
    @Resource
    protected BdFormDisposeWriteDao bdFormDisposeWriteDao;
    @Resource
    protected BdStockInvWriteDao bdStockInvWriteDao;
    @Resource
    protected BdStockInvLogWriteDao bdStockInvLogWriteDao;
    @Resource
    protected SystemUserWriteDao systemUserWriteDao;
    @Resource
    protected SystemUserReadDao systemUserReadDao;
    @Resource
    protected SystemRoleWriteDao systemRoleWriteDao;
    @Resource
    protected SystemLogWriteDao systemLogWriteDao;
    @Resource
    protected SystemResourceWriteDao systemResourceWriteDao;
    @Resource
    protected SystemRoleResourceWriteDao systemRoleResourceWriteDao;

    protected void changeData(BdStockInv stockInv) throws Exception {
        if (stockInv.getStockCounter() == null) stockInv.setStockCounter(0);
        if (stockInv.getStockCounterEnabled() == null) stockInv.setStockCounterEnabled(0);
        if (stockInv.getStockDepot() == null) stockInv.setStockDepot(0);
        if (stockInv.getStockDepotEnabled() == null) stockInv.setStockDepotEnabled(0);
        if (stockInv.getStockDepotFreeze() == null) stockInv.setStockDepotFreeze(0);
        if (stockInv.getStockCounterFreeze() == null) stockInv.setStockCounterFreeze(0);
    }

    protected void addFlow(String userNo, Integer currentLv, Integer lv, String applyNo, String storeNo, String storeName) throws Exception {
        while (userNo != null) {
            MdUser user = mdUserWriteDao.getUserByUserNo(userNo);
            MdUser fUser = mdUserWriteDao.getUserByUserNo(user.getUserPid());
            if (fUser == null) {
                userNo = null;
            } else {
                BdFormDispose formDispose = new BdFormDispose();
                formDispose.setApplyNo(applyNo);
                formDispose.setDisposeType(0);
                formDispose.setDisposeUserName(fUser.getUserName());
                formDispose.setDisposeUserNo(fUser.getUserNo());
                formDispose.setStoreNo(storeNo);
                formDispose.setStoreName(storeName);
                if (currentLv == 2) {
                    formDispose.setDisposeStatus("二级审批人");
                } else if (currentLv == 2) {
                    formDispose.setDisposeStatus("三级审批人");
                }
                bdFormDisposeWriteDao.insert(formDispose);

                currentLv = currentLv + 1;
                if (currentLv > lv) {
                    userNo = null;
                }
            }
        }
    }

}
