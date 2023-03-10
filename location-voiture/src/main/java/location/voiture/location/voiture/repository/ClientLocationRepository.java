package location.voiture.location.voiture.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import location.voiture.location.voiture.model.ClientLocation;

@Repository
public interface ClientLocationRepository extends CrudRepository<ClientLocation, Integer> {
    
    public ClientLocation findByClient_IdAndLocation_Id(Integer clientId, Integer id);

}
