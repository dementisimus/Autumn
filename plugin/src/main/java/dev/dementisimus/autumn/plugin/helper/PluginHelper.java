/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.helper;

import com.destroystokyo.paper.profile.CraftPlayerProfile;
import com.destroystokyo.paper.profile.PlayerProfile;
import dev.dementisimus.autumn.api.callback.SingleCallback;
import dev.dementisimus.autumn.api.executor.AutumnTaskExecutor;
import dev.dementisimus.autumn.plugin.cache.AutumnCache;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

public class PluginHelper {

    public static void playerHeadByUrl(String headID, SingleCallback<@NotNull ItemStack> itemStackCallback) {
        ItemStack cachedItemStack = AutumnCache.get(headID, ItemStack.class);

        if (cachedItemStack != null) {
            itemStackCallback.done(cachedItemStack);
            return;
        }

        AutumnTaskExecutor.staticAsynchronous(() -> {
            try {
                ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                PlayerProfile profile = new CraftPlayerProfile(UUID.randomUUID(), RandomStringUtils.randomAlphabetic(4));
                PlayerTextures textures = profile.getTextures();

                textures.setSkin(new URI("https://textures.minecraft.net/texture/" + headID).toURL());

                profile.setTextures(textures);
                skullMeta.setPlayerProfile(profile);
                itemStack.setItemMeta(skullMeta);

                AutumnCache.set(headID, itemStack);
                itemStackCallback.done(itemStack);
            } catch (URISyntaxException | MalformedURLException e) {
                throw new RuntimeException(e);
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
