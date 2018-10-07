package org.springframework.samples.petclinic.vet;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Maciej Walkowiak
 */
public interface SpecialtyRepository extends Repository<Specialty, Long> {

    @Transactional(readOnly = true)
    @Cacheable("specialty")
    Specialty findById(Long id);
}
