<%-- 
    Document   : index
    Created on : 23 nov. 2018, 10:00:32
    Author     : Clément
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <body>
        <h1>Login</h1>
        <!-- /.navbar-top-links -->
        <div class="container">
            <c:if test="${sessionScope.errorMessage !=null}">
                <div class="alert alert-danger" role="alert">
                    <c:out value="${errorMessage}" default=""></c:out>
                </div>
                <c:remove var="errorMessage" scope="session"></c:remove>
            </c:if>
        <div class="row">
            <div class="col-md-5 col-md-offset-5">
                <div class="card">
                    <div class="card-header">
                        <h3 class="panel-title">Login</h3>
                    </div>
                    <div class="card-body">
                        <form method="POST" action="/projetJEEMaven">
                            <fieldset>
                                
                                <div class="form-group">
                                    <input class="form-control" placeholder="Login" name="user" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Mot de Passe" name="password" type="password" value="">
                                </div>
                                
                                <input class="btn btn-lg btn-success btn-block" type="submit" name="action" value="Login">
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>  
    </body>
</html>
