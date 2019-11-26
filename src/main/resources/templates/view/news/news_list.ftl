<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="copyright" content="All Rights Reserved, Copyright (C) 2013, looli.club, Ltd." />
    <link rel="stylesheet" type="text/css" href="/easyui/easyui/1.3.4/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="/easyui/css/wu.css" />
    <link rel="stylesheet" type="text/css" href="/easyui/css/icon.css" />
    <script type="text/javascript" src="/easyui/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/easyui/easyui/1.3.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/easyui/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar">
        <div class="wu-toolbar-button">
            <#list news as m>
                <a href="#" class="easyui-linkbutton" iconCls="${m.icon}" onclick="${m.url}" plain="true">${m.name}</a>
            </#list>
        </div>
        <div class="wu-toolbar-search">
            <label>新闻标题:</label><input id="search-title" class="wu-text" style="width:100px">
            <label>新闻作者:</label><input id="search-author" class="wu-text" style="width:100px">
            <label>所属分类:</label>
            <select id="search-category" class="easyui-combobox" panelHeight="auto" style="width:120px">
                <option value="-1">全部</option>
            </select>
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#F9F9F9;text-align :center;padding-top:20%;">
    <img src="/easyui/images/load-page.gif" width="50%">
    <h1><font color="#15428B">加载中....</font></h1>
</div>
</body>
</html>
<script type="text/javascript">
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
     * 打开添加窗口
     */
    function openAdd(){
        window.location.href = 'add';
    }

    /**
     * 打开修改窗口
     */
    function openEdit(){
        var item = $('#data-datagrid').datagrid('getSelected');
        if(item == null || item.length == 0){
            $.messager.alert('信息提示','请选择要修改的数据！','info');
            return;
        }
        window.location.href = 'edit?id=' + item.id;
    }


    //搜索按钮监听
    $("#search-btn").click(function(){
        var option = {title:$("#search-title").val(),categoryId:$("#search-category").combobox('getValue'),author:$("#search-author").val()};
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
        // treeField:'name',
        remoteSort: false,
        striped: true,
        // fit:true,
        selectOnCheck: true,
        columns:[[
            { field:'chk',checkbox:true},
            { field:'title',title:'标题',width:300,formatter:function(value,row,index){
                    return '<a href="../../news/detail?id='+row.id+'" target="_blank">' + value + '</a>';
                }},
             // { field:'categoryId',title:'分类',width:80,formatter:function(value,row,index){
             //         return row.newsCategory.name;
             //     }},
            { field:'author',title:'作者',width:80},
             // { field:'tags',title:'标签',width:100},
             { field:'viewNumber',title:'浏览量',sortable:true,width:30},
             { field:'commentNumber',title:'评论数',sortable:true,width:30},
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