<%@ page
	import="java.util.List,
	model.controladores.ConcesionarioControlador,
	model.Concesionario"%>

<jsp:include page="cabecera.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="Listado de concesionarios" />
</jsp:include>

<div class="container">
	<h1>Listado de Concesionarios</h1>
	<table class="table table-hover">
		<thead class="thead-dark">
			<tr>
				<th>Cif</th>
				<th>Localidad</th>
				<th>Nombre</th>
			</tr>
		</thead>
		<tbody>
			<%
				// Hasta la fila anterior ha llegado la primera fila de títulos de la tabla de concesionario de la gestión de ventas
			// En las siguietnes líneas se crea una fila "elemento <tr>" por cada fila de la tabla de BBDD "concesionario"
			List<Concesionario> concesionarios = ConcesionarioControlador.getControlador().findAll();
			for (Concesionario concesionario : concesionarios) {
			%>
			<tr>
				<td><a
					href="fichaConcesionario.jsp?idConcesionario=<%=concesionario.getId()%>"> <%=concesionario.getCif()%>
				</a></td>
				<td><%=concesionario.getLocalidad()%></td>
				<td><%=concesionario.getNombre()%></td>
			</tr>
			<%
				}
			// Al finalizar de exponer la lista de profesores termino la tabla y cierro el fichero HTML
			%>
		</tbody>
	</table>
	<p />
	<input type="submit" class="btn btn-primary" name="nuevo" value="Nuevo"
		onclick="window.location='fichaConcesionario.jsp?idConcesionario=0'" />
</div>
<%@ include file="pie.jsp"%>