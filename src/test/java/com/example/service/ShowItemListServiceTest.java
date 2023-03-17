package com.example.service;



import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.repository.ItemRepository;
@SpringBootTest
class ShowItemListServiceTest {
	@Mock
	private ItemRepository itemRepository;
	@InjectMocks
	private ShowItemListService showItemListService;

	@Test
	void 商品を全件取得するテスト() {
		System.out.println("全件表示するテスト開始");
		
		showItemListService.showItemList("");
		Mockito.verify(itemRepository).findAll();
		
		System.out.println("全件表示するテスト終了");
		
	}
	
	@Test
	void 商品を曖昧検索するテスト() {
	System.out.println("曖昧検索するテスト開始");
		
		showItemListService.showItemList("name");
		Mockito.verify(itemRepository).findByName("name");
		
		System.out.println("曖昧検索するテスト終了");
	}
	
	@Test
	void 商品の並び替えるテスト() {
		System.out.println("商品を並び変えるテスト開始");
		showItemListService.sortItem(3);
		Mockito.verify(itemRepository).sortByMprice();
		System.out.println("商品を並び変えるテスト終了");
		
	}

}
