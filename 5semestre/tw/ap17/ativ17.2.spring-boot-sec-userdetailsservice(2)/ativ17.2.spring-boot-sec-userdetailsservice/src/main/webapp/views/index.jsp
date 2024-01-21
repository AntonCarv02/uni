<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- PAGINA INICIAL -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Security Basic - Form Based JDBC Authentication</title>
</head>
<body>
	<header>
 
		<div align="center">
		<h1>Home Page</h1>
		<h2>${msg}</h2>
                <p>
		<a href="${pageContext.request.contextPath}/admin">Go to Administrator page</a>
                </p>
                <p>
		<a href="${pageContext.request.contextPath}/newuser">Sign Up page</a>
                </p>
				<p>
		<a href="${pageContext.request.contextPath}/login">Log in page</a>
                </p>
	</div>

	</header>
	<main>
	
</main>
<footer>

	
</footer>
</body>
</html>