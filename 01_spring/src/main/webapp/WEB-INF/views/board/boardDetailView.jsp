<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
   img{
      width:400px;
   }
</style>
</head>
<body>

   <jsp:include page="/WEB-INF/views/common/header.jsp"/>
   
   <div class="content">
      <br><br>
      <div class="innerOuter">
         <h2>게시글 상세보기</h2>
         <br>
         <table id="contentArea" align="center" class="table">
            <tr>
               <th width="100">제목</th>
               <td colspan="3">${board.boardTitle}</td>
            </tr>
            <tr>
               <th>작성자</th>
               <td>
                  ${board.userName }
               </td>
               <th>작성일</th>
               <td>${board.createDate }</td>
            </tr>
            <c:if test="${board.attachment ne null}"> <!-- 첨부파일이 존재할때 (null이 아닐때)  -->
               <tr>
                  <th>첨부파일</th>
                  <td>
                     <button type="button" class="btn btn-outline-success btn-block"
                     onclick="location.href='${contextPath}/board/fileDownload/${board.boardNo }'"
                     >
                        ${board.attachment.originName} - 다운로드
                     </button>
                  </td>
               </tr>
            </c:if>            
            <c:if test="${boardCode == 'P'}"> <!-- P : 사진 게시판에 해당할때 -->
               <c:set var="imgList" value="${board.imgList}" />
               
               <c:if test="${not empty imgList }">
                  <c:forEach var="i" begin="0" end="${fn:length(imgList) - 1}">
                     <tr>
                        <th>이미지${i+1 }</th>
                        <td colspan="3">
                           <a href="${contextPath }/resources/images/board/${boardCode}/${imgList[i].changeName}"
                           download="${imgList[i].originName }">
                           <img src="${contextPath }/resources/images/board/${boardCode}/${imgList[i].changeName}">
                           </a>
                        </td>
                     </tr>
                  </c:forEach>
               </c:if>               
            </c:if>
            <tr>
               <th>내용</th>
               <td></td>
               <th>조회수</th>
               <td>${board.count }</td>
            </tr>
            <tr>
               <td colspan="4">
                  <p style="height:150px;">
                     ${board.boardContent}
                  </p>
               </td>
            </tr>
         </table>
         
         <br>
         
         <div align="center">
            <!-- 현재 게시글을 작성한 본인인 경우에만 수정하기 버튼이 보여야함. -->
            <c:if test="${not empty loginUser }">
               <a class="btn btn-primary" href="${contextPath }/board/update/${boardCode}/${board.boardNo}">수정하기</a>
            </c:if>
         </div>         
         
         <br><br>
         
         <table id="replyArea" class="table" align="center">
            <thead>
               <tr>
                  <th colspan="2">
                     <textarea class="form-control" name="replyContent" id="replyContent" rows="2" cols="55" 
                       style="resize:none; width:100%;"></textarea>
                  </th>
                  <th style="vertical-align:middle;">
                     <button class="btn btn-secondary" onclick="insertReply();">등록하기</button>
                  </th>
               </tr>
               <tr>
                  <td colspan="3">댓글(<span id="rcount">0</span>)</td>
               </tr>
            </thead>
            <tbody>
               
            
            </tbody>
         </table>
      </div>
   </div>
   
   <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>



</body>
</html>