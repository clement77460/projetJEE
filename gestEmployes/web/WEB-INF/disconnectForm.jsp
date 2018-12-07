<%-- 
    Document   : disconnectForm
    Created on : 7 déc. 2018, 10:26:30
    Author     : Clément
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div class="col-lg-3">
    <form method="POST" action="/gestEmployes">
        <i class="fas fa-user"></i>Votre session est active
        <button type='submit' name="action"  value="disconnect" class='btn btn-light'> <i class="fas fa-power-off"></i> </button>
    </form>
</div>
