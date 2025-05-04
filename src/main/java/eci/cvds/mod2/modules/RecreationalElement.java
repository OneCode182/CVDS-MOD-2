package eci.cvds.mod2.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Elements")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecreationalElement {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String id;

    private String name;
    private int quantity;

    private String description;

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
