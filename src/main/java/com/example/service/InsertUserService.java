package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.UserInfo;
import com.example.repository.UserRepository;

/**
 * Userリポジトリを呼び出すサービスクラス.
 * 
 * @author matsuoka_keiichi
 *
 */
@Service
@Transactional

public class InsertUserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * ユーザー情報を登録します.
	 * 
	 * @param user 入力されたuser情報
	 */
	public void insert(UserInfo user) {

		userRepository.insert(user);

	}

	/**
	 * メールアドレスの重複チェックをします.
	 * 
	 * @param mailAddress 入力されたメールアドレス
	 * @return 情報が入ったuserオブジェクトを呼び出し元に返します
	 */
	public UserInfo findByMaillAddress(String email) {

		UserInfo user = userRepository.findByMailAddress(email);

		return user;
	}

}
