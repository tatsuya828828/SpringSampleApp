package com.example.demo.login.domain.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;

@Repository("UserDaoJdbcImpl3")
public class UserDaoJdbcImpl3 extends UserDaoJdbcImpl {
	@Autowired
	private JdbcTemplate jdbc;
	// ユーザー1件取得
	@Override
	public User selectOne(String userId) {
		// 1件取得用SQL
		String sql = "SELECT * FROM m_user WHERE user_id=?";
		// BeanPropertyRowMapperでは、データベースから取得してきたカラム名と同一のフィールド名がクラスにあれば、自動でマッピングをしてくれる
		// つまりRowMapperのようにどのカラムとどのフィールドを一致させるか、いちいち用意する必要がない
		// ただし自動で、マッピングするためには決められたカラム名とアンダースコアでくぎる必要がある
		// カラム名は単語をアンダースコアで区切る(スネークケース)
		// フィールド名は、2つ目の単語から大文字にする(キャメルケース)
		// もしも、カラム名がアンダースコアで区切られていなければ、AS句をしようして別名でもマッピングが可能になる
		// このように、カラム名とフィールド名が一致する場合は、BeanPropertyRowMapperを使うと便利である
		// RowMapperの生成
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		// SQL実行
		return jdbc.queryForObject(sql, rowMapper, userId);
	}

	// ユーザー全権取得
	@Override
	public List<User> selectMany(){
		// M_USERテーブルのデータを全権取得するSQL
		String sql = "SELECT * FROM m_user";
		// RowMapperの生成
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		// SQL実行
		return jdbc.query(sql, rowMapper);
	}
}
