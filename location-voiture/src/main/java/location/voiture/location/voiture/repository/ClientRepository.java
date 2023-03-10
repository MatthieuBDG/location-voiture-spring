package location.voiture.location.voiture.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import location.voiture.location.voiture.model.Client;
import location.voiture.location.voiture.model.Location;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer>
{
    List<Client> findByLocationsNotContaining(Location location);
}