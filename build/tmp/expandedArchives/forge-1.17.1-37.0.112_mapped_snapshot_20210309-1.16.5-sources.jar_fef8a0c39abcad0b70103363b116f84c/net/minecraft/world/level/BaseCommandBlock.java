package net.minecraft.world.level;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public abstract class BaseCommandBlock implements CommandSource {
   private static final SimpleDateFormat f_45397_ = new SimpleDateFormat("HH:mm:ss");
   private static final Component f_45398_ = new TextComponent("@");
   private long f_45399_ = -1L;
   private boolean f_45400_ = true;
   private int f_45401_;
   private boolean f_45402_ = true;
   @Nullable
   private Component f_45403_;
   private String f_45404_ = "";
   private Component f_45405_ = f_45398_;

   public int m_45436_() {
      return this.f_45401_;
   }

   public void m_45410_(int p_45411_) {
      this.f_45401_ = p_45411_;
   }

   public Component m_45437_() {
      return this.f_45403_ == null ? TextComponent.f_131282_ : this.f_45403_;
   }

   public CompoundTag m_45421_(CompoundTag p_45422_) {
      p_45422_.m_128359_("Command", this.f_45404_);
      p_45422_.m_128405_("SuccessCount", this.f_45401_);
      p_45422_.m_128359_("CustomName", Component.Serializer.m_130703_(this.f_45405_));
      p_45422_.m_128379_("TrackOutput", this.f_45402_);
      if (this.f_45403_ != null && this.f_45402_) {
         p_45422_.m_128359_("LastOutput", Component.Serializer.m_130703_(this.f_45403_));
      }

      p_45422_.m_128379_("UpdateLastExecution", this.f_45400_);
      if (this.f_45400_ && this.f_45399_ > 0L) {
         p_45422_.m_128356_("LastExecution", this.f_45399_);
      }

      return p_45422_;
   }

   public void m_45431_(CompoundTag p_45432_) {
      this.f_45404_ = p_45432_.m_128461_("Command");
      this.f_45401_ = p_45432_.m_128451_("SuccessCount");
      if (p_45432_.m_128425_("CustomName", 8)) {
         this.m_45423_(Component.Serializer.m_130701_(p_45432_.m_128461_("CustomName")));
      }

      if (p_45432_.m_128425_("TrackOutput", 1)) {
         this.f_45402_ = p_45432_.m_128471_("TrackOutput");
      }

      if (p_45432_.m_128425_("LastOutput", 8) && this.f_45402_) {
         try {
            this.f_45403_ = Component.Serializer.m_130701_(p_45432_.m_128461_("LastOutput"));
         } catch (Throwable throwable) {
            this.f_45403_ = new TextComponent(throwable.getMessage());
         }
      } else {
         this.f_45403_ = null;
      }

      if (p_45432_.m_128441_("UpdateLastExecution")) {
         this.f_45400_ = p_45432_.m_128471_("UpdateLastExecution");
      }

      if (this.f_45400_ && p_45432_.m_128441_("LastExecution")) {
         this.f_45399_ = p_45432_.m_128454_("LastExecution");
      } else {
         this.f_45399_ = -1L;
      }

   }

   public void m_6590_(String p_45420_) {
      this.f_45404_ = p_45420_;
      this.f_45401_ = 0;
   }

   public String m_45438_() {
      return this.f_45404_;
   }

   public boolean m_45414_(Level p_45415_) {
      if (!p_45415_.f_46443_ && p_45415_.m_46467_() != this.f_45399_) {
         if ("Searge".equalsIgnoreCase(this.f_45404_)) {
            this.f_45403_ = new TextComponent("#itzlipofutzli");
            this.f_45401_ = 1;
            return true;
         } else {
            this.f_45401_ = 0;
            MinecraftServer minecraftserver = this.m_5991_().m_142572_();
            if (minecraftserver.m_6993_() && !StringUtil.m_14408_(this.f_45404_)) {
               try {
                  this.f_45403_ = null;
                  CommandSourceStack commandsourcestack = this.m_6712_().m_81334_((p_45417_, p_45418_, p_45419_) -> {
                     if (p_45418_) {
                        ++this.f_45401_;
                     }

                  });
                  minecraftserver.m_129892_().m_82117_(commandsourcestack, this.f_45404_);
               } catch (Throwable throwable) {
                  CrashReport crashreport = CrashReport.m_127521_(throwable, "Executing command block");
                  CrashReportCategory crashreportcategory = crashreport.m_127514_("Command to be executed");
                  crashreportcategory.m_128165_("Command", this::m_45438_);
                  crashreportcategory.m_128165_("Name", () -> {
                     return this.m_45439_().getString();
                  });
                  throw new ReportedException(crashreport);
               }
            }

            if (this.f_45400_) {
               this.f_45399_ = p_45415_.m_46467_();
            } else {
               this.f_45399_ = -1L;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public Component m_45439_() {
      return this.f_45405_;
   }

   public void m_45423_(@Nullable Component p_45424_) {
      if (p_45424_ != null) {
         this.f_45405_ = p_45424_;
      } else {
         this.f_45405_ = f_45398_;
      }

   }

   public void m_6352_(Component p_45426_, UUID p_45427_) {
      if (this.f_45402_) {
         this.f_45403_ = (new TextComponent("[" + f_45397_.format(new Date()) + "] ")).m_7220_(p_45426_);
         this.m_7368_();
      }

   }

   public abstract ServerLevel m_5991_();

   public abstract void m_7368_();

   public void m_45433_(@Nullable Component p_45434_) {
      this.f_45403_ = p_45434_;
   }

   public void m_45428_(boolean p_45429_) {
      this.f_45402_ = p_45429_;
   }

   public boolean m_45440_() {
      return this.f_45402_;
   }

   public InteractionResult m_45412_(Player p_45413_) {
      if (!p_45413_.m_36337_()) {
         return InteractionResult.PASS;
      } else {
         if (p_45413_.m_20193_().f_46443_) {
            p_45413_.m_7907_(this);
         }

         return InteractionResult.m_19078_(p_45413_.f_19853_.f_46443_);
      }
   }

   public abstract Vec3 m_6607_();

   public abstract CommandSourceStack m_6712_();

   public boolean m_6999_() {
      return this.m_5991_().m_46469_().m_46207_(GameRules.f_46144_) && this.f_45402_;
   }

   public boolean m_7028_() {
      return this.f_45402_;
   }

   public boolean m_6102_() {
      return this.m_5991_().m_46469_().m_46207_(GameRules.f_46138_);
   }
}