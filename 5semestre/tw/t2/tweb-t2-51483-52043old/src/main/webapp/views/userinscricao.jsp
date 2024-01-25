<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Iron Runners</title>
		<link rel="stylesheet" type="text/css" href="../static/css/style.css" />
	</head>

	<body>
		<header>
			
			<a class="logo" href="${pageContext.request.contextPath}/index">
				<img src="../static/images/logo.png" alt="logo">
			</a>
			<nav>
				<ul>
					<li>
						
						<a href="${pageContext.request.contextPath}/index">Home</a>
						<c:if test="${pageContext.request.userPrincipal!=null}">
							<a href="${pageContext.request.contextPath}/logout">Log Out</a>
						</c:if></li>
				</ul>
			</nav>

		</header>

		<main>
			<h2>Minhas Inscrições</h2>
			<table border="1">
				<thead>
					<tr>
						<th>Nome do Evento</th>
						<th>Dorsal</th>
						<th>Nome</th>
						<th>Género</th>
						<th>Escalão</th>
                        <th>Pagamento</th>  
					</tr>
				</thead>
				<tbody>
				<c:choose>
           		<c:when test="${empty page}">
					<tr>
						<td colspan="6" align="center">Sem Inscrições</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="p" items="${pList}" varStatus="loop">
						<tr>
							<td>${nList[loop.index]}</td>
							<td>${p.getEvent_Dorsal()}</td>
							<td>${p.getName()}</td>
							<td>${p.getGender()}</td>
							<td>${p.getEscalao()}</td>
							<td>
								<c:if test="${p.pago}">
									Confirmado
								</c:if>
								<c:if test="${not p.pago}">
									<a href="${pageContext.request.contextPath}/userinscricao?eventid=${p.getEvent_ID()}&amount=${aList[loop.index]}&eventDorsal=${p.getEvent_Dorsal()}">
										<button>Pagar</button>
									</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
				</c:choose>
				</tbody>
			</table>
			<c:if test="${not empty message}">
				<div class="msg">${message}</div>
				<c:if test="${showpaybt}">	<a href="${pageContext.request.contextPath}/userinscricao?eventid=${event_ID}&eventDorsal=${event_Dorsal}">
					<button >Efetuar Pagamento</button>
				</a></c:if>
				
			</c:if>
		</main>
		<footer>

			
		</footer>
	</body>

	</html>