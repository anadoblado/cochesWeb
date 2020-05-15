<%@ page
	import="java.util.List,
	model.controladores.VentaControlador,
	model.Venta"%>

<jsp:include page="cabecera.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="Listado de ventas" />
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
	<h1>Listado de Ventas</h1>
	<table class="table table-hover">
		<thead class="thead-dark">
			<tr>
				<th>Concesionario</th>
				<th>Cliente</th>
				<th>Coche</th>
				<th>Precio de venta</th>
				<th>Fecha</th>
			</tr>
		</thead>
		<tbody>
			<%
				// Hasta la fila anterior ha llegado la primera fila de títulos de la tabla de venta de la gestión de ventas
			// En las siguietnes líneas se crea una fila "elemento <tr>" por cada fila de la tabla de BBDD "venta"
			List<Venta> ventas = VentaControlador.getControlador().findAllLimited(5, offset);
			for (Venta venta : ventas) {
			%>
			<tr>
				<td><a
					href="fichaVenta.jsp?idVenta=<%=venta.getId()%>"> <%=venta.getConcesionario()%>
				</a></td>
				<td><%=venta.getCliente()%></td>
				<td><%=venta.getCoche()%></td>
				<td><%=venta.getPrecioVenta()%></td>
				<td><%=venta.getFecha()%></td>
			</tr>
			<%
				}
			// Al finalizar de exponer la lista de profesores termino la tabla y cierro el fichero HTML
			%>
		</tbody>
	</table>
	<p />
	<input type="submit" class="btn btn-info" name="nuevo" value="Nuevo"
		onclick="window.location='fichaVenta.jsp?idVenta=0'" />
		
	<ul class="pagination justify-content-center">
	   <li class="page-item"><a class="page-link text-info" href="?idPag=1">First</a></li>
	  
	  <%
//	  List<Venta> c = VentaControlador.getControlador().findAll();
	  int num = VentaControlador.getControlador().numRegistros();
	   double size = Math.ceil(num / 5);
	  
	   if(paginationIndex > 1){
	   
		  %> 
		     <li class="page-item"><a class="page-link text-info" href="?idPag=<%= paginationIndex-1 %>" ><%= paginationIndex-1 %></a></li>
			 
		  <%
		  }
		  %>
		  <li class="page-item active"><a class="page-link" href="?idPag=<%= paginationIndex %>" ><%= paginationIndex %></a></li>
		<%
		if (paginationIndex < size){
		%>
		<li class="page-item"><a class="page-link text-info" href="?idPag=<%= paginationIndex+1 %>" ><%= paginationIndex+1 %></a></li>  
		<%
		  }
		  %>
		     
		  <li class="page-item"><a class="page-link text-info" href="?idPag=<%=Math.round(size)%>">Last</a></li>
	 </ul> 
</div>
<%@ include file="pie.jsp"%>