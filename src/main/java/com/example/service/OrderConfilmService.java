package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

/**
 * 注文内容表示サービス
 * 
 * @author yoshida_yuuta
 *
 */
@Service
@Transactional
public class OrderConfilmService {

	@Autowired
	private OrderRepository  orderrepository;

	/**
	 * ユーザIDを受け取り、ステータスを１にし
	 * 
	 * @param orderId
	 * @return
	 */
	public Order findByOrderid(Integer orderId) {
		Order order = orderrepository.findByUserIdAndStatus(orderId, 0);
		return order;
	}
}
