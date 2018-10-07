package org.springframework.samples.petclinic.vet;

import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Objects;

/**
 * Reference between {@link Vet} and {@link Specialty}
 * required to have many to many relationships in Spring Data JDBC.
 *
 * @author Maciej Walkowiak
 */
@Table("vet_specialty")
public class SpecialtyRef implements Serializable {
    private Long specialty;

    public SpecialtyRef(Long specialty) {
        this.specialty = specialty;
    }

    public Long getSpecialty() {
        return specialty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SpecialtyRef that = (SpecialtyRef) o;
        return Objects.equals(specialty, that.specialty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialty);
    }
}
