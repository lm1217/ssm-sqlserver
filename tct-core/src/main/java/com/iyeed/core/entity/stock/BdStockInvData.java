package com.iyeed.core.entity.stock;

import java.io.Serializable;

/**
 * 业务-库存表
 * <p>Table: <strong>bd_stock_inv_data</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>storeNo</td><td>{@link String}</td><td>store_no</td><td>varchar</td><td>门店号</td></tr>
 *   <tr><td>storeName</td><td>{@link String}</td><td>store_name</td><td>varchar</td><td>门店名称</td></tr>
 *   <tr><td>skuCode</td><td>{@link String}</td><td>sku_code</td><td>varchar</td><td>SKU编码</td></tr>
 *   <tr><td>skuName</td><td>{@link String}</td><td>sku_name</td><td>varchar</td><td>SKU名称</td></tr>
 *   <tr><td>stockDepot</td><td>{@link Integer}</td><td>stock_depot</td><td>int</td><td>店仓库存总量</td></tr>
 *   <tr><td>stockDepotEnabled</td><td>{@link Integer}</td><td>stock_depot_enabled</td><td>int</td><td>店仓可用库存总量</td></tr>
 *   <tr><td>stockCounter</td><td>{@link Integer}</td><td>stock_counter</td><td>int</td><td>柜台库存总量</td></tr>
 *   <tr><td>stockCounterEnabled</td><td>{@link Integer}</td><td>stock_counter_enabled</td><td>int</td><td>柜台可用库存总量</td></tr>
 *   <tr><td>updateDate</td><td>{@link java.util.Date}</td><td>update_date</td><td>datetime</td><td>更新时间</td></tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键自增长</td></tr>
 *   <tr><td>stockDepotFreeze</td><td>{@link Integer}</td><td>stock_depot_freeze</td><td>int</td><td>店仓可用库存冻结总量</td></tr>
 *   <tr><td>stockCounterFreeze</td><td>{@link Integer}</td><td>stock_counter_freeze</td><td>int</td><td>柜面可用库存冻结总量</td></tr>
 *   <tr><td>brandNo</td><td>{@link String}</td><td>brand_no</td><td>varchar</td><td>品牌号</td></tr>
 *   <tr><td>userNo</td><td>{@link String}</td><td>user_no</td><td>varchar</td><td>用户编号</td></tr>
 *   <tr><td>userType</td><td>{@link Integer}</td><td>user_type</td><td>int</td><td>用户类型</td></tr>
 *   <tr><td>backupDate</td><td>{@link java.util.Date}</td><td>backup_date</td><td>datetime</td><td>备份日期</td></tr>
 * </table>
 *
 */
public class BdStockInvData implements Serializable {

 	private String storeNo;
 	private String storeName;
 	private String skuCode;
 	private String skuName;
 	private Integer stockDepot;
 	private Integer stockDepotEnabled;
 	private Integer stockCounter;
 	private Integer stockCounterEnabled;
 	private java.util.Date updateDate;
 	private Integer id;
 	private Integer stockDepotFreeze;
 	private Integer stockCounterFreeze;
 	private String brandNo;
 	private String userNo;
 	private Integer userType;
 	private java.util.Date backupDate;


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
     * 获取店仓库存总量
     */
	public Integer getStockDepot(){
		return this.stockDepot;
	}

	/**
     * 设置店仓库存总量
     */
	public void setStockDepot(Integer stockDepot){
		this.stockDepot = stockDepot;
	}

	/**
     * 获取店仓可用库存总量
     */
	public Integer getStockDepotEnabled(){
		return this.stockDepotEnabled;
	}

	/**
     * 设置店仓可用库存总量
     */
	public void setStockDepotEnabled(Integer stockDepotEnabled){
		this.stockDepotEnabled = stockDepotEnabled;
	}

	/**
     * 获取柜台库存总量
     */
	public Integer getStockCounter(){
		return this.stockCounter;
	}

	/**
     * 设置柜台库存总量
     */
	public void setStockCounter(Integer stockCounter){
		this.stockCounter = stockCounter;
	}

	/**
     * 获取柜台可用库存总量
     */
	public Integer getStockCounterEnabled(){
		return this.stockCounterEnabled;
	}

	/**
     * 设置柜台可用库存总量
     */
	public void setStockCounterEnabled(Integer stockCounterEnabled){
		this.stockCounterEnabled = stockCounterEnabled;
	}

	/**
     * 获取更新时间
     */
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
     * 设置更新时间
     */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
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
     * 获取店仓可用库存冻结总量
     */
	public Integer getStockDepotFreeze(){
		return this.stockDepotFreeze;
	}

	/**
     * 设置店仓可用库存冻结总量
     */
	public void setStockDepotFreeze(Integer stockDepotFreeze){
		this.stockDepotFreeze = stockDepotFreeze;
	}

	/**
     * 获取柜面可用库存冻结总量
     */
	public Integer getStockCounterFreeze(){
		return this.stockCounterFreeze;
	}

	/**
     * 设置柜面可用库存冻结总量
     */
	public void setStockCounterFreeze(Integer stockCounterFreeze){
		this.stockCounterFreeze = stockCounterFreeze;
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
     * 获取用户编号
     */
	public String getUserNo(){
		return this.userNo;
	}

	/**
     * 设置用户编号
     */
	public void setUserNo(String userNo){
		this.userNo = userNo;
	}

	/**
     * 获取用户类型
     */
	public Integer getUserType(){
		return this.userType;
	}

	/**
     * 设置用户类型
     */
	public void setUserType(Integer userType){
		this.userType = userType;
	}
 		
	/**
     * 获取备份日期
     */
	public java.util.Date getBackupDate(){
		return this.backupDate;
	}
 		
	/**
     * 设置备份日期
     */
	public void setBackupDate(java.util.Date backupDate){
		this.backupDate = backupDate;
	}
 }