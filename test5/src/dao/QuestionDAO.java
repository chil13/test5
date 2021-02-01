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

	public  ArrayList<QuestionBean> test(int q_id) throws SQLException{
		ArrayList<QuestionBean> list = new ArrayList<QuestionBean>();

		PreparedStatement preparedStatement = null;

		try {
			// ステートメントの作成
			String sql = "SELECT question,intention FROM question WHERE q_id = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, q_id);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				QuestionBean bean = new QuestionBean();
				bean.setQuestion(resultSet.getString("question"));
				bean.setIntention(resultSet.getString("intention"));
				list.add(bean);
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("テーブルの取得失敗");
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
					System.out.println("ステートメントの解放に成功しました");
				}
			} catch (Exception e) {
				//throw new SQLException("ステートメントの解放に失敗しました", e);
			}
		}
		return list;
	}




	public  ArrayList<QuestionBean> questionById(ArrayList<RetryBean> retryList) throws SQLException{

		ArrayList<QuestionBean> list = new ArrayList<QuestionBean>();


		PreparedStatement preparedStatement = null;
		try {

				String listStr = "(";
				for(int i = 1 ; i <= retryList.size() ; i++ ){
					if(i == retryList.size()) {
						listStr += "?";
					}else {
						listStr += "?,";
					}
				}
				listStr += ")";


				String sql = "SELECT question,intention FROM question WHERE q_id IN " + listStr;
				System.out.println(sql);

				preparedStatement = connection.prepareStatement(sql);

				for(int i = 1 ; i <= retryList.size() ; i++ ) {
					preparedStatement.setInt(i, retryList.get(i-1).getQ_id());
				}



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
