package net.minecraft.world.level.timers;

import net.minecraft.commands.CommandFunction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.tags.Tag;

public class FunctionTagCallback implements TimerCallback<MinecraftServer> {
   final ResourceLocation f_82189_;

   public FunctionTagCallback(ResourceLocation p_82191_) {
      this.f_82189_ = p_82191_;
   }

   public void m_5821_(MinecraftServer p_82199_, TimerQueue<MinecraftServer> p_82200_, long p_82201_) {
      ServerFunctionManager serverfunctionmanager = p_82199_.m_129890_();
      Tag<CommandFunction> tag = serverfunctionmanager.m_136123_(this.f_82189_);

      for(CommandFunction commandfunction : tag.m_6497_()) {
         serverfunctionmanager.m_136112_(commandfunction, serverfunctionmanager.m_136129_());
      }

   }

   public static class Serializer extends TimerCallback.Serializer<MinecraftServer, FunctionTagCallback> {
      public Serializer() {
         super(new ResourceLocation("function_tag"), FunctionTagCallback.class);
      }

      public void m_6585_(CompoundTag p_82206_, FunctionTagCallback p_82207_) {
         p_82206_.m_128359_("Name", p_82207_.f_82189_.toString());
      }

      public FunctionTagCallback m_6006_(CompoundTag p_82204_) {
         ResourceLocation resourcelocation = new ResourceLocation(p_82204_.m_128461_("Name"));
         return new FunctionTagCallback(resourcelocation);
      }
   }
}