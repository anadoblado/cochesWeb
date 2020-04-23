package Utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class RequestUtils {

	/**
	 * 
	 * @param request
	 * @param nombreParametro
	 * @return
	 * @throws FormularioIncorrectoRecibidoException
	 */
	public static int getIntParameter (HttpServletRequest request, String nombreParametro) throws FormularioIncorrectoRecibidoException {
		try {
			return Integer.parseInt(getStringParameter(request, nombreParametro));
		} catch (Exception e) {
			throw new FormularioIncorrectoRecibidoException("No se ha recibido valor entero para el parámetro " + nombreParametro);
		}
	}

	/**
	 * 
	 * @param request
	 * @param nombreParametro
	 * @return
	 * @throws FormularioIncorrectoRecibidoException
	 */
	public static String getStringParameter (HttpServletRequest request, String nombreParametro)  {
		return request.getParameter(nombreParametro);
	}

	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static HashMap<String, Object> requestToHashMap (HttpServletRequest request) {
		HashMap<String, Object> hashMap = new HashMap<String, Object> ();
		try {
			// Debo diferenciar el caso de tener una request de tipo multipart a una de tipo normal
			if (ServletFileUpload.isMultipartContent(request)) { // Caso de que el request sea de tipo multipart
				FileItemFactory factory = new DiskFileItemFactory();
			    ServletFileUpload upload = new ServletFileUpload(factory);
			    Iterator<FileItem> items = upload.parseRequest(request).iterator();
			    // Una vez obtenidos todos los campos del formulario, busco uno de ellos, que no sea de tipo Fichero y cuyo nombre coincida con el nombre buscado
			    while (items.hasNext()) {
			        FileItem thisItem = (FileItem) items.next();
			        if (thisItem.isFormField()) {
			        	hashMap.put(thisItem.getFieldName(), thisItem.getString());
			        }
			        else {
			        	// En el caso de que recibamos un fichero tenemos métodos para tener el nombre del fichero, su tipo, tamaño, etc. pero aquí nos conformaremos
			        	// sólo con obtener el fichero como un array de bytes
			        	hashMap.put(thisItem.getFieldName(), thisItem.get());
			        }

			    }
			} 
			// Si son request normales se aplica lo siguiente
			else {
				Enumeration<String> enNombresDeParametros = request.getParameterNames();
				while (enNombresDeParametros.hasMoreElements()) {
					String nombreDeParametro = enNombresDeParametros.nextElement();
					hashMap.put(nombreDeParametro, request.getParameter(nombreDeParametro));
				}		
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashMap;
	}


	/**
	 * 
	 * @param request
	 * @param nombreParametro
	 * @return
	 */
	public static String getStringParameterFromHashMap (HashMap<String, Object> hashMap, String nombreParametro)  {
		return (String) hashMap.get(nombreParametro);
	}

	/**
	 * 
	 * @param request
	 * @param nombreParametro
	 * @return
	 */
	public static int getIntParameterFromHashMap (HashMap<String, Object> hashMap, String nombreParametro)  {
		try {
			return Integer.parseInt((String) hashMap.get(nombreParametro));
		} catch (Exception e) {
			return 0;
		}
	}

	
	/**
	 * 
	 * @param hashMap
	 * @param nombreParametro
	 * @return
	 */
	public static byte[] getByteArrayFromHashMap (HashMap<String, Object> hashMap, String nombreParametro)  {
		try {
			return (byte[]) hashMap.get(nombreParametro);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param hashMap
	 * @param nombreParametro
	 * @return
	 * @throws FormularioIncorrectoRecibidoException
	 */
	public static String getMandatoryStringParameterFromHashMap (HashMap<String, Object> hashMap, String nombreParametro) throws FormularioIncorrectoRecibidoException  {
		if (getStringParameterFromHashMap(hashMap, nombreParametro) != null) {
			return getStringParameterFromHashMap(hashMap, nombreParametro);
		}
		else {
			throw new FormularioIncorrectoRecibidoException("No se ha recibido valor para el parámetro " + nombreParametro);
		}
	}
	
	/**
	 * 
	 * @param hashMap
	 * @param nombreParametro
	 * @return
	 * @throws FormularioIncorrectoRecibidoException
	 */
	public static int getMandatoryIntParameterFromHashMap (HashMap<String, Object> hashMap, String nombreParametro) throws FormularioIncorrectoRecibidoException  {
		try {
			return Integer.parseInt((String) getMandatoryStringParameterFromHashMap(hashMap, nombreParametro));
		} catch (Exception e) {
			throw new FormularioIncorrectoRecibidoException("No se ha recibido valor entero para el parámetro " + nombreParametro);
		}
	}

}
