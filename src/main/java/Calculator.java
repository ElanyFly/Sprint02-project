import java.util.*;

public class Calculator {
    Scanner scanner = new Scanner(System.in);
    private final ArrayList<ProductBought> productsList = new ArrayList<>();
    private final String ADD_ITEM = "добавить";
    private final String END_ADD_ITEM = "завершить";

    public void startCalculator() {

        int peopleCount = askPeopleCount();
        executeMenu();
        scanner.close();
        calculateResults(peopleCount);
    }

    private void executeMenu() {

        while (true) {

            String command = commandRequest();

            if (command.toLowerCase().contains(ADD_ITEM)) {
                ProductBought productBought = executeAddProducts();
                productsList.add(productBought);
            } else if (command.toLowerCase().contains(END_ADD_ITEM)) {
                System.out.println("Добавление товаров завершено.");
                break;
            } else {
                System.out.println("Неправильно введена команда, попробуйте снова.");
            }
        }
    }

    private String commandRequest() {
        System.out.println("\nДля добавления товара, введите: Добавить");
        System.out.println("Для завершения добавления товаров введите: Завершить");

        return scanner.next();
    }


    public int askPeopleCount() {
        int peopleCount = 0;
        do {
            System.out.println("На сколько человек разделить счёт?");
            if (scanner.hasNextInt()) {
                peopleCount = scanner.nextInt();
                if (peopleCount == 1) {
                    System.out.println("Для одного человека нет смысла считать как разделить счёт. Попробуйте ещё раз.");
                } else if (peopleCount < 1) {
                    System.out.println("Количество человек должно быть больше 1-ого.");
                }
            } else {
                System.out.println("Введено не корректное число.");
                scanner.next();
            }
        } while (peopleCount <= 1);
        return peopleCount;
    }

    private ProductBought executeAddProducts() {
        scanner.useLocale(Locale.UK);

        String productName;
        double productPrice = 0;

        System.out.println("Введите наименование товара:");
        productName = scanner.next();

        do {
            System.out.println("Введите цену:");
            if (scanner.hasNextDouble()) {
                productPrice = scanner.nextDouble();
            } else {
                scanner.next();
                System.out.println("Неправильно введена стоимость. Попробуйте снова.");
            }
        } while (productPrice <= 0);

        System.out.println("Товар \"" + productName + "\" успешно добавлен");
        return new ProductBought(productName, productPrice);
    }

    public void calculateResults(int peopleCount) {

        double totalSum = 0;
        double divideMoney;

        String messageTemplate = "\t%s.....%.2f р.\n";

        System.out.println("\nДобавленные товары: ");
        for (int i = 0; i < productsList.size(); i++) {
            String productName = productsList.get(i).productName;
            double productPrice = productsList.get(i).price;
            System.out.printf(messageTemplate, productName, productPrice);
            totalSum = totalSum + productPrice;
        }
        divideMoney = totalSum / peopleCount;

        System.out.printf("\nСумма всего чека: %.2f " + RubFormat.getRub(totalSum) + "%n", totalSum);
        System.out.printf("Каждый человек должен заплатить: %.2f " + RubFormat.getRub(divideMoney), divideMoney);
    }
}

