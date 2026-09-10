package com.matchalab.travel_todo_api.model.Reservation;

import com.matchalab.travel_todo_api.enums.ReservationCategory;
import com.matchalab.travel_todo_api.mapper.ReservationDetailMapper;
import com.matchalab.travel_todo_api.model.Todo.Todo;
import com.matchalab.travel_todo_api.model.Trip;
import io.micrometer.common.lang.NonNull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.UUID;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.domain.Persistable;

@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class Reservation implements Persistable<UUID> {

  @Enumerated(EnumType.STRING)
  ReservationCategory category;

  @Id @NonNull @Builder.Default private UUID id = UUID.randomUUID();
  @Builder.Default private Boolean isCompleted = false;
  @Basic(fetch = FetchType.LAZY)
  private String rawText;

  @Nullable
  @Column(length = 2048)
  @Size(max = 2048, message = "primaryHrefLink cannot exceed 2048 characters.")
  private String primaryHrefLink;

  @Nullable private String code;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "reservation_detail", columnDefinition = "jsonb")
  private String detailJson;

  @Transient
  private ReservationDetail detail;

  @PostLoad
  private void deserializeDetail() {
    if (this.detailJson != null && this.category != null) {
      this.detail = ReservationDetailMapper.fromJson(this.detailJson, this.category);
    }
  }

  @PrePersist
  @PreUpdate
  private void serializeDetail() {
    if (this.detail != null) {
      this.detailJson = ReservationDetailMapper.toJson(this.detail);
    }
  }

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Nullable
  private Todo todo;

  // @Nullable
  // private String serverFileUri;

  // @Nullable
  // private String localAppStorageFileUri;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trip_id")
  private Trip trip;

  @Transient @Builder.Default private boolean isNew = true;

  public Reservation(Reservation reservation) {
    this.id = UUID.randomUUID();
    this.isCompleted = reservation.getIsCompleted();
    this.category = reservation.getCategory();
    // this.rawText = reservation.getRawText();
    this.primaryHrefLink = reservation.getPrimaryHrefLink();
    this.detail = reservation.detail;
    // this.serverFileUri = reservation.getServerFileUri();
    // this.localAppStorageFileUri = reservation.getLocalAppStorageFileUri();
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public boolean isNew() {
    return this.isNew;
  }
}
