package com.sumerge.grad.program.jpa.Repository;

import com.sumerge.grad.program.jpa.entity.*;
import com.sumerge.grad.program.jpa.models.NewOrder;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Collection;

import static com.sumerge.grad.program.jpa.constants.Constants.PERSISTENT_UNIT;

@Stateless
public class Repository {

    @PersistenceContext(unitName = PERSISTENT_UNIT)
    private EntityManager em;

    public Order addNewOrder(NewOrder newOrder)
    {
        Order order = addOrder(newOrder);
        Order norder = addItemsToOrder(order, newOrder.getItems());
        return norder;
    }

    public Order addOrder(NewOrder newOrder)
    {
        User user = (User) getUserByUsername(newOrder.getUsername());
        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(newOrder.getTotalPrice());
        em.persist(order);
        return order;
    }

    public Order addItemsToOrder(Order order, ArrayList<OrderItem> orderItems)
    {
        for (OrderItem orderItem:orderItems)
        {
            OrderItem newOrder = new OrderItem(order, orderItem.getItem());
            newOrder.setQuantity(orderItem.getQuantity());
            order.getItems().add(newOrder);
        }
        em.merge(order);
        return order;
    }

    public Collection<Item> getMenuItems()
    {
       return em.createQuery("SELECT i FROM Item i",Item.class).getResultList();
    }

    public User addUser(User user)
    {
        em.persist(user);
        return user;
    }

    public Address addAddress(Address address)
    {
        em.persist(address);
        return address;
    }

    public Complain addComplain(User user,Complain complain)
    {
        complain.setUser(user);
        em.persist(complain);
        return complain;
    }

    public Object getUserByUsername(String username)
    {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :name").
                    setParameter("name",username).getSingleResult();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
