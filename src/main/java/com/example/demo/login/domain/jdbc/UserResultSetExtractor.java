package com.example.demo.login.domain.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.example.demo.login.domain.model.User;

/* ResultSetExtractorを使うときは、ResultSetExtractor<List<T>>をimplementsする
 * <T>の部分に、任意の型を指定する、あとは、extractData()メソッドをOverrideする
 * そして、そのメソッドの中でResultSetとオブジェクトのマッピングを行い
 * なお、複数件取得する前提のため、while文でループ処理をしている
 */
public class UserResultSetExtractor implements ResultSetExtractor<List<User>> {
	@Override
	public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
		// User格納用List
		List<User> userList = new ArrayList<>();
		// 取得件数分のloop
		while(rs.next()) {
			// Listに追加するインスタンスを生成する
			User user = new User();
			// 取得したレコードUserインスタンスにセット
			user.setUserId(rs.getString("user_id"));
			user.setPassword(rs.getNString("password"));
			user.setUserName(rs.getNString("user_name"));
			user.setBirthday(rs.getDate("birthday"));
			user.setAge(rs.getInt("age"));
			user.setMarriage(rs.getBoolean("marriage"));
			user.setRole(rs.getNString("role"));
			// ListにUserを追加
			userList.add(user);
		}
		// 1件もなかった場合は例外を投げる
		if(userList.size() == 0) {
			throw new EmptyResultDataAccessException(1);
		}
		return userList;
	}
}
