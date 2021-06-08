package br.com.rhribeiro25.baseprojectspringwebflux.core.enums;

/**
 * Enum Phone Type
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
public enum PhoneType {
    CEL("Celular"),
    LANDLINE("Fixo"),
    MESSAGE("Recado"),
    CORPORATE("Corporativo");

    private String description;

    PhoneType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescription(String value) {
        for (PhoneType c : PhoneType.values()) {
            if (c.name().equals(value)) {
                return c.getDescription();
            }
        }
        return "";
    }
}
