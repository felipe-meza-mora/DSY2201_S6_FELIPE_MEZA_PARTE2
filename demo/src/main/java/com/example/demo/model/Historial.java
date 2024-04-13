package com.example.demo.model;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "historials")
public class Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_atencion", length = 10) // La longitud de una fecha es 10 (YYYY-MM-DD)
    @NotNull(message = "La fecha de atención no puede ser nula")
    private LocalDate fechaAtencion;

    @Column(name = "tipo", length = 50)
    @NotNull(message = "El tipo no puede ser nulo")
    @Size(max = 50, message = "El tipo debe tener como máximo {max} caracteres")
    private String tipo;

    @Column(name = "descripcion", length = 250)
    @NotNull(message = "La descripción no puede ser nula")
    @Size(max = 250, message = "La descripción debe tener como máximo {max} caracteres")
    private String descripcion;

    @Column(name = "doctor", length = 100)
    @NotNull(message = "El doctor no puede ser nulo")
    @Size(max = 100, message = "El doctor debe tener como máximo {max} caracteres")
    private String doctor;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "paciente", referencedColumnName = "id")
    private Paciente paciente;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(LocalDate fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}