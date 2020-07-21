package com.example.demo.login.domain.model;
import java.util.Date;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

// ユーザー登録画面用のフォームクラスを編集します
@Data
public class SignupForm {
	// 入力必須、メールアドレス形式
	@NotBlank
	@Email
	private String userId; // ユーザーID
	// 必須入力、長さ4から100桁まで、半角英数字のみ
	@NotBlank
	@Length(min= 4, max=100)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String password; // パスワード
	// 必須入力
	@NotBlank
	private String userName; // ユーザー名
	// @DateTimeFormatアノテーションをフィールドにつけることで、画面から渡されてきた文字列を日付方に変換してくれる
	// なお、pattern属性にどのようなフォーマットでデータが渡されてくるかを指定する
	// 必須入力
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date birthday; // 誕生日
	// 値が0から100まで
	@Min(0)
	@Max(100)
	private int age; // 年齢
	// falseのみ可能
	@AssertFalse
	private boolean marriage; // 結婚ステータス
}
// データバインド用のアノテーションは@DateTimeFormat以外には、@NumberFormatがある
// @NumberFormatアノテーションは、指定されたフォーマットの文字列を数値型に変換する
// @NumberFormat(pattern = "#,###")
// String salary;						のように記述する
