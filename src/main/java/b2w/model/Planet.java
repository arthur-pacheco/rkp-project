package b2w.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class Planet {

    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Planet.class.getName());


    /**
     *
     */
    private String id;

    /**
     *
     */
    private String nome;

    /**
     *
     */
    private String clima;

    /**
     *
     */
    private String terreno;

    /**
     *
     * @param nome
     * @param clima
     * @param terreno
     */
    public Planet(String id, String nome, String clima, String terreno){
        this.id = id;
        this.nome = nome;
        this.clima = clima;
        this.terreno = terreno;
    }

    /**
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @return
     */
    public String getClima() {
        return clima;
    }

    /**
     *
     * @param clima
     */
    public void setClima(String clima) {
        this.clima = clima;
    }

    /**
     *
     * @return
     */
    public String getTerreno() {
        return terreno;
    }

    /**
     *
     * @param terreno
     */
    public void setTerreno(String terreno) {
        this.terreno = terreno;
    }

    /**
     *
     * @return
     */
    public String toString(){
        String str = "|" +
                     "ID: "      + id      +   "\n" +
                     "Nome: "    + nome    +   "\n" +
                     "Clima: "   + clima   +   "\n" +
                     "Terreno: " + terreno +   "\n" +
                     "|";

        return str;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * @throws JSONException
     */
    public JSONObject toJSON() {
        try {
            JSONObject objJSON = new JSONObject()
                    .put("id", getId())
                    .put("nome", getNome())
                    .put("clima", getClima())
                    .put("terreno", getTerreno());
            return objJSON;
        } catch(Exception e){
            LOGGER.error("Failed to convert planet to JSON!", e);
            return new JSONObject();
        }


    }
}
