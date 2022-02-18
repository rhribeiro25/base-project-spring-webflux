package br.com.rhribeiro25.baseprojectspringwebflux.core.entity.postgresql;

import java.time.LocalDateTime;

/**
 * interface Generic Entity
 *
 * @author Renan Henrique Ribeiro
 * @since 06/27/2021
 */
public interface GenericEntity {
    Long getId();
    void setId(Long id);
    Boolean getIsActivated();
    void setIsActivated(Boolean isActivated);
    LocalDateTime getCreatedAt();
    void setCreatedAt(LocalDateTime createdAt);
    LocalDateTime getUpdatedAt();
    void setUpdatedAt(LocalDateTime updatedAt);
}
