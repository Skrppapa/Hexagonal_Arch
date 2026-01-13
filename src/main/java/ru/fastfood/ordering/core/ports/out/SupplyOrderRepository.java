package ru.fastfood.ordering.core.ports.out;
import ru.fastfood.ordering.core.domain.*;
import java.util.Optional;
import java.util.UUID;

public interface SupplyOrderRepository {
    void save(SupplyOrder order);
    Optional<SupplyOrder> findById(UUID id);
}