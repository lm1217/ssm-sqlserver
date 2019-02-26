package com.iyeed.core.entity.stock;

import java.io.Serializable;

/**
 * 库存日志表
 * <p>Table: <strong>bd_stock_inv_log</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键自增长</td></tr>
 *   <tr><td>storeNo</td><td>{@link String}</td><td>store_no</td><td>varchar</td><td>门店号</td></tr>
 *   <tr><td>storeName</td><td>{@link String}</td><td>store_name</td><td>varchar</td><td>门店名称</td></tr>
 *   <tr><td>skuCode</td><td>{@link String}</td><td>sku_code</td><td>varchar</td><td>SKU编码</td></tr>
 *   <tr><td>skuName</td><td>{@link String}</td><td>sku_name</td><td>varchar</td><td>SKU名称</td></tr>
 *   <tr><td>type</td><td>{@link Integer}</td><td>type</td><td>int</td><td>1.店仓转柜面 2.柜面转店仓</td></tr>
 *   <tr><td>stockTotalBerore</td><td>{@link Integer}</td><td>stock_total_berore</td><td>int</td><td>库存总量变化前</td></tr>
 *   <tr><td>stockTotalAfter</td><td>{@link Integer}</td><td>stock_total_after</td><td>int</td><td>库存总量变化后</td></tr>
 *   <tr><td>moveNumber</td><td>{@link Integer}</td><td>move_number</td><td>int</td><td>变化数量</td></tr>
 *   <tr><td>inputDate</td><td>{@link java.util.Date}</td><td>input_date</td><td>datetime</td><td>放入时间</td></tr>
 * </table>
 *
 */
public class BdStockInvLog implements Serializable {

 	private Integer id;
 	private String storeNo;
 	private String storeName;
 	private String skuCode;
 	private String skuName;
 	private Integer type;
 	private Integer stockTotalBerore;
 	private Integer stockTotalAfter;
 	private Integer moveNumber;
 	private java.util.Date inputDate;
	private String brandNo;
	private String userNo;
	private Integer userType;

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
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
     * 获取SKU编码
     */
	public String getSkuCode(){
		return this.skuCode;
	}

	/**
     * 设置SKU编码
     */
	public void setSkuCode(String skuCode){
		this.skuCode = skuCode;
	}

	/**
     * 获取SKU名称
     */
	public String getSkuName(){
		return this.skuName;
	}

	/**
     * 设置SKU名称
     */
	public void setSkuName(String skuName){
		this.skuName = skuName;
	}

	/**
     * 获取1.店仓转柜面 2.柜面转店仓
     */
	public Integer getType(){
		return this.type;
	}

	/**
     * 设置1.店仓转柜面 2.柜面转店仓
     */
	public void setType(Integer type){
		this.type = type;
	}

	/**
     * 获取库存总量变化前
     */
	public Integer getStockTotalBerore(){
		return this.stockTotalBerore;
	}

	/**
     * 设置库存总量变化前
     */
	public void setStockTotalBerore(Integer stockTotalBerore){
		this.stockTotalBerore = stockTotalBerore;
	}

	/**
     * 获取库存总量变化后
     */
	public Integer getStockTotalAfter(){
		return this.stockTotalAfter;
	}

	/**
     * 设置库存总量变化后
     */
	public void setStockTotalAfter(Integer stockTotalAfter){
		this.stockTotalAfter = stockTotalAfter;
	}

	/**
     * 获取变化数量
     */
	public Integer getMoveNumber(){
		return this.moveNumber;
	}

	/**
     * 设置变化数量
     */
	public void setMoveNumber(Integer moveNumber){
		this.moveNumber = moveNumber;
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