<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/decorators/getFileUrl.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set value="${pageContext.request.contextPath }" var="ctx"></c:set>
<script type="text/javascript" src="${ctx }/resource/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="${ctx }/resource/public/platform/js/news/slistedit.js"></script>
<script type="text/javascript"
	src="${ctx }/resource/public/platform/js/news/news_class.js"></script>
<script
	src="${ctx}/resource/public/commonjs/kindeditor-4.1.10/kindeditor-min.js"></script>
<script
	src="${ctx}/resource/public/commonjs/kindeditor-4.1.10/lang/zh_CN.js"></script>

<script type="text/javascript">
	var editor;
	/*var basepath=getRootPath();  */
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
			uploadJson : "/app/api/img/upload?relationtype=40&iskdr=1"
		});
	});
</script>
<div class="mainright">
	<div class="l_wzmb">
		<div class="l_wzmbtop">
			<ul>
				<li class="sj_hover"><a href="javascript:void(0);">添加公告</a><span
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
								<input class="tjcpxx-fprm-inp" name="name" type="text"
									value="${vo.title }">
							</div>
						</div>
						<div class="tjcpxx-con-mk1">
							<div class="tjcpxx-con-form-title">
								<label>公告分类：</label>
							</div>
							<div class="tjcpxx-con-form1">
								<input id="ClassID" name="classid" type="hidden" value="0" /> <input
									type="hidden" value="${fid}" id="fid" /> <input type="hidden"
									value="${sid}" id="sid" /> <input type="hidden" value="${tid}"
									id="tid" /> <input type="hidden" value="${vo.id }" id="id"
									name="id" /> <select name="firstID" id="firstID"
									style="width: 90px" onchange="Class.firstChange()">

									<option value="0" id="defaultfc">无</option>
									<c:forEach var="s" items="${fslist}">
										<c:choose>
											<c:when test="${s.id == fid}">
												<option value="${s.id}" selected="selected">${s.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${s.id}">${s.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<script id="flist" type="text/html">
                                            {{each list as fclass i}}
                                            <option value="{{fclass.id}}">{{fclass.name}}</option>
                                            {{/each}}
                                        </script>
								</select>--> <select name="secondID" id="secondID" style="width: 90px"
									onchange="Class.secondChange()">
									<option value="0" id="defaultsc">无</option>
									<c:forEach var="s" items="${sslist}">
										<c:choose>
											<c:when test="${s.id == sid}">
												<option value="${s.id}" selected="selected">${s.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${s.id}">${s.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>

								</select>--> <select name="thirdID" id="thirdID" style="width: 90px">
									<option value="0" id="defaulttc">无</option>
									<c:forEach var="s" items="${tslist}">
										<c:choose>
											<c:when test="${s.id==tid}">
												<option value="${s.id}" selected="selected">${s.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${s.id}">${s.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>

								</select>
							</div>
						</div>
						<div class="tjcpxx-con-mk1">
							<div class="tjcpxx-con-form-title">
								<label>发布时间：</label>
							</div>
							<div class="tjcpxx-con-form1">
								<input type="text" id="updatetime" name="updatetime"
									class="Wdate"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									value="${vo.updatetimetr }" />
							</div>
						</div>
						<div class="tjcpxx-con-mk1">
							<div class="tjcpxx-con-form-title1">
								<label>图片：</label>
							</div>
							<div class="tjcpxx-con-form1">
								<div class="tjcpxx-con-form-upimg">
									<img id="loadimg" width="120px" height="115px"
										src="<%=path %>${vo.imgurl}" />
								</div>
								<input type="hidden" name="img" value="${vo.imgurl}" />
								<div
							style="width: 200px; float: left; position: relative; padding-left: 30px;">
							<input type="button" value="选择图片" class="h_scimgbut" /> <input
								type="file" id="singlefile" name="pics" 
								class="filemhbut" 
								style="top: 10px; left: 27px;" />
							<div>
								<input type="button" value="本地上传" class="h_scimgbut h_scimgbut1" />
							</div>
						</div>
							</div>
						</div>
						<!--tjcpxx-con-mk stop -->
						<div class="tjcpxx-con-mk1">
							<div class="tjcpxx-con-form-title1"></div>
							<div class="tjcpxx-con-form1" style="position: relative;">
								<label for="selectimg" class="error"></label> <span
									class="beizhu-mc-upimg">上传图片要小于500kb</span>
							</div>
						</div>
						<!--tjcpxx-con-mk stop -->
						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">
								<label>内容：</label>
							</div>
							<div class="tjcpxx-con-form">
								<textarea id="content1" name=""
									style="width: 450px; height: 200px; visibility: hidden;">${vo.content }</textarea>
								<input class="tjcpxx-fprm-inp" name="content" type="hidden">
							</div>
						</div>
						</br>
						<div class="tjcpxx-con-mk1">
                            <div class="tjcpxx-con-form-title"><label>发布状态：</label></div>
                            <div id="divStatus" class="tjcpxx-con-form huise">
	                            <span class='marrig35'></span>
	                            <c:choose>
		                           <c:when test="${vo.state==0 }">
	                            		<input name='state' checked type='radio' value='0'><span>发布</span>
	                            		<input name='state' type='radio' value='1'><span>未发布</span>
		                           </c:when>
		                           <c:otherwise>
		                           		<input name='state' type='radio' value='0'><span>发布</span>
	                            		<input name='state' checked type='radio' value='1'><span>未发布</span>
		                           </c:otherwise>
	                           </c:choose>
                            </div>
                        </div>
                        <div class="tjcpxx-con-mk">
                    <div class="tjcpxx-con-form-title"><span class="red">*</span>使用平台：</div>
                    <div class="tjcpxx-con-form">
                        <input name='useplatform' ${fn:indexOf(vo.ex7,'1')!=-1?'checked':'' }  type='checkbox' value='1'><span>pc端</span>
                        <span class='marrig35'></span>
                        <input name='useplatform' ${fn:indexOf(vo.ex7,'2')!=-1?'checked':'' } type='checkbox' value='2'><span>app端</span>
                        <span class='marrig35'></span>
                        <input name='useplatform' ${fn:indexOf(vo.ex7,'3')!=-1?'checked':'' } type='checkbox' value='3'><span>wap端</span>
                        <!-- <span class='marrig35'></span>
                        <input name='useplatform' type='checkbox' value='4'><span>微信端</span> -->
                    </div>
                </div>
						<span class="marrig35"></span><input type="hidden" id="type"
							name="type" value=${type } type="text"> <input
							type="hidden" id="ctype" name="ctype" value="${ctype }" type="text">
						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title"></div>
							<div class="tjcpxx-con-form huise">
								<input class="preserve-inp" name="Save" type="button" value="保存">
								<input id="type_action" type="hidden" value="add"> <span
									class="marrig35"></span> <input class="preserve-inp_hs" name=""
									type="button" value="取消" onclick="formCancel()">
							</div>
						</div>
						<!--tjcpxx-con-mk stop -->


					</form>
				</div>
			</div>
		</div>
		<!--tjcpxx-con stop -->
	</div>
	<script id="slist" type="text/html">
  {{each list as sclass i}}
   <option value="{{sclass.id}}">{{sclass.name}}</option>
  {{/each}}
   </script>
	<script id="tlist" type="text/html">
  {{each list as tclass i}}
   <option value="{{tclass.id}}">{{tclass.name}}</option>
   {{/each}}
    </script>
</div>


<script type="text/javascript">
	$(document).ready(function() {
		$("#singlefile").change(x);
    	function x(){
    		var objUrl = getObjectURL(this.files[0]);
    		console.log("objUrl = " + objUrl);
    		if (objUrl) {
    			$("#loadimg").attr("src", objUrl);
    		} else {
    			$("#loadimg").attr("src", "");
    		}
    	};
    	//建立一个可存取到该file的url
    	function getObjectURL(file) {	
    		var url = null;
    		if (window.createObjectURL != undefined) { // basic
    			url = window.createObjectURL(file);
    		} else if (window.URL != undefined) { // mozilla(firefox)
    			url = window.URL.createObjectURL(file);
    		} else if (window.webkitURL != undefined) { // webkit or chrome
    			url = window.webkitURL.createObjectURL(file);
    		}
    		return url;
    	}
				var id = $("#id").val();
				if (id != "") {
					Class.unit(Class.callback);
				} else {
					Class.unit();
				}
				$(".h_scimgbut1").click(
						function() {
							$.ajaxFileUpload({
								url : "/app/api/img/upload",
								secureuri : false,
								fileElementId : 'singlefile',
								dataType : "json",
								//relationtype: 模块（品牌( 11)）
								data : {
									relationtype : 40
								},
								type : 'POST',
								success : function(result) {
									//  alert(JSON.stringify(result));
									//$(".url1").html(JSON.stringify(result));
									$("input[name='img']").val(result.data);
									if (result.code == 0){
										Dalert("上传成功");										
										$("#loadimg").attr(
												"src",
												$("#imgsrc").val()
														+ result.data[0]);
									}
									else {
										$("#loadimg").attr("src", "");
										Dalert("上传图片失败");
									}
									//TODO 结束正在加载中
								},
								error : function(e) {
									alert(JSON.stringify(e));
								}
							});
						});
			})
	function formCancel() {
		var type = $("#type").val();
		var ctype = $("#ctype").val();
		location.href = "/platform/news/slist?type=" + type+"&ctype="+ctype;
	}
</script>
