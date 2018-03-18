package net.bms.genera.entity

import io.netty.buffer.ByteBuf
import net.bms.genera.capability.interfaces.IFaerieInformation
import net.bms.genera.custom.Ritual
import net.bms.genera.init.GeneraItems
import net.minecraft.entity.EntityCreature
import net.minecraft.entity.ai.EntityAIWander
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.NonNullList
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData
import java.nio.charset.Charset

/**
 * Created by ben on 3/25/17.
 */
class EntityFaerie @JvmOverloads constructor(worldIn: World, maxHealth: Double = 2.0, type: String = "woodland",
                                             size: Float = 0.1f, level: Int = 1, current_exp: Int = 0): EntityCreature(worldIn), IEntityAdditionalSpawnData {
    var faerieInformation = FAERIE_INFORMATION!!.defaultInstance

    init {
        faerieInformation!!.maxHealth = maxHealth
        faerieInformation!!.type = type
        faerieInformation!!.size = size
        faerieInformation!!.level = level
        faerieInformation!!.currentExp = current_exp
        setSize(1F, 1F)
    }

    override fun initEntityAI() {
        this.tasks.addTask(1, EntityAIWander(this, 1.0))
    }

    public override fun processInteract(player: EntityPlayer?, hand: EnumHand?): Boolean {
        if (!player!!.world.isRemote) {
            val stack = player.getHeldItem(hand!!)
            if (stack.item === Items.GLASS_BOTTLE) {
                this.setDead()
                val newStack = ItemStack(GeneraItems.ItemGlassJarFull)
                newStack.tagCompound = NBTTagCompound()
                val nbt = newStack.tagCompound ?: return false
                nbt.setFloat("size", faerieInformation!!.size)
                nbt.setString("type", faerieInformation!!.type)
                nbt.setDouble("max_health", faerieInformation!!.maxHealth)
                nbt.setInteger("level", faerieInformation!!.level)
                nbt.setInteger("current_exp", faerieInformation!!.currentExp)
                stack.count -= 1
                val slot = player.inventory.firstEmptyStack
                if (slot != -1) {
                    player.inventory.setInventorySlotContents(slot, newStack)
                }
                else {
                    player.dropItem(newStack, false)
                }
            }
        }
        return super.processInteract(player, hand!!)
    }

    override fun hasCapability(capObject: Capability<*>, side: EnumFacing?): Boolean {
        return capObject === FAERIE_INFORMATION && side == EnumFacing.UP || super.hasCapability(capObject, side)
    }

    override fun <T> getCapability(capObject: Capability<T>, side: EnumFacing?): T? {
        return if (capObject === FAERIE_INFORMATION && side == EnumFacing.UP) FAERIE_INFORMATION.cast<T>(FAERIE_INFORMATION.defaultInstance) else super.getCapability(capObject, side)
    }

    override fun onEntityUpdate() {
        runRituals()
        if (faerieInformation!!.currentExp >= EXP_TO_LEVEL_UP) {
            faerieInformation!!.level = faerieInformation!!.level + 1
            faerieInformation!!.currentExp = 0
        } else if (faerieInformation!!.currentExp < 0) {
            faerieInformation!!.level = faerieInformation!!.level - 1
            faerieInformation!!.currentExp = 0
        }
        if (faerieInformation!!.level < 0)
            faerieInformation!!.level = 0
        addEffects()
    }

    // called by server
    override fun writeSpawnData(buffer: ByteBuf) {
        buffer.writeDouble(faerieInformation!!.maxHealth)
        buffer.writeFloat(faerieInformation!!.size)
        buffer.writeInt(faerieInformation!!.type.length)
        buffer.writeCharSequence(faerieInformation!!.type, Charset.defaultCharset())
        buffer.writeInt(faerieInformation!!.level)
        buffer.writeInt(faerieInformation!!.currentExp)
    }

    // called by client
    override fun readSpawnData(additionalData: ByteBuf) {
        faerieInformation!!.maxHealth = additionalData.readDouble()
        faerieInformation!!.size = additionalData.readFloat()
        val typeSize = additionalData.readInt()
        faerieInformation!!.type = additionalData.readCharSequence(typeSize, Charset.defaultCharset()).toString()
        faerieInformation!!.level = additionalData.readInt()
        faerieInformation!!.currentExp = additionalData.readInt()
    }

    override fun writeEntityToNBT(comp: NBTTagCompound) {
        super.writeEntityToNBT(comp)

        comp.setFloat("size", faerieInformation!!.size)
        comp.setInteger("current_exp", faerieInformation!!.currentExp)
        comp.setString("type", faerieInformation!!.type)
        comp.setInteger("level", faerieInformation!!.level)
        comp.setDouble("max_health", faerieInformation!!.maxHealth)
    }

    override fun readEntityFromNBT(comp: NBTTagCompound) {
        super.readEntityFromNBT(comp)
        faerieInformation!!.size = comp.getFloat("size")
        faerieInformation!!.currentExp = comp.getInteger("current_exp")
        faerieInformation!!.type = comp.getString("type")
        faerieInformation!!.level = comp.getInteger("level")
        faerieInformation!!.maxHealth = comp.getDouble("max_health")
    }

    private fun runRituals() {
        val items = world.getEntitiesWithinAABB(EntityItem::class.java, entityBoundingBox.grow(faerieInformation!!.size.toDouble() + 1))
        if (!items.isEmpty()) {
            val itemStacks = NonNullList.create<ItemStack>()
            var experienceCost = 0
            val index = 0
            var stack = ItemStack.EMPTY
            val iterator = GameRegistry.findRegistry(Ritual::class.java).iterator()
            for (item in items) {
                itemStacks.add(index, item.item)
            }
            while (iterator.hasNext()) {
                if (!stack.isEmpty) break
                val ritual = iterator.next()
                stack = ritual.getResult(itemStacks, faerieInformation!!.level, faerieInformation!!.type)
                experienceCost = ritual.getInt("experience_cost")
            }
            if (!stack.isEmpty) {
                for (item in items) {
                    item.item = ItemStack.EMPTY
                }
                val item = EntityItem(world, posX, posY, posZ)
                item.item = stack
                if (!world.isRemote)
                    world.spawnEntity(item)
                faerieInformation!!.level = faerieInformation!!.level - experienceCost
            }
        }
    }

    private fun addEffects() {
        val player = this.world.getNearestAttackablePlayer(this, 10.0, 10.0) ?: return
        when (faerieInformation!!.type) {
            "woodland" // Woodland
            -> if (player.entityData.getInteger("genera.sacrifices_made") < 5) {
                val healthBoost = Potion.getPotionById(21) ?: return
                if (!player.isPotionActive(healthBoost)) {
                    faerieInformation!!.currentExp = faerieInformation!!.currentExp + 10
                    player.addPotionEffect(PotionEffect(healthBoost, faerieInformation!!.maxHealth.toInt() * 300))
                }
                if (faerieInformation!!.level >= 3) {
                    val health = Potion.getPotionById(6) ?: return
                    if (!player.isPotionActive(health)) {
                        faerieInformation!!.currentExp = faerieInformation!!.currentExp + 10
                        player.addPotionEffect(PotionEffect(health, faerieInformation!!.maxHealth.toInt() * 300))
                    }
                }
                if (faerieInformation!!.level >= 10) {
                    var hasCinnabar = false
                    var slot = 0
                    while (slot <= player.inventory.sizeInventory) {
                        val stack = player.inventory.getStackInSlot(slot)
                        if (stack.item === GeneraItems.ItemCinnabar && stack.count >= 2) {
                            hasCinnabar = true
                        }
                        slot++
                    }
                    if (hasCinnabar) {
                        if (player.health <= player.maxHealth - 1.0) {
                            player.health = player.maxHealth
                            faerieInformation!!.currentExp = faerieInformation!!.currentExp - 25
                            player.inventory.decrStackSize(slot, 2)
                        }
                    }
                }
            } else {
                val poison = Potion.getPotionById(19) ?: return
                if (!player.isPotionActive(poison)) {
                    faerieInformation!!.currentExp = faerieInformation!!.currentExp + 10
                    player.addPotionEffect(PotionEffect(poison, faerieInformation!!.maxHealth.toInt() * 300))
                }
            }
            "underground" // Underground
            -> if (player.entityData.getInteger("genera.sacrifices_made") < 5) {
                val haste = Potion.getPotionById(3) ?: return
                if (!player.isPotionActive(haste)) {
                    faerieInformation!!.currentExp = faerieInformation!!.currentExp + 10
                    player.addPotionEffect(PotionEffect(haste, faerieInformation!!.maxHealth.toInt() * 300))
                }
                if (faerieInformation!!.level >= 5) {
                    val nightVision = Potion.getPotionById(16) ?: return
                    if (!player.isPotionActive(nightVision)) {
                        faerieInformation!!.currentExp = faerieInformation!!.currentExp + 10
                        player.addPotionEffect(PotionEffect(nightVision, faerieInformation!!.maxHealth.toInt() * 300))
                    }
                }
            } else {
                val blindness = Potion.getPotionById(15) ?: return
                if (!player.isPotionActive(blindness)) {
                    faerieInformation!!.currentExp = faerieInformation!!.currentExp + 10
                    player.addPotionEffect(PotionEffect(blindness, faerieInformation!!.maxHealth.toInt() * 300))
                }
            }
            "highland" // Mountainous
            -> if (player.entityData.getInteger("genera.sacrifices_made") < 5) {
                val jump_boost = Potion.getPotionById(8) ?: return
                if (!player.isPotionActive(jump_boost)) {
                    faerieInformation!!.currentExp = faerieInformation!!.currentExp + 10
                    player.addPotionEffect(PotionEffect(jump_boost, faerieInformation!!.maxHealth.toInt() * 300))
                }
            } else {
                val wither = Potion.getPotionById(20) ?: return
                if (!player.isPotionActive(wither)) {
                    faerieInformation!!.currentExp = faerieInformation!!.currentExp + 10
                    player.addPotionEffect(PotionEffect(wither, faerieInformation!!.maxHealth.toInt() * 300))
                }
            }
        }
    }

    companion object {
        @CapabilityInject(IFaerieInformation::class)
        private val FAERIE_INFORMATION: Capability<IFaerieInformation>? = null
        private val EXP_TO_LEVEL_UP = 50
    }
}