package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.services.ElementsService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elements")
@CrossOrigin(origins = "*")

public class ElementsController {
    ElementsService elementsService;

    @Autowired
    public ElementsController(ElementsService elementsService) {
        this.elementsService = elementsService;
    }

    @GetMapping("/id/{elementId}")
    public ResponseEntity<RecreationalElement> getElementById(@PathVariable String elementId) {
        Optional<RecreationalElement> element = elementsService.getElementById(elementId);
        if (element.isPresent()) {
            return ResponseEntity.ok(element.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping("/name/{elementName}")
    public ResponseEntity<RecreationalElement> getElementByName(@PathVariable String elementName) {
        Optional<RecreationalElement> element = elementsService.getElementByName(elementName);
        if (element.isPresent()) {
            return ResponseEntity.ok(element.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<RecreationalElement> createElement(@RequestBody RecreationalElement element) {
        RecreationalElement newElement = elementsService.createElement(element);
        if (newElement != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newElement);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @DeleteMapping("/{elementId}")
    public ResponseEntity<?> deleteElement(@PathVariable String elementId) {
        boolean deleted = elementsService.deleteElement(elementId);
        if(deleted){
            return ResponseEntity.ok().body("Element successfully deleted");
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{elementId}")
    public ResponseEntity<RecreationalElement> updateElement(@PathVariable String elementId,
            @RequestBody RecreationalElement newElement) {
        RecreationalElement updatedElement = elementsService.updateElement(elementId, newElement);
        if (updatedElement != null) {
            return ResponseEntity.ok(updatedElement);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
