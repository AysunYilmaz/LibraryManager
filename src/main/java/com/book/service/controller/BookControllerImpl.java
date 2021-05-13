package com.book.service.controller;

import com.book.service.model.Book;
import com.book.service.service.BookService;
import com.book.service.util.Constants;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Constants.URI_V1 +Constants.URI_BOOKS)
@AllArgsConstructor
public class BookControllerImpl implements BookController{
    private static final Logger log = LogManager.getLogger(BookControllerImpl.class);

    private final BookService bookService;

    @Override
    @ApiOperation(value = "Get All the books in the library")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllBooks() {
        log.debug("Get All Books Request Received");
        List<Book> books=bookService.getAllBooks();

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Override
    @ApiOperation(value = "Get book by id", notes = "Retrieves book id ")
    @GetMapping(value="{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getBook(@PathVariable("id") Long id) {
        log.debug("Get Book Request Received");
        Book book=bookService.getBookById(id);

        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @Override
    @ApiOperation(value = "Removes book from library", notes = "Retrieves book id ")
    @DeleteMapping(value="{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteBook(@PathVariable("id") Long id) {
        log.debug("Delete Book Request Received");
        bookService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @ApiOperation(value = "Add book into library", notes = "Retrieves book object as request ")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addBook(@RequestBody @Validated Book book) {
        log.debug("Add Book Request Received");
        book=bookService.addBook(book);
        return new ResponseEntity<>(book,HttpStatus.CREATED);
    }
}
