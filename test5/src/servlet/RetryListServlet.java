package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.QuestionBean;
import bean.RetryBean;
import dao.ConnectionManager;
import dao.MmAccountDAO;
import dao.QuestionDAO;

/**
 * Servlet implementation class RetryListServlet
 */
@WebServlet("/RetryList")
public class RetryListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		int u_id = (int) session.getAttribute("loginU_id");
		System.out.println("u_idStr:" + u_id);

		ArrayList<RetryBean> retryList = new ArrayList<RetryBean>();

		//Array retryList = null;

		//etryBean retryBean = null;

		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = connectionManager.getConnection();
		MmAccountDAO mmAccountDAO = new MmAccountDAO(connection);

		try {
			retryList = mmAccountDAO.retry(u_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(retryList.size());

		if (retryList.size() == 0) {
			String nonMsg = "現在登録がありません。";
			request.setAttribute("nonMsg", nonMsg);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/retry.jsp");
			dispatcher.forward(request, response);

		} else {
			QuestionDAO qDAO = new QuestionDAO(connection);
			try {
				ArrayList<QuestionBean> qList = new ArrayList<QuestionBean>();
				qList = qDAO.questionById(retryList);
				request.setAttribute("qList", qList);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/retry.jsp");
				dispatcher.forward(request, response);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
