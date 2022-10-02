package ralg.ulsa.controlador;

import java.io.IOException;
import java.io.PrintWriter;

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
			if (action.contains("eliminar")) {
				String[] partes = action.split("/");
				Integer id = Integer.parseInt(partes[partes.length - 1]);
				this.eliminar(request, response, id);
			} else if (action.contains("editar")) {
				String[] partes = action.split("/");
				Integer id = Integer.parseInt(partes[partes.length - 1]);
				this.editar(request, response, id);
			} else if (action.contains("registrar")) {
				this.registrar(request, response);
			} else if (action.contains("actualizar")) {
				this.actualizar(request, response);
			} else if (action.contains("listarRoles")) {
				this.listarRoles(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/");
			}
		}
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
					session.setAttribute("listaRoles", rolDao.getAllRoles());
					response.sendRedirect(request.getContextPath() + "/usuario/rol.jsp");
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			response.sendRedirect(request.getContextPath() + "/usuario/registrarRol.jsp");
		}
	}

	private void actualizar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String nombre = request.getParameter("nombre");
			String descripcion = request.getParameter("descripcion");
			if (isEmptyOrNull(nombre) || isEmptyOrNull(descripcion)) {
				request.setAttribute("error", "Datos de ingreso erróneos o incompletos");
				response.sendRedirect(request.getContextPath() + "/usuario/editarRol.jsp");

			} else {
				HttpSession session = request.getSession();
				Rol rol = (Rol) session.getAttribute("rolToEdit");
				rol.setNombre(nombre);
				rol.setDescripcion(descripcion);
				rolDao.updateRol(rol);
				synchronized (session) {
					session.setAttribute("listaRoles", rolDao.getAllRoles());
					response.sendRedirect(request.getContextPath() + "/usuario/rol.jsp");
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			response.sendRedirect(request.getContextPath() + "/usuario/editarRol.jsp");
		}
	}

	private void editar(HttpServletRequest request, HttpServletResponse response, Integer id)
			throws ServletException, IOException {
		try {
			Rol rol = rolDao.getRol(id);
			if (rol != null) {
				HttpSession session = request.getSession();
				synchronized (session) {
					session.setAttribute("rolToEdit", rol);
					response.sendRedirect(request.getContextPath() + "/usuario/editarRol.jsp");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response.sendRedirect(request.getContextPath() + "/usuario/rol.jsp");
		}
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response, Integer id)
			throws ServletException, IOException {
		try {
			rolDao.deleteRol(id);
			HttpSession session = request.getSession();
			synchronized (session) {
				session.setAttribute("listaRoles", rolDao.getAllRoles());
				response.sendRedirect(request.getContextPath() + "/usuario/rol.jsp");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			response.sendRedirect(request.getContextPath() + "/usuario/registrarRol.jsp");
		}
	}

	private void listarRoles(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			synchronized (session) {
				session.setAttribute("listaRoles", rolDao.getAllRoles());
				response.sendRedirect(request.getContextPath() + "/usuario/rol.jsp");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private Boolean isEmptyOrNull(String param) {
		return param == null || param.isEmpty();
	}

}
