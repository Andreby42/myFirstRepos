package com.yinlian.wssc.web.mapper;

import com.yinlian.wssc.web.po.login_info;
import com.yinlian.wssc.web.po.login_infoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface login_infoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_info
     *
     * @mbggenerated Fri Jun 24 15:54:43 CST 2016
     */
    int countByExample(login_infoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_info
     *
     * @mbggenerated Fri Jun 24 15:54:43 CST 2016
     */
    int deleteByExample(login_infoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_info
     *
     * @mbggenerated Fri Jun 24 15:54:43 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_info
     *
     * @mbggenerated Fri Jun 24 15:54:43 CST 2016
     */
    int insert(login_info record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_info
     *
     * @mbggenerated Fri Jun 24 15:54:43 CST 2016
     */
    int insertSelective(login_info record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_info
     *
     * @mbggenerated Fri Jun 24 15:54:43 CST 2016
     */
    List<login_info> selectByExample(login_infoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_info
     *
     * @mbggenerated Fri Jun 24 15:54:43 CST 2016
     */
    login_info selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_info
     *
     * @mbggenerated Fri Jun 24 15:54:43 CST 2016
     */
    int updateByExampleSelective(@Param("record") login_info record, @Param("example") login_infoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_info
     *
     * @mbggenerated Fri Jun 24 15:54:43 CST 2016
     */
    int updateByExample(@Param("record") login_info record, @Param("example") login_infoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_info
     *
     * @mbggenerated Fri Jun 24 15:54:43 CST 2016
     */
    int updateByPrimaryKeySelective(login_info record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login_info
     *
     * @mbggenerated Fri Jun 24 15:54:43 CST 2016
     */
    int updateByPrimaryKey(login_info record);
}