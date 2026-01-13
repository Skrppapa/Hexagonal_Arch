package ru.fastfood.ordering.infrastructure.adapters.out;

import ru.fastfood.ordering.core.domain.SupplyOrder;
import ru.fastfood.ordering.core.ports.out.SupplierNotificationService;

public class MockSupplierNotificationService implements SupplierNotificationService {
    @Override
    public void notify(SupplyOrder order) {
        System.out.println("Email: Уведомление отправлено поставщику " + order.getSupplierId());
    }
}