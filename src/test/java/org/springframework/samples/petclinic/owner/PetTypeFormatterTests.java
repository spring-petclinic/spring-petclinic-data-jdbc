package org.springframework.samples.petclinic.owner;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        PetType petType = new PetType(3L, "Hamster");
        String petTypeName = this.petTypeFormatter.print(petType, Locale.ENGLISH);
        assertThat(petTypeName).isEqualTo("Hamster");
    }

    @Test
    public void shouldParse() throws ParseException {
        Mockito.when(this.pets.findPetTypes()).thenReturn(makePetTypes());
        PetType petType = petTypeFormatter.parse("Bird", Locale.ENGLISH);
        assertThat(petType.getName()).isEqualTo("Bird");
    }

    @Test
    public void shouldThrowParseException() throws ParseException {
        Mockito.when(this.pets.findPetTypes()).thenReturn(makePetTypes());

        assertThatThrownBy(() -> petTypeFormatter.parse("Fish", Locale.ENGLISH))
            .isInstanceOf(ParseException.class);
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
