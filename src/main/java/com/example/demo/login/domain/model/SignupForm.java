package com.example.demo.login.domain.model;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

// ユーザー登録画面用のフォームクラスを編集します
@Data
public class SignupForm {
	private String userId; // ユーザーID
	private String password; // パスワード
	private String userName; // ユーザー名
	// @DateTimeFormatアノテーションをフィールドにつけることで、画面から渡されてきた文字列を日付方に変換してくれる
	// なお、pattern属性にどのようなフォーマットでデータが渡されてくるかを指定する
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date birthday; // 誕生日

	private int age; // 年齢
	private boolean marriage; // 結婚ステータス
}
// データバインド用のアノテーションは@DateTimeFormat以外には、@NumberFormatがある
// @NumberFormatアノテーションは、指定されたフォーマットの文字列を数値型に変換する
// @NumberFormat(pattern = "#,###")
// String salary;						のように記述する
