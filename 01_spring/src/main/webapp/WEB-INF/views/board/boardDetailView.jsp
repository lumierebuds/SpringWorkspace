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
                  <td colspan="3">댓글(<span id="rcount">${empty board.replyList ? "0" : board.replyList.size()}</span>)</td>
               </tr>
            </thead>
            <tbody>
               <c:forEach items="${board.replyList}" var="reply">
               		<tr>
               			<td>${reply.userName}</td>
               			<td>${reply.replyContent }</td>
               			<td>${reply.createDate }
               				<button onclick="showReplyUpdateForm(${reply.replyNo}, this)">수정</button>
               				<button onclick="update">삭제</button>
               			</td>
               		</tr>
               		
               </c:forEach>
            
            </tbody>
         </table>
      </div>
   </div>
   <script>
   		// 댓글 목록 조회
   		function selectReplyList(){
   			$.ajax({
   				url : '${contextPath}/reply/selectReplyList',
   				data : {
   				boardNo:`${boardNo}`// requestScope에 그대로 존재하는 값을 보냄
   				},
   				success : function(result){
   					console.log(result);
   					
   					var replys = "";
   					for(var reply of result){
   						replys  += "<tr>";
   						replys +=`<td>\${reply.userName}</td>`;
   						replys += `<td>\${reply.replyContent}</td>`;
   						replys += `<td>\${reply.createDate}
   									<button onclick='showReplyUpdateForm(\${reply.replyNo}, this)'>수정</button>
   									<button onclick=''>삭제</button>
   									</td>`;
						replys+="</tr>";   									
   					}
   					
   					$("#replyArea tbody").html(replys);
   					$("#rcount").html(result.length);
   				},
   				error : function(xhr){
   					console.log(xhr);
   				}
   			})
   		}
   		
   		// 댓글 작성하기 
   		function insertReply(){
   			$.ajax({
   				url : `${contextPath}/reply/insertReply`,
   				type: "post",
   				data: {
   					refBno : `${boardNo}`,
   					replyWriter : `${loginUser.userNo}`,
   					replyContent : $("#replyContent").val()
   				}, 
   				success : function(result){
   					if(result == 0) alert("댓글 등록 실패");
   					else alert("댓글등록 성공");
   					selectReplyList();
   					$("#replyContent").val();
   				}, 
   				error : function(xhr){
   					
   				}
   				
   			})
   		}
   		
   		// 댓글 수정하기 폼으로 변경 
   		function showReplyUpdateForm(replyNo, btn){
   			var $textArea = $("<textarea></textarea>");
   			var $button = $("<button></button>").text("수정");
   			
   			var $td = $(btn).parent().parent().children().eq(1); //td
   			
   			$textArea.text($td.text()); // 댓글 내용 복사
   			
   			$td.html(""); // 기존 댓글내용 제거 
   			$td.append($textArea).append($button);
   			
   			$button.click(function(){
   				updateReply(replyNo, $textArea);
   			})
   		}
   		
   		
   		// 댓글 수정하기 
   		function updateReply(replyNo, $textArea){
   			$.ajax({
   				url: `${contextPath}/reply/update`,
   				data :{
   					replyNo, 
   					replyContent: $textArea.val()
   				},
   				type : 'post',
   				success : function(result) {
   					if(result > 0){
   						alert("댓글수정성공");
   					} else{
   						alert("댓글수정실패");
   					}
   					selectReplyList();
   				}, 
   				error : function(xhr){
   					console.log(xhr);
   				}
   				
   			})
   		}
   		
   		
   		// selectReplyList(); // 페이지 들어오자마자 호출 -- "수정" 
   		
   </script>
   
   <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>



</body>
</html>