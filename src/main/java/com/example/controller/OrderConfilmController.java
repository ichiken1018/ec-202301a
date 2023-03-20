package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.UserInfo;
import com.example.form.OrderForm;
import com.example.service.OrderConfilmService;

/**
 * 注文内容を表示するコントローラー
 * 
 * @author yoshida_yuuta
 *
 */
@Controller
@RequestMapping("/orderconfilm")
public class OrderConfilmController {

	@Autowired
	private OrderConfilmService orderconfilmservice;

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
	@GetMapping("/vieworder")
	public String orderPost(Model model,OrderForm orderform,@AuthenticationPrincipal LoginUser loginUser) {
		if (loginUser.getUser() == null) {
			return "login";
		} else {
			UserInfo user = loginUser.getUser();
			Integer id = user.getId();
			Order order = orderconfilmservice.findByOrderid(id);
			orderform.setDestinationName(user.getName());
			orderform.setDestinationEmail(user.getEmail());
			orderform.setDestinationZipcode(user.getZipcode());
			orderform.setDestinationAddress(user.getAddress());
			orderform.setDestinationTel(user.getTelephone());
			model.addAttribute("order", order);			
			return "order_confirm";
		}
	}

	
	

}
