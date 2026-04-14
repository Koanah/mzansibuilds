# Mzansi Builds

A modern platform for developers to store, manage, and track their software projects with an intuitive web interface and powerful backend infrastructure.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [System Requirements](#system-requirements)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [Security](#security)
- [Database](#database)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

---

## 🎯 Overview

**Mzansi Builds** is a comprehensive project management platform designed specifically for developers. It provides a centralized location to store, organize, and monitor all your software development projects in one place. With a clean, user-friendly interface powered by Vaadin, and robust backend infrastructure built on Spring Boot, Mzansi Builds streamlines project tracking and management.

### Key Highlights
- 📱 **Responsive Web Interface**: Built with Vaadin for a seamless user experience
- 🔐 **Secure**: Built-in authentication and authorization
- 🗄️ **Data Persistence**: Reliable database integration
- 🚀 **Scalable**: Modular architecture ready for expansion

---

## ✨ Features

- **Project Management**: Create, update, and organize your development projects
- **Secure Authentication**: User login and role-based access control
- **Project Tracking**: Monitor project progress and status
- **Data Persistence**: Reliable storage and retrieval of project information
- **Modern UI**: Intuitive Vaadin-based interface
- **RESTful APIs**: Programmatic access to project data

---

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.5.13
- **Java Version**: Java 17
- **Build Tool**: Maven
- **ORM**: Spring Data JPA
- **Security**: Spring Security
- **REST API**: Spring Web

### Frontend
- **UI Framework**: Vaadin 24.9.13
- **Languages**: JavaScript, TypeScript, HTML

### Database
- **Development**: H2 Database (embedded)
- **Production Ready**: Easily integrable with PostgreSQL, MySQL, or other relational databases

### Language Composition
- **JavaScript**: ~98% (frontend build artifacts)
- **Java**: ~0.8% (backend logic)
- **TypeScript**: ~0.9% (type definitions)
- **HTML**: ~0.3% (markup)

### Additional Tools
- **Lombok**: Reduces boilerplate code
- **Maven Wrapper**: Ensures consistent Maven version across environments

---

## 💻 System Requirements

### Minimum Requirements
- **Java**: JDK 17 or higher
- **Maven**: 3.6.0 or higher (or use the included Maven Wrapper)
- **Memory**: 2GB RAM minimum
- **Disk Space**: 500MB free space

### Recommended Requirements
- **Java**: JDK 21 or later
- **Memory**: 4GB+ RAM
- **Database**: PostgreSQL 12+ (for production)

---

## 📥 Installation & Setup

### Step 1: Clone the Repository

```bash
git clone https://github.com/Koanah/mzansibuilds.git
cd mzansibuilds
```

### Step 2: Verify Java Installation

```bash
java -version
```

Ensure you have Java 17 or higher installed.

### Step 3: Install Dependencies

The project uses Maven. If Maven is not installed, use the provided Maven Wrapper:

**On Linux/macOS:**
```bash
./mvnw clean install
```

**On Windows:**
```bash
mvnw.cmd clean install
```

Or if Maven is installed globally:
```bash
mvn clean install
```

---

## 🚀 Running the Application

### Development Mode

**Using Maven Wrapper (Linux/macOS):**
```bash
./mvnw spring-boot:run
```

**Using Maven Wrapper (Windows):**
```bash
mvnw.cmd spring-boot:run
```

**Using Maven (if installed globally):**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080` by default.

### Accessing the Application

1. Open your web browser
2. Navigate to `http://localhost:8080`
3. Log in with your credentials (default credentials may be configured in `application.properties`)

---

## 📂 Project Structure

```
mzansibuilds/
├── .mvn/                    # Maven wrapper configuration
├── src/
│   ├── main/
│   │   ├── java/            # Java backend source code
│   │   │   └── com/mzansibuilds/
│   │   │       ├── controller/     # REST controllers
│   │   │       ├── service/        # Business logic
│   │   │       ├── repository/     # Data access layer
│   │   │       ├── entity/         # JPA entities
│   │   │       └── config/         # Configuration classes
│   │   └── resources/
│   │       ├── application.properties    # Configuration
│   │       ├── templates/          # Vaadin templates
│   │       └── static/             # Static assets
│   └── test/
│       ├── java/            # Unit and integration tests
│       └── resources/       # Test configuration
├── pom.xml                  # Maven configuration and dependencies
├── mvnw                     # Maven Wrapper (Linux/macOS)
├── mvnw.cmd                 # Maven Wrapper (Windows)
├── .gitignore               # Git ignore rules
└── README.md               # This file
```

---

## 🔐 Security

### Built-in Features
- **Spring Security**: Configured for authentication and authorization
- **Password Encryption**: Passwords are hashed using industry-standard algorithms
- **CSRF Protection**: Enabled by default
- **SQL Injection Prevention**: Using parameterized queries via JPA

### Best Practices
1. Never commit sensitive credentials to the repository
2. Use environment variables for configuration
3. Keep dependencies updated: `./mvnw versions:display-dependency-updates`
4. Regularly audit your application logs

---

## 🗄️ Database

### Development Database (H2)

The application uses H2 embedded database by default for development:

**Access H2 Console:**
- URL: `http://localhost:8080/h2-console`
- Driver: `org.h2.Driver`
- JDBC URL: `jdbc:h2:mem:testdb`

### Production Database

Replace H2 with PostgreSQL or MySQL by adding the appropriate database driver dependency to `pom.xml` and updating the connection settings in your application configuration.

---

## 🐛 Troubleshooting

### Common Issues

#### Issue: Build fails with "Java version mismatch"
**Solution**: Verify your Java version is 17+
```bash
java -version
```

#### Issue: Port 8080 already in use
**Solution**: Change the port in `application.properties`
```properties
server.port=8081
```

#### Issue: H2 console not accessible
**Solution**: Ensure H2 console is enabled in `application.properties`
```properties
spring.h2.console.enabled=true
```

#### Issue: Maven dependencies not downloading
**Solution**: Clear Maven cache and retry
```bash
rm -rf ~/.m2/repository
./mvnw clean install
```

### Logs Location

Application logs are typically printed to the console during development.

---

## 🤝 Contributing

We welcome contributions from the community! Please follow these guidelines:

### Getting Started
1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

### Code Standards
- Follow Java naming conventions (camelCase for variables/methods, PascalCase for classes)
- Write clear, descriptive commit messages
- Add unit tests for new functionality
- Update documentation as needed

### Testing
Before submitting a PR, ensure all tests pass:

```bash
./mvnw test
```

### Reporting Issues
- Check existing issues before creating new ones
- Provide detailed descriptions with reproduction steps
- Include error logs and system information

---

## 📄 License

This project is licensed under the MIT License. See the LICENSE file for details.

---

## 📞 Support & Contact

For issues, questions, or suggestions:
- **GitHub Issues**: [Mzansi Builds Issues](https://github.com/Koanah/mzansibuilds/issues)
- **GitHub Discussions**: Available in the repository
- **Author**: [Koanah](https://github.com/Koanah)

---

## 🎉 Acknowledgments

- Built with [Spring Boot](https://spring.io/projects/spring-boot)
- UI Framework: [Vaadin](https://vaadin.com/)
- Package Management: [Maven](https://maven.apache.org/)

---

**Happy Building! 🚀**