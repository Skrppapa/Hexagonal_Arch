package ru.fastfood.ordering.core.ports.out;

import ru.fastfood.ordering.core.domain.SupplyOrder;

public interface SupplierNotificationService {
    void notify(SupplyOrder order);
}