/*
 * Copyright 2012-2018 the original author or authors.
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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Maciej Walkowiak
 */
@Controller
class VetController {

    private final VetRepository vets;
    private final SpecialtyRepository specialties;

    public VetController(VetRepository clinicService, SpecialtyRepository specialties) {
        this.vets = clinicService;
        this.specialties = specialties;
    }

    @GetMapping("/vets.html")
    public String showVetList(Map<String, Object> model) {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for Object-Xml mapping
        Vets vets = new Vets();
        vets.getVetList().addAll(vetToVetDto(this.vets.findAll()));
        model.put("vets", vets);
        return "vets/vetList";
    }

    @GetMapping({"/vets"})
    public @ResponseBody
    Vets showResourcesVetList() {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for JSon/Object mapping
        Vets vets = new Vets();
        vets.getVetList().addAll(vetToVetDto(this.vets.findAll()));
        return vets;
    }

    private List<VetDto> vetToVetDto(Collection<Vet> vets) {
        return vets.stream()
                   .map(this::vetToVetDto)
                   .collect(Collectors.toList());
    }

    private VetDto vetToVetDto(Vet v) {
        List<Specialty> specialtyList = v.getSpecialties()
                                         .stream()
                                         .map(s -> specialties.findById(s.getSpecialty()))
                                         .collect(Collectors.toList());
        return new VetDto(v.getId(), v.getFirstName(), v.getLastName(), specialtyList);
    }

}
