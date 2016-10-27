package de.bund.bva.isyfact.benutzerverwaltung.sicherheit;

import java.io.Serializable;

public class LoginVerwalter<T extends BenutzerverwaltungAufrufKontextImpl> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T aufrufKontext;

    public T getAufrufKontext() {
        return aufrufKontext;
    }

    public void setAufrufKontext(T aufrufKontext) {
        this.aufrufKontext = aufrufKontext;
    }

}
