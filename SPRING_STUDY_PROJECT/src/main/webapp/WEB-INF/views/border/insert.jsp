<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 등록</title>
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<main class="container my-4">
    <section>
        <h2 class="h5">게시물 등록</h2>
        <form action="insert" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" class="form-control" id="title" name="title" required>
            </div>
            <div class="form-group">
                <label for="author">부제목</label>
                <input type="text" class="form-control" id="subTitle" name="subTitle" required>
            </div>
            <div class="form-group">
                <label for="content">내용</label>
                <textarea class="form-control" id="content" name="content" rows="5" required></textarea>
            </div>
            <div class="form-group">
                <label for="images">이미지 업로드</label>
                <input type="file" class="form-control-file" id="images" name="images" multiple>
            </div>
            <button type="submit" class="btn btn-primary">등록</button>
            <a href="list" class="btn btn-secondary">취소</a>
        </form>
    </section>
</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />

	

</body>
</html>
