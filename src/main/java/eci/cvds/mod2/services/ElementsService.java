package eci.cvds.mod2.services;

import eci.cvds.mod2.exceptions.ElementException;
import eci.cvds.mod2.exceptions.ElementNotFoundException;
import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.reposistories.ElementsRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Getter
@Service
public class ElementsService {
    private final ElementsRepo elementsRepo;
    @Autowired
    public ElementsService(ElementsRepo elementsRepo){
        this.elementsRepo = elementsRepo;
    }

    public RecreationalElement getElementById(String elementId) {
        return elementsRepo.findById(elementId).
                orElseThrow(()-> new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND));

    }

    public RecreationalElement getElementByName(String elementName) {
        return elementsRepo.findByName(elementName)
                .orElseThrow(()-> new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND));
    }

    public RecreationalElement createElement(RecreationalElement element) {
        return elementsRepo.findByName(element.getName())
                .map(existingElement -> {
                    existingElement.setQuantity(existingElement.getQuantity() + element.getQuantity());
                    return elementsRepo.save(existingElement);
                })
                .orElseGet(() -> elementsRepo.save(element));
    }

    public RecreationalElement deleteElement(String elementId) {
        RecreationalElement element = elementsRepo.findById(elementId)
                .orElseThrow(() -> new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND));
        elementsRepo.deleteById(elementId);
        return element;
    }

    public RecreationalElement updateElement(String elementId, RecreationalElement newElement) {
        RecreationalElement element = elementsRepo.findById(elementId)
                        .orElseThrow(()-> new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND));
        element.setName(newElement.getName());
        element.setQuantity(newElement.getQuantity());
        element.setDescription(newElement.getDescription());
        return elementsRepo.save(element);
    }
}
