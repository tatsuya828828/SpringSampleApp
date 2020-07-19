package com.example.demo.trySpring;

import lombok.Data;

// @Dataアノテーションをつけると、getterやsetterなどを自動で作成してくれる
// これはSpringではなくて、Lombokの機能である
@Data
public class Employee {
	private int employeeId; // 従業員ID
	private String employeeName; // 従業員名
	private int age; // 年齢
}
// リポジトリークラスやサービスクラスなどの間で渡すクラスのことを、Springではdomainクラスと呼ぶ
// Employeeクラスもドメインクラスである
