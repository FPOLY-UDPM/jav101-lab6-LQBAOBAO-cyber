<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Danh Sách Phòng Ban</title>

    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>

<body class="container mt-5">

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2 class="text-primary m-0">QUẢN LÝ PHÒNG BAN</h2>
</div>

<table class="table table-bordered table-striped">

    <thead class="thead-dark">
    <tr>
        <th>Mã Phòng Ban</th>
        <th>Tên Phòng Ban</th>
        <th>Mô Tả</th>
        <th width="180">Hành Động</th>
    </tr>
    </thead>

    <tbody>

    <c:forEach var="d" items="${departments}">

        <tr>

            <td>${d.id}</td>

            <td>${d.name}</td>

            <td>${d.description}</td>

            <td>

                <a href="${pageContext.request.contextPath}/department/id/${d.id}"
                   class="btn btn-info btn-sm">
                    🔍 Xem
                </a>

                <a href="${pageContext.request.contextPath}/department/delete/${d.id}"
                   class="btn btn-danger btn-sm"
                   onclick="return confirm('Bạn có chắc muốn xóa không?');">
                    🗑️ Xóa
                </a>

            </td>

        </tr>

    </c:forEach>

    </tbody>

</table>

</body>
</html>