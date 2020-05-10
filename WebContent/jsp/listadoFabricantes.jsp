<%@ page
	import="java.util.List,
	model.controladores.FabricanteControlador,
	model.Fabricante"%>

<jsp:include page="cabecera.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="Listado de fabricantes" />
</jsp:include>


<div class="container">
	<h1>Listado de Fabricantes</h1>
	<table class="table table-hover">
		<thead class="thead-dark">
			<tr>
				<th>Cif</th>
				<th>Nombre</th>
			</tr>
		</thead>
		<tbody>
			<%
				// Hasta la fila anterior ha llegado la primera fila de títulos de la tabla de fabricante de la gestión de ventas
			// En las siguietnes líneas se crea una fila "elemento <tr>" por cada fila de la tabla de BBDD "fabricante"
			List<Fabricante> fabricantes = FabricanteControlador.getControlador().findAll();
			for (Fabricante fabricante : fabricantes) {
			%>
			<tr>
				<td><a
					href="fichaFabricante.jsp?idFabricante=<%=fabricante.getId()%>"> <%=fabricante.getCif()%>
				</a></td>
				<td><%=fabricante.getNombre()%></td>
			</tr>
			<%
				}
			// Al finalizar de exponer la lista de fabricantes termino la tabla y cierro el fichero HTML
			%>
		</tbody>
	</table>
	<p />
	<input type="submit" class="btn btn-primary" name="nuevo" value="Nuevo"
		onclick="window.location='fichaFabricante.jsp?idFabricante=0'" />
</div>
<%@ include file="pie.jsp"%>