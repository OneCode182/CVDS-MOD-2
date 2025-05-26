package eci.cvds.mod2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import eci.cvds.mod2.exceptions.ElementException;
import eci.cvds.mod2.exceptions.ElementNotFoundException;
import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.services.ElementsService;
import eci.cvds.mod2.util.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ElementsControllerTest.class)
class ElementsControllerTest {

    @InjectMocks
    private ElementsController elementsController;

    @Mock
    private ElementsService elementsService;

    private RecreationalElement element;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        element = new RecreationalElement("1", "Ball", 10, "A round ball");
            mockMvc = MockMvcBuilders
                    .standaloneSetup(elementsController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
    }

    @Test
    void shouldReturn200WhenGettingElementWithId() throws  Exception{
        when(elementsService.getElementById("1")).thenReturn(element);
        mockMvc.perform(get("/elements/id/1")).andExpect(status().isOk());
    }
    @Test
    void shouldReturn404WhenGettingNonExistingElementWithId() throws Exception{
        when(elementsService.getElementById("11"))
                .thenThrow( new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND));
        mockMvc.perform(get("/elements/id/11")).andExpect(status().isNotFound());
    }
    @Test
    void shouldReturn200WhenGettingElementWithName() throws  Exception{
        when(elementsService.getElementByName("Ball")).thenReturn(element);
        mockMvc.perform(get("/elements/name/Ball")).andExpect(status().isOk());
    }
    @Test
    void shouldReturn404WhenGettingNonExistingElementWithName() throws Exception{
        when(elementsService.getElementByName("Ball"))
                .thenThrow( new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND));
        mockMvc.perform(get("/elements/name/Ball")).andExpect(status().isNotFound());
    }
    @Test
    void shouldReturn201WhenCorrectCreationOfElement() throws Exception {
        RecreationalElement element1 = new RecreationalElement("2", "Jenga", 1, "fun game");
        when(elementsService.createElement(any(RecreationalElement.class))).thenReturn(element1);
        mockMvc.perform(post("/elements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(element1)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Element successfully created"));
        verify(elementsService).createElement(any(RecreationalElement.class));
    }
    @Test
    void shouldReturn201WhenCreatingAExistingElement() throws Exception{
        RecreationalElement element1 = new RecreationalElement("1", "Ball", 10, "A round ball");
        when(elementsService.createElement(any(RecreationalElement.class))).thenReturn(element1);
        mockMvc.perform(post("/elements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(element1)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Element successfully created"));
        verify(elementsService).createElement(any(RecreationalElement.class));
    }
    @Test
    void shouldReturn200WhenDeletingElement() throws Exception {
        when(elementsService.deleteElement("1")).thenReturn(element);
        mockMvc.perform(delete("/elements/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Element successfully deleted"));
        verify(elementsService).deleteElement("1");
    }
    @Test
    void shouldReturn400WhenDeletingNonExistentElement() throws Exception {
        when(elementsService.deleteElement("1"))
                .thenThrow(new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND));
        mockMvc.perform(delete("/elements/1"))
                .andExpect(status().isNotFound());
        verify(elementsService).deleteElement("1");
    }
    @Test
    void shouldReturn200WhenUpdatingLab() throws Exception {
        when (elementsService.updateElement(eq("1"),any(RecreationalElement.class))).thenReturn(element);
        mockMvc.perform(put("/elements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(element)))
                .andExpect(status().isOk())
                .andExpect(content().string("Element successfully updated"));
        verify(elementsService).updateElement(eq("1"),any(RecreationalElement.class));
    }
    @Test
    void shouldReturn404WhenUpdatingNotExistentLab() throws Exception {
        when(elementsService.updateElement(eq("1"),any(RecreationalElement.class)))
                .thenThrow(new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND));
        mockMvc.perform(put("/elements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(element)))
                .andExpect(status().isNotFound());
        verify(elementsService).updateElement(eq("1"),any(RecreationalElement.class));
    }
    @Test
    void shouldReturn200WhenGettingAllElements() throws Exception{
        List<RecreationalElement> elements = Collections.singletonList(element);
        when(elementsService.getAll()).thenReturn(elements);
        mockMvc.perform(get("/elements")).andExpect(status().isOk());
    }
    @Test
    void shouldReturn200WhenReducingElementQuantity() throws Exception {
        doNothing().when(elementsService).reduceElementQuantity("1", 1);

        mockMvc.perform(put("/elements/reduce/1"))
                .andExpect(status().isOk());
    }
    @Test
    void shouldReturn404WhenReducingTheQuantityOfANonExistingElement() throws Exception{
        doThrow(new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND))
                .when(elementsService).reduceElementQuantity("1",1);
        mockMvc.perform(put("/elements/reduce/1"))
                .andExpect(status().isNotFound());
    }
    @Test
    void shouldReturn200WhenIncreasingElementQuantity() throws Exception {
        doNothing().when(elementsService).increaseElementQuantity("1", 1);

        mockMvc.perform(put("/elements/increase/1"))
                .andExpect(status().isOk());
    }
    @Test
    void shouldReturn404WhenIncreasingTheQuantityOfANonExistingElement() throws Exception{
        doThrow(new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND))
                .when(elementsService).increaseElementQuantity("1",1);
        mockMvc.perform(put("/elements/increase/1"))
                .andExpect(status().isNotFound());
    }

}
