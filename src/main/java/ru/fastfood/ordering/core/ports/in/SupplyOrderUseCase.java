package ru.fastfood.ordering.core.ports.in;
import ru.fastfood.ordering.core.domain.*;

import java.util.List;
import java.util.UUID;

public interface SupplyOrderUseCase {
    SupplyOrder createFromForecast(UUID supplierId);
    void sendToSupplier(UUID orderId);
    void confirmOrder(UUID orderId);
    void receiveDelivery(DeliveryReport report);
    List<SupplyOrder> getAllOrders();
}