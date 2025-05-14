# 🏗️ Arquitectura del Sistema de Gestión de Biblioteca

## 📚 Descripción General

El sistema está construido siguiendo una arquitectura en capas, aplicando los principios SOLID y utilizando Spring Framework. La persistencia se maneja en memoria usando estructuras de datos concurrentes para garantizar la thread-safety.

## 🔄 Diagrama de Capas

```
┌─────────────────┐
│   Controllers   │ ← REST APIs
├─────────────────┤
│    Services     │ ← Lógica de Negocio
├─────────────────┤
│  Repositories   │ ← Persistencia
└─────────────────┘
```

## 🎯 Principios SOLID Aplicados

### Single Responsibility Principle (SRP)
- Cada clase tiene una única responsabilidad
- Ejemplo: `LibroController` solo maneja las peticiones HTTP, `LibroService` maneja la lógica de negocio

### Interface Segregation Principle (ISP)
- Interfaces específicas para cada tipo de operación
- Ejemplo: `LibroRepository`, `UsuarioRepository`, `PrestamoRepository`

### Dependency Inversion Principle (DIP)
- Las dependencias se inyectan a través de constructores
- Se depende de abstracciones (interfaces) no de implementaciones

## 🏛️ Componentes Principales

### 📋 Modelos
- `Libro`: Representa un libro en el sistema
- `Usuario`: Representa un usuario del sistema
- `Prestamo`: Representa un préstamo de un libro a un usuario
- `EstadoLibro`: Enum que representa los posibles estados de un libro

### 🔍 Repositories
- Implementan la persistencia en memoria
- Usan `ConcurrentHashMap` para thread-safety
- Generan IDs automáticamente usando `AtomicLong`

### ⚙️ Services
- Implementan la lógica de negocio
- Validan reglas de negocio
- Manejan las excepciones del dominio

### 🌐 Controllers
- Exponen la API REST
- Manejan la conversión de datos
- Implementan el manejo de errores HTTP

## 🔒 Manejo de Concurrencia

- Uso de `ConcurrentHashMap` para almacenamiento thread-safe
- `AtomicLong` para generación de IDs
- Métodos sincronizados donde es necesario

## 🎭 Patrones de Diseño

### Repository Pattern
- Abstrae la persistencia de datos
- Permite cambiar la implementación sin afectar otras capas

### Dependency Injection
- Spring maneja la inyección de dependencias
- Facilita el testing y reduce el acoplamiento

### Builder Pattern
- Usado en algunos modelos para construcción flexible
- Ejemplo: creación de préstamos con validaciones

## 🚨 Manejo de Errores

### Excepciones de Dominio
- `LibroNoEncontradoException`
- `UsuarioNoEncontradoException`
- `PrestamoInvalidoException`

### Manejo Global
- `GlobalExceptionHandler` convierte excepciones a respuestas HTTP
- Mapeo consistente de errores de dominio a códigos HTTP

## 📊 Decisiones de Diseño

### Persistencia en Memoria
- Uso de `ConcurrentHashMap` por su thread-safety
- Compromiso entre rendimiento y consistencia

### Validaciones
- Validaciones de negocio en capa de servicio
- Validaciones básicas en modelos

### Estados
- Estados de libro manejados con enum
- Estados de usuario como strings para flexibilidad

## 🔄 Flujo de Datos Típico

1. Request HTTP llega al Controller
2. Controller valida formato y llama al Service
3. Service aplica lógica de negocio
4. Repository persiste los cambios
5. Response viaja de vuelta al cliente

## 📈 Escalabilidad

El diseño permite:
- Agregar nuevos tipos de recursos
- Cambiar la persistencia a una base de datos real
- Extender funcionalidad sin modificar código existente 