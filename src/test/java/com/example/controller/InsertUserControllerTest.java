package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.form.InsertUserForm;

@SpringBootTest
class InsertUserControllerTest {
	// MockMvc サーバーやDBに接続せずにSpringMVCを動作できる
	private MockMvc mockMvc;
	@Autowired
	InsertUserController insertUserController;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(insertUserController).build();
	}

	@AfterEach
	void tearDown() throws Exception {
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
	void formTest1() throws Exception {
		System.out.println("会員登録を確認するテスト開始");
		InsertUserForm form = new InsertUserForm();
		form.setLastName("山田");
		form.setFirstName("太郎");
		form.setEmail("yamada@sample.com");
		form.setZipcode("111-1111");
		form.setAddress("東京都");
		form.setTelephone("080-1234-5678");
		form.setPassword("aaaaaaaa");
		form.setConfirmationPassword("aaaaaaaa");

		MvcResult result = mockMvc.perform(post("/insert-user/insert"))
							.andExpect(status().isOk())
							.andExpect(view().name("form")).andReturn();

		InsertUserForm resultForm = (InsertUserForm) result.getModelAndView().getModel().get("form");
		assertEquals(resultForm.getLastName(), "山田", "名字を入力してください");

		System.out.println("会員登録を確認するテスト終了");
	}

}
