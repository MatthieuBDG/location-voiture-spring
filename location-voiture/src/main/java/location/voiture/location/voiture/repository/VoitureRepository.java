package location.voiture.location.voiture.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import location.voiture.location.voiture.model.Voiture;

@Repository
public interface VoitureRepository extends CrudRepository<Voiture, Integer>
{
    
}