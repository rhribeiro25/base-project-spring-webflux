package br.com.rhribeiro25.baseprojectspringwebflux.core.enums;

/**
 * Enum Role Type
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
public enum RoleType {
    SUPER("Super Usuário"),
    MANAGER("Gerente"),
    EMPLOYEE("Funcionário"),
    INTERN("Estagiário");

    private String description;

    RoleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescription(String value) {
        for (RoleType c : RoleType.values()) {
            if (c.name().equals(value)) {
                return c.getDescription();
            }
        }
        return "";
    }
}
