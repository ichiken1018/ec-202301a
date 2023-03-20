package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.form.InsertUserForm;

@SpringBootTest
class InsertUserControllerTest {
	// MockMvc サーバーやDBに接続せずにSpringMVCを動作できる
	private MockMvc mockMvc;

	@Autowired
	InsertUserController insertUserController;
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	//登録したものを削除するメソッド
	public void delete(String email) {
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		template.update("delete from users where email = :email", param);
	}
	
	
	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(insertUserController).build();
		//登録情報初期化
		this.delete("yamada1@sample.com");
	}
	
	
	@AfterEach
	void tearDown() throws Exception {
		//登録情報初期化
		this.delete("yamada1@sample.com");
	} 

	@Test
	@DisplayName("会員登録画面に遷移するか確認するテスト")
	void indexTest() throws Exception {
		System.out.println("画面遷移を確認するテスト開始");
		mockMvc.perform(get("/insert-user")).andExpect(status().isOk()).andExpect(view().name("register_admin"));
		System.out.println("画面遷移を確認するテスト終了");
	}

	@Test
	@DisplayName("会員登録処理を確認するテスト")
	public void test() throws Exception {
		System.out.println("会員登録の入力値チェック開始");

		InsertUserForm form = new InsertUserForm();
		form.setLastName("山田");
		form.setFirstName("太郎");
		form.setEmail("yamada1@sample.com");
		form.setZipcode("111-1111");
		form.setAddress("東京都");
		form.setTelephone("080-1234-5678");
		form.setPassword("12345678");
		form.setConfirmationPassword("12345678");

		mockMvc.perform(post("/insert-user/insert").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("lastName", form.getLastName()).param("firstName", form.getFirstName())
				.param("email", form.getEmail()).param("zipcode", form.getZipcode()).param("address", form.getAddress())
				.param("telephone", form.getTelephone()).param("password", form.getPassword())
				.param("confirmationPassword", form.getConfirmationPassword()))// フォームの入力情報
				.andExpect(model().hasNoErrors()) // エラーがないか？
				.andExpect(status().is3xxRedirection()) // ステータスコードのチェック
				.andExpect(redirectedUrl("/login-user")); // リダイレクト先のURLをチェック

		System.out.println("会員登録の入力値チェック終了");
	}

}
