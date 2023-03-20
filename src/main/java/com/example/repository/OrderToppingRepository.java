package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderTopping;
import com.example.form.ShoppingCartForm;

/**
 * order_toppingsテーブルを操作するリポジトリ
 * 
 * @author seiji_kitahara
 *
 */
@Repository
public class OrderToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<OrderTopping> ORDER_TOPPING_ROW_MAPPER = (rs, i) -> {
		OrderTopping orderTopping = new OrderTopping();
		orderTopping.setId(rs.getInt("id"));
		orderTopping.setToppingId(rs.getInt("topping_id"));
		orderTopping.setOrderItemId(rs.getInt("order_item_id"));
		return orderTopping;
	};
	
	/**
	 * 注文トッピング情報を挿入する.
	 * 
	 * @param shoppingCartForm 商品詳細画面の入力値
	 * @param orderItemId　トッピング情報と紐づく商品ID
	 */
	public void insert(ShoppingCartForm shoppingCartForm, Integer orderItemId) {
		String insertSql = "INSERT INTO order_toppings(topping_id, order_item_id) VALUES(:toppingId, :orderItemId);";
		for (Integer toppingId : shoppingCartForm.getToppingIdList()) {
			SqlParameterSource param = new MapSqlParameterSource().addValue("toppingId", toppingId)
					.addValue("orderItemId", orderItemId);
			template.update(insertSql, param);
		}
	}
	
	public List<OrderTopping> load(Integer orderItemId){
		String sql = "SELECT id, topping_id, order_item_id FROM order_toppings WHERE order_item_id = :orderItemId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("order_item_id", orderItemId);
		List<OrderTopping> orderToppingList = template.query(sql, param, ORDER_TOPPING_ROW_MAPPER);
		return orderToppingList;
	}
}
