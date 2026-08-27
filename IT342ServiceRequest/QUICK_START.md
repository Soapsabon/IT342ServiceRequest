# Quick Start Guide

## 🚀 Fast Setup (5 minutes)

### Step 1: Open in IntelliJ

1. Open IntelliJ IDEA
2. File → Open → Select `/IT342ServiceRequest/backend` folder
3. Wait for Maven to sync dependencies

### Step 2: Start Backend (IntelliJ Terminal)

```bash
cd backend
./mvnw spring-boot:run
```

✅ Backend runs on `http://localhost:8080`

### Step 3: Start Frontend (New Terminal)

```bash
cd frontend
npm install
npm start
```

✅ Frontend opens on `http://localhost:3000`

---

## 📝 Test the Application

### Create Test Users

**User A:**
- Email: `userA@test.com`
- Username: `userA`
- Password: `password123`

**User B:**
- Email: `userB@test.com`
- Username: `userB`
- Password: `password123`

### Test Ownership Verification

1. Login as User A
2. Create Service Request #1
3. Logout
4. Login as User B
5. Verify User B CANNOT see User A's request
6. Try editing User A's request via curl:

```bash
# Get User A's token first, then try:
curl -X PUT http://localhost:8080/api/requests/1 \
  -H "Authorization: Bearer USER_B_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"title":"Hacked","description":"test","category":"Other"}'
```

✅ Should return `403 Forbidden`

---

## 📂 Project Structure

```
IT342ServiceRequest/
├── backend/          ← Spring Boot (Java 17)
│   └── src/main/java/edu/cit/Jabines/activity01/
│       ├── model/           (User, ServiceRequest entities)
│       ├── controller/       (Auth, ServiceRequest, User)
│       ├── repository/       (JPA repos)
│       ├── security/         (JWT, Spring Security)
│       └── util/             (JWT token provider)
│
├── frontend/         ← React (localhost:3000)
│   └── src/
│       ├── components/       (Navbar, Forms, Lists)
│       ├── pages/            (Login, Register, Requests)
│       ├── context/          (Auth context)
│       ├── services/         (API client)
│       └── styles/           (CSS)
│
└── README.md         ← Full documentation
```

---

## 🔧 Key Backend Files

| File | Purpose |
|------|---------|
| `SecurityConfig.java` | Spring Security + JWT setup |
| `JwtTokenProvider.java` | Generate/validate tokens |
| `JwtAuthenticationFilter.java` | Intercept requests, validate JWT |
| `ServiceRequestController.java` | CRUD + authorization checks |
| `ServiceRequestRepository.java` | Custom queries for ownership |

---

## 🎨 Key Frontend Files

| File | Purpose |
|------|---------|
| `AuthContext.js` | Manage user state + JWT storage |
| `api.js` | Axios instance + token injection |
| `ProtectedRoute.js` | Guard authenticated pages |
| `ServiceRequests.js` | Main feature page |
| `ServiceRequestForm.js` | Modal form for add/edit |

---

## ✅ Checklist Before Submitting

- [ ] Backend runs without errors
- [ ] Frontend loads on localhost:3000
- [ ] Can register new users
- [ ] Can login and receive JWT token
- [ ] Can create service requests
- [ ] Can edit own requests
- [ ] Can delete own requests
- [ ] User B cannot access User A's requests
- [ ] Logout clears token and redirects to login
- [ ] All code committed to GitHub

---

## 🐛 Common Issues

| Issue | Solution |
|-------|----------|
| Backend won't start | Check MySQL is running, verify DB credentials |
| CORS errors | Backend is on 8080, Frontend on 3000 - should work |
| npm install fails | Delete `node_modules`, try again |
| "Cannot find module" | Run `npm install` in frontend folder |
| Token expired | Logout and login again (24hr expiration) |

---

## 📞 API Testing

**Get JWT Token:**
```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"email":"usera@test.com","password":"password123"}'
```

**Create Request:**
```bash
curl -X POST http://localhost:8080/api/requests \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title":"My Request",
    "description":"Description here",
    "category":"Bug Report"
  }'
```

**Get Your Requests:**
```bash
curl -X GET http://localhost:8080/api/requests \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

**Ready to go! 🎉 Open IntelliJ and start the backend!**
