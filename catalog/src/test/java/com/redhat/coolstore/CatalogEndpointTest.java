package com.redhat.coolstore;

import com.redhat.coolstore.model.Product;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

@QuarkusTest
public class CatalogEndpointTest {


    @Inject
    ProductRepository repository;

    @Test
    public void testCDI() {
        assert repository!=null;
    }

    @Test
    void testRetriveAll() {
        given()
                .when().get("/api/products")
                .then()
                .statusCode(200)
                .body("prodId",hasItems("329299","329199","165613","165614","165954","444434","444435","444436"))
                .body("name",hasItems("Red Fedora","Forge Laptop Sticker","Solid Performance Polo","Ogio Caliber Polo","16 oz. Vortex Tumbler","Pebble Smart Watch","Oculus Rift","Lytro Camera"));
    }

    @Test
    void testGetById() {
        given()
                .when().get("/api/products/1")
                .then()
                .statusCode(200)
                .body("prodId",equalTo("329299"));
    }

    @Test
    void testCreateUpdateAndDelete() {
        Product coffeeMug = new Product();
        coffeeMug.setProdId("444444");
        coffeeMug.setName("Awesome Red Hat Coffee Mug");
        coffeeMug.setDescription("This coffee mug is a high quality stainless steal mug");
        coffeeMug.setPrice(100.0);

        Jsonb builder = JsonbBuilder.newBuilder().build();


        // Create
        int id = given()
                .when()
                    .contentType(ContentType.JSON)
                    .body(builder.toJson(coffeeMug))
                    .post("/api/products")
                .then()
                    .statusCode(200)
                .extract()
                    .response().jsonPath().get("id");

        // Verify Create
        given()
                .when()
                    .get("/api/products/" + id)
                .then()
                    .statusCode(200)
                    .body("prodId",equalTo("444444"))
                    .body("price",equalTo(100.0f));


        // Update

        coffeeMug.setPrice(50.0);
        given()
                .when()
                    .contentType(ContentType.JSON)
                    .body(builder.toJson(coffeeMug))
                    .put("/api/products/" + id)
                .then()
                    .statusCode(200)
                    .body("prodId",equalTo("444444"))
                    .body("price",equalTo(50.0f));


        // Delete
        given()
                .when()
                    .delete("/api/products/" + id)
                .then()
                    .statusCode(200);


    }

}