package com.yinlian.wssc.web.po;

import java.math.BigDecimal;
import java.util.Date;

public class OrderBills {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderbills.id
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderbills.shopid
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    private Integer shopid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderbills.shopname
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    private String shopname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderbills.orderdate
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    private Date orderdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderbills.orderprice
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    private BigDecimal orderprice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderbills.status
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderbills.createdate
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    private Date createdate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderbills.id
     *
     * @return the value of orderbills.id
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderbills.id
     *
     * @param id the value for orderbills.id
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderbills.shopid
     *
     * @return the value of orderbills.shopid
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    public Integer getShopid() {
        return shopid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderbills.shopid
     *
     * @param shopid the value for orderbills.shopid
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderbills.shopname
     *
     * @return the value of orderbills.shopname
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    public String getShopname() {
        return shopname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderbills.shopname
     *
     * @param shopname the value for orderbills.shopname
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    public void setShopname(String shopname) {
        this.shopname = shopname == null ? null : shopname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderbills.orderdate
     *
     * @return the value of orderbills.orderdate
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    public Date getOrderdate() {
        return orderdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderbills.orderdate
     *
     * @param orderdate the value for orderbills.orderdate
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderbills.orderprice
     *
     * @return the value of orderbills.orderprice
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    public BigDecimal getOrderprice() {
        return orderprice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderbills.orderprice
     *
     * @param orderprice the value for orderbills.orderprice
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    public void setOrderprice(BigDecimal orderprice) {
        this.orderprice = orderprice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderbills.status
     *
     * @return the value of orderbills.status
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderbills.status
     *
     * @param status the value for orderbills.status
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderbills.createdate
     *
     * @return the value of orderbills.createdate
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderbills.createdate
     *
     * @param createdate the value for orderbills.createdate
     *
     * @mbggenerated Wed Oct 18 18:18:05 CST 2017
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}