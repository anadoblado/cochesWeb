<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
  <% String tituloDePagina = request.getParameter("tituloDePagina"); %>
  <title><%= (tituloDePagina != null)? tituloDePagina : "Sin título" %></title>
</head>
<body>
<nav class="navbar navbar-expand-md bg-info navbar-dark">
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
    	<span class="navbar-toggler-icon"></span>
   </button>
   <div class="collapse navbar-collapse" id="collapsibleNavbar"> 
   		<ul class="navbar-nav">
    <li class="nav-item active dropdown">
      <a class="nav-link  dropdown-toggle" data-toggle="dropdown" href="#">Concesionarios</a>
          <div class="dropdown-menu">
      		<a class="dropdown-item" href=listadoConcesionarios.jsp?idPag=1>Listado de Concesionarios</a>
    	</div>	
    </li>
   <li class="nav-item active dropdown">
      <a class="nav-link  dropdown-toggle" data-toggle="dropdown" href="#">Fabricantes</a>
          <div class="dropdown-menu">
      		<a class="dropdown-item" href=listadoFabricantes.jsp?idPag=1>Listado de Fabricantes</a>
    	</div>	
    </li>
    <li class="nav-item active dropdown">
      <a class="nav-link  dropdown-toggle" data-toggle="dropdown" href="#">Coches</a>
          <div class="dropdown-menu">
      		<a class="dropdown-item" href=listadoCoches.jsp?idPag=1>Listado de Coches</a>
    	</div>	
    </li>
    <li class="nav-item active dropdown">
      <a class="nav-link  dropdown-toggle" data-toggle="dropdown" href="#">Clientes</a>
          <div class="dropdown-menu">
      		<a class="dropdown-item" href=listadoClientes.jsp?idPag=1>Listado de Clientes</a>
    	</div>	
    </li>
    <li class="nav-item active dropdown">
      <a class="nav-link  dropdown-toggle" data-toggle="dropdown" href="#">Ventas</a>
          <div class="dropdown-menu">
      		<a class="dropdown-item" href=listadoVentas.jsp?idPag=1>Listado de Ventas</a>
    	</div>	
    </li>
    <li class="nav-item active dropdown">
      <a class="nav-link  dropdown-toggle" data-toggle="dropdown" href="#">Opciones</a>
          <div class="dropdown-menu">
      		<a class="dropdown-item" href=../index.html>Menú con opciones</a>
    	</div>	
    </li>
  </ul>
   </div>
  
</nav>

</body>
