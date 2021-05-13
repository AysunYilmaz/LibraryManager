package com.book.service.service.impl;

import com.book.service.entity.BookEntity;
import com.book.service.exception.ResourceNotFoundException;
import com.book.service.model.Book;
import com.book.service.model.mapper.BookDTOMapper;
import com.book.service.repository.BookRepository;
import com.book.service.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    BookRepository bookRepository;

    @Override
    public void deleteBookById(Long id) {
        log.debug("Delete book by id {}",id);
        BookEntity book= bookRepository.findActiveById(id);

        if (book == null) {
            throw new ResourceNotFoundException(id);
        }
        book.setIsActive(0);
        bookRepository.saveAndFlush(book);
    }

    @Override
    public List<Book> getAllBooks() {
        log.debug("Get All books service");

        List<BookEntity> bookEntities= bookRepository.findActiveAll();

        List<Book> books= BookDTOMapper.INSTANCE.booksEntityListToBookList(bookEntities);
        return books;
    }

    @Override
    public Book getBookById(Long id) {

        log.debug("Get book by id {}",id);

        BookEntity bookEntity= bookRepository.findActiveById(id);

        if (bookEntity == null) {
            throw new ResourceNotFoundException(id);
        }

        Book book=BookDTOMapper.INSTANCE.booksEntityToBook(bookEntity);

        return book;
    }

    @Override
    public Book addBook(Book book) {
        log.debug("Add a new book ");

        BookEntity bookEntity=BookDTOMapper.INSTANCE.bookToBooksEntity(book);
        bookEntity.setIsActive(1);

        BookEntity savedBookEntity= bookRepository.saveAndFlush(bookEntity);

        book=BookDTOMapper.INSTANCE.booksEntityToBook(savedBookEntity);

        return book;
    }
}
