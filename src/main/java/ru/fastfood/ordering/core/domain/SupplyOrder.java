package ru.fastfood.ordering.core.domain;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class SupplyOrder {
    private UUID id;
    private UUID supplierId;
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime createdAt;
}