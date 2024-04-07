package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    private String nombres;

    @Column(name = "numero_cedula")
    private String numCedula;

}
