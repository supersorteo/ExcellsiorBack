package com.example.exellsior.services;

import com.example.exellsior.entity.Client;
import com.example.exellsior.entity.Space;
import com.example.exellsior.entity.VehicleType;
import com.example.exellsior.repository.ClientRepository;
import com.example.exellsior.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SpaceRepository spaceRepository;


    @Autowired
    private VehicleTypeService vehicleTypeService;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Transactional
    public Client saveClient(Client client) {
        // Si viene solo el ID del vehículo, cargar la entidad completa
        if (client.getVehicleType() != null && client.getVehicleType().getId() != null) {
            Long vehicleId = client.getVehicleType().getId();
            VehicleType vehicleType = vehicleTypeService.getById(vehicleId);
            client.setVehicleType(vehicleType);
        }
        return clientRepository.save(client);
    }



   /* public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }*/

    @Transactional
    public void deleteClient(Long id) {
        Client client = getById(id);

        // Buscar si tiene espacio asociado
        String spaceKey = client.getSpaceKey();
        if (spaceKey != null) {
            Space space = spaceRepository.findById(spaceKey).orElse(null);
            if (space != null) {
                space.setOccupied(false);
                space.setHold(false);
                space.setClientId(null);
                space.setStartTime(null);
                space.setClient(null);
                spaceRepository.save(space);
                System.out.println("Espacio " + spaceKey + " liberado al eliminar cliente " + id);
            }
        }

        // Eliminar cliente
        clientRepository.delete(client);
    }

    public Client getByDni(String dni) {
        return clientRepository.findByDni(dni).orElse(null);
    }

    /* public List<Client> findByDni(String dni) {
        return clientRepository.findByDniList(dni);
    }*/


    @Transactional
    public Client reserveSpace(String spaceKey, Client clientData) {
        Space space = spaceRepository.findById(spaceKey)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado: " + spaceKey));

        if (space.isOccupied()) {
            throw new RuntimeException("El espacio ya está ocupado");
        }

        Client client;

        // SI VIENE ID → ACTUALIZAR CLIENTE EXISTENTE
        if (clientData.getId() != null) {
            client = clientRepository.findById(clientData.getId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        } else {
            // SI NO → CREAR NUEVO
            client = new Client();
        }

        // ACTUALIZAR DATOS DEL CLIENTE
        client.setName(clientData.getName());
        client.setDni(clientData.getDni());
        client.setPhoneRaw(clientData.getPhoneRaw());
        client.setPhoneIntl(clientData.getPhoneIntl());
        client.setCode(clientData.getCode());
        client.setVehicle(clientData.getVehicle());
        client.setPlate(clientData.getPlate());
        client.setNotes(clientData.getNotes());
        client.setCategory(clientData.getCategory());
        client.setPrice(clientData.getPrice());
        client.setSpaceKey(spaceKey);

        // VehicleType
        if (clientData.getVehicleType() != null && clientData.getVehicleType().getId() != null) {
            VehicleType vt = vehicleTypeService.getById(clientData.getVehicleType().getId());
            client.setVehicleType(vt);
        }

        // GUARDAR CLIENTE (crea o actualiza)
        client = clientRepository.save(client);

        // ACTUALIZAR ESPACIO
        space.setOccupied(true);
        space.setHold(false);
        space.setClientId(client.getId());
        space.setStartTime(System.currentTimeMillis());
        spaceRepository.save(space);

        return client;
    }
    @Transactional
    public void releaseSpace(String spaceKey) {
        Space space = spaceRepository.findById(spaceKey)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado: " + spaceKey));

        Long clientId = space.getClientId();

        // Liberar space
        space.setOccupied(false);
        space.setHold(false);
        space.setClientId(null);
        space.setStartTime(null);
        spaceRepository.save(space);

        // Resetear datos de reserva en cliente (NO eliminar cliente)
        if (clientId != null) {
            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            client.setSpaceKey(null);
            client.setCode(null);
            client.setVehicle(null);
            client.setCategory(null);
            client.setPrice(null);
            client.setVehicleType(null);
            client.setPlate(null);
            client.setNotes(null);

            clientRepository.save(client);
        }
    }


    @Transactional
    public void resetAllData() {
        // Liberar todos los espacios
        List<Space> allSpaces = spaceRepository.findAll();
        allSpaces.forEach(space -> {
            space.setOccupied(false);
            space.setHold(false);
            space.setClientId(null);
            space.setStartTime(null);
        });
        spaceRepository.saveAll(allSpaces);

        // Eliminar todos los clientes
        clientRepository.deleteAll();
    }


}
