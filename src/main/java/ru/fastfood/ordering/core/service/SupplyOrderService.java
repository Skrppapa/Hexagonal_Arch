package ru.fastfood.ordering.core.service;

import lombok.RequiredArgsConstructor;
import ru.fastfood.ordering.core.domain.*;
import ru.fastfood.ordering.core.ports.in.SupplyOrderUseCase;
import ru.fastfood.ordering.core.ports.out.*;
import java.time.LocalDateTime;
import java.util.List;
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
    public List<SupplyOrder> getAllOrders() {
        return repository.findAll();
    }


    @Override
    public void sendToSupplier(UUID orderId) {
        SupplyOrder order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден: " + orderId));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("Заказ уже отправлен или находится в работе!");
        }
        order.setStatus(OrderStatus.SENT);
        repository.save(order);
    }

    @Override
    public void confirmOrder(UUID orderId) {
        SupplyOrder order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден: " + orderId));

        if (order.getStatus() != OrderStatus.SENT) {
            throw new IllegalStateException("Ошибка: Нельзя подтвердить заказ, который не был отправлен!");
        }
        order.setStatus(OrderStatus.CONFIRMED);
        repository.save(order);
    }

    @Override
    public void receiveDelivery(DeliveryReport report) {
        SupplyOrder order = repository.findById(report.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден"));

        if (report.isQualityCheckPassed()) {
            order.setStatus(OrderStatus.COMPLETED);
        } else {
            order.setStatus(OrderStatus.REJECTED);
            order.setRejectReason(report.getComments());
        }
        repository.save(order);
    }

}