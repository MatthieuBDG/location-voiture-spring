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
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(ClientController.class);
    @GetMapping("/location/liste") // liste de toutes les locations
    public ModelAndView listeLocations() {
        logger.info("Liste des locations appelée le " + new Date());
        return new ModelAndView("listeLocation", "locations", locationService.getLocations());
    }

    @GetMapping("/location/creer") // création d'une location
    public ModelAndView creerLocation() {
        logger.info("Création d'une location appelée le " + new Date());
        ModelAndView modelAndView = new ModelAndView("creerLocation");
        modelAndView.addObject("location", new Location());
        modelAndView.addObject("voitures", voitureRepository.findAll()); // Récupération de la liste des
                                                                                   // voitures
        return modelAndView;
    }

    @PostMapping("/location/creer")
    public ModelAndView submit(@ModelAttribute("location") Location location, ModelMap model) {
        logger.info("Enregistrement d'une location appelée le " + new Date());
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
        logger.info("Modification d'une location appelée le " + new Date());
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid location Id:" + id));
        model.addAttribute("location", location);

        ModelAndView modelAndView = new ModelAndView("modifierLocation");
        modelAndView.addObject("location", location);

        return modelAndView;
    }

    @PostMapping("/location/modifier/{id}")
    public ModelAndView modifierVoiture(@ModelAttribute("location") Location location) {
        logger.info("Enregistrement de la modification d'une location appelée le " + new Date());
        locationRepository.save(location);
        ModelAndView modelAndView = new ModelAndView("listeLocation");
        modelAndView.addObject("locations", locationService.getLocations());
        modelAndView.addObject("message", "La location à été modifié avec succès.");
        modelAndView.setViewName("redirect:/location/liste");
        return modelAndView;
    }

    @GetMapping("/location/supprimer/{id}") // suppression d'une location
    public ModelAndView deleteLocation(@PathVariable("id") Integer id) {
        logger.info("Suppression d'une location appelée le " + new Date());
        locationRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("listeLocation");
        modelAndView.addObject("message_error", "La location à été supprimée avec succès");
        modelAndView.setViewName("redirect:/location/liste");
        return modelAndView;
    }

    @GetMapping("/location/details/{id}") // détail d'une location
    public ModelAndView detailLocation(@PathVariable("id") Integer id) {
        logger.info("Details d'une location appelée le " + new Date());
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
        logger.info("Ajout client à une location appelée le " + new Date());
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
        logger.info("Suppression client à une location appelée le " + new Date());
        ClientLocation clientLocation = clientLocationRepository.findByClient_IdAndLocation_Id(clientId, id);
        clientLocationRepository.delete(clientLocation);
        ModelAndView modelAndView = new ModelAndView("ajoutClient");
        modelAndView.addObject("message_error", "Client supprimé de la location avec succès");
        modelAndView.setViewName("redirect:/location/details/{id}");
        return modelAndView;
    }
    
}
