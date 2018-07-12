package org.edviz.trenapp;

import org.edviz.trenapp.service.GraphRunTask;
import org.edviz.trenapp.utils.Constants;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrenappApplication implements CommandLineRunner {

	public static void main(String[] args) {
		// SpringApplication.run(TrenappApplication.class, args);
		SpringApplication app = new SpringApplication(TrenappApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
//		GrafoService gs = new GrafoService();
//		gs.crearGrafoDirecto();
//		gs.imprimirGrafo();
//		gs.ejecutarOperaciones();
		
		GraphRunTask operations = new GraphRunTask(Constants.filename);
		operations.runTasks();
	}
}
