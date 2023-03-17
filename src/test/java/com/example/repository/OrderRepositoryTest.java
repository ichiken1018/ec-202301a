package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.example.domain.Order;
import com.example.domain.OrderItem;

@SpringBootTest
class OrderRepositoryTest {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private NamedParameterJdbcTemplate template;

	// 注文情報(Ordersテーブル)にテスト情報を登録するメソッド
	public Order registerOrder() throws Exception {
		System.out.println("ordersテーブル情報を初期化する処理開始");
		Order order = new Order();
		Date orderDate = Date.valueOf("2023-3-17");
		Timestamp deliveryTime = Timestamp.valueOf("2023-3-17 10:30:30");
		order.setId(100);
		order.setUserId(100);
		order.setStatus(0);
		order.setTotalPrice(1000);
		order.setOrderDate(orderDate);
		order.setDestinationName("山田太郎");
		order.setDestinationEmail("yamada@sample.com");
		order.setDestinationZipcode("111-1111");
		order.setDestinationAddress("東京都〇〇");
		order.setDestinationTel("080-1234-5678");
		order.setDeliveryTime(deliveryTime);
		order.setPaymentMethod(1);
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		order.setOrderItemList(orderItemList);
		System.out.println(order);
		System.out.println("ordersテーブル情報を初期化する処理終了");
		return order;

	}

	// テストで登録する情報をDBに追加(挿入)するメソッド
	public void Insert(Order order) {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append(
				"INSERT INTO orders(id,user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode,destination_address,destination_tel,delivery_time,payment_method");
		insertSql.append(
				") VALUES(:id,:userId, :status, :totalPrice,:orderDate,:destinationName,:destinationEmail,:destinationZipcode,:destinationAddress,:destinationTel,:deliveryTime,:paymentMethod);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		template.update(insertSql.toString(), param);
	}

	// テスト情報初期化時に受け取ったidでテーブル情報を削除するメソッド
	public void delete(int userId) {
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", userId);
		template.update("delete from orders where user_id = :id", param);
	}
	
	@BeforeEach
	void testSave() throws Exception {
		System.out.println("DB初期化処理開始");
		this.delete(100);
		// テスト用テーブルを作成する
		Order order = this.registerOrder();
		//テスト用テーブルをDBに登録する
		this.Insert(order);
		
		System.out.println("DB初期化処理終了");
	}
	
	@Test
	void ユーザーIDと注文ステータスから存在する注文情報が取得できているか確認するテスト() {
		System.out.println("ユーザーIDとステータスで検索するテスト開始");
		Order order = orderRepository.findByUserIdAndStatus(100, 0);
		assertEquals(100,order.getUserId(),"ユーザーIDが登録されていません");
		assertEquals(0,order.getStatus(),"ステータスが登録されていません");
		assertEquals(1000,order.getTotalPrice(),"合計金額が登録されていません");
		assertEquals("東京都〇〇",order.getDestinationAddress(),"購入者の住所が登録されていません");
		assertEquals(1,order.getPaymentMethod(),"支払い方法が登録されていません");
		
		System.out.println("ユーザーIDとステータスで検索するテスト終了");
	}
	
	@Test
	void 注文者情報を更新できてるか確認するテスト() {
		System.out.println("注文情報更新を確認するテスト開始");
		//現在の注文情報を取得する
		Order oldOrder = orderRepository.findByUserIdAndStatus(100, 0);
		//更新後の注文情報を作成する
		Order order = oldOrder;
		order.setDestinationName("佐藤太郎");
		order.setTotalPrice(3000);
		//注文情報を更新する
		orderRepository.update(order);
		//更新後の注文情報を確認する
		Order updateOrder = orderRepository.findByUserIdAndStatus(100, 0);
		//更新前後の注文情報比較するテスト
		assertEquals("佐藤太郎",oldOrder.getDestinationName(),"購入者名が更新されていません。");
		assertEquals("佐藤太郎",updateOrder.getDestinationName(),"購入者名が更新されていません。");
		assertEquals(3000,updateOrder.getTotalPrice(),"合計金額が更新されていません。");
		
		System.out.println("注文情報更新を確認するテスト終了");
	}
	
	@AfterEach
	//テスト終了後に注文情報を削除する
	void tearDown() throws Exception {
		this.delete(100);
		System.out.println("DBのテストデータを削除しました。");
	}
	
}
