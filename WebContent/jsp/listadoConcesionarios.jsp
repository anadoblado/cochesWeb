<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.If"%>
<%@ page
	import="java.util.List,
	model.controladores.ConcesionarioControlador,
	model.Concesionario"%>

<jsp:include page="cabecera.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="Listado de concesionarios" />
</jsp:include>

	<%!
			public int getOffset(String param){
				int offset = Integer.parseInt(param);
				if(offset > 1){
					return 5 * offset;
				}
				else{
					return 0;
				}
				
			}
		%>
		<%! private int offset; %>
		<%= offset = getOffset(request.getParameter("idPag")) %>
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
			List<Concesionario> concesionarios = ConcesionarioControlador.getControlador().findAllLimited(5, offset );
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
		
	<ul class="pagination">
	  <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
	  <%
	  List<Concesionario> c = ConcesionarioControlador.getControlador().findAll();
	  double size = Math.ceil(c.size() / 5);
	  for(int i = 1; i <= size; i++){
	  %> 
		  <li class="page-item"><a class="page-link" href="?idPag=<%= i %>" ><%= i %></a></li>
	  <%
	  }
	  %>
<!--  <li class="page-item"><a class="page-link" href="?idPag=1">1</a></li>
	  <li class="page-item"><a class="page-link" href="?idPag=2">2</a></li>
	  <li class="page-item"><a class="page-link" href="?idPag=3">3</a></li>
	  <li class="page-item"><a class="page-link" href="#">Next</a></li>
-->	</ul> 
</div>
<%@ include file="pie.jsp"%>