package de.bund.bva.isyfact.benutzerverwaltung.persistence.basisdaten.entity;

/*-
 * #%L
 * IsyFact Benutzerverwaltung Core
 * %%
 * Copyright (C) 2016 - 2017 Bundesverwaltungsamt (BVA)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.NamedQuerySchluessel;
import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;

/**
 * Die persistente Entität eines {@link Benutzer}s.
 *
 * @author jozitz
 * @version $Id: Benutzer.java 41876 2013-07-26 08:30:23Z jozitz $
 */
@Entity
@NamedQueries({ @NamedQuery(name = NamedQuerySchluessel.GET_BENUTZER_BENUTZERNAME,
                            query = "SELECT b FROM Benutzer b where b.benutzername=:benutzername") })
public class Benutzer implements Serializable {

    private static final long serialVersionUID = -0L;

    private Long id;

    private String benutzername;

    private String passwort;

    private String behoerde;

    private String nachname;

    private String vorname;

    private BenutzerStatus status;

    private int fehlanmeldeVersuche;

    private Date letzteAnmeldung;

    private Date letzteAbmeldung;

    private String bemerkung;

    private String emailAdresse;

    private String telefonnummer;

    private Set<Rolle> rollen = new HashSet<>();

    private int version;

    /**
     * Returns the field 'id'.
     *
     * @return Value of id
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    /**
     * Sets the field 'id'.
     *
     * @param id New value for id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method gets the field <tt>benutzername</tt>.
     *
     * @return the field benutzername
     */
    public String getBenutzername() {
        return benutzername;
    }

    /**
     * This method sets the field <tt>benutzername</tt>.
     *
     * @param benutzername the new value of the field benutzername
     */
    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    /**
     * This method gets the field <tt>behoerde</tt>.
     *
     * @return the field behoerde
     */
    public String getBehoerde() {
        return behoerde;
    }

    /**
     * This method sets the field <tt>behoerde</tt>.
     *
     * @param behoerde the new value of the field behoerde
     */
    public void setBehoerde(String behoerde) {
        this.behoerde = behoerde;
    }

    /**
     * Returns the field 'nachname'.
     *
     * @return Value of nachname
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * Sets the field 'nachname'.
     *
     * @param nachname New value for nachname
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     * This method gets the field <tt>vorname</tt>.
     *
     * @return the field vorname
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * This method sets the field <tt>vorname</tt>.
     *
     * @param vorname the new value of the field vorname
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * This method gets the field <tt>status</tt>.
     *
     * @return the field status
     */
    public BenutzerStatus getStatus() {
        return status;
    }

    /**
     * This method sets the field <tt>status</tt>.
     *
     * @param status the new value of the field status
     */
    public void setStatus(BenutzerStatus status) {
        this.status = status;
    }

    /**
     * This method gets the field <tt>fehlanmeldeVersuche</tt>.
     *
     * @return the field fehlanmeldeVersuche
     */
    public int getFehlanmeldeVersuche() {
        return fehlanmeldeVersuche;
    }

    /**
     * This method sets the field <tt>fehlanmeldeVersuche</tt>.
     *
     * @param fehlanmeldeVersuche the new value of the field fehlanmeldeVersuche
     */
    public void setFehlanmeldeVersuche(int fehlanmeldeVersuche) {
        this.fehlanmeldeVersuche = fehlanmeldeVersuche;
    }

    /**
     * This method gets the field <tt>letzteAnmeldung</tt>.
     *
     * @return the field letzteAnmeldung
     */
    public Date getLetzteAnmeldung() {
        return letzteAnmeldung;
    }

    /**
     * This method sets the field <tt>letzteAnmeldung</tt>.
     *
     * @param letzteAnmeldung the new value of the field letzteAnmeldung
     */
    public void setLetzteAnmeldung(Date letzteAnmeldung) {
        this.letzteAnmeldung = letzteAnmeldung;
    }

    /**
     * This method gets the field <tt>letzteAbmeldung</tt>.
     *
     * @return the field letzteAbmeldung
     */
    public Date getLetzteAbmeldung() {
        return letzteAbmeldung;
    }

    /**
     * This method sets the field <tt>letzteAbmeldung</tt>.
     *
     * @param letzteAbmeldung the new value of the field letzteAbmeldung
     */
    public void setLetzteAbmeldung(Date letzteAbmeldung) {
        this.letzteAbmeldung = letzteAbmeldung;
    }

    /**
     * This method gets the field <tt>bemerkung</tt>.
     *
     * @return the field bemerkung
     */
    public String getBemerkung() {
        return bemerkung;
    }

    /**
     * This method sets the field <tt>bemerkung</tt>.
     *
     * @param bemerkung the new value of the field bemerkung
     */
    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    /**
     * This method gets the field <tt>emailAdresse</tt>.
     *
     * @return the field emailAdresse
     */
    public String getEmailAdresse() {
        return emailAdresse;
    }

    /**
     * This method sets the field <tt>emailAdresse</tt>.
     *
     * @param emailAdresse the new value of the field emailAdresse
     */
    public void setEmailAdresse(String emailAdresse) {
        this.emailAdresse = emailAdresse;
    }

    /**
     * This method gets the field <tt>telefonnummer</tt>.
     *
     * @return the field telefonnummer
     */
    public String getTelefonnummer() {
        return telefonnummer;
    }

    /**
     * This method sets the field <tt>telefonnummer</tt>.
     *
     * @param telefonnummer the new value of the field telefonnummer
     */
    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    /**
     * Returns the field 'version'.
     *
     * @return Value of version
     */
    @Version
    public int getVersion() {
        return version;
    }

    /**
     * Sets the field 'version'.
     *
     * @param version New value for version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Gibt die Rollen des Benutzers zurück.
     *
     * @return eine Liste mit allen Rollen des Benutzers.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Benutzer_Rollen",
               joinColumns = @JoinColumn(name = "Benutzer_PK", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "Rolle_PK", referencedColumnName = "primarykey"))
    public Set<Rolle> getRollen() {
        return rollen;
    }

    /**
     * Setzt die Rollen des Benutzers.
     *
     * @param rollen neue Rollen
     */
    public void setRollen(Set<Rolle> rollen) {
        this.rollen = rollen;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
