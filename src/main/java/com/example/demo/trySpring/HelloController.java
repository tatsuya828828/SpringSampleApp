package com.example.demo.trySpring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	//	@GetMappingアノテーションと同じようにメソッドに、@PostMappingアノテーションをつけることでPOSTメソッドで送られてきた場合の処理ができる
	@PostMapping("/hello")
	// メソッドの引数に@RequestParamアノテーションをつけることで、画面からの入力内容を受け取ることができる
	// アノテーションの引数にはhtmlのname属性の値を指定する
	public String postRequest(@RequestParam("text1")String str, Model model) {
		// model.attAttributeにキーと値をセットしておくことで画面から指定したキーの値を受け取ることができる
		model.addAttribute("sample", str);
		// helloResponse.htmlに画面遷移
		return "helloResponse";
	}
}
