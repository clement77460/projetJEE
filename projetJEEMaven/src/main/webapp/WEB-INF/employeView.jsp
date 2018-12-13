<%-- 
    Document   : employeView
    Created on : 24 nov. 2018, 11:34:29
    Author     : Cl�ment
--%>


<!DOCTYPE html>
<html>
    <body>
        <div id="page-wrapper">
            <div class="row">

                <!-- display error message && type of action (insert or update) -->
                <jsp:include page="dynamicEmployeView.jsp"/>
                
                <!-- display disconnect part -->
                <jsp:include page="disconnectForm.jsp"/>
                
                
            </div>
         <form method="POST" action="/projetJEEMaven" >
            <div class="form-group row">
              <label class="col-sm-1 col-form-label">Nom</label>
              <div class="col-sm-11">
                <input type="text" class="form-control" placeholder="Nom" name='nom'
                       value="<c:out value="${sessionScope.employe.nom}" default=""></c:out>">
              </div>
            </div>
            <div class="form-group row">
              <label class="col-sm-1 col-form-label">Pr�nom</label>
              <div class="col-sm-11">
                <input type="text" class="form-control" placeholder="pr�nom" NAME='prenom'
                       value="<c:out value="${sessionScope.employe.prenom}" default=""></c:out>">
              </div>
            </div>
             <div class="form-group row">
              <label class="col-sm-1 col-form-label">T�l dom</label>
              <div class="col-sm-11">
                <input type="text" class="form-control" placeholder="T�l dom" NAME='telDom'
                       value="<c:out value="${sessionScope.employe.teldom}" default=""></c:out>">
              </div>
            </div>
             <div class="form-group row">
              <label class="col-sm-1 col-form-label">T�l mob</label>
              <div class="col-sm-11">
                <input type="text" class="form-control" placeholder="T�l mob" NAME='telMob'
                       value="<c:out value="${sessionScope.employe.telport}" default=""></c:out>">
              </div>
            </div>
             <div class="form-group row">
              <label class="col-sm-1 col-form-label">T�l pro</label>
              <div class="col-sm-11">
                <input type="text" class="form-control" placeholder="T�l pro" NAME='telPro'
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
                         value="<c:out value="${sessionScope.employe.codepostal}" default=""></c:out>">
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
