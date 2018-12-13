<%-- 
    Document   : employeView
    Created on : 24 nov. 2018, 11:34:29
    Author     : Clément
--%>


<!DOCTYPE html>
<html>
    <body>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-9">
                    
                    <!-- display error message && type of action (insert or update) -->
                    <jsp:include page="dynamicEmployeView.jsp"/>
                    
                </div>
                    
                <!-- display disconnect part -->
                <jsp:include page="disconnectForm.jsp"/>
                
            </div>
         <form method="POST" action="/gestEmployes">
            <div class="form-group row">
              <label class="col-sm-1 col-form-label">Nom</label>
              <div class="col-sm-11">
                <input type="text" class="form-control" placeholder="Nom" name='nom'
                       value="<c:out value="${sessionScope.employe.nom}" default=""></c:out>">
              </div>
            </div>
            <div class="form-group row">
              <label class="col-sm-1 col-form-label">Prénom</label>
              
              <div class="col-sm-11">
                <input type="text" class="form-control" placeholder="prenom" NAME='prenom'
                       value="<c:out value="${sessionScope.employe.prenom}" default=""></c:out>"
                </input>
              </div>
            </div>
             <div class="form-group row">
              <label class="col-sm-1 col-form-label">Tél dom</label>
              <div class="col-sm-11">
                <input type="text" class="form-control" placeholder="Tél dom" NAME='telDom'
                       value="<c:out value="${sessionScope.employe.teldom}" default=""></c:out>">
              </div>
            </div>
             <div class="form-group row">
              <label class="col-sm-1 col-form-label">Tél mob</label>
              <div class="col-sm-11">
                <input type="text" class="form-control" placeholder="Tél mob" NAME='telMob'
                       value="<c:out value="${sessionScope.employe.telport}" default=""></c:out>">
              </div>
            </div>
             <div class="form-group row">
              <label class="col-sm-1 col-form-label">Tél pro</label>
              <div class="col-sm-11">
                <input type="text" class="form-control" placeholder="Tél pro" NAME='telPro'
                       value="<c:out value="${sessionScope.employe.telpro}" default=""></c:out>">
              </div>
            </div>
            
             <div class="form-group row">
                <label class="col-sm-1 col-form-label">Adresse</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" placeholder="Adresse" NAME='adresse'
                         value="<c:out value="${sessionScope.employe.adresse}" default=""></c:out>">
                </div>
                <label class="col-sm-1 col-form-label">code Postal</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" placeholder="cp" NAME='codePostal'
                         value="<c:out value="${sessionScope.employe.codePostal}" default=""></c:out>">
                </div>
              </div>
             
             <div class="form-group row">
                <label class="col-sm-1 col-form-label">Ville</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" placeholder="Ville" NAME='ville'
                         value="<c:out value="${sessionScope.employe.ville}" default=""></c:out>">
                </div>
                <label class="col-sm-1 col-form-label">Adresse e-mail</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" placeholder="e-mail" NAME='mail'
                         value="<c:out value="${sessionScope.employe.email}" default=""></c:out>">
                </div>
             </div>
             <div class="row justify-content-end">
                <div class="col-1">
                  <input type='submit' name="action" value="Valider" class='btn btn-primary'/>
                </div>
                <div class="col-4">
                  <input type='submit' name="action" value="Voir liste" class='btn btn-light'/>
                </div>
              </div>
             
             
          </form>
        </div>
    </body>
</html>
