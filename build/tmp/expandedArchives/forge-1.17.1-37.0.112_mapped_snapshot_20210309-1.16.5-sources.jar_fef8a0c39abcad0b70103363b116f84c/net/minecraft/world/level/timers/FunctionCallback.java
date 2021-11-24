package net.minecraft.world.level.timers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;

public class FunctionCallback implements TimerCallback<MinecraftServer> {
   final ResourceLocation f_82162_;

   public FunctionCallback(ResourceLocation p_82164_) {
      this.f_82162_ = p_82164_;
   }

   public void m_5821_(MinecraftServer p_82172_, TimerQueue<MinecraftServer> p_82173_, long p_82174_) {
      ServerFunctionManager serverfunctionmanager = p_82172_.m_129890_();
      serverfunctionmanager.m_136118_(this.f_82162_).ifPresent((p_82177_) -> {
         serverfunctionmanager.m_136112_(p_82177_, serverfunctionmanager.m_136129_());
      });
   }

   public static class Serializer extends TimerCallback.Serializer<MinecraftServer, FunctionCallback> {
      public Serializer() {
         super(new ResourceLocation("function"), FunctionCallback.class);
      }

      public void m_6585_(CompoundTag p_82182_, FunctionCallback p_82183_) {
         p_82182_.m_128359_("Name", p_82183_.f_82162_.toString());
      }

      public FunctionCallback m_6006_(CompoundTag p_82180_) {
         ResourceLocation resourcelocation = new ResourceLocation(p_82180_.m_128461_("Name"));
         return new FunctionCallback(resourcelocation);
      }
   }
}