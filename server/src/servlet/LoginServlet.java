package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bean.UserDAO;


/**
 * 这个是一个Servlet类
 *
 * @WebServlet("/loginServlet")相当于在web.xml里面配置了servlet,这是一个注解
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * The doGet method of the servlet. <br>
	 *这个方法应该是作者用来测试这个servlet是否可行而编写的业务逻辑
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		//这两个是获得android端传过来的数据
		String phoneNumber = request.getParameter(User.PHONENUMBER);
		String passWord = request.getParameter(User.PASSWORD);

		
		//这个是初始化这个UserDao类，会先调用无参构造器
		UserDAO userDAO = new UserDAO();

		//OK和Wrong会在安卓端被接收到用来判断服务端是否通过了验证
		if (userDAO.isLoginOK(phoneNumber, passWord)) {
			out.println("OK");
		}else {
			out.println("Wrong");
		}
		
		out.flush();
		out.close();
	}

}
