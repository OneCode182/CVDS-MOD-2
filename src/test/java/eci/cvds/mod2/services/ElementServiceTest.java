package eci.cvds.mod2.services;

import eci.cvds.mod2.exceptions.ElementNotFoundException;
import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.reposistories.ElementsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElementsServiceTest {

    @Mock
    private ElementsRepo elementsRepo;

    @InjectMocks
    private ElementsService elementsService;

    private RecreationalElement element;

    @BeforeEach
    void setUp() {
        element = new RecreationalElement("1","Bicicleta",5,"Bici de montaña");
    }

    @Test
    void shouldReturnElementById() {
        when(elementsRepo.findById("1")).thenReturn(Optional.of(element));

        RecreationalElement result = elementsService.getElementById("1");

        assertEquals("Bicicleta", result.getName());
        verify(elementsRepo).findById("1");
    }

    @Test
    void shouldThrowWhenElementIdNotFound() {
        when(elementsRepo.findById("2")).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> elementsService.getElementById("2"));
    }

    @Test
    void shouldCreateNewElementIfNotExists() {
        when(elementsRepo.findByName("Bicicleta")).thenReturn(Optional.empty());
        when(elementsRepo.save(element)).thenReturn(element);

        RecreationalElement result = elementsService.createElement(element);

        assertEquals("Bicicleta", result.getName());
        verify(elementsRepo).save(element);
    }

    @Test
    void shouldUpdateQuantityIfElementExists() {
        RecreationalElement existing = new RecreationalElement("1","Bicicleta",2,"");

        when(elementsRepo.findByName("Bicicleta")).thenReturn(Optional.of(existing));
        when(elementsRepo.save(existing)).thenReturn(existing);

        element.setQuantity(3);

        RecreationalElement result = elementsService.createElement(element);

        assertEquals(5, result.getQuantity());
        verify(elementsRepo).save(existing);
    }

    @Test
    void shouldDeleteElementSuccessfully() {
        when(elementsRepo.findById("1")).thenReturn(Optional.of(element));

        RecreationalElement result = elementsService.deleteElement("1");

        assertEquals("Bicicleta", result.getName());
        verify(elementsRepo).deleteById("1");
    }

    @Test
    void shouldUpdateElementSuccessfully() {
        RecreationalElement newElement = new RecreationalElement();
        newElement.setName("Updated");
        newElement.setDescription("New desc");
        newElement.setQuantity(10);

        when(elementsRepo.findById("1")).thenReturn(Optional.of(element));
        when(elementsRepo.save(any())).thenReturn(element);

        RecreationalElement result = elementsService.updateElement("1", newElement);

        assertEquals("Updated", result.getName());
        verify(elementsRepo).save(element);
    }

    @Test
    void shouldReduceElementQuantity() {
        when(elementsRepo.findById("1")).thenReturn(Optional.of(element));

        elementsService.reduceElementQuantity("1", 2);

        verify(elementsRepo).save(element);
    }

    @Test
    void shouldIncreaseElementQuantity() {
        when(elementsRepo.findById("1")).thenReturn(Optional.of(element));

        elementsService.increaseElementQuantity("1", 3);

        verify(elementsRepo).save(element);
    }

    @Test
    void shouldReturnAllElements() {
        List<RecreationalElement> elements = List.of(element);
        when(elementsRepo.findAll()).thenReturn(elements);

        List<RecreationalElement> result = elementsService.getAll();

        assertEquals(1, result.size());
        verify(elementsRepo).findAll();
    }
}
