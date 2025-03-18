# Cosas importantes

Para compilar el CUP hay que tener en cuenta que la clase que implementa se llama `ConstructorASTTiny` no `AnalizadorSintacticoTiny` como en la pr2.

Teniendo el javacc.jar en la misma carpeta que el respositorio se puede compilar el javacc con este comando: `java -cp ..\..\..\..\javacc.jar org.javacc.parser.Main .\spec.jj`
