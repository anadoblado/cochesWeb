package servlets;

import Utils.RequestUtils;
import Utils.SuperTipoServlet;
import model.Coche;
import model.Fabricante;
import model.controladores.CocheControlador;
import model.controladores.FabricanteControlador;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class fichaCoche
 */
@WebServlet("/fichaCoche")
public class fichaCoche extends SuperTipoServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see SuperTipoServlet#SuperTipoServlet()
     */
    public fichaCoche() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Obtengo una HashMap con todos los parámetros del request, sea este del tipo que sea;
		HashMap<String, Object> hashMap = RequestUtils.requestToHashMap(request);
		
		// Para plasmar la información de un profesor determinado utilizaremos un parámetro, que debe llegar a este Servlet obligatoriamente
		// El parámetro se llama "idCoche" y gracias a él podremos obtener la información del coche y mostrar sus datos en pantalla
		Coche coche = null;
		
		try {
			int idCoche = RequestUtils.getIntParameterFromHashMap(hashMap, "idCoche"); // Necesito obtener el id del coche que se quiere editar. En caso de un alta
			// de coche obtendríamos el valor 0 como idCoche
			if (idCoche != 0) {
				coche = (Coche) CocheControlador.getControlador().find(idCoche);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Inicializo unos valores correctos para la presentación del coche
		if (coche == null) {
			coche = new Coche();
		}
		if (coche.getBastidor() == null) coche.setBastidor("");
		if (coche.getColor() == null) coche.setColor("");
		if (coche.getFabricante() == null) coche.setFabricante((Fabricante) FabricanteControlador.getControlador().find(1));
		if (coche.getModelo() == null) coche.setModelo("");
		
		// Ahora debo determinar cuál es la acción que este página debería llevar a cabo, en función de los parámetros de entrada al Servlet.
		// Las acciones que se pueden querer llevar a cabo son tres:
		//    - "eliminar". Sé que está es la acción porque recibiré un un parámetro con el nombre "eliminar" en el request
		//    - "guardar". Sé que está es la acción elegida porque recibiré un parámetro en el request con el nombre "guardar"
		//    - Sin acción. En este caso simplemente se quiere editar la ficha
		
		// Variable con mensaje de información al usuario sobre alguna acción requerida
		String mensajeAlUsuario = "";

		// Primera acción posible: eliminar
		if (RequestUtils.getStringParameterFromHashMap(hashMap, "eliminar") != null) {
			// Intento eliminar el registro, si el borrado es correcto redirijo la petición hacia el listado de profesores
			try {
				CocheControlador.getControlador().remove(coche);
				response.sendRedirect(request.getContextPath() + "ListadoCoches"); // Redirección del response hacia el listado
			}
			catch (Exception ex) {
				mensajeAlUsuario = "Imposible eliminar. Es posible que existan restricciones.";
			}
		}
		
		// Segunda acción posible: guardar
		if (RequestUtils.getStringParameterFromHashMap(hashMap, "guardar") != null) {
			// Obtengo todos los datos del coche y los almaceno en BBDD
			try {
				coche.setBastidor(RequestUtils.getStringParameterFromHashMap(hashMap, "bastidor"));
				coche.setColor(RequestUtils.getStringParameterFromHashMap(hashMap, "color"));
				coche.setModelo(RequestUtils.getStringParameterFromHashMap(hashMap, "modelo"));
				coche.setFabricante((Fabricante) FabricanteControlador.getControlador().find(RequestUtils.getIntParameterFromHashMap(hashMap, "idFabricante")));
				byte[] posibleImagen = RequestUtils.getByteArrayFromHashMap(hashMap, "ficheroImagen");
				if (posibleImagen != null && posibleImagen.length > 0) {
					coche.setImagen(posibleImagen);
				}
				
				// Finalmente guardo el objeto de tipo coche
				CocheControlador.getControlador().save(coche);
				mensajeAlUsuario = "Guardado correctamente";
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		// Ahora muestro la pantalla de respuesta al usuario
		response.getWriter().append(this.getCabeceraHTML("Ficha de coche"));
		response.getWriter().append("\r\n" + 
				"<body " +((mensajeAlUsuario != null && mensajeAlUsuario != "")? "onLoad=\"alert('" + mensajeAlUsuario + "');\"" : "")  + " >\r\n" + 
				"<h1>Ficha de coche</h1>\r\n" + 
				"<a href=\"ListadoCoches\">Ir al listado de coches</a>" +
				"<form id=\"form1\" name=\"form1\" method=\"post\" action=\"fichaCoche\" enctype=\"multipart/form-data\" onsubmit=\"return validateForm()\">\r\n" + 
				" <img src=\"Utils/DownloadImagenCoche?idCoche=" + coche.getId() + "\" width='100px' height='100px'/>" +
				" <input type=\"hidden\" name=\"idCoche\" value=\"" + coche.getId() + "\"\\>" +
				"  <p>\r\n" +
				"    <label for=\"ficheroImagen\">Imagen:</label>\r\n" + 
				"    <input name=\"ficheroImagen\" type=\"file\" id=\"ficheroImagen\" />\r\n" + 
				"  </p>\r\n" + 
				"  <p>\r\n" + 
				"    <label for=\"bastidor\">Bastidor:</label>\r\n" +
				"    <input name=\"bastidor\" type=\"text\" id=\"bastidor\"  value=\"" + coche.getBastidor()  + "\" />\r\n" + 
				"  </p>\r\n" +
				"  <p>\r\n" + 
				"    <label for=\"color\">Color: </label>\r\n" +  
				"    <input name=\"color\" type=\"text\" id=\"color\" value=\"" + coche.getColor()  + "\" />\r\n" +
				"  </p>\r\n" +
				"  <p>\r\n" + 
				"    <label for=\"modelo\">Modelo: </label>\r\n" +  
				"    <input name=\"modelo\" type=\"text\" id=\"modelo\" value=\"" + coche.getModelo()  + "\" />\r\n" +
				"  </p>\r\n" +
				"  <p>\r\n" + 
				"    <label for=\"idFabricante\">Marca:</label>\r\n" + 
				"    <select name=\"idFabricante\" id=\"idFabricante\">\r\n");
				// Inserto los valores de la tipología del sexo del profesor y, si el registro tiene un valor concreto, lo establezco
				List<Fabricante> fabricantes = FabricanteControlador.getControlador().findAll(); 
				for (Fabricante fabricante : fabricantes) {
					response.getWriter().append("<option value=\"" + fabricante.getId() + "\" " + ((fabricante.getId() == coche.getFabricante().getId())? "selected=\"selected\"" : "") + ">" + fabricante.getNombre() + "</option>");
				}
				response.getWriter().append("    </select>\r\n" + 
				"  <p>\r\n" + 
				"    <input type=\"submit\" name=\"guardar\" value=\"Guardar\" />\r\n" + 
				"    <input type=\"submit\" name=\"eliminar\" value=\"Eliminar\" />\r\n" + 
				"  </p>\r\n" + 
				"</form>");
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
