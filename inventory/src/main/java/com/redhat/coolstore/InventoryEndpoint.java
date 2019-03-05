package com.redhat.coolstore;

import com.redhat.coolstore.model.Inventory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/api/inventory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InventoryEndpoint {

    @Inject
    InventoryRepository repository;


    @GET
    public Response getProductsInventory() {
        return Response.ok(repository.getInventories()).build();
    }

    @GET
    @Path("{id}")
    public Response getProduct(@PathParam("id") String id) {
        Inventory inv = repository.getById(id);
        if(inv==null) {
            return Response.noContent().build();
        }
        return Response.ok(inv).build();
    }


    @PUT
    @Path("{id}/{diff}")
    public Response updateQuantityWithDiffAndReturnNewTotal(@PathParam("id") String id, @PathParam("diff") Long diff) {
        Long newQuantity = repository.updateQuantity(id,diff);
        JsonObject retVal = Json.createObjectBuilder().add("prodId", id).add("quantity", newQuantity).build();
        return Response.ok(retVal).build();
    }

}