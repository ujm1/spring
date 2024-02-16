<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	/* 제이쿼리 쓸것 */
	function getDeptName(pDeptno) {
		console.log(pDeptno);
		$.ajax(
				{
					url:"<%=request.getContextPath()%>/getDeptName",
					data:{deptno: pDeptno},
					dataType:'text',
					success:function(deptName) {/* getDeptName이 성공하면, */
						$('#deptName').val(deptName); /* input 태그라 val로 값을 넣어줌 */
						$('#msg').html(deptName);    /* span id 태그라 html로 */	
												}
				}
					
						
			  );
								}		
	
	function getDept(pDeptno) {
		$.ajax(
				{
					url:"sample/sendVO2",
					data:{deptno:pDeptno},
					dataType:'json',
					success:function(sampleVo) {
						resultStr=sampleVo.firstName+" "
						+sampleVo.lastName+" "+sampleVo.mno;
						alert("ajax getDept resultStr->"+resultStr);
						$('#RestDept').val(resultStr); //input tag
					
					}
				}		
		);
	}
	
	function empWriteBtn() { //버튼누르면
		var empno=$('#empno').val();
		//var sendData=JSON.stringify($('#empTrans')).serialize());
		var sendData=$('#empTrans').serializeArray(); 
		//empTrans는 아래에 있음. 저 html 객체? empTrans를 직렬화해서 보낸다는것
		alert('sendData->'+sendData);
		//sendData->[object Object],[object Object],[object Object] 뜸.
		//js에서 객체는 오브젝트로 보내며 밖에서 안을 볼 수 없다
		
		//Json Data Conversion;
		sendData3=jsonParse(sendData); //파싱함수-아래에서 정의->alert로 내부 출력하기 위한 목적
		alert('sendData3->'+sendData3);
		//sendData3->[object Object]
		
		console.log('sendData3->'+sendData3);
	
		$.ajax( {
					url: 'empSerializeWrite', //이건 EmpController에서 매핑
					type:'POST',
					contentType: 'application/json;charset=UTF-8',
					data:JSON.stringify(sendData3),
					dataType:'json',
					success: function(response) {
						if(response.writeResult>0) {
							alert("성공"); //직렬화 전송 성공 여부를 화면에 띄워주기 위함
						}
					}
		});
	}
	
	function jsonParse(sendData2) { //파싱
		obj={};
		if(sendData2) {
			jQuery.each(sendData2, function() {
				obj[this.name]=this.value;
				alert('this.name->'+this.name);
				//this.name->empno 뜸. 이후로 번갈아가며 ename sal
				alert('this.value->'+this.value);
				//this.value->1234 뜸. 이후로 번갈아가며 시리얼 1000... 이건 아래에서 empTrans에서 지정한것
			
			});
		}
		return obj;
	};
	

</script>
</head>
<body>
	<h2>회원 정보</h2>
	<table >
		<tr><th>사번</th><th>이름</th><th>업무</th><th>부서</th><th>근무지</th></tr>
		<c:forEach var="emp" items="${listEmp}"> <!-- 여기에 Attribute로 listEmp 이동 -->
			<tr><td>${emp.empno }</td><td>${emp.ename }</td>
				<td>${emp.job }</td>
				<td>${emp.deptno} 
				    <input type="button" id="btn_idCheck" value="부서명" onmouseover="getDeptName(${emp.deptno })">
				    <!-- 부서명 버튼에 마우스 올리면, getDeptName 제이쿼리 함수에 의해, 
				    해당 deptno에 해당하는 deptName을 가져와, 보여준다는 것. -->
				</td>
				<td>${empDept.loc }</td>
			</tr>
		</c:forEach>
	</table>

	deptName:  <input type="text" id="deptName"  readonly="readonly"><p>
    Message :  <span id="msg"></span><p>
    <!-- 이것도 getDeptName에 의해 deptName과 msg 파트가 변경 -->
    
    RestController sendVO2: <input type="text" id="RestDept"    readonly="readonly"><p>
    <!-- 클릭 후에.. -->
	RestController sendVO2: sendVO2<input type="button" id="btn_Dept"  value="부서명"  
	                                      onclick="getDept(10)"><p>
	                                      <!-- 부서명에 마우스 클릭시 제이쿼리 -->
	                                      <!-- ajax getDept resultStr->길동 홍 10 출력 -->
	                                      
	                                      
	<h2>Serialize Test</h2>
    <form  name="empTrans"   id="empTrans"> <!-- 직렬화에 쓸 객체 -->
    		<input type="hidden"  id="empno"  name="empno"    value="1234">
    		<input type="hidden"  id="ename"  name="ename"    value="시리얼">
    		<input type="hidden"  id="sal"    name="sal"      value="1000"">
        <input type="button"  value="Ajax Serialize 확인" onclick="empWriteBtn()"></button>
        						<!-- 직렬화 js 소환 -->
     </form>
	                                      
    
	 
</body>
</html>