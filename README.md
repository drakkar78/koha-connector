# 🧩 Koha Connector for MidPoint

Este conector permite integrar [Koha ILS](https://koha-community.org/) con [Evolveum MidPoint](https://evolveum.com/midpoint/), usando su API REST oficial.

---

## 📌 Características soportadas

- ✅ Autenticación contra `/api/v1/authentication`
- ✅ CRUD completo de usuarios (`/api/v1/patrons`)
- ✅ Mapeo de atributos comunes (`userid`, `surname`, `email`, etc.)
- ✅ Compatible con ConnId y MidPoint 4.9+

---

## 📦 Requisitos

- Java 17+
- MidPoint 4.9+
- Koha ≥ 21.11 con API REST habilitada
- Token-based authentication habilitada (`/api/v1/authentication`)

---

## ⚙️ Configuración en MidPoint

### 1. Importar el conector

1. Empaqueta con Maven:

```bash
mvn clean package
```

2. Copia el `.jar` resultante:

```bash
cp target/koha-connector-1.0.0.jar midpoint_home/icf-connectors/
```

3. Reinicia MidPoint.

---

### 2. Crear recurso

- Tipo de conector: **Koha Connector**
- Clase: `com.upeu.connector.KohaConnector`
- Parámetros:

| Propiedad   | Descripción                  |
|-------------|------------------------------|
| `baseUrl`   | URL base del Koha Staff API  |
| `username`  | Usuario del API REST         |
| `password`  | Contraseña del API           |

---

## 🔐 API de Koha usada

- Autenticación: `POST /api/v1/authentication`
- Operaciones de usuario:
    - `GET /api/v1/patrons`
    - `GET /api/v1/patrons/{id}`
    - `POST /api/v1/patrons`
    - `PUT /api/v1/patrons/{id}`
    - `DELETE /api/v1/patrons/{id}`

---

## 🛠️ Compilar

```bash
mvn clean package
```

---

## 🧪 Test (opcional)

Este proyecto aún **no incluye test unitarios**, pero puede extenderse usando `connector-test-common` para pruebas ConnId.

---

## 🧑‍💻 Autores

- UPeU CRAI - Proyecto MidPoint
- Basado en ConnId y MidPoint 4.x

---

## 📄 Licencia

Este proyecto usa licencia MIT. Ver archivo [LICENSE](LICENSE).
