package com.example.demo.login.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class HomeController {
	@Autowired
	UserService userService;

	// 結婚ステータスのラジオボタン用変数
	private Map<String, String> radioMarriage;
	// ラジオボタンの初期化メソッド
	private Map<String, String> initRadioMarriage() {
		Map<String, String> radio = new LinkedHashMap<>();
		// 既婚、未婚をMapに格納
		radio.put("既婚", "true");
		radio.put("未婚", "false");

		return radio;
	}

	// ホーム画面のGET用メソッド
	// /homeにGETリクエストが来たとき、Modelクラスのcontentsというキーにlogin/home :: home_contentsという値をセットしている
	// この値が、homeLayout.htmlのth:include属性に入ってくる
	// つまりth:include="login/home :: home_contents"となる
	@GetMapping("/home")
	public String getHome(Model model) {
		// コンテンツ部分にホーム画面を表示するための文字列を登録
		model.addAttribute("contents", "login/home :: home_contents");
		return "login/homeLayout";
	}

	// ユーザー一覧画面のGET用メソッド
	@GetMapping("/userList")
	public String getUserList(Model model) {
		// コンテンツ部分にユーザー一覧を表示するための文字列を登録
		model.addAttribute("contents", "login/userList :: userList_contents");
		// ユーザー一覧の生成
		List<User> userList = userService.selectMany();
		// Modelにユーザーリストを登録
		model.addAttribute("userList", userList);
		// データ件数を取得
		int count = userService.count();
		model.addAttribute("userListCount", count);

		return "login/homeLayout";
	}

	// 動的URL
	/* 動的URLに対応したメソッドを作るためには、@GetMappingや@PostMappingの値に/{変数名}をつける
	 * 例えば、ユーザーIDを受け取る場合は、@GetMapping(/userDetail/{id})とする
	 * 今回の場合は、ユーザーIDがメールアドレス形式となっているため上のような書き方では処理がうまくいかない
	 * それに対応するため今回は正規表現を使い{id:.+}となっている
	 */
	// ユーザー詳細画面のGET用メソッド
	@GetMapping("/userDetail/{id:.+}")
	// @PathVariableアノテーションをつけると、渡されてきたパス(URL)の値を引数の変数に入れることができる
	// 今回の場合でいうとURLの{id:.+}の部分の値が引数のuserIdという変数に入れられる
	public String getUserDetail(@ModelAttribute SignupForm form, Model model, @PathVariable("id") String userId) {
		// ユーザーID確認(デバッグ)
		System.out.println("userId ="+ userId);
		// コンテンツ部分にユーザー詳細を表示するための文字列を登録
		model.addAttribute("contents", "login/userDetail :: userDetail_contents");
		// 結婚ステータスようラジオボタンの初期化
		radioMarriage = initRadioMarriage();
		// ラジオボタン用のMapをModelに登録
		model.addAttribute("radioMarriage", radioMarriage);
		// ユーザーIDのチェック
		if(userId != null && userId.length() > 0) {
			// ユーザー情報を取得
			User user = userService.selectOne(userId);
			// Userクラスをフォームクラスに変換
			form.setUserId(user.getUserId());
			form.setUserName(user.getUserName());
			form.setBirthday(user.getBirthday());
			form.setAge(user.getAge());
			form.setMarriage(user.isMarriage());
			// Modelに登録
			model.addAttribute("signupForm", form);
		}
		return "login/homeLayout";
	}

	// ボタン名によるメソッド判定
	// ユーザー更新用処理
	// 更新処理用のメソッドと削除用のメソッドを分けるためにparams属性を使い同じURLのPOSTでもボタン名で判別できるようにする
	@PostMapping(value = "/userDetail", params="update")
	public String postUserDetailUpdate(@ModelAttribute SignupForm form, Model model) {
		System.out.println("更新ボタンの処理");
		// Userインスタンスの生成
		User user = new User();
		// フォームクラスをUserクラスに変換
		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setBirthday(form.getBirthday());
		user.setAge(form.getAge());
		user.setMarriage(form.isMarriage());
		// 更新実行
		boolean result = userService.updateOne(user);
		if(result == true) {
			model.addAttribute("result", "更新成功");
		} else {
			model.addAttribute("result", "更新失敗");
			System.out.println("失敗");
		}
		// ユーザー一覧画面を表示
		return getUserList(model);
	}

	// ユーザー削除用処理
	@PostMapping(value = "/userDetail", params = "delete")
	public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model) {
		System.out.println("削除ボタンの処理");
		// 削除実行
		boolean result = userService.deleteOne(form.getUserId());
		if(result == true) {
			model.addAttribute("result", "削除成功");
		} else {
			model.addAttribute("result", "削除失敗");
		}
		// ユーザー一覧画面を表示
		return getUserList(model);
	}

	// ログアウト用メソッド
	@PostMapping("/logout")
	public String postLogout() {
		// ログアウト後はログイン画面にリダイレクト
		return "redirect:/login";
	}

	// ユーザー一覧のCSV出力用メソッド
	@GetMapping("/userList/csv")
	public ResponseEntity<byte[]> getUserListCsv(Model model){
		// ユーザーを全権取得して、CSVをサーバーに保存する
		userService.userCsvOut();
		byte[] bytes = null;
		try {
			// サーバーに保存されているsample.csvファイルをbyteで取得する
			bytes = userService.getFile("samplle.csv");
		} catch(IOException e) {
			e.printStackTrace();
		}

		// HTTPヘッダーの設定
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "sample.csv");

		// sample.csvを戻す
		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}
	/* 上のメソッドでは、サービスクラスのCSV出力とファイル取得メソッドを呼び出している
	 * その後に、HTTPヘッダーの値をセットして、リターンしている
	 * なお、メソッドの戻り値をResponseEntity型にすると、タイムリーフのテンプレート(html)ではなく、ファイル(byte型の配列)を呼び出し元に返却することができる
	 *
	 */
}
