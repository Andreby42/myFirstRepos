<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="mainright">
	<div class="l_wzmb">
		<div class="l_wzmbtop">
			<ul>
				<li class="sj_hover"><a href="javascript:void(0);">查看团购</a><span
					class="sj-img"></span></li>

			</ul>
		</div>
		<!--l_wzmbtop   stop -->
		<div class="tjcpxx">

			<div class="tjcpxx-con">
				<div class="tjcpxx-con-con">
					<form id="form" method="post">
						<div class="tjcpxx-con-mk1">
							<div class="tjcpxx-con-form-title">
								<label><span class="red marrig5">*</span>标题：</label>
							</div>
							<div class="tjcpxx-con-form1">
								<input id="title" class="tjcpxx-fprm-inp" name="title" disabled
									type="text" value="${data.title }"> 
							</div>
						</div>
						<div class="tjcpxx-con-mk1">
							<div class="tjcpxx-con-form-title">
								<label><span class="red marrig5">*</span>现价：</label>
							</div>
							<div class="tjcpxx-con-form1">
								<input id="cprice" class="tjcpxx-fprm-inp" style="width: 150px;" disabled
									name="cprice" type="text"
									value="<fmt:formatNumber value="${data.cprice}" pattern="0.00"/>">&nbsp;元
							</div>
						</div>
						<div class="tjcpxx-con-mk1">
							<div class="tjcpxx-con-form-title">
								<label><span class="red marrig5">*</span>原价：</label>
							</div>
							<div class="tjcpxx-con-form1">
								<input id="oprice" class="tjcpxx-fprm-inp" style="width: 150px;" disabled
									name="oprice" type="text"
									value="<fmt:formatNumber value="${data.oprice}" pattern="0.00"/>">&nbsp;元
							</div>
						</div>
						<!--   @*数量*@ -->
						<div class="tjcpxx-con-mk1">
							<div class="tjcpxx-con-form-title">
								<label><span class="red marrig5">*</span>数量：</label>
							</div>
							<div class="tjcpxx-con-form1">
								<input id="stock" class="tjcpxx-fprm-inp" style="width: 150px;" disabled
									name="stock" type="text" value="${data.stock}">
							</div>
						</div>
						<!--   @*类型*@ -->
						<div class="tjcpxx-con-mk1">
							<div class="tjcpxx-con-form-title">
								<label><span class="red marrig5">*</span>类型：</label>
							</div>
							<div class="tjcpxx-con-form1">
								<select id="type" name="type" disabled class="the-form-select">
									<option value="0">代金券</option>
									<option value="1">打折卡</option>
									<option value="2">活动优惠</option>
								</select>
							</div>
						</div>
						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">
								<label><span class="red marrig5">*</span>开始时间：</label>
							</div>
							<div class="tjcpxx-con-form">
								<input type="text" name="starttime" id="starttime"
									class="Wdate2"
									onclick="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'endtime\')}' })"
									value="${data.startstr}" readonly="readonly" />
							</div>
						</div>
						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">
								<label><span class="red marrig5">*</span>过期时间：</label>
							</div>
							<div class="tjcpxx-con-form">
								<input type="text" name="endtime" id="endtime" class="Wdate2"
									onclick="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'starttime\')}' })"
									value="${data.endstr}" readonly="readonly" />
							</div>
						</div>

						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">
								<label><span class="red marrig5"></span>详细页简介：</label>
							</div>
							<div class="tjcpxx-con-form">
								<input id="detaildesc" class="tjcpxx-fprm-inp" name="detaildesc" disabled
									type="text" value="${data.detaildesc}">
							</div>
						</div>
						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">
								<label><span class="red marrig5"></span>列表页简介：</label>
							</div>
							<div class="tjcpxx-con-form">
								<input id="listdesc" class="tjcpxx-fprm-inp" name="listdesc" disabled
									type="text" value="${data.listdesc}">
							</div>
						</div>
						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">
								<label>内容详情：</label>
							</div>
							<div class="tjcpxx-con-form">
								<textarea id="content1" name=""
									style="width: 450px; height: 200px; visibility: hidden;"></textarea>
								<input class="tjcpxx-fprm-inp" name="content" type="hidden"
									value="${data.content}">
							</div>
						</div>

						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">
								<label>购买须知：</label>
							</div>
							<div class="tjcpxx-con-form">
								<textarea id="content2" name=""
									style="width: 450px; height: 200px; visibility: hidden;"></textarea>
								<input class="tjcpxx-fprm-inp" name="buynotes" type="hidden"
									value="${data.buynotes}">
							</div>
						</div>
						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">随时退：</div>
							<div class="tjcpxx-con-form huise">
								<c:choose>
									<c:when test="${data.isanytime==true}">
										<input name='isanytime' checked type='radio' value='true'>
										<span>是</span>
										<span class='marrig35'></span>
										<input name='isanytime' type='radio' value='false'>
										<span>否</span>
									</c:when>
									<c:otherwise>
										<input name='isanytime' type='radio' value='true'>
										<span>是</span>
										<span class='marrig35'></span>
										<input name='isanytime' checked type='radio' value='false'>
										<span>否</span>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">预约：</div>
							<div class="tjcpxx-con-form huise">
								<c:choose>
									<c:when test="${data.isbook==true}">
										<input name='isbook' checked type='radio' value='true'>
										<span>是</span>
										<span class='marrig35'></span>
										<input name='isbook' type='radio' value='false'>
										<span>否</span>
									</c:when>
									<c:otherwise>
										<input name='isbook' type='radio' value='true'>
										<span>是</span>
										<span class='marrig35'></span>
										<input name='isbook' checked type='radio' value='false'>
										<span>否</span>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">过期退：</div>
							<div class="tjcpxx-con-form huise">
								<c:choose>
									<c:when test="${data.isexpired==true}">
										<input name='isexpired' checked type='radio' value='true'>
										<span>是</span>
										<span class='marrig35'></span>
										<input name='isexpired' type='radio' value='false'>
										<span>否</span>
									</c:when>
									<c:otherwise>
										<input name='isexpired' type='radio' value='true'>
										<span>是</span>
										<span class='marrig35'></span>
										<input name='isexpired' checked type='radio' value='false'>
										<span>否</span>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						
						<div class="tjcpxx-con-mk">
                            <div class="tjcpxx-con-form-title"><span class="red">*</span>使用平台：</div>
                            <div class="tjcpxx-con-form">
                              <c:choose>
                            <c:when test="${data.usesite != null}">
										<%-- <c:choose>
											<c:when test="${fn:indexOf(data.usesite,'1')>=0}">
												<input name="useplatform" type="checkbox" checked value="1">
												<span>pc端</span>
											</c:when>
											<c:otherwise>
												<input name="useplatform" type="checkbox" value="1">
												<span>pc端</span>
											</c:otherwise>
										</c:choose> --%>
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
                              
                            </div>
                        </div>
	                    <div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">&nbsp;</div>
							<div class="tjcpxx-con-form huise">
								<c:choose>
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
								</c:choose>
							</div>
						</div>
						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title"></div>
							<div class="tjcpxx-con-form huise">
								
<input class="preserve-inp_hs" name="" type="button" value="返回" onclick="formCancel()">
							</div>
						</div>
						<!--tjcpxx-con-mk stop -->


					</form>
				</div>
			</div>
		</div>
		<!--tjcpxx-con stop -->
	</div>
</div>
<script
	src="${pageContext.request.contextPath }/resource/public/platform/js/yxgl/groupbuySave.js"></script>
<script
	src="${pageContext.request.contextPath }/resource/public/commonjs/kindeditor-4.1.10/kindeditor-min.js"></script>
<script
	src="${pageContext.request.contextPath }/resource/public/commonjs/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script type="text/javascript">
	function formCancel() {
		location.href = "yxgl_GroupBuyList";
	}
	var editor, editor1;
	KindEditor.ready(function(K) {
		//K.create('#content1');
		//取消功能 打印，插入模板，插入代码，插入flash，插入视频，插入表情，锚点
		editor = K.create('#content1', {
			items : [ 'source', 'undo', 'redo', 'preview', 'cut', 'cpoy',
					'paste', 'plainpaste', 'wordpaste', 'justifyleft',
					'justifycenter', 'justifyright', 'justifyfull',
					'insertorderedlist', 'insertunorderedlist', 'indent',
					'outdent', 'subscript', 'superscript', 'clearhtml',
					'quickformat', 'selectall', 'fullscreen', 'formatblock',
					'fontname', 'fontsize', 'forecolor', 'hilitecolor', 'bold',
					'italic', 'underline', 'strikethrough', 'lineheight',
					'removeformat', 'image', 'multiimage', 'insertfile',
					'table', 'hr', 'baidumap', 'pagebreak', 'link', 'unlink' ],
			uploadJson : "/app/api/img/upload?relationtype=1&iskdr=1"
		});
		editor1 = K.create('#content2', {
			items : [ 'source', 'undo', 'redo', 'preview', 'cut', 'cpoy',
					'paste', 'plainpaste', 'wordpaste', 'justifyleft',
					'justifycenter', 'justifyright', 'justifyfull',
					'insertorderedlist', 'insertunorderedlist', 'indent',
					'outdent', 'subscript', 'superscript', 'clearhtml',
					'quickformat', 'selectall', 'fullscreen', 'formatblock',
					'fontname', 'fontsize', 'forecolor', 'hilitecolor', 'bold',
					'italic', 'underline', 'strikethrough', 'lineheight',
					'removeformat', 'image', 'multiimage', 'insertfile',
					'table', 'hr', 'baidumap', 'pagebreak', 'link', 'unlink' ],
			uploadJson : "/app/api/img/upload?relationtype=1&iskdr=1"
		});
		if ($("input[name=content]").val() != ""
				&& $("input[name=content]").val() != undefined) {
			editor.html($("input[name=content]").val());
		}
		if ($("input[name=buynotes]").val() != ""
				&& $("input[name=buynotes]").val() != undefined) {
			editor1.html($("input[name=buynotes]").val());
		}
	});
</script>