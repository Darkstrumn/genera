package net.bms.genera.packets;

import io.netty.buffer.ByteBuf;
import net.bms.genera.te.TileFaerieHome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class MessageGlassJarUpdateStats implements IMessage {

    private ItemStack stack;
    private BlockPos pos;

    public MessageGlassJarUpdateStats(){}

    public MessageGlassJarUpdateStats(ItemStack stack, BlockPos pos) {
        this.stack = stack;
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        stack = ByteBufUtils.readItemStack(buf);
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    public static class MessageHandlerGlassJarUpdateStats implements IMessageHandler<MessageGlassJarUpdateStats, IMessage> {
        @Override
        public IMessage onMessage(MessageGlassJarUpdateStats message, MessageContext ctx) {
            ItemStack stack = message.stack;
            BlockPos pos = message.pos;
            WorldClient world = Minecraft.getMinecraft().world;
            if (world.isBlockLoaded(pos) &&
                    world.getBlockState(pos).getBlock().hasTileEntity(world.getBlockState(pos))) {
                TileEntity te = world.getTileEntity(pos);
                if (te instanceof TileFaerieHome) {
                    IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                    if (handler != null) {
                        handler.insertItem(0, stack, false);
                    }
                }
            }
            return null;
        }
    }

}
