<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		
	<div class="content">
		<div class="content-1">
			<h3>ȸ�� ���� ��ȸ</h3>
			
			<p>���̵� �Է� �޾� ��ġ�ϴ� ȸ�� ������ ���</p>
			���̵� : <input type="text" id="in1">
			<button id="select1">��ȸ</button>
			<div id="result" style="height:150px">
			</div>
		</div>
	</div>
	<script>
		$("#select1").click(function(){
			var $in1 = $("#in1");
			var $result = $("#result");
			
			$.ajax({
				url : "${contextPath}/member/selectOne",
				type: "post",
				data: {
					userId : $in1.val()
				}, 
				success : function(result){
					// js ��ü ���·� ������� ���� {key : value, key : value...}
					console.log(result);
					// console.log(JSON.parse(result));
					// console.log(JSON.stringify(new Object()));
					var $ul = $("<ul></ul>");
					
					var $li = $("<li></li>").text("���̵� : "+result.userId);
					var $li2 = $("<li></li>").text("�̸� : "+result.userName);
					$ul.append($li).append($li2).appendTo($result);
				},
				error : function(xhr){
					if(xhr.status == 404){
						alert("�������� �ʴ� ȸ���Դϴ�.")
					}
					else {
						alert("������ �߻��߽��ϴ�.")
					}
				}
			})
			
		})
		
		</script>	
		
	<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>