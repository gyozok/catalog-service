package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void whenPostRequest_ThenBookCreated() {
		//GIVEN
		var expectedBook = new Book("1231231231", "Title", "Author", 9.90);

		//WHEN
		webTestClient
				.post()
				.uri("/books")
				.bodyValue(expectedBook)
				.exchange()
				//THEN
				.expectStatus().isCreated()
				.expectBody(Book.class).value(actualBook -> {
					assertThat(actualBook).isNotNull();
					assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
				});
	}

	@Test
	void contextLoads() {
	}

}
