package ru.fastfood.ordering.infrastructure.adapters.in;

import lombok.RequiredArgsConstructor;
import ru.fastfood.ordering.core.domain.DeliveryReport;
import ru.fastfood.ordering.core.domain.SupplyOrder;
import ru.fastfood.ordering.core.ports.in.SupplyOrderUseCase;
import java.util.UUID;

@RequiredArgsConstructor
public class ConsoleUserInterface {
    private final SupplyOrderUseCase supplyOrderUseCase;

    public void runDemo() {
        UUID supplierId = UUID.randomUUID();

        System.out.println("--- Создание заказа на основе прогноза ---");
        SupplyOrder order = supplyOrderUseCase.createFromForecast(supplierId);

        System.out.println("--- Отправка заказа поставщику ---");
        supplyOrderUseCase.sendToSupplier(order.getId());

        System.out.println("--- Подтверждение от поставщика ---");
        supplyOrderUseCase.confirmOrder(order.getId());

        System.out.println("--- Приемка поставки (Контроль качества) ---");
        DeliveryReport report = DeliveryReport.builder()
                .orderId(order.getId())
                .qualityCheckPassed(true)
                .comments("Все овощи свежие")
                .build();
        supplyOrderUseCase.receiveDelivery(report);
    }
}