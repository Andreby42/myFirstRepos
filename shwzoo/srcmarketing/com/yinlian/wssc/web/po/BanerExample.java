package com.yinlian.wssc.web.po;

import java.util.ArrayList;
import java.util.List;

public class BanerExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
	 */
	public BanerExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
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

		public Criteria andUrlIsNull() {
			addCriterion("url is null");
			return (Criteria) this;
		}

		public Criteria andUrlIsNotNull() {
			addCriterion("url is not null");
			return (Criteria) this;
		}

		public Criteria andUrlEqualTo(String value) {
			addCriterion("url =", value, "url");
			return (Criteria) this;
		}

		public Criteria andUrlNotEqualTo(String value) {
			addCriterion("url <>", value, "url");
			return (Criteria) this;
		}

		public Criteria andUrlGreaterThan(String value) {
			addCriterion("url >", value, "url");
			return (Criteria) this;
		}

		public Criteria andUrlGreaterThanOrEqualTo(String value) {
			addCriterion("url >=", value, "url");
			return (Criteria) this;
		}

		public Criteria andUrlLessThan(String value) {
			addCriterion("url <", value, "url");
			return (Criteria) this;
		}

		public Criteria andUrlLessThanOrEqualTo(String value) {
			addCriterion("url <=", value, "url");
			return (Criteria) this;
		}

		public Criteria andUrlLike(String value) {
			addCriterion("url like", value, "url");
			return (Criteria) this;
		}

		public Criteria andUrlNotLike(String value) {
			addCriterion("url not like", value, "url");
			return (Criteria) this;
		}

		public Criteria andUrlIn(List<String> values) {
			addCriterion("url in", values, "url");
			return (Criteria) this;
		}

		public Criteria andUrlNotIn(List<String> values) {
			addCriterion("url not in", values, "url");
			return (Criteria) this;
		}

		public Criteria andUrlBetween(String value1, String value2) {
			addCriterion("url between", value1, value2, "url");
			return (Criteria) this;
		}

		public Criteria andUrlNotBetween(String value1, String value2) {
			addCriterion("url not between", value1, value2, "url");
			return (Criteria) this;
		}

		public Criteria andImgIsNull() {
			addCriterion("img is null");
			return (Criteria) this;
		}

		public Criteria andImgIsNotNull() {
			addCriterion("img is not null");
			return (Criteria) this;
		}

		public Criteria andImgEqualTo(String value) {
			addCriterion("img =", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgNotEqualTo(String value) {
			addCriterion("img <>", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgGreaterThan(String value) {
			addCriterion("img >", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgGreaterThanOrEqualTo(String value) {
			addCriterion("img >=", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgLessThan(String value) {
			addCriterion("img <", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgLessThanOrEqualTo(String value) {
			addCriterion("img <=", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgLike(String value) {
			addCriterion("img like", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgNotLike(String value) {
			addCriterion("img not like", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgIn(List<String> values) {
			addCriterion("img in", values, "img");
			return (Criteria) this;
		}

		public Criteria andImgNotIn(List<String> values) {
			addCriterion("img not in", values, "img");
			return (Criteria) this;
		}

		public Criteria andImgBetween(String value1, String value2) {
			addCriterion("img between", value1, value2, "img");
			return (Criteria) this;
		}

		public Criteria andImgNotBetween(String value1, String value2) {
			addCriterion("img not between", value1, value2, "img");
			return (Criteria) this;
		}

		public Criteria andTypeIsNull() {
			addCriterion("type is null");
			return (Criteria) this;
		}

		public Criteria andTypeIsNotNull() {
			addCriterion("type is not null");
			return (Criteria) this;
		}

		public Criteria andTypeEqualTo(Integer value) {
			addCriterion("type =", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotEqualTo(Integer value) {
			addCriterion("type <>", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeGreaterThan(Integer value) {
			addCriterion("type >", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("type >=", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLessThan(Integer value) {
			addCriterion("type <", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLessThanOrEqualTo(Integer value) {
			addCriterion("type <=", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeIn(List<Integer> values) {
			addCriterion("type in", values, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotIn(List<Integer> values) {
			addCriterion("type not in", values, "type");
			return (Criteria) this;
		}

		public Criteria andTypeBetween(Integer value1, Integer value2) {
			addCriterion("type between", value1, value2, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("type not between", value1, value2, "type");
			return (Criteria) this;
		}

		public Criteria andCtypeIsNull() {
			addCriterion("ctype is null");
			return (Criteria) this;
		}

		public Criteria andCtypeIsNotNull() {
			addCriterion("ctype is not null");
			return (Criteria) this;
		}

		public Criteria andCtypeEqualTo(String value) {
			addCriterion("ctype =", value, "ctype");
			return (Criteria) this;
		}

		public Criteria andCtypeNotEqualTo(String value) {
			addCriterion("ctype <>", value, "ctype");
			return (Criteria) this;
		}

		public Criteria andCtypeGreaterThan(String value) {
			addCriterion("ctype >", value, "ctype");
			return (Criteria) this;
		}

		public Criteria andCtypeGreaterThanOrEqualTo(String value) {
			addCriterion("ctype >=", value, "ctype");
			return (Criteria) this;
		}

		public Criteria andCtypeLessThan(String value) {
			addCriterion("ctype <", value, "ctype");
			return (Criteria) this;
		}

		public Criteria andCtypeLessThanOrEqualTo(String value) {
			addCriterion("ctype <=", value, "ctype");
			return (Criteria) this;
		}

		public Criteria andCtypeLike(String value) {
			addCriterion("ctype like", value, "ctype");
			return (Criteria) this;
		}

		public Criteria andCtypeNotLike(String value) {
			addCriterion("ctype not like", value, "ctype");
			return (Criteria) this;
		}

		public Criteria andCtypeIn(List<String> values) {
			addCriterion("ctype in", values, "ctype");
			return (Criteria) this;
		}

		public Criteria andCtypeNotIn(List<String> values) {
			addCriterion("ctype not in", values, "ctype");
			return (Criteria) this;
		}

		public Criteria andCtypeBetween(String value1, String value2) {
			addCriterion("ctype between", value1, value2, "ctype");
			return (Criteria) this;
		}

		public Criteria andCtypeNotBetween(String value1, String value2) {
			addCriterion("ctype not between", value1, value2, "ctype");
			return (Criteria) this;
		}

		public Criteria andAltIsNull() {
			addCriterion("alt is null");
			return (Criteria) this;
		}

		public Criteria andAltIsNotNull() {
			addCriterion("alt is not null");
			return (Criteria) this;
		}

		public Criteria andAltEqualTo(String value) {
			addCriterion("alt =", value, "alt");
			return (Criteria) this;
		}

		public Criteria andAltNotEqualTo(String value) {
			addCriterion("alt <>", value, "alt");
			return (Criteria) this;
		}

		public Criteria andAltGreaterThan(String value) {
			addCriterion("alt >", value, "alt");
			return (Criteria) this;
		}

		public Criteria andAltGreaterThanOrEqualTo(String value) {
			addCriterion("alt >=", value, "alt");
			return (Criteria) this;
		}

		public Criteria andAltLessThan(String value) {
			addCriterion("alt <", value, "alt");
			return (Criteria) this;
		}

		public Criteria andAltLessThanOrEqualTo(String value) {
			addCriterion("alt <=", value, "alt");
			return (Criteria) this;
		}

		public Criteria andAltLike(String value) {
			addCriterion("alt like", value, "alt");
			return (Criteria) this;
		}

		public Criteria andAltNotLike(String value) {
			addCriterion("alt not like", value, "alt");
			return (Criteria) this;
		}

		public Criteria andAltIn(List<String> values) {
			addCriterion("alt in", values, "alt");
			return (Criteria) this;
		}

		public Criteria andAltNotIn(List<String> values) {
			addCriterion("alt not in", values, "alt");
			return (Criteria) this;
		}

		public Criteria andAltBetween(String value1, String value2) {
			addCriterion("alt between", value1, value2, "alt");
			return (Criteria) this;
		}

		public Criteria andAltNotBetween(String value1, String value2) {
			addCriterion("alt not between", value1, value2, "alt");
			return (Criteria) this;
		}

		public Criteria andSortIsNull() {
			addCriterion("sort is null");
			return (Criteria) this;
		}

		public Criteria andSortIsNotNull() {
			addCriterion("sort is not null");
			return (Criteria) this;
		}

		public Criteria andSortEqualTo(Integer value) {
			addCriterion("sort =", value, "sort");
			return (Criteria) this;
		}

		public Criteria andSortNotEqualTo(Integer value) {
			addCriterion("sort <>", value, "sort");
			return (Criteria) this;
		}

		public Criteria andSortGreaterThan(Integer value) {
			addCriterion("sort >", value, "sort");
			return (Criteria) this;
		}

		public Criteria andSortGreaterThanOrEqualTo(Integer value) {
			addCriterion("sort >=", value, "sort");
			return (Criteria) this;
		}

		public Criteria andSortLessThan(Integer value) {
			addCriterion("sort <", value, "sort");
			return (Criteria) this;
		}

		public Criteria andSortLessThanOrEqualTo(Integer value) {
			addCriterion("sort <=", value, "sort");
			return (Criteria) this;
		}

		public Criteria andSortIn(List<Integer> values) {
			addCriterion("sort in", values, "sort");
			return (Criteria) this;
		}

		public Criteria andSortNotIn(List<Integer> values) {
			addCriterion("sort not in", values, "sort");
			return (Criteria) this;
		}

		public Criteria andSortBetween(Integer value1, Integer value2) {
			addCriterion("sort between", value1, value2, "sort");
			return (Criteria) this;
		}

		public Criteria andSortNotBetween(Integer value1, Integer value2) {
			addCriterion("sort not between", value1, value2, "sort");
			return (Criteria) this;
		}

		public Criteria andEx1IsNull() {
			addCriterion("ex1 is null");
			return (Criteria) this;
		}

		public Criteria andEx1IsNotNull() {
			addCriterion("ex1 is not null");
			return (Criteria) this;
		}

		public Criteria andEx1EqualTo(Integer value) {
			addCriterion("ex1 =", value, "ex1");
			return (Criteria) this;
		}

		public Criteria andEx1NotEqualTo(Integer value) {
			addCriterion("ex1 <>", value, "ex1");
			return (Criteria) this;
		}

		public Criteria andEx1GreaterThan(Integer value) {
			addCriterion("ex1 >", value, "ex1");
			return (Criteria) this;
		}

		public Criteria andEx1GreaterThanOrEqualTo(Integer value) {
			addCriterion("ex1 >=", value, "ex1");
			return (Criteria) this;
		}

		public Criteria andEx1LessThan(Integer value) {
			addCriterion("ex1 <", value, "ex1");
			return (Criteria) this;
		}

		public Criteria andEx1LessThanOrEqualTo(Integer value) {
			addCriterion("ex1 <=", value, "ex1");
			return (Criteria) this;
		}

		public Criteria andEx1In(List<Integer> values) {
			addCriterion("ex1 in", values, "ex1");
			return (Criteria) this;
		}

		public Criteria andEx1NotIn(List<Integer> values) {
			addCriterion("ex1 not in", values, "ex1");
			return (Criteria) this;
		}

		public Criteria andEx1Between(Integer value1, Integer value2) {
			addCriterion("ex1 between", value1, value2, "ex1");
			return (Criteria) this;
		}

		public Criteria andEx1NotBetween(Integer value1, Integer value2) {
			addCriterion("ex1 not between", value1, value2, "ex1");
			return (Criteria) this;
		}

		public Criteria andEx2IsNull() {
			addCriterion("ex2 is null");
			return (Criteria) this;
		}

		public Criteria andEx2IsNotNull() {
			addCriterion("ex2 is not null");
			return (Criteria) this;
		}

		public Criteria andEx2EqualTo(String value) {
			addCriterion("ex2 =", value, "ex2");
			return (Criteria) this;
		}

		public Criteria andEx2NotEqualTo(String value) {
			addCriterion("ex2 <>", value, "ex2");
			return (Criteria) this;
		}

		public Criteria andEx2GreaterThan(String value) {
			addCriterion("ex2 >", value, "ex2");
			return (Criteria) this;
		}

		public Criteria andEx2GreaterThanOrEqualTo(String value) {
			addCriterion("ex2 >=", value, "ex2");
			return (Criteria) this;
		}

		public Criteria andEx2LessThan(String value) {
			addCriterion("ex2 <", value, "ex2");
			return (Criteria) this;
		}

		public Criteria andEx2LessThanOrEqualTo(String value) {
			addCriterion("ex2 <=", value, "ex2");
			return (Criteria) this;
		}

		public Criteria andEx2Like(String value) {
			addCriterion("ex2 like", value, "ex2");
			return (Criteria) this;
		}

		public Criteria andEx2NotLike(String value) {
			addCriterion("ex2 not like", value, "ex2");
			return (Criteria) this;
		}

		public Criteria andEx2In(List<String> values) {
			addCriterion("ex2 in", values, "ex2");
			return (Criteria) this;
		}

		public Criteria andEx2NotIn(List<String> values) {
			addCriterion("ex2 not in", values, "ex2");
			return (Criteria) this;
		}

		public Criteria andEx2Between(String value1, String value2) {
			addCriterion("ex2 between", value1, value2, "ex2");
			return (Criteria) this;
		}

		public Criteria andEx2NotBetween(String value1, String value2) {
			addCriterion("ex2 not between", value1, value2, "ex2");
			return (Criteria) this;
		}

		public Criteria andEx3IsNull() {
			addCriterion("ex3 is null");
			return (Criteria) this;
		}

		public Criteria andEx3IsNotNull() {
			addCriterion("ex3 is not null");
			return (Criteria) this;
		}

		public Criteria andEx3EqualTo(String value) {
			addCriterion("ex3 =", value, "ex3");
			return (Criteria) this;
		}

		public Criteria andEx3NotEqualTo(String value) {
			addCriterion("ex3 <>", value, "ex3");
			return (Criteria) this;
		}

		public Criteria andEx3GreaterThan(String value) {
			addCriterion("ex3 >", value, "ex3");
			return (Criteria) this;
		}

		public Criteria andEx3GreaterThanOrEqualTo(String value) {
			addCriterion("ex3 >=", value, "ex3");
			return (Criteria) this;
		}

		public Criteria andEx3LessThan(String value) {
			addCriterion("ex3 <", value, "ex3");
			return (Criteria) this;
		}

		public Criteria andEx3LessThanOrEqualTo(String value) {
			addCriterion("ex3 <=", value, "ex3");
			return (Criteria) this;
		}

		public Criteria andEx3Like(String value) {
			addCriterion("ex3 like", value, "ex3");
			return (Criteria) this;
		}

		public Criteria andEx3NotLike(String value) {
			addCriterion("ex3 not like", value, "ex3");
			return (Criteria) this;
		}

		public Criteria andEx3In(List<String> values) {
			addCriterion("ex3 in", values, "ex3");
			return (Criteria) this;
		}

		public Criteria andEx3NotIn(List<String> values) {
			addCriterion("ex3 not in", values, "ex3");
			return (Criteria) this;
		}

		public Criteria andEx3Between(String value1, String value2) {
			addCriterion("ex3 between", value1, value2, "ex3");
			return (Criteria) this;
		}

		public Criteria andEx3NotBetween(String value1, String value2) {
			addCriterion("ex3 not between", value1, value2, "ex3");
			return (Criteria) this;
		}

		public Criteria andEx4IsNull() {
			addCriterion("ex4 is null");
			return (Criteria) this;
		}

		public Criteria andEx4IsNotNull() {
			addCriterion("ex4 is not null");
			return (Criteria) this;
		}

		public Criteria andEx4EqualTo(String value) {
			addCriterion("ex4 =", value, "ex4");
			return (Criteria) this;
		}

		public Criteria andEx4NotEqualTo(String value) {
			addCriterion("ex4 <>", value, "ex4");
			return (Criteria) this;
		}

		public Criteria andEx4GreaterThan(String value) {
			addCriterion("ex4 >", value, "ex4");
			return (Criteria) this;
		}

		public Criteria andEx4GreaterThanOrEqualTo(String value) {
			addCriterion("ex4 >=", value, "ex4");
			return (Criteria) this;
		}

		public Criteria andEx4LessThan(String value) {
			addCriterion("ex4 <", value, "ex4");
			return (Criteria) this;
		}

		public Criteria andEx4LessThanOrEqualTo(String value) {
			addCriterion("ex4 <=", value, "ex4");
			return (Criteria) this;
		}

		public Criteria andEx4Like(String value) {
			addCriterion("ex4 like", value, "ex4");
			return (Criteria) this;
		}

		public Criteria andEx4NotLike(String value) {
			addCriterion("ex4 not like", value, "ex4");
			return (Criteria) this;
		}

		public Criteria andEx4In(List<String> values) {
			addCriterion("ex4 in", values, "ex4");
			return (Criteria) this;
		}

		public Criteria andEx4NotIn(List<String> values) {
			addCriterion("ex4 not in", values, "ex4");
			return (Criteria) this;
		}

		public Criteria andEx4Between(String value1, String value2) {
			addCriterion("ex4 between", value1, value2, "ex4");
			return (Criteria) this;
		}

		public Criteria andEx4NotBetween(String value1, String value2) {
			addCriterion("ex4 not between", value1, value2, "ex4");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table baner
	 * @mbggenerated  Wed Sep 21 14:34:25 CST 2016
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
     * This class corresponds to the database table baner
     *
     * @mbggenerated do_not_delete_during_merge Sun Sep 18 16:50:52 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}