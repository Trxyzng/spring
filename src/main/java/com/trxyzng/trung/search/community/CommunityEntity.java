package com.trxyzng.trung.search.community;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.trxyzng.trung.create_post.PostEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "community", schema = "INFO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommunityEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "community_id_generator")
    @SequenceGenerator(name="community_id_generator", sequenceName = "seq_community_id", allocationSize = 1)
    @Column(name = "community_id", nullable = false)
    private int community_id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant created_at;

    @NotNull
    @Column(name = "subscriber_count", nullable = false)
    private int subscriber_count;

    @NotNull
    @Column(name = "icon_base64", nullable = false)
    private String icon_base64;

    @OneToMany(mappedBy="communityEntity")
    @JsonBackReference
    private Set<PostEntity> postEntities;
}