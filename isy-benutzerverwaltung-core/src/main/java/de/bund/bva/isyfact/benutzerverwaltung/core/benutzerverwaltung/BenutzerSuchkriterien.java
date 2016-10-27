package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung;

import de.bund.bva.isyfact.benutzerverwaltung.core.basisdaten.daten.RolleDaten;

import javax.validation.constraints.Size;
import java.io.Serializable;

//import org.hibernate.validator.constraints.Length;

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
}
