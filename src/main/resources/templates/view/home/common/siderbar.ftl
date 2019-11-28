<aside class="sidebar">
    <div class="fixed">
        <div class="widget widget-tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#notice" aria-controls="notice" role="tab" data-toggle="tab" >统计信息</a></li>
                <li role="presentation"><a href="#contact" aria-controls="contact" role="tab" data-toggle="tab" >联系站长</a></li>
            </ul>
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane contact active" id="notice">
                    <h2>文章总数:
                        <span id="total-article-span">888</span>篇
                    </h2>
                    <h2>网站运行:
                        <span id="sitetime">88 </span>天</h2>
                </div>
                <div role="tabpanel" class="tab-pane contact" id="contact">
                    <h2>QQ:
                        <a href="#" target="_blank" rel="nofollow" data-toggle="tooltip" data-placement="bottom" title=""  data-original-title="QQ:2891934338">2891934338</a>
                    </h2>
                    <h2>Email:
                        <a href="#" target="_blank" data-toggle="tooltip" rel="nofollow" data-placement="bottom" title=""  data-original-title="#">2891934338@qq.com</a></h2>
                </div>
            </div>
        </div>
        <div class="widget widget_search">
            <form class="navbar-form" action="" method="get">
                <div class="input-group">
                    <input type="text" name="keyword" class="form-control" size="35" placeholder="请输入关键字" maxlength="15" autocomplete="off" value="">
                    <span class="input-group-btn">
		                <button class="btn btn-default btn-search" name="search" type="submit">搜索</button>
		            </span>
                </div>
            </form>
        </div>
    </div>
    <div class="widget widget_hot">
        <h3>最新评论文章</h3>
        <ul id="last-comment-list">


            
        </ul>
    </div>
    <div class="widget widget_sentence">
        <a href="http://www.looli.club" target="_blank" rel="nofollow" title="looli.club" >
            <img  src="/home/images/333.png" alt="looli.club" ></a>
    </div>
    <div class="widget widget_sentence">
        <a href="http://www.looli.club" target="_blank" rel="nofollow" title="looli.club" >
            <img  src="/home/images/444.png" alt="looli.club"></a>
    </div>
    <div class="widget widget_sentence">
        <h3>友情链接</h3>
        <div class="widget-sentence-link">
            <a href="http://programmer.ischoolbar.com" title="looli.club" target="_blank" >looli.club</a>&nbsp;&nbsp;&nbsp;
            <a href="http://www.ischoolbar.com" title="校园二手市场" target="_blank" >校园二手街</a>&nbsp;&nbsp;&nbsp;
        </div>
    </div>
</aside>
<script>
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
    $(document).ready(function(){
        // $.ajax({
        //     url:'../news/last_comment_list',
        //     type:'post',
        //     dataType:'json',
        //     success:function(data){
        //         if(data.type == 'success'){
        //             var newsList = data.newsList;
        //             var html = '';
        //             for(var i=0;i<newsList.length;i++){
        //                 var li = '<li><a title="'+newsList[i].title+'" href="../news/detail?id='+newsList[i].id+'" ><span class="thumbnail">';
        //                 li += '<img class="thumb" data-original="/home/images/201610181739277776.jpg" src="'+newsList[i].photo+'" alt="'+newsList[i].id+'"  style="display: block;">';
        //                 li += '</span><span class="text">'+newsList[i].title+'</span><span class="muted"><i class="glyphicon glyphicon-time"></i>';
        //                 li += format(newsList[i].createTime) + '</span><span class="muted"><i class="glyphicon glyphicon-eye-open"></i>'+newsList[i].viewNumber+'</span></a></li>';
        //                 html += li;
        //             }
        //             $("#last-comment-list").append(html);
        //         }
        //     }
        // });
        // $.ajax({
        //     url:'../index/get_site_info',
        //     type:'post',
        //     dataType:'json',
        //     success:function(data){
        //         if(data.type == 'success'){
        //             $("#total-article-span").text(data.totalArticle);
        //             $("#sitetime").text(data.siteDays);
        //         }
        //     }
        // });
    });
</script>