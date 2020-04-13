<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head> 
  <meta charset="utf-8" /> 
  <title>登录页面</title> 
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" /> 
  <meta name="author" content="" />  
  <link href="${APP_PATH}/static/home/css/bootstrap.css" rel="stylesheet" /> 
  <link href="${APP_PATH}/static/home/css/buttons.css" rel="stylesheet" /> 
  <link href="${APP_PATH}/static/home/css/flat.css" rel="stylesheet" />
  <link href="${APP_PATH}/static/home/css/font-awesome.css" rel="stylesheet" />
 </head> 
 <body> 

<div id="submitView" class="viewer clearfix" style="">
    <section class="submit_title">
        <div class="container" id="">  
            <div class="col-md-12 clearfix">
                <div class="now_submit clearfix" style="margin-left:0px;">        
                        <div class="form-group" id="form_info">
                            <label for="phone">用户名</label>
                            <span class="twitter-typeahead">
                                <input type="text" name="name" id="name" class="form-control tt-query" autocomplete="off"
                                spellcheck="false" dir="auto">
                            </span>
                            <label for="name" class="mgt10">密码</label>
                            <span class="twitter-typeahead">
                                <input type="password" name="password" id="password" class="form-control tt-query" autocomplete="off"
                                spellcheck="false" dir="auto">
                            </span>
                            <label for="name" class="mgt10">确认密码</label>
                            <span class="twitter-typeahead">
                                <input type="password" id="rePassword" class="form-control tt-query" autocomplete="off"
                                spellcheck="false" dir="auto">
                            </span>            
                        </div>
                        <div class="form-group">
                            <div class="btn_control fr">
                                <a class="btn btn-default bottommargin" id="loginSubmit">
                                                                               登录
                                </a>
                                <button type="button" class="btn btn-info bottommargin" id="registeSubmit">注册</button>
                            </div>
                        </div>
                </div>
            </div>
        </div>
    </section>
</div>
  <script src="${APP_PATH}/static/home/js/jquery.js"></script> 
  <script src="${APP_PATH}/static/home/js/bootstrap.min.js"></script> 
  <script>
        //跳往登录界面
		$('#loginSubmit').click(function(){
			window.location.href="${APP_PATH}/account/login";
		});
        
        //点击注册时
        $('#registeSubmit').click(function(){
            //先进行非空校验
            var name = $('#name').val();
            var password = $('#password').val();
            var rePassword = $('#rePassword').val();
            if(name==""){
            	alert("请填写用户名！");
            	return;
            }
            if(password==""){
            	alert("请设置密码！");
            	return;
            }
            if(rePassword==""){
            	alert("请确认密码！");
            	return;
            }
            if(password!=rePassword){
            	alert("两次密码设置不一致！");
            	return;
            }
            $('#registeSubmit').click(function(){
            	$.ajax({
            		url:'${APP_PATH}/account/registeDo',
            		type:"POST",
            		data:{
            			"name":name,
            			"password":password
            		},
            		success:function(result){
            			if(result.success){
            				alert("注册成功！");
            				//定位到登录界面
            				window.location.href="${APP_PATH}/account/login";
            			}else{
            				alert(result.data);
            			}
            		}
            	});
            });
        });
        
  </script>  
 </body>
</html>