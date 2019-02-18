<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="/resource/public/platform/js/zoo/news/news_list.js"></script>
<script type="text/javascript" src="/resource/public/platform/js/zoo/zooCommon.js"></script>
<div class="mainright">
	<!--l_wzmb  开始 -->
	<div>
		<div class="clear"></div>
		<div class="dotted mar35"></div>
			<div class="members-form">
				<form>
					<div style="float: left; padding-right: 20px;"></div>
					<span>标题：<input type="text" id="title" class="inp-seller" /></span>
					</span> <span>状态： <select class="the-form-select-one" name="state" id="state">
							<option value="">请选择</option>
							<option value=1>发布</option>
							<option value=0>未发布</option>
					</select></span> <span><input class="inquire" id="searchtitle" type="button" value="搜索"></span> 
					<span>
							<input class="inquire" onclick="ZooNews.add();" type="button" value="+添加">
					</span>
				</form>
			</div>
			<!--account-form stop -->
			<div class="clear"></div>
			<div class="table-con" id="divshow">
				<table class="data_list">
					<tr id="Advertdata">
						<th width="20%">标题</th>
						<th width="10%">图片</th>
						<th width="10%">创建日期</th>
						<th width="15%">状态</th>
						<th width="20%">操作</th>
					</tr>
					<tbody id="datalist">
						<script id="bannerList" type="text/html">
 						{{each list as bean i}}
                        <tr name="dataTr">
							<td>{{bean.title}}</td>
                            <td><img src="{{bean.titlePic}}" width="70%" alt="加载中......"/></td>
							<td>{{bean.createTimeStr}}</td>
							{{if bean.state==1}}
                   		 	<td>
								发布
                   			</td>
                    		{{else }}
                   			 <td>
                        	            未发布
                   			 </td>
                    		{{/if}}
							<td class="member-operate">
                              {{if bean.state==1}}
                               <span class="lvs shenlan"">
							     <a data-id="{{bean.id}}" data-status="{{bean.state}}" class="set"  href="javascript:void(0);">撤销</a>
							   </span>
                              {{else }} 
                              <span class="lvs shenlan">
								 <a data-id="{{bean.id}}" data-status="{{bean.state}}" class="set"  href="javascript:void(0);">发布</a>
							  </span>     
							  <span class="cxtt shenlan" data-id="{{bean.id}}">编辑</span>
                              <span class="delete shenlan" data-id="{{bean.id}}">删除</span>
                               {{/if}} 
                            </td>
                        </tr>
                        {{/each}}
                    </script>
					</tbody>
				</table>
			</div>
			<div class="clear"></div>
			<div class="page" id="pager"></div>
			<!--page stop -->
		</div>
	</div>
	<!--l_wzmb  结束 -->
</div>
<!--mainright stop -->
<script type="text/javascript">
	$(document).ready(function() {
		//查询
		$("#searchtitle").bind("click",function(){
			ZooNews.getAll(1);
		});
		//启用禁用
		$("body").on("click", ".set", function() {
			ZooNews.updateStatus(this);
		});

		// 删除
	    $("body").on("click", ".delete", function() {
	        var id = $(this).attr("data-id");
	        ZooNews.delCheck(id);
	    });

	    // 编辑
	    $("body").on("click", ".cxtt", function() {
	    	var id = $(this).attr("data-id");
	        location.href = "/zoo/news/listedit?id=" + id;
	    });
		//初始化数据
		ZooNews.getAll(1)
	})
</script>
