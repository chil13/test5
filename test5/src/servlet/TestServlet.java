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

		System.out.println("つながる？");
		String reQuestion = request.getParameter("question");
		System.out.println("reQuestion:"+reQuestion);


		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = connectionManager.getConnection();
		QuestionDAO questionDAO = new QuestionDAO(connection);

		int q_value = 0;

		if(reQuestion == null) {
			System.out.println("わわあわ");
			Random random = new Random();
			q_value = random.nextInt(100);
			System.out.println("randomValue:" + q_value);
		}else {
			ArrayList<QuestionBean> list = new ArrayList<QuestionBean>();
			try {
				list = questionDAO.reQuestion(reQuestion);
				q_value =list.get(0).getQ_id();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		ArrayList<QuestionBean> testList = new ArrayList<QuestionBean>();

		try {
			testList = questionDAO.test(q_value);
			String q = testList.get(0).getQuestion();
			String intention = testList.get(0).getIntention();
			System.out.println("q:"+ q);
			System.out.println("intention:" + intention);

			request.setAttribute("q_id", q_value);
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
