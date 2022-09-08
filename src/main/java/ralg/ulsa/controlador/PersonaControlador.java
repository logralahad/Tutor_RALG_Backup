package ralg.ulsa.controlador;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ralg.ulsa.dao.hibernate.PersonaDAO;
import ralg.ulsa.modelo.Persona;

/**
 * Servlet implementation class PersonaControlador
 */
public class PersonaControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PersonaDAO personaDAO;

	public void init() {
		personaDAO = new PersonaDAO();
	}

	public PersonaControlador() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		procesar(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		procesar(request, response);
	}

	protected void procesar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			String action = request.getPathInfo();
			switch (action) {
			case "/registrar":
				this.registrar(request, response);
				break;
			default:
				response.sendRedirect(request.getContextPath() + "/");
				break;
			}
		}
	}

	private void registrar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String parametroNombre = request.getParameter("nombre");
			String parametroPaterno = request.getParameter("paterno");
			String parametroMaterno = request.getParameter("materno");
			String parametroTelefono = request.getParameter("telefono");
			String parametroRFC = request.getParameter("rfc");
			String parametroNacimiento = request.getParameter("nacimiento");
			String parametroDomicilio = request.getParameter("domicilio");
			String parametroEdad = request.getParameter("edad");

			if (isEmptyOrNull(parametroNombre) || isEmptyOrNull(parametroPaterno) || isEmptyOrNull(parametroMaterno)
					|| isEmptyOrNull(parametroTelefono) || isEmptyOrNull(parametroRFC) || isEmptyOrNull(parametroEdad)
					|| isEmptyOrNull(parametroNacimiento) || isEmptyOrNull(parametroDomicilio)) {
				request.setAttribute("error", "Datos de ingreso erróneos o incompletos");
				response.sendRedirect(request.getContextPath() + "/usuario/registrarPersona.jsp");

			} else {
				Persona persona = new Persona();
				persona.setNombre(parametroNombre);
				persona.setPaterno(parametroPaterno);
				persona.setMaterno(parametroMaterno);
				persona.setEdad(Integer.parseInt(parametroEdad));
				persona.setTelefono(parametroTelefono);
				persona.setRfc(parametroRFC);
				persona.setFechaNacimiento(parametroNacimiento);
				persona.setDomicilio(parametroDomicilio);

				personaDAO.createPersona(persona);
				HttpSession session = request.getSession();
				synchronized (session) {
					session.setAttribute("listaPersonas", personaDAO.getAllPersonas());
					response.sendRedirect(request.getContextPath() + "/usuario/persona.jsp");
				}
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/usuario/registrarPersona.jsp");
		}
	}

	private Boolean isEmptyOrNull(String param) {
		return param == null || param.isEmpty();
	}

}
