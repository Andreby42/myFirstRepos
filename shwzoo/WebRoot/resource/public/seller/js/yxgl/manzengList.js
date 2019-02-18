﻿//满赠活动列表
var pcount, pindex, psize = 10;
var manzeng = {
    bind: function () {
        $("input[name=btnsearch]").bind("click", function () { manzeng.getlist(1); });
        $("input[name=btnadd]").bind("click", function () { location.href = "yxgl_FullgiftAdd"; });
    },
    getlist: function (index) {
        // 开始时间-开始 开始时间-结束  结束时间-开始  结束时间-结束
        var checkss,detailtype, num, name = "", s1, e1, s2, e2;
        checkss = $("#checkstatus").val();
        detailtype = $("#type_select").val();
        name = $("#name").val();
        num = $("#num").val();
        s1 = $("#starts").val();
        e1 = $("#starte").val();
        s2 = $("#ends").val();
        e2 = $("#ende").val();
        $.ajax({
            url: "/seller/ShopFullgift/getList",
            type: "Post",
            data: {
            	"page": index, "size": psize, "startf": s1, "startt": e1, "endf": s2, "endt": e2, "usetype": detailtype,
                "num": num, "name": name, "status": checkss
            },
            dataType: "json",
            success: function (data) {
                if (data.code < 0) {
                    Dalert(data.desc);
                } else {
                    var listdata = {

                        list: data.data
                    }

                    var html = template('manzenglist', listdata);

                    //html 填充
                    $("#datalist").html(html);
                    pcount = data.maxRow;
                    pindex = data.pageIndex;
                    //绑定删除事件
                    manzeng.unit();
                    //分页
                    $("#pager").html(pagination(pcount, pindex, psize, "manzeng.getlist"));
                }
            },
            error: function () {

            }
        });
    },
    unit: function () {
        $(".del").each(function () {
            $(this).bind("click", manzeng.del);
        });
        $(".ischeck").each(function () {
            $(this).bind("click", manzeng.ischeck);
        });
    },
    del: function () {
        if (confirm("确定要删除吗？")) {
            var id = $(this).attr("data");
            $.ajax({
                url: "/seller/ShopFullgift/deletefullgift",
                type: "Post",
                data: { "id": id },
                dataType: "json",
                success: function (data) {
                    if (data.code == 0) {
                        Dalert(data.desc, 1000);
                        manzeng.getlist(pindex);
                    } else {
                        Dalert(data.desc);
                    }
                }
            })
        }
    },
    ischeck: function () {
        var id = $(this).attr("data");
        $.ajax({
            url: "/seller/ShopFullgift/updateCheck",
            type: "Post",
            data: { "id": id },
            dataType: "json",
            success: function (data) {
                if (data.code == 0) {
                    Dalert(data.desc, 1000);
                    var td_html = "";
                    td_html = "<a href='yxgl_GiftList?id=" + id + "'><span class='shenlan'>赠品列表</span></a>";
                    td_html += "<span style='margin-right:5px;'>";
                    td_html += "<a href='yxgl_FullgiftEdit?id=" + id + "'><span class='shenlan'>编辑</span></a>";
                    td_html += "<span style='margin-right:5px;'>";
                    td_html += "<a href='javascript:void(0);' class='del' data='" + id + "'><span class='shenlan'>删除</span></a>";

                    $("#cz_" + id).html(td_html);
                    $("#check_" + id).html("<span>已审核</span>");
                } else {
                    Dalert(data.desc);
                }
            }
        })
    }
}
//禁用启用
function setStatus(id, ss) {
    $.ajax({
        url: "/seller/ShopFullgift/updateStatus",
        type: "Post",
        data: { "id": id, "status": ss },
        dataType: "json",
        success: function (data) {
            if (data.code < 0) { Dalert(data.desc); }
            else {
                var td_html = "";
                if (ss == 0) {
                    td_html = "<span class='lvs'><a href='#' onclick=setStatus(" + id + ",1)>启用</a></span>";
                }
                else {
                    td_html = "<span class='lvs'><a href='#' onclick=setStatus(" + id + ",0)>禁用</a></span>";
                }
                $("#td_" + id).html(td_html);
            }
        },
        error: function () {
            Dalert("修改状态失败");
        }
    });
}