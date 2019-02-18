﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath }" var="ctx"></c:set>
<script src="${ctx }/resource/public/commonjs/MrYangUtil.js"></script>
<div class="mainright">
    <!--l_wzmb  开始 -->
    <div class="l_wzmb fix">
        <div class="l_wzmbtop fix">
            <ul>
                <li class="sj_hover"><a href="#">客户明细</a><span class="sj-img"></span></li>
            </ul>
        </div><!--l_wzmbtop   stop -->
        <!-------------pad10  begin --------------->
        <div class="pad10 fix">
            <div class="l_khqyfb FastSelect">
                <p>
                    快速选择：
                    <a href="javascript:void(0);" data-val="0" class="CurrentDate">本周</a>
                    <a href="javascript:void(0);" class="PreviousWeek" data-val="1">上周</a>
                    <a href="javascript:void(0);" data-val="2" class="CurrentMonth">本月</a>
                    <a href="javascript:void(0);" class=" PreviousMonth " data-val="3">上月</a>
                </p>
                <div style="margin-top:10px;"></div>
                <span>所属店铺：</span><span><input id="select_shop" type="text" class="inp-seller" style="margin-top:0px;" /></span>
                <div style="margin-left:12px;">
                    <ul>
                        <script id="select_shoplist" type="text/html">
                            {{each list as shop i}}
                            <li data="{{shop.id}}">{{shop.name}}</li>
                            {{/each}}
                        </script>
                    </ul>
                </div>

                <div class="mt15">
                    选择日期：
                    <input name="" type="text" class="inp-seller Wdate" onclick="WdatePicker({ dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endtime\')}' })" value="" readonly="readonly" id="starttime">
                    -
                    <input name="" type="text" id="endtime" class="inp-seller mr10 Wdate" onclick="WdatePicker({ dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'starttime\')}' })">
                    <input class="chaxun" id="search" type="button" value="查看">
                </div>
            </div>
            <div id="alldata">
                <script id="alllist" type="text/html">
                    <div class="l_zlb all">数据汇总</div>
                    <div class="l_sjhzxsmx fix">
                        <ul>
							 {{if list.ordercount >0}}
 							 <li>下单量：<span class="red">{{list.ordercount}}</span></li>
							 {{else}}
                             <li>下单量：<span class="red">0</span></li>
							 {{/if}}

							 {{if list.buyercount >0}}
 							  <li>下单客户数：<span class="red">{{list.buyercount}}</span></li>
							 {{else}}
                             <li>下单客户数：<span class="red">0</span></li>
							 {{/if}}

							 {{if list.skucount >0}}
 							  <li>下单商品件数：<span class="red">{{list.skucount}}</span></li>
							 {{else}}
                            <li>下单商品件数：<span class="red">0</span></li>
							 {{/if}}

							 {{if list.ordermoney >0}}
 							  <li>下单金额：<span class="red">{{list.ordermoney | toFixed}}</span></li>
							 {{else}}
                            <li>下单金额：<span class="red">0</span></li>
							 {{/if}}
                           
                            {{if list.buyercount >0}}
                            <li>客单价：<span class="red">{{list.buyerbymoney | toFixed}}</span></li>
                            <li>客单量：<span class="red">{{list.buyerbycount | toFixed}}</span></li>
                            {{else}}
                            <li>客单价：<span class="red">{{0 | toFixed}}</span></li>
                            <li>客单量：<span class="red">0</span></li>
                            {{/if}}
                        </ul>
                    </div><!--l_sjhzxsmx  stop -->
                    <div class="l_zlb detail">客户明细</div>
                    <div class="l_zlbcon fix l_jymxbtab">
                        <table>
                            <tr>
                                <th>客户名称</th>
                                <th>下单量</th>
                                <th>下单商品件数</th>
                                <th>下单金额</th>
                                <th>平均下单金额</th>
                            </tr>
                            {{each list.proDetail as buyerlist i}}
                            <tr>
                                <td>{{buyerlist.username}}</td>
                                <td>{{buyerlist.ordercount}}</td>
                                <td>{{buyerlist.skucount}}</td>
                                <td>{{buyerlist.ordermoney | toFixed}}</td>
                                <td>{{buyerlist.buyerbymoney | toFixed}} </td>
                            </tr>
                            {{/each}}
                        </table>
                    </div><!--l_zlbcon  stop -->
                    <div class="clear"></div>
                    <div id="pager" class="page">

                    </div>
                </script>
            </div>

        </div>
        <!-------------pad10  stop --------------->
    </div>
    <!--l_wzmb  结束 -->
</div>
<!-- <script src="~/js/MrYangUtil.js"></script> -->
<script type="text/javascript">
    $(function () {
        var date = new Date();
        $("#starttime").val(date.Format());
        $("#endtime").val(date.Format());
        getlist(1);
        $(".FastSelect").children().find("a").each(function (i) {
            var $obj = $(this);
            $obj.bind("click", function () { FastSelect($obj.attr("data-val")) });
        });
        $("#search").bind("click", function () { getlist(1); });
        autoxl.bind("select_shop", getShopStartwithName, true);
    });
    function FastSelect(type) {
        var my = new MrYangUtil(); var date;
        switch (parseInt(type)) {
            case 0://本周
                date = my.getCurrentWeek();
                break;
            case 1://上周
                date = my.getPreviousWeek();
                break;
            case 2://本月
                date = my.getCurrentMonth();
                break;
            case 3://上月
                date = my.getPreviousMonth();
                break;
        }
        $("#starttime").val(date[0].Format());
        $("#endtime").val(date[1].Format());
        $(".FastSelect").children().find("a").each(function (i) {
            var $obj = $(this);
            if ($obj.attr("data-val") == type) {
                $obj.addClass("red");
            } else {
                $obj.removeClass("red");
            }
        });
    }
    /*
callback 成功 有数据时 调的方法
event 事件
*/
    function getShopStartwithName(callback, event) {
        var name = $("#select_shop").val();
        if (event)
            name += String.fromCharCode(event.keyCode);
        $.ajax({
            url: "/platform/shop/queryShopByLikeName",
            type: "Post",
            data: { "name": name },
            dataType: "json",
            success: function (data) {
                if (data.code == 0) {
                    var listdata = {
                        list: data.data
                    }
                    var html = template('select_shoplist', listdata);

                    if (callback) {
                        callback(html);
                    }
                } else {
                    Dalert(data.desc);
                }
            },
            error: function () {

            }
        });
    }
    function getlist(index) {
        var psize = 10;
        var timef = $("#starttime").val();
        var timet = $("#endtime").val();
        if (timef == undefined || timef == "" || timet == undefined || timet == "") {
            alert("请选择时间段！");
        } else {
            $.ajax({
                url: "/platform/analysis/getbuyerDetail",
                type: "Post",
                data: {
                	"starttime": timef, 
                	"endtime": timet, 
                	"shopid": $("#select_shop").attr("data"),
                	"page": index,
                	"size": psize},
                dataType: "json",
                success: function (data) {
                    if (data.code < 0) {
                        Dalert(data.desc);
                    } else {
                        var listdata = {
                            list: data.data
                        }
                        var html = template('alllist', listdata);
                        //html 填充
                        $("#alldata").html(html);
                        datafromto();
                        pcount = data.maxRow;
                        pindex = data.pageIndex;
                       //分页
                       $("#pager").html(pagination(pcount, pindex, psize, "getlist"));
                    }
                },
                error: function () {

                }
            });
        }
    }
    function datafromto() {
        $(".all").html("数据汇总（" + $("#starttime").val() + "至" + $("#endtime").val() + ")");
        $(".detail").html("客户明细（" + $("#starttime").val() + "至" + $("#endtime").val() + ")");
    }
</script>