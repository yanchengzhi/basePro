<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- saved from url=(0051)http://demo1.mycodes.net/denglu/HTML5_yonghudenglu/ -->
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
  <title>登录界面</title>
  <meta name="description" content="particles.js is a lightweight JavaScript library for creating particles.">
  <meta name="author" content="Vincent Garreau">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <link rel="stylesheet" media="screen" href="${APP_PATH}/static/login/css/style.css">
  <link rel="stylesheet" type="text/css" href="${APP_PATH}/static/login/css/reset.css">
</head>
<body>

<div id="particles-js">
		<div class="login" style="display: block;">
			<div class="login-top">
				登录
			</div>
			<div class="login-center clearfix">
				<div class="login-center-img"><img src="${APP_PATH}/static/login/images/name.png"></div>
				<div class="login-center-input">
					<input type="text" name="username" id="username" value="" placeholder="请输入您的用户名" onfocus="this.placeholder=&#39;&#39;" onblur="this.placeholder=&#39;请输入您的用户名&#39;">
					<div class="login-center-input-text">用户名</div>
				</div>
			</div>
			<div class="login-center clearfix">
				<div class="login-center-img"><img src="${APP_PATH}/static/login/images/password.png"></div>
				<div class="login-center-input">
					<input type="password" name="password" id="password" value="" placeholder="请输入您的密码" onfocus="this.placeholder=&#39;&#39;" onblur="this.placeholder=&#39;请输入您的密码&#39;">
					<div class="login-center-input-text">密码</div>
				</div>
			</div>
			<div class="login-center clearfix">
				<div class="login-center-img"><img src="${APP_PATH}/static/login/images/验证码.png"></div>
				<div class="login-center-input">
					<input type="password" style="width:50%" name="cpacha" id="cpacha" value="" placeholder="请输入验证码" onfocus="this.placeholder=&#39;&#39;" onblur="this.placeholder=&#39;请输入验证码&#39;">
					<div class="login-center-input-text">验证码</div>
					<img src="${APP_PATH}/system/getCpacha?len=4&width=100&height=30&type=loginCpacha" id="cpacha_img" onclick="changeCpacha()" style="width:110px;height:30px;cursor:pointer;" title="点击更换验证码"></div>
			</div>
			<div class="login-button">
				登录
			</div>
		</div>
		<div class="sk-rotating-plane"></div>
<canvas class="particles-js-canvas-el" width="1147" height="952" style="width: 100%; height: 100%;"></canvas></div>

<!-- scripts -->
<script type="text/javascript" src="${APP_PATH}/static/login/js/jquery.min.js"></script>
<script src="${APP_PATH}/static/login/js/particles.min.js"></script>
<script src="${APP_PATH}/static/login/js/app.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/layer/layer.js"></script>
<script type="text/javascript">
	function hasClass(elem, cls) {
	  cls = cls || '';
	  if (cls.replace(/\s/g, '').length == 0) return false; //当cls没有参数时，返回false
	  return new RegExp(' ' + cls + ' ').test(' ' + elem.className + ' ');
	}
	 
	function addClass(ele, cls) {
	  if (!hasClass(ele, cls)) {
	    ele.className = ele.className == '' ? cls : ele.className + ' ' + cls;
	  }
	}
	 
	function removeClass(ele, cls) {
	  if (hasClass(ele, cls)) {
	    var newClass = ' ' + ele.className.replace(/[\t\r\n]/g, '') + ' ';
	    while (newClass.indexOf(' ' + cls + ' ') >= 0) {
	      newClass = newClass.replace(' ' + cls + ' ', ' ');
	    }
	    ele.className = newClass.replace(/^\s+|\s+$/g, '');
	  }
	}
	
	//更换验证码
	function changeCpacha(){
		$('#cpacha_img').attr("src","${APP_PATH}/system/getCpacha?len=4&width=100&height=30&type=loginCpacha&t="+ new Date().getTime());
	}	
		document.querySelector(".login-button").onclick = function(){
			    //非空校验
			    var username = $('#username').val();
			    var password = $('#password').val();
			    var cpacha = $('#cpacha').val();
			    if(username==""){
			    	layer.msg("请填写用户名！",{time:2000,icon:0,shift:5},function(){
			    		
			    	});
			    	return;
			    }
			    if(password==""){
			    	layer.msg("请填写用户密码！",{time:2000,icon:0,shift:5},function(){
			    		
			    	});
			    	return;
			    }
			    if(cpacha==""){
			    	layer.msg("请填写验证码！",{time:2000,icon:0,shift:5},function(){
			    		
			    	});
			    	return;
			    }
				
				var loadingIndex = null;
				//登录验证
				$.ajax({
					url:"${APP_PATH}/system/loginDo",
					type:"POST",
					data:{
						"username":username,
						"password":password,
						"cpacha":cpacha
					},
					dataType:"json",
					beforeSend:function(){
						loadingIndex = layer.msg("处理中",{icon:16});
					},
					success:function(result){
						layer.close(loadingIndex);
						if(result.success){
							window.location.href="${APP_PATH}/system/index";
						}else{
					    	layer.msg(result.data,{time:2000,icon:5,shift:5},function(){
					    		
					    	});
						}
					}
				});				
		}
</script>
</body></html>