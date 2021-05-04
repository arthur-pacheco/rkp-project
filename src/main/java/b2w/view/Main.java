package b2w.view;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication(scanBasePackages={
        "b2w.controller", "b2w.model", "b2w.spring.config", "b2w.view"})
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);

    }
}
