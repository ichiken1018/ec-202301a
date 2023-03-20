package com.example.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * ログイン認証のドメイン.
 * 
 * @author matsuoka_keiichi
 *
 */
public class LoginUser extends User{

	private static final long serialVersionUID = 1L;
	
	private final UserInfo user;
	
	
	public LoginUser(UserInfo user, Collection<GrantedAuthority> authorityList) {
		super(user.getEmail(), user.getPassword(), authorityList);
		this.user=user;
		
	}
	
	/**
	 * ユーザー情報を返します.
	 * 
	 * @return
	 */
	public UserInfo getUser() {
		return user;
	}
	
}
