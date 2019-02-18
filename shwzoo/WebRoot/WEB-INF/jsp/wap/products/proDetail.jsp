<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="/wap/css/Yform.css">
<link rel="stylesheet" type="text/css" href="/wap/css/css.css">
<script src="/wap/js/jquery-1.9.1.min.js"></script>
<script src="/wap/js/Yeffect16_0118.min.js"></script>
<script src="/wap/js/Yform.js"></script>
<script src="/wap/js/js.js"></script>
<script src="/js/artTemplate.js"></script>

<script src="/wap/js/products/proDetail.js"></script>

<title>商品详情</title>
</head>
<body>

	<!--banner-->
	<div class="pordct_banner">
		<ul>
			<li><img src="/wap/images/pbaner1.jpg"></li>
			<li><img src="/wap/images/pbaner1.jpg"></li>
			<li><img src="/wap/images/pbaner1.jpg"></li>
		</ul>
		<p>
			<i></i><i></i><i></i>
		</p>
	</div>
	<script>
		$(function() {
			////banner/////
			Yeffect.mobileBanner(".pordct_banner", ".pordct_banner li",
					".pordct_banner p i", "", "", "current", 5000, 500);
			var pordct_banner = $(".pordct_banner");
			Yeffect.resizeHtW(pordct_banner, pordct_banner, 640, 600);
			///热销产品///
			Yeffect.click_hxk(".pordct_cptab nav a", ".pordct_cptab ul",
					"current");
			Yeffect.resizeHtWstr(".pordct_cptab ul li:visible",
					".pordct_cptab ul li img", 209, 159);
		})
	</script>
	<!--banner***-->
	<h1 class="pordct_H"></h1>
	<div class="pordct_Hp"></div>
	<div class="pordct_jg">
		<b ><font>¥</font>0.00</b> <span>价格<u>¥500.00</u></span>
		<div>
			<img src="/wap/images/index_01.png">
			<img src="/wap/images/index_02.png">
			<img src="/wap/images/index_03.png">
		</div>
	</div>
	<ul class="pordct_quan">
		<li>
			<div class="L">
				满<font>99</font>元包邮
			</div>
			<div class="R">由奇酷提供发货以及售后服务</div>
		</li>
		<li>
			<div class="L">领 券</div>
			<div class="R">
				<img src="/wap/images/index_04.png">
			</div>
		</li>
	</ul>
	<hr class="back" />
	<div class="pordct_links">
		<a href="#">商品组合</a> <a href="/wap/products/showProInfo">产品详情</a> <a
			href="#">选择套餐类型/颜色分类</a>
	</div>
	<hr class="back" />
	<!--评论-->
	<div class="pordct_pl">
		<h2>
			<span>商品评价 (53)</span><font>好评率：98.13%</font>			
		</h2>
		<ul>
			<li>
				<h2>
					<img src="/wap/images/index_06.png">2015年11月14日
				</h2> <time>2015年11月14日</time>
				<div>看外观样子很不错，相信会很搜欢迎的！很喜欢。</div>
			</li>
			<li>
				<h2>
					<img src="/wap/images/index_07.png">2015年11月14日
				</h2> <time>2015年11月14日</time>
				<div>功能强大啊，不知道效果怎么样。上市了吗？多少钱卖？是升级版，超级喜欢。</div>
			</li>
			<li>
				<h2>
					<img src="/wap/images/index_08.png">2015年11月14日
				</h2> <time>2015年11月14日</time>
				<div>是智吉丹的升级版，我买了一个。</div>
			</li>
		</ul>
		<a href="/wap/products/showProCommen" class="link_more">查看全部评价</a>
	</div>
	<hr class="back" />
	<!--店铺-->
	<div class="pordct_dp">
	 <script type="text/html" id="shopinfo">
		<div>
			<p>
				<img src="{{shopUrl}}">{{shopTitle}}
			</p>
			<a href="javascript:void(0)">进店逛逛</a>
		</div>
		<ul>
			<li><b>{{shopAllPro}}</b>全部商品</li>
			<li><b>{{shopCon}}</b>收藏店铺</li>
			<li><b>{{shopGoodRate}}</b>好评率</li>
		</ul>
		</script>
	</div>
	<hr class="back" />
	<!--最新与热销产品-->
	<div class="pordct_cptab">
		<nav> <a href="javascript:void(0)">最新商品</a> <a
			href="javascript:void(0)">热销商品</a></nav>
		<ul>
			<li><a href="#"> <img src="/wap/images/index2.jpg">
					<h2>迪奥（Dior）香水三件套 经典小样</h2> <span>￥119</span>
			</a></li>
			<li><a href="#"> <img src="/wap/images/index3.jpg">
					<h2>丸美眼霜护肤套装护肤套装</h2> <span>￥224</span>
			</a></li>
			<li><a href="#"> <img src="/wap/images/index2.jpg">
					<h2>半坡皮包女手提包秋冬款热销新品</h2> <span>￥456</span>
			</a></li>
			<li><a href="#"> <img src="/wap/images/index2.jpg">
					<h2>迪奥（Dior）香水三件套 经典小样</h2> <span>￥119</span>
			</a></li>
			<li><a href="#"> <img src="/wap/images/index3.jpg">
					<h2>丸美眼霜护肤套装护肤套装</h2> <span>￥224</span>
			</a></li>
			<li><a href="#"> <img src="/wap/images/index2.jpg">
					<h2>半坡皮包女手提包秋冬款热销新品</h2> <span>￥456</span>
			</a></li>
		</ul>
		<ul>
			<li><a href="#"> <img src="/wap/images/index2.jpg">
					<h2>迪奥（Dior）香水三件套 经典小样</h2> <span>￥119</span>
			</a></li>
			<li><a href="#"> <img src="/wap/images/index3.jpg">
					<h2>丸美眼装</h2> <span>￥224</span>
			</a></li>
			<li><a href="#"> <img src="/wap/images/index2.jpg">
					<h2>半坡皮品</h2> <span>￥456</span>
			</a></li>
			<li><a href="#"> <img src="/wap/images/index3.jpg">
					<h2>丸美眼装</h2> <span>￥224</span>
			</a></li>
			<li><a href="#"> <img src="/wap/images/index2.jpg">
					<h2>半坡新品</h2> <span>￥456</span>
			</a></li>
			<li><a href="#"> <img src="/wap/images/index2.jpg">
					<h2>迪奥小样</h2> <span>￥119</span>
			</a></li>
		</ul>
	</div>
</body>
</html>