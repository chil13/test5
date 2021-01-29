package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.MmAccountBean;
import bean.RetryBean;

public class MmAccountDAO {
	private Connection connection;

	public MmAccountDAO(Connection connection) {
		this.connection = connection;
	}

	public  ArrayList<MmAccountBean> login(String name,String pass) {

		System.out.println("MmAccountDAO接続確認----------");
		System.out.println("name:"+name);
		System.out.println("name:"+pass);

		ArrayList<MmAccountBean> list = new ArrayList<MmAccountBean>();

		PreparedStatement preparedStatement = null;
		try {

			// ステートメントの作成
			String sql = "SELECT u_id FROM test WHERE name= ? AND pass= ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, pass);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				MmAccountBean bean = new MmAccountBean();
				bean.setU_id(resultSet.getInt("u_id"));
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

/*
	public  List<RetryBean> retry(int u_id) {

		List<RetryBean> list =null;

		PreparedStatement preparedStatement = null;
		try {

			// ステートメントの作成
			String sql = "SELECT q_id FROM retry WHERE u_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, u_id);

			ResultSet resultSet = preparedStatement.executeQuery();

			RetryBean bean = new RetryBean();
			while (resultSet.next()) {
				bean.setQ_id(resultSet.getInt("q_id"));
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
*/
	public  ArrayList<RetryBean> retry(int id) throws SQLException{

		ArrayList<RetryBean> list = new ArrayList<RetryBean>();

		PreparedStatement preparedStatement = null;
		try {
			// ステートメントの作成
			String sql = "SELECT q_id FROM retry WHERE u_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				RetryBean bean = new RetryBean();
				bean.setQ_id(resultSet.getInt("q_id"));
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
