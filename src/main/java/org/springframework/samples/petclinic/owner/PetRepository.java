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
package org.springframework.samples.petclinic.owner;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>Pet</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here:
 * <a href="https://docs.spring.io/spring-data/jpa/reference/repositories/query-methods-details.html#repositories.query-methods.query-creation">Query Creation</a>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Maciej Walkowiak
 */
public interface PetRepository extends Repository<Pet, Integer> {

	/**
	 * Retrieve all {@link PetType}s from the data store.
	 *
	 * @return a Collection of {@link PetType}s.
	 */
	@Query("select * from pet_type order by name")
	@Transactional(readOnly = true)
	List<PetType> findPetTypes();

	@Query("select * from pet_type where id = :typeId")
	PetType findPetType(@Param("typeId") Integer id);

	/**
	 * Retrieve a {@link Pet} from the data store by id.
	 *
	 * @param id the id to search for
	 * @return the {@link Pet} if found
	 */
	@Transactional(readOnly = true)
	Pet findById(Integer id);

	/**
	 * Save a {@link Pet} to the data store, either inserting or updating it.
	 *
	 * @param pet the {@link Pet} to save
	 */
	void save(Pet pet);

	@Query("select * from pet where owner_id = :ownerId and name = :name")
	List<Pet> findByOwnerIdAndName(@Param("ownerId") Integer ownerId, @Param("name") String name);

	@Query("select * from pet where owner_id = :ownerId")
	List<Pet> findByOwnerId(@Param("ownerId") Integer id);
}
