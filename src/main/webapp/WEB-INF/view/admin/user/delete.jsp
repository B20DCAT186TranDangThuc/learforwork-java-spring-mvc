<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <!-- Latest compiled and minified CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                <!-- Latest compiled JavaScript -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

                <!-- <link rel="stylesheet" href="/css/demo.css"> -->
                <title>Delete User ${id}</title>
            </head>

            <body>
                <div class="container mt-5">
                    <div class="row">
                        <div class="col-12 mx-auto">
                            <div class="d-flex justify-content-between">
                                <h3>Delete User With id = ${id}</h3>
                            </div>

                            <hr>

                            <div class="alert alert-danger">
                                Are you sure you want to delete this user?
                            </div>

                            <form:form action="/admin/user/delete" method="post" modelAttribute="newUser">
                                <form:input type="hidden" name="id" path="id" />
                                <button class="btn btn-danger">Confirm</button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </body>

            </html>