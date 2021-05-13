package com.book.service.controller;

import org.springframework.http.ResponseEntity;

public interface BookLendController {

    ResponseEntity<Object> addLend(Long id, String customerName);

    ResponseEntity<Object> returnBook(Long id);

    ResponseEntity<Object> updateLend(Long id);

    ResponseEntity<Object> getAllLends();

    ResponseEntity<Object> getLendById(Long id);

    ResponseEntity<Object> getLendByCustomer(String customerName);

}
