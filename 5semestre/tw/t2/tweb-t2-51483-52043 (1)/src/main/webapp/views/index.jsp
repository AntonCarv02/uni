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
						
						<c:if test="${pageContext.request.userPrincipal== null}">
							<a href="${pageContext.request.contextPath}/newuser">Register</a>
							<a href="${pageContext.request.contextPath}/login">Login</a>
						</c:if>
						<c:if test="${pageContext.request.userPrincipal!=null}">
							<a href="${pageContext.request.contextPath}/logout">Log Out</a>
							
							<c:if test="${isAdmin}">
								<a test="${isAdmin}" href="${pageContext.request.contextPath}/admin">Página de Admin</a>
							</c:if>
							<c:if test="${isUser}">
								<a test="${isUser}" href="${pageContext.request.contextPath}/userinscricao">Minhas inscrições</a>
							</c:if>
							
						</c:if></li>
				</ul>
			</nav>

		</header>

		<main>
			<h2>Home</h2>
			<form action="/index" method="get">
				<input type="text" id="eventName" name="eventName" placeholder="Nome do Evento">
				<input type="date" id="eventDate" name="eventDate">
				<button type="submit">Search</button>
			</form>
			<form action="/index" method="GET">
				<button type="submit" name="type" value="A">Eventos Passados</button>
				<button type="submit" name="type" value="P">Eventos Presentes</button>
				<button type="submit" name="type" value="F">Eventos Futuros</button>
			</form>
			<table border="1">
				<thead>
					<tr>
						<th>ID do Evento</th>
						<th>Nome do Evento</th>
						<th>Data</th>
						<th>Descrição</th>
						<th>Preço Inscrição</th>
						<th>Detalhes</th> 
					</tr>
				</thead>
				<tbody>
					<c:forEach var="event" items="${eventList}">
						<tr>
							<td>${event.getEvent_ID()}</td>
							<td>${event.getName()}</td>
							<td>${event.getDate()}</td>
							<td>${event.getDescription()}</td>
							<td>${event.getPrice()}€</td>
							<td>

								<a href="${pageContext.request.contextPath}/detailsPage?eventId=${event.getEvent_ID()}">
									<button>ver</button>
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</main>

		<footer>
			<div class="footer-info">				
						<p class="contact">
							<span class="cont">Contatos:</span>
							<a href="mailto:l51483@alunos.uevora.pt"> l51483@alunos.uevora.pt</a>
							<a href="mailto:l52403@alunos.uevora.pt">l52403@alunos.uevora.pt</a>
						</p>	
						<p class="sponsors">
							<span class="cont">Patrocínios:</span>
							<a href="https://www.nike.com/pt">Nike</a>
							<a href="https://www.beinshapepro.pt">Be in Shape</a>
							<a href="https://www.caldasdepenacova.pt">Águas Penacova</a>
						</p>
			</div>
			<div class="credits">
				<p>Desenvolvido por: António Carvalho e Pedro Bacalhau</p>
			</div>
			
		</footer>
	</body>

	</html>