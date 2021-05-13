package com.book.service.service;

import com.book.service.model.Lend;

import java.util.List;

public interface BookLendService {

    Lend addLend(Long id, String customerName);

    Lend returnBook(Long id);

    Lend updateLend(Long id);

    List<Lend> getAllLends();

    Lend getLendById(Long id);

    List<Lend> getLendByCustomer(String customerName);

}
