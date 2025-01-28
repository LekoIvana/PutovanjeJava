package putovanje.putovanje.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

import java.math.BigDecimal;


@Entity
@Table(name="osvrti")
public class Putovanje {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Size(min = 2, max = 50, message = "Naziv destinacije mora biti duzi od 2 znaka i kraci od 50.") // Anotator za provjeru duljine unesenog teksta.
        @Column(nullable = false, length = 50)
        private String nazivDestinacije;

        @Size(min = 2, max = 50, message = "Naziv drzave mora biti duze od 2 znaka i kraci od 50.") // Anotator za provjeru duljine unesenog teksta.
        @Column(nullable = false, length = 50)
        private String nazivDrzave;


        @Size(min = 10, max = 5000, message = "=svrt mora biti izmedju 10 i 5000 znakova.") // Anotator za provjeru duljine unesenog teksta.
        @Column(nullable = false, length = 5000)
        private String osvrt;

        @DecimalMin(value = "1.00", message = "Ocjena ne moze biti manja od  1.")
        @DecimalMax(value = "5.00", message = "Ocjena ne moze biti veca od 5.")
        @Digits(integer = 1, fraction = 2, message = "ne mogu biti vise od 2 decimalna mjesta")
        private BigDecimal ocjena;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;



        // Getter i Setter
        @Column(nullable = false, updatable = false, name = "datum_dodavanja")
        private LocalDateTime datumDodavanja;

        // Novo polje za formatirani datum (neće se spremati u bazu)
        @Transient
        private String formattedDatumDodavanja;

        @PrePersist
        protected void onCreate() {
                this.datumDodavanja = LocalDateTime.now();
        }

        public Putovanje(String nazivDestinacije, String nazivDrzave, String osvrt, BigDecimal ocjena) {
                this.nazivDestinacije = nazivDestinacije;
                this.nazivDrzave = nazivDrzave;
                this.osvrt = osvrt;
                this.ocjena = ocjena;
        }

        public Putovanje() {
        }
        public Long getId() {
                return id;
        }
        public void setId(Long id) {
                this.id = id;
        }
        public String getNazivDestinacije() {
                return nazivDestinacije;
        }
        public void setNazivDestinacije(String nazivDestinacije) {
                this.nazivDestinacije = nazivDestinacije;
        }
        public String getNazivDrzave() {
                return nazivDrzave;
        }
        public void setNazivDrzave(String nazivDrzave) {
                this.nazivDrzave = nazivDrzave;
        }
        public String getOsvrt() {
                return osvrt;
        }
        public void setOsvrt(String osvrt) {
                this.osvrt = osvrt;
        }
        public BigDecimal getOcjena() {
                return ocjena;
        }
        public void setOcjena(BigDecimal ocjena) {
                this.ocjena = ocjena;
        }
        public void setUser(User user) {
                this.user = user;
        }
        public User getUser() {
                return user;
        }


        public LocalDateTime getDatumDodavanja() {
                return datumDodavanja;
        }

        public void setDatumDodavanja(LocalDateTime datumDodavanja) {
                this.datumDodavanja = datumDodavanja;
        }

        // Getter i setter za formatirani datum
        public String getFormattedDatumDodavanja() {
                return formattedDatumDodavanja;
        }

        public void setFormattedDatumDodavanja(String formattedDatumDodavanja) {
                this.formattedDatumDodavanja = formattedDatumDodavanja;
        }



}
