package com.iyeed.core.entity.system;

import java.io.Serializable;

/**
 * 
 * <p>Table: <strong>system_log</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键自增长</td></tr>
 *   <tr><td>module</td><td>{@link String}</td><td>module</td><td>varchar</td><td>模块</td></tr>
 *   <tr><td>ip</td><td>{@link String}</td><td>ip</td><td>varchar</td><td>ip地址</td></tr>
 *   <tr><td>commit</td><td>{@link String}</td><td>commit</td><td>varchar</td><td>执行结果</td></tr>
 *   <tr><td>inputDate</td><td>{@link java.util.Date}</td><td>input_date</td><td>datetime</td><td>记录时间</td></tr>
 *   <tr><td>targetName</td><td>{@link String}</td><td>target_name</td><td>varchar</td><td>包-类全称</td></tr>
 *   <tr><td>methodName</td><td>{@link String}</td><td>method_name</td><td>varchar</td><td>执行方法</td></tr>
 *   <tr><td>businessDesc</td><td>{@link String}</td><td>business_desc</td><td>varchar</td><td>业务描述</td></tr>
 *   <tr><td>params</td><td>{@link String}</td><td>params</td><td>text</td><td>请求参数记录</td></tr>
 *   <tr><td>exeTime</td><td>{@link Long}</td><td>exe_time</td><td>bigint</td><td>执行时间(毫秒)</td></tr>
 *   <tr><td>opUserNo</td><td>{@link String}</td><td>op_user_no</td><td>varchar</td><td>操作人记录</td></tr>
 * </table>
 *
 */
public class SystemLog implements Serializable {

 	private Integer id;
 	private String module;
 	private String ip;
 	private String commit;
 	private java.util.Date inputDate;
 	private String targetName;
 	private String methodName;
 	private String businessDesc;
 	private String requestParams;
 	private String responseParams;
 	private Long exeTime;
 	private String opUserNo;

	public String getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	public String getResponseParams() {
		return responseParams;
	}

	public void setResponseParams(String responseParams) {
		this.responseParams = responseParams;
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
     * 获取模块
     */
	public String getModule(){
		return this.module;
	}

	/**
     * 设置模块
     */
	public void setModule(String module){
		this.module = module;
	}

	/**
     * 获取ip地址
     */
	public String getIp(){
		return this.ip;
	}

	/**
     * 设置ip地址
     */
	public void setIp(String ip){
		this.ip = ip;
	}

	/**
     * 获取执行结果
     */
	public String getCommit(){
		return this.commit;
	}

	/**
     * 设置执行结果
     */
	public void setCommit(String commit){
		this.commit = commit;
	}

	/**
     * 获取记录时间
     */
	public java.util.Date getInputDate(){
		return this.inputDate;
	}

	/**
     * 设置记录时间
     */
	public void setInputDate(java.util.Date inputDate){
		this.inputDate = inputDate;
	}

	/**
     * 获取包-类全称
     */
	public String getTargetName(){
		return this.targetName;
	}

	/**
     * 设置包-类全称
     */
	public void setTargetName(String targetName){
		this.targetName = targetName;
	}

	/**
     * 获取执行方法
     */
	public String getMethodName(){
		return this.methodName;
	}

	/**
     * 设置执行方法
     */
	public void setMethodName(String methodName){
		this.methodName = methodName;
	}

	/**
     * 获取业务描述
     */
	public String getBusinessDesc(){
		return this.businessDesc;
	}

	/**
     * 设置业务描述
     */
	public void setBusinessDesc(String businessDesc){
		this.businessDesc = businessDesc;
	}

	/**
     * 获取执行时间(毫秒)
     */
	public Long getExeTime(){
		return this.exeTime;
	}

	/**
     * 设置执行时间(毫秒)
     */
	public void setExeTime(Long exeTime){
		this.exeTime = exeTime;
	}

	/**
     * 获取操作人记录
     */
	public String getOpUserNo(){
		return this.opUserNo;
	}

	/**
     * 设置操作人记录
     */
	public void setOpUserNo(String opUserNo){
		this.opUserNo = opUserNo;
	}
 }