package br.org.financeiro.model;

import br.org.financeiro.persistence.model.MovementEntity;
import br.org.financeiro.persistence.model.PersonEntity;
import br.org.financeiro.persistence.model.enums.MovementTypeEnum;
import br.org.financeiro.util.DateUtil;
import javafx.beans.property.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Movement {

    private final LongProperty id = new SimpleLongProperty(0L);
    private final StringProperty description = new SimpleStringProperty("");
    private final SimpleObjectProperty<PersonEntity> person = new SimpleObjectProperty<PersonEntity>(null);
    private final ObjectProperty<MovementTypeEnum> type = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> value = new SimpleObjectProperty<>();
    private final BooleanProperty paid = new SimpleBooleanProperty(false);
    private final ObjectProperty<LocalDate> paymentDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(LocalDate.now());
    private final ObjectProperty<LocalDate> dueDate = new SimpleObjectProperty<>();

    public Movement() {

    }

    public Long getId(){
        return id.get();
    }

    public String getDescription(){
        return description.get();
    }

    public PersonEntity getPerson(){
        return person.get();
    }

    public MovementTypeEnum getType(){
        return type.get();
    }

    public BigDecimal getValue(){
        return value.get();
    }

    public  Boolean getPaid(){
        return paid.get();
    }

    public LocalDate getPaymentDate(){
        return paymentDate.get();
    }

    public LocalDate getDate(){
        return date.get();
    }

    public LocalDate getDueDate(){
        return dueDate.get();
    }

    public void setId(Long val){
        id.set(val);
    }

    public void setDescription(String val){
        description.set(val);
    }

    public void setPerson(PersonEntity val){
        person.set(val);
    }

    public void setType(MovementTypeEnum val){
        type.set(val);
    }

    public void setValue(BigDecimal val){
        value.set(val);
    }

    public  void setPaid(Boolean val){
        paid.set(val);
    }

    public void setPaymentDate(LocalDate val){
        paymentDate.set(val);
    }

    public void setDate(LocalDate val){
        date.set(val);
    }

    public void setDueDate(LocalDate val){
        dueDate.set(val);
    }

    public static MovementEntity from(Movement dto) {
        MovementEntity entity = new MovementEntity();
        entity.setId(dto.getId() != 0L ? dto.getId() : null);
        entity.setDescription(dto.getDescription());
        entity.setPerson(dto.getPerson());
        entity.setType(dto.getType());
        entity.setValue(dto.getValue());
        entity.setPaid(dto.getPaid());
        entity.setPaymentDate(DateUtil.toDate(dto.getPaymentDate()));
        entity.setDate(DateUtil.toDate(dto.getDate()));
        entity.setDueDate(DateUtil.toDate(dto.getDueDate()));
        return entity;
    }

    public static Movement to(MovementEntity entity) {
        Movement dto = new Movement();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setPerson(entity.getPerson());
        dto.setType(entity.getType());
        dto.setValue(entity.getValue());
        dto.setPaid(entity.getPaid());
        dto.setPaymentDate(DateUtil.toLocalDate(entity.getPaymentDate()));
        dto.setDate(DateUtil.toLocalDate(entity.getDate()));
        dto.setDueDate(DateUtil.toLocalDate(entity.getDueDate()));
        return dto;
    }
}
