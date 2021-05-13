package com.book.service.service;

import com.book.service.entity.LendEntity;
import com.book.service.exception.ResourceNotFoundException;
import com.book.service.model.Lend;
import com.book.service.repository.LendRepository;
import com.book.service.service.impl.BookLendServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class BookLendServiceTest {

    @Mock
    private LendRepository lendRepository;

    @InjectMocks
    private BookLendServiceImpl objectUnderTest;


    private LendEntity bookLendEntity;
    private LendEntity bookLendEntity2;
    private Lend bookLend;
    private Lend bookLend2;

    List<LendEntity> bookLendEntities =new ArrayList<LendEntity>();

    @Before
    public void setUp() throws Exception {

        bookLendEntity =new LendEntity(1L,1L,"Andy Warhol","2020/05/12","2020/06/02",0);
        bookLendEntity2 =new LendEntity(2L,2L,"Andy Smith","2020/05/05","2020/05/30",0);


        bookLendEntities.add(bookLendEntity);
        bookLendEntities.add(bookLendEntity2);

        bookLend =new Lend();
        bookLend.setBookId(1L);
        bookLend.setCustomerName("Andy Warhol");

        bookLend2 =new Lend();
        bookLend2.setId(2L);
        bookLend2.setBookId(1L);
        bookLend2.setCustomerName("Andy Smith");
        bookLend2.setReturned(1);
        bookLend2.setLendDate("2020/05/05");
        bookLend2.setExpiryDate("2020/05/30");
    }

    @Test(expected = ResourceNotFoundException.class)
    public final void testAddLendBookAlreadyLentThrowsError() throws Exception {

        Mockito.when( lendRepository.findActiveByBookId(any())).thenReturn(bookLendEntity);

        Lend lend= objectUnderTest.addLend(1L,"Andy Warhol");

    }

    @Test
    public final void testAddLend() throws Exception {

        Mockito.when( lendRepository.findActiveByBookId(any())).thenReturn(null);

        Mockito.when(lendRepository.saveAndFlush(any(LendEntity.class))).thenReturn(bookLendEntity);

        Lend lend= objectUnderTest.addLend(1L,"Andy Warhol");
        assertEquals("Andy Warhol", lend.getCustomerName());
    }

    @Test
    public final void testGetAllLends() throws Exception {

        Mockito.when(lendRepository.findActiveAll()).thenReturn(bookLendEntities);

        List<Lend> lends= objectUnderTest.getAllLends();
        assertEquals(2, lends.size());
        assertEquals("Andy Warhol", lends.get(0).getCustomerName());
        assertEquals("Andy Smith", lends.get(1).getCustomerName());

    }

    @Test
    public final void testGetLendById() throws Exception {

        Mockito.when(lendRepository.findActiveById(1L)).thenReturn(bookLendEntity);

        Lend lend= objectUnderTest.getLendById(1L);
        assertEquals("Andy Warhol", lend.getCustomerName());

    }

    @Test
    public final void testGetLendByCustomerName() throws Exception {

        Mockito.when(lendRepository.findActiveByCustomerName("Andy Warhol")).thenReturn(Arrays.asList(bookLendEntity));

        List<Lend> lends= objectUnderTest.getLendByCustomer("Andy Warhol");

        assertEquals(1, lends.size());
        assertEquals("Andy Warhol", lends.get(0).getCustomerName());

    }

    @Test
    public final void testReturnBook() throws Exception {
        Long id=1L;

        Mockito.when(lendRepository.findActiveById(id)).thenReturn(bookLendEntity);

        Mockito.when(lendRepository.saveAndFlush(any(LendEntity.class))).thenReturn(bookLendEntity);

        Lend lend= objectUnderTest.returnBook(id);
        assertEquals(1, lend.getReturned());
    }

    @Test
    public final void testUpdateLend() throws Exception {
        Long id=2L;

        Mockito.when(lendRepository.findActiveById(id)).thenReturn(bookLendEntity2);

        Mockito.when(lendRepository.saveAndFlush(any(LendEntity.class))).thenReturn(bookLendEntity2);
        Lend lend= objectUnderTest.updateLend(id);

        String expiryDate= LocalDate.now().plusDays(15).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        assertEquals(expiryDate, lend.getExpiryDate());

    }
}
