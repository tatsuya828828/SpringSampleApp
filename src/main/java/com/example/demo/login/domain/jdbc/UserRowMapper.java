package com.example.demo.login.domain.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.login.domain.model.User;

// RowMapper
// RowMapperを使用するには、RowMapper<?>インターフェースを継承する
// ?の部分には、マッピングに使うJavaオブジェクトのクラスを使用する
// RowMapperを継承して、mapRowメソッドをOverrideする、引数のResultSetにはSelect結果が入っている
// そのため、ResultSetの値をUserクラスにセットする、最後にUserクラスのインスタンスをreturnすれば、RowMapperが完成する
// このように、RowMapperでは、Select結果とUserクラスをあらかじめマッピングしておくことができる
public class UserRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		// 戻り値用のUserインスタンスを生成
		User user = new User();
		// ResultSetの取得結果をUserインスタンスにセット
		user.setUserId(rs.getString("user_id"));
		user.setPassword(rs.getString("password"));
		user.setUserName(rs.getString("user_name"));
		user.setBirthday(rs.getDate("birthday"));
		user.setAge(rs.getInt("age"));
		user.setMarriage(rs.getBoolean("marriage"));
		user.setRole(rs.getNString("role"));

		return user;
	}
}
