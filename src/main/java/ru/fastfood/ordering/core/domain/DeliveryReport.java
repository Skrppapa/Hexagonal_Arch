package ru.fastfood.ordering.core.domain;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class DeliveryReport {
    private UUID orderId;
    private boolean qualityCheckPassed;
    private String comments;
}