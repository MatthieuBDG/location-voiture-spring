package location.voiture.location.voiture.controller;

import location.voiture.location.voiture.model.ClientLocation;
import location.voiture.location.voiture.model.Location;
import location.voiture.location.voiture.model.Voiture;
import location.voiture.location.voiture.model.Client;
import location.voiture.location.voiture.repository.LocationRepository;
import location.voiture.location.voiture.repository.ClientRepository;
import location.voiture.location.voiture.repository.ClientLocationRepository;
import location.voiture.location.voiture.repository.VoitureRepository;
import location.voiture.location.voiture.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
public class LocationController {
    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private ClientLocationRepository clientLocationRepository;

    @GetMapping("/location/liste") // liste de toutes les locations
    public ModelAndView listeLocations() {
        return new ModelAndView("listeLocation", "locations", locationService.getLocations());
    }

    @GetMapping("/location/creer") // création d'une location
    public ModelAndView creerLocation() {
        ModelAndView modelAndView = new ModelAndView("creerLocation");
        modelAndView.addObject("location", new Location());
        modelAndView.addObject("voitures", voitureRepository.findAll()); // Récupération de la liste des
                                                                                   // voitures
        return modelAndView;
    }

    @PostMapping("/location/creer")
    public ModelAndView submit(@ModelAttribute("location") Location location, ModelMap model) {
        model.addAttribute("dateDebut", location.getDateDebut());
        model.addAttribute("dateFin", location.getDateFin());
        model.addAttribute("heureDebut", location.getHeureDebut());
        model.addAttribute("heureFin", location.getHeureFin());
        model.addAttribute("lieu", location.getLieu());

        locationService.saveLocation(location);
        ModelAndView modelAndView = new ModelAndView("listeLocation");
        modelAndView.addObject("locations", locationService.getLocations());
        modelAndView.addObject("message", "La location a été créée avec succès.");
        modelAndView.setViewName("redirect:/location/liste");
        return modelAndView;
    }

    @GetMapping("/location/modifier/{id}") // modification d'une location
    public ModelAndView showUpdateForm(@PathVariable("id") Integer id, ModelMap model) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid location Id:" + id));
        model.addAttribute("location", location);

        ModelAndView modelAndView = new ModelAndView("modifierLocation");
        modelAndView.addObject("location", location);

        return modelAndView;
    }

    @PostMapping("/location/modifier/{id}")
    public ModelAndView modifierVoiture(@ModelAttribute("location") Location location) {
        locationRepository.save(location);
        ModelAndView modelAndView = new ModelAndView("listeLocation");
        modelAndView.addObject("locations", locationService.getLocations());
        modelAndView.addObject("message", "La location à été modifié avec succès.");
        modelAndView.setViewName("redirect:/location/liste");
        return modelAndView;
    }

    @GetMapping("/location/supprimer/{id}") // suppression d'une location
    public ModelAndView deleteLocation(@PathVariable("id") Integer id) {
        locationRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("listeLocation");
        modelAndView.addObject("message_error", "La location à été supprimée avec succès");
        modelAndView.setViewName("redirect:/location/liste");
        return modelAndView;
    }

    @GetMapping("/location/details/{id}") // détail d'une location
    public ModelAndView detailLocation(@PathVariable("id") Integer id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid location Id:" + id));
        Voiture voiture = location.getVoiture();
        List<Client> clients = location.getClients();
        ModelAndView modelAndView = new ModelAndView("detailsLocation");
        modelAndView.addObject("location", location);
        modelAndView.addObject("voiture", voiture);
        modelAndView.addObject("clients", clients);
        modelAndView.addObject("locationId", id);
        return modelAndView;
    }

    @GetMapping("/location/{id}/ajoutClient") // ajouter un client à une location
    public ModelAndView ajoutClient(@PathVariable("id") Integer id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid location Id:" + id));
        List<Client> clients = clientRepository.findByLocationsNotContaining(location);
        ModelAndView modelAndView = new ModelAndView("ajoutClient");
        modelAndView.addObject("location", location);
            modelAndView.addObject("ajoutClient", clients);
        return modelAndView;
    }

    @PostMapping("/location/{id}/ajoutClient")
    public ModelAndView ajoutClientPost(@PathVariable("id") Integer id,
            @RequestParam("clientId") Integer clientId) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid location Id:" + id));
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + clientId));
        ClientLocation clientLocation = new ClientLocation();
        clientLocation.setLocation(location);
        clientLocation.setClient(client);
        clientLocationRepository.save(clientLocation);
        ModelAndView modelAndView = new ModelAndView("detailsLocation");
        modelAndView.addObject("message", "Client ajouté de la location avec succès");
        modelAndView.setViewName("redirect:/location/details/{id}");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    @GetMapping("/location/{id}/supprimerClient/{clientId}") //retirer un client d'une location
    public ModelAndView supprimerClient(@PathVariable("id") Integer id, @PathVariable("clientId") Integer clientId) {
        ClientLocation clientLocation = clientLocationRepository.findByClient_IdAndLocation_Id(clientId, id);
        clientLocationRepository.delete(clientLocation);
        ModelAndView modelAndView = new ModelAndView("ajoutClient");
        modelAndView.addObject("message_error", "Client supprimé de la location avec succès");
        modelAndView.setViewName("redirect:/location/details/{id}");
        return modelAndView;
    }
    
}
