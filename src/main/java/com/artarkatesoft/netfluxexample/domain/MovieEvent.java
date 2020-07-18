package com.artarkatesoft.netfluxexample.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MovieEvent {
    private String movieId;
    private Date date;
}
