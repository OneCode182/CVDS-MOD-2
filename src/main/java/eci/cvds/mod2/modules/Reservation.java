package eci.cvds.mod2.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Document(collection = "reservations")
@Builder
@Schema(description = "Representa una reserva de una sala recreativa.")
public class Reservation {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "ID único de la Reserva", example = "642b7f1asdfsdfsdf54d4")
    private String id;

    @Setter
    @Schema(description = "Nombre de la persona que reserva", example = "Santiago")
    private String userName;

    @Setter
    @Schema(description = "Identificacion de la persona que reserva", example = "1000043233")
    private String userId;

    @Setter
    @Schema(description = "Role de la persona", example = "Usuario")
    private Role role;

    @Setter
    @Schema(description = "Fecha de la reserva")
    private Date date;

    @Setter
    @Schema(description = "ID único de la Sala donde es la reserva", example = "64sdfsdfacb01j2000154d4")
    private String roomId;

    @Builder.Default
    @Schema(description = "Prestamos que se han hecho en la reserva")
    private Set<String> loans = new HashSet<>();

    @Setter
    @Schema(description = "Estado de la reserva", example = "RESERVA_CONFIRMADA")
    private State state;

    @Setter
    @Schema(description = "Cantidad de personas que van a la reserva", example = "5")
    private int people;

    public void addLoan(String loanId) {
        if (loans == null) {
            this.loans = new HashSet<>();
        }
        this.loans.add(loanId);
    }
    public void setLoans(Set<String> loans) {
        if (loans == null) {
            this.loans = new HashSet<>();
        } else {
            this.loans = new HashSet<>(loans);
        }
    }

    public Set<String> getLoans() {
        return Collections.unmodifiableSet(loans != null ? loans : new HashSet<>());
    }

}
