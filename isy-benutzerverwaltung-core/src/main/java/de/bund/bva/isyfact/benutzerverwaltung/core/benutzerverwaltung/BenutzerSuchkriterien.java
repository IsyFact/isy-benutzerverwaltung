package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung;

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


import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;


/**
 * Filter transport objekt fuer die Trefferliste
 * 
 * @author Capgemini, Jonas Zitz
 * @version $Id: BenutzerFilter.java 41870 2013-07-25 13:54:34Z jozitz $
 */
public class BenutzerSuchkriterien implements Serializable {

    private static final long serialVersionUID = 0L;

    private String nachname;

    private String vorname;

    private String behoerde;

    private String rollenId;

    private String benutzername;

    private BenutzerStatus status;

    /**
     * Initialisierung Standardmaessig wird nicht gefiltert (=null).
     */
    public BenutzerSuchkriterien() {
        nachname = null;
        vorname = null;
        behoerde = null;
        rollenId = null;
        status = null;
    }

    /**
     * Initialisierung. Filterung nach der angegebenen {@link RolleDaten}.
     */
    public BenutzerSuchkriterien(String rollenId) {
        nachname = null;
        vorname = null;
        behoerde = null;
        this.rollenId = rollenId;
        status = null;
    }

    /**
     * This method gets the field <tt>nachname</tt>.
     * 
     * @return the field nachname
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * This method sets the field <tt>nachname</tt>.
     * 
     * @param nachname
     *            the new value of the field nachname
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     * This method gets the field <tt>vorname</tt>.
     * 
     * @return the field vorname
     */
    @Size(max = 50)
    public String getVorname() {
        return vorname;
    }

    /**
     * This method sets the field <tt>vorname</tt>.
     * 
     * @param vorname
     *            the new value of the field vorname
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * This method gets the field <tt>behoerde</tt>.
     * 
     * @return the field behoerde
     */
    @Size(max = 50)
    public String getBehoerde() {
        return behoerde;
    }

    /**
     * This method sets the field <tt>behoerde</tt>.
     * 
     * @param behoerde
     *            the new value of the field behoerde
     */
    public void setBehoerde(String behoerde) {
        this.behoerde = behoerde;
    }

    /**
     * This method gets the field <tt>rollenId</tt>.
     * 
     * @return the field rollenId
     */
    @Size(max = 50)
    public String getRollenId() {
        return rollenId;
    }

    /**
     * This method sets the field <tt>rollenId</tt>.
     * 
     * @param rollenId
     *            the new value of the field rollenId
     */
    public void setRollenId(String rollenId) {
        this.rollenId = rollenId;
    }

    /**
     * This method gets the field <tt>benutzername</tt>.
     * 
     * @return the field benutzername
     */
    @Size(max = 20)
    public String getBenutzername() {
        return benutzername;
    }

    /**
     * This method sets the field <tt>benutzername</tt>.
     * 
     * @param benutzername
     *            the new value of the field benutzername
     */
    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
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
     * @param status
     *            the new value of the field status
     */
    public void setStatus(BenutzerStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BenutzerSuchkriterien that = (BenutzerSuchkriterien) o;
        return Objects.equals(nachname, that.nachname) &&
                Objects.equals(vorname, that.vorname) &&
                Objects.equals(behoerde, that.behoerde) &&
                Objects.equals(rollenId, that.rollenId) &&
                Objects.equals(benutzername, that.benutzername) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nachname, vorname, behoerde, rollenId, benutzername, status);
    }
}
