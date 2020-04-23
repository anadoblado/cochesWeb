package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Concesionario;
import model.controladores.ConcesionarioControlador;

/**
 * Servlet implementation class ListadoConcesionarios
 */
@WebServlet("/ListadoConcesionarios")
public class ListadoConcesionarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListadoConcesionarios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitonial//EN\" \"http://www.w3.org/TR/DTD/xhtml1-transitional.dtd\">\r\n" +
				"<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n" + 
				"<head>\r\n" +
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\r\n" +
				"</head>\r\n" + 
				"\r\n" +
				"<body>\r\n" + 
				"<h1>Listado de concesionarios</h1>\r\n" + 
				"<table width=\"95%\" border=\"1\">\r\n" +
				"  <tr>\r\n" + 
				"    <th scope=\"col\">Cif</th>\r\n" + 
				"    <th scope=\"col\">Nombre</th>\r\n" + 
				"    <th scope=\"col\">Localidad</th>\r\n" + 
				"  </tr>\r\n");
		
		// Son las líneas para hacer con HTML la estructura de la tabla
		// A continuación le daremos los valores que deben de ir en la tabla
		
		List<Concesionario> concesionarios = ConcesionarioControlador.getControlador().findAll();
		for (Concesionario c : concesionarios) {
			response.getWriter().append("" + 
				"  <tr>\r\n" + 
				"    <td><a href=\"fichaConcesionarios?idConcesionario=" + c.getId() + "\">" + c.getCif() + "</a></td>\r\n" +
				"	 <td>" + c.getNombre() + "</td>\r\n" +
				"	 <td>" + c.getLocalidad() + "</td>\r\n" +
				"  </tr>\r\n"
		);
		}
		
		// Cerramos la tabla y el HTML
		response.getWriter().append("" +
				"</table>\r\n" +
				"<p/><input type=\"submit\"  name=\"nuevo\" value=\"Nuevo\"  onclick=\"window.location='fichaConcesionarios?idConcesionario=0'\"/>" +
				"</body>\r\n" + 
				"</html>\r\n" + 
				"");
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
