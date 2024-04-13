package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Paciente;
import com.example.demo.model.Historial;
import com.example.demo.repository.PacienteRepository;
import com.example.demo.service.PacienteService;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private static final Logger log = LoggerFactory.getLogger(PacienteController.class);

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PacienteRepository pacienteRepository;
    
    //PACIENTE
    @GetMapping
    public List<Paciente> getAllPacientes(){
        log.info("GET /pacientes");
        log.info("Retornado todos los pacientes");
        return pacienteService.getAllPacientes();
    }
   
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPacienteById(@PathVariable("id") Integer id) {
        Optional<Paciente> paciente = pacienteService.getPacienteById(id);

        if(paciente.isEmpty()){
            log.error("No se encontro el paciente con ID: {}",id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontró el Paciente con el ID: " + id));
        }
        log.info("Usuario encontrado con exito");
        return ResponseEntity.ok(pacienteService.getPacienteById(id));
    }


     //CONTROLADORES NUEVO PACIENTE CRUD

     @PostMapping
     public ResponseEntity<Object> createPaciene(@RequestBody Paciente paciente){
        Paciente createPaciente = pacienteService.createPaciente(paciente);
        if(createPaciente == null){
          log.error("Error al crear el paciente {}",paciente);
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error al crear el Paciente"));
        }
        return ResponseEntity.ok("El Paciente fue creado correctamente");
     }
 
     @PutMapping("/{id}")
     public ResponseEntity<Object> updatePaciente(@PathVariable Integer id, @RequestBody Paciente paciente){
         Paciente updateUsuario = pacienteService.updatePaciente(id, paciente);
         if(updateUsuario == null){
            log.error("Error al modificar al Paciente {}",paciente);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error al modificar el Paciente"));
         }
         log.info("Paciente modificado con exito");
         //return ResponseEntity.ok(updateUsuario);
         return ResponseEntity.ok("Paciente con ID " + id + " fue modificado correctamente");
     }
 
     @DeleteMapping("/{id}")
     public  ResponseEntity<Object> deletePaciente(@PathVariable("id") Integer id){
        boolean deleted = pacienteService.deletePaciente(id);
        if(deleted){
            log.info("Paciente eliminado con éxito");
            return ResponseEntity.ok("Paciente con ID " + id + " eliminado correctamente");
        } else {
            log.error("Error al eliminar el paciente con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se puede eliminar el Paciente con el ID: " + id));
        }
     }

     //FIN PACIENTE
    
     @GetMapping("/{idPaciente}/historial")
     public ResponseEntity<Object> getAllHistorialByPacienteId(@PathVariable("idPaciente") Integer idPaciente) {
         List<Historial> historial = pacienteService.getAllHistorialByPacienteId(idPaciente);
 
         if (historial != null) {
             if (!historial.isEmpty()) {
                 return ResponseEntity.ok(historial);
             } else {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El Paciente no tiene historial asociados.");
             }
         } else {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado.");
         }
     }

     @PostMapping("/{idPaciente}/historial")
     public ResponseEntity<Object> createHistorial(@PathVariable("idPaciente") Integer idPaciente, @RequestBody Historial historial) {
         // Intenta crear el pedido utilizando el servicio
         Historial createdHistorial = pacienteService.createHistorial(idPaciente, historial);
         
         if (createdHistorial != null) {
             // Si el historial se crea con éxito, devuelve un mensaje de éxito
             return ResponseEntity.ok("Historial creado correctamente");
         } else {
             // Si el Paciente no existe, devuelve un mensaje de error
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado. No se pudo crear el Historial.");
         }
     }

     @PutMapping("/{idPaciente}/historial/{idHistorial}")
     public ResponseEntity<Object> updateHistorial(@PathVariable("idPaciente") Integer idPaciente, @PathVariable("idHistorial") Integer idHistorial,@RequestBody Historial historial) {
         Historial updatedHistorial = pacienteService.updateHistorial(idHistorial, historial);
         if (updatedHistorial != null) {
             return ResponseEntity.ok("Historial con ID " + idHistorial + " actualizado correctamente");
         } else {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Historial con ID: " + idHistorial);
         }
     }
 
 
     @DeleteMapping("/{idPaciente}/historial/{idHistorial}")
     public ResponseEntity<Object> deleteHistorial(@PathVariable("idPaciente") Integer idPaciente, @PathVariable("idHistorial") Integer idHistorial) {
         // Verificar si el paciente existe
         Optional<Paciente> pacienteOptional = pacienteRepository.findById(idPaciente);
         if (pacienteOptional.isPresent()) {
             // Si el paciente existe, intentar eliminar el historial
             boolean deleted = pacienteService.deleteHistorial(idHistorial);
             if (deleted) {
                 // Si se eliminó correctamente, devolver respuesta exitosa
                 log.info("Historial con ID {} eliminado con éxito para el paciente con ID {}", idHistorial, idPaciente);
                 return ResponseEntity.ok("Historial con ID " + idHistorial + " eliminado correctamente.");
             } else {
                 // Si el historial no se pudo eliminar, devolver error
                 log.error("Error al eliminar el Historial con ID {} para el paciente con ID {}", idHistorial, idPaciente);
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo eliminar el Historial con ID " + idHistorial + ".");
             }
         } else {
             // Si el Paciente no existe, devolver error
             log.error("Paciente con ID {} no encontrado.", idPaciente);
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente con ID " + idPaciente + " no encontrado.");
         }
     }
 



    static class ErrorResponse {
        private final String message;
        
        public ErrorResponse(String message){
            this.message = message;
        }

        public String getMessage(){
            return message;
        }
     }
    
}
