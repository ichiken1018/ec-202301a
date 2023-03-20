package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.UserInfo;
import com.example.service.OrderHistoryService;

/**
 * 注文履歴を操作するコントローラ.
 * 
 * @author yoshida_yuuta
 *
 */
@Controller
@RequestMapping("/orederhistory")
public class OrderHistoryViewContoroller {
@Autowired
private OrderHistoryService orderhistoryservice;
	
/**
 * ユーザーの注文履歴所法を表示
 * @param model　モデル
 * @param order　注文情報
 * @param loginUser　ログイン情報
 * @return　注文履歴画面
 */
@GetMapping("/view")
public String view(Model model,Order order,@AuthenticationPrincipal LoginUser loginUser) {
	UserInfo User = loginUser.getUser();
	List<Order> orderList= orderhistoryservice.orderHistoryView(User.getId());
	model.addAttribute("orderList",orderList);
	return"order_history";
}
}
