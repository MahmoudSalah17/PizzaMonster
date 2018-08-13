package com.sumerge.grad.program.jpa.rest.resources;

import com.sumerge.grad.program.jpa.Repository.Repository;
import com.sumerge.grad.program.jpa.entity.Complain;
import com.sumerge.grad.program.jpa.entity.Item;
import com.sumerge.grad.program.jpa.entity.Order;
import com.sumerge.grad.program.jpa.entity.User;
import com.sumerge.grad.program.jpa.models.NewOrder;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Collection;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RequestScoped
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("/user")

public class UserResources {

    private static final Logger LOGGER = Logger.getLogger(UserResources.class.getName());
//
//    @GET
//    @Transactional
//    public Response getAllStudents() {
//        try {
//
//            User u = em.find(User.class, 1L);
//            Collection<Order> orders= u.getOrders();
//            Item item = null;
//            for (Order o: orders
//                 ) {
//                Collection<OrderItem> items = o.getItems();
//                for (OrderItem i : items)
//                {
//                    item = i.getItem();
//                }
//                break;
//            }
//            return Response.ok().
//                    entity(item).
//                    build();
//        } catch (Exception e) {
//            LOGGER.log(SEVERE, e.getMessage(), e);
//            return Response.serverError().
//                    entity(e).
//                    build();
//        }
//    }

    @EJB
    private Repository repository;

    @Context
    private SecurityContext securityContext;

    @GET
    public Response getMenuItems() {
        try {
            if(securityContext.isUserInRole("user")) {
                return Response.ok().entity(repository.getMenuItems()).build();
            }
            else {
                return Response.serverError().build();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/login")
    public Response login()
    {
        if(securityContext.isUserInRole("user"))
            return Response.ok().build();
        else
            return Response.serverError().build();
    }

    @POST
    @Path("/submitOrder")
    public Response addOrder(NewOrder newOrder)
    {
        try
        {
            Order order = repository.addOrder(newOrder);
            return Response.ok().entity(repository.addItemsToOrder(order, newOrder.getItems())).
                    build();
    } catch (Exception e) {
        LOGGER.log(SEVERE, e.getMessage(), e);
        return Response.serverError().
                entity(e).
                build();
    }
    }

    @POST
    @Path("/complain")
    public Response addItems(@QueryParam("username") String username, Complain complain)
    {
        try {
            User user = (User) repository.getUserByUsername(username);
            return Response.ok().entity(repository.addComplain(user, complain)).build();
        }
        catch (Exception e)
        {
            return Response.serverError().build();
        }
    }

}
