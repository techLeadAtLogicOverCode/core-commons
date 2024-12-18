## step 1 :-

fix `dependencies.conf`

---

## step 2 :-

upgrade version in `build.sbt`

---

## step 3 :-

`sbt dependencyCodeGenerator/test`


`sbt clean compile scalafmt`


`sbt +publishLocal`