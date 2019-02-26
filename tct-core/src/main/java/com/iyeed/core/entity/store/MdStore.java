package com.iyeed.core.entity.store;

import java.io.Serializable;

/**
 * 门店表
 * <p>Table: <strong>md_store</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键自增长</td></tr>
 *   <tr><td>storeNo</td><td>{@link String}</td><td>store_no</td><td>varchar</td><td>门店号</td></tr>
 *   <tr><td>storeName</td><td>{@link String}</td><td>store_name</td><td>varchar</td><td>门店名称</td></tr>
 *   <tr><td>brandNo</td><td>{@link String}</td><td>brand_no</td><td>varchar</td><td>品牌号</td></tr>
 *   <tr><td>brandName</td><td>{@link String}</td><td>brand_name</td><td>varchar</td><td>品牌名称</td></tr>
 *   <tr><td>brandLogo</td><td>{@link String}</td><td>brand_logo</td><td>varchar</td><td>品牌logo</td></tr>
 *   <tr><td>inputDate</td><td>{@link java.util.Date}</td><td>input_date</td><td>datetime</td><td>放入时间</td></tr>
 * </table>
 *
 */
public class MdStore implements Serializable {

 	private Integer id;
 	private String storeId;
 	private String storeNo;
 	private String storeCode;
 	private String storeName;
 	private String brandNo;
 	private String brandCode;
 	private String brandName;
 	private String userNo;
 	private String userName;
 	private java.util.Date inputDate;

    public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
     * 获取主键自增长
     */
	public Integer getId(){
		return this.id;
	}

	/**
     * 设置主键自增长
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
     * 获取品牌号
     */
	public String getBrandNo(){
		return this.brandNo;
	}

	/**
     * 设置品牌号
     */
	public void setBrandNo(String brandNo){
		this.brandNo = brandNo;
	}

	/**
     * 获取品牌名称
     */
	public String getBrandName(){
		return this.brandName;
	}

	/**
     * 设置品牌名称
     */
	public void setBrandName(String brandName){
		this.brandName = brandName;
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