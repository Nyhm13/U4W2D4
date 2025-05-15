package stream;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ProvaStream {
    public static void main(String[] args) {

        Product p1 = new Product(1L, "Il signore degli Anelli", "Books", 10.0);
        Product p2 = new Product(2L, "Il nome della rosa", "Books", 101.0);
        Product p3 = new Product(3L, "pannolini", "Baby", 4.0);
        Product p4 = new Product(4L, "maglietta", "Baby", 7.0);
        Product p5 = new Product(5L, "carrozzina", "Baby", 70.0);
        Product p6 = new Product(6L, "scarpe", "Boys", 60.0);
        Product p7 = new Product(7L, "pantalone", "Boys", 30.0);
        Product p8 = new Product(8L, "felpa", "Boys", 30.0);
//        Product p9 = new Product(8L, "felpa", "Boys", 30.0);

        List<Product> prodotti = List.of(p1, p2, p3, p4, p5, p6, p7, p8);
        // uso map perche voglio trasformare un oggetto in un altro

        List<Double> l1 = prodotti.stream().map(product -> product.getPrice()).collect(Collectors.toList());

        System.out.println(l1);

        // a questo giro la lista non ha valori duplicati e l`ordine non è garantito a  quello della lista originale
        //perche le
        Set<Double> l2 = prodotti.stream().map(product -> product.getPrice()).collect(Collectors.toSet());

        System.out.println("------------------");
        System.out.println(l2);
        System.out.println("------------------");

        // il set per vedere se un oggetto  e uguale ad un altro usa il metodo equals
        // l`equals che prende è quello della classe object che vede l`indirizzo di memoria
        // per fare in modo che il set non prenda i duplicati dobbiamo sovrascrivere il metodo equals e hashcode
        // in product su quello che vogliamo, nel nostro caso lo abbiamo fatto con id
        Set<Product> l3 = prodotti.stream().collect(Collectors.toSet());
        System.out.println(l3);
        System.out.println("------------------");

        // voglio creare una mappa con nome del prodotto e relativo prezzo

        // il toMap mi crea una mappa  ma devo fare capire allo stream cosa mettere come chiave e cosa come valore
        // la chiave non puo avere duplicati ma noi abbiamo il duplicato del nome  del prodotto p8 e p9
        /* se vogliamo creare una mappa da un metodo stream, devo indicare al metodo toMap, quale sarà il campo chiave
        e quale sara il campo valore . Il campo chiave non deve avere duplicati altrimento lo stream andrà in errore
        */
        Map<String, Double> l4 = prodotti.stream().collect(Collectors.toMap(product -> product.getName(), product -> product.getPrice()));

        System.out.println(l4);

        System.out.println("------------------");

        // vorrei una mappa delle categorie e ad ogni categoria,la lista dei prodotti associati, lo posso fare con il
        //metodo groupingBy che mi ritorna una mappa( )
        /* la groupingBy mi permette di raggruppare i prodotti nello stream in base ad una caratteristica.
         * In questo caso abbiamo raggrupato per categoria. groupingBY crea una mappa con chiave la categoria
         *  e come valori una lista di prodotti che appartengono a quella categoria
         *  */
        Map<String, List<Product>> l5 = prodotti.stream().collect(Collectors.groupingBy(product -> product.getCategory()));

        System.out.println(l5);

        System.out.println("------------------");
        // per ogni categoria voglio il conteggio dei prodotti di quella categoria

        /* la grouping by puo prendere anche un secondo parametro che indica cosa voglio come valori,In questo caso
         * sono interessato a contare gli elementi che fanno parte di quella categoria e quindi uso Collectors.counting
         * che prende e conta quanti sono i prodotti di quella categoria e li passa come valore alla chiave della mappa
         * */
        Map<String, Long> l6 = prodotti.stream().collect(Collectors.groupingBy(product -> product.getCategory(), Collectors.counting()));

        System.out.println(l6);

        System.out.println("------------------");


        //voglio creare una mappa di categorie e somme dei prezzi dei prodotti di quella categoria
        /* in questo caso la chiave è la categoria e il valore è la somma dei prezzi dei prodotti di quella categoria
         * per fare questo uso Collectors.summingDouble che mi calcola la somma dei prezzi dei prodotti di quella categoria
         * il Collectors.groupingBy mi ritorna una mappa con chiave la categoria e come valore la somma dei prezzi
         * */

        Map<String, Double> l7 = prodotti.stream().collect(Collectors.groupingBy(product -> product.getCategory(),
                Collectors.summingDouble(Product::getPrice)));

        System.out.println(l7);

        System.out.println("------------------");
        // voglio creare una mappa di categorie e medie dei prezzi per  categoria
        // per fare cio uso Collectors.averagingDouble che mi calcola la media dei prezzi dei prodotti di quella categoria

        Map<String, Double> l8 = prodotti.stream().collect(Collectors.groupingBy(product -> product.getCategory(),
                Collectors.averagingDouble(Product::getPrice)));

        System.out.println(l8);

        System.out.println("------------------");

        //metodo calcolare la somma dei prezzi prezzi dei prodotti Collectors.summingDouble

        System.out.println(prodotti.stream().collect(Collectors.summingDouble(Product::getPrice)));

        System.out.println("------------------");

        // metodo calcolare la media  dei prezzi dei prodotti Collectors.averagingDouble
        // il metodo averagingDouble mi permette di calcolare la media dei prezzi dei prodotti

        System.out.println(prodotti.stream().collect(Collectors.averagingDouble(Product::getPrice)));

        System.out.println("------------------");

        // metodo sumarizingDouble
        // il metodo summarizingDouble mi permette di calcolare la somma, la media, il minimo e il massimo dei prezzi
        //di una lista di prodotti

        DoubleSummaryStatistics stat = prodotti.stream().collect(Collectors.summarizingDouble(product -> product.getPrice()));

        System.out.println("CIAO SONO STAT MAX"+ stat.getMax());


        System.out.println("------------------");

        //partitioningBy permette di ..... da finire
        // vogliamo creare una mappa per sapere quali prodotti hanno un prezzo inferiore a 50 e quali superiore
        /*
         * partitioningBy CREA una mappa in cui le chiavi sono booleani , quindi solo 2 coppie di chiave valore,
         * dove le coppie sono true e false e i valori sono delle liste di prodotti che verificano la condizione inserita nel
         * partitioningBy*/

        Map<Boolean, List<Product>> l9 = prodotti.stream().collect(Collectors.partitioningBy(product -> product.getPrice() < 50));
        System.out.println(l9);

        System.out.println("------------------");
        // voglio calcolare la somma dei prezzi dei prodotti usando il metodo reduce
        // abbiamo usato .map per trasformare la lista di prodotti in una lista di prezzi
        // e poi abbiamo usato il metodo reduce per sommare i prezzi
        // nel metodo reduce lo 0.0 e il valore iniziale della somma mentre prezzo è il valore del prezzo di ogni prodotto
        // a ogni iterazione il prezzo viene sommato alla somma


        Double sommaTotale = prodotti.stream().map(Product::getPrice).collect(Collectors.reducing(0.0, (somma, prezzo) -> somma + prezzo));

        System.out.println(sommaTotale);
        System.out.println("------------------");

        //mettiamo che voglio mettere sotto forma di stringa tutti i prodotti che stanno nella lista
        // per fare questo uso il metodo Collectors.joining che mi permette di unire gli elementi di una lista in una stringa
        // se lo usiamo senza passare nulla o possiamo passare nelle ( ) un separatore come ad esempio la virgola
        /*PROFF:
         * */

        String joiningStringhe = prodotti.stream().map(product -> product.getId() + "@" + product.getName() + "@" + product.getCategory() + "@" + product.getPrice()).collect(Collectors.joining(","));

        System.out.println(joiningStringhe);

        System.out.println("------------------");

        // calcolare la somma dei prezzi dei prodotti
        // per calcolare la somma dei prezzi dei prodotti posso usare il metodo mapToDouble
        // che mi permette di trasformare la lista di prodotti in una lista di prezzi e poi usare il metodo sum
        // il metodo sum mi permette di sommare i prezzi dei prodotti
        // ed e il metodo piu veloce per calcolare la somma dei prezzi dei prodotti
        // il metodo mapToDouble mi ritorna una mappa di prezzi e il metodo sum mi permette di sommare i prezzi
        //questa mappa mi ritorna un oggetto DoubleStream che è una lista di prezzi

        /* Proff: mapToDouble permette un map con conversione di tipo automatica e sopratutto permette di usare dei
        metodi esclusivi che sono sum,avg,max,min, che non potrei avere se usassi map
        * */

        prodotti.stream().mapToDouble(product -> product.getPrice()).sum();


        File file = new File("prova.txt");
        // con append true posso scrivere su un file senza sovrascrivere il file
        // se non metto append true il file viene sovrascritto

        try {
            FileUtils.writeStringToFile(file, joiningStringhe, "UTF-8", true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
           String stringaDaFile= FileUtils.readFileToString(file, "UTF-8");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
