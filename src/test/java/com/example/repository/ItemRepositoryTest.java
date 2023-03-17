package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.Item;

@SpringBootTest
class ItemRepositoryTest {
	@Autowired
	private ItemRepository itemRepository;

	@Test
	void 商品を全件検索するテスト() {
		System.out.println("全件検索するテスト開始");
		//メソッド実行
		List<Item> itemList = itemRepository.findAll();
		//テスト
		assertEquals(18, itemList.size(), "商品の数があっていません");
		assertEquals("アンパンマンタウン ようこそ！たのしいパンこうじょうハウス", itemList.get(0).getName(), "商品の並び順があっていません");
		assertEquals("野球盤 3Dエース", itemList.get(17).getName(), "商品の並び順があっていません");

		System.out.println("全件検索するテスト終了");
	}

	@Test
	void 名前の降順で表示するテスト() {
		System.out.println("名前の降順で表示するテスト開始");
		//メソッド実行
		List<Item> itemList = itemRepository.sortByNameDesc();
		//テスト
		assertEquals(18, itemList.size(), "商品の数があっていません");
		assertEquals("野球盤 3Dエース", itemList.get(0).getName(), "商品の並び順があっていません");
		assertEquals("アンパンマンタウン ようこそ！たのしいパンこうじょうハウス", itemList.get(17).getName(), "商品の並び順があっていません");

		System.out.println("名前の降順で表示するテスト終了");
	}

	@Test
	void 曖昧検索を確認するテスト1() {
		System.out.println("曖昧検索を確認するテスト1開始");
		//引数定義、メソッド実行
		String name = "a";
		List<Item> itemList = itemRepository.findByName(name);
		//テスト
		assertEquals(3, itemList.size(), "検索結果が異なります");
		assertEquals("シルバニアファミリーSylvanian Families(赤い屋根の大きなお家)", itemList.get(0).getName(), "検索結果が異なります");

		System.out.println("曖昧検索を確認するテスト1終了");
	}

	@Test
	void 曖昧検索を確認するテスト2() {
		System.out.println("曖昧検索を確認するテスト2開始");
		//引数定義、メソッド実行
		String name = "";
		List<Item> itemList = itemRepository.findByName(name);
		//テスト
		assertEquals(18, itemList.size(), "検索結果が異なります");
		assertEquals("ビニールプール", itemList.get(0).getName(), "検索結果が異なります");
		assertEquals("おはじきシール ゆめかわDX", itemList.get(17).getName(), "検索結果が異なります");

		System.out.println("曖昧検索を確認するテスト2終了");
	}

	@Test
	void 主キー検索を確認するテスト() {
		System.out.println("主キー検索を確認するテスト開始");
		//引数定義、メソッド実行
		Integer id = 1;
		Item item = itemRepository.load(id);
		//テスト
		assertEquals(1, item.getId(), "検索した商品IDが異なります");
		assertEquals("ビニールプール", item.getName(), "検索した商品が異なります");
		assertEquals(1490, item.getPriceM(), "検索した商品のMサイズ料金が異なります");
		assertEquals(2570, item.getPriceL(), "検索した商品のLサイズ料金が異なります");

		System.out.println("主キー検索を確認するテスト終了");

	}
}
