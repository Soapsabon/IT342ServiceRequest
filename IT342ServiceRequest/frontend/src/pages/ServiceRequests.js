import React, { useState, useEffect } from 'react';
import { serviceRequestAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';
import ServiceRequestForm from '../components/ServiceRequestForm';
import ServiceRequestList from '../components/ServiceRequestList';
import '../styles/ServiceRequests.css';

const ServiceRequests = () => {
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [editingRequest, setEditingRequest] = useState(null);
  const { user } = useAuth();

  // Fetch requests on mount
  useEffect(() => {
    fetchRequests();
  }, []);

  const fetchRequests = async () => {
    setLoading(true);
    setError('');
    try {
      const response = await serviceRequestAPI.getAll();
      setRequests(response.data);
    } catch (err) {
      setError('Failed to fetch service requests');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleAddClick = () => {
    setEditingId(null);
    setEditingRequest(null);
    setShowForm(true);
  };

  const handleEditClick = (request) => {
    setEditingId(request.id);
    setEditingRequest(request);
    setShowForm(true);
  };

  const handleFormSubmit = async (formData) => {
    setError('');
    setSuccess('');

    try {
      if (editingId) {
        // Update existing request
        await serviceRequestAPI.update(editingId, formData);
        setSuccess('Service request updated successfully!');
      } else {
        // Create new request
        await serviceRequestAPI.create(formData);
        setSuccess('Service request created successfully!');
      }

      setShowForm(false);
      setEditingId(null);
      setEditingRequest(null);
      
      // Refresh the list
      await fetchRequests();

      // Clear success message after 3 seconds
      setTimeout(() => setSuccess(''), 3000);
    } catch (err) {
      const errorMsg = err.response?.data?.message || 'An error occurred';
      setError(errorMsg);
    }
  };

  const handleFormCancel = () => {
    setShowForm(false);
    setEditingId(null);
    setEditingRequest(null);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this service request?')) {
      setError('');
      setSuccess('');
      try {
        await serviceRequestAPI.delete(id);
        setSuccess('Service request deleted successfully!');
        await fetchRequests();
        setTimeout(() => setSuccess(''), 3000);
      } catch (err) {
        const errorMsg = err.response?.data?.message || 'Failed to delete service request';
        setError(errorMsg);
      }
    }
  };

  return (
    <div className="service-requests-container">
      <div className="requests-header">
        <h1>My Service Requests</h1>
        <button onClick={handleAddClick} className="btn-primary">
          + Add New Request
        </button>
      </div>

      {error && <div className="error-message">{error}</div>}
      {success && <div className="success-message">{success}</div>}

      {showForm && (
        <ServiceRequestForm
          onSubmit={handleFormSubmit}
          onCancel={handleFormCancel}
          initialData={editingRequest}
          isEditing={!!editingId}
        />
      )}

      {loading ? (
        <div className="loading">Loading service requests...</div>
      ) : requests.length === 0 ? (
        <div className="empty-state">
          <p>No service requests yet. Create one to get started!</p>
        </div>
      ) : (
        <ServiceRequestList
          requests={requests}
          onEdit={handleEditClick}
          onDelete={handleDelete}
          currentUserId={user?.userId}
        />
      )}
    </div>
  );
};

export default ServiceRequests;
