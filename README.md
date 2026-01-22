[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/UoY0NL5F)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-blue?style=for-the-badge&logo=java&logoColor=white)

<img width="1170" height="757" alt="imagen" src="https://github.com/user-attachments/assets/0bbcf5f8-68f6-43ad-b427-f701907777b7" />
<img width="1113" height="1031" alt="imagen" src="https://github.com/user-attachments/assets/6ee346cf-64f9-401d-80b8-d74170e60e91" />
<img width="658" height="591" alt="imagen" src="https://github.com/user-attachments/assets/221162d0-f78c-4605-b5d7-cc4bf7a50c40" />



# Robots Game (JavaFX)

Proyecto inspirado en el clásico juego **Gnome-Robots**, desarrollado como parte de un Trabajo Práctico para la facultad.

## 🚀 Características
- **Entidades:** Jugador, RobotX1, RobotX2 y Diamantes.
- **Mecánicas:** Movimiento en 8 direcciones, colisiones, celdas incendiadas y niveles progresivos.
- **Interfaz:** Menú principal, tablero dinámico y ventana de derrota desarrollados con JavaFX y FXML.
- **Arquitectura:** Diseño basado en el patrón Modelo-Vista-Controlador (MVC).

## Mecánicas especiales
- **Teleport Randomly**: Mueve al jugador a cualquier celda vacía, con riesgo de caer junto a un robot.
- **Teleport Safely**: Garantiza una ubicación libre de amenazas inmediatas (con usos limitados por nivel).

## Patrón MVC 
- **Modelo**: Lógica del juego, tablero y entidades (Tablero.java, Ubicacion.java).
- **Vista**: Interfaz gráfica desarrollada con FXML y JavaFX (VistaTablero.java, VistaMenu.java).
- **Controlador**: Gestión de eventos y comunicación entre el modelo y la vista.

## 🛠️ Tecnologías utilizadas
- Java 17+
- JavaFX
- FXML

## 📦 Cómo ejecutarlo
1. Clonar el repositorio.
2. Importar como proyecto Maven/Gradle (o agregar las librerías de JavaFX manualmente).
3. Ejecutar la clase `Robots.java`.
