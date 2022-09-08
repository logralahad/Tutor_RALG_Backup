<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="ralg.ulsa.modelo.Usuario"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Rol</title>
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
                    <h1 class="h2">ROL</h1>


                    <a class="btn btn-labeled btn-primary"
                        href="<%=request.getContextPath()%>/usuario/registrarRol.jsp"
                        role="button"><span class="btn-label pe-2"><i
                            class="fa fa-plus"></i></span>Nuevo rol</a>

                </div>

                <div class="table-responsive">
                    <table class="table table-striped table-sm">
                        <thead>
                            <tr>
                                <th
                                    class="col-2 text-center align-middle"
                                    scope="col">Id</th>
                                <th
                                    class="col-4 text-center align-middle"
                                    scope="col">Nombre</th>
                                <th
                                    class="col-4 text-center align-middle"
                                    scope="col">Descripcion</th>
                                <th
                                    class="col-1 text-center align-middle"
                                    scope="col"></th>
                                <th
                                    class="col-1 text-center align-middle"
                                    scope="col"></th> 
                            </tr>
                        </thead>
                        <c:if test="${listaRoles != null}">
                            <tbody>
                                <c:forEach items="${listaRoles}"
                                    var="rol">
                                    <tr>
                                        <th scope="row"
                                            class="text-center align-middle"><c:out
                                                value="${rol.getId()}" /></th>
                                        <td
                                            class="text-center align-middle"><c:out
                                                value="${rol.getNombre()}" /></td>
                                        <td
                                            class="text-center align-middle"><c:out
                                                value="${rol.getDescripcion()}" /></td>
                                        <td
                                            class="text-center align-middle">
                                            <form
                                                action="<%=request.getContextPath()%>/Rol/editar/${rol.getId()}">
                                                <button type="submit"
                                                    class="btn btn-success w-50">
                                                    <i
                                                        class="fa fa-pencil-square-o"
                                                        aria-hidden="true"></i>
                                                </button>
                                            </form>
                                        </td>
                                        <td
                                            class="text-center align-middle">
                                            <form
                                                action="<%=request.getContextPath()%>/Rol/eliminar/${rol.getId()}">
                                                <button type="submit"
                                                    class="btn btn-danger w-50">
                                                    <i
                                                        class="fa fa-trash"
                                                        aria-hidden="true"></i>
                                                </button>
                                            </form>
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