package ralg.ulsa.controlador;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ralg.ulsa.modelo.Persona;
import ralg.ulsa.modelo.Producto;
import ralg.ulsa.modelo.Usuario;

/**
 * Servlet implementation class UsuarioControlador
 */
public class UsuarioControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UsuarioControlador() {
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
			switch (action) {
			case "/login":
				this.perfil(request, response);
				break;
			case "/registrar":
				this.registrar(request, response);
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

	protected void perfil(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String parametroCorreo = request.getParameter("correo");
			String parametroPwd = request.getParameter("contrasenia");
			if ((parametroCorreo == null || parametroCorreo.isEmpty())
					&& (parametroPwd == null || parametroPwd.isEmpty())) {
				request.setAttribute("msg", "Datos de ingresos erróneos");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/usuario/login.jsp");
				dispatcher.forward(request, response);
			} else {
				// Buscar usuario y contraseña en la base de datos
				Usuario usuario = new Usuario();
				usuario.setCorreo(parametroCorreo);
				usuario.setPassword(parametroPwd.trim());

				HttpSession session = request.getSession();
				synchronized (session) {
					session.setAttribute("usuario", usuario);
					response.sendRedirect(request.getContextPath() + "/usuario/perfil.jsp");
				}
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/usuario/login.jsp");
		}
	}

	protected void registrar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String parametroNombre = request.getParameter("name");
		String parametroPaterno = request.getParameter("paterno");
		String parametroMaterno = request.getParameter("materno");
		Integer parametroEdad = Integer.parseInt(request.getParameter("edad"));
		String parametroTelefono = request.getParameter("telefono");
		String parametroCorreo = request.getParameter("correo");
		String parametroPwd = request.getParameter("pwd");
		String parametroEmpresa = request.getParameter("empresa");
		String parametroDireccion = request.getParameter("direccion");

		try {
			Persona persona = new Persona();
			persona.setNombre(parametroNombre);
			persona.setPaterno(parametroPaterno);
			persona.setMaterno(parametroMaterno);
			persona.setEdad(parametroEdad);
			persona.setTelefono(parametroTelefono);

			Usuario usuario = new Usuario();
			usuario.setCorreo(parametroCorreo);
			usuario.setPassword(parametroPwd);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/usuario/login.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Boolean parametroEstatus = Boolean.valueOf(request.getParameter("estatus"));
		String parametroCorreo = request.getParameter("correo");
		String parametroPwd = request.getParameter("password");
		String parametroVigencia = request.getParameter("vigencia");

		try {
			if (isEmptyOrNull(parametroCorreo) || isEmptyOrNull(parametroPwd) || isEmptyOrNull(parametroVigencia)) {
				request.setAttribute("error", "Datos de ingreso erróneos o incompletos");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/usuario/registrarUsuario.jsp");
				dispatcher.forward(request, response);

			} else {
				Usuario usuario = new Usuario();
				usuario.setCorreo(parametroCorreo);
				usuario.setPassword(parametroPwd);
				usuario.setStatus(parametroEstatus);
				usuario.setFechaVigencia(parametroVigencia);
				request.setAttribute("success", "Un nuevo usuario ha sido agregado al sistema.");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/usuario/registrarUsuario.jsp");
				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/usuario/usuario.jsp");
			dispatcher.forward(request, response);
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
