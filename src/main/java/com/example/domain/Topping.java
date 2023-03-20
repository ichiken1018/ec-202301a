package com.example.domain;

/**
 * Topping情報を表すドメインクラス.
 * 
 * @author ichiyoshi_kenta
 *
 */
public class Topping {

	/** トッピングの識別ID */
	private Integer id;
	/** トッピング名 */
	private String name;
	/** Mサイズの値段 */
	private Integer priceM;
	/** Lサイズの値段 */
	private Integer priceL;

	public Topping() {
	}

	public Topping(Integer id, String name, Integer priceM, Integer priceL) {
		super();
		this.id = id;
		this.name = name;
		this.priceM = priceM;
		this.priceL = priceL;
	}

	@Override
	public String toString() {
		return "Topping [id=" + id + ", name=" + name + ", priceM=" + priceM + ", priceL=" + priceL + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPriceM() {
		return priceM;
	}

	public void setPriceM(Integer priceM) {
		this.priceM = priceM;
	}

	public Integer getPriceL() {
		return priceL;
	}

	public void setPriceL(Integer priceL) {
		this.priceL = priceL;
	}

}
