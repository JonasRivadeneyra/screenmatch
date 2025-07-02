package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.principal.Principal;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }




	//metodos
    @Override
    public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraElMenu();
//        EjemploStreams ejemploStreams = new EjemploStreams();
//        ejemploStreams.muestraEjemplo();

    }



}
