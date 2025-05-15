package Homeworks;

import ch.qos.logback.core.net.server.Client;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class MainHomework {

    public static void main(String[] args) {

        // ho creato oggetti di tipo Product
        Product p1 = new Product(123, "Cado malato", 0.99, "Books");
        Product p2 = new Product(1234, "Cado Overflowato", 104.99, "Books");
        Product p3 = new Product(12345, "Cado con la corda", 99.99, "Boys");
        Product p4 = new Product(123456, "Cado in depressione", 104.00, "Boys");
        Product p5 = new Product(1234567, "Cado senza rialzarmi ", 200, "Baby");
        Product p6 = new Product(12345678, "Cado investito da un camion", 0.99, "Baby");

        // ho creato oggetti di tipo Customer
        Customer c1 = new Customer(1, "SIGNORA I LIMONI", 1);
        Customer c2 = new Customer(2, "Ottaviano", 2);
        Customer c3 = new Customer(3, "Gattopardo", 1);

        // ho creato oggetti di tipo order

        Order o1 = new Order(1, "spedito", LocalDate.of(2021, 2, 15), LocalDate.of(2021, 2, 10), List.of(p1, p2, p3), c1);
        Order o2 = new Order(2, "spedito", LocalDate.of(2021, 3, 20), LocalDate.of(2021, 3, 13), List.of(p1, p3, p6), c2);
        Order o3 = new Order(3, "in attesa", LocalDate.of(2021, 12, 3), LocalDate.of(2021, 11, 24), List.of(p2, p5, p6), c3);

        //creo una lista di prodotti
        List<Product> products = List.of(p1, p2, p3, p4, p5, p6);
        // creo una lista di ordini
        List<Order> orders = List.of(o1, o2, o3);

        /*
        * Esercizio #1
        Raggruppare gli ordini per cliente utilizzando Stream e Lambda Expressions.
        * Crea una mappa in cui la chiave è il cliente e il valore è una lista di ordini effettuati da quel cliente
        */

        Map<Customer, List<Order>> ordiniPerCliente = orders.stream().collect(Collectors.groupingBy(order -> order.getCustomer()));

        ordiniPerCliente.forEach((cliente, ordini) -> {
            System.out.println(cliente);
            System.out.println(ordini);
            System.out.println("------------------");
        });
        System.out.println(ordiniPerCliente);

        //Esercizio #2

        /* Esercizio #2
        Dato un elenco di ordini, calcola il totale delle vendite per ogni cliente utilizzando Stream e Lambda Expressions.
        Crea una mappa in cui la chiave è il cliente e il valore è l'importo totale dei suoi acquisti
         */

        Map<Customer, Double> totalePerCliente = orders.stream().collect(Collectors.groupingBy(order -> order.getCustomer(),
                Collectors.summingDouble(order -> order.getProducts().stream().mapToDouble(product -> product.getPrice()).sum())));

        System.out.println("ESERCIZIO 2" + totalePerCliente);

        //Esercizio #3
        /* Esercizio #3
        Dato un elenco di prodotti, trova i prodotti più costosi utilizzando Stream e Lambda Expressions
        */
        // Trova il prezzo massimo
        double prezzoPiuAlto = products.stream().collect(Collectors.summarizingDouble(Product::getPrice)).getMax();
        System.out.println("ESERCIZIO 3" + "Costo piu alto = " + prezzoPiuAlto);

        // Esercizio #4
        /*Esercizio #4
        Dato un elenco di ordini, calcola la media degli importi degli ordini utilizzando Stream e Lambda Expressions
         */
        OptionalDouble mediaPrezzi = orders.stream().mapToDouble(order -> order.getProducts()
                .stream().mapToDouble(product -> product.getPrice()).sum()).average();

        System.out.println("ESERCIZIO 4" + "Media Prezzi = " + mediaPrezzi);
        // Esercizio #5
        /*
        * Dato un elenco di prodotti, raggruppa i prodotti per categoria
        *  e calcola la somma degli importi per ogni categoria utilizzando Stream e Lambda Expressions.*/

       Map<String,Double> sommaPerCategoria=products.stream().collect(Collectors.groupingBy(Product::getCategory,Collectors.summingDouble(Product::getPrice)));

        System.out.println("Sono esercizio 5 "+sommaPerCategoria);
    }

}