package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();

    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String APY_KEY = "&apikey=508ff5b0"; //no debe ser visible
    private ConvierteDatos convierteDatos = new ConvierteDatos();





    //METODOS
    public void muestraElMenu() {
        System.out.println("Ingrese el nombre de la serie que desea buscar");
        //Busca los datos generales de las series
        var nombreSerie = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL_BASE +
                nombreSerie.replace(" ", "+") + APY_KEY);

        var datos = convierteDatos.obtenerDatos(json, DatosSerie.class);//opbjeto de datosSerie
        System.out.println(datos);

        //Busca los datos de toda las temporadas

        List<DatosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+")
                    + "&Season=" +i+ APY_KEY);
            var datosTemporadas = convierteDatos.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);

        }
        //temporadas.forEach(System.out::println);
        System.out.println(" ");

        //MOSTRAR SOLO EL TITULO DE LOS EPISODIOS PARA LAS TEMPORADAS

        temporadas.forEach(t-> t.episodios().forEach(e->
                System.out.println(e.titulo())));

        //convertir todas las informaciones a una lista del tipo datios episodio

        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());




        //top 5 episodios

//        System.out.println("Top 5 Episodios");
//
//        datosEpisodios.stream()
//                .filter(e-> !e.evaluacion().equalsIgnoreCase("N/A"))
//                .peek(e-> System.out.println("Primer filtro N/A" + e))
//                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//                .peek(e-> System.out.println("Segundo ordenacion (M>m)" + e))
//                .map(e->e.titulo().toUpperCase())
//                .peek(e-> System.out.println("Tercer Filtro Mayusculas (m>m)" + e))
//                .limit(5)
//                .forEach(System.out::println);


        //convirteiendo los datos a una lista del tipo epísodio

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d-> new Episodio(t.numero() ,d)))

                .collect(Collectors.toList());

     //   episodios.forEach(System.out::println);



//BUSQUEDA DE EPISODIOS POR AÑO
//        System.out.println("A partir de que año desea ver los episodios");
//        var fecha = teclado.nextInt();
//        teclado.nextLine();

        //
//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e->e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
//                .forEach(e-> System.out.println("Temporada " + e.getTemporada()+
//                        "Episodio " + e.getTitulo() +
//                        "Fecha de Lanamiento" + e.getFechaDeLanzamiento().format(dtf)
//                ));

        //Busca episodio por fragmentto d etitulo
//        System.out.println("Escriba el pedazo de titulo del episodio que desea vere");
//        var pedazoTitulo = teclado.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
//                .findFirst();
//        if (episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado");
//            System.out.println("los Datos son:  " +episodioBuscado.get());
//        }else {
//            System.out.println("Episodio no encontrado");
//        }

        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e->e.getEvaluacion()>0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println(evaluacionesPorTemporada);
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));

        System.out.println("Media de las evaluaciones: " + est.getAverage());
        System.out.println("Episodio Mejor valorado: "+est.getMax());
        System.out.println("Episodio peor evaluado:  "+est.getMin());





























    }
}
