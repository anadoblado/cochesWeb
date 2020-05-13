<%@ page
	import="java.util.List, 
	java.util.HashMap,
	java.util.Date,
	java.text.SimpleDateFormat,
	Utils.RequestUtils,
	model.Cliente,
	model.controladores.ClienteControlador"%>
	
<jsp:include page="cabecera.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="Ficha de cliente" />
</jsp:include>

<% 

SimpleDateFormat sdfFechaNac = new SimpleDateFormat("dd/MM/yyyy");
	// Obtengo un HashMap con todos los parámetros del request, sea del tipo que sea;
HashMap<String, Object> hashMap = RequestUtils.requestToHashMap(request);

	// Para plasmar la información de un cliente determinado utilizaremos un parámetro que debe llegar al Servlet obligatoriamente
	// El parámetro se llama "idCliente" y gracias a él podremos obtener la info del cliente y mostrar sus datos por pantalla
	

Cliente cliente = null;
	// Obtengo el cliente a editar, en el caso de que el cliente ya exista se cargarán los datos, y si no, quedará a null
try {
	int idCliente = RequestUtils.getIntParameterFromHashMap(hashMap, "idCliente"); // Necesito obtener el id del cliente que se quiere editar
	// Si el Cliente es nuevo, el valor del id será 0
	if (idCliente != 0){
		cliente = (Cliente) ClienteControlador.getControlador().find(idCliente);
	}
} catch (Exception e){
	e.printStackTrace();
}
// Inicio la carga de los valores de un cliente
if (cliente == null){
	cliente = new Cliente();
}
if (cliente.getDniNie() == null) cliente.setDniNie("");
if (cliente.getNombre() == null) cliente.setNombre("");
if (cliente.getApellidos() == null) cliente.setApellidos("");

if (cliente.getFechaNac() == null) {
//Date date = sdf.parse(RequestUtils.getStringParameterFromHashMap(hashMap, "fechaNac"));
	cliente.setFechaNac(null);	
}
if (cliente.getLocalidad() == null) cliente.setLocalidad("");


if (cliente.getActivo() == false) cliente.setActivo(false);




// En función de los parámetros de entrada del Servlet, debemos determinar qué se hará según 3 acciones:
// - "eliminar" sabemos que es esta opción porque recibe un parámetro con el nombre "elimiar" en el request
// - "guardar" sabemos que es esta opción porque recibe un parámetro con el nombre "guardar" en el request
// - Sin acción. En este caso, sólo se quiere editar

// Variable con mensaje de información al usuario sobre alguna acción requerido
String mensajeAlUsuario = "";

// Primera acción posible: eliminar
if (RequestUtils.getStringParameterFromHashMap(hashMap, "eliminar") != null){
	// Intento elimiar un registro, si he ejecuta volvemos a la pantalla del listado
	try{
		ClienteControlador.getControlador().remove(cliente);
		response.sendRedirect(request.getContextPath() + "jsp/listadoClientes.jsp"); // Redirección al listado
	} catch (Exception ex){
		mensajeAlUsuario = "ERROR - Imposible eliminar. Es posible que exitan restricciones.";
	}
}

// Segunda acción posible: guardar
if (RequestUtils.getStringParameterFromHashMap(hashMap, "guardar") != null){
	// Obtengo todos los datos del cliente y los guardo en la BBDD
	try{
		cliente.setDniNie(RequestUtils.getStringParameterFromHashMap(hashMap, "dniNie"));
		cliente.setNombre(RequestUtils.getStringParameterFromHashMap(hashMap, "nombre"));
		cliente.setApellidos(RequestUtils.getStringParameterFromHashMap(hashMap, "apellidos"));
		try {
			cliente.setFechaNac(sdfFechaNac.parse(RequestUtils.getStringParameterFromHashMap(hashMap, "fechaNac")));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		cliente.setLocalidad(RequestUtils.getStringParameterFromHashMap(hashMap, "localidad"));
		
		cliente.setActivo(Boolean.parseBoolean(RequestUtils.getStringParameterFromHashMap(hashMap, "activo")));
		
		//Finalmente guardo el objeto de tipo profesor
		ClienteControlador.getControlador().save(cliente);
		mensajeAlUsuario = "Guardado correctamente";
	} catch (Exception e){
		throw new ServletException(e);
	}
}
// Ahora muestro la pantalla de respuesta al usuario
%>

<div class="container py-3">
	<%
		String tipoAlerta = "alert-success";
	if (mensajeAlUsuario != null && mensajeAlUsuario != ""){
		if(mensajeAlUsuario.startsWith("ERROR")){
			tipoAlerta = "alert-danger";
		}
	
	%>
	<div class="alert <%=tipoAlerta %> alert-dismissible fade show">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<%=mensajeAlUsuario %>
	</div>
	<%
	}
	%>
	<div class="row">
		<div class="mx-auto col-sm-6">
			<!-- form user info -->
			<div class="card">
				<div class="card-header">
					<h4 class="mb-0">Ficha de cliente</h4>
				</div>
				<div class="card-body">
					<a href="listadoClientes.jsp?idPag=1" class="btn btn-info btn-sm" role="button">Volver al listado</a>
					<form id="form1" name="form1" method="post" 
					action="fichaCliente.jsp" enctype="multipart/form-data"
					class="form" role="form" autocomplete="off">
					 <p />
					 <input type="hidden" name="idCliente" value="<%=cliente.getId()%>"/>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="dniNie">Dni o Nie:</label>
					 	<div class="col-lg-9">
					 		<input name="dniNie" class="form-control" type="text" 
					 		id="dniNie" value="<%=cliente.getDniNie() %>" />
					 	</div>
					 </div>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="nombre">Nombre:</label>
					 	<div class="col-lg-9">
					 		<input name="nombre" class="form-control" type="text" id="nombre" value="<%=cliente.getNombre()%>" />
					 	</div>
					 </div>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="apellidos">Apellidos:</label>
					 	<div class="col-lg-9">
					 		<input name="apellidos" class="form-control" type="text" id="apellidos" value="<%=cliente.getApellidos()%>" />
					 	</div>
					 </div>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="fechaNac">Fecha de Nacimiento:</label>
					 	<div class="col-lg-9">
					 		<input name="fechaNac" class="form-control" type="text"
					 		 id="fechaNac" value="<%= ((cliente.getFechaNac() != null)? sdfFechaNac.format(cliente.getFechaNac()) : "") %>" />
					 	</div>
					 </div>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="localidad">Localidad:</label>
					 	<div class="col-lg-9">
					 		<input name="localidad" class="form-control" type="text" id="localidad" value="<%=cliente.getLocalidad()%>" />
					 	</div>
					 </div>
 				    <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-check-label" for="activo">Estado:</label>
					 	<div class="col-lg-9">
					 		<input name="activo" class="form-check-input" type="checkbox" id="activo" value="<%=true%>" />
					 	</div>
					 </div> 
 					 
					 <div class="form-group row">
							<div class="col-lg-9">
								<input type="submit" name="guardar" class="btn btn-info" value="Guardar" /> 
								<input type="submit" name="eliminar" class="btn btn-secondary" value="Eliminar" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="pie.jsp"%>