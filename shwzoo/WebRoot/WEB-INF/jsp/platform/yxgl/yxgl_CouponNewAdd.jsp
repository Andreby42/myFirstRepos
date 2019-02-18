<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src="/resource/public/platform/js/yxgl/couponnewSave.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("input[name=getcount]").val(1);
        autoxl.bind("select_shop", couponnew.getShopStartwithName,true);
        $('body').on("click","#divshop ul li",function(){
        	  autoxl.bind("select_spu", couponnew.getSpuStartwithName,true);
    	});
    })
    function formCancel() {
        location.href = "yxgl_CouponNewList";
    }
</script>
<div class="mainright">
    <div class="l_wzmb">
        <div class="l_wzmbtop">
            <ul>
                <li class="sj_hover"><a href="javascript:void(0);">添加优惠劵</a><span class="sj-img"></span></li>

            </ul>
        </div><!--l_wzmbtop   stop -->
        <div class="tjcpxx">

            <div class="tjcpxx-con">
                <div class="tjcpxx-con-con">
                    <form id="form" method="post">
                        <!-- @*名称*@ -->
                        <div class="tjcpxx-con-mk1">
                            <div class="tjcpxx-con-form-title"><label><span class="red marrig5">*</span>名称：</label></div>
                            <div class="tjcpxx-con-form1">
                                <input id="name" class="tjcpxx-fprm-inp" name="name" type="text" value="">
                            </div>
                        </div>
                       <!--  @*面值*@ -->
                        <div class="tjcpxx-con-mk1">
                            <div class="tjcpxx-con-form-title"><label><span class="red marrig5">*</span>面值：</label></div>
                            <div class="tjcpxx-con-form1">
                                <input id="facevalue" class="tjcpxx-fprm-inp" style="width:150px;" name="facevalue" type="text" value="">&nbsp;元
                            </div>
                        </div>
                      <!--   @*数量*@ -->
                        <div class="tjcpxx-con-mk1">
                            <div class="tjcpxx-con-form-title"><label><span class="red marrig5">*</span>数量：</label></div>
                            <div class="tjcpxx-con-form1">
                                <input id="count" class="tjcpxx-fprm-inp" style="width:150px;" name="count" type="text" value="">
                            </div>
                        </div>
                      <!--   @*优惠卷类型*@ -->
                        <div class="tjcpxx-con-mk1">
                            <div class="tjcpxx-con-form-title"><label><span class="red marrig5">*</span>优惠劵类型：</label></div>
                            <div class="tjcpxx-con-form1">
                                <select id="coupontype" name="coupontype" class="the-form-select" onchange="couponnew.TypeChange()">
                                    <option value="0">通用</option>
                                    <option value="1">金额限制</option>
                                </select>
                            </div>
                        </div>
                        <div id="divtype" class="tjcpxx-con-mk1" style="display:none;">
                            <div class="tjcpxx-con-form-title"><label><span class="red marrig5">*</span>满减金额：</label></div>
                            <div class="tjcpxx-con-form1">
                                <input id="mjprice" class="tjcpxx-fprm-inp" style="width:150px;" name="mjprice" type="text" value="">&nbsp;元
                            </div>
                        </div>
                          <div class="tjcpxx-con-mk1">
                            <div class="tjcpxx-con-form-title"><label><span class="red marrig5">*</span>使用对象：</label></div>
                            <div class="tjcpxx-con-form1">
                                <select id="usetype" name="usetype" class="the-form-select" onchange="couponnew.Type2Change()">
                                    <option value="0">商品</option>
                                    <option value="2">店铺</option>
                                    <option value="3">全场通用</option>
                                </select>
                            </div>
                        </div>
                       <div id="divusetype1" class="tjcpxx-con-mk1">
                            <div class="tjcpxx-con-form-title"><label><span class="red marrig5">*</span>所属店铺：</label></div>
                            <div class="tjcpxx-con-form1">
                                <input id="shopid" name="shopid" type="hidden" />
                                <input id="select_shop" type="text" class="tjcpxx-fprm-inp" />
                            </div>
                            <div id="divshop" style="margin-top:25px;margin-left:13px;">
                                <ul>
                                    <script id="select_shoplist" type="text/html">
                                        {{each list as shop i}}
                                        <li data="{{shop.id}}">{{shop.name}}</li>
                                        {{/each}}
                                    </script>
                                </ul>
                            </div>
                        </div>
                        <div id="divusetype2" class="tjcpxx-con-mk1">
                            <div class="tjcpxx-con-form-title"><label><span class="red marrig5">*</span>选择商品：</label></div>
                            <div class="tjcpxx-con-form1">
                                <input id="spuid" name="spuid" type="hidden" />
                                <input id="select_spu" type="text" class="tjcpxx-fprm-inp" />
                            </div>
                            <div style="margin-top:25px;margin-left:13px;">
                                <ul>
                                    <script id="select_spulist" type="text/html">
                                        {{each list as spu i}}
                                        <li data="{{spu.id}}">{{spu.name}}</li>
                                        {{/each}}
                                    </script>
                                </ul>
                            </div>
                        </div>
                        <div class="tjcpxx-con-mk">
                            <div class="tjcpxx-con-form-title"><label><span class="red marrig5">*</span>领用方式：</label></div>
                            <div class="tjcpxx-con-form">
                                <select id="gettype" name="gettype" class="the-form-select">
                                    <option value="2">系统赠送</option>
                                    <option value="5">注册赠送</option>
                                    <option value="0">会员领取</option>
                                </select>
                            </div>
                        </div>
                        <div class="tjcpxx-con-mk">
                            <div class="tjcpxx-con-form-title"><label><span class="red marrig5">*</span>发放时间：</label></div>
                            <div class="tjcpxx-con-form">
                                <input type="text" name="starttime" id="starttime" class="Wdate2" onclick="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'endtime\')}' })" value="" readonly="readonly" />
                            </div>
                        </div>
                        <div class="tjcpxx-con-mk">
                            <div class="tjcpxx-con-form-title"><label><span class="red marrig5">*</span>过期时间：</label></div>
                            <div class="tjcpxx-con-form">
                                <input type="text" name="endtime" id="endtime" class="Wdate2" onclick="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'starttime\')}' })" value="" readonly="readonly" />
                            </div>
                        </div>
                        <div class="tjcpxx-con-mk">
                            <div class="tjcpxx-con-form-title"><label><span class="red marrig5">*</span>每人限领：</label></div>
                            <div class="tjcpxx-con-form">
                                <input id="endday" class="tjcpxx-fprm-inp" style="width:150px;" name="getcount" type="text" value="">&nbsp;张
                            </div>
                        </div>
                        <div class="tjcpxx-con-mk">
                            <div class="tjcpxx-con-form-title"><label>有效期：</label></div>
                            <div class="tjcpxx-con-form">
                                <input id="endday" class="tjcpxx-fprm-inp" style="width:150px;" name="endday" type="text" value="">&nbsp;天
                            </div>
                        </div>
                      <!-- <div class="tjcpxx-con-mk">
                            <div class="tjcpxx-con-form-title"><label>会员等级限制：</label></div>
                            <div class="tjcpxx-con-form">
                                <select id="userlevel" name="userlevel" class="the-form-select">
                                    <option value="0" id="defaultlevel" selected>无</option>
                                    <script id="levellist" type="text/html">
                                        {{each list as level i}}
                                        <option value="{{level.level}}">{{level.name}}</option>
                                        {{/each}}
                                    </script>
                                </select>
                            </div>
                        </div> -->
                        <div class="tjcpxx-con-mk">
                            <div class="tjcpxx-con-form-title">公开领劵：</div>
                            <div id="divStatus" class="tjcpxx-con-form huise">
                                <input name='status' checked type='radio' value='0'><span>公开</span>
                                <span class='marrig35'></span>
                                <input name='status' type='radio' value='1'><span>不公开</span>
                            </div>
                        </div>

                        <div class="tjcpxx-con-mk">
                            <div class="tjcpxx-con-form-title"></div>
                            <div class="tjcpxx-con-form huise">
                                <input class="preserve-inp" name="Save" type="button" value="保存">
                                <input id="action" type="hidden" value="addCoupon">
                                <span class="marrig35"></span>
                                <input class="preserve-inp_hs" name="" type="button" value="取消" onclick="formCancel()">
                            </div>
                        </div><!--tjcpxx-con-mk stop -->


                    </form>
                </div>
            </div>
        </div><!--tjcpxx-con stop -->
    </div>
</div>