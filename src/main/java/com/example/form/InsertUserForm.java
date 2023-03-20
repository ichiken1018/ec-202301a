package com.example.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 入力されたユーザー情報を引き継がせるフォームクラス.
 * 
 * @author matsuoka_keiichi
 *
 */
public class InsertUserForm {
	/** 名字 */
	@NotBlank(message="名前を入力して下さい")
	private String lastName;
	/** 名前 */
	@NotBlank(message="名前を入力して下さい")
	private String firstName;
	/** メールアドレス */
	@NotBlank(message="メールアドレスを入力して下さい")
	@Email(message="メールアドレスの形式が不正です")
	private String email;
	/** 郵便番号 */
	@Pattern(regexp="^[0-9]{3}-[0-9]{4}$",message="郵便番号はXXX-XXXXの形式で入力してください")
	private String zipcode;
	/** 住所 */
	@NotBlank(message="住所を入力して下さい")
	private String address;
	/**  電話番号 */
	@Pattern(regexp="^(070|080|090)-\\d{4}-\\d{4}$",message="電話番号はXXX-XXXX-XXXXの形式で入力してください")
	private String telephone;
	/** パスワード */
	@Size(min=8,max=16,message="パスワードは8文字以上16文字以内で設定してください")
	private String password;
	/** 確認パスワード */
	@NotBlank(message="確認用パスワードを入力して下さい")
	private String confirmationPassword;
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmationPassword() {
		return confirmationPassword;
	}
	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}
	@Override
	public String toString() {
		return "InsertUserForm [lastName=" + lastName + ", firstName=" + firstName + ", email=" + email + ", zipcode="
				+ zipcode + ", address=" + address + ", telephone=" + telephone + ", password=" + password
				+ ", confirmationPassword=" + confirmationPassword + "]";
	}

	
}
