<#include "../common/header.ftl">
<section class="container">
    <div class="content-wrap">
        <div class="content">
            <div id="focusslide" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#focusslide" data-slide-to="0" class="active"></li>
                    <li data-target="#focusslide" data-slide-to="1"></li>
                </ol>
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <a href="#" target="_blank" title="looli.club新闻博客网站" >
                            <img src="/home/images/111.png" alt="looli.club新闻博客网站" class="img-responsive"></a>
                    </div>
                    <div class="item">
                        <a href="#" target="_blank" title="专业网站建设" >
                            <img src="/home/images/222.png" alt="专业网站建设" class="img-responsive"></a>
                    </div>
                </div>
                <a class="left carousel-control" href="#focusslide" role="button" data-slide="prev" rel="nofollow">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">上一个</span>
                </a>
                <a class="right carousel-control" href="#focusslide" role="button" data-slide="next" rel="nofollow">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">下一个</span>
                </a>
            </div>
            <div class="title">
                <h3>最新发布</h3>
            </div>
                <#list newsList as news>
                    <article class="excerpt excerpt-1" style="">
                        <a class="focus" href="/home/news/detail?id=${news.id }" title="${news.title }" target="_blank" ><img class="thumb" data-original="${news.photo }" src="${news.photo }" alt="${news.title }"  style="display: inline;"></a>
                        <header>
                            <a class="cat" href="/home/news/category_list?cid=${news.categoryId}" title="${newsCategoryMap['${news.categoryId}']}" >
                                ${newsCategoryMap['${news.categoryId}']}<i></i>
                            </a>
                            <h2><a href="/home/news/detail?id=${news.id }" title="${news.title }" target="_blank" >${news.title }</a>
                            </h2>
                        </header>
                        <p class="meta">
                            <time class="time"><i class="glyphicon glyphicon-time"></i> ${news.createTime?string('yyyy-MM-dd hh:mm:ss')}</time>
                            <span class="views"><i class="glyphicon glyphicon-eye-open"></i> ${news.pageViews }</span>
                            <a class="comment" href="/home/news/detail?id=${news.id }#comment" title="评论" target="_blank" ><i class="glyphicon glyphicon-comment"></i>${news.commentVolume }</a>
                        </p>
                        <p class="note">${news.summary }</p>
                    </article>
                </#list>
<#--            <c:forEach items="${newsList }" var="news">-->
<#--                <article class="excerpt excerpt-1" style="">-->
<#--                    <a class="focus" href="../news/detail?id=${news.id }" title="${news.title }" target="_blank" ><img class="thumb" data-original="${news.photo }" src="${news.photo }" alt="${news.title }"  style="display: inline;"></a>-->
<#--                    <header><a class="cat" href="../news/category_list?cid=${news.categoryId }" title="${news.newsCategory.name }" >${news.newsCategory.name }<i></i></a>-->
<#--                        <h2><a href="../news/detail?id=${news.id }" title="${news.title }" target="_blank" >${news.title }</a>-->
<#--                        </h2>-->
<#--                    </header>-->
<#--                    <p class="meta">-->
<#--                        <time class="time"><i class="glyphicon glyphicon-time"></i> <fmt:formatDate value="${news.createTime }" pattern="yyyy-MM-dd hh:mm:ss" /></time>-->
<#--                        <span class="views"><i class="glyphicon glyphicon-eye-open"></i> ${news.viewNumber }</span>-->
<#--                        <a class="comment" href="../news/detail?id=${news.id }#comment" title="评论" target="_blank" ><i class="glyphicon glyphicon-comment"></i>${news.commentNumber }</a>-->
<#--                    </p>-->
<#--                    <p class="note">${news.abstrs }</p>-->
<#--                </article>-->
<#--            </c:forEach>-->
            <div class="ias_trigger" style="display:none;"><a href="javascript:;">查看更多</a></div>
        </div>
    </div>
    <#include "../common/siderbar.ftl">
</section>
<#include "../common/footer.ftl">