package com.book.service.service;

import com.book.service.model.Book;

import java.util.List;

public interface BookService {
    void deleteBookById(Long id);

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book addBook(Book book);

}
