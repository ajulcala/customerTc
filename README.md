# customerTc
- Primero se debe ejecutar el servidor Eureka que se encuentra en el repositorio https://github.com/ajulcala/eurekaTc
- Segundo se debe ejecutar el servidor Config Server que se encuentra en el repositorio https://github.com/ajulcala/configServerTc
- Tercero registrar CUSTOMER https://github.com/ajulcala/customerTc

CLIENTE PERSONAL

buscar en Postman:  http://localhost:8090/api/customer

Estructura de datos para registrar Customer de tipo Personal enviado a traves de POST:

{
    "dni": "85658965",
    "name": "Maria",
    "apep": "Perez",
    "apem": "Torres",
    "birth_date": "2000-05-04",
    "address":"Calle los Angeles 230"
}

El sistema no dejara registrar a otro CUSTOMER con el mismo DNI.
Para listar los CUSTOMER hacemos una peticion a traves de GET


para buscar un CUSTOMER por ID hacemos una peticion GET:

http://localhost:8090/api/customer/61855fd5c040b66aa9c526d2

para buscar un CUSTOMER por DNI hacemos una peticion GET:

http://localhost:8090/api/customer/dni/85658965

Para Modificar un CUSTOMER por ID hacemos una peticion PUT con la estructura antes mencionada: 

http://localhost:8090/api/customer/61855fd5c040b66aa9c526d2

y finalmente para eliminar un CUSTOMER por ID hacemos una peticion DELETE:

http://localhost:8090/api/customer/61855fd5c040b66aa9c526d2

CLIENTE EMPRESARIAL

buscar en Postman: http://localhost:8090/api/customerbusiness
Estructura de datos para registrar Customer de tipo Empresarial enviado a traves de POST:

{
    "ruc": "124585784584",
    "business_name": "INVERSIONES MENSOZA",
    "createAt": "2021-10-29T15:18:11.156+00:00",
    "address": "AV GRAU 256"
}

de la misma manera el sistema no dejara registrar a un usuario con el mismo ruc

para buscar un CUSTOMER por RUC hacemos una peticion GET:

http://localhost:8090/api/customerbusiness/ruc/124585784584

Para Modificar un CUSTOMER por ID hacemos una peticion PUT con la estructura antes mencionada: 

http://localhost:8090/api/customerbusiness/61855fd5c040b66aa9c526d2

y finalmente para eliminar un CUSTOMER por ID hacemos una peticion DELETE:

http://localhost:8090/api/customerbusiness/61855fd5c040b66aa9c526d2
