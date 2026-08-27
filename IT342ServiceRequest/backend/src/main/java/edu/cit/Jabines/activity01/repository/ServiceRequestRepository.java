package edu.cit.Jabines.activity01.repository;

import edu.cit.Jabines.activity01.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByCreatedBy(Long userId);
    Optional<ServiceRequest> findByIdAndCreatedBy(Long id, Long userId);
}
