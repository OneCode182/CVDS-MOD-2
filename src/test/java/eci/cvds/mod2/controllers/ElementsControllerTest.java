package eci.cvds.mod2.controllers;

import eci.cvds.mod2.exceptions.ElementException;
import eci.cvds.mod2.exceptions.ElementNotFoundException;
import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.services.ElementsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ElementsControllerTest {

    @InjectMocks
    private ElementsController elementsController;

    @Mock
    private ElementsService elementsService;

    private RecreationalElement element;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        element = new RecreationalElement("1", "Ball", 10, "A round ball");
        //element = new RecreationalElement();
    }

    @Test
    void shouldGetElementById_Success() {
        // Arrange
        when(elementsService.getElementById("1")).thenReturn(element);

        // Act
        ResponseEntity<RecreationalElement> response = elementsController.getElementById("1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(element, response.getBody());
        verify(elementsService, times(1)).getElementById("1");
    }

    @Test
    void shouldThrowExceptionWhenElementNotFound() {
        // Arrange: Simulamos que no se encuentra el elemento
        when(elementsService.getElementById("999")).thenThrow(new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND));

        // Act y Assert: Verificamos que se lanza una ElementNotFoundException cuando no se encuentra el elemento
        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class, () -> {
            elementsController.getElementById("999");
        });

        // Verificamos el mensaje de la excepción
        assertTrue(exception.getMessage().contains(ElementException.ELEMENT_NOT_FOUND));
    }


    @Test
    void shouldCreateElement() {
        // Arrange
        when(elementsService.createElement(any(RecreationalElement.class))).thenReturn(element);

        // Act
        ResponseEntity<String> response = elementsController.createElement(element);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Element successfully created", response.getBody());
        verify(elementsService, times(1)).createElement(any(RecreationalElement.class));
    }

    @Test
    void shouldGetElementByName_Success() {
        // Arrange: Crear un RecreationalElement para simular la respuesta del servicio
        RecreationalElement element = new RecreationalElement("1", "Ball", 10, "A round ball");

        // Simula la respuesta del servicio cuando se solicita un elemento por nombre
        when(elementsService.getElementByName("Ball")).thenReturn(element);

        // Act: Llamamos al método del controlador
        ResponseEntity<RecreationalElement> response = elementsController.getElementByName("Ball");

        // Assert: Verificamos que el código de estado sea OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificamos que el cuerpo de la respuesta contenga el elemento esperado
        assertEquals(element, response.getBody());

        // Verificamos que el servicio haya sido llamado una vez con el nombre "Ball"
        verify(elementsService, times(1)).getElementByName("Ball");
    }



    @Test
    void shouldDeleteElement() {
        // Arrange: Crear un objeto de RecreationalElement para simular su existencia
        RecreationalElement elementToDelete = new RecreationalElement("1", "Ball", 10, "Description");

        // Simula la búsqueda del elemento con ID "1" en el repositorio
        when(elementsService.getElementById("1")).thenReturn(elementToDelete);

        // Simula la eliminación del elemento
        when(elementsService.deleteElement("1")).thenReturn(elementToDelete);

        // Act: Llamamos al método de eliminación en el controlador
        ResponseEntity<String> response = elementsController.deleteElement("1");

        // Assert: Verificamos que la respuesta sea la esperada
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Element successfully deleted", response.getBody());

        // Verificamos que getElementById y deleteElement se llamaron correctamente
        verify(elementsService, times(1)).getElementById("1");  // Verifica que getElementById fue llamado
        verify(elementsService, times(1)).deleteElement("1");  // Verifica que deleteElement fue llamado
    }




    @Test
    void shouldUpdateElement() {
        // Arrange: Crear un nuevo elemento con datos actualizados
        RecreationalElement updatedElement = new RecreationalElement("1","Updated Ball", 15, "Updated description");

        // Simula la búsqueda del elemento original
        when(elementsService.getElementById("1")).thenReturn(new RecreationalElement("1", "Ball", 10, "Old description"));

        // Simula la acción de guardar el elemento actualizado
        when(elementsService.updateElement(eq("1"), any(RecreationalElement.class))).thenReturn(updatedElement);

        // Act: Llamamos al método de actualización
        ResponseEntity<String> response = elementsController.updateElement("1", updatedElement);

        // Assert: Verificamos que la respuesta es correcta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Element successfully updated", response.getBody());

        // Verificamos que el método de actualización se haya llamado una vez
        verify(elementsService, times(1)).updateElement(eq("1"), any(RecreationalElement.class));
    }


    @Test
    void testGetAllElements() {
        // Arrange
        when(elementsService.getAll()).thenReturn(List.of(element));

        // Act
        List<RecreationalElement> elements = elementsController.getAll();

        // Assert
        assertNotNull(elements);
        assertEquals(1, elements.size());
        assertEquals(element, elements.get(0));
        verify(elementsService, times(1)).getAll();
    }
}
