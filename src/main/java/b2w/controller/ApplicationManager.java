package b2w.controller;

import b2w.model.Planet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Component;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que contém a lógica de acesso ao banco de dados SQLite embutido
 */
@Component("appManager")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ApplicationManager {

    /**
     * Logger da classe
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationManager.class.getName());

    /**
     *
     */
    @Autowired
    @Qualifier("sqliteUrl")
    private String sqliteUrl;


    /**
     *
     */
    private Connection conn;

    /**
     *
     **/
    public ApplicationManager() {
    }


    /**
     * Dada a conexão com o banco checa se existe a tabela "planets"
     * @param conn
     */
    public void initialize(Connection conn){
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS planets (nome TEXT, " +
                                                                 "clima TEXT, " +
                                                                 "terreno TEXT)");
        } catch (SQLException sqlException) {
            LOGGER.error("Failed to create planets table!", sqlException);
        }
    }

    /**
     * Inicializa a conexão com o banco de dados
     * @return
     */
    public Connection getConn () {
        try {
            if (conn == null) {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection(sqliteUrl);
                LOGGER.info("Connected to the database!");
            }
            initialize(conn);
            return conn;
        } catch(Exception e){
            LOGGER.error("Failed to connect to the database!", e);
        }

        return null;
    }


    /**
     * Busca todos os planetas cadastrados e os coverte para serem apresentados como
     * uma única string
     * @return
     * @throws SQLException
     */
    public String listPlanets() throws SQLException {
        java.lang.String sqlCommand = "SELECT rowid, nome, clima, terreno FROM planets";

        Statement statement = getConn().createStatement();
        ResultSet result = statement.executeQuery(sqlCommand);

        List<Planet> planetList = new ArrayList<>();

        while (result.next()){
            String id = result.getString("rowid");
            String nome = result.getString("nome");
            String clima = result.getString("clima");
            String terreno = result.getString("terreno");

            planetList.add(new Planet(id, nome, clima, terreno));
        }

        if(planetList.isEmpty()){
            LOGGER.warn("Planets List is empty!");
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Planet p : planetList){
            sb.append(p.toJSON().toString());
        }

        return sb.toString();
    }

    /**
     * Cadastra um novo planeta na tabela planets
     * @param nome
     * @param clima
     * @param terreno
     * @return
     */
    public boolean createNewPlanet(String nome, String clima, String terreno){
        try {
            String sqlCommand = "INSERT INTO planets (nome, clima, terreno) VALUES (?, ?, ?)";
            PreparedStatement statement = getConn().prepareStatement(sqlCommand);

            statement.setString(1, nome);
            statement.setString(2, clima);
            statement.setString(3, terreno);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }

            LOGGER.warn("Failed to insert new planet!");
            return false;

        } catch(SQLException sqex){
            LOGGER.error("Failed to execute SQL command!", sqex);
            return false;
        }
    }

    /**
     * Dado um nome retorna o planeta correspondente
     * @param nome
     * @return
     */
    public Planet getPlanetByName(String nome) {
        try {
            String sqlCommand = "SELECT rowid, nome, clima, terreno FROM planets WHERE nome=?";

            PreparedStatement statement = getConn().prepareStatement(sqlCommand);
            statement.setString(1, nome);
            ResultSet result = statement.executeQuery();

            if (null == result) {
                LOGGER.warn("Planet not found! Nome=" + nome);
                return null;
            }

            return new Planet(result.getString("rowid"),
                              result.getString("nome"),
                              result.getString("clima"),
                              result.getString("terreno"));

        } catch (SQLException sqex) {
            LOGGER.error("Failed to execute SQL command!", sqex);
            return null;
        }
    }

    /**
     * Dado um id retorna o planeta correspondente
     * @param id
     * @return
     */
    public Planet getPlanetByID(String id) {
        try {
            String sqlCommand = "SELECT rowid, nome, clima, terreno FROM planets WHERE rowid=?";

            PreparedStatement statement = getConn().prepareStatement(sqlCommand);
            statement.setString(1, id);
            ResultSet result = statement.executeQuery();

            if (null == result) {
                LOGGER.error("Planet not found! ID=" + id);
                return null;
            }

            return new Planet(result.getString("rowid"),
                              result.getString("nome"),
                              result.getString("clima"),
                              result.getString("terreno"));

        } catch(SQLException sqex){
            LOGGER.error("Failed to execute SQL command!", sqex);
            return null;
        }
    }

    /**
     * Dado um id remove o planeta correspondente
     * @param id
     * @return
     */
    public boolean deletePlanetByID(String id) {
        try {
            String sql = "DELETE FROM planets WHERE rowid=?";

            PreparedStatement statement = getConn().prepareStatement(sql);
            statement.setString(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                LOGGER.info("Planet deleted! ID=" + id);
                return true;
            }
            return false;
        } catch (SQLException sqex){
            LOGGER.error("Failed to execute SQL command!", sqex);
            return false;
        }
    }
}
