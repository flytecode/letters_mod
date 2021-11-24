package net.minecraft.world.level.block;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.StringUtils;

public class PlayerHeadBlock extends SkullBlock {
   public PlayerHeadBlock(BlockBehaviour.Properties p_55177_) {
      super(SkullBlock.Types.PLAYER, p_55177_);
   }

   public void m_6402_(Level p_55179_, BlockPos p_55180_, BlockState p_55181_, @Nullable LivingEntity p_55182_, ItemStack p_55183_) {
      super.m_6402_(p_55179_, p_55180_, p_55181_, p_55182_, p_55183_);
      BlockEntity blockentity = p_55179_.m_7702_(p_55180_);
      if (blockentity instanceof SkullBlockEntity) {
         SkullBlockEntity skullblockentity = (SkullBlockEntity)blockentity;
         GameProfile gameprofile = null;
         if (p_55183_.m_41782_()) {
            CompoundTag compoundtag = p_55183_.m_41783_();
            if (compoundtag.m_128425_("SkullOwner", 10)) {
               gameprofile = NbtUtils.m_129228_(compoundtag.m_128469_("SkullOwner"));
            } else if (compoundtag.m_128425_("SkullOwner", 8) && !StringUtils.isBlank(compoundtag.m_128461_("SkullOwner"))) {
               gameprofile = new GameProfile((UUID)null, compoundtag.m_128461_("SkullOwner"));
            }
         }

         skullblockentity.m_59769_(gameprofile);
      }

   }
}