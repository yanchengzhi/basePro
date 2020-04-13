<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
 <head> 
  <title>我的菜单</title> 
  <meta charset="utf-8" /> 
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" /> 
  <meta name="author" content="" /> 
  <link href="${APP_PATH}/static/home/css/bootstrap.css" rel="stylesheet" /> 
  <link href="${APP_PATH}/static/home/css/buttons.css" rel="stylesheet" /> 
  <link href="${APP_PATH}/static/home/css/flat.css" rel="stylesheet" />
  <link href="${APP_PATH}/static/home/css/font-awesome.css" rel="stylesheet" />
  <script type="text/javascript" src="${APP_PATH}/static/home/js/jquery.js"></script>   
 </head> 
 <body> 
<div id="wrapper2" class="viewer wrapper countpage clearfix show" style="display:none"> 
   <div id="submitView" class="viewer clearfix">
    <section class="submit_title">
        <div class="container" id="">  
        
         <div class="col-md-12 clearfix">
                <div class="now_submit clearfix" style="margin-left: 0px;">
                    <div class="line-body clearfix info-form_more" >
                            <span class="fl lh43">
                                                                   
                            </span>
                            <ul class="nav nav-pills tabdrop fr">
                                <li class="dropdown pull-right tabdrop">
                                    <a href="${APP_PATH}/index/index" class="dropdown-toggle">
                                        <i class="fa fa-th-list">返回主页
                                        </i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                </div>
            </div>
        
            <div class="col-md-12 clearfix">
                <div class="now_submit clearfix" style="margin-left: 0px;">
                    <div class="line-body clearfix info-form_more" >
                            <span class="fl lh43">
                                                                   基础信息
                            </span>
                            <ul class="nav nav-pills tabdrop fr">
                                <li class="dropdown pull-right tabdrop">
                                    <a class="dropdown-toggle" id="J_btn_reg">
                                        <i class="fa fa-th-list">
                                        </i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    <form class="slide-form">   
                        <div class="form-group" >
                         <label for="name" class="mgt10">真实姓名</label>
                            <span class="twitter-typeahead">
                                <input type="text" id="edit-realName" name="realName" value="${account.realName}" class="form-control tt-query" autocomplete="off"
                                spellcheck="false" dir="auto">
                            </span>
                         <label for="phone">密码</label>
                            <span class="twitter-typeahead">
                                <input type="password" id="edit-password" name="password" value="${account.password}" class="form-control tt-query" autocomplete="off"
                                spellcheck="false" dir="auto">
                            </span>
                            <label for="phone">手机号码</label>
                            <span class="twitter-typeahead">
                                <input type="text" id="edit-phone" name="phone" maxlength="11" value="${account.phone}" class="form-control tt-query" autocomplete="off"
                                spellcheck="false" dir="auto">
                            </span>
                            <label for="adress" class="mgt10">地址</label>
                            <span class="twitter-typeahead">
                                <input type="text" id="edit-address" name="address" value="${account.address}" class="form-control tt-query" autocomplete="off"
                                spellcheck="false" dir="auto">
                            </span>
                        </div>
                        <div class="form-group">
                            <div class="btn_control fr">
                                <a class="btn btn-info bottommargin" id="submit-info-action">
                                                                          确认
                                </a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            
            <c:forEach items="${orderList}" var="order">
            <div class="col-md-12 clearfix">
                <div class="now_submit clearfix" style="margin-left: 0px;">
                    <div class="line-body clearfix info-form_more">
                            <span class="fl lh43">
                                ${order.createTimeStr}&nbsp;&nbsp;&nbsp;
                                <c:if test="${order.status==0}"><strong style="color:red">【未配送】</strong></c:if>
                                <c:if test="${order.status==1}"><strong style="color:red">【配送中】</strong></c:if>
                                <c:if test="${order.status==2}"><strong style="color:red">【已完成】</strong></c:if>
                            </span>
                            <ul class="nav nav-pills tabdrop fr">
                                <li class="dropdown pull-right tabdrop">
                                    <a class="dropdown-toggle" id="J_btn_reg">
                                        <i class="fa fa-th-list">
                                        </i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    <form class="slide-form">   
                        <div class="form-group" style="padding-bottom:0px;">
                           <c:forEach items="${order.orderItems}" var ="item">
                            <label>
                                  ${item.foodName}<span style="float:right;color:red;">${item.price} ✕  ${item.foodNum} ￥：${item.money}元</span>
                            </label>
                           </c:forEach>
                            <label>
                             	 <span style="float:right;color:red;">总计￥：${order.money}</span>
                            </label>
                        </div>
                    </form>
                </div>
            </div>
            </c:forEach>
        </div>
    </section>
</div> 
  </div>   
 </body>
<script>
	$(document).ready(function(){
		$(".slide-form").slideUp();
	});
	$(".info-form_more").click(function(){
		$(this).next("form").slideToggle();
	});
	$("#submit-info-action").click(function(){
		var realName = $("#edit-realName").val();
		var password = $("#edit-password").val();
		var phone = $("#edit-phone").val();
		var address = $("#edit-address").val();
		if(realName==""){
			alert("请填写真实姓名！");
			return;
		}
		if(password==""){
			alert("请设置密码！");
			return;
		}
		if(phone==""){
			alert("请填写联系方式！");
			return;
		}
		if(address==""){
			alert("请填写配送地址！");
			return;
		}
		
		var $this = $(this);
		$.ajax({
			url:'${APP_PATH}/account/updateInfo',
			type:'post',
			data:{
				"realName":realName,
				"password":password,
				"phone":phone,
				"address":address
			},
			success:function(result){
				if(result.success){
					alert("修改成功！");
					$this.closest("form").slideUp();
				}else{
					alert("修改失败！");
				}
			}
		});
	});
</script>
</html>