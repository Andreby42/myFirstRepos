package com.yinlian.api.app.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.yinlian.Enums.ActivityUsePlatformEnum;
import com.yinlian.Enums.OrderDetailStatusEnum;
import com.yinlian.Enums.OrderStatusEnum;
import com.yinlian.Enums.PayTypeEnum;
import com.yinlian.Enums.ShopCartProType;
import com.yinlian.Extended.LogType;
import com.yinlian.api.app.dto.AddNewDetailDto;
import com.yinlian.api.app.dto.AddNewOrderDto;
import com.yinlian.api.app.dto.ApplyAfterDto;
import com.yinlian.api.app.dto.GroupOrderStateDto;
import com.yinlian.api.app.dto.OrderCountDto;
import com.yinlian.api.app.dto.OrderPayDto;
import com.yinlian.app.alipay.AppAlipayNotify;
import com.yinlian.app.tenpay.ClientResponseHandler;
import com.yinlian.tenpay.MatrixToImageWriter;
import com.yinlian.tenpay.PaymentUtil;
import com.yinlian.wssc.platform.vo.BaseResult;
import com.yinlian.wssc.platform.vo.ReusltItem;
import com.yinlian.wssc.search.Api_OrderCriteria;
import com.yinlian.wssc.web.dto.SessionUser;
import com.yinlian.wssc.web.interceptor.PageBean;
import com.yinlian.wssc.web.po.Dispatching;
import com.yinlian.wssc.web.po.Idcardinfo;
import com.yinlian.wssc.web.po.Invoice;
import com.yinlian.wssc.web.po.Logistics;
import com.yinlian.wssc.web.po.LogisticsExample;
import com.yinlian.wssc.web.po.Orders;
import com.yinlian.wssc.web.service.GroupBuyOrderService;
import com.yinlian.wssc.web.service.LogisticsService;
import com.yinlian.wssc.web.service.OrderRemindService;
import com.yinlian.wssc.web.service.OrderService;
import com.yinlian.wssc.web.service.OrderStatusService;
import com.yinlian.wssc.web.service.OrderUpdaterecordsService;
import com.yinlian.wssc.web.service.OrderdetailService;
import com.yinlian.wssc.web.service.ShopService;
import com.yinlian.wssc.web.service.UserService;
import com.yinlian.wssc.web.util.DebugConfig;
import com.yinlian.wssc.web.util.GetIpAddresss;
import com.yinlian.wssc.web.util.MD5Util;
import com.yinlian.wssc.web.util.PayCallBackUtils;
import com.yinlian.wssc.web.util.PropertiesUtil;
import com.yinlian.wssc.web.util.SessionState;
import com.yinlian.wssc.web.util.StringUtils;
import com.yinlian.wssc.web.util.StringUtilsEX;
import com.yinlian.wssc.web.util.XmlUtils;
import com.yl.soft.log.LogHandle;

/**
 * 订单控制器
 * 
 * @author mjx
 *
 */
@RestController
@RequestMapping("/api/app/order")
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderStatusService orderStatusService;
	@Autowired
	private OrderdetailService orderdetailService;
	@Autowired
	private UserService userService;
	@Autowired
	private GroupBuyOrderService groupBuyOrderService;
	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private OrderUpdaterecordsService orderUpdaterecordsService;
	@Autowired
	private OrderRemindService orderremindService;
	@Autowired
	private ShopService shopService;

	/**
	 * 订单提交参数验证
	 * 
	 * @param item
	 * @param orders
	 * @param Invoices
	 * @param disp
	 * @param invoice
	 * @param orderParams
	 * @param receiveAddrID
	 * @param dispType
	 * @param dateType
	 * @param timeType
	 * @param invoiceType
	 * @param invoiceTitleType
	 * @param invoiceTitle
	 * @param invoiceContent
	 * @param mobile
	 * @param email
	 * @param zyActivityID
	 * @param zyConponID
	 * @param receiveID
	 */
	private void checkOrderParams(BaseResult item, List<AddNewOrderDto> orders,
			List<Invoice> Invoices, Dispatching disp, Invoice invoice,
			String orderParams, String receiveAddrID, String dispType,
			String dateType, String timeType, String invoiceType,
			String invoiceTitleType, String invoiceTitle,
			String invoiceContent, String mobile, String email,
			String zyActivityID, String zyConponID, Integer[] receiveID,
			String realname, String phone, String idcode, Idcardinfo idcardinfo)
			throws Exception {
		try {
			String[] ordersStr = orderParams.split(";");
			for (int i = 0; i < ordersStr.length; i++) {
				// OrderDto参数验证

				AddNewOrderDto orderDto = new AddNewOrderDto();
				String[] orderStr = ordersStr[i].split(",");
				int shopID = StringUtilsEX.ToInt(orderStr[0].split(":")[1]);
				if (shopID <= 0) {
					item.setCode(-102);
					item.setDesc("店铺id参数错误");
					return;
				}
				orderDto.setShopID(shopID);
				Double totalMoney = StringUtilsEX.ToDouble(orderStr[1]
						.split(":")[1]);
				if (totalMoney < 0) {
					item.setCode(-103);
					item.setDesc("订单总金额参数错误");
					return;
				}
				orderDto.setTotalMoney(totalMoney);
				double delMoney = StringUtilsEX
						.ToDouble(orderStr[2].split(":")[1]);
				if (delMoney < 0) {
					item.setCode(-104);
					item.setDesc("优惠金额参数错误");
					return;
				}
				orderDto.setDelMoney(delMoney);
				double freightMoney = StringUtilsEX.ToDouble(orderStr[3]
						.split(":")[1]);
				if (freightMoney < 0) {
					item.setCode(-105);
					item.setDesc("运费参数错误");
					return;
				}
				orderDto.setFreightMoney(freightMoney);
				int couponID = StringUtilsEX.ToInt(orderStr[4].split(":")[1]);
				if (couponID < 0) {
					item.setCode(-106);
					item.setDesc("优惠券id参数错误");
					return;
				}
				orderDto.setCouponID(couponID);
				int activityID = StringUtilsEX.ToInt(orderStr[5].split(":")[1]);
				if (activityID < 0) {
					item.setCode(-107);
					item.setDesc("活动id参数错误");
					return;
				}
				orderDto.setActivityID(activityID);

				int isInvoice = StringUtilsEX.ToInt(orderStr[6].split(":")[1]);
				if (isInvoice < 0 || isInvoice > 1) {
					item.setCode(-108);
					item.setDesc("isInvoice参数错误");
					return;
				}
				orderDto.setIsInvoice(isInvoice);
				String remark = orderStr[7].split(":")[1];
				orderDto.setRemark(remark);
				// #endregion

				List<AddNewDetailDto> details = new ArrayList<AddNewDetailDto>();
				// #region DetailDto参数验证

				String[] detailsStr = orderStr[8].split("\\|");
				for (int j = 0; j < detailsStr.length; j++) {
					AddNewDetailDto detailDto = new AddNewDetailDto();
					String[] detailStr = detailsStr[j].split("~");
					if (StringUtilsEX.ToInt(detailStr[6].split(":")[1]) == 4
							&& StringUtilsEX.ToInt(detailStr[1].split(":")[1]) <= 0) {
						continue;
					} else {
						int skuID = StringUtilsEX
								.ToInt(detailStr[0].split(":")[1]);
						if (skuID <= 0) {
							item.setCode(-109);
							item.setDesc("商品ID参数错误");
							return;
						}
						detailDto.setSkuID(skuID);
						int proCount = StringUtilsEX.ToInt(detailStr[1]
								.split(":")[1]);
						if (proCount <= 0) {
							item.setCode(-110);
							item.setDesc("商品数量参数错误");
							return;
						}
						detailDto.setProCount(proCount);
						int packageID = StringUtilsEX.ToInt(detailStr[2]
								.split(":")[1]);
						if (packageID < 0) {
							item.setCode(-111);
							item.setDesc("组合商品ID参数错误");
							return;

						}
						detailDto.setPackageID(packageID);
						int marketID = StringUtilsEX.ToInt(detailStr[3]
								.split(":")[1]);
						if (marketID < 0) {
							item.setCode(-112);
							item.setDesc("商品活动ID参数错误");
							return;
						}
						detailDto.setMarketID(marketID);
						if (packageID <= 0) {
							int spuid = StringUtilsEX.ToInt(detailStr[4]
									.split(":")[1]);
							// if (spuid <= 0)
							// {
							// model.Code = -112;
							// model.Desc = "标准商品ID参数错误";
							// return model.ToJson();
							// }
							detailDto.setSpuID(spuid);
						} else {
							detailDto.setSpuID(0);
						}
						detailDto
								.setType(ShopCartProType.values()[StringUtilsEX
										.ToInt(detailStr[6].split(":")[1])]);
						detailDto.setSpikeID(StringUtilsEX.ToInt(detailStr[5]
								.split(":")[1]));
						if (StringUtilsEX.IsNullOrWhiteSpace(detailStr[7]
								.split(":")[1])) {
							item.setCode(-113);
							item.setDesc("使用时间参数错误");
							return;
						}
						detailDto.setUsetime(StringUtilsEX
								.ToShortDate(detailStr[7].split(":")[1]));
//						if(StringUtilsEX.ToInt(detailStr[6].split(":")[1])
//								.equals(ShopTypeEnum.门票.getValue())){
//							if (StringUtilsEX.IsNullOrWhiteSpace(idcode)) {
//								item.setCode(-114);
//								item.setDesc("用户身份证号参数错误");
//								return;
//							}
//						}
						details.add(detailDto);
					}
				}
				if (StringUtilsEX.IsNullOrWhiteSpace(realname)) {
					item.setCode(-114);
					item.setDesc("用户姓名参数错误");
					return;
				}
				if (StringUtilsEX.IsNullOrWhiteSpace(phone)) {
					item.setCode(-115);
					item.setDesc("用户手机号参数错误");
					return;
				}
				if (StringUtilsEX.IsNullOrWhiteSpace(idcode)) {
					item.setCode(-114);
					item.setDesc("用户身份证号参数错误");
					return;
				}
				// #endregion
				orderDto.setDetails(details);
				orders.add(orderDto);
				// 发票信息
				int invType = StringUtilsEX.ToInt(invoiceType);

				if (invType > 0) {
					invoice = new Invoice();
					invoice.setType(invType);
					invoice.setTitletype(StringUtilsEX.ToInt(invoiceTitleType));
					if (StringUtils.isNotNull(invoiceTitle))
						invoice.setTitle(invoiceTitle.trim());
					if (StringUtils.isNotNull(invoiceContent))
						invoice.setContent(invoiceContent.trim());
					if (StringUtils.isNotNull(mobile))
						invoice.setMobile(mobile.trim());
					if (StringUtils.isNotNull(email))
						invoice.setEmail(email.trim());
					invoice.setStatus(0);
					invoice.setVaildflag(0);
					invoice.setCreatedate(new Date());
					Invoices.add(invoice);
				}
			}
			// #region receiveID & Dispatching & Invoice
			// receiveID[0] = StringUtilsEX.ToInt(receiveAddrID);
			// if (receiveID[0] <= 0) {
			// item.setCode(-113);
			// item.setDesc("请选择收获地址或添加收获地址");
			// return;
			// }
			idcardinfo.setName(realname);
			idcardinfo.setCreatetime(new Date());
			idcardinfo.setIsdel(0);
			idcardinfo.setPhone(phone);
			idcardinfo.setCard(idcode);
			disp.setType(StringUtilsEX.ToInt(dispType));
			disp.setDatetype(StringUtilsEX.ToInt(dateType));
			disp.setTimetype(StringUtilsEX.ToInt(timeType));
			// int invType = invoiceType);
			// if (invType > 0) {
			// invoice = new Invoice();
			// invoice.Type = invType;
			// invoice.TitleType = titleType;
			// invoice.Title = title.Trim();
			// invoice.Content = content.Trim();
			// invoice.Mobile = mobile.Trim();
			// invoice.Email = email.Trim();
			// invoice.Status = 0;
			// }
			// #endregion
		} catch (Exception ex) {
			item.setCode(-100);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("参数格式错误，" + ex.toString());
			} else {
				item.setDesc("系统错误!");
			}
			return;
		}
	}

	/**
	 * 订单提交
	 * 
	 * @param orderParams
	 *            shopID:x,totalMoney:x,delMoney:x,freightMoney:x,
	 *            couponID:x,activityid:x,isInvoice:x,remark:x,skuID:y-proCount:
	 *            y-packageid:y-marketid:
	 *            y-spikeid:y-type:y|skuID:y-proCount:y-packageid:y-marketid:y-
	 *            spuid:y-spikeid:y-type:y;
	 *            shopID:x,totalMoney:x,delMoney:x,freightMoney:x,couponID:x,
	 *            activityid:x,isInvoice:x,remark:x,skuID:y-proCount:y-packageid
	 *            :y-marketid:y|skuID:y-proCount:y-packageid:y-marketid:y-spuid:
	 *            y 参数示例说明: 以分号区分主订单，以逗号区分主订单各参数(子订单集合作为主订单参数之一)，
	 *            以竖线|区分子订单，以短杠区分子订单各参数，以冒号区分键值
	 * @param receiveAddrID
	 *            收货地址ID
	 * @param dispType
	 *            配送方式 0快递 1自提
	 * @param dateType
	 *            >配送日期类型 0任意 1工作日 2节假日
	 * @param timeType
	 *            配送时段类型 0任意 1 9:00-18:00 2 18:00-22:00
	 * @param invoiceType
	 *            发票类型 0不需要发票 1普通发票 2电子发票 3增值税发票
	 * @param invoiceTitleType
	 *            抬头类型 0个人 1公司
	 * @param invoiceTitle
	 *            发票抬头
	 * @param invoiceContent
	 *            发票内容
	 * @param mobile
	 *            手机号（预留电子发票用）
	 * @param email
	 *            邮箱（预留电子发票用）
	 * @param zyActivityID
	 *            平台全场通用活动Id
	 * @param zyConponID
	 *            平台全场通用优惠卷Id
	 * @param token
	 *            用户登陆凭证
	 * @return
	 */
	@RequestMapping(value = "/add", produces = "text/html;charset=UTF-8")
	public String Create(String orderparams, String receiveaddrid,
			String disptype, String datetype, String timetype,
			String invoicetype, String invoicetitletype, String invoicetitle,
			String invoicecontent, String mobile, String email,
			String zyactivityid, String zyconponid, String beans, String scids,
			String ch, String token, String realname, String phone,
			String idcode) {
		BaseResult item = new BaseResult();
		try {
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-101);
				item.setDesc("通道(ch)不正确！");
				return item.toJson();
			}
			SessionUser user = SessionState.GetCurrentUser(token);
			if (user == null || user.getCode() < 0) {
				item.setCode(-201);
				item.setDesc("用户未登陆");
				return item.toJson();
			}
			List<AddNewOrderDto> orders = new ArrayList<AddNewOrderDto>();
			List<Invoice> Invoices = new ArrayList<Invoice>();
			Dispatching disp = new Dispatching();
			Invoice invoice = null;
			Integer[] receiveID = new Integer[1];
			Idcardinfo idcardinfo = new Idcardinfo();
			idcardinfo.setUserid(user.getUserId());
			checkOrderParams(item, orders, Invoices, disp, invoice,
					orderparams, receiveaddrid, disptype, datetype, timetype,
					invoicetype, invoicetitletype, invoicetitle,
					invoicecontent, mobile, email, zyactivityid, zyconponid,
					receiveID, realname, phone, idcode, idcardinfo);
			if (item.getCode() < 0) {
				return item.toJson();
			}
			String[] groupCode = new String[2];
			Integer pbeans = StringUtilsEX.ToIntNull(beans);
			if (pbeans == null || pbeans < 100) {
				pbeans = 0;
			}
			if (orderService.add(orders, disp, Invoices, 0,
					StringUtilsEX.ToInt(zyconponid),
					StringUtilsEX.ToInt(zyactivityid), user.getUserId(),
					user.getLoginName(), GetIpAddresss.getIpAddr(), pbeans,
					groupCode, scids, ActivityUsePlatformEnum.app.getValue(),
					idcardinfo)) {
				item.setDesc("订单提交成功");
				//判断订单是否需要支付
				if(!StringUtilsEX.IsNullOrWhiteSpace(groupCode[1]) 
						&& new BigDecimal(groupCode[1]).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()==0){
					item.setCode(100);  //待使用
				}
				item.setData(groupCode[0]);
				LogHandle.info(LogType.Api,
						MessageFormat.format("添加新订单成功！添加时间:{0},UserID:{1}",
								new Date(), user.getId()), "order/add");
				// 添加日志
				// String desc =MessageFormat.format("添加新订单成功！订单组编号:{0}",
				// groupCode[0]);
				// OperateLogService.AddLog(OperateRecordsTypeEnum.添加,
				// OperateRecordsFromEnum.web前台, user.ID, user.UserName,
				// PublicClass.GetUserIp, "order_jiesuan.cshtml", "Order/Add",
				// desc);
			} else {
				item.setCode(-200);
				item.setDesc("订单提交失败：" + groupCode[0]);
				LogHandle.warn(LogType.Api, MessageFormat.format(
						"添加新订单失败！添加时间:{0},参数:{1},UserID:{2}", new Date(),
						orderparams, user.getId()), "order/add");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc(ex.toString());
			} else {
				item.setDesc("系统错误!");
			}
			LogHandle.error(LogType.Api, "添加新订单失败{0}", ex, "order/add");
		}
		return item.toJson();
	}

	/**
	 * 查询订单
	 * 
	 * @param status0待付款
	 *            1待发货 2待收货 99待评价9返修和退换 ，不传 查询全部订单
	 * @param page
	 * @param size
	 * @param token
	 * @param ch
	 * @return
	 */
	@RequestMapping(value = "/getorder", produces = "text/html;charset=UTF-8")
	public String getorder(String status, String page, String size,
			String token, String ch) {
		ReusltItem item = new ReusltItem();
		try {
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}

			SessionUser user = SessionState.GetCurrentUser(token);
			if (user.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("请先登陆！");
			} else {
				Integer page1 = StringUtilsEX.ToIntNull(page);
				Integer size11 = StringUtilsEX.ToIntNull(size);
				if (page1 == null || page1 <= 0) {
					page1 = 1;
				}
				if (size11 == null || size11 <= 0) {
					size11 = 10;
				}
				item.setCode(0);
				Api_OrderCriteria aoc = new Api_OrderCriteria();
				aoc.setOrderByClause("a.id");
				aoc.setSort("desc");
				aoc.setUserid(user.getUserId());
				PageBean list = new PageBean();
				switch (StringUtilsEX.ToInt(status)) {
				case 0:
					aoc.setStatus(OrderStatusEnum.待付款.getValue());
					list = orderService.getUserListOrder(page1, size11,aoc);
					break;
				case 1:
					aoc.setDetailStatus(OrderDetailStatusEnum.待使用.getValue());
					aoc.setStatus(OrderStatusEnum.出票中.getValue());
					list = orderService.getUserListOrder(page1, size11,aoc);
					break;
				case 2:
					aoc.setDetailStatus(OrderDetailStatusEnum.已使用.getValue());
					list = orderService.getUserListOrder(page1, size11,aoc);
					break;
				case 3:
					list = orderService.getSHOrderList(page1, size11,aoc);
					break;
				default:
					break;
				}
				item.setData(list.getBeanList());
				if (list.getTr() == null) {
					item.setMaxRow(0);
				} else {
					item.setMaxRow(list.getTr());
				}
				item.setPageIndex(list.getPc());
				item.setPageSize(list.getPs());
				item.setDesc("查询成功");
			}
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("查询订单异常：" + e.getMessage());
			} else {
				item.setDesc("查询订单异常");
			}

			LogHandle.error(LogType.Api, "查询订单异常! 异常信息:{0}", e,
					"order/getorder");
		}
		return item.toJson();
	}

	/**
	 * 查询当前顾客的某个状态的订单
	 * 
	 * @param buyerid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/slectByIDAndStatus", produces = "text/html;charset=UTF-8")
	public String slectByIDAndStatus(String token, String status, String ch) {
		BaseResult item = new BaseResult();

		if (StringUtilsEX.IsNullOrWhiteSpace(status)) {
			item.setCode(-102);
			item.setDesc("订单类型(status)不能为空！");
			return item.toJson();
		}
		if (!StringUtilsEX.isChannelTypeExist(ch)) {
			item.setCode(-108);
			item.setDesc("登录通道参数错误");
			return item.toJson();
		}
		SessionUser sessionUser = new SessionUser();
		sessionUser = SessionState.GetCurrentUser(token);
		if (sessionUser.getCode() != 0) {
			item.setCode(-401);
			item.setDesc("请先登陆！");
		} else {
			int buyerId = sessionUser.getUserId();
			List<Orders> list = null;
			try {
				list = orderService.queryByStatus(buyerId,
						StringUtilsEX.ToInt(status));
				if (list == null || list.size() <= 0) {
					item.setCode(-101);
					item.setDesc("没有该类型的订单");
				} else {
					item.setCode(0);
					item.setData(list);
					item.setDesc("查询成功");
				}
			} catch (Exception e) {
				item.setCode(-900);
				if (DebugConfig.BLUETOOTH_DEBUG) {
					item.setDesc("查询当前顾客的某个状态的订单异常：" + e.getMessage());
				} else {
					item.setDesc("查询订单异常");
				}

				LogHandle.error(LogType.Api, "查询当前顾客的某个状态的订单异常! 异常信息:{0}", e,
						"order/slectByIDAndStatus");
			}
		}
		return item.toJson();
	}

	/**
	 * 取消订单
	 * 
	 * @param token
	 * @param status
	 * @param ch
	 * @param reason
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/cancel", produces = "text/html;charset=UTF-8")
	public String cancel(String token, String ch, String reason,
			String groupcode) {
		BaseResult item = new BaseResult();
		try {
			if (StringUtilsEX.IsNullOrWhiteSpace(groupcode)) {
				item.setCode(-101);
				item.setDesc("组订单编号不能为空");
				return item.toJson();
			}
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}
			SessionUser sessionUser = SessionState.GetCurrentUser(token);
			if (sessionUser == null || sessionUser.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("请先登陆！");
				return item.toJson();
			} else {
				List<Orders> orders = orderService.getOrderByGroupCode(groupcode);
				if(orders == null || orders.isEmpty()){
					item.setCode(-102);
					item.setDesc("该订单不存在");
					return item.toJson();
				}
				for(Orders orders2 : orders){
//					Integer _orderid = orders2.getId();
					Integer state = orders2.getStatus();
					if(state.equals(OrderStatusEnum.待付款.getValue())==false){
						item.setCode(-103);
						item.setDesc("当前状态不允许取消");
					}
				}
				int result = orderStatusService.updateCancelOrder(groupcode, sessionUser.getUserId(), reason);
				if (result > 0) {
					item.setCode(0);
					item.setDesc("取消成功");
					
				} else {
					item.setCode(-200);
					item.setDesc("取消失败");
					LogHandle.debug(LogType.Api, MessageFormat.format(
							"取消付款订单失败! 错误信息,{groupcode}", groupcode), "order/cancel");
				}
			}
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("取消付款订单异常：" + e.getMessage());
			} else {
				item.setDesc("取消付款订单异常");
			}

			LogHandle.error(LogType.Api,"取消付款订单异常! 异常信息:{0}", e,
					"order/cancel");
		}

		return item.toJson();
	}

	/**
	 * 支付
	 * 
	 * @param ids
	 * @param status
	 * @param userid
	 * @param totalMoney
	 * @param userip
	 * @return
	 */
	@RequestMapping(value = "/pay", produces = "text/html;charset=UTF-8")
	public String pay(String groupnum, String status, String token, String ch) {
		ReusltItem item = new ReusltItem();
		try {
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}
			if (StringUtils.isBlanck(groupnum)) {
				item.setCode(-101);
				item.setDesc("订单组单号不能为空");
				return item.toJson();
			}
			if (StringUtilsEX.ToInt(status) < 0) {
				item.setCode(-102);
				item.setDesc("订单状态不能为空");
				return item.toJson();
			}
			SessionUser sessionUser = SessionState.GetCurrentUser(token);
			if (sessionUser.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("用戶未登陆！");
				return item.toJson();
			}
			String userip = GetIpAddresss.getIp();
			int userid = sessionUser.getUserId();
			if (StringUtilsEX.ToInt(status) == OrderStatusEnum.待付款.getValue()) {
				int result = orderStatusService.updatePayforBalanceCode(
						groupnum, userid, userip, item);
				if (result > 0) {
					item.setCode(0);
					item.setDesc("付款成功");
				} else {
					item.setCode(-200);
					item.setDesc("付款失败");
					LogHandle.debug(LogType.Api, MessageFormat.format(
							"付款失败! 参数信息订单组编号:{0}", groupnum), "order/pay");
				}
			} else {
				item.setCode(-103);
				item.setDesc("订单状态不是待付款，不能进行付款");
				return item.toJson();
			}

		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("支付订单异常：" + e.getMessage());
			} else {
				item.setDesc("支付订单异常");
			}

			LogHandle.error(LogType.Api,"支付订单异常! 异常信息:{0}", e,
					"order/pay");
		}

		return item.toJson();
	}

	/**
	 * 确认收货
	 * 
	 * @param orderid
	 * @param userip
	 * @param token
	 * @param ch
	 * @return
	 */
	@RequestMapping(value = "/comfirmreceive", produces = "text/html;charset=UTF-8")
	public String comfirmReceive(String orderid, String token, String ch) {
		ReusltItem item = new ReusltItem();
		try {
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}

			Integer _orderid = StringUtilsEX.ToInt(orderid);
			if (_orderid < 0) {
				item.setCode(-101);
				item.setDesc("订单id参数错误,id:" + orderid);
				return item.toJson();
			}
			SessionUser sessionUser = SessionState.GetCurrentUser(token);
			if (sessionUser.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("用戶未登陆！");
				return item.toJson();
			}
			String userip = GetIpAddresss.getIp();
			int result = orderStatusService.updatecomfirmReceive(_orderid,
					sessionUser, userip);
			if (result > 0) {
				item.setCode(0);
				item.setDesc("确认收货成功");

				// 异步操作 不影响正常流程
				ExecutorService cachedThreadPool = Executors
						.newCachedThreadPool();
				cachedThreadPool.execute(new Runnable() {
					@Override
					public void run() {
						try {
							// orderUpdaterecordsService.addOrderUpadateRecords("Status",OrderStatusEnum.待收货.getValue().toString(),OrderStatusEnum.已确认收货.getValue().toString(),
							// _orderid, sessionUser.getUserId(),
							// sessionUser.getLoginName(),userip);
						} catch (Exception e) {
							LogHandle.error(LogType.OperateRecords,
									"确认收货操作记录出错! 异常信息:", e,
									"/pc/order/comfirmreceive");
						}

					}
				});

			} else {
				item.setCode(-200);
				item.setDesc("确认收货失败");
				LogHandle.warn(LogType.Api, MessageFormat.format(
						"确认收货失败! 参数信息:{orderid}", _orderid),
						"order/comfirmreceive");
			}

		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("确认收货异常：" + e.getMessage());
			} else {
				item.setDesc("确认收货异常");
			}

			LogHandle.error(LogType.Api,"确认收货异常! 异常信息:{0}", e,
					"order/comfirmreceive");
		}

		return item.toJson();
	}

	/**
	 * 查询订单明细
	 * 
	 * @param token
	 * @param orderid
	 * @param ch
	 * @return
	 */
	@RequestMapping(value = "/showOrderDetail", produces = "text/html;charset=UTF-8")
	public String showOrderDetail(String token, String orderid, String ch) {
		BaseResult item = new BaseResult();
		try {
			if (StringUtilsEX.ToInt(orderid) <= 0) {
				item.setCode(-101);
				item.setDesc("订单id参数错误");
				return item.toJson();
			}
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}
			SessionUser user = SessionState.GetCurrentUser(token);
			if (user.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("请先登陆！");
				return item.toJson();
			}
			item.setCode(0);
			item.setData(orderService.getorderdetail(
					StringUtilsEX.ToInt(orderid), user.getUserId()));
			item.setDesc("查询订单详情成功");

		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("查询订单明细异常：" + e.getMessage());
			} else {
				item.setDesc("查询订单明细异常");
			}

			LogHandle.error(LogType.Api,"查询订单明细异常! 异常信息:{0}", e,
					"order/showOrderDetail");
		}
		return item.toJson();
	}

	/**
	 * 售后申请
	 * 
	 * @param ch
	 * @param token
	 * @param orderdetailid
	 *            订单详情id
	 * @param applytype
	 *            售后申请类型 1-订单退货退款 2-订单换货 3-订单维修 4-订单退款
	 * @param reason
	 * @param images
	 *            eg: "/dsm/imge1,/dsm/imge2"
	 * @return
	 */
	@RequestMapping(value = "/applyafter", produces = "text/html;charset=UTF-8")
	public String applyafter(String ch, String token, String orderdetailid,
			String reason) {
		BaseResult item = new BaseResult();
		try {
			if (StringUtilsEX.ToInt(orderdetailid) < 0) {
				item.setCode(-101);
				item.setDesc("订单详情id(orderdetailid)参数错误：" + orderdetailid);
				return item.toJson();
			}
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}
			if (StringUtils.isBlanck(reason)) {
				item.setCode(-102);
				item.setDesc("申请原因不能为空");
				return item.toJson();
			}
			SessionUser user = SessionState.GetCurrentUser(token);
			if (user.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("请先登陆！");
				return item.toJson();
			}

			int result = orderStatusService.updateApplyTK(
					StringUtilsEX.ToInt(orderdetailid), user.getUserId(),
					reason, item);
			if (result > 0) {
				item.setCode(0);
				item.setDesc("售后申请成功");
				LogHandle.info(LogType.Api, MessageFormat.format(
						"售后申请成功! 订单详情id{0}", orderdetailid),
						"/order/applyafter");
			} else {
				item.setCode(-200);
				if(StringUtilsEX.IsNullOrWhiteSpace(item.getDesc())){
					item.setDesc("售后申请失败");
				}
				LogHandle.info(LogType.Api, MessageFormat.format(
						"售后申请错误! 错误信息:订单详情id{0}", orderdetailid),
						"/order/applyafter");
			}

		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("申请退款异常：" + e.getMessage());
			} else {
				item.setDesc("申请退款异常");
			}

			LogHandle.error(LogType.Api,"售后申请异常! 异常信息:{0}", e,
					"order/applyafter");
		}
		return item.toJson();
	}

	/**
	 * 个人中心-订单支付
	 * 
	 * @param orderid
	 * @param token
	 * @param ch
	 * @return
	 */
	@RequestMapping(value = "/paybyorderid", produces = "text/html;charset=UTF-8")
	public String paybyOrderID(String orderid, String token, String ch) {
		ReusltItem item = new ReusltItem();
		try {
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}
			if (StringUtilsEX.ToInt(orderid) <= 0) {
				item.setCode(-101);
				item.setDesc("订单ID参数错误");
				return item.toJson();
			}
			SessionUser sessionUser = SessionState.GetCurrentUser(token);
			if (sessionUser.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("用戶未登陆！");
			} else {
				String userip = GetIpAddresss.getIp();
				int userid = sessionUser.getUserId();
				int result = orderStatusService.updatePayforBalanceID(
						StringUtilsEX.ToInt(orderid), userid, userip, item);
				if (result > 0) {
					item.setCode(0);
					item.setDesc("付款成功");
				} else {
					item.setCode(-200);
					item.setDesc("付款失败");
					LogHandle.debug(LogType.Api, MessageFormat.format(
							"付款失败! 参数信息：订单ID:{0}", orderid),
							"order/paybyOrderID");
				}
			}

		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("支付订单异常：" + e.getMessage());
			} else {
				item.setDesc("支付订单异常");
			}

			LogHandle.error(LogType.Api,"支付订单异常! 异常信息:{0}", e,
					"order/paybyOrderID");
		}

		return item.toJson();
	}

	/**
	 * 删除订单
	 * 
	 * @param token
	 * @param ch
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delorder", produces = "text/html;charset=UTF-8")
	public String delOrder(String token, String ch, String id) {
		BaseResult item = new BaseResult();
		try {
			Integer _orderid = StringUtilsEX.ToInt(id);
			if (_orderid < 0) {
				item.setCode(-101);
				item.setDesc("订单id不能为空,id" + _orderid);
				return item.toJson();
			}
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}
			SessionUser sessionUser = new SessionUser();
			sessionUser = SessionState.GetCurrentUser(token);
			if (sessionUser.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("请先登陆！");
				return item.toJson();
			} else {
				Integer userid = sessionUser.getUserId();
				int result = orderService.delOrder(_orderid, userid);
				if (result > 0) {
					item.setCode(0);
					item.setDesc("删除成功");
				} else {
					item.setCode(-200);
					item.setDesc("删除失败");
					LogHandle.warn(LogType.Api, MessageFormat.format(
									"删除订单失败! 错误信息,{id},", _orderid),
									"order/delOrder");
				}
			}
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("删除订单异常：" + e.getMessage());
			} else {
				item.setDesc("删除订单异常");
			}
			LogHandle.error(LogType.Api,"删除订单异常! 异常信息:{0}", e,
					"order/delOrder");
		}

		return item.toJson();
	}

	/**
	 * 得到订单各状态数量
	 * 
	 * @param token
	 * @param ch
	 * @return
	 */
	@RequestMapping(value = "/getorderCount", produces = "text/html;charset=UTF-8")
	public String getorderCount(String token, String ch) {
		BaseResult item = new BaseResult();
		try {
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}

			SessionUser sessionUser = SessionState.GetCurrentUser(token);
			if (sessionUser.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("用戶未登陆！");
				return item.toJson();
			}
			Integer userid = sessionUser.getUserId();
			OrderCountDto dto = orderService.selectCount(userid);
			item.setData(dto);
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("得到订单各状态数量异常：" + e.getMessage());
			} else {
				item.setDesc("得到订单各状态数量异常");
			}

			LogHandle.error(LogType.Api, "得到订单各状态数量异常! 异常信息:{0}",e, "order/getorderCount");
		}
		return item.toJson();
	}

	/**
	 * 得到团购订单各状态数量
	 * 
	 * @param token
	 * @param ch
	 * @return
	 */
	@RequestMapping(value = "/getGroupbyorderCount", produces = "text/html;charset=UTF-8")
	public String getGroupbyorderCount(String token, String ch) {
		BaseResult item = new BaseResult();
		try {
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}

			SessionUser sessionUser = SessionState.GetCurrentUser(token);
			if (sessionUser.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("用戶未登陆！");
				return item.toJson();
			}
			Integer userid = sessionUser.getUserId();
			int dfk = groupBuyOrderService.selectDfkCount(userid);
			int dxf = groupBuyOrderService.selectDxfCount(userid);
			int dpl = groupBuyOrderService.selectDplCount(userid);
			int tk = groupBuyOrderService.selectTkCount(userid);
			GroupOrderStateDto dto = new GroupOrderStateDto();
			dto.setDfk(dfk);
			dto.setDsh(dxf);
			dto.setDpj(dpl);
			dto.setTk(tk);
			item.setData(dto);
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("得到团购订单各状态数量异常：" + e.getMessage());
			} else {
				item.setDesc("得到团购订单各状态数量异常");
			}
			LogHandle.error(LogType.Api,"得到团购订单各状态数量异常! 异常信息:{0}",e, "order/getGroupbyorderCount");
		}
		return item.toJson();
	}

	/**
	 * 去支付
	 * 
	 * @param num
	 * @param token
	 * @param ch
	 * @return
	 */
	@RequestMapping(value = "/orderpay", produces = "text/html;charset=UTF-8")
	public String orderPay(String num, String token, String ch) {
		BaseResult item = new BaseResult();
		try {
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}
			if (!StringUtils.isNotNull(num)) {
				item.setCode(-101);
				item.setDesc("订单编号 参数错误");
				return item.toJson();
			}
			SessionUser sessionUser = SessionState.GetCurrentUser(token);
			if (sessionUser.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("用戶未登陆！");
				return item.toJson();
			}
			OrderPayDto dto = orderService.orderPay(num);
			dto.setUserName(sessionUser.getLoginName());
			item.setDesc("订单提交成功");
			// TODO： 原支撑系统 要修改
			/*
			 * byte[] buffer =
			 * XmlUtils.serilazeOrderPay(XmlUtils.serilazeHeaderEx("P100001",
			 * "backurl"), dto).toString() .getBytes("UTF-8");
			 * 
			 * StringBuffer rsl = new StringBuffer(); for (byte b : buffer) {
			 * rsl.append(b); rsl.append(","); } item.setData(rsl.toString());
			 */
		} catch (Exception ex) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("支付订单错误：" + ex.toString());
			} else {
				item.setDesc("支付订单错误");
			}

			LogHandle.error(LogType.Api, "支付订单异常! 异常信息:{0}", ex,
					"order/orderpay");
		}
		return item.toJson();
	}

	/**
	 * 支付宝支付异步回调地址
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/notifyurl",produces = "text/html;charset=UTF-8")
	public String notify_url(HttpServletRequest request,
			HttpServletResponse response) {
		LogHandle.info(LogType.Api, "支付宝回调:", "/notify/zfb");
		ReusltItem item = new ReusltItem();
		try {
			// 获取支付宝POST过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "gbk");
				params.put(name, valueStr);
			}

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			// 商户订单号

			String out_trade_no = new String(request.getParameter(
					"out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 支付宝交易号

			String trade_no = new String(request.getParameter("trade_no")
					.getBytes("ISO-8859-1"), "UTF-8");

			// 交易状态
			String trade_status = new String(request.getParameter(
					"trade_status").getBytes("ISO-8859-1"), "UTF-8");

			// 订单总金额
			String total_fee = new String(request.getParameter("total_fee")
					.getBytes("ISO-8859-1"), "UTF-8");
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			 if (AppAlipayNotify.verify(params)) {// 验证成功
				 LogHandle.info(LogType.Api, "支付宝回调1:"+total_fee, "/notify/zfb");
				// ////////////////////////////////////////////////////////////////////////////////////////
				// 请在这里加上商户的业务逻辑程序代码

				// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

				if (trade_status.equals("TRADE_FINISHED")
						|| trade_status.equals("TRADE_SUCCESS")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序
				String userip = GetIpAddresss.getIp();
				int commitre=PayCallBackUtils.getPayCallBackUtils().add(out_trade_no, userip,
							trade_no, total_fee, PayTypeEnum.支付宝支付.getValue(), item,orderStatusService);
					/*int commitre = orderStatusService.updatePayforAlipayCode(
							out_trade_no, userip, trade_no, total_fee,
							PayTypeEnum.支付宝支付.getValue(), item);*/
					if (commitre == 1) {
						item.setCode(0);
						item.setDesc("验证成功");
					}
					// 注意：
					// 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				}
				// ////////////////////////////////////////////////////////////////////////////////////////
			} else {// 验证失败
				item.setCode(-201);
				item.setDesc("支付宝验证失败");
			}
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("支付宝支付处理订单错误：" + e.toString());
			} else {
				item.setDesc("支付宝支付处理订单错误");
			}

			LogHandle.error(LogType.Api, "支付宝支付处理订单异常! 异常信息:{0}", e,
					"order/notifyurl");
		}
		return item.toJson();
	}

	/***
	 * 根据订单批次号（组订单编号）获取订单金额
	 * 
	 * @param groupnum
	 *            订单批次号
	 * @param token
	 * @param ch
	 * @return
	 */
	@RequestMapping(value = "/getmoney", produces = "text/html;charset=UTF-8")
	public String getMoney(String groupnum, String token, String ch) {
		BaseResult item = new BaseResult();
		try {
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}
			SessionUser sessionUser = SessionState.GetCurrentUser(token);
			if (sessionUser.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("用戶未登陆！");
				return item.toJson();
			}
			List<Orders> orderlist = orderService.getOrderByGroupCode(groupnum);
			if (orderlist == null) {
				item.setCode(-101);
				item.setDesc("组订单号错误");
				LogHandle.error(LogType.Order,
						MessageFormat.format("组订单号错误，组订单号：{0}", groupnum));
				return item.toJson();
			}
			Double ss = 0.00;
			if (orderlist != null && orderlist.size() > 0) {
				ss = orderlist.stream()
						.mapToDouble(x -> x.getActualpay().doubleValue()).sum();
			}
			item.setData(ss);
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("根据组编号获取订单金额错误：" + e.toString());
			} else {
				item.setDesc("根据组编号获取订单金额错误");
			}

			LogHandle.error(LogType.Api, "根据组编号获取订单金额异常! 异常信息:{0}", e,
					"order/getmoney");
		}
		return item.toJson();
	}

	/**
	 * 微信支付异步回调地址
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wxnotifyurl", produces = "text/html;charset=UTF-8")
	public String wechat_notifyurl(HttpServletRequest request,
			HttpServletResponse response) {
		Long beginTime  = System.currentTimeMillis();
		LogHandle.info(LogType.Api, "微信回调", "/notify/wx");
		logger.info("call wxnotifyurl");
		String ret = "";
		ReusltItem item = new ReusltItem();
		try {
			// 解析结果存储在HashMap
			Map<String, String> map = new HashMap<String, String>();
			InputStream inputStream = request.getInputStream();
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			// 遍历所有子节点
			for (Element e : elementList)
				map.put(e.getName(), e.getText());

			// 释放资源
			inputStream.close();
			inputStream = null;

			Map<String, String> retMap = new HashMap<String, String>();
			String returnCode = map.get("return_code");
			if (PaymentUtil.checkSign(map) && "SUCCESS".equals(returnCode)) {
				String resultCode = map.get("result_code");
				if ("SUCCESS".equals(resultCode)) {
					LogHandle.info(LogType.Api, "微信回调1:", "/notify/wx");
					// //下面为业务逻辑处理：如果支付成功，则修改该用户的付费状态，更新付费时间。
					String out_trade_no = map.get("out_trade_no");
					String total_fee = map.get("total_fee");
					String trade_no = map.get("transaction_id");
					String userip = GetIpAddresss.getIp();
					int commitre = PayCallBackUtils.getPayCallBackUtils().add(out_trade_no, userip,
									trade_no, String.valueOf(Float.parseFloat(total_fee)/100),
									PayTypeEnum.微信支付.getValue(), item,orderStatusService);
							//orderStatusService
							//.updatePayforAlipayCode(out_trade_no, userip,
									//trade_no, String.valueOf(Float.parseFloat(total_fee)/100),
									//PayTypeEnum.微信支付.getValue(), item);
					if (commitre == 1) {
						item.setCode(0);
						item.setDesc("验证成功");
					retMap.put("return_code", "SUCCESS");
					retMap.put("return_msg", "OK");
					ret = XmlUtils.mapToXml(retMap);
					response.getWriter().print(ret);
					} else {
						item.setCode(-200);
						item.setDesc("验证失败");
					}
					// log.info("支付成功！out_trade_no:" + outTradeNo +
					// ", result_code:" + resultCode);
				} else {
					item.setCode(-200);
					item.setDesc("支付失败");
					//String errCode = map.get("err_code");
					// log.error("支付失败！out_trade_no:" + outTradeNo +
					// ",result_code:" + resultCode + ", err_code:" + errCode);
					retMap.put("return_code", returnCode);
					retMap.put("return_msg", resultCode);
					ret = XmlUtils.mapToXml(retMap);
					response.getWriter().print(ret);
				}
			} else {
				item.setCode(-201);
				String returnMsg = map.get("return_msg");
				item.setDesc("支付通信失败！" + returnMsg);
				retMap.put("return_code", returnCode);
				retMap.put("return_msg", returnMsg);
				ret = XmlUtils.mapToXml(retMap);
				response.getWriter().print(ret);

			}
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("微信支付处理订单错误：" + e.toString());
			} else {
				item.setDesc("微信支付处理订单错误");
			}
			LogHandle.error(LogType.Api, "微信支付处理订单异常! 异常信息:{0}", e,
					"order/wxnotifyurl");
		}
		logger.info("wechat_notifyurl time {}",System.currentTimeMillis()-beginTime);
		return item.toJson();
	}

	/**
	 * 获取物流公司列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logisticslist", produces = "text/html;charset=UTF-8")
	public String pagelist(String ch, String token) {
		ReusltItem item = new ReusltItem();
		try {
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}
			SessionUser sessionUser = SessionState.GetCurrentUser(token);
			if (sessionUser.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("用戶未登陆！");
				return item.toJson();
			}
			List<Logistics> wllist = new ArrayList<Logistics>();
			LogisticsExample example = new LogisticsExample();
			wllist = logisticsService.selectByExample(example);
			item.setCode(0);
			item.setData(wllist);
		} catch (Exception ex) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("获取物流公司列表异常：" + ex.getMessage());
			} else {
				item.setDesc("系统错误！");
			}
			LogHandle.error(LogType.Api, "获取物流公司列表异常! 异常信息:", ex,
					"/order/logisticslist");
		}
		return item.toJson();
	}

	/**
	 * 核销通知(需要提供给客户)
	 * 
	 * @param orderdetailid
	 * @param reason
	 * @param item
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/orderUse", produces = "text/html;charset=UTF-8")
	public String orderUse(String order_no, String sub_order_no, String status,
			String checkNum, String sign) {
		BaseResult item = new BaseResult();
		try {
			if (StringUtilsEX.IsNullOrWhiteSpace(order_no)) {
				item.setCode(-101);
				item.setDesc("订单为空");
				return item.toJson();
			}
			if (StringUtilsEX.IsNullOrWhiteSpace(sub_order_no)) {
				item.setCode(-102);
				item.setDesc("子订单为空");
				return item.toJson();
			}
			if (StringUtilsEX.IsNullOrWhiteSpace(status)||!status.equals("check")) {
				item.setCode(-103);
				item.setDesc("状态错误");
				return item.toJson();
			}
			if (StringUtilsEX.IsNullOrWhiteSpace(sign)) {
				item.setCode(-104);
				item.setDesc("签名为空");
				return item.toJson();
			}
			LogHandle.info(LogType.Api, "核销通知:"+order_no+","+sub_order_no+","+sign, "/orderUse");
			Properties properties = PropertiesUtil.getProperties("cfg.properties");
			if(!sign.equals(MD5Util.encodeByMD5("order_no="+order_no+properties.getProperty("serviceKey")).toLowerCase())){
				item.setCode(-105);
				item.setDesc("签名错误");
				return item.toJson();
			}
			if(orderStatusService.updateDetailUse(sub_order_no, item)>0){
				return "success";
			}else{
				item.setCode(-200);
				item.setDesc("核销操作失败");
				return item.toJson();
			}
		} catch (Exception ex) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc(ex.toString());
			} else {
				item.setDesc("系统错误！");
			}
			LogHandle.error(LogType.Api, "订单核销通知操作错误 ", ex, "/order/orderUse");
		}
		return item.toJson();
	}
	
	public static void main(String[] args) {
		String string = MD5Util.encodeByMD5("order_no="+"181682159103526"+"09C115C4FA4596BEBF88A6C64C81D4A0").toLowerCase();
		System.out.println(string);
	}

	/**
	 * 退款通知(需要提供给客户)
	 * 
	 * @param orderdetailid
	 * @param userid
	 * @param reason
	 * @param item
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/orderReturn", produces = "text/html;charset=UTF-8")
	public String orderReturn(String orderCode, String subOrderCode,
			String auditStatus, String sign) {
		BaseResult item = new BaseResult();
		try {
			if (StringUtilsEX.IsNullOrWhiteSpace(orderCode)) {
				item.setCode(-101);
				item.setDesc("订单为空");
				return item.toJson();
			}
			if (StringUtilsEX.IsNullOrWhiteSpace(subOrderCode)) {
				item.setCode(-102);
				item.setDesc("子订单为空");
				return item.toJson();
			}
			if (StringUtilsEX.IsNullOrWhiteSpace(auditStatus)) {
				item.setCode(-103);
				item.setDesc("状态为空");
				return item.toJson();
			}
			if (!auditStatus.equals("failure") && !auditStatus.equals("success")) {
				item.setCode(-103);
				item.setDesc("状态错误");
				return item.toJson();
			}
			if (StringUtilsEX.IsNullOrWhiteSpace(sign)) {
				item.setCode(-104);
				item.setDesc("签名为空");
				return item.toJson();
			}
			LogHandle.info(LogType.Api, "退票通知:"+orderCode+","+subOrderCode+","+sign, "/orderReturn");
			Properties properties = PropertiesUtil.getProperties("cfg.properties");
			if(!sign.equals(MD5Util.encodeByMD5(orderCode+properties.getProperty("serviceKey")).toLowerCase())){
				item.setCode(-105);
				item.setDesc("签名错误");
				return item.toJson();
			}
			if(orderStatusService.updateDetailTK(subOrderCode,auditStatus, item)>0){
				return "success";
			}else{
				item.setCode(-200);
				item.setDesc("退票操作失败");
				return item.toJson();
			}
		} catch (Exception ex) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc(ex.toString());
			} else {
				item.setDesc("系统错误！");
			}
			LogHandle.error(LogType.Api, "订单退票通知操作错误 ", ex, "/order/orderReturn");
		}
		return item.toJson();
	}

	/**
	 * 主订单完结通知(需要提供给客户)
	 * 
	 * @param groupcode
	 * @param item
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/orderFinish", produces = "text/html;charset=UTF-8")
	public String orderFinish(String order_code, String sign) {
		BaseResult item = new BaseResult();
		try {
			if (StringUtilsEX.IsNullOrWhiteSpace(order_code)) {
				item.setCode(-101);
				item.setDesc("订单为空");
				return item.toJson();
			}
			if (StringUtilsEX.IsNullOrWhiteSpace(sign)) {
				item.setCode(-104);
				item.setDesc("签名为空");
				return item.toJson();
			}
			LogHandle.info(LogType.Api, "完结通知:"+order_code+","+sign, "/orderFinish");
			Properties properties = PropertiesUtil.getProperties("cfg.properties");
			if(!sign.equals(MD5Util.encodeByMD5("order_no="+order_code+properties.getProperty("serviceKey")).toLowerCase())){
				item.setCode(-105);
				item.setDesc("签名错误");
				return item.toJson();
			}
			if(orderStatusService.updateFinishOrder(order_code, item)>0){
				return "success";
			}else{
				item.setCode(-200);
				item.setDesc("订单完结操作失败");
				return item.toJson();
			}
		} catch (Exception ex) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc(ex.toString());
			} else {
				item.setDesc("系统错误！");
			}
			LogHandle.error(LogType.Api, "订单完结通知操作错误 ", ex, "/order/orderFinish");
		}
		return item.toJson();
	}
	
	/**
	 * 创建二维码
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/createQRCode",produces = "text/html;charset=UTF-8")
	public String createQRCode(String ch,String groupnum,HttpServletResponse response,HttpServletRequest request,
			String token) {
		ReusltItem item = new ReusltItem();
		try {
//			String ch=request.getParameter("ch");
//			String groupnum=request.getParameter("groupnum");
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}
			if (StringUtilsEX.IsNullOrWhiteSpace(groupnum)) {
				item.setCode(-101);
				item.setDesc("订单组编号参数错误");
				return item.toJson();
			}
			SessionUser sessionUser = SessionState.GetCurrentUser(token);
			if (sessionUser.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("用戶未登陆！");
				return item.toJson();
			}
			List<Orders> orderlist = orderService.getOrderByGroupCode(groupnum);
			if (orderlist == null) {
				item.setCode(-403);
				item.setDesc("组订单号错误");
				LogHandle.error(LogType.Order,
						MessageFormat.format("组订单号错误，组订单号：{0}", groupnum));
				return item.toJson();
			}
			Double ss=0.00;
			if (orderlist != null && orderlist.size() > 0) {
				 ss = BigDecimal.valueOf(orderlist.stream().mapToDouble(x -> x.getActualpay()
						 .doubleValue()).sum()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
			else{
				item.setCode(-404);
				item.setDesc("根据组订单号未能检索到数据");
				return item.toJson();
			}
			String userip = GetIpAddresss.getIp();
			String [] errordesc=new String [1];
			// 调统一下单API
			String code_url = PaymentUtil.getCodeurl(groupnum, ss.toString(), userip, "订单在线支付",errordesc);
			if(code_url==""){
				item.setCode(-405);
				item.setDesc("获取二维码错误："+errordesc[0]);
				return item.toJson();
			}
			// 将返回预支付交易链接（code_url）生成二维码图片
			int width = 200;
			int height = 200;
			String format = "png";
			Hashtable hints = new Hashtable();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(code_url,
					BarcodeFormat.QR_CODE, width, height, hints);
			OutputStream out = null;
			out = response.getOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, format, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			item.setCode(-900);
			item.setDesc("获取微信支付二维码错误：" + e.toString());
			LogHandle.error(LogType.pc, "获取微信支付二维码异常! 异常信息:{0}", e,
					"/pc/pay/createQRCode");
		}
		return item.toJson();
	}
	
	/**
	 * 获取售后详情
	 * @param ch
	 * @param token
	 * @param orderdetailid
	 * @return
	 */
	@RequestMapping(value = "/getapplydetail", produces = "text/html;charset=UTF-8")
	public String getApplyDetail(String ch, String orderdetailid) {
		BaseResult item = new BaseResult();
		try {
			if (StringUtilsEX.ToInt(orderdetailid) < 0) {
				item.setCode(-101);
				item.setDesc("订单详情id(orderdetailid)参数错误：" + orderdetailid);
				return item.toJson();
			}
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}
			ApplyAfterDto dto = orderStatusService.getApplyDetail(StringUtilsEX.ToInt(orderdetailid));
			item.setCode(0);
			item.setDesc("获取售后详情成功");
			item.setData(dto);
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("获取售后详情异常：" + e.getMessage());
			} else {
				item.setDesc("获取售后详情异常");
			}
			LogHandle.debug(LogType.Api,
					MessageFormat.format("获取售后详情异常! 异常信息:{0}", e.toString()),
					"order/getapplydetail");
		}
		return item.toJson();
	}
	
	/**
	 * 根据主订单号查询商品单号
	 * @param groupcode
	 * @return
	 */
	@RequestMapping(value = "/getprocode", produces = "text/html;charset=UTF-8")
	public String getProCode(String groupcode) {
		BaseResult item = new BaseResult();
		try {
			if (StringUtilsEX.IsNullOrWhiteSpace(groupcode)) {
				item.setCode(-101);
				item.setDesc("主订单号参数错误");
				return item.toJson();
			}
			String str = orderdetailService.getProCode(groupcode);
			item.setCode(0);
			item.setDesc("根据主订单号查询商品单号成功");
			item.setData(str);
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("根据主订单号查询商品单号异常：" + e.getMessage());
			} else {
				item.setDesc("根据主订单号查询商品单号异常");
			}
			LogHandle.debug(LogType.Api,
					MessageFormat.format("根据主订单号查询商品单号异常! 异常信息:{0}", e.toString()),
					"order/getprocode");
		}
		return item.toJson();
	}
	
	/**
	 * 删除订单
	 * 
	 * @param token
	 * @param ch
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delorderdetail", produces = "text/html;charset=UTF-8")
	public String delOrderDetail(String token, String ch, String detailid) {
		BaseResult item = new BaseResult();
		try {
			Integer _orderid = StringUtilsEX.ToInt(detailid);
			if (_orderid < 0) {
				item.setCode(-101);
				item.setDesc("字订单id不能为空," + _orderid);
				return item.toJson();
			}
			if (!StringUtilsEX.isChannelTypeExist(ch)) {
				item.setCode(-108);
				item.setDesc("登录通道参数错误");
				return item.toJson();
			}
			SessionUser sessionUser = new SessionUser();
			sessionUser = SessionState.GetCurrentUser(token);
			if (sessionUser.getCode() != 0) {
				item.setCode(-401);
				item.setDesc("请先登陆！");
				return item.toJson();
			} else {
				Integer userid = sessionUser.getUserId();
				int result = orderdetailService.delOrderDetail(_orderid, userid);
				if (result > 0) {
					item.setCode(0);
					item.setDesc("删除成功");
				} else {
					item.setCode(-200);
					item.setDesc("删除失败");
					LogHandle.warn(LogType.Api, MessageFormat.format(
									"删除订单失败! 错误信息,{id},", _orderid),
									"order/delOrderDetail");
				}
			}
		} catch (Exception e) {
			item.setCode(-900);
			if (DebugConfig.BLUETOOTH_DEBUG) {
				item.setDesc("删除订单异常：" + e.getMessage());
			} else {
				item.setDesc("删除订单异常");
			}

			LogHandle.error(LogType.Api,"删除订单异常! 异常信息:{0}", e,
					"order/delOrderDetail");
		}

		return item.toJson();
	}
}
