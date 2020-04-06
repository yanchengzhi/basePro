<%@ page language="java" contentType="text/html; charset=UTF-8"  isErrorPage="true"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
  .selected{
    background:orange;
  }
</style>
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar">
        <div class="wu-toolbar-button">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
        </div>
        <div class="wu-toolbar-search">
            <label>菜单名称：</label><input class="wu-text" id="search-name" style="width:100px">
            <a href="#" class="easyui-linkbutton" id="search-btn" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<!-- Begin of easyui-dialog -->
<div id="wu-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
	<form id="add-form" method="post">
        <table>
            <tr>
                <td width="60" align="right">菜单名称:</td>
                <td><input type="text" name="name" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写菜单名称！' "/></td>
            </tr>
            <tr>
                <td align="right">上级菜单:</td>
                <td>           
                <select class="easyui-combobox" name="parentId" panelHeight="auto" style="width:268px">
                   <!-- 遍历父菜单 -->
                   <c:forEach items="${topList}" var="menu">
                      <option value="${menu.id}">${menu.name}</option>
                   </c:forEach>
                </select>
                </td>
            </tr>
            <tr>
                <td align="right">菜单URL:</td>
                <td><input type="text" name="url" class="wu-text" /></td>
            </tr>
            <tr>
                <td valign="top" align="right">菜单图标:</td>
                <td>
                   <input type="text" name="icon" id="add-icon" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写菜单图标！' "/>
                   <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="selectIcon()" plain="true">选择</a>
                   </td>
            </tr>
        </table>
    </form>
</div>

<!-- 选择图标弹窗 -->
<div id="select-icon-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:670px;height:450px;padding:10px;">
   <table id="icons-table">
   </table>
</div>
<!-- End of easyui-dialog -->
<script type="text/javascript">
	/**
	* Name 添加记录
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
			url:'${APP_PATH}/menu/add',
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
		$('#select-icon-dialog').dialog({
			closed: false,
			modal:true,
            title: "选择icon信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                //点击确定时
                handler: function(){
                	var icon = $('.selected a').attr('class');//获取选中图标的class值
                	$('#add-icon').val(icon);//将值填充到文本框中
                	$('#select-icon-dialog').dialog('close');//关闭弹窗
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#select-icon-dialog').dialog('close');                    
                }
            }]
        });
	}
	
	function selected(e){
		$('.icon-td').removeClass('selected');
		$(e).parent('td').addClass('selected');
	}
	
	/**
	* Name 修改记录
	*/
	function edit(){
		$('#add-form').form('submit', {
			url:'',
			success:function(data){
				if(data){
					$.messager.alert('信息提示','提交成功！','info');
					$('#wu-dialog').dialog('close');
				}
				else
				{
					$.messager.alert('信息提示','提交失败！','info');
				}
			}
		});
	}
	
	/**
	* Name 删除记录
	*/
	function remove(){
		$.messager.confirm('信息提示','确定要删除该记录？', function(result){
			if(result){
				var items = $('#data-datagrid').datagrid('getSelections');
				var ids = [];
				$(items).each(function(){
					ids.push(this.productid);	
				});
				//alert(ids);return;
				$.ajax({
					url:'',
					data:'',
					success:function(data){
						if(data){
							$.messager.alert('信息提示','删除成功！','info');		
						}
						else
						{
							$.messager.alert('信息提示','删除失败！','info');		
						}
					}	
				});
			}	
		});
	}
	
	/**
	* Name 打开添加窗口
	*/
	function openAdd(){
		$('#add-form').form('clear');
		$('#wu-dialog').dialog({
			closed: false,
			modal:true,
            title: "添加信息",
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
	* Name 打开修改窗口
	*/
	function openEdit(){
		$('#add-form').form('clear');
		var item = $('#data-datagrid').datagrid('getSelected');
		//alert(item.productid);return;
		$.ajax({
			url:'',
			data:'',
			success:function(data){
				if(data){
					$('#wu-dialog').dialog('close');	
				}
				else{
					//绑定值
					$('#add-form').form('load', data)
				}
			}	
		});
		$('#wu-dialog').dialog({
			closed: false,
			modal:true,
            title: "修改信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: edit
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#wu-dialog').dialog('close');                    
                }
            }]
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
		url:'${APP_PATH}/menu/listData',
		rownumbers:true,//是否显示行号
		singleSelect:true,//是否只支持单选
		pageSize:20,//每页显示的记录条数           
		pagination:true,//是否开启分页功能
		multiSort:true,
		fitColumns:true,//是否填充列
		fit:true,
		columns:[[
			{ checkbox:true},
			{ field:'name',title:'菜单名称',width:100,sortable:true},
			{ field:'url',title:'菜单URL',width:180,sortable:true},
			{ field:'icon',title:'菜单图标',width:100}
		]]
	});
</script>