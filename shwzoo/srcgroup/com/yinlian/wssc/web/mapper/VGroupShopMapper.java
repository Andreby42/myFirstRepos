package com.yinlian.wssc.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yinlian.api.dto.Groupbuyinfo_Api_Dto;
import com.yinlian.wssc.web.po.VGroupShop;
import com.yinlian.wssc.web.po.VGroupShopExample;
import com.yinlian.wssc.web.util.PageBeanUtil;

public interface VGroupShopMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table v_group_shop
	 * 
	 * @mbggenerated Thu Sep 08 15:33:50 CST 2016
	 */
	int countByExample(VGroupShopExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table v_group_shop
	 * 
	 * @mbggenerated Thu Sep 08 15:33:50 CST 2016
	 */
	int deleteByExample(VGroupShopExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table v_group_shop
	 * 
	 * @mbggenerated Thu Sep 08 15:33:50 CST 2016
	 */
	int insert(VGroupShop record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table v_group_shop
	 * 
	 * @mbggenerated Thu Sep 08 15:33:50 CST 2016
	 */
	int insertSelective(VGroupShop record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table v_group_shop
	 * 
	 * @mbggenerated Thu Sep 08 15:33:50 CST 2016
	 */
	List<VGroupShop> selectByExampleWithBLOBs(VGroupShopExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table v_group_shop
	 * 
	 * @mbggenerated Thu Sep 08 15:33:50 CST 2016
	 */
	List<VGroupShop> selectByExample(VGroupShopExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table v_group_shop
	 * 
	 * @mbggenerated Thu Sep 08 15:33:50 CST 2016
	 */
	int updateByExampleSelective(@Param("record") VGroupShop record, @Param("example") VGroupShopExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table v_group_shop
	 * 
	 * @mbggenerated Thu Sep 08 15:33:50 CST 2016
	 */
	int updateByExampleWithBLOBs(@Param("record") VGroupShop record, @Param("example") VGroupShopExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table v_group_shop
	 * 
	 * @mbggenerated Thu Sep 08 15:33:50 CST 2016
	 */
	int updateByExample(@Param("record") VGroupShop record, @Param("example") VGroupShopExample example);

	List<VGroupShop> getListPage(PageBeanUtil pageBeanUtil) throws Exception;

	List<Groupbuyinfo_Api_Dto> getApiListPage(PageBeanUtil pageBeanUtil) throws Exception;

	VGroupShop getById(int id) throws Exception;

}