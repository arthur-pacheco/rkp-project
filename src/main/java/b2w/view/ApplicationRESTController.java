package b2w.view;

import b2w.controller.ApplicationManager;
import b2w.model.Planet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


@RestController
@RequestMapping("/rkp")
public class ApplicationRESTController {


    /**
     * Logger da classe
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationRESTController.class.getName());


    /**
     * Controller ao qual esta view solicita os resultados
     */
    @Autowired
    @Qualifier("appManager")
    private ApplicationManager manager;

    /**
     * Busca uma lista dos planetas cadastrados
     * @return
     */
    @GetMapping(value = "/listPlanets")
    public ResponseEntity<String> listPlanets() throws SQLException {
       String planets = manager.listPlanets();
        if(!planets.isEmpty()) {
            LOGGER.info("List of planets: \n" + planets);
            return ResponseEntity.ok(planets);
        } else {
            LOGGER.warn("Empty planets list");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(planets);
        }
    }

    /**
     * Dados os parâmetros nome, clima e terreno cadastra um novo planeta no banco de dados
     * @param nome
     * @param clima
     * @param terreno
     * @return
     */
    @PostMapping(value = "/createNewPlanet")
    public ResponseEntity<Object> createNewPlanet(@RequestParam String nome,
                                                  @RequestParam String clima,
                                                  @RequestParam String terreno) throws SQLException {

        boolean isSucceeded = manager.createNewPlanet(nome, clima, terreno);

        if(isSucceeded) {
            LOGGER.info("Successfullly created planet! Nome=" +  nome + " Clima=" + clima + " Terreno=" + terreno);
            return ResponseEntity.ok("Successfullly created planet! Nome=" +  nome + " Clima=" + clima + " Terreno=" + terreno);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create planet! Nome=" +  nome + " Clima=" + clima + " Terreno=" + terreno);
        }
    }


    /**
     * Dado um nome busca o planeta correspondente
     * @param nome
     * @return
     */
    @GetMapping(value = "/getPlanetByName")
    public ResponseEntity<Object> getPlanetByName(@RequestParam String nome) throws SQLException {
        Planet planet = manager.getPlanetByName(nome);
        if(planet != null) {
            LOGGER.info("Get planet " + nome);
            return ResponseEntity.ok(planet.toJSON());
        } else {
            LOGGER.warn("Get planet failed " + nome);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("");
        }
    }

    /**
     * Dado um id busca o planeta correspondente
     * @param id
     * @return
     */
    @GetMapping(value = "/getPlanetByID")
    public ResponseEntity<Object> getPlanetByID(@RequestParam String id) throws SQLException {
        Planet planet = manager.getPlanetByID(id);
        if(planet != null) {
            LOGGER.info("Get planet " + id);
            return ResponseEntity.ok(planet.toJSON());
        } else {
            LOGGER.info("Get planet failed " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("");
        }
    }

    /**
     * Dado um id remove o planeta correspondente
     * @param id
     * @return
     */
    @PostMapping(value = "/deletePlanetByID")
    public ResponseEntity<String> deletePlanetByID(@RequestParam String id) {
        boolean isRemoved = manager.deletePlanetByID(id);

        if (!isRemoved) {
            LOGGER.info("Delete planet (" + id + ") failed");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info("Delete planet (" + id + ") succeeded");
        return ResponseEntity.status(HttpStatus.OK).body("Planet deleted = " + id);

    }


}
