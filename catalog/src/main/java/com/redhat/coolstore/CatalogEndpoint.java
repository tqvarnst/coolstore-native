package com.redhat.coolstore;

import com.redhat.coolstore.model.Product;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CatalogEndpoint {

    @Inject
    ProductRepository repository;

    @GET
    public Response getProducts() {
        return Response.ok(repository.getProducts()).build();
    }

    @GET
    @Path("{id}")
    public Response getProduct(@PathParam("id") Long id) {
        return Response.ok(repository.getById(id)).build();
    }

    @POST
    public Response create(Product product) {
        repository.create(product);
        JsonObject response = Json.createObjectBuilder().add("id", product.getId()).build();
        return Response.ok(response).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, Product product) {
        Product updateProduct = repository.getById(id);
        updateProduct.setName(product.getName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setProdId(product.getProdId());
        updateProduct.setPrice(product.getPrice());
        repository.update(product);
        return Response.ok(updateProduct).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        repository.remove(id);
        return Response.ok().build();
    }

}