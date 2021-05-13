package com.book.service.service.impl;

import com.book.service.entity.LendEntity;
import com.book.service.exception.ResourceNotFoundException;
import com.book.service.model.Lend;
import com.book.service.model.mapper.LendDTOMapper;
import com.book.service.repository.LendRepository;
import com.book.service.service.BookLendService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class BookLendServiceImpl implements BookLendService {

    private static final Logger log = LoggerFactory.getLogger(BookLendServiceImpl.class);


    private final LendRepository lendRepository;

    @Override
    public Lend addLend(Long bookId, String customerName) {
        log.debug("Add a new lend data ");

        LendEntity bookLent= lendRepository.findActiveByBookId(bookId);

        if (bookLent != null) {
            throw new ResourceNotFoundException(bookId);
        }

        String lendDate= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String expiryDate= LocalDate.now().plusDays(15).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        LendEntity lendEntity= new LendEntity( null,bookId,customerName,lendDate,expiryDate,0);


        LendEntity savedLendEntity= lendRepository.saveAndFlush(lendEntity);

        return LendDTOMapper.INSTANCE.lendsEntityToLend(savedLendEntity);
    }

    @Override
    public Lend returnBook(Long id) {
        log.debug("Return lends service");
        LendEntity lendEntity= lendRepository.findActiveById(id);

        if (lendEntity == null) {
            throw new ResourceNotFoundException(id);
        }

        lendEntity.setReturned(1);

        LendEntity savedLendEntity= lendRepository.saveAndFlush(lendEntity);

        return LendDTOMapper.INSTANCE.lendsEntityToLend(savedLendEntity);
    }

    @Override
    public Lend updateLend(Long id) {
        log.debug("Update lend service");

        LendEntity lendEntity= lendRepository.findActiveById(id);

        if (lendEntity == null) {
            throw new ResourceNotFoundException(id);
        }
        String expiryDate= LocalDate.now().plusDays(15).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        lendEntity.setExpiryDate(expiryDate);

        LendEntity savedLendEntity= lendRepository.saveAndFlush(lendEntity);

        return LendDTOMapper.INSTANCE.lendsEntityToLend(savedLendEntity);

    }

    @Override
    public List<Lend> getAllLends() {
        log.debug("Get all lends service");

        List<LendEntity> lendEntities= lendRepository.findActiveAll();

        return LendDTOMapper.INSTANCE.lendsEntityListToLendList(lendEntities);
    }

    @Override
    public Lend getLendById(Long id) {

        log.debug("Get lend by id {}",id);

        LendEntity lendEntity= lendRepository.findActiveById(id);

        if (lendEntity == null) {
            throw new ResourceNotFoundException(id);
        }

        return LendDTOMapper.INSTANCE.lendsEntityToLend(lendEntity);
    }

    @Override
    public List<Lend> getLendByCustomer(String customerName) {
        log.debug("Get lend by customer {}",customerName);

        List<LendEntity> lendEntity= lendRepository.findActiveByCustomerName(customerName);

        if (lendEntity == null) {
            throw new ResourceNotFoundException(customerName);
        }

        return LendDTOMapper.INSTANCE.lendsEntityListToLendList(lendEntity);
    }
}
