package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * [機 能] データベースの接続と切断ドライバークラス<br>
 * [説 明] データベースの接続と切断を行う。<br>
 * [備 考] なし<br>
 * [環 境] OpenJDK 11 <br>
 *
 * @author [作 成] 2020/08/26 山崎和樹 [修 正]
 */
public class ConnectionManager {
	/**
	 * コネクション
	 */
	private Connection connection;

	/**
	 * 接続ドライバー名
	 */
	private static final String DRIVER_NAME = "org.postgresql.Driver";

	/**
	 * 接続URL
	 */
	private static final String URL = "jdbc:postgresql://localhost:5432/test";

	/**
	 * 接続ユーザ
	 */
	private static final String USER = "test";

	/**
	 * 接続パスワード
	 */
	private static final String PASSWORD = "test";

	public ConnectionManager() {
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("ドライバーのロードに失敗しました", e);
		}
	}

	/**
	 *
	 * [機 能] コネクション取得メソッド<br>
	 * [説 明] コネクションを取得し、返却する。<br>
	 * ※例外取得時にはRuntimeExceptionにラップし上位に送出する。<br>
	 * [備 考] なし
	 *
	 * @return コネクション
	 */
	public Connection getConnection() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				// コミットモードの設定
				//connection.setAutoCommit(false);
			} catch (SQLException e) {
				throw new RuntimeException("データベースの接続に失敗しました", e);
			}
		}
		return connection;
	}

	/**
	 *
	 * [機 能] <br>
	 * [説 明] コネクションを切断する。<br>
	 * ※例外取得時にはRuntimeExceptionにラップし上位に送出する。<br>
	 * [備 考] なし
	 */
	public void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("データベースの切断に失敗しました", e);
		}
	}

	/**
	 *
	 * [機 能] コミットメソッド<br>
	 * [説 明] トランザクションをコミットする。<br>
	 * ※例外取得時にはRuntimeExceptionにラップし上位に送出する。<br>
	 * [備 考] なし
	 */
	public void commit() {
		try {
			if (connection != null) {
				connection.commit();
			}
		} catch (SQLException e) {
			throw new RuntimeException("トランザクションのコミットに失敗しました", e);
		}
	}

	/**
	 *
	 * [機 能] <br>
	 * [説 明] トランザクションをロールバックする。<br>
	 * ※例外取得時にはRuntimeExceptionにラップし上位に送出する。<br>
	 * [備 考] なし
	 */
	public void rollback() {
		try {
			if (connection != null) {
				connection.rollback();
			}
		} catch (SQLException e) {
			throw new RuntimeException("トランザクションのロールバックに失敗しました", e);
		}
	}

}
