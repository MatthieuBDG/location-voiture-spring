package location.voiture.location.voiture.service;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import location.voiture.location.voiture.model.Voiture;
import location.voiture.location.voiture.model.Location;
import location.voiture.location.voiture.repository.LocationRepository;

import java.util.Optional;

@Data
@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public Optional<Location> getLocation(final Integer id){
        return locationRepository.findById(id);
    }

    public Iterable<Location> getLocations(){
        return locationRepository.findAll();
    }

    public void deleteLocation(final Integer id){
        locationRepository.deleteById(id);
    }

    public Location saveLocation(Location location){
        return locationRepository.save(location);
    }

    public Optional<Voiture> getVoitureByLocationId(Integer id) {
        Optional<Location> location = locationRepository.findById(id);
        return location.map(Location::getVoiture);
    }
}
