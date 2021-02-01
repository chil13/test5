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
import javax.servlet.http.HttpSession;

import bean.RetryBean;
import dao.ConnectionManager;
import dao.MmAccountDAO;

/**
 * Servlet implementation class RetryRegisterServlet
 */
@WebServlet("/RetryRegister")
public class RetryRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--------------");
		System.out.println("RetryRegister接続確認");

		HttpSession session = request.getSession(true);

		int q_id = Integer.parseInt(request.getParameter("q_id"));
		int u_id = (int) session.getAttribute("loginU_id");

		RetryBean retryBean = new RetryBean();
		retryBean.setU_id(u_id);
		retryBean.setQ_id(q_id);

		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = connectionManager.getConnection();
		MmAccountDAO mmAccountDAO = new MmAccountDAO(connection);

		try {
			int result = mmAccountDAO.insert(retryBean);
			System.out.println("リスト登録："+ result);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/retryRegister.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}




	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
