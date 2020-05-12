<%@ page
	import="java.util.List, 
	java.util.HashMap,
	Utils.RequestUtils,
	model.Fabricante,
	model.controladores.FabricanteControlador"%>
	
<jsp:include page="cabecera.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="Ficha de fabricante" />
</jsp:include>

<% 
	// Obtengo un HashMap con todos los parámetros del request, sea del tipo que sea;
HashMap<String, Object> hashMap = RequestUtils.requestToHashMap(request);

	// Para plasmar la información de un concesionario determinado utilizaremos un parámetro que debe llegar al Servlet obligatoriamente
	// El parámetro se llama "idFabricante" y gracias a él podremos obtener la info del concesionario y mostrar sus datos por pantalla
	
Fabricante fabricante = null;
	// Obtengo el fabricante a editar, en el caso de que el concesionario ya exista se cargarán los datos, y si no, quedará a null
try {
	int idFabricante = RequestUtils.getIntParameterFromHashMap(hashMap, "idFabricante"); // Necesito obtener el id del fabricante que se quiere editar
	// Si el Fabricante es nuevo, el valor del id será 0
	if (idFabricante != 0){
		fabricante = (Fabricante) FabricanteControlador.getControlador().find(idFabricante);
	}
} catch (Exception e){
	e.printStackTrace();
}
// Inicio la carga de los valores de un fabricante
if (fabricante == null){
	fabricante = new Fabricante();
}
if (fabricante.getCif() == null) fabricante.setCif("");
if (fabricante.getNombre() == null) fabricante.setNombre("");


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
		FabricanteControlador.getControlador().remove(fabricante);
		response.sendRedirect(request.getContextPath() + "jsp/listadoFabricantes.jsp"); // Redirección al listado
	} catch (Exception ex){
		mensajeAlUsuario = "ERROR - Imposible eliminar. Es posible que exitan restricciones.";
	}
}

// Segunda acción posible: guardar
if (RequestUtils.getStringParameterFromHashMap(hashMap, "guardar") != null){
	// Obtengo todos los datos del concesionario y los guardo en la BBDD
	try{
		fabricante.setCif(RequestUtils.getStringParameterFromHashMap(hashMap, "cif"));
		fabricante.setNombre(RequestUtils.getStringParameterFromHashMap(hashMap, "nombre"));
		byte[] posibleImagen = RequestUtils.getByteArrayFromHashMap(hashMap, "ficheroImagen");
		if (posibleImagen != null && posibleImagen.length > 0){
			fabricante.setImagen(posibleImagen);
		}
		
		//Finalmente guardo el objeto de tipo fabricante
		FabricanteControlador.getControlador().save(fabricante);
		mensajeAlUsuario = "Guardado correctamente";
	} catch (Exception e){
		throw new ServletException(e);
	}
}
// Ahora mustro la pantalla de respuesta al usuario
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
					<h4 class="mb-0">Ficha de fabricante</h4>
				</div>
				<div class="card-body">
					<a href="listadoFabricantes.jsp?idPag=1" class="btn btn-info btn-sm" role="button">Volver al listado</a>
					<form id="form1" name="form1" method="post" 
					action="fichaFabricante.jsp" enctype="multipart/form-data"
					class="form" role="form" autocomplete="off">
					<p />
					<img class="mx-auto d-block round-circle" 
					 src="../Utils/DownloadImagenFabricante?idFabricante=<%=fabricante.getId()%>"
					 width="100px" height="100px" />
					 <p />
					 <input type="hidden" name="idFabricante" value="<%=fabricante.getId()%>" />
					 <div class="form-group row">
					 	<label class="coll-lg-3 col-form-label form-control-label"
					 	for="ficheroImagen">Imagen:</label>
					 	<div class="col-lg-9">
					 		<input name="ficheroImagen" class="form-control-file" type="file" id="ficheroImagen" />
					 	</div>
					 </div>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="cif">Cif:</label>
					 	<div class="col-lg-9">
					 		<input name="cif" class="form-control" type="text" id="cif" value="<%=fabricante.getCif() %> ">
					 	</div>
					 </div>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="nombre">Nombre:</label>
					 	<div class="col-lg-9">
					 		<input name="nombre" class="form-control" type="text" id="nombre" value="<%=fabricante.getNombre()%>">
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