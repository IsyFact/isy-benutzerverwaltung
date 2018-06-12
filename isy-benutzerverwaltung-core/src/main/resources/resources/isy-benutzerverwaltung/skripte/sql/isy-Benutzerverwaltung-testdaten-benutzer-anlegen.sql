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
-- user: user
-- passwort: qwertZ1!

INSERT INTO BENUTZER (ID,BEHOERDE,BEMERKUNG,BENUTZERNAME,PASSWORT,STATUS,VERSION,FEHLANMELDEVERSUCHE,VORNAME,NACHNAME )
VALUES
  (1, 'Behörde1', 'Bemerkung1', 'user', '$2a$10$FrhgE995.OkufUJ7YKlRHekMyTKRjzUAo54HkQ46ANlZibPY0n4ym', 2, 0,
   0, 'Max', 'Mustermann');
Commit;

INSERT INTO ROLLE (PRIMARYKEY, ID, NAME, VERSION) VALUES (2, 'Benutzer', 'Normaler Benutzer', 0);
INSERT INTO ROLLE (PRIMARYKEY, ID, NAME, VERSION) VALUES (3, 'Administrator', 'Administrator mit allen Rechten', 0);
Commit;

INSERT INTO BENUTZER_ROLLEN(BENUTZER_PK,ROLLE_PK)
VALUES (1,2);
INSERT INTO BENUTZER_ROLLEN(BENUTZER_PK,ROLLE_PK)
VALUES (1,3);
Commit;
