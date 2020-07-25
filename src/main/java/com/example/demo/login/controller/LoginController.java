package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	// ログイン画面のGET用コントローラー
	@GetMapping("/login")
	public String getLogin(Model model) {
		// login.htmlに画面遷移
		// login/loginというのは、loginフォルダ配下のlogin.htmlを指定している
		// htmlファイルは、src/main/resources/templatesからのパスで指定する
		return "login/login";
	}

	// ログイン画面のPOST用のコントローラー
	@PostMapping("/login")
	public String postLogin(Model model) {
		// login.htmlに画面遷移
		return "redirect:/home";
	}
}
