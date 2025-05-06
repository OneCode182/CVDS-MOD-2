package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.services.ElementsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elements")
public class ElementsController {
    ElementsService elementsService;
    @Autowired
    public ElementsController(ElementsService elementsService){
        this.elementsService=elementsService;
    }
    @GetMapping("/id/{elementId}")
    public ResponseEntity<RecreationalElement> getElementById(@PathVariable  String elementId) {
        RecreationalElement element = elementsService.getElementById(elementId);
        return ResponseEntity.ok(element);
    }
    @GetMapping("/name/{elementName}")
    public ResponseEntity<RecreationalElement> getElementByName(@PathVariable String elementName) {
        RecreationalElement element = elementsService.getElementByName(elementName);
        return ResponseEntity.ok(element);
    }
    @PostMapping
    public ResponseEntity<String> createElement(@Valid @RequestBody RecreationalElement element) {
        elementsService.createElement(element);
        return ResponseEntity.status(HttpStatus.CREATED).body("Element successfully created");
    }
    @DeleteMapping("/{elementId}")
    public ResponseEntity<String> deleteElement(@PathVariable String elementId) {
        elementsService.deleteElement(elementId);
        return ResponseEntity.ok("Element successfully deleted ");
    }
    @PutMapping("/{elementId}")
    public ResponseEntity<String> updateElement(@PathVariable String elementId,@Valid @RequestBody RecreationalElement newElement){
        elementsService.updateElement(elementId, newElement);
        return ResponseEntity.ok("Element successfully updated");
    }
}
