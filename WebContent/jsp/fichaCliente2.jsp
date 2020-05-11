<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page 

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
	// Obtengo una HashMap con todos los parámetros del request, sea este del tipo que sea;
HashMap<String, Object> hashMap = RequestUtils.requestToHashMap(request);
// Para plasmar la información de un profesor determinado utilizaremos un parámetro, que debe llegar a este Servlet obligatoriamente
// El parámetro se llama "idProfesor" y gracias a él podremos obtener la información del profesor y mostrar sus datos en pantalla
Cliente cli = null;
// Obtengo el profesor a editar, en el caso de que el profesor exista se cargarán sus datos, en el caso de que no exista quedará a null
try {
	int idCliente = RequestUtils.getIntParameterFromHashMap(hashMap, "idCliente"); // Necesito obtener el id del profesor que se quiere editar. En caso de un alta
	// de profesor obtendríamos el valor 0 como idProfesor
	
	
	if (idCliente != 0) {
		cli = (Cliente) ClienteControlador.getControlador().find(idCliente);
	}
} catch (Exception e) {
	e.printStackTrace();
}
// Inicializo unos valores correctos para la presentación del profesor
if (cli == null) {
	cli = new Cliente();
}
if (cli.getNombre() == null)
	cli.setNombre("");
if (cli.getApellidos() == null)
	cli.setApellidos("");
if (cli.getLocalidad() == null)
	cli.setLocalidad("");
if (cli.getDniNie() == null)
	cli.setDniNie("");
if (cli.getFechaNac() == null) 
	cli.setFechaNac(null);
// Ahora debo determinar cuál es la acción que este página debería llevar a cabo, en función de los parámetros de entrada al Servlet.
// Las acciones que se pueden querer llevar a cabo son tres:
//    - "eliminar". Sé que está es la acción porque recibiré un un parámetro con el nombre "eliminar" en el request
//    - "guardar". Sé que está es la acción elegida porque recibiré un parámetro en el request con el nombre "guardar"
//    - Sin acción. En este caso simplemente se quiere editar la ficha
// Variable con mensaje de información al usuario sobre alguna acción requerida
String mensajeAlUsuario = "";
// Primera acción posible: eliminar
if (RequestUtils.getStringParameterFromHashMap(hashMap, "eliminar") != null) {
	// Intento eliminar el registro, si el borrado es correcto redirijo la petición hacia el listado de profesores
	try {
		ClienteControlador.getControlador().remove(cli);
		response.sendRedirect(request.getContextPath() + "/jsp/ListadoCliente.jsp"); // Redirección del response hacia el listado
	} catch (Exception ex) {
		mensajeAlUsuario = "ERROR - Imposible eliminar. Es posible que existan restricciones.";
	}
}
// Segunda acción posible: guardar
if (RequestUtils.getStringParameterFromHashMap(hashMap, "guardar") != null) {
	// Obtengo todos los datos del profesor y los almaceno en BBDD
	try {
		cli.setNombre(RequestUtils.getStringParameterFromHashMap(hashMap, "nombre"));
		cli.setApellidos(RequestUtils.getStringParameterFromHashMap(hashMap, "apellido"));
		cli.setLocalidad(RequestUtils.getStringParameterFromHashMap(hashMap, "localidad"));
		cli.setDniNie(RequestUtils.getStringParameterFromHashMap(hashMap, "dniNie"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = sdf.parse(RequestUtils.getStringParameterFromHashMap(hashMap, "fechaNac"));
		cli.setFechaNac(date);
		
		// Finalmente guardo el objeto de tipo profesor 
		ClienteControlador.getControlador().save(cli);
		mensajeAlUsuario = "Guardado correctamente";
	} catch (Exception e) {
		throw new ServletException(e);
	}
}
// Ahora muestro la pantalla de respuesta al usuario
%>


<div class="container py-3">
	<%
		String tipoAlerta = "alert-success";
	if (mensajeAlUsuario != null && mensajeAlUsuario != "") {
		if (mensajeAlUsuario.startsWith("ERROR")) {
			tipoAlerta = "alert-danger";
		}
	%>
	<div class="alert <%=tipoAlerta%> alert-dismissible fade show">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<%=mensajeAlUsuario%>
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

					<a href="ListadoCliente.jsp">Ir al listado de cliente</a>
					<form id="form1" name="form1" method="post"
						action="FichaCliente.jsp" enctype="multipart/form-data"
						class="form" role="form" autocomplete="off">
						<p />
						<div class="form-group row">
							<label class="col-lg-3 col-form-label form-control-label"
								for="nombre">Nombre:</label>
							<div class="col-lg-9">
								<input name="nombre" class="form-control" type="text"
									id="nombre" value="<%=cli.getNombre()%>" />
							</div>
						</div>
						<div class="form-group row">
							<label class="col-lg-3 col-form-label form-control-label"
								for="apellidos">Apellidos:</label>
							<div class="col-lg-9">
								<input name="apellidos" class="form-control" type="text"
									id="apellidos" value="<%=cli.getApellidos()%>" />
							</div>
						</div>
						<div class="form-group row">
							<label class="col-lg-3 col-form-label form-control-label"
								for="localidad">Localidad:</label>
							<div class="col-lg-9">
								<input name="localidad" class="form-control" type="text"
									id="localidad" value="<%=cli.getLocalidad()%>" />
							</div>
						</div>
						<div class="form-group row">
							<label class="col-lg-3 col-form-label form-control-label"
								for="dniNie">DniNie:</label>
							<div class="col-lg-9">
								<input name="dniNie" class="form-control" type="text"
									id="dniNie" value="<%=cli.getDniNie()%>" />
							</div>
						</div>
						<div class="form-group row">
							<label class="col-lg-3 col-form-label form-control-label"
								for="fechaNac">Fecha Nacimiento:</label>
							<div class="col-lg-9">
								<input name="fechaNac" class="form-control" type="text"
									id="fechaNac" value="<%=cli.getFechaNac()%>" />
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