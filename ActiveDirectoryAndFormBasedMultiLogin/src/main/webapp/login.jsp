<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

	<h2>Form Based & AAD Login</h2>
	<h5>Default username: alp , password: password</h5>
	<c:if test="${'fail' eq param.auth}">
		<div style="color:red">
			Login Failed!!!<br />
			Reason : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if>
	<form action="${pageContext.request.contextPath}/login" method="post">
		<table>
			<tr>
				<td>Username:</td>
				<td><input type='text' name='username' /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password'></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit" value="Form Login"></td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/loginAad" method="post">
	<input type ="hidden" name="username" value="1" />
	<input type ="hidden" name="password" value="2" />
		<input type="submit" value="Active Directory Login">
	</form>

</body>
</html>
