package com.book.service.controller;

import com.book.service.model.Book;
import com.book.service.model.Lend;
import com.book.service.service.BookLendService;
import com.book.service.service.BookService;
import com.book.service.util.Constants;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Constants.URI_V1 +Constants.URI_LEND)
@AllArgsConstructor
public class BookLendControllerImpl implements BookLendController{
    private static final Logger log = LogManager.getLogger(BookLendControllerImpl.class);
    private final BookLendService bookLendService;

    @Override
    @ApiOperation(value = "Borrow book", notes = "Retrieves bookId and CustomerName")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addLend(
            @RequestParam(value = "bookId", required = true) Long bookId,
            @RequestParam(value = "customerName", required = true) String customerName) {


        Lend lend=bookLendService.addLend(bookId,customerName);
        return new ResponseEntity<>(lend, HttpStatus.CREATED);
    }

    @Override
    @ApiOperation(value = "Return borrowed book", notes = "Retrieves lendId")
    @PutMapping(value = "/return/{lendId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> returnBook(@PathVariable("lendId") Long lendId) {

        Lend lend=bookLendService.returnBook(lendId);
        return new ResponseEntity<>(lend, HttpStatus.ACCEPTED);
    }

    @Override
    @ApiOperation(value = "Update expiry date of the borrowed book", notes = "Retrieves lendId")
    @PutMapping(value = "/extend/{lendId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateLend(@PathVariable("lendId") Long lendId) {
        Lend lend=bookLendService.updateLend(lendId);
        return new ResponseEntity<>(lend, HttpStatus.ACCEPTED);
    }

    @Override
    @ApiOperation(value = "Get all borrowed books")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllLends() {
        log.debug("Get All Borrowed Books Request Received");
        List<Lend> lends=bookLendService.getAllLends();

        return new ResponseEntity<>(lends, HttpStatus.OK);
    }

    @Override
    @ApiOperation(value = "Get borrowed book by lendId", notes = "Retrieves lendId")
    @GetMapping(value="/{lendId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getLendById(@PathVariable("lendId") Long lendId) {
        log.debug("Get Borrowed Books By id  Request Received. id: {}", lendId);
        Lend lend=bookLendService.getLendById(lendId);

        return new ResponseEntity<>(lend, HttpStatus.OK);
    }

    @Override
    @ApiOperation(value = "Get borrowed books by Customer", notes = "Retrieves customerName")
    @GetMapping(value="/customer/{customerName}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getLendByCustomer(@PathVariable("customerName") String customerName) {
        log.debug("Get Borrowed Books By Customer  Request Received. Customer Name: {}", customerName);
        List<Lend> lends=bookLendService.getLendByCustomer(customerName);
        return new ResponseEntity<>(lends, HttpStatus.OK);
    }
}
