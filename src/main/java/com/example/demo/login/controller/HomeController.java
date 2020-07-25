package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.service.UserService;

@Controller
public class HomeController {
	@Autowired
	UserService userService;

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

	// ログアウト用メソッド
	@PostMapping("/logout")
	public String postLogout() {
		// ログアウト後はログイン画面にリダイレクト
		return "redirect:/login";
	}


}
