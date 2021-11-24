package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Nameable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BannerBlockEntity extends BlockEntity implements Nameable {
   public static final int f_155030_ = 6;
   public static final String f_155031_ = "Patterns";
   public static final String f_155032_ = "Pattern";
   public static final String f_155033_ = "Color";
   @Nullable
   private Component f_58473_;
   private DyeColor f_58474_;
   @Nullable
   private ListTag f_58475_;
   private boolean f_58476_;
   @Nullable
   private List<Pair<BannerPattern, DyeColor>> f_58477_;

   public BannerBlockEntity(BlockPos p_155035_, BlockState p_155036_) {
      super(BlockEntityType.f_58935_, p_155035_, p_155036_);
      this.f_58474_ = ((AbstractBannerBlock)p_155036_.m_60734_()).m_48674_();
   }

   public BannerBlockEntity(BlockPos p_155038_, BlockState p_155039_, DyeColor p_155040_) {
      this(p_155038_, p_155039_);
      this.f_58474_ = p_155040_;
   }

   @Nullable
   public static ListTag m_58487_(ItemStack p_58488_) {
      ListTag listtag = null;
      CompoundTag compoundtag = p_58488_.m_41737_("BlockEntityTag");
      if (compoundtag != null && compoundtag.m_128425_("Patterns", 9)) {
         listtag = compoundtag.m_128437_("Patterns", 10).m_6426_();
      }

      return listtag;
   }

   public void m_58489_(ItemStack p_58490_, DyeColor p_58491_) {
      this.f_58475_ = m_58487_(p_58490_);
      this.f_58474_ = p_58491_;
      this.f_58477_ = null;
      this.f_58476_ = true;
      this.f_58473_ = p_58490_.m_41788_() ? p_58490_.m_41786_() : null;
   }

   public Component m_7755_() {
      return (Component)(this.f_58473_ != null ? this.f_58473_ : new TranslatableComponent("block.minecraft.banner"));
   }

   @Nullable
   public Component m_7770_() {
      return this.f_58473_;
   }

   public void m_58501_(Component p_58502_) {
      this.f_58473_ = p_58502_;
   }

   public CompoundTag m_6945_(CompoundTag p_58500_) {
      super.m_6945_(p_58500_);
      if (this.f_58475_ != null) {
         p_58500_.m_128365_("Patterns", this.f_58475_);
      }

      if (this.f_58473_ != null) {
         p_58500_.m_128359_("CustomName", Component.Serializer.m_130703_(this.f_58473_));
      }

      return p_58500_;
   }

   public void m_142466_(CompoundTag p_155042_) {
      super.m_142466_(p_155042_);
      if (p_155042_.m_128425_("CustomName", 8)) {
         this.f_58473_ = Component.Serializer.m_130701_(p_155042_.m_128461_("CustomName"));
      }

      this.f_58475_ = p_155042_.m_128437_("Patterns", 10);
      this.f_58477_ = null;
      this.f_58476_ = true;
   }

   @Nullable
   public ClientboundBlockEntityDataPacket m_7033_() {
      return new ClientboundBlockEntityDataPacket(this.f_58858_, 6, this.m_5995_());
   }

   public CompoundTag m_5995_() {
      return this.m_6945_(new CompoundTag());
   }

   public static int m_58504_(ItemStack p_58505_) {
      CompoundTag compoundtag = p_58505_.m_41737_("BlockEntityTag");
      return compoundtag != null && compoundtag.m_128441_("Patterns") ? compoundtag.m_128437_("Patterns", 10).size() : 0;
   }

   public List<Pair<BannerPattern, DyeColor>> m_58508_() {
      if (this.f_58477_ == null && this.f_58476_) {
         this.f_58477_ = m_58484_(this.f_58474_, this.f_58475_);
      }

      return this.f_58477_;
   }

   public static List<Pair<BannerPattern, DyeColor>> m_58484_(DyeColor p_58485_, @Nullable ListTag p_58486_) {
      List<Pair<BannerPattern, DyeColor>> list = Lists.newArrayList();
      list.add(Pair.of(BannerPattern.BASE, p_58485_));
      if (p_58486_ != null) {
         for(int i = 0; i < p_58486_.size(); ++i) {
            CompoundTag compoundtag = p_58486_.m_128728_(i);
            BannerPattern bannerpattern = BannerPattern.m_58575_(compoundtag.m_128461_("Pattern"));
            if (bannerpattern != null) {
               int j = compoundtag.m_128451_("Color");
               list.add(Pair.of(bannerpattern, DyeColor.m_41053_(j)));
            }
         }
      }

      return list;
   }

   public static void m_58509_(ItemStack p_58510_) {
      CompoundTag compoundtag = p_58510_.m_41737_("BlockEntityTag");
      if (compoundtag != null && compoundtag.m_128425_("Patterns", 9)) {
         ListTag listtag = compoundtag.m_128437_("Patterns", 10);
         if (!listtag.isEmpty()) {
            listtag.remove(listtag.size() - 1);
            if (listtag.isEmpty()) {
               p_58510_.m_41749_("BlockEntityTag");
            }

         }
      }
   }

   public ItemStack m_155043_() {
      ItemStack itemstack = new ItemStack(BannerBlock.m_49014_(this.f_58474_));
      if (this.f_58475_ != null && !this.f_58475_.isEmpty()) {
         itemstack.m_41698_("BlockEntityTag").m_128365_("Patterns", this.f_58475_.m_6426_());
      }

      if (this.f_58473_ != null) {
         itemstack.m_41714_(this.f_58473_);
      }

      return itemstack;
   }

   public DyeColor m_155044_() {
      return this.f_58474_;
   }
}