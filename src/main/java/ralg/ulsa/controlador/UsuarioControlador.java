package ralg.ulsa.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ralg.ulsa.dao.hibernate.UsuarioDAO;
import ralg.ulsa.modelo.Producto;
import ralg.ulsa.modelo.Usuario;

/**
 * Servlet implementation class UsuarioControlador
 */
public class UsuarioControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioDAO usuarioDAO;

	public void init() {
		usuarioDAO = new UsuarioDAO();
	}

	public UsuarioControlador() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		procesar(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		procesar(request, response);
	}

	protected void procesar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			String action = request.getPathInfo();
			switch (action) {
			case "/login":
				this.login(request, response);
				break;
			case "/registrar":
				this.registrar(request, response);
				break;
			case "/listarUsuarios":
				this.listarUsuarios(request, response);
				break;
			case "/registrarUsuario":
				this.registrarUsuario(request, response);
				break;
			case "/registrarProducto":
				this.registrarProducto(request, response);
				break;
			default:
				response.sendRedirect(request.getContextPath() + "/");
				break;
			}
		}
	}

	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String parametroCorreo = request.getParameter("correo");
			String parametroPwd = request.getParameter("contrasenia");
			if ((parametroCorreo == null || parametroCorreo.isEmpty())
					&& (parametroPwd == null || parametroPwd.isEmpty())) {
				request.setAttribute("error", "Datos de ingreso erróneos o incompletos");
				response.sendRedirect(request.getContextPath() + "/usuario/login.jsp");
			} else {
				Usuario usuario = usuarioDAO.login(parametroCorreo, parametroPwd);
				if (usuario != null) {
					HttpSession session = request.getSession();
					synchronized (session) {
						session.setAttribute("usuario", usuario);
						response.sendRedirect(request.getContextPath() + "/usuario/perfil.jsp");
					}
				} else {
					response.sendRedirect(request.getContextPath() + "/usuario/login.jsp");
				}
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/usuario/login.jsp");
		}
	}

	protected void registrar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String parametroCorreo = request.getParameter("correo");
			String parametroPwd = request.getParameter("password");

			if (isEmptyOrNull(parametroCorreo) || isEmptyOrNull(parametroPwd)) {
				request.setAttribute("error", "Datos de ingreso erróneos o incompletos");
				response.sendRedirect(request.getContextPath() + "/usuario/registrar.jsp");

			} else {
				Usuario usuario = new Usuario();
				usuario.setCorreo(parametroCorreo);
				usuario.setPassword(parametroPwd);
				usuario.setStatus(true);
				Calendar today = Calendar.getInstance();
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				String todayFormatted = format1.format(today.getTime());
				usuario.setFechaRegistro(todayFormatted);

				today.add(Calendar.YEAR, 1);
				String vigentFormatted = format1.format(today.getTime());
				usuario.setFechaVigencia(vigentFormatted);
				usuarioDAO.createUsuario(usuario);
				response.sendRedirect(request.getContextPath() + "/usuario/login.jsp");
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/usuario/registrar.jsp");
			e.printStackTrace();
		}
	}

	protected void listarUsuarios(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			synchronized (session) {
				session.setAttribute("listaUsuarios", usuarioDAO.getAllUsuarios());
				response.sendRedirect(request.getContextPath() + "/usuario/usuario.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Boolean parametroEstatus = Boolean.valueOf(request.getParameter("estatus"));
			String parametroCorreo = request.getParameter("correo");
			String parametroPwd = request.getParameter("password");
			String parametroVigencia = request.getParameter("vigencia");

			if (isEmptyOrNull(parametroCorreo) || isEmptyOrNull(parametroPwd) || isEmptyOrNull(parametroVigencia)) {
				request.setAttribute("error", "Datos de ingreso erróneos o incompletos");
				response.sendRedirect(request.getContextPath() + "/usuario/registrarUsuario.jsp");

			} else {
				Usuario usuario = new Usuario();
				usuario.setCorreo(parametroCorreo);
				usuario.setPassword(parametroPwd);
				usuario.setStatus(parametroEstatus);
				usuario.setFechaVigencia(parametroVigencia);

				Calendar today = Calendar.getInstance();
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				String todayFormatted = format1.format(today.getTime());
				usuario.setFechaRegistro(todayFormatted);

				usuarioDAO.createUsuario(usuario);
				response.sendRedirect(request.getContextPath() + "/usuario/registrarUsuario.jsp");
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/usuario/usuario.jsp");
			e.printStackTrace();
		}

	}

	protected void registrarProducto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");
		String precio = request.getParameter("precio");
		String imagen = request.getParameter("imagen");
		String existencias = request.getParameter("existencias");
		String codigo = request.getParameter("codigo");
		String porcentaje = request.getParameter("porcentaje");

		try {
			if (isEmptyOrNull(nombre) || isEmptyOrNull(descripcion) || isEmptyOrNull(precio) || isEmptyOrNull(imagen)
					|| isEmptyOrNull(existencias) || isEmptyOrNull(codigo) || isEmptyOrNull(porcentaje)) {
				request.setAttribute("error", "Datos de ingreso erróneos o incompletos");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/usuario/registrarProducto.jsp");
				dispatcher.forward(request, response);

			} else {
				Producto producto = new Producto();
				producto.setId(1);
				producto.setNombre(nombre);
				producto.setDescripcion(descripcion);
				producto.setPrecio(Float.parseFloat(precio));
				producto.setImagen(imagen);
				producto.setNumExistencias(Integer.parseInt(existencias));
				producto.setCodigoBarras(codigo);
				producto.setIvaPorcentaje(Integer.parseInt(porcentaje));

				request.setAttribute("success", "Un nuevo producto ha sido agregado al sistema.");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/usuario/registrarProducto.jsp");
				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/usuario/producto.jsp");
			dispatcher.forward(request, response);
		}
	}

	private Boolean isEmptyOrNull(String param) {
		return param == null || param.isEmpty();
	}

}
