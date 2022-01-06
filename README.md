# nrpk-22
Hoved-repo for alt relatert NRPK

## Forutsetninger

Du trenger terminal-tilgang til et unix-miljø (WSL/Windows, MacOS eller Linux). 

Hovedaktivetene skjer i Clojure/Clojurescript så dette må være på plass. Babashka brukes som oppgave-system som erstatning for `Make`.

## Vanlige oppgaver

##### Oppstart

Utfør fra terminalen

```bash
bb eykt-dev
```

for å starte kompilator-prosessen, åpne nettleseren på [http://localhost:9630/build/eykt](http://localhost:9630) og trykk `start`. 

Nå kan du redigere kode fra `/src/cljs/eykt/...` og kompilatoren oppdaterer innholdet automatisk. Tilstand bevares mellom oppdateringene.

##### Publisering

For å publisere endringer kjører du følgende fra terminalen

```bash
bb eykt-publish
```

Dette skal ideelt sett lagre endringene på github og sette igang skript som utfører 2 sett med tester, en lokal som må være ok, og en CI som også må være ok før publisering. CI blir ikke satt igang dersom den lokale testen feiler.

Dersom alle tester er ok publiseres app'en som du så kan nå på [https://eykt.nrpk.no](https://eykt.nrpk.no) 


## Verktøy som brukes

For å komme i gang, installer node og npm:
- [node/npm](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)

Skap utviklingsmiljø (etter `git clone`, se til at også node/npm er installert):

```bash
npm i
```

Annet
- [babashka](https://book.babashka.org/)
- [babashka task runner](https://book.babashka.org/#tasks)
- [shadow-cljs](https://shadow-cljs.github.io/docs/UsersGuide.html)
- [firebase](https://firebase.google.com/)
- [clojurescript](https://clojurescript.org/)
- [clojure](https://clojure.org/)
- [tailwind](https://tailwindcss.com/)

@TODO
Ideelt vil det være en docker-container som inneholder alt sammen inkludert utviklingsmiljø. 