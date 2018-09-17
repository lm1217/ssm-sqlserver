package com.iyeed.core.entity.express;

import java.io.Serializable;

/**
 * 快递公司信息表
 * <p>Table: <strong>md_express</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键自增长</td></tr>
 *   <tr><td>expressCode</td><td>{@link String}</td><td>express_code</td><td>varchar</td><td>快递编号</td></tr>
 *   <tr><td>expressName</td><td>{@link String}</td><td>express_name</td><td>varchar</td><td>快递名称</td></tr>
 *   <tr><td>inputDate</td><td>{@link java.util.Date}</td><td>input_date</td><td>datetime</td><td>放入时间</td></tr>
 * </table>
 *
 */
public class MdExpress implements Serializable {

 	private Integer id;
 	private String expressCode;
 	private String expressName;
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