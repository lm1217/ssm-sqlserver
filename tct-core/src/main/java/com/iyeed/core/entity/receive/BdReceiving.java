package com.iyeed.core.entity.receive;

import java.io.Serializable;
import java.util.Date;

/**
 * 收货表-总表
 * <p>Table: <strong>bd_receiving</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键自增长</td></tr>
 *   <tr><td>erpNo</td><td>{@link String}</td><td>erp_no</td><td>varchar</td><td>ERP_NO</td></tr>
 *   <tr><td>orderNo</td><td>{@link String}</td><td>order_no</td><td>varchar</td><td>订单号</td></tr>
 *   <tr><td>expressCode</td><td>{@link String}</td><td>express_code</td><td>varchar</td><td>快递编号</td></tr>
 *   <tr><td>sendType</td><td>{@link Integer}</td><td>send_type</td><td>int</td><td>1.门店调拨 2.仓库发货</td></tr>
 *   <tr><td>sendStoreNo</td><td>{@link String}</td><td>send_store_no</td><td>varchar</td><td>发货方门店号</td></tr>
 *   <tr><td>receiveStoreNo</td><td>{@link String}</td><td>receive_store_no</td><td>varchar</td><td>收货方门店号</td></tr>
 *   <tr><td>sendTotal</td><td>{@link Integer}</td><td>send_total</td><td>int</td><td>发货总箱数</td></tr>
 *   <tr><td>actSendTotal</td><td>{@link Integer}</td><td>act_send_total</td><td>int</td><td>实际收货总箱数</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>int</td><td>收货状态</td></tr>
 *   <tr><td>sendDate</td><td>{@link java.util.Date}</td><td>send_date</td><td>datetime</td><td>发货时间</td></tr>
 *   <tr><td>expectReceiveDate</td><td>{@link java.util.Date}</td><td>expect_receive_date</td><td>datetime</td><td>预计收货时间</td></tr>
 *   <tr><td>inputDate</td><td>{@link java.util.Date}</td><td>input_date</td><td>datetime</td><td>放入时间</td></tr>
 * </table>
 *
 */
public class BdReceiving implements Serializable {

 	private Integer id;
 	private String erpNo;
 	private String orderNo;
 	private String brandNo;
 	private String expressCode;
 	private Integer sendType;
 	private String sendStoreNo;
 	private String receiveStoreNo;
 	private Integer sendTotal;
 	private Integer actSendTotal;
 	private Integer status;
 	private java.util.Date sendDate;
 	private java.util.Date expectReceiveDate;
 	private java.util.Date receiveDate;
 	private java.util.Date inputDate;
 	private String receiveUserNo;
 	private String pkgNo;
 	private String receiveType;

	/** vo额外参数 */
	private String sendStoreName;
	private String receiveStoreName;

	public String getReceiveUserNo() {
		return receiveUserNo;
	}

	public void setReceiveUserNo(String receiveUserNo) {
		this.receiveUserNo = receiveUserNo;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
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
     * 获取订单号
     */
	public String getOrderNo(){
		return this.orderNo;
	}

	/**
     * 设置订单号
     */
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
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
     * 获取1.门店调拨 2.仓库发货
     */
	public Integer getSendType(){
		return this.sendType;
	}

	/**
     * 设置1.门店调拨 2.仓库发货
     */
	public void setSendType(Integer sendType){
		this.sendType = sendType;
	}

	/**
     * 获取发货方门店号
     */
	public String getSendStoreNo(){
		return this.sendStoreNo;
	}

	/**
     * 设置发货方门店号
     */
	public void setSendStoreNo(String sendStoreNo){
		this.sendStoreNo = sendStoreNo;
	}

	/**
     * 获取收货方门店号
     */
	public String getReceiveStoreNo(){
		return this.receiveStoreNo;
	}

	/**
     * 设置收货方门店号
     */
	public void setReceiveStoreNo(String receiveStoreNo){
		this.receiveStoreNo = receiveStoreNo;
	}

	/**
     * 获取发货总箱数
     */
	public Integer getSendTotal(){
		return this.sendTotal;
	}

	/**
     * 设置发货总箱数
     */
	public void setSendTotal(Integer sendTotal){
		this.sendTotal = sendTotal;
	}

	/**
     * 获取实际收货总箱数
     */
	public Integer getActSendTotal(){
		return this.actSendTotal;
	}

	/**
     * 设置实际收货总箱数
     */
	public void setActSendTotal(Integer actSendTotal){
		this.actSendTotal = actSendTotal;
	}

	/**
     * 获取收货状态
     */
	public Integer getStatus(){
		return this.status;
	}

	/**
     * 设置收货状态
     */
	public void setStatus(Integer status){
		this.status = status;
	}
 		
	/**
     * 获取发货时间
     */
	public java.util.Date getSendDate(){
		return this.sendDate;
	}
 		
	/**
     * 设置发货时间
     */
	public void setSendDate(java.util.Date sendDate){
		this.sendDate = sendDate;
	}
 		
	/**
     * 获取预计收货时间
     */
	public java.util.Date getExpectReceiveDate(){
		return this.expectReceiveDate;
	}
 		
	/**
     * 设置预计收货时间
     */
	public void setExpectReceiveDate(java.util.Date expectReceiveDate){
		this.expectReceiveDate = expectReceiveDate;
	}

	/**
	 * 获取收货时间
	 */
	public Date getReceiveDate() {
		return receiveDate;
	}

	/**
	 * 设置收货时间
	 */
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
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

	public String getSendStoreName() {
		return sendStoreName;
	}

	public void setSendStoreName(String sendStoreName) {
		this.sendStoreName = sendStoreName;
	}

	public String getReceiveStoreName() {
		return receiveStoreName;
	}

	public void setReceiveStoreName(String receiveStoreName) {
		this.receiveStoreName = receiveStoreName;
	}

	public String getPkgNo() {
		return pkgNo;
	}

	public void setPkgNo(String pkgNo) {
		this.pkgNo = pkgNo;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}
}