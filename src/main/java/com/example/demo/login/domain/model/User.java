package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

// データベースから取得した値を、コントローラーやサービスクラスなどの間でやりとりするためのクラス
// これはユーザーテーブルのカラムをフィールドに持つためのクラスである
// なお、@Dataアノテーションをつけているため、Lombokでsetterやgetterを自動で作る
@Data
public class User {

	private String userId;
	private String password;
	private String userName;
	private Date birthday;
	private int age;
	private boolean marriage;
	private String role;
}
