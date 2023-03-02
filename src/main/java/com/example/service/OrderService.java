package com.example.service;


import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
import com.example.form.OrderForm;
import com.example.repository.OrderRepository;

/**
 * 購入確認フォームをDBに入れるサービスクラス
 * @author yoshidayuuta
 *
 */
@Service

public class OrderService {
	@Autowired
	private OrderRepository orderrepository;

	/**
	 * フォームの入力値をDBに反映するメソッド
	 * 
	 * @param form 購入者情報
	 */
	public void updateOrder(OrderForm form) {
		Order order = orderrepository.findByUserIdAndStatus(form.getUserId(), 0);
		if (form.getPaymentMethod() == 1) {
			order.setPaymentMethod(1);
		} else if (form.getPaymentMethod() == 2) {
			order.setPaymentMethod(2);
		}
		order.setTotalPrice(form.getTotalPrice());
		LocalDate date = LocalDate.now();
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
		order.setOrderDate(sqlDate);
		order.setDestinationName(form.getDestinationName());
		order.setDestinationEmail(form.getDestinationEmail());
		order.setDestinationZipcode(form.getDestinationZipcode());
		order.setDestinationAddress(form.getDestinationAddress());
		order.setDestinationTel(form.getDestinationTel());
		try {
			Date deliveryTime = (Date) new SimpleDateFormat("yyyy-MM-dd-hh時").parse(form.getOrderDate() + "-" + form.getOrderTime());
			order.setDeliveryTime(new Timestamp(deliveryTime.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		orderrepository.update(order);
	}
}