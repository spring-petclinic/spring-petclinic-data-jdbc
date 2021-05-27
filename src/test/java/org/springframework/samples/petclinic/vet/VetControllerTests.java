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
package org.springframework.samples.petclinic.vet;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

/**
 * Test class for the {@link VetController}
 */
@WebMvcTest(VetController.class)
public class VetControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VetRepository vets;

	@MockBean
	private SpecialtyRepository specialties;

	@BeforeEach
	public void setup() {
		var james = new Vet();
		james.setFirstName("James");
		james.setLastName("Carter");
		james.setId(1L);
		var helen = new Vet();
		helen.setFirstName("Helen");
		helen.setLastName("Leary");
		helen.setId(2L);
		var radiology = new Specialty(1L, "radiology");
		helen.addSpecialty(radiology);
		given(this.vets.findAll()).willReturn(Lists.newArrayList(james, helen));
		given(this.specialties.findById(1L)).willReturn(radiology);
	}

	@Test
	public void testShowVetListHtml() throws Exception {
		mockMvc.perform(get("/vets.html")).andExpect(status().isOk()).andExpect(model().attributeExists("vets"))
				.andExpect(view().name("vets/vetList"));
	}

	@Test
	public void testShowResourcesVetList() throws Exception {
		var actions = mockMvc.perform(get("/vets").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		actions.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.vets[0].id").value(1));
	}

}
