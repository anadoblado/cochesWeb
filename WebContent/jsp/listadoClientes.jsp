<%@ page
	import="java.util.List,
	model.controladores.ClienteControlador,
	model.Cliente"%>

<jsp:include page="cabecera.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="Listado de clientes" />
</jsp:include>

<div class="container">
	<h1>Listado de Clientes</h1>
	<table class="table table-hover">
		<thead class="thead-dark">
			<tr>
				<th>Dni/Nie</th>
				<th>Nombre</th>
				<th>Apellidos</th>
				<th>Fecha de nacimiento</th>
				<th>Localidad</th>
				<th>Activo</th>
			</tr>
		</thead>
		<tbody>
			<%
				// Hasta la fila anterior ha llegado la primera fila de t�tulos de la tabla de cliente de la gesti�n de ventas
			// En las siguietnes l�neas se crea una fila "elemento <tr>" por cada fila de la tabla de BBDD "cliente"
			List<Cliente> clientes = ClienteControlador.getControlador().findAll();
			for (Cliente cliente : clientes) {
			%>
			<tr>
				<td><a
					href="fichaCliente.jsp?idCliente=<%=cliente.getId()%>"> <%=cliente.getDniNie()%>
				</a></td>
				<td><%=cliente.getNombre()%></td>
				<td><%=cliente.getApellidos()%></td>
				<td><%=cliente.getFechaNac()%></td>
				<td><%=cliente.getLocalidad()%></td>
				<td><%=cliente.getActivo()%></td>
			</tr>
			<%
				}
			// Al finalizar de exponer la lista de profesores termino la tabla y cierro el fichero HTML
			%>
		</tbody>
	</table>
	<p />
	<input type="submit" class="btn btn-primary" name="nuevo" value="Nuevo"
		onclick="window.location='fichaCliente.jsp?idCliente=0'" />
</div>
<%@ include file="pie.jsp"%>