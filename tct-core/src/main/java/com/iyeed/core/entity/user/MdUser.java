package com.iyeed.core.entity.user;

import java.io.Serializable;

/**
 * 
 * <p>Table: <strong>md_user</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键自增长ID</td></tr>
 *   <tr><td>userId</td><td>{@link String}</td><td>user_id</td><td>varchar</td><td>用户ID</td></tr>
 *   <tr><td>userNo</td><td>{@link String}</td><td>user_no</td><td>varchar</td><td>用户编号</td></tr>
 *   <tr><td>userName</td><td>{@link String}</td><td>user_name</td><td>varchar</td><td>用户名称</td></tr>
 *   <tr><td>userPid</td><td>{@link String}</td><td>user_pid</td><td>varchar</td><td>用户父ID</td></tr>
 *   <tr><td>post</td><td>{@link String}</td><td>post</td><td>varchar</td><td>职位</td></tr>
 *   <tr><td>email</td><td>{@link String}</td><td>email</td><td>varchar</td><td>邮箱</td></tr>
 *   <tr><td>inputDate</td><td>{@link java.util.Date}</td><td>input_date</td><td>datetime</td><td>放入时间</td></tr>
 * </table>
 *
 */
public class MdUser implements Serializable {

 	private Integer id;
 	private String userId;
 	private String userNo;
 	private String userName;
 	private String userPid;
 	private String post;
 	private String email;
 	private String storeNo;
 	private java.util.Date inputDate;

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	/**
     * 获取主键自增长ID
     */
	public Integer getId(){
		return this.id;
	}

	/**
     * 设置主键自增长ID
     */
	public void setId(Integer id){
		this.id = id;
	}

	/**
     * 获取用户ID
     */
	public String getUserId(){
		return this.userId;
	}

	/**
     * 设置用户ID
     */
	public void setUserId(String userId){
		this.userId = userId;
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
     * 获取用户名称
     */
	public String getUserName(){
		return this.userName;
	}

	/**
     * 设置用户名称
     */
	public void setUserName(String userName){
		this.userName = userName;
	}

	/**
     * 获取用户父ID
     */
	public String getUserPid(){
		return this.userPid;
	}

	/**
     * 设置用户父ID
     */
	public void setUserPid(String userPid){
		this.userPid = userPid;
	}

	/**
     * 获取职位
     */
	public String getPost(){
		return this.post;
	}

	/**
     * 设置职位
     */
	public void setPost(String post){
		this.post = post;
	}

	/**
     * 获取邮箱
     */
	public String getEmail(){
		return this.email;
	}

	/**
     * 设置邮箱
     */
	public void setEmail(String email){
		this.email = email;
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