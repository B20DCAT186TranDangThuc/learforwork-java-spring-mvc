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
                <title>Update User</title>
            </head>

            <body>
                <div class="container mt-5">
                    <div class="row ">
                        <div class="col-md-6 col-12 mx-auto">
                            <h1>Update User ${id}</h1>
                            <hr>
                            <form:form method="post" action="/admin/user/update" modelAttribute="user">
                                <div class="mb-3" style="display: none;">
                                    <label for="id" class="form-label">id:</label>
                                    <form:input type="text" class="form-control" path="id" />
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email address:</label>
                                    <form:input type="email" class="form-control" path="email" disabled="true" />
                                </div>
                                <div class="mb-3">
                                    <label for="phone" class="form-label">Phone Number:</label>
                                    <form:input type="text" class="form-control" path="phone" />
                                </div>
                                <div class="mb-3">
                                    <label for="fullName" class="form-label">Full Name:</label>
                                    <form:input type="text" class="form-control" path="fullName" />
                                </div>
                                <div class="mb-3">
                                    <label for="address" class="form-label">Address:</label>
                                    <form:input type="text" class="form-control" path="address" />
                                </div>
                                <button type="submit" class="btn btn-warning">Update</button>
                                <a href="/admin/user" class="btn btn-primary">Back</a>
                            </form:form>
                        </div>
                    </div>
                </div>
            </body>

            </html>