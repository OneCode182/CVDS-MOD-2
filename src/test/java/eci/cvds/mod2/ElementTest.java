package eci.cvds.mod2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eci.cvds.mod2.controllers.ElementsController;
import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.services.ElementsService;


@SpringBootTest
public class ElementTest {
    
    @Mock
    private ElementsService elementsService;

    @InjectMocks
    private ElementsController elementsController;

    @Test
	void testGetElementById() {

        String elementId= "23";
        RecreationalElement element = new RecreationalElement(elementId,"testElement",1,"is a test");
        when(elementsService.getElementById(elementId).get()).thenReturn(element);
        ResponseEntity<RecreationalElement>response = elementsController.getElementById(elementId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(element, response.getBody());
	}

    @Test
    void testGetElementByName(){
        String elementName= "jenga";
        RecreationalElement element = new RecreationalElement("23",elementName,1,"is a test");
        when(elementsService.getElementByName(elementName).get()).thenReturn(element);
        ResponseEntity<RecreationalElement>response = elementsController.getElementByName(elementName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(element, response.getBody());


    }

    @Test
    void testCreateElement(){
        RecreationalElement element = new RecreationalElement("23","testElement",1,"is a test");
        ResponseEntity<RecreationalElement>response = elementsController.createElement(element);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(element, response.getBody());        

    }

    @Test
    void testUpdateElement(){
        String idElement = "23";
        RecreationalElement element = new RecreationalElement(idElement,"testElement",1,"is a test");
        when(elementsService.createElement(element)).thenReturn(element);
        element.setName("jenga");
        when(elementsService.updateElement(idElement,element)).thenReturn(element);
        ResponseEntity<RecreationalElement>response = elementsController.createElement(element);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(element, response.getBody());        

    }    
    
}
