---
-- #%L
-- IsyFact Benutzerverwaltung Core
-- %%
-- Copyright (C) 2016 - 2017 Bundesverwaltungsamt (BVA)
-- %%
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- 
--      http://www.apache.org/licenses/LICENSE-2.0
-- 
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
-- #L%
---
--DROP TABLE IF EXISTS Benutzer_Rollen;
--DROP TABLE IF EXISTS Benutzer;

CREATE TABLE Benutzer (
  id number(20) NOT NULL,
  behoerde varchar(255),
  bemerkung varchar(255),
  benutzername varchar(255) NOT NULL,
  emailAdresse varchar(255),
  fehlanmeldeVersuche number(11) NOT NULL,
  letzteAbmeldung DATE ,
  letzteAnmeldung DATE ,
  nachname varchar(255),
  passwort varchar(255),
  status number(11) DEFAULT NULL,
  telefonnummer varchar(255),
  "version" number(11) NOT NULL,
  vorname varchar(255),
  --KEY Benutzer_Benutzername (benutzername),
  PRIMARY KEY (id)
);

CREATE TABLE Benutzer_Rollen (
  benutzer_id NUMBER(20) NOT NULL,
  rolle varchar(255) NOT NULL,
  --KEY Benutzer_Rollen_Benutzer_Id (benutzer_id),
  CONSTRAINT Benutzer_Rollen FOREIGN KEY (benutzer_id) REFERENCES Benutzer (id),
  PRIMARY KEY (benutzer_id,rolle)
);
