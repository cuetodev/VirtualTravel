<h1 align = center>Virtual Travel - By David</h1>

<!-- ABOUT THE PROJECT -->
## Sobre el proyecto

Este proyecto fue realizado durante la etapa final de la formación de back en Bosonit (2022/05)

El proyecto se basa en hacer el back de una agencia de autobuses donde se podrán hacer reservas, consultar reservas, autobuses, emails enviados, se podrán reenviar emails, todo esto bajo la seguridad de un token con JWT, además, la aplicación dispone de varios servicios como Kafka para la comunicación entre el Backempresa y los Backwebs, además del uso de eureka y de un gateway como balanceador de carga.

### Tecnologías

* Springboot - https://spring.io/projects/spring-boot
* Java       - https://www.w3schools.com/java/java_intro.asp

Librerías usadas de interés

* Lombok     - https://projectlombok.org/
* Gson       - https://github.com/google/gson
* JJWT       - https://jwt.io/
* JavaX Mail - https://mvnrepository.com/artifact/javax.mail

<!-- GETTING STARTED -->
## Empezando

### Prerrequisitos

Importante tener instalado / actualizado lo siguiente:
* Docker
* JDK 17
* Postman (Opcional) - Para hacer pruebas

### Instalación

1. Primero hacemos un clone del repositorio

```
git clone https://github.com/cuetodev/FormacionNXT-Backend/tree/master/EFinal_Cloud
```

2. Después inicializamos el docker compose yéndonos a la ruta del proyecto desde un CMD:

 ```
 docker compose up -d
 ```

3. Importar en Postman el archivo `EFinal_Cloud.postman_collection.json` (Recomendado)

<!-- USAGE EXAMPLES -->
## Uso

Lista de servicios con sus respectivos puertos:

|Servicio|Puerto|
|---|---|
|Backempresa|`:8085`|
|Backweb|`:8086`|
|Backweb2|`:8087`|

Es importante saber que todos los endpoints llevan seguridad, es decir, sin un token no podremos acceder, por lo tanto, lo primero es adquirir ese token

---

#### Token - (Empresa)

|Método|URL|Descripción|
|-|-|-|
|`GET`|`api/v0/token`|Devuelve un token para poder utilizar los demás endpoints según correo y contraseña|

##### Headers*
|Key|Value|
|-|-|
|`email`|`user@user.es`|
|`password`|`user`|

---

#### Crear una reserva - (Empresa y Web)

`Authorization: Bearer Token`

|Método|URL|Descripción|
|-|-|-|
|`POST`|`api/v0/booking`|Crea una reserva|

##### Ejemplo de un body enviado
```
{
    "city": "Valencia",
    "name": "David",
    "surname": "Cueto",
    "phone": "123456789",
    "email": "ejemplo@gmail.com",
    "date": "2022-05-25",
    "hour": 20
}
```

---

#### Listado de reservas - (Empresa y Web)

`Authorization: Bearer Token`

|Método|URL|Descripción|
|-|-|-|
|`GET`|`api/v0/booking/Valencia?lowerDate=2022-05-10&upperDate=2022-12-20&lowerHour=7&upperHour=21`|Listado de reservas según los parámetros dados|

|Parámetros|Valor|Descripción|Comentarios|
|-|-|-|-|
|`lowerDate`|`2022-05-10`|Muestra las reservas posteriores a la fecha dada|Parámetro obligatorio|
|`upperDate`|`2022-12-15`|Muestra las reservas anteriores a la fecha dada||
|`lowerHour`|`8`|Muestra las reservas posteriores a la hora dada|Hora dada no incluida|
|`upperHour`|`20`|Muestra las reservas anteriores a la hora dada|Hora dada no incluida|

##### Ejemplo de un body recibido
```
[
    {
        "id": 1,
        "city": "Valencia",
        "name": "David",
        "surname": "Cueto",
        "phone": "123456789",
        "email": "davidcueto.dev@gmail.com",
        "date": "2022-05-23",
        "hour": 20.0,
        "status": "Accepted"
    },
    {
        "id": 2,
        "city": "Valencia",
        "name": "David",
        "surname": "Cueto",
        "phone": "123456789",
        "email": "davidcueto.dev@gmail.com",
        "date": "2022-05-22",
        "hour": 20.0,
        "status": "Accepted"
    }
]
```

---

#### Listado de autobuses disponibles - (Empresa y Web)

`Authorization: Bearer Token`

|Método|URL|Descripción|
|-|-|-|
|`GET`|`api/v0/bus/available/Valencia?lowerDate=2022-04-01&upperDate=2022-09-01&lowerHour=7&upperHour=21`|Listado de autobuses según los parámetros dados|

|Parámetros|Valor|Descripción|Comentarios|
|-|-|-|-|
|`lowerDate`|`2022-05-10`|Muestra los autobuses posteriores a la fecha dada|Parámetro obligatorio|
|`upperDate`|`2022-12-15`|Muestra los autobuses anteriores a la fecha dada||
|`lowerHour`|`8`|Muestra los autobuses posteriores a la hora dada|Hora dada no incluida|
|`upperHour`|`20`|Muestra los autobuses anteriores a la hora dada|Hora dada no incluida|

##### Ejemplo de un body recibido
```
[
    {
        "city": "Valencia",
        "departureDate": "2022-05-22",
        "departureHour": 20.0,
        "availableSeats": 38
    },
    {
        "city": "Valencia",
        "departureDate": "2022-05-24",
        "departureHour": 20.0,
        "availableSeats": 35
    }
]
```

---

#### Listado de correos enviados - (Empresa)

`Authorization: Bearer Token`

|Método|URL|Descripción|
|-|-|-|
|`GET`|`api/v0/mails?lowerDate=2022-05-10&upperDate=2022-09-20&lowerHour=&upperHour=`|Listado de correos según los parámetros dados|

|Parámetros|Valor|Descripción|Comentarios|
|-|-|-|-|
|`lowerDate`|`2022-05-10`|Muestra los correos posteriores a la fecha dada|Parámetro obligatorio|
|`upperDate`|`2022-12-15`|Muestra los correos anteriores a la fecha dada||
|`lowerHour`|`8`|Muestra los autobuses correos a la hora dada|Hora dada no incluida|
|`upperHour`|`20`|Muestra los autobuses correos a la hora dada|Hora dada no incluida|

##### Ejemplo de un body recibido
```
[
    {
        "city": "Valencia",
        "receiver": "davidcueto.dev@gmail.com",
        "bookingDate": "2022-05-23",
        "bookingHour": 20.0,
        "subject": "Virtual Travel - Booking Accepted"
    },
    {
        "city": "Valencia",
        "receiver": "davidcueto.dev@gmail.com",
        "bookingDate": "2022-05-22",
        "bookingHour": 20.0,
        "subject": "Virtual Travel - Booking Accepted"
    }
]
```

---

#### Reenviar un correo - (Empresa)

`Authorization: Bearer Token`

|Método|URL|Descripción|
|-|-|-|
|`POST`|`api/v0/mail`|Reenviar un correo|

##### Ejemplo de un body enviado
```
{
    "city": "Valencia",
    "email": "davidcueto.dev@gmail.com",
    "date": "2022-05-22",
    "hour": 20
}
```

---

<!-- CONTACT -->
## Contacto

<p align="center">
  <a href="https://www.linkedin.com/in/david-cueto-garrido-2158a2227"><img src="https://img.shields.io/badge/LinkedIn-blue?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn Badge"></a>
  <a href="https://www.twitter.com/davidcueto_dev"><img src="https://img.shields.io/badge/Twitter-blue?style=for-the-badge&logo=twitter&logoColor=white" alt="Twitter Badge"></a>
</p>

* Link del proyecto: https://github.com/cuetodev/FormacionNXT-Backend/tree/master/EFinal_Cloud
