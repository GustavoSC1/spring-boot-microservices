package com.gustavo.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavo.productservice.dto.ProductRequest;
import com.gustavo.productservice.repository.ProductRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	
	// Para que Junit 5 entenda que isso é um MongoDB container
	@Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;
	
	static {
        mongoDBContainer.start();
    }
	
	// Define a Url do container nas propriedades do teste
	// Em todo teste será iniciado o mongodb no container e dps será obtido a Url dessa instancia
	@DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
		dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

	 @Test
	void shouldCreateProduct() throws Exception {
		 ProductRequest productRequest = getProductRequest();
		 String productRequestString = objectMapper.writeValueAsString(productRequest);
		 mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(productRequestString))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		
		 Assertions.assertEquals(1, productRepository.findAll().size());
	}
	
	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("iPhone 13")
				.description("iPhone 13")
				.price(BigDecimal.valueOf(1200))
				.build();
	}

}
