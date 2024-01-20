package dev.dementisimus.autumn.bukkit.api.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import dev.dementisimus.autumn.common.api.callback.AutumnEmptyCallback;

import java.util.function.Consumer;

/**
 * Used for controlling events before and after sending a given packet
 *
 * @since 2.0.0
 */
public interface PacketContainerEvent {

    /**
     * Gets the packet container
     *
     * @return the packet container
     */
    PacketContainer getPacketContainer();

    /**
     * Checks if the packet is being sent
     *
     * @return true if the packet will not be sent
     */
    boolean isCancelled();

    /**
     * Sets whether the packet will be sent
     *
     * @param cancelled true if the packet should not be sent
     */
    void setCancelled(boolean cancelled);

    /**
     * Executes before the packet is being sent
     *
     * @param emptyCallback {@link AutumnEmptyCallback}
     */
    void runBefore(AutumnEmptyCallback emptyCallback);

    /**
     * Sends a packet before the packet is being sent
     *
     * @param packetContainer {@link PacketContainer}
     */
    void sendBefore(PacketContainer packetContainer);

    /**
     * Sends a packet (initialized with the entityId) before the packet is being sent
     *
     * @param packetType              {@link PacketType}
     * @param packetContainerConsumer a {@link PacketContainer} initialized with the entityId
     */
    void sendBefore(PacketType packetType, Consumer<PacketContainer> packetContainerConsumer);

    /**
     * Executes after the packet has been sent
     *
     * @param emptyCallback {@link AutumnEmptyCallback}
     */
    void runAfter(AutumnEmptyCallback emptyCallback);

    /**
     * Sends a packet after the packet has been sent
     *
     * @param packetContainer {@link PacketContainer}
     */
    void sendAfter(PacketContainer packetContainer);

    /**
     * Sends a packet (initialized with the entityId) after the packet has been sent
     *
     * @param packetType              {@link PacketType}
     * @param packetContainerConsumer a {@link PacketContainer} initialized with the entityId
     */
    void sendAfter(PacketType packetType, Consumer<PacketContainer> packetContainerConsumer);

    /**
     * <ul>
     *     <li>All runnables/packets in the event will be processed in the following sequence</li>
     *     <ul>
     *         <li>{@link #runBefore(AutumnEmptyCallback)}</li>
     *         <li>{@link #sendBefore(PacketContainer)} | {@link #sendBefore(PacketType, Consumer)}</li>
     *          <li>{@link #getPacketContainer()}</li>
     *           <li>{@link #sendAfter(PacketContainer)} | {@link #sendAfter(PacketType, Consumer)}</li>
     *          <li>{@link #runAfter(AutumnEmptyCallback)}</li>
     *     </ul>
     * </ul>
     */
    void process();
}
