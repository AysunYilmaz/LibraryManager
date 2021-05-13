package com.book.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

@Data
@Entity
@Table(name = "lends")
@AllArgsConstructor
@NoArgsConstructor
public class LendEntity implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "lend_date")
    private String lendDate;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "returned")
    private int returned;
}
