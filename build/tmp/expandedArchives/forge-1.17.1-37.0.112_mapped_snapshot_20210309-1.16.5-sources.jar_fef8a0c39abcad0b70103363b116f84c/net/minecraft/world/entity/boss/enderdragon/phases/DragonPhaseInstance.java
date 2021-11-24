package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public interface DragonPhaseInstance {
   boolean m_7080_();

   void m_6991_();

   void m_6989_();

   void m_8059_(EndCrystal p_31315_, BlockPos p_31316_, DamageSource p_31317_, @Nullable Player p_31318_);

   void m_7083_();

   void m_7081_();

   float m_7072_();

   float m_7089_();

   EnderDragonPhase<? extends DragonPhaseInstance> m_7309_();

   @Nullable
   Vec3 m_5535_();

   float m_7584_(DamageSource p_31313_, float p_31314_);
}