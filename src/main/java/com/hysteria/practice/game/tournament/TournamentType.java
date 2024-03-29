package com.hysteria.practice.game.tournament;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Hysteria Development
 * @project Practice
 * @date 2/12/2023
 */

@AllArgsConstructor
@Getter
public enum TournamentType {

    SOLO("Solo"),
    PARTY("Party"),
    CLANS("Clans");

    private final String name;

}