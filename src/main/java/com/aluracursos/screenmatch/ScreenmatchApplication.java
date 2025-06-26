package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.service.ConsumoAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consultaApi = new ConsumoAPI();
		var json = consultaApi.obtenerDatos(
				"https://www.omdbapi.com/?t=game+of+thrones&apikey=508ff5b0");
		System.out.println(json);

	}



}
