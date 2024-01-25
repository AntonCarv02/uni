<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

				<nav id="header">
					<ul>
						<li>
							<div id="nav-buttons">

								<a href="${pageContext.request.contextPath}/logout">Logout</a>
								<a href="${pageContext.request.contextPath}/index">Home</a>
							</div>
						</li>
					</ul>

				</nav>


			</header>
			<h1>${title}</h1>
			<main class="admin">

				<div class="regevento">
					<div id="input-box">
					<h2>Registo de Eventos</h2>
					<c:if test="${not empty msg}">
						<div class="msg">${msg}</div>
					</c:if>
					<form class="form" action="/admin" method="get">
						<input type="text" id="eventName" name="eventName" placeholder="Nome do Evento" required>
						<input type="date" id="eventDate" name="eventDate" required>
						<input type='text' name='description' placeholder="Descrição" required>
						<input type='text' name='price' placeholder="Preço de Inscrição" required>
						<button type="submit">Adicionar Evento</button>
					</form>
					</div>
				</div>



				<div class="regtempo">
					<div id="input-box">
					<h2>Registo de Tempos</h2>
					<c:if test="${not empty message}">
						<div class="msg">${message}</div>
					</c:if>
					<form class="form" action="/admin" method="get">
						<input type="text" name="eventID" placeholder="Id do Evento" required>
						<input type="text" name="eventDorsal" placeholder="Nº do Dorsal" required>
						<input type="datetime-local" id="Timestamp" name="Timestamp" step="1" required>
						<select name="local" required>
							<option value="start">Start</option>
							<option value="P1">P1</option>
							<option value="P2">P2</option>
							<option value="P3">P3</option>
							<option value="finish">Finish</option>
						</select>
						<button type="submit">Adicionar Tempo</button>
					</form>
					</div>
				</div>


			</main>
		</body>

		</html>