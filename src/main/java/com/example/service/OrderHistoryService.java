package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

/**
 * 履歴情報を表示するサービス
 * @author yoshida_yuuta
 *
 */
@Service
@Transactional
public class OrderHistoryService {
	@Autowired
	private OrderRepository orderrepository;
	
public List<Order> orderHistoryView(Integer id) {
	List<Order> orderList=orderrepository.historyFindByUserIdAndStatus(id, 1);
	System.out.println(orderList);
	return orderList;
}

}
