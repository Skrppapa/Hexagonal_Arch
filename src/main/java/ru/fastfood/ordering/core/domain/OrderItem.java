package ru.fastfood.ordering.core.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {
    private String productId;
    private int quantity;
}