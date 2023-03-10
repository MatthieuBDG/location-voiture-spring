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

    public Iterable<Location> getLocations(){
        return locationRepository.findAll();
    }

    public Location saveLocation(Location location){
        return locationRepository.save(location);
    }

}
