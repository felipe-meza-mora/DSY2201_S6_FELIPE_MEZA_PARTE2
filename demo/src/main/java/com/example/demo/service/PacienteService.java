package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Paciente;
import com.example.demo.model.Historial;

public interface PacienteService {

     //Paciente
     List<Paciente> getAllPacientes();
     Optional<Paciente> getPacienteById(Integer id);
 
     //CRUD
     Paciente createPaciente(Paciente paciente);
     Paciente updatePaciente(Integer id, Paciente paciente);
     boolean deletePaciente(Integer id);
     //FIN PACIENTE
 
     //HISTORIAL
     List<Historial> getAllHistorialByPacienteId(Integer idPaciente);
 
     //CRUD
     Historial createHistorial(Integer historialId,Historial historial);
     Historial updateHistorial(Integer historialIdH, Historial historial);
     boolean deleteHistorial(Integer idHistoiral);
     //FIN HISTORIAL
    
}
