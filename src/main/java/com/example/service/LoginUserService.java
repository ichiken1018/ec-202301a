package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.UserInfo;
import com.example.repository.UserRepository;

/**
 * Userリポジトリを操作するサービスクラス.
 * 
 * @author matsuoka_keiichi
 *
 */
@Service
@Transactional
public class LoginUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * ログインをする為にメールアドレスとパスワードをチェックする.
	 * 
	 * @param email 入力されたメールアドレス
	 * @param password　入力されたパスワード
	 * @return
	 */
	public UserInfo login(String email,String password) {
		
		UserInfo user = userRepository.findByMailAddress(email);
		
		if(passwordEncoder.matches(password,user.getPassword())) {
			return user;
		}
		
		return null;
		
	}
	
	
}
