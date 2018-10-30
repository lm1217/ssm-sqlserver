package com.iyeed.core.entity.stock;

import java.io.Serializable;

/**
 * 库存表
 * <p>Table: <strong>bd_stock_inv</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键自增长</td></tr>
 *   <tr><td>storeNo</td><td>{@link String}</td><td>store_no</td><td>varchar</td><td>门店号</td></tr>
 *   <tr><td>storeName</td><td>{@link String}</td><td>store_name</td><td>varchar</td><td>门店名称</td></tr>
 *   <tr><td>skuCode</td><td>{@link String}</td><td>sku_code</td><td>varchar</td><td>SKU编码</td></tr>
 *   <tr><td>skuName</td><td>{@link String}</td><td>sku_name</td><td>varchar</td><td>SKU名称</td></tr>
 *   <tr><td>stockDepot</td><td>{@link Integer}</td><td>stock_depot</td><td>int</td><td>店仓库存总量</td></tr>
 *   <tr><td>stockDepotEnabled</td><td>{@link Integer}</td><td>stock_depot_enabled</td><td>int</td><td>店仓可用库存总量</td></tr>
 *   <tr><td>stockCounter</td><td>{@link Integer}</td><td>stock_counter</td><td>int</td><td>柜台库存总量</td></tr>
 *   <tr><td>stockCounterEnabled</td><td>{@link Integer}</td><td>stock_counter_enabled</td><td>int</td><td>柜台可用库存总量</td></tr>
 *   <tr><td>updateDate</td><td>{@link java.util.Date}</td><td>update_date</td><td>datetime</td><td>更新时间</td></tr>
 * </table>
 *
 */
public class BdStockInv implements Serializable {

 	private Integer id;
 	private String storeNo;
 	private String storeName;
 	private String skuCode;
 	private String skuName;
 	private Integer stockDepot;
 	private Integer stockDepotEnabled;
 	private Integer stockCounter;
 	private Integer stockCounterEnabled;
 	private java.util.Date updateDate;
 	private Integer stockDepotFreeze;
 	private Integer stockCounterFreeze;

	public Integer getStockDepotFreeze() {
		return stockDepotFreeze;
	}

	public void setStockDepotFreeze(Integer stockDepotFreeze) {
		this.stockDepotFreeze = stockDepotFreeze;
	}

	public Integer getStockCounterFreeze() {
		return stockCounterFreeze;
	}

	public void setStockCounterFreeze(Integer stockCounterFreeze) {
		this.stockCounterFreeze = stockCounterFreeze;
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

	@Override
	public String toString() {
		return "BdStockInv{" +
				"id=" + id +
				", storeNo='" + storeNo + '\'' +
				", storeName='" + storeName + '\'' +
				", skuCode='" + skuCode + '\'' +
				", skuName='" + skuName + '\'' +
				", stockDepot=" + stockDepot +
				", stockDepotEnabled=" + stockDepotEnabled +
				", stockCounter=" + stockCounter +
				", stockCounterEnabled=" + stockCounterEnabled +
				", updateDate=" + updateDate +
				'}';
	}
}