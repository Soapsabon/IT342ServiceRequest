import React from 'react';
import '../styles/ServiceRequestList.css';

const ServiceRequestList = ({ requests, onEdit, onDelete, currentUserId }) => {
  const formatDate = (dateString) => {
    const options = {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    };
    return new Date(dateString).toLocaleDateString('en-US', options);
  };

  const getCategoryColor = (category) => {
    const colors = {
      'Technical Support': '#3498db',
      'Bug Report': '#e74c3c',
      'Feature Request': '#2ecc71',
      'General Inquiry': '#f39c12',
      'Other': '#95a5a6',
    };
    return colors[category] || '#95a5a6';
  };

  return (
    <div className="requests-list">
      {requests.map((request) => (
        <div key={request.id} className="request-card">
          <div className="request-header">
            <div className="request-title-section">
              <h3 className="request-title">{request.title}</h3>
              <span
                className="request-category"
                style={{ backgroundColor: getCategoryColor(request.category) }}
              >
                {request.category}
              </span>
            </div>
            <div className="request-actions">
              <button
                onClick={() => onEdit(request)}
                className="btn-edit"
                title="Edit this request"
              >
                ✎ Edit
              </button>
              <button
                onClick={() => onDelete(request.id)}
                className="btn-delete"
                title="Delete this request"
              >
                🗑 Delete
              </button>
            </div>
          </div>

          <div className="request-body">
            <p className="request-description">{request.description}</p>
          </div>

          <div className="request-footer">
            <span className="request-meta">
              Created: {formatDate(request.dateCreated)}
            </span>
            <span className="request-meta">By: {request.createdByUsername}</span>
          </div>
        </div>
      ))}
    </div>
  );
};

export default ServiceRequestList;
