package com.example.form;

/**
 * 入力された情報を引き継がせるフォームクラス.
 * 
 * @author matsuokatoshiichi
 *
 */
public class LoginUserForm {

	private String email;

	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginUserForm [email=" + email + ", password=" + password + "]";
	}
	
	

}
