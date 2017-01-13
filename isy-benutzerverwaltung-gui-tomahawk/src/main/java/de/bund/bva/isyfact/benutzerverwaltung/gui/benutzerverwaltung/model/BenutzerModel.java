package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.model;

/*-
 * #%L
 * IsyFact Benutzerverwaltung GUI mit Tomahawk
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

import de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.BenutzerStatus;
import de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableItem;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Das Model für Benutzer in der GUI-Schicht.
 *
 * @author Capgemini, Jonas Zitz
 * @version $Id: BenutzerTreffer.java 41863 2013-07-25 06:55:46Z jozitz $
 */
public class BenutzerModel implements Serializable, DataTableItem {

    /**
     * The field <tt>serialVersionUID</tt>:
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String benutzername;

    private String passwort;

    private String passwortSalt;

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

    private List<RolleModel> rollen;

    private int version;

    /**
     * This method gets the field <tt>id</tt>.
     *
     * @return the field id
     */
    public Long getId() {
        return id;
    }

    /**
     * This method sets the field <tt>id</tt>.
     *
     * @param id the new value of the field id
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
     * This method gets the field <tt>passwort</tt>.
     *
     * @return the field passwort
     */
    public String getPasswort() {
        return passwort;
    }

    /**
     * This method sets the field <tt>passwort</tt>.
     *
     * @param passwort the new value of the field passwort
     */
    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    /**
     * This method gets the field <tt>passwortSalt</tt>.
     *
     * @return the field passwortSalt
     */
    public String getPasswortSalt() {
        return passwortSalt;
    }

    /**
     * This method sets the field <tt>passwortSalt</tt>.
     *
     * @param passwortSalt the new value of the field passwortSalt
     */
    public void setPasswortSalt(String passwortSalt) {
        this.passwortSalt = passwortSalt;
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
     * This method gets the field <tt>rollen</tt>.
     *
     * @return the field rollen
     */
    public List<RolleModel> getRollen() {
        return rollen;
    }

    /**
     * This method sets the field <tt>rollen</tt>.
     *
     * @param rollen the new value of the field rollen
     */
    public void setRollen(List<RolleModel> rollen) {
        this.rollen = rollen;
    }

    /**
     * This method gets the field <tt>version</tt>.
     *
     * @return the field version
     */
    public int getVersion() {
        return version;
    }

    /**
     * This method sets the field <tt>version</tt>.
     *
     * @param version the new value of the field version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * @see de.bund.bva.isyfact.common.web.jsf.components.datatable.DataTableItem#getIdentifierForItem()
     */
    @Override
    public long getIdentifierForItem() {
	return getId();
    }

}
