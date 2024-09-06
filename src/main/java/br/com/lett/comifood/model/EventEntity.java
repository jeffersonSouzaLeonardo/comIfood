package br.com.lett.comifood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "event")
public class EventEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="id")
   private Long id;

   @Column(name="id_ifood")
   private String idIfood;

   @Column(name="code")
   private String code;

   @Column(name="full_code")
   private String fullCode;

   @Column(name="order_id")
   private String orderId;

   @Column(name="merchant_id")
   private String merchantId;

   @Column(name="created_at")
   private LocalDateTime createdAt;

   @Column(name="status")
   private String status;

}

