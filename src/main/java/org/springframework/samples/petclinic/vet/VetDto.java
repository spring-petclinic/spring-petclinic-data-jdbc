package org.springframework.samples.petclinic.vet;

import java.util.List;

/**
 * @author Maciej Walkowiak
 */
public class VetDto {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Specialty> specialties;

    VetDto(Long id, String firstName, String lastName, List<Specialty> specialities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialties = specialities;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Specialty> getSpecialties() {
        return specialties;
    }

    public int getNrOfSpecialties() {
        return specialties.size();
    }
}
