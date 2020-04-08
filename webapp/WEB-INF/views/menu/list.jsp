<%@ page language="java" contentType="text/html; charset=UTF-8"  isErrorPage="true"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
  .selected{
    background:orange;
  }
</style>
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
            <a href="#" class="easyui-linkbutton" iconCls="icon-add1" onclick="openAddMenu()" plain="true">添加按钮</a>        
        </div>
        <div class="wu-toolbar-search">
            <label>菜单名称：</label><input class="wu-text" id="search-name" style="width:100px">
            <a href="#" class="easyui-linkbutton" id="search-btn" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-treegrid" toolbar="#wu-toolbar"></table>
</div>
<!-- 添加弹窗 -->
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
                   <option value="0">顶级菜单</option>
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
                <td align="right">菜单图标:</td>
                <td>
                   <input type="text" name="icon" id="add-icon" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写菜单图标！' "/>
                   <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="selectIcon()" plain="true">选择</a>
                   </td>
            </tr>
        </table>
    </form>
</div>

<!-- 添加按钮弹窗 -->
<div id="add-menu-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
	<form id="add-menu-form" method="post">
        <table>
            <tr>
                <td width="60" align="right">按钮名称:</td>
                <td><input type="text" name="name" id="name" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写按钮名称！' "/></td>
            </tr>
            <tr>
                <td width="60" align="right">上级菜单:</td>
                <td>
                    <input type="text" id="parent-menu" readonly="readonly" class="wu-text easyui-validatebox"/>
                    <input type="hidden" name="parentId" id="add-menu-parentId"/>
                </td>
            </tr>
            <tr>
                <td align="right">按钮事件:</td>
                <td><input type="text" name="url" class="wu-text" /></td>
            </tr>
            <tr>
                <td align="right">按钮图标:</td>
                <td>
                   <input type="text" name="icon" id="add-menu-icon" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写菜单图标！' "/>
                   <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="selectIcon()" plain="true">选择</a>
                   </td>
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
                <td width="60" align="right">菜单名称:</td>
                <td><input type="text" name="name" id="edit-name" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写菜单名称！' "/></td>
            </tr>
            <tr>
                <td align="right">上级菜单:</td>
                <td>           
                <select class="easyui-combobox" name="parentId" id="edit-parentId" panelHeight="auto" style="width:268px">
                   <!-- 遍历父菜单 -->
                   <option value="0">顶级菜单</option>
                   <c:forEach items="${topList}" var="menu">
                      <option value="${menu.id}">${menu.name}</option>
                   </c:forEach>
                </select>
                </td>
            </tr>
            <tr>
                <td align="right">菜单URL:</td>
                <td><input type="text" name="url" id="edit-url" class="wu-text" /></td>
            </tr>
            <tr>
                <td align="right">菜单图标:</td>
                <td>
                   <input type="text" name="icon" id="edit-icon" class="wu-text easyui-validatebox" data-options="required:true,missingMessage:'请填写菜单图标！' "/>
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
					$('#data-datagrid').treegrid('reload');
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
                	$('#add-menu-icon').val(icon);//将值填充到文本框中
                	$('#edit-icon').val(icon);//将值填充到文本框中
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
		var validate = $('#edit-form').form('validate');
		if(!validate){
			$.messager.alert('提示信息','请检查你的数据！','warning');
		}
		//序列化表单
		var data = $('#edit-form').serialize();
		$.ajax({
			url:"${APP_PATH}/menu/edit",
			type:"POST",
			data:data,
			success:function(result){
				if(result.success){
					$.messager.alert('提示信息','修改成功！','info');
					//关闭弹窗
					$('#edit-dialog').dialog('close');
					//修改成功后重载数据
					$('#data-datagrid').treegrid('reload');
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
					url:"${APP_PATH}/menu/delete",
					type:"POST",
					data:{
						"id":Number(item.id)
					},
					success:function(result2){
						if(result2.success){
							$.messager.alert('提示信息','删除成功！','info');
							//修改成功后重载数据
							$('#data-datagrid').treegrid('reload');
						}else{
							$.messager.alert('提示信息',result2.data,'error');
						}
					}
				});
				
			}	
		});
	}
	
	//添加菜单弹框
	function openAddMenu(){
		//获取选中的栏目
		var item = $('#data-datagrid').treegrid('getSelected');
		//判断是否选中
		if(item==null){
			$.messager.alert('信息提示','请选择要添加菜单的数据！','info');
			return;
		}
		//判断是否为二级菜单
		if(item.parentId==0){
			$.messager.alert('信息提示','请选择二级菜单！','info');
			return;
		}
		//获取父级菜单
		var parent = $('#data-datagrid').treegrid('getParent',item.id);
		if(parent.parentId!=0){
			$.messager.alert('信息提示','请选择二级菜单！','info');
			return;
		}
		
		$('#add-menu-dialog').dialog({
			closed: false,
			modal:true,
            title: "添加按钮信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function(){
                	var validate = $('#add-menu-form').form('validate');
                	if(!validate){
                		$.messager.alert("消息提示",'请检查你的数据！','warning');
                		return;
                	}
                	//序列化表单
                    var data = $('#add-menu-form').serialize();
            		$.ajax({
            			url:'${APP_PATH}/menu/add',
            			type:"POST",
            			data:data,
            			success:function(result){
            				if(result.success){
            					$.messager.alert('信息提示','添加成功！','info');
            					$('#add-menu-dialog').dialog('close');
            					//添加成功后重载数据
            					$('#data-datagrid').treegrid('reload');
            				}else{
            					$.messager.alert('信息提示','添加失败！','info');
            				}
            			}
            		});
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#add-menu-dialog').dialog('close');                    
                }
            }],
            onBeforeOpen:function(){
            	$('#parent-menu').val(item.name);
            	$('#add-menu-parentId').val(item.id);
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
	* 打开修改窗口
	*/
	function openEdit(){
		//获取选中的记录
		var item = $('#data-datagrid').treegrid('getSelected');
		if(item==null||item.length==0){
			$.messager.alert('信息提示','请选择需要修改的数据','info');
			}
		
		$('#edit-dialog').dialog({
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
                    $('#edit-dialog').dialog('close');                    
                }
            }],
            //修改的记录数据回显
            onBeforeOpen:function(){
            	$('#edit-id').val(item.id);
            	$('#edit-name').val(item.name);
            	//下拉框赋值如下
            	$('#edit-parentId').combobox('setValue',item.parentId);
            	$('#edit-parentId').combobox('readonly',false);
            	//判断是否为按钮
            	var parent = $('#data-datagrid').treegrid('getParent',item.id);
            	if(parent!=null){//不为空说明不是父级菜单
            		if(parent.parentId!=0){//parentId不为0说明不为二级菜单
            			//确定为按钮时
            			$('#edit-parentId').combobox('setText',parent.name);
            			$('#edit-parentId').combobox('readonly',true);//不允许修改
            		}
            	}
            	$('#edit-url').val(item.url);
            	$('#edit-icon').val(item.icon);
            }
        });
	}	
	
	//实现模糊查询
		$('#search-btn').click(function(){
		$('#data-datagrid').treegrid('reload',{
			name:$('#search-name').val()
		});
	});
	
	
	/**
	* Name 载入数据
	*/
	$('#data-datagrid').treegrid({
		url:'${APP_PATH}/menu/listData',
		rownumbers:true,//是否显示行号
		singleSelect:true,//是否只支持单选
		pageSize:20,//每页显示的记录条数           
		pagination:true,//是否开启分页功能
		multiSort:true,
		fitColumns:true,//是否填充列
		idField:"id",
		treeField:"name",
		fit:true,
		columns:[[
			{ field:'name',title:'菜单名称',width:100,sortable:true},
			{ field:'url',title:'菜单URL',width:180,sortable:true},
			//icon图标添加一个预览功能
			{ field:'icon',title:'菜单图标',width:100,formatter:function(value,index,row){
				var test = '<a class='+value+'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>';
				return test + value;
			}}
		]]
	});
</script>