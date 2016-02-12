<%-- 
    Document   : index
    Created on : Aug 15, 2015, 11:29:09 PM
    Author     : yash
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Travel Buddy</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <div align="center"><img src="http://localhost:8080/TravelBuddy_Web/resources/logo.png"></div>
    
    <style>
      

   body { background: #ECC418; }
</style>

  
    </style>
    </head>
    <body>
       <div class="container">
        <form id="frm1" method="get" action="/TravelBuddy_Web/process.jsp" role="form">
            <div class="form-group">

                <label>Source</label><input class="form-control" id="source" name="Source" type="text" readonly="readonly">
            </div>
             <div class="form-group">
            
                 <label>Destination</label><input class="form-control" id="destination" name="Destination" type="text" readonly="readonly">
             </div>
            
             <div class="form-group">
                 <label>Medium</label><select class="form-group" onchange="test()" id="medium" name="Medium"><option value="A">Air</option><option value="R">Road</option><option value="M">Marine</option></select>
             </div>
            
             <div align="center" class="form-group">
<input class="btn btn-info" name="Calculate Route" value="Cheapest Route" id="start" type="submit">

            </div>
       </div>
 </form>

        
    </body>
    
            <script> 
        
        function test(){
        
            var target = $('#medium').val();
            
            if(target == "R"){
            
            $("#source").val("A");
            $("#destination").val("Z");  
            }
            
            if(target == "M"){
            
            $("#source").val("L");
            $("#destination").val("C");  
            }
            
            if(target == "A"){
            
            $("#source").val("S");
            $("#destination").val("B");  
            }
            
            function start () {
                
                $("#frm1").serialize();
                
                $("#frm1").submit();
            }
        }

        
        </script>
</html>
