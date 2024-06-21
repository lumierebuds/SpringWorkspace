<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${contextPath }/resources/css/chat-style.css" rel="stylesheet">
<link href="${contextPath }/resources/css/main-style.css" rel="stylesheet">
<style>
   .chatting-area{
      margin :auto;
      height : 600px;
      width : 800px;
      margin-top : 50px;
      margin-bottom : 500px;
   }
   #exit-area{
      text-align:right;
      margin-bottom : 10px;
   }
   .display-chatting {
      width:42%;
      height:450px;
      border : 1px solid gold;
      overflow: auto; /*스크롤 처럼*/
      list-style:none;
      padding: 10px 10px;
      background : lightblue;
      z-index: 1;
         margin: auto;
      background-image : url(${contextPath}/resources/main/chunsickbackground.png);
       background-position: center;
   }
   .img {
      width:100%;
      height:100%;
       position: relative;
       z-index:-100;
   }
   .chat{
      display : inline-block;
      border-radius : 5px;
      padding : 5px;
      background-color : #eee;
   }
   .input-area{
      width:100%;
      display:flex;
      justify-content: center;
   }
   #inputChatting{
      width: 32%;
      resize : none;
   }
   #send{
      width:20%;
   }
   .myChat{
      text-align: right;
   }
   .myChat > p {
      background-color : gold;
   }
   .chatDate{
      font-size : 10px;
   }
</style>
</head>
<body>

   <jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
   
      <div class="chatting-area">
         <div id="exit-area">
            <button class="btn btn-outline-danger" id="exit-btn">나가기</button>
         </div>
         <ul class="display-chatting">
            <c:forEach items="${list}" var="msg">
               <c:if test='${msg.userNo eq loginUser.userNo }'>
                  <li class="myChat">
                     <span class="chatDate">${msg.createDate}</span>
                     <p class="chat">${msg.message}</p>
                  </li>   
               </c:if>
               <c:if test="${msg.userNo ne loginUser.userNo }">
                  <li>
                     <b>${msg.userName }</b>
                     <p class="chat">${msg.message }</p>
                     <span class="chatDate">${msg.createDate }</span>
                  </li>               
               </c:if>            
            </c:forEach>
         </ul>
         
         <div class="input-area">
            <textarea id="inputChatting" rows="3"></textarea>
            <button id="send">보내기</button>
         </div>               
      </div>
   
   <!-- 클라이언트용 소켓 -->
   <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
   <script>
   		// chat.js에서 사용하기 위한 변수등록
  		const userNo = `${loginUser.userNo}`;
  		const userName = `${loginUser.userName}`;
  		const chatRoomNo = `${chatRoomNo}`;
  		const contextPath = `${contextPath}`;
  		
  		// 웹소캣객체 생성 --> 연결요청보냄
  		var chattingSocket = new SockJS(contextPath+"/chat"); 
  		// ex) "http://192.168.30.193:8080"+contextPath+"/chat/" 
  		// ip 주소로 경로를 주게 된다면 다른 사람들과 통신이 가능하다. 
   
   </script>
   
   <script src="${contextPath }/resources/js/chat.js"></script>
   
   <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>


</body>
</html>