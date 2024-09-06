package br.com.lett.comifood.model;

import br.com.lett.comifood.enuns.EnterpriseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enterprise")
public class EnterpriseEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @Column(name="id_client_ifood")
   private String idClientIfood;

   @Column(name="client_secret")
   private String clientSecret;

   @Column(name="enterprise")
   private String enterprise;

   @Column(name="token")
   private String token;

   @Column(name="expire_in")
   private Integer expireIn;

   @Column(name="date_time_expired_token")
   private LocalDateTime dateTimeExpiredToken;

   @Column(name="status")
   @Enumerated(EnumType.STRING)
   private EnterpriseStatus status;

}

