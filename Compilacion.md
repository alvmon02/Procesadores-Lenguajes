# Ejemplos de compilacion para las distintas herramientas

## JFlex

Es bastante facil al tener una GUI. Ejecutar `jflex.jar` y se abrirá una GUI donde puedes explorar y seleccionar el archivo que contiene la información.

## JavaCC

## CUP

Para compilar CUP es similar a JavaCC al tener que hacerlo mediante un comando. Este sería el comando ejemplo:

``` ssh, pws
java -jar .\cup.jar -interface -parser <Analizador> -symbols <ClaseLexica> -nopositions <SPEC>
```

Donde:

- `<Analizador>` es el nombre de la clase que implementa el analizador.
- `<ClaseLexica>` es el nombre de la clase que define las clases léxicas.
- `<Spec>` es el archivo que contiene la especificación CUP.
