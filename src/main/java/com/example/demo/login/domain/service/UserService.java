package com.example.demo.login.domain.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Service
public class UserService {
	@Autowired
	@Qualifier("UserDaoJdbcImpl4")
	/* @Autowiredと一緒に@Qualifierアノテーションを使用すると、どのBeanを使用するか指定することができる
	 * UserDaoインターフェースを継承したクラスが1つだけであればSpringが自動的にBeanを探してくれるため、@Qualifierはつける必要がない
	 * ただし、今回のようにインターフェースを継承したクラスが2つある場合は、@Qualifierをつけなければならない
	 * Springではインタフェースを作り、それを継承したクラスを作るというのが一般的な使い方になるため、@Qualifierは覚えておいた方が良い
	 */
	UserDao dao;

	// Serviceクラスのinsertメソッドでは、リポジトリークラスのinsertOneメソッドを呼び出している
	// そして、戻り値が0より大きければinsertが成功したという判定結果をリターンしている
	// insert(登録)用メソッド
	public boolean insert(User user) {
		// insert(登録)実行
		int rowNumber = dao.insertOne(user);
		// 判定用変数
		boolean result = false;
		if(rowNumber>0) {
			// insert成功
			result = true;
		}
		return result;
	}

	// カウント用メソッド
	public int count() {
		return dao.count();
	}

	// 全権取得メソッド
	public List<User> selectMany() {
		// 全権取得
		return dao.selectMany();
	}
	// 1件取得メソッド
	public User selectOne(String userId) {
		// selectOne実行
		return dao.selectOne(userId);
	}
	// 1件更新メソッド
	public boolean updateOne(User user) {
		// 1件更新
		int rowNumber = dao.updateOne(user);
		// 判定用変数
		boolean result = false;
		if(rowNumber>0) {
			// update成功
			result = true;
		}
		return result;
	}

	// 1件削除メソッド
	public boolean deleteOne(String userId) {
		// 1件削除
		int rowNumber = dao.deleteOne(userId);
		// 判定用変数
		boolean result = false;
		if(rowNumber>0) {
			// delete成功
			result = true;
		}
		return result;
	}

	// ユーザー一覧をCSV出力する
	public void userCsvOut() throws DataAccessException {
		// CSV出力
		dao.userCsvOut();
	}

	// サーバーに保存されているファイルを取得して、byte配列に変換する
	public byte[] getFile(String fileName) throws IOException {
		// ファイルシステム(デフォルト)の取得
		FileSystem fs = FileSystems.getDefault();
		// ファイル取得
		Path p = fs.getPath(fileName);
		// ファイルをbyte配列に変換
		byte[] bytes = Files.readAllBytes(p);
		return bytes;
	}
	/* ユーザー一覧を出力するメソッドは、リポジトリークラスのCSV出力メソッドを呼び出しているだけ
	 * 一方、ファイルを取得するメソッドについては、引数で指定されたファイル名をサーバーから取得する
	 * そして、ファイルの中身をbyte型の配列にしてリターンしている
	 */
}
