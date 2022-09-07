<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="ralg.ulsa.modelo.Usuario"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Persona</title>
<jsp:include page="/componentes/bootstrap.jsp"></jsp:include>

<link rel="stylesheet" type="text/css"
    href="<%=request.getContextPath()%>/css/bootstrap-5.0.2/dashboard.css">

<link rel="stylesheet" type="text/css"
    href="<%=request.getContextPath()%>/css/bootstrap-5.0.2/dashboard.rtl.css">
</head>
<body>
    <%
    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
    %>
    <c:if test="${usuario == null }">
        <jsp:forward page="./login.jsp"></jsp:forward>
    </c:if>
    <jsp:include page="/componentes/adminHeader.jsp">
        <jsp:param value="${usuario.getCorreo()}" name="correoUsuario" />
    </jsp:include>

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="/componentes/menuLateral.jsp"></jsp:include>
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div
                    class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">PERSONA</h1>


                    <a class="btn btn-labeled btn-primary"
                        href="<%=request.getContextPath()%>/usuario/registrarPersona.jsp"
                        role="button"><span class="btn-label pe-2"><i
                            class="fa fa-plus"></i></span>Nueva persona</a>

                </div>

                <div class="table-responsive">
                    <table class="table table-striped table-sm">
                        <thead>
                            <tr>
                                <th
                                    class="col-md-1 text-center align-middle"
                                    scope="col">Id</th>
                                <th
                                    class="col-md-4 text-center align-middle"
                                    scope="col">Nombre</th>
                                <th
                                    class="col-md-3 text-center align-middle"
                                    scope="col">RFC</th>
                                <th
                                    class="col-md-3 text-center align-middle"
                                    scope="col">Tel&eacute;fono</th>
                                <th
                                    class="col-md-1 text-center align-middle"
                                    scope="col">Edad</th>
                            </tr>
                        </thead>
                        <c:if test="${listaPersonas != null}">
                            <tbody>
                                <c:forEach items="${listaPersonas}"
                                    var="persona">
                                    <tr>
                                        <th scope="row"
                                            class="text-center align-middle"><c:out
                                                value="${persona.getId()}" /></th>
                                        <td
                                            class="text-center align-middle">
                                            <c:out
                                                value="${persona.getNombreCompleto()}" />
                                        </td>
                                        <td
                                            class="text-center align-middle"><c:out
                                                value="${persona.getRfc()}" />
                                        </td>
                                        <td
                                            class="text-center align-middle">
                                            <c:out
                                                value="${persona.getTelefono()}" />
                                        </td>
                                        <td
                                            class="text-center align-middle">
                                            <c:out
                                                value="${persona.getEdad()}" />
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </c:if>
                    </table>
                </div>
            </main>
        </div>
    </div>
</body>
</html>