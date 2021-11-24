package net.minecraft.server.dedicated;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.storage.PlayerDataStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedicatedPlayerList extends PlayerList {
   private static final Logger f_139571_ = LogManager.getLogger();

   public DedicatedPlayerList(DedicatedServer p_139574_, RegistryAccess.RegistryHolder p_139575_, PlayerDataStorage p_139576_) {
      super(p_139574_, p_139575_, p_139576_, p_139574_.m_7913_().f_139715_);
      DedicatedServerProperties dedicatedserverproperties = p_139574_.m_7913_();
      this.m_11217_(dedicatedserverproperties.f_139714_);
      super.m_6628_(dedicatedserverproperties.f_139726_.get());
      this.m_139596_();
      this.m_139594_();
      this.m_139595_();
      this.m_139593_();
      this.m_139597_();
      this.m_139578_();
      this.m_139577_();
      if (!this.m_11305_().m_11385_().exists()) {
         this.m_139579_();
      }

   }

   public void m_6628_(boolean p_139584_) {
      super.m_6628_(p_139584_);
      this.m_7873_().m_139688_(p_139584_);
   }

   public void m_5749_(GameProfile p_139582_) {
      super.m_5749_(p_139582_);
      this.m_139577_();
   }

   public void m_5750_(GameProfile p_139587_) {
      super.m_5750_(p_139587_);
      this.m_139577_();
   }

   public void m_7542_() {
      this.m_139578_();
   }

   private void m_139593_() {
      try {
         this.m_11299_().m_11398_();
      } catch (IOException ioexception) {
         f_139571_.warn("Failed to save ip banlist: ", (Throwable)ioexception);
      }

   }

   private void m_139594_() {
      try {
         this.m_11295_().m_11398_();
      } catch (IOException ioexception) {
         f_139571_.warn("Failed to save user banlist: ", (Throwable)ioexception);
      }

   }

   private void m_139595_() {
      try {
         this.m_11299_().m_11399_();
      } catch (IOException ioexception) {
         f_139571_.warn("Failed to load ip banlist: ", (Throwable)ioexception);
      }

   }

   private void m_139596_() {
      try {
         this.m_11295_().m_11399_();
      } catch (IOException ioexception) {
         f_139571_.warn("Failed to load user banlist: ", (Throwable)ioexception);
      }

   }

   private void m_139597_() {
      try {
         this.m_11307_().m_11399_();
      } catch (Exception exception) {
         f_139571_.warn("Failed to load operators list: ", (Throwable)exception);
      }

   }

   private void m_139577_() {
      try {
         this.m_11307_().m_11398_();
      } catch (Exception exception) {
         f_139571_.warn("Failed to save operators list: ", (Throwable)exception);
      }

   }

   private void m_139578_() {
      try {
         this.m_11305_().m_11399_();
      } catch (Exception exception) {
         f_139571_.warn("Failed to load white-list: ", (Throwable)exception);
      }

   }

   private void m_139579_() {
      try {
         this.m_11305_().m_11398_();
      } catch (Exception exception) {
         f_139571_.warn("Failed to save white-list: ", (Throwable)exception);
      }

   }

   public boolean m_5764_(GameProfile p_139590_) {
      return !this.m_11311_() || this.m_11303_(p_139590_) || this.m_11305_().m_11453_(p_139590_);
   }

   public DedicatedServer m_7873_() {
      return (DedicatedServer)super.m_7873_();
   }

   public boolean m_5765_(GameProfile p_139592_) {
      return this.m_11307_().m_11351_(p_139592_);
   }
}