package net.bms.genera.item

import net.bms.genera.Genera
import net.bms.genera.capability.FaerieInformationProvider
import net.bms.genera.custom.Faerie
import net.bms.genera.entity.EntityFaerie
import net.bms.genera.te.TileFaerieHome
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class ItemGlassJarFull: Item() {
    init {
        creativeTab = Genera.TabGenera
        unlocalizedName = "glass_jar_full"
        setRegistryName("glass_jar_full")
        setMaxStackSize(1)
    }

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack?, worldIn: World?, tooltip: MutableList<String>?, flagIn: ITooltipFlag?) {
        val nbt = stack!!.tagCompound ?: return
        val type = nbt.getString("type")
        var typeName: String? = null

        val registry = GameRegistry.findRegistry(Faerie::class.java)
        if (registry != null) {
            val faerie = registry.getValue(ResourceLocation(Genera.MODID, "woodland"))
            if (faerie != null)
                typeName = faerie.name
        }

        when (type) {
            "woodland" -> typeName = I18n.format("item.glass_jar.description.type.woodland")
            "underground" -> typeName = I18n.format("item.glass_jar.description.type.cave")
            "highland" -> typeName = I18n.format("item.glass_jar.description.type.highlands")
        }

        tooltip!!.add(I18n.format("item.glass_jar.description.type.specifier") + ": " + typeName)
        tooltip.add(I18n.format("item.glass_jar.description.size.specifier") + ": " + nbt.getFloat("size"))
        tooltip.add(I18n.format("item.glass_jar.description.max_health.specifier") + ": " + nbt.getDouble("max_health"))
        tooltip.add(I18n.format("item.glass_jar.description.level.specifier") + ": " + nbt.getInteger("level"))
        tooltip.add(I18n.format("item.glass_jar.description.current_exp.specifier") + ": " + nbt.getInteger("current_exp"))
    }

    override fun onUpdate(itemstack: ItemStack?, world: World?, entity: Entity?, metadata: Int, bool: Boolean) {
        if (itemstack!!.tagCompound == null) {
            itemstack.tagCompound = NBTTagCompound()
        }
        val nbt = itemstack.tagCompound
        if (nbt != null && nbt.getInteger("current_exp") >= 100) {
            nbt.setInteger("level", nbt.getInteger("level") + 1)
            nbt.setInteger("current_exp", 0)
        }
    }

    override fun initCapabilities(stack: ItemStack?, nbt: NBTTagCompound?): net.minecraftforge.common.capabilities.ICapabilityProvider? {
        return FaerieInformationProvider()
    }

    override fun onItemUse(player: EntityPlayer?, worldIn: World?, pos: BlockPos?, hand: EnumHand?, facing: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        if (!worldIn!!.isRemote) {
            val stack = player!!.getHeldItem(hand!!)
            if (worldIn.getTileEntity(pos!!) !is TileFaerieHome) {
                val nbt = stack.tagCompound ?: return EnumActionResult.FAIL
                val faerie = EntityFaerie(worldIn, nbt.getDouble("max_health"), nbt.getString("type"), nbt.getFloat("size"), nbt.getInteger("level"), nbt.getInteger("current_exp"))
                faerie.setPosition(pos.x.toDouble(), pos.up().y.toDouble(), pos.z.toDouble())
                worldIn.spawnEntity(faerie)
                player.setHeldItem(hand, ItemStack(Items.GLASS_BOTTLE))
                return EnumActionResult.SUCCESS
            }
        }
        return super.onItemUse(player!!, worldIn, pos!!, hand!!, facing!!, hitX, hitY, hitZ)
    }
}
