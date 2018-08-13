package com.sumerge.grad.program.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static com.sumerge.grad.program.jpa.constants.Constants.SCHEMA_NAME;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "COMPLAINS", schema = SCHEMA_NAME)
public class Complain implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COMPLAIN")
    private String complainText;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplainText() {
        return complainText;
    }

    public void setComplainText(String complainText) {
        this.complainText = complainText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complain complain = (Complain) o;
        return Objects.equals(id, complain.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
