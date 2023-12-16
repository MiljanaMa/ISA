package medequipsystem.domain;
import javax.persistence.*;

@Entity
@Table(name = "system_admins")
public class SystemAdmin {
    @Id
    @SequenceGenerator(name = "seqGenSystemAdmin", sequenceName = "seqSystemAdmin", initialValue = 5, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGenSystemAdmin")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "is_main", nullable = false)
    private boolean isMain;

    @Column(name = "is_initial_password_changed", nullable = false)
    private boolean isInitialPasswordChanged;

    public SystemAdmin() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public boolean isInitialPasswordChanged() {
        return isInitialPasswordChanged;
    }

    public void setInitialPasswordChanged(boolean initialPasswordChanged) {
        isInitialPasswordChanged = initialPasswordChanged;
    }
}
