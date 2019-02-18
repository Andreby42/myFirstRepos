﻿//规格类型管理

$(document).ready(function() {
	// 全选
	$("#ch_All").click(function() {
		if ($(this).prop("checked")) {
			$("input[name=ck_list]").prop("checked", true);
		} else {
			$("input[name=ck_list]").prop("checked", false);
		}
	});
	// 全选删除
	$("#delAll").click(function() {
		var idList = "";
		$('input[name="ck_list"]:checked').each(function() {
			var id = $(this).val();
			idList += id + ",";
		});
		if (idList != "") {
			var d = dialog({
				title : '提示',
				content : '确认要删除选取规格吗？',
				okValue : '确定',
				cancelValue : '取消',
				ok : function() {
					idList = idList.substring(0, idList.length - 1);
					$.ajax({
						type : "post",
						url : "/platform/SpecsType/DeleteList",
						dataType : "json",
						data : {
							'ids' : idList
						},
						success : function(rsl) {
							if (rsl.code == 0) {
								Dalert(rsl.desc, "", refresh);// window.location.reload();
							} else {
								Dalert(rsl.desc);
							}
						},
						error : function(e) {
							Dalert(e.statusText);
						}
					})
				},
				cancel : function() {
				}
			}).show();

		} else {
			var d = dialog({
				title : '提示',
				content : '还没有选取哦',
				okValue : '确定',
				ok : function() {
				}
			}).show();
		}
	});
	// 全选更新序号
	$("#updAll").click(function() {
		var idList = "";
		var orderList = ""
		$('input[name="ck_list"]:checked').each(function() {
			var id = $(this).val();
			if (id != "" && id != undefined) {
				var or = $("#ob_" + id).val();
				if (or != "" && or != undefined) {
					orderList += or + ",";
					idList += id + ",";
				}
			}
		});
		if (idList != "" && orderList != "") {
			idList = idList.substring(0, idList.length - 1);
			orderList = orderList.substring(0, orderList.length - 1);
			$.ajax({
				type : "post",
				url : "/platform/SpecsType/UpdateOrderList",
				dataType : "json",
				data : {
					ids : idList,
					orderbys : orderList
				},
				success : function(rsl) {
					if (rsl.code == 0) {
						Dalert(rsl.desc, "", refresh);// window.location.reload();
					} else {
						Dalert(rsl.desc);
					}
				},
				error : function(e) {
					Dalert(e.statusText);
				}
			})

		} else {
			var d = dialog({
				title : '提示',
				content : '还没有选取哦',
				okValue : '确定',
				ok : function() {
				}
			}).show();
		}
	});
});
var pcount, pindex, psize = size_product;
var claid = "";
// 页面加载数据
var type = {
	bind : function(index) {
		claid = $("#firstID").val();
		$.ajax({
			url : "/platform/SpecsType/GetList",
			type : "Post",
			data : {
				'index' : index,
				'size' : psize,
				'classid' : $("#firstID").val(),
				'classid2':$("#secondID").val(),
				'classid3':$("#thirdID").val()
			},
			dataType : "json",
			success : function(data) {
				if (data.code < 0) {
					Dalert(data.desc);
				} else {
					var listdata = {

						list : data.data
					}

					var html = template('typelist', listdata);

					// html 填充
					$("#datalist").html(html);
					// 加载table样式：改变偶数行背景色，鼠标移动时背景色
					init();
					pcount = data.maxRow;
					pindex = data.pageIndex;
					type.unit();
					// 分页
					$("#pager").html(
							pagination(pcount, pindex, psize, "pagelist"));
					$("input[name=ch_All]").attr("checked", false);
				}
			},
			error : function() {
				// Dalert("数据加载失败");
			}
		});
	},
	unit : function() {
		$(".del").each(function() {
			$(this).bind("click", type.del);
		});
		$(".set").each(function() {
			$(this).bind("click", type.set);
		});
	},
	del : function() {
		var id = $(this).attr("data-id");
		if (confirm('确定将此记录删除?')) {
			$.ajax({
				url : "/platform/SpecsType/Delete",
				type : "Post",
				data : {
					'id' : id
				},
				dataType : "json",
				success : function(data) {
					if (data.code < 0) {
						Dalert(data.desc);
					} else {
						type.bind(pindex);
					}
				},
				error : function() {
					Dalert("删除失败");
				}
			});
		}
	},
	set : function() {
		var typeid = $(this).attr("data-id");
		var ss = $(this).attr("data-status");
		$
				.ajax({
					url : "/platform/SpecsType/UpdateStatus",
					type : "Post",
					data : {
						'id' : typeid,
						'status' : ss
					},
					dataType : "json",
					success : function(data) {
						if (data.code < 0) {
							Dalert(data.desc);
						} else {
							var td_html = "";
							if (ss == "0") {
								td_html = "<span class='lvs'><a data-id='"
										+ typeid
										+ "'  data-status='1' class='set' href='javascript:void(0);'>启用</a></span>";
							} else {
								td_html = "<span class='lvs'><a data-id='"
										+ typeid
										+ "'  data-status='0' class='set' href='javascript:void(0);'>禁用</a></span>";
							}
							$("#td_" + typeid).html(td_html);
							type.unit;
						}
					},
					error : function() {
						Dalert("修改状态失败");
					}
				});
	}
}


// 按钮单击事件绑定
$(function() {
	$(".chaxun").bind("click", typeadd);
	$("input[name=Save]").bind("click", Save);
	$("input[name=status]").bind("click", status);
	$("input[name=btnsearch]").bind("click", function() {
		type.bind(1);
	});
})

// 分页回调函数
function pagelist(index) {
	type.bind(index)
}

// 添加
function typeadd() {
	self.location = "specstype_add";
}
// 状态修改
function setStatus(typeid, ss) {
	$
			.ajax({
				url : "/platform/SpecsType/UpdateStatus",
				type : "Post",
				data : {
					'id' : typeid,
					'status' : ss
				},
				dataType : "json",
				success : function(data) {
					if (data.code < 0) {
						Dalert(data.desc);
					} else {
						var td_html = "";
						if (ss == "0") {
							td_html = "<span class='lvs'><a href='#' onclick=setStatus("
									+ typeid + ",1)>启用</a></span>";

						} else {
							td_html = "<span class='lvs'><a href='#' onclick=setStatus("
									+ typeid + ",0)>禁用</a></span>";
						}
						$("#td_" + typeid).html(td_html);
					}
				},
				error : function() {
					Dalert("修改状态失败");
				}
			});
}
// 修改排序
function setOrder(typeid, ob) {
	var obtext = $("#" + ob).val();
	$.ajax({
		url : "/platform/SpecsType/UpdateOrder",
		type : "Post",
		data : {
			'id' : typeid,
			'orderby' : obtext
		},
		dataType : "json",
		success : function(data) {
			Dalert(data.desc);
		},
		error : function() {
			Dalert("修改排序失败");
		}
	});
}
// 保存
function Save() {
	var action = $("#type_action").val();
	if (formSubmit()) {
		// 防止重复提交 点击保存后隐藏按钮
		$("input[name='Save']").hide();
		$.ajax({
			url : "/platform/Specstype/" + action,
			type : "Post",
			data : $("#form").serialize(),
			dataType : "json",
			success : function(data) {
				if (data.code == 0) {
					Dalert("保存成功！", "", backhref);
				} else {
					$("input[name='Save']").show();
					Dalert(data.desc);
				}
			},
			error : function() {
			}
		});
	}
}
// 保存前参数验证
function check() {
	return $("#form").validate({
		rules : {
			name : {
				required : true,
				stringCheck : true,
				rangelength : [ 1, 100 ]
			},
			orderby : {
				required : true,
				digits : true
			},
		},
		message : {
			name : {
				required : "类型名称不可为空"
			},
			orderby : {
				required : "排序不能为空",
				digits : "必须输入整数"
			}
		}
	});
}
function formSubmit() {
	if (check().form()) {
		if ($("#thirdID").val() != 0) {
			$("#ClassID").val($("#thirdID").val());
		}else if ($("#secondID").val()!= 0) {
			$("#ClassID").val($("#secondID").val());
		}else if ($("#firstID").val()!= 0) {
			$("#ClassID").val($("#firstID").val());
		} else {
			Dalert("请选择商品分类");
			$("#thirdID").focus();
			return false;
		}
		if ($("#ClassID").val() == 0) {
			return false;
		}
		return true;
	}

}
// 返回刷新
function backhref() {
	window.location.href = '/platform/product/specstype_list';
}
function refresh() {
	window.location.reload();
}