package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        //MOSTRAR SOLO EL TITULO DE LOS EPISODIOS PARA LAS temporadas

        temporadas.forEach(t-> t.episodios().forEach(e->
                System.out.println(e.titulo())));





















    }
}
