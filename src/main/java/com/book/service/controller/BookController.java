package com.book.service.controller;

import com.book.service.model.Book;
import org.springframework.http.ResponseEntity;

public interface BookController {


    ResponseEntity<Object> getAllBooks();
    ResponseEntity<Object> deleteBook(Long id);
    ResponseEntity<Object> getBook(Long id);

    ResponseEntity<Object> addBook(Book book);
}
