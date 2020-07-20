package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {
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
	public String getSignUp(Model model) {
		// ラジオボタンの初期化メソッド呼び出し
		radioMarriage = initRadioMarriage();
		// ラジオボタン用のMapをModelに登録
		model.addAttribute("radioMarriage", radioMarriage);

		// signup.htmlに画面遷移
		return "login/signup";
	}

	// ユーザー登録画面のPOST用コントローラー
	@PostMapping("/signup")
	public String postSignUp(Model model) {
		// login.htmlにリダイレクト
		// リダイレクトする場合は、メソッドの戻り値にredirect:遷移先パスと指定する
		// リダイレクトすると遷移先のControllerクラスのメソッドが呼ばれる
		// 今回は、/loginにGETメソッドでHTTPリクエストが送られる。そして、LoginControllerのgetLoginメソッドが呼び出される
		return "redirect:/login";
	}
}
