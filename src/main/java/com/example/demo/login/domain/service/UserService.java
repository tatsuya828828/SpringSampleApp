package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Service
public class UserService {
	@Autowired
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
}
