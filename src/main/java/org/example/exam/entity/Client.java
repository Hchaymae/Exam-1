package org.example.exam.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "client")
    private List<CD> borrowedCDs;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CD> getBorrowedCDs() {
        return borrowedCDs;
    }

    public void setBorrowedCDs(List<CD> borrowedCDs) {
        this.borrowedCDs = borrowedCDs;
    }
}