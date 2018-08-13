package com.sumerge.grad.program.jpa.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItem  implements Serializable {

    @EmbeddedId
    private OrderItemId id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ORDER_ID", insertable = false, updatable = false)
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID", insertable = false, updatable = false)
    private Item item;

    @Column(name = "QUANTITY")
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(Order order, Item item) {
        this.order = order;
        this.item = item;
        this.id = new OrderItemId(order.getId(), item.getId());
    }

    public OrderItemId getId() {
        return id;
    }

    public void setId(OrderItemId id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(order, orderItem.order) &&
                Objects.equals(item, orderItem.item);
    }

    @Override
    public int hashCode() {

        return Objects.hash(order, item);
    }
}
