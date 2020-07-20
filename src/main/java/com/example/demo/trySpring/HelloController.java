package com.example.demo.trySpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


// Springではコントローラークラスに@Controllerアノテーションをつける
@Controller
public class HelloController {
	//
	@Autowired
	private HelloService helloService;
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
	// メソッドの引数に@RequestParamアノテーションをつけることで、画面からの入力内容を受け取ることができる
	// アノテーションの引数にはhtmlのname属性の値を指定する
	@PostMapping("/hello")
	public String postRequest(@RequestParam("text1")String str, Model model) {
		// model.attAttributeにキーと値をセットしておくことで画面から指定したキーの値を受け取ることができる
		model.addAttribute("sample", str);
		// helloResponse.htmlに画面遷移
		return "helloResponse";
	}

	// @RequestParamアノテーションでtext2という名前のパラメーターを受け取る
	// あとはその値をHelloServiceクラスのfindOneメソッドに渡せば、検索結果としてEmployeeのインスタンスが返ってくる
	// また、employeeクラスの値をmodel.addAttributeで登録しておけば、画面で受け取ることができる
	@PostMapping("/hello/db")
	public String postDbRequest(@RequestParam("text2")String str, Model model) {
		// Stringからint型に変換
		int id = Integer.parseInt(str);
		// 1件検索
		Employee employee = helloService.findOne(id);

		// 検索結果をModelに登録
		model.addAttribute("id", employee.getEmployeeId());
		model.addAttribute("name", employee.getEmployeeName());
		model.addAttribute("age", employee.getAge());
		// helloResponsiveDB.htmlに画面遷移
		return "HelloResponseDB";
	}
}

/*
	@ControllerアノテーションをつけることでDI(依存性注入)で利用できるようになる

	DI(依存性注入)とは、Springの全ての土台となっているためとても重要である、一言で何をやっているのかを表すとインスタンス管理である
	@Autowiredアノテーションをフィールドなどにつけると、DIコンテナからインスタンスを取得してくる

	DIコンテナは、@Controller,@Service,@Repositoryなどのアノテーションがついたクラスのインスタンスを管理している
	DIコンテナの中で、インスタンスを生成する(クラスをnewする)そして、アプリケーションではそれらのインスタンスを取得して利用する
	DIの中で、毎回アプリケーションにnewしたインスタンスを渡すのか、それとも一度newしたインスタンスを渡すのか管理してくれている

	また、インスタンスのライフサイクル管理を簡単に実装できるようになる、これは、Webアプリケーションの開発をとても楽にする
	どういうことかというと、サーブレットのリクエストスコープや、セッションスコープにインスタンスを簡単に登録できるということ
	このようにインスタンスの生成とライフサイクル管理(インスタンスの破棄)をDIがやってくれるため、クラスをnewしたり、使い終わった変数にnullを入れる必要がなくなる
	こうすることで、nullの入れ忘れを防止できたり、コードの可読性が上がる

	Javaにおける依存性とは何かというと、他のクラスを利用しているかどうかということ、具体的にいうと
	他のクラスをローカル変数として持っている、他のクラスがメソッドの引数、戻り値になっている
	のどちらかが該当すると依存していると言う
	インタフェースなどの抽象的なものに依存してる場合は、依存度合いが低く疎結合という
	逆に、具体的なクラスなどに依存している場合は、依存度合いが高く、密結合という
	インタフェース型の変数に、インスタンスを入れることを注入という

	Springを起動すると、コンポーネントスキャンという処理が走る。
	これは、DIで管理する対象のアノテーションがつけられているクラスを探す処理である
	コンポーネントスキャン対象のアノテーションは、
	@Component, @Controller, @Service, @Repository, @Configuration, @RestController, @ControllAdvice,
	@ManagedBean, @Named
	などがある、これらのアノテーションがついているクラスのことを、Beanという
	アノテーションをつける以外にもDIコンテナにクラスを登録することができるため、正確にはDIコンテナ常で管理するクラスのことをBeanと呼ぶ
	DI対象のクラス(Bean)を集めたあとは、それらのインスタンスをDIが生成し、@Autowiredアノテーションがついているフィールドなどに注入(代入)する
	DIコンテナの中で、各クラスのインスタンスを生成しておいて、そのインスタンスをgetterで取得できるイメージ、
	そして@Autowiredがついているフィールドなどでは、DIコンテナのgetterを呼び出しているイメージのように捉えておくと良い
	これは、Factoryクラスを作ってインスタンスを取得しているようなもの、しかしFactoryクラスとは違い、毎回生成してはいない(することも可能)
	つまり、@Componentなどのアノテーションをつけるだけで、いちいちFactoryクラスを作る手間が省けるということ
	ちなみに@Autowiredをつけることができるのは、フィールド変数、コンストラクタの引数、setterの引数、の3つ、それ以外にはつけることができない

	DIの実装方法は、アノテーションベース、	JavaConfig、 xml、 JavaConfig+アノテーション、 xml+アノテーションの5つがある
	その中で、JavaConfigのDI実装方法は
	最初にDIコンテナに登録するBeanの定義をするため、まずは@Configurationアノテーションをつけたクラスを用意する
	そのクラスの中に@Beanアノテーションがついたgetterメソッドを用意する、これでgetterの戻り値がBeanとしてDIコンテナに登録される
	次に、インスタンスの生成と注入をするためDIコンテナに登録されたBeanインスタンス生成と注入をしている

	DIコンテナの中で、JavaConfigクラス、Beanのインスタンスを用意しておいて、各インスタンスをgetterで取得できるといったイメージ
	そして@Autowiredがついている部分でgetterを呼び出しているこれはアノテーションベースでDIを実装する場合も同じである
	アノテーションベースでDIを実装する方がの方が簡単にアプリケーションを作れるがJavaConfigを使うと細かい設定や切り替えができるといった利点がある
	例えば、インスタンスを生成する際にコンストラクタなどに渡す値などを設定することができる、他にも本番環境と開発環境用のJavaConfigクラスを切り替えたりできる
	開発環境が大きくなればなるほど、JavaConfigの中に定義しないといけないメソッドが増えていく、それを回避するために通常は、
	アノテーションベースとJavaConfigの両方を使ってDIを実装する
	アノテーションベースとJavaCofigの両方でDIを実装した場合、DIで管理したいクラスに@Controllerなどのアノテーションをつける
	細かい設定などをしたいインスタンスだけ、JavaConfigで設定するといったようなやり方でアプリケーションを開発する

	DIでは、ライフサイクル管理も行っている、ライフサイクルはインスタンスの生成と破棄を管理してくれる
	インスタンスを生成するときは、通常はnewを使って生成する、逆に破棄するときは変数にnullを入れる
	これは使わなくなった変数にnullを入れないと、メモリ上に残り続けてメモリを無駄にするからである、
	メモリを無駄にすることによってパフォーマンスへの影響やメモリ不足によるシステム停止を引き起こす
	しかし、@AutowiredをつけたフィールドはSpringが自動でどちらも行ってくれる、またJavaでWebアプリケーションを作る場合サーブレットを使う
	サーブレットを使う場合、インスタンスをSessionスコープやRequestスコープに登録するが、それもSpringがやってくれる
	しかし、自動で行ってくれるからこそ、いつそのインスタンスが破棄されるのかを把握しておくことが必要である
	ちなみにSessionスコープやRequestスコープをいうのは、インスタンスの有効期限である
	Sessionスコープは、ユーザーがログインしてからログアウトするまでが有効期限である
	例えば、ユーザー情報やそれに紐づく権限などの情報をSessionスコープとして持っておく
	RequestスコープとはHTTPの1リクエストが有効期限である
	例えば、ユーザー登録画面で入力を行い、登録ボタンを押すと登録結果として入力値を表示する画面が出るとすると
	その場合、ユーザー登録画面から登録結果画面までがRequestスコープの範囲となる

	どうやってライフサイクル管理をするかというと、@Scopeアノテーションをつける、そのアノテーションに、どのスコープに登録するかを指定する
	@Scopeの中には、
	singletonスコープ: Spring起動時にインスタンスを1つだけ生成してそれ以降は1つのインスタンスを共有して使う
					  デフォルト設定のため、@Scopeアノテーションをつけなかった場合は、全てsingletonになる
	prototypeスコープ: Beanを取得するたびに、毎回インスタンスが生成される
	sessionスコープ: HTTPのセッション単位でインスタンスが生成される、Webアプリケーションの場合のみ使用できる
	requestスコープ: HTTPのリクエスト単位でインスタンスが生成される、Webアプリケーションの場合のみ使用できる
	globalSessionスコープ: ポートレット環境におけるGrobalSession単位でインスタンスが生成される
						 ポートレットに対応したWebアプリケーションの場合のみ使用できる
	applicationスコープ: サーブレットのコンテキスト単位でインスタンスが生成される、Webアプリケーションの場合のみ使用できる
	といった値を指定できる
	@Componentや@Beanや@Controllerなどに@Scopeアノテーションは利用可能である

	ライフサイクルはアプリケーションを開発する上では、かなり便利な機能だが、落とし穴もある

	まず一つはsingletonを使った場合の落とし穴である
	Springを学び始めたばかりだと@Scopeをつけることを知らずにアプリケーションを作ることがある
	@Scopeをつけないと、インスタンスがsingletonで作成される
	singletonとは、Javaのデザインパターンの1つである、singletonだとオブジェクトのインスタンスは
	1つしか作れず2個目、3個目のインスタンスを作ることはできない
	ちなみに、@Contoroller、@Service、@Repositoryのスコープは通常singletonで問題ない

	二つ目はスコープの違いである
	例えば、singletonスコープのインスタンスが、prototypeスコープのオブジェクトを持っている場合などである
	このようなことをしてしまうと、prototypeスコープを設定したBeanがsingletonスコープになってしまう
	スコープが異なるBeanをフィールドとして持つ場合は注意が必要である



*/
