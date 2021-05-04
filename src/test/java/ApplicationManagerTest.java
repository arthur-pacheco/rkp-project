import b2w.controller.ApplicationManager;
import b2w.model.Planet;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class ApplicationManagerTest {

    @Autowired
    private ApplicationManager manager;

    @Test
    public void testCreateNewPlanet(){
        boolean v = manager.createNewPlanet("Tatooine", "Arid", "Dessert");
        assertTrue(v);
    }

    @Test
    public void testGetPlanetByName(){
        String name = "Alderaan";
        manager.createNewPlanet(name , "Arid", "Dessert");
        Planet planet = manager.getPlanetByName("Alderaan");
        assertEquals(name, planet.getNome());
    }

    @Test
    public void testGetPlanetByID(){
        manager.createNewPlanet("Yavin IV" , "Arid", "Dessert");
        Planet planet = manager.getPlanetByName("Yavin IV");
        Planet planetByID = manager.getPlanetByID(planet.getId());
        assertEquals(planet.getNome(), planetByID.getNome());
    }

    @Test
    public void testDeletePlanetByID(){
        manager.createNewPlanet("Kamino" , "Temperate", "Ocean");
        Planet planet = manager.getPlanetByName("Kamino");
        boolean v = manager.deletePlanetByID(planet.getId());
        assertTrue(v);
    }

    @Test
    public void testListPlanets() throws SQLException {
        String list = manager.listPlanets();
        assertNotNull(list);
    }
}
