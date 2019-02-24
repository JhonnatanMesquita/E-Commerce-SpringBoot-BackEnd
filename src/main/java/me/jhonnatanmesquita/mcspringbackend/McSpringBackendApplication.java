package me.jhonnatanmesquita.mcspringbackend;

import me.jhonnatanmesquita.mcspringbackend.dao.CategoriaDao;
import me.jhonnatanmesquita.mcspringbackend.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class McSpringBackendApplication implements CommandLineRunner {

    @Autowired
    private CategoriaDao categoriaDao;

    public static void main(String[] args) {
        SpringApplication.run(McSpringBackendApplication.class, args);
    }

    @Override
    public void run (String... args) throws Exception{
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");

        categoriaDao.saveAll(Arrays.asList(cat1,cat2));
    }
}
