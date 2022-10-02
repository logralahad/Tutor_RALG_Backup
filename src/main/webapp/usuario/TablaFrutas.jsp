<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Tabla de frutas</title>
</head>
<body>
    <div class="container justify-content-center">
        <h2>Comparar Fresas y Zarzamoras</h2>
        <input hidden=true name="formato" value="excel"/>
        <%
        String formato = request.getParameter("formato");
        if ((formato != null) && (formato.equals("excel"))) {
        	response.setContentType("application/vnd.ms-excel");
        	response.setHeader("Content-disposition", "attachment; filename=ExcelFrutas.xls");
        }
        %>
        <table border=2>
            <tr>
                <th></th>
                <th>Fresas</th>
                <th>Zarzamoras</th>
            </tr>
            <tr>
                <th>Primer cuarto</th>
                <th>2307</th>
                <th>4706</th>
            </tr>
            <tr>
                <th>Segundo cuarto</th>
                <th>2982</th>
                <th>5104</th>
            </tr>
            <tr>
                <th>Tercer cuarto</th>
                <th>3011</th>
                <th>5220</th>
            </tr>
            <tr>
                <th>Cuarto cuarto</th>
                <th>3055</th>
                <th>5287</th>
            </tr>
        </table>
    </div>
</body>
</html>