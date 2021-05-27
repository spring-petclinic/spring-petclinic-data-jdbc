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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for the {@link PetController}
 *
 * @author Colin But
 */
@WebMvcTest(value = PetController.class, includeFilters = @ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE))
public class PetControllerTests {

	private static final int TEST_OWNER_ID = 1;
	private static final int TEST_PET_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PetRepository pets;

	@MockBean
	private OwnerRepository owners;

	@BeforeEach
	public void setup() {
		var cat = new PetType(3L, "hamster");
		given(this.pets.findPetTypes()).willReturn(Lists.newArrayList(cat));
		given(this.owners.findById(TEST_OWNER_ID)).willReturn(new Owner());
		given(this.pets.findById(TEST_PET_ID)).willReturn(new Pet());

	}

	@Test
	public void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/new", TEST_OWNER_ID)).andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdatePetForm")).andExpect(model().attributeExists("pet"));
	}

	@Test
	public void testProcessCreationFormSuccess() throws Exception {
		mockMvc
				.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID).param("name", "Betty").param("type", "1")
						.param("birthDate", "2015-02-12"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	public void testProcessCreationFormHasErrors() throws Exception {
		mockMvc
				.perform(
						post("/owners/{ownerId}/pets/new", TEST_OWNER_ID).param("name", "Betty").param("birthDate", "2015-02-12"))
				.andExpect(model().attributeHasNoErrors("owner")).andExpect(model().attributeHasErrors("pet"))
				.andExpect(model().attributeHasFieldErrors("pet", "type"))
				.andExpect(model().attributeHasFieldErrorCode("pet", "type", "required")).andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	public void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("pet")).andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	public void testProcessUpdateFormSuccess() throws Exception {
		mockMvc
				.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).param("name", "Betty")
						.param("type", "1").param("birthDate", "2015-02-12"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	public void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc
				.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).param("name", "Betty")
						.param("birthDate", "2015/02/12"))
				.andExpect(model().attributeHasNoErrors("owner")).andExpect(model().attributeHasErrors("pet"))
				.andExpect(status().isOk()).andExpect(view().name("pets/createOrUpdatePetForm"));
	}

}
