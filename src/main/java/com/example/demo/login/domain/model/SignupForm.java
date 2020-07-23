package com.example.demo.login.domain.model;
import java.util.Date;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
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

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private Date birthday; // 誕生日
	// 値が0から100まで
	@Range(min=20, max=100)
	private int age; // 年齢
	// falseのみ可能
	@AssertFalse
	private boolean marriage; // 結婚ステータス
}
// データバインド用のアノテーションは@DateTimeFormat以外には、@NumberFormatがある
// @NumberFormatアノテーションは、指定されたフォーマットの文字列を数値型に変換する
// @NumberFormat(pattern = "#,###")
// String salary;						のように記述する

/* バリデーション用のアノテーションは
	javax.validation
@NotNull: nullでないことをチェック
@NotEmpty: 文字列やCollectionがnullまたはからでないことをチェック
@NotBlank: 文字列がnull、からもじ、空白スペースのみでないことをチェック
@Max: 指定した値以下であるかをチェック
@Min: 指定した値以上であるかをチェック
@Size: 文字列の長さやCollectionのsizeが、指定した範囲内にあるかどうかをチェック
@AssertTrue: trueかどうかチェック
@AssertFale: falseかどうかチェック
@Pattern: 指定した正規表現に一致するかどうかをチェック
@Email: 文字列がメールアドレス形式かどうかをチェック
	hibernate.validator
@Range: 数値が指定した範囲内にあるかをチェック
@Length: 文字列の長さが指定した範囲内であるかをチェック
@CreditCardNumber: 文字列がクレジットカード番号形式かどうかをチェック
@URL: 文字列がURL形式かどうかをチェック
*/

