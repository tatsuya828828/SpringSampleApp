package com.example.demo.login.domain.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository
public class UserDaoJdbcImpl implements UserDao {
	// JdbcTemplateはSpringが用意しているため、すでにBean定義がされている。
	// そのため、@Autowiredするだけで使えるようになる、このクラスのメソッドを使って、SQLを実行していく
	@Autowired
	JdbcTemplate jdbc;

	// Userテーブルの件数を取得
	@Override
	public int count() throws DataAccessException {
		// Objectの取得
		// カウントの結果や、カラムを1つだけ取得してくるような場合には、queryForObjectメソッドを使う。
		// 第1引数にSQL文、第2引数に戻り値のオブジェクトのclassを指定する
		// 全権取得してカウント
		int count = jdbc.queryForObject("SELECT COUNT(*) FROM m_user", Integer.class);
		return count;
	}

	// Userテーブルにデータを1件insert
	@Override
	public int insertOne(User user) throws DataAccessException {
		// JdbcTemplateを使って登録(insert)するには、updateメソッドを使う
		// updataメソッドは更新、削除にも使う。使い方は、第1引数にSQL文を入れる
		// 第2引数以降には、PreparedStatementを使う
		// PreparedStatementには、SQL文の?の部分に入れる変数を引数にセットしていく
		// 引数にセットした順番にSQL文に代入されていく、なお、updateメソッドの戻り値には、登録したレコード数が返ってくる
		// 1件登録
		int rowNumber = jdbc.update(
				"INSERT INTO m_user(user_id, "+"password, "+"user_name, "
				+"birthday, "+"age, "+"marriage, "+"role)"+"VALUES(?,?,?,?,?,?,?)",
				user.getUserId(), user.getPassword(), user.getUserName(), user.getBirthday(),
				user.getAge(), user.isMarriage(), user.getRole());
		return rowNumber;
	}

	// Userテーブルのデータを1件取得
	// 1件のレコードを取得するには、queryForMapメソッドを使う
	// 戻り値はMap<String, Object>形である
	// 第1引数にSQL文、第2引数以降にPreparedStatementを指定する
	// 戻り値のMapのgetメソッドにカラム名を指定することで、値を取得することができる
	// 複数件取得する場合と、使い方はほとんど一緒
	@Override
	public User selectOne(String userId) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM m_user"+" WHERE user_id = ?", userId);
		// 結果返却用の変数
		User user = new User();
		// 取得したデータを結果返却用の変数にセットしていく
		user.setUserId((String) map.get("user_id"));
		user.setPassword((String) map.get("password"));
		user.setUserName((String) map.get("user_name"));
		user.setBirthday((Date) map.get("birthday"));
		user.setAge((Integer) map.get("age"));
		user.setMarriage((Boolean) map.get("marriage"));
		user.setRole((String) map.get("role"));

		return user;
	}

	// Userテーブルの全データを取得
	@Override
	public List<User> selectMany() throws DataAccessException {
		// 複数件のselect
		// 複数件のselectをする場合は、queryForListメソッドを使う
		// 戻り値の方にはList<Map<String, Object>>を指定する、Listが行を表していて、Mapが列を表している
		// Mapのgetメソッドでテーブルのカラム名を指定することで値を取得できる
		// 引数を追加すれば、PreparedStatementを使うこともできる
		// M_USERテーブルのデータを全権取得
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM m_user");
		// 結果返却用の変数
		List<User> userList = new ArrayList<>();
		// 取得したデータを結果返却用のListに格納していく
		for(Map<String, Object> map: getList) {
			// Userインスタンスの生成
			User user = new User();
			// Userインスタンスに取得したデータをセットする
			user.setUserId((String)map.get("user_id"));
			user.setPassword((String)map.get("password"));
			user.setUserName((String)map.get("user_name"));
			user.setBirthday((Date)map.get("birthday"));
			user.setAge((Integer)map.get("age"));
			user.setMarriage((Boolean)map.get("marriage"));
			user.setRole((String)map.get("role"));

			// 結果返却用のListに追加
			userList.add(user);
		}
		return userList;
	}

	// Userテーブルを1件更新
	@Override
	public int updateOne(User user) throws DataAccessException {
		// jdbcTemplateのupdateメソッドを使用する
		// SQL文と、PreparedStatementの値を引数に渡していく
		int rowNumber = jdbc.update(
				"UPDATE M_USER"+" SET"+" password = ?, "+"user_name = ?, "+"birthday = ?, "+"age = ?, "+"marriage = ?, "+"WHERE user_id = ?",
				user.getPassword(), user.getUserName(), user.getBirthday(), user.getAge(), user.isMarriage(), user.getUserId());
		return rowNumber;
	}

	// Userテーブルを1件削除
	@Override
	public int deleteOne(String userId) throws DataAccessException {
		return 0;
	}

	// Userテーブルの前データをCSVに出力する
	@Override
	public void userCsvOut() throws DataAccessException {

	}
}
