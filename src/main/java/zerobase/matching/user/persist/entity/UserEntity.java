package zerobase.matching.user.persist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zerobase.matching.user.persist.consist.Gender;
import zerobase.matching.user.persist.consist.MembershipLevel;
import zerobase.matching.user.persist.consist.OnOffline;
import zerobase.matching.user.persist.consist.Role;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USER")
@Builder
public class UserEntity implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID")
  private Long userId;

  @Column(name = "USER_LOGIN_ID")
  private String userLoginId;

  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "NICKNAME")
  private String nickname;

  @Column(name = "EMAIL")
  @Email
  private String email;

  @Column(name = "GIT")
  private String git;

  @Column(name = "PHONE_NUMBER")
  private String phoneNumber;

  @Column(name = "BIRTH_DATE")
  private LocalDate birthDate;

  @Column(name = "GENDER")
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(name = "RESIDENCE")
  private String residence;

  @Column(name = "USER_ON_OFFLINE")
  @Enumerated(EnumType.STRING)
  private OnOffline onOffline;

  @Column(name = "PORTFOLIO")
  private String portfolio;

  @Column(name = "REGISTER_TIME")
  private Timestamp registerTime;

  @Column(name = "UPDATE_TIME")
  private Timestamp updateTime;

  @Column(name = "ROLE")
  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(name = "MEMBERSHIP_LEVEL")
  @Enumerated(EnumType.STRING)
  private MembershipLevel membershipLevel;

  @Column(name = "TEAM_LEVEL")
  private Double teamLevel;

  @Column(name = "BANK")
  private String bank;

  @Column(name = "ACCOUNTNUMBER")
  private String accountNumber;

  /** 유저의 권한 추출 (오버라이딩) */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
    auth.add(new SimpleGrantedAuthority(role.toString()));
    return auth;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.userLoginId;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
