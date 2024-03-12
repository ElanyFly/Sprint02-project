import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class Calculator {
    Scanner scanner = new Scanner(System.in);
    HashMap<User, ArrayList<ProductBought>> billList = new HashMap<>();

    public void startCalculator() {

        int peopleCount = askPeopleCount();

        for (int i = 1; i <= peopleCount; i++) {
            executeMenu(i);
        }
    }

    private void executeMenu(int personCount) {

        User user = userInput(personCount);

        ArrayList<ProductBought> productsList = new ArrayList<>();

        while (true) {

            System.out.println("\nДля добавления товара, введите: Добавить");
            System.out.println("Для завершения добавления товаров пользователя, введите: Завершить");

            String command = scanner.next();

            if (command.toLowerCase().contains("добавить")) {
                productsList.add(executeAddProducts());
            } else if (command.toLowerCase().contains("печать")) {
                printArray(productsList);
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
        int peopleCount= 0;
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
//            try {
//                System.out.println("Введите цену:");
//                productPrice = scanner.nextDouble();
//            } catch (RuntimeException e) {
//                scanner.next();
//                System.out.println("Неправильно введена стоимость. Попробуйте снова.");
//            }
        } while (productPrice <= 0);

        System.out.println("Товар \"" + productName + "\" успешно добавлен");
        return new ProductBought(productName, productPrice);
    }

    private void printArray(ArrayList<ProductBought> productList) {
        for (int i = 0; i < productList.size(); i++) {
            String getName = productList.get(i).productName;
            double getPrice = productList.get(i).price;
            System.out.println(getName + "\t" + getPrice);
        }
    }


}

