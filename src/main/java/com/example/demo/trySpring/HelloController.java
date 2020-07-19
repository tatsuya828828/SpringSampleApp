package com.example.demo.trySpring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Springではコントローラークラスに@Controllerアノテーションをつける
// @ControllerアノテーションをつけることでDI(依存性注入)で利用できるようになる
@Controller
public class HelloController {
	// GetMappingアノテーションをメソッドにつけるとHTTPリクエストのGETメソッドを処理できるようになる
	// GETリクエストに対する処理をgetHelloメソッドで行うという意味になる
	// なお、GETリクエストの場合、メソッド名の最初にgetをつけるのが慣習となっている
	// メソッドの戻り値にhtmlファイルを指定することでGETリクエストが来るとhello.htmlを表示してくれる
	@GetMapping("/hello")
	public String getHello() {
		// hello.htmlに画面遷移
		return "hello";
	}
}
