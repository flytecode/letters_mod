package net.minecraft.client.gui.screens.inventory;

import net.minecraft.network.protocol.game.ServerboundSetCommandMinecartPacket;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MinecartCommandBlockEditScreen extends AbstractCommandBlockEditScreen {
   private final BaseCommandBlock f_99214_;

   public MinecartCommandBlockEditScreen(BaseCommandBlock p_99216_) {
      this.f_99214_ = p_99216_;
   }

   public BaseCommandBlock m_6556_() {
      return this.f_99214_;
   }

   int m_7821_() {
      return 150;
   }

   protected void m_7856_() {
      super.m_7856_();
      this.f_97646_.m_94144_(this.m_6556_().m_45438_());
   }

   protected void m_6372_(BaseCommandBlock p_99218_) {
      if (p_99218_ instanceof MinecartCommandBlock.MinecartCommandBase) {
         MinecartCommandBlock.MinecartCommandBase minecartcommandblock$minecartcommandbase = (MinecartCommandBlock.MinecartCommandBase)p_99218_;
         this.f_96541_.m_91403_().m_104955_(new ServerboundSetCommandMinecartPacket(minecartcommandblock$minecartcommandbase.m_38543_().m_142049_(), this.f_97646_.m_94155_(), p_99218_.m_45440_()));
      }

   }
}