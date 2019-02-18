package com.yinlian.wssc.platform.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yinlian.Enums.ConfigSetTypeEnum;
import com.yinlian.Enums.MenusTypeEnum;
import com.yinlian.Enums.OperateRecordsFromEnum;
import com.yinlian.Enums.OperateRecordsTypeEnum;
import com.yinlian.Extended.LogType;
import com.yinlian.wssc.platform.vo.ReusltItem;
import com.yinlian.wssc.web.dto.SessionUser;
import com.yinlian.wssc.web.interceptor.PageBean;
import com.yinlian.wssc.web.po.Configdictionary;
import com.yinlian.wssc.web.po.Role;
import com.yinlian.wssc.web.service.AccountsService;
import com.yinlian.wssc.web.service.ConfigSetService;
import com.yinlian.wssc.web.service.OperaterecordsService;
import com.yinlian.wssc.web.service.RoleService;
import com.yinlian.wssc.web.service.ShopService;
import com.yinlian.wssc.web.util.CriteriaMenu;
import com.yinlian.wssc.web.util.DebugConfig;
import com.yinlian.wssc.web.util.SessionState;
import com.yinlian.wssc.web.util.StringUtilsEX;
import com.yl.soft.log.LogHandle;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/platform/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AccountsService accountsService;

	@Autowired
	private ShopService shopService;
	
    @Autowired
    private ConfigSetService    configsetService;
	
    SessionUser user=null;
    @Autowired
    private OperaterecordsService operaterecordsService ;
	/**
	 * 添加角色
	 * 
	 * @param name
	 * @param status
	 * @param desc
	 * @return
	 */
	@RequestMapping("/addRole")
	public @ResponseBody ReusltItem addRole(String name, String status,
			String desc) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.IsNullOrWhiteSpace(name)) {
				item.setCode(-101);
				item.setDesc("角色名称不能为空");
				return item;
			}
			if (StringUtilsEX.ToInt(status) < 0) {
				item.setCode(-102);
				item.setDesc("角色状态参数错误，status:" + status);
				return item;
			}
			Role role = new Role();
			role.setCreatetime(new Date());
			role.setIsdel(false);
			role.setShopid(0);
			role.setName(name.trim());
			role.setStatus(StringUtilsEX.ToInt(status));
			role.setDescription(desc);
			if (roleService.insert(role) > 0) {
				item.setCode(0);
				item.setDesc("添加角色成功");
				LogHandle.info(LogType.Role, MessageFormat.format("添加角色成功!名称:{0},操作人ID:{1}", name,user.getUserId())
						,"/role/addRole");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.添加.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_RoleEdit.jsp", "/platform/role/addRole", "添加角色");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"添加角色操作记录出错! 异常信息:",
    								e, "/platform/role/addRole");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("添加角色失败");
				LogHandle.info(LogType.Role, MessageFormat.format("添加角色失败!名称:{0},操作人ID:{1}", name,user.getUserId()),
						"/role/addRole");
			}
		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("添加角色出现的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "添加角色出现的异常信息:" ,e,"/platform/role/addRole");
		}
		return item;
	}

	/**
	 * 编辑角色
	 * 
	 * @param id
	 * @param name
	 * @param status
	 * @param desc
	 * @return
	 */
	@RequestMapping("/updateRole")
	public @ResponseBody ReusltItem updateRole(String id, String name,
			String status, String desc) {
		ReusltItem item = new ReusltItem();
		try {
			if (StringUtilsEX.ToInt(id) <= 0) {
				item.setCode(-101);
				item.setDesc("角色ID参数错误，id:" + id);
				return item;
			}
			if (StringUtilsEX.IsNullOrWhiteSpace(name)) {
				item.setCode(-102);
				item.setDesc("角色名称不能为空");
				return item;
			}
			if (StringUtilsEX.ToInt(status) < 0) {
				item.setCode(-103);
				item.setDesc("角色状态参数错误，status:" + status);
				return item;
			}
			Role role = roleService.getByID(StringUtilsEX.ToInt(id));
			role.setName(name.trim());
			role.setStatus(StringUtilsEX.ToInt(status));
			role.setDescription(desc);
			if (roleService.update(role) > 0) {
				item.setCode(0);
				item.setDesc("编辑角色成功");
				LogHandle.info(LogType.Role, MessageFormat.format("编辑角色成功! ID:{0},名称:{1},操作人ID:{2}", 
						id,name,user.getUserId()),"/role/updateRole");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.修改.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_RoleEdit.jsp", "/platform/role/updateRole", "修改角色");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"修改角色操作记录出错! 异常信息:",
    								e, "/platform/role/updateRole");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("编辑角色失败");
				LogHandle.info(LogType.Role, MessageFormat.format("编辑角色失败! ID:{0},名称:{1},操作人ID:{2}", 
						id,name,user.getUserId()),"/role/updateRole");
			}

		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("编辑角色出现的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "编辑角色出现的异常信息:" ,e,"/platform/role/updateRole");
		}
		return item;
	}

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteRole")
	public @ResponseBody ReusltItem deleteRole(String id) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.ToInt(id) <= 0) {
				item.setCode(-101);
				item.setDesc("角色ID参数错误，id:" + id);
				return item;
			}
			if(accountsService.getByRoleID(StringUtilsEX.ToInt(id))!=null){
				item.setCode(-102);
				item.setDesc("角色已添加到账户，不能删除！");
				return item;
			}
			if (roleService.delete(StringUtilsEX.ToInt(id)) > 0) {
				item.setCode(0);
				item.setDesc("删除角色成功");
				LogHandle.info(LogType.Role, MessageFormat.format("删除角色成功! ID:{0},操作人ID:{1}", 
						id,user.getUserId()),"/role/deleteRole");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.删除.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_RoleList.jsp", "/platform/role/deleteRole", "删除角色");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"删除角色操作记录出错! 异常信息:",
    								e, "/platform/role/deleteRole");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("删除角色失败");
				LogHandle.info(LogType.Role, MessageFormat.format("删除角色失败! ID:{0},操作人ID:{1}", 
						id,user.getUserId()),"/role/deleteRole");
			}
		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("删除角色出现的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "删除角色出现的异常信息:" , e,"/platform/role/deleteRole");
		}
		return item;
	}

	/**
	 * 查询平台角色列表
	 * 
	 * @param name
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/getRoleList")
	public @ResponseBody ReusltItem getRoleList(String name, String page,
			String size) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.ToInt(page) <= 0
					|| StringUtilsEX.ToInt(size) <= 0) {
				item.setCode(-101);
				item.setDesc("分页参数错误，pageindex:" + page + ",pagesize:" + size);
				return item;
			}
			CriteriaMenu cMenu = new CriteriaMenu();
			if (!StringUtilsEX.IsNullOrWhiteSpace(name)) {
				cMenu.setRolename(name);
			}
			cMenu.setShopid(0);
			PageBean pBean = roleService.getList(cMenu,
					StringUtilsEX.ToInt(page), StringUtilsEX.ToInt(size));
			item.setCode(0);
			item.setData(pBean.getBeanList());
			item.setMaxRow(pBean.getTr());
			item.setPageIndex(pBean.getPc());
		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("删除角色出现的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "查询平台角色列表出现的异常信息:" , e,"/platform/role/getRoleList");
		}
		return item;
	}

	/**
	 * 查询店铺角色列表
	 * 
	 * @param name
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/getShopRoleList")
	public @ResponseBody ReusltItem getShopRoleList(String name, String shopid,
			String page, String size) {
		ReusltItem item = new ReusltItem();
		try {
			if (StringUtilsEX.ToInt(page) <= 0
					|| StringUtilsEX.ToInt(size) <= 0) {
				item.setCode(-101);
				item.setDesc("分页参数错误，pageindex:" + page + ",pagesize:" + size);
				return item;
			}
			CriteriaMenu cMenu = new CriteriaMenu();
			if (!StringUtilsEX.IsNullOrWhiteSpace(name)) {
				cMenu.setRolename(name);
			}
			if (StringUtilsEX.ToInt(shopid) > 0) {
				cMenu.setShopid(StringUtilsEX.ToInt(shopid));
			}
			PageBean pBean = roleService.getList(cMenu,
					StringUtilsEX.ToInt(page), StringUtilsEX.ToInt(size));
			item.setCode(0);
			item.setData(pBean.getBeanList());
			item.setMaxRow(pBean.getTr());
			item.setPageIndex(pBean.getPc());
		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("删除角色出现的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "查询店铺角色列表出现的异常信息:",e,"/platform/role/getShopRoleList");
		}
		return item;
	}

	/**
	 * 禁用/启用 角色状态
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("/updateStatus")
	public @ResponseBody ReusltItem updateStatus(String id, String status) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.ToInt(id) <= 0) {
				item.setCode(-101);
				item.setDesc("角色ID参数错误，id:" + id);
				return item;
			}
			if (StringUtilsEX.ToInt(status) < 0) {
				item.setCode(-102);
				item.setDesc("角色状态参数错误，status:" + status);
				return item;
			}
			if (roleService.updateStatus(StringUtilsEX.ToInt(status),
					StringUtilsEX.ToInt(id)) > 0) {
				item.setCode(0);
				item.setDesc("编辑角色状态成功");
				LogHandle.info(LogType.Role, MessageFormat.format("编辑角色状态成功! ID:{0},状态:{1},操作人ID:{2}", 
						id,status,user.getUserId()),"/role/updateStatus");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.修改.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_RoleList.jsp", "/platform/role/updateStatus", "修改角色状态");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"修改角色状态操作记录出错! 异常信息:",
    								e, "/platform/role/updateStatus");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("编辑角色状态失败");
				LogHandle.info(LogType.Role, MessageFormat.format("编辑角色状态失败! ID:{0},状态:{1},操作人ID:{2}", 
						id,status,user.getUserId()),"/role/updateStatus");
			}

		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("编辑角色状态出现的异常信息：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "编辑角色状态出现的异常信息:" ,e,"/platform/role/updateStatus");
		}
		return item;
	}

	/**
	 * 添加店铺角色
	 * 
	 * @param name
	 * @param status
	 * @param shopid
	 * @param desc
	 * @return
	 */
	@RequestMapping("/addShopRole")
	public @ResponseBody ReusltItem addShopRole(String name, String status,String shopid,
			String desc) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.IsNullOrWhiteSpace(name)) {
				item.setCode(-101);
				item.setDesc("角色名称不能为空");
				return item;
			}
			if (StringUtilsEX.ToInt(status) < 0) {
				item.setCode(-102);
				item.setDesc("角色状态参数错误，status:" + status);
				return item;
			}
			if (StringUtilsEX.ToInt(shopid) <= 0) {
				item.setCode(-103);
				item.setDesc("角色所属店铺参数错误，shopid:" + shopid);
				return item;
			}
			//获取店铺下角色个数
			List<Role> rolelist=roleService.selectShopRole(StringUtilsEX.ToInt(shopid));
			if(rolelist!=null){
				Configdictionary config=configsetService.showConfigSetByType(ConfigSetTypeEnum.角色个数.getValue());
				if(config!=null){
					if(config.getValue()!=null){
						if(rolelist.size()>=Integer.valueOf(config.getValue())){
							item.setCode(-104);
							item.setDesc("角色个数超过限制，不能添加。");
							return item;
						}
					}
					
				}
				
			}
			
			Role role = new Role();
			role.setCreatetime(new Date());
			role.setIsdel(false);
			role.setShopid(StringUtilsEX.ToInt(shopid));
			role.setName(name.trim());
			role.setStatus(StringUtilsEX.ToInt(status));
			role.setDescription(desc);
			if (roleService.insert(role) > 0) {
				item.setCode(0);
				item.setDesc("添加店铺角色成功");
				LogHandle.info(LogType.Role, MessageFormat.format("编辑角色状态成功! 角色名称:{0},操作人ID:{1}", 
						name,user.getUserId()),"/role/addShopRole");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.添加.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_dpRoleEdit.jsp", "/platform/role/addShopRole", "添加店铺角色");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"添加店铺角色操作记录出错! 异常信息:",
    								e, "/platform/role/addShopRole");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("添加店铺角色失败");
				LogHandle.info(LogType.Role, MessageFormat.format("编辑角色状态失败! 角色名称:{0},操作人ID:{1}", 
						name,user.getUserId()),"/role/addShopRole");
			}
		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("添加店铺角色出现的异常信息：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "添加店铺角色出现的异常信息:",e,"/platform/role/addShopRole");
		}
		return item;
	}

	/**
	 * 编辑店铺角色
	 * 
	 * @param id
	 * @param name
	 * @param status
	 * @param desc
	 * @return
	 */
	@RequestMapping("/updateShopRole")
	public @ResponseBody ReusltItem updateShopRole(String id, String name,
			String status, String desc) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.ToInt(id) <= 0) {
				item.setCode(-101);
				item.setDesc("角色ID参数错误，id:" + id);
				return item;
			}
			if (StringUtilsEX.IsNullOrWhiteSpace(name)) {
				item.setCode(-102);
				item.setDesc("角色名称不能为空");
				return item;
			}
			if (StringUtilsEX.ToInt(status) < 0) {
				item.setCode(-103);
				item.setDesc("角色状态参数错误，status:" + status);
				return item;
			}
			Role role = roleService.getByID(StringUtilsEX.ToInt(id));
			role.setName(name.trim());
			role.setStatus(StringUtilsEX.ToInt(status));
			role.setDescription(desc);
			if (roleService.update(role) > 0) {
				item.setCode(0);
				item.setDesc("编辑店铺角色成功");
				LogHandle.info(LogType.Role, MessageFormat.format("编辑店铺角色成功! ID:{0},名称:{1},操作人ID:{2}", 
						id,name,user.getUserId()),"/role/updateShopRole");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.修改.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_dpRoleEdit.jsp", "/platform/role/updateShopRole", "修改店铺角色");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"修改店铺角色操作记录出错! 异常信息:",
    								e, "/platform/role/updateShopRole");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("编辑店铺角色失败");
				LogHandle.info(LogType.Role, MessageFormat.format("编辑店铺角色失败! ID:{0},名称:{1},操作人ID:{2}", 
						id,name,user.getUserId()),"/role/updateShopRole");
			}

		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("编辑店铺角色出现的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "编辑店铺角色出现的异常信息:" , e,"/platform/role/updateShopRole");
		}
		return item;
	}

	/**
	 * 删除店铺角色
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteShopRole")
	public @ResponseBody ReusltItem deleteShopRole(String id) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.ToInt(id) <= 0) {
				item.setCode(-101);
				item.setDesc("角色ID参数错误，id:" + id);
				return item;
			}
			if(accountsService.getByRoleID(StringUtilsEX.ToInt(id))!=null){
				item.setCode(-102);
				item.setDesc("角色已添加到账户，不能删除！");
				return item;
			}
			if (roleService.delete(StringUtilsEX.ToInt(id)) > 0) {
				item.setCode(0);
				item.setDesc("删除店铺角色成功");
				LogHandle.info(LogType.Role, MessageFormat.format("删除店铺角色成功! 角色ID:{0},操作人ID:{1}", 
						id,user.getUserId()),"/role/deleteShopRole");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.删除.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_dpRoleList.jsp", "/platform/role/deleteShopRole", "删除店铺角色");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"删除店铺角色操作记录出错! 异常信息:",
    								e, "/platform/role/deleteShopRole");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("删除店铺角色失败");
				LogHandle.info(LogType.Role, MessageFormat.format("删除店铺角色失败! 角色ID:{0},操作人ID:{1}", 
						id,user.getUserId()),"/role/deleteShopRole");
			}
		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("编辑店铺角色出现的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "删除店铺角色出现的异常信息:", e,"/platform/role/deleteShopRole");
		}
		return item;
	}

	/**
	 * 禁用/启用 角色状态
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("/updateShopStatus")
	public @ResponseBody ReusltItem updateShopStatus(String id, String status) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.ToInt(id) <= 0) {
				item.setCode(-101);
				item.setDesc("角色ID参数错误，id:" + id);
				return item;
			}
			if (StringUtilsEX.ToInt(status) < 0) {
				item.setCode(-102);
				item.setDesc("角色状态参数错误，status:" + status);
				return item;
			}
			if (roleService.updateStatus(StringUtilsEX.ToInt(status),
					StringUtilsEX.ToInt(id)) > 0) {
				item.setCode(0);
				item.setDesc("编辑店铺角色状态成功");
				LogHandle.info(LogType.Role, MessageFormat.format("编辑店铺角色状态成功! 角色ID:{0},状态:{1},操作人ID:{2}", 
						id,status,user.getUserId()),"/role/updateShopStatus");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.修改.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_dpRoleList.jsp", "/platform/role/updateShopStatus", "修改店铺角色状态");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"修改店铺角色状态操作记录出错! 异常信息:",
    								e, "/platform/role/updateShopStatus");
    					}
    					
    				}
    			});
				
			} else {
				item.setCode(-200);
				item.setDesc("编辑店铺角色状态失败");
				LogHandle.info(LogType.Role, MessageFormat.format("编辑店铺角色状态失败! 角色ID:{0},状态:{1},操作人ID:{2}", 
						id,status,user.getUserId()),"/role/updateShopStatus");
			}

		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("编辑店铺角色状态的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "编辑店铺角色状态的异常信息:",e,"/platform/role/updateShopStatus");
		}
		return item;
	}

	/**
	 * 获取平台管理菜单
	 * 
	 * @return
	 */
	@RequestMapping("/getMenusTree")
	public @ResponseBody ReusltItem getMenusTree() {
		ReusltItem item = new ReusltItem();
		try {
			// type 0-菜单 1-按钮
			item.setData(roleService.getByMenuType(0,
					MenusTypeEnum.系统后台.getValue()));
			item.setCode(0);
		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("编辑店铺角色状态的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "获取菜单树结构的异常信息:" , e,"/platform/role/getMenusTree");
		}
		return item;
	}

	/**
	 * 获取卖家中心菜单
	 * 
	 * @return
	 */
	@RequestMapping("/getShopMenusTree")
	public @ResponseBody ReusltItem getShopMenusTree() {
		ReusltItem item = new ReusltItem();
		try {
			// type 0-菜单 1-按钮
			item.setData(roleService.getByMenuType(0,
					MenusTypeEnum.卖家中心.getValue()));
			item.setCode(0);
		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("获取菜单树结构的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "获取菜单树结构的异常信息:" , e,"/platform/role/getShopMenusTree");
		}
		return item;
	}

	@RequestMapping("/getMenuByRoleID")
	public @ResponseBody ReusltItem getMenuByRoleID(String roleid){
		ReusltItem item = new ReusltItem();
		try {
			if (StringUtilsEX.ToInt(roleid) <= 0) {
				item.setCode(-101);
				item.setDesc("角色ID参数错误，roleid:" + roleid);
				return item;
			}
			item.setData(roleService.selectByRoleID(StringUtilsEX.ToInt(roleid)));
			item.setCode(0);
			
		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("根据角色ID获取权限出现的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "根据角色ID获取权限出现的异常信息:" , e,"/platform/role/getMenuByRoleID");
		}
		return item;
	}
	
	/**
	 * 平台角色编辑权限
	 * 
	 * @param menuids
	 * @param roleid
	 * @return
	 */
	@RequestMapping("/addRoleMenu")
	public @ResponseBody ReusltItem addRoleMenu(String menuids, String roleid) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			List<Integer> idlist = new ArrayList<Integer>();
			if (StringUtilsEX.ToInt(roleid) <= 0) {
				item.setCode(-101);
				item.setDesc("角色ID参数错误，roleid:" + roleid);
				return item;
			}
			for (String id : menuids.split(",")) {
				if (StringUtilsEX.ToInt(id) <= 0) {
					item.setCode(-102);
					item.setDesc("菜单ID参数错误，id:" + id);
					return item;
				}
				idlist.add(StringUtilsEX.ToInt(id));
			}
			if (roleService.insertList(idlist, StringUtilsEX.ToInt(roleid)) > 0) {
				item.setCode(0);
				item.setDesc("平台角色编辑权限成功");
				LogHandle.info(LogType.Role, MessageFormat.format("平台角色编辑权限成功! roleid:{0},菜单ID集合:{1},操作人ID:{2}", 
						roleid,menuids,user.getUserId()),"/role/addRoleMenu");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.修改.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_RoleList.jsp", "/platform/role/addRoleMenu", "修改平台角色权限");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"修改平台角色权限操作记录出错! 异常信息:",
    								e, "/platform/role/addRoleMenu");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("平台角色编辑权限失败");
				LogHandle.info(LogType.Role, MessageFormat.format("平台角色编辑权限失败! roleid:{0},菜单ID集合:{1},操作人ID:{2}", 
						roleid,menuids,user.getUserId()),"/role/addRoleMenu");
			}

		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("平台角色编辑权限的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "平台角色编辑权限的异常信息:" + e.getMessage(),"/role/addRoleMenu");
		}
		return item;
	}

	/**
	 * 平台角色清空权限
	 * 
	 * @param roleid
	 * @return
	 */
	@RequestMapping("/deleteRoleMenu")
	public @ResponseBody ReusltItem deleteRoleMenu(String roleid) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.ToInt(roleid) <= 0) {
				item.setCode(-101);
				item.setDesc("角色ID参数错误，roleid:" + roleid);
				return item;
			}
			if (roleService.deleteByRoleID(StringUtilsEX.ToInt(roleid)) > 0) {
				item.setCode(0);
				item.setDesc("平台角色清空权限成功");
				LogHandle.info(LogType.Role, MessageFormat.format("平台角色清空权限成功! roleid:{0},操作人ID:{1}", 
						roleid,user.getUserId()),"/role/deleteRoleMenu");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.删除.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_RoleList.jsp", "/platform/role/deleteRoleMenu", "平台角色清空权限");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"平台角色清空权限操作记录出错! 异常信息:",
    								e, "/platform/role/deleteRoleMenu");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("平台角色清空权限失败");
				LogHandle.info(LogType.Role, MessageFormat.format("平台角色清空权限失败! roleid:{0},操作人ID:{1}", 
						roleid,user.getUserId()),"/role/deleteRoleMenu");
			}
		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("平台角色清空权限的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "平台角色清空权限的异常信息:" + e.getMessage(),"/role/deleteRoleMenu");
		}
		return item;
	}

	/**
	 * 店铺角色编辑权限
	 * 
	 * @param menuids
	 * @param roleid
	 * @return
	 */
	@RequestMapping("/addShopRoleMenu")
	public @ResponseBody ReusltItem addShopRoleMenu(String menuids,
			String roleid) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			List<Integer> idlist = new ArrayList<Integer>();
			if (StringUtilsEX.ToInt(roleid) <= 0) {
				item.setCode(-101);
				item.setDesc("角色ID参数错误，roleid:" + roleid);
				return item;
			}
			for (String id : menuids.split(",")) {
				if (StringUtilsEX.ToInt(id) <= 0) {
					item.setCode(-102);
					item.setDesc("菜单ID参数错误，id:" + id);
					return item;
				}
				idlist.add(StringUtilsEX.ToInt(id));
			}
			if (roleService.insertList(idlist, StringUtilsEX.ToInt(roleid)) > 0) {
				item.setCode(0);
				item.setDesc("店铺角色编辑权限成功");
				LogHandle.info(LogType.Role, MessageFormat.format("店铺角色编辑权限成功! roleid:{0},菜单ID集合:{1},操作人ID:{2}", 
						roleid,menuids,user.getUserId()),"/role/addShopRoleMenu");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.修改.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_dpRoleList.jsp", "/platform/role/addShopRoleMenu", "修改店铺角色权限");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"修改店铺角色权限操作记录出错! 异常信息:",
    								e, "/platform/role/addShopRoleMenu");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("店铺角色编辑权限失败");
				LogHandle.info(LogType.Role, MessageFormat.format("店铺角色编辑权限成功! roleid:{0},菜单ID集合:{1},操作人ID:{2}", 
						roleid,menuids,user.getUserId()),"/role/addShopRoleMenu");
			}

		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("店铺角色编辑权限出现的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "店铺角色编辑权限出现的异常信息:" + e.getMessage(),"/role/addShopRoleMenu");
		}
		return item;
	}

	/**
	 * 店铺角色清空权限
	 * 
	 * @param roleid
	 * @return
	 */
	@RequestMapping("/deleteShopRoleMenu")
	public @ResponseBody ReusltItem deleteShopRoleMenu(String roleid) {
		ReusltItem item = new ReusltItem();
		try {
			user=SessionState.GetCurrentUser();
			if (StringUtilsEX.ToInt(roleid) <= 0) {
				item.setCode(-101);
				item.setDesc("角色ID参数错误，roleid:" + roleid);
				return item;
			}
			if (roleService.deleteByRoleID(StringUtilsEX.ToInt(roleid)) > 0) {
				item.setCode(0);
				item.setDesc("店铺角色清空权限成功");
				LogHandle.info(LogType.Role, MessageFormat.format("店铺角色清空权限成功! roleid:{0},操作人ID:{1}", 
						roleid,user.getUserId()),"/role/deleteShopRoleMenu");
				//异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.删除.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_dpRoleList.jsp", "/platform/role/deleteShopRoleMenu", "店铺角色清空权限");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"店铺角色清空权限操作记录出错! 异常信息:",
    								e, "/platform/role/deleteShopRoleMenu");
    					}
    					
    				}
    			});
			} else {
				item.setCode(-200);
				item.setDesc("店铺角色清空权限失败");
				LogHandle.info(LogType.Role, MessageFormat.format("店铺角色清空权限成功! roleid:{0},操作人ID:{1}", 
						roleid,user.getUserId()),"/role/deleteShopRoleMenu");
			}
		} catch (Exception e) {
			item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("店铺角色清空权限出现的异常：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
			LogHandle.error(LogType.Role, "店铺角色清空权限出现的异常信息:" + e.getMessage(),"/role/deleteShopRoleMenu");
		}
		return item;
	}
	/**
	 * 模糊检索角色列表
	 * @param name
	 * @return
	 */
	@RequestMapping("/getPlatRoleStartWithName")
	public @ResponseBody ReusltItem getPlatRoleStartWithName(String name){
		ReusltItem item = new ReusltItem();
        try {
       	
            List<Role> list = roleService.getRoleStartWithName(name,0);
            item.setCode(0);
            item.setData(list);
            item.setDesc("获取成功");
        } catch (Exception e) {
        	item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("模糊检索角色列表的异常信息：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
        	LogHandle.error(LogType.Role, "模糊检索角色列表的异常信息:" + e.getMessage(),"/role/getPlatRoleStartWithName");
        }
        return item;
	}
	/**
	 * 模糊检索角色列表
	 * @param name
	 * @return
	 */
	@RequestMapping("/getRoleStartWithName")
	public @ResponseBody ReusltItem getRoleStartWithName(String name,String shopid){
		ReusltItem item = new ReusltItem();
        try {
        	if(StringUtilsEX.ToInt(shopid)<0){
        		item.setCode(-101);
				item.setDesc("店铺ID参数错误，shopid:" + shopid);
				return item;
        	}
        	
            List<Role> list = roleService.getRoleStartWithName(name,StringUtilsEX.ToInt(shopid));
            item.setCode(0);
            item.setData(list);
            item.setDesc("获取成功");
        } catch (Exception e) {
        	item.setCode(-900);
			 if (DebugConfig.BLUETOOTH_DEBUG) {
				 item.setDesc("模糊检索角色列表的异常信息：" + e.getMessage());
				} else {
					item.setDesc("系统错误！");
				}
        	LogHandle.error(LogType.Role, "模糊检索角色列表的异常信息:" + e.getMessage(),"/role/getRoleStartWithName");
        }
        return item;
	}

}
