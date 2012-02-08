<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>Man in Man</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main.css" type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/chinese.css"
	type="text/css" />

</head>
<body>
	<div id="wrap">
		<div id="container">
			<div id="header">
				<div id="caption">
					<h1 id="title">
						<a href="${pageContext.request.contextPath}">男人天堂 </a>
					</h1>
					<div id="tagline">享受作为男人的快乐 – http://www.maninman.net/</div>
				</div>
				<div class="fixed"></div>
			</div>
			<div id="navigation"></div>
			<div id="content">
				<div id="main">
					<!--
					1、 每页显示10篇文章 
					2、每篇文章包含主题，及文章的前140个字。
					3、首页显示最新的10篇文章	
					-->
					<c:forEach var="articleItem" items="${articles}">
						<div class="box">
							<div class="post">
								<div class="info">
									<h2>
										<a class="title"
											href="../article/${articleItem.channel}/${articleItem.id}">${articleItem.title}</a>
									</h2>
									<span class="date">${articleItem.createTime}</span> <span
										class="author">${articleItem.author}</span>
									<div class="fixed"></div>
								</div>
								<div class="content">${articleItem.content}</div>
							</div>
						</div>
					</c:forEach>

					<div id="pagenavi">
						<div class="wp-pagenavi">
							<!-- 
							1、显示当前第几页，共有多少页
							2、最多显示10页，不足显示仅有的页，多于10页则显示 ... 预示有更多页。
							3、有最前页，前页，下页，最末页等标签				
							 -->
							<span class="pages">第 ${page.currentPage}/${page.totalPages}页</span> 

							<a href="./1" class="first">首页</a>		
							<c:if test="${page.prevPage > 0}"> 
					 			<a href="./${page.prevPage}" class="previouspostslink">上一页</a>
					 		</c:if>
							<c:forEach var="p" begin="${page.startPage}"
								end="${page.endPage}" step="1">
								<c:choose>
									<c:when test="${page.currentPage == p}">
										<span>${page.currentPage}</span>
									</c:when>
									<c:otherwise>
										<a href="./${p}" class="page larger">${p} </a>
									</c:otherwise>
								</c:choose>
							</c:forEach>

							<c:if test="${page.nextPage > 0}"> 
								<a href="./${page.nextPage}" class="nextpostslink">下一页</a>
							</c:if>
							<a href="./${page.totalPages}" class="last">末页 </a>
						</div>
						<div class="fixed"></div>
					</div>

				</div>
				<jsp:include page="sidebar.jsp" />
				<div class="fixed"></div>
			</div>
			<div id="footer">
				<div id="copyright">版权所有 © 2009-2011 男人天堂 – www.maninman.net</div>
			</div>
		</div>
	</div>
</body>
</html>