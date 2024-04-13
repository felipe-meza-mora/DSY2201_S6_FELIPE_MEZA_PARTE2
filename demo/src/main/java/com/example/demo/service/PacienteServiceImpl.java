package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Paciente;
import com.example.demo.model.Historial;
import com.example.demo.repository.HistorialRepository;
import com.example.demo.repository.PacienteRepository;

@Service
public class PacienteServiceImpl implements PacienteService{

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private HistorialRepository historialRepository;

    //PACIENTE

    @Override
    public List<Paciente> getAllPacientes(){
        return pacienteRepository.findAll();
    }

    @Override
    public Optional<Paciente> getPacienteById(Integer id){
        return pacienteRepository.findById(id);
    }

    //logica CRUD
    @Override
    public Paciente createPaciente(Paciente paciente){
        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente updatePaciente(Integer id, Paciente paciente){
        if(pacienteRepository.existsById(id)){
           paciente.setId(id);
           return pacienteRepository.save(paciente);
        } else {
         return null;
        }
    }

    @Override
    public boolean deletePaciente(Integer id){
        if (pacienteRepository.existsById(id)) {
            pacienteRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    //FIN PACIENTE

    //HISTORIAL

    @Override
    public List<Historial> getAllHistorialByPacienteId(Integer idPaciente){
        // Obtener el paciente por su ID
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(idPaciente);

        if(pacienteOptional.isPresent()){
            // Si el paciente existe, obtener los historiales medicos
            Paciente paciente = pacienteOptional.get();
            List<Historial> historials = paciente.getHistoriales();

            // Verificar si hay historiales asociados
            if(historials != null && !historials.isEmpty()) {
                return historials;
            } else {
                // Devolver una lista vacía si no hay hitoriales asociados
                return new ArrayList<>();
            }
        } else {
            // Devolver null si el usuario no existe
            return null;
        }
    }

    @Override
    public Historial createHistorial(Integer pacienteId, Historial historial) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(pacienteId);
        
        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();
            historial.setPaciente(paciente); // Establecer la relación con el paciente
            
            return historialRepository.save(historial); // Guardar el historial
        } else {
            return null; // Devolver null si el paciente no existe
        }
    }

    @Override
    public Historial updateHistorial(Integer idHistorial, Historial historial) {
        Optional<Historial> historialOptional = historialRepository.findById(idHistorial);
        if (historialOptional.isPresent()) {
            Historial existingHistorial = historialOptional.get();
            existingHistorial.setFechaAtencion(historial.getFechaAtencion());
            existingHistorial.setTipo(historial.getTipo());
            existingHistorial.setDescripcion(historial.getDescripcion());
            existingHistorial.setDoctor(historial.getDoctor());
            // Actualizar otros campos según sea necesario

            return historialRepository.save(existingHistorial);
        } else {
            return null; // o puedes lanzar una excepción indicando que el Historial no se encontró
        }
   }    

    @Override
    public boolean deleteHistorial(Integer idHistorial) {
        // Verificar si existe el historial
        Optional<Historial> historialOptional = historialRepository.findById(idHistorial);
        if (historialOptional.isPresent()) {
            // Si existe, eliminar el historial
            historialRepository.deleteById(idHistorial);
            return true;
        } else {
            // Si no existe, devolver false
            return false;
        }
    }
    //FIN HISTORIAL

    
}
