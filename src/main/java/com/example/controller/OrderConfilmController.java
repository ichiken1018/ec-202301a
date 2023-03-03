package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.UserInfo;
import com.example.form.OrderForm;
import com.example.service.OrderConfilmService;

import jakarta.servlet.http.HttpSession;

/**
 * 注文内容を表示するコントローラー
 * 
 * @author yoshidayuuta
 *
 */
@Controller
@RequestMapping("/orderconfilm")
public class OrderConfilmController {

	@Autowired
	private OrderConfilmService orderconfilmservice;
	
	@Autowired
	private HttpSession session;

	/**
	 * ユーザーIDからカートの商品情報を受け取るメソッド
	 * 
	 * @param userId ログインユーザーID
	 * @return 注文内容確認画面
	 */
	
	@GetMapping("")
	public String index() {
		return"cart_list";
	}
	/**
	 * ログインしているユーザー情報を受け取り、詳細確認画面を表示させる。
	 * @param userId　　ユーザーID
	 * @param model モデル
	 * @return　詳細確認画面
	 */
	@PostMapping("/vieworder")
	public String orderPost(Model model,OrderForm orderform) {
		if (session.getAttribute("User") == null) {
			return "login";
		} else {
			UserInfo user=(UserInfo) session.getAttribute("User");
			Integer id = user.getId();
			Order order = orderconfilmservice.findByOrderid(id);
			System.out.println("a");
			System.out.println(order);
			model.addAttribute("order", order);
			return "order_confirm";
		}
	}

		
	

}
