package com.book.service.controller;

import com.book.service.entity.LendEntity;
import com.book.service.model.Lend;
import com.book.service.service.BookLendService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
import java.util.Arrays;
import java.util.List;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookLendControllerTest {
    @LocalServerPort
    int serverPort;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookLendService mockBookLendService;

    @InjectMocks
    private BookLendControllerImpl objectUnderTest;

    private LendEntity bookLendEntity;
    private LendEntity bookLendEntity2;
    private Lend bookLend;
    private Lend bookLend2;

    List<LendEntity> bookLendEntities =new ArrayList<LendEntity>();
    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = serverPort;

        bookLendEntity =new LendEntity(1L,1L,"Andy Warhol","2020/05/12","2020/06/02",0);
        bookLendEntity2 =new LendEntity(2L,2L,"Andy Smith","2020/05/05","2020/05/30",0);


        bookLendEntities.add(bookLendEntity);
        bookLendEntities.add(bookLendEntity2);

        bookLend =new Lend();
        bookLend.setId(1L);
        bookLend.setBookId(1L);
        bookLend.setCustomerName("Andy Warhol");
        bookLend.setReturned(0);
        bookLend.setLendDate("2020/05/12");
        bookLend.setExpiryDate("2020/06/02");

        bookLend2 =new Lend();
        bookLend2.setId(2L);
        bookLend2.setBookId(2L);
        bookLend2.setCustomerName("Andy Smith");
        bookLend2.setReturned(1);
        bookLend2.setLendDate("2020/05/05");
        bookLend2.setExpiryDate("2020/05/30");
    }

    @Test
    public final void testAddLend() throws Exception {

        Mockito.when(mockBookLendService.addLend(any(),any())).thenReturn(bookLend);

        // given:
        RequestSpecification request = given()
                .header("Content-Type", "application/json;charset=UTF-8")
                .queryParam("bookId", 1L)
                .queryParam("customerName","Andy Warhol");


        // when:
        ResponseOptions response = given().spec(request)
                .post("/api/v1.0/lend");

        // then:
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Content-Type")).isEqualTo("application/json;charset=UTF-8");
        // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
        assertThatJson(parsedJson).field("['customerName']").isEqualTo("Andy Warhol");


    }

    @Test
    public final void testGetAllLends() throws Exception {

        Mockito.when(mockBookLendService.getAllLends()).thenReturn(Arrays.asList(bookLend,bookLend2));

        this.mockMvc.perform(get("/api/v1.0/lend")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$..customerName",hasSize(2)));

    }

    @Test
    public final void testGetLendById() throws Exception {


        Mockito.when(mockBookLendService.getLendById(1L)).thenReturn(bookLend);

        this.mockMvc.perform(get("/api/v1.0/lend/{lendId}",1)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Andy Warhol"));

    }

    @Test
    public final void testGetLendByCustomerName() throws Exception {

        Mockito.when(mockBookLendService.getLendByCustomer("Andy Warhol")).thenReturn(Arrays.asList(bookLend));

        this.mockMvc.perform(get("/api/v1.0/lend/customer/{customerName}","Andy Warhol")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$..customerName").value("Andy Warhol"));

    }

    @Test
    public final void testReturnBook() throws Exception {
        Long id=2L;

        Mockito.when(mockBookLendService.returnBook(id)).thenReturn(bookLend2);

        this.mockMvc.perform(put("/api/v1.0/lend/return/{lendId}",2)).andDo(print()).andExpect(status().isAccepted())
                .andExpect(jsonPath("$.returned").value("1"));

    }

    @Test
    public final void testUpdateLend() throws Exception {
        Long id=2L;

        Mockito.when(mockBookLendService.updateLend(id)).thenReturn(bookLend2);

        this.mockMvc.perform(put("/api/v1.0/lend/extend/{lendId}",2)).andDo(print()).andExpect(status().isAccepted())
                .andExpect(jsonPath("$.expiryDate").value("2020/05/30"));

    }
}
