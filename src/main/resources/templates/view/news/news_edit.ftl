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
    <script src="/admin/ueditor/ueditor.config.js"></script>
    <script src="/admin/ueditor/ueditor.all.min.js"></script>
    <script src="/admin/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body class="easyui-layout">
<div class="easyui-panel" title="添加新闻页面" iconCls="icon-add" fit="true" >
    <div style="padding:10px 60px 20px 60px">
        <form id="add-form" method="post">
            <table cellpadding="5">
                <tr>
                    <td>新闻标题:</td>
                    <td><input class="wu-text easyui-textbox easyui-validatebox" type="text" name="title" value="${news.title}" data-options="required:true,missingMessage:'请填写新闻标题'"></input></td>
                    <td><input class="wu-text easyui-textbox easyui-validatebox" type="text" name="id" hidden value="${news.id}"></input></td>
                </tr>
                <tr>
                    <td>所属分类:</td>
                    <td>
                        <input id="edit-parentId" class="easyui-combobox" value="${news.categoryId}" name="categoryId" style="width:100%;" data-options="
                            url:'/news_category/categorys',
                            method:'post',
                            valueField:'id',
                            textField:'name',
                            panelHeight:'auto',
                            labelPosition: 'top'
                            ">
                    </td>
                </tr>
                <tr>
                    <td>摘要:</td>
                    <td>
                        <textarea name="summary"  rows="6" class="wu-textarea easyui-validatebox" style="width:260px" data-options="required:true,missingMessage:'请填写新闻摘要'">
                            ${news.summary }
                        </textarea>
                    </td>
                </tr>
                <tr>
                    <td>新闻标签:</td>
                    <td><input class="wu-text easyui-textbox easyui-validatebox" type="text"  value="${news.tags}" name="tags" data-options="required:true,missingMessage:'请填写新闻标签'">

                        </input></td>
                </tr>
                <tr>
                    <td>新闻封面:</td>
                    <td>
                        <input class="wu-text easyui-textbox easyui-validatebox" type="text" id="add-photo" name="photo" readonly="readonly"  value="${news.photo}" data-options="required:true,missingMessage:'请上传封面'"></input>
                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-upload" onclick="uploadPhoto()">上传</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-eye" onclick="preview()">预览</a>
                    </td>
                </tr>
                <tr>
                    <td>新闻作者:</td>
                    <td><input class="wu-text easyui-textbox easyui-validatebox" type="text" value="${news.author}" name="author" data-options="required:true,missingMessage:'请填写新闻作者'"></input></td>
                </tr>
                <tr>
                    <td>新闻内容:</td>
                    <td>
                        <textarea id="add-content" name="content"  style="width:760px;height:300px;" >
                            ${news.content}
                        </textarea>
                    </td>
                </tr>
            </table>
        </form>
        <div style="padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="submitForm()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back"  onclick="back()">返回</a>
        </div>
    </div>
</div>
<div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#F9F9F9;text-align :center;padding-top:20%;">
    <img src="/easyui/images/load-page.gif" width="50%">
    <h1><font color="#15428B">加载中....</font></h1>
</div>
<!-- 预览图片弹窗 -->
<div id="preview-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:330px; padding:10px;">
    <table>
        <tr>
            <td><img id="preview-photo" src="/images/user_photo.jpg" width="300px"></td>
        </tr>
    </table>
</div>
<!-- 上传图片进度条 -->
<div id="process-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-upload',title:'正在上传图片'" style="width:450px; padding:10px;">
    <div id="p" class="easyui-progressbar" style="width:400px;" data-options="text:'正在上传中...'"></div>
</div>
<input type="file" id="photo-file" style="display:none;" onchange="upload()">
<!-- End of easyui-dialog -->
</body>
</html>
<script type="text/javascript">
    var ue = UE.getEditor('add-content');

    function back(){
        window.history.back(-1);
    }
    function preview(){
        $('#preview-dialog').dialog({
            closed: false,
            modal:true,
            title: "预览封面图片",
            buttons: [{
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#preview-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                //$("#add-form input").val('');
            }
        });
    }
    //上传图片
    //加载进度条设置
    function start(){
        var value = $('#p').progressbar('getValue');
        if (value < 100){
            value += Math.floor(Math.random() * 10);
            $('#p').progressbar('setValue', value);
        }else{
            $('#p').progressbar('setValue',0)
        }
    };
    function upload(){
        if($("#photo-file").val() == '')return;
        var formData = new FormData();
        formData.append('photo',document.getElementById('photo-file').files[0]);
        $("#process-dialog").dialog('open');
        var interval = setInterval(start,200);
        $.ajax({
            url:'upload_photo',
            type:'post',
            data:formData,
            contentType:false,
            processData:false,
            success:function(data){
                clearInterval(interval);
                $("#process-dialog").dialog('close');
                if(data.type == 'success'){
                    $("#preview-photo").attr('src',data.filepath);
                    $("#add-photo").val(data.filepath);
                }else{
                    $.messager.alert("消息提醒",data.msg,"warning");
                }
            },
            error:function(data){
                clearInterval(interval);
                $("#process-dialog").dialog('close');
                $.messager.alert("消息提醒","上传失败!","warning");
            }
        });
    }


    function uploadPhoto(){
        $("#photo-file").click();

    }

    //表单数据提交
    function submitForm(){
        var validate = $("#add-form").form("validate");
        if(!validate){
            $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            return;
        }
        var content = ue.getContent();
        if(content == ''){
            $.messager.alert("消息提醒","请输入新闻内容!","warning");
            return;
        }
        var data = $("#add-form").serialize();
        $.ajax({
            url:'edit',
            type:'post',
            dataType:'json',
            data:data,
            success:function(rst){
                if(rst.type == 'success'){
                    $.messager.alert("消息提醒","修改成功!","warning");
                    setTimeout(function(){
                        window.history.go(-1);
                    },500);
                }else{
                    $.messager.alert("消息提醒",rst.msg,"warning");
                }
            }
        });
    };
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