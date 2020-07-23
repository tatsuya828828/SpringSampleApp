package com.example.demo.login.domain.model;

import javax.validation.GroupSequence;

/* バリデーションエラーメッセージを複数表示させないために、順序をつけて順番にエラーメッセージを表示するようにさせる
 * そのためにバリデーションをグループ実行する。そのためには、実行順序を設定するインターフェースに@GroupSequenceアノテーションをつけます
 * アノテーションのパラメータに、各グループの.classを指定する。なお、指定した順番に(左から順番に)バリデーションが実行される
 */
@GroupSequence({ValidGroup1.class, ValidGroup2.class, ValidGroup3.class})
public interface GroupOrder {

}
