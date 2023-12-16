package medequipsystem.util;

import medequipsystem.domain.Client;
import medequipsystem.domain.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "emailTokens")
public class EmailToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId", nullable = false)
    private Client client;

    public EmailToken(){}


    public EmailToken(Client client) {
        this.client = client;
        this.creationDate = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}

