package location.voiture.location.voiture.controller;

import location.voiture.location.voiture.model.Voiture;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import location.voiture.location.voiture.repository.VoitureRepository;
import location.voiture.location.voiture.service.VoitureService;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Optional;
import org.apache.logging.log4j.Logger;
@RestController
public class VoitureController {
    @Autowired
    private VoitureService voitureService;

    @Autowired
    private VoitureRepository voitureRepository;

    @GetMapping("/voiture/liste")
    public ModelAndView listeVoitures() {
        logger.info("Liste des voitures appelée le " + new Date());
        return new ModelAndView("listeVoiture", "voitures", voitureService.getVoitures());
    }

    @GetMapping("/voiture/creer")
    public ModelAndView creerVoiture() {
        logger.info("Création d'une voiture appelée le " + new Date());
        Voiture o = new Voiture();
        return new ModelAndView("creerVoiture", "voiture", o);
    }
    private static final Logger logger = LogManager.getLogger(VoitureController.class);
    @PostMapping("/voiture/creer")
    public ModelAndView submit(@ModelAttribute("voiture") Voiture voiture, ModelMap model) {
        logger.info("Enregistrement d'une voiture appelée le " + new Date());
        model.addAttribute("marque", voiture.getMarque());
        model.addAttribute("modele", voiture.getModele());
        model.addAttribute("carburant", voiture.getCarburant());

        voitureService.saveVoiture(voiture);
        ModelAndView modelAndView = new ModelAndView("listeVoiture");
        modelAndView.addObject("voitures", voitureService.getVoitures());
        modelAndView.addObject("message", "La voiture a été créée avec succès.");
        modelAndView.setViewName("redirect:/voiture/liste");
        return modelAndView;
    }

    @GetMapping("/voiture/modifier/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") Integer id, ModelMap model) {
        logger.info("Modification d'une voiture appelée le " + new Date());
        Voiture voiture = voitureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid location Id:" + id));
        model.addAttribute("voiture", voiture);

        ModelAndView modelAndView = new ModelAndView("modifierVoiture");
        modelAndView.addObject("voiture", voiture);
        return modelAndView;
    }

    @PostMapping("/voiture/modifier/{id}")
    public ModelAndView modifierVoiture(@ModelAttribute("voiture") Voiture voiture) {
        logger.info("Enregistrement de la modification d'une voiture appelée le " + new Date());
        voitureRepository.save(voiture);
        ModelAndView modelAndView = new ModelAndView("listeVoiture");
        modelAndView.addObject("voitures", voitureService.getVoitures());
        modelAndView.addObject("message", "la voiture a été modifié avec succès.");
        modelAndView.setViewName("redirect:/voiture/liste");
        return modelAndView;
    }

    @GetMapping("/voiture/supprimer/{id}")
    public ModelAndView deleteLocation(@PathVariable("id") Integer id) {
        logger.info("Suppression d'une voiture appelée le " + new Date());
        voitureRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("listeVoiture");
        modelAndView.addObject("message_error", "Voiture supprimé avec succès");
        modelAndView.setViewName("redirect:/voiture/liste");
        return modelAndView;
    }


}
