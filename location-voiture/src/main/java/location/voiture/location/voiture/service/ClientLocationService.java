package location.voiture.location.voiture.service;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import location.voiture.location.voiture.model.ClientLocation;
import location.voiture.location.voiture.repository.ClientLocationRepository;

import java.util.Optional;

@Data
@Service
public class ClientLocationService {
    @Autowired
    private ClientLocationRepository clientLocationRepository;

    public Optional<ClientLocation> getClient(final Integer id){
        return clientLocationRepository.findById(id);
    }

    public Iterable<ClientLocation> getClients() {
        return clientLocationRepository.findAll();
    }

    public void deleteClientLocation(final Integer id){
        clientLocationRepository.deleteById(id);
    }
    
    public ClientLocation saveClientLocation(ClientLocation clientLocation){
        return clientLocationRepository.save(clientLocation);
    }
}
