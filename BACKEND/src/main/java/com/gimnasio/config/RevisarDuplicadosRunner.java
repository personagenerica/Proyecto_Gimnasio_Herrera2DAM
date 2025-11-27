package com.gimnasio.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.gimnasio.service.LimpiarDuplicadosService;

@Component
public class RevisarDuplicadosRunner implements CommandLineRunner {

    private final LimpiarDuplicadosService limpiarDuplicadosService;

    public RevisarDuplicadosRunner(LimpiarDuplicadosService limpiarDuplicadosService) {
        this.limpiarDuplicadosService = limpiarDuplicadosService;
    }

    @Override
    public void run(String... args) throws Exception {
    	limpiarDuplicadosService.limpiarDuplicados();
    }
}
