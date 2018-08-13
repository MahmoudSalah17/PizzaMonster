package com.sumerge.grad.program.jpa.rest.resources;

import com.sumerge.grad.program.jpa.Repository.Repository;
import com.sumerge.grad.program.jpa.entity.Address;
import com.sumerge.grad.program.jpa.entity.User;
import com.sumerge.grad.program.jpa.models.NewUser;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RequestScoped
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("/")
public class GuestResources {


    @EJB
    private Repository repository;

    @GET
    public Response getMenuItems() {
        try {
            System.out.println("ana user");
            return Response.ok().entity(repository.getMenuItems()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }


    @POST
    public Response signUp(NewUser signUpUser)
    {

        try {
            User user = new User();
            user.setUsername(signUpUser.getUserName());
            user.setPassword(signUpUser.getPassword());
            user.setPhoneNo(signUpUser.getPhoneNo());
            user.setRole("user");
            User newUser = repository.addUser(user);
            Address address = new Address();
            address.setCity(signUpUser.getCity());
            address.setStreetAddress(signUpUser.getStreetAddress());
            address.setUser(newUser);
            repository.addAddress(address);
            return Response.ok().entity(newUser).build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

}
