package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 商品情報を操作するサービス.
 * 
 * @author ichiyoshi_kenta
 *
 */

@Service
@Transactional
public class ShowItemListService {
	@Autowired
	private ItemRepository repository;

	/**
	 * 商品情報を全件取得する.(商品名が存在すれば曖昧検索、存在しなければ全件検索)
	 * 
	 * @param name 商品名
	 * @return 検索された商品一覧.
	 */
	public List<Item> showItemList(String name) {
		List<Item> itemList = repository.findAll();

		if (name == null) {
			itemList = repository.findAll();
		} else {
			itemList = repository.findByName(name);
		}
		return itemList;
	}
	
	/**
	 * 商品を並び替える
	 * 
	 * @param sort　並び替え条件
	 * @return　並び替え結果
	 */
	public List<Item> sortItem(Integer sort) {
		List<Item> sortList = null;
		if (sort == 1) {
			sortList = repository.sortByName();
		} else if (sort == 2) {
			sortList = repository.sortByNameDesc();
		} else if (sort == 3) {
			sortList = repository.sortByMprice();
		} else if (sort == 4) {
			sortList = repository.sortByMpriceDesc();
		} else if (sort == 5) {
			sortList = repository.sortByLprice();
		} else if (sort == 6) {
			sortList = repository.sortByLpriceDecs();
		} else if (sort == 0) {
			sortList = repository.findAll();
		} else {
			sortList = repository.findAll();
		}

		return sortList;
	}


}
