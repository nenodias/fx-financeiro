package br.org.financeiro.persistence.model.enums;

public enum MovementTypeEnum {
    IN("IN", "Entrada"),
    OUT("OUT", "Saída");

    private final String type;
    private final String description;


    MovementTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public static MovementTypeEnum findByType(String type){
        for(MovementTypeEnum enumModel : MovementTypeEnum.values()){
            if(enumModel.type.equals(type)){
                return enumModel;
            }
        }
        return null;
    }
}
