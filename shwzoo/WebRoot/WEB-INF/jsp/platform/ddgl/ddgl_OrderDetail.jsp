﻿
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/decorators/getFileUrl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src="/resource/public/platform/js/order/ddgl_dddetail.js"></script>
<script src="/resource/public/platform/js/order/ddgl_ddstatus.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var id = ${order.id};
		var status = ${order.status};
		GetStatus(id, status);
	})
	function formCancel() {
		var isowned = ${isowned};
		var ch = ${ch};
		var type = "${type}";
		if(parseInt(ch)>0){
			switch (parseInt(ch)) {
	         case 1: location.href = "/platform/shop/cdorderList"; break;
	         default:break;
			}
		}else{
			if (type == 0) {
				location.href = "/platform/ddgl/ddgl_dpDfkOrderList";
			} else if(type == 1){
				location.href = "/platform/ddgl/ddgl_dpDsyOrderList";
			}else if(type == 2){
				location.href = "/platform/ddgl/ddgl_dpShzOrderList";
			}else if(type == 3){
				location.href = "/platform/ddgl/ddgl_dpYqxOrderList";
			}else if(type == 4){
				location.href = "/platform/ddgl/ddgl_dpYwjOrderList";
			}else{
				location.href = "/platform/ddgl/ddgl_dpOrderList";
			}
		}
		
	}
</script>

<div class="mainright">
	<div class="l_wzmb">
		<div class="l_wzmbtop">
			<ul>
				<li class="sj_hover"><a href="javascript:void(0);">订单详情</a><span
					class="sj-img"></span></li>

			</ul>
			<span class="l_fhddlb"><input class="inquire"
				type="button" onclick="formCancel()" value="返回订单列表"></span>
		</div>
		<!--l_wzmbtop   stop onclick="formCancel()"  -->

		<!--n_ddxq  begin -->
		<div class="n_ddxq">
			<div class="l_ddhbj">
				<span>主订单号：${order.groupcode}</span> 
				<span>状态：<span id="status" class="red"></span></span> 
					<span class="l_fhddlb" name="ddcz"
					style="margin-right: 0px;"></span>
					 <input type="hidden" id="reason" value="${reason}"  />
					 <div></div>
					 <span id="span_reason" style="display:none;">买家申请原因：
				<span id="reason" class="red">${reason}</span></span>
			</div>
			<!--l_ddhbj   stop -->
			<div class="n_ddxqmk" style="margin-top: 20px;">
				<div class="n_ddxqmktop">身份信息</div>
				<div class="n_ddxqtr">
					<div class="n_ddxqtdleft">姓名：</div>
					<div class="n_ddxqtdright">${idcardinfo.name}</div>
				</div>
				<!--n_ddxqtr  stop -->
				<div class="n_ddxqtr">
					<div class="n_ddxqtdleft">手机号：</div>
					<div class="n_ddxqtdright">${idcardinfo.phone}</div>
				</div>
				<!--n_ddxqtr  stop -->
				<div class="n_ddxqtr">
					<div class="n_ddxqtdleft">身份证号：</div>
					<div class="n_ddxqtdright">${idcardinfo.card}</div>
				</div>
				<!--n_ddxqtr  stop -->
				<div class="clear"></div>
			</div>
			<!--n_ddxqmk  stop -->
			<div class="n_ddxqmk">
				<div class="n_ddxqmktop">支付方式</div>
				<div class="n_ddxqtr">
					<div class="n_ddxqtdleft">支付方式：</div>
					<div class="n_ddxqtdright">
					<!--
						在线支付(1), 余额支付(2), 优惠券支付(3), 混合支付(4), 货到付款(5),
						支付宝支付(6),微信支付(7),IPS支付(8);
					  -->
						<c:choose>
							<c:when test="${order.paytype==6 }">
								<span>支付宝支付</span>
							</c:when>
							<c:when test="${order.paytype==7 }">
								<span>微信支付</span>
							</c:when>
							<c:when test="${order.paytype==3 }">
								<span>优惠券支付</span>
							</c:when>
							<c:otherwise>
								<span>未支付</span>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<!--n_ddxqmk  stop -->
			<div class="n_ddxqmk" style="border-bottom: none;">
				<div class="n_ddxqmktop">商品清单</div>
				<div class="n_spqd">
					<table>
						<tr>
							<td width="15%">商品编号</td>
							<td width="20%">商品图片</td>
							<td width="35%">商品名称</td>
							<td width="10%">价格（元）</td>
							<td width="10%">使用时间</td>
							<td width="10%">数量</td>
						</tr>
						<c:forEach var="detail" items="${details}">
							<tr>
								<td>${detail.productnum }</td>
								<c:choose>
									<c:when test="${detail.productimg==null}">
										<td><img src=""></td>
									</c:when>
									<c:otherwise>
										<td><img src="<%=path %>${detail.productimg} "></td>
									</c:otherwise>
								</c:choose>
								<td class="text-left"><a href="javascript:void(0);">${detail.productname }</a></td>
								<td><fmt:formatNumber value="${detail.productprice }"
										pattern="0.00" /></td>
								<td><fmt:formatDate value="${detail.usetime }" pattern="yyyy-MM-dd"/></td>
								<td>${detail.productcount }</td>
							</tr>
						</c:forEach>

					</table>
				</div>
				<!--n_spqd  stop -->
				<div class="clear"></div>
			</div>
			<!--n_ddxqmk  stop -->
			<div class="n_ddxqbottom">
				<table>
				    <tr class="n_ddxqyfze">
						<td class="bolder">订单金额：</td>
						<td class="text-left bolder red">￥<fmt:formatNumber
								value="${order.price }" pattern="0.00" /></td>
					</tr>
					<tr class="n_ddxqyfze">
						<td class="bolder">-优惠金额：</td>
						<td class="text-left bolder red">￥<fmt:formatNumber
								value="${order.discount }" pattern="0.00" /></td>
					</tr>
			    	<tr class="n_ddxqyfze">
						<td class="bolder">总商品金额：</td>
						<td class="text-left bolder red">￥<fmt:formatNumber
								value="${totalmoney }" pattern="0.00" /></td>
					</tr>
				</table>

			</div>
			<!-- n_ddxqbottom  stop-->
		</div>
		<!--n_ddxq  stop -->
	</div>
	<!--主要内容 右边结束 -->
</div>