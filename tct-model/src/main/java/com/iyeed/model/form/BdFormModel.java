package com.iyeed.model.form;

import com.iyeed.core.StringUtil;
import com.iyeed.core.entity.form.BdForm;
import com.iyeed.core.entity.form.BdFormDispose;
import com.iyeed.core.entity.form.BdFormImage;
import com.iyeed.core.entity.form.BdFormSku;
import com.iyeed.core.entity.form.vo.*;
import com.iyeed.core.entity.receive.BdReceiving;
import com.iyeed.core.entity.receive.BdReceivingSku;
import com.iyeed.core.entity.stock.BdStockInv;
import com.iyeed.core.entity.store.MdStore;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.exception.ArgumentException;
import com.iyeed.core.mail.MailInfo;
import com.iyeed.core.mail.MailSender;
import com.iyeed.core.utils.DateUtils;
import com.iyeed.core.utils.Identities;
import com.iyeed.model.BaseModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BdFormModel extends BaseModel {

    /**
     * 根据id取得表单-总表
     * @param  bdFormId
     * @return
     */
    public BdForm getBdFormById(Integer bdFormId) throws Exception {
    	return bdFormWriteDao.get(bdFormId);
    }

	public BdForm getBdFormByApplyNo(String applyNo) throws Exception {
		return bdFormWriteDao.getByApplyNo(applyNo);
	}

	/**
	 * 获取申请表单列表
	 * @param  queryMap
	 * @param  start
	 * @param  size
	 * @return
	 */
	public List<GetDisposeFormListBean> getBdFormList(Map<String, Object> queryMap,
														   Integer start, Integer size) throws Exception {
		return bdFormWriteDao.getBdFormList(queryMap, start, size);
	}

	/**
	 * 获取申请表单列表总数
	 * @param  queryMap
	 * @return
	 */
	public Integer getBdFormListCount(Map<String, Object> queryMap) throws Exception{
		return bdFormWriteDao.getBdFormListCount(queryMap);
	}

	public List<ExceptionReportBean> getExceptionReportList(Map<String, Object> queryMap,
													  Integer start, Integer size) throws Exception {
		return bdFormWriteDao.getExceptionReportList(queryMap, start, size);
	}

	public Integer getExceptionReportListCount(Map<String, Object> queryMap) throws Exception{
		return bdFormWriteDao.getExceptionReportListCount(queryMap);
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

	/**
	 * 表单审批(同意、拒绝)
	 */
	@Transactional(rollbackFor = Exception.class)
    public Integer execDisposeFormDetails(BdForm bdForm, ExecDisposeForm form) throws Exception {
    	Integer exec;

		MdUser user = mdUserWriteDao.getUserByUserNo(form.getUserNo());
		BdFormDispose bdFormDispose = bdFormDisposeWriteDao.getDispose(bdForm.getApplyNo(), user.getUserNo(), bdForm.getStoreNo());
		bdFormDispose.setApplyNo(bdForm.getApplyNo());
		bdFormDispose.setDisposeDate(new Date());
		bdFormDispose.setRemark(form.getRemark());
		List<BdFormSku> skuList = bdFormSkuWriteDao.getFormSkuList(bdForm.getApplyNo());
		//处理计数，默认为0
		bdForm.setDisposeGrade(bdForm.getDisposeGrade() + 1);
		if (form.getType() == 2) {//拒绝处理
			bdFormDispose.setDisposeType(3);
			bdFormDisposeWriteDao.update(bdFormDispose);
            bdFormDisposeWriteDao.del(bdFormDispose.getApplyNo());
			bdForm.setDisposeStatus(0);
			bdForm.setDisposeStatusDesc("拒绝");
			bdForm.setDisposeResult(2);//结果-拒绝
			if (bdForm.getFormType() != 1) {
				insertOrUpdateStockInv(skuList, bdForm, form.getType());
			}
		} else {//同意处理
			MdUser fUser = null;
//			if (bdForm.getFormType() == 2 && bdForm.getDisposeGrade() < 2) {
//				//销毁表单要二级审批
//				fUser = mdUserWriteDao.getUserByUserId(user.getUserPid());
//			}
			if (bdForm.getDisposeGrade() < 2) {
				//所有表单要二级审批
				fUser = mdUserWriteDao.getUserByUserId(user.getUserPid());
			}

			if (fUser == null) {
				bdFormDispose.setDisposeType(2);
				bdFormDisposeWriteDao.update(bdFormDispose);
				bdForm.setDisposeStatus(0);
				bdForm.setDisposeStatusDesc("通过");
				bdForm.setDisposeResult(1);//结果-通过
				if (bdForm.getFormType() == 3) {//调拨单审批通过要在收货表插入数据
					insertReceiveByAllot(skuList, bdForm);
				}
				insertOrUpdateStockInv(skuList, bdForm, form.getType());
			} else {
				bdFormDispose.setDisposeType(2);
				bdFormDisposeWriteDao.update(bdFormDispose);

				bdForm.setDisposeUserNo(fUser.getUserNo());
				bdForm.setDisposeStatus(2);
                if (bdForm.getDisposeGrade() + 1 == 2) {
                    bdForm.setDisposeStatusDesc("等待二级审批");
                } else if (bdForm.getDisposeGrade() + 1 == 3) {
                    bdForm.setDisposeStatusDesc("等待三级审批");
                }
			}
		}

		if (form.getFormImageList() != null) {
			for (BdFormImage image : form.getFormImageList()) {
				image.setApplyNo(bdForm.getApplyNo());
				image.setType(2);
				if (image.getImageUrl() == null || StringUtils.trim(image.getImageUrl()).equals(""))
					continue;
				bdFormImageWriteDao.insert(image);
			}
		}

		bdForm.setUpdateDate(new Date());
		bdForm.setSendMailDate(new Date());
		exec = bdFormWriteDao.update(bdForm);

    	return exec;
	}

	private void insertReceiveByAllot(List<BdFormSku> skuList, BdForm bdForm) throws Exception {
		if (skuList == null) {
			throw new ArgumentException("skuList 为空");
		}
		String erpNo = String.valueOf(Identities.getId());
		Integer total = 0;
		for(BdFormSku sku : skuList) {
			BdReceivingSku receivingSku = new BdReceivingSku();
			receivingSku.setErpNo(erpNo);
			receivingSku.setSkuCode(sku.getSkuCode());
			receivingSku.setSkuName(sku.getSkuName());
			receivingSku.setSendTotal(sku.getChangeTotal());
            total += sku.getChangeTotal();
            bdReceivingSkuWriteDao.insert(receivingSku);
		}
        BdReceiving bdReceiving = new BdReceiving();
		bdReceiving.setErpNo(erpNo);
        bdReceiving.setOrderNo(bdForm.getOrderNo());
        bdReceiving.setExpressCode(bdForm.getExpressCode());
        bdReceiving.setSendStoreNo(bdForm.getStoreNo());
        bdReceiving.setReceiveStoreNo(bdForm.getReceiveStoreNo());
        bdReceiving.setSendType(1);
        bdReceiving.setStatus(1);
        bdReceiving.setActSendTotal(0);
        bdReceiving.setSendTotal(total);
        bdReceiving.setSendDate(new Date());
        bdReceiving.setExpectReceiveDate(bdForm.getExpressDate());
        bdReceivingWriteDao.insert(bdReceiving);
	}

	private void insertOrUpdateStockInv(List<BdFormSku> skuList, BdForm bdForm, Integer type) throws Exception {
		if (skuList == null) {
			throw new ArgumentException("skuList 为空");
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
				stockInv.setStockCounterFreeze(-sku.getChangeTotal());
			} else if (bdForm.getFormType() == 3) {//调拨
				if (type == 1) {//调拨-同意
					stockInv.setStockDepot(-sku.getChangeTotal());
				} else {//调拨-拒绝（撤销）
					stockInv.setStockDepotEnabled(sku.getChangeTotal());
				}
				stockInv.setStockDepotFreeze(-sku.getChangeTotal());
			} else if (bdForm.getFormType() == 4) {//异常调整
				if (type == 1) {//异常调整-同意
					if (sku.getChangeType() == 1) {
						stockInv.setStockDepot(sku.getChangeTotal());
						stockInv.setStockDepotEnabled(sku.getChangeTotal());
					} else if (sku.getChangeType() == 2) {
						stockInv.setStockDepot(-sku.getChangeTotal());
						stockInv.setStockDepotFreeze(-sku.getChangeTotal());
					}
				} else {//异常调整-拒绝（撤销）
					if (sku.getChangeType() == 2) {
						stockInv.setStockDepotEnabled(sku.getChangeTotal());
						stockInv.setStockDepotFreeze(-sku.getChangeTotal());
					}
				}
			}
			changeData(stockInv);
			bdStockInvWriteDao.insertOrUpdate(stockInv);
		}
	}

	/**
	 * 保存表单(提交)-总表
	 * @param  form
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Integer saveForm(SaveApplyForm form) throws Exception {
		//清除旧数据
		if (form.getId() != null) {
			bdFormWriteDao.delBdFormByApplyNo(form.getApplyNo());
			bdFormSkuWriteDao.del(form.getApplyNo());
			bdFormImageWriteDao.del(form.getApplyNo());
            bdFormDisposeWriteDao.del(form.getApplyNo());
		}
		String format = "yyyy-MM-dd";
		BdForm bdForm = new BdForm();
		bdForm.setApplicant(form.getApplicant());
		bdForm.setApplicantTel(form.getApplicantTel());
		bdForm.setApplyNo(form.getApplyNo());
		bdForm.setApplyDate(new Date());
		bdForm.setApplyUserNo(form.getUserNo());//申请的帐号NO
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
		bdForm.setDisposeStatusDesc("等待一级审批");
		bdForm.setIsBack(0);

		Integer insert;
		insert = bdFormWriteDao.insert(bdForm);
		if (form.getFormImageList() != null) {
			for (BdFormImage image : form.getFormImageList()) {
				image.setApplyNo(form.getApplyNo());
				image.setType(1);
				if (image.getImageUrl() == null || StringUtils.trim(image.getImageUrl()).equals(""))
					continue;
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
						stockInv.setStockCounterFreeze(sku.getChangeTotal());
					} else if (bdForm.getFormType() == 3) {//调拨
						stockInv.setStockDepotEnabled(- sku.getChangeTotal());
						stockInv.setStockDepotFreeze(sku.getChangeTotal());
					} else if (bdForm.getFormType() == 4 && sku.getChangeType() == 2) {//异常调整
						//异常单只有减少的时候才会去预减可用库存
						stockInv.setStockDepotEnabled(- sku.getChangeTotal());
						stockInv.setStockDepotFreeze(sku.getChangeTotal());
					}
					changeData(stockInv);
					bdStockInvWriteDao.insertOrUpdate(stockInv);
				}
			}
		}

		BdFormDispose formDispose = new BdFormDispose();
		formDispose.setApplyNo(bdForm.getApplyNo());
		formDispose.setDisposeType(1);
		formDispose.setDisposeUserName(form.getUserName());
		formDispose.setDisposeUserNo(form.getUserNo());
		formDispose.setDisposeDate(new Date());
		formDispose.setStoreNo(form.getStoreNo());
		formDispose.setStoreName(form.getStoreName());
		formDispose.setDisposeStatus("申请单提交");
		bdFormDisposeWriteDao.insert(formDispose);

		formDispose = new BdFormDispose();
		formDispose.setApplyNo(bdForm.getApplyNo());
		formDispose.setDisposeType(0);
		formDispose.setDisposeUserName(store.getUserName());
		formDispose.setDisposeUserNo(store.getUserNo());
		formDispose.setStoreNo(form.getStoreNo());
		formDispose.setStoreName(form.getStoreName());
		formDispose.setDisposeStatus("一级审批人");
		bdFormDisposeWriteDao.insert(formDispose);

        Integer currentLv = 2;
		String userNo = store.getUserNo();
		if (form.getFormType() == 1) {
            Integer lv = 2;
            addFlow(userNo, currentLv, lv, bdForm.getApplyNo(), form.getStoreNo(), form.getStoreName());
        } else if (form.getFormType() == 2) {
            Integer lv = 2;
            addFlow(userNo, currentLv, lv, bdForm.getApplyNo(), form.getStoreNo(), form.getStoreName());
        } else if (form.getFormType() == 3) {
            Integer lv = 2;
            addFlow(userNo, currentLv, lv, bdForm.getApplyNo(), form.getStoreNo(), form.getStoreName());
        } else if (form.getFormType() == 4) {
            Integer lv = 2;
            addFlow(userNo, currentLv, lv, bdForm.getApplyNo(), form.getStoreNo(), form.getStoreName());
        }

		return insert;
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer saveLocalForm(SaveApplyForm form) throws Exception {
		//清除旧数据
		if (form.getId() != null) {
			bdFormWriteDao.delBdForm(form.getId());
			bdFormSkuWriteDao.del(form.getApplyNo());
			bdFormImageWriteDao.del(form.getApplyNo());
		}

		String format = "yyyy-MM-dd";
		BdForm bdForm = new BdForm();
		bdForm.setApplicant(form.getApplicant());
		bdForm.setApplicantTel(form.getApplicantTel());
		bdForm.setApplyNo(form.getApplyNo());
		bdForm.setApplyDate(new Date());
		bdForm.setApplyUserNo(form.getUserNo());//申请的帐号NO
		bdForm.setStoreNo(form.getStoreNo());
		bdForm.setStoreName(form.getStoreName());
		bdForm.setRemark(form.getRemark());
		bdForm.setFormType(form.getFormType());
		bdForm.setDisposeStatus(3);//状态：保存未提交
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

		bdForm.setDisposeUserNo(form.getUserNo());
		bdForm.setDisposeStatusDesc("保存待提交");
		bdForm.setIsBack(0);

		Integer insert;
		insert = bdFormWriteDao.insert(bdForm);
		if (form.getFormImageList() != null) {
			for (BdFormImage image : form.getFormImageList()) {
				image.setApplyNo(form.getApplyNo());
				image.setType(1);
				insert = bdFormImageWriteDao.insert(image);
			}
		}

		if (form.getFormSkuList() != null) {
			for (BdFormSku sku : form.getFormSkuList()) {
				sku.setApplyNo(form.getApplyNo());
				insert = bdFormSkuWriteDao.insert(sku);
			}
		}
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

    public Integer sendFormMail() throws Exception {
    	List<FormJobBean> formList = bdFormWriteDao.getBdFormListForSendFormMail();
    	if (formList.size() > 0) {
			Map<String, String> mailMap = new HashMap<>();
			Integer[] idArr = new Integer[formList.size()];
			int i = 0;
			//邮箱去重
			for(FormJobBean formJobBean : formList) {
				mailMap.put(formJobBean.getEmail(), formJobBean.getEmail());
				idArr[i] = formJobBean.getId();
				i++;
			}
			StringBuffer notifyTo = new StringBuffer();
			for(Map.Entry<String, String> map : mailMap.entrySet()) {
				notifyTo.append(map.getValue()).append(";");
			}

			//发送邮件-审批提醒
			MailSender mailSender = MailSender.getInstance();
			MailInfo mailInfo = new MailInfo();
			mailInfo.setNotifyTo(notifyTo.toString());
			mailInfo.setSubject("审批提醒-测试");
			mailInfo.setContent("您有新的表单申请需要您审批！");
			mailInfo.setAttachFileNames(new String[]{});//添加附件
			mailSender.sendHtmlMail(mailInfo, 3);

			//更新邮件发送时间（避免重复发送）
			bdFormWriteDao.updateForSendFormMail(idArr);
		}
		return null;
	}

	// TODO 此处要严谨判断
	@Transactional(rollbackFor = Exception.class)
	public Integer changeAudit() throws Exception {
		List<FormJobBean> formList = bdFormWriteDao.getBdFormListForChangeAudit();
		if (formList.size() > 0) {
			for(FormJobBean formJobBean : formList) {
				MdUser user = mdUserWriteDao.getUserByUserNo(formJobBean.getDisposeUserNo());
				MdUser fUser = mdUserWriteDao.getUserByUserId(user.getUserPid());

				//更新表单
				FormJobForm formJobForm = new FormJobForm();
				formJobForm.setId(formJobBean.getId());
				formJobForm.setDisposeUserNo(fUser.getUserNo());
				formJobForm.setDisposeStatus(formJobBean.getDisposeStatus() + 1);
//				formJobForm.setDisposeGrade(formJobBean.getDisposeGrade() + 1);
				formJobForm.setUpdateDate(new Date());
				formJobForm.setSendMailDate(new Date());

				formJobForm.setDisposeStatusDesc("转下一级审批");
				bdFormWriteDao.updateForChangeAudit(formJobForm);

				//更新审批步骤
				BdFormDispose bdFormDispose = bdFormDisposeWriteDao.getDispose(formJobBean.getApplyNo(), user.getUserNo(), formJobBean.getStoreNo());
				bdFormDispose.setDisposeStatus("未审批-转交上级审批");
				bdFormDispose.setDisposeDate(new Date());
				bdFormDisposeWriteDao.update(bdFormDispose);

				bdFormDispose.setDisposeDate(null);
				bdFormDispose.setDisposeStatus("上级审批");
				bdFormDispose.setDisposeUserNo(fUser.getUserNo());
				bdFormDispose.setDisposeUserName(fUser.getUserName());
				bdFormDisposeWriteDao.insert(bdFormDispose);

				//发送邮件-审批提醒
				MailSender mailSender = MailSender.getInstance();
				MailInfo mailInfo = new MailInfo();
				mailInfo.setNotifyTo(fUser.getEmail());
				mailInfo.setSubject("审批提醒-测试");
				mailInfo.setContent("您有新的表单申请需要您审批！");
				mailInfo.setAttachFileNames(new String[]{});//添加附件
				mailSender.sendHtmlMail(mailInfo, 3);
			}

		}
		return null;
	}

	/** 审批退回 */
    @Transactional(rollbackFor = Exception.class)
	public Integer backFormDestroy(BdForm bdForm, ExecDisposeForm form) throws Exception {
        MdUser user = mdUserWriteDao.getUserByUserNo(form.getUserNo());
        BdFormDispose bdFormDispose = bdFormDisposeWriteDao.getDispose(bdForm.getApplyNo(), user.getUserNo(), bdForm.getStoreNo());
        bdFormDispose.setApplyNo(bdForm.getApplyNo());
        bdFormDispose.setDisposeDate(new Date());
        bdFormDispose.setRemark(form.getRemark());
        List<BdFormSku> skuList = bdFormSkuWriteDao.getFormSkuList(bdForm.getApplyNo());
        bdFormDispose.setDisposeType(4);
        bdFormDisposeWriteDao.update(bdFormDispose);
        bdFormDisposeWriteDao.del(bdFormDispose.getApplyNo());
        bdForm.setDisposeStatus(1);
        bdForm.setDisposeStatusDesc("退回待处理");
        bdForm.setIsBack(1);//退回标记
		bdForm.setDisposeResult(3);//结果-退回
		bdForm.setDisposeGrade(bdForm.getDisposeGrade() + 1);
		bdForm.setDisposeUserNo(bdForm.getApplyUserNo());
        if (bdForm.getFormType() != 1) {
            insertOrUpdateStockInv(skuList, bdForm, form.getType());
        }
        bdFormWriteDao.update(bdForm);
        return null;
    }

	@Transactional(rollbackFor = Exception.class)
    public Integer delBdForm(BdForm bdForm) throws Exception {
		bdFormWriteDao.delBdForm(bdForm.getId());
		bdFormSkuWriteDao.del(bdForm.getApplyNo());
		bdFormImageWriteDao.del(bdForm.getApplyNo());
    	return null;
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
		bdForm.setApplyUserNo(StringUtil.dbSafeString(bdForm.getApplyUserNo(), true, 40));
    }
}