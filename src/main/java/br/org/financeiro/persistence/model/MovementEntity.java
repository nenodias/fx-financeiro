package br.org.financeiro.persistence.model;

import br.org.financeiro.persistence.model.enums.MovementTypeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "movement")
public class MovementEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name="person_id")
    private PersonEntity person;

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private MovementTypeEnum type;

    @Column(name="value")
    private BigDecimal value;

    @Column(name="is_paid")
    private Boolean paid;

    @Column(name="payment_date")
    private Date paymentDate;

    @Column(name="date")
    private Date date;

    @Column(name="due_date")
    private Date dueDate;
}
