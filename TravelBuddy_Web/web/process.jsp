<%-- 
    Document   : process
    Created on : Aug 15, 2015, 11:41:19 PM
    Author     : yash
--%>

<%@page import="scala.Console"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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


<%
    
    String src = request.getParameter("Source");
    String des = request.getParameter("Destination");
    String mode = request.getParameter("Medium");
    
   System.out.print(src);

   String a [] = new String[3];
   
   a[0] = src;
   a[1] = des;
   a[2] = mode;
  
   travel.TravelBuddy.main(a);
   
   response.sendRedirect("http://localhost:8080/TravelBuddy_Web/");
    %>

    <body>
        
        <h3> Task Completed </h3> 
        </body>
        
</html>