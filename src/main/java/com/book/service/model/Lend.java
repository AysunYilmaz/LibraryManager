package com.book.service.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Lend {
    @NotNull
    private long id;

    @NotNull
    @NotEmpty
    private long bookId;

    @NotNull
    @NotEmpty
    private String customerName;

    @NotNull
    @NotEmpty
    private String lendDate;

    @NotNull
    @NotEmpty
    private String expiryDate;

    @NotNull
    @NotEmpty
    private int returned;
}
