import java.util.*;

public class Calculator {
    Scanner scanner = new Scanner(System.in);
    HashMap<User, ArrayList<ProductBought>> billList = new HashMap<>();

    public void startCalculator() {

        int peopleCount = askPeopleCount();

        for (int i = 1; i <= peopleCount; i++) {
            executeMenu(i);
        }

        calculateResults();
    }

    private void executeMenu(int personCount) {

        User user = userInput(personCount);

        ArrayList<ProductBought> productsList = new ArrayList<>();

        while (true) {

            System.out.println("\nДля добавления товара, введите: Добавить");
            System.out.println("Для завершения добавления товаров введите: Завершить");

            String command = scanner.next();

            if (command.toLowerCase().contains("добавить")) {
                productsList.add(executeAddProducts());
            } else if (command.toLowerCase().contains("завершить")) {
                System.out.println("Добавление товаров " + personCount + "-ого пользователя завершено.");
                break;
            } else {
                System.out.println("Неправильно введена команда, попробуйте снова.");
            }
        }
        billList.put(user, productsList);
    }

    private User userInput(int personCount) {
        System.out.println("\nВведите имя " + personCount + "-ого пользователя: ");
        String userName = scanner.next();
        System.out.println("Добро пожаловать, " + userName + ".");
        return new User(userName);
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

    public void calculateResults() {

        double totalSum = 0;
        double personSum = 0;

        String messageTemplate = "\t%s.....%.2f р.\n";

        for (Map.Entry<User, ArrayList<ProductBought>> listEntry : billList.entrySet()) {
            System.out.printf("\nПользователь %s. \n\tДобавленные товары: %n", listEntry.getKey().name);
            for (int j = 0; j < listEntry.getValue().size(); j++) {
                System.out.printf(messageTemplate,
                        listEntry.getValue().get(j).productName,
                        listEntry.getValue().get(j).price);
                personSum = personSum + listEntry.getValue().get(j).price;
            }
            System.out.printf("Итого: %.2f" + " " + RubFormat.getRub(personSum) + "\n", personSum);
            totalSum = totalSum + personSum;
            personSum = 0;
        }
        System.out.printf("\nСумма всего чека: %.2f %n" + RubFormat.getRub(totalSum), totalSum);
    }

}

