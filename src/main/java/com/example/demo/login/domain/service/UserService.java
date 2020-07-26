package com.example.demo.login.domain.service;

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
}
