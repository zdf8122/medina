<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
					<div class="post">
						<div class="info">
							<h2>${article.title}</h2>
							<span class="date">${article.createTime}</span> <span
								class="author">${article.author}</span>
							<c:if test="${article.srcUrl != null}">
								<span class="srcurl">源址: <a href="${article.srcUrl}">
										${fn:substring(article.srcUrl,0,30)}/... </a>
								</span>
							</c:if>
							<div class="fixed"></div>
						</div>
						<div class="content">${article.content}</div>
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