package com.yinlian.wssc.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yinlian.api.app.dto.CouponDto;
import com.yinlian.api.app.dto.CouponShopDto;
import com.yinlian.api.app.dto.UserCouponDto;
import com.yinlian.api.app.dto.UserInfoDto;
import com.yinlian.pc.dto.CouponGetDto;
import com.yinlian.wssc.web.dto.V_CouponDto;
import com.yinlian.wssc.web.po.Coupon;
import com.yinlian.wssc.web.util.PageBeanUtil;

public interface CouponMapperCustom {
	
	List<UserCouponDto> getByUserIDPage(PageBeanUtil pageBeanUtil)throws Exception;
	
	List<UserInfoDto> getMenberListPage(PageBeanUtil pageBeanUtil)throws Exception;
	
	List<V_CouponDto> getByCouponIDPage(PageBeanUtil pageBeanUtil)throws Exception;
	
	List<CouponDto> getApiCoupon(@Param("userid") int userid,@Param("useplatform") int useplatform)throws Exception;
	
	CouponShopDto getLotteryCoupon()throws Exception;
	
	List<CouponShopDto> getLotteryCouponList()throws Exception;
	
	List<UserCouponDto> getByUserIDorderbyPage(PageBeanUtil pageBeanUtil)throws Exception;

	
	List<CouponGetDto> getAvailableCouponPage(PageBeanUtil pageBeanUtil)throws Exception;
	
	CouponGetDto getAllUseCoupon()throws Exception;
	
	List<Coupon> getRegistergiftCoupon()throws Exception;

}
