package servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Utils.RequestUtils;
import Utils.SuperTipoServlet;
import model.Concesionario;
import model.controladores.ConcesionarioControlador;

/**
 * Servlet implementation class fichaConcesionarios
 */
@WebServlet("/fichaConcesionarios")
public class fichaConcesionarios extends SuperTipoServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fichaConcesionarios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Obtengo una HashMap con todos los parámetros del request, sea este del tipo que sea;
		HashMap<String, Object> hashMap = RequestUtils.requestToHashMap(request);
		
		// Creo un Concesionario a null para recibir ahí los parámetros
		Concesionario concesionario = null;
		
		// buscamos cómo conseguir el concesionario a editar que será a través del parámetro idConcesionario
		try {
			int idConcesionario = RequestUtils.getIntParameterFromHashMap(hashMap, "idConcesionario");// necsitamos saber el id para acceder al 
			//concesionario que vamos a editar, si es nuevo, sale a 0
				if (idConcesionario != 0) {
					concesionario = (Concesionario) ConcesionarioControlador.getControlador().find(idConcesionario);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Inicializo el objeto porfesor para la presentación
		if (concesionario == null) {
			concesionario = new Concesionario();
		}
		if (concesionario.getCif() == null) concesionario.setCif("");
		if (concesionario.getNombre() == null) concesionario.setNombre("");
		if (concesionario.getLocalidad() == null) concesionario.setLocalidad("");
		
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
				ConcesionarioControlador.getControlador().remove(concesionario);
				response.sendRedirect(request.getContextPath() + "/ListadoConcesionarios"); // hay que hacer la lista
			} catch (Exception e2) {
				mensajeAlUsuario = "No se puedo eliminar. Quizás haya restricciones asociadas a este registro";
			}
		}

		// Acción guardar
		if(RequestUtils.getStringParameterFromHashMap(hashMap, "guardar") != null) {
			// primero obtenemos todos los datos del concesionario para luego guardarlos en la BBDD
			try {
				concesionario.setCif(RequestUtils.getStringParameterFromHashMap(hashMap, "cif"));
				concesionario.setNombre(RequestUtils.getStringParameterFromHashMap(hashMap, "nombre"));
				concesionario.setLocalidad(RequestUtils.getStringParameterFromHashMap(hashMap, "localidad"));
				byte[] posibleImagen = RequestUtils.getByteArrayFromHashMap(hashMap, "ficheroImagen");
				if (posibleImagen != null && posibleImagen.length > 0) {
					concesionario.setImagen(posibleImagen);
				}

				ConcesionarioControlador.getControlador().save(concesionario);
				mensajeAlUsuario = "Guardado correctamente";
			} catch (Exception e1) {
				throw new ServletException(e1);
			}
		}

		// Ahora mostramos la pantalla de respuesta al usuario
		response.getWriter().append(this.getCabeceraHTML("Ficha de concesionario"));
		response.getWriter().append("\r\n" + 
				"<script> \r\n" +
				"function validateForm() {" +
				"var x = document.forms[\"form1\"][\"cif\"].value;" +
				"var y = document.forms[\"form1\"][\"nombre\"].value;" +
				"var z = document.forms[\"form1\"][\"localidad\"].value;" +
				"if (x == \"\"){" +
				"alert (\"No has introducido el cif\");"+
				"return false;"+
				"}" +
				"if (y == \"\"){" +
				"alert (\"No has introducido el nombre\");"+
				"return false;"+
				"}" +
				"if (z == \"\"){" +
				"alert (\"No has introducido el localidad\");"+
				"return false;"+
				"}" +
				"}" +
				"</script>\r\n" +
				"\r\n" + 
				"<body " +((mensajeAlUsuario != null && mensajeAlUsuario != "")? "onLoad=\"alert('" + mensajeAlUsuario + "');\"" : "")  + " >\r\n" +
				"<h1>Ficha de concesionario</h1>\r\n" + 
				"<a href=\"ListadoConcesionarios\">Ir al listado de concesionario</a>" +
				"<form id=\"form1\" name=\"form1\" method=\"post\" action=\"fichaConcesionarios\" enctype=\"multipart/form-data\" onsubmit=\"return validateForm()\">\r\n" + 
				" <img src=\"Utils/DownloadImagenConcesionario?idConcesionario=" + concesionario.getId() + "\" width='100px' height='100px'/>" +
				" <input type=\"hidden\" name=\"idConcesionario\" value=\"" + concesionario.getId() + "\"\\>" +
				"  <p>\r\n" + 
				"    <label for=\"ficheroImagen\">Imagen:</label>\r\n" + 
				"    <input name=\"ficheroImagen\" type=\"file\" id=\"ficheroImagen\" />\r\n" + 
				"  </p>\r\n" + 
				"  <p>\r\n" + 
				"    <label for=\"cif\">Cif:</label>\r\n" +
				"    <input name=\"cif\" type=\"text\" id=\"cif\"  value=\"" + concesionario.getCif()  + "\" />\r\n" + 
				"  </p>\r\n" +
				"  <p>\r\n" + 
				"    <label for=\"nombre\">Nombre: </label>\r\n" +  
				"    <input name=\"nombre\" type=\"text\" id=\"nombre\" value=\"" + concesionario.getNombre()  + "\" />\r\n" +
				"  </p>\r\n" +
				"  <p>\r\n" +
				"    <label for=\"localidad\">Localidad: </label>\r\n" + 
				"    <input name=\"localidad\" type=\"text\" id=\"localidad\" value=\"" + concesionario.getLocalidad()  + "\" />\r\n" +
				"  </p>\r\n" + 
				"  <p>\r\n" + 
				"    <input type=\"submit\" name=\"guardar\" value=\"Guardar\" />\r\n" + 
				"    <input type=\"submit\" name=\"eliminar\" value=\"Eliminar\" />\r\n" + 
				"  </p>\r\n" + 
				"</form>" +
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
