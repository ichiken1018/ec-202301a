package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.repository.ItemRepository;
import com.example.repository.ToppingRepository;

/**
 * 商品詳細を操作するサービス
 * 
 * @author ichiyoshi_kenta
 *
 */
@Service
@Transactional
public class ShowItemDetailService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * 商品詳細情報を取得する
	 * 
	 * @param itemId　商品ID
	 * @return 検索された商品詳細情報
	 */
	public Item showItemDetail(Integer itemId) {
		Item item = itemRepository.load(itemId);
		List<Topping>toppingList = toppingRepository.findAll();
		item.setToppingList(toppingList);
		return item;
	}
}
