package de.bund.bva.isyfact.benutzerverwaltung.sicherheit;

import de.bund.bva.pliscommon.aufrufkontext.impl.AufrufKontextImpl;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Für die Benutzerverwaltung erweiterter Aufrufkontext.
 * 
 * @author Jonas Zitz, jonas.zitz@capgemini.com
 */
public class BenutzerverwaltungAufrufKontextImpl extends AufrufKontextImpl implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
     * Gibt an, ob ein Passwort im AufrufKontext als Hash oder im Klartext hinterlegt ist. Initial ist dieser
     * Parameter immer {@link Boolean#FALSE}
     */
    private boolean passwortIstHash = false;

    public boolean isPasswortIstHash() {
        return passwortIstHash;
    }

    public void setPasswortIstHash(boolean passwortIstHash) {
        this.passwortIstHash = passwortIstHash;
    }
    
	@Override
	public String getDurchfuehrenderSachbearbeiterName() {
		if (super.getDurchfuehrenderSachbearbeiterName() == null) {
			return StringUtils.EMPTY;
		}
		return super.getDurchfuehrenderSachbearbeiterName();
	}

	@Override
	public String getDurchfuehrenderBenutzerKennung() {
		
		if (super.getDurchfuehrenderBenutzerKennung() == null) {
			return StringUtils.EMPTY;
		}
		return super.getDurchfuehrenderBenutzerKennung();
	}

}
