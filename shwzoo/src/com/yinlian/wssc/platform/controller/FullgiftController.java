package com.yinlian.wssc.platform.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yinlian.Enums.ActivityTypeEnum;
import com.yinlian.Enums.ManZengTypeEnum;
import com.yinlian.Enums.OperateRecordsFromEnum;
import com.yinlian.Enums.OperateRecordsTypeEnum;
import com.yinlian.Extended.LogType;
import com.yinlian.wssc.platform.vo.ReusltItem;
import com.yinlian.wssc.web.dto.SessionUser;
import com.yinlian.wssc.web.interceptor.PageBean;
import com.yinlian.wssc.web.po.ActivityMarket;
import com.yinlian.wssc.web.po.Fullgift;
import com.yinlian.wssc.web.service.ActivityService;
import com.yinlian.wssc.web.service.OperaterecordsService;
import com.yinlian.wssc.web.util.CriteriaActivity;
import com.yinlian.wssc.web.util.DebugConfig;
import com.yinlian.wssc.web.util.ProductNumUtil;
import com.yinlian.wssc.web.util.SessionState;
import com.yinlian.wssc.web.util.StringUtilsEX;
import com.yl.soft.log.LogHandle;

@RestController
@RequestMapping("/platform/Fullgift")
public class FullgiftController {

	@Autowired
	private ActivityService activityService;

	SessionUser user=null;
	@Autowired
    private OperaterecordsService operaterecordsService ;
	/**
	 * 添加满赠活动
	 * 
	 * @param name
	 * @param acttype
	 * @param fullprice
	 * @param spuid
	 * @param count
	 * @param start
	 * @param end
	 * @param stock
	 * @param status
	 * @param isphone
	 * @return
	 */
	@RequestMapping("/addFullgift")
	public ReusltItem addFullgift(String name, String acttype,
			String fullprice, String spuid, String count, String start,
			String end, String stock, String status,String useplatform) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			ActivityMarket activity = new ActivityMarket();
			activity = checkParam(name, acttype, fullprice, spuid, count,
					start, end, stock, status,useplatform, "0", item);
			if (item.getCode() < 0) {
				return item;
			}
			if (activityService.add(activity) > 0) {
				item.setCode(0);
				item.setDesc("添加满赠活动成功!");
				LogHandle.info(LogType.Activity, MessageFormat.format("添加满赠活动成功! 编号:{0},userid:{1}",
						activity.getActnum(),user.getUserId()),"/platform/Fullgift/addFullgift");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.添加.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "yxgl_FullgiftAdd.jsp", "/platform/Fullgift/addFullgift", "添加满减活动");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"添加满减活动操作记录出错! 异常信息:",
    								e, "/platform/Fullgift/addFullgift");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("添加满赠活动失败");
				LogHandle.warn(LogType.Activity, MessageFormat.format("添加满赠活动失败! 编号:{0},userid:{1}",
						activity.getActnum(),user.getUserId()),"/platform/Fullgift/addFullgift");
			}

		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("添加满赠活动异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Activity, "添加满赠活动出现异常， 信息:",
					e, "/platform/Fullgift/addFullgift");
		}
		return item;
	}

	/**
	 * 编辑满赠活动
	 * 
	 * @param id
	 * @param name
	 * @param acttype
	 * @param fullprice
	 * @param spuid
	 * @param count
	 * @param start
	 * @param end
	 * @param stock
	 * @param status
	 * @param isphone
	 * @return
	 */
	@RequestMapping("/updateFullgift")
	public ReusltItem updateFullgift(String id, String name, String acttype,
			String fullprice, String spuid, String count, String start,
			String end, String stock, String status,String useplatform) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			ActivityMarket activity = new ActivityMarket();
			activity = checkParam(name, acttype, fullprice, spuid, count,
					start, end, stock, status,useplatform, id, item);
			if (item.getCode() < 0) {
				return item;
			}
			if (activityService.update(activity) > 0) {
				item.setCode(0);
				item.setDesc("编辑满赠活动成功!");
				LogHandle.info(LogType.Activity, MessageFormat.format("编辑满赠活动成功! id:{0},编号:{1},用户ID:{2}",
						id,activity.getActnum(),user.getUserId()), "/platform/Fullgift/updateFullgift");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.修改.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "yxgl_FullgiftEdit.jsp", "/platform/Fullgift/updateFullgift", "修改满减活动");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"修改满减活动操作记录出错! 异常信息:",
    								e, "/platform/Fullgift/updateFullgift");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("编辑满赠活动失败");
				LogHandle.warn(LogType.Activity, MessageFormat.format("编辑满赠活动失败! id:{0},编号:{1},用户ID:{2}",
						id,activity.getActnum(),user.getUserId()), "/platform/Fullgift/updateFullgift");
			}
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("编辑满赠活动异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Activity, "编辑满赠活动出现异常， 信息:",
					e, "/platform/Fullgift/updateFullgift");
		}
		return item;
	}

	private ActivityMarket checkParam(String name, String acttype,
			String fullprice, String spuid, String count, String start,
			String end, String stock, String status,  String useplatform,String id,
			ReusltItem item) throws Exception {
		user=SessionState.GetCurrentUser();
		ActivityMarket activity = new ActivityMarket();
		Integer ID = StringUtilsEX.ToInt(id);
		if (ID < 0) {
			item.setCode(-101);
			item.setDesc("满赠活动ID参数错误：" + id);
			return null;
		}
		if (ID == 0) {
			activity.setIsdel(false);
			activity.setCreatetime(new Date());
			activity.setCreateuserid(user.getShopid());
			; // 用户ID 默认为1
			activity.setActnum(ProductNumUtil.getCouponNum()); // 自动生成活动编号
		} else {
			activity = activityService.getById(ID);
			if (activity == null) {
				item.setCode(-401);
				item.setDesc("根据ID未能检索到数据");
				LogHandle.error(LogType.Activity, MessageFormat.format("修改满赠活动错误，根据ID未能检索到数据.ID:{0}", id), 
						"/platform/Fullgift/updateFullgift");
				return null;
			}
		}
		if (StringUtilsEX.IsNullOrWhiteSpace(name)) {
			item.setCode(-102);
			item.setDesc("满赠活动名称不能为空");
			return null;
		}
		if (StringUtilsEX.ToInt(acttype) < 0) {
			item.setCode(-103);
			item.setDesc("满赠活动类型参数错误：" + acttype);
			return null;
		} else {
			if (StringUtilsEX.ToInt(acttype) == 0) {
				if (StringUtilsEX.ToFloat(fullprice) <= 0.0f) {
					item.setCode(-101);
					item.setDesc("满赠活动满金额参数错误：" + fullprice);
					return null;
				}
				activity.setFullvalue(StringUtilsEX.ToFloat(fullprice));
			} else {
				if (StringUtilsEX.ToInt(spuid) <= 0) {
					item.setCode(-105);
					item.setDesc("满赠活动商品ID参数错误：" + spuid);
					return null;
				}
				if (StringUtilsEX.ToInt(count) <= 0) {
					item.setCode(-106);
					item.setDesc("满赠活动商品件数参数错误：" + count);
					return null;
				}
				activity.setSpuid(StringUtilsEX.ToInt(spuid));
				activity.setCount(StringUtilsEX.ToInt(count));

			}
			activity.setUsetype(StringUtilsEX.ToInt(acttype));
		}

		if (StringUtilsEX.ToInt(status) < 0) {
			item.setCode(-108);
			item.setDesc("满赠活动状态参数错误：" + status);
			return null;
		}
		if (StringUtilsEX.IsNullOrWhiteSpace(start)) {
			item.setCode(-109);
			item.setDesc("开始时间参数错误：" + start);
			return null;

		}
		if (StringUtilsEX.IsNullOrWhiteSpace(end)) {
			item.setCode(-110);
			item.setDesc("结束时间参数错误：" + end);
			return null;
		}
		if (StringUtilsEX.ToInt(stock) <= 0) {
			item.setCode(-111);
			item.setDesc("满赠活动库存参数错误：" + stock);
			return null;
		}
		if (StringUtilsEX.IsNullOrWhiteSpace(useplatform)) {
			item.setCode(-117);
			item.setDesc("请选择使用平台");
			return null;
		}
	/*	// 是否手机专享
		if (StringUtilsEX.ToInt(isphone) > 0) {
			activity.setIsphone(1); // 是
		} else {
			activity.setIsphone(0); // 否
		}
*/
		activity.setIscheck(false);
		activity.setActname(name.trim());
		activity.setActtype(ActivityTypeEnum.满赠.getValue());
		activity.setStarttime(StringUtilsEX.ToDate(start));
		activity.setEndtime(StringUtilsEX.ToDate(end));
		activity.setStatus(StringUtilsEX.ToInt(status));
		activity.setStock(StringUtilsEX.ToInt(stock));
		activity.setShopid(user.getShopid()); // 所属店铺ID 默认为 1
		activity.setUsesite(useplatform);
		return activity;
	}

	/**
	 * 删除满赠活动
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteFullgift")
	public ReusltItem deleteFullgift(String id) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.ToInt(id) < 0) {
				item.setCode(-101);
				item.setDesc("满赠活动ID参数错误");
				return item;
			}
			Integer userid = 1; // 用户ID 默认为1
			if (activityService.delete(StringUtilsEX.ToInt(id), userid) > 0) {
				item.setCode(0);
				item.setDesc("删除满赠活动成功!");
				LogHandle.info(LogType.Activity, MessageFormat.format("删除满赠活动成功! id:{0},userID:{1}",
						id,user.getUserId()), "/platform/Fullgift/deleteFullgift");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.删除.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "yxgl_FullgiftList.jsp", "/platform/Fullgift/deleteFullgift", "删除满赠活动");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"删除满赠活动操作记录出错! 异常信息:",
    								e, "/platform/Fullgift/deleteFullgift");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("删除满赠活动失败");
				LogHandle.warn(LogType.Activity, MessageFormat.format("删除满赠活动失败! id:{0},userID:{1}",
						id,user.getUserId()), "/platform/Fullgift/deleteFullgift");
			}

		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("删除满赠活动异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Activity,"删除满赠活动出现异常， 信息:",
					e, "/platform/Fullgift/deleteFullgift");
		}
		return item;
	}

	/**
	 * 获取满赠活动列表
	 * 
	 * @param num
	 * @param name
	 * @param shopid
	 * @param usetype
	 * @param status
	 * @param startf
	 * @param startt
	 * @param endf
	 * @param endt
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/getList")
	public ReusltItem getList(String num, String name, String shopid,
			String usetype, String status, String startf, String startt,
			String endf, String endt, String page, String size) {
		ReusltItem item = new ReusltItem();
		try {
			if (StringUtilsEX.ToInt(page) <= 0
					|| StringUtilsEX.ToInt(size) <= 0) {
				item.setCode(-101);
				item.setDesc("分页参数错误，pageindex:" + page + ",pagesize:" + size);
				return item;
			}
			CriteriaActivity cActivity = new CriteriaActivity();
			cActivity.setActType(ActivityTypeEnum.满赠.getValue());
			if (!StringUtilsEX.IsNullOrWhiteSpace(num)) {
				cActivity.setNum(num);
			}
			if (!StringUtilsEX.IsNullOrWhiteSpace(name)) {
				cActivity.setName(name);
			}
			if (StringUtilsEX.ToInt(shopid) > 0) {
				cActivity.setShopid(StringUtilsEX.ToInt(shopid));
			}
			if (StringUtilsEX.ToInt(status) >= 0) {
				cActivity.setStatus(StringUtilsEX.ToInt(status));
			}
			if (StringUtilsEX.ToInt(usetype) >= 0) {
				cActivity.setUserType(StringUtilsEX.ToInt(usetype));
			}
			if (!StringUtilsEX.IsNullOrWhiteSpace(startf)) {
				cActivity.setStartFrom(StringUtilsEX.ToShortDate(startf));
			}
			if (!StringUtilsEX.IsNullOrWhiteSpace(startt)) {
				cActivity.setStartTo(StringUtilsEX.ToShortDate(startt));
			}
			if (!StringUtilsEX.IsNullOrWhiteSpace(endf)) {
				cActivity.setEndFrom(StringUtilsEX.ToShortDate(endf));
			}
			if (!StringUtilsEX.IsNullOrWhiteSpace(endt)) {
				cActivity.setEndTo(StringUtilsEX.ToShortDate(endt));
			}
			PageBean pBean = activityService.getList(cActivity,
					StringUtilsEX.ToInt(page), StringUtilsEX.ToInt(size));
			item.setCode(0);
			item.setData(pBean.getBeanList());
			item.setMaxRow(pBean.getTr());
			item.setPageIndex(pBean.getPc());

		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("获取满赠活动列表异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Activity,"获取满赠活动列表出现异常， 信息:",
					e, "/platform/Fullgift/getList");
		}
		return item;
	}

	/**
	 * 启用/禁用
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("/updateStatus")
	public ReusltItem updateStatus(String id, String status) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.ToInt(id) <= 0) {
				item.setCode(-101);
				item.setDesc("活动ID参数错误");
				return item;
			}
			if (StringUtilsEX.ToInt(status) < 0) {
				item.setCode(-102);
				item.setDesc("活动状态参数错误");
				return item;
			}
			if (activityService.updateStatus(StringUtilsEX.ToInt(id),
					StringUtilsEX.ToInt(status)) > 0) {
				item.setCode(0);
				item.setDesc("编辑满赠活动状态成功!");
				LogHandle.info(LogType.Activity, MessageFormat.format("编辑满赠活动状态成功! id:{0},status:{1},userID:{2}",
						id,status,user.getUserId()), "/platform/Fullgift/updateStatus");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.修改.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "yxgl_FullgiftList.jsp", "/platform/Fullgift/updateStatus", "修改满赠活动状态");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"修改满赠活动状态操作记录出错! 异常信息:",
    								e, "/platform/Fullgift/updateStatus");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("编辑满赠活动状态失败");
				LogHandle.info(LogType.Activity, MessageFormat.format("编辑满赠活动状态失败! id:{0},status:{1},userID:{2}",
						id,status,user.getUserId()), "/platform/Fullgift/updateStatus");
			}
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("编辑满赠活动状态异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Activity, "编辑满赠活动状态出现异常， 信息:",
					e, "/platform/Fullgift/updateStatus");
		}
		return item;
	}

	/**
	 * 审核
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateCheck")
	public ReusltItem updateCheck(String id) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.ToInt(id) <= 0) {
				item.setCode(-101);
				item.setDesc("活动ID参数错误");
				return item;
			}
			if (activityService.updateCheck(StringUtilsEX.ToInt(id)) > 0) {
				item.setCode(0);
				item.setDesc("审核满赠活动成功!");
				LogHandle.info(LogType.Activity, MessageFormat.format("审核满赠活动成功! id:{0},userID:{1}",
						id,user.getUserId()), "/platform/Fullgift/updateCheck");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.修改.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "yxgl_FullgiftList.jsp", "/platform/Fullgift/updateCheck", "修改审核满赠活动");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"修改审核满赠活动操作记录出错! 异常信息:",
    								e, "/platform/Fullgift/updateCheck");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("审核满赠活动失败");
				LogHandle.info(LogType.Activity, MessageFormat.format("审核满赠活动失败! id:{0},userID:{1}",
						id,user.getUserId()), "/platform/Fullgift/updateCheck");
			}
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("审核满赠活动异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Activity, "审核满赠活动出现异常， 信息:",
					e, "/platform/Fullgift/updateCheck");
		}
		return item;
	}
	/**
	 * 批量审核
	 * @param ids
	 * @return
	 */
	@RequestMapping("/updateCheckList")
	public ReusltItem updateCheckList(String ids){
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			List<Integer> idlist=new ArrayList<Integer>();
			for (String id : ids.split(",")) {
				if (StringUtilsEX.ToInt(id) <= 0) {
					item.setCode(-101);
					item.setDesc("ID参数错误，id:" + id);
					return item;
				}
				idlist.add(StringUtilsEX.ToInt(id));
			}		
			if (activityService.updateCheckList(idlist) > 0) {
				item.setCode(0);
				item.setDesc("批量审核满赠活动成功");
				LogHandle.info(LogType.Activity, MessageFormat.format("批量审核满赠活动成功! id集合:{0},userID:{1}",
						ids,user.getUserId()), "/platform/Fullgift/updateCheckList");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.修改.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "yxgl_FullgiftCheck.jsp", "/platform/Fullgift/updateCheckList", "修改批量审核满赠活动");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"修改批量审核满赠活动操作记录出错! 异常信息:",
    								e, "/platform/Fullgift/updateCheckList");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("批量审核满赠活动失败");
				LogHandle.info(LogType.Activity, MessageFormat.format("批量审核满赠活动失败! id集合:{0},userID:{1}",
						ids,user.getUserId()), "/platform/Fullgift/updateCheckList");
			}
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("批量审核满赠活动异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Activity, "批量审核满赠活动出现异常， 信息:",
					e, "/platform/Fullgift/updateCheckList");
		}
		return item;
	}
	
	/**
	 * 添加赠品
	 * @param actid
	 * @param givetype
	 * @param giveid
	 * @param givecount
	 * @param points
	 * @return
	 */
	@RequestMapping("/addGift")
	public ReusltItem addGift(String actid, String givetype, String giveid,
			String givecount, String points) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			Fullgift gift = new Fullgift();
			if (StringUtilsEX.ToInt(actid) <= 0) {
				item.setCode(-101);
				item.setDesc("活动ID参数错误：" + actid);
				return item;
			}
			if (StringUtilsEX.ToInt(givetype) == ManZengTypeEnum.积分.getValue()) {
				if (StringUtilsEX.ToInt(points) <= 0) {
					item.setCode(-102);
					item.setDesc("积分参数错误：" + points);
					return item;
				}
				gift.setPoints(StringUtilsEX.ToInt(points));
			} else {				
				if (StringUtilsEX.ToInt(giveid) <= 0) {
					item.setCode(-103);
					item.setDesc("赠送品ID参数错误：" + giveid);
					return item;
				}
				gift.setGiftid(StringUtilsEX.ToInt(giveid));
				if (StringUtilsEX.ToInt(givecount) <= 0) {
					item.setCode(-104);
					item.setDesc("赠送品数量参数错误：" + givecount);
					return item;
				}
				Fullgift fgift=activityService.getbyTypeAndGiftid(StringUtilsEX.ToInt(givetype), StringUtilsEX.ToInt(giveid));
				if(fgift!=null){
					item.setCode(-105);
					item.setDesc("该赠品已添加");
					return item;
				}
				gift.setGiftcount(StringUtilsEX.ToInt(givecount));
			}			
			gift.setActivityid(StringUtilsEX.ToInt(actid));
			gift.setGifttype(StringUtilsEX.ToInt(givetype));

			if (activityService.addGift(gift) > 0) {
				item.setCode(0);
				item.setDesc("添加赠品成功");
				LogHandle.info(LogType.Activity, MessageFormat.format("添加赠品成功! 添加时间:{0},活动id:{1},userID:{2}",
						new Date(), actid,user.getUserId()), "/platform/Fullgift/addGift");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.添加.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "yxgl_GiftList.jsp", "/platform/Fullgift/addGift", "添加赠品");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"添加赠品操作记录出错! 异常信息:",
    								e, "/platform/Fullgift/addGift");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("添加赠品失败");
				LogHandle.info(LogType.Activity, MessageFormat.format("添加赠品成功! 添加时间:{0},活动id:{1},userID:{2}",
						new Date(), actid,user.getUserId()), "/platform/Fullgift/addGift");
			}
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("添加赠品出现异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Activity,"添加赠品出现异常， 信息:",
					e, "/platform/Fullgift/addGift");
		}
		return item;
	}

	/**
	 * 删除赠品
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteGift")
	public ReusltItem deleteGift(String id) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.ToInt(id) < 0) {
				item.setCode(-101);
				item.setDesc("ID参数错误：" + id);
				return item;
			}
			if (activityService.deleteGift(StringUtilsEX.ToInt(id)) > 0) {
				item.setCode(0);
				item.setDesc("删除赠品成功");
				LogHandle.info(LogType.Activity, MessageFormat.format("删除赠品成功！删除时间:{0},ID:{1},userID:{2}",
						new Date(),id,user.getUserId()), "/platform/Fullgift/deleteGift");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.删除.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "yxgl_GiftList.jsp", "/platform/Fullgift/deleteGift", "删除赠品");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"删除赠品操作记录出错! 异常信息:",
    								e, "/platform/Fullgift/deleteGift");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("删除赠品失败");
				LogHandle.info(LogType.Activity, MessageFormat.format("删除赠品失败！删除时间:{0},ID:{1},userID:{2}",
						new Date(),id,user.getUserId()), "/platform/Fullgift/deleteGift");
			}
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("删除赠品出现异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Activity,"删除赠品出现异常， 信息:",
					e, "/platform/Fullgift/deleteGift");
		}
		return item;
	}

	/**
	 * 修改赠品数量
	 * 
	 * @param id
	 * @param count
	 * @return
	 */
	@RequestMapping("/changeCount")
	public ReusltItem changeCount(String id, String count) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.ToInt(id) < 0) {
				item.setCode(-101);
				item.setDesc("ID参数错误：" + id);
				return item;
			}
			if (StringUtilsEX.ToInt(count) <= 0) {
				item.setCode(-102);
				item.setDesc("数量参数错误：" + count);
				return item;
			}
			if (activityService.changeCount(StringUtilsEX.ToInt(id),
					StringUtilsEX.ToInt(count)) > 0) {
				item.setCode(0);
				item.setDesc("修改赠品数量成功");
				LogHandle.info(LogType.Activity, MessageFormat.format("修改赠品数量成功！修改时间:{0},满赠活动赠品id:{1},userID:{2}",
						new Date(),id,user.getUserId()), "/platform/Fullgift/changeCount");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.修改.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "yxgl_GiftList.jsp", "/platform/Fullgift/changeCount", "修改赠品数量");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"修改赠品数量操作记录出错! 异常信息:",
    								e, "/platform/Fullgift/changeCount");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("修改赠品数量失败");
				LogHandle.info(LogType.Activity, MessageFormat.format("修改赠品数量失败！修改时间:{0},满赠活动赠品id:{1},userID:{2}",
						new Date(),id,user.getUserId()), "/platform/Fullgift/changeCount");
			}
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("修改赠品数量出现异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Activity, "修改赠品数量出现异常， 信息:",
					e, "/platform/Fullgift/changeCount");
		}
		return item;
	}

	/**
	 * 修改赠品积分
	 * 
	 * @param id
	 * @param points
	 * @return
	 */
	@RequestMapping("/changePoint")
	public ReusltItem changePoint(String id, String points) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.ToInt(id) < 0) {
				item.setCode(-101);
				item.setDesc("ID参数错误：" + id);
				return item;
			}
			if (StringUtilsEX.ToInt(points) <= 0) {
				item.setCode(-102);
				item.setDesc("积分参数错误：" + points);
				return item;
			}
			if (activityService.changePoint(StringUtilsEX.ToInt(id),
					StringUtilsEX.ToInt(points)) > 0) {
				item.setCode(0);
				item.setDesc("修改积分成功");
				LogHandle.info(LogType.Activity, MessageFormat.format("修改赠品数量成功！"
						+ "修改时间:{0},满赠活动赠品id:{1},积分:{2},userID:{3}",
						new Date(),id,points,user.getUserId()), "/platform/Fullgift/changePoint");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.修改.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "yxgl_GiftList.jsp", "/platform/Fullgift/changePoint", "修改赠品积分");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"修改赠品积分操作记录出错! 异常信息:",
    								e, "/platform/Fullgift/changePoint");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("修改积分失败");
				LogHandle.info(LogType.Activity, MessageFormat.format("修改积分失败！"
						+ "修改时间:{0},满赠活动赠品id:{1},积分:{2},userID:{3}",
						new Date(),id,points,user.getUserId()), "/platform/Fullgift/changePoint");
			}
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("修改积分出现异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Activity, "修改积分出现异常， 信息:",
					e, "/platform/Fullgift/changeCount");
		}
		return item;
	}

	/**
	 * 查询满赠活动赠品列表
	 * 
	 * @param actid
	 * @return
	 */
	@RequestMapping("/getGiftList")
	public ReusltItem getGiftList(String actid) {
		ReusltItem item = new ReusltItem();
		try {
			if (StringUtilsEX.ToInt(actid) <= 0) {
				item.setCode(-101);
				item.setDesc("活动ID参数错误：" + actid);
				return item;
			}
			item.setData(activityService.getGiftList(StringUtilsEX.ToInt(actid)));
			item.setCode(0);
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("查询满赠活动赠品列表异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Activity, "查询满赠活动赠品列表出现异常， 信息:",
					e, "/platform/Fullgift/getGiftList");
		}
		return item;
	}

	/**
	 * 获取满赠活动审核列表
	 * 
	 * @param num
	 * @param name
	 * @param shopid
	 * @param usetype
	 * @param startf
	 * @param startt
	 * @param endf
	 * @param endt
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/getCheckList")
	public ReusltItem getCheckList(String num, String name, String shopid,
			String usetype, String startf, String startt, String endf,
			String endt, String page, String size) {
		ReusltItem item = new ReusltItem();
		try {
			if (StringUtilsEX.ToInt(page) <= 0
					|| StringUtilsEX.ToInt(size) <= 0) {
				item.setCode(-101);
				item.setDesc("分页参数错误，pageindex:" + page + ",pagesize:" + size);
				return item;
			}
			CriteriaActivity cActivity = new CriteriaActivity();
			cActivity.setActType(ActivityTypeEnum.满赠.getValue());
			if (!StringUtilsEX.IsNullOrWhiteSpace(num)) {
				cActivity.setNum(num);
			}
			if (!StringUtilsEX.IsNullOrWhiteSpace(name)) {
				cActivity.setName(name);
			}
			if (StringUtilsEX.ToInt(shopid) > 0) {
				cActivity.setShopid(StringUtilsEX.ToInt(shopid));
			}

			cActivity.setStatus(0); // 未审核
			if (StringUtilsEX.ToInt(usetype) >= 0) {
				cActivity.setUserType(StringUtilsEX.ToInt(usetype));
			}
			if (!StringUtilsEX.IsNullOrWhiteSpace(startf)) {
				cActivity.setStartFrom(StringUtilsEX.ToShortDate(startf));
			}
			if (!StringUtilsEX.IsNullOrWhiteSpace(startt)) {
				cActivity.setStartTo(StringUtilsEX.ToShortDate(startt));
			}
			if (!StringUtilsEX.IsNullOrWhiteSpace(endf)) {
				cActivity.setEndFrom(StringUtilsEX.ToShortDate(endf));
			}
			if (!StringUtilsEX.IsNullOrWhiteSpace(endt)) {
				cActivity.setEndTo(StringUtilsEX.ToShortDate(endt));
			}
			PageBean pBean = activityService.getList(cActivity,
					StringUtilsEX.ToInt(page), StringUtilsEX.ToInt(size));
			item.setCode(0);
			item.setData(pBean.getBeanList());
			item.setMaxRow(pBean.getTr());
			item.setPageIndex(pBean.getPc());

		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("获取满赠活动审核列表异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Activity, "获取满赠活动审核列表出现异常， 信息:",
					e, "/platform/Fullgift/getCheckList");
		}
		return item;
	}
}
