/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S30PacketWindowItems
implements Packet<INetHandlerPlayClient> {
    private int windowId;
    private ItemStack[] itemStacks;

    public S30PacketWindowItems() {
    }

    public S30PacketWindowItems(int windowIdIn, List<ItemStack> p_i45186_2_) {
        this.windowId = windowIdIn;
        this.itemStacks = new ItemStack[p_i45186_2_.size()];
        for (int i = 0; i < this.itemStacks.length; ++i) {
            ItemStack itemstack = p_i45186_2_.get(i);
            this.itemStacks[i] = itemstack == null ? null : itemstack.copy();
        }
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.windowId = buf.readUnsignedByte();
        int i = buf.readShort();
        this.itemStacks = new ItemStack[i];
        for (int j = 0; j < i; ++j) {
            this.itemStacks[j] = buf.readItemStackFromBuffer();
        }
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeByte(this.windowId);
        buf.writeShort(this.itemStacks.length);
        for (ItemStack itemstack : this.itemStacks) {
            buf.writeItemStackToBuffer(itemstack);
        }
    }

    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleWindowItems(this);
    }

    public int func_148911_c() {
        return this.windowId;
    }

    public ItemStack[] getItemStacks() {
        return this.itemStacks;
    }
}
