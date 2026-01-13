package ru.fastfood.ordering.core.ports.out;

import ru.fastfood.ordering.core.domain.SupplyOrder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SupplyOrderRepository {
    void save(SupplyOrder order);
    Optional<SupplyOrder> findById(UUID id);
    List<SupplyOrder> findAll();
}