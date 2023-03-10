package location.voiture.location.voiture.controller;

import location.voiture.location.voiture.model.Voiture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import location.voiture.location.voiture.repository.VoitureRepository;
import location.voiture.location.voiture.service.VoitureService;
import org.springframework.web.servlet.ModelAndView;
import java.util.Optional;

@RestController
public class VoitureController {
    @Autowired
    private VoitureService voitureService;

    @Autowired
    private VoitureRepository voitureRepository;

    @GetMapping("/voiture/liste")
    public ModelAndView listeVoitures() {
        return new ModelAndView("listeVoiture", "voitures", voitureService.getVoitures());
    }

    @GetMapping("/voiture/creer")
    public ModelAndView creerVoiture() {
        Voiture o = new Voiture();
        return new ModelAndView("creerVoiture", "voiture", o);
    }

    @PostMapping("/voiture/creer")
    public ModelAndView submit(@ModelAttribute("voiture") Voiture voiture, ModelMap model) {
        model.addAttribute("nom", voiture.getNom());
        model.addAttribute("prenom", voiture.getPrenom());
        model.addAttribute("email", voiture.getEmail());
        model.addAttribute("telephone", voiture.getTelephone());

        voitureService.saveVoiture(voiture);
        ModelAndView modelAndView = new ModelAndView("listeVoiture");
        modelAndView.addObject("voitures", voitureService.getVoitures());
        modelAndView.addObject("message", "La voiture a été créée avec succès.");
        modelAndView.setViewName("redirect:/voiture/liste");
        return modelAndView;
    }

    @GetMapping("/voiture/modifier/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") Integer id, ModelMap model) {
        Voiture voiture = voitureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid location Id:" + id));
        model.addAttribute("voiture", voiture);

        ModelAndView modelAndView = new ModelAndView("modifierVoiture");
        modelAndView.addObject("voiture", voiture);
        return modelAndView;
    }

    @PostMapping("/voiture/modifier/{id}")
    public ModelAndView modifierVoiture(@ModelAttribute("voiture") Voiture voiture) {
        voitureRepository.save(voiture);
        ModelAndView modelAndView = new ModelAndView("listeVoiture");
        modelAndView.addObject("voitures", voitureService.getVoitures());
        modelAndView.addObject("message", "la voiture a été modifié avec succès.");
        modelAndView.setViewName("redirect:/voiture/liste");
        return modelAndView;
    }

    @GetMapping("/voiture/supprimer/{id}")
    public ModelAndView deleteLocation(@PathVariable("id") Integer id) {
        voitureRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("listeVoiture");
        modelAndView.addObject("message_error", "Voiture supprimé avec succès");
        modelAndView.setViewName("redirect:/voiture/liste");
        return modelAndView;
    }

    @GetMapping("/voiture/detail/{id}")
    public ModelAndView detailvoiture(@PathVariable("id") final Integer id) {
        Optional<Voiture> voiture = voitureService.getVoiture(id);
        assert voiture.orElse(null) != null;
        return new ModelAndView("detailVoiture", "voiture", voiture.orElse(null));
    }

}
