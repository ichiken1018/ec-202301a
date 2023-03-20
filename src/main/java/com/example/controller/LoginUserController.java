package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * ログイン/ログアウトをするコントローラー.
 * 
 * @author matsuoka_keiichi
 *
 */

@Controller
@RequestMapping("/login-user")
public class LoginUserController {

	/**
	 * ログイン画面を表示させます.
	 * 
	 * @param form  入力されたログイン情報
	 * @param model ログイン情報をリクエストスコープに格納する変数
	 * @return ログイン画面に戻します.
	 */
	@GetMapping("")
	public String login(Model model, @RequestParam(required = false) String error) {
		
		
		if (error != null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
		}
		
		return "login";

	}

}
