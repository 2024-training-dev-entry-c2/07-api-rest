# 🍱 Restaurant Management
[![GitHub last commit](https://img.shields.io/github/last-commit/KJRM20/07-api-rest)](#)<br><br>

## Descripción
**Restaurant Management** es una aplicación interactiva diseñada para gestionar información de restaurantes y sus platos, además de permitir realizar pedidos con sus respectivos platos. La aplicación se basa en la arquitectura de la API REST y se utiliza una base de datos en memoria para almacenar los datos.

## Características
1. **Gestión de menús:**
    - **Registro:** añadir nuevos menús especificando nombre.
    - **c:** posibilidad de actualizar información.
    - **Eliminación:** facilita la eliminación de menús obsoletos o irrelevantes.
2. **Gestión de platos:**
    - **Registro:** agrega platos a cada menú con detalles como nombre, descripción y precio.
    - **Edición:** permite modificar información de los platos existentes.
    - **Eliminación:** elimina platos que ya no están disponibles o que han sido reemplazados.
3. **Gestión de clientes:**
    - **Registro:** añadir nuevos clientes especificando nombre, correo electrónico y opcionalmente un valor booleano para indicar si es un cliente frecuente
    - **Edición:** edita la información de los clientes existentes.
    - **Eliminación:** elimina clientes que ya no están en uso o que han sido reemplazados.
4. **Sistema de pedidos:**
    - **Realizar pedido:** se crea un pedido con los platos seleccionados y el cliente que realiza el pedido.
    - **Visualización de pedidos:** muestra los pedidos realizados y sus detalles.
    - **Edición de pedidos:** permite modificar los detalles del pedido.
    - **Eliminación de pedidos:** elimina pedidos que ya no están en uso o que han sido reemplazados.

## Tecnologías
- **Java JDK 17**
- **Gradle**: Herramienta para la construcción y gestión del proyecto.
- **Spring Boot**: Framework para la construcción de aplicaciones Java.
- **Spring Data JPA**: Herramienta para la gestión de bases de datos relacionales.
- **Spring MVC**: Framework para la construcción de aplicaciones web.
- **MySQL**: Base de datos relacional.
- **Sqlyog**: Herramienta para la gestión de bases de datos relacionales.

## Instalación y Configuración
1. **Clona el Repositorio:**
   ```bash
   git clone https://github.com/KJRM20/07-api-rest.git
   ```
2. **Compila y ejecuta el proyecto:**
    - Abre el proyecto en tu IDE y en la terminal ejecuta el comando:
   ```bash
      ./gradlew buid
      ```
    - A continuación, desde la línea de comandos ejecuta el comando:
   ```bash
      ./gradlew bootRun
      ```
3. **Interactúa con el sistema:**
   - Haz uso de Insomnia ó Postman para interactuar con el API REST.

## Arquitectura y Funcionalidad
El proyecto está organizado en una estructura simple basada en POO que emplea una base de datos con MySQL.


## Contacto
Para preguntas, problemas o colaboraciones, por favor, contáctame.
