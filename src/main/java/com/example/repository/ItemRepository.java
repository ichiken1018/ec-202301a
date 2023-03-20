package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

/**
 * itemsテーブルを操作するリポジトリ.
 * 
 * @author ichiyoshi_kenta
 *
 */
@Repository
public class ItemRepository {
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setImagePath(rs.getString("image_path"));
		item.setDeleted(rs.getBoolean("deleted"));
		return item;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 商品一覧を表示する.
	 * 
	 * @return 全商品一覧
	 */
	public List<Item> findAll() {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY name;";

		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);

		return itemList;
	}
	
	/**
	 * 名前の昇順で商品一覧を表示する.
	 * 
	 * @return 商品一覧
	 * 
	 */
	public List<Item> sortByName() {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY name;";

		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);

		return itemList;
	}
	
	/**
	 * 名前の降順で商品一覧を表示する.
	 * 
	 * @return 商品一覧
	 * 
	 */
	public List<Item> sortByNameDesc() {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY name DESC;";

		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);

		return itemList;
	}
	
	/**
	 * Mサイズの値段の昇順で商品一覧を表示する.
	 * 
	 * @return 商品一覧
	 * 
	 */
	public List<Item> sortByMprice() {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY price_m;";

		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);

		return itemList;
	}
	
	/**
	 * Mサイズの値段の降順で商品一覧を表示する.
	 * 
	 * @return 商品一覧
	 * 
	 */
	public List<Item> sortByMpriceDesc() {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY price_m DESC;";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * Lサイズの値段の昇順で商品一覧を表示する.
	 * 
	 * @return 商品一覧
	 * 
	 */
	public List<Item> sortByLprice() {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY price_l ;";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * Lサイズの値段の降順で商品一覧を表示する.
	 * 
	 * @return 商品一覧
	 * 
	 */
	public List<Item> sortByLpriceDecs() {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY price_l DESC ;";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}


	/**
	 * 商品を曖昧検索する.
	 * 
	 * @param name 商品名
	 * @return 検索された商品
	 */
	public List<Item> findByName(String name) {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items WHERE name ILIKE :name ;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");

		List<Item> itemList = template.query(sql.toString(), param, ITEM_ROW_MAPPER);

		return itemList;
	}

	/**
	 * 主キーから商品を検索する.
	 * 
	 * @param id 商品id
	 * @return 検索された商品情報
	 */
	public Item load(Integer id) {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items WHERE id=:id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);

		return item;
	}

}
