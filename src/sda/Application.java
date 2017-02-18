package sda;

import sda.workers.AbstractEmployee;
import sda.workers.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by RENT on 2017-02-18.
 */
public class Application {
    public static void main(String[] args) {
        List<AbstractEmployee> employeesLista = new ArrayList<>();
        employeesLista.add(new AbstractEmployee("Sebastian", "Piechowski", 4000, "JAVA"));
        employeesLista.add(new AbstractEmployee("Anna", "Nowak", 3000, "HR"));
        employeesLista.add(new AbstractEmployee("Karolina", "Milczek", 6000, "JAVA"));
        employeesLista.add(new AbstractEmployee("Andrzej", "Duda", 4000, "PM"));
        employeesLista.add(new AbstractEmployee("Sabrina", "Wodczak", 6000, "DIRECTOR"));

        employeesLista.stream()
                .filter(e -> e.getDepartment().equals("JAVA"))
                .forEach(e -> System.out.println(e));

        employeesLista.stream()
                .filter(e -> e.getFirstName().endsWith("a"))
                .forEach(e -> System.out.println(e));
        System.out.println();

        employeesLista.stream()
                .filter(e -> e.getSalary()>4000)
                .filter(e -> e.getDepartment().equals("JAVA"))
                .forEach(e -> System.out.println(e));

        List<AbstractEmployee> javaEmployess = employeesLista.stream()
                .filter(e -> e.getDepartment().equals("JAVA"))
                .collect(Collectors.toList());
        System.out.println(javaEmployess);
        System.out.println();

        employeesLista.stream()
                .filter(e -> e.getLastName().equals("Nowak"))
                .forEach(e -> System.out.println(e));
        System.out.println();

        employeesLista.stream()
                .filter(e -> e.getLastName().startsWith("P"))
                .forEach(e -> System.out.println(e));
        System.out.println();

        Map<String, AbstractEmployee> map = employeesLista.stream()
                .collect(Collectors.toMap(e -> e.getFirstName(), e -> e));
        map.forEach((k,v) -> System.out.println(k + ": " + v));
        System.out.println();

        //posortuj po salary
        employeesLista.sort((e1, e2) -> e1.getSalary() > e2.getSalary() ? 1 : -1);
        employeesLista.forEach(e -> System.out.println(e.getFirstName() + ": " + e.getSalary()));
        System.out.println();

        //zwaracmy liste Employee szukanej po imieniu
        employeesLista.stream()
                .filter(e -> (e.getFirstName() + " " + e.getLastName()).equals("Sebastian Piechowski"))
                .forEach(e -> System.out.println(e));

        //wyswietl employee ktory zarabia najwiecej
        System.out.println();
        System.out.println("Najwieksze zarobki");
        employeesLista.sort((e1, e2) -> e1.getSalary() < e2.getSalary() ? 1 : -1);
        System.out.println(employeesLista.get(0));
        System.out.println();

        AbstractEmployee richestEmployee = employeesLista.stream()
                .max((e1, e2) -> e1.getSalary() > e2.getSalary() ? 1 : -1)
                .get();

        System.out.println();
        System.out.println("najmniejsze zarobki");
        Map<String, List<AbstractEmployee>> map2 = listToMap(employeesLista);
        List<AbstractEmployee> tmpList = new ArrayList<>();
        map2.entrySet().stream()
                .forEach(e -> {
                    List<AbstractEmployee> value = e.getValue();
                    value.stream()
                            .filter(e1 -> e1.getSalary() >= 3000)
                            .forEach(e1 -> tmpList.add(e1));
                });
        System.out.println(tmpList);
    }

    public static Map<String, List<AbstractEmployee>> listToMap(List<AbstractEmployee> employees) {
        Map<String, List<AbstractEmployee>> map = new HashMap<>();
        for (AbstractEmployee employee : employees) {
            if (map.containsKey(employee.getDepartment())) {
                List<AbstractEmployee> listOfEmpleyes = map.get(employee.getDepartment());
                listOfEmpleyes.add(employee);
            } else {
                ArrayList<AbstractEmployee> listOfEmployess = new ArrayList<>();
                listOfEmployess.add(employee);
                map.put(employee.getDepartment(), listOfEmployess);
            }
        }
        return map;
    }
}
