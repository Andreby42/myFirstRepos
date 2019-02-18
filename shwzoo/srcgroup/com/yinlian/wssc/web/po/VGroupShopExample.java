package com.yinlian.wssc.web.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VGroupShopExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	public VGroupShopExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andIdIsNull() {
			addCriterion("id is null");
			return (Criteria) this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("id is not null");
			return (Criteria) this;
		}

		public Criteria andIdEqualTo(Integer value) {
			addCriterion("id =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(Integer value) {
			addCriterion("id <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(Integer value) {
			addCriterion("id >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("id >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(Integer value) {
			addCriterion("id <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(Integer value) {
			addCriterion("id <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<Integer> values) {
			addCriterion("id in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<Integer> values) {
			addCriterion("id not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(Integer value1, Integer value2) {
			addCriterion("id between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(Integer value1, Integer value2) {
			addCriterion("id not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andImgurlIsNull() {
			addCriterion("imgurl is null");
			return (Criteria) this;
		}

		public Criteria andImgurlIsNotNull() {
			addCriterion("imgurl is not null");
			return (Criteria) this;
		}

		public Criteria andImgurlEqualTo(String value) {
			addCriterion("imgurl =", value, "imgurl");
			return (Criteria) this;
		}

		public Criteria andImgurlNotEqualTo(String value) {
			addCriterion("imgurl <>", value, "imgurl");
			return (Criteria) this;
		}

		public Criteria andImgurlGreaterThan(String value) {
			addCriterion("imgurl >", value, "imgurl");
			return (Criteria) this;
		}

		public Criteria andImgurlGreaterThanOrEqualTo(String value) {
			addCriterion("imgurl >=", value, "imgurl");
			return (Criteria) this;
		}

		public Criteria andImgurlLessThan(String value) {
			addCriterion("imgurl <", value, "imgurl");
			return (Criteria) this;
		}

		public Criteria andImgurlLessThanOrEqualTo(String value) {
			addCriterion("imgurl <=", value, "imgurl");
			return (Criteria) this;
		}

		public Criteria andImgurlLike(String value) {
			addCriterion("imgurl like", value, "imgurl");
			return (Criteria) this;
		}

		public Criteria andImgurlNotLike(String value) {
			addCriterion("imgurl not like", value, "imgurl");
			return (Criteria) this;
		}

		public Criteria andImgurlIn(List<String> values) {
			addCriterion("imgurl in", values, "imgurl");
			return (Criteria) this;
		}

		public Criteria andImgurlNotIn(List<String> values) {
			addCriterion("imgurl not in", values, "imgurl");
			return (Criteria) this;
		}

		public Criteria andImgurlBetween(String value1, String value2) {
			addCriterion("imgurl between", value1, value2, "imgurl");
			return (Criteria) this;
		}

		public Criteria andImgurlNotBetween(String value1, String value2) {
			addCriterion("imgurl not between", value1, value2, "imgurl");
			return (Criteria) this;
		}

		public Criteria andSidIsNull() {
			addCriterion("sid is null");
			return (Criteria) this;
		}

		public Criteria andSidIsNotNull() {
			addCriterion("sid is not null");
			return (Criteria) this;
		}

		public Criteria andSidEqualTo(Integer value) {
			addCriterion("sid =", value, "sid");
			return (Criteria) this;
		}

		public Criteria andSidNotEqualTo(Integer value) {
			addCriterion("sid <>", value, "sid");
			return (Criteria) this;
		}

		public Criteria andSidGreaterThan(Integer value) {
			addCriterion("sid >", value, "sid");
			return (Criteria) this;
		}

		public Criteria andSidGreaterThanOrEqualTo(Integer value) {
			addCriterion("sid >=", value, "sid");
			return (Criteria) this;
		}

		public Criteria andSidLessThan(Integer value) {
			addCriterion("sid <", value, "sid");
			return (Criteria) this;
		}

		public Criteria andSidLessThanOrEqualTo(Integer value) {
			addCriterion("sid <=", value, "sid");
			return (Criteria) this;
		}

		public Criteria andSidIn(List<Integer> values) {
			addCriterion("sid in", values, "sid");
			return (Criteria) this;
		}

		public Criteria andSidNotIn(List<Integer> values) {
			addCriterion("sid not in", values, "sid");
			return (Criteria) this;
		}

		public Criteria andSidBetween(Integer value1, Integer value2) {
			addCriterion("sid between", value1, value2, "sid");
			return (Criteria) this;
		}

		public Criteria andSidNotBetween(Integer value1, Integer value2) {
			addCriterion("sid not between", value1, value2, "sid");
			return (Criteria) this;
		}

		public Criteria andTitleIsNull() {
			addCriterion("title is null");
			return (Criteria) this;
		}

		public Criteria andTitleIsNotNull() {
			addCriterion("title is not null");
			return (Criteria) this;
		}

		public Criteria andTitleEqualTo(String value) {
			addCriterion("title =", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleNotEqualTo(String value) {
			addCriterion("title <>", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleGreaterThan(String value) {
			addCriterion("title >", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleGreaterThanOrEqualTo(String value) {
			addCriterion("title >=", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleLessThan(String value) {
			addCriterion("title <", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleLessThanOrEqualTo(String value) {
			addCriterion("title <=", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleLike(String value) {
			addCriterion("title like", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleNotLike(String value) {
			addCriterion("title not like", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleIn(List<String> values) {
			addCriterion("title in", values, "title");
			return (Criteria) this;
		}

		public Criteria andTitleNotIn(List<String> values) {
			addCriterion("title not in", values, "title");
			return (Criteria) this;
		}

		public Criteria andTitleBetween(String value1, String value2) {
			addCriterion("title between", value1, value2, "title");
			return (Criteria) this;
		}

		public Criteria andTitleNotBetween(String value1, String value2) {
			addCriterion("title not between", value1, value2, "title");
			return (Criteria) this;
		}

		public Criteria andEndtimeIsNull() {
			addCriterion("endtime is null");
			return (Criteria) this;
		}

		public Criteria andEndtimeIsNotNull() {
			addCriterion("endtime is not null");
			return (Criteria) this;
		}

		public Criteria andEndtimeEqualTo(Date value) {
			addCriterion("endtime =", value, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeNotEqualTo(Date value) {
			addCriterion("endtime <>", value, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeGreaterThan(Date value) {
			addCriterion("endtime >", value, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeGreaterThanOrEqualTo(Date value) {
			addCriterion("endtime >=", value, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeLessThan(Date value) {
			addCriterion("endtime <", value, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeLessThanOrEqualTo(Date value) {
			addCriterion("endtime <=", value, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeIn(List<Date> values) {
			addCriterion("endtime in", values, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeNotIn(List<Date> values) {
			addCriterion("endtime not in", values, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeBetween(Date value1, Date value2) {
			addCriterion("endtime between", value1, value2, "endtime");
			return (Criteria) this;
		}

		public Criteria andEndtimeNotBetween(Date value1, Date value2) {
			addCriterion("endtime not between", value1, value2, "endtime");
			return (Criteria) this;
		}

		public Criteria andStarttimeIsNull() {
			addCriterion("starttime is null");
			return (Criteria) this;
		}

		public Criteria andStarttimeIsNotNull() {
			addCriterion("starttime is not null");
			return (Criteria) this;
		}

		public Criteria andStarttimeEqualTo(Date value) {
			addCriterion("starttime =", value, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeNotEqualTo(Date value) {
			addCriterion("starttime <>", value, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeGreaterThan(Date value) {
			addCriterion("starttime >", value, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeGreaterThanOrEqualTo(Date value) {
			addCriterion("starttime >=", value, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeLessThan(Date value) {
			addCriterion("starttime <", value, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeLessThanOrEqualTo(Date value) {
			addCriterion("starttime <=", value, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeIn(List<Date> values) {
			addCriterion("starttime in", values, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeNotIn(List<Date> values) {
			addCriterion("starttime not in", values, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeBetween(Date value1, Date value2) {
			addCriterion("starttime between", value1, value2, "starttime");
			return (Criteria) this;
		}

		public Criteria andStarttimeNotBetween(Date value1, Date value2) {
			addCriterion("starttime not between", value1, value2, "starttime");
			return (Criteria) this;
		}

		public Criteria andIsowerIsNull() {
			addCriterion("isower is null");
			return (Criteria) this;
		}

		public Criteria andIsowerIsNotNull() {
			addCriterion("isower is not null");
			return (Criteria) this;
		}

		public Criteria andIsowerEqualTo(Integer value) {
			addCriterion("isower =", value, "isower");
			return (Criteria) this;
		}

		public Criteria andIsowerNotEqualTo(Integer value) {
			addCriterion("isower <>", value, "isower");
			return (Criteria) this;
		}

		public Criteria andIsowerGreaterThan(Integer value) {
			addCriterion("isower >", value, "isower");
			return (Criteria) this;
		}

		public Criteria andIsowerGreaterThanOrEqualTo(Integer value) {
			addCriterion("isower >=", value, "isower");
			return (Criteria) this;
		}

		public Criteria andIsowerLessThan(Integer value) {
			addCriterion("isower <", value, "isower");
			return (Criteria) this;
		}

		public Criteria andIsowerLessThanOrEqualTo(Integer value) {
			addCriterion("isower <=", value, "isower");
			return (Criteria) this;
		}

		public Criteria andIsowerIn(List<Integer> values) {
			addCriterion("isower in", values, "isower");
			return (Criteria) this;
		}

		public Criteria andIsowerNotIn(List<Integer> values) {
			addCriterion("isower not in", values, "isower");
			return (Criteria) this;
		}

		public Criteria andIsowerBetween(Integer value1, Integer value2) {
			addCriterion("isower between", value1, value2, "isower");
			return (Criteria) this;
		}

		public Criteria andIsowerNotBetween(Integer value1, Integer value2) {
			addCriterion("isower not between", value1, value2, "isower");
			return (Criteria) this;
		}

		public Criteria andNumIsNull() {
			addCriterion("num is null");
			return (Criteria) this;
		}

		public Criteria andNumIsNotNull() {
			addCriterion("num is not null");
			return (Criteria) this;
		}

		public Criteria andNumEqualTo(Integer value) {
			addCriterion("num =", value, "num");
			return (Criteria) this;
		}

		public Criteria andNumNotEqualTo(Integer value) {
			addCriterion("num <>", value, "num");
			return (Criteria) this;
		}

		public Criteria andNumGreaterThan(Integer value) {
			addCriterion("num >", value, "num");
			return (Criteria) this;
		}

		public Criteria andNumGreaterThanOrEqualTo(Integer value) {
			addCriterion("num >=", value, "num");
			return (Criteria) this;
		}

		public Criteria andNumLessThan(Integer value) {
			addCriterion("num <", value, "num");
			return (Criteria) this;
		}

		public Criteria andNumLessThanOrEqualTo(Integer value) {
			addCriterion("num <=", value, "num");
			return (Criteria) this;
		}

		public Criteria andNumIn(List<Integer> values) {
			addCriterion("num in", values, "num");
			return (Criteria) this;
		}

		public Criteria andNumNotIn(List<Integer> values) {
			addCriterion("num not in", values, "num");
			return (Criteria) this;
		}

		public Criteria andNumBetween(Integer value1, Integer value2) {
			addCriterion("num between", value1, value2, "num");
			return (Criteria) this;
		}

		public Criteria andNumNotBetween(Integer value1, Integer value2) {
			addCriterion("num not between", value1, value2, "num");
			return (Criteria) this;
		}

		public Criteria andPnumIsNull() {
			addCriterion("pnum is null");
			return (Criteria) this;
		}

		public Criteria andPnumIsNotNull() {
			addCriterion("pnum is not null");
			return (Criteria) this;
		}

		public Criteria andPnumEqualTo(Integer value) {
			addCriterion("pnum =", value, "pnum");
			return (Criteria) this;
		}

		public Criteria andPnumNotEqualTo(Integer value) {
			addCriterion("pnum <>", value, "pnum");
			return (Criteria) this;
		}

		public Criteria andPnumGreaterThan(Integer value) {
			addCriterion("pnum >", value, "pnum");
			return (Criteria) this;
		}

		public Criteria andPnumGreaterThanOrEqualTo(Integer value) {
			addCriterion("pnum >=", value, "pnum");
			return (Criteria) this;
		}

		public Criteria andPnumLessThan(Integer value) {
			addCriterion("pnum <", value, "pnum");
			return (Criteria) this;
		}

		public Criteria andPnumLessThanOrEqualTo(Integer value) {
			addCriterion("pnum <=", value, "pnum");
			return (Criteria) this;
		}

		public Criteria andPnumIn(List<Integer> values) {
			addCriterion("pnum in", values, "pnum");
			return (Criteria) this;
		}

		public Criteria andPnumNotIn(List<Integer> values) {
			addCriterion("pnum not in", values, "pnum");
			return (Criteria) this;
		}

		public Criteria andPnumBetween(Integer value1, Integer value2) {
			addCriterion("pnum between", value1, value2, "pnum");
			return (Criteria) this;
		}

		public Criteria andPnumNotBetween(Integer value1, Integer value2) {
			addCriterion("pnum not between", value1, value2, "pnum");
			return (Criteria) this;
		}

		public Criteria andPriceIsNull() {
			addCriterion("price is null");
			return (Criteria) this;
		}

		public Criteria andPriceIsNotNull() {
			addCriterion("price is not null");
			return (Criteria) this;
		}

		public Criteria andPriceEqualTo(Double value) {
			addCriterion("price =", value, "price");
			return (Criteria) this;
		}

		public Criteria andPriceNotEqualTo(Double value) {
			addCriterion("price <>", value, "price");
			return (Criteria) this;
		}

		public Criteria andPriceGreaterThan(Double value) {
			addCriterion("price >", value, "price");
			return (Criteria) this;
		}

		public Criteria andPriceGreaterThanOrEqualTo(Double value) {
			addCriterion("price >=", value, "price");
			return (Criteria) this;
		}

		public Criteria andPriceLessThan(Double value) {
			addCriterion("price <", value, "price");
			return (Criteria) this;
		}

		public Criteria andPriceLessThanOrEqualTo(Double value) {
			addCriterion("price <=", value, "price");
			return (Criteria) this;
		}

		public Criteria andPriceIn(List<Double> values) {
			addCriterion("price in", values, "price");
			return (Criteria) this;
		}

		public Criteria andPriceNotIn(List<Double> values) {
			addCriterion("price not in", values, "price");
			return (Criteria) this;
		}

		public Criteria andPriceBetween(Double value1, Double value2) {
			addCriterion("price between", value1, value2, "price");
			return (Criteria) this;
		}

		public Criteria andPriceNotBetween(Double value1, Double value2) {
			addCriterion("price not between", value1, value2, "price");
			return (Criteria) this;
		}

		public Criteria andYpriceIsNull() {
			addCriterion("yprice is null");
			return (Criteria) this;
		}

		public Criteria andYpriceIsNotNull() {
			addCriterion("yprice is not null");
			return (Criteria) this;
		}

		public Criteria andYpriceEqualTo(Double value) {
			addCriterion("yprice =", value, "yprice");
			return (Criteria) this;
		}

		public Criteria andYpriceNotEqualTo(Double value) {
			addCriterion("yprice <>", value, "yprice");
			return (Criteria) this;
		}

		public Criteria andYpriceGreaterThan(Double value) {
			addCriterion("yprice >", value, "yprice");
			return (Criteria) this;
		}

		public Criteria andYpriceGreaterThanOrEqualTo(Double value) {
			addCriterion("yprice >=", value, "yprice");
			return (Criteria) this;
		}

		public Criteria andYpriceLessThan(Double value) {
			addCriterion("yprice <", value, "yprice");
			return (Criteria) this;
		}

		public Criteria andYpriceLessThanOrEqualTo(Double value) {
			addCriterion("yprice <=", value, "yprice");
			return (Criteria) this;
		}

		public Criteria andYpriceIn(List<Double> values) {
			addCriterion("yprice in", values, "yprice");
			return (Criteria) this;
		}

		public Criteria andYpriceNotIn(List<Double> values) {
			addCriterion("yprice not in", values, "yprice");
			return (Criteria) this;
		}

		public Criteria andYpriceBetween(Double value1, Double value2) {
			addCriterion("yprice between", value1, value2, "yprice");
			return (Criteria) this;
		}

		public Criteria andYpriceNotBetween(Double value1, Double value2) {
			addCriterion("yprice not between", value1, value2, "yprice");
			return (Criteria) this;
		}

		public Criteria andAuditingIsNull() {
			addCriterion("auditing is null");
			return (Criteria) this;
		}

		public Criteria andAuditingIsNotNull() {
			addCriterion("auditing is not null");
			return (Criteria) this;
		}

		public Criteria andAuditingEqualTo(Integer value) {
			addCriterion("auditing =", value, "auditing");
			return (Criteria) this;
		}

		public Criteria andAuditingNotEqualTo(Integer value) {
			addCriterion("auditing <>", value, "auditing");
			return (Criteria) this;
		}

		public Criteria andAuditingGreaterThan(Integer value) {
			addCriterion("auditing >", value, "auditing");
			return (Criteria) this;
		}

		public Criteria andAuditingGreaterThanOrEqualTo(Integer value) {
			addCriterion("auditing >=", value, "auditing");
			return (Criteria) this;
		}

		public Criteria andAuditingLessThan(Integer value) {
			addCriterion("auditing <", value, "auditing");
			return (Criteria) this;
		}

		public Criteria andAuditingLessThanOrEqualTo(Integer value) {
			addCriterion("auditing <=", value, "auditing");
			return (Criteria) this;
		}

		public Criteria andAuditingIn(List<Integer> values) {
			addCriterion("auditing in", values, "auditing");
			return (Criteria) this;
		}

		public Criteria andAuditingNotIn(List<Integer> values) {
			addCriterion("auditing not in", values, "auditing");
			return (Criteria) this;
		}

		public Criteria andAuditingBetween(Integer value1, Integer value2) {
			addCriterion("auditing between", value1, value2, "auditing");
			return (Criteria) this;
		}

		public Criteria andAuditingNotBetween(Integer value1, Integer value2) {
			addCriterion("auditing not between", value1, value2, "auditing");
			return (Criteria) this;
		}

		public Criteria andStateIsNull() {
			addCriterion("state is null");
			return (Criteria) this;
		}

		public Criteria andStateIsNotNull() {
			addCriterion("state is not null");
			return (Criteria) this;
		}

		public Criteria andStateEqualTo(Integer value) {
			addCriterion("state =", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateNotEqualTo(Integer value) {
			addCriterion("state <>", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateGreaterThan(Integer value) {
			addCriterion("state >", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateGreaterThanOrEqualTo(Integer value) {
			addCriterion("state >=", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateLessThan(Integer value) {
			addCriterion("state <", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateLessThanOrEqualTo(Integer value) {
			addCriterion("state <=", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateIn(List<Integer> values) {
			addCriterion("state in", values, "state");
			return (Criteria) this;
		}

		public Criteria andStateNotIn(List<Integer> values) {
			addCriterion("state not in", values, "state");
			return (Criteria) this;
		}

		public Criteria andStateBetween(Integer value1, Integer value2) {
			addCriterion("state between", value1, value2, "state");
			return (Criteria) this;
		}

		public Criteria andStateNotBetween(Integer value1, Integer value2) {
			addCriterion("state not between", value1, value2, "state");
			return (Criteria) this;
		}

		public Criteria andYnumIsNull() {
			addCriterion("ynum is null");
			return (Criteria) this;
		}

		public Criteria andYnumIsNotNull() {
			addCriterion("ynum is not null");
			return (Criteria) this;
		}

		public Criteria andYnumEqualTo(Integer value) {
			addCriterion("ynum =", value, "ynum");
			return (Criteria) this;
		}

		public Criteria andYnumNotEqualTo(Integer value) {
			addCriterion("ynum <>", value, "ynum");
			return (Criteria) this;
		}

		public Criteria andYnumGreaterThan(Integer value) {
			addCriterion("ynum >", value, "ynum");
			return (Criteria) this;
		}

		public Criteria andYnumGreaterThanOrEqualTo(Integer value) {
			addCriterion("ynum >=", value, "ynum");
			return (Criteria) this;
		}

		public Criteria andYnumLessThan(Integer value) {
			addCriterion("ynum <", value, "ynum");
			return (Criteria) this;
		}

		public Criteria andYnumLessThanOrEqualTo(Integer value) {
			addCriterion("ynum <=", value, "ynum");
			return (Criteria) this;
		}

		public Criteria andYnumIn(List<Integer> values) {
			addCriterion("ynum in", values, "ynum");
			return (Criteria) this;
		}

		public Criteria andYnumNotIn(List<Integer> values) {
			addCriterion("ynum not in", values, "ynum");
			return (Criteria) this;
		}

		public Criteria andYnumBetween(Integer value1, Integer value2) {
			addCriterion("ynum between", value1, value2, "ynum");
			return (Criteria) this;
		}

		public Criteria andYnumNotBetween(Integer value1, Integer value2) {
			addCriterion("ynum not between", value1, value2, "ynum");
			return (Criteria) this;
		}

		public Criteria andSnameIsNull() {
			addCriterion("sname is null");
			return (Criteria) this;
		}

		public Criteria andSnameIsNotNull() {
			addCriterion("sname is not null");
			return (Criteria) this;
		}

		public Criteria andSnameEqualTo(String value) {
			addCriterion("sname =", value, "sname");
			return (Criteria) this;
		}

		public Criteria andSnameNotEqualTo(String value) {
			addCriterion("sname <>", value, "sname");
			return (Criteria) this;
		}

		public Criteria andSnameGreaterThan(String value) {
			addCriterion("sname >", value, "sname");
			return (Criteria) this;
		}

		public Criteria andSnameGreaterThanOrEqualTo(String value) {
			addCriterion("sname >=", value, "sname");
			return (Criteria) this;
		}

		public Criteria andSnameLessThan(String value) {
			addCriterion("sname <", value, "sname");
			return (Criteria) this;
		}

		public Criteria andSnameLessThanOrEqualTo(String value) {
			addCriterion("sname <=", value, "sname");
			return (Criteria) this;
		}

		public Criteria andSnameLike(String value) {
			addCriterion("sname like", value, "sname");
			return (Criteria) this;
		}

		public Criteria andSnameNotLike(String value) {
			addCriterion("sname not like", value, "sname");
			return (Criteria) this;
		}

		public Criteria andSnameIn(List<String> values) {
			addCriterion("sname in", values, "sname");
			return (Criteria) this;
		}

		public Criteria andSnameNotIn(List<String> values) {
			addCriterion("sname not in", values, "sname");
			return (Criteria) this;
		}

		public Criteria andSnameBetween(String value1, String value2) {
			addCriterion("sname between", value1, value2, "sname");
			return (Criteria) this;
		}

		public Criteria andSnameNotBetween(String value1, String value2) {
			addCriterion("sname not between", value1, value2, "sname");
			return (Criteria) this;
		}

		public Criteria andCnameIsNull() {
			addCriterion("cname is null");
			return (Criteria) this;
		}

		public Criteria andCnameIsNotNull() {
			addCriterion("cname is not null");
			return (Criteria) this;
		}

		public Criteria andCnameEqualTo(String value) {
			addCriterion("cname =", value, "cname");
			return (Criteria) this;
		}

		public Criteria andCnameNotEqualTo(String value) {
			addCriterion("cname <>", value, "cname");
			return (Criteria) this;
		}

		public Criteria andCnameGreaterThan(String value) {
			addCriterion("cname >", value, "cname");
			return (Criteria) this;
		}

		public Criteria andCnameGreaterThanOrEqualTo(String value) {
			addCriterion("cname >=", value, "cname");
			return (Criteria) this;
		}

		public Criteria andCnameLessThan(String value) {
			addCriterion("cname <", value, "cname");
			return (Criteria) this;
		}

		public Criteria andCnameLessThanOrEqualTo(String value) {
			addCriterion("cname <=", value, "cname");
			return (Criteria) this;
		}

		public Criteria andCnameLike(String value) {
			addCriterion("cname like", value, "cname");
			return (Criteria) this;
		}

		public Criteria andCnameNotLike(String value) {
			addCriterion("cname not like", value, "cname");
			return (Criteria) this;
		}

		public Criteria andCnameIn(List<String> values) {
			addCriterion("cname in", values, "cname");
			return (Criteria) this;
		}

		public Criteria andCnameNotIn(List<String> values) {
			addCriterion("cname not in", values, "cname");
			return (Criteria) this;
		}

		public Criteria andCnameBetween(String value1, String value2) {
			addCriterion("cname between", value1, value2, "cname");
			return (Criteria) this;
		}

		public Criteria andCnameNotBetween(String value1, String value2) {
			addCriterion("cname not between", value1, value2, "cname");
			return (Criteria) this;
		}

		public Criteria andCidIsNull() {
			addCriterion("cid is null");
			return (Criteria) this;
		}

		public Criteria andCidIsNotNull() {
			addCriterion("cid is not null");
			return (Criteria) this;
		}

		public Criteria andCidEqualTo(Integer value) {
			addCriterion("cid =", value, "cid");
			return (Criteria) this;
		}

		public Criteria andCidNotEqualTo(Integer value) {
			addCriterion("cid <>", value, "cid");
			return (Criteria) this;
		}

		public Criteria andCidGreaterThan(Integer value) {
			addCriterion("cid >", value, "cid");
			return (Criteria) this;
		}

		public Criteria andCidGreaterThanOrEqualTo(Integer value) {
			addCriterion("cid >=", value, "cid");
			return (Criteria) this;
		}

		public Criteria andCidLessThan(Integer value) {
			addCriterion("cid <", value, "cid");
			return (Criteria) this;
		}

		public Criteria andCidLessThanOrEqualTo(Integer value) {
			addCriterion("cid <=", value, "cid");
			return (Criteria) this;
		}

		public Criteria andCidIn(List<Integer> values) {
			addCriterion("cid in", values, "cid");
			return (Criteria) this;
		}

		public Criteria andCidNotIn(List<Integer> values) {
			addCriterion("cid not in", values, "cid");
			return (Criteria) this;
		}

		public Criteria andCidBetween(Integer value1, Integer value2) {
			addCriterion("cid between", value1, value2, "cid");
			return (Criteria) this;
		}

		public Criteria andCidNotBetween(Integer value1, Integer value2) {
			addCriterion("cid not between", value1, value2, "cid");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table v_group_shop
	 * @mbggenerated  Thu Sep 08 15:33:50 CST 2016
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table v_group_shop
     *
     * @mbggenerated do_not_delete_during_merge Thu Sep 08 14:57:33 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}