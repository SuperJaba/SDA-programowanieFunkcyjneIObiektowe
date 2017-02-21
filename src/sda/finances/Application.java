package sda.finances;

import sda.finanse.model.Expense;
import sda.finanse.model.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by RENT on 2017-02-20.
 */
public class Application {
    public static void main(String[] args) {
        List<Expense> expenses = init();
        //1. wyswietlic wszystkie towary ktorych cena jest mniejsza od 3
        expenses.forEach(e -> {
            e.getProducts().stream()
                    .filter(price -> price.getUnitPrice() <= 3)
                    .forEach(price -> System.out.println(price));
        });

        System.out.println();
        //2. sout tylko produktow spozywczych, cena mniejsza od 3
        expenses.stream()
                .filter(expense -> expense.getType().equals("spozywcze"))
                .forEach(expense -> {
                    expense.getProducts().stream()
                            .filter(product -> product.getUnitPrice() <= 3)
                            .forEach(product -> System.out.println(product));
                });
        //3. wyswietlamy tylko banany
        System.out.println();
        double banan = expenses.stream()
                .mapToDouble(expense -> expense.getProducts()
                        .stream()
                        .filter(product -> product.getName().equals("banan"))
                        .mapToDouble(product -> product.getUnitPrice() * product.getAmount())
                        .sum()
                )
                .sum();
        System.out.println(banan);

        //4. suma cen wszystkich produktow spozywczych -
        System.out.println();
        double spozywcze = expenses.stream()
                .filter(expense -> expense.getType().equals("spozywcze"))
                .mapToDouble(Expense::getPrice)
//                .forEach(System.out::println)
//                .forEach(expense -> System.out.println(expense)) //to samo co linijka wyzej
                .sum();
        System.out.println(spozywcze);

        //5. produkty kupione przed 19 lutym
        System.out.println();
        System.out.println("produkty kupione przed 19 lutym");
        expenses.stream()
                .filter(expense -> expense.getDate().isBefore(LocalDate.of(2017, 2, 19)))
                .forEach(expense -> expense.getProducts()
                        .forEach(product -> System.out.println(product)));

        //6. wyswietlic wydatki dla konkretnego dnia
        System.out.println();
        System.out.println("wydatki dla konkretnego dnia");
        System.out.println(expenses.stream()
                .filter(expense -> expense.getDate().isEqual(LocalDate.of(2017, 2, 17)))
                .mapToDouble(expense -> expense.getPrice())
                .sum());


        System.out.println("aaa"+expenses.stream()
                .filter(expense -> expense.getDate().isEqual(LocalDate.of(2017, 02, 17)))
                .mapToDouble(expanse -> expanse.getPrice())
                .sum());

        //6.1 wyswietlic wydatki na piwo dla konkretnego dnia
        //   (ilosc kupionych produktow, ilosc wydanych pieniedzy)
        System.out.println();
        System.out.println("ilosc kupionych produktow, ilosc wydanych pieniedzy");
        System.out.println(expenses.stream()
                .filter(expense -> expense.getDate().isEqual(LocalDate.of(2017, 2, 19)))
                .mapToDouble(expense -> expense.getProducts()
                        .stream()
                        .filter(product -> product.getName().equals("piwo"))
                        .mapToDouble(product -> product.getUnitPrice() * product.getAmount())
                        .sum())
                .sum());

        //7. zsumowac calkowita kwote wydana na produkty zaczynajace sie na 'p'
        System.out.println();
        System.out.println("zsumowac calkowita kwote wydana na produkty zaczynajace sie na 'p'");
        System.out.println(expenses.stream()
                .mapToDouble(expense -> expense.getProducts()
                        .stream()
                        .filter(product -> product.getName().startsWith("p"))
                        .mapToDouble(product -> product.getAmount() * product.getUnitPrice())
                        .sum())
                .sum());

        //8. zsumowac koszt produktow spozywczych, ktore kupilismy w parzystych ilosciach
        System.out.println();
        System.out.println("zsumowac koszt produktow spozywczych, ktore kupilismy w parzystych ilosciach");
        System.out.println(expenses.stream()
                .filter(expense -> expense.getType().equals("spozywcze"))
                .mapToDouble(value -> value.getProducts()
                        .stream()
                        .filter(product -> product.getAmount() % 2 == 0)
                        .mapToDouble(product -> product.getAmount() * product.getUnitPrice())
                        .sum())
                .sum());

        //9. z kazdego expensa wyswietlic produkt na ktorego wydalismy najwiecej (amount*unitPrice)
        System.out.println();
        System.out.println("z kazdego expensa wyswietlic produkt na ktorego wydalismy najwiecej (amount*unitPrice)");
        expenses.stream()
                .map(expense -> expense.getProducts()
                        .stream()
                        .max((e1, e2) ->
                                (e1.getUnitPrice() * e1.getAmount()) >
                                        (e2.getUnitPrice() * e2.getAmount()) ? 1 : -1)
                        .get())
                .forEach(produkt -> System.out.println(produkt));

        //10. wyswietlic najdrozszy produkt z wszystkich expensow
        System.out.println();
        System.out.println("wyswietlic najdrozszy produkt z wszystkich expensow");
        System.out.println(expenses.stream()
                .map(expense -> expense.getProducts()
                        .stream()
                        .max((e1, e2) ->
                                (e1.getUnitPrice() * e1.getAmount()) >
                                        (e2.getUnitPrice() * e2.getAmount()) ? 1 : -1)
                        .get())
                .max((e1, e2) ->
                        (e1.getUnitPrice() * e1.getAmount()) >
                                (e2.getUnitPrice() * e2.getAmount()) ? 1 : -1)
                .get());
    }

    private static List<Expense> init() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("piwo", 1, 2.99));
        products.add(new Product("chleb", 1, 2.49));
        products.add(new Product("RedBull", 1, 4.99));
        products.add(new Product("fajki", 1, 15.70));
        products.add(new Product("banan", 1, 4.5));
        Expense expense = new Expense("spozywcze", products);

        List<Product> products2 = new ArrayList<>();
        products2.add(new Product("farba", 1, 25));
        products2.add(new Product("mlotek", 2, 10));
        products2.add(new Product("gwozdzie", 100, 0.1));
        Expense expense2 = new Expense("budowlane", products2, 2017, 2, 19);

        List<Product> products3 = new ArrayList<>();
        products3.add(new Product("witamina C", 2, 13));
        products3.add(new Product("apap", 1, 10));
        products3.add(new Product("syrop na kaszel", 1, 5));
        Expense expense3 = new Expense("lekarstwa", products3, 2017, 2, 18);

        List<Product> products4 = new ArrayList<>();
        products4.add(new Product("banan", 2, 1.5));
        products4.add(new Product("cukier", 1, 4.99));
        products4.add(new Product("mieso", 1, 9.99));
        Expense expense4 = new Expense("spozywcze", products4, 2017, 2, 17);

        return Arrays.asList(expense, expense2, expense3, expense4);
    }

}
