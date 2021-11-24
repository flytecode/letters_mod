package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ServerList {
   private static final Logger f_105425_ = LogManager.getLogger();
   private final Minecraft f_105426_;
   private final List<ServerData> f_105427_ = Lists.newArrayList();

   public ServerList(Minecraft p_105430_) {
      this.f_105426_ = p_105430_;
      this.m_105431_();
   }

   public void m_105431_() {
      try {
         this.f_105427_.clear();
         CompoundTag compoundtag = NbtIo.m_128953_(new File(this.f_105426_.f_91069_, "servers.dat"));
         if (compoundtag == null) {
            return;
         }

         ListTag listtag = compoundtag.m_128437_("servers", 10);

         for(int i = 0; i < listtag.size(); ++i) {
            this.f_105427_.add(ServerData.m_105385_(listtag.m_128728_(i)));
         }
      } catch (Exception exception) {
         f_105425_.error("Couldn't load server list", (Throwable)exception);
      }

   }

   public void m_105442_() {
      try {
         ListTag listtag = new ListTag();

         for(ServerData serverdata : this.f_105427_) {
            listtag.add(serverdata.m_105378_());
         }

         CompoundTag compoundtag = new CompoundTag();
         compoundtag.m_128365_("servers", listtag);
         File file3 = File.createTempFile("servers", ".dat", this.f_105426_.f_91069_);
         NbtIo.m_128955_(compoundtag, file3);
         File file1 = new File(this.f_105426_.f_91069_, "servers.dat_old");
         File file2 = new File(this.f_105426_.f_91069_, "servers.dat");
         Util.m_137462_(file2, file3, file1);
      } catch (Exception exception) {
         f_105425_.error("Couldn't save server list", (Throwable)exception);
      }

   }

   public ServerData m_105432_(int p_105433_) {
      return this.f_105427_.get(p_105433_);
   }

   public void m_105440_(ServerData p_105441_) {
      this.f_105427_.remove(p_105441_);
   }

   public void m_105443_(ServerData p_105444_) {
      this.f_105427_.add(p_105444_);
   }

   public int m_105445_() {
      return this.f_105427_.size();
   }

   public void m_105434_(int p_105435_, int p_105436_) {
      ServerData serverdata = this.m_105432_(p_105435_);
      this.f_105427_.set(p_105435_, this.m_105432_(p_105436_));
      this.f_105427_.set(p_105436_, serverdata);
      this.m_105442_();
   }

   public void m_105437_(int p_105438_, ServerData p_105439_) {
      this.f_105427_.set(p_105438_, p_105439_);
   }

   public static void m_105446_(ServerData p_105447_) {
      ServerList serverlist = new ServerList(Minecraft.m_91087_());
      serverlist.m_105431_();

      for(int i = 0; i < serverlist.m_105445_(); ++i) {
         ServerData serverdata = serverlist.m_105432_(i);
         if (serverdata.f_105362_.equals(p_105447_.f_105362_) && serverdata.f_105363_.equals(p_105447_.f_105363_)) {
            serverlist.m_105437_(i, p_105447_);
            break;
         }
      }

      serverlist.m_105442_();
   }
}