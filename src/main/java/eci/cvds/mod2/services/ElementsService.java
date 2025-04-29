package eci.cvds.mod2.services;

import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.reposistories.ElementsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElementsService {
    private ElementsRepo elementsRepo;
    @Autowired
    public ElementsService(ElementsRepo elementsRepo){
        this.elementsRepo = elementsRepo;
    }

    public RecreationalElement getElementById(String elementId) {
        return null;
    }

    public RecreationalElement getElementByName(String elementName) {
        return null;
    }

    public RecreationalElement createElement(RecreationalElement element) {
        return null;
    }

    public RecreationalElement deleteElement(String elementID) {
        return null;
    }

    public RecreationalElement updateElement(RecreationalElement element, RecreationalElement newElement) {
        return null;
    }
}
