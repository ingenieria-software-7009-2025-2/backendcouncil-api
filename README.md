# ECdB-SISREP-API

## Documentación

La documentación principal de la API se encuentra en [`documentacion/Documentacion-SISREP-ECdB.pdf`](documentacion/Documentacion-SISREP-ECdB.pdf).

Se tiene la documentación de la API con OpenAPI en el siguiente link:
[`http://localhost:8000/api-docs.html`](http://localhost:8080/api-docs.html).
Se busca `/api-docs` en la barra de búsqueda. O si se busca el JSON, se puede consultar en: [`http://localhost:8080/api-docs`](http://localhost:8000/api-docs.html)

Se usa KDoc para la documentación del código de la API, se puede ver sobre target/dokka/index.html al ejecutar este repositorio.

Se deja el siguiente link al video en donde se muestra la ejecución de la primera iteración con detalle:
[`https://youtu.be/4c_whWsSbx0`](https://youtu.be/4c_whWsSbx0).

## Tests

Las pruebas están hechas sobre POSTMAN.
La colección con las pruebas se encuentra en [`postman-collections-and-tests/TESTS-API.postman_collection.json`](postman-collections-and-tests/TESTS-API.postman_collection.json).
Contiene indicaciones dentro de la misma.
Para correr las pruebas, primero hay que importar dicha colección; después clickear `Run`, arriba a la derecha, o con el clic derecho sobre el nombre de la colección, y clickear en Run.
No olvidar seleccionar todas las pruebas, y no cambiar el orden de las mismas.

## Base de Datos

Los archivos necesarios y diagramas relacionados a la base de datos se encuentran en [`database/`](database/).

## Identidad

La identidad del proyecto (logotipo con variaciones y paleta de colores) en [`documentacion/Identidad`](documentacion/Identidad).