package com.yinlian.wssc.web.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ticket.xml.utils.XmlFuncUtils;
import ticket.xml.utils.XmlUint;

import com.techown.wssc.web.mapper.ScenicMapper;
import com.techown.wssc.web.po.Scenic;
import com.yinlian.Enums.ActivityHistory;
import com.yinlian.Enums.ActivityTypeEnum;
import com.yinlian.Enums.ActivityUsePlatformEnum;
import com.yinlian.Enums.ActivityUseTypeEnum;
import com.yinlian.Enums.CapitalChange_Type;
import com.yinlian.Enums.ChangeTableTypeEnum;
import com.yinlian.Enums.CompleteStatus;
import com.yinlian.Enums.ConfigSetTypeEnum;
import com.yinlian.Enums.CouponIssueTypeEnum;
import com.yinlian.Enums.CouponUseTypeEnum;
import com.yinlian.Enums.FinanceType;
import com.yinlian.Enums.FinanceTypeEnum;
import com.yinlian.Enums.ManZengTypeEnum;
import com.yinlian.Enums.MessagesTypeEnum;
import com.yinlian.Enums.Och_Status;
import com.yinlian.Enums.Och_Type;
import com.yinlian.Enums.OrderDetailStatusEnum;
import com.yinlian.Enums.OrderStatusEnum;
import com.yinlian.Enums.PackageTypeEnum;
import com.yinlian.Enums.PayTypeEnum;
import com.yinlian.Enums.ShopCartProType;
import com.yinlian.Enums.ShopTypeEnum;
import com.yinlian.Enums.TemplateTagEnum;
import com.yinlian.Enums.UserFinance_Type;
import com.yinlian.Extended.LogType;
import com.yinlian.api.app.dto.AddNewDetailDto;
import com.yinlian.api.app.dto.AddNewOrderDto;
import com.yinlian.api.app.dto.OrderCommentCountDto;
import com.yinlian.api.app.dto.OrderCountDto;
import com.yinlian.api.app.dto.OrderMemberDto;
import com.yinlian.api.app.dto.OrderPayDto;
import com.yinlian.api.app.dto.OrderdetailDto;
import com.yinlian.api.app.dto.OrdersDto;
import com.yinlian.api.app.dto.UpDateActDto;
import com.yinlian.app.jpush.PushService;
import com.yinlian.pc.dto.MemberOrderDto;
import com.yinlian.wssc.search.Api_OrderCriteria;
import com.yinlian.wssc.search.IdCardCriteria;
import com.yinlian.wssc.search.P_OrderListCriteria;
import com.yinlian.wssc.search.Pc_OrderCriteria;
import com.yinlian.wssc.search.Platfrom_SYCriteria;
import com.yinlian.wssc.web.dto.OrderDto;
import com.yinlian.wssc.web.dto.OrderInfo;
import com.yinlian.wssc.web.dto.SaleOrder;
import com.yinlian.wssc.web.dto.SessionUser;
import com.yinlian.wssc.web.interceptor.PageBean;
import com.yinlian.wssc.web.mapper.FinancerecordsMapper;
import com.yinlian.wssc.web.mapper.IdcardinfoMapper;
import com.yinlian.wssc.web.mapper.MessagesMapper;
import com.yinlian.wssc.web.mapper.OrderMemberMapper;
import com.yinlian.wssc.web.mapper.OrderdetailMapper;
import com.yinlian.wssc.web.mapper.OrdersMapper;
import com.yinlian.wssc.web.mapper.ShopMapper;
import com.yinlian.wssc.web.mapper.SkuMapper;
import com.yinlian.wssc.web.mapper.SkuShowtimeMapper;
import com.yinlian.wssc.web.mapper.SpikeactivityMapper;
import com.yinlian.wssc.web.mapper.SpuMapper;
import com.yinlian.wssc.web.mapper.UsercapitalMapper;
import com.yinlian.wssc.web.mapper.UserfinanceMapper;
import com.yinlian.wssc.web.po.ActivityMarket;
import com.yinlian.wssc.web.po.Applyforcancelorder;
import com.yinlian.wssc.web.po.Configdictionary;
import com.yinlian.wssc.web.po.Coupon;
import com.yinlian.wssc.web.po.Dispatching;
import com.yinlian.wssc.web.po.Financerecords;
import com.yinlian.wssc.web.po.Fullgift;
import com.yinlian.wssc.web.po.Idcardinfo;
import com.yinlian.wssc.web.po.Invoice;
import com.yinlian.wssc.web.po.Messages;
import com.yinlian.wssc.web.po.OrderBackup;
import com.yinlian.wssc.web.po.OrderactivityChildHistory;
import com.yinlian.wssc.web.po.OrderactivityHistory;
import com.yinlian.wssc.web.po.Orderdetail;
import com.yinlian.wssc.web.po.Orders;
import com.yinlian.wssc.web.po.OrdersExample;
import com.yinlian.wssc.web.po.Package;
import com.yinlian.wssc.web.po.Receiveinfo;
import com.yinlian.wssc.web.po.Sendtemplate;
import com.yinlian.wssc.web.po.Shop;
import com.yinlian.wssc.web.po.Sku;
import com.yinlian.wssc.web.po.SkuPackage;
import com.yinlian.wssc.web.po.SkuShowtime;
import com.yinlian.wssc.web.po.Spu;
import com.yinlian.wssc.web.po.Usercapital;
import com.yinlian.wssc.web.po.Userfinance;
import com.yinlian.wssc.web.po.Users;
import com.yinlian.wssc.web.po.V_Freights;
import com.yinlian.wssc.web.service.AccountsService;
import com.yinlian.wssc.web.service.ActivityService;
import com.yinlian.wssc.web.service.ApplyforcancelorderService;
import com.yinlian.wssc.web.service.AreaService;
import com.yinlian.wssc.web.service.CartService;
import com.yinlian.wssc.web.service.CityServcie;
import com.yinlian.wssc.web.service.ConfigSetService;
import com.yinlian.wssc.web.service.CouponService;
import com.yinlian.wssc.web.service.DispatchingService;
import com.yinlian.wssc.web.service.FreightService;
import com.yinlian.wssc.web.service.InvoiceService;
import com.yinlian.wssc.web.service.OrderBackupService;
import com.yinlian.wssc.web.service.OrderService;
import com.yinlian.wssc.web.service.OrderUpdaterecordsService;
import com.yinlian.wssc.web.service.OrderactivityChildHistoryService;
import com.yinlian.wssc.web.service.OrderactivityHistoryService;
import com.yinlian.wssc.web.service.PackageService;
import com.yinlian.wssc.web.service.PointsRecordService;
import com.yinlian.wssc.web.service.ProductImgsService;
import com.yinlian.wssc.web.service.ProvinceServcice;
import com.yinlian.wssc.web.service.ReceiveAddressService;
import com.yinlian.wssc.web.service.ReceiveInfoService;
import com.yinlian.wssc.web.service.ShopService;
import com.yinlian.wssc.web.service.SkuService;
import com.yinlian.wssc.web.service.SpikeActivityService;
import com.yinlian.wssc.web.service.SpuService;
import com.yinlian.wssc.web.service.UserFinanceService;
import com.yinlian.wssc.web.service.UserService;
import com.yinlian.wssc.web.service.VoucherService;
import com.yinlian.wssc.web.util.OrderUtils;
import com.yinlian.wssc.web.util.Criteria;
import com.yinlian.wssc.web.util.CriteriaDdtj;
import com.yinlian.wssc.web.util.CriteriaOrder;
import com.yinlian.wssc.web.util.DateUtil;
import com.yinlian.wssc.web.util.OrderCriteria;
import com.yinlian.wssc.web.util.PageBeanUtil;
import com.yinlian.wssc.web.util.ProductNumUtil;
import com.yinlian.wssc.web.util.StringUtilsEX;
import com.yl.soft.log.LogHandle;

public class OrderServiceBaseImpl implements OrderService {

	protected static Logger logger = LoggerFactory
			.getLogger(OrderServiceBaseImpl.class);

	@Autowired
	protected OrdersMapper ordersMapper;
	@Autowired
	protected UserService userService;
	@Autowired
	protected UserFinanceService userFinanceService;
	@Autowired
	public OrderBackupService orderBackupService;
	@Autowired
	protected OrderdetailMapper orderdetailMapper;
	@Autowired
	protected ReceiveAddressService receiveAddressService;
	@Autowired
	protected FreightService freightService;
	@Autowired
	protected OrderactivityChildHistoryService orderactivityChildHistoryService;
	@Autowired
	protected OrderactivityHistoryService orderactivityHistoryService;
	@Autowired
	protected ReceiveInfoService receiveInfoService;
	@Autowired
	protected DispatchingService dispatchingService;
	@Autowired
	protected InvoiceService invoiceService;
	@Autowired
	protected ShopService shopService;
	@Autowired
	protected SkuService skuService;
	@Autowired
	protected SpikeActivityService spikeActivityService;
	@Autowired
	protected CouponService couponService;
	@Autowired
	protected ActivityService activityService;
	@Autowired
	protected ProductImgsService productImgsService;
	@Autowired
	protected PackageService packageService;
	@Autowired
	protected OrderUpdaterecordsService orderUpdaterecordsService;
	@Autowired
	protected ApplyforcancelorderService applyforcancelorderService;
	@Autowired
	protected CartService cartService;
	@Autowired
	protected AccountsService accountsService;
	@Autowired
	protected VoucherService voucherService;

	@Autowired
	protected SpikeactivityMapper spikeactivityMapper;
	@Autowired
	protected MessagesMapper messagesMapper;

	@Autowired
	protected OrderMemberMapper orderMemberMapper;
	@Autowired
	private ConfigSetService configSetService;

	@Autowired
	private ProvinceServcice provinceServcice;
	@Autowired
	private CityServcie cityServcie;
	@Autowired
	private AreaService areaService;
	@Autowired
	private PointsRecordService pointsRecordService;
	@Autowired
	private UsercapitalMapper usercapitalMapper;
	@Autowired
	private FinancerecordsMapper financerecordsMapper;
	@Autowired
	private UserfinanceMapper userfinanceMapper;

	@Autowired
	private SpuService spuService;
	@Autowired
	private IdcardinfoMapper idcardinfoMapper;
	@Autowired
	private ScenicMapper scenicMapper;
	@Autowired
	private SpuMapper spuMapper;
	@Autowired
	private ShopMapper shopMapper;
	@Autowired
	private SkuShowtimeMapper skuShowtimeMapper;
	@Autowired
	private SkuMapper    skuMapper;
	
	
	@Override
	public boolean add(List<AddNewOrderDto> orders, Dispatching disp,
			List<Invoice> invoices, int receiveAddrID, int zyConponID,
			int zyActivityID, int userId, String username, String Ip,
			Integer beans, String[] desc, String scids, Integer sites,
			Idcardinfo idcardinfo) throws Exception {
		List<Orders> newOrders = new ArrayList<Orders>();

		String gc = ProductNumUtil.GetOrderNum();
		desc[0] = gc;
		double[] order_TotalMoney = new double[1];
		order_TotalMoney[0] = 0d;
		Users users = null;
		users = userService.selectByPrimaryKey(userId);

		double order_TotalPrice = 0, order_FreightMoney = 0, order_FreightPrice = 0;
		Integer[] procount = new Integer[1];

		List<Orderdetail> newDetails = new ArrayList<Orderdetail>();
		List<OrderactivityHistory> records = new ArrayList<OrderactivityHistory>();
		List<OrderactivityChildHistory> ocrecords = new ArrayList<OrderactivityChildHistory>();
		List<UpDateActDto> ugiftdtos = new ArrayList<UpDateActDto>();// 更新满减满赠商品库存IdS
		List<UpDateActDto> uPackagedtos = new ArrayList<UpDateActDto>();// 更新组合商品库存IdS
		List<Integer> ConponIDs = new ArrayList<Integer>();// 更新优惠卷状态IdS
		List<UpDateActDto> actIds = new ArrayList<UpDateActDto>();// 更新活动库存IdS
		List<UpDateActDto> spiIds = new ArrayList<UpDateActDto>(); // 更新闪购库存IdS
		double faceValue = 0;
		double yfaceValue = 0;
		double orderTotalMoney = 0;

		double ratio = 0.01; // 积分兑换比例
		double totalMoney = 0;
		double orderTotalpay = 0;
		for (AddNewOrderDto orderDto : orders) {
			orderTotalMoney += orderDto.totalMoney;
			if (orderDto.totalMoney + orderDto.freightMoney >= orderDto.delMoney) {
				totalMoney += orderDto.totalMoney;
				totalMoney += orderDto.freightMoney;
				totalMoney -= orderDto.delMoney;
			}
		}
		if (zyConponID > 0) {
			Coupon Coupondto = couponService.getByIDandUsesite(zyConponID,
					sites);
			if(Coupondto==null){
				LogHandle.warn(LogType.Order, MessageFormat.format(
						"订单提交失败，未查询到优惠券信息，优惠券ID:{0}", zyConponID),
						"OrderServiceBaseImpl/add");

				desc[0] = "未查询到优惠券信息";
				return false;
			}
			if (Coupondto.getCouponissuetype() == CouponIssueTypeEnum.平台
					.getValue()
					&& (Coupondto.getCouponusetype() == CouponUseTypeEnum.通用
							.getValue())) {
				faceValue = Coupondto.getFacevalue();
				if(faceValue>orderTotalMoney){
					faceValue=orderTotalMoney;
				}
			}
			ConponIDs.add(zyConponID);

		}

		if (beans > 0) {
			Configdictionary configdictionary = configSetService
					.showConfigSetByType(ConfigSetTypeEnum.积分兑换人民币.getValue());
			if (configdictionary != null) {
				if (StringUtilsEX.ToDouble(configdictionary.getValue()) > 0) {
					ratio = new BigDecimal(configdictionary.getValue())
							.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}
				if (ratio == 0) {
					ratio = 0.01;
				}
			}
			BigDecimal tm = new BigDecimal(totalMoney);
			if (beans * ratio > tm.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue() / 2) {
				double ap = (tm.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue() / 2);
				Double discount = (ap / ratio);
				beans = discount.intValue();
			}
		}
		int skucount=0;//门票商品数量
		for (int i = 0; i < orders.size(); i++) {
			AddNewOrderDto orderDto = orders.get(i);
			Shop shop = shopService.queryById(orderDto.getShopID());
			if (shop == null) {
				LogHandle.warn(LogType.Order, MessageFormat.format(
						"订单提交失败，未查询到店铺数据，店铺ID:{0}", orderDto.getShopID()),
						"OrderServiceBaseImpl/add");

				desc[0] = "未查询到此店铺信息";
				return false;
			}
			if(shop.getShoptype().equals(ShopTypeEnum.门票.getValue())){
				skucount=orderDto.getDetails().stream().mapToInt(x->x.getProCount()).sum();
				if(skucount>5){
					LogHandle.warn(LogType.Order, MessageFormat.format(
							"门票数量不能超过5张，数量:{0}", skucount),
							"OrderServiceBaseImpl/add");

					desc[0] = "门票数量不能超过5张";
					return false;
				}
				IdCardCriteria criteria = new IdCardCriteria();
				criteria.setUserid(userId);
				criteria.setIdcard(idcardinfo.getCard());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				// 获取当前月第一天：
				Calendar c = Calendar.getInstance();
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
				criteria.setBegintime(format.parse(format.format(c.getTime())));
				// 获取当前月最后一天
				Calendar ca = Calendar.getInstance();
				ca.set(Calendar.DAY_OF_MONTH,
						ca.getActualMaximum(Calendar.DAY_OF_MONTH));
				criteria.setEndtime(format.parse(format.format(ca.getTime())));
				Integer count = idcardinfoMapper.checkcardinfo(criteria);
				if (count == null) {
					count = 0;
				}
				if((count+skucount)>5){
					LogHandle.warn(LogType.Order, MessageFormat.format(
							"门票数量不能超过5张，购买数量:{0},已购买数量:{1}", skucount,count),
							"OrderServiceBaseImpl/add");

					desc[0] = "门票数量不能超过5张";
					return false;
				}
			}
			Orders order = new Orders();
			order.setWebsites(sites); // 下单对应站点
			order.setCode(ProductNumUtil.GetOrderNum());
			order.setGroupcode(gc);
			order.setStatus(OrderStatusEnum.待付款.getValue());
			order.setBuyerid(userId);
			order.setSellerid(shop.getUserid());
			order.setShopid(shop.getId());
			order.setPrice(BigDecimal.valueOf(orderDto.getTotalMoney()));
			order.setFreight(BigDecimal.valueOf(orderDto.getFreightMoney()));
			order.setDiscount(0f);
			order.setReqcount(0);
			order.setActualpay(BigDecimal.valueOf(orderDto.getTotalMoney()
					+ orderDto.getFreightMoney() - orderDto.getDelMoney()));
			// #region 优惠券使用
			double[] cfacevalue = new double[1];
			cfacevalue[0] = 0;
			if (faceValue > 0) {
				if (i != orders.size() - 1) {
					BigDecimal a = new BigDecimal(orderDto.getTotalMoney());
					BigDecimal b = new BigDecimal(orderTotalMoney);
					cfacevalue[0] = a.multiply(new BigDecimal(faceValue))
							.divide(b, 2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				} else {
					cfacevalue[0] = faceValue - yfaceValue;
				}
				yfaceValue += cfacevalue[0];
			}

			if (!CheckPreferential(orderDto, order, records, cfacevalue,
					zyConponID, ConponIDs, userId, desc)) {
				return false;
			}
			// #endregion
			// 积分兑换金额
			double beansmoney = 0.0;
			if (beans != null && beans > 0) {
				double apay = orderDto.getTotalMoney()
						+ orderDto.getFreightMoney() - orderDto.getDelMoney();
				if (apay > 2) {
					// BigDecimal a = new BigDecimal(apay);
					// BigDecimal b = new BigDecimal(totalMoney);
					double f1 = apay / 2;
					double f = apay / totalMoney;
					beansmoney = beans * f * ratio;
					BigDecimal bm = new BigDecimal(beansmoney);
					if (f1 > bm.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue()) {
						order.setBeans(Double.valueOf(beans * f).intValue());
					} else {
						order.setBeans(Double.valueOf(f1 / ratio).intValue());
					}
					order.setBeansratio(ratio);
					// Long ap =Math.round(apay / 2);
					// beansmoney=beans*(apay/totalMoney);
					// if (ap > beansmoney) {
					// order.setBeans(beansmoney/ratio);
					// }else
					// {
					// beans = ap.intValue();
					// order.setBeans(ap.intValue()/ratio);
					// }
				} else {
					beans = 0;
					order.setBeans(0);
				}
			} else {
				beans = 0;
				order.setBeans(0);
			}

			// #region 满减满赠店铺活动验证
			if (!CheckShopFullGift(orderDto, order, records, ocrecords,
					ugiftdtos, userId, actIds, desc, sites)) {
				CancelGoupon(ConponIDs, userId);
				return false;
			}

			// #endregion
			order.setActivityid(orderDto.getActivityID());
			order.setIsinvoice(orderDto.getIsInvoice());
			order.setRemark(orderDto.getRemark());
			order.setValidflag(0);
			order.setUserisdel(0);
			order.setAddorderdate(new Date());
			order.setIsowned(shop.getIsowned());

			order_TotalPrice += order.getPrice().doubleValue();
			order_FreightPrice += order.getFreight().doubleValue();
			procount[0] = 0;
			
			
			

			if (!CheckOrderDetail(orderDto, order, records, ocrecords,
					ugiftdtos, uPackagedtos, newDetails, userId, actIds,
					spiIds, procount, order_TotalMoney, sites, desc)) {
				CancelGoupon(ConponIDs, userId);
				return false;
			}
			// int postcount = newDetails.stream().filter(x -> x.getIsPost() ==
			// true).collect(Collectors.toList()).size();
			// if (postcount != newDetails.size()) {
			// // #region 验证订单金额和运费
			// // 获取订单下 总运费
			// String addrProvince = "";
			// if (addr != null && addr.getProvincename() != null) {
			// addrProvince = addr.getProvincename().replace("省",
			// "").replace("市", "");
			// }
			// v_fre = freightService.getByShopID(shop.getId(), addrProvince);
			// if (v_fre != null) {
			// order_FreightMoney += freightMoney(v_fre, procount[0],
			// order.getPrice(),postcount);
			// order_FreightMoney=order_FreightMoney>=0?order_FreightMoney:0;
			// } else {
			// LogHandle.warn(LogType.Order,
			// MessageFormat.format("运费模板不存在 店铺ID:{0},省名称:{1}", shop.getId(),
			// addr.getProvincename()),
			// "OrderServiceBaseImpl/add");
			//
			// // desc[0] = "运费模板不存在!";
			// // CancelGoupon(ConponIDs, userId);
			// // return false;
			//
			// }
			// }
			if (BigDecimal.valueOf(order_TotalPrice)
					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != BigDecimal
					.valueOf(order_TotalMoney[0])
					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) {
				LogHandle.warn(LogType.Order, MessageFormat.format(
						"订单金额与实际商品金额不符！订单金额:{0},实际商品金额:{1}", order.getPrice(),
						order_TotalMoney[0]), "OrderServiceBaseImpl/add");

				desc[0] = "订单金额错误";
				CancelGoupon(ConponIDs, userId);
				return false;
			}
//			double disc = BigDecimal.valueOf(order.getDiscount())
//					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//			if (orderDto.getDelMoney() != disc) {
//				LogHandle.warn(LogType.Api, MessageFormat.format(
//						"订单优惠金额不符！订单优惠金额:{0},实际活动优惠总金额:{1}",
//						orderDto.getDelMoney(), order.getDiscount()),
//						"OrderServiceBaseImpl/add");
//
//				CancelGoupon(ConponIDs, userId);
//				desc[0] = "订单优惠金额错误";
//				return false;
//			}
//			// DecimalFormat df = new DecimalFormat("#.00");
//			BigDecimal bg = new BigDecimal(order_FreightMoney);
//			if (order_FreightPrice != bg.setScale(2, BigDecimal.ROUND_HALF_UP)
//					.doubleValue()) {
//				LogHandle.warn(LogType.Api, MessageFormat.format(
//						"订单运费与实际商品运费不符！订单运费:{0},实际商品运费:{1}",
//						order.getFreight(), order_FreightMoney),
//						"OrderServiceBaseImpl/add");
//
//				desc[0] = "订单运费错误";
//				CancelGoupon(ConponIDs, userId);
//				return false;
//			}

			double actualpay = order.getPrice().floatValue()
					+ order.getFreight().floatValue()
					- order.getDiscount().floatValue()
					- Double.valueOf(order.getBeans() * ratio);
			// #endregion
			order.setActualpay(BigDecimal.valueOf(actualpay).setScale(2,
					BigDecimal.ROUND_HALF_UP));
			// 判断支付金额是否小于0
			if (order.getActualpay().compareTo(BigDecimal.valueOf(0.0)) == -1) {
				order.setActualpay(BigDecimal.valueOf(0.0));
				order.setDiscount(order.getPrice().floatValue()
						+ order.getFreight().floatValue());
			}
			newOrders.add(order);
			orderTotalpay+=order.getActualpay().doubleValue();
		}

		// #region ReceiveInfo
		Receiveinfo info = new Receiveinfo();
		// info.setOrdergroupcode(gc);
		// info.setProvince(addr.getProvincecode());
		// info.setCity(addr.getCitycode());
		// info.setArea(addr.getAreacode());
		// info.setConsignee(addr.getName());
		// info.setAddress(addr.getAddress());
		// info.setTelphone(addr.getMobile());
		// #endregion

		// #region Dispatching & Invoice
		disp.setOrdergroupcode(gc);
		disp.setValidflag(0);
		// if (invoice != null)
		// invoice.UserID = user.ID;
		if (invoices != null) {
			for (Invoice inv : invoices) {
				inv.setUserid(userId);
			}
		}
		// #endregion
		if (zyConponID > 0) {
			faceValue = couponService.UseCoupon(zyConponID, userId,
					(float) orderTotalMoney, "提交订单使用优惠券");
			if (faceValue <= 0) {
				desc[0] = "优惠券使用失败";
				return false;
			}
		}

		if (CommitNewOrder(newOrders, newDetails, info, disp, invoices,
				records, ocrecords, ugiftdtos, actIds, uPackagedtos, spiIds,
				users, beans, ratio, Ip, gc, desc, idcardinfo)) {
			BigDecimal totalpay = new BigDecimal(0);
			StringBuffer stringBuffer3 = new StringBuffer();
			for (Orders o : newOrders) {
				int oid=0;
				if (o.getActualpay().compareTo(BigDecimal.valueOf(0.0)) == 0) {
					if(orderTotalpay == 0) {
//					        ordersMapper.updateStatus(OrderStatusEnum.出票中.getValue(),
//							   o.getId());
					        //票务系统
							List<Orderdetail> details = orderdetailMapper.getDetailsByOrderID(o.getId());
							List<Integer> ids = new ArrayList<Integer>();
							Hashtable<Integer, Integer> Orderdetaildic = new Hashtable<Integer, Integer>();
							oid=details.get(0).getOrderid();
							for (Orderdetail x : details) {
								ids.add(x.getSkuId());
								Orderdetaildic.put(x.getSkuId(), x.getProductcount());
								Sku sku = skuMapper.selectByPrimaryKey(x.getSkuId());
								// 对接票务
								stringBuffer3.append("<ticketOrder>");
								stringBuffer3.append("<orderCode>"+x.getProcode()+"</orderCode>");// B
								stringBuffer3.append("<price>"+x.getProductprice()+"</price>");
								stringBuffer3.append("<quantity>"+x.getProductcount()+"</quantity>");
								stringBuffer3.append("<totalPrice>"+x.getProductprice().multiply(new BigDecimal(x.getProductcount()))+"</totalPrice>");
								stringBuffer3.append("<occDate>"+DateUtil.datePattren(x.getUsetime())+"</occDate>");
								stringBuffer3.append("<goodsCode>"+sku.getTicketnum()+"</goodsCode>");//PST20160918013085 "+sku.getTicketnum()+"
								stringBuffer3.append("<goodsName>"+x.getProductname()+"</goodsName>");
								stringBuffer3.append("<remark>测试环境用上述参数</remark>");//测试环境用上述参数  "+sku.getRemark()+"
								stringBuffer3.append("</ticketOrder>");
							}
					}
					// 添加用户财务记录
					// 以下是卖家操作
					int sellerid = o.getSellerid();// 卖家id
					Usercapital sellerUsercapital = usercapitalMapper
							.getBalanceRowLockById(sellerid);
					if (sellerUsercapital == null) {
						LogHandle.error(LogType.Order, MessageFormat.format(
								"订单付款未查询到卖家资金数据，错误详情：{0}", "未查询到卖家用户资金数据"));
					}
					sellerUsercapital.setFreezemoney(sellerUsercapital
							.getFreezemoney() + totalMoney);
					usercapitalMapper.updateByPrimaryKeySelective(sellerUsercapital); // 增加卖家的冻结资金
					// 资金变动记录
					financeAdd(sellerid, sellerUsercapital.getBalance(),
							totalMoney, "订单支付卖家冻结金额增加", "", PayTypeEnum.优惠券支付.getValue(),
							"",CapitalChange_Type.冻结金额增加.getValue(), o.getCode(),
							UserFinance_Type.已支付.getValue());

					// 用户资金更改记录表
					recordsAdd(sellerid, "", ChangeTableTypeEnum.修改.getValue(),
							sellerUsercapital.getId());
					Orders orders2=new Orders();
					orders2.setPaytype(PayTypeEnum.优惠券支付.getValue());
					orders2.setId(oid);
					orders2.setPaydate(new Date());
					ordersMapper.updateByPrimaryKeySelective(orders2);
				}
				totalpay.add(o.getActualpay());
			}
			if(orderTotalpay == 0) {
				// 对接票务
				Idcardinfo idcardinfo1 = idcardinfoMapper.getByGroupCode(gc);
				String req=XmlFuncUtils.sendCodeReq(gc, totalpay.toString(), idcardinfo1, stringBuffer3.toString());
				String retmsg = "";
				String[] temp = XmlUint.sendPost("SEND_CODE_REQ", req);
				Map<String, Object> map = new HashMap<>();
				if (temp[1].equals("1")) {
					map.put("code", "-1");
				} else {
					retmsg = temp[0];
					map = XmlUint.parserValue(retmsg);
				}

				LogHandle.info(LogType.Order, "出票中票务对接下单返回：" + retmsg + ",gnum:" + gc+ ",req:" + req,
						"OrderServiceBaseImpl/add");
				// XmlUint.sendPost("SEND_CODE_REQ",stringBuffer.toString());
				// 如果成功code=0 订单状态 主订单 :代付款-待使用 子订单:待使用
				// 如果不成功code!=0 订单状态 主订单 :代付款-审核中
				// Map<String ,Object> map = XmlUint.parserValue(retmsg);
				// 订单二维码地址
				String codeimg = "";
				if ("0".equals(map.get("code"))) {
					// 下单成功发送信息
					XmlUint.sendPost("SEND_SM_REQ", XmlFuncUtils.sendSmReq(gc));
					// 获取二维码
					// String retImg ="";
					temp = XmlUint.sendPost("QUERY_IMG_URL_REQ", XmlFuncUtils.queryImgUrlReq(gc));
					map = new HashMap<>();
					if (temp[1].equals("1")) {
						map.put("code", "-1");
					} else {
						retmsg = temp[0];
						map = XmlUint.parserValue(retmsg);
					}
					Map<String, Object> mapImg = map;
					if ("0".equals(mapImg.get("code"))) {
						codeimg = mapImg.get("img").toString();
					}
					for (Orders orders1 : newOrders) {
						orders1.setStatus(OrderStatusEnum.待使用.getValue());
						orders1.setQrcode(codeimg);
						ordersMapper.updateByPrimaryKey(orders1);
						List<Orderdetail> details1 = orderdetailMapper.getDetailsByOrderID(orders1.getId());
						for (Orderdetail y : details1) {
							orderdetailMapper.updateStatus(OrderDetailStatusEnum.待使用.getValue(), y.getId());
							// 分商品1种商品一条
//							financeAdd(y.getBuyerid(), StringUtilsEX.ToDouble(y.getProductprice().toString()),
//									StringUtilsEX.ToDouble(y.getProductprice().toString()), "订单在线支付商品", paynum, userip,
//									CapitalChange_Type.收入.getValue(), y.getProcode(), UserFinance_Type.已支付.getValue());
						}
					}
				}else{
					updateStatus(newOrders, OrderStatusEnum.审核中.getValue());
				}
			}
			int points = newOrders.stream().mapToInt(x -> x.getBeans()).sum();
			// 支付金额
			desc[1] = String.valueOf(orderTotalpay);

			OrderUtils.getBackOrder().set(cartService);
			OrderUtils.getBackOrder().set(pointsRecordService);
			OrderUtils.getBackOrder().set(orderBackupService);
			OrderUtils.getBackOrder().add(newOrders, scids, points, userId);
			
			desc[0] = gc;
			return true;
		} else {
			LogHandle.warn(LogType.Order, "订单提交失败！", "OrderServiceBaseImpl/add");
			desc[0] = "订单提交失败";
			CancelGoupon(ConponIDs, userId);
			return false;
		}
	}
	public int updateStatus(List<Orders> orderlist,Integer status) throws Exception {
		for (Orders orders : orderlist) {
			List<Orderdetail> details1 = orderdetailMapper.getDetailsByOrderID(orders.getId());
			for (Orderdetail y : details1) {
				orderdetailMapper.updateStatus(status, y.getId());
			}
		}
		return ordersMapper.updateStatusByGroupcode(status, orderlist.get(0).getGroupcode());
	}
	/**
	 * 计算包邮
	 * 
	 * @param v_fre
	 * @param price
	 * @return
	 */
	/*
	 * private double freightMoney1(V_Freights v_fre, BigDecimal price) { if
	 * (v_fre.getNum() <= price.doubleValue()) { return 0; } return
	 * v_fre.getFirstPrice(); }
	 */

	@Override
	public void addOrderBackup(List<Orders> orders) {
		try {
			List<OrderBackup> ordersbc = new ArrayList<OrderBackup>();
			for (Orders order : orders) {
				// #region Orderbackup
				OrderBackup orderbc = new OrderBackup();
				orderbc.setCode(order.getCode());
				orderbc.setGroupcode(order.getGroupcode());
				orderbc.setStatus(order.getStatus());
				orderbc.setBuyerid(order.getBuyerid());
				orderbc.setSellerid(order.getSellerid());
				if (order.getPaytype() == null)
					orderbc.setPaytype(1);
				else
					orderbc.setPaytype(order.getPaytype());
				orderbc.setPrice(order.getPrice());
				orderbc.setFreight(order.getFreight());
				orderbc.setCouponid(order.getCouponid());
				orderbc.setLogisticscode(order.getLogisticscode());
				orderbc.setLogisticsname(order.getLogisticsname());
				orderbc.setAddorderdate(order.getAddorderdate());
				orderbc.setPaydate(order.getPaydate());
				orderbc.setAdddate(new Date());
				ordersbc.add(orderbc);
				// #endregion
			}
			orderBackupService.add(ordersbc);
		} catch (Exception ex) {
			LogHandle.error(LogType.Api, "订单信息备份失败，错误详情：{0}", ex,
					"OrderServiceBaseImpl/addOrderBackup");
		}
	}

	boolean CommitNewOrder(List<Orders> orders, List<Orderdetail> details,
			Receiveinfo receive, Dispatching disp, List<Invoice> invoices,
			List<OrderactivityHistory> records,
			List<OrderactivityChildHistory> ocrecords,
			List<UpDateActDto> ugiftdtos, List<UpDateActDto> actIds,
			List<UpDateActDto> uPackagedtos, List<UpDateActDto> spiIds,
			Users users, Integer beans, double beansratio, String Ip,
			String gc, String[] desc, Idcardinfo idcardinfo) throws Exception {
		List<Integer> ids = null;
		int mtotal = 0;
		int ucount = 0;
		beans = orders.stream().mapToInt(x -> x.getBeans()).sum();
		desc[0] = "";
		if (ugiftdtos.size() > 0) {
			mtotal = 0;
			ucount = 0;
			ids = new ArrayList<Integer>();
			for (UpDateActDto uad : ugiftdtos) {
				ids.add(uad.getId());
				ucount += uad.getCount();
			}
			List<Fullgift> fullgifts = activityService.getByIds(ids);

			if (fullgifts == null) {
				desc[0] = "活动不存在";
				throw new Exception("活动不存在！");
			}

			for (Fullgift fullgift : fullgifts) {
				mtotal += fullgift.getGiftcount();
			}
			if (mtotal < ucount) {
				desc[0] = "活动库存不足！";
				throw new Exception("活动库存不足！");
			}
			activityService.updateFullGiftCountByIds(ugiftdtos);
		}
		if (actIds.size() > 0) {
			activityService.updateStockByIds(actIds);
		}
		if (spiIds.size() > 0) {
			spikeActivityService.updateSpikeSpuCountByIds(spiIds);
		}
		if (uPackagedtos.size() > 0) {
			mtotal = 0;
			ucount = 0;
			ids = new ArrayList<Integer>();
			for (UpDateActDto uad : uPackagedtos) {
				ids.add(uad.getId());
				ucount += uad.getCount();
			}
			List<Package> packages = packageService.getByIDs(ids);
			if (packages == null) {
				desc[0] = "套餐不存在！";
				throw new Exception("套餐不存在！");
			}
			for (Package pck : packages) {
				mtotal += pck.getCount();
			}
			if (mtotal < ucount) {
				desc[0] = "套餐库存不足！";
				throw new Exception("套餐库存不足！");
			}
			packageService.updateCountByids(uPackagedtos);
		}
		for (Orders order : orders) {
			order.setUserisdel(0);
			ordersMapper.insert(order);
			// order.setId(order.getId());
			Shop shop = shopService.queryById(order.getShopid());
			for (Orderdetail w : details) {
				if (w.getOrdercode() == order.getCode()) {
					w.setOrderid(order.getId());
					if (shop != null) {
						if (shop.getShoptype() == ShopTypeEnum.门票.getValue()) {
							if (idcardinfo != null) {
								w.setIdcard(idcardinfo.getCard());
							}
						}
					}
					orderdetailMapper.insert(w);
					// 更改商品库存
					Integer year=DateUtil.getYear(w.getUsetime());
					Integer month=DateUtil.getMonth(w.getUsetime());
					Integer day=DateUtil.getDay(w.getUsetime());
					SkuShowtime skutime=skuShowtimeMapper.getSkuTimeBySkuid(w.getSkuId(), year, month, day);
					if(skutime!=null){
						skutime.setStock(skutime.getStock()-w.getProductcount());
						skuShowtimeMapper.updateByPrimaryKey(skutime);
					}
					// skuService.updateStockById(w.getProductcount(),
					// w.getSkuId());
				}
			}
			if (idcardinfo != null) {
				idcardinfo.setGroupcode(order.getGroupcode());
				idcardinfoMapper.insert(idcardinfo);
			}
		}
		if (ocrecords.size() > 0) {
			orderactivityChildHistoryService.inserts(ocrecords);
		}
		if (users != null && beans > 0) {
			/*
			 * users.setPoints(users.getPoints() - beans);
			 * userService.updateInfo(users);
			 */
			// 添加积分消费财务记录
			Userfinance userfinance = new Userfinance();
			userfinance.setBalance(Double.valueOf(users.getPoints()
					* beansratio));
			userfinance.setCreatetime(new Date());
			userfinance.setDescription("创建订单积分支付");
			userfinance.setFinancetype(FinanceType.积分.getValue());
			userfinance.setType(FinanceTypeEnum.支出.getValue());
			userfinance.setMoney(Double.valueOf(beans));
			userfinance.setNumber(gc);
			userfinance.setStatus(0);
			userfinance.setUserid(users.getId());
			userfinance.setUserip(Ip);
			userFinanceService.insert(userfinance);
		}
		// receiveInfoService.insert(receive);
		dispatchingService.insert(disp);
		if (records.size() > 0) {
			orderactivityHistoryService.inserts(records);
		}
		if (invoices != null) {
			for (int i = 0; i < invoices.size(); i++) {
				invoices.get(i).setOrderid(orders.get(i).getId());
				if (invoices.get(0).getTitletype() == 0)
					invoices.get(0).setTitle("个人");
				invoiceService.insert(invoices.get(i));
			}
		}
		return true;
	}

	/**
	 * 计算运费
	 * 
	 * @param fre
	 *            运费信息
	 * @param ProductCount
	 *            商品数量
	 * @param price
	 *            商品金额
	 * @return
	 */
	@Override
	public double freightMoney(V_Freights fre, int ProductCount,
			BigDecimal price, int postcount) throws Exception {
		if (fre != null && fre.getFreightID() > 0) {
			// 包邮
			Double firstPrice = fre.getFirstPrice() < 0 ? 0 : fre
					.getFirstPrice();
			if (fre.getIsexemptionpostage() == 1) {// 是否包邮
				if (fre.getPricingmode() == 2) {// 2按金额
					if (fre.getIscondition() == 0) {// 包邮规则 0-大于等于 1-小于等于
						if (price.intValue() >= fre.getNum()) {
							return 0;
						} else {
							return firstPrice;// fre.getFirstPrice();
						}
					} else {
						if (fre.getNum() >= price.intValue()) {
							return 0;
						} else {
							return firstPrice;// fre.getFirstPrice();
						}
					}

				} else {
					if (fre.getIscondition() == 0) {// 包邮规则 0-大于等于 1-小于等于
						if (ProductCount >= fre.getNum()) {
							return 0;
						} else {
							return firstPrice;// fre.getFirstPrice();
						}
					} else {
						if (fre.getNum() >= ProductCount) {
							return 0;
						} else {
							return firstPrice;// fre.getFirstPrice();
						}
					}
				}
			}
			// 不包邮
			else {
				if (fre.getFirstCount() == null) {
					return 0;
					// throw new Exception("运费模板错误！");
				}
				if (fre.getFirstCount() >= ProductCount) {
					return firstPrice;// fre.getFirstPrice();
				}
				if (fre.getElseCount() > 0) {
					int divd = (ProductCount - fre.getFirstCount() - postcount)
							% fre.getElseCount();
					if (divd > 0) {
						divd = 1;
					}
					return // fre.getFirstPrice()
					firstPrice
							+ ((ProductCount - fre.getFirstCount())
									/ fre.getElseCount() + divd)
							* fre.getElsePrice();
				}
				return firstPrice;
			}
		} else {
			return 0;
		}
	}

	boolean CheckOrderDetail(AddNewOrderDto orderDto, Orders order,
			List<OrderactivityHistory> records,
			List<OrderactivityChildHistory> ocrecords,
			List<UpDateActDto> ugiftdtos, List<UpDateActDto> uPackagedtos,
			List<Orderdetail> newDetails, int userid,
			List<UpDateActDto> actIds, List<UpDateActDto> spiIds,
			Integer[] procount, double[] order_TotalMoney, Integer sites,
			String[] desc) throws Exception {
		desc[0] = "";
		UpDateActDto upDateActDto = null;
		// List<Integer> packids = new ArrayList<Integer>();
		for (AddNewDetailDto detailDto : orderDto.getDetails()) {
			Sku sku = skuService.selectByID(detailDto.getSkuID());

			if (sku == null) {
				logger.warn(MessageFormat.format("订单提交失败，未查询到商品数据，商品ID:{0}",
						detailDto.getSkuID()));
				return false;
			}
			Spu spu = spuService.queryById(sku.getSpuId());
			if (spu == null) {
				logger.warn(MessageFormat.format("订单提交失败，未查询到商品数据，商品ID:{0}",
						detailDto.getSkuID()));
				return false;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long days=(sdf.parse(sdf.format(new Date())).getTime()-detailDto.getUsetime().getTime())/(1000*3600*24);
			if(spu.getIstoday()==null || spu.getIstoday()!=1){
				//不能购买当天票
				 //long days=(new Date().getTime()-detailDto.getUsetime().getTime())/(1000*3600*24);
				 if(days==0){
					 logger.warn(MessageFormat.format("订单提交失败，该商品不支持购买当天票，商品ID:{0}",
							 sku.getSpuId()));
						desc[0]="该商品不支持购买当天票";
						return false;
				 }
			}
			double skuprice = 0.0d;
			
			Integer year=DateUtil.getYear(detailDto.getUsetime());
			Integer month=DateUtil.getMonth(detailDto.getUsetime());
			Integer day=DateUtil.getDay(detailDto.getUsetime());
			SkuShowtime skutime=skuShowtimeMapper.getSkuTimeBySkuid(detailDto.getSkuID(), year, month, day);
			if(skutime!=null){
				skuprice=skutime.getPrice().doubleValue();
			}else{
				skuprice=spu.getPrice().doubleValue();
			}
			
			if(spu.getIstoday()!=null && spu.getIstoday()==1){
				if(days==0){
					 skuprice=spu.getPrice().doubleValue();
				 }
			}
			 
			Orderdetail detail = new Orderdetail();
			detail.setOrderdate(new Date());
			detail.setIsPost(spu.getIspostage());
			detail.setOrdercode(order.getCode());
			detail.setBuyerid(order.getBuyerid());
			detail.setSellerid(order.getSellerid());
			detail.setShopid(order.getShopid());
			detail.setSkuId(detailDto.getSkuID());
			detail.setProductnum(sku.getNum());
			detail.setProductimg(sku.getImgurlApp());
			detail.setProductname(sku.getName());
			detail.setUsetime(detailDto.getUsetime());
			
			// switch (detailDto.getType()) {
			/*
			 * case 闪购: case 秒杀: if (detailDto.getSpikeID() == null) break;
			 * SpSpuDto spidto =
			 * spikeactivityMapper.getSpikeBySpikeIDAndSpuID(detailDto
			 * .getSpikeID(), detailDto.getSpuID()); if (spidto != null &&
			 * spidto.getId() > 0 && spidto.getSpuCount() > 0) { if
			 * (spidto.getSpuCount() < detailDto.proCount) {
			 * logger.warn(MessageFormat
			 * .format("订单提交失败，商品库存数量不足！订单商品数量:{0},库存商品数量:{1}",
			 * detailDto.getProCount(), sku.getStock())); desc[0] = "活动商品数量不足";
			 * 
			 * return false; } OrderactivityHistory record = new
			 * OrderactivityHistory();
			 * record.setId(UUID.randomUUID().toString());
			 * 
			 * record.setOrdercode(order.getCode());
			 * record.setOrderprice(order.getPrice().floatValue());
			 * record.setOrderdate(new Date());
			 * record.setShopId(order.getShopid());
			 * record.setOrdersellerid(order.getSellerid());
			 * record.setActivityId(spidto.getId());
			 * record.setActivityType(detailDto.getType() == ShopCartProType.秒杀
			 * ? ActivityHistory.秒杀.getValue() : ActivityHistory.闪购.getValue());
			 * record.setActivityNum(spidto.getNum());
			 * record.setActivityName(spidto.getName());
			 * 
			 * record.setActivityUserid(userid); record.setActivityUsertime(new
			 * Date()); record.setActivityPrice((sku.getAppprice().floatValue()
			 * - (float) spidto.getDiscountPrice()) detailDto.getProCount());
			 * record.setStatus(CompleteStatus.未支付.getValue());
			 * records.add(record); // sku.setAppprice((float)
			 * spidto.getDiscountPrice()); skuprice = (float)
			 * spidto.getDiscountPrice(); } order_TotalMoney[0] +=
			 * (Math.round(skuprice * 100) / 100d) * detailDto.getProCount();
			 * upDateActDto = new UpDateActDto();
			 * upDateActDto.setCount(detailDto.getProCount());
			 * upDateActDto.setId(spidto.getId()); procount[0] +=
			 * detailDto.getProCount(); spiIds.add(upDateActDto); break; case
			 * 组合商品: procount[0] += detailDto.getProCount(); break; case 赠品:
			 * break; default:
			 */
			// 获取商品数量
			procount[0] += detailDto.getProCount();
			order_TotalMoney[0] += (Math.round(skuprice * 100) / 100d)
					* detailDto.getProCount();
			// break;
			/* } */
			// if (detailDto.getType() == ShopCartProType.赠品) {
			// detail.setMarketid(1);
			// detail.setProductprice(BigDecimal.valueOf(0d));
			// } else {
			detail.setMarketid(0);
			detail.setProductprice(BigDecimal.valueOf(skuprice));
			// }
			detail.setProductcount(detailDto.getProCount());
			detail.setProductweight(detailDto.getWeight());
			detail.setIscomment(0);
			detail.setIsbackcomment(0);
			detail.setVaildflag(0);
			detail.setStatus(OrderDetailStatusEnum.订单未完成.getValue());
			detail.setProcode(ProductNumUtil.GetOrderNum());
			
			if(skutime!=null){
				int stock=skutime.getStock()-detailDto.getProCount();
				if (stock < 0) {
					LogHandle.error(LogType.Order, MessageFormat.format(
							"订单提交失败，商品库存数量不足！订单商品数量:{0},库存商品数量:{1}",
							detailDto.getProCount(), skutime.getStock()));
					desc[0] = "商品" + sku.getName() + "库存不足";
					return false;
				}
			}else{
				LogHandle.error(LogType.Order, MessageFormat.format(
						"订单提交失败，商品没有库存信息！订单商品:{0}",
						sku.getName()));
				desc[0] =sku.getName()+ detailDto.getUsetime()+ "没有库存信息";
				return false;
			}
			// 赠品不减库存
			// if (detailDto.getType() != ShopCartProType.赠品) {
			// int stock = sku.getStock() - detailDto.getProCount();
			// if (stock < 0) {
			// logger.warn(MessageFormat.format("订单提交失败，商品库存数量不足！订单商品数量:{0},库存商品数量:{1}",
			// detailDto.getProCount(),
			// sku.getStock()));
			// desc[0] = "商品库存不足";
			//
			// return false;
			// }
			// }
			// #region 商品活动验证
			if (detailDto.getMarketID() != null && detailDto.getMarketID() > 0) {
				ActivityMarket market = activityService.getById(detailDto
						.getMarketID());
				List<Fullgift> gifts = activityService.getGiftListByID(market
						.getId());
				if (market != null
						&& market.getId() > 0
						&& (market.getIscheck() != null && market.getIscheck()
								.booleanValue())
						&& market.getEndtime().getTime() >= new Date()
								.getTime()) {
					boolean iscount = false;

					for (Fullgift fullgift : gifts) {
						if (fullgift.getGiftcount() != null
								&& fullgift.getGiftcount() <= 0) {
							iscount = true;
							break;
						}
					}
					if ((market.getStock() != null && market.getStock() <= 0)
							|| (market.getActtype() == ActivityTypeEnum.满赠
									.getValue() && iscount)) {
						desc[0] = "活动资格数或赠送商品已领完";
						return true;
					}
					boolean check = true;
					switch (ActivityUseTypeEnum.values()[market.getUsetype()]) {
					case 针对金额:
						if (detail.getProductcount()
								* detail.getProductprice().floatValue() < market
									.getFullvalue()) {
							check = false;
						}
						break;
					case 针对商品:
						if (detail.getProductcount() < market.getCount()) {
							check = false;
						}
						break;
					default:
						check = false;
						break;
					}
					if (!check) {
						desc[0] = "订单不满足优惠条件";
						return false;
					}
					if (!CheckFullGift(market, orderDto, order, gifts, records,
							ocrecords, ugiftdtos, userid, actIds, sites, desc)) {
						return false;
					}
					detail.setMarketid(detailDto.getMarketID());
				} else {
					desc[0] = "无效活动或活动已过期";
					return false;
				}
			}
			// #endregion

			// #region 套餐验证

			if (!CheckPackage(detailDto, order, sku, detail, records,
					uPackagedtos, userid, desc, order_TotalMoney, sites)) {
				return false;
			}
			// #endregion
			// 获取订单下 商品单价*商品数量 得到的总金额

			newDetails.add(detail);
		}

		return true;
	}

	boolean CheckFullGift(ActivityMarket act, AddNewOrderDto orderDto,
			Orders order, List<Fullgift> gifts,
			List<OrderactivityHistory> records,
			List<OrderactivityChildHistory> ocrecords,
			List<UpDateActDto> uactdtos, int userid, List<UpDateActDto> actIds,
			Integer sites, String[] desc) throws Exception {
		OrderactivityChildHistory och = null;
		UpDateActDto uact = null;
		OrderactivityHistory record = new OrderactivityHistory();
		record.setActivityPrice(0f);
		record.setId(UUID.randomUUID().toString());
		UpDateActDto upDateActDto = new UpDateActDto();
		upDateActDto.setCount(1);
		upDateActDto.setId(act.getId());
		actIds.add(upDateActDto);
		switch (ActivityTypeEnum.values()[act.getActtype()]) {
		case 满减:
			order.setDiscount(order.getDiscount() + act.getSubvalue());
			record.setActivityPrice(act.getSubvalue());
			break;
		case 满赠:
			switch (ManZengTypeEnum.values()[gifts.get(0).getGifttype()]) {
			case 商品:
				List<Integer> giftIds = new ArrayList<Integer>();
				gifts.forEach(x -> giftIds.add(x.getGiftid()));
				List<Sku> skus = skuService.getListByIds(giftIds);
				for (Sku item : skus) {
					och = new OrderactivityChildHistory();
					uact = new UpDateActDto();
					och.setActid(act.getId());
					AddNewDetailDto da = new AddNewDetailDto();
					for (AddNewDetailDto w : orderDto.getDetails()) {
						if (w.getSkuID() == item.getId()
								&& w.getType() == ShopCartProType.赠品) {
							// da = w;
							if (w.getWeight() == null)
								w.setWeight(new BigDecimal(0));
							BeanUtils.copyProperties(da, w);
							och.setCount(w.getProCount());
							break;
						}
					}
					// order.Discount += da.ProCount * item.Price; 赠品不参与减免金额计算
					// 20160127 wl
					double skuprice = 0.0d;
					if (sites == ActivityUsePlatformEnum.pc.getValue()) {
						skuprice = item.getPrice().doubleValue();
					} else if (sites == ActivityUsePlatformEnum.app.getValue()) {
						skuprice = item.getAppprice().doubleValue();
					} else if (sites == ActivityUsePlatformEnum.wap.getValue()) {
						skuprice = item.getWapprice().doubleValue();
					} else if (sites == ActivityUsePlatformEnum.wechat
							.getValue()) {
						skuprice = item.getWechatprice().doubleValue();
					}
					och.setSkuprice((float) skuprice);
					och.setStatus(Och_Status.已增送.getValue());
					och.setType(Och_Type.商品.getValue());
					och.setOahid(record.getId());
					och.setOrdercode(order.getCode());
					och.setActid(item.getId());
					och.setSkuname(item.getName());
					ocrecords.add(och);
					if (record.getActivityPrice() != null)
						record.setActivityPrice(record.getActivityPrice()
								+ da.getProCount()
								* item.getPrice().floatValue());
					else
						record.setActivityPrice(da.getProCount()
								* item.getPrice().floatValue());
					record.setFullgiftstype(Och_Type.商品.getValue());
					Fullgift gift = null;
					for (Fullgift x : gifts) {
						if (x.getGiftid() == da.getSkuID()) {
							gift = x;
							break;
						}
					}
					if (gift != null && gift.getGiftcount() != null) {
						uact.setCount(da.getProCount());
						uact.setId(gift.getId());
						uactdtos.add(uact);
					}
				}
				break;
			case 优惠卷:
				// 异步批量赠送优惠券
				for (Fullgift gift : gifts) {
					och = new OrderactivityChildHistory();
					och.setStatus(Och_Status.未增送.getValue());
					och.setType(Och_Type.优惠券.getValue());
					och.setCouponid(gift.getGiftid());
					och.setCount(gift.getGiftcount());
					och.setActid(act.getId());
					och.setOrdercode(order.getCode());
					och.setOahid(record.getId());
					Coupon coupon = couponService.getByID(och.getCouponid());
					if (record.getActivityPrice() != null) {
						record.setActivityPrice(record.getActivityPrice()
								+ coupon.getFacevalue());
					} else {
						record.setActivityPrice(coupon.getFacevalue());
					}
					record.setFullgiftstype(Och_Type.优惠券.getValue());
					ocrecords.add(och);
				}
				break;
			case 积分:
				for (Fullgift gift : gifts) {
					och = new OrderactivityChildHistory();
					och.setStatus(Och_Status.未增送.getValue());
					och.setType(Och_Type.积分.getValue());
					och.setPoint(gift.getPoints());
					och.setActid(act.getId());
					och.setOrdercode(order.getCode());
					record.setFullgiftstype(Och_Type.积分.getValue());
					record.setActivityPrice(record.getActivityPrice()
							+ och.getPoint());
					och.setOahid(record.getId());
					ocrecords.add(och);
				}
				break;
			default:
				desc[0] = "活动赠送物品类型错误";
				return false;
			}
			break;
		default:
			desc[0] = "活动类型错误";
			return false;
		}
		// 写入活动记录
		record.setOrdercode(order.getCode());
		record.setOrderprice(order.getPrice().floatValue());
		record.setOrderdate(new Date());
		record.setOrdersellerid(order.getSellerid());
		record.setActivityId(act.getId());
		record.setActivityType(act.getActtype() == ActivityTypeEnum.满减
				.getValue() ? 1 : 2);
		record.setActivityNum(act.getActnum());
		record.setActivityName(act.getActname());
		record.setActivityUserid(userid);
		record.setActivityUsertime(new Date());
		record.setStatus(CompleteStatus.未支付.getValue());
		records.add(record);
		return true;
	}

	boolean CheckPackage(AddNewDetailDto detailDto, Orders order, Sku sku,
			Orderdetail detail, List<OrderactivityHistory> records,
			List<UpDateActDto> uPackagedtos, int userid, String[] desc,
			double[] order_TotalMoney, Integer sites) throws Exception {
		desc[0] = "";
		if (detailDto.getPackageID() != null && detailDto.getPackageID() > 0) {
			OrderactivityHistory record = new OrderactivityHistory();
			Package pack = packageService.getByID(detailDto.getPackageID());
			if (pack != null && pack.getId() > 0 && pack.getIscheck() != null
					&& pack.getIscheck().booleanValue()
					&& pack.getStatus() == 0) {
				if (pack.getEndtime().getTime() >= new Date().getTime()) {
					switch (PackageTypeEnum.values()[pack.getPacktype()]) {
					case 组合商品:
						List<SkuPackage> sps = packageService
								.getPackSkuList(pack.getId());
						// order.Discount += (sku.Price - sps.FirstOrDefault(f
						// => f.SKUID ==sku.ID).SKUPrice);
						SkuPackage spk = new SkuPackage();
						for (SkuPackage f : sps) {
							if (f.getSkuid().equals(sku.getId())) {
								spk = f;
								break;
							}
						}
						double skuprice = 0.0d;
						if (sites == ActivityUsePlatformEnum.pc.getValue()) {
							skuprice = sku.getPrice().doubleValue();
						} else if (sites == ActivityUsePlatformEnum.app
								.getValue()) {
							skuprice = sku.getAppprice().doubleValue();
						} else if (sites == ActivityUsePlatformEnum.wap
								.getValue()) {
							skuprice = sku.getWapprice().doubleValue();
						} else if (sites == ActivityUsePlatformEnum.wechat
								.getValue()) {
							skuprice = sku.getWechatprice().doubleValue();
						}
						record.setActivityPrice((float) skuprice
								- spk.getSkuprice());
						order_TotalMoney[0] += (double) spk.getSkuprice()
								* detailDto.getProCount();
						detail.setProductprice(BigDecimal.valueOf(spk
								.getSkuprice()));
						record.setActivityPrice(((float) skuprice - spk
								.getSkuprice()) * detail.getProductcount());
						break;
					case 限时抢购:
						// TODO 限时抢购活动处理
						break;
					default:
						desc[0] = "套餐类型错误";
						return false;
					}
					// UpDateActDto uact = new UpDateActDto();
					// 组合商品数量
					if (uPackagedtos != null && uPackagedtos.size() > 0) {
						if (!uPackagedtos.stream()
								.filter(x -> x.getId() == pack.getId())
								.findFirst().isPresent()) {
							UpDateActDto uact = new UpDateActDto();
							uact.setCount(detailDto.getProCount());
							uact.setId(pack.getId());
							uPackagedtos.add(uact);
						}
					} else {
						UpDateActDto uact = new UpDateActDto();
						uact.setCount(detailDto.getProCount());
						uact.setId(pack.getId());
						uPackagedtos.add(uact);
					}

					// 写入套餐记录

					record.setId(UUID.randomUUID().toString());
					record.setOrdercode(order.getCode());
					record.setOrderprice(order.getPrice().floatValue());
					record.setOrderdate(new Date());
					record.setOrdersellerid(order.getSellerid());
					record.setActivityId(pack.getId());
					record.setActivityType(3);
					record.setActivityNum(pack.getNum());
					record.setActivityName(pack.getName());
					record.setActivityUserid(userid);
					record.setActivityUsertime(new Date());
					;
					record.setStatus(CompleteStatus.未支付.getValue());
					records.add(record);
				} else {
					desc[0] = "套餐已过期";
					return false;
				}
			} else {
				desc[0] = "无效套餐活动";
				return false;
			}
		}
		return true;
	}

	/**
	 * 取消优惠卷
	 * 
	 * @param ConponIDs
	 * @param userid
	 * @throws Exception
	 */
	void CancelGoupon(List<Integer> ConponIDs, int userid) throws Exception {
		if (ConponIDs.size() > 0)
			couponService.updateUCouponCancel(ConponIDs, userid);

	}

	/**
	 * 优惠卷处理
	 * 
	 * @param orderDto
	 * @param order
	 * @param records
	 * @param faceValue
	 * @param CouponID
	 * @param ConponIDs
	 * @param userid
	 * @param desc
	 * @return
	 * @throws Exception
	 */
	boolean CheckPreferential(AddNewOrderDto orderDto, Orders order,
			List<OrderactivityHistory> records, double[] faceValue,
			int CouponID, List<Integer> ConponIDs, int userid, String[] desc)
			throws Exception {
		order.setPcoupon(faceValue[0]);
		double skuprice=orderDto.getTotalMoney();
		if (faceValue[0] <= 0) {
			if (orderDto.getCouponID() != null && orderDto.getCouponID() > 0) {

				Coupon Coupondto = couponService
						.getByID(orderDto.getCouponID());
				if (Coupondto.getShopid() != orderDto.getShopID()) {
					desc[0] = "优惠券使用失败:使用规则不匹配！";
					LogHandle.error(LogType.Order, "优惠券使用失败:店铺规则不匹配！",
							"OrderServiceBaseImpl/CheckPreferential");
					return false;
				}
				if (Coupondto.getCouponusetype() == CouponUseTypeEnum.商品
						.getValue()) {
					Optional<AddNewDetailDto> detailDto=orderDto
							.getDetails()
							.stream()
							.filter(x -> (x.getPackageID() == null || x
									.getPackageID().equals(0))
									&& x.getSpuID().equals(Coupondto.getUsetypeid())).findFirst();
					if (!detailDto.isPresent()) {
						desc[0] = "优惠券使用失败:使用规则不匹配！";
						LogHandle.error(LogType.Order, "优惠券使用失败:商品规则不匹配！",
								"OrderServiceBaseImpl/CheckPreferential");
						return false;
					}
					Integer year=DateUtil.getYear(detailDto.get().getUsetime());
					Integer month=DateUtil.getMonth(detailDto.get().getUsetime());
					Integer day=DateUtil.getDay(detailDto.get().getUsetime());
					SkuShowtime skutime=skuShowtimeMapper.getSkuTimeBySkuid(detailDto.get().getSkuID(), year, month, day);
					skuprice=skutime.getPrice().doubleValue();
					skuprice=skuprice*detailDto.get().getProCount();
				}
				
				faceValue[0] = couponService.UseCoupon(orderDto.getCouponID(),
						userid, order.getPrice().floatValue(),
						"提交订单使用优惠券，订单编号：" + order.getCode());
				if (faceValue[0] < 0) {
					desc[0] = "优惠券使用失败";
					return false;
				}
				order.setPcoupon(0d);
				order.setScoupon(faceValue[0]);
				CouponID = orderDto.getCouponID();
				ConponIDs.add(CouponID);
				if(faceValue[0] >skuprice){
					faceValue[0]=skuprice;
				}
			}
		}

		if (faceValue[0] <= 0) {
			return true;
		}
		order.setCouponid(CouponID);
		order.setDiscount((float) faceValue[0]);
		Coupon coupon = couponService.getByID(CouponID);
		OrderactivityHistory record = new OrderactivityHistory();
		record.setId(UUID.randomUUID().toString());
		record.setOrdercode(order.getCode());
		record.setOrderprice(order.getPrice().floatValue());
		record.setOrderdate(new Date());
		record.setOrdersellerid(order.getSellerid());
		record.setActivityId(order.getCouponid());
		record.setShopId(order.getShopid());
		record.setActivityType(ActivityHistory.优惠卷.getValue());
		record.setActivityNum(coupon.getCouponnum());
		record.setActivityName(coupon.getCouponname());
		record.setActivityUserid(userid);
		record.setActivityUsertime(new Date());
		record.setActivityPrice((float) faceValue[0]);
		record.setStatus(CompleteStatus.未支付.getValue());
		records.add(record);
		return true;
	}

	/**
	 * 店铺满减满增活动
	 * 
	 * @param orderDto
	 * @param order
	 * @param records
	 * @param ocrecords
	 * @param Uskudto
	 * @param userid
	 * @param actIds
	 * @param desc
	 * @return
	 * @throws Exception
	 */
	boolean CheckShopFullGift(AddNewOrderDto orderDto, Orders order,
			List<OrderactivityHistory> records,
			List<OrderactivityChildHistory> ocrecords,
			List<UpDateActDto> Uskudto, int userid, List<UpDateActDto> actIds,
			String[] desc, Integer sites) throws Exception {

		if (orderDto.getActivityID() != null && orderDto.getActivityID() > 0) {

			ActivityMarket act = activityService.getById(orderDto
					.getActivityID());
			List<Fullgift> gifts = activityService.getGiftListByID(act.getId());
			if (act != null && act.getId() != 0 && act.getIscheck() == true
					&& act.getEndtime().getTime() >= new Date().getTime()) {
				int count = 0;
				int giftype = 1;// 赠送类型：0优惠券，1商品，2积分
				for (Fullgift w : gifts) {
					giftype = w.getGifttype();
					if (giftype == 1) {
						if (w.getGiftcount() != null && w.getGiftcount() > 0) {
							count++;
							break;
						}
					}
				}
				if (giftype == 1) {
					if ((act.getStock() != null && act.getStock() <= 0)
							|| (act.getActtype() == ActivityTypeEnum.满赠
									.getValue() && count <= 0)) {
						desc[0] = "活动资格数或赠送商品已领完";
						return true;
					}
				}
				boolean check = true;
				switch (ActivityUseTypeEnum.values()[act.getUsetype()]) {
				case 针对金额:
					if (order.getPrice().floatValue() < act.getFullvalue()) {
						check = false;
					}
					break;
				case 针对商品:
					count = 0;
					for (AddNewDetailDto w : orderDto.getDetails()) {
						if (w.getSpuID() == act.getSpuid()) {
							count += w.getProCount();
						}
					}
					if (count < act.getCount()) {
						check = false;
					}
					break;
				default:
					check = false;
					break;
				}
				if (!check) {
					desc[0] = "订单不满足优惠条件";
					return false;
				}
				return CheckFullGift(act, orderDto, order, gifts, records,
						ocrecords, Uskudto, userid, actIds, sites, desc);
			} else {
				desc[0] = "无效活动或活动已过期";
				return false;
			}
		}
		return true;
	}

	@Override
	public List<Orders> queryAllByUserId(Integer buyerid) throws Exception {
		// TODO Auto-generated method stub
		if (buyerid == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("参数为null");
				throw new IllegalArgumentException(
						"The parameter Criteria is null!");
			}
		}
		return ordersMapper.selectByBuyerId(buyerid);
	}

	@Override
	public PageBean getMemberUserListOrder(Integer page1, Integer size11,
			Pc_OrderCriteria aoc) throws Exception {
		PageBeanUtil pageBeanUtil = new PageBeanUtil(aoc, page1, size11);// 还可以
		// 设置其他的参数
		// 多条件查询
		PageBean pageBean = pageBeanUtil.getPage();
		List<OrderMemberDto> beanList = orderMemberMapper
				.getMemberUserListOrderPage(pageBeanUtil);
		pageBean.setBeanList(beanList);
		return pageBean;
		// return orderMemberMapper.getMemberUserListOrder(buyerid);
	}

	@Override
	public List<Orders> queryByStatus(Integer buyerid, Integer status)
			throws Exception {
		if (buyerid == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("参数为null");
				throw new IllegalArgumentException(
						"The parameter Criteria is null!");
			}
		}
		if (status == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("参数为null");
				throw new IllegalArgumentException(
						"The parameter Criteria is null!");
			}
		}
		return ordersMapper.selectByStatus(buyerid, status);
	}

	@Override
	public int delbyId(int orderID, int userid) throws Exception {
		// 子类实现
		return 0;
	}

	@Override
	public Orders getOrderByID(int id) throws Exception {
		// 子类实现
		return null;
	}

	@Override
	public List<Orders> getOrderByGroupCode(String groupCode) throws Exception {
		// 子类实现
		return null;
	}

	@Override
	public List<Orderdetail> getDetailsByOrderID(int orderID) throws Exception {
		// 子类实现
		return null;
	}

	@Override
	public Orderdetail getDetailByID(int detailID) throws Exception {
		// 子类实现
		return null;
	}

	@Override
	public boolean updatePayForBalance(String groupCode, Double totalMoney,
			String ip, SessionUser user, String[] desc) throws Exception {
		// 子类实现
		return false;
	}

	@Override
	public int addOrderRecords(String Filed, String OldValue, String NewValue,
			List<Orders> orders, Orders order, SessionUser user, String userip)
			throws Exception {
		// 子类实现
		return 0;
	}

	@Override
	public boolean updatePayForOrderSuccess(int orderID, PayTypeEnum payType,
			float money, int couponID, String userip, String[] desc)
			throws Exception {
		// 子类实现
		return false;
	}

	public boolean updateSendProForOrder(int orderID, String logisticsName,
			String logisticsCode, String ip, SessionUser user, Orders order)
			throws Exception {
		// 子类实现
		return false;
	}

	@Override
	public boolean updateCancelOrder(int orderID, String reason, String img,
			String ip, SessionUser user, String[] desc) throws Exception {
		// 子类实现
		return false;
	}

	@Override
	public boolean updateConfirmReceivePro(int orderID, SessionUser user,
			String ip, String[] desc) throws Exception {
		// 子类实现
		return false;
	}

	@Override
	public PageBean getP_OrdersList(int index, int pageRow,
			P_OrderListCriteria criteria) throws Exception {
		// 子类实现
		return null;
	}

	@Override
	public Orders queryByID(Integer id) throws Exception {

		return ordersMapper.selectByPrimaryKey(id);
	}

	@Override
	public int update(Orders order) throws Exception {

		return ordersMapper.updateByPrimaryKeySelective(order);
	}

	public PageBean getOrdersByPage(CriteriaOrder criteria, int page, int size)
			throws Exception {

		return null;
	}

	@Override
	public PageBean getUserListOrder(int page, int size,
			Api_OrderCriteria criteria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public PageBean getAfterOrdersByPage(Criteria criteria, int page, int size)
			throws Exception {
		return null;
	}

	/**
	 * 根据id查询订单
	 * 
	 * @see com.yinlian.wssc.web.service.OrderService#queryById(java.lang.Integer)
	 */
	@Override
	public Orders queryById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return ordersMapper.getOrderByID(id);
	}

	/**
	 * @see com.yinlian.wssc.web.service.OrderService#selectByStatus(java.lang.Integer)
	 */
	@Override
	public List<Orders> selectByStatus(Integer status) throws Exception {
		return null;
	}

	/**
	 * 发送推送消息
	 * 
	 * @param userid
	 *            用户id
	 * @param ordercode
	 *            订单id
	 * @param ordertime
	 *            订单时间
	 * @param logisticsname
	 *            物流公司名称
	 * @param logisticscode
	 *            物流编码
	 * @param ttype
	 *            模板类型 (0邮件 1短信 2系统短信 3系统推送)
	 * @param ctype
	 *            内容类型(0订单 1促销)
	 * @param tag
	 *            模板标识 （详细见枚举TemplateTagEnum）
	 * @param mtype
	 *            消息类型
	 * @throws Exception
	 */
	@Override
	public void sendMessage(int userid, String ordercode, Date ordertime,
			String logisticsname, String logisticscode, int ttype, int ctype,
			TemplateTagEnum tag, MessagesTypeEnum mtype) throws Exception {
		Users users = userService.queryById(userid);
		PushService pushService = new PushService();
		Sendtemplate template = freightService.getbyType(ttype, ctype,
				tag.getValue());
		String[] content = new String[1];
		SimpleDateFormat dateFormater = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		if (template == null) {
			if(users.getNickname()!=null){
				content[0] = "尊敬的用户" + users.getNickname() + ",您已" + tag.name()
						+ "，单号" + ordercode + "。敬请关注订单变化。";
			}else{
				content[0] = "尊敬的用户" + users.getMobile() + ",您已" + tag.name()
						+ "，单号" + ordercode + "。敬请关注订单变化。";
			}
			

		} else {
			content[0] = template.getContent();
			freightService.exchangeTemplate(users.getUsername(), ordercode,
					logisticsname, logisticscode,
					dateFormater.format(ordertime), content);
		}
		Messages messages = new Messages();
		messages.setContent(content[0]);
		messages.setUserid(userid);
		messages.setUsername(users.getUsername());
		messages.setTitle(tag.name());
		messages.setType(mtype.getValue());
		messages.setSendtime(new Date());
		messagesMapper.insert(messages);
		pushService.sendPushAlias(tag.name(), content[0], users.getId()
				.toString());
		pushService.sendPushAliasIOS(tag.name(), content[0], users.getId()
				.toString());
	}

	/**
	 * 根据id删除订单
	 * 
	 * @see com.yinlian.wssc.web.service.OrderService#delOrder(java.lang.Integer)
	 */
	@Override
	public int delOrder(Integer _orderid, Integer userid) throws Exception {
		Orders orders = new Orders();
		orders.setId(_orderid);
		orders.setUserdeltime(new Date());
		orders.setUserisdel(1);
		return ordersMapper.userDelOrderById(orders);
	}

	/**
	 * 根据组订单编号修改订单状态为已支付
	 * 
	 * @see com.yinlian.wssc.web.service.OrderService#updateByGroupCode(java.lang.String)
	 */
	@Override
	public void updateByGroupCode(String orderGroup, String money)
			throws Exception {

		OrdersExample example = new OrdersExample();
		OrdersExample.Criteria criteria = example.createCriteria();
		criteria.andGroupcodeEqualTo(orderGroup);
		List<Orders> list = ordersMapper.selectByExample(example);
		// TODO 未验证 金额
		// list.parallelStream().mapToDouble(x->x.getPrice().doubleValue()).reduce(0,
		// (x, y) -> x + y);
		if (list != null && list.size() > 0) {
			for (Orders orders : list) {
				ordersMapper.updateStatus(OrderStatusEnum.待使用.getValue(),
						orders.getId());
			}
		}
	}

	/**
	 * 直营订单统计
	 */
	@Override
	public PageBean selectOrderTjByPage(CriteriaDdtj criteria, Integer pc,
			Integer ps) throws Exception {

		PageBeanUtil pageBeanUtil = new PageBeanUtil(criteria, pc, ps);// 还可以
																		// 设置其他的参数
																		// 多条件查询
		PageBean pageBean = pageBeanUtil.getPage();
		String type = criteria.getType();
		List<SaleOrder> beanList = null;
		if (type.equals("1")) {
			beanList = ordersMapper.selectOrderTjDayByPage(pageBeanUtil);
		} else if (type.equals("2")) {
			beanList = ordersMapper.selectOrderTjWeekByPage(pageBeanUtil);
		} else if (type.equals("3")) {
			beanList = ordersMapper.selectOrderTjMonthByPage(pageBeanUtil);
		} else if (type.equals("4")) {
			beanList = ordersMapper.selectOrderTjQuaterByPage(pageBeanUtil);
		} else if (type.equals("5")) {
			beanList = ordersMapper.selectOrderTjYearByPage(pageBeanUtil);
		}
		pageBean.setBeanList(beanList);

		return pageBean;
	}

	/**
	 * 店铺订单统计
	 */
	@Override
	public PageBean selectDpOrderTjByPage(CriteriaDdtj criteria, Integer pc,
			Integer ps) throws Exception {
		PageBeanUtil pageBeanUtil = new PageBeanUtil(criteria, pc, ps);// 还可以
																		// 设置其他的参数
																		// 多条件查询
		PageBean pageBean = pageBeanUtil.getPage();
		String type = criteria.getType();
		List<SaleOrder> beanList = null;
		if (type.equals("1")) {
			beanList = ordersMapper.selectDpOrderTjDayByPage(pageBeanUtil);
		} else if (type.equals("2")) {
			// 订单详细报表
			beanList = ordersMapper.selectDpOrderTjDetailByPage(pageBeanUtil);
		} else if (type.equals("3")) {
			beanList = ordersMapper.selectDpOrderTjMonthByPage(pageBeanUtil);
			if (beanList != null && beanList.size() > 0) {
				for (SaleOrder dto : beanList) {
					dto.setDatestr(criteria.getDatem());
				}
			}
		} else if (type.equals("4")) {
			beanList = ordersMapper.selectDpOrderTjQuaterByPage(pageBeanUtil);
			if (beanList != null && beanList.size() > 0) {
				for (SaleOrder dto : beanList) {
					dto.setDatestr(criteria.getDatem());
				}
			}
		} else if (type.equals("5")) {
			beanList = ordersMapper.selectDpOrderTjYearByPage(pageBeanUtil);
			if (beanList != null && beanList.size() > 0) {
				for (SaleOrder dto : beanList) {
					dto.setDatestr(criteria.getDatem());
				}
			}
		}

		pageBean.setBeanList(beanList);

		return pageBean;
	}

	/**
	 * 统计待付款，待收货，待评价，退款的订单个数
	 */
	@Override
	public OrderCountDto selectCount(Integer userid) {
		List<OrderCountDto> list = ordersMapper.queryCount(userid);
		OrderCountDto dto = new OrderCountDto();
		dto.setDfk(0);
		dto.setDsy(0);
		dto.setYsy(0);
		dto.setSh(0);
		for (OrderCountDto cDto : list) {
			dto.setDfk(dto.getDfk() + cDto.getDfk());
			dto.setDsy(dto.getDsy() + cDto.getDsy());
			dto.setYsy(dto.getYsy() + cDto.getYsy());
			dto.setSh(dto.getSh() + cDto.getSh());
		}
		return dto;
	}

	@Override
	public OrderPayDto orderPay(String num) throws Exception {

		return null;
	}

	@Override
	public List<Orders> getUnpaidOrders() throws Exception {
		return ordersMapper.getUnpaidOrders();
	}

	@Override
	public List<Orders> getDeliverOrders() throws Exception {
		return ordersMapper.getDeliverOrders();
	}

	@Override
	public OrdersDto getorderdetail(int orderid, int userid) throws Exception {

		OrdersDto orderDto = new OrdersDto();
		Orders orders = ordersMapper.selectByPrimaryKey(orderid);
		if (orders != null) {
			// Receiveinfo receiveinfo =
			// receiveInfoService.queryByOrderGroupId(orders.getGroupcode());
			// if (receiveinfo != null) {
			// orderDto.setConsignee(receiveinfo.getConsignee());
			// orderDto.setAddress(receiveinfo.getAddress());
			// orderDto.setTelPhone(receiveinfo.getTelphone());
			// }
			Idcardinfo idcardinfo = idcardinfoMapper.getByGroupCode(orders
					.getGroupcode());
			if (idcardinfo != null) {
				orderDto.setName(idcardinfo.getName());
				orderDto.setPhone(idcardinfo.getPhone());
				orderDto.setIdcard(idcardinfo.getCard());
			}
			Scenic scenic = scenicMapper.getScenicByShopId(orders.getShopid());
			if (scenic != null) {
				orderDto.setLatitude(scenic.getLatitude().toString());
				orderDto.setLongitude(scenic.getLongitude().toString());
			}
			orderDto.setCode(orders.getGroupcode());
			orderDto.setId(orderid);
			orderDto.setPayType(orders.getPaytype());
			orderDto.setStatus(orders.getStatus());
			orderDto.setRemark(orders.getRemark());
			orderDto.setOrderDate(orders.getAddorderdate());
			orderDto.setQrcode(orders.getQrcode());
			orderDto.setGroupcode(orders.getGroupcode());
			Integer beans = orders.getBeans();
			if (beans == null) {
				beans = 0;
			}
			orderDto.setPulsePay(StringUtilsEX.ToDoubleNull(beans + ".00"));
			// Dispatching dispatching =
			// dispatchingService.queryByOrderGroupCode(orders.getGroupcode());
			// if (dispatching != null) {
			// orderDto.setTransportMode(dispatching.getType());
			// }

			orderDto.setFreight(StringUtilsEX.ToFloat(orders.getFreight()
					.toString()));
			orderDto.setDeliverDate(orders.getDeliverdate());
			Shop shop = shopService.queryById(orders.getShopid());
			if (shop != null) {
				orderDto.setShopname(shop.getName());
				orderDto.setShoptype(shop.getShoptype());
			}
			// 查询订单发票
			// Invoice invoice = invoiceService.selectByOrderId(orderid);
			// if (invoice != null) {
			// orderDto.setInvoiceTitle(invoice.getTitle());
			// orderDto.setInvoiceContent(invoice.getContent());
			// }
			// receiveinfo =
			// receiveInfoService.queryByOrderGroupId(orders.getGroupcode());
			// if (receiveinfo != null) {
			// orderDto.setProvincename(provinceServcice.queryByCode(receiveinfo.getProvince()).getName());
			// orderDto.setCityname(cityServcie.queryByCode(receiveinfo.getCity()).getName());
			// orderDto.setAreaname(areaService.queryByCode(receiveinfo.getArea()).getName());
			// }
			Double acutalpay = StringUtilsEX.ToDoubleNull(orders.getActualpay()
					.toString());

			orderDto.setActualPay(acutalpay);
			orderDto.setPrice(orders.getPrice());
			/* orderDto.setPointsPay(0.00); */
			List<Orderdetail> orderdetails = orderdetailMapper
					.queryByOrderid(orderid);
			List<OrderdetailDto> listdtos = new ArrayList<OrderdetailDto>();
			for (Orderdetail od : orderdetails) {
				OrderdetailDto orderdetailDto = new OrderdetailDto();
				orderdetailDto.setProductcount(od.getProductcount());
				Spu spu = spuMapper.selectByPrimaryKey(od.getSpuid());
				if (spu != null) {
					orderdetailDto.setProductimg(spu.getImgurl());
				}
				orderdetailDto.setProductname(od.getProductname());
				orderdetailDto.setProductprice(od.getProductprice());
				orderdetailDto.setProductnum(od.getProductnum());
				orderdetailDto.setStatus(od.getStatus());
				orderdetailDto.setProductid(od.getSkuId());
				orderdetailDto.setId(od.getId());
				orderdetailDto.setSpuid(od.getSpuid());
				orderdetailDto.setUsetime(od.getUsetime());
				listdtos.add(orderdetailDto);
			}
			orderDto.setChildren(listdtos);
			orderDto.setCount(orderdetails.size());
			if (orders.getCancelreason() != null) {
				orderDto.setReason(orders.getCancelreason());
			}
			if (orders.getStatus() == OrderStatusEnum.审核中.getValue()) {
				Applyforcancelorder applyfor = applyforcancelorderService
						.getbyOrderAndUser(orders.getId(), orders.getBuyerid());
				Applyforcancelorder applyforseller = applyforcancelorderService
						.getbyOrderAndUser(orders.getId(), orders.getSellerid());
				if (applyfor != null) {
					orderDto.setReason(applyfor.getContent());
				}
				if (applyforseller != null) {
					orderDto.setCancelreason(applyforseller.getContent());
					orderDto.setCancelimg(applyforseller.getImgurl());
				}
			}
			// BigDecimal totalm = orderDto.getPrice().add(new
			// BigDecimal(orderDto.getFreight()));
			// BigDecimal paym = new BigDecimal(orderDto.getActualPay());
			// orderDto.setDiscount(totalm.subtract(paym).doubleValue());

		}
		return orderDto;
	}

	@Override
	public PageBean pcselectByStatus(Integer page1, Integer size11,
			Pc_OrderCriteria aoc) throws Exception {
		PageBeanUtil pageBeanUtil = new PageBeanUtil(aoc, page1, size11);// 还可以
		// 设置其他的参数
		// 多条件查询
		PageBean pageBean = pageBeanUtil.getPage();
		List<OrderMemberDto> beanList = orderMemberMapper
				.pcselectByStatusPage(pageBeanUtil);
		pageBean.setBeanList(beanList);
		return pageBean;
		// return orderMemberMapper.pcselectByStatus(userid,status);
	}

	@Override
	public PageBean cancelorder(Integer page1, Integer size11,
			Pc_OrderCriteria aoc) throws Exception {
		PageBeanUtil pageBeanUtil = new PageBeanUtil(aoc, page1, size11);// 还可以
		// 设置其他的参数
		// 多条件查询
		PageBean pageBean = pageBeanUtil.getPage();
		List<OrderMemberDto> beanList = orderMemberMapper
				.pccancelorderPage(pageBeanUtil);
		pageBean.setBeanList(beanList);
		return pageBean;
		// return orderMemberMapper.pccancelorder(userid);
	}

	@Override
	public PageBean commentorder(Integer page1, Integer size11,
			Pc_OrderCriteria aoc) throws Exception {
		PageBeanUtil pageBeanUtil = new PageBeanUtil(aoc, page1, size11);// 还可以
		// 设置其他的参数
		// 多条件查询
		PageBean pageBean = pageBeanUtil.getPage();
		List<OrderMemberDto> beanList = orderMemberMapper
				.pccommentorderPage(pageBeanUtil);
		pageBean.setBeanList(beanList);
		return pageBean;
		// return orderMemberMapper.pccommentorder(userid,iscomment);
	}

	// 评价，未评价，未添加图片评价订单数量
	@Override
	public List<OrderCommentCountDto> queryCount(Integer userid)
			throws Exception {
		if (userid == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("参数为null");
				throw new IllegalArgumentException(
						"The parameter Criteria is null!");
			}
		}
		return orderMemberMapper.queryCount(userid);
	}

	@Override
	public List<OrderMemberDto> byorderidcomment(Integer userid, Integer orderid)
			throws Exception {
		if (userid == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("参数为null");
				throw new IllegalArgumentException(
						"The parameter Criteria is null!");
			}
		}
		return orderMemberMapper.byorderidcomment(userid, orderid);
	}

	@Override
	public OrderInfo getOrderInfoById(int orderId) throws Exception {
		return null;
	}

	@Override
	public List<OrderMemberDto> getMemberBytimeOrder(Integer buyerid,
			String start, String end) throws Exception {
		if (buyerid == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("参数为null");
				throw new IllegalArgumentException(
						"The parameter Criteria is null!");
			}
		}
		return orderMemberMapper.getMemberBytimeOrder(buyerid, start, end);
	}

	@Override
	public PageBean getPcUserListOrder(Integer page1, Integer size11,
			Pc_OrderCriteria aoc) throws Exception {
		PageBeanUtil pageBeanUtil = new PageBeanUtil(aoc, page1, size11);// 还可以
																			// 设置其他的参数
																			// 多条件查询
		PageBean pageBean = pageBeanUtil.getPage();
		List<OrderDto> beanList = ordersMapper
				.getpcUserListOrderPage(pageBeanUtil);
		pageBean.setBeanList(beanList);
		return pageBean;
	}

	@Override
	public Map<String, Object> queryOrders(Platfrom_SYCriteria criteria)
			throws Exception {

		return ordersMapper.selectOrder(criteria);
	}

	@Override
	public PageBean getMemberListOrderPage(Integer page1, Integer size11,
			Pc_OrderCriteria aoc) throws Exception {
		PageBeanUtil pageBeanUtil = new PageBeanUtil(aoc, page1, size11);// 还可以
		// 设置其他的参数
		// 多条件查询
		PageBean pageBean = pageBeanUtil.getPage();
		List<MemberOrderDto> beanList = orderMemberMapper
				.getMemberListOrderPage(pageBeanUtil);
		pageBean.setBeanList(beanList);
		return pageBean;
	}

	@Override
	public List<SaleOrder> selectAllOrder(Integer userid, Integer shopid)
			throws Exception {
		List<SaleOrder> beanList = ordersMapper.selectAllOrder(userid);
		if (beanList != null && beanList.size() > 0) {

			CriteriaDdtj criteria = new CriteriaDdtj();
			criteria.setId(shopid);
			List<Orderdetail> odlist = orderdetailMapper
					.getDetailsByDate(criteria);
			for (SaleOrder dto : beanList) {
				List<Orderdetail> ods = odlist.stream()
						.filter(x -> x.getShopid().equals(dto.getShopid()))
						.collect(Collectors.toList());
				List<Orderdetail> odstatus = null;
				if (ods != null && ods.size() > 0) {
					// 待使用
					odstatus = ods
							.stream()
							.filter(x -> x.getStatus() != null
									&& x.getStatus().equals(
											OrderDetailStatusEnum.待使用.getValue()))
							.collect(Collectors.toList());
					dto.setCount_DSY(odstatus.stream()
							.collect(Collectors.groupingBy(Orderdetail::getOrderid))
							.size());
					dto.setMoney_DSY(BigDecimal.valueOf(odstatus.stream().mapToLong(x -> x.getProductcount()
											* x.getProductprice().setScale(2,BigDecimal.ROUND_HALF_UP)
													.longValue()).sum()));
					// 已使用
					odstatus = ods
							.stream()
							.filter(x -> x.getStatus() != null
									&& x.getStatus().equals(
											OrderDetailStatusEnum.已使用
													.getValue()))
							.collect(Collectors.toList());
					dto.setCount_YSY(odstatus
							.stream()
							.collect(
									Collectors
											.groupingBy(Orderdetail::getOrderid))
							.size());
					dto.setMoney_YSY(BigDecimal.valueOf(odstatus.stream().mapToLong(
									x -> x.getProductcount() * x.getProductprice().setScale(2,BigDecimal.ROUND_HALF_UP)
															.longValue()).sum()));
					// 申请退款
					odstatus = ods
							.stream()
							.filter(x -> x.getStatus() != null
									&& x.getStatus().equals(
											OrderDetailStatusEnum.申请退款
													.getValue()))
							.collect(Collectors.toList());
					dto.setCount_TKSQ(odstatus
							.stream()
							.collect(
									Collectors
											.groupingBy(Orderdetail::getOrderid))
							.size());
					dto.setMoney_TKSQ(BigDecimal.valueOf(odstatus.stream().mapToLong(
									x -> x.getProductcount() * x.getProductprice()
											.setScale(2,BigDecimal.ROUND_HALF_UP)
															.longValue()).sum()));
					// 退款失败
					odstatus = ods
							.stream()
							.filter(x -> x.getStatus() != null
									&& x.getStatus().equals(
											OrderDetailStatusEnum.退款失败
													.getValue()))
							.collect(Collectors.toList());
					dto.setCount_TKSB(odstatus
							.stream()
							.collect(
									Collectors
											.groupingBy(Orderdetail::getOrderid))
							.size());
					dto.setMoney_TKSB(BigDecimal.valueOf(odstatus.stream().mapToLong(
									x -> x.getProductcount() * x.getProductprice()
													.setScale(2,BigDecimal.ROUND_HALF_UP)
															.longValue()).sum()));
					// 退款审核
					odstatus = ods
							.stream()
							.filter(x -> x.getStatus() != null
									&& x.getStatus().equals(
											OrderDetailStatusEnum.审核中
													.getValue()))
							.collect(Collectors.toList());
					dto.setCount_TKSH(odstatus
							.stream()
							.collect(
									Collectors
											.groupingBy(Orderdetail::getOrderid))
							.size());
					dto.setMoney_TKSH(BigDecimal.valueOf(odstatus.stream().mapToLong(
									x -> x.getProductcount()
											* x.getProductprice()
											.setScale(2,BigDecimal.ROUND_HALF_UP)
											.longValue()).sum()));
					// 已退款
					odstatus = ods
							.stream()
							.filter(x -> x.getStatus() != null
									&& x.getStatus().equals(
											OrderDetailStatusEnum.退款成功
													.getValue()))
							.collect(Collectors.toList());
					dto.setCount_YTK(odstatus
							.stream()
							.collect(
									Collectors
											.groupingBy(Orderdetail::getOrderid))
							.size());
					dto.setMoney_YTK(BigDecimal.valueOf(odstatus.stream().mapToLong(
									x -> x.getProductcount()
											* x.getProductprice()
											.setScale(2,BigDecimal.ROUND_HALF_UP)
											.longValue()).sum()));

				}

			}
		}
		return beanList;
	}

	@Override
	public OrderCountDto selectDetailCount(int buyerId) throws Exception {

		return ordersMapper.selectGoodCount(buyerId);
	}

	@Override
	public PageBean getBillOrdersList(OrderCriteria criteria, Integer page,
			Integer size) throws Exception {
		PageBeanUtil pageBeanUtil = new PageBeanUtil(criteria, page, size);
		PageBean pageBean = pageBeanUtil.getPage();
		List<OrderDto> beanList = ordersMapper.selectOrdersByPage(pageBeanUtil);
		beanList.forEach(b -> {
			try {
				b.setChildren(getDetailsByOrderID(b.getId()));
			} catch (Exception e) {
				b.setChildren(null);
			}
		});
		pageBean.setBeanList(beanList);
		return pageBean;
	}

	@Override
	public com.yinlian.wssc.web.dto.OrderDto getorderdetail(int orderid)
			throws Exception {
		OrderDto dto = ordersMapper.getorderdetail(orderid);
		if (dto != null) {
			dto.setChildren(getDetailsByOrderID(dto.getId()));
		}
		return dto;
	}

	/**
	 * 添加用户资金变更
	 * 
	 * @param userid
	 *            用户id
	 * @param balance
	 *            用户余额
	 * @param money
	 *            变更金额
	 * @param desc
	 *            说明
	 * @param paynum
	 *            流水号
	 * @param userip
	 *            客户端ip
	 * @param type
	 *            类型
	 * @param ordercode
	 * @param status
	 * @return
	 * @throws Exception
	 */
	private int financeAdd(int userid, double balance, double money,
			String desc, String paynum,int paytype, String userip, int type,
			String ordercode, int status) throws Exception {
		Userfinance userfinance = new Userfinance();
		userfinance.setUserid(userid);
		userfinance.setCreatetime(new Date());
		userfinance.setBalance(balance);
		userfinance.setMoney(money);
		userfinance.setDescription(desc);
		userfinance.setPaynum(paynum);
		userfinance.setUserip(userip);
		userfinance.setType(type);
		userfinance.setNumber(ordercode);
		userfinance.setStatus(status);
		userfinance.setFinancetype(FinanceType.金额.getValue());
		return userfinanceMapper.insert(userfinance);
	}

	private int recordsAdd(int userid, String userip, int type,
			int usercapitalid) throws Exception {
		Financerecords financerecords = new Financerecords();
		financerecords.setUserid(userid);
		financerecords.setUserip(userip);
		financerecords.setType(type);
		financerecords.setCreatetime(new Date());
		financerecords.setStatus(0);
		financerecords.setUsercapitalid(usercapitalid);
		return financerecordsMapper.insert(financerecords);
	}

	@Override
	public PageBean getSHOrderList(Integer page1, Integer size11,
			Api_OrderCriteria aoc) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Orders> getInvalidOrder() throws Exception {
		return ordersMapper.getInvalidOrder();
	}

	@Override
	public List<SaleOrder> selectDpOrderTj(CriteriaDdtj criteria)
			throws Exception {
		String type = criteria.getType();
		List<SaleOrder> beanList = null;
		if (type.equals("1")) {
			beanList = ordersMapper.selectDpOrderTjDay(criteria);
		} else if (type.equals("2")) {
			// 订单详细报表
			beanList = ordersMapper.selectDpOrderTjDetail(criteria);
		} else if (type.equals("3")) {
			beanList = ordersMapper.selectDpOrderTjMonth(criteria);
		} else if (type.equals("4")) {
			beanList = ordersMapper.selectDpOrderTjQuater(criteria);
		} else if (type.equals("5")) {
			beanList = ordersMapper.selectDpOrderTjYear(criteria);
		}
		return beanList;
	}

}
