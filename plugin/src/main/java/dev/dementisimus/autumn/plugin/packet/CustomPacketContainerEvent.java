/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import dev.dementisimus.autumn.api.callback.EmptyCallback;
import dev.dementisimus.autumn.api.packet.PacketContainerEvent;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class CustomPacketContainerEvent implements PacketContainerEvent {

    private final List<EmptyCallback> consumersBefore = new LinkedList<>();
    private final List<PacketContainer> packetsBefore = new LinkedList<>();
    private final List<EmptyCallback> consumersAfter = new LinkedList<>();
    private final List<PacketContainer> packetsAfter = new LinkedList<>();

    private final Player player;
    private final int entityId;
    @Getter
    private final PacketContainer packetContainer;

    private boolean cancelled;

    public CustomPacketContainerEvent(Player player, int entityId, PacketContainer packetContainer) {
        this.player = player;
        this.entityId = entityId;
        this.packetContainer = packetContainer;
    }

    public CustomPacketContainerEvent(Player player, PacketType packetType) {
        this.player = player;
        this.entityId = -1;
        this.packetContainer = this.newEmptyContainer(packetType);
    }

    public CustomPacketContainerEvent(Player player, int entityId, PacketType packetType) {
        this.player = player;
        this.entityId = entityId;
        this.packetContainer = this.newBasicContainer(packetType);
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public void runBefore(EmptyCallback emptyCallback) {
        this.consumersBefore.add(emptyCallback);
    }

    @Override
    public void sendBefore(PacketContainer packetContainer) {
        this.packetsBefore.add(packetContainer);
    }

    @Override
    public void sendBefore(PacketType packetType, Consumer<PacketContainer> packetContainerConsumer) {
        this.packetsBefore.add(this.executeContainerConsumer(packetType, packetContainerConsumer));
    }

    @Override
    public void runAfter(EmptyCallback emptyCallback) {
        this.consumersAfter.add(emptyCallback);
    }

    @Override
    public void sendAfter(PacketContainer packetContainer) {
        this.packetsAfter.add(packetContainer);
    }

    @Override
    public void sendAfter(PacketType packetType, Consumer<PacketContainer> packetContainerConsumer) {
        this.packetsAfter.add(this.executeContainerConsumer(packetType, packetContainerConsumer));
    }

    @Override
    public void process() {
        if (this.cancelled) return;

        for (EmptyCallback emptyCallback : this.consumersBefore) {
            emptyCallback.done();
        }

        for (PacketContainer container : this.packetsBefore) {
            this.sendPacket(container);
        }

        this.sendPacket(this.packetContainer);

        for (PacketContainer container : this.packetsAfter) {
            this.sendPacket(container);
        }

        for (EmptyCallback emptyCallback : this.consumersAfter) {
            emptyCallback.done();
        }
    }

    @SneakyThrows
    private void sendPacket(PacketContainer packetContainer) {
        ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, packetContainer);
    }

    private PacketContainer executeContainerConsumer(PacketType packetType, Consumer<PacketContainer> packetContainerConsumer) {
        PacketContainer container = new PacketContainer(packetType);

        container.getIntegers().write(0, this.entityId);

        packetContainerConsumer.accept(container);

        return container;
    }

    private PacketContainer newEmptyContainer(PacketType packetType) {
        return new PacketContainer(packetType);
    }

    private PacketContainer newBasicContainer(PacketType packetType) {
        PacketContainer packetContainer = this.newEmptyContainer(packetType);

        packetContainer.getIntegers().write(0, this.entityId);

        return packetContainer;
    }
}
