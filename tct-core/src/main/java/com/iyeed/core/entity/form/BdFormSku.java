package com.iyeed.core.entity.form;

import java.io.Serializable;

/**
 * 表单-SKU子表 
 * <p>Table: <strong>bd_form_sku</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键自增长</td></tr>
 *   <tr><td>applyNo</td><td>{@link String}</td><td>apply_no</td><td>varchar</td><td>申请号</td></tr>
 *   <tr><td>skuCode</td><td>{@link String}</td><td>sku_code</td><td>varchar</td><td>SKU编码</td></tr>
 *   <tr><td>skuName</td><td>{@link String}</td><td>sku_name</td><td>varchar</td><td>SKU名称</td></tr>
 *   <tr><td>stockDepotTotal</td><td>{@link Integer}</td><td>stock_depot_total</td><td>int</td><td>店仓总量</td></tr>
 *   <tr><td>stockCounterTotal</td><td>{@link Integer}</td><td>stock_counter_total</td><td>int</td><td>柜面总量</td></tr>
 *   <tr><td>changeTotal</td><td>{@link Integer}</td><td>change_total</td><td>int</td><td>变动总量</td></tr>
 *   <tr><td>changeType</td><td>{@link Integer}</td><td>change_type</td><td>int</td><td>变动类型 1.加 2.减</td></tr>
 *   <tr><td>inputDate</td><td>{@link java.util.Date}</td><td>input_date</td><td>datetime</td><td>放入时间</td></tr>
 * </table>
 *
 */
public class BdFormSku implements Serializable {

 	private Integer id;
 	private String applyNo;
 	private String skuCode;
 	private String skuName;
 	private Integer stockDepotTotal;
 	private Integer stockCounterTotal;
 	private Integer changeTotal;
 	private Integer changeType;
 	private java.util.Date inputDate;


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
     * 获取店仓总量
     */
	public Integer getStockDepotTotal(){
		return this.stockDepotTotal;
	}

	/**
     * 设置店仓总量
     */
	public void setStockDepotTotal(Integer stockDepotTotal){
		this.stockDepotTotal = stockDepotTotal;
	}

	/**
     * 获取柜面总量
     */
	public Integer getStockCounterTotal(){
		return this.stockCounterTotal;
	}

	/**
     * 设置柜面总量
     */
	public void setStockCounterTotal(Integer stockCounterTotal){
		this.stockCounterTotal = stockCounterTotal;
	}

	/**
     * 获取变动总量
     */
	public Integer getChangeTotal(){
		return this.changeTotal;
	}

	/**
     * 设置变动总量
     */
	public void setChangeTotal(Integer changeTotal){
		this.changeTotal = changeTotal;
	}

	/**
     * 获取变动类型 1.加 2.减
     */
	public Integer getChangeType(){
		return this.changeType;
	}

	/**
     * 设置变动类型 1.加 2.减
     */
	public void setChangeType(Integer changeType){
		this.changeType = changeType;
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