package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.QuestionBean;
import bean.RetryBean;

public class QuestionDAO {
	private Connection connection;

	public QuestionDAO(Connection connection) {
		this.connection = connection;
	}

	public  ArrayList<QuestionBean> questionById(ArrayList<RetryBean> retryList) throws SQLException{

		ArrayList<QuestionBean> list = new ArrayList<QuestionBean>();


		PreparedStatement preparedStatement = null;
		try {
				String sql = "SELECT question,intention FROM question WHERE q_id = ?";
				preparedStatement = connection.prepareStatement(sql);
				System.out.println("ここ徹？");

				preparedStatement.setArray(1, retryList);


				System.out.println("kokotooru ");

				ResultSet resultSet = preparedStatement.executeQuery();



				while (resultSet.next()) {
					QuestionBean bean = new QuestionBean();
					bean.setQuestion(resultSet.getString("question"));
					bean.setIntention(resultSet.getString("intention"));
					list.add(bean);
				}
			}catch (Exception e) {
				throw new SQLException("accountテーブルのSELECTに失敗しました", e);
			} finally {
				try {
					if (preparedStatement != null) {
						preparedStatement.close();
						System.out.println("ステートメントの解放に成功しました");
					}
				} catch (Exception e) {
					throw new SQLException("ステートメントの解放に失敗しました", e);
				}

			}

	return list;
}

}
