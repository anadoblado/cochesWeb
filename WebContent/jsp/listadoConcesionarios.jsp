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
		<%! private int offset, paginationIndex; %>
		<% offset = getOffset(request.getParameter("idPag"));
			paginationIndex = Integer.parseInt(request.getParameter("idPag"));
		%>
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
	<input type="submit" class="btn btn-info" name="nuevo" value="Nuevo"
		onclick="window.location='fichaConcesionario.jsp?idConcesionario=0'" />
		
	<ul class="pagination justify-content-center">
	   <li class="page-item previus"><a class="page-link" href="?idPag=1">First</a></li>
	  <%
	  List<Concesionario> c = ConcesionarioControlador.getControlador().findAll();
	  double size = Math.ceil(c.size() / 5);
	  
	  if (paginationIndex > 1){
		  %> 
			  <li class="page-item"><a class="page-link" href="?idPag=<%= paginationIndex-1 %>" ><%= paginationIndex-1 %></a></li>
		  <%
		  }
		  %>
		  <li class="page-item active"><a class="page-link" href="?idPag=<%= paginationIndex %>" ><%= paginationIndex %></a></li>
		  <li class="page-item"><a class="page-link" href="?idPag=<%= paginationIndex+1 %>" ><%= paginationIndex+1 %></a></li>
		  <li class="page-item"><a class="page-link" href="?idPag=<%=Math.round(size) %>" >Last</a></li>
	  </ul>
 
</div>
<%@ include file="pie.jsp"%>