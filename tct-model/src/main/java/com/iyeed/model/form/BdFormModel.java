package com.iyeed.model.form;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.form.BdForm;
import com.iyeed.core.entity.form.BdFormDispose;
import com.iyeed.core.entity.form.BdFormImage;
import com.iyeed.core.entity.form.BdFormSku;
import com.iyeed.core.entity.form.vo.ExecDisposeForm;
import com.iyeed.core.entity.form.vo.GetDisposeFormListBean;
import com.iyeed.core.entity.form.vo.SaveApplyForm;
import com.iyeed.core.entity.receive.BdReceiving;
import com.iyeed.core.entity.receive.vo.GetReceivingListForm;
import com.iyeed.core.entity.stock.BdStockInv;
import com.iyeed.core.entity.store.MdStore;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.utils.DateUtils;
import com.iyeed.dao.db.write.xzn.form.BdFormDisposeWriteDao;
import com.iyeed.dao.db.write.xzn.form.BdFormImageWriteDao;
import com.iyeed.dao.db.write.xzn.form.BdFormSkuWriteDao;
import com.iyeed.dao.db.write.xzn.form.BdFormWriteDao;
import com.iyeed.dao.db.write.xzn.stock.BdStockInvWriteDao;
import com.iyeed.dao.db.write.xzn.store.MdStoreWriteDao;
import com.iyeed.dao.db.write.xzn.user.MdUserWriteDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class BdFormModel {
    
    @Resource
    private BdFormWriteDao bdFormWriteDao;

	@Resource
	private BdFormSkuWriteDao bdFormSkuWriteDao;

	@Resource
	private BdFormImageWriteDao bdFormImageWriteDao;

	@Resource
	private BdFormDisposeWriteDao bdFormDisposeWriteDao;

	@Resource
	private MdStoreWriteDao mdStoreWriteDao;

	@Resource
	private MdUserWriteDao mdUserWriteDao;

	@Resource
	private BdStockInvWriteDao bdStockInvWriteDao;
    
    /**
     * 根据id取得表单-总表
     * @param  bdFormId
     * @return
     */
    public BdForm getBdFormById(Integer bdFormId) throws Exception {
    	return bdFormWriteDao.get(bdFormId);
    }

	/**
	 * 获取申请表单列表
	 * @param  queryMap
	 * @param  start
	 * @param  size
	 * @return
	 */
	public List<GetDisposeFormListBean> getBdFormList(Map<String, String> queryMap,
														   Integer start, Integer size) throws Exception {
		return bdFormWriteDao.getBdFormList(queryMap, start, size);
	}

	/**
	 * 获取申请表单列表总数
	 * @param  queryMap
	 * @return
	 */
	public Integer getBdFormListCount(Map<String, String> queryMap) throws Exception{
		return bdFormWriteDao.getBdFormListCount(queryMap);
	}
    
    /**
     * 保存表单-总表
     * @param  bdForm
     * @return
     */
    public Integer saveBdForm(BdForm bdForm) throws Exception {
     	this.dbConstrains(bdForm);
     	return bdFormWriteDao.insert(bdForm);
    }

	@Transactional(rollbackFor = Exception.class)
    public Integer execDisposeFormDetails(BdForm bdForm, ExecDisposeForm form) throws Exception {
    	Integer exec;

		MdUser user = mdUserWriteDao.getUserByUserNo(form.getUserNo());
		BdFormDispose bdFormDispose = bdFormDisposeWriteDao.getDispose(bdForm.getApplyNo(), user.getUserNo(), bdForm.getStoreNo());
		bdFormDispose.setApplyNo(bdForm.getApplyNo());
		bdFormDispose.setDisposeDate(new Date());
		bdFormDispose.setRemark(form.getRemark());
		List<BdFormSku> skuList = bdFormSkuWriteDao.getFormSkuList(bdForm.getApplyNo());
		if (form.getType() == 2) {//拒绝处理
			bdFormDispose.setDisposeType(2);
			bdFormDispose.setDisposeStatus("结束-已被拒绝");
			bdFormDisposeWriteDao.update(bdFormDispose);
			bdForm.setDisposeStatus(0);
			bdForm.setDisposeStatusDesc("结束-已被拒绝");
			if (bdForm.getFormType() != 1) {
				insertOrUpdateStockInv(skuList, bdForm, form.getType());
			}
		} else {//同意处理
			MdUser fUser = null;
			if (bdForm.getDisposeGrade() != 2) {
				fUser = mdUserWriteDao.getUserByUserId(user.getUserPid());
			}

			if (fUser == null) {
				bdFormDispose.setDisposeType(1);
				bdFormDispose.setDisposeStatus("结束-成功审批");
				bdFormDisposeWriteDao.update(bdFormDispose);
				bdForm.setDisposeStatus(0);
				bdForm.setDisposeStatusDesc("结束-成功审批");
				insertOrUpdateStockInv(skuList, bdForm, form.getType());
			} else {
				bdFormDispose.setDisposeType(1);
				bdFormDispose.setDisposeStatus(user.getPost() + "审核");
				bdFormDisposeWriteDao.update(bdFormDispose);

				bdFormDispose = new BdFormDispose();
				bdFormDispose.setDisposeType(0);
				bdFormDispose.setApplyNo(bdForm.getApplyNo());
				bdFormDispose.setStoreNo(bdForm.getStoreNo());
				bdFormDispose.setStoreName(bdForm.getStoreName());
				bdFormDispose.setDisposeUserNo(fUser.getUserNo());
				bdFormDispose.setDisposeUserName(fUser.getUserName());
				bdFormDispose.setDisposeStatus(fUser.getPost() + "审核");
                bdFormDisposeWriteDao.insert(bdFormDispose);

				bdForm.setDisposeUserNo(fUser.getUserNo());
				bdForm.setDisposeStatus(2);
				bdForm.setDisposeStatusDesc("等待" + fUser.getPost() + "审核");
			}
		}

		bdForm.setDisposeGrade(bdForm.getDisposeGrade() + 1);
		exec = bdFormWriteDao.update(bdForm);

    	return exec;
	}

	private void insertOrUpdateStockInv(List<BdFormSku> skuList, BdForm bdForm, Integer type) throws Exception {
		if (skuList == null) {
			return;
		}
    	for(BdFormSku sku : skuList) {
			BdStockInv stockInv = new BdStockInv();
			stockInv.setStoreNo(bdForm.getStoreNo());
			stockInv.setStoreName(bdForm.getStoreName());
			stockInv.setSkuCode(sku.getSkuCode());
			stockInv.setSkuName(sku.getSkuName());
			if (bdForm.getFormType() == 1 && type == 1) {//库存初始化（只有同意才执行）
				stockInv.setStockDepot(sku.getStockDepotTotal());
				stockInv.setStockDepotEnabled(sku.getStockDepotTotal());
				stockInv.setStockCounter(sku.getStockCounterTotal());
				stockInv.setStockCounterEnabled(sku.getStockCounterTotal());
			} else if (bdForm.getFormType() == 2) {//销毁
				if (type == 1) {//销毁-同意
					stockInv.setStockCounter(-sku.getChangeTotal());
				} else {//销毁-撤销
					stockInv.setStockCounterEnabled(sku.getChangeTotal());
				}
			} else if (bdForm.getFormType() == 3) {//调拨
				if (type == 1) {//调拨-同意
					stockInv.setStockDepot(-sku.getChangeTotal());
				} else {//调拨-拒绝（撤销）
					stockInv.setStockDepotEnabled(sku.getChangeTotal());
				}
			} else if (bdForm.getFormType() == 4) {//异常调整
				if (type == 1) {//异常调整-同意
					if (sku.getChangeType() == 1) {
						stockInv.setStockDepot(sku.getChangeTotal());
					} else if (sku.getChangeType() == 2) {
						stockInv.setStockDepot(-sku.getChangeTotal());
					}
				} else {//异常调整-拒绝（撤销）
					if (sku.getChangeType() == 1) {
						stockInv.setStockDepot(-sku.getChangeTotal());
					} else if (sku.getChangeType() == 2) {
						stockInv.setStockDepot(sku.getChangeTotal());
					}
				}
			}
			bdStockInvWriteDao.insertOrUpdate(stockInv);
		}
	}

	/**
	 * 保存表单-总表
	 * @param  form
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Integer saveForm(SaveApplyForm form) throws Exception {
		String format = "yyyy-MM-dd";
		BdForm bdForm = new BdForm();
		bdForm.setApplicant(form.getApplicant());
		bdForm.setApplicantTel(form.getApplicantTel());
		bdForm.setApplyNo(form.getApplyNo());
		bdForm.setApplyDate(new Date());
		bdForm.setStoreNo(form.getStoreNo());
		bdForm.setStoreName(form.getStoreName());
		bdForm.setRemark(form.getRemark());
		bdForm.setFormType(form.getFormType());
		bdForm.setDisposeStatus(1);//首次提交默认为待处理状态
		bdForm.setDisposeGrade(0);//
		if (form.getFormType() == 2) {//销毁表单
			bdForm.setDestroyType(form.getDestroyType());
		} else if (form.getFormType() == 3) { //调拨表单
			bdForm.setAllotType(form.getAllotType());
			bdForm.setExpressCode(form.getExpressCode());
			bdForm.setExpressDate(DateUtils.strToDate(form.getExpressDate(), format));
			bdForm.setExpressName(form.getExpressName());
			bdForm.setOrderNo(form.getOrderNo());
			bdForm.setReceiveStoreNo(form.getReceiveStoreNo());
			bdForm.setReceiveStoreName(form.getReceiveStoreName());
		} else if (form.getFormType() == 4) {//异常表单
			bdForm.setExceptionType(form.getExceptionType());
		}

		MdStore store = mdStoreWriteDao.getStoreByStoreNo(form.getStoreNo());
		bdForm.setDisposeUserNo(store.getUserNo());
		bdForm.setDisposeStatusDesc("等待" + store.getPost() + "审核");

		Integer insert;
		insert = bdFormWriteDao.insert(bdForm);
		if (form.getFormImageList() != null) {
			for (BdFormImage image : form.getFormImageList()) {
				image.setApplyNo(form.getApplyNo());
				insert = bdFormImageWriteDao.insert(image);
			}
		}

		if (form.getFormSkuList() != null) {
			for (BdFormSku sku : form.getFormSkuList()) {
				sku.setApplyNo(form.getApplyNo());
				insert = bdFormSkuWriteDao.insert(sku);

				//库存变化
				if (bdForm.getFormType() != 1) {
					BdStockInv stockInv = new BdStockInv();
					stockInv.setStoreNo(bdForm.getStoreNo());
					stockInv.setStoreName(bdForm.getStoreName());
					stockInv.setSkuCode(sku.getSkuCode());
					stockInv.setSkuName(sku.getSkuName());
					if (bdForm.getFormType() == 2) {//销毁
						stockInv.setStockCounterEnabled(- sku.getChangeTotal());
					} else if (bdForm.getFormType() == 3) {//调拨
						stockInv.setStockDepotEnabled(- sku.getChangeTotal());
					} else if (bdForm.getFormType() == 4) {//异常调整
						if (sku.getChangeType() == 1) {
							stockInv.setStockDepotEnabled(sku.getChangeTotal());
						} else if (sku.getChangeType() == 2) {
							stockInv.setStockDepotEnabled(- sku.getChangeTotal());
						}
					}
					bdStockInvWriteDao.insertOrUpdate(stockInv);
				}
			}
		}

		BdFormDispose formDispose = new BdFormDispose();
		formDispose.setApplyNo(bdForm.getApplyNo());
		formDispose.setDisposeType(0);
		formDispose.setDisposeUserName(form.getUserName());
		formDispose.setDisposeUserNo(form.getUserNo());
		formDispose.setDisposeDate(new Date());
		formDispose.setStoreNo(form.getStoreNo());
		formDispose.setStoreName(form.getStoreName());
		formDispose.setDisposeStatus("提交申请");
		bdFormDisposeWriteDao.insert(formDispose);

		formDispose = new BdFormDispose();
		formDispose.setApplyNo(bdForm.getApplyNo());
		formDispose.setDisposeType(0);
		formDispose.setDisposeUserName(store.getUserName());
		formDispose.setDisposeUserNo(store.getUserNo());
		formDispose.setStoreNo(form.getStoreNo());
		formDispose.setStoreName(form.getStoreName());
		formDispose.setDisposeStatus(store.getPost() + "审核");
		bdFormDisposeWriteDao.insert(formDispose);

		return insert;
	}
     
    /**
     * 更新表单-总表
     * @param  bdForm
     * @return
     */
    public Integer updateBdForm(BdForm bdForm) throws Exception {
        this.dbConstrains(bdForm);
     	return bdFormWriteDao.update(bdForm);
    }
     
    private void dbConstrains(BdForm bdForm) throws Exception {
		bdForm.setStoreNo(StringUtil.dbSafeString(bdForm.getStoreNo(), true, 40));
		bdForm.setStoreName(StringUtil.dbSafeString(bdForm.getStoreName(), true, 40));
		bdForm.setApplyNo(StringUtil.dbSafeString(bdForm.getApplyNo(), true, 40));
		bdForm.setApplicant(StringUtil.dbSafeString(bdForm.getApplicant(), true, 40));
		bdForm.setApplicantTel(StringUtil.dbSafeString(bdForm.getApplicantTel(), true, 40));
		bdForm.setRemark(StringUtil.dbSafeString(bdForm.getRemark(), true, 400));
		bdForm.setReceiveStoreNo(StringUtil.dbSafeString(bdForm.getReceiveStoreNo(), true, 40));
		bdForm.setReceiveStoreName(StringUtil.dbSafeString(bdForm.getReceiveStoreName(), true, 40));
		bdForm.setExpressCode(StringUtil.dbSafeString(bdForm.getExpressCode(), true, 40));
		bdForm.setExpressName(StringUtil.dbSafeString(bdForm.getExpressName(), true, 40));
		bdForm.setOrderNo(StringUtil.dbSafeString(bdForm.getOrderNo(), true, 40));
		bdForm.setInputUserNo(StringUtil.dbSafeString(bdForm.getInputUserNo(), true, 40));
    }
}