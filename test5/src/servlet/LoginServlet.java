package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MmAccountBean;
import dao.ConnectionManager;
import dao.MmAccountDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet2")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("nickName");
		String pass = request.getParameter("pass");

		System.out.println("name:"+name);
		System.out.println("pass:"+pass);

		HttpSession session = request.getSession(false);

		// 姓・ID格納用のAccountBeanリストを作成。
		List<MmAccountBean> loginUser = null;
		int loginU_id = 0;

		// DB接続スタート
		ConnectionManager connectionManager = null;

		try {

			connectionManager = new ConnectionManager();
			Connection connection = connectionManager.getConnection();


			// AccountDAOに接続し、入力内容をログインチェックにかける。
			MmAccountDAO mmAccountDAO = new MmAccountDAO(connection);
			loginUser = mmAccountDAO.login(name, pass);
			loginU_id = loginUser.get(0).getU_id();

			MmAccountBean mmAccountBean = new MmAccountBean();
			int u_id = mmAccountBean.getU_id();
			System.out.println("loginU_id:"+loginU_id);



			if(loginU_id == 1) {
				session.setAttribute("loginU_id", loginU_id);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/menu.jsp");
				dispatcher.forward(request, response);

			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
				dispatcher.forward(request, response);
			}

		} catch (Exception e) {

			try {
				//connectionManager.rollback();
				System.out.println("aaaaa");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} finally {
			try {
				connectionManager.closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("AdminServlet");

				return;
			}
		}

		// 取得したユーザIDと姓（表示名）をセッションに格納する。

		/*		// カレンダーサーブレットへリダイレクト。
				response.sendRedirect("admin/CalendarServlet");
		*/		return;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}


