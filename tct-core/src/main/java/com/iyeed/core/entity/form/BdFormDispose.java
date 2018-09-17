package com.iyeed.core.entity.form;

import java.io.Serializable;

/**
 * 
 * <p>Table: <strong>bd_form_dispose</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>自增长id</td></tr>
 *   <tr><td>applyNo</td><td>{@link String}</td><td>apply_no</td><td>varchar</td><td>申请号</td></tr>
 *   <tr><td>storeNo</td><td>{@link String}</td><td>store_no</td><td>varchar</td><td>门店号</td></tr>
 *   <tr><td>storeName</td><td>{@link String}</td><td>store_name</td><td>varchar</td><td>门店名称</td></tr>
 *   <tr><td>disposeUserNo</td><td>{@link String}</td><td>dispose_user_no</td><td>varchar</td><td>处理人编号</td></tr>
 *   <tr><td>disposeUserName</td><td>{@link String}</td><td>dispose_user_name</td><td>varchar</td><td>处理人名称</td></tr>
 *   <tr><td>disposeStatus</td><td>{@link String}</td><td>dispose_status</td><td>varchar</td><td>处理情况</td></tr>
 *   <tr><td>remark</td><td>{@link String}</td><td>remark</td><td>varchar</td><td>备注</td></tr>
 *   <tr><td>disposeDate</td><td>{@link java.util.Date}</td><td>dispose_date</td><td>datetime</td><td>处理时间</td></tr>
 *   <tr><td>inputDate</td><td>{@link java.util.Date}</td><td>input_date</td><td>datetime</td><td>放入时间</td></tr>
 * </table>
 *
 */
public class BdFormDispose implements Serializable {

 	private Integer id;
 	private String applyNo;
 	private String storeNo;
 	private String storeName;
 	private String disposeUserNo;
 	private String disposeUserName;
 	private String disposeStatus;
 	private Integer disposeType;
 	private String remark;
 	private java.util.Date disposeDate;
 	private java.util.Date inputDate;

	public Integer getDisposeType() {
		return disposeType;
	}

	public void setDisposeType(Integer disposeType) {
		this.disposeType = disposeType;
	}

	/**
     * 获取自增长id
     */
	public Integer getId(){
		return this.id;
	}

	/**
     * 设置自增长id
     */
	public void setId(Integer id){
		this.id = id;
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
     * 获取处理人编号
     */
	public String getDisposeUserNo(){
		return this.disposeUserNo;
	}

	/**
     * 设置处理人编号
     */
	public void setDisposeUserNo(String disposeUserNo){
		this.disposeUserNo = disposeUserNo;
	}

	/**
     * 获取处理人名称
     */
	public String getDisposeUserName(){
		return this.disposeUserName;
	}

	/**
     * 设置处理人名称
     */
	public void setDisposeUserName(String disposeUserName){
		this.disposeUserName = disposeUserName;
	}

	/**
     * 获取处理情况
     */
	public String getDisposeStatus(){
		return this.disposeStatus;
	}

	/**
     * 设置处理情况
     */
	public void setDisposeStatus(String disposeStatus){
		this.disposeStatus = disposeStatus;
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
     * 获取处理时间
     */
	public java.util.Date getDisposeDate(){
		return this.disposeDate;
	}
 		
	/**
     * 设置处理时间
     */
	public void setDisposeDate(java.util.Date disposeDate){
		this.disposeDate = disposeDate;
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
 }