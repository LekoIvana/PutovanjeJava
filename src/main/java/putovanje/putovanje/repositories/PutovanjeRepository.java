package putovanje.putovanje.repositories;

import putovanje.putovanje.models.Putovanje;
import putovanje.putovanje.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PutovanjeRepository extends JpaRepository<Putovanje, Long> {
    List<Putovanje> findByUserId(Long userId);
    Optional<Putovanje> findByIdAndUser(Long id, User user);
}