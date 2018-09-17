package com.iyeed.core.entity.sku;

import java.io.Serializable;

/**
 * SKU表
 * <p>Table: <strong>md_sku</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键自增长</td></tr>
 *   <tr><td>skuCode</td><td>{@link String}</td><td>sku_code</td><td>varchar</td><td>SKU编码</td></tr>
 *   <tr><td>skuName</td><td>{@link String}</td><td>sku_name</td><td>varchar</td><td>SKU名称</td></tr>
 *   <tr><td>brandNo</td><td>{@link String}</td><td>brand_no</td><td>varchar</td><td>品牌号</td></tr>
 *   <tr><td>brandName</td><td>{@link String}</td><td>brand_name</td><td>varchar</td><td>品牌名称</td></tr>
 *   <tr><td>updateUserNo</td><td>{@link String}</td><td>update_user_no</td><td>varchar</td><td>更新人</td></tr>
 *   <tr><td>inputDate</td><td>{@link java.util.Date}</td><td>input_date</td><td>datetime</td><td>放入时间</td></tr>
 * </table>
 *
 */
public class MdSku implements Serializable {

 	private Integer id;
 	private String skuCode;
 	private String skuName;
 	private String brandNo;
 	private String brandName;
 	private String updateUserNo;
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
     * 获取更新人
     */
	public String getUpdateUserNo(){
		return this.updateUserNo;
	}

	/**
     * 设置更新人
     */
	public void setUpdateUserNo(String updateUserNo){
		this.updateUserNo = updateUserNo;
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