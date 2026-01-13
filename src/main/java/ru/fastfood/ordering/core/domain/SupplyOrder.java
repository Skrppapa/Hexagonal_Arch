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
    private String rejectReason;

    public UUID getId() { return id; }
    public UUID getSupplierId() { return supplierId; }
    public OrderStatus getStatus() { return status; }
    public String getRejectReason() { return rejectReason; }

    public void setStatus(OrderStatus status) { this.status = status; }
    public void setRejectReason(String rejectReason) { this.rejectReason = rejectReason; }
}