package convertNumbersToDotDisplay;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

//ドット表示パターン法
/*
 * 例：0の場合(アンダースコアはスペース)
 * 0000
 * 0__0
 * 0__0
 * 0__0
 * 0000
 *
 * と表示するのに必要とするパターンは、
 * １．「0000」
 * ２．「0__0」
 * の２パータン必要である。
 * パターン１
 * パターン２
 * パターン２
 * パターン２
 * パターン１
 * と表示処理を記述する。
 * そのため0～9までの数字のパターンを静的変数にて「定義」
 * 入力された数字に応じてパターンを呼び出し出力させる
 */

/**
 * 入力された数字をドット形式に変換し出力、表示するプログラムです。
 * @version 1.0
 * @author HIROKI
 */
public class ConvertNumbersToDotDisplay extends JFrame implements ActionListener{

	// 共通ドットパターン定義
	static final String PATTERN1 = "000"; // パターン１
	static final String PATTERN2 = "0 0"; // パターン２
	static final String PATTERN3 = "00 "; // パターン３
	static final String PATTERN4 = "  0"; // パターン４
	static final String PATTERN5 = "0  "; // パターン５
	static final String PATTERN6 = " 0 "; // パターン６

	// 数字別ドットパターン定義
	static final String[] DOT0 = {PATTERN1, PATTERN2, PATTERN2, PATTERN2, PATTERN1};
	static final String[] DOT1 = {PATTERN6, PATTERN3, PATTERN6, PATTERN6, PATTERN1};
	static final String[] DOT2 = {PATTERN1, PATTERN4, PATTERN1, PATTERN5, PATTERN1};
	static final String[] DOT3 = {PATTERN1, PATTERN4, PATTERN1, PATTERN4, PATTERN1};
	static final String[] DOT4 = {PATTERN2, PATTERN2, PATTERN1, PATTERN4, PATTERN4};
	static final String[] DOT5 = {PATTERN1, PATTERN5, PATTERN1, PATTERN4, PATTERN1};
	static final String[] DOT6 = {PATTERN1, PATTERN5, PATTERN1, PATTERN2, PATTERN1};
	static final String[] DOT7 = {PATTERN1, PATTERN2, PATTERN4, PATTERN4, PATTERN4};
	static final String[] DOT8 = {PATTERN1, PATTERN2, PATTERN1, PATTERN2, PATTERN1};
	static final String[] DOT9 = {PATTERN1, PATTERN2, PATTERN1, PATTERN4, PATTERN1};

	/**
	 * ドットパターンを返すオブジェクトを列挙型にて宣言します。
	 * Switch文の代替コードです。
	 * @author HIROKI
	 */
	public enum MyEnum {
		// 列挙型に数値のドットパターンを返すメソッドを用意します。
		// 定数の横の()はIDです。
		ZERO(0)  { @Override public String[] getNumDotPattern() { return DOT0; } },
		ONE(1)   { @Override public String[] getNumDotPattern() { return DOT1; } },
		TWO(2)   { @Override public String[] getNumDotPattern() { return DOT2; } },
		THREE(3) { @Override public String[] getNumDotPattern() { return DOT3; } },
		FOUR(4)  { @Override public String[] getNumDotPattern() { return DOT4; } },
		FIVE(5)  { @Override public String[] getNumDotPattern() { return DOT5; } },
		SIX(6)   { @Override public String[] getNumDotPattern() { return DOT6; } },
		SEVEN(7) { @Override public String[] getNumDotPattern() { return DOT7; } },
		EIGHT(8) { @Override public String[] getNumDotPattern() { return DOT8; } },
		NINE(9)  { @Override public String[] getNumDotPattern() { return DOT9; } };

		// 定数のid
		private final int id;

		// コンストラクタ
		private MyEnum(final int id) {
			this.id = id;
		}

		// もらったidから定義されているEnumIdと一致すればそのEnumを返します。
		// getIdとは逆のことをしています。
		public static MyEnum valueOf(int id) {
			for (MyEnum num : values()) {
				if (num.getId() == id) {
					return num;
				}
			}
			// こうすることで余計なreturn文を書かなくて良い
			// Idが見つからなかった時のためのExceptionスロー
			throw new IllegalArgumentException("定義情報の中にidが見つかりませんでした。");
		}

		// Enumオブジェクトのidを取得します。
		public int getId() {
			return id;
		}

		// ドットパターンを返す抽象クラスです。
		public abstract String[] getNumDotPattern();
	}

	JTextArea inputTxtArea;		// 入力用テキストエリア
	JTextArea outputTxtArea;	// 出力用テキストエリア

	/**
	 * メインクラスです。
	 * @param args
	 */
	public static void main(String[] args) {
		// 入力インターフェース表示
		ConvertNumbersToDotDisplay frame = new ConvertNumbersToDotDisplay();
		frame.setVisible(true);				// フレームの表示
		frame.inputTxtArea.requestFocus();	// 入力テキストエリアにフォーカスを設定
	}

	/**
	 * 変換の際のフレームを内での処理をここに記述します。
	 * コンストラクタです。
	 */
	public ConvertNumbersToDotDisplay() {
		// 初期設定(コンポーネントの設定を行います)
		this.initialize();
	}

	/**
	 * コンポーネントの設定を行います。
	 */
	private void initialize() {
		setTitle("ドット変換");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 300, 760, 180);

		JPanel panel = new JPanel();

		inputTxtArea = new JTextArea(3, 20);
		inputTxtArea.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		outputTxtArea = new JTextArea(6, 50);
		outputTxtArea.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		Label convertLabel = new Label("変換⇒");
		JButton convertBtn = new JButton("変換");

		panel.add(inputTxtArea);
		panel.add(convertLabel);
		panel.add(outputTxtArea);

		// イベントリスナーの設定
		convertBtn.addActionListener(this);

		// コンポーネントの配置
		Container contentPane = getContentPane();
		contentPane.add(panel, BorderLayout.NORTH);
		contentPane.add(convertBtn, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 入力値取得
		ArrayList<String> inputNumList = getInputTxtToArray(inputTxtArea.getText());

		// ドット表示用配列作成
		ArrayList<String[]> outputDotList = createDotNumberList(inputNumList);

		// 出力処理
		outputDotNumbers(outputDotList);
	}

	/** 出力時はoutputDotListを5回ループして
	 * 一行ずつ出力していきます。
	 * 出力時はテキストエリアをクリア
	 *
	 * 例:01入力の場合
	 *
	 * 000  0  //1行目出力
	 * 0 0 00  //2行目出力
	 * 0 0  0  //3行目出力
	 * 0 0  0  //4行目出力
	 * 000 000 //5行目出力
	 *
	 * 1行目から順に出力していく。
	 */
	private void outputDotNumbers(ArrayList<String[]> outputDotList) {

		final int DOT_DISPLAY_ROW_INDEX = 5; // 行数

		outputTxtArea.setText("");	// テキストエリア初期化
		for (int i = 0; i < DOT_DISPLAY_ROW_INDEX; i++) {
			for (int j = 0; j < outputDotList.size(); j++) {
				outputTxtArea.append(outputDotList.get(j)[i]);
				outputTxtArea.append(" ");	// 次の数字との間のスペース
			}
			// 1行出力終わり
			outputTxtArea.append("\r\n");
		}
	}

	/**
	 * 入力された文字列からドット出力用の
	 * リストを作成します。
	 * @param inputNumList
	 * @return ドットパターンが入ったリスト
	 */
	private ArrayList<String[]> createDotNumberList(ArrayList<String> inputNumList) {

		ArrayList<String[]> returnList = new ArrayList<>();

		// 入力文字列を１文字ずつ見て終わるまで繰り返し
		for (int i = 0; i < inputNumList.size(); i++) {

			//↓↓↓↓↓↓↓ もともとswitch文にて処理していた場所 ↓↓↓↓↓↓↓//
			int dotId = Integer.valueOf(inputNumList.get(i));
			returnList.add(MyEnum.valueOf(dotId).getNumDotPattern());
			//↑↑↑↑↑↑↑ もともとswitch文にて処理していた場所 ↑↑↑↑↑↑↑//

		}
		return returnList;
	}

	/**
	 * 入力された値を配列で取得します。
	 * @param inputTxt：テキストボックスに入力された値
	 */
	private ArrayList<String> getInputTxtToArray(String inputTxt) {
		ArrayList<String> returnList = new ArrayList<String>();

		// 入力文字列を位置文字ずつ取り出してリストに格納
		for (int i = 0; i < inputTxt.length(); i++) {
			returnList.add(String.valueOf(inputTxt.charAt(i)));
		}

		return returnList;
	}
}


