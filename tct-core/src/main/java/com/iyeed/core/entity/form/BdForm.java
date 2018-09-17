package com.iyeed.core.entity.form;

import java.io.Serializable;

/**
 * 表单-总表
 * <p>Table: <strong>bd_form</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键自增长ID</td></tr>
 *   <tr><td>storeNo</td><td>{@link String}</td><td>store_no</td><td>varchar</td><td>门店号</td></tr>
 *   <tr><td>storeName</td><td>{@link String}</td><td>store_name</td><td>varchar</td><td>门店名称</td></tr>
 *   <tr><td>applyNo</td><td>{@link String}</td><td>apply_no</td><td>varchar</td><td>申请号</td></tr>
 *   <tr><td>applicant</td><td>{@link String}</td><td>applicant</td><td>varchar</td><td>申请人</td></tr>
 *   <tr><td>applicantTel</td><td>{@link String}</td><td>applicant_tel</td><td>varchar</td><td>申请人电话</td></tr>
 *   <tr><td>remark</td><td>{@link String}</td><td>remark</td><td>varchar</td><td>备注</td></tr>
 *   <tr><td>applyDate</td><td>{@link java.util.Date}</td><td>apply_date</td><td>datetime</td><td>申请日期</td></tr>
 *   <tr><td>destroyType</td><td>{@link Integer}</td><td>destroy_type</td><td>int</td><td>销毁类型 1.已用完 2.破损 3.过期</td></tr>
 *   <tr><td>receiveStoreNo</td><td>{@link String}</td><td>receive_store_no</td><td>varchar</td><td>收货方门店号</td></tr>
 *   <tr><td>receiveStoreName</td><td>{@link String}</td><td>receive_store_name</td><td>varchar</td><td>收货方门店名称</td></tr>
 *   <tr><td>expressCode</td><td>{@link String}</td><td>express_code</td><td>varchar</td><td>快递编号</td></tr>
 *   <tr><td>expressName</td><td>{@link String}</td><td>express_name</td><td>varchar</td><td>快递名称</td></tr>
 *   <tr><td>orderNo</td><td>{@link String}</td><td>order_no</td><td>varchar</td><td>快递单号</td></tr>
 *   <tr><td>expressDate</td><td>{@link java.util.Date}</td><td>express_date</td><td>datetime</td><td>快递时间</td></tr>
 *   <tr><td>allotType</td><td>{@link Integer}</td><td>allot_type</td><td>int</td><td>调拨类型</td></tr>
 *   <tr><td>exceptionType</td><td>{@link Integer}</td><td>exception_type</td><td>int</td><td>异常(差异)类型 1.串号 2.失窃</td></tr>
 *   <tr><td>formType</td><td>{@link Integer}</td><td>form_type</td><td>int</td><td>表单类型 1.库存初始化 2.销毁申请单 3.调拨申请单 4.异常申请单</td></tr>
 *   <tr><td>inputDate</td><td>{@link java.util.Date}</td><td>input_date</td><td>datetime</td><td>放入时间</td></tr>
 *   <tr><td>inputUserNo</td><td>{@link String}</td><td>input_user_no</td><td>varchar</td><td>放入人</td></tr>
 * </table>
 *
 */
public class BdForm implements Serializable {

 	private Integer id;
 	private String storeNo;
 	private String storeName;
 	private String applyNo;
 	private String applicant;
 	private String applicantTel;
 	private String remark;
 	private java.util.Date applyDate;
 	private Integer destroyType;
 	private String receiveStoreNo;
 	private String receiveStoreName;
 	private String expressCode;
 	private String expressName;
 	private String orderNo;
 	private java.util.Date expressDate;
 	private Integer allotType;
 	private Integer exceptionType;
 	private Integer formType;
 	private java.util.Date inputDate;
 	private String inputUserNo;
 	private String disposeUserNo;
 	private Integer disposeGrade;
 	private Integer disposeStatus;
 	private String disposeStatusDesc;

	public String getDisposeStatusDesc() {
		return disposeStatusDesc;
	}

	public void setDisposeStatusDesc(String disposeStatusDesc) {
		this.disposeStatusDesc = disposeStatusDesc;
	}

	public String getDisposeUserNo() {
		return disposeUserNo;
	}

	public void setDisposeUserNo(String disposeUserNo) {
		this.disposeUserNo = disposeUserNo;
	}

	public Integer getDisposeGrade() {
		return disposeGrade;
	}

	public void setDisposeGrade(Integer disposeGrade) {
		this.disposeGrade = disposeGrade;
	}

	public Integer getDisposeStatus() {
		return disposeStatus;
	}

	public void setDisposeStatus(Integer disposeStatus) {
		this.disposeStatus = disposeStatus;
	}

	/**
     * 获取主键自增长ID
     */
	public Integer getId(){
		return this.id;
	}

	/**
     * 设置主键自增长ID
     */
	public void setId(Integer id){
		this.id = id;
	}

	/**
     * 获取门店号
     */
	public String getStoreNo(){
		return this.storeNo;
	}

	/**
     * 设置门店号
     */
	public void setStoreNo(String storeNo){
		this.storeNo = storeNo;
	}

	/**
     * 获取门店名称
     */
	public String getStoreName(){
		return this.storeName;
	}

	/**
     * 设置门店名称
     */
	public void setStoreName(String storeName){
		this.storeName = storeName;
	}

	/**
     * 获取申请号
     */
	public String getApplyNo(){
		return this.applyNo;
	}

	/**
     * 设置申请号
     */
	public void setApplyNo(String applyNo){
		this.applyNo = applyNo;
	}

	/**
     * 获取申请人
     */
	public String getApplicant(){
		return this.applicant;
	}

	/**
     * 设置申请人
     */
	public void setApplicant(String applicant){
		this.applicant = applicant;
	}

	/**
     * 获取申请人电话
     */
	public String getApplicantTel(){
		return this.applicantTel;
	}

	/**
     * 设置申请人电话
     */
	public void setApplicantTel(String applicantTel){
		this.applicantTel = applicantTel;
	}

	/**
     * 获取备注
     */
	public String getRemark(){
		return this.remark;
	}

	/**
     * 设置备注
     */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
     * 获取申请日期
     */
	public java.util.Date getApplyDate(){
		return this.applyDate;
	}

	/**
     * 设置申请日期
     */
	public void setApplyDate(java.util.Date applyDate){
		this.applyDate = applyDate;
	}

	/**
     * 获取销毁类型 1.已用完 2.破损 3.过期
     */
	public Integer getDestroyType(){
		return this.destroyType;
	}

	/**
     * 设置销毁类型 1.已用完 2.破损 3.过期
     */
	public void setDestroyType(Integer destroyType){
		this.destroyType = destroyType;
	}

	/**
     * 获取收货方门店号
     */
	public String getReceiveStoreNo(){
		return this.receiveStoreNo;
	}

	/**
     * 设置收货方门店号
     */
	public void setReceiveStoreNo(String receiveStoreNo){
		this.receiveStoreNo = receiveStoreNo;
	}

	/**
     * 获取收货方门店名称
     */
	public String getReceiveStoreName(){
		return this.receiveStoreName;
	}

	/**
     * 设置收货方门店名称
     */
	public void setReceiveStoreName(String receiveStoreName){
		this.receiveStoreName = receiveStoreName;
	}

	/**
     * 获取快递编号
     */
	public String getExpressCode(){
		return this.expressCode;
	}

	/**
     * 设置快递编号
     */
	public void setExpressCode(String expressCode){
		this.expressCode = expressCode;
	}

	/**
     * 获取快递名称
     */
	public String getExpressName(){
		return this.expressName;
	}

	/**
     * 设置快递名称
     */
	public void setExpressName(String expressName){
		this.expressName = expressName;
	}

	/**
     * 获取快递单号
     */
	public String getOrderNo(){
		return this.orderNo;
	}

	/**
     * 设置快递单号
     */
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}

	/**
     * 获取快递时间
     */
	public java.util.Date getExpressDate(){
		return this.expressDate;
	}

	/**
     * 设置快递时间
     */
	public void setExpressDate(java.util.Date expressDate){
		this.expressDate = expressDate;
	}

	/**
     * 获取调拨类型
     */
	public Integer getAllotType(){
		return this.allotType;
	}

	/**
     * 设置调拨类型
     */
	public void setAllotType(Integer allotType){
		this.allotType = allotType;
	}

	/**
     * 获取异常(差异)类型 1.串号 2.失窃
     */
	public Integer getExceptionType(){
		return this.exceptionType;
	}

	/**
     * 设置异常(差异)类型 1.串号 2.失窃
     */
	public void setExceptionType(Integer exceptionType){
		this.exceptionType = exceptionType;
	}

	/**
     * 获取表单类型 1.库存初始化 2.销毁申请单 3.调拨申请单 4.异常申请单
     */
	public Integer getFormType(){
		return this.formType;
	}

	/**
     * 设置表单类型 1.库存初始化 2.销毁申请单 3.调拨申请单 4.异常申请单
     */
	public void setFormType(Integer formType){
		this.formType = formType;
	}

	/**
     * 获取放入时间
     */
	public java.util.Date getInputDate(){
		return this.inputDate;
	}

	/**
     * 设置放入时间
     */
	public void setInputDate(java.util.Date inputDate){
		this.inputDate = inputDate;
	}

	/**
     * 获取放入人
     */
	public String getInputUserNo(){
		return this.inputUserNo;
	}

	/**
     * 设置放入人
     */
	public void setInputUserNo(String inputUserNo){
		this.inputUserNo = inputUserNo;
	}
 }