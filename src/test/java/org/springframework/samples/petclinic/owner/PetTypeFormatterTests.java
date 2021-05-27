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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class for {@link PetTypeFormatter}
 *
 * @author Colin But
 */
@ExtendWith(MockitoExtension.class)
public class PetTypeFormatterTests {

	@Mock
	private PetRepository pets;

	private PetTypeFormatter petTypeFormatter;

	@BeforeEach
	public void setup() {
		this.petTypeFormatter = new PetTypeFormatter(pets);
	}

	@Test
	public void testPrint() {
		var petType = new PetType(3L, "Hamster");
		String petTypeName = this.petTypeFormatter.print(petType, Locale.ENGLISH);
		assertThat(petTypeName).isEqualTo("Hamster");
	}

	@Test
	public void shouldParse() throws ParseException {
		Mockito.when(this.pets.findPetTypes()).thenReturn(makePetTypes());
		PetType petType = petTypeFormatter.parse("Bird", Locale.ENGLISH);
		assertThat(petType.name()).isEqualTo("Bird");
	}

	@Test
	public void shouldThrowParseException() throws ParseException {
		Mockito.when(this.pets.findPetTypes()).thenReturn(makePetTypes());

		assertThatThrownBy(() -> petTypeFormatter.parse("Fish", Locale.ENGLISH)).isInstanceOf(ParseException.class);
	}

	/**
	 * Helper method to produce some sample pet types just for test purpose
	 *
	 * @return {@link Collection} of {@link PetType}
	 */
	private List<PetType> makePetTypes() {
		List<PetType> petTypes = new ArrayList<>();
		petTypes.add(new PetType(1L, "Dog"));
		petTypes.add(new PetType(2L, "Bird"));
		return petTypes;
	}

}
