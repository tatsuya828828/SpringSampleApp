package com.example.demo.login.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// AOPのクラスには、@Aspectアノテーションをつける
// 同時にDIコンテナにBean定義をするため、@Componentアノテーションもつける。この2つのアノテーションをセットでつける
@Aspect
@Component
public class LogAspect {
	// AOPを実行するメソッドには@Beforeや@Afterアノテーションをつける
	// これらは、JoinPointと同じ名称を指定する。アノテーション内のパラメーターには、どのクラスのどのメソッドが呼び出されるかを指定する
	// excutionの指定方法
	// excution(戻り値 パッケージ名.クラス名.メソッド名（引数))
	// 正規表現の使い方
	// *: *を使用すると、任意の文字列を表す。」パッケージでは1階層のパッケージ名、メソッドの引数では、1つの引数になる
	//..: ドットを2個続けると、パッケージの記述箇所では、任意(0以上)のパッケージになるメソッドの引数では、任意(0以上)の引数になる
	// +: +をクラス名の後に指定すると、指定クラスのサブクラス/実装クラスが含まれる
	@Before("execution( * *..*.*Controller.*(..))")
	public void startLog(JoinPoint jp) {
		System.out.println("メソッド開始:"+ jp.getSignature());
	}

	@After("execution( * *..*.*Controller.*(..))")
	public void exdLog(JoinPoint jp) {
		System.out.println("メソッド終了:"+ jp.getSignature());
	}
}