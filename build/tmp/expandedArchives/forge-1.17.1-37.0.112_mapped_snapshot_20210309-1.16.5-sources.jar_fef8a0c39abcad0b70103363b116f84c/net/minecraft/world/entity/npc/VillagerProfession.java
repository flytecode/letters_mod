package net.minecraft.world.entity.npc;

import com.google.common.collect.ImmutableSet;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class VillagerProfession extends net.minecraftforge.registries.ForgeRegistryEntry<VillagerProfession> {
   public static final VillagerProfession f_35585_ = m_35612_("none", PoiType.f_27331_, (SoundEvent)null);
   public static final VillagerProfession f_35586_ = m_35612_("armorer", PoiType.f_27332_, SoundEvents.f_12510_);
   public static final VillagerProfession f_35587_ = m_35612_("butcher", PoiType.f_27333_, SoundEvents.f_12564_);
   public static final VillagerProfession f_35588_ = m_35612_("cartographer", PoiType.f_27334_, SoundEvents.f_12565_);
   public static final VillagerProfession f_35589_ = m_35612_("cleric", PoiType.f_27335_, SoundEvents.f_12566_);
   public static final VillagerProfession f_35590_ = m_35616_("farmer", PoiType.f_27336_, ImmutableSet.of(Items.f_42405_, Items.f_42404_, Items.f_42733_, Items.f_42499_), ImmutableSet.of(Blocks.f_50093_), SoundEvents.f_12567_);
   public static final VillagerProfession f_35591_ = m_35612_("fisherman", PoiType.f_27337_, SoundEvents.f_12568_);
   public static final VillagerProfession f_35592_ = m_35612_("fletcher", PoiType.f_27338_, SoundEvents.f_12569_);
   public static final VillagerProfession f_35593_ = m_35612_("leatherworker", PoiType.f_27339_, SoundEvents.f_12570_);
   public static final VillagerProfession f_35594_ = m_35612_("librarian", PoiType.f_27340_, SoundEvents.f_12571_);
   public static final VillagerProfession f_35595_ = m_35612_("mason", PoiType.f_27341_, SoundEvents.f_12572_);
   public static final VillagerProfession f_35596_ = m_35612_("nitwit", PoiType.f_27342_, (SoundEvent)null);
   public static final VillagerProfession f_35597_ = m_35612_("shepherd", PoiType.f_27343_, SoundEvents.f_12573_);
   public static final VillagerProfession f_35598_ = m_35612_("toolsmith", PoiType.f_27344_, SoundEvents.f_12574_);
   public static final VillagerProfession f_35599_ = m_35612_("weaponsmith", PoiType.f_27345_, SoundEvents.f_12575_);
   private final String f_35600_;
   private final PoiType f_35601_;
   private final ImmutableSet<Item> f_35602_;
   private final ImmutableSet<Block> f_35603_;
   @Nullable
   private final SoundEvent f_35604_;

   public VillagerProfession(String p_35607_, PoiType p_35608_, ImmutableSet<Item> p_35609_, ImmutableSet<Block> p_35610_, @Nullable SoundEvent p_35611_) {
      this.f_35600_ = p_35607_;
      this.f_35601_ = p_35608_;
      this.f_35602_ = p_35609_;
      this.f_35603_ = p_35610_;
      this.f_35604_ = p_35611_;
   }

   public String m_150028_() {
      return this.f_35600_;
   }

   public PoiType m_35622_() {
      return this.f_35601_;
   }

   public ImmutableSet<Item> m_35623_() {
      return this.f_35602_;
   }

   public ImmutableSet<Block> m_35624_() {
      return this.f_35603_;
   }

   @Nullable
   public SoundEvent m_35625_() {
      return this.f_35604_;
   }

   public String toString() {
      return this.f_35600_;
   }

   static VillagerProfession m_35612_(String p_35613_, PoiType p_35614_, @Nullable SoundEvent p_35615_) {
      return m_35616_(p_35613_, p_35614_, ImmutableSet.of(), ImmutableSet.of(), p_35615_);
   }

   static VillagerProfession m_35616_(String p_35617_, PoiType p_35618_, ImmutableSet<Item> p_35619_, ImmutableSet<Block> p_35620_, @Nullable SoundEvent p_35621_) {
      return Registry.m_122965_(Registry.f_122869_, new ResourceLocation(p_35617_), new VillagerProfession(p_35617_, p_35618_, p_35619_, p_35620_, p_35621_));
   }
}
