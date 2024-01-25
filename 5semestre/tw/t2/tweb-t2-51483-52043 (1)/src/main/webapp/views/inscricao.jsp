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
                <nav>
                    <ul>
                        <li> <a href="${pageContext.request.contextPath}/index">Home</a>


                            <c:if test="${pageContext.request.userPrincipal!=null}">
                                <a href="${pageContext.request.contextPath}/logout">Log Out</a>

                            </c:if>
                        </li>
                    </ul>
                </nav>

            </header>


            <main>
                <div id="input-box">
                <h2>Inscrição no evento: ${event}</h2>
                <c:if test="${not empty msg}">
						<div class="msg">${msg}</div>
				</c:if>
                <form class="form" action="/registerparticipant" method="get">

                    <input style="display: none;" name="event" value="${eventid}">

                    <input style="display: none;" name='username' value='${username}'>

                    <input class="input-text" type='text' name='nome' value='' placeholder="Nome" required>

                    <input class="input-text" type='text' name='nif' value=''placeholder="Nif" required>

                    <p class="input-label">Género</p>
                    <div class="radio-group">
                        <div class="radio-subgroup">
                            <div class="radio">
                                <input type="radio" checked name="genero" value="m">
                                <p class="radio-label">Masculino</p>
                            </div>
                            <div class="radio">
                                <input type="radio" name="genero" value="f">
                                <p class="radio-label">Feminino</p>
                            </div>
                        </div>
                    </div>

                    <p class="input-label">Escalão</p>
                    <div class="radio-group">
                        <div class="radio-subgroup">
                            <div class="radio">
                                <input type="radio" checked name="escalao" value="jun">
                                <p class="radio-label">Junior</p>
                            </div>
                            <div class="radio">
                                <input type="radio" name="escalao" value="sen">
                                <p class="radio-label">Senior</p>
                            </div>
                            <div class="radio">
                                <input type="radio" name="escalao" value="vet35">
                                <p class="radio-label">Vet35</p>
                            </div>
                        </div>
                        <div class="radio-subgroup">
                            <div class="radio">
                                <input type="radio" name="escalao" value="vet40">
                                <p class="radio-label">Vet40</p>
                            </div>
                            <div class="radio">
                                <input type="radio" name="escalao" value="vet45">
                                <p class="radio-label">Vet45</p>
                            </div>
                            <div class="radio">
                                <input type="radio" name="escalao" value="vet50">
                                <p class="radio-label">Vet50</p>
                            </div>
                        </div>
                        <div class="radio-subgroup">
                            <div class="radio">
                                <input type="radio" name="escalao" value="vet55">
                                <p class="radio-label">Vet55</p>
                            </div>
                            <div class="radio">
                                <input type="radio" name="escalao" value="vet60">
                                <p class="radio-label">Vet60</p>
                            </div>
                            <div class="radio">
                                <input type="radio" name="escalao" value="vet65">
                                <p class="radio-label">Vet65</p>
                            </div>
                        </div>
                    </div>
                    <input type="submit" value="inscrever"><br>
                </form>
                </div>
            </main>

        </body>

        </html>