# Service Request Management System

A full-stack web application for managing service requests with JWT-based authentication.

## Features

- **User Authentication**: Secure registration and login with JWT tokens
- **Service Request Management**: Create, read, update, and delete service requests
- **User-Specific Requests**: Users can only access their own requests
- **Category Management**: Organize requests by category
- **Responsive Design**: Works on desktop and mobile devices

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.1.0
- Spring Security
- JWT (JSON Web Tokens)
- JPA/Hibernate
- MySQL 8.0

### Frontend
- React 18.2.0
- React Router v6
- Axios
- CSS3

## Prerequisites

### Backend
- Java Development Kit (JDK) 17 or higher
- Maven 3.6+
- MySQL 8.0 or higher

### Frontend
- Node.js 14+ and npm 6+

## Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE activity01;
```

2. Update database credentials in `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/activity01
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

**Default credentials (in application.properties):**
- Username: `L23Y19W22`
- Password: `123456`

## Backend Setup & Running

### 1. Navigate to backend directory
```bash
cd backend
```

### 2. Build the project
```bash
./mvnw clean install
```

### 3. Run the application
```bash
./mvnw spring-boot:run
```

The backend will start on `http://localhost:8080`

## Frontend Setup & Running

### 1. Navigate to frontend directory
```bash
cd frontend
```

### 2. Install dependencies
```bash
npm install
```

### 3. Run the development server
```bash
npm start
```

The frontend will open at `http://localhost:3000`

## API Endpoints

### Authentication
- **POST** `/api/register` - Register a new user
- **POST** `/api/login` - Login user (returns JWT token)
- **POST** `/api/logout` - Logout user

### Service Requests
- **POST** `/api/requests` - Create a new service request (authenticated)
- **GET** `/api/requests` - Get all service requests for current user (authenticated)
- **GET** `/api/requests/{id}` - Get a specific service request (authenticated)
- **PUT** `/api/requests/{id}` - Update a service request (authenticated)
- **DELETE** `/api/requests/{id}` - Delete a service request (authenticated)

### User
- **GET** `/api/user/profile` - Get current user profile (authenticated)
- **GET** `/api/user/{id}` - Get user by ID

## Usage

### 1. Register Account
- Go to `http://localhost:3000/register`
- Fill in username, email, and password
- Click "Register"

### 2. Login
- Go to `http://localhost:3000/login`
- Enter your email and password
- Click "Login"

### 3. Create Service Request
- After login, click "My Requests" in navbar
- Click "+ Add New Request"
- Fill in title, description, and category
- Click "Create Request"

### 4. View Requests
- All your service requests are displayed in a card grid
- Click "Edit" to modify a request
- Click "Delete" to remove a request

### 5. Logout
- Click "Logout" button in navbar

## Security Features

- **JWT Authentication**: All protected endpoints require a valid JWT token
- **Password Hashing**: Passwords are hashed using BCrypt
- **CORS Configuration**: Configured for localhost:3000, 5173, 5174
- **Authorization**: Backend enforces ownership verification for all requests
- **Token Validation**: Invalid or expired tokens are rejected

## Error Handling

The application includes comprehensive error handling:
- Invalid credentials → 401 Unauthorized
- Unauthorized access → 403 Forbidden
- Missing JWT token → 401 Unauthorized
- Server errors → 400/500 with descriptive messages

## Testing

### Test with Two Different Users

1. **Create User A**
   - Register: username=`userA`, email=`usera@test.com`, password=`password123`

2. **Create User B**
   - Register: username=`userB`, email=`userb@test.com`, password=`password123`

3. **Test Ownership**
   - Login as User A, create a request
   - Logout
   - Login as User B, verify they cannot see/edit/delete User A's request
   - Try manual API call to User A's request → should receive 403 Forbidden

### Example Testing with cURL

```bash
# Register
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}'

# Login and get token
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'

# Create request (replace TOKEN with actual JWT)
curl -X POST http://localhost:8080/api/requests \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"title":"Test Request","description":"This is a test request","category":"Technical Support"}'
```

## Project Structure

```
IT342ServiceRequest/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/edu/cit/Jabines/activity01/
│   │   │   │   ├── model/
│   │   │   │   │   ├── User.java
│   │   │   │   │   └── ServiceRequest.java
│   │   │   │   ├── controller/
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── ServiceRequestController.java
│   │   │   │   │   └── UserController.java
│   │   │   │   ├── repository/
│   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   └── ServiceRequestRepository.java
│   │   │   │   ├── security/
│   │   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   │   ├── JwtAuthenticationEntryPoint.java
│   │   │   │   │   ├── SecurityConfig.java
│   │   │   │   │   └── UserPrincipal.java
│   │   │   │   ├── util/
│   │   │   │   │   └── JwtTokenProvider.java
│   │   │   │   └── Activity01Application.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── Navbar.js
│   │   │   ├── ProtectedRoute.js
│   │   │   ├── ServiceRequestForm.js
│   │   │   └── ServiceRequestList.js
│   │   ├── context/
│   │   │   └── AuthContext.js
│   │   ├── pages/
│   │   │   ├── Home.js
│   │   │   ├── Login.js
│   │   │   ├── Register.js
│   │   │   └── ServiceRequests.js
│   │   ├── services/
│   │   │   └── api.js
│   │   ├── styles/
│   │   │   ├── Global.css
│   │   │   ├── App.css
│   │   │   ├── Navbar.css
│   │   │   ├── AuthPages.css
│   │   │   ├── ServiceRequests.css
│   │   │   ├── ServiceRequestForm.css
│   │   │   ├── ServiceRequestList.css
│   │   │   └── Home.css
│   │   ├── App.js
│   │   └── index.js
│   ├── public/
│   │   └── index.html
│   └── package.json
└── README.md
```

## Future Improvements

- [ ] Add email notifications
- [ ] Implement request filtering and searching
- [ ] Add request status tracking (Open, In Progress, Closed)
- [ ] Implement pagination
- [ ] Add file upload support
- [ ] Create admin dashboard
- [ ] Implement request assignment to support staff

## Troubleshooting

### Backend won't start
- Check if MySQL is running
- Verify database credentials in `application.properties`
- Check if port 8080 is available

### Frontend won't start
- Clear node_modules and reinstall: `rm -rf node_modules && npm install`
- Check if Node.js version is 14+
- Check if port 3000 is available

### CORS errors
- Verify backend is running on http://localhost:8080
- Check CORS configuration in `SecurityConfig.java`
- Clear browser cache and reload

### JWT token issues
- Token may have expired (24 hours default)
- Logout and login again to get a new token
- Check if Authorization header format is correct: `Bearer <token>`

## Support

For issues or questions, please check the backend logs for detailed error messages.

## License

This project is part of the SYSTEMS INTEGRATION AND ARCHITECTURE laboratory course at CIT-U.
