<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h1>다양한 Ajax Test</h1>
    <a href="/helloText">helloText </a><p> <!-- helloText클릭시 Controller에 의해 안녕 출력 -->
    <a href="/sample/sendVO2?deptno=123">sample/sendVO2(객체) </a><p>
    <a href="/sendVO3">sendVO3 </a><p>
    <a href="/getDeptName?deptno=10">getDeptName(Empcontroller) </a><p>
    <a href="/listEmpAjaxForm">listEmpAjaxForm(EmpController) </a><p>
    <a href="/listEmpAjaxForm2">listEmpAjaxForm2(aJax JSP 객체리스트 Get) </a><p>
    <a href="/listEmpAjaxForm3">listEmpAjaxForm3(aJax List를 Controller로 전송 ) </a><p>
</body>
</html>