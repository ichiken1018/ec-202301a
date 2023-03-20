package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.form.InputTextForm;
import com.example.repository.ItemRepository;
import com.example.service.ShowItemListService;

/**
 * 商品情報を操作するコントローラ.
 * 
 * @author ichiyoshi_kenta
 *
 */
@Controller
@RequestMapping("/")
public class ShowItemListController {
	@Autowired
	ShowItemListService service;
	@Autowired
	ItemRepository repository;

	/**
	 * 商品一覧を表示する
	 * 
	 * @param model モデル
	 * @param form  入力された検索商品名を受け取るフォーム
	 * @return 商品一覧画面
	 */
	@GetMapping("")
	public String showItemList(Model model, InputTextForm form) {

		List<Item> itemList = service.showItemList(form.getName());
		if (itemList.size() == 0) {
			model.addAttribute("errorMessage", "該当する商品はありません。");
			itemList = service.showItemList(null);
		}
		model.addAttribute("itemList", itemList);

		return "item_list";
	}

	/**
	 * 商品一覧を並び替える
	 * 
	 * @param model モデル
	 * @param form  並び替え順の値を受け取るフォーム
	 * @return 商品並び替え結果画面
	 */
	@GetMapping("/sort")
	public String sortName(Model model, InputTextForm form) {
		List<Item> sortList = service.sortItem(form.getSort());
		model.addAttribute(sortList);
		return "item_list";
	}

}
