package net.bms.genera.packets

import io.netty.buffer.ByteBuf
import net.bms.genera.te.TileFaerieHome
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.ByteBufUtils
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.items.CapabilityItemHandler

class MessageGlassJarUpdateStats(private var stack: ItemStack, private var pos: BlockPos): IMessage {

    override fun fromBytes(buf: ByteBuf) {
        stack = ByteBufUtils.readItemStack(buf)
        pos = BlockPos(buf.readInt(), buf.readInt(), buf.readInt())
    }

    override fun toBytes(buf: ByteBuf) {
        ByteBufUtils.writeItemStack(buf, stack)
        buf.writeInt(pos.x)
        buf.writeInt(pos.y)
        buf.writeInt(pos.z)
    }

    class MessageHandlerGlassJarUpdateStats : IMessageHandler<MessageGlassJarUpdateStats, IMessage> {
        override fun onMessage(message: MessageGlassJarUpdateStats, ctx: MessageContext): IMessage? {
            val stack = message.stack
            val pos = message.pos
            val world = Minecraft.getMinecraft().world
            if (world.isBlockLoaded(pos) && world.getBlockState(pos).block.hasTileEntity(world.getBlockState(pos))) {
                val te = world.getTileEntity(pos)
                if (te is TileFaerieHome) {
                    val handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
                    handler?.insertItem(0, stack, false)
                }
            }
            return null
        }
    }

}
