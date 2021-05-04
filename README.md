# Registration of Known Planets (RKP)

### Para realizar o build do sistema com o maven basta executar o comando abaixo:
* **mvn clean install**
- Durante o build serão executados os testes unitários.

### Após o build para executar o serviço basta executar o comando abaixo:
* **java -jar target/registration-known-planets-b2w.jar**

### Endpoints expostos pela REST API
* /rkp/listPlanets GET
* /rkp/createNewPlanet?nome=&clima=&terreno= POST
* /rkp/getPlanetByName?nome= GET
* /rkp/getPlanetByID?id= GET
* /rkp/deletePlanetByID?id= POST
