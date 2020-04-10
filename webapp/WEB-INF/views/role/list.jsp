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
<style>
  .selected{
    background:orange;
  }
</style>
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
            <c:forEach items="${thirdMenus}" var="tMenu">
               <a href="#" class="easyui-linkbutton" iconCls="${tMenu.icon}" onclick="${tMenu.url}" plain="true">${tMenu.name}</a>
            </c:forEach>
        </div>
        <div class="wu-toolbar-search">
            <label>角色名称：</label><input class="wu-text" id="search-name" style="width:100px">
            <a href="#" class="easyui-linkbutton" id="search-btn" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<!-- 添加弹窗 -->
<div id="wu-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
	<form id="add-form" method="post">
        <table>
            <tr>
                <td width="60" align="right">角色名称:</td>
                <td><input type="text" name="name" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写角色名称！' "/></td>
            </tr>           
            <tr>
                <td valign="top" align="right">角色备注:</td>
                <td><textarea name="remark" rows="6" class="wu-textarea" style="width:260px"></textarea></td>
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
                <td width="60" align="right">角色名称:</td>
                <td><input type="text" name="name" id="edit-name" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写角色名称！' "/></td>
            </tr>           
            <tr>
                <td valign="top" align="right">角色备注:</td>
                <td><textarea name="remark" id="edit-remark" rows="6" class="wu-textarea" style="width:260px"></textarea></td>
            </tr>
        </table>
    </form>
</div>

<!-- 选择权限弹窗 -->
<div id="select-authority-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:220px;height:300px;padding:10px;">
   <!-- easyui的Tree容器 -->
   <ul id="authority-tree" url="${APP_PATH}/auth/getAllMenu" checkbox="true"></ul>
</div>
<!-- End of easyui-dialog -->
<script type="text/javascript">
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
			url:'${APP_PATH}/role/add',
			type:"POST",
			data:data,
			success:function(result){
				if(result.success){
					$.messager.alert('信息提示','添加成功！','info');
					$('#wu-dialog').dialog('close');
					//添加成功后重载数据
					$('#data-datagrid').datagrid('reload');
				}else{
					$.messager.alert('信息提示','添加失败！','info');
				}
			}
		});
	}
	
	//选择icon图标弹窗
	function selectIcon(){
		if($('#icons-table').children().length==0){
		$.ajax({
			url:"${APP_PATH}/menu/getIcons",
			type:"POST",
			success:function(result){
				if(result.success){
				   var icons = result.data;
				   var table = "";
				   for(var i=0;i<icons.length;i++){
					   var tbody = '<td class="icon-td"><a onclick="selected(this)" href="javascript:void(0)" class="'+icons[i]+'">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></td>'
				       if(i==0){
				    	   table+='<tr style="line-height:20px;">'+tbody;
				    	   continue;
				       }
					   if((i+1)%24==0){//24个图标一行
						   table+=tbody+'</tr><tr style="line-height:20px;">';
						   continue;
					   }
					   table+=tbody;
				   }
				   table+='</tr>';
				   $('#icons-table').append(table);
				}else{
					
				}
			}
		});
		}
		$('#select-authority-dialog').dialog({
			closed: false,
			modal:true,
            title: "选择权限信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                //点击确定时
                handler: function(){
                	var icon = $('.selected a').attr('class');//获取选中图标的class值
                	$('#add-icon').val(icon);//将值填充到文本框中
                	$('#edit-icon').val(icon);//将值填充到文本框中
                	$('#select-authority-dialog').dialog('close');//关闭弹窗
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#select-authority-dialog').dialog('close');                    
                }
            }]
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
			url:"${APP_PATH}/role/edit",
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
					$.messager.alert('提示信息','修改失败！','info');
				}
			}
		});
	}
	
	/**
	* 删除记录
	*/
	function remove(){
		var item = $('#data-datagrid').datagrid('getSelected');
		if(item==null||item.length==0){
			$.messager.alert('信息提示','请选择需要删除的数据！','info');
			}
		$.messager.confirm('信息提示','确定要删除【'+item.name+'】记录？', function(result){
			if(result){
				$.ajax({
					url:"${APP_PATH}/role/delete",
					type:"POST",
					data:{
						"id":Number(item.id)
					},
					success:function(result2){
						if(result2.success){
							$.messager.alert('提示信息','删除成功！','info');
							//修改成功后重载数据
							$('#data-datagrid').datagrid('reload');
						}else{
							$.messager.alert('提示信息',result2.data,'error');
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
            title: "添加角色信息",
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
            }]
        });
	}
	
	/**
	* 打开修改窗口
	*/
	function openEdit(){
		//获取选中的记录
		var item = $('#data-datagrid').datagrid('getSelected');
		if(item==null||item.length==0){
			$.messager.alert('信息提示','请选择需要修改的数据！','info');
			}
		
		$('#edit-dialog').dialog({
			closed: false,
			modal:true,
            title: "修改角色信息",
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
            	$('#edit-name').val(item.name);
            	$('#edit-remark').val(item.remark);
            }
        });
	}	
	
	//角色已有权限
	var existAuth = null;
	function isAdded(row,rows){
		for(var k=0;k<existAuth.length;k++){
			if(existAuth[k].menuId==row.id && haveParent(rows,row.parentId)){
				return true;
			}
		}
		return false;
	}
	
	//判断是否有顶级父类
	function exists(rows, parentId){
		for(var i=0; i<rows.length; i++){
			if (rows[i].id == parentId) return true;
		}
		return false;
	}
	
	//判断父分类是否还有父类
	function haveParent(rows,parentId){
		for(var i=0;i<rows.length;i++){
			if(rows[i].id==parentId){
				if(rows[i].parentId!=0)return true;
			}
		}
		return false;
	}
	
	function convert(rows){	
		var nodes = [];
		//获取所有的父类，放到nodes中
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			if (!exists(rows, row.parentId)){
				nodes.push({
					id:row.id,
					text:row.name
				});
			}
		}
		
		//将nodes中的内容复制到toDo中
		var toDo = [];
		for(var i=0; i<nodes.length; i++){
			toDo.push(nodes[i]);
		}
		while(toDo.length){
			var node = toDo.shift();	// the parent node
			// get the children nodes
			for(var i=0; i<rows.length; i++){
				var row = rows[i];
				if (row.parentId == node.id){
					var child = {id:row.id,text:row.name};
					//添加选中属性
					if(isAdded(row,rows)){
						child.checked = true;
					}
					if (node.children){
						node.children.push(child);
					} else {
						node.children = [child];
					}
					//把刚创建的子节点加到父类中
					toDo.push(child);
				}
			}
		}
		return nodes;
	}
	
	//打开权限选择框
	function selectAuth(roleId){
		$('#select-authority-dialog').dialog({
			closed: false,
			modal:true,
            title: "选择权限信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                //点击确定时
                handler: function(){
                	//获取所有选中的子节点
                	var checkedNodes = $('#authority-tree').tree('getChecked');
                	var ids = '';
                	for(var i=0;i<checkedNodes.length;i++){
                		ids+=checkedNodes[i].id + ',';
                	}
                	//获取子节点的父节点（不会重复）
                	var checkedParentNodes = $('#authority-tree').tree('getChecked', 'indeterminate');
                	for(var i=0;i<checkedParentNodes.length;i++){
                		ids+=checkedParentNodes[i].id + ',';
                	}
                	
                	//执行添加            
                	if(ids!=""){//先进行非空判断
                	$.ajax({
                		url:"${APP_PATH}/auth/add",
                		type:"POST",
                		data:{
                			"ids":ids,
                			"roleId":roleId
                		},
                		success:function(result){
                			if(result.success){
                				$.messager.alert('信息提示','权限编辑成功！','info');
                				//关闭弹窗
                				$('#select-authority-dialog').dialog('close');
                			}else{
                				$.messager.alert('信息提示','权限编辑失败！','info');
                			}
                		}
                	});
                   }else{
                	   $.messager.alert('信息提示','请选择需要添加的权限！','info'); 
                   }
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#select-authority-dialog').dialog('close');                    
                }
            }],
            //打开窗口之前的操作
            onBeforeOpen:function(){
            	//查出该角色已有权限
            	$.ajax({
            		url:"${APP_PATH}/auth/getRoleAuthority",
            		type:"POST",
            		data:{
            			"roleId":roleId
            		},
            		success:function(data){
            			existAuth = data;
                        //加载树
                        $('#authority-tree').tree({
                        	loadFilter:function(rows){
                        		return convert(rows);
                        	}
                        });
            		}
            	});
            	//
            }
        });
		
	}
	
	//实现模糊查询
		$('#search-btn').click(function(){
		$('#data-datagrid').datagrid('reload',{
			name:$('#search-name').val()
		});
	});
	
	
	/**
	* Name 载入数据
	*/
	$('#data-datagrid').datagrid({
		url:'${APP_PATH}/role/listData',
		rownumbers:true,//是否显示行号
		singleSelect:true,//是否只支持单选
		pageSize:20,//每页显示的记录条数           
		pagination:true,//是否开启分页功能
		multiSort:true,
		fitColumns:true,//是否填充列
		fit:true,
		columns:[[
			{ field:'chk',checkbox:true},
			{ field:'name',title:'角色名称',align:'center',width:100,sortable:true},
			{ field:'remark',title:'角色备注',align:'center',width:180,sortable:true},
			//icon图标添加一个预览功能
			{ field:'icon',title:'角色权限',align:'center',width:100,formatter:function(value,row,index){
				var test = '<a class="authority-edit" onclick="selectAuth('+row.id+')"></a>';
				return test;
			}}
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