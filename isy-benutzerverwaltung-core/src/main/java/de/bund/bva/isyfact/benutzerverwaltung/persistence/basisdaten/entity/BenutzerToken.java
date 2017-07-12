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


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

/**
 * Die persistente Entität eines {@link BenutzerToken}s.
 *
 * @author Bjoern Saxe, msg systems ag
 * @author Alexander Salvanos, msg systems ag
 *
 */
@Entity
public class BenutzerToken implements Serializable {

    private static final long serialVersionUID = -0L;

    @Id
    private String token;

    private Date ablaufDatum;

    @OneToOne
    private Benutzer benutzer;

    @Version
    private int version;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getAblaufDatum() {
        return ablaufDatum;
    }

    public void setAblaufDatum(Date ablaufDatum) {
        this.ablaufDatum = ablaufDatum;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }
}
