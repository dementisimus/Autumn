/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.plugin.helper;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.dementisimus.autumn.common.api.callback.AutumnSingleCallback;
import dev.dementisimus.autumn.common.api.executor.AutumnTaskExecutor;
import dev.dementisimus.autumn.common.plugin.cache.AutumnCache;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

public class BukkitHelper {

    public static void playerHeadByUrl(String headID, AutumnSingleCallback<ItemStack> itemStackCallback) {
        ItemStack cachedItemStack = AutumnCache.get(headID, ItemStack.class);

        if (cachedItemStack != null) {
            itemStackCallback.done(cachedItemStack);
            return;
        }

        AutumnTaskExecutor.staticAsynchronous(() -> {
            try {
                String url = "https://textures.minecraft.net/texture/" + headID;

                ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);

                byte[] encodeBase64 = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
                gameProfile.getProperties().put("textures", new Property("textures", new String(encodeBase64)));

                Field field = skullMeta.getClass().getDeclaredField("profile");
                field.setAccessible(true);
                field.set(skullMeta, gameProfile);

                itemStack.setItemMeta(skullMeta);

                AutumnCache.set(headID, itemStack);
                itemStackCallback.done(itemStack);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
        });
    }

    public static void playSound(Player player, Sound sound) {
        playSound(player, sound, 10, 1);
    }

    public static void playSound(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }
}
