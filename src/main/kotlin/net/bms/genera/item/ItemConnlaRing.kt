package net.bms.genera.item

import baubles.api.BaubleType
import baubles.api.IBauble
import net.bms.genera.Genera
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraftforge.fml.common.Optional

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
class ItemConnlaRing: Item(), IBauble {
    init {
        creativeTab = Genera.TabGenera
        setMaxStackSize(1)
        setRegistryName(Genera.MODID, "ring_of_connla")
        unlocalizedName = "ring_of_connla"
    }

    override fun getBaubleType(itemstack: ItemStack): BaubleType {
        return BaubleType.RING
    }

    override fun onWornTick(itemstack: ItemStack?, player: EntityLivingBase?) {
        val strength = Potion.getPotionById(5)
        if (strength != null && !player!!.isPotionActive(strength)) {
            player.addPotionEffect(PotionEffect(strength, 300, 2, false, false))
        }
    }

    override fun onEquipped(itemstack: ItemStack?, player: EntityLivingBase?) {

    }

    override fun onUnequipped(itemstack: ItemStack?, player: EntityLivingBase?) {

    }

    override fun canEquip(itemstack: ItemStack?, player: EntityLivingBase?): Boolean {
        return true
    }

    override fun canUnequip(itemstack: ItemStack?, player: EntityLivingBase?): Boolean {
        return true
    }

    override fun willAutoSync(itemstack: ItemStack?, player: EntityLivingBase?): Boolean {
        return false
    }
}
