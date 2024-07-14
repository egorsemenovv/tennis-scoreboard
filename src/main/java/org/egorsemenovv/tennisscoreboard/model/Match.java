package org.egorsemenovv.tennisscoreboard.model;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, optional = false)
    @JoinColumn(name = "player1")
    private Player player1;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, optional = false)
    @JoinColumn(name = "player2")
    private Player player2;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, optional = false)
    @JoinColumn(name = "winner")
    private Player winner;

    @Builder.Default
    @Transient
    private MatchScore matchScore = new MatchScore();
}


