package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class DropperBlock extends DispenserBlock {
   private static final DispenseItemBehavior f_52939_ = new DefaultDispenseItemBehavior();

   public DropperBlock(BlockBehaviour.Properties p_52942_) {
      super(p_52942_);
   }

   protected DispenseItemBehavior m_7216_(ItemStack p_52947_) {
      return f_52939_;
   }

   public BlockEntity m_142194_(BlockPos p_153179_, BlockState p_153180_) {
      return new DropperBlockEntity(p_153179_, p_153180_);
   }

   protected void m_5824_(ServerLevel p_52944_, BlockPos p_52945_) {
      BlockSourceImpl blocksourceimpl = new BlockSourceImpl(p_52944_, p_52945_);
      DispenserBlockEntity dispenserblockentity = blocksourceimpl.m_8118_();
      int i = dispenserblockentity.m_59248_();
      if (i < 0) {
         p_52944_.m_46796_(1001, p_52945_, 0);
      } else {
         ItemStack itemstack = dispenserblockentity.m_8020_(i);
         if (!itemstack.m_41619_() && net.minecraftforge.items.VanillaInventoryCodeHooks.dropperInsertHook(p_52944_, p_52945_, dispenserblockentity, i, itemstack)) {
            Direction direction = p_52944_.m_8055_(p_52945_).m_61143_(f_52659_);
            Container container = HopperBlockEntity.m_59390_(p_52944_, p_52945_.m_142300_(direction));
            ItemStack itemstack1;
            if (container == null) {
               itemstack1 = f_52939_.m_6115_(blocksourceimpl, itemstack);
            } else {
               itemstack1 = HopperBlockEntity.m_59326_(dispenserblockentity, container, itemstack.m_41777_().m_41620_(1), direction.m_122424_());
               if (itemstack1.m_41619_()) {
                  itemstack1 = itemstack.m_41777_();
                  itemstack1.m_41774_(1);
               } else {
                  itemstack1 = itemstack.m_41777_();
               }
            }

            dispenserblockentity.m_6836_(i, itemstack1);
         }
      }
   }
}
