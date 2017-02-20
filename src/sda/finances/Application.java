package sda.finances;

import sda.finanse.model.Expense;
import sda.finanse.model.Product;

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
                            .filter(product -> product.getUnitPrice() <=3)
                            .forEach(product -> System.out.println(product));
                });
        //3. wyswietlamy tylko banany
        //4. suma cen wszystkich produktow spozywczych -


    }

    private static List<Expense> init() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("piwo", 1, 2.99));
        products.add(new Product("chleb", 1, 2.49));
        products.add(new Product("RedBull", 1, 4.99));
        products.add(new Product("fajki", 1, 15.70));
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
