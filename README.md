<div align="center">
  <h1>🏀 Court</h1>
  <p>Aplicación Android para jugadores de baloncesto de todos los niveles</p>

  ![GitHub release](https://img.shields.io/github/v/release/TU-USUARIO/court)
  ![CI](https://github.com/TU-USUARIO/court/actions/workflows/ci.yml/badge.svg)
  ![Android](https://img.shields.io/badge/Android-API%2024%2B-green)
  ![Java](https://img.shields.io/badge/Java-17-orange)
  ![License](https://img.shields.io/badge/License-MIT-blue)
</div>

---

## ¿Qué es Court?

Court es una aplicación móvil Android pensada para jugadores de baloncesto de cualquier nivel — desde jugadores de calle hasta competición federada. Proporciona ejercicios técnicos y de acondicionamiento físico organizados por posición en cancha.

## Capturas de pantalla

> *Próximamente en la v0.3.0*

## Descarga

Descarga la última versión desde la [sección Releases](../../releases).

## Funcionalidades

- 🏀 **5 roles** — Base, Escolta, Alero, Ala-pívot, Pívot
- 📋 **Ejercicios básicos y avanzados** por posición
- 🎥 **Vídeos** de técnica (YouTube embebido) e imágenes locales
- 💪 **Sección fitness** — salto, fuerza y resistencia
- ⭐ **Favoritos** — guarda tus ejercicios preferidos *(v0.5.0)*

## Stack tecnológico

| Componente | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Arquitectura | MVVM |
| Base de datos | Room (SQLite) |
| UI | Fragments + Navigation Component |
| Imágenes | Glide |
| CI/CD | GitHub Actions |

## Desarrollo local

### Requisitos

- Android Studio Hedgehog (2023.1.1) o superior
- JDK 17
- Android SDK API 24+

### Instalación

```bash
git clone https://github.com/TU-USUARIO/court.git
cd court
```

Abre el proyecto con Android Studio y ejecuta en emulador o dispositivo físico.

## Arquitectura

```
com.court.app/
├── data/
│   ├── model/       ← Entidades Room (Rol, Ejercicio, Video, Favorito)
│   ├── db/          ← DAOs + CourtDatabase
│   └── repository/  ← Repositorios (fuente única de verdad)
├── ui/
│   ├── roles/       ← Pantalla selección de rol
│   ├── ejercicios/  ← Lista y detalle de ejercicios
│   ├── videos/      ← Sección de vídeos
│   └── fitness/     ← Entrenamiento físico
└── viewmodel/       ← ViewModels por sección
```

Ver la [Wiki](../../wiki) para el diagrama E/R y MVVM completos.

## Flujo de trabajo

Este proyecto sigue [Conventional Commits](https://www.conventionalcommits.org/) y Git Flow simplificado.

```bash
# Nueva funcionalidad
git checkout -b feature/nombre-feature
git commit -m "feat: descripción del cambio"
git push origin feature/nombre-feature
# Abrir Pull Request → develop
```

Ver la [Guía de contribución](../../wiki/Guia-de-contribucion).

## Autor

**Marcos Martínez Vijuesca** — TFG Centro San Valero · 2025

## Licencia

MIT License — ver [LICENSE](LICENSE)
