package com.example.demo.login.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.login.domain.model.Article;
import com.example.demo.login.domain.model.ArticleupForm;
import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.ArticleService;
import com.example.demo.login.domain.service.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
	@Autowired
	ArticleService articleService;
	
	// 結婚ステータスのラジオボタン用変数
	private Map<String, String> radioMarriage;

	/**
	 * ラジオボタンの初期化メソッド（ユーザー登録画面と同じ）.
	 */
	private Map<String, String> initRadioMarrige() {

		Map<String, String> radio = new LinkedHashMap<>();

		// 既婚、未婚をMapに格納
		radio.put("既婚", "true");
		radio.put("未婚", "false");

		return radio;
	}

	/**
	 * ホーム画面のGET用メソッド
	 */
	@GetMapping("/home")
	public String getHome(Model model) {

		// コンテンツ部分にユーザー詳細を表示するための文字列を登録
		model.addAttribute("contents", "login/home :: home_contents");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//Principalからログインユーザの情報を取得
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		return "login/homeLayout";
	}
	
	@GetMapping("/articleup")
	public String getArticleUp(@ModelAttribute ArticleupForm form, Model model) {
		// コンテンツ部分にユーザー詳細を表示するための文字列を登録
		model.addAttribute("contents", "login/articleup :: articleup_contents");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//Principalからログインユーザの情報を取得
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		// articleup.htmlに画面遷移
		return "login/homeLayout";
	}
	
	@PostMapping("/articleup")
	public String postArticleUp(@ModelAttribute @Validated(GroupOrder.class)
			ArticleupForm form, BindingResult bindingResult, Model model){
				
		// 入力チェックに引っかかった場合、投稿画面に戻る
		if(bindingResult.hasErrors()) {
			// GETリクエスト用のメソッドを呼び出して、投稿画面に戻る
			return getArticleUp(form, model);
		}
		// formの中身をコンソールに出して確認します
		System.out.println(form);
		
		// insert用変数
		Article article = new Article();
		
		article.setName(form.getName()); // ユーザー名
		article.setTitle(form.getTitle()); // タイトル
		article.setTheme(form.getTheme()); // テーマ
		article.setOverview(form.getOverview()); // 作品概要
		
		boolean result = articleService.insert(article);
		
		// ユーザー登録結果の判定
		if(result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}
		
		 return getArticleList(model);
	}
	
	
	// 投稿一覧のGETメソッド用処理
	@GetMapping("/articleList")
	public String getArticleList(Model model) {
		// コンテンツ部分に投稿一覧を表示するための文字列を登録
		model.addAttribute("contents", "login/articleList :: articleList_contents");
		
		// 投稿一覧の生成
		List<Article> articleList = articleService.selectMany();
		
		// Modelに作品リストを登録
		model.addAttribute("articleList", articleList);
		
		// データ件数を取得
		int count = articleService.count();
		model.addAttribute("articleListCount", count);
		
		return "login/homeLayout";
	}
	
	@GetMapping("/admin")
	public String getAdmin(Model model) {

		// コンテンツ部分にユーザー詳細を表示するための文字列を登録
		model.addAttribute("contents", "login/admin :: admin_contents");

		int pages = Article.page;
		// 投稿一覧の生成
		List<Article> pageList = articleService.pageNextSelect(pages);
		
		model.addAttribute("pageList", pageList);
		
		pages = Article.page + 1;
		model.addAttribute("pages", pages);
		
		// データ件数を取得
		int count = articleService.count();
		model.addAttribute("articleListCount", count);
		int maxpage = count / 10;
		model.addAttribute("maxpage", maxpage);
		
		// レイアウト用テンプレート
		return "login/homeLayout";
	}
	
	@GetMapping("/admin/{page}")
	public String getAdmin(Model model, @PathVariable("page") int page) {

		// コンテンツ部分にユーザー詳細を表示するための文字列を登録
		model.addAttribute("contents", "login/admin :: admin_contents");

		int pages = page * 10;
		int backpage;
		
		// 投稿一覧の生成
		List<Article> pageList = articleService.pageNextSelect(pages);
		
		model.addAttribute("pageList", pageList);
		
		if(page == 0) {
			pages = page + 1;
			model.addAttribute("pages", pages);
		} else if(page > 0) {
			pages = page + 1;
			backpage = page -1;
			model.addAttribute("pages", pages);
			model.addAttribute("backpage", backpage);
		}
		// データ件数を取得
		int count = articleService.count();
		model.addAttribute("articleListCount", count);
		int maxpage = (int) Math.ceil(count / 10);
		model.addAttribute("maxpage", maxpage);
		if (maxpage == pages) {
			model.addAttribute("pages", maxpage);
		}
		
		// レイアウト用テンプレート
		return "login/homeLayout";
	}	

	/**
	 * ユーザー一覧画面のGETメソッド用処理.
	 */
	@GetMapping("/userList")
	public String getUserList(Model model) {

		// コンテンツ部分にユーザー一覧を表示するための文字列を登録
		model.addAttribute("contents", "login/userList :: userList_contents");

		// ユーザー一覧の生成
		List<User> userList = userService.selectMany();

		// Modelにユーザーリストを登録
		model.addAttribute("userList", userList);

		// データ件数を取得
		int count = userService.count();
		model.addAttribute("userListCount", count);

		return "login/homeLayout";
	}

	/**
	 * ユーザー詳細画面のGETメソッド用処理.
	 */
	@GetMapping("/userDetail/{id:.+}")
	public String getUserDetail(@ModelAttribute SignupForm form, Model model, @PathVariable("id") String userId) {

		// ユーザーID確認（デバッグ）
		System.out.println("userId = " + userId);

		// コンテンツ部分にユーザー詳細を表示するための文字列を登録
		model.addAttribute("contents", "login/userDetail :: userDetail_contents");

		// 結婚ステータス用ラジオボタンの初期化
		radioMarriage = initRadioMarrige();

		// ラジオボタン用のMapをModelに登録
		model.addAttribute("radioMarriage", radioMarriage);

		// ユーザーIDのチェック
		if (userId != null && userId.length() > 0) {

			// ユーザー情報を取得
			User user = userService.selectOne(userId);

			// Userクラスをフォームクラスに変換
			form.setUserId(user.getUserId()); // ユーザーID
			form.setUserName(user.getUserName()); // ユーザー名
			form.setBirthday(user.getBirthday()); // 誕生日
			form.setAge(user.getAge()); // 年齢
			form.setMarriage(user.isMarriage()); // 結婚ステータス

			// Modelに登録
			model.addAttribute("signupForm", form);
		}

		return "login/homeLayout";
	}

	/**
	 * ユーザー更新用処理.
	 */
	@PostMapping(value = "/userDetail", params = "update")
	public String postUserDetailUpdate(@ModelAttribute SignupForm form, Model model) {

		System.out.println("更新ボタンの処理");

		// Userインスタンスの生成
		User user = new User();

		// フォームクラスをUserクラスに変換
		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setBirthday(form.getBirthday());
		user.setAge(form.getAge());
		user.setMarriage(form.isMarriage());

		try {

			// 更新実行
			boolean result = userService.updateOne(user);

			if (result == true) {
				model.addAttribute("result", "更新成功");
			} else {
				model.addAttribute("result", "更新失敗");
			}

		} catch (DataAccessException e) {

			model.addAttribute("result", "更新失敗(トランザクションテスト)");

		}

		// ユーザー一覧画面を表示
		return getUserList(model);
	}

	/**
	 * ユーザー削除用処理.
	 */
	@PostMapping(value = "/userDetail", params = "delete")
	public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model) {

		System.out.println("削除ボタンの処理");

		// 削除実行
		boolean result = userService.deleteOne(form.getUserId());

		if (result == true) {

			model.addAttribute("result", "削除成功");

		} else {

			model.addAttribute("result", "削除失敗");

		}

		// ユーザー一覧画面を表示
		return getUserList(model);
	}
	
	/**
	 * ユーザー詳細画面のGETメソッド用処理.
	 */
	@GetMapping("/articleDetail/{id}")
	public String getArticleDetail(@ModelAttribute ArticleupForm form, Model model, @PathVariable("id") Long id) {

		// ユーザーID確認（デバッグ）
		System.out.println("id = " + id);

		// コンテンツ部分にユーザー詳細を表示するための文字列を登録
		model.addAttribute("contents", "login/articleDetail :: articleDetail_contents");

		// ユーザーIDのチェック
		if (id != 0) {

			// ユーザー情報を取得
			Article article = articleService.selectOne(id);

			// Userクラスをフォームクラスに変換
			form.setId(article.getId());
			form.setName(article.getName()); // ユーザー名
			form.setTitle(article.getTitle()); // タイトル
			form.setTheme(article.getTheme()); // テーマ
			form.setOverview(article.getOverview()); // 概要

			// Modelに登録
			model.addAttribute("articleupForm", form);
		}

		return "login/homeLayout";
	}

	/**
	 * ユーザー更新用処理.
	 */
	@PostMapping(value = "/articleDetail", params = "update")
	public String postArticleDetailUpdate(@ModelAttribute ArticleupForm form, Model model) {

		System.out.println("更新ボタンの処理");

		// Userインスタンスの生成
		Article article = new Article();

		// フォームクラスをUserクラスに変換
		article.setId(form.getId());
		article.setName(form.getName());
		article.setTitle(form.getTitle());
		article.setTheme(form.getTheme());
		article.setOverview(form.getOverview());

		try {

			// 更新実行
			boolean result = articleService.updateOne(article);

			if (result == true) {
				model.addAttribute("result", "更新成功");
			} else {
				model.addAttribute("result", "更新失敗");
			}

		} catch (DataAccessException e) {

			model.addAttribute("result", "更新失敗(トランザクションテスト)");

		}

		// ユーザー一覧画面を表示
		return getArticleList(model);
	}

	/**
	 * ユーザー削除用処理.
	 */
	@PostMapping(value = "/articleDetail", params = "delete")
	public String postArticleDetailDelete(@ModelAttribute ArticleupForm form, Model model) {

		System.out.println("削除ボタンの処理");

		// 削除実行
		boolean result = articleService.deleteOne(form.getOverview(), form.getName());

		if (result == true) {

			model.addAttribute("result", "削除成功");

		} else {

			model.addAttribute("result", "削除失敗");

		}

		// ユーザー一覧画面を表示
		return getArticleList(model);
	}

	/**
	 * ログアウト用処理.
	 */
	@PostMapping("/logout")
	public String postLogout() {

		// ログイン画面にリダイレクト
		return "redirect:/login";
	}

	/**
	 * ユーザー一覧のCSV出力用処理.
	 */
	@GetMapping("/userList/csv")
	public ResponseEntity<byte[]> getUserListCsv(Model model) {

		// ユーザーを全件取得して、CSVをサーバーに保存する
		userService.userCsvOut();

		byte[] bytes = null;

		try {

			// サーバーに保存されているsample.csvファイルをbyteで取得する
			bytes = userService.getFile("sample.csv");

		} catch (IOException e) {
			e.printStackTrace();
		}

		// HTTPヘッダーの設定
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "sample.csv");

		// sample.csvを戻す
		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}

	/**
	 * アドミン権限専用画面のGET用メソッド.
	 * 
	 * @param model Modelクラス
	 * @return 画面のテンプレート名
	 */
}
