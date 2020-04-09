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
<script>
    var pc; 
    $.parser.onComplete = function () {
        if (pc) clearTimeout(pc);
        pc = setTimeout(closes, 1000);
    } 

    function closes() {
        $('#loading').fadeOut('normal', function () {
            $(this).remove();
        });
    }
</script>
</head>
<body>

<div id="loading" style="position:absolute;z-index:1000;top:0;left:0;width:100%;height:100%;background:#F9F9F9;text-align :center;padding-top:10%;">
   <img alt="" src="${APP_PATH}/static/h-ui/images/loadinginner.gif" style="width:40%">
   <h1><font color="#15428B">加载中....</font></h1>
</div>

<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar">
        <div class="wu-toolbar-button">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
        </div>
        <div class="wu-toolbar-search">
            <label>用户名：</label><input class="wu-text" id="search-name" style="width:100px">&nbsp;&nbsp;&nbsp;
            <label>所属角色：</label>
            <select class="easyui-combobox" id="search-role" name="role" panelHeight="auto" style="width:120px">
                <!-- 遍历父菜单 -->
                   <option value="-1">全部</option>
                <c:forEach items="${roleList}" var="role">
                   <option value="${role.id}">${role.name}</option>
                </c:forEach>
             </select>&nbsp;&nbsp;&nbsp;
             <label>性别：</label>
            <select class="easyui-combobox" id="search-sex" name="sex" panelHeight="auto" style="width:120px">
                <option value="-1">全部</option>
                <option value="0">女</option>
                <option value="1">男</option>
             </select>
            <a href="#" class="easyui-linkbutton" id="search-btn" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>

<!-- 这个文本框用作弹出 -->
<input type="file" id="photo-file" style="display:none;" onchange="upload()">
<!-- 添加弹窗 -->
<div id="wu-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
	<form id="add-form" method="post">
        <table>
            <tr>
                <td width="60" align="right">头像:</td>
                <td>
                   <input type="text" name="photo" id="add-photo" class="wu-text" style="width:250px;" value="${APP_PATH}/static/easyui/images/狗狗.jpg"/>
                   <a href="#" class="easyui-linkbutton" iconCls="icon-upload" onclick="uploadPhoto()" plain="true">上传图片</a>
                </td>
            </tr> 
            <tr>
                <td width="60" align="right">头像预览:</td>
                <td>
                   <img alt="预览" id="preview-photo" src="${APP_PATH}/static/easyui/images/狗狗.jpg" style="width:70px;height:50px;">
                </td>
            </tr> 
            <tr>
                <td width="60" align="right">用户名:</td>
                <td><input type="text" name="username" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写用户名！' "/></td>
            </tr>           
            <tr>
                <td align="right">密码:</td>
                <td><input type="text" name="password" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请设置密码！' "/></td>
            </tr>
            <tr>
               <td align="right">用户角色:</td>
               <td>
                   <select class="easyui-combobox" id="roleId" name="roleId" panelHeight="auto" style="width:268px"> 
                     <c:forEach items="${roleList}" var="role">
                     <option value="${role.id}">${role.name}</option>
                     </c:forEach>
                   </select>
               </td>
            </tr>
            <tr>
               <td align="right">性别:</td>
               <td>
                   <select class="easyui-combobox" id="sex" name="sex" panelHeight="auto" style="width:268px" data-options="required:true,missingMessage:'请选择年龄！' "> 
                     <option value="1">男</option>
                     <option value="0">女</option>                   
                   </select>
               </td>
            </tr>
            <tr>
                <td width="60" align="right">年龄:</td>
                <td><input type="text" name="age" class="wu-text easyui-numberbox easyui-validatebox" data-options="required:true,min:1,precision:0,missingMessage:'请填写年龄！' "/></td>
            </tr> 
            <tr>
                <td width="60" align="right">住址:</td>
                <td><input type="text" name="address" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写住址！' "/></td>
            </tr> 
        </table>
    </form>
</div>

<!-- 修改弹窗 -->
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
	<form id="edit-form" method="post">
	    <!-- 隐藏域的文本框，无需显示，用于修改记录传递id值 -->
	    <input type="hidden" name="id" id="edit-id"/>
        <table>
            <tr>
                <td width="60" align="right">头像:</td>
                <td>
                   <input type="text" name="photo" id="edit-photo" class="wu-text" style="width:250px;" value="${APP_PATH}/static/easyui/images/狗狗.jpg"/>
                   <a href="#" class="easyui-linkbutton" iconCls="icon-upload" onclick="uploadPhoto()" plain="true">上传图片</a>
                </td>
            </tr> 
            <tr>
                <td width="60" align="right">头像预览:</td>
                <td>
                   <img alt="预览" id="edit-preview-photo" src="${APP_PATH}/static/easyui/images/狗狗.jpg" style="width:70px;height:50px;">
                </td>
            </tr> 
            <tr>
                <td width="60" align="right">用户名:</td>
                <td><input type="text" name="username" id="edit-username" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写用户名！' "/></td>
            </tr>           
            <tr>
               <td align="right">用户角色:</td>
               <td>
                   <select class="easyui-combobox" id="edit-roleId" name="roleId" panelHeight="auto" style="width:268px"> 
                     <c:forEach items="${roleList}" var="role">
                     <option value="${role.id}">${role.name}</option>
                     </c:forEach>
                   </select>
               </td>
            </tr>
            <tr>
               <td align="right">性别:</td>
               <td>
                   <select class="easyui-combobox" id="edit-sex" name="sex" panelHeight="auto" style="width:268px" data-options="required:true,missingMessage:'请选择年龄！' "> 
                     <option value="1">男</option>
                     <option value="0">女</option>                   
                   </select>
               </td>
            </tr>
            <tr>
                <td width="60" align="right">年龄:</td>
                <td><input type="text" name="age" id="edit-age" class="wu-text easyui-numberbox easyui-validatebox" data-options="required:true,min:1,precision:0,missingMessage:'请填写年龄！' "/></td>
            </tr> 
            <tr>
                <td width="60" align="right">住址:</td>
                <td><input type="text" name="address" id="edit-address" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写住址！' "/></td>
            </tr> 
        </table>
    </form>
</div>

<div id="process-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" title="正在上传" style="width:350px; padding:10px;">
   <div id="p" class="easyui-progressbar" style="width:300px;" data-options="text:'上传中...' "></div>
</div>
<!-- End of easyui-dialog -->
<script type="text/javascript">

    function start(){
    	var value = $('#p').progressbar('getValue');
    	if(value<100){
    		value+=Math.floor(Math.random()*10);
    		 $('#p').progressbar('setValue',value);
    	}else{
    		$('#p').progressbar('setValue',0);
    	}
    };
    
    //点击上传图片时打开文件选择窗口
    function uploadPhoto(){
    	$('#photo-file').click();
    }
    
    //图片上传
    function upload(){
    	if($('#photo-file').val=="")
    		return;
    	var formData = new FormData();
    	formData.append('photo',document.getElementById('photo-file').files[0]);
    	//打开进度条窗口
    	$('#process-dialog').dialog('open');
    	var interval = setInterval(start,100);
    	$.ajax({
    		url:'${APP_PATH}/user/uploadPhoto',
    	    type:"POST",
    	    data:formData,
    	    contentType:false,
    	    processData:false,
    	    success:function(result){
    	    	if(result.success){
    	    		//清除进度条
    	    		clearInterval(interval);
    	    		//关闭进度条窗口
    	    		$('#process-dialog').dialog('close');
    	    		//将图片的路径重新赋给预览窗口
    	    		$('#preview-photo').attr('src',result.data);
    	    		//将图片路径填充进文本框
    	    		$('#add-photo').val(result.data);
    	    		$('#edit-preview-photo').attr('src',result.data);
    	    		$('#edit-photo').val(result.data);
    	    	}else{
    	    		$.messager.alert('提示信息',result.data,'warning');
    	    		//清除进度条
    	    		clearInterval(interval);
    	    		//关闭进度条窗口
    	    		$('#process-dialog').dialog('close');
    	    	}
    	    }
    	});
    }

	/**
	* 添加记录
	*/
	function add(){
		var validate = $('#add-form').form("validate");
		if(!validate){
			$.messager.alert("消息提示","请检查你的数据！","warning");
			return;
		}
		//序列化表单数据
		var data = $('#add-form').serialize();
		$.ajax({
			url:'${APP_PATH}/user/addUser',
			type:"POST",
			data:data,
			success:function(result){
				if(result.success){
					$.messager.alert('信息提示','添加成功！','info');
					$('#wu-dialog').dialog('close');
					//添加成功后重载数据
					$('#data-datagrid').datagrid('reload');
				}else{
					$.messager.alert('信息提示',result.data,'info');
				}
			}
		});
	}
	
	/**
	* 修改记录
	*/
	function edit(){
		var validate = $('#edit-form').form('validate');
		if(!validate){
			$.messager.alert('提示信息','请检查你的数据！','warning');
		}
		//序列化表单
		var data = $('#edit-form').serialize();
		$.ajax({
			url:"${APP_PATH}/user/editUser",
			type:"POST",
			data:data,
			success:function(result){
				if(result.success){
					$.messager.alert('提示信息','修改成功！','info');
					//关闭弹窗
					$('#edit-dialog').dialog('close');
					//修改成功后重载数据
					$('#data-datagrid').datagrid('reload');
				}else{
					$.messager.alert('提示信息',result.data,'info');
				}
			}
		});
	}
	
	/**
	* 删除记录
	*/
	function remove(){
		var item = $('#data-datagrid').datagrid('getSelections');
		if(item==null||item.length==0){
			$.messager.alert('信息提示','请选择需要删除的数据！','info');
			return;
			}
		$.messager.confirm('信息提示','确定要删除这'+item.length+'条记录？', function(result){
			if(result){
				var ids = '';
				for(var i=0;i<item.length;i++){
				     ids+=item[i].id+',';	
				}
				$.ajax({
					url:"${APP_PATH}/user/deleteUsers",
					type:"POST",
					data:{
						"ids":ids
					},
					success:function(result2){
						if(result2.success){
							$.messager.alert('提示信息','删除成功！','info');
							//修改成功后重载数据
							$('#data-datagrid').datagrid('reload');
						}else{
							$.messager.alert('提示信息','删除失败！','error');
						}
					}
				});
				
			}	
		});
	}
	
	/**
	* 打开添加窗口
	*/
	function openAdd(){
		$('#wu-dialog').dialog({
			closed: false,
			modal:true,
            title: "添加用户信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: add
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#wu-dialog').dialog('close');                    
                }
            }],
            //添加用户时，清空文本框中的数据，需要时使用，因为用户添加时有重复的信息，避免再次输入，可以不清空
            /*
            onBeforeOpen:function(){
            	$('#add-form input').val("");
            }
		*/
        });
	}
	
	/**
	* 打开修改窗口
	*/
	function openEdit(){
		//获取选中的多条记录
		var item = $('#data-datagrid').datagrid('getSelections');
		if(item==null||item.length==0){
			$.messager.alert('信息提示','请选择需要修改的数据！','info');
			return;
			}
		if(item.length>1){
			$.messager.alert('信息提示','请选择单条数据！','info');
			return;
		}
		//选择单条数据时，获取数组中的数据
		item = item[0];
		
		$('#edit-dialog').dialog({
			closed: false,
			modal:true,
            title: "修改用户信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: edit
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#edit-dialog').dialog('close');                    
                }
            }],
            //修改的记录数据回显
            onBeforeOpen:function(){
            	$('#edit-id').val(item.id);
            	$('#edit-preview-photo').attr('src',item.photo);
            	$('#edit-photo').val(item.photo);
            	$('#edit-username').val(item.username);
            	$('#edit-roleId').combobox('setValue',item.roleId);
            	$('#edit-sex').combobox('setValue',item.sex);
            	$('#edit-age').val(item.age);
            	$('#edit-address').val(item.address);
            }
        });
	}	
	
	//实现模糊查询
		$('#search-btn').click(function(){
			var roleId = $('#search-role').combobox('getValue');
			var sex = $('#search-sex').combobox('getValue');
			//这里使用json的格式来传值
			var option = {
					username:$('#search-name').val()
			};
			if(roleId!=-1){
				option.roleId = roleId;
			}
			if(sex!=-1){
				option.sex = sex;
			}
			$('#data-datagrid').datagrid('reload',option);
	});
	
	
	/**
	* Name 载入数据
	*/
	$('#data-datagrid').datagrid({
		url:'${APP_PATH}/user/listData',
		rownumbers:true,//是否显示行号
		singleSelect:false,//是否只支持单选,true支持单选，false支持多选
		pageSize:20,//每页显示的记录条数           
		pagination:true,//是否开启分页功能
		multiSort:true,
		fitColumns:true,//是否填充列
		fit:true,
		columns:[[
			{ field:'chk',checkbox:true},
			//加载数据的时候显示头像
			{ field:'photo',title:'用户头像',width:70,align:'center',formatter:function(value,row,index){
				var img = '<img src="'+value+'" width="80px"/>';
				return img;
			}},
			{ field:'username',title:'用户名',align:'center',width:100,sortable:true},
			{ field:'password',title:'密码',align:'center',width:100},
			{ field:'roleId',title:'所属角色',align:'center',width:100,formatter:function(value,row,index){
				//获取所有角色
				var roleList = $('#search-role').combobox('getData');
				for(var i=0;i<roleList.length;i++){
					if(value==roleList[i].value){
						return roleList[i].text;
					}
				}
			}},
			{ field:'sex',title:'性别',align:'center',width:100,formatter:function(value,row,index){
				if(value==0){
					return "女";
				}else{
					return "男";
				}
			}},
			{ field:'age',title:'年龄',align:'center',width:100},
			{ field:'address',title:'住址',align:'center',width:200},
		]],
		//将文字内容加入到按钮中，连接为一个整体
		onLoadSuccess:function(data){
			$('.authority-edit').linkbutton({
				text:'编辑权限',
				plain:true,
				iconCls:'icon-edit'
			});
		}
	});
</script>
</body>
</html>