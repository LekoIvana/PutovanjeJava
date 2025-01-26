package putovanje.putovanje.services;

import putovanje.putovanje.models.Putovanje;
import putovanje.putovanje.models.Putovanje;
import putovanje.putovanje.repositories.PutovanjeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PutovanjeService {

    @Autowired
    private PutovanjeRepository putovanjeRepository;

    public Page<Putovanje> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return putovanjeRepository.findAll(pageable);
    }

}
