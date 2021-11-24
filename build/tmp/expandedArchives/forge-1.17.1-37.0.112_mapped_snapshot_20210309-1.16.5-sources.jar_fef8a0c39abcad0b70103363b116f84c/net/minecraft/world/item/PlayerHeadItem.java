package net.minecraft.world.item;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.apache.commons.lang3.StringUtils;

public class PlayerHeadItem extends StandingAndWallBlockItem {
   public static final String f_151174_ = "SkullOwner";

   public PlayerHeadItem(Block p_42971_, Block p_42972_, Item.Properties p_42973_) {
      super(p_42971_, p_42972_, p_42973_);
   }

   public Component m_7626_(ItemStack p_42977_) {
      if (p_42977_.m_150930_(Items.f_42680_) && p_42977_.m_41782_()) {
         String s = null;
         CompoundTag compoundtag = p_42977_.m_41783_();
         if (compoundtag.m_128425_("SkullOwner", 8)) {
            s = compoundtag.m_128461_("SkullOwner");
         } else if (compoundtag.m_128425_("SkullOwner", 10)) {
            CompoundTag compoundtag1 = compoundtag.m_128469_("SkullOwner");
            if (compoundtag1.m_128425_("Name", 8)) {
               s = compoundtag1.m_128461_("Name");
            }
         }

         if (s != null) {
            return new TranslatableComponent(this.m_5524_() + ".named", s);
         }
      }

      return super.m_7626_(p_42977_);
   }

   public void m_142312_(CompoundTag p_151179_) {
      super.m_142312_(p_151179_);
      if (p_151179_.m_128425_("SkullOwner", 8) && !StringUtils.isBlank(p_151179_.m_128461_("SkullOwner"))) {
         GameProfile gameprofile = new GameProfile((UUID)null, p_151179_.m_128461_("SkullOwner"));
         SkullBlockEntity.m_155738_(gameprofile, (p_151177_) -> {
            p_151179_.m_128365_("SkullOwner", NbtUtils.m_129230_(new CompoundTag(), p_151177_));
         });
      }

   }
}