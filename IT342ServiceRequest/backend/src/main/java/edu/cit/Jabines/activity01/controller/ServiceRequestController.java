package edu.cit.Jabines.activity01.controller;

import edu.cit.Jabines.activity01.model.ServiceRequest;
import edu.cit.Jabines.activity01.model.User;
import edu.cit.Jabines.activity01.repository.ServiceRequestRepository;
import edu.cit.Jabines.activity01.repository.UserRepository;
import edu.cit.Jabines.activity01.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "http://localhost:5174"})
public class ServiceRequestController {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * CREATE - POST /api/requests
     * Create a new service request for the authenticated user
     */
    @PostMapping
    public ResponseEntity<?> createServiceRequest(@Valid @RequestBody ServiceRequest request, Authentication authentication) {
        try {
            // Get authenticated user from security context
            UserPrincipal principal = (UserPrincipal) authentication.getDetails();
            Long userId = principal.getUserId();
            String username = principal.getUsername();

            // Verify user exists
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "User not found"));
            }

            // Set ownership
            request.setCreatedBy(userId);
            request.setCreatedByUsername(username);

            // Save and return
            ServiceRequest saved = serviceRequestRepository.save(request);

            log.info("Service request created by user {}: {}", userId, saved.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception e) {
            log.error("Error creating service request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error creating service request: " + e.getMessage()));
        }
    }

    /**
     * READ - GET /api/requests
     * Get all service requests for the authenticated user
     */
    @GetMapping
    public ResponseEntity<?> getUserServiceRequests(Authentication authentication) {
        try {
            UserPrincipal principal = (UserPrincipal) authentication.getDetails();
            Long userId = principal.getUserId();

            List<ServiceRequest> requests = serviceRequestRepository.findByCreatedBy(userId);

            log.info("Retrieved {} service requests for user {}", requests.size(), userId);
            return ResponseEntity.ok(requests);

        } catch (Exception e) {
            log.error("Error retrieving service requests", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error retrieving service requests"));
        }
    }

    /**
     * READ - GET /api/requests/{id}
     * Get a specific service request (only if user owns it)
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getServiceRequest(@PathVariable Long id, Authentication authentication) {
        try {
            UserPrincipal principal = (UserPrincipal) authentication.getDetails();
            Long userId = principal.getUserId();

            // Get request and verify ownership
            Optional<ServiceRequest> request = serviceRequestRepository.findByIdAndCreatedBy(id, userId);

            if (request.isEmpty()) {
                log.warn("Unauthorized access attempt to request {} by user {}", id, userId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "You do not have permission to view this service request"));
            }

            return ResponseEntity.ok(request.get());

        } catch (Exception e) {
            log.error("Error retrieving service request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error retrieving service request"));
        }
    }

    /**
     * UPDATE - PUT /api/requests/{id}
     * Update a service request (only if user owns it)
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateServiceRequest(@PathVariable Long id, @Valid @RequestBody ServiceRequest updatedRequest, Authentication authentication) {
        try {
            UserPrincipal principal = (UserPrincipal) authentication.getDetails();
            Long userId = principal.getUserId();

            // Get request and verify ownership
            Optional<ServiceRequest> existingRequest = serviceRequestRepository.findByIdAndCreatedBy(id, userId);

            if (existingRequest.isEmpty()) {
                log.warn("Unauthorized update attempt to request {} by user {}", id, userId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "You do not have permission to update this service request"));
            }

            ServiceRequest request = existingRequest.get();

            // Update only allowed fields (not dateCreated or createdBy)
            request.setTitle(updatedRequest.getTitle());
            request.setDescription(updatedRequest.getDescription());
            request.setCategory(updatedRequest.getCategory());

            ServiceRequest saved = serviceRequestRepository.save(request);

            log.info("Service request {} updated by user {}", id, userId);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            log.error("Error updating service request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error updating service request: " + e.getMessage()));
        }
    }

    /**
     * DELETE - DELETE /api/requests/{id}
     * Delete a service request (only if user owns it)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteServiceRequest(@PathVariable Long id, Authentication authentication) {
        try {
            UserPrincipal principal = (UserPrincipal) authentication.getDetails();
            Long userId = principal.getUserId();

            // Get request and verify ownership
            Optional<ServiceRequest> request = serviceRequestRepository.findByIdAndCreatedBy(id, userId);

            if (request.isEmpty()) {
                log.warn("Unauthorized delete attempt to request {} by user {}", id, userId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "You do not have permission to delete this service request"));
            }

            serviceRequestRepository.deleteById(id);

            log.info("Service request {} deleted by user {}", id, userId);
            return ResponseEntity.ok(Map.of("message", "Service request deleted successfully"));

        } catch (Exception e) {
            log.error("Error deleting service request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error deleting service request"));
        }
    }

}
