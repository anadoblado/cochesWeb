package servlets;

import Utils.RequestUtils;

import Utils.SuperTipoServlet;
import model.Fabricante;
import model.controladores.FabricanteControlador;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class fichaFabricante
 */
@WebServlet("/fichaFabricante")
public class fichaFabricante extends SuperTipoServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see SuperTipoServlet#SuperTipoServlet()
     */
    public fichaFabricante() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Obtengo una HashMap con todos los parámetros del request, sea este del tipo que sea;
		HashMap<String, Object> hashMap = RequestUtils.requestToHashMap(request);
		
		
		// Creo un Fabricante a null para recibir ahí los parámetros
		Fabricante fabricante = null;
		
		// buscamos cómo conseguir el concesionario a editar que será a través del parámetro idConcesionario
		try {
			int idFabricante = RequestUtils.getIntParameterFromHashMap(hashMap, "idFabricante");// necsitamos saber el id para acceder al 
			//fabricante que vamos a editar, si es nuevo, sale a 0
				if (idFabricante != 0) {
					fabricante = (Fabricante) FabricanteControlador.getControlador().find(idFabricante);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Inicializo el objeto fabricante para la presentación
		if (fabricante == null) {
			fabricante = new Fabricante();
		}
		if (fabricante.getCif() == null) fabricante.setCif("");
		if (fabricante.getNombre() == null) fabricante.setNombre("");
		
		// el Server puede llevar a cabo tres acciones:
		// "eliminar" identificamos esta acción porque recibiremos un parámetro con el nombre "eliminar" en el request
		// "guardar" esta acción se identificará porque recibiremos un parámetro con el nombre "guardar" en el request
		// si no hay ninguna de las acciones anteriores, será que se quiere modificar

		// variable con el nobmre de la acción
		String mensajeAlUsuario = "";

		// Acción eliminar
		if(RequestUtils.getStringParameterFromHashMap(hashMap, "eliminar") != null) {
			// intento eliminar el registro, si el borrado es correcto redirijo la petición hacia el listado de concesionarios
			try {
				FabricanteControlador.getControlador().remove(fabricante);
				response.sendRedirect(request.getContextPath() + "/ListadoFabricantes"); // hay que hacer la lista
			} catch (Exception e2) {
				mensajeAlUsuario = "No se puedo eliminar. Quizás haya restricciones asociadas a este registro";
			}
		}

		// Acción guardar
		if(RequestUtils.getStringParameterFromHashMap(hashMap, "guardar") != null) {
			// primero obtenemos todos los datos del concesionario para luego guardarlos en la BBDD
			try {
				fabricante.setCif(RequestUtils.getStringParameterFromHashMap(hashMap, "cif"));
				fabricante.setNombre(RequestUtils.getStringParameterFromHashMap(hashMap, "nombre"));
				byte[] posibleImagen = RequestUtils.getByteArrayFromHashMap(hashMap, "ficheroImagen");
				if (posibleImagen != null && posibleImagen.length > 0) {
					fabricante.setImagen(posibleImagen);
				}

				FabricanteControlador.getControlador().save(fabricante);
				mensajeAlUsuario = "Guardado correctamente";
			} catch (Exception e1) {
				throw new ServletException(e1);
			}
		}

		// Ahora mostramos la pantalla de respuesta al usuario
		response.getWriter().append(this.getCabeceraHTML("Ficha de fabricante"));
		response.getWriter().append("\r\n" + 
				"<script> \r\n" +
				"function validateForm() {" +
				"var x = document.forms[\"form1\"][\"cif\"].value;" +
				"var y = document.forms[\"form1\"][\"nombre\"].value;" +
				"if (x == \"\"){" +
				"alert (\"No has introducido el cif\");"+
				"return false;"+
				"}" +
				"if (y == \"\"){" +
				"alert (\"No has introducido el nombre\");"+
				"return false;"+
				"}" +
				"}" +
				"</script>\r\n" +
				"\r\n" + 
				"<body " +((mensajeAlUsuario != null && mensajeAlUsuario != "")? "onLoad=\"alert('" + mensajeAlUsuario + "');\"" : "")  + " >\r\n" +
				"<h1>Ficha de fabricante</h1>\r\n" + 
				"<a href=\"ListadoFabricantes\">Ir al listado de fabricantes</a>" +
				"<form id=\"form1\" name=\"form1\" method=\"post\" action=\"fichaFabricante\" enctype=\"multipart/form-data\" onsubmit=\"return validateForm()\">\r\n" + 
				" <img src=\"Utils/DownloadImagenFabricante?idFabricante=" + fabricante.getId() + "\" width='100px' height='100px'/>" +
				" <input type=\"hidden\" name=\"idFabricante\" value=\"" + fabricante.getId() + "\"\\>" +
				"  <p>\r\n" + 
				"    <label for=\"ficheroImagen\">Imagen:</label>\r\n" + 
				"    <input name=\"ficheroImagen\" type=\"file\" id=\"ficheroImagen\" />\r\n" + 
				"  </p>\r\n" + 
				"  <p>\r\n" + 
				"    <label for=\"cif\">Cif:</label>\r\n" +
				"    <input name=\"cif\" type=\"text\" id=\"cif\"  value=\"" + fabricante.getCif()  + "\" />\r\n" + 
				"  </p>\r\n" +
				"  <p>\r\n" + 
				"    <label for=\"nombre\">Nombre: </label>\r\n" +  
				"    <input name=\"nombre\" type=\"text\" id=\"nombre\" value=\"" + fabricante.getNombre()  + "\" />\r\n" +
				"  </p>\r\n" +
				"  <p>\r\n" + 
				"    <input type=\"submit\" name=\"guardar\" value=\"Guardar\" />\r\n" + 
				"    <input type=\"submit\" name=\"eliminar\" value=\"Eliminar\" />\r\n" + 
				"  </p>\r\n" + 
				"</form>" +
				"");
		response.getWriter().append(this.getPieHTML());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
