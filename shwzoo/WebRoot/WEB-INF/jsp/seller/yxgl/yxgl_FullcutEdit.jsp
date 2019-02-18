<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src="${pageContext.request.contextPath }/resource/public/seller/js/yxgl/goods_class.js"></script>
<script src="${pageContext.request.contextPath }/resource/public/seller/js/yxgl/manjianSave.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var ut = ${data.usetype };
		if (ut == 1) {
			Class.unit(Class.callback);
			activity.Init();
		}
		$("#acttype").val(ut);
	});
	function formCancel() {

		location.href = "yxgl_FullcutList";

	}
</script>
<div class="container">
	<div class="allcon">
		<div class="position">您所在的位置：营销管理 &gt; 满减活动 &gt; 修改满减活动</div>
		<!--所在位置信息  结束 -->
		<div class="zhgl-con">
			<div class="zhgl-con-top">
				<div class="zhgl-con-top-title">添加满减活动</div>
			</div>
			<!--zhgl-con-top  stop -->
			<form id="form" method="post">
				<div class="zhgl-con-con">
					<table>
						<tr>
							<td class="xjdpzzh-left"><span class="red">*</span>编号：</td>
							<td><input id="num" class="text-inp-big"
								style="width: 150px;" name="num" readonly="readonly" type="text"
								value="${data.actnum }"> <input type="hidden"
								value="${data.id }" name="id" /></td>
						</tr>
						<tr>
							<td class="xjdpzzh-left"><span class="red">*</span>名称：</td>
							<td><input id="name" class="text-inp-big" name="name"
								type="text" value="${data.actname }"></td>
						</tr>
						<tr>
							<td class="xjdpzzh-left"><span class="red">*</span>满减类型：</td>
							<td><select name="acttype" id="acttype" class="sel-form"
								onchange="activity.TypeChange()">
									<option value="0">针对金额</option>
									<option value="1">针对商品</option>
							</select></td>
						</tr>
						<c:choose>
							<c:when test="${data.usetype==0 }">
								<tr>
									<td class="xjdpzzh-left"><span class="red">*</span>满足条件：</td>
									<td><input id="fullprice" class="text-inp-big"
										style="width: 150px;" name="fullprice" type="text"
										value="<fmt:formatNumber value="${data.fullvalue}" pattern="0.00"/>">&nbsp;元
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tbody id="divsp">
									<tr>
										<td class="xjdpzzh-left"><span class="red">*</span>商品分类：</td>
										<td>
											<div class="tjcpxx-con-form1">
												<input type="hidden" value="${data.spuid }" name="spuid" />
												<input type="hidden" value="${fid }" id="fid" /> <input
													type="hidden" value="${sid }" id="sid" /> <input
													type="hidden" value="${tid }" id="tid" /> <select
													name="firstID" id="firstID" class="sel_allmost"
													onchange="Class.firstChange(Class.callback, 'fc')">
													<option value="0" id="defaultfc" selected>无</option>
													<script id="flist" type="text/html">
                                                    {{each list as fclass i}}
                                                    <option value="{{fclass.id}}">{{fclass.name}}</option>
                                                    {{/each}}
                                                </script>
												</select>--> <select name="secondID" id="secondID"
													class="sel_allmost"
													onchange="Class.firstChange(Class.callback, 'sc')">
													<option value="0" id="defaultsc" selected>无</option>
													<script id="slist" type="text/html">
                                                    {{each list as sclass i}}
                                                    <option value="{{sclass.id}}">{{sclass.name}}</option>
                                                    {{/each}}
                                                </script>
												</select>--> <select name="thirdID" id="thirdID" class="sel_allmost"
													onchange="activity.getSPU()">
													<option value="0" id="defaulttc" selected>无</option>
													<script id="tlist" type="text/html">
                                                    {{each list as tclass i}}
                                                    <option value="{{tclass.id}}">{{tclass.name}}</option>
                                                    {{/each}}
                                                </script>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td class="xjdpzzh-left"><span class="red">*</span>标准商品：</td>
										<td>
											<div class="tjcpxx-con-form1">
												<input id="select_spu" type="text" class="text-inp-big"
													value="${spuname }" data="${data.spuid }" />
											</div>
											<div style="margin-left: 13px;">
												<ul>
													<script id="select_spulist" type="text/html">
                                                    {{each list as spu i}}
                                                    <li data="{{spu.id}}">{{spu.name}}</li>
                                                    {{/each}}
                                                </script>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td class="xjdpzzh-left"><span class="red">*</span>件数：</td>
										<td><input id="count" class="text-inp-big"
											style="width: 150px;" name="count" type="text"
											value="${data.count }">&nbsp;件</td>
									</tr>
								</tbody>
							</c:otherwise>
						</c:choose>
						<tr>
							<td class="xjdpzzh-left"><span class="red">*</span>减免金额：</td>
							<td><input id="subprice" class="text-inp-big"
								style="width: 150px;" name="subprice" type="text"
								value="<fmt:formatNumber value="${data.subvalue}" pattern="0.00"/>">&nbsp;元
							</td>
						</tr>
						<tr>
							<td class="xjdpzzh-left"><span class="red">*</span>开始时间：</td>
							<td><input type="text" name="start" id="start" class="Wdate"
								onclick="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'end\')}' })"
								value="${data.starttimestr}" readonly="readonly" /></td>
						</tr>
						<tr>
							<td class="xjdpzzh-left"><span class="red">*</span>结束时间：</td>
							<td><input type="text" name="end" id="end" class="Wdate"
								onclick="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'start\')}' })"
								value="${data.endtimestr}" readonly="readonly" /></td>
						</tr>
						<tr>
							<td class="xjdpzzh-left"><span class="red">*</span>数量：</td>
							<td><input id="stock" class="text-inp-big"
								style="width: 150px;" name="stock" type="text"
								value="${data.stock}">&nbsp;件</td>
						</tr>
					<%-- 	<tr>
							<td class="xjdpzzh-left"><span class="red">*</span>手机专享：</td>
							<td><c:choose>
									<c:when test="${data.isphone==0}">
										<input name='isphone' checked type='radio' value='0'>
										<span>否</span>
										<span class='marrig35'></span>
										<input name='isphone' type='radio' value='1'>
										<span>是</span>
									</c:when>
									<c:otherwise>
										<input name='isphone' type='radio' value='0'>
										<span>否</span>
										<span class='marrig35'></span>
										<input name='isphone' checked type='radio' value='1'>
										<span>是</span>
									</c:otherwise>
								</c:choose></td>
						</tr> --%>
						
						<tr>
                        	<td class="xjdpzzh-left" ><span class="red">*</span>使用平台：</td>
                        	<td>
                        		  <c:choose>
                            <c:when test="${data.usesite != null}">
										<c:choose>
											<c:when test="${fn:indexOf(data.usesite,'1')>=0}">
												<input name="useplatform" type="checkbox" checked value="1">
												<span>pc端</span>
											</c:when>
											<c:otherwise>
												<input name="useplatform" type="checkbox" value="1">
												<span>pc端</span>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${fn:indexOf(data.usesite,'2')>=0}">
												<input name="useplatform" type="checkbox" checked value="2">
												<span>app端</span>
											</c:when>
											<c:otherwise>
												<input name="useplatform" type="checkbox" value="2">
												<span>app端</span>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${fn:indexOf(data.usesite,'3')>=0}">
												<input name="useplatform" type="checkbox" checked value="3">
												<span>wap端</span>
											</c:when>
											<c:otherwise>
												<input name="useplatform" type="checkbox" value="3">
												<span>wap端</span>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${fn:indexOf(data.usesite,'4')>=0}">
												<input name="useplatform" type="checkbox" checked value="4">
												<span>微信端</span>
											</c:when>
											<c:otherwise>
												<input name="useplatform" type="checkbox" value="4">
												<span>微信端</span>
											</c:otherwise>
										</c:choose>

									</c:when>
                            <c:otherwise>
                               <input name='useplatform' type='checkbox' value='1'><span>pc端</span>
                                <span class='marrig35'></span>
                                <input name='useplatform' type='checkbox' value='2'><span>app端</span>
                                <span class='marrig35'></span>
                                <input name='useplatform' type='checkbox' value='3'><span>wap端</span>
                                <span class='marrig35'></span>
                                <input name='useplatform' type='checkbox' value='4'><span>微信端</span>
                            </c:otherwise>
                            </c:choose>
                        	</td>
         				</tr>
						<tr>
							<td class="xjdpzzh-left"><span class="red">*</span>状态：</td>
							<td><c:choose>
									<c:when test="${data.status==0}">
										<input name='status' checked type='radio' value='0'>
										<span>启用</span>
										<span class='marrig35'></span>
										<input name='status' type='radio' value='1'>
										<span>禁用</span>
									</c:when>
									<c:otherwise>
										<input name='status' type='radio' value='0'>
										<span>启用</span>
										<span class='marrig35'></span>
										<input name='status' checked type='radio' value='1'>
										<span>禁用</span>
									</c:otherwise>
								</c:choose></td>
						</tr>
						<tr>
							<td class="xjdpzzh-left"></td>
							<td><input name="Save" type="button" value="保存" class="big-but"> 
								<input id="action" type="hidden" value="updatefullcut"> 
								<input name="" type="button" value="取消" onclick="formCancel()" class="big-but-huise">
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
</div>
