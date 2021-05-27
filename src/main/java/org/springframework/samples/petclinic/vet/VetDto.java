package org.springframework.samples.petclinic.vet;

import java.util.List;

/**
 * @author Maciej Walkowiak
 */
public record VetDto(Long id, String firstName, String lastName, List<Specialty> specialties) {

    public int getNrOfSpecialties() {
        return specialties.size();
    }
}
