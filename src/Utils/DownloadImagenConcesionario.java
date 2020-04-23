package Utils;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Concesionario;
import model.controladores.ConcesionarioControlador;



/**
 * Servlet implementation class PrimerServlet
 */
@WebServlet(description = "Download imagen almacenada en BBDD", urlPatterns = { "/Utils/DownloadImagenConcesionario" })
public class DownloadImagenConcesionario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadImagenConcesionario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean imagenEncontradaYDevuelta = false;
		
		// Para devolver una imagen de un profesor necesitamos conocer el id del mismo. Este Servlet se podría hacer de manera mucho
		// más general, recibiendo diferentes parámetros y obteniendo diferentes imágenes de diferentes fuentes, pero por simplificar
		// sólo descargará imágenes de un profesor.
		Concesionario concesionario = null;
		if (request.getParameter("idConcesionario") != null) { // comparar con "null" es la forma de comprobar si el parámetro está en la petición web
			String strIdConcesionario = request.getParameter("idConcesionario");
			try {
				concesionario = (Concesionario) ConcesionarioControlador.getControlador().find(Integer.parseInt(strIdConcesionario)); 

				// Una vez que tengo el concesionario localizado compruebo si tengo imagen para él almacenada en BBDD
				
				if (concesionario.getImagen() != null && concesionario.getImagen().length > 0) {
					// Establezco el tipo de respuesta
					response.setContentType("image/jpeg");
					// Establezco la longitud del fichero, para que el explorador cliente pueda conocer este dato
					response.setContentLength(concesionario.getImagen().length);
					// Establezco el nombre y la disposición del fichero
					//response.setHeader("Content-Disposition", "attachment; filename=\"imagen.jpg\"");
					// Leo el fichero pero en diferentes iteraciones, con un máximo de 4096 B por cada lectura del buffer
					InputStream inputStream = new ByteArrayInputStream(concesionario.getImagen()); // Convierto el array de bytes en un objeto InputStream
					OutputStream outStream = response.getOutputStream(); // Un stream que permite pasar la respuesta del Servlet en forma secuencial
					
					byte[] bufferDeLectura = new byte[4096];
					int bytesRead = -1; // Comienza la lectura del fichero y su paso al objeto response del Servlet 
	                while ((bytesRead = inputStream.read(bufferDeLectura)) != -1) {
	                    outStream.write(bufferDeLectura, 0, bytesRead);
	                }
	                inputStream.close();
	                outStream.close();
	                // Activo la bandera que indica que todo se ha podido realizar correctamente
	                imagenEncontradaYDevuelta = true;
				}
				
			} catch (Exception e) {
				System.out.println("Concesionario no encontrado para el id: " + strIdConcesionario);
			}
			
			// En caso de que no se haya podido devolver la imagen, se devuelve un texto indicando que no existe la imagen pedida
			if (!imagenEncontradaYDevuelta) {
				response.setContentType("text/html");
				response.getWriter().append("No se encuentra la imagen pedida");
			}
		}
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
