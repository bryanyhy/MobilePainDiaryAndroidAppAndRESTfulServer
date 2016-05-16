/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bryanyhy
 */
@Entity
@Table(name = "REGISTRATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registration.findAll", query = "SELECT r FROM Registration r"),
    @NamedQuery(name = "Registration.findByUsername", query = "SELECT r FROM Registration r WHERE r.username = :username"),
    @NamedQuery(name = "Registration.findByPassword", query = "SELECT r FROM Registration r WHERE r.password = :password"),
    @NamedQuery(name = "Registration.findByRegDatetime", query = "SELECT r FROM Registration r WHERE r.regDatetime = :regDatetime"),
    @NamedQuery(name = "Registration.findByRegId", query = "SELECT r FROM Registration r WHERE r.regId = :regId")})
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REG_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDatetime;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "REG_ID")
    private Integer regId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private Patient userId;

    public Registration() {
    }

    public Registration(Integer regId) {
        this.regId = regId;
    }

    public Registration(Integer regId, String username, String password, Date regDatetime) {
        this.regId = regId;
        this.username = username;
        this.password = password;
        this.regDatetime = regDatetime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegDatetime() {
        return regDatetime;
    }

    public void setRegDatetime(Date regDatetime) {
        this.regDatetime = regDatetime;
    }

    public Integer getRegId() {
        return regId;
    }

    public void setRegId(Integer regId) {
        this.regId = regId;
    }

    public Patient getUserId() {
        return userId;
    }

    public void setUserId(Patient userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regId != null ? regId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registration)) {
            return false;
        }
        Registration other = (Registration) object;
        if ((this.regId == null && other.regId != null) || (this.regId != null && !this.regId.equals(other.regId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "serv.Registration[ regId=" + regId + " ]";
    }
    
}
