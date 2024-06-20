<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
   img{
      width: 100px;
   }
</style>
</head>
<body>

   <jsp:include page="/WEB-INF/views/common/header.jsp" />
      
   <div class="content">
      <br><br>
      <div class="innerOuter">
         <h2>게시글 수정하기</h2>
         <br>
         <form action="${contextPath }/board/update/${boardCode}/${boardNo}" id="enrollForm"
          method="post" enctype="multipart/form-data">
            <table align="center">
               <tr>
                  <th>제목</th>
                  <td><input type="text" id="title" class="form-control" name="boardTitle" value="${board.boardTitle }" required></td>
               </tr>
               <tr>
                  <th>작성자</th>
                  <td>${loginUser.userId }</td>
               </tr>
               
                  <tr>
                     <th>첨부파일</th>
                     <td><input type="file" id="upfile" class="form-control" name="upfile"> <!-- "file input"은 WAS가 임의로 value 값을 넣어줄 수 없다. -->
                     	
                        <span id="originName">${board.attachment.originName }</span>
                        <span class="delete-image" data-no="${board.attachment.boardImgNo }">&times;</span>
                        <input type="hidden" name="boardImgNo" value="${empty board.attachment.boardImgNo ? '0' : board.attachment.boardImgNo}"/>
                        
                     </td>
                  </tr>
               
               <tr>
                  <th>내용</th>
                  <td>
                     <textarea id="content" style="resize:none;" rows="10" class="form-control" 
                     name="boardContent" required="required">${board.boardContent}</textarea>
                  </td>
               </tr>
            </table>
         
            <div align="center">
               <button type="submit" class="btn btn-primary">수정</button>
               <button type="reset" class="btn btn-danger">취소</button>
            </div>
            <input type="hidden" name="deleteList" id="deleteList" value=""/>      
          </form>
      </div>
   </div>   
   <script>
   	$(function(){
   		
   		var $deleteImage = $(".delete-image");
   		var $deleteList = $("#deleteList");
   		
   		$deleteImage.each(function(index, ele){
   			$(ele).click(function(e){
   				var boardImgNo = $(this).data("no"); // data-no
   				var originName = $("#originName"); // span
   				
   				// 첨부파일번호
   				$deleteList.val(boardImgNo);
   				originName.remove(); // 게시글이름도 삭제 
   				$(this).remove(); // x 버튼도 삭제
   			});
   		});
   	})	
   
   </script>
   
   <jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>