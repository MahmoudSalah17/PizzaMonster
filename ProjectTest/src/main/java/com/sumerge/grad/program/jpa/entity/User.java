package com.sumerge.grad.program.jpa.entity;


import com.mysql.cj.x.protobuf.MysqlxCrud;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import static com.sumerge.grad.program.jpa.constants.Constants.SCHEMA_NAME;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "USERS", schema = SCHEMA_NAME)
public class User implements Serializable {

    private static final long serialVersionUID = -626290349113994097L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PHONE_NO")
    private String phoneNo;

    @Column(name = "ROLE")
    private String role;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private Collection<Address> addresses;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private Collection<Order> orders;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private Collection<Complain> complains;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    public Collection<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<Address> addresses) {
        this.addresses = addresses;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order newOrder){orders.add(newOrder);}

    public Collection<Complain> getComplains() {
        return complains;
    }

    public void setComplains(Collection<Complain> complains) {
        this.complains = complains;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
