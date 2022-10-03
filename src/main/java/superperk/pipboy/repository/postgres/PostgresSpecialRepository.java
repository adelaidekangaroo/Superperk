package superperk.pipboy.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import superperk.pipboy.model.postgres.Special;

@Repository
public interface PostgresSpecialRepository extends JpaRepository<Special, Long> {
}