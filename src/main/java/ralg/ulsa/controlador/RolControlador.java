package ralg.ulsa.controlador;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ralg.ulsa.dao.hibernate.RolDAO;
import ralg.ulsa.modelo.Rol;

/**
 * Servlet implementation class RolControlador
 */
public class RolControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	private RolDAO rolDao;

	public void init() {
		rolDao = new RolDAO();
	}

	public RolControlador() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		registrar(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		registrar(request, response);
	}

	private void registrar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String nombre = request.getParameter("nombre");
			String descripcion = request.getParameter("descripcion");
			if (isEmptyOrNull(nombre) || isEmptyOrNull(descripcion)) {
				request.setAttribute("error", "Datos de ingreso erróneos o incompletos");
				response.sendRedirect(request.getContextPath() + "/usuario/registrarRol.jsp");

			} else {
				Rol rol = new Rol();
				rol.setNombre(nombre);
				rol.setDescripcion(descripcion);
				rolDao.createRol(rol);
				HttpSession session = request.getSession();
				synchronized (session) {
					request.setAttribute("listaRoles", rolDao.getAllRoles());
					RequestDispatcher dispatcher = request.getRequestDispatcher("/usuario/rol.jsp");
					dispatcher.forward(request, response);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			response.sendRedirect(request.getContextPath() + "/usuario/registrarRol.jsp");
		}
	}

	private Boolean isEmptyOrNull(String param) {
		return param == null || param.isEmpty();
	}

}
