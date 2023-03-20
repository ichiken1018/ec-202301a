package com.example.form;

/**
 * 検索、並び替え後に表示を残すためのフォームクラス
 * 
 * @author Ichiyoshi_kenta
 *
 */
public class InputTextForm {

	/** 検索入力文字 */
	private String name;

	/** 並び替え条件の値 */
	private Integer sort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "InputTextForm [name=" + name + ", sort=" + sort + "]";
	}

}
