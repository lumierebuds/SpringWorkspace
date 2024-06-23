<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.list-group-item + .list-group-item {
    margin-top: 10px;
}
</style>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<main class="container my-4">
    <section>
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h2 class="h5">게시물 목록</h2>
            <a href="insert" class="btn btn-primary">글쓰기</a>
        </div>
        <div class="list-group-row flex-wrap row">
        
         	<c:forEach var="board" items="${list }">
         	
         	<article class="col-md-4">
                <div class="card mb-4">
                    <img src="/study/resources/upload/study/thumbnail.jpg" class="card-img-top thumbnail" alt="Image 1">
                    <div class="card-body">
                        <h5 class="card-title"><a class="text-dark" href="detail/${board.no}">${board.title}</a></h5>
                        <p class="card-text">${board.subTitle}</p>
                        <small class="text-muted">작성자: KH 수달 | 작성일: ${board.createDate}</small>
                    </div>
                </div>
            </article>
         	</c:forEach>
               
        </div>
    </section>
</main>


<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>
