package de.bund.bva.isyfact.benutzerverwaltung.gui.benutzerverwaltung.login;

import de.bund.bva.isyfact.common.web.global.AbstractMaskenModel;

import java.io.Serializable;

/**
 * Model der Login Flows
 *
 * @author msg systems ag, Dirk Jäger
 */
public class LoginModel extends AbstractMaskenModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
