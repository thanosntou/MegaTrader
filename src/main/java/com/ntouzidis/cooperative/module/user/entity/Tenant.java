package com.ntouzidis.cooperative.module.user.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tenant")
public class Tenant {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tenantSeqGen")
  @SequenceGenerator(name = "tenantSeqGen", sequenceName = "tenant_id_seq")
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "create_date")
  private LocalDate create_date;

  public Tenant() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getCreate_date() {
    return create_date;
  }

  public void setCreate_date(LocalDate create_date) {
    this.create_date = create_date;
  }

  @Override
  public String toString() {
    return "Tenant{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", create_date=" + create_date +
        '}';
  }
}
