<#include "../common/header.ftl">
<section class="container">
    <div class="content-wrap">
        <div class="content" id="news-list">
            <div class="title">
                <h3>${keyword}</h3>
            </div>
            <#list newsList as news>
                <article class="excerpt excerpt-1" style="">
                    <a class="focus" href="../news/detail?id=${news.id }" title="${news.title }" target="_blank" >
                        <img class="thumb" data-original="${news.photo }" src="${news.photo }" alt="${news.title }"  style="display: inline;">
                    </a>
                    <header>
                        <a class="cat" href="../news/category_list?cid=${news.categoryId }" title="${newsCategoryMap['${news.categoryId}']}" >${newsCategoryMap['${news.categoryId}']}<i></i></a>
                        <h2><a href="../news/detail?id=${news.id }" title="${news.title }" target="_blank" >${news.title }</a>
                        </h2>
                    </header>
                    <p class="meta">
                        <time class="time"><i class="glyphicon glyphicon-time"></i> ${news.createTime?string('yyyy-MM-dd hh:mm:ss')}</time>
                        <span class="views"><i class="glyphicon glyphicon-eye-open"></i> ${news.pageViews }</span>
                        <a class="comment" href="../news/detail?id=${news.id }#comment" title="评论" target="_blank" ><i class="glyphicon glyphicon-comment"></i>${news.commentVolume }</a>
                    </p>
                    <p class="note">${news.summary }</p>
                </article>
            </#list>
            <div class="ias_trigger"><a href="javascript:;" id="load-more-btn">查看更多</a></div>
        </div>
    </div>
    <#include "../common/siderbar.ftl">
</section>
<#include "../common/footer.ftl">
<script>
    var page = 2;
    $(document).ready(function(){
        $("#load-more-btn").click(function(){
            if($("#load-more-btn").attr('data-key') == 'all')return;
            $("#load-more-btn").text('查看更多');
            $.ajax({
                url:'../news/get_search_list',
                type:'post',
                data:{rows:3,page:page++,keyword:'${keyword}'},
                dataType:'json',
                success:function(data){
                    if(data.type == 'success'){
                        $("#load-more-btn").text('查看更多!');
                        var newsList = data.newsList;
                        var newsCategoryMap = data.newsCategoryMap;
                        if(newsList.length == 0){
                            $("#load-more-btn").text('没有更多了!');
                            $("#load-more-btn").attr('data-key','all');
                        }
                        var html = '';
                        for(var i=0;i<newsList.length;i++){
                            var article = '<article class="excerpt excerpt-1" style="">';
                            article +='<a class="focus" href="../news/detail?id='+newsList[i].id+'" title="'+newsList[i].title+'" target="_blank" >';
                            article +='<img class="thumb" data-original="'+newsList[i].photo+'" src="'+newsList[i].photo+'" alt="'+newsList[i].title+'"  style="display: inline;"></a>';
                            article +='<header><a class="cat" href="../news/category_list?cid='+newsList[i].categoryId+'" title="'+newsCategoryMap[newsList[i].categoryId]+'" >'+newsCategoryMap[newsList[i].categoryId]+'<i></i></a>';
                            article +='<h2><a href="../news/detail?id='+newsList[i].id+'" title="'+newsList[i].title+'" target="_blank" >'+newsList[i].title+'</a></h2></header>';
                            article +='<p class="meta"><time class="time"><i class="glyphicon glyphicon-time"></i>'+format(newsList[i].createTime)+'</time>';
                            article +='<span class="views"><i class="glyphicon glyphicon-eye-open"></i> '+newsList[i].pageViews+'</span>';
                            article +='<a class="comment" href="../news/detail?id='+newsList[i].id+'#comment" title="评论" target="_blank" ><i class="glyphicon glyphicon-comment"></i>'+newsList[i].commentVolume+'</a></p>';
                            article +='<p class="note">'+newsList[i].summary+'</p>';
                            article +='</article>';
                            html += article;
                        }
                        $("#load-more-btn").parent("div").before(html);
                    }
                }
            });
        });
    });
</script>