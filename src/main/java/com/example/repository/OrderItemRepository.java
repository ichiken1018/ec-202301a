package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;
import com.example.form.ShoppingCartForm;

/**
 * OrderItemsテーブルを操作するリポジトリ.
 * 
 * @author yoshida_yuuta
 *
 */
@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = (rs, i) -> {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(rs.getInt("id"));
		orderItem.setItemId(rs.getInt("item_id"));
		orderItem.setOrderId(rs.getInt("order_id"));
		char size = rs.getString("size").charAt(0);
		orderItem.setSize(size);
		return orderItem;
	};

	/**
	 * 商品詳細画面より入力された値をDBに挿入する.
	 * 
	 * @param shoppingCartForm 商品詳細画面より入力された値
	 * @param orderId 注文ID
	 */
	public void insert(ShoppingCartForm shoppingCartForm, Integer orderId) {
		StringBuilder insertsql = new StringBuilder();
		insertsql.append("INSERT INTO order_items(item_id, order_id, quantity, size)");
		insertsql.append(" VALUES(:itemId, :orderId, :quantity, :size);");

		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", shoppingCartForm.getItemId())
				.addValue("orderId", orderId).addValue("quantity", shoppingCartForm.getQuantity())
				.addValue("size", shoppingCartForm.getSize());
		template.update(insertsql.toString(), param);
	}
	
	public OrderItem load(Integer orderId) {
		String sql = "SELECT id, item_id, order_id, quantity, size FROM order_items WHERE order_id = :orderId ORDER BY id DESC;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);
		List<OrderItem> orderItem = template.query(sql, param, ORDER_ITEM_ROW_MAPPER);
		if(orderItem.size() == 0) {
			return null;
		}
		
		return orderItem.get(0);
	}
	
	/**
	 * 注文商品を削除する.
	 * 
	 * @param orderItemId
	 */
	public void delete(Integer orderItemId) {
		String deleteSql = "DELETE FROM order_items WHERE id = :orderItemId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);
		template.update(deleteSql, param);
	}
}
