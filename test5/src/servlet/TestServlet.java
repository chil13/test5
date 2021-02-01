package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.QuestionBean;
import dao.ConnectionManager;
import dao.QuestionDAO;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/Test")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		Random random = new Random();
		int randomValue = random.nextInt(100);
		System.out.println("randomValue:" + randomValue);


		ArrayList<QuestionBean> testList = new ArrayList<QuestionBean>();
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = connectionManager.getConnection();
		QuestionDAO questionDAO = new QuestionDAO(connection);
		try {
			testList = questionDAO.test(randomValue);
			String q = testList.get(0).getQuestion();
			String intention = testList.get(0).getIntention();
			System.out.println("q:"+ q);
			System.out.println("intention:" + intention);

			request.setAttribute("question", q);
			request.setAttribute("intention", intention);

		} catch (SQLException e) {
			e.printStackTrace();
		}



		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/test.jsp");
		dispatcher.forward(request, response);
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
