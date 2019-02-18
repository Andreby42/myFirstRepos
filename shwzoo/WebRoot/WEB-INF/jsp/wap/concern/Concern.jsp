<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta http-equiv="Content-Type" content="text/html">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<title>我的收藏</title>
<link rel="stylesheet" type="text/css" href="/wap/css/Yform.css">
<link rel="stylesheet" type="text/css" href="/wap/css/css.css">
<script src="/wap/js/jquery-1.9.1.min.js"></script>
<script src="/wap/js/Yeffect16_0118.min.js"></script>
<script src="/wap/js/Yform.js"></script>
<script src="/wap/js/js.js"></script>
<script src="/wap/js/Concern.js"></script>
<script src="/js/artTemplate.js"></script>
</head>
<body>
<nav class="guanzhu-nav"><a onclick="changetype(this,0);" id="prode" data-type="1" class="">商品</a><a onclick="changetype(this,1);" id="shop" data-type="2" class="active">商家</a></nav>
<hr class="back">
<!-- 收藏的商品 -->
<ul class="guanzhu-kuai" id="spu">
<script type="text/html" id="spulist">
{{each list spu i}}
<li>
    	<a href="#">
        	<img src="{{spu.imgurl}}" class="L">
            <h2>{{spu.name}}</h2>
            <p>戴尔经典i3小机箱，3年上门服务，可靠品质，稳中取胜！</p>
            <span>￥{{spu.wapprice}}</span>
        </a>
    </li>
{{/each}}
</script>
</ul>
<ul class="guanzhu-kuai" id="shops">
<script type="text/html" id="shoplist">
{{each  list shop i}}
<li>
    	<a href="#">
        	<img src="{{shop.imgurl}}" class="L">
            <h2>{{shop.name}}</h2>
            <p>当吉野家牛肉饭遇到茶碗蒸、裙带菜、百事可乐将擦出怎样的……</p>
            <span><font>2.5km<font></span>
        </a>
    </li>
{{/each}}
</script>
</ul>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	concernShop.getconsernshoplist(1);
})
 function changetype(obj,type) {
            if (type == 1) {
            	concernShop.getconsernshoplist(1);
                $("#prode").removeClass("active");
               $(obj).addClass("active");
            } else {
            	concernShop.getconcedrnprolist(0);
                $("#shop").removeClass("active");
                $(obj).addClass("active");
            }
        }
</script>
