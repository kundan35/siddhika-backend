# Siddhika Backend API

REST API backend for the [Siddhika](https://github.com/kundan35/Siddhika) spiritual wellness application. Built with Spring Boot and Kotlin, providing endpoints for quotes, meditations, prayers, and scriptures.

## Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Kotlin** | 2.0.21 | Primary language |
| **Spring Boot** | 3.2.5 | Application framework |
| **Spring Data JPA** | 3.2.x | Data persistence |
| **PostgreSQL** | 15+ | Relational database |
| **Gradle** | 8.x | Build tool |
| **Java** | 17 | Runtime |

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        Siddhika Backend                         │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────┐      │
│  │  Controller  │───▶│  Repository  │───▶│  PostgreSQL  │      │
│  │    Layer     │    │    Layer     │    │   Database   │      │
│  └──────────────┘    └──────────────┘    └──────────────┘      │
│         │                                                       │
│         ▼                                                       │
│  ┌──────────────┐                                               │
│  │    Entity    │                                               │
│  │    Models    │                                               │
│  └──────────────┘                                               │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### Layer Responsibilities

| Layer | Package | Responsibility |
|-------|---------|----------------|
| **Controller** | `controller/` | REST endpoints, request/response handling |
| **Repository** | `repository/` | Data access via Spring Data JPA |
| **Model** | `model/` | JPA entities mapping to database tables |
| **Config** | `config/` | CORS configuration, security settings |
| **Seed** | `seed/` | Initial data population on startup |

## Project Structure

```
siddhika-backend/
├── src/main/kotlin/com/siddhika/backend/
│   ├── SiddhikaBackendApplication.kt    # Application entry point
│   ├── config/
│   │   └── CorsConfig.kt                # CORS configuration
│   ├── controller/
│   │   ├── QuoteController.kt           # /api/quotes endpoints
│   │   ├── MeditationController.kt      # /api/meditations endpoints
│   │   ├── PrayerController.kt          # /api/prayers endpoints
│   │   └── ScriptureController.kt       # /api/scriptures endpoints
│   ├── model/
│   │   ├── QuoteEntity.kt               # Quote JPA entity
│   │   ├── MeditationEntity.kt          # Meditation JPA entity
│   │   ├── PrayerEntity.kt              # Prayer JPA entity
│   │   └── ScriptureEntity.kt           # Scripture JPA entity
│   ├── repository/
│   │   ├── QuoteRepository.kt           # Quote data access
│   │   ├── MeditationRepository.kt      # Meditation data access
│   │   ├── PrayerRepository.kt          # Prayer data access
│   │   └── ScriptureRepository.kt       # Scripture data access
│   └── seed/
│       └── DataSeeder.kt                # Populates initial data
├── src/main/resources/
│   └── application.yml                  # Configuration
├── build.gradle.kts                     # Gradle build config
└── README.md
```

## Database Schema

```
┌─────────────────────────────────────────────────────────────────┐
│                         PostgreSQL                              │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────────┐    ┌─────────────────┐                    │
│  │     quotes      │    │   meditations   │                    │
│  ├─────────────────┤    ├─────────────────┤                    │
│  │ id (PK)         │    │ id (PK)         │                    │
│  │ text            │    │ title           │                    │
│  │ author          │    │ description     │                    │
│  │ source          │    │ duration_minutes│                    │
│  │ category        │    │ category        │                    │
│  │ is_favorite     │    │ image_url       │                    │
│  │ created_at      │    │ audio_url       │                    │
│  └─────────────────┘    └─────────────────┘                    │
│                                                                 │
│  ┌─────────────────┐    ┌─────────────────┐                    │
│  │     prayers     │    │   scriptures    │                    │
│  ├─────────────────┤    ├─────────────────┤                    │
│  │ id (PK)         │    │ id (PK)         │                    │
│  │ title           │    │ title           │                    │
│  │ content         │    │ description     │                    │
│  │ category        │    │ content         │                    │
│  │ language        │    │ category        │                    │
│  └─────────────────┘    │ language        │                    │
│                         │ total_chapters  │                    │
│                         └─────────────────┘                    │
└─────────────────────────────────────────────────────────────────┘
```

### Entity Details

#### QuoteEntity
| Field | Type | Description |
|-------|------|-------------|
| `id` | Long | Auto-generated primary key |
| `text` | String | Quote text content |
| `author` | String | Quote attribution |
| `source` | String? | Book/scripture source |
| `category` | String | Category (peace, wisdom, love, etc.) |
| `isFavorite` | Boolean | User favorite flag |
| `createdAt` | Long | Timestamp in milliseconds |

#### MeditationEntity
| Field | Type | Description |
|-------|------|-------------|
| `id` | Long | Auto-generated primary key |
| `title` | String | Meditation name |
| `description` | String | Detailed description |
| `durationMinutes` | Int | Session length |
| `category` | String | Type (breathing, relaxation, guided, etc.) |
| `imageUrl` | String? | Cover image URL |
| `audioUrl` | String? | Audio guide URL |

#### PrayerEntity
| Field | Type | Description |
|-------|------|-------------|
| `id` | Long | Auto-generated primary key |
| `title` | String | Prayer name |
| `content` | String | Full prayer text |
| `category` | String | Type (morning, evening, gratitude, etc.) |
| `language` | String | Language code (default: "en") |

#### ScriptureEntity
| Field | Type | Description |
|-------|------|-------------|
| `id` | Long | Auto-generated primary key |
| `title` | String | Scripture title |
| `description` | String | Brief summary |
| `content` | String | Full text with verses |
| `category` | String | Type (bhagavad_gita, upanishads, etc.) |
| `language` | String | Language code (default: "en") |
| `totalChapters` | Int | Number of chapters |

## API Endpoints

All endpoints follow RESTful conventions with full CRUD support.

### Quotes API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/quotes` | Get all quotes |
| `GET` | `/api/quotes/{id}` | Get quote by ID |
| `GET` | `/api/quotes/category/{category}` | Get quotes by category |
| `POST` | `/api/quotes` | Create new quote |
| `PUT` | `/api/quotes/{id}` | Update quote |
| `DELETE` | `/api/quotes/{id}` | Delete quote |

### Meditations API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/meditations` | Get all meditations |
| `GET` | `/api/meditations/{id}` | Get meditation by ID |
| `GET` | `/api/meditations/category/{category}` | Get by category |
| `POST` | `/api/meditations` | Create new meditation |
| `PUT` | `/api/meditations/{id}` | Update meditation |
| `DELETE` | `/api/meditations/{id}` | Delete meditation |

### Prayers API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/prayers` | Get all prayers |
| `GET` | `/api/prayers/{id}` | Get prayer by ID |
| `GET` | `/api/prayers/category/{category}` | Get by category |
| `POST` | `/api/prayers` | Create new prayer |
| `PUT` | `/api/prayers/{id}` | Update prayer |
| `DELETE` | `/api/prayers/{id}` | Delete prayer |

### Scriptures API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/scriptures` | Get all scriptures |
| `GET` | `/api/scriptures/{id}` | Get scripture by ID |
| `GET` | `/api/scriptures/category/{category}` | Get by category |
| `POST` | `/api/scriptures` | Create new scripture |
| `PUT` | `/api/scriptures/{id}` | Update scripture |
| `DELETE` | `/api/scriptures/{id}` | Delete scripture |

## Response Examples

### GET /api/quotes
```json
[
  {
    "id": 1,
    "text": "Peace comes from within. Do not seek it without.",
    "author": "Buddha",
    "source": "Dhammapada",
    "category": "peace",
    "isFavorite": false,
    "createdAt": 1706400000000
  }
]
```

### GET /api/meditations
```json
[
  {
    "id": 1,
    "title": "Breath Awareness",
    "description": "A simple meditation focusing on the natural rhythm of your breath.",
    "durationMinutes": 5,
    "category": "breathing",
    "imageUrl": null,
    "audioUrl": null
  }
]
```

### POST /api/quotes
```json
{
  "text": "The journey of a thousand miles begins with a single step.",
  "author": "Lao Tzu",
  "source": "Tao Te Ching",
  "category": "wisdom"
}
```

## Getting Started

### Prerequisites

- **Java 17+**: `java -version`
- **PostgreSQL 15+**: Running on port 5432
- **Gradle 8+**: (wrapper included)

### Database Setup

```bash
# Connect to PostgreSQL
psql -U postgres

# Create database and user
CREATE DATABASE siddhika;
CREATE USER siddhika WITH PASSWORD 'siddhika';
GRANT ALL PRIVILEGES ON DATABASE siddhika TO siddhika;
\q
```

### Configuration

Edit `src/main/resources/application.yml` to customize:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/siddhika
    username: siddhika
    password: siddhika
  jpa:
    hibernate:
      ddl-auto: update    # auto-creates tables
    show-sql: false

server:
  port: 8080
```

### Build & Run

```bash
# Clone repository
git clone https://github.com/kundan35/siddhika-backend.git
cd siddhika-backend

# Build
./gradlew build

# Run
./gradlew bootRun
```

The API will be available at `http://localhost:8080`

### Verify Installation

```bash
# Health check
curl http://localhost:8080/api/quotes

# Should return array of seeded quotes
```

## Data Seeding

The `DataSeeder` component automatically populates the database with sample data on first run:

| Content Type | Count | Categories |
|--------------|-------|------------|
| **Quotes** | 10 | peace, wisdom, mindfulness, strength, love, gratitude |
| **Meditations** | 8 | breathing, relaxation, morning, focus, sleep, guided |
| **Prayers** | 6 | morning, general, gratitude, evening, healing, protection |
| **Scriptures** | 4 | bhagavad_gita, yoga_sutras, upanishads |

Seeding only runs when the database is empty (checks `quoteRepository.count() > 0`).

## CORS Configuration

The backend allows cross-origin requests from any origin for development. Configured in `CorsConfig.kt`:

```kotlin
@Configuration
class CorsConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
    }
}
```

## Client Applications

This backend serves the Siddhika multiplatform app:

| Platform | Repository | Network Config |
|----------|------------|----------------|
| **Android** | [Siddhika](https://github.com/kundan35/Siddhika) | `http://<your-ip>:8080` |
| **iOS** | [Siddhika](https://github.com/kundan35/Siddhika) | `http://localhost:8080` |
| **Desktop** | [Siddhika](https://github.com/kundan35/Siddhika) | `http://localhost:8080` |

For Android physical devices, update `NetworkConfig.kt` with your machine's local IP.

## Development

### Running Tests

```bash
./gradlew test
```

### Building JAR

```bash
./gradlew bootJar
# Output: build/libs/siddhika-backend-0.0.1-SNAPSHOT.jar

# Run JAR
java -jar build/libs/siddhika-backend-0.0.1-SNAPSHOT.jar
```

### Docker (Optional)

```dockerfile
FROM eclipse-temurin:17-jre
COPY build/libs/siddhika-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## License

MIT License - See [LICENSE](LICENSE) for details.

## Related Projects

- [Siddhika App](https://github.com/kundan35/Siddhika) - Kotlin Multiplatform mobile/desktop client
