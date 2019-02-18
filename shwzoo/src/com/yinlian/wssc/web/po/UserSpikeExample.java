package com.yinlian.wssc.web.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserSpikeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserSpikeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andUseridIsNull() {
            addCriterion("userid is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userid is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(Integer value) {
            addCriterion("userid =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(Integer value) {
            addCriterion("userid <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(Integer value) {
            addCriterion("userid >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("userid >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(Integer value) {
            addCriterion("userid <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(Integer value) {
            addCriterion("userid <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<Integer> values) {
            addCriterion("userid in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<Integer> values) {
            addCriterion("userid not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(Integer value1, Integer value2) {
            addCriterion("userid between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("userid not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andSpikeidIsNull() {
            addCriterion("spikeid is null");
            return (Criteria) this;
        }

        public Criteria andSpikeidIsNotNull() {
            addCriterion("spikeid is not null");
            return (Criteria) this;
        }

        public Criteria andSpikeidEqualTo(Integer value) {
            addCriterion("spikeid =", value, "spikeid");
            return (Criteria) this;
        }

        public Criteria andSpikeidNotEqualTo(Integer value) {
            addCriterion("spikeid <>", value, "spikeid");
            return (Criteria) this;
        }

        public Criteria andSpikeidGreaterThan(Integer value) {
            addCriterion("spikeid >", value, "spikeid");
            return (Criteria) this;
        }

        public Criteria andSpikeidGreaterThanOrEqualTo(Integer value) {
            addCriterion("spikeid >=", value, "spikeid");
            return (Criteria) this;
        }

        public Criteria andSpikeidLessThan(Integer value) {
            addCriterion("spikeid <", value, "spikeid");
            return (Criteria) this;
        }

        public Criteria andSpikeidLessThanOrEqualTo(Integer value) {
            addCriterion("spikeid <=", value, "spikeid");
            return (Criteria) this;
        }

        public Criteria andSpikeidIn(List<Integer> values) {
            addCriterion("spikeid in", values, "spikeid");
            return (Criteria) this;
        }

        public Criteria andSpikeidNotIn(List<Integer> values) {
            addCriterion("spikeid not in", values, "spikeid");
            return (Criteria) this;
        }

        public Criteria andSpikeidBetween(Integer value1, Integer value2) {
            addCriterion("spikeid between", value1, value2, "spikeid");
            return (Criteria) this;
        }

        public Criteria andSpikeidNotBetween(Integer value1, Integer value2) {
            addCriterion("spikeid not between", value1, value2, "spikeid");
            return (Criteria) this;
        }

        public Criteria andSpikecodeIsNull() {
            addCriterion("spikecode is null");
            return (Criteria) this;
        }

        public Criteria andSpikecodeIsNotNull() {
            addCriterion("spikecode is not null");
            return (Criteria) this;
        }

        public Criteria andSpikecodeEqualTo(String value) {
            addCriterion("spikecode =", value, "spikecode");
            return (Criteria) this;
        }

        public Criteria andSpikecodeNotEqualTo(String value) {
            addCriterion("spikecode <>", value, "spikecode");
            return (Criteria) this;
        }

        public Criteria andSpikecodeGreaterThan(String value) {
            addCriterion("spikecode >", value, "spikecode");
            return (Criteria) this;
        }

        public Criteria andSpikecodeGreaterThanOrEqualTo(String value) {
            addCriterion("spikecode >=", value, "spikecode");
            return (Criteria) this;
        }

        public Criteria andSpikecodeLessThan(String value) {
            addCriterion("spikecode <", value, "spikecode");
            return (Criteria) this;
        }

        public Criteria andSpikecodeLessThanOrEqualTo(String value) {
            addCriterion("spikecode <=", value, "spikecode");
            return (Criteria) this;
        }

        public Criteria andSpikecodeLike(String value) {
            addCriterion("spikecode like", value, "spikecode");
            return (Criteria) this;
        }

        public Criteria andSpikecodeNotLike(String value) {
            addCriterion("spikecode not like", value, "spikecode");
            return (Criteria) this;
        }

        public Criteria andSpikecodeIn(List<String> values) {
            addCriterion("spikecode in", values, "spikecode");
            return (Criteria) this;
        }

        public Criteria andSpikecodeNotIn(List<String> values) {
            addCriterion("spikecode not in", values, "spikecode");
            return (Criteria) this;
        }

        public Criteria andSpikecodeBetween(String value1, String value2) {
            addCriterion("spikecode between", value1, value2, "spikecode");
            return (Criteria) this;
        }

        public Criteria andSpikecodeNotBetween(String value1, String value2) {
            addCriterion("spikecode not between", value1, value2, "spikecode");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createtime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createtime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createtime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createtime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createtime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createtime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createtime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createtime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createtime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createtime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createtime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createtime not between", value1, value2, "createtime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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
}