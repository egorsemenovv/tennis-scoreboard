package org.egorsemenovv.tennisscoreboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "matches", schema = "public")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE},optional = false)
    @JoinColumn(name = "player1")
    private Player player1;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE},optional = false)
    @JoinColumn(name = "player2")
    private Player player2;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE},optional = true)
    @JoinColumn(name = "winner")
    private Player winner;

    @Transient
    private MatchScore matchScore;
}


