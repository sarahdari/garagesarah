package org.acme.resource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.acme.Auto;
import org.acme.database.DBList;
import org.acme.database.DbMongo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.CoreMatchers.is;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestHTTPEndpoint(GarageResource.class)
public class GarageResourceTest {

	@Inject
   DbMongo garage;

    @Test
    @Order(1)
    void TestGetGarage() {
        given().
                when()
                .get().
                then()
                .statusCode(is(200));

    }

    @Test
    @Order(2)
    public void testAggiungiAuto() {
       RestAssured.given().contentType("application/json").body(new Auto("bianco", "a1", "audi"))
                .when().post()
                .then()
                .statusCode(204);
    }



    @Test
    @Order(3)
    public void TestCambiaAuto() {
        RestAssured.given().contentType("application/json").body(new Auto("verde", "punto", "fiat"))
                .when().put("/1")
                .then().statusCode(204);
    }

    @Test
    @Order(3)
    void cercaColoreAuto() {
        given()
                .when().put("auto/1/verde")
                .then().statusCode(204);


    }


    @Test
    @Order(4)
    void rimuoviAuto() {
        given()
                .when().delete("/garage/1")
                .then().statusCode(404);
    }

    @Test
    @Order(3)
    void cambiaColoreAuto() {
    }
}