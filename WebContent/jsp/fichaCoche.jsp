<%@page import="model.Fabricante"%>
<%@ page
	import="java.util.List, 
	java.util.HashMap,
	Utils.RequestUtils,
	model.Coche,
	model.Fabricante,
	model.controladores.FabricanteControlador,
	model.controladores.CocheControlador"%>
	
<jsp:include page="cabecera.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="Ficha de coche" />
</jsp:include>

<% 
	// Obtengo un HashMap con todos los parámetros del request, sea del tipo que sea;
HashMap<String, Object> hashMap = RequestUtils.requestToHashMap(request);

	// Para plasmar la información de un coche determinado utilizaremos un parámetro que debe llegar al Servlet obligatoriamente
	// El parámetro se llama "idCoche" y gracias a él podremos obtener la info del coche y mostrar sus datos por pantalla
	
Coche coche = null;
	// Obtengo el coche a editar, en el caso de que el coche ya exista se cargarán los datos, y si no, quedará a null
try {
	int idCoche = RequestUtils.getIntParameterFromHashMap(hashMap, "idCoche"); // Necesito obtener el id del coche que se quiere editar
	// Si el Coche es nuevo, el valor del id será 0
	if (idCoche != 0){
		coche = (Coche) CocheControlador.getControlador().find(idCoche);
	}
} catch (Exception e){
	e.printStackTrace();
}
// Inicio la carga de los valores de un coche
if (coche == null){
	coche = new Coche();
}
if (coche.getBastidor() == null) coche.setBastidor("");
if (coche.getColor() == null) coche.setColor("");
if (coche.getModelo() == null) coche.setModelo("");
if (coche.getFabricante() == null) coche.setFabricante((Fabricante) FabricanteControlador.getControlador().find(1) );



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
		CocheControlador.getControlador().remove(coche);
		response.sendRedirect(request.getContextPath() + "jsp/listadoCoches.jsp"); // Redirección al listado
	} catch (Exception ex){
		mensajeAlUsuario = "ERROR - Imposible eliminar. Es posible que exitan restricciones.";
	}
}

// Segunda acción posible: guardar
if (RequestUtils.getStringParameterFromHashMap(hashMap, "guardar") != null){
	// Obtengo todos los datos del concesionario y los guardo en la BBDD
	try{
		coche.setBastidor(RequestUtils.getStringParameterFromHashMap(hashMap, "bastidor"));
		coche.setColor(RequestUtils.getStringParameterFromHashMap(hashMap, "color"));
		coche.setModelo(RequestUtils.getStringParameterFromHashMap(hashMap, "modelo"));
		coche.setFabricante((Fabricante) FabricanteControlador.getControlador().find(RequestUtils.getIntParameterFromHashMap(hashMap, "idFabricante")));
		byte[] posibleImagen = RequestUtils.getByteArrayFromHashMap(hashMap, "ficheroImagen");
		if (posibleImagen != null && posibleImagen.length > 0){
			coche.setImagen(posibleImagen);
		}
		
		//Finalmente guardo el objeto de tipo profesor
		CocheControlador.getControlador().save(coche);
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
					<h4 class="mb-0">Ficha de coche</h4>
				</div>
				<div class="card-body">
					<a href="listadoCoches.jsp?idPag=1"  class="btn btn-info btn-sm" role="button">Volver al listado</a>
					<form id="form1" name="form1" method="post" 
					action="fichaCoche.jsp" enctype="multipart/form-data"
					class="form" role="form" autocomplete="off">
					<p />
					<img class="mx-auto d-block round-circle" 
					 src="../Utils/DownloadImagenCoche?idCoche=<%=coche.getId()%>"
					 width="150px" height="100px" />
					 <p />
					 <input type="hidden" name="idCoche" value="<%=coche.getId()%>" />
					 <div class="form-group row">
					 	<label class="coll-lg-3 col-form-label form-control-label"
					 	for="ficheroImagen">Imagen:</label>
					 	<div class="col-lg-9">
					 		<input name="ficheroImagen" class="form-control-file" type="file" id="ficheroImagen" />
					 	</div>
					 </div>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="bastidor">Bastidor:</label>
					 	<div class="col-lg-9">
					 		<input name="bastidor" class="form-control" type="text" id="bastidor" value="<%=coche.getBastidor() %> ">
					 	</div>
					 </div>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="color">Color:</label>
					 	<div class="col-lg-9">
					 		<input name="color" class="form-control" type="text" id="color" value="<%=coche.getColor()%>">
					 	</div>
					 </div>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="modelo">Modelo:</label>
					 	<div class="col-lg-9">
					 		<input name="modelo" class="form-control" type="text" id="modelo" value="<%=coche.getModelo()%>">
					 	</div>
					 </div>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="idFabricante">Fabricante:</label>
					 	<div class="col-lg-9">
					 		<select name="idFabricante" id="idFabricante"
									class="form-control">
									<%
										// Inserto los valores del fabricnate y, si el registro tiene un valor concreto, lo establezco
									List<Fabricante> fabricantes = FabricanteControlador.getControlador().findAll();
									for (Fabricante fabricante : fabricantes) {
									%>
									<option value="<%=fabricante.getId()%>"
										<%=((fabricante.getId() == coche.getFabricante().getId()) ? "selected=\"selected\"" : "")%>><%=fabricante.getNombre()%></option>
									<% } %>
								</select>
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