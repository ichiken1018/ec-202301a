package com.example.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 注文者情報フォーム
 * 
 * @author yoshida_yuuta
 *
 */
public class OrderForm {
	/** 注文ID */
	private Integer id;
	/** 注文者名 */
	@NotBlank(message = "名前を入力して下さい")
	private String destinationName;
	@NotBlank(message = "メールアドレスを入力して下さい")
	@Email(message = "メールアドレスの形式が不正です")
	/** 注文者メールアドレス */
	private String destinationEmail;
	/** 注文者郵便番号 */
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "郵便番号はXXX-XXXXの形式で入力してください")
	private String destinationZipcode;
	/** 注文者住所 */
	@NotBlank(message = "住所を入力して下さい")

	private String destinationAddress;
	/** 注文者電話番号 */

	@Pattern(regexp = "^(070|080|090)-\\d{4}-\\d{4}$", message = "電話番号はXXX-XXX-XXXXの形式で入力してください")
	private String destinationTel;
	/** お届け日 */
	@NotBlank(message = "配達日を入力して下さい")
	private String orderDate;
	/** お届け時間 */
	private String orderTime;
	/** 支払い方法 */
	private Integer paymentMethod;
	/** 合計金額 */
	private Integer totalPrice;

	public OrderForm() {
	}

	public OrderForm(Integer id, String destinationName, String destinationEmail, String destinationZipcode,
			String destinationAddress, String destinationTel, String orderDate, String orderTime, Integer paymentMethod,
			Integer totalPrice) {
		super();
		this.id = id;
		this.destinationName = destinationName;
		this.destinationEmail = destinationEmail;
		this.destinationZipcode = destinationZipcode;
		this.destinationAddress = destinationAddress;
		this.destinationTel = destinationTel;
		this.orderDate = orderDate;
		this.orderTime = orderTime;
		this.paymentMethod = paymentMethod;
		this.totalPrice = totalPrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "OrderForm [id=" + id + ", destinationName=" + destinationName + ", destinationEmail=" + destinationEmail
				+ ", destinationZipcode=" + destinationZipcode + ", destinationAddress=" + destinationAddress
				+ ", destinationTel=" + destinationTel + ", orderDate=" + orderDate + ", orderTime=" + orderTime
				+ ", paymentMethod=" + paymentMethod + ", totalPrice=" + totalPrice + "]";
	}

}