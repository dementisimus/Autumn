/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.plugin.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.datafix.fixes.References;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;

public class AutumnItemStackSerializerUtil {

    public static String itemToString(ItemStack item) {
        CompoundTag tag = CraftItemStack.asNMSCopy(item).save(new CompoundTag());
        tag.put("DataVersion", IntTag.valueOf(CraftMagicNumbers.INSTANCE.getDataVersion()));
        return tag.toString();
    }

    public static ItemStack stringToItem(String data) {
        return CraftItemStack.asCraftMirror(stringToNMSItem(data));
    }

    public static net.minecraft.world.item.ItemStack stringToNMSItem(String data) {
        CompoundTag tag;

        try {
            tag = TagParser.parseTag(data);
        } catch (CommandSyntaxException var11) {
            var11.printStackTrace();
            return null;
        }

        int dataVersion = tag.contains("DataVersion") ? tag.getInt("DataVersion") : -1;
        if (dataVersion < CraftMagicNumbers.INSTANCE.getDataVersion()) {
            DataFixer dataFixer = ((CraftServer) Bukkit.getServer()).getServer().fixerUpper;
            tag = (CompoundTag) dataFixer.update(References.ITEM_STACK, new Dynamic(NbtOps.INSTANCE, tag), dataVersion, CraftMagicNumbers.INSTANCE.getDataVersion()).getValue();
        }

        return net.minecraft.world.item.ItemStack.of(tag);
    }
}
