package de.bund.bva.isyfact.benutzerverwaltung.sicherheit;

/*-
 * #%L
 * IsyFact Benutzerverwaltung Sicherheit
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
