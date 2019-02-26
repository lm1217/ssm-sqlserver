package com.iyeed.model;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.form.BdForm;
import com.iyeed.core.entity.form.BdFormDispose;
import com.iyeed.core.entity.form.BdFormSku;
import com.iyeed.core.entity.stock.BdStockInv;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.dao.db.read.xzn.system.SystemUserReadDao;
import com.iyeed.dao.db.write.xzn.express.MdExpressWriteDao;
import com.iyeed.dao.db.write.xzn.form.BdFormDisposeWriteDao;
import com.iyeed.dao.db.write.xzn.form.BdFormImageWriteDao;
import com.iyeed.dao.db.write.xzn.form.BdFormSkuWriteDao;
import com.iyeed.dao.db.write.xzn.form.BdFormWriteDao;
import com.iyeed.dao.db.write.xzn.hrmd.HrDoorMappingWriteDao;
import com.iyeed.dao.db.write.xzn.hrmd.HrUserMappingWriteDao;
import com.iyeed.dao.db.write.xzn.receive.BdReceivingSkuWriteDao;
import com.iyeed.dao.db.write.xzn.receive.BdReceivingWriteDao;
import com.iyeed.dao.db.write.xzn.sku.MdSkuWriteDao;
import com.iyeed.dao.db.write.xzn.stock.BdStockInvDataWriteDao;
import com.iyeed.dao.db.write.xzn.stock.BdStockInvLogWriteDao;
import com.iyeed.dao.db.write.xzn.stock.BdStockInvWriteDao;
import com.iyeed.dao.db.write.xzn.store.MdBrandWriteDao;
import com.iyeed.dao.db.write.xzn.store.MdStoreWriteDao;
import com.iyeed.dao.db.write.xzn.system.*;
import com.iyeed.dao.db.write.xzn.user.MdUserStoreWriteDao;
import com.iyeed.dao.db.write.xzn.user.MdUserWriteDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;

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
    @Resource
    protected SystemUserStoreWriteDao systemUserStoreWriteDao;
    @Resource
    protected HrUserMappingWriteDao hrUserMappingWriteDao;
    @Resource
    protected HrDoorMappingWriteDao hrDoorMappingWriteDao;
    @Resource
    protected BdStockInvDataWriteDao bdStockInvDataWriteDao;

    protected void changeData(BdStockInv stockInv) throws Exception {
        if (stockInv.getStockCounter() == null) stockInv.setStockCounter(0);
        if (stockInv.getStockCounterEnabled() == null) stockInv.setStockCounterEnabled(0);
        if (stockInv.getStockDepot() == null) stockInv.setStockDepot(0);
        if (stockInv.getStockDepotEnabled() == null) stockInv.setStockDepotEnabled(0);
        if (stockInv.getStockDepotFreeze() == null) stockInv.setStockDepotFreeze(0);
        if (stockInv.getStockCounterFreeze() == null) stockInv.setStockCounterFreeze(0);
    }

    protected void checkFormSkuData(BdFormSku formSku) throws Exception {
        if (formSku.getStockDepotTotal() == null) formSku.setStockDepotTotal(0);
        if (formSku.getStockCounterTotal() == null) formSku.setStockCounterTotal(0);
        if (formSku.getChangeTotal() == null) formSku.setChangeTotal(0);
    }

    protected void backupCsvFile(File file) throws Exception {
        //文件备份
        String backupFileDir = file.getParent() + "\\" + "BUCKUP" + "\\" + StringUtil.formatDate("yyyyMMdd", new Date());
        File backupDir = new File(backupFileDir);
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }

        File backupFile = new File(backupFileDir + "\\" + file.getName());
        if (backupFile.exists() && backupFile.isFile()) {
            backupFile.delete();
        }
        file.renameTo(new File(backupFile.getPath()));
    }

    protected void addFlow(String userNo, Integer currentLv, Integer lv, String applyNo, String storeNo, String storeName, String brandNo, Integer formType) throws Exception {
        int i = 0;
        MdUser user = null;
        while (userNo != null) {
            user = mdUserWriteDao.getUserByUserNo(userNo);
            // 如若一级CM品牌的销毁单一级审批人是xihe，则跳出循环
            if (brandNo.equals("5") && formType == 2 && user.getUserId().equals("1549959")) {
                userNo = null;
                continue;
            }
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
                formDispose.setDisposeStatus("二级审批人");
                bdFormDisposeWriteDao.insert(formDispose);
                currentLv = currentLv + 1;
                if (currentLv > lv) {
                    userNo = null;
                } else {
                    userNo = fUser.getUserNo();
                }
                i++;
            }
        }

        // CM品牌特殊处理(只有销毁审批才是由xihe的审批)
        if (brandNo.equals("5") && formType == 2 && !user.getUserId().equals("1549959")) {
            MdUser fUser = mdUserWriteDao.getUserByUserNo("1549959");// xihe
            BdFormDispose formDispose = new BdFormDispose();
            formDispose.setApplyNo(applyNo);
            formDispose.setDisposeType(0);
            formDispose.setDisposeUserName(fUser.getUserName());
            formDispose.setDisposeUserNo(fUser.getUserNo());
            formDispose.setStoreNo(storeNo);
            formDispose.setStoreName(storeName);
            if (i == 0) {
                formDispose.setDisposeStatus("二级审批人");
            } else if (i == 1) {
                formDispose.setDisposeStatus("三级审批人");
            }
            bdFormDisposeWriteDao.insert(formDispose);
        }
    }

}
