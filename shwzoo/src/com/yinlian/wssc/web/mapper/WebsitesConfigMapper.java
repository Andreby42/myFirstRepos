package com.yinlian.wssc.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yinlian.wssc.web.dto.WebsitesConfigDto;
import com.yinlian.wssc.web.po.WebsitesConfig;
import com.yinlian.wssc.web.po.WebsitesConfigExample;
import com.yinlian.wssc.web.util.PageBeanUtil;

public interface WebsitesConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websites_config
     *
     * @mbggenerated Wed Jun 22 17:38:47 CST 2016
     */
    int countByExample(WebsitesConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websites_config
     *
     * @mbggenerated Wed Jun 22 17:38:47 CST 2016
     */
    int deleteByExample(WebsitesConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websites_config
     *
     * @mbggenerated Wed Jun 22 17:38:47 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websites_config
     *
     * @mbggenerated Wed Jun 22 17:38:47 CST 2016
     */
    int insert(WebsitesConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websites_config
     *
     * @mbggenerated Wed Jun 22 17:38:47 CST 2016
     */
    int insertSelective(WebsitesConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websites_config
     *
     * @mbggenerated Wed Jun 22 17:38:47 CST 2016
     */
    List<WebsitesConfig> selectByExample(WebsitesConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websites_config
     *
     * @mbggenerated Wed Jun 22 17:38:47 CST 2016
     */
    WebsitesConfig selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websites_config
     *
     * @mbggenerated Wed Jun 22 17:38:47 CST 2016
     */
    int updateByExampleSelective(@Param("record") WebsitesConfig record, @Param("example") WebsitesConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websites_config
     *
     * @mbggenerated Wed Jun 22 17:38:47 CST 2016
     */
    int updateByExample(@Param("record") WebsitesConfig record, @Param("example") WebsitesConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websites_config
     *
     * @mbggenerated Wed Jun 22 17:38:47 CST 2016
     */
    int updateByPrimaryKeySelective(WebsitesConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websites_config
     *
     * @mbggenerated Wed Jun 22 17:38:47 CST 2016
     */
    int updateByPrimaryKey(WebsitesConfig record);
    
    
    int insertAdd(WebsitesConfigDto dto);

	List<WebsitesConfigDto> getListByPage(PageBeanUtil pageBeanUtil);

	WebsitesConfigDto selectById(Integer id);


	int updateById(WebsitesConfig dto);

	WebsitesConfigDto selectByUserId(Integer integer);
	
	WebsitesConfig selectConfig()throws Exception;
}