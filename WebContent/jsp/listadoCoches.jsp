<%@ page
	import="java.util.List,
	model.controladores.CocheControlador,
	model.Coche"%>

<jsp:include page="cabecera.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="Listado de coches" />
</jsp:include>

<div class="container">
	<h1>Listado de Coches</h1>
	<table class="table table-hover">
		<thead class="thead-dark">
			<tr>
				<th>Bastidor</th>
				<th>Color</th>
				<th>Modelo</th>
				<th>Fabricante</th>
			</tr>
		</thead>
		<tbody>
			<%
				// Hasta la fila anterior ha llegado la primera fila de t�tulos de la tabla de coche de la gesti�n de ventas
			// En las siguietnes l�neas se crea una fila "elemento <tr>" por cada fila de la tabla de BBDD "coche"
			List<Coche> coches = CocheControlador.getControlador().findAll();
			for (Coche coche : coches) {
			%>
			<tr>
				<td><a
					href="fichaCoche.jsp?idCoche=<%=coche.getId()%>"> <%=coche.getBastidor()%>
				</a></td>
				<td><%=coche.getColor()%></td>
				<td><%=coche.getModelo()%></td>
				<td><%=coche.getFabricante()%></td>
				
			</tr>
			<%
				}
			// Al finalizar de exponer la lista de profesores termino la tabla y cierro el fichero HTML
			%>
		</tbody>
	</table>
	<p />
	<input type="submit" class="btn btn-primary" name="nuevo" value="Nuevo"
		onclick="window.location='fichaCoche.jsp?idCoche=0'" />
</div>
<%@ include file="pie.jsp"%>