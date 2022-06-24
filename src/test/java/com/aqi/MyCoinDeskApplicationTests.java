package com.aqi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest
class MyCoinDeskApplicationTests {

//	@Test
//	void contextLoads() {
//	}
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testGetMyCoinDesk() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/v1/coin"));
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
		resultActions.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].code").value("USD"))
		.andExpect(jsonPath("$[0].name").value("美元"))
		.andExpect(jsonPath("$[0].rate_float").hasJsonPath())
		.andExpect(jsonPath("$[0].updatetime").hasJsonPath())
		.andDo(print());
		String result = resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("Test Result: " + result);
	}
	
	@Test
	public void contextLoads() throws Exception {
		String uri = "/v1/coin";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		System.out.println(status);
		Assertions.assertEquals(status,200,"錯誤");
	}
	
	@Test
	public void getCoindeskAPI() {
		try {
			String path = "http://localhost:8080/v1/coin";
			HttpResponse<com.mashape.unirest.http.JsonNode> response = Unirest.get(path).asJson();
			System.out.printf("Test 取得 Coindesk API 回應：%s 回傳資料：%s \n",response.getStatus(), response.getBody());
			System.out.println();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

}
