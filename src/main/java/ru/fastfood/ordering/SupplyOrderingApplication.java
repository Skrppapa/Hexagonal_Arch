package ru.fastfood.ordering;

import ru.fastfood.ordering.core.service.SupplyOrderService;
import ru.fastfood.ordering.infrastructure.adapters.in.ConsoleUserInterface;
import ru.fastfood.ordering.infrastructure.adapters.out.InMemorySupplyOrderRepository;
import ru.fastfood.ordering.infrastructure.adapters.out.MockSupplierNotificationService;

public class SupplyOrderingApplication {
    public static void main(String[] args) {
        // Адаптеры
        var repository = new InMemorySupplyOrderRepository();
        var notificationService = new MockSupplierNotificationService();

        //  Ядро
        var service = new SupplyOrderService(repository, notificationService);

        // Входной адаптер
        var consoleUI = new ConsoleUserInterface(service);

        consoleUI.start();
    }
}