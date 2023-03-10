package location.voiture.location.voiture.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import location.voiture.location.voiture.model.Client;
import location.voiture.location.voiture.repository.ClientRepository;
import location.voiture.location.voiture.service.ClientService;
import org.springframework.web.servlet.ModelAndView;
import org.apache.logging.log4j.Logger;

import java.util.Date;

@RestController
public class ClientController {
    private static final Logger logger = LogManager.getLogger(ClientController.class);
    @Autowired
    private ClientService ClientService;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/client/liste") //liste de tout les clients
    public ModelAndView listeClients() {
        logger.info("Liste des clients appelée le " + new Date());
        return new ModelAndView("listeClient", "clients", ClientService.getClients());
    }

    @GetMapping("/client/creer") //créer un client
    public ModelAndView creerClient() {
        logger.info("Création d'un client appelée le " + new Date());
        Client a = new Client();
        return new ModelAndView("creerClient", "client", a);
    }

    @PostMapping("/client/creer")
    public ModelAndView submit(@ModelAttribute("client") Client client, ModelMap model) {
        logger.info("Enregistrement d'un client appelée le " + new Date());
        model.addAttribute("nom", client.getNom());
        model.addAttribute("prenom", client.getPrenom());
        model.addAttribute("age", client.getAge());
        model.addAttribute("email", client.getEmail());
        model.addAttribute("telephone", client.getTelephone());

        ClientService.saveClient(client);
        ModelAndView modelAndView = new ModelAndView("listeClient");
        modelAndView.addObject("clients", ClientService.getClients());
        modelAndView.addObject("message", "Le client a été créé avec succès.");
        modelAndView.setViewName("redirect:/client/liste");
        return modelAndView;
    }

    @GetMapping("/client/modifier/{id}") //modification d'un client
    public ModelAndView showUpdateForm(@PathVariable("id") Integer id, ModelMap model) {
        logger.info("Modification d'un client appelée le " + new Date());
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));
        model.addAttribute("client", client);

        ModelAndView modelAndView = new ModelAndView("modifierClient");
        modelAndView.addObject("client", client);
        return modelAndView;
    }

    @PostMapping("/client/modifier/{id}")
    public ModelAndView modifierClient(@ModelAttribute("client") Client client) {
        logger.info("Enregistrement de la modification d'un client appelée le " + new Date());
        clientRepository.save(client);
        ModelAndView modelAndView = new ModelAndView("listeClient");
        modelAndView.addObject("clients", ClientService.getClients());
        modelAndView.addObject("message", "Le client a été modifié avec succès.");
        modelAndView.setViewName("redirect:/client/liste");
        return modelAndView;
    }

    @GetMapping("/client/supprimer/{id}") //suppression d'un client
    public ModelAndView deleteClient(@PathVariable("id") Integer id) {
        logger.info("Suppression d'un client appelée le " + new Date());
        clientRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("listeClient");
        modelAndView.addObject("message_error", "Le client à été supprimé avec succès");
        modelAndView.setViewName("redirect:/client/liste");
        return modelAndView;
    }

}
