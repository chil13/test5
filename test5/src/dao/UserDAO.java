package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	private Connection connection;

	public UserDAO(Connection connection) {
		this.connection = connection;
	}

	public int login(String name, String pass) throws SQLException {
		System.out.println("--------------");
		System.out.println("1002AccountDAO接続確認");
		System.out.println("name:" + name);
		System.out.println("pass:" + pass);
		System.out.println("--------------");

		PreparedStatement preparedStatement = null;
		try {

			String sql = "SELECT id FROM test WHERE name=? and pass=?;";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, pass);
			preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement.executeQuery();
			int result= resultSet.getInt(1);

			System.out.println("--------------");
			System.out.println("result:" + result);
			System.out.println("--------------");

			return result;
		} catch (Exception e) {
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
	}
}
