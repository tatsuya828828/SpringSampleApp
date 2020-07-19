package com.example.demo.trySpring;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// リポジトリークラスにも@Repositoryクラスをつける
// こうすることでDIに登録される
@Repository
public class HelloRepository {
	// JdbcTemplateを使う場合は、@Autowiredアノテーションをつける
	// 最初の内は、@Autowiredをつけることでインスタンスを生成しているというイメージで良い
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Map<String, Object> findOne(int id){
		// Springが用意しているJdbc接続用のクラス(JdbcTemplate)を使って、SELECT分を実行している
		// SyntaxErrorが発生するためスペースやカンマを入れることを気をつける
		String query = "SELECT " + "employee_id," + "employee_name," + "age " + "FROM employee " + "WHERE employee_id=?";
		// 検索実行
		Map<String, Object> employee = jdbcTemplate.queryForMap(query, id);
		return employee;
	}
}
