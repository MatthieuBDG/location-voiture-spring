package location.voiture.location.voiture.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import location.voiture.location.voiture.model.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer>
{
    
}