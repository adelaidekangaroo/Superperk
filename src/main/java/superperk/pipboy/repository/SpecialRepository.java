package superperk.pipboy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import superperk.pipboy.model.Special;

@Repository
public interface SpecialRepository extends JpaRepository<Special, Long> {
}