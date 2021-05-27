/*
 * Copyright 2012-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.vet.SpecialtyRef;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided by the Spring TestContext
 * Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up time between test
 * execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we don't need to perform
 * application context lookups. See the use of {@link Autowired @Autowired} on the <code>{@link
 * ClinicServiceTests#clinicService clinicService}</code> instance variable, which uses autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in its own transaction, which is
 * automatically rolled back by default. Thus, even if tests insert or otherwise change database state, there is no need
 * for a teardown or cleanup script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is also inherited and can be used
 * for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */
@DataJdbcTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClinicServiceTests {

	@Autowired
	protected OwnerRepository owners;

	@Autowired
	protected PetRepository pets;

	@Autowired
	protected VisitRepository visits;

	@Autowired
	protected VetRepository vets;

	@Test
	public void shouldFindOwnersByLastName() {
		Collection<Owner> owners = this.owners.findByLastName("Davis");
		assertThat(owners.size()).isEqualTo(2);

		owners = this.owners.findByLastName("Daviss");
		assertThat(owners.isEmpty()).isTrue();
	}

	@Test
	public void shouldFindSingleOwner() {
		Owner owner = this.owners.findById(1);
		assertThat(owner.getLastName()).startsWith("Franklin");
	}

	@Test
	@Transactional
	public void shouldInsertOwner() {
		Collection<Owner> owners = this.owners.findByLastName("Schultz");
		int found = owners.size();

		Owner owner = new Owner();
		owner.setFirstName("Sam");
		owner.setLastName("Schultz");
		owner.setAddress("4, Evans Street");
		owner.setCity("Wollongong");
		owner.setTelephone("4444444444");
		this.owners.save(owner);
		assertThat(owner.getId().longValue()).isNotEqualTo(0);

		owners = this.owners.findByLastName("Schultz");
		assertThat(owners.size()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	public void shouldUpdateOwner() {
		Owner owner = this.owners.findById(1);
		String oldLastName = owner.getLastName();
		String newLastName = oldLastName + "X";

		owner.setLastName(newLastName);
		this.owners.save(owner);

		// retrieving new name from database
		owner = this.owners.findById(1);
		assertThat(owner.getLastName()).isEqualTo(newLastName);
	}

	@Test
	public void shouldFindPetWithCorrectId() {
		Pet pet7 = this.pets.findById(7);
		assertThat(pet7.getName()).startsWith("Samantha");
	}

	@Test
	public void shouldFindAllPetTypesOrderedByName() {
		List<PetType> petTypes = this.pets.findPetTypes();
		assertThat(petTypes.get(0).name()).isEqualTo("bird");
		assertThat(petTypes.get(4).name()).isEqualTo("lizard");
	}

	@Test
	@Transactional
	public void shouldInsertPetIntoDatabaseAndGenerateId() {
		Owner owner6 = this.owners.findById(6);
		int found = this.pets.findByOwnerId(6).size();

		Pet pet = new Pet();
		pet.setName("bowser");
		pet.setType(2);
		pet.setOwner(owner6);
		this.pets.save(pet);
		// checks that id has been generated
		assertThat(pet.getId()).isNotNull();

		assertThat(this.pets.findByOwnerId(6).size()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	public void shouldUpdatePetName() {
		Pet pet7 = this.pets.findById(7);
		String oldName = pet7.getName();

		String newName = oldName + "X";
		pet7.setName(newName);
		this.pets.save(pet7);

		pet7 = this.pets.findById(7);
		assertThat(pet7.getName()).isEqualTo(newName);
	}

	@Test
	public void shouldFindVets() {
		Vet vet = this.vets.findAll().get(3);

		assertThat(vet.getLastName()).isEqualTo("Douglas");
		assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
		assertThat(vet.getSpecialties()).containsExactlyInAnyOrder(new SpecialtyRef(3L), new SpecialtyRef(2L));
	}

	@Test
	@Transactional
	public void shouldAddNewVisitForPet() {
		Pet pet7 = this.pets.findById(7);
		int found = this.visits.findByPetId(pet7.getId()).size();

		Visit visit = new Visit();
		visit.setDescription("test");
		visit.setPetId(pet7.getId());
		this.visits.save(visit);

		assertThat(this.visits.findByPetId(pet7.getId()).size()).isEqualTo(found + 1);
		assertThat(visit.getId()).isNotNull();
	}

	@Test
	public void shouldFindVisitsByPetId() throws Exception {
		Collection<Visit> visits = this.visits.findByPetId(7);
		assertThat(visits.size()).isEqualTo(2);
		Visit[] visitArr = visits.toArray(new Visit[visits.size()]);
		assertThat(visitArr[0].getDate()).isNotNull();
		assertThat(visitArr[0].getPetId()).isEqualTo(7);
	}

}
