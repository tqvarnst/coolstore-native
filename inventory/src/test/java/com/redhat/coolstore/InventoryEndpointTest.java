package com.redhat.coolstore;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
public class InventoryEndpointTest {

    @Test
    void testRetrieveOne() {
        given()
                .when()
                    .get("/api/inventory/329299")
                .then()
                    .statusCode(200)
                    .body("prodId",equalTo("329299"))
                    .body("quantity",equalTo(200));
    }

    @Test
    void testRetrieveNoneExisting() {
        given()
                .when()
                .get("/api/inventory/1234567890")
                .then()
                .statusCode(204);
    }

    @Test
    void testUpdateQuanity() {
        given()
                .when()
                    .put("/api/inventory/329299/-10")
                .then()
                    .statusCode(200)
                    .body("prodId",equalTo("329299"))
                    .body("quantity",equalTo(190));
        given()
                .when()
                    .get("/api/inventory/329299")
                .then()
                    .statusCode(200)
                    .body("prodId",equalTo("329299"))
                    .body("quantity",equalTo(190));

        given().when().put("/api/inventory/329299/10").then().statusCode(200);
    }

}