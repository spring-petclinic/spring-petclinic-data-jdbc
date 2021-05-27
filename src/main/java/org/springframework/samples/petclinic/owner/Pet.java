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

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Simple business object representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Maciej Walkowiak
 */
public class Pet {

	@Id
	private Integer id;

	private String name;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@Column("type_id")
	private Integer type;

	@Column("owner_id")
	private Integer owner;

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOwner() {
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner.getId();
	}

	public boolean isNew() {
		return this.id == null;
	}

	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Pet{" + "id=" + id + ", name='" + name + '\'' + ", birthDate=" + birthDate + ", type=" + type + ", owner="
				+ owner + '}';
	}
}
