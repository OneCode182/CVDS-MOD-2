package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.services.ElementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/elements")
@CrossOrigin(origins = "*")

public class ElementsController {
    ElementsService elementsService;
    @Autowired
    public ElementsController(ElementsService elementsService){
        this.elementsService=elementsService;
    }
    public ResponseEntity<RecreationalElement> getElementById(String elementId) {
        return null;
    }

    public ResponseEntity<RecreationalElement> getElementByName(String elementName) {
        return null;
    }

    public ResponseEntity<RecreationalElement> createElement(RecreationalElement element) {
        return null;
    }

    public ResponseEntity<RecreationalElement> deleteElement(String elementID) {
        return null;
    }

    public ResponseEntity<RecreationalElement> updateElement(RecreationalElement element, RecreationalElement newElement) {
        return null;
    }
}
