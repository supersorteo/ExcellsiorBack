package com.example.exellsior.controller;

import com.example.exellsior.entity.Client;
import com.example.exellsior.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> getAll() {
        return clientService.getAllClients();
    }




    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id) {
        return clientService.getById(id);
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<Client> getByDni(@PathVariable String dni) {
        Client client = clientService.getByDni(dni);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    /*@GetMapping("/dni-list/{dni}")
    public ResponseEntity<List<Client>> getByDniList(@PathVariable String dni) {
        List<Client> clients = clientService.findByDni(dni);
        if (clients.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clients);
    }*/



    @PostMapping
    public Client create(@RequestBody Client client) {
        client.setId(null); // Aseguramos que sea nuevo
        return clientService.saveClient(client);
    }



    @PutMapping("/{id}")
    public Client update(@PathVariable Long id, @RequestBody Client updatedClient) {
        // Validamos que el cliente exista
        clientService.getById(id);

        updatedClient.setId(id);
        return clientService.saveClient(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/spaces/{spaceKey}/reserve")
    public ResponseEntity<Client> reserveSpace(
            @PathVariable String spaceKey,
            @RequestBody Client client
    ) {
        Client saved = clientService.reserveSpace(spaceKey, client);
        return ResponseEntity.ok(saved);
    }

    // Liberar space y resetear cliente
    @PutMapping("/spaces/{spaceKey}/release")
    public ResponseEntity<Void> releaseSpace(@PathVariable String spaceKey) {
        clientService.releaseSpace(spaceKey);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/reset")
    public ResponseEntity<Void> resetAllData() {
        clientService.resetAllData();
        return ResponseEntity.noContent().build();
    }


}
