package eci.cvds.mod2.services;

import eci.cvds.mod2.modules.RecreationalElements;
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

    public RecreationalElements getElementById(String elementId) {
        return null;
    }

    public RecreationalElements getElementByName(String elementName) {
        return null;
    }

    public RecreationalElements createElement(RecreationalElements element) {
        return null;
    }

    public RecreationalElements deleteElement(String elementID) {
        return null;
    }

    public RecreationalElements updateElement(RecreationalElements element, RecreationalElements newElement) {
        return null;
    }
}
