# Overview BOOKING

## Prerequisites

A modern unix-shell with the following installed, all current versions (or newer)

- Java/JDK 15
- Clojure 1.10
- Node (with NPM) 12.0

Run `npm i` to install all the js-related stuff

This project is hosted on googlecloud using Firebase (both firestore and the
realtime-database). To experiment locally install firebase-tools and ditto
emulators. Run the dev.sh script for dev-development. It will open the browser
at [:8010](http://localhost:8010) (the app) and a shadow-cljs dashboard at [:9630](http://localhost:9630) (all the usual goodies).

The firebase-emulators starts by running the fbe.sh script, run it in a
different terminal-window and forget about it. Reach the dashboard at [:4000](http://localhost:4000) in
your browser.

My workflow consists of doing the above and then having a browser window
showing the app which hot-reloads (and preserve state between
reloads ideally) while writing code in the editor. Hot reload is triggered when
saving - basic clojure/script stuff.

## Development

Entrypoint at namespace `booking.core/init!` located
in `src/cljs/booking/core.cljs`. See `shadow-cljs.edn` for project-setup, look
at the key `:booking`.

Html and CSS templates (as well as fonts) located in `template` and are copied
into `public/booking` and hydrated before deployment.

## Deployment

Deployment at google/firebase - serials and identites located
in `src/cljs/booking/data.cljs` replace these with your own if needed.

```
bb --config booking.edn deploy
```

## Other tasks

```
bb --config booking.edn tasks
```

yields

```
stats
template
dev
css
clean
build
fonts
deploy
```

To run a task (use `dev` for development):

```
bb --config booking.edn <task-name>
```

## References

#### Development

- [firebase](https://firebase.google.com/)
- [shadow-cljs](https://shadow-cljs.github.io/docs/UsersGuide.html)
- [clojurescript](https://clojurescript.org/)
- [clojure](https://clojure.org/)
- [tailwind](https://tailwindcss.com/)

#### Build-tools

- [babashka](https://book.babashka.org/)
- [babashka task runner](https://book.babashka.org/#tasks)

