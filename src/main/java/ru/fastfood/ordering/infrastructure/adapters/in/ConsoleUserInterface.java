package ru.fastfood.ordering.infrastructure.adapters.in;

import lombok.RequiredArgsConstructor;
import ru.fastfood.ordering.core.domain.DeliveryReport;
import ru.fastfood.ordering.core.domain.OrderStatus;
import ru.fastfood.ordering.core.domain.SupplyOrder;
import ru.fastfood.ordering.core.ports.in.SupplyOrderUseCase;

import java.util.Scanner;
import java.util.UUID;

@RequiredArgsConstructor
public class ConsoleUserInterface {
    private final SupplyOrderUseCase supplyOrderUseCase;
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1" -> createOrder();
                    case "2" -> sendOrder();
                    case "3" -> confirmOrder();
                    case "4" -> receiveDelivery();
                    case "5" -> showAllOrders();
                    case "0" -> System.exit(0);
                    default -> System.out.println("Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println("ОШИБКА: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- УПРАВЛЕНИЕ ЗАКАЗАМИ ---");
        System.out.println("1. Создать заказ");
        System.out.println("2. Отправить заказ поставщику");
        System.out.println("3. Получить подтверждение");
        System.out.println("4. Приемка (Контроль качества)");
        System.out.println("5. Список заказов");
        System.out.println("0. Выход");
        System.out.print("Выбор: ");
    }

    private void createOrder() {
        UUID supplierId = UUID.randomUUID();
        SupplyOrder order = supplyOrderUseCase.createFromForecast(supplierId);
        System.out.println("Заказ успешно создан! ID: " + order.getId());
        System.out.println("Статус: " + order.getStatus());
    }

    private void sendOrder() {
        System.out.print("Введите ID заказа для отправки: ");
        try {
            UUID id = UUID.fromString(scanner.nextLine());
            supplyOrderUseCase.sendToSupplier(id);
            System.out.println("Заказ отправлен.");
        } catch (Exception e) {
            System.out.println("Ошибка: Неверный формат UUID или заказ не найден.");
        }
    }

    private void confirmOrder() {
        System.out.print("Введите ID заказа для подтверждения: ");
        try {
            UUID id = UUID.fromString(scanner.nextLine());
            supplyOrderUseCase.confirmOrder(id);
            System.out.println("Заказ подтвержден поставщиком.");
        } catch (Exception e) {
            System.out.println("Ошибка при подтверждении.");
        }
    }

    private void receiveDelivery() {
        System.out.print("Введите ID заказа для приемки: ");
        try {
            UUID id = UUID.fromString(scanner.nextLine());
            System.out.print("Проверка качества пройдена? (yes/no): ");
            boolean passed = scanner.nextLine().equalsIgnoreCase("yes");
            System.out.print("Комментарий: ");
            String comment = scanner.nextLine();

            DeliveryReport report = DeliveryReport.builder()
                    .orderId(id)
                    .qualityCheckPassed(passed)
                    .comments(comment)
                    .build();

            supplyOrderUseCase.receiveDelivery(report);
            System.out.println("Отчет о приемке сформирован.");
        } catch (Exception e) {
            System.out.println("Ошибка при приемке.");
        }
    }

    private void showAllOrders() {
        System.out.println("\n=== ТЕКУЩИЕ ЗАКАЗЫ И СТАТУСЫ ===");
        supplyOrderUseCase.getAllOrders().forEach(order -> {
            System.out.printf("ID: %s | Статус: %s | Поставщик: %s\n",
                    order.getId(), order.getStatus(), order.getSupplierId());

            if (order.getStatus() == OrderStatus.REJECTED) {
                System.out.println("   >>> ПРИЧИНА ВОЗВРАТА: " + order.getRejectReason());
            }
        });
    }
}







