package location.voiture.location.voiture.model;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_debut", length = 50)
    private Date date_debut;
    @Column(name = "date_fin", length = 50)
    private Date date_fin;
    @Column(name = "heureDebut", length = 50, columnDefinition = "TIME")
    @DateTimeFormat(pattern = "HH:mm")
    private Time heureDebut;

    @Column(name = "heureFin", length = 50, columnDefinition = "TIME")
    @DateTimeFormat(pattern = "HH:mm")
    private Time heureFin;

    @Column(name = "lieu", length = 255)
    private String lieu;

    @ManyToOne
    @JoinColumn(name = "idvoiture")
    private Voiture voiture;

    @ManyToMany
    @JoinTable(name = "client_location", joinColumns = @JoinColumn(name = "location_id"), inverseJoinColumns = @JoinColumn(name = "client_id"))
    private List<Client> clients = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return date_debut;
    }

    public void setDateDebut(Date date_debut) {
        this.date_debut = date_debut;
    }
        public Date getDateFin() {
        return date_fin;
    }

    public void setDateFin(Date date_fin) {
        this.date_fin = date_fin;
    }
    public Time getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Time getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Time heureFin) {
        this.heureFin = heureFin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "Location [id=" + id + ", date=" + date_debut + ", date_fin=" + date_fin + ", heureDebut=" + heureDebut + ", heureFin=" + heureFin
                + ", lieu=" + lieu + ", voiture=" + voiture + ", clients=" + clients
                + "]";
    }
}
