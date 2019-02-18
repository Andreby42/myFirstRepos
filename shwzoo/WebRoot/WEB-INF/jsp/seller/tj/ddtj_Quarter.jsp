﻿<!-- @{
    ViewBag.Title = "卖家中心-订单季度统计";
    Layout = "~/Areas/Seller/Views/Shared/_Layout_Seller_Center.cshtml";
} -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath }" var="ctx"></c:set>

<script src="${ctx }/resource/public/seller/js/DdTj/ddtj.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        solist.bind(4);
    })
</script>
<div id="container">
<input type="hidden" id="userid" value="${userid}">
    <div class="allcon">
        <div class="position">
            您所在的位置：订单管理 &gt; 订单统计 &gt; 订单季度统计
        </div><!--所在位置信息  结束 -->
        <div class="the-form">
            <span>
                <label>订单季度：</label>
                <input type="text" id="time" onclick="WdatePicker({ dateFmt: 'yyyy-M季度', isQuarter: true, disabledDates: ['....-0[5-9]-..', '....-1[0-2]-..'], startDate: '%y-01-01' })" value="" readonly="readonly" class="Wdate" />
            </span>
            <input class="but-comm" name="search" type="button" value="查询">
        </div>
        <div class="clear"></div>
        <div class="thgl">
            <table>
               <tr class="blank-tr"><td colspan="16"><div style="height:10px;"></div></td></tr>
                <tr id="dd_title">
                    <th width="20%">商品名称</th>
                <th width="15%">订单日期	</th>
                <th width="10%">单价（元）</th>
                <th width="10%">数量</th>
                <th width="15%">已使用（数量/金额）</th>
                <th width="15%">未使用（数量/金额）</th>
                <th width="15%">已退款（数量/金额）</th>
            </tr>
            <tbody id="datalist">
                <script id="ddlist" type="text/html">
                    {{each list as saleorder i}}
                    <tr>
                        <td>{{saleorder.proname}}</td>
                        <td>
                           {{saleorder.datestr}}
                        </td>
                        <td>{{saleorder.proprice | toFixed}}</td>
                        <td>{{saleorder.procount}}</td>
                        <td>{{saleorder.count_YSY}}/{{saleorder.money_YSY | toFixed}}</td>
                        <td>{{saleorder.count_DSY}}/{{saleorder.money_DSY | toFixed}}</td>
                        <td>{{saleorder.count_YTK}}/{{saleorder.money_YTK | toFixed}}</td>
                        </tr>
                        {{/each}}
                    </script>
                </tbody>
            </table>
        </div><!--thgl 表格部分结束 -->
        <div class="clear"></div>
        <div id="pager" class="page">

        </div>
    </div><!--主要内容 右边结束 -->
</div>