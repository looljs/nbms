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
            <#list commentList as m>
                <a href="#" class="easyui-linkbutton" iconCls="${m.icon}" onclick="${m.url}" plain="true">${m.name}</a>
            </#list>
        </div>
        <div class="wu-toolbar-search">
            <label>昵称:</label><input id="search-nickname" class="wu-text" style="width:100px">
            <label>内容:</label><input id="search-content" class="wu-text" style="width:100px">
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
                <td width="60" align="right">评论新闻:</td>
                <td>
                    <input id="edit-parentId" class="easyui-combobox"  name="newsId" style="width:268px" data-options="
                            url:'/news/list2',
                            method:'post',
                            valueField:'id',
                            textField:'title',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            required:true,
                            missingMessage:'请选择要评论的新闻'
                            ">
                </td>
            </tr>
            <tr>
                <td width="60" align="right">昵称:</td>
                <td><input type="text" name="nickname" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写评论昵称'" /></td>
            </tr>
            <tr>
                <td>内容:</td>
                <td>
                    <textarea name="content" rows="6" class="wu-textarea easyui-validatebox" style="width:260px" data-options="required:true,missingMessage:'请填写评论内容'"></textarea>
                </td>
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
                <td width="60" align="right">评论新闻:</td>
                <td>
                    <input id="edit-newsId" class="easyui-combobox" name="newsId" style="width:268px;" data-options="
                            url:'/news/list2',
                            method:'post',
                            valueField:'id',
                            textField:'title',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            required:true,
                            missingMessage:'请选择要评论的新闻'
                            ">
                </td>
            </tr>
            <tr>
                <td width="60" align="right">昵称:</td>
                <td><input type="text" id="edit-nickname" name="nickname" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写评论昵称'" /></td>
            </tr>
            <tr>
                <td>内容:</td>
                <td>
                    <textarea id="edit-content" name="content" rows="6" class="wu-textarea easyui-validatebox" style="width:260px" data-options="required:true,missingMessage:'请填写评论内容'"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#F9F9F9;text-align :center;padding-top:20%;">
    <img src="/easyui/images/load-page.gif" width="50%">
    <h1><font color="#15428B">加载中....</font></h1>
</div>
</body>
</html>
<!-- End of easyui-dialog -->
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
                // var ids = '';
                // for(var i=0;i<item.length;i++){
                //     ids += item[i].id + ',';
                // }
                var ids = [];
                for (var i = 0; i < item.length;i++){
                    ids[i] = item[i].id
                }
                $.ajax({
                    url:'delete',
                    dataType:'json',
                    type:'post',
                    data:{ids:ids},
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

        // console.log(list);
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
        // console.log(item);
        // console.log(item.newsId);
        // url = "/news/get?newsId="+item.newsId;
        // console.log(url);
        // $.ajax({
        //     type: "post",
        //     url: "/news/get",//请求后台数据
        //     data: {newsId: item.newsId},
        //     dataType: "json",
        //     success: function (json) {
        //         console.log(json);
        //     }
        // });

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
                $("#edit-nickname").val(item.nickname);
                $("#edit-content").val(item.content);
                $("#edit-newsId").combobox('setValue',item.newsId);
            }
        });
    }


    //搜索按钮监听
    $("#search-btn").click(function(){
        var option = {nickname:$("#search-nickname").val(),content:$("#search-content").val()};
        $('#data-datagrid').datagrid('reload',option);
    });

    function add0(m){return m<10?'0'+m:m }
    function format(shijianchuo){
        //shijianchuo是整数，否则要parseInt转换
        var time = new Date(shijianchuo);
        var y = time.getFullYear();
        var m = time.getMonth()+1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
    }

    /**
     * 载入数据
     */
    $('#data-datagrid').datagrid({
        url:'list',
        rownumbers:true,
        singleSelect:false,
        pageSize:20,
        pagination:true,
        multiSort:true,
        fitColumns:true,
        idField:'id',
        treeField:'name',
        fit:true,
        columns:[[
            { field:'chk',checkbox:true},
            { field:'newsId',title:'评论新闻',width:100,formatter:function(value,row,index){
                    return row.news.title;
                }},
            { field:'nickname',title:'评论昵称',width:100,sortable:true},
            { field:'content',title:'评论内容',sortable:true,width:100},
            { field:'createTime',title:'评论时间',sortable:true,width:100,formatter:function(value,row,index){
                    return format(value);
                }},
        ]],
    });

    // //显示新闻下拉列表
    // $(function() {
    //     $.ajax({
    //         type: "post",
    //         url: "/news/list",//请求后台数据
    //         dataType: "json",
    //         success: function (json) {
    //             list = json;
    //             $("#search-category").combobox({//往下拉框塞值
    //                 data: json,
    //                 valueField: "id",//value值
    //                 textField: "title",//文本值
    //                 panelHeight: "auto"
    //             })
    //         }
    //     });
    // });
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