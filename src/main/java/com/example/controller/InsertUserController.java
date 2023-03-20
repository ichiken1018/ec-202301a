package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.UserInfo;
import com.example.form.InsertUserForm;
import com.example.service.InsertUserService;

/**
 * ユーザー登録を行うコントローラー.
 * 
 * @author matsuoka_keiichi
 *
 */
@Controller
@RequestMapping("/insert-user")
public class InsertUserController {

	@Autowired
	private InsertUserService insertUserService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("")
	public String index(InsertUserForm form, Model model) {

		return "register_admin";
	}

	/**
	 * 入力された情報を受け取りユーザー登録をします.
	 * 
	 * @param form   入力されたユーザー情報
	 * @param mlodel リクエストスコープを使う為の変数
	 * @return ログイン画面にリダイレクトします
	 */
	@PostMapping("/insert")
	public String insert(@Validated InsertUserForm form, BindingResult result, RedirectAttributes redirectAttributes,
			Model model) {

		/** パスワードと確認用パスワードの入力値チェックをします. */
		if (!(form.getPassword().equals(form.getConfirmationPassword()))) {
			FieldError fieldError = new FieldError(result.getObjectName(), "confirmationPassword",
					"パスワードと確認用パスワードが不一致です");
			result.addError(fieldError);
		}

		/** メールアドレスの重複チェックをします. */
		UserInfo userMailAddress = insertUserService.findByMaillAddress(form.getEmail());

		if (!(userMailAddress == null)) {
			FieldError fieldError = new FieldError(result.getObjectName(), "email", "そのメールアドレスは既に使われています");
			result.addError(fieldError);
		}

		/** エラーが発生したら登録画面にリターンします. */
		if (result.hasErrors()) {
			return index(form, model);
		}

		UserInfo user = new UserInfo();
		BeanUtils.copyProperties(form, user);
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setName(form.getLastName() + form.getFirstName());
		insertUserService.insert(user);

		return "redirect:/login-user";
	}

}
