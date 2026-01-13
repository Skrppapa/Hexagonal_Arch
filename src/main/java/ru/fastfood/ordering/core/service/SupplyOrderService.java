package ru.fastfood.ordering.core.service;

import lombok.RequiredArgsConstructor;
import ru.fastfood.ordering.core.domain.*;
import ru.fastfood.ordering.core.ports.in.SupplyOrderUseCase;
import ru.fastfood.ordering.core.ports.out.*;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class SupplyOrderService implements SupplyOrderUseCase {
    private final SupplyOrderRepository repository;
    private final SupplierNotificationService notificationService;

    @Override
    public SupplyOrder createFromForecast(UUID supplierId) {
        SupplyOrder order = SupplyOrder.builder()
                .id(UUID.randomUUID())
                .supplierId(supplierId)
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();
        repository.save(order);
        return order;
    }

    @Override
    public void sendToSupplier(UUID orderId) {
        SupplyOrder order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.SENT);
        notificationService.notify(order);
        repository.save(order);
    }

    @Override
    public void confirmOrder(UUID orderId) {
        SupplyOrder order = repository.findById(orderId).orElseThrow();
        order.setStatus(OrderStatus.CONFIRMED);
        repository.save(order);
    }

    @Override
    public void receiveDelivery(DeliveryReport report) {
        SupplyOrder order = repository.findById(report.getOrderId()).orElseThrow();
        if (report.isQualityCheckPassed()) {
            order.setStatus(OrderStatus.DELIVERED);
            System.out.println("Качество подтверждено. Продукты приняты.");
        } else {
            order.setStatus(OrderStatus.REJECTED);
            System.out.println("Проблема с качеством: " + report.getComments());
        }
        repository.save(order);
    }
}