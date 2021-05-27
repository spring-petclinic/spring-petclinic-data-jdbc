package org.springframework.samples.petclinic.vet;

import org.springframework.data.relational.core.mapping.Table;

/**
 * Reference between {@link Vet} and {@link Specialty}
 * required to have many to many relationships in Spring Data JDBC.
 *
 * @author Maciej Walkowiak
 */
@Table("vet_specialty")
public record SpecialtyRef(Long specialty) {}
