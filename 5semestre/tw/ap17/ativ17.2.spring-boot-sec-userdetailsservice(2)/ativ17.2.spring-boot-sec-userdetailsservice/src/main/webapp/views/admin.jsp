<%@ page language="java" session="true"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- PAGINA DE ADMINISTRADOR-->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Security Basic - Form Based JDBC Authentication</title>
</head>
<body>
	<header>


	</header>
	<main>
	<div align="center">
		<h1>${title}</h1>
		<h2>${message}</h2>
		<h2>${msg}</h2>

		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<h2>
				Welcome
				: ${pageContext.request.userPrincipal.name} | <a
					href="<c:url value='logout'/>">Logout</a>
			</h2>
		</c:if>
	</div>
</main>
<footer>

	
</footer>
</body>
</html>