# Overview

## Deployment

Deployment at google/firebase

```
bb --config booking.edn deploy
```

## Development

Entrypoint in namespace at `eykt.core/init!` located
in `src/cljs/eykt/core.cljs`

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

