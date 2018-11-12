package io.github.jhipster.masterloanauthentication.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "userloan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userloan")
public class Userloan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cedula_customer", nullable = false)
    private Long cedulaCustomer;

    @NotNull
    @Column(name = "type_id_customer", nullable = false)
    private String typeIdCustomer;

    @NotNull
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotNull
    @Column(name = "jhi_password", nullable = false)
    private String password;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCedulaCustomer() {
        return cedulaCustomer;
    }

    public Userloan cedulaCustomer(Long cedulaCustomer) {
        this.cedulaCustomer = cedulaCustomer;
        return this;
    }

    public void setCedulaCustomer(Long cedulaCustomer) {
        this.cedulaCustomer = cedulaCustomer;
    }

    public String getTypeIdCustomer() {
        return typeIdCustomer;
    }

    public Userloan typeIdCustomer(String typeIdCustomer) {
        this.typeIdCustomer = typeIdCustomer;
        return this;
    }

    public void setTypeIdCustomer(String typeIdCustomer) {
        this.typeIdCustomer = typeIdCustomer;
    }

    public String getUserName() {
        return userName;
    }

    public Userloan userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public Userloan password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Userloan userloan = (Userloan) o;
        if (userloan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userloan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Userloan{" +
            "id=" + getId() +
            ", cedulaCustomer=" + getCedulaCustomer() +
            ", typeIdCustomer='" + getTypeIdCustomer() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
