package com.iyeed.core.entity.form;

import java.io.Serializable;

/**
 * 表单-图片子表
 * <p>Table: <strong>bd_form_image</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键自增长</td></tr>
 *   <tr><td>applyNo</td><td>{@link String}</td><td>apply_no</td><td>int</td><td>申请号</td></tr>
 *   <tr><td>imageUrl</td><td>{@link String}</td><td>image_url</td><td>varchar</td><td>图片URL</td></tr>
 *   <tr><td>inputDate</td><td>{@link java.util.Date}</td><td>input_date</td><td>datetime</td><td>放入时间</td></tr>
 * </table>
 *
 */
public class BdFormImage implements Serializable {

 	private Integer id;
 	private String applyNo;
 	private String imageUrl;
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

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	/**
     * 获取图片URL
     */
	public String getImageUrl(){
		return this.imageUrl;
	}

	/**
     * 设置图片URL
     */
	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
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