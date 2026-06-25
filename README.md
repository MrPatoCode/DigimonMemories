# Digimon Memories

**Digimon Memories** es una aplicación de Android inspirada en el universo Digimon, diseñada para poner a prueba tu memoria. El proyecto implementa una arquitectura moderna y robusta, enfocada en la escalabilidad y el mantenimiento, ideal para demostrar habilidades técnicas avanzadas en el desarrollo móvil.

## Características
- **Juego de Memoria:** Encuentra las parejas de Digimon antes de que se agoten tus intentos.
- **Lista de Digimon:** Explora los diferentes Digimon obtenidos a través de la [Digimon API](https://digi-api.com/).
- **Estadísticas Locales:** Registro de puntuaciones, intentos y errores utilizando base de datos local.
- **Gestión de Estado:** Manejo reactivo de la UI mediante `LiveData` y estados de carga/error.
- **Conectividad:** Verificación en tiempo real del estado de la red.

## Stack
- **Lenguaje:** Kotlin
- **Arquitectura:** Clean Architecture + MVVM (Model-View-ViewModel).
- **Inyección de Dependencias:** Hilt.
- **Red:** Retrofit 2 y OkHttp3 para el consumo de APIs REST.
- **Base de Datos:** Room para persistencia de datos local.
- **UI & Navegación:** 
  - Jetpack Navigation Component.
  - ViewBinding.
  - Material Design 3.
  - Glide para la carga eficiente de imágenes.
- **Asincronía:** Corrutinas de Kotlin y Flow/LiveData.

## Estructura del Proyecto
El proyecto sigue los principios de **Clean Architecture**, dividiéndose en tres capas principales:

1.  **Data:** Implementación de repositorios, fuentes de datos (Remote y Local) y mappers de datos.
2.  **Domain:** Lógica de negocio pura (Entities, Repository Interfaces y Use Cases).
3.  **Presentation:** Fragments, ViewModels y Adapters, siguiendo el patrón MVVM.

<img width="720" height="1612" alt="Screenshot_20260624_222407" src="https://github.com/user-attachments/assets/4705bade-f048-4c78-b95c-125dfcc36700" />

<img width="720" height="1612" alt="Screenshot_20260624_222809" src="https://github.com/user-attachments/assets/9e86938d-49fe-40e6-ae90-1bffe67cbb6a" />

<img width="720" height="1612" alt="Screenshot_20260624_222841" src="https://github.com/user-attachments/assets/608c0b23-3d07-4b1c-af09-19f2c70e7173" />



