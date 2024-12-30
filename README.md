# Money Planner

A personal finance tracking web application built with Java Spring Boot and Vue.js that helps users manage their income and expenses efficiently.

![Landing Page](/Misc/LandingPage.png)

## Features

- Multi-user support with secure authentication
- Personalized dashboard for financial overview
- Income and expense tracking
- Categorized spending analysis
- Income source management
- Secure data isolation between users

![Dashboard](/Misc/DashBoard.png)

## Tech Stack

- **Backend**
    - Java 17
    - Spring Boot 3.3.5
    - MySQL Database
    - RESTful API

- **Frontend**
    - Vue.js
    - Tailwind CSS
    - BootstrapVue

## Prerequisites

Before running this application, make sure you have the following installed:
- Java Development Kit (JDK) 17 or higher
- Node.js and npm
- MySQL Server
- Maven

## Database Schema

The database schema and Entity-Relationship Diagram (ERR) can be found in the `MySQL` directory of this project.

![EER](/Misc/EER.png)

## Project Structure

```
money-planner/
├── backend/
│   ├── src/
│   └── pom.xml
├── frontend/
│   ├── src/
│   └── package.json
├── MySQL/
│   ├── schema.sql
│   └── ERR-diagram.png
└── README.md
```

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/LvLTroubleshooter/money_planner.git
   cd money-planner
   ```

2. Set up the database:
   ```bash
   mysql -u root -p < MySQL/money_planner.sql
   ```

3. Configure the backend:
   ```bash
   cd backend
   # Update application.properties with your database credentials
   mvn clean install
   ```

4. Set up the frontend:
   ```bash
   cd frontend
   npm install
   ```

## Running the Application

1. Start the backend server:
   ```bash
   cd src
   mvn spring-boot:run
   ```

2. In a new terminal, start the frontend development server:
   ```bash
   cd frontendvue
   npm run dev
   ```

3. Access the application at `http://localhost:3000`

## API Documentation

The API endpoints are documented using Swagger UI and can be accessed at `http://localhost:8080` when running the application.

## Key Features

### User Authentication
- Secure login and registration system
- JWT-based authentication
- Password encryption

### Dashboard
- Overview of financial status
- Income and expense summaries
- Spending categories visualization
- Income source breakdown

### Transaction Management
- Add/edit/delete income entries
- Add/edit/delete expense entries
- Categorize transactions also Add/edit/delete them
- Transaction history

## Security

- Password hashing
- JWT token authentication
- Data isolation between users
- SQL injection prevention
- CORS configuration

## License

This is a personal project created for educational and portfolio purposes. All rights reserved.

## Contact

Ismail - ismailm.bahlaoui@gmail.com

## Acknowledgments

- Spring Boot Documentation
- Vue.js Documentation
- Tailwind CSS Documentation