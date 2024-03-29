package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTest {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    public void testSerialize() throws IOException {
        //GIVEN
        var book = new Book(null, "1234567890", "Title", "Author", 9.90, "Publisher", null, null,0);

        //WHEN
        var jsonContent = json.write(book);

        //THEN
        assertThat(jsonContent)
            .extractingJsonPathStringValue("@.isbn")
            .isEqualTo(book.isbn());
        assertThat(jsonContent)
            .extractingJsonPathStringValue("@.title")
            .isEqualTo(book.title());
        assertThat(jsonContent)
            .extractingJsonPathStringValue("@.author")
            .isEqualTo(book.author());
        assertThat(jsonContent)
            .extractingJsonPathNumberValue("@.price")
            .isEqualTo(book.price());
    }

    @Test
    public void testDeserialize() throws Exception {
        //GIVEN
        var content = """
            {
                "isbn": "1234567890",
                "title": "Title",
                "author": "Author",
                "price": 9.90,
                "publisher": "Publisher"
            }
        """;

        //WHEN
        assertThat(json.parse(content))
        //THEN
            .usingRecursiveComparison()
            .isEqualTo(new Book(null, "1234567890", "Title", "Author", 9.90, "Publisher", null, null,0));
    }
}
