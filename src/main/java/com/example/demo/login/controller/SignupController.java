package com.example.demo.login.controller;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class SignupController {
	@Autowired
	private UserService userService;

	// ラジオボタンの実装
	// タイムリーフでラジオボタンの値を動的に変更するためにはMapを用意する
	// そのMapに入ったキーと値を画面に表示することができる
	private Map<String, String> radioMarriage;
	// ラジオボタンの初期化メソッド
	// 今回は、initRadioMarriage()というメソッドのなかでMapに値を入れている
	// そして、ユーザー登録画面にGETリクエストが来たら、ModelクラスにMapを登録している
	// こうすることで画面からMapの値を取得できるようになる
	private Map<String, String> initRadioMarriage() {
		Map<String, String> radio = new LinkedHashMap<>();

		// 既婚、未婚をMapに格納
		radio.put("既婚", "true");
		radio.put("未婚", "false");

		return radio;
	}

	// ユーザー登録画面のGET用コントローラー
	@GetMapping("/signup")

	public String getSignUp(@ModelAttribute SignupForm form, Model model) {
		// ラジオボタンの初期化メソッド呼び出し
		radioMarriage = initRadioMarriage();
		// ラジオボタン用のMapをModelに登録
		model.addAttribute("radioMarriage", radioMarriage);

		// signup.htmlに画面遷移
		return "login/signup";
	}


	// 引数のフォームクラスに@ModelAttributeアノテーションをつけると、自動でModelクラスに登録(addAtribute)してくれる
	// なお、@ModelAttributeをつけた場合、デフォルトではクラス名の最初の文字を小文字に変えた文字列が、キー名に登録される
	// 今回の場合だと、signupFormというキー名で登録されている、もしキー名を変えたい場合は
	// @ModelAttribute("キー名")と、パラメータを指定する

	// データバインド結果を受け取るためには、メソッドの引数にBindingResultクラスを追加する
	// このクラスのhasErrorsメソッドで、データバインドに失敗しているかどうかがわかる
	// また、バリデーションでエラーが発生した場合もこのhasErrorsメソッドで失敗しているかどうかわかる

	// データバインドに失敗した場合、BindingResultのhasErrorsメソッドでfalseが返ってくる
	// 今回はデータバインドに失敗した場合、ユーザー登録画面に戻る。
	// その際には、getSignUpメソッドを呼び出している。理由としては、ラジオボタンの変数を初期化してくれるから

	// ユーザー登録画面のPOST用コントローラー
	@PostMapping("/signup")
	// バリデーションを実施するには、引数のフォームクラスに@Validatedアノテーションをつける
	// また、バリデーションのチェック結果はBindingResultクラスに入っている
	// @Validatedアノテーションのパラメーターに実行順序のインタフェースを指定する、そうすることで、バリデーションがグループ実行される
	// なお、実行順序のインタフェースではなく、グループのインタフェースを直接指定することも可能
	public String postSignUp(@ModelAttribute @Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult, Model model) {
		// 入力チェックに引っかかった場合、ユーザー登録画面に戻る
		if(bindingResult.hasErrors()) {
			// GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻る
			return getSignUp(form, model);
		}
		// formの中身をコンソールに出して確認する
		System.out.println(form);

		// まずはサービスクラスのメソッドに渡すUserクラスをnewしているUserクラスには、画面から入力された値をセットしていく
		// そして、サービスクラスのinsertメソッドを呼び出している
		// その後、コンソールにinsert結果を出力している
		// insert用変数
		User user = new User();
		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setBirthday(form.getBirthday());
		user.setAge(form.getAge());
		user.setMarriage(form.isMarriage());
		user.setRole("ROLE_GENERAL");
		// ユーザー登録処理
		boolean result = userService.insert(user);
		// ユーザー登録結果の判定
		if(result == true) {
			System.out.println("insert 成功");
		} else {
			System.out.println("insert 失敗");
		}
		// login.htmlにリダイレクト
		// リダイレクトする場合は、メソッドの戻り値にredirect:遷移先パスと指定する
		// リダイレクトすると遷移先のControllerクラスのメソッドが呼ばれる
		// 今回は、/loginにGETメソッドでHTTPリクエストが送られる。そして、LoginControllerのgetLoginメソッドが呼び出される
		return "redirect:/login";
	}

	/*
	 * Springでバリデーションをする際には@Validatedアノテーションを使う、ただし、@Validというアノテーションをつけても同じようにバリデーションを実施する
	 * @ValidはJ2EEから標準で搭載されているBeanValidator(バリデーション用のアノテーション)である
	 * メッセージ用のプロパティファイル名やエラーメッセージの書き方が@Validatedと比べて少し変わる。
	 */
}