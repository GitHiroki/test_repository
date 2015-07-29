package studyFukushima;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class test_fukushima {

	static final String outputFilePath = "C:\\Users\\HIROKI\\Desktop\\";
	static final String outputFileName = "fukushima.txt";
	static final String moveFilePath = "F:\\";

	public static void main(String[] args) {

		excute(); // 実行メソッド

		System.exit(0);

	}

	// 実行メソッド記述するとこ
	private static void excute() {
		System.out.println(outputFileTest());

	}

	private static String outputFileTest() {
		// ファイルオブジェクト生成
		File file = new File(outputFilePath + outputFileName);
		boolean retryFlg;
		int retryCnt = 0; //リトライカウント
		String msg= "";

		try {
			// 結果保存用変数
			boolean createResult = false;
			boolean writeResult = false;
			boolean moveResult = false;

			do {
				createResult = createFile(file);
				writeResult = writeFile(file);
				moveResult = moveFile(file);

				if(createResult && writeResult && moveResult){
					// 正常系
					msg = "ファイル出力が正常完了しました。";
					retryFlg = false;
				} else {
					// 異常系
					retryFlg = true;
				}
				retryCnt++;
			} while (retryFlg && retryCnt < 5);

			// 異常終了して規定回数リトライでも異常終了した
			if (retryFlg == true) {
				msg = "ファイル出力が異常終了しました。\n"
						+ "プロセスを終了します。";
			}

			// 出力フォルダのファイル後始末
			if (file.exists()) {
				file.delete();
			}

		} catch (SecurityException se) {
			se.printStackTrace();
		}
		return msg;
	}

	// ファイル移動
	private static boolean moveFile(File file) {
		File moveToFile = new File(moveFilePath + outputFileName);
		boolean result;

		if (file.renameTo(moveToFile)){
			System.out.println("ファイルの移動が完了しました。");
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	// ファイル書き込み
	private static boolean writeFile(File file) {
		String writeStr = "テストふくしま";
		// ファイルに書き込む
		FileWriter filewriter;
		boolean result;
		try {
			// ファイルがあったら処理実行
			if (file.exists()) {
				filewriter = new FileWriter(file);
				filewriter.write(writeStr);
				filewriter.close();
				result = true;
			} else {
				result = false;
			}

			return result;
		} catch (IOException e) {
			return false;
		}

	}

	// ファイル作成
	private static boolean createFile(File file) {
		try {
			boolean result;
			// ファイルチェック作成しようとしているファイル名と同じものがあったら作らない
			if (file.exists()) {
				if (file.delete())
					System.out.println("ファイルは削除されました。");

				result = false;
			} else {
				if (file.createNewFile()) // ファイル作成
				System.out.println("ファイルが作成されました。");

				result = true;
			}

			return result;
		} catch (Exception e) {
			return false;
		}
	}

}
