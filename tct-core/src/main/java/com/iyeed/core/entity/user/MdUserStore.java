package com.iyeed.core.entity.user;

import java.io.Serializable;

/**
 * 
 * <p>Table: <strong>md_user_store</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>自增长ID</td></tr>
 *   <tr><td>userNo</td><td>{@link String}</td><td>user_no</td><td>varchar</td><td>用户NO</td></tr>
 *   <tr><td>storeNo</td><td>{@link String}</td><td>store_no</td><td>varchar</td><td>门店NO</td></tr>
 * </table>
 *
 */
public class MdUserStore implements Serializable {

 	private Integer id;
 	private String userNo;
 	private String storeNo;


	/**
     * 获取自增长ID
     */
	public Integer getId(){
		return this.id;
	}

	/**
     * 设置自增长ID
     */
	public void setId(Integer id){
		this.id = id;
	}

	/**
     * 获取用户NO
     */
	public String getUserNo(){
		return this.userNo;
	}

	/**
     * 设置用户NO
     */
	public void setUserNo(String userNo){
		this.userNo = userNo;
	}

	/**
     * 获取门店NO
     */
	public String getStoreNo(){
		return this.storeNo;
	}

	/**
     * 设置门店NO
     */
	public void setStoreNo(String storeNo){
		this.storeNo = storeNo;
	}
 }