package com.book.service.controller;

import com.book.service.entity.BookEntity;
import com.book.service.model.Book;
import com.book.service.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookControllerTest {
    @LocalServerPort
    int serverPort;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;
    private BookEntity bookEntity;
    private BookEntity bookEntity2;
    private Book book;
    private Book book2;

    private List<BookEntity> bookEntities=new ArrayList<BookEntity>();
    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = serverPort;

        bookEntity=new BookEntity();
        bookEntity.setAuthor("V.E. SCHWAB");
        bookEntity.setName("Invisible Life of Addie Larue");
        bookEntity.setId(1L);
        bookEntity.setGenre("Romance");
        bookEntity.setIsActive(1);

        bookEntity2=new BookEntity();
        bookEntity2.setAuthor("Matt Haig");
        bookEntity2.setName("The Midnight Library");
        bookEntity2.setId(2L);
        bookEntity2.setGenre("Fiction");
        bookEntity2.setIsActive(1);

        bookEntities.add(bookEntity);
        bookEntities.add(bookEntity2);

        book=new Book();
        book.setAuthor("V.E. SCHWAB");
        book.setName("Invisible Life of Addie Larue");
        book.setGenre("Romance");
        book.setIsActive(1);

        book2=new Book();
        book2.setAuthor("Chris Whitaker");
        book2.setName("We Begin at The End");
        book2.setGenre("Fiction");
        book2.setIsActive(1);
    }

    @Test
    public final void testAddBook() throws Exception {
        String json = mapper.writeValueAsString(book);

        Mockito.when(bookRepository.saveAndFlush(any(BookEntity.class))).thenReturn(bookEntity);
        // given:
        RequestSpecification request = given()
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(json);


        // when:
        ResponseOptions response = given().spec(request)
                .post("/api/v1.0/books");

        // then:
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Content-Type")).isEqualTo("application/json;charset=UTF-8");
        // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
        assertThatJson(parsedJson).field("['name']").isEqualTo("Invisible Life of Addie Larue");


    }

    @Test
    public final void testGetAllBooks() throws Exception {

        Mockito.when(bookRepository.findActiveAll()).thenReturn(bookEntities);

        this.mockMvc.perform(get("/api/v1.0/books")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$..name",hasSize(2)));

    }

    @Test
    public final void testGetBookById() throws Exception {

        Mockito.when(bookRepository.findActiveById(1L)).thenReturn(bookEntity);

        this.mockMvc.perform(get("/api/v1.0/books/{id}",1)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Invisible Life of Addie Larue"));

    }

    @Test
    public final void testDeleteBook() throws Exception {
        Long id=2L;

        Mockito.when(bookRepository.findActiveById(id)).thenReturn(bookEntity2);

        bookEntity2.setIsActive(0);
        Mockito.when(bookRepository.saveAndFlush(bookEntity2)).thenReturn(bookEntity2);

        this.mockMvc.perform(delete("/api/v1.0/books/{id}",id)).andDo(print()).andExpect(status().isNoContent());

    }
}
