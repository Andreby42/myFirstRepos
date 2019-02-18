package com.yinlian.api.app.dto;

import java.util.Date;

import com.yinlian.wssc.web.util.DateUtil;

/**
 * api优惠卷返回字段
 * @author Administrator
 *
 */
public class CouponDto {

	private Integer id;                //领用ID
	private Integer couponid;          //优惠卷ID
	private String couponnum;          //优惠卷编号
	private String couponname;         //优惠卷名称
	private Double facevalue;           //优惠卷面值
	private Integer couponissuetype;   //发放类型
	private Integer couponusetype;     //优惠卷使用类型
	private Integer usetypeid;         //使用对象ID
	private Integer coupontype;        //优惠卷类型
	private Double fullreductionvalue;  //满额
	private Date providetime;          //使用开始时间
	private Integer shopid;            //所属店铺ID
	private String shopname;           //所属店铺名称
	private Date gettime;              //领取时间
	private Date usetime;              //使用时间
	private Boolean isuser;            //是否使用
	private Integer userid;            //领取用户
	private Date outtime;              //过期时间
	private Double actface;             //实际使用金额
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCouponid() {
		return couponid;
	}
	public void setCouponid(Integer couponid) {
		this.couponid = couponid;
	}
	public String getCouponnum() {
		return couponnum;
	}
	public void setCouponnum(String couponnum) {
		this.couponnum = couponnum;
	}
	public String getCouponname() {
		return couponname;
	}
	public void setCouponname(String couponname) {
		this.couponname = couponname;
	}
	public Double getFacevalue() {
		return facevalue;
	}
	public void setFacevalue(Double facevalue) {
		this.facevalue = facevalue;
	}
	public Integer getCouponissuetype() {
		return couponissuetype;
	}
	public void setCouponissuetype(Integer couponissuetype) {
		this.couponissuetype = couponissuetype;
	}
	public Integer getCouponusetype() {
		return couponusetype;
	}
	public void setCouponusetype(Integer couponusetype) {
		this.couponusetype = couponusetype;
	}
	public Integer getUsetypeid() {
		return usetypeid;
	}
	public void setUsetypeid(Integer usetypeid) {
		this.usetypeid = usetypeid;
	}
	public Integer getCoupontype() {
		return coupontype;
	}
	public void setCoupontype(Integer coupontype) {
		this.coupontype = coupontype;
	}
	public Double getFullreductionvalue() {
		return fullreductionvalue;
	}
	public void setFullreductionvalue(Double fullreductionvalue) {
		this.fullreductionvalue = fullreductionvalue;
	}
	public String getProvidetime() {
		return DateUtil.dateConvert(this.providetime);
	}
	public void setProvidetime(Date providetime) {
		this.providetime = providetime;
	}
	public Integer getShopid() {
		return shopid;
	}
	public void setShopid(Integer shopid) {
		this.shopid = shopid;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getGettime() {
		return DateUtil.dateConvert(this.gettime);
	}
	public void setGettime(Date gettime) {
		this.gettime = gettime;
	}
	public String getUsetime() {
		return DateUtil.dateConvert(this.usetime);
	}
	public void setUsetime(Date usetime) {
		this.usetime = usetime;
	}
	public Boolean getIsuser() {
		return isuser;
	}
	public void setIsuser(Boolean isuser) {
		this.isuser = isuser;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getOuttime() {
		return DateUtil.dateConvert(this.outtime);
	}
	public void setOuttime(Date outtime) {
		this.outtime = outtime;
	}
	public Double getActface() {
		return actface;
	}
	public void setActface(Double actface) {
		this.actface = actface;
	}
	
}
