package ru.fastfood.ordering.infrastructure.adapters.out;

import ru.fastfood.ordering.core.domain.SupplyOrder;
import ru.fastfood.ordering.core.ports.out.SupplyOrderRepository;
import java.util.*;

public class InMemorySupplyOrderRepository implements SupplyOrderRepository {
    private final Map<UUID, SupplyOrder> database = new HashMap<>();

    @Override
    public void save(SupplyOrder order) {
        database.put(order.getId(), order);
        System.out.println("БД: Заказ сохранен/обновлен. Текущий статус: " + order.getStatus());
    }

    @Override
    public Optional<SupplyOrder> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }
}

