<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="/resource/ajaxfileupload.js"></script>
<script src="/resource/public/commonjs/kindeditor-4.1.10/kindeditor-min.js"></script>
<script src="/resource/public/commonjs/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script type="text/javascript" src="/resource/public/platform/js/product/spgl_proedit.js"></script>

<div class="mainright">
	<!--l_wzmb  开始 -->
	<div class="l_wzmb">
		<form id="form">
			<div class="l_wzmbtop">
				<ul>
					<li class="sj_hover"><a href="javascript:void(0);">商品基本信息</a><span
						class="sj-img"></span></li>
				</ul>
			</div>
			<!--l_wzmbtop   stop -->
			<div class="tjcpxx">
				<div class="tjcpxx-con-con">
					<div class="tjcpxx-con">
						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">
								<label><span class="red marrig5">*</span>商品名称：</label>
							</div>
							<div class="tjcpxx-con-form" style="width: 500px;">
								<input class="tjcpxx-fprm-inp" name="name" type="text">
								<span class="huise"> 商品名称不能为空，长度限制在100个字符以内 </span>
							</div>
							<div class="remark"></div>
						</div>
						 <div class="tjcpxx-con-mk">
                            <div class="tjcpxx-con-form-title">
                            <label><span class="red marrig5">*</span>商品图片：</label></div>
                            <div class="tjcpxx-con-form">
                                <div class="tjcpxx-con-form-upimg">
                                    <img id="loadimg" width="120px" height="115px" src="" />
                                </div>
                                <input type="hidden" name="imgurlApp" value="" />
                                    <div style=" width:200px;margin-left:10px; float:left;">
                                        <input type="file" id="singlefile" name="pics" />
                                        <a href="javascript:void(0);" style="color:#000"><div class="tjcpxx-con-form-upthis">本地上传</div></a> 
                                    </div>
                            </div>
                        </div>
                        <!--tjcpxx-con-mk stop -->
						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">
								<label><span class="red marrig5">*</span>商品原价：</label>
							</div>
							<div class="tjcpxx-con-form">
								<input class="tjcpxx-fprm-inp" name="price" type="text">
							</div>
						</div>
						
						<!--tjcpxx-con-mk stop -->
						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">
								<label>商品短标题：</label>
							</div>
							<div class="tjcpxx-con-form">
								<input class="tjcpxx-fprm-inp" name="titleShort" type="text">
							</div>
						</div>
						<!--tjcpxx-con-mk stop -->

						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">
								<label>商品标签：</label>
							</div>
							<div class="tjcpxx-con-form">
								<input class="tjcpxx-fprm-inp" name="tag" type="text">
							</div>
						</div>
                        
						<!--tjcpxx-con-mk stop -->
						<div class="tjcpxx-con-mk">
							<div class="tjcpxx-con-form-title">
								<label>商品详情：</label>
							</div>
							<div class="tjcpxx-con-form">
								<textarea id="content1" name=""
									style="width: 450px; height: 200px; visibility: hidden;"></textarea>
								<input class="tjcpxx-fprm-inp" name="description" type="hidden">
							</div>
						</div>
						<!--tjcpxx-con-mk stop -->
					</div>
				</div>
				<!--tjcpxx-con stop -->
			</div>
			<div class="l_wzmbtop">
				<ul>
					<li class="sj_hover"><a href="javascript:void(0);">商品库存信息</a><span
						class="sj-img"></span></li>
				</ul>
			</div>
			<!--l_wzmbtop   stop -->
			<div class="tjcpxx">
				<div class="tjcpxx-con-con" style="width:100%;">
				    <div class="tjcpxx-con">
							<div class="tjcpxx-con-form-title">
								<label><span class="red marrig5">*</span>所属店铺：</label>
							</div>
							<div class="tjcpxx-con-form">
								<select id="shopid" name="shopid" class="the-form-select-one" style="width:215px;" onchange="shopChange()" >
										<option value="0" data="">请选择</option>
										<c:if test="${shoplist != null and shoplist.size() > 0 }">
											<c:forEach items="${shoplist }" var="shop">
												<option value="${shop.id }" data="${shop.shoptype }">${shop.name }</option>
											</c:forEach>
										</c:if>
								</select>
								<input type="hidden" id="skuparam" name="skuparam" />
								<input type="hidden" id="stype" value="" />
							</div>
						</div>
				        <div class="tjcpxx-con" id="divnk" style="padding:8px 0;display:none;">
                            <div class="tjcpxx-con-form-title"></div>
                            <div class="tjcpxx-con-form " style="padding:8px 0">
                                    <input name='protype' id='protype' type='checkbox'><span>年卡</span>
                                    <input type="hidden" name="isnk" value="0" /> 
                            </div>
                        </div>  
					    <div class="tjcpxx-con" id="divday" style="padding:8px 0;display:none;">
						<div class="tjcpxx-con-form-title" style="padding-top: 3px;"><span class="red marrig5">*</span>当天票：</div>
                            <div class="tjcpxx-con-form day" style="width:500px;">
                               	<div style="float:left;width:50%;"><label>商品ID</label><input class="tjcpxx-fprm-inp" style="width:160px;margin-left:5px;" name="tnum" type="text"></div>
                                <div style="float:left;width:50%;"><label>商品售价</label><input class="tjcpxx-fprm-inp" style="width:160px;margin-left:5px;" name="tprice" type="text"></div>
                            </div>
					    </div>
					    
					    <div class="tjcpxx-con" id="div2" style="padding:8px 0">
						<div class="tjcpxx-con-form-title" style="padding-top: 3px;"><span class="red marrig5">*</span>预订票：</div>
                            <div class="tjcpxx-con-form skup" style="width:500px;">
                               	<div style="float:left;width:50%;"><label>商品ID</label><input class="tjcpxx-fprm-inp" style="width:160px;margin-left:5px;" name="tnum" type="text"></div>
                                <div style="float:left;width:50%;"><label>商品售价</label><input class="tjcpxx-fprm-inp" style="width:160px;margin-left:5px;" name="tprice" type="text"></div>
                            </div>
					    </div>
					    <div class="tjcpxx-con" id="divshow" style="padding:8px 0;display:none;">
						<div class="tjcpxx-con-form-title" style="padding-top: 3px;"><span class="red marrig5">*</span>表演时间：</div>
                            <div class="tjcpxx-con-form">
                               	<select id="showy" name="showy" class="the-form-select-one">
										<option value="2017">2017年</option>
										<option value="2018">2018年</option>
										<option value="2019">2019年</option>
										<option value="2020">2020年</option>
										<option value="2021">2021年</option>
										<option value="2022">2022年</option>
										<option value="2023">2023年</option>
										<option value="2024">2024年</option>
										<option value="2025">2025年</option>
										<option value="2026">2026年</option>
										<option value="2027">2027年</option>
										<option value="2028">2028年</option>
										<option value="2029">2029年</option>
										<option value="2030">2030年</option>
								</select>
								<select id="showm" name="showm" class="the-form-select-one">
										<option value="1">1月</option>
										<option value="2">2月</option>
										<option value="3">3月</option>
										<option value="4">4月</option>
										<option value="5">5月</option>
										<option value="6">6月</option>
										<option value="7">7月</option>
										<option value="8">8月</option>
										<option value="9">9月</option>
										<option value="10">10月</option>
										<option value="11">11月</option>
										<option value="12">12月</option>
								</select>
								<input class="tjcpxx-fprm-inp" id="showdays" name="showdays" type="text">
								<span class="huise">日期之间用英文逗号分隔，例1,2,3 </span>
                            </div>
					    </div>
					  <div class="tjcpxx-con">
                            <div class="tjcpxx-con-form-title"></div>
                            <div class="tjcpxx-con-form " style="padding:8px 0">
                                    <input name='iszk' id='iszk' type='checkbox'><span>折扣</span>
                                    <input type="hidden" name="isoffer" value="0" />
                                    
                            </div>
                     </div>  
					
					<div class="tjcpxx-con">
						<div class="tjcpxx-con-form-title"></div>
						<div class="tjcpxx-con-form huise" style="padding:8px 0">
							<input class="preserve-inp" name="commit" type="button"
								value="保存" onclick="formSubmit()"> <input
								class="preserve-inp_hs" name="" type="button" value="取消"
								onclick="backhref()">
						</div>
					</div>
					<!--tjcpxx-con-mk stop -->
				</div>
			</div>
		</form>
	</div>
	<!--l_wzmb  结束 -->
	<!--tjcpxx stop -->
</div>
<!--mainright stop -->
<script type="text/javascript">
	$(document).ready(function() {
		   $(".tjcpxx-con-form-upthis").click(function () {
			  $.ajaxFileUpload({
			        url : "/app/api/img/upload",
					secureuri : false,
					fileElementId : 'singlefile',
					dataType : "json",
					//relationtype: 模块（会员头像( 0), 店铺(1), 产品 ( 2), 其他 (20);） 可以自由增加
					data : {
						"relationtype" : 2,
						//"type" : 0
					},
			        type: 'POST',
			        success: function (result) {
			            $("input[name='imgurlApp']").val(result.data); 
			            if (result.code == 0){
			            	Dalert("上传成功");
			                 $("#loadimg").attr("src",result.data[0]);
			            }else{
			                   $("#loadimg").attr("src", "");
			                   Dalert("上传图片失败");
			             }
			             //TODO 结束正在加载中
			         },
			         error:function(e){
			              alert(JSON.stringify(e));
			         }
			   });
	        });
		
		var year = "${year}";
		var month = "${month}";
		$("#showy").val(year);
		$("#showm").val(month);
		
	})
    function backhref() {
		window.location.href = "/platform/product/goods_dpspgl";
	}
	var editor;
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
				uploadJson :"/zoo/image/upload?relationtype=1&iskdr=1"
		});
	});

</script>