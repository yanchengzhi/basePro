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
            <c:forEach items="${thirdMenus}" var="tMenu">
               <a href="#" class="easyui-linkbutton" iconCls="${tMenu.icon}" onclick="${tMenu.url}" plain="true">${tMenu.name}</a>
            </c:forEach>
        </div>
        <div class="wu-toolbar-search">
            <label>菜品分类名称：</label><input class="wu-text" id="search-name" style="width:100px">&nbsp;&nbsp;&nbsp;
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
                <td valign="top" align="right">菜品分类名称:</td>
                <td><input type="text" name="name" id="name" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写菜品分类名称！' "/></td>
            </tr>
            <tr>
                <td valign="top" align="right">备注信息:</td>
                <td><textarea name="remark" id="remark" rows="6" class="wu-textarea" style="width:260px"></textarea></td>
            </tr> 
        </table>
    </form>
</div>

<!-- 修改弹窗 -->
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
	<form id="edit-form" method="post">
	    <input type="hidden" name="id" id="edit-id" class="wu-text"/>  
        <table>         
            <tr>
                <td valign="top" align="right">菜品分类名称:</td>
                <td>
                <input type="text" name="name" id="edit-name" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写菜品分类名称！' "/></td>
            </tr> 
            <tr>
                <td valign="top" align="right">备注信息:</td>
                <td><textarea name="remark" id="edit-remark" rows="6" class="wu-textarea" style="width:260px"></textarea></td>
            </tr> 
        </table>
    </form>
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
			url:'${APP_PATH}/foodCategory/add',
			type:"POST",
			data:data,
			success:function(result){
				if(result.success){
					$.messager.alert('信息提示','添加成功！','info');
					//清空菜品分类内容
					$('#content').val("");
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
		var validate = $('#edit-form').form("validate");
		if(!validate){
			$.messager.alert("消息提示","请检查你的数据！","warning");
			return;
		}
		//序列化表单数据
		var data = $('#edit-form').serialize();
		$.ajax({
			url:'${APP_PATH}/foodCategory/edit',
			type:"POST",
			data:data,
			success:function(result){
				if(result.success){
					$.messager.alert('信息提示','修改成功！','info');
					//清空菜品分类内容
					$('#edit-name').val("");
					$('#edit-remark').val("");
					$('#edit-dialog').dialog('close');
					//添加成功后重载数据
					$('#data-datagrid').datagrid('reload');
				}else{
					$.messager.alert('信息提示','修改失败！','info');
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
			return;
			}
		$.messager.confirm('信息提示','确定要删除菜品分类【'+item.name+'】？', function(result){
		    if(result){
			$.ajax({
					url:"${APP_PATH}/foodCategory/delete",
					type:"POST",
					data:{
						"id":item.id,
						"name":item.name
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
            title: "添加菜品分类信息",
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
            onBeforeOpen:function(){
            	//清空
            	$('#name').val("");
            	$('#remark').val("");
            }
        });
	}
	
	//打开修改窗口
	function openEdit(){
		//获取选中的记录
    	var item = $('#data-datagrid').datagrid('getSelected');
		if(item==null){
			$.messager.alert('提示信息','请选择要修改的数据！','info');
			return;
		}
		$('#edit-dialog').dialog({
			closed: false,
			modal:true,
            title: "修改菜品分类信息",
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
            onBeforeOpen:function(){
            	//数据回显
            	$("#edit-id").val(item.id);
            	$('#edit-name').val(item.name);
            	$('#edit-remark').val(item.remark);
            }
        });
	}
	
	
	//实现模糊查询
		$('#search-btn').click(function(){
			var name = $('#search-name').val();
			var option = {
		       name:name
			};
			$('#data-datagrid').datagrid('reload',option);
	});
	
	
	/**
	* Name 载入数据
	*/
	$('#data-datagrid').datagrid({
		url:'${APP_PATH}/foodCategory/listData',
		rownumbers:true,//是否显示行号
		singleSelect:true,//是否只支持单选,true支持单选，false支持多选
		pageSize:20,//每页显示的记录条数           
		pagination:true,//是否开启分页功能
		multiSort:true,
		fitColumns:true,//是否填充列
		fit:true,
		columns:[[
			{ field:'chk',checkbox:true},
			{ field:'name',title:'菜品分类内容',align:'center',width:100,sortable:true},
			{ field:'remark',title:'备注信息',align:'center',width:100,sortable:true},
		]],	
	});
</script>
</body>
</html>