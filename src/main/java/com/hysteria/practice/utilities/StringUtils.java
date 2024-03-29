package com.hysteria.practice.utilities;

import org.bukkit.ChatColor;

import java.util.Collections;

public class StringUtils {

    public static String getStringPoint(int points, ChatColor color, int pointsToWin){
        String x = "■";
        switch (points){
            case 1: return color + "■" + "&7" + String.join("", Collections.nCopies(pointsToWin - points, x));
            case 2: return color + "■■" + "&7" + String.join("", Collections.nCopies(pointsToWin - points, x));
            case 3: return color + "■■■" + "&7" + String.join("", Collections.nCopies(pointsToWin - points, x));
            case 4: return color + "■■■■" + "&7"+ String.join("", Collections.nCopies(pointsToWin - points, x));
            case 5: return color + "■■■■■";
            default: return String.join("", Collections.nCopies(pointsToWin - points, x));
        }
    }

    public static String getStringPointLose(int points, ChatColor color, int pointsToLose){
        String x = "■";
        switch (points){
            case 1: return color + "■" + "&7" + String.join("", Collections.nCopies(pointsToLose - points, x));
            case 2: return color + "■■" + "&7" + String.join("", Collections.nCopies(pointsToLose - points, x));
            case 3: return color + "■■■" + "&7" + String.join("", Collections.nCopies(pointsToLose - points, x));
            default: return String.join("", Collections.nCopies(pointsToLose - points, x));
        }
    }
}