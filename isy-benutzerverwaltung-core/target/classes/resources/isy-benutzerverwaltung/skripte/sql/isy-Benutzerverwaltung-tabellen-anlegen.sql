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
