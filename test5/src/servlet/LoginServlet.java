package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionManager;
import dao.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("nickName");
		String pass = request.getParameter("pass");
		String errMsg = null;

		System.out.println("--------------");
		System.out.println("LoginSevlet接続確認");
		System.out.println("name:" + name);
		System.out.println("pass:" + pass);
		System.out.println("--------------");

		ConnectionManager connectionManager = null;
		connectionManager = new ConnectionManager();
		Connection connection = connectionManager.getConnection();
		UserDAO  userDAO= new UserDAO(connection);
		try {
			int result = userDAO.login(name, pass);

			if(result == 0) {
				errMsg = "「ニックネーム」または「パスワード」が異なります";
				request.setAttribute("errMsg", errMsg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
				dispatcher.forward(request, response);
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/qa.jsp");
				dispatcher.forward(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
