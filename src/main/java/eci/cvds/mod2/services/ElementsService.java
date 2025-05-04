package eci.cvds.mod2.services;

import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.reposistories.ElementsRepo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElementsService {
    private ElementsRepo elementsRepo;
    @Autowired
    public ElementsService(ElementsRepo elementsRepo){
        this.elementsRepo = elementsRepo;
    }

    public Optional<RecreationalElement> getElementById(String elementId) {
        return elementsRepo.findById(elementId);
    }

    public Optional<RecreationalElement> getElementByName(String elementName) {
        return elementsRepo.findByName(elementName);
    }

    public RecreationalElement createElement(RecreationalElement element) {
        try {
            RecreationalElement savedElement =  elementsRepo.save(element);
            return savedElement;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteElement(String elementId) {
        try {
            elementsRepo.deleteById(elementId);
        } catch (Exception e) {
            return false;
        }
        return true; 
    }

    public RecreationalElement updateElement(String elementId, RecreationalElement element) {
        if(!elementsRepo.existsById(elementId)){
            return null;
        }
        RecreationalElement updatedElement = elementsRepo.findById(elementId).orElse(null);
        if(updatedElement==null){
            return null;
        }
        updatedElement.setName(element.getName());
        updatedElement.setQuantity(element.getQuantity());
        updatedElement.setDescription(element.getDescription());
        return elementsRepo.save(updatedElement);
    }
}
