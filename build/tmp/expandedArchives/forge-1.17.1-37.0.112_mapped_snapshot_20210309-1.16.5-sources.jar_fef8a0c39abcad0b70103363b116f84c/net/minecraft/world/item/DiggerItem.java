package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DiggerItem extends TieredItem implements Vanishable {
   private final Tag<Block> f_40979_;
   protected final float f_40980_;
   private final float f_40981_;
   private final Multimap<Attribute, AttributeModifier> f_40982_;

   public DiggerItem(float p_150810_, float p_150811_, Tier p_150812_, Tag<Block> p_150813_, Item.Properties p_150814_) {
      super(p_150812_, p_150814_);
      this.f_40979_ = p_150813_;
      this.f_40980_ = p_150812_.m_6624_();
      this.f_40981_ = p_150810_ + p_150812_.m_6631_();
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Tool modifier", (double)this.f_40981_, AttributeModifier.Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Tool modifier", (double)p_150811_, AttributeModifier.Operation.ADDITION));
      this.f_40982_ = builder.build();
   }

   public float m_8102_(ItemStack p_41004_, BlockState p_41005_) {
      return this.f_40979_.m_8110_(p_41005_.m_60734_()) ? this.f_40980_ : 1.0F;
   }

   public boolean m_7579_(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
      p_40994_.m_41622_(2, p_40996_, (p_41007_) -> {
         p_41007_.m_21166_(EquipmentSlot.MAINHAND);
      });
      return true;
   }

   public boolean m_6813_(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
      if (!p_40999_.f_46443_ && p_41000_.m_60800_(p_40999_, p_41001_) != 0.0F) {
         p_40998_.m_41622_(1, p_41002_, (p_40992_) -> {
            p_40992_.m_21166_(EquipmentSlot.MAINHAND);
         });
      }

      return true;
   }

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot p_40990_) {
      return p_40990_ == EquipmentSlot.MAINHAND ? this.f_40982_ : super.m_7167_(p_40990_);
   }

   public float m_41008_() {
      return this.f_40981_;
   }

   @Deprecated // FORGE: Use stack sensitive variant below
   public boolean m_8096_(BlockState p_150816_) {
      if (net.minecraftforge.common.TierSortingRegistry.isTierSorted(m_43314_())) {
         return net.minecraftforge.common.TierSortingRegistry.isCorrectTierForDrops(m_43314_(), p_150816_) && p_150816_.m_60620_(this.f_40979_);
      }
      int i = this.m_43314_().m_6604_();
      if (i < 3 && p_150816_.m_60620_(BlockTags.f_144284_)) {
         return false;
      } else if (i < 2 && p_150816_.m_60620_(BlockTags.f_144285_)) {
         return false;
      } else {
         return i < 1 && p_150816_.m_60620_(BlockTags.f_144286_) ? false : p_150816_.m_60620_(this.f_40979_);
      }
   }

   // FORGE START
   @Override
   public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
      return state.m_60620_(f_40979_) && net.minecraftforge.common.TierSortingRegistry.isCorrectTierForDrops(m_43314_(), state);
   }
}
