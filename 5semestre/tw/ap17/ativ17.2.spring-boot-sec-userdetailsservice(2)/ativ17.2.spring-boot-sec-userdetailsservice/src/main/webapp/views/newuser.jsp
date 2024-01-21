<%@ page language="java" session="true" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


        <!-- PAGINA DE CRIAÇÃO DE NOVO USER-->

        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <title>User Registration: new user</title>
            <link rel="stylesheet" type="text/css" href="../static/css/style.css" />
        </head>

        <body>
            <header>
                <nav id="header">
                    
                    <div id="nav-buttons">
                        <a href="${pageContext.request.contextPath}/index">
                            <button>Home</button>
                        </a>
                        <a href="${pageContext.request.contextPath}/login">
                            <button>Login</button>
                        </a>
                    </div>
                    
                </nav>


            </header>
            <main>
                <div align="center">
                    <h1>${title}</h1>
                    <h2>${message}, new user</h2>

                    <form class="form" id="form1" method="get" action="register">
                        username: <input type="text" name="username"><br>
                        password:<input type="password" name="password"><br>
                        email:<input type="text" name="email1"><br>

                        <p class="input-label">gender</p>
                        <div class="radio-group">
                            <div class="radio">
                                <input type="radio" checked name="genero" value="m">
                                <p class="radio-label">Masculine</p>
                            </div>
                            <div class="radio">
                                <input type="radio" name="genero" value="f">
                                <p class="radio-label">Feminine</p>
                            </div>
                        </div>
                        <p class="input-label">tier</p>
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
                        <input type="submit" value="send"><br>
                    </form><!-- comment -->
                </div>
            </main>
            <footer>


            </footer>
        </body>

        </html>