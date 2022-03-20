/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.helper;

import org.bukkit.Location;

public class LocationHelper {

    private static double getCorrectedXZ(String xz) {
        String[] splittedX = xz.split("\\.");
        return Double.parseDouble(splittedX[0] + "." + "500");
    }

    private static float getCorrectedYaw(float yaw) {
        if(yaw < 0) yaw += 360;

        if(yaw >= 315 || yaw < 45) {
            return 0.00f;
        }else if(yaw < 135) {
            return 90.00f;
        }else if(yaw < 225) {
            return 180.00f;
        }else if(yaw < 315) {
            return -90.00f;
        }

        return -180.00f;
    }

    public static Location round(Location loc) {
        String oldX = String.valueOf(loc.getX());
        String oldZ = String.valueOf(loc.getZ());

        float oldYaw = loc.getYaw();

        return new Location(loc.getWorld(), getCorrectedXZ(oldX), loc.getY(), getCorrectedXZ(oldZ), getCorrectedYaw(oldYaw), 0f);
    }

}
