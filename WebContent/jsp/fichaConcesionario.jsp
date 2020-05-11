<%@ page
	import="java.util.List, 
	java.util.HashMap,
	Utils.RequestUtils,
	model.Concesionario,
	model.controladores.ConcesionarioControlador"%>
	
<jsp:include page="cabecera.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="Ficha de concesionario" />
</jsp:include>

<% 
	// Obtengo un HashMap con todos los par�metros del request, sea del tipo que sea;
HashMap<String, Object> hashMap = RequestUtils.requestToHashMap(request);

	// Para plasmar la informaci�n de un concesionario determinado utilizaremos un par�metro que debe llegar al Servlet obligatoriamente
	// El par�metro se llama "idConcesionario" y gracias a �l podremos obtener la info del concesionario y mostrar sus datos por pantalla
	
Concesionario concesionario = null;
	// Obtengo el concesionario a editar, en el caso de que el concesionario ya exista se cargar�n los datos, y si no, quedar� a null
try {
	int idConcesionario = RequestUtils.getIntParameterFromHashMap(hashMap, "idConcesionario"); // Necesito obtener el id del concesionario que se quiere editar
	// Si el Concesionario es nuevo, el valor del id ser� 0
	if (idConcesionario != 0){
		concesionario = (Concesionario) ConcesionarioControlador.getControlador().find(idConcesionario);
	}
} catch (Exception e){
	e.printStackTrace();
}
// Inicio la carga de los valores de un concesionario
if (concesionario == null){
	concesionario = new Concesionario();
}
if (concesionario.getCif() == null) concesionario.setCif("");
if (concesionario.getLocalidad() == null) concesionario.setLocalidad("");
if (concesionario.getNombre() == null) concesionario.setNombre("");


// En funci�n de los par�metros de entrada del Servlet, debemos determinar qu� se har� seg�n 3 acciones:
// - "eliminar" sabemos que es esta opci�n porque recibe un par�metro con el nombre "elimiar" en el request
// - "guardar" sabemos que es esta opci�n porque recibe un par�metro con el nombre "guardar" en el request
// - Sin acci�n. En este caso, s�lo se quiere editar

// Variable con mensaje de informaci�n al usuario sobre alguna acci�n requerido
String mensajeAlUsuario = "";

// Primera acci�n posible: eliminar
if (RequestUtils.getStringParameterFromHashMap(hashMap, "eliminar") != null){
	// Intento elimiar un registro, si he ejecuta volvemos a la pantalla del listado
	try{
		ConcesionarioControlador.getControlador().remove(concesionario);
		response.sendRedirect(request.getContextPath() + "jsp/listadoConcesionarios2.jsp"); // Redirecci�n al listado
	} catch (Exception ex){
		mensajeAlUsuario = "ERROR - Imposible eliminar. Es posible que exitan restricciones.";
	}
}

// Segunda acci�n posible: guardar
if (RequestUtils.getStringParameterFromHashMap(hashMap, "guardar") != null){
	// Obtengo todos los datos del concesionario y los guardo en la BBDD
	try{
		concesionario.setCif(RequestUtils.getStringParameterFromHashMap(hashMap, "cif"));
		concesionario.setLocalidad(RequestUtils.getStringParameterFromHashMap(hashMap, "localidad"));
		concesionario.setNombre(RequestUtils.getStringParameterFromHashMap(hashMap, "nombre"));
		byte[] posibleImagen = RequestUtils.getByteArrayFromHashMap(hashMap, "ficheroImagen");
		if (posibleImagen != null && posibleImagen.length > 0){
			concesionario.setImagen(posibleImagen);
		}
		
		//Finalmente guardo el objeto de tipo profesor
		ConcesionarioControlador.getControlador().save(concesionario);
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
					<h4 class="mb-0">Ficha de concesionario</h4>
				</div>
				<div class="card-body">
					<a href="listadoConcesionarios2.jsp">Ir al listado de Concesionarios</a>
					<form id="form1" name="form1" method="post" 
					action="fichaConcesionario.jsp" enctype="multipart/form-data"
					class="form" role="form" autocomplete="off">
					<p />
					<img class="mx-auto d-block round-circle" 
					 src="../Utils/DownloadImagenConcesionario?idConcesionario=<%=concesionario.getId()%>"
					 width="100px" height="100px" />
					 <p />
					 <input type="hidden" name="idConcesionario" value="<%=concesionario.getId()%>" />
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
					 		<input name="cif" class="form-control" type="text" id="cif" value="<%=concesionario.getCif() %> ">
					 	</div>
					 </div>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="localidad">Localidad:</label>
					 	<div class="col-lg-9">
					 		<input name="localidad" class="form-control" type="text" id="localidad" value="<%=concesionario.getLocalidad()%>">
					 	</div>
					 </div>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="nombre">Nombre:</label>
					 	<div class="col-lg-9">
					 		<input name="nombre" class="form-control" type="text" id="nombre" value="<%=concesionario.getNombre()%>">
					 	</div>
					 </div>
					 	<div class="form-group row">
							<div class="col-lg-9">
								<input type="submit" name="guardar" class="btn btn-primary"
									value="Guardar" /> <input type="submit" name="eliminar"
									class="btn btn-secondary" value="Eliminar" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="pie.jsp"%>