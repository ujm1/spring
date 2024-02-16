<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>

<script type="text/javascript">
function getListDept() {
	console.log("getListDeptRun");
	// alert("getListDept Run..");
	var str="";
	var str2="";
	$.ajax(
			{
				url:"/sendVO3",
			dataType:'json',
			success:function(dept) {
				var jsonStr=JSON.stringify(dept);
				alert("jsonStr->"+jsonStr);
				$('#dept_list_str').append(jsonStr);
				str+="<select name='dept'>";
				$(dept).each(
					function() {
						str2="<option value'"+this.deptno+"'> "+this.dname+"</option>";
						str+=str2;
					}		
				)
				str+="</select><p>"
				alert("combobox str->"+str);
				$('#dept_list_combobox').append(str);
			}
			}		
	);
}

function getDeptDelete(pInext) {
	var selEmpno=$("#empno"+pIndex).val();
	var selEname=${"#ename"+pIndex}.val();
	
	//alert("getDeptDelete selEmpno->"+selEmpno);
	
	$.ajax(
			{
				url:"/empnoDelete",
				data:{empno:selEmpno, ename: selEname},
				dataType:'text',
				success:function(data) {
					alert(".ajax getDeptDelete data->"+data);
					if(data=='1') {
						$('#emp'+pIndex).remove(); /* delete tag */
					}
				}
			}
			);
}


</script>
</head>
<body>
	<h2>회원 정보</h2>
	<table>
		<tr><th>번호</th><th>사번</th><th>이름</th><th>업무</th><th>부서</th></tr>
	 	<c:forEach var="emp" items="${listEmp}" varStatus="status">
	 	<!-- controller에서 보낸 listEmp를 받음 -->
			<tr id="emp${status.index}"><td>emp${status.index}</td>
		     			<!-- status에 자동으로 들어가는 것 -->
			    <td>
			        <input type="hidden" id="deptno${status.index}" value="${emp.deptno }">
			        <input type="text" id="empno${status.index}" value="${emp.empno }">${emp.empno }</td>
			    <td><input type="text" id="ename${status.index}" value="${emp.ename }">${emp.ename }</td>
				<td>${emp.job }</td><td>${emp.deptno } 
				    <input type="button" id="btn_idCheck2" value="사원 Row Delete" onclick="getDeptDelete(${status.index})">
				</td>																<!-- 인덱스 -> 지정한 것만 간편히 삭제 가능 -->							
			</tr>    
	     
	     </c:forEach>
	
	
	</table>

    RestController LISTVO3: <input type="button" id="btn_Dept3"
                                   value="부서명 LIST"  
                                   onclick="getListDept()"><p>
                                   <!-- 함수 호출 -->
                                   
	dept_list_str:	<div id="dept_list_str"></div>

	dept_list_combobox:
	<div id="dept_list_combobox"></div>
	
	<h1>The End </h1>
</body>
</html>