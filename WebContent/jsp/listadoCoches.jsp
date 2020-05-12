<%@ page
	import="java.util.List,
	model.controladores.CocheControlador,
	model.Coche"%>

<jsp:include page="cabecera.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="Listado de coches" />
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
			List<Coche> coches = CocheControlador.getControlador().findAllLimited(5, offset);
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
		
		
	<ul class="pagination">
	  <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
	  <%
	  List<Coche> c = CocheControlador.getControlador().findAll();
	  double size = Math.ceil(c.size() / 5);
	  for(int i = 1; i <= size; i++){
	  %> 
		  <li class="page-item"><a class="page-link" href="?idPag=<%= i %>" ><%= i %></a></li>
	  <%
	  }
	  %>
</div>
<%@ include file="pie.jsp"%>