package com.example.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.UserInfo;
import com.example.form.OrderForm;
import com.example.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 注文処理を操作するコントローラ.
 * 
 * @author yoshida_yuuta
 * 
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderservice;
	@Autowired
	private OrderConfilmController controller;

	@PostMapping("/orderinfosend")
	public String orderInfoSend(@Validated OrderForm orderform, BindingResult result, Model model,
			@AuthenticationPrincipal LoginUser loginUser, HttpServletRequest request) {
		if (orderform.getOrderDate().equals("")) {
			return controller.orderPost(model, orderform, loginUser);
		}
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dates = LocalDate.parse(orderform.getOrderDate(), formatter);
		LocalDateTime datetime = dates.atStartOfDay();
		if (datetime.isBefore(date)) {
			System.out.println("前の日付");
			FieldError fieldError = new FieldError(result.getObjectName(), "orderDate", "今から3時間後の日時をご入力ください");
			result.addError(fieldError);
		}
		if (datetime.isEqual(date)) {
			System.out.println("同日");
			if (date.getHour() <= (Integer.parseInt(orderform.getOrderTime()) - 3)) {
				System.out.println("時間が前");
				FieldError fieldError = new FieldError(result.getObjectName(), "orderDate", "今から3時間後の日時をご入力ください");
				result.addError(fieldError);
			}

		}
		if (result.hasErrors()) {
			return controller.orderPost(model, orderform, loginUser);
		}
		;
		UserInfo user = loginUser.getUser();
		orderservice.updateOrder(orderform, user);
		return "order_finished";
	}

}
