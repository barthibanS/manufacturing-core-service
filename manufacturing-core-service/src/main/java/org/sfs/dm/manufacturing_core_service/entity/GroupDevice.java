package org.sfs.dm.manufacturing_core_service.entity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "GroupDevice")
public class GroupDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

}