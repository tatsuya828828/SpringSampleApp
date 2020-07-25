package com.example.demo.login.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
	// 今回のようなPointcut(実行場所)の記述では、クラス名の最後にControllerがつくクラスの全てのメソッドをAOPの対象としている
	// 戻り値を*にすることで、全ての戻り値を指定し、パッケージ名を*..*とすることで全てのパッケージが対象になっている
	// また、クラス名を*Controllerと指定することで、末尾にControllerとつくクラスが対象になる
	// JoinPoint(実行タイミング)でAroundを使う場合、@Aroundアノテーションを使う
	// Aroundを使う場合、アノテーションをつけたメソッドの中でAOP対象クラスのメソッドをproceedメソッドで直接実行する。
	// だから、Aroundを使うと、メソッド実行の前後で任意の処理をすることができる。
	// メソッドを調節実行しているためreturnには実行結果の戻り値を指定する。
	// Around内でメソッドの実行を忘れると大変なので、注意すること
	// @Aroundのパラメータを、
	// @Around("bean(*Controller)")とすることでDIに登録されている、名前の最後にControllerと付くbean名をAOPの対象を指定できる
	// @Around("@annotasion(org.springframework.web.bind.annotation.GetMapping)")とすることでアノテーションがついている全てのメソッドを対象とする
	// @Around("@within(org.springframework.stereotype.Controller)")とすることで指定したアノテーションがついているクラスの全てのメソッドが対象となる
	@Around("execution(* *..*.*Controller.*(..))")
	public Object startLog(ProceedingJoinPoint jp)throws Throwable {
		System.out.println("メソッド開始:"+ jp.getSignature());
		try {
			Object result = jp.proceed();
			System.out.println("メソッド終了:"+ jp.getSignature());
			return result;
		} catch(Exception e) {
			System.out.println("メソッド異常終了:"+ jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}

	// UserDaoクラスのログ出力
	@Around("execution(* *..*.*UserDao*.*(..))")
	public Object daoLog(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("メソッド開始:"+ jp.getSignature());
		try {
			Object result = jp.proceed();
			System.out.println("メソッド終了:"+ jp.getSignature());
			return result;
		} catch(Exception e) {
			System.out.println("メソッド異常終了:" + jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}
}