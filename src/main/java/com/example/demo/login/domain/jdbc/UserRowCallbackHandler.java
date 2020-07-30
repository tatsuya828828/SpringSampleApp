package com.example.demo.login.domain.jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

/* RowCallbackHandlerは、RowMapperと似たような使い方をする。
 * まずは、RowCallbackHandlerをimplementsする、
 * そして、processRow()メソッド内で、ResultSetから取得した値をsample.csvに書き込む処理をしている
 * 書き込み自体は、Javaが用意しているライブラリを使っている
 * ちなみに、ResultSetExtractorでは、Resultのnext()メソッドを使わなければ、レコードの値を取得できなかったが
 * RowCallbackHandlerの場合は、既に1回next()メソッドが実行された状態になっているため、
 * while文ではなく、do~while文でループ処理を行なっている
 * 作成したファイルはプロジェクトフォルダの直下におかれる
 */
public class UserRowCallbackHandler implements RowCallbackHandler {
	@Override
	public void processRow(ResultSet rs) throws SQLException {
		try {
			// ファイルの書き込みの準備
			File file = new File("sample.csv");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			// 取得件数分loop
			do {
				// ResultSetから値を取得してStringにセット
				String str = rs.getNString("user_id") +","
						+ rs.getString("password") +","+ rs.getString("user_name") +","+ rs.getDate("birthday") +","
						+ rs.getInt("age") +","+ rs.getBoolean("marriage") +","+ rs.getString("role");
				// ファイルに書き込み&改行
				bw.write(str);
				bw.newLine();
			} while(rs.next());
				// 強制的に書き込み&ファイルクローズ
				bw.flush();
				bw.close();
		} catch(IOException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}
	}
}
