package com.example.shardingtable.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : cofe
 * @date : 2023-06-12
 */
@Data
public class OrderVo {
    private String orderNo;
    private BigDecimal amount;
}