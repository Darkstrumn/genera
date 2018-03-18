package net.bms.genera.te

import net.bms.genera.Genera
import net.bms.genera.init.GeneraItems
import net.bms.genera.packets.MessageGlassJarUpdateStats
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

class TileFaerieHome : TileEntity(), ITickable {
    private val SIZE = 1
    private val TIME_BETWEEN_GROWTH = 20 * 30
    private var timeSinceLastGrowth = 0

    var itemStackHandler = object : ItemStackHandler(SIZE) {
        override fun onContentsChanged(slot: Int) {
            this@TileFaerieHome.markDirty()
        }
    }

    override fun readFromNBT(nbt: NBTTagCompound) {
        super.readFromNBT(nbt)
        if (nbt.hasKey("item")) {
            itemStackHandler.deserializeNBT(nbt.getTag("item") as NBTTagCompound)
        }
    }

    override fun writeToNBT(nbt: NBTTagCompound): NBTTagCompound {
        super.writeToNBT(nbt)
        nbt.setTag("item", itemStackHandler.serializeNBT())
        return nbt
    }

    fun canInteractWith(player: EntityPlayer): Boolean {
        return !isInvalid && player.getDistanceSq(pos.add(0.5, 0.5, 0.5)) <= 64.0
    }

    override fun hasCapability(cap: Capability<*>, side: EnumFacing?): Boolean {
        return cap === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(cap, side)
    }

    override fun <T> getCapability(cap: Capability<T>, side: EnumFacing?): T? {
        return if (cap === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) itemStackHandler as T else super.getCapability(cap, side)
    }

    override fun update() {
        val cap = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null) ?: return
        if (!world.isRemote) {
            timeSinceLastGrowth++
            if (timeSinceLastGrowth >= TIME_BETWEEN_GROWTH) {
                val stack = cap.getStackInSlot(0)
                if (stack.item === GeneraItems.ItemGlassJarFull && stack.count == 1) {
                    val nbt = stack.tagCompound
                    if (nbt != null) {
                        nbt.setFloat("size", nbt.getFloat("size") + 0.02f)
                        nbt.setDouble("max_health", nbt.getDouble("max_health") + 0.5)
                        nbt.setInteger("current_exp", nbt.getInteger("current_exp") + 25)
                    }
                }
                Genera.SIMPLEIMPL_INSTANCE.sendToAll(MessageGlassJarUpdateStats(stack, getPos()))
                timeSinceLastGrowth = 0
            }
        }
    }
}
