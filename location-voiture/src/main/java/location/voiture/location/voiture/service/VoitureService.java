package location.voiture.location.voiture.service;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import location.voiture.location.voiture.model.Voiture;
import location.voiture.location.voiture.repository.VoitureRepository;
import java.util.Optional;

@Data
@Service
public class VoitureService {
    @Autowired
    private VoitureRepository voitureRepository;

    public Optional<Voiture> getVoiture(final Integer id){
        return voitureRepository.findById(id);
    }

    public Iterable<Voiture> getVoitures() {
        return voitureRepository.findAll();
    }
    
    public Voiture saveVoiture(Voiture voiture){
        return voitureRepository.save(voiture);
    }
}
