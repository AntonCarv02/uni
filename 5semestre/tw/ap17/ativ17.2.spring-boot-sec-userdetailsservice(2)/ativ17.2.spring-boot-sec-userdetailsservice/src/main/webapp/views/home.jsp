<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

	<!--PAGINA DE ENTRADA UTILIZADORES AUTENTICADOS-->

	<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Spring Security Basic - Form Based JDBC Authentication</title>
	</head>

	<body>
		<header>

			<h1>Home Page</h1>
			<div align="right">
				<p>
					<a href="${pageContext.request.contextPath}/admin">Go to Administrator page</a>
				</p>
				<p>
					<a href="${pageContext.request.contextPath}/logout">log Out</a>
				</p>
			</div>

		</header>
		<main>

			<h2>${message}</h2>
			
		</main>
		<footer>


		</footer>
	</body>

	</html>