<%@ page language="java" contentType="text/html; charset=UTF-8"  isErrorPage="true"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="copyright" content="All Rights Reserved, Copyright (C) 2013, Wuyeguo, Ltd." />
<title>后台管理主页</title>
<link rel="stylesheet" type="text/css" href="${APP_PATH}/static/easyui/easyui/1.3.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${APP_PATH}/static/easyui/css/wu.css" />
<link rel="stylesheet" type="text/css" href="${APP_PATH}/static/easyui/css/icon.css" />
<script type="text/javascript" src="${APP_PATH}/static/easyui/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/easyui/easyui/1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/easyui/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>

<!-- 修改密码弹窗 -->
<div id="edit-dialog" class="easyui-dialog" data-options="closed:false,iconCls:'icon-save',modal:true,title:'修改密码',buttons:[{text:'确认修改',iconCls: 'icon-ok',handler:submitEdit}]" 
style="width:450px; padding:10px;">
	<form id="edit-form" method="post">
        <table>           
            <tr>
                <td width="60" align="right">用户名:</td>
                <td><input type="text" name="username" class="wu-text easyui-validatebox" readonly="readonly" value="${currentUser.username}"/></td>
            </tr> 
            <tr>
                <td width="70" align="right">输入原密码:</td>
                <td><input type="password" name="password" id="oldPassword" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写密码！' "/></td>
            </tr> 
            <tr>
                <td width="70" align="right">设置新密码:</td>
                <td><input type="password" id="newPassword" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写密码！' "/></td>
            </tr> 
            <tr>
                <td width="70" align="right">确认新密码:</td>
                <td><input type="password" id="reNewPassword" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写密码！' "/></td>
            </tr>
        </table>
    </form>
</div>

<!-- End of easyui-dialog -->
<script type="text/javascript">
    function submitEdit(){
    	//非空校验
    	var validate = $('#edit-form').form("validate");
    	if(!validate){
    		$.messager.alert("提示信息","请检查你的数据！","warning");
    		return;
    	}
    	//原密码校验
    	var pass = "${currentUser.password}";
    	if($('#oldPassword').val()!=pass){
    		$.messager.alert("提示信息","原密码错误！","warning");
    		return;
    	}
    	//新密码校验
    	if($('#newPassword').val()!=$('#reNewPassword').val()){
    		$.messager.alert("提示信息","两次密码设置不一致！","warning");
    		return;
    	}
    	$.ajax({
    		url:"${APP_PATH}/system/resetPasswordDo",
    		type:"POST",
    		data:{
    			"password":$('#newPassword').val()
    		},
    		success:function(result){
    			if(result.success){
    				$.messager.alert("提示信息","密码修改成功！","warning");
    			}else{
    				$.messager.alert("提示信息","密码修改失败！","warning");
    			}
    		}
    	});
    }
</script>
</body>
</html>