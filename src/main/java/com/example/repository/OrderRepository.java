package com.example.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;

/**
 * ordersテーブルを操作するリポジトリ.
 * 
 * @author seiji_kitahara
 *
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final ResultSetExtractor<Order> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {

		Order order = null;
		List<OrderItem> orderItemList = new LinkedList<OrderItem>();
		List<OrderTopping> orderToppingList = null;
		List<Topping> toppingList = null;

		// 前の注文IDを退避しておくための変数
		long beforeOrderId = 0;
		// 前の注文商品IDを退避しておくための変数
		long beforeOrderItemId = 0;

		// データが無くなるまで参照
		while (rs.next()) {

			int nowOrderId = rs.getInt("ord_id");
			int nowOrderItemId = rs.getInt("order_items_id");

			if (nowOrderId != beforeOrderId) {
				order = new Order();
				order.setId(nowOrderId);
				order.setUserId(rs.getInt("ord_user_id"));
				order.setStatus(rs.getInt("ord_status"));
				order.setTotalPrice(rs.getInt("ord_total_price"));
				order.setOrderDate(rs.getDate("ord_order_date"));
				order.setDestinationName(rs.getString("ord_destination_name"));
				order.setDestinationEmail(rs.getString("ord_destination_email"));
				order.setDestinationZipcode(rs.getString("ord_destination_zipcode"));
				order.setDestinationAddress(rs.getString("ord_destination_address"));
				order.setDestinationTel(rs.getString("ord_destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("ord_delivery_time"));
				order.setPaymentMethod(rs.getInt("ord_payment_method"));
				// 空の注文商品リストを作成
				orderItemList = new ArrayList<OrderItem>();
				order.setOrderItemList(orderItemList);
			}

			// 前の注文商品IDと比較して、同じでなければ注文商品をインスタンス化
			if (nowOrderItemId != beforeOrderItemId) {

				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("order_items_id"));
				orderItem.setItemId(rs.getInt("order_items_item_id"));
				orderItem.setOrderId(rs.getInt("order_items_order_id"));
				orderItem.setQuantity(rs.getInt("order_items_quantity"));
				char size = rs.getString("order_items_size").charAt(0);
				orderItem.setSize(size);

				// OrderItemドメインに格納するItemを生成
				Item item = new Item();
				item.setId(rs.getInt("item_id"));
				item.setName(rs.getString("item_name"));
				item.setDescription(rs.getString("item_description"));
				item.setPriceM(rs.getInt("item_price_m"));
				item.setPriceL(rs.getInt("item_price_l"));
				item.setImagePath(rs.getString("item_image_path"));
				item.setDeleted(rs.getBoolean("item_deleted"));
//				item.setToppingList(toppingList);

				orderItem.setItem(item);

				// 空の注文トッピングリストを作成
				orderToppingList = new ArrayList<>();
				orderItem.setOrderToppingList(orderToppingList);
				orderItemList.add(orderItem);

				order.setOrderItemList(orderItemList);
			}

			// 注文トッピング情報があれば、注文トッピングをインスタンス化
			if (rs.getInt("order_topping_id") != 0) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setId(rs.getInt("order_topping_id"));
				orderTopping.setToppingId(rs.getInt("order_topping_topping_id"));
				orderTopping.setOrderItemId(rs.getInt("order_topping_order_item_id"));

				// OrderToppingドメインに格納するTopping
				Topping topping = new Topping();
				topping.setId(rs.getInt("topping_id"));
				topping.setName(rs.getString("topping_name"));
				topping.setPriceM(rs.getInt("topping_price_m"));
				topping.setPriceL(rs.getInt("topping_price_l"));
				toppingList = new ArrayList<Topping>();
				toppingList.add(topping);

				orderTopping.setTopping(topping);
				orderToppingList.add(orderTopping);
			}

			// 現在の注文IDと注文商品IDを退避させる
			beforeOrderId = nowOrderId;
			beforeOrderItemId = nowOrderItemId;
		}
		return order;
	};

	private static final ResultSetExtractor<List<Order>> ORDERLIST_RESULT_SET_EXTRACTOR = (rs) -> {

		Order order = new Order();
		List<OrderItem> orderItemList = new LinkedList<OrderItem>();
		List<OrderTopping> orderToppingList = null;
		List<Topping> toppingList = null;
		List<Order> orderList = new ArrayList<Order>();

		// 前の注文IDを退避しておくための変数
		long beforeOrderId = 0;
		// 前の注文商品IDを退避しておくための変数
		long beforeOrderItemId = 0;
		// 前の注文情報を対比しておくための変数

		while (rs.next()) {

			int nowOrderId = rs.getInt("ord_id");
			int nowOrderItemId = rs.getInt("order_items_id");

			order.setOrderItemList(orderItemList);
			if (nowOrderId != beforeOrderId) {

				order = new Order();
				order.setId(nowOrderId);
				order.setUserId(rs.getInt("ord_user_id"));
				order.setStatus(rs.getInt("ord_status"));
				order.setTotalPrice(rs.getInt("ord_total_price"));
				order.setOrderDate(rs.getDate("ord_order_date"));
				order.setDestinationName(rs.getString("ord_destination_name"));
				order.setDestinationEmail(rs.getString("ord_destination_email"));
				order.setDestinationZipcode(rs.getString("ord_destination_zipcode"));
				order.setDestinationAddress(rs.getString("ord_destination_address"));
				order.setDestinationTel(rs.getString("ord_destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("ord_delivery_time"));
				order.setPaymentMethod(rs.getInt("ord_payment_method"));
				// 空の注文商品リストを作成
				orderItemList = new ArrayList<OrderItem>();
				order.setOrderItemList(orderItemList);

			}

			if (nowOrderItemId != beforeOrderItemId) {

				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("order_items_id"));
				orderItem.setItemId(rs.getInt("order_items_item_id"));
				orderItem.setOrderId(rs.getInt("order_items_order_id"));
				orderItem.setQuantity(rs.getInt("order_items_quantity"));
				char size = rs.getString("order_items_size").charAt(0);
				orderItem.setSize(size);

				// OrderItemドメインに格納するItemを生成
				Item item = new Item();
				item.setId(rs.getInt("item_id"));
				item.setName(rs.getString("item_name"));
				item.setDescription(rs.getString("item_description"));
				item.setPriceM(rs.getInt("item_price_m"));
				item.setPriceL(rs.getInt("item_price_l"));
				item.setImagePath(rs.getString("item_image_path"));
				item.setDeleted(rs.getBoolean("item_deleted"));
//				item.setToppingList(toppingList);

				orderItem.setItem(item);

				// 空の注文トッピングリストを作成
				orderToppingList = new ArrayList<>();
				orderItem.setOrderToppingList(orderToppingList);
				orderItemList.add(orderItem);

				order.setOrderItemList(orderItemList);
			}

			if (rs.getInt("order_topping_id") != 0) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setId(rs.getInt("order_topping_id"));
				orderTopping.setToppingId(rs.getInt("order_topping_topping_id"));
				orderTopping.setOrderItemId(rs.getInt("order_topping_order_item_id"));

				// OrderToppingドメインに格納するTopping
				Topping topping = new Topping();
				topping.setId(rs.getInt("topping_id"));
				topping.setName(rs.getString("topping_name"));
				topping.setPriceM(rs.getInt("topping_price_m"));
				topping.setPriceL(rs.getInt("topping_price_l"));
				toppingList = new ArrayList<Topping>();
				toppingList.add(topping);

				orderTopping.setTopping(topping);
				orderToppingList.add(orderTopping);
			}
			if (nowOrderId != beforeOrderId) {
				orderList.add(order);
			}
			// 現在の注文IDと注文商品IDを退避させる
			beforeOrderId = nowOrderId;
			beforeOrderItemId = nowOrderItemId;

		}
		return orderList;
	};

	/**
	 * ユーザーIDとステータス状態から存在する注文情報を取得する.
	 * 
	 * @param ユーザーID
	 * @param 注文ステータス
	 * @return ユーザーIDが存在しているかつ、ステータスが注文前（0）状態だった場合は該当データを返す 該当データが無い場合はNullを返す
	 */
	public Order findByUserIdAndStatus(Integer userId, Integer status) {
		String sql = "SELECT ord.id AS ord_id, ord.user_id AS ord_user_id, ord.status AS ord_status, ord.total_price AS ord_total_price, ord.order_date AS ord_order_date, ord.destination_name AS ord_destination_name, ord.destination_email AS ord_destination_email, ord.destination_zipcode AS ord_destination_zipcode, ord.destination_address AS ord_destination_address, ord.destination_tel AS ord_destination_tel, ord.delivery_time AS ord_delivery_time, ord.payment_method AS ord_payment_method, oi.id AS order_items_id, oi.item_id AS order_items_item_id, oi.order_id AS order_items_order_id, oi.quantity AS order_items_quantity, oi.size AS order_items_size, i.id AS item_id, i.name AS item_name, i.description AS item_description, i.price_m AS item_price_m, i.price_l AS item_price_l, i.image_path AS item_image_path, i.deleted AS item_deleted, ot.id AS order_topping_id, ot.topping_id AS order_topping_topping_id, ot.order_item_id AS order_topping_order_item_id, t.id AS topping_id, t.name AS topping_name, t.price_m AS topping_price_m, t.price_l AS topping_price_l  FROM orders AS ord LEFT JOIN order_items AS oi ON ord.id = oi.order_id LEFT JOIN items AS i ON oi.item_id = i.id LEFT JOIN order_toppings AS ot ON oi.id = ot.order_item_id LEFT JOIN toppings AS t ON ot.topping_id = t.id WHERE ord.user_id = :userId AND ord.status = :status;";
//		String sql="SELECT id, user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, destination_address, destination_tel, delivery_time, payment_method FROM orders WHERE user_id = :userId AND status = :status;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);

		Order order = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);

		return order;
	}

	/**
	 * 主キー検索をする.
	 * 
	 * @param ユーザーID
	 * @return 一致した注文情報を返す
	 */
	public Order load(Integer userId, Integer status) {
		StringBuilder sql = new StringBuilder();
		// Order分
		sql.append(
				"SELECT ord.id AS ord_id, ord.user_id AS ord_user_id, ord.status AS ord_status, ord.total_price AS ord_total_price,");
		sql.append(
				"ord.order_date AS ord_order_date, ord.destination_name AS ord_destination_name, ord.destination_email AS ord_destination_email,");
		sql.append(
				"ord.destination_zipcode AS ord_destination_zipcode, ord.destination_address AS ord_destination_address,");
		sql.append(
				"ord.destination_tel AS ord_destination_tel, ord.delivery_time AS ord_delivery_time, ord.payment_method AS ord_payment_method,");
		// OrderItem分
		sql.append("oi.id AS order_items_id, oi.item_id AS order_items_item_id, oi.order_id AS order_items_order_id,");
		sql.append("oi.quantity AS order_items_quantity, oi.size AS order_items_size,");
		// Item分
		sql.append(
				"i.id AS item_id, i.name AS item_name, i.description AS item_description,i.price_m AS item_price_m,");
		sql.append("i.price_l AS item_price_l, i.image_path AS item_image_path, i.deleted AS item_deleted,");
		// OrderTopping分
		sql.append(
				"ot.id AS order_topping_id, ot.topping_id AS order_topping_topping_id, ot.order_item_id AS order_topping_order_item_id,");
		// Topping分
		sql.append(
				"t.id AS topping_id, t.name AS topping_name, t.price_m AS topping_price_m, t.price_l AS topping_price_l");
		// 結合部分
		sql.append(" FROM orders AS ord LEFT JOIN order_items AS oi ON ord.id = oi.order_id");
		sql.append(" LEFT JOIN items AS i ON oi.item_id = i.id LEFT JOIN order_toppings AS ot ON ");
		sql.append(
				"oi.id = ot.order_item_id LEFT JOIN toppings AS t ON ot.topping_id = t.id WHERE ord.user_id = :userId AND ord.status = :status ORDER BY oi.id;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		Order order = template.query(sql.toString(), param, ORDER_RESULT_SET_EXTRACTOR);
		return order;
	}

	/**
	 * 注文情報を挿入する.
	 * 
	 * @param DBへ登録する注文情報
	 */
	public void insert(Integer userId, Integer status) {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("INSERT INTO orders(user_id, status, total_price");
		insertSql.append(") VALUES(:userId, :status, 0);");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		template.update(insertSql.toString(), param);
	}

	/**
	 * 注文者情報更新
	 * 
	 * @param order 更新情報
	 */
	public void update(Order order) {
		String sql = "UPDATE orders SET user_id=:userId, status=:status, total_price=:totalPrice, order_date=:orderDate, destination_name=:destinationName, destination_email=:destinationEmail, destination_zipcode=:destinationZipcode,destination_address=:destinationAddress,destination_tel=:destinationTel,delivery_time=:deliveryTime,payment_method=:paymentMethod WHERE id=:id;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		template.update(sql, param);
	}

	/**
	 * 過去注文情報取得
	 * 
	 * @param userId ユーザID
	 * @param status 注文状況
	 * @return 過去注文情報
	 */
	public List<Order> historyFindByUserIdAndStatus(Integer userId, Integer status) {
		String sql = "SELECT ord.id AS ord_id, ord.user_id AS ord_user_id, ord.status AS ord_status, ord.total_price AS ord_total_price, ord.order_date AS ord_order_date, ord.destination_name AS ord_destination_name, ord.destination_email AS ord_destination_email, ord.destination_zipcode AS ord_destination_zipcode, ord.destination_address AS ord_destination_address, ord.destination_tel AS ord_destination_tel, ord.delivery_time AS ord_delivery_time, ord.payment_method AS ord_payment_method, oi.id AS order_items_id, oi.item_id AS order_items_item_id, oi.order_id AS order_items_order_id, oi.quantity AS order_items_quantity, oi.size AS order_items_size, i.id AS item_id, i.name AS item_name, i.description AS item_description, i.price_m AS item_price_m, i.price_l AS item_price_l, i.image_path AS item_image_path, i.deleted AS item_deleted, ot.id AS order_topping_id, ot.topping_id AS order_topping_topping_id, ot.order_item_id AS order_topping_order_item_id, t.id AS topping_id, t.name AS topping_name, t.price_m AS topping_price_m, t.price_l AS topping_price_l  FROM orders AS ord LEFT JOIN order_items AS oi ON ord.id = oi.order_id LEFT JOIN items AS i ON oi.item_id = i.id LEFT JOIN order_toppings AS ot ON oi.id = ot.order_item_id LEFT JOIN toppings AS t ON ot.topping_id = t.id WHERE ord.user_id = :userId AND ord.status >= :status;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		List<Order> orderList = template.query(sql, param, ORDERLIST_RESULT_SET_EXTRACTOR);
		return orderList;

	}

}
