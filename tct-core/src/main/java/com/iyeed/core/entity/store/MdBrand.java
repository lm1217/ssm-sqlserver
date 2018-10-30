package com.iyeed.core.entity.store;

import java.io.Serializable;

/**
 * 
 * <p>Table: <strong>md_brand</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>brandNo</td><td>{@link String}</td><td>brand_no</td><td>varchar</td><td>brandNo</td></tr>
 *   <tr><td>brandCode</td><td>{@link String}</td><td>brand_code</td><td>varchar</td><td>brandCode</td></tr>
 *   <tr><td>brandName</td><td>{@link String}</td><td>brand_name</td><td>varchar</td><td>brandName</td></tr>
 *   <tr><td>brandLogo</td><td>{@link String}</td><td>brand_logo</td><td>varchar</td><td>brandLogo</td></tr>
 * </table>
 *
 */
public class MdBrand implements Serializable {

 	private Integer id;
 	private String brandNo;
 	private String brandCode;
 	private String brandName;
 	private String brandLogo;


	/**
     * 获取id
     */
	public Integer getId(){
		return this.id;
	}

	/**
     * 设置id
     */
	public void setId(Integer id){
		this.id = id;
	}

	/**
     * 获取brandNo
     */
	public String getBrandNo(){
		return this.brandNo;
	}

	/**
     * 设置brandNo
     */
	public void setBrandNo(String brandNo){
		this.brandNo = brandNo;
	}

	/**
     * 获取brandCode
     */
	public String getBrandCode(){
		return this.brandCode;
	}

	/**
     * 设置brandCode
     */
	public void setBrandCode(String brandCode){
		this.brandCode = brandCode;
	}

	/**
     * 获取brandName
     */
	public String getBrandName(){
		return this.brandName;
	}

	/**
     * 设置brandName
     */
	public void setBrandName(String brandName){
		this.brandName = brandName;
	}

	/**
     * 获取brandLogo
     */
	public String getBrandLogo(){
		return this.brandLogo;
	}

	/**
     * 设置brandLogo
     */
	public void setBrandLogo(String brandLogo){
		this.brandLogo = brandLogo;
	}
 }