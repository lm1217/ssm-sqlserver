package com.iyeed.core.entity.receive;

import java.io.Serializable;

/**
 * 收货表-SKU子表
 * <p>Table: <strong>bd_receiving_sku</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键自增长</td></tr>
 *   <tr><td>erpNo</td><td>{@link String}</td><td>erp_no</td><td>varchar</td><td>ERP_NO</td></tr>
 *   <tr><td>skuCode</td><td>{@link String}</td><td>sku_code</td><td>varchar</td><td>SKU编码</td></tr>
 *   <tr><td>skuName</td><td>{@link String}</td><td>sku_name</td><td>varchar</td><td>SKU名称</td></tr>
 *   <tr><td>sendTotal</td><td>{@link Integer}</td><td>send_total</td><td>int</td><td>发货总数</td></tr>
 *   <tr><td>actSendTotal</td><td>{@link Integer}</td><td>act_send_total</td><td>int</td><td>实际收货总数</td></tr>
 *   <tr><td>inputDate</td><td>{@link java.util.Date}</td><td>input_date</td><td>datetime</td><td>放入时间</td></tr>
 * </table>
 *
 */
public class BdReceivingSku implements Serializable {

 	private Integer id;
 	private String erpNo;
 	private String skuCode;
 	private String skuName;
 	private Integer sendTotal;
 	private Integer actSendTotal;
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
     * 获取ERP_NO
     */
	public String getErpNo(){
		return this.erpNo;
	}

	/**
     * 设置ERP_NO
     */
	public void setErpNo(String erpNo){
		this.erpNo = erpNo;
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
     * 获取发货总数
     */
	public Integer getSendTotal(){
		return this.sendTotal;
	}

	/**
     * 设置发货总数
     */
	public void setSendTotal(Integer sendTotal){
		this.sendTotal = sendTotal;
	}

	/**
     * 获取实际收货总数
     */
	public Integer getActSendTotal(){
		return this.actSendTotal;
	}

	/**
     * 设置实际收货总数
     */
	public void setActSendTotal(Integer actSendTotal){
		this.actSendTotal = actSendTotal;
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