package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.form.OrderForm;

@SpringBootTest
class OrderControllerTest {

	@Autowired
	private NamedParameterJdbcTemplate template;

	// Formを作成するメソッド
	public OrderForm formmade(int x, String date, String time) {
		OrderForm form = new OrderForm();
		form.setId(100);
		form.setDestinationName("山田太郎");
		form.setDestinationEmail("yamada@sample.com");
		form.setDestinationZipcode("123-4567");
		form.setDestinationAddress("東京都");
		form.setDestinationTel("080-1111-1111");
		form.setOrderDate(date);
		form.setOrderTime(time);
		form.setPaymentMethod(x);
		form.setTotalPrice(1000);
		return form;
	}

	// DBに入れるorderの例を作成するメソッド
	public Order ordermade() {
		Order order = new Order();
		order.setId(100);
		order.setUserId(100);
		order.setStatus(0);
		order.setTotalPrice(0);
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		order.setOrderItemList(orderItemList);
		return order;
	}

	// 受け取ったOrderでインサートするメソッド
	public void Insert(Order order) {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append(
				"INSERT INTO orders(id,user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode,destination_address,destination_tel,delivery_time,payment_method");
		insertSql.append(
				") VALUES(:id,:userId, :status, :totalPrice,:orderDate,:destinationName,:destinationEmail,:destinationZipcode,:destinationAddress,:destinationTel,:deliveryTime,:paymentMethod);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		template.update(insertSql.toString(), param);
	}

	// 受け取ったidでdeleteを実行するメソッド
	public void delete(int userId) {
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", userId);
		template.update("delete from orders where user_id = :id", param);
	}

	@BeforeEach
	void testSave() throws Exception {
		System.out.println("DB初期化処理開始");
		this.delete(100);
		// orderの例を作成し、返すメソッド
		Order order = this.ordermade();
		this.Insert(order);
		System.out.println("DB初期化処理終了");
	}

	@AfterEach
	void tearDown() throws Exception {
		this.delete(100);
	}

	@DisplayName("注文情報の入力値チェック")
	@Test
	void validationOrderFormTesttr() {
		OrderForm form = new OrderForm();
		try {
			form.setDestinationName("");
		} catch (Exception e) {
			assertTrue(e.toString().contains("名前を入力して下さい"));
		}
		try {
			form.setDestinationEmail("");
		} catch (Exception e) {
			assertTrue(e.toString().contains("メールアドレスを入力して下さい"));
		}
		try {
			form.setDestinationEmail("a");
		} catch (Exception e) {
			assertTrue(e.toString().contains("メールアドレスの形式が不正です"));
		}
		try {
			form.setDestinationZipcode("1111111");
		} catch (Exception e) {
			assertTrue(e.toString().contains("郵便番号はXXX-XXXXの形式で入力してください"));
		}
		try {
			form.setDestinationAddress("");
		} catch (Exception e) {
			assertTrue(e.toString().contains("住所を入力して下さい"));
		}
		try {
			form.setDestinationTel("00000000000");
		} catch (Exception e) {
			assertTrue(e.toString().contains("電話番号はXXX-XXX-XXXXの形式で入力してください"));
		}
		try {
			form.setOrderDate("");
		} catch (Exception e) {
			assertTrue(e.toString().contains("配達日を入力して下さい"));
		}
	}

	@DisplayName("注文情報の入力値チェック")
	@Test
	void validationOrderFormTestfal() {
		try {
			OrderForm form = this.formmade(1, "2100/01/01", "00:00:00");
			System.out.println(form);
		} catch (Exception e) {
			assertNull(e, "購入方法、注文日、注文時間を入力してください");
		}
	}

}
