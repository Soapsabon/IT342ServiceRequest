import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import '../styles/Home.css';

const Home = () => {
  const { isAuthenticated } = useAuth();

  return (
    <div className="home-container">
      <div className="home-content">
        <h1>Service Request Management System</h1>
        <p>Manage your service requests efficiently with our secure platform</p>

        {isAuthenticated ? (
          <div className="home-authenticated">
            <p>Welcome! You're logged in.</p>
            <Link to="/requests" className="btn-primary btn-large">
              Go to My Requests
            </Link>
          </div>
        ) : (
          <div className="home-unauthenticated">
            <p>Get started by logging in or creating an account</p>
            <div className="home-buttons">
              <Link to="/login" className="btn-primary btn-large">
                Login
              </Link>
              <Link to="/register" className="btn-secondary btn-large">
                Register
              </Link>
            </div>
          </div>
        )}

        <div className="features">
          <h2>Features</h2>
          <ul>
            <li>✓ Secure user authentication with JWT</li>
            <li>✓ Create, view, edit, and delete service requests</li>
            <li>✓ Categorize requests for better organization</li>
            <li>✓ View request history with timestamps</li>
            <li>✓ User-specific request management</li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Home;
