package net.minecraft.client.multiplayer;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ClientAdvancements {
   private static final Logger f_104387_ = LogManager.getLogger();
   private final Minecraft f_104388_;
   private final AdvancementList f_104389_ = new AdvancementList();
   private final Map<Advancement, AdvancementProgress> f_104390_ = Maps.newHashMap();
   @Nullable
   private ClientAdvancements.Listener f_104391_;
   @Nullable
   private Advancement f_104392_;

   public ClientAdvancements(Minecraft p_104395_) {
      this.f_104388_ = p_104395_;
   }

   public void m_104399_(ClientboundUpdateAdvancementsPacket p_104400_) {
      if (p_104400_.m_133575_()) {
         this.f_104389_.m_139332_();
         this.f_104390_.clear();
      }

      this.f_104389_.m_139335_(p_104400_.m_133573_());
      this.f_104389_.m_139333_(p_104400_.m_133570_());

      for(Entry<ResourceLocation, AdvancementProgress> entry : p_104400_.m_133574_().entrySet()) {
         Advancement advancement = this.f_104389_.m_139337_(entry.getKey());
         if (advancement != null) {
            AdvancementProgress advancementprogress = entry.getValue();
            advancementprogress.m_8198_(advancement.m_138325_(), advancement.m_138329_());
            this.f_104390_.put(advancement, advancementprogress);
            if (this.f_104391_ != null) {
               this.f_104391_.m_7922_(advancement, advancementprogress);
            }

            if (!p_104400_.m_133575_() && advancementprogress.m_8193_() && advancement.m_138320_() != null && advancement.m_138320_().m_14995_()) {
               this.f_104388_.m_91300_().m_94922_(new AdvancementToast(advancement));
            }
         } else {
            f_104387_.warn("Server informed client about progress for unknown advancement {}", entry.getKey());
         }
      }

   }

   public AdvancementList m_104396_() {
      return this.f_104389_;
   }

   public void m_104401_(@Nullable Advancement p_104402_, boolean p_104403_) {
      ClientPacketListener clientpacketlistener = this.f_104388_.m_91403_();
      if (clientpacketlistener != null && p_104402_ != null && p_104403_) {
         clientpacketlistener.m_104955_(ServerboundSeenAdvancementsPacket.m_134442_(p_104402_));
      }

      if (this.f_104392_ != p_104402_) {
         this.f_104392_ = p_104402_;
         if (this.f_104391_ != null) {
            this.f_104391_.m_6896_(p_104402_);
         }
      }

   }

   public void m_104397_(@Nullable ClientAdvancements.Listener p_104398_) {
      this.f_104391_ = p_104398_;
      this.f_104389_.m_139341_(p_104398_);
      if (p_104398_ != null) {
         for(Entry<Advancement, AdvancementProgress> entry : this.f_104390_.entrySet()) {
            p_104398_.m_7922_(entry.getKey(), entry.getValue());
         }

         p_104398_.m_6896_(this.f_104392_);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public interface Listener extends AdvancementList.Listener {
      void m_7922_(Advancement p_104404_, AdvancementProgress p_104405_);

      void m_6896_(@Nullable Advancement p_104406_);
   }
}