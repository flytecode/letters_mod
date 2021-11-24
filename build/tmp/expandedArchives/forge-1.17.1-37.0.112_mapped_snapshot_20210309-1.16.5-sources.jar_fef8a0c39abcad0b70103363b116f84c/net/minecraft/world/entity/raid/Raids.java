package net.minecraft.world.entity.raid;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;

public class Raids extends SavedData {
   private static final String f_150234_ = "raids";
   private final Map<Integer, Raid> f_37951_ = Maps.newHashMap();
   private final ServerLevel f_37952_;
   private int f_37953_;
   private int f_37954_;

   public Raids(ServerLevel p_37956_) {
      this.f_37952_ = p_37956_;
      this.f_37953_ = 1;
      this.m_77762_();
   }

   public Raid m_37958_(int p_37959_) {
      return this.f_37951_.get(p_37959_);
   }

   public void m_37957_() {
      ++this.f_37954_;
      Iterator<Raid> iterator = this.f_37951_.values().iterator();

      while(iterator.hasNext()) {
         Raid raid = iterator.next();
         if (this.f_37952_.m_46469_().m_46207_(GameRules.f_46154_)) {
            raid.m_37774_();
         }

         if (raid.m_37762_()) {
            iterator.remove();
            this.m_77762_();
         } else {
            raid.m_37775_();
         }
      }

      if (this.f_37954_ % 200 == 0) {
         this.m_77762_();
      }

      DebugPackets.m_133688_(this.f_37952_, this.f_37951_.values());
   }

   public static boolean m_37965_(Raider p_37966_, Raid p_37967_) {
      if (p_37966_ != null && p_37967_ != null && p_37967_.m_37769_() != null) {
         return p_37966_.m_6084_() && p_37966_.m_37882_() && p_37966_.m_21216_() <= 2400 && p_37966_.f_19853_.m_6042_() == p_37967_.m_37769_().m_6042_();
      } else {
         return false;
      }
   }

   @Nullable
   public Raid m_37963_(ServerPlayer p_37964_) {
      if (p_37964_.m_5833_()) {
         return null;
      } else if (this.f_37952_.m_46469_().m_46207_(GameRules.f_46154_)) {
         return null;
      } else {
         DimensionType dimensiontype = p_37964_.f_19853_.m_6042_();
         if (!dimensiontype.m_63963_()) {
            return null;
         } else {
            BlockPos blockpos = p_37964_.m_142538_();
            List<PoiRecord> list = this.f_37952_.m_8904_().m_27181_(PoiType.f_27330_, blockpos, 64, PoiManager.Occupancy.IS_OCCUPIED).collect(Collectors.toList());
            int i = 0;
            Vec3 vec3 = Vec3.f_82478_;

            for(PoiRecord poirecord : list) {
               BlockPos blockpos2 = poirecord.m_27257_();
               vec3 = vec3.m_82520_((double)blockpos2.m_123341_(), (double)blockpos2.m_123342_(), (double)blockpos2.m_123343_());
               ++i;
            }

            BlockPos blockpos1;
            if (i > 0) {
               vec3 = vec3.m_82490_(1.0D / (double)i);
               blockpos1 = new BlockPos(vec3);
            } else {
               blockpos1 = blockpos;
            }

            Raid raid = this.m_37960_(p_37964_.m_9236_(), blockpos1);
            boolean flag = false;
            if (!raid.m_37770_()) {
               if (!this.f_37951_.containsKey(raid.m_37781_())) {
                  this.f_37951_.put(raid.m_37781_(), raid);
               }

               flag = true;
            } else if (raid.m_37773_() < raid.m_37772_()) {
               flag = true;
            } else {
               p_37964_.m_21195_(MobEffects.f_19594_);
               p_37964_.f_8906_.m_141995_(new ClientboundEntityEventPacket(p_37964_, (byte)43));
            }

            if (flag) {
               raid.m_37728_(p_37964_);
               p_37964_.f_8906_.m_141995_(new ClientboundEntityEventPacket(p_37964_, (byte)43));
               if (!raid.m_37757_()) {
                  p_37964_.m_36220_(Stats.f_12980_);
                  CriteriaTriggers.f_10558_.m_53645_(p_37964_);
               }
            }

            this.m_77762_();
            return raid;
         }
      }
   }

   private Raid m_37960_(ServerLevel p_37961_, BlockPos p_37962_) {
      Raid raid = p_37961_.m_8832_(p_37962_);
      return raid != null ? raid : new Raid(this.m_37977_(), p_37961_, p_37962_);
   }

   public static Raids m_150235_(ServerLevel p_150236_, CompoundTag p_150237_) {
      Raids raids = new Raids(p_150236_);
      raids.f_37953_ = p_150237_.m_128451_("NextAvailableID");
      raids.f_37954_ = p_150237_.m_128451_("Tick");
      ListTag listtag = p_150237_.m_128437_("Raids", 10);

      for(int i = 0; i < listtag.size(); ++i) {
         CompoundTag compoundtag = listtag.m_128728_(i);
         Raid raid = new Raid(p_150236_, compoundtag);
         raids.f_37951_.put(raid.m_37781_(), raid);
      }

      return raids;
   }

   public CompoundTag m_7176_(CompoundTag p_37976_) {
      p_37976_.m_128405_("NextAvailableID", this.f_37953_);
      p_37976_.m_128405_("Tick", this.f_37954_);
      ListTag listtag = new ListTag();

      for(Raid raid : this.f_37951_.values()) {
         CompoundTag compoundtag = new CompoundTag();
         raid.m_37747_(compoundtag);
         listtag.add(compoundtag);
      }

      p_37976_.m_128365_("Raids", listtag);
      return p_37976_;
   }

   public static String m_37968_(DimensionType p_37969_) {
      return "raids" + p_37969_.m_63899_();
   }

   private int m_37977_() {
      return ++this.f_37953_;
   }

   @Nullable
   public Raid m_37970_(BlockPos p_37971_, int p_37972_) {
      Raid raid = null;
      double d0 = (double)p_37972_;

      for(Raid raid1 : this.f_37951_.values()) {
         double d1 = raid1.m_37780_().m_123331_(p_37971_);
         if (raid1.m_37782_() && d1 < d0) {
            raid = raid1;
            d0 = d1;
         }
      }

      return raid;
   }
}