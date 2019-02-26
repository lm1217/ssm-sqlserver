package com.iyeed.model.form;

import com.iyeed.core.ConstantsEJS;
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
import com.iyeed.core.entity.system.SystemUser;
import com.iyeed.core.entity.user.MdUser;
import com.iyeed.core.exception.ArgumentException;
import com.iyeed.core.mail.MailInfo;
import com.iyeed.core.mail.MailSender;
import com.iyeed.core.utils.DateUtils;
import com.iyeed.core.utils.Identities;
import com.iyeed.model.BaseModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BdFormModel extends BaseModel {
	private static final Logger log = LoggerFactory.getLogger(BdFormModel.class);

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

	public List<GetDestroyFormListBean> getDestroyFormList(Map<String, Object> queryMap,
													  Integer start, Integer size) throws Exception {
		return bdFormWriteDao.getDestroyFormList(queryMap, start, size);
	}

	public Integer getDestroyFormListCount(Map<String, Object> queryMap) throws Exception{
		return bdFormWriteDao.getDestroyFormListCount(queryMap);
	}

    public List<GetDestroyTheftFormListBean> getDestroyTheftFormList(Map<String, Object> queryMap,
                                                           Integer start, Integer size) throws Exception {
        return bdFormWriteDao.getDestroyTheftFormList(queryMap, start, size);
    }

    public Integer getDestroyTheftFormListCount(Map<String, Object> queryMap) throws Exception{
        return bdFormWriteDao.getDestroyTheftFormListCount(queryMap);
    }

	public List<GetDSIFormListBean> getDSIFormList(Map<String, Object> queryMap,
												   Integer start, Integer size) throws Exception {
		return bdFormWriteDao.getDSIFormList(queryMap, start, size);
	}

	public Integer getDSIFormListCount(Map<String, Object> queryMap) throws Exception{
		return bdFormWriteDao.getDSIFormListCount(queryMap);
	}

	public List<ExceptionReportBean> getExceptionReportList(Map<String, Object> queryMap,
													  Integer start, Integer size) throws Exception {
		return bdFormWriteDao.getExceptionReportList(queryMap, start, size);
	}

	public Integer getExceptionReportListCount(Map<String, Object> queryMap) throws Exception{
		return bdFormWriteDao.getExceptionReportListCount(queryMap);
	}

	public List<StockReportBean> getStockReportList(Map<String, Object> queryMap,
															Integer start, Integer size) throws Exception {
		return bdFormWriteDao.getStockReportList(queryMap, start, size);
	}

	public Integer getStockReportListCount(Map<String, Object> queryMap) throws Exception{
		return bdFormWriteDao.getStockReportListCount(queryMap);
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
		MdStore store = mdStoreWriteDao.getStoreByStoreNo(bdForm.getStoreNo());
		SystemUser systemUser = systemUserWriteDao.getByUserNo(bdForm.getApplyUserNo());
		form.setUserType(systemUser.getUserType());
		MdUser user = mdUserWriteDao.getUserByUserNo(bdForm.getDisposeUserNo());
		BdFormDispose bdFormDispose = bdFormDisposeWriteDao.getDispose(bdForm.getApplyNo(), user.getUserNo(), bdForm.getStoreNo());
		bdFormDispose.setApplyNo(bdForm.getApplyNo());
		bdFormDispose.setDisposeDate(new Date());
		bdFormDispose.setRemark(form.getRemark());
		List<BdFormSku> skuList = bdFormSkuWriteDao.getFormSkuList(bdForm.getApplyNo());
		//处理计数，默认为0
		bdForm.setDisposeGrade(bdForm.getDisposeGrade() + 1);
		if (form.getType() == 2) {//拒绝处理
			bdForm.setDisposeStatus(0);
			if (form.getIsBrandAdmin() != null && form.getIsBrandAdmin() == 1) {
				bdForm.setDisposeStatusDesc("品牌管理员-拒绝");
				bdFormDispose.setDisposeStatus("品牌管理员-拒绝");
			} else {
				bdForm.setDisposeStatusDesc("拒绝");
			}
			bdForm.setDisposeStatusDesc("拒绝");
			bdForm.setDisposeResult(2);//结果-拒绝
			bdFormDispose.setDisposeType(3);
			bdFormDisposeWriteDao.update(bdFormDispose);
			bdFormDisposeWriteDao.del(bdFormDispose.getApplyNo());
			if (bdForm.getFormType() != 1) {
				insertOrUpdateStockInv(skuList, bdForm, form);
			}
		} else {//同意处理
			MdUser fUser = null;

			if (bdForm.getFormType() == 2 && bdForm.getDestroyType() == 1) {
				//正常销毁流程(原因为正常使用完)只有一级审批，CM特殊有二级审批，指定二级审批人为xihe
				if (store.getBrandNo().equals("5") && !user.getUserId().equals("1549959")) {
					fUser = mdUserWriteDao.getUserByUserId("1549959");
				}
			} else if (store.getBrandNo().equals("5") && bdForm.getFormType() == 2 && !user.getUserId().equals("1549959") && bdForm.getDisposeGrade() < 3) {
				// CM品牌特殊处理(只有销毁审批才是由xihe的审批) 三级审批
				fUser = mdUserWriteDao.getUserByUserId(user.getUserPid());
				if (fUser == null && bdForm.getDisposeGrade() == 1) {
					fUser = mdUserWriteDao.getUserByUserId("1549959");
				} else if (bdForm.getDisposeGrade() == 2) {
					fUser = mdUserWriteDao.getUserByUserId("1549959");
				}
			} else if (bdForm.getDisposeGrade() < 2) {// 其他所有审批流程都是二级审批
				fUser = mdUserWriteDao.getUserByUserId(user.getUserPid());
			}

			if (fUser == null) {
				bdForm.setDisposeStatus(0);
				if (form.getIsBrandAdmin() != null && form.getIsBrandAdmin() == 1) {
					bdForm.setDisposeStatusDesc("品牌管理员-通过");
					bdFormDispose.setDisposeStatus("品牌管理员-通过");
				} else {
					bdForm.setDisposeStatusDesc("通过");
				}
				bdForm.setDisposeResult(1);//结果-通过
				bdFormDispose.setDisposeType(2);
				bdFormDisposeWriteDao.update(bdFormDispose);
				if (bdForm.getFormType() == 3) {//调拨单审批通过要在收货表插入数据
					insertReceiveByAllot(skuList, bdForm);
				}
				insertOrUpdateStockInv(skuList, bdForm, form);
			} else {
				if (form.getIsBrandAdmin() != null && form.getIsBrandAdmin() == 1) {
					bdForm.setDisposeStatusDesc("品牌管理员-通过");
					bdFormDispose.setDisposeStatus("品牌管理员-通过");
				} else {
					bdForm.setDisposeStatusDesc("通过");
				}
				bdForm.setDisposeUserNo(fUser.getUserNo());
				bdForm.setDisposeStatus(2);
				bdFormDispose.setDisposeType(2);
				bdFormDisposeWriteDao.update(bdFormDispose);
                if (bdForm.getDisposeGrade() + 1 == 2) {
                    bdForm.setDisposeStatusDesc("等待二级审批");
                } else if (bdForm.getDisposeGrade() + 1 == 3) {
                    bdForm.setDisposeStatusDesc("等待三级审批");
                }

				// 多线程解决邮件发送超时阻塞主线程执行时间慢的问题
				MdUser finalFUser = fUser;
				Thread thread = new Thread() {
                	public void run() {
						// 邮件发送
						try {
							MailSender mailSender = MailSender.getInstance();
							MailInfo mailInfo = new MailInfo();
							mailInfo.setNotifyTo(finalFUser.getUserEmail());
//							mailInfo.setNotifyCc("112538201@qq.com");
							String strHtml = "";
							if (bdForm.getFormType() == 1) {
								//发送邮件-审批提醒-初始化
								mailInfo.setSubject("[Tester Control Tower]库存初始化\"" + bdForm.getApplyNo() + "\"审批");
								strHtml = "尊敬的用户您好：<br/>" +
										"&nbsp;&nbsp;&nbsp;&nbsp;门店“" + store.getStoreName() + "”，店号：" + store.getStoreNo() + "，品牌：" + store.getBrandName() + " 正在做tester库存初始化，需要您审批，" +
										"请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
							} else if (bdForm.getFormType() == 2) {
								//发送邮件-审批提醒-销毁单
								mailInfo.setSubject("[Tester Control Tower]销毁申请单\"" + bdForm.getApplyNo() + "\"审批");
								strHtml = "尊敬的用户您好：<br/>" +
										"&nbsp;&nbsp;&nbsp;&nbsp;门店“" + store.getStoreName() + "”，店号：" + store.getStoreNo() + "，品牌：" + store.getBrandName() + " 提交了tester销毁申请，需要您审批，" +
										"请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
							} else if (bdForm.getFormType() == 3) {
								//发送邮件-审批提醒-调拨单
								mailInfo.setSubject("[Tester Control Tower]调拨申请单\"" + bdForm.getApplyNo() + "\"审批");
								strHtml = "尊敬的用户您好：<br/>" +
										"&nbsp;&nbsp;&nbsp;&nbsp;门店“" + store.getStoreName() + "”，店号：" + store.getStoreNo() + "，品牌：" + store.getBrandName() + " 提交了tester调拨申请，需要您审批，" +
										"请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
							} else if (bdForm.getFormType() == 4) {
								//发送邮件-审批提醒-异常单
								mailInfo.setSubject("[Tester Control Tower]异常申请单\"" + bdForm.getApplyNo() + "\"审批");
								strHtml = "尊敬的用户您好：<br/>" +
										"&nbsp;&nbsp;&nbsp;&nbsp;门店“" + store.getStoreName() + "”，店号：" + store.getStoreNo() + "，品牌：" + store.getBrandName() + " 提交了tester异常申请，需要您审批，" +
										"请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
							}
                            strHtml += "<br/>支持电话: 15921533102 李书增";
							mailInfo.setContent(strHtml);
							mailInfo.setAttachFileNames(new String[]{});//添加附件
							mailSender.sendHtmlMail(mailInfo, 3);
						} catch (Exception e) {
							log.error("邮件发送失败", e.getMessage());
						}
					}
				};
				thread.start();
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
		bdReceiving.setPkgNo(erpNo);
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
        bdReceiving.setBrandNo(bdForm.getBrandNo());
        bdReceivingWriteDao.insert(bdReceiving);
	}

	private void insertOrUpdateStockInv(List<BdFormSku> skuList, BdForm bdForm, ExecDisposeForm form) throws Exception {
		if (skuList == null) {
			throw new ArgumentException("skuList 为空");
		}
    	for(BdFormSku sku : skuList) {
			BdStockInv stockInv = new BdStockInv();
			stockInv.setStoreNo(bdForm.getStoreNo());
			stockInv.setStoreName(bdForm.getStoreName());
			stockInv.setSkuCode(sku.getSkuCode());
			stockInv.setSkuName(sku.getSkuName());
			stockInv.setBrandNo(bdForm.getBrandNo());
			stockInv.setUserNo(bdForm.getApplyUserNo());
			stockInv.setUserType(form.getUserType());
			if (bdForm.getFormType() == 1 && form.getType() == 1) {//库存初始化（只有同意才执行）
				stockInv.setStockDepot(sku.getStockDepotTotal());
				stockInv.setStockDepotEnabled(sku.getStockDepotTotal());
				stockInv.setStockCounter(sku.getStockCounterTotal());
				stockInv.setStockCounterEnabled(sku.getStockCounterTotal());
			} else if (bdForm.getFormType() == 2) {//销毁
				if (form.getType() == 1) {//销毁-同意
					stockInv.setStockCounter(-sku.getChangeTotal());
				} else {//销毁-撤销
					stockInv.setStockCounterEnabled(sku.getChangeTotal());
				}
				stockInv.setStockCounterFreeze(-sku.getChangeTotal());
			} else if (bdForm.getFormType() == 3) {//调拨
				if (form.getType() == 1) {//调拨-同意
					stockInv.setStockDepot(-sku.getChangeTotal());
				} else {//调拨-拒绝（撤销）
					stockInv.setStockDepotEnabled(sku.getChangeTotal());
				}
				stockInv.setStockDepotFreeze(-sku.getChangeTotal());
			} else if (bdForm.getFormType() == 4) {//异常调整
				if (form.getType() == 1) {//异常调整-同意
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
		bdForm.setBrandNo(store.getBrandNo());

		Integer insert;
		insert = bdFormWriteDao.insert(bdForm);
		SystemUser systemUser = systemUserWriteDao.getByUserNo(bdForm.getApplyUserNo());
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

				//库存变化
				if (bdForm.getFormType() != 1) {
					BdStockInv stockInv = new BdStockInv();
					stockInv.setStoreNo(bdForm.getStoreNo());
					stockInv.setStoreName(bdForm.getStoreName());
					stockInv.setSkuCode(sku.getSkuCode());
					stockInv.setSkuName(sku.getSkuName());
					stockInv.setBrandNo(bdForm.getBrandNo());
					stockInv.setUserNo(systemUser.getUserNo());
					stockInv.setUserType(systemUser.getUserType());
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
			bdFormSkuWriteDao.insertList(form.getFormSkuList());
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
            addFlow(userNo, currentLv, lv, bdForm.getApplyNo(), form.getStoreNo(), form.getStoreName(), store.getBrandNo(), form.getFormType());
        } else if (form.getFormType() == 2) { // 只有CM的销毁单才有三级审批
			if (form.getDestroyType() == 1) {// 所有销毁单销毁原因为正常使用完的，只需要一级审批，CM品牌特殊，会有二级审批且审批人为xihe
				if (store.getBrandNo().equals("5")) {
					MdUser fUser = mdUserWriteDao.getUserByUserNo("1549959");// xihe
					formDispose = new BdFormDispose();
					formDispose.setApplyNo(bdForm.getApplyNo());
					formDispose.setDisposeType(0);
					formDispose.setDisposeUserName(fUser.getUserName());
					formDispose.setDisposeUserNo(fUser.getUserNo());
					formDispose.setStoreNo(store.getStoreNo());
					formDispose.setStoreName(store.getStoreName());
					formDispose.setDisposeStatus("二级审批人");
					bdFormDisposeWriteDao.insert(formDispose);
				}
			} else {
				Integer lv = 2;
				addFlow(userNo, currentLv, lv, bdForm.getApplyNo(), form.getStoreNo(), form.getStoreName(), store.getBrandNo(), form.getFormType());
			}
		} else if (form.getFormType() == 3) {
            Integer lv = 2;
            addFlow(userNo, currentLv, lv, bdForm.getApplyNo(), form.getStoreNo(), form.getStoreName(), store.getBrandNo(), form.getFormType());
        } else if (form.getFormType() == 4) {
            Integer lv = 2;
            addFlow(userNo, currentLv, lv, bdForm.getApplyNo(), form.getStoreNo(), form.getStoreName(), store.getBrandNo(), form.getFormType());
        }

		return insert;
	}

	// 保存待提交
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

        MdStore store = mdStoreWriteDao.getStoreByStoreNo(form.getStoreNo());
		bdForm.setDisposeUserNo(form.getUserNo());
		bdForm.setDisposeStatusDesc("保存待提交");
		bdForm.setIsBack(0);
        bdForm.setBrandNo(store.getBrandNo());

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
				checkFormSkuData(sku);
				sku.setApplyNo(form.getApplyNo());
			}
			insert = bdFormSkuWriteDao.insertList(form.getFormSkuList());
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

	/**
	 * 定时发送邮件提醒（销毁单第一级审批人45天内未审批则跳过，每7天邮件提醒一次，第二级和其他流程都是7天未审批跳过，每3天邮件提醒一次）
	 */
    public Integer sendFormMail() throws Exception {
		List<FormJobBean> formList = null;
		// 销毁单一级审批特殊处理（每7天邮件提醒一次）
		formList = bdFormWriteDao.getBdFormListForDestroySendFormMail();
		sendMail(formList);
		// 其他流程或二级审批（每3天邮件提醒一次）
    	formList = bdFormWriteDao.getBdFormListForSendFormMail();
		sendMail(formList);
		return null;
	}
	private void sendMail(List<FormJobBean> formList) throws Exception {
		if (formList.size() > 0) {
			//发送邮件-审批提醒
			Integer[] idArr = new Integer[formList.size()];
			MailSender mailSender = MailSender.getInstance();
			String strHtml = "";
			int i = 0;
			for(FormJobBean formJobBean : formList) {
				idArr[i] = formJobBean.getId();
				i++;
				// 多线程解决邮件发送超时阻塞主线程执行时间慢的问题
				Thread thread = new Thread() {
					public void run() {
						//发送邮件-审批提醒
						MailInfo mailInfo = new MailInfo();
						String strHtml = "";
						if (formJobBean.getFormType() == 1) {
							//发送邮件-审批提醒-初始化
							mailInfo.setSubject("[Tester Control Tower]库存初始化\"" + formJobBean.getApplyNo() + "\"审批");
							strHtml = "尊敬的用户您好：<br/>" +
									"&nbsp;&nbsp;&nbsp;&nbsp;门店“" + formJobBean.getStoreName() + "”，店号：" + formJobBean.getStoreNo() + "，品牌：" + formJobBean.getBrandName() + " 正在做tester库存初始化，需要您审批，" +
									"请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
						} else if (formJobBean.getFormType() == 2) {
							//发送邮件-审批提醒-销毁单
							mailInfo.setSubject("[Tester Control Tower]销毁申请单\"" + formJobBean.getApplyNo() + "\"审批");
							strHtml = "尊敬的用户您好：<br/>" +
									"&nbsp;&nbsp;&nbsp;&nbsp;门店“" + formJobBean.getStoreName() + "”，店号：" + formJobBean.getStoreNo() + "，品牌：" + formJobBean.getBrandName() + " 提交了tester销毁申请，需要您审批，" +
									"请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
						} else if (formJobBean.getFormType() == 3) {
							//发送邮件-审批提醒-调拨单
							mailInfo.setSubject("[Tester Control Tower]调拨申请单\"" + formJobBean.getApplyNo() + "\"审批");
							strHtml = "尊敬的用户您好：<br/>" +
									"&nbsp;&nbsp;&nbsp;&nbsp;门店“" + formJobBean.getStoreName() + "”，店号：" + formJobBean.getStoreNo() + "，品牌：" + formJobBean.getBrandName() + " 提交了tester调拨申请，需要您审批，" +
									"请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
						} else if (formJobBean.getFormType() == 4) {
							//发送邮件-审批提醒-异常单
							mailInfo.setSubject("[Tester Control Tower]异常申请单\"" + formJobBean.getApplyNo() + "\"审批");
							strHtml = "尊敬的用户您好：<br/>" +
									"&nbsp;&nbsp;&nbsp;&nbsp;门店“" + formJobBean.getStoreName() + "”，店号：" + formJobBean.getStoreNo() + "，品牌：" + formJobBean.getBrandName() + " 提交了tester异常申请，需要您审批，" +
									"请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
						}
						mailInfo.setNotifyTo(formJobBean.getUserEmail());
//						mailInfo.setNotifyCc("112538201@qq.com");
						mailInfo.setContent(strHtml);
						mailInfo.setAttachFileNames(new String[]{});//添加附件
						try {
							mailSender.sendHtmlMail(mailInfo, 3);
						} catch (Exception e) {
							log.error("邮件发送失败:{}", e.getMessage());
						}
					}
				};
				thread.start();
			}

			//更新邮件发送时间（避免重复发送）
			bdFormWriteDao.updateForSendFormMail(idArr);
		}
	}

	/**
	 * 定时审批检查，超时跳级（销毁单第一级审批人45天内未审批则跳过，每7天邮件提醒一次，第二级和其他流程都是7天未审批跳过，每3天邮件提醒一次）
	 */
	@Transactional(rollbackFor = Exception.class)
	public Integer changeAudit() throws Exception {
		List<FormJobBean> formList = null;
		// 销毁单一级审批特殊处理（45天内未审批则跳过）
		formList = bdFormWriteDao.getBdFormListForDestroyChangeAudit();
		changeAuditSendMail(formList);
		// 其他流程或二级审批（7天未审批跳过）
		formList = bdFormWriteDao.getBdFormListForChangeAudit();
		changeAuditSendMail(formList);
		return null;
	}
	private void changeAuditSendMail(List<FormJobBean> formList) throws Exception {
		if (formList.size() > 0) {
			MailSender mailSender = MailSender.getInstance();
			for(FormJobBean formJobBean : formList) {
				MdUser user = mdUserWriteDao.getUserByUserNo(formJobBean.getDisposeUserNo());
				MdUser fUser = null;
				formJobBean.setDisposeGrade(formJobBean.getDisposeGrade() + 1);
				if (formJobBean.getFormType() == 2 && formJobBean.getDestroyType() == 1) {
					//正常销毁流程(原因为正常使用完)只有一级审批，CM特殊有二级审批，指定二级审批人为xihe
					if (formJobBean.getBrandNo().equals("5") && !user.getUserId().equals("1549959")) {
						fUser = mdUserWriteDao.getUserByUserId("1549959");
					}
				} else if (formJobBean.getBrandNo().equals("5") && formJobBean.getFormType() == 2 && !user.getUserId().equals("1549959") && formJobBean.getDisposeGrade() < 3) {
					// CM品牌特殊处理(只有销毁审批才是由xihe的审批) 三级审批
					fUser = mdUserWriteDao.getUserByUserId(user.getUserPid());
					if (fUser == null && formJobBean.getDisposeGrade() == 1) {
						fUser = mdUserWriteDao.getUserByUserId("1549959");
					} else if (formJobBean.getDisposeGrade() == 2) {
						fUser = mdUserWriteDao.getUserByUserId("1549959");
					}
				} else if (formJobBean.getDisposeGrade() < 2) {// 其他所有审批流程都是二级审批
					fUser = mdUserWriteDao.getUserByUserId(user.getUserPid());
				}

				if (fUser != null) {
					//更新表单
					FormJobForm formJobForm = new FormJobForm();
					formJobForm.setId(formJobBean.getId());
					formJobForm.setDisposeUserNo(fUser.getUserNo());
					formJobForm.setDisposeGrade(formJobBean.getDisposeGrade());
					formJobForm.setUpdateDate(new Date());
					formJobForm.setSendMailDate(new Date());
					if (formJobBean.getDisposeGrade() == 1) {
						formJobForm.setDisposeStatusDesc("等待二级审批");
					} else if (formJobBean.getDisposeGrade() == 2) {
						formJobForm.setDisposeStatusDesc("等待三级审批");
					}
					bdFormWriteDao.updateForChangeAudit(formJobForm);

					//更新审批步骤
					BdFormDispose bdFormDispose = bdFormDisposeWriteDao.getDispose(formJobBean.getApplyNo(), user.getUserNo(), formJobBean.getStoreNo());
					bdFormDispose.setDisposeStatus("未审批-转交上级审批");
					bdFormDispose.setDisposeDate(new Date());
					bdFormDisposeWriteDao.update(bdFormDispose);
					MdUser mdUser = fUser;
					// 多线程解决邮件发送超时阻塞主线程执行时间慢的问题
					Thread thread = new Thread() {
						public void run() {
							//发送邮件-审批提醒
							MailInfo mailInfo = new MailInfo();
							String strHtml = "";
							if (formJobBean.getFormType() == 1) {
								//发送邮件-审批提醒-初始化
								mailInfo.setSubject("[Tester Control Tower]库存初始化\"" + formJobBean.getApplyNo() + "\"审批");
								strHtml = "尊敬的用户您好：<br/>" +
										"&nbsp;&nbsp;&nbsp;&nbsp;门店“" + formJobBean.getStoreName() + "”，店号：" + formJobBean.getStoreNo() + "，品牌：" + formJobBean.getBrandName() + " 正在做tester库存初始化，需要您审批，" +
										"请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
							} else if (formJobBean.getFormType() == 2) {
								//发送邮件-审批提醒-销毁单
								mailInfo.setSubject("[Tester Control Tower]销毁申请单\"" + formJobBean.getApplyNo() + "\"审批");
								strHtml = "尊敬的用户您好：<br/>" +
										"&nbsp;&nbsp;&nbsp;&nbsp;门店“" + formJobBean.getStoreName() + "”，店号：" + formJobBean.getStoreNo() + "，品牌：" + formJobBean.getBrandName() + " 提交了tester销毁申请，需要您审批，" +
										"请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
							} else if (formJobBean.getFormType() == 3) {
								//发送邮件-审批提醒-调拨单
								mailInfo.setSubject("[Tester Control Tower]调拨申请单\"" + formJobBean.getApplyNo() + "\"审批");
								strHtml = "尊敬的用户您好：<br/>" +
										"&nbsp;&nbsp;&nbsp;&nbsp;门店“" + formJobBean.getStoreName() + "”，店号：" + formJobBean.getStoreNo() + "，品牌：" + formJobBean.getBrandName() + " 提交了tester调拨申请，需要您审批，" +
										"请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
							} else if (formJobBean.getFormType() == 4) {
								//发送邮件-审批提醒-异常单
								mailInfo.setSubject("[Tester Control Tower]异常申请单\"" + formJobBean.getApplyNo() + "\"审批");
								strHtml = "尊敬的用户您好：<br/>" +
										"&nbsp;&nbsp;&nbsp;&nbsp;门店“" + formJobBean.getStoreName() + "”，店号：" + formJobBean.getStoreNo() + "，品牌：" + formJobBean.getBrandName() + " 提交了tester异常申请，需要您审批，" +
										"请登录<a href=\"" + ConstantsEJS.WEB_HOST + "\" target=\"_blank\">" + ConstantsEJS.WEB_HOST + "</a>  审批。";
							}
							mailInfo.setNotifyTo(mdUser.getUserEmail());
//							mailInfo.setNotifyCc("112538201@qq.com");
							mailInfo.setContent(strHtml);
							mailInfo.setAttachFileNames(new String[]{});//添加附件
							try {
								mailSender.sendHtmlMail(mailInfo, 3);
							} catch (Exception e) {
								log.error("邮件发送失败:{}", e.getMessage());
							}
						}
					};
					thread.start();
				}
			}
		}
	}

	/** 审批退回 */
    @Transactional(rollbackFor = Exception.class)
	public Integer backFormDispose(BdForm bdForm, ExecDisposeForm form) throws Exception {
		SystemUser systemUser = systemUserWriteDao.getByUserNo(bdForm.getApplyUserNo());
		form.setUserType(systemUser.getUserType());
        MdUser user = mdUserWriteDao.getUserByUserNo(bdForm.getDisposeUserNo());
        BdFormDispose bdFormDispose = bdFormDisposeWriteDao.getDispose(bdForm.getApplyNo(), user.getUserNo(), bdForm.getStoreNo());
        bdFormDispose.setApplyNo(bdForm.getApplyNo());
        bdFormDispose.setDisposeDate(new Date());
        bdFormDispose.setRemark(form.getRemark());
        List<BdFormSku> skuList = bdFormSkuWriteDao.getFormSkuList(bdForm.getApplyNo());
        bdFormDispose.setDisposeType(4);
        bdForm.setDisposeStatus(1);
		if (form.getIsBrandAdmin() != null && form.getIsBrandAdmin() == 1) {
			bdForm.setDisposeStatusDesc("品牌管理员-退回待处理");
			bdFormDispose.setDisposeStatus("品牌管理员-退回待处理");
		} else {
			bdForm.setDisposeStatusDesc("退回待处理");
		}

        bdForm.setIsBack(1);//退回标记
		bdForm.setDisposeResult(3);//结果-退回
		bdForm.setDisposeGrade(bdForm.getDisposeGrade() + 1);
		bdForm.setDisposeUserNo(bdForm.getApplyUserNo());
        if (bdForm.getFormType() != 1) {
            insertOrUpdateStockInv(skuList, bdForm, form);
        }
		bdFormDisposeWriteDao.update(bdFormDispose);
		bdFormDisposeWriteDao.del(bdFormDispose.getApplyNo());
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