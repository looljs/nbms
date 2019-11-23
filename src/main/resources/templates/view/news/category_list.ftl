<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="copyright" content="All Rights Reserved, Copyright (C) 2013, looli.club, Ltd." />
        <link rel="stylesheet" type="text/css" href="/static/easyui/easyui/1.3.4/themes/default/easyui.css" />
        <link rel="stylesheet" type="text/css" href="/static/easyui/css/wu.css" />
        <link rel="stylesheet" type="text/css" href="/static/easyui/css/icon.css" />
        <script type="text/javascript" src="/static/easyui/js/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/static/easyui/easyui/1.3.4/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/static/easyui/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
    </head>
<body class="easyui-layout">
    <div class="easyui-layout" data-options="fit:true">
            <!-- Begin of toolbar -->
            <div id="wu-toolbar">
                <#list news_category as m>
                    <a href="#" class="easyui-linkbutton" iconCls="${m.icon}" onclick="${m.url}" plain="true">${m.name}</a>
                </#list>
                <div class="wu-toolbar-search">
                    <label>分类名:</label><input id="search-name" class="wu-text" style="width:100px">
                    <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
                </div>
            </div>
            <!-- End of toolbar -->
            <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
        </div>
    <!-- Begin of easyui-dialog -->
    <div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:420px; padding:10px;">
            <form id="add-form" method="post">
                <table>
                    <tr>
                        <td width="60" align="right">分类名:</td>
                        <td><input type="text" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写分类名'" /></td>
                    </tr>
                    <tr>
                        <td width="60" align="right">排序:</td>
                        <td><input type="text" name="sort" class="wu-text easyui-numberbox easyui-validatebox" value="0" data-options="required:true, missingMessage:'请填写排序'" /></td>
                    </tr>
                </table>
            </form>
        </div>
    <!-- 修改窗口 -->
    <div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
            <form id="edit-form" method="post">
                <input type="hidden" name="id" id="edit-id">
                <table>
                    <tr>
                        <td width="60" align="right">分类名:</td>
                        <td><input type="text" id="edit-name" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写分类名'" /></td>
                    </tr>
                    <tr>
                        <td width="60" align="right">排序:</td>
                        <td><input type="text" id="edit-sort" name="sort" class="wu-text easyui-validatebox" value="0" data-options="required:true, missingMessage:'请填写排序'" /></td>
                    </tr>
                </table>
            </form>
    </div>
    <!-- End of easyui-dialog -->
    <div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#F9F9F9;text-align :center;padding-top:20%;">
        <img src="/static/easyui/images/load-page.gif" width="50%">
        <h1><font color="#15428B">加载中....</font></h1>
    </div>
</body>
    <script type="text/javascript">


        /**
         *  添加记录
         */
        function add(){
            var validate = $("#add-form").form("validate");
            if(!validate){
                $.messager.alert("消息提醒","请检查你输入的数据!","warning");
                return;
            }
            var data = $("#add-form").serialize();
            $.ajax({
                url:'add',
                dataType:'json',
                type:'post',
                data:data,
                success:function(data){
                    if(data.type == 'success'){
                        $.messager.alert('信息提示','添加成功！','info');
                        $('#add-dialog').dialog('close');
                        $('#data-datagrid').datagrid('reload');
                    }else{
                        $.messager.alert('信息提示',data.msg,'warning');
                    }
                }
            });
        }

        /**
         * Name 修改记录
         */
        function edit(){
            var validate = $("#edit-form").form("validate");
            if(!validate){
                $.messager.alert("消息提醒","请检查你输入的数据!","warning");
                return;
            }
            var data = $("#edit-form").serialize();
            $.ajax({
                url:'edit',
                dataType:'json',
                type:'post',
                data:data,
                success:function(data){
                    if(data.type == 'success'){
                        $.messager.alert('信息提示','修改成功！','info');
                        $('#edit-dialog').dialog('close');
                        $('#data-datagrid').datagrid('reload');
                    }else{
                        $.messager.alert('信息提示',data.msg,'warning');
                    }
                }
            });
        }

        /**
         * 删除记录
         */
        function remove(){
            $.messager.confirm('信息提示','确定要删除该记录？', function(result){
                if(result){
                    var item = $('#data-datagrid').datagrid('getSelections');
                    if(item == null || item.length == 0){
                        $.messager.alert('信息提示','请选择要删除的数据！','info');
                        return;
                    }
                    $.ajax({
                        url:'delete',
                        dataType:'json',
                        type:'post',
                        data:{id:item[0].id},
                        success:function(data){
                            if(data.type == 'success'){
                                $.messager.alert('信息提示','删除成功！','info');
                                $('#data-datagrid').datagrid('reload');
                            }else{
                                $.messager.alert('信息提示',data.msg,'warning');
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
            //$('#add-form').form('clear');
            $('#add-dialog').dialog({
                closed: false,
                modal:true,
                title: "添加分类信息",
                buttons: [{
                    text: '确定',
                    iconCls: 'icon-ok',
                    handler: add
                }, {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        $('#add-dialog').dialog('close');
                    }
                }],
                onBeforeOpen:function(){
                    //$("#add-form input").val('');
                }
            });
        }

        /**
         * 打开修改窗口
         */
        function openEdit(){
            //$('#edit-form').form('clear');
            var item = $('#data-datagrid').datagrid('getSelections');
            if(item == null || item.length == 0){
                $.messager.alert('信息提示','请选择要修改的数据！','info');
                return;
            }
            if(item.length > 1){
                $.messager.alert('信息提示','请选择一条数据进行修改！','info');
                return;
            }
            item = item[0];
            $('#edit-dialog').dialog({
                closed: false,
                modal:true,
                title: "修改分类信息",
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
                    $("#edit-id").val(item.id);
                    $("#edit-name").val(item.name);
                    $("#edit-sort").val(item.sort);
                }
            });
        }


        //搜索按钮监听
        $("#search-btn").click(function(){
            var option = {name:$("#search-name").val()};
            $('#data-datagrid').datagrid('reload',option);
        });

        /**
         * 载入数据
         */
        $('#data-datagrid').datagrid({
            url:'list',
            rownumbers:true,
            singleSelect:true,
            pageSize:20,
            pagination:true,
            multiSort:true,
            fitColumns:true,
            idField:'id',
            treeField:'name',
            fit:true,
            columns:[[
                { field:'chk',checkbox:true},
                { field:'name',title:'分类名',width:100,sortable:true,formatter:function(value,row,index){
                        return '<a href="../../news/category_list?cid='+row.id+'" target="_blank">' + value + '</a>';
                    }},
                { field:'sort',title:'排序',sortable:true,width:100},
            ]],
        });
    </script>
<script>
        var pc;
        //不要放在$(function(){});中
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
