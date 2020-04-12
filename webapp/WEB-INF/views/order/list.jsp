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
            <label>客户名：</label><input class="wu-text" id="search-name" style="width:100px">&nbsp;&nbsp;&nbsp;
            <label>订单状态：</label>
            <select class="easyui-combobox" id="search-status" name="status" panelHeight="auto" style="width:120px">
                   <option value="-1">全部</option>
                   <option value="0">未配送</option>
                   <option value="1">配送中</option>
                   <option value="2">已完成</option>
             </select>&nbsp;&nbsp;&nbsp;
            <a href="#" class="easyui-linkbutton" id="search-btn" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>

<!-- 查看弹窗 -->
<div id="view-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:570px; padding:10px;">
   <table id="order-items" class="easyui-datagrid" style="width:530px;height:auto;">
      <thead>
         <tr>
            <th field="foodImage" width="100" align="center">菜品图片</th>
            <th field="foodName" width="100" align="center">菜品名称</th>
            <th field="foodNum" width="100" align="center">菜品数量</th>
            <th field="price" width="100" align="center">菜品单价</th>
            <th field="money" width="100" align="center">总金额</th>
         </tr>
      </thead>
      <tbody id="order-items" align="center">
      </tbody>
   </table>
</div>

<!-- 修改弹窗 -->
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
	<form id="edit-form" method="post">
	    <input type="hidden" name="id" id="edit-id" class="wu-text"/>  
        <table>         
            <tr>
                <td valign="top" align="right">收货人:</td>
                <td><input type="text" name="receiveName" id="edit-receiveName" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写收货人！' "/></td>
            </tr> 
            <tr>
                <td valign="top" align="right">联系方式:</td>
                <td><input type="text" name="phone" id="edit-phone" maxlength="11" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写收货人联系方式！' "/></td>
            </tr> 
            <tr>
                <td valign="top" align="right">收货地址:</td>
                <td><input type="text" name="address" id="edit-address" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写收货地址！' "/></td>
            </tr> 
            <tr>
               <td valign="top" align="right">订单状态:</td>
               <td>
                   <select class="easyui-combobox" id="edit-status" name="status" panelHeight="auto" style="width:120px">
                     <option value="0">未配送</option>
                     <option value="1">配送中</option>
                     <option value="2">已完成</option>
                    </select>
               </td>
            </tr>            
        </table>
    </form>
</div>

<!-- End of easyui-dialog -->
<script type="text/javascript">

	
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
		console.log(data);
		$.ajax({
			url:'${APP_PATH}/order/edit',
			type:"POST",
			data:data,
			success:function(result){
				if(result.success){
					$.messager.alert('信息提示','修改成功！','info');
					//清空菜品分类内容
					$('#edit-receiveName').val("");
					$('#edit-phone').val("");
					$('#edit-address').val("");
					$('#edit-status').val("");
					$('#edit-dialog').dialog('close');
					//添加成功后重载数据
					$('#data-datagrid').datagrid('reload');
				}else{
					$.messager.alert('信息提示','修改失败！','warning');
				}
			}
		});
	}
	
	
	/**
	* 打开查看窗口
	*/
	function openView(){
		//获取已选数据
    	var item = $('#data-datagrid').datagrid('getSelected');
		if(item==null){
			$.messager.alert('提示信息','请选择要查看的订单！','info');
			return;
		}
		$('#view-dialog').dialog({
			closed: false,
			modal:true,
            title: "查看订单商品信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function(){
                	$('#view-dialog').dialog('close');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#view-dialog').dialog('close');                    
                }
            }],
            onBeforeOpen:function(){
            	//加载行数据
            	$('#order-items').datagrid('loadData',{total:0,rows:[]});
            	//遍历子订单
            	for(var i=0;i<item.orderItems.length;i++){
            		$('#order-items').datagrid('appendRow',{
            			foodImage:'<img src="'+item.orderItems[i].foodImage+'" style="width:90px;height:50px;">',
            			foodName:item.orderItems[i].foodName,
            			foodNum:item.orderItems[i].foodNum,
            			price:item.orderItems[i].price,
            			money:item.orderItems[i].money
            		});
            	}
            }
        });
	}
	
	//打开修改窗口
	function openEdit(){
		//获取选中的记录
    	var item = $('#data-datagrid').datagrid('getSelected');
		if(item==null){
			$.messager.alert('提示信息','请选择要修改的订单！','info');
			return;
		}
		$('#edit-dialog').dialog({
			closed: false,
			modal:true,
            title: "修改订单信息",
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
            	$('#edit-receiveName').val(item.receiveName);
            	$('#edit-phone').val(item.phone);
            	$('#edit-address').val(item.address);
            	$('#edit-status').combobox('setValue',item.status);
            }
        });
	}
	
	
	//实现模糊查询
		$('#search-btn').click(function(){
			var name = $('#search-name').val();
			var option = {
		       name:name
			};
			var status = $('#search-status').combobox('getValue');
			if(status!=-1){
				option.status = status;
			}
			$('#data-datagrid').datagrid('reload',option);
	});
	
	
	/**
	* Name 载入数据
	*/
	$('#data-datagrid').datagrid({
		url:'${APP_PATH}/order/listData',
		rownumbers:true,//是否显示行号
		singleSelect:true,//是否只支持单选,true支持单选，false支持多选
		pageSize:20,//每页显示的记录条数           
		pagination:true,//是否开启分页功能
		multiSort:true,
		fitColumns:true,//是否填充列
		fit:true,
		columns:[[
			{ field:'chk',checkbox:true},
			{ field:'accountId',title:'用户名',align:'center',width:100,formatter:function(value,row,index){
				return row.account.name;
			}},
			{ field:'productNum',title:'商品数量',align:'center',width:50,sortable:true},
			{ field:'money',title:'总金额',align:'center',width:50,sortable:true},
			{ field:'status',title:'订单状态',align:'center',width:50,formatter:function(value,row,index){
				switch(value){
				case 0:{
					return "未配送";
				}
				case 1:{
					return "配送中";
				}
				case 2:{
					return "已完成";
				}
				}
			}},
			{ field:'createTimeStr',title:'下单时间',align:'center',width:100,sortable:true},
			{ field:'receiveName',title:'联系人',align:'center',width:100,sortable:true},
			{ field:'phone',title:'联系方式',align:'center',width:100,sortable:true},
			{ field:'address',title:'收货地址',align:'center',width:200,sortable:true},
		]],	
	});
</script>
</body>
</html>