package net.minecraft.server.rcon;

import java.util.UUID;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class RconConsoleSource implements CommandSource {
   private static final String f_144022_ = "Rcon";
   private static final Component f_11500_ = new TextComponent("Rcon");
   private final StringBuffer f_11501_ = new StringBuffer();
   private final MinecraftServer f_11502_;

   public RconConsoleSource(MinecraftServer p_11505_) {
      this.f_11502_ = p_11505_;
   }

   public void m_11512_() {
      this.f_11501_.setLength(0);
   }

   public String m_11513_() {
      return this.f_11501_.toString();
   }

   public CommandSourceStack m_11514_() {
      ServerLevel serverlevel = this.f_11502_.m_129783_();
      return new CommandSourceStack(this, Vec3.m_82528_(serverlevel.m_8900_()), Vec2.f_82462_, serverlevel, 4, "Rcon", f_11500_, this.f_11502_, (Entity)null);
   }

   public void m_6352_(Component p_11509_, UUID p_11510_) {
      this.f_11501_.append(p_11509_.getString()).append("\n"); // FIX MC-7569 - RCON has no newlines in multiline output
   }

   public boolean m_6999_() {
      return true;
   }

   public boolean m_7028_() {
      return true;
   }

   public boolean m_6102_() {
      return this.f_11502_.m_6983_();
   }
}
