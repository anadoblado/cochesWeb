package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Coche;
import model.controladores.CocheControlador;



/**
 * Servlet implementation class ListadoCoches
 */
@WebServlet("/ListadoCoches")
public class ListadoCoches extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListadoCoches() {
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
				"<h1>Listado de coches</h1>\r\n" + 
				"<p></p>" +
				"<a href=\"index.html\">Ir al inicio</a>" +
				"<table width=\"95%\" border=\"1\">\r\n" +
				"  <tr>\r\n" + 
				"    <th scope=\"col\">Bastidor</th>\r\n" + 
				"    <th scope=\"col\">Color</th>\r\n" +
				"    <th scope=\"col\">Modelo</th>\r\n" +
				"  </tr>\r\n");

		// Son las líneas para hacer con HTML la estructura de la tabla
		// A continuación le daremos los valores que deben de ir en la tabla
		
		List<Coche> coches = CocheControlador.getControlador().findAll();
		for (Coche c : coches) {
			response.getWriter().append("" + 
				"  <tr>\r\n" + 
				"    <td><a href=\"fichaCoche?idCoche=" + c.getId() + "\">" + c.getBastidor() + "</a></td>\r\n" +
				"	 <td>" + c.getColor() + "</td>\r\n" +
				"	 <td>" + c.getModelo() + "</td>\r\n" +
				"  </tr>\r\n"
		);
		}
		
		// Cerramos la tabla y el HTML
		response.getWriter().append("" +
				"</table>\r\n" +
				"<p/><input type=\"submit\"  name=\"nuevo\" value=\"Nuevo\"  onclick=\"window.location='fichaCoche?idCoche=0'\"/>" +
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
