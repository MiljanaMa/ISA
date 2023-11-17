package medequipsystem.token;

import medequipsystem.domain.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name = "emailToken")
public class EmailToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tokenId")
    private long tokenId;

    @Column(name = "token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "id")
    private User user;


}
