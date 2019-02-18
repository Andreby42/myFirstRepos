/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.yinlian.wssc.platform.controller;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yinlian.Enums.OperateRecordsFromEnum;
import com.yinlian.Enums.OperateRecordsTypeEnum;
import com.yinlian.Extended.LogType;
import com.yinlian.wssc.platform.vo.ReusltItem;
import com.yinlian.wssc.web.dto.SessionUser;
import com.yinlian.wssc.web.interceptor.PageBean;
import com.yinlian.wssc.web.po.Menus;
import com.yinlian.wssc.web.service.MenuService;
import com.yinlian.wssc.web.service.MessageService;
import com.yinlian.wssc.web.service.OperaterecordsService;
import com.yinlian.wssc.web.service.ShopService;
import com.yinlian.wssc.web.service.UsercapitalService;
import com.yinlian.wssc.web.util.CriteriaMenu;
import com.yinlian.wssc.web.util.DebugConfig;
import com.yinlian.wssc.web.util.SessionState;
import com.yinlian.wssc.web.util.StringUtilsEX;
import com.yl.soft.log.LogHandle;

/**
 * 菜单的控制层(平台,卖家,前台)
 * 
 * @author Administrator
 * @version $Id: MenuController.java, v 0.1 2016年3月9日 下午2:24:08 Administrator
 *          Exp $
 */
@RestController
@RequestMapping("/platform/menu")
public class MenuController {

    @Autowired
    private MenuService        menuService;

    @Autowired
    private UsercapitalService usercapitalService;

    @Autowired
    private ShopService        shopService;
    @Autowired
    private MessageService     messageService;

    SessionUser                user = null;
	@Autowired
    private OperaterecordsService operaterecordsService ;

    /**
     * 获取所有的一级菜单 (包含所有的子菜单)
     * 
     * @return
     */
    @RequestMapping("/queryAllMenus")
    public ReusltItem queryAllMenus() {
        ReusltItem item = new ReusltItem();
        try {
            List<Menus> menus = menuService.queryAllPlatformMenus(0);
            item.setCode(0);
            item.setData(menus);
            item.setDesc("查询出所有一级菜单");
        } catch (Exception e) {
        	item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("查询所有一级菜单异常：" + e.getMessage());
			} else {
				item.setDesc("系统错误！");
			}
            LogHandle.error(LogType.Menu, "查询所有一级菜单异常：", e,
                "/platform/menu/queryAllMenus");
        }
        return item;
    }

    
    /**
     * 添加菜单
     * 
     * @param name
     * @param menuurl
     * @param menutype
     * @param type
     * @param status
     * @param haschild
     * @param firstID
     * @param secondID
     * @param thirdID
     * @return
     */
    @RequestMapping("/addMenu")
    public ReusltItem addMenu(String name, String menuurl, String menutype,
                                            String type, String status, String haschild,
                                            String firstID, String secondID, String thirdID) {
        ReusltItem item = new ReusltItem();
        try {
        	user= SessionState.GetCurrentUser();
            Menus menus = new Menus();
            menus = checkParam(name, menuurl, menutype, type, status, haschild, firstID, secondID,
                thirdID, "0", item);
            if (item.getCode() < 0) {
                return item;
            }
            if (menuService.insert(menus) > 0) {
                LogHandle.info(LogType.Menu,
                    MessageFormat.format("添加菜单成功,名称:{0},链接:{1},", name, menuurl),
                    "/platform/menu/addMenu");
                item.setCode(0);
                item.setDesc("添加菜单成功");
              //异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.添加.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_Menuadd.jsp", "/platform/menu/addMenu", "添加菜单");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"添加菜单操作记录出错! 异常信息:",
    								e, "/platform/menu/addMenu");
    					}
    					
    				}
    			});
            } else {
                LogHandle.info(LogType.Menu,
                    MessageFormat.format("添加菜单失败,名称:{0},链接:{1},", name, menuurl),
                    "/platform/menu/addMenu");
                item.setCode(-200);
                item.setDesc("添加菜单失败");
            }

        } catch (Exception e) {
        	item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("添加菜单异常：" + e.getMessage());
			} else {
				item.setDesc("系统错误！");
			}
            LogHandle.error(LogType.Menu, "添加菜单异常：" ,e, "/platform/menu/addMenu");
        }
        return item;
    }

    /**
     * 编辑菜单
     * 
     * @param id
     * @param name
     * @param menuurl
     * @param menutype
     * @param type
     * @param status
     * @param haschild
     * @param firstID
     * @param secondID
     * @param thirdID
     * @return
     */
    @RequestMapping("/updateMenu")
    public ReusltItem updateMenu(String id, String name, String menuurl,
                                               String menutype, String type, String status,
                                               String haschild, String firstID, String secondID,
                                               String thirdID) {
        ReusltItem item = new ReusltItem();
        try {
        	user= SessionState.GetCurrentUser();
            Menus menus = new Menus();
            if (StringUtilsEX.ToInt(id) <= 0) {
                item.setCode(-101);
                item.setDesc("菜单ID参数错误，id：" + id);
                return item;
            } else {
                menus = menuService.getByID(StringUtilsEX.ToInt(id));
            }
            List<Menus> menulist = menuService.selectByFullpath(menus.getFullpath());
            menus = checkParam(name, menuurl, menutype, type, status, haschild, firstID, secondID,
                thirdID, id, item);
            if (item.getCode() < 0) {
                return item;
            }
            if (menuService.update(menus, menulist) > 0) {
                LogHandle.info(LogType.Menu,
                    MessageFormat.format("编辑菜单成功,ID:{0},名称:{1},链接:{2}", id, name, menuurl),
                    "/platform/menu/updateMenu");
                item.setCode(0);
                item.setDesc("编辑菜单成功");
              //异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.修改.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_Menuedit.jsp", "/platform/menu/updateMenu", "修改菜单");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"修改菜单操作记录出错! 异常信息:",
    								e, "/platform/menu/updateMenu");
    					}
    					
    				}
    			});
            } else {
                LogHandle.info(LogType.Menu,
                    MessageFormat.format("编辑菜单失败,ID:{0},名称:{1},链接:{2}", id, name, menuurl),
                    "/platform/menu/updateMenu");
                item.setCode(-200);
                item.setDesc("编辑菜单失败");
            }

        } catch (Exception e) {
        	item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("编辑菜单异常：" + e.getMessage());
			} else {
				item.setDesc("系统错误！");
			}
            LogHandle.error(LogType.Menu, "编辑菜单异常：" ,e, "/platform/menu/updateMenu");
        }
        return item;
    }

    private Menus checkParam(String name, String menuurl, String menutype, String type,
                             String status, String haschild, String firstid, String secondid,
                             String thirdid, String id, ReusltItem item) {
        Menus menus = new Menus();
        Integer ID = StringUtilsEX.ToInt(id);
        Integer firstID = StringUtilsEX.ToInt(firstid), secondID = StringUtilsEX.ToInt(secondid), thirdID = StringUtilsEX
            .ToInt(thirdid);

        if (ID < 0) {
            item.setCode(-101);
            item.setDesc("菜单ID参数错误，id：" + id);
            return null;
        }

        if (firstID < 0) {
            item.setCode(-102);
            item.setDesc("菜单父ID参数错误，firstid：" + firstid);
            return null;
        }

        if (StringUtilsEX.isBlank(name)) {
            item.setCode(-103);
            item.setDesc("菜单名称不能为空");
            return null;
        }
        Boolean HasChild = StringUtilsEX.ToBoolean(haschild);
        // if(HasChild<0){
        // item.setCode(-108);
        // item.setDesc("是否有子菜单参数错误，haschild："+haschild);
        // return null;
        // }
        if (HasChild != true && HasChild != false) {
            item.setCode(-108);
            item.setDesc("是否有子菜单参数错误，haschild：" + haschild);
            return null;
        } else {
            if (HasChild == false) {
                if (StringUtilsEX.isBlank(menuurl)) {
                    item.setCode(-104);
                    item.setDesc("菜单链接不能为空");
                    return null;
                }
            }
        }

        if (StringUtilsEX.ToInt(menutype) < 0) {
            item.setCode(-105);
            item.setDesc("菜单类型（所属平台）参数错误，menutype：" + menutype);
            return null;
        }
        if (StringUtilsEX.ToInt(type) < 0) {
            item.setCode(-106);
            item.setDesc("菜单类型参数错误，type：" + type);
            return null;
        }
        if (StringUtilsEX.ToInt(status) < 0) {
            item.setCode(-107);
            item.setDesc("菜单状态参数错误，status：" + status);
            return null;
        }
        if (ID == 0) {
            menus.setCreatetime(new Date());
            menus.setIsdel(false);
        } else {
            menus = menuService.getByID(ID);

        }
        String fullpath = "";
        if (thirdID == 0) {
            if (secondID == 0) {
                if (firstID == 0) {
                    menus.setLevel(1);
                    menus.setFatherid(0);
                } else {
                    fullpath = firstID.toString();
                    menus.setFatherid(firstID);
                    menus.setLevel(2);
                }

            } else {
                fullpath = firstID.toString() + "," + secondID.toString();
                menus.setFatherid(secondID);
                menus.setLevel(3);
            }
        } else {
            fullpath = firstID.toString() + "," + secondID.toString() + "," + thirdID.toString();
            menus.setFatherid(thirdID);
            menus.setLevel(4);
        }
        if (ID > 0) {
            if (fullpath == "") {
                menus.setFullpath(menus.getId().toString());
            } else
                menus.setFullpath(fullpath + "," + menus.getId().toString());
        } else
            menus.setFullpath(fullpath);

        menus.setName(name.trim());
        menus.setMenuurl(menuurl.trim());
        menus.setHaschild(StringUtilsEX.ToBoolean(haschild));
        menus.setStatus(StringUtilsEX.ToInt(status));
        menus.setMenutype(StringUtilsEX.ToInt(menutype));
        menus.setType(StringUtilsEX.ToInt(type));

        return menus;
    }

    /**
     * 删除菜单
     * 
     * @param id
     * @return
     */
    @RequestMapping("/deleteMenu")
    public ReusltItem deleteMenu(String id) {
        ReusltItem item = new ReusltItem();
        try {
        	user=SessionState.GetCurrentUser();
            if (StringUtilsEX.ToInt(id) <= 0) {
                item.setCode(-101);
                item.setDesc("菜单ID参数错误，id：" + id);
                return item;
            }
            if (menuService.deletemenu(user.getUserId(),StringUtilsEX.ToInt(id)) > 0) {
                LogHandle.info(LogType.Menu, MessageFormat.format("删除菜单成功,ID:{0}", id),
                    "/platform/menu/deleteMenu");
                item.setCode(0);
                item.setDesc("删除菜单成功");
              //异步操作 不影响正常流程
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    			cachedThreadPool.execute(new Runnable() {
    				@Override
    				public void run() {
    					try{
    						operaterecordsService.insertOperaterecords(
                            		OperateRecordsTypeEnum.删除.getValue(), OperateRecordsFromEnum.系统后台.getValue(), 
                            		user.getUserId(), user.getLoginName(), "Control_Menulist.jsp", "/platform/menu/deleteMenu", "删除菜单");
    					}
    					catch(Exception e){
    						LogHandle.error(LogType.OperateRecords,"删除菜单操作记录出错! 异常信息:",
    								e, "/platform/menu/deleteMenu");
    					}
    					
    				}
    			});
            } else {
                LogHandle.info(LogType.Menu, MessageFormat.format("删除菜单失败,ID:{0}", id),
                    "/platform/menu/deleteMenu");
                item.setCode(-200);
                item.setDesc("删除菜单失败");
            }
        } catch (Exception e) {
        	item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("删除菜单异常：" + e.getMessage());
			} else {
				item.setDesc("系统错误！");
			}
        	LogHandle.error(LogType.Menu, "删除菜单异常：",e, "/platform/menu/deleteMenu");
        }
        return item;
    }

    /**
     * 获取菜单列表信息
     * @param name
     * @param menutype
     * @param type
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/getList")
    public ReusltItem getList(String name, String menutype, String type, String page,
                                            String size) {
        ReusltItem item = new ReusltItem();
        try {
            CriteriaMenu criteria = new CriteriaMenu();
            if (StringUtilsEX.isNotBlank(name)) {
                criteria.setName(name.trim());
            }
            if (StringUtilsEX.ToInt(menutype) >= 0) {
                criteria.setMenutype(StringUtilsEX.ToInt(menutype));
            }
            if (StringUtilsEX.ToInt(type) >= 0) {
                criteria.setType(StringUtilsEX.ToInt(type));
            }
            if (StringUtilsEX.ToInt(page) <= 0 || StringUtilsEX.ToInt(size) <= 0) {
                item.setCode(-101);
                item.setDesc("分页参数错误，pageindex：" + page + ",pagesize：" + size);
                return item;
            }

            PageBean pageBean = menuService.getMenuByPage(criteria, StringUtilsEX.ToInt(page),
                StringUtilsEX.ToInt(size));
            item.setCode(0);
            item.setDesc("查询成功");
            item.setData(pageBean.getBeanList());
            item.setMaxRow(pageBean.getTr());
            item.setPageIndex(pageBean.getPc());

        } catch (Exception e) {
        	item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("查询菜单列表异常：" + e.getMessage());
			} else {
				item.setDesc("系统错误！");
			}
            LogHandle.error(LogType.Menu, "查询菜单列表异常：",e, "/platform/menu/getList");
        }
        return item;
    }

    /**
     * 根据父ID获取子菜单列表
     * @param fid
     * @return
     */
    @RequestMapping("/getByFatherID")
    public ReusltItem getByFatherID(String fid) {
        ReusltItem item = new ReusltItem();
        try {
            if (StringUtilsEX.ToInt(fid) < 0) {
                item.setCode(-101);
                item.setDesc("父ID参数错误，fid：" + fid);
                return item;
            }
            item.setData(menuService.getListByFatherid(StringUtilsEX.ToInt(fid)));
            item.setCode(0);

        } catch (Exception e) {
        	item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("根据父ID获取子菜单列表异常：" + e.getMessage());
			} else {
				item.setDesc("系统错误！");
			}
            LogHandle.error(LogType.Menu, "根据父ID获取子菜单列表异常：",e,
                "/platform/menu/getByFatherID");
        }
        return item;
    }
}
