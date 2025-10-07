# Opis aplikacije

Projekat „Rezervacije aranžmana“ izrađen je za predmet Napredne Java tehnologije. Repozitorijum sadrži backend u folderu `njtrezervacijearanzmana` (Spring Boot, JPA, MySQL, JWT) i frontend u folderu `njtfront` (React).

Kratko uputstvo za preuzimanje i pokretanje:
1) Kloniranje:
   git clone https://github.com/aleksandrabegovic/rezervacije_aranzmana.git
   cd rezervacije_aranzmana

2) Backend (Spring Boot):
   cd njtrezervacijearanzmana
   # Podesi konekciju ka MySQL u src/main/resources/application.properties
   mvn spring-boot:run
   # API na http://localhost:8080/api

3) Frontend (React):
   cd ../njtfront
   npm install
   npm start
   # Aplikacija na http://localhost:3000 (povezana na backend preko http://localhost:8080)

Napomena: Uloguj se nalozima koje definišeš u bazi (uloge ADMIN/AGENT).
