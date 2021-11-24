package net.minecraft.world.level.saveddata.maps;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.saveddata.SavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapItemSavedData extends SavedData {
   private static final Logger f_77895_ = LogManager.getLogger();
   private static final int f_164765_ = 128;
   private static final int f_164766_ = 64;
   public static final int f_164764_ = 4;
   public static final int f_181307_ = 256;
   public final int f_77885_;
   public final int f_77886_;
   public final ResourceKey<Level> f_77887_;
   private final boolean f_77888_;
   private final boolean f_77889_;
   public final byte f_77890_;
   public byte[] f_77891_ = new byte[16384];
   public final boolean f_77892_;
   private final List<MapItemSavedData.HoldingPlayer> f_77893_ = Lists.newArrayList();
   private final Map<Player, MapItemSavedData.HoldingPlayer> f_77896_ = Maps.newHashMap();
   private final Map<String, MapBanner> f_77897_ = Maps.newHashMap();
   final Map<String, MapDecoration> f_77894_ = Maps.newLinkedHashMap();
   private final Map<String, MapFrame> f_77898_ = Maps.newHashMap();
   private int f_181308_;

   private MapItemSavedData(int p_164768_, int p_164769_, byte p_164770_, boolean p_164771_, boolean p_164772_, boolean p_164773_, ResourceKey<Level> p_164774_) {
      this.f_77890_ = p_164770_;
      this.f_77885_ = p_164768_;
      this.f_77886_ = p_164769_;
      this.f_77887_ = p_164774_;
      this.f_77888_ = p_164771_;
      this.f_77889_ = p_164772_;
      this.f_77892_ = p_164773_;
      this.m_77762_();
   }

   public static MapItemSavedData m_164780_(double p_164781_, double p_164782_, byte p_164783_, boolean p_164784_, boolean p_164785_, ResourceKey<Level> p_164786_) {
      int i = 128 * (1 << p_164783_);
      int j = Mth.m_14107_((p_164781_ + 64.0D) / (double)i);
      int k = Mth.m_14107_((p_164782_ + 64.0D) / (double)i);
      int l = j * i + i / 2 - 64;
      int i1 = k * i + i / 2 - 64;
      return new MapItemSavedData(l, i1, p_164783_, p_164784_, p_164785_, false, p_164786_);
   }

   public static MapItemSavedData m_164776_(byte p_164777_, boolean p_164778_, ResourceKey<Level> p_164779_) {
      return new MapItemSavedData(0, 0, p_164777_, false, false, p_164778_, p_164779_);
   }

   public static MapItemSavedData m_164807_(CompoundTag p_164808_) {
      ResourceKey<Level> resourcekey = DimensionType.m_63911_(new Dynamic<>(NbtOps.f_128958_, p_164808_.m_128423_("dimension"))).resultOrPartial(f_77895_::error).orElseThrow(() -> {
         return new IllegalArgumentException("Invalid map dimension: " + p_164808_.m_128423_("dimension"));
      });
      int i = p_164808_.m_128451_("xCenter");
      int j = p_164808_.m_128451_("zCenter");
      byte b0 = (byte)Mth.m_14045_(p_164808_.m_128445_("scale"), 0, 4);
      boolean flag = !p_164808_.m_128425_("trackingPosition", 1) || p_164808_.m_128471_("trackingPosition");
      boolean flag1 = p_164808_.m_128471_("unlimitedTracking");
      boolean flag2 = p_164808_.m_128471_("locked");
      MapItemSavedData mapitemsaveddata = new MapItemSavedData(i, j, b0, flag, flag1, flag2, resourcekey);
      byte[] abyte = p_164808_.m_128463_("colors");
      if (abyte.length == 16384) {
         mapitemsaveddata.f_77891_ = abyte;
      }

      ListTag listtag = p_164808_.m_128437_("banners", 10);

      for(int k = 0; k < listtag.size(); ++k) {
         MapBanner mapbanner = MapBanner.m_77777_(listtag.m_128728_(k));
         mapitemsaveddata.f_77897_.put(mapbanner.m_77787_(), mapbanner);
         mapitemsaveddata.m_77937_(mapbanner.m_77782_(), (LevelAccessor)null, mapbanner.m_77787_(), (double)mapbanner.m_77773_().m_123341_(), (double)mapbanner.m_77773_().m_123343_(), 180.0D, mapbanner.m_77783_());
      }

      ListTag listtag1 = p_164808_.m_128437_("frames", 10);

      for(int l = 0; l < listtag1.size(); ++l) {
         MapFrame mapframe = MapFrame.m_77872_(listtag1.m_128728_(l));
         mapitemsaveddata.f_77898_.put(mapframe.m_77877_(), mapframe);
         mapitemsaveddata.m_77937_(MapDecoration.Type.FRAME, (LevelAccessor)null, "frame-" + mapframe.m_77876_(), (double)mapframe.m_77874_().m_123341_(), (double)mapframe.m_77874_().m_123343_(), (double)mapframe.m_77875_(), (Component)null);
      }

      return mapitemsaveddata;
   }

   public CompoundTag m_7176_(CompoundTag p_77956_) {
      ResourceLocation.f_135803_.encodeStart(NbtOps.f_128958_, this.f_77887_.m_135782_()).resultOrPartial(f_77895_::error).ifPresent((p_77954_) -> {
         p_77956_.m_128365_("dimension", p_77954_);
      });
      p_77956_.m_128405_("xCenter", this.f_77885_);
      p_77956_.m_128405_("zCenter", this.f_77886_);
      p_77956_.m_128344_("scale", this.f_77890_);
      p_77956_.m_128382_("colors", this.f_77891_);
      p_77956_.m_128379_("trackingPosition", this.f_77888_);
      p_77956_.m_128379_("unlimitedTracking", this.f_77889_);
      p_77956_.m_128379_("locked", this.f_77892_);
      ListTag listtag = new ListTag();

      for(MapBanner mapbanner : this.f_77897_.values()) {
         listtag.add(mapbanner.m_77784_());
      }

      p_77956_.m_128365_("banners", listtag);
      ListTag listtag1 = new ListTag();

      for(MapFrame mapframe : this.f_77898_.values()) {
         listtag1.add(mapframe.m_77869_());
      }

      p_77956_.m_128365_("frames", listtag1);
      return p_77956_;
   }

   public MapItemSavedData m_164775_() {
      MapItemSavedData mapitemsaveddata = new MapItemSavedData(this.f_77885_, this.f_77886_, this.f_77890_, this.f_77888_, this.f_77889_, true, this.f_77887_);
      mapitemsaveddata.f_77897_.putAll(this.f_77897_);
      mapitemsaveddata.f_77894_.putAll(this.f_77894_);
      mapitemsaveddata.f_181308_ = this.f_181308_;
      System.arraycopy(this.f_77891_, 0, mapitemsaveddata.f_77891_, 0, this.f_77891_.length);
      mapitemsaveddata.m_77762_();
      return mapitemsaveddata;
   }

   public MapItemSavedData m_164787_(int p_164788_) {
      return m_164780_((double)this.f_77885_, (double)this.f_77886_, (byte)Mth.m_14045_(this.f_77890_ + p_164788_, 0, 4), this.f_77888_, this.f_77889_, this.f_77887_);
   }

   public void m_77918_(Player p_77919_, ItemStack p_77920_) {
      if (!this.f_77896_.containsKey(p_77919_)) {
         MapItemSavedData.HoldingPlayer mapitemsaveddata$holdingplayer = new MapItemSavedData.HoldingPlayer(p_77919_);
         this.f_77896_.put(p_77919_, mapitemsaveddata$holdingplayer);
         this.f_77893_.add(mapitemsaveddata$holdingplayer);
      }

      if (!p_77919_.m_150109_().m_36063_(p_77920_)) {
         this.m_164799_(p_77919_.m_7755_().getString());
      }

      for(int i = 0; i < this.f_77893_.size(); ++i) {
         MapItemSavedData.HoldingPlayer mapitemsaveddata$holdingplayer1 = this.f_77893_.get(i);
         String s = mapitemsaveddata$holdingplayer1.f_77959_.m_7755_().getString();
         if (!mapitemsaveddata$holdingplayer1.f_77959_.m_146910_() && (mapitemsaveddata$holdingplayer1.f_77959_.m_150109_().m_36063_(p_77920_) || p_77920_.m_41794_())) {
            if (!p_77920_.m_41794_() && mapitemsaveddata$holdingplayer1.f_77959_.f_19853_.m_46472_() == this.f_77887_ && this.f_77888_) {
               this.m_77937_(MapDecoration.Type.PLAYER, mapitemsaveddata$holdingplayer1.f_77959_.f_19853_, s, mapitemsaveddata$holdingplayer1.f_77959_.m_20185_(), mapitemsaveddata$holdingplayer1.f_77959_.m_20189_(), (double)mapitemsaveddata$holdingplayer1.f_77959_.m_146908_(), (Component)null);
            }
         } else {
            this.f_77896_.remove(mapitemsaveddata$holdingplayer1.f_77959_);
            this.f_77893_.remove(mapitemsaveddata$holdingplayer1);
            this.m_164799_(s);
         }
      }

      if (p_77920_.m_41794_() && this.f_77888_) {
         ItemFrame itemframe = p_77920_.m_41795_();
         BlockPos blockpos = itemframe.m_31748_();
         MapFrame mapframe1 = this.f_77898_.get(MapFrame.m_77870_(blockpos));
         if (mapframe1 != null && itemframe.m_142049_() != mapframe1.m_77876_() && this.f_77898_.containsKey(mapframe1.m_77877_())) {
            this.m_164799_("frame-" + mapframe1.m_77876_());
         }

         MapFrame mapframe = new MapFrame(blockpos, itemframe.m_6350_().m_122416_() * 90, itemframe.m_142049_());
         this.m_77937_(MapDecoration.Type.FRAME, p_77919_.f_19853_, "frame-" + itemframe.m_142049_(), (double)blockpos.m_123341_(), (double)blockpos.m_123343_(), (double)(itemframe.m_6350_().m_122416_() * 90), (Component)null);
         this.f_77898_.put(mapframe.m_77877_(), mapframe);
      }

      CompoundTag compoundtag = p_77920_.m_41783_();
      if (compoundtag != null && compoundtag.m_128425_("Decorations", 9)) {
         ListTag listtag = compoundtag.m_128437_("Decorations", 10);

         for(int j = 0; j < listtag.size(); ++j) {
            CompoundTag compoundtag1 = listtag.m_128728_(j);
            if (!this.f_77894_.containsKey(compoundtag1.m_128461_("id"))) {
               this.m_77937_(MapDecoration.Type.m_77854_(compoundtag1.m_128445_("type")), p_77919_.f_19853_, compoundtag1.m_128461_("id"), compoundtag1.m_128459_("x"), compoundtag1.m_128459_("z"), compoundtag1.m_128459_("rot"), (Component)null);
            }
         }
      }

   }

   private void m_164799_(String p_164800_) {
      MapDecoration mapdecoration = this.f_77894_.remove(p_164800_);
      if (mapdecoration != null && mapdecoration.m_77803_().m_181306_()) {
         --this.f_181308_;
      }

      this.m_164812_();
   }

   public static void m_77925_(ItemStack p_77926_, BlockPos p_77927_, String p_77928_, MapDecoration.Type p_77929_) {
      ListTag listtag;
      if (p_77926_.m_41782_() && p_77926_.m_41783_().m_128425_("Decorations", 9)) {
         listtag = p_77926_.m_41783_().m_128437_("Decorations", 10);
      } else {
         listtag = new ListTag();
         p_77926_.m_41700_("Decorations", listtag);
      }

      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128344_("type", p_77929_.m_77853_());
      compoundtag.m_128359_("id", p_77928_);
      compoundtag.m_128347_("x", (double)p_77927_.m_123341_());
      compoundtag.m_128347_("z", (double)p_77927_.m_123343_());
      compoundtag.m_128347_("rot", 180.0D);
      listtag.add(compoundtag);
      if (p_77929_.m_77857_()) {
         CompoundTag compoundtag1 = p_77926_.m_41698_("display");
         compoundtag1.m_128405_("MapColor", p_77929_.m_77858_());
      }

   }

   private void m_77937_(MapDecoration.Type p_77938_, @Nullable LevelAccessor p_77939_, String p_77940_, double p_77941_, double p_77942_, double p_77943_, @Nullable Component p_77944_) {
      int i = 1 << this.f_77890_;
      float f = (float)(p_77941_ - (double)this.f_77885_) / (float)i;
      float f1 = (float)(p_77942_ - (double)this.f_77886_) / (float)i;
      byte b0 = (byte)((int)((double)(f * 2.0F) + 0.5D));
      byte b1 = (byte)((int)((double)(f1 * 2.0F) + 0.5D));
      int j = 63;
      byte b2;
      if (f >= -63.0F && f1 >= -63.0F && f <= 63.0F && f1 <= 63.0F) {
         p_77943_ = p_77943_ + (p_77943_ < 0.0D ? -8.0D : 8.0D);
         b2 = (byte)((int)(p_77943_ * 16.0D / 360.0D));
         if (this.f_77887_ == Level.f_46429_ && p_77939_ != null) {
            int l = (int)(p_77939_.m_6106_().m_6792_() / 10L);
            b2 = (byte)(l * l * 34187121 + l * 121 >> 15 & 15);
         }
      } else {
         if (p_77938_ != MapDecoration.Type.PLAYER) {
            this.m_164799_(p_77940_);
            return;
         }

         int k = 320;
         if (Math.abs(f) < 320.0F && Math.abs(f1) < 320.0F) {
            p_77938_ = MapDecoration.Type.PLAYER_OFF_MAP;
         } else {
            if (!this.f_77889_) {
               this.m_164799_(p_77940_);
               return;
            }

            p_77938_ = MapDecoration.Type.PLAYER_OFF_LIMITS;
         }

         b2 = 0;
         if (f <= -63.0F) {
            b0 = -128;
         }

         if (f1 <= -63.0F) {
            b1 = -128;
         }

         if (f >= 63.0F) {
            b0 = 127;
         }

         if (f1 >= 63.0F) {
            b1 = 127;
         }
      }

      MapDecoration mapdecoration1 = new MapDecoration(p_77938_, b0, b1, b2, p_77944_);
      MapDecoration mapdecoration = this.f_77894_.put(p_77940_, mapdecoration1);
      if (!mapdecoration1.equals(mapdecoration)) {
         if (mapdecoration != null && mapdecoration.m_77803_().m_181306_()) {
            --this.f_181308_;
         }

         if (p_77938_.m_181306_()) {
            ++this.f_181308_;
         }

         this.m_164812_();
      }

   }

   @Nullable
   public Packet<?> m_164796_(int p_164797_, Player p_164798_) {
      MapItemSavedData.HoldingPlayer mapitemsaveddata$holdingplayer = this.f_77896_.get(p_164798_);
      return mapitemsaveddata$holdingplayer == null ? null : mapitemsaveddata$holdingplayer.m_164815_(p_164797_);
   }

   private void m_164789_(int p_164790_, int p_164791_) {
      this.m_77762_();

      for(MapItemSavedData.HoldingPlayer mapitemsaveddata$holdingplayer : this.f_77893_) {
         mapitemsaveddata$holdingplayer.m_164817_(p_164790_, p_164791_);
      }

   }

   private void m_164812_() {
      this.m_77762_();
      this.f_77893_.forEach(MapItemSavedData.HoldingPlayer::m_164820_);
   }

   public MapItemSavedData.HoldingPlayer m_77916_(Player p_77917_) {
      MapItemSavedData.HoldingPlayer mapitemsaveddata$holdingplayer = this.f_77896_.get(p_77917_);
      if (mapitemsaveddata$holdingplayer == null) {
         mapitemsaveddata$holdingplayer = new MapItemSavedData.HoldingPlayer(p_77917_);
         this.f_77896_.put(p_77917_, mapitemsaveddata$holdingplayer);
         this.f_77893_.add(mapitemsaveddata$holdingplayer);
      }

      return mapitemsaveddata$holdingplayer;
   }

   public boolean m_77934_(LevelAccessor p_77935_, BlockPos p_77936_) {
      double d0 = (double)p_77936_.m_123341_() + 0.5D;
      double d1 = (double)p_77936_.m_123343_() + 0.5D;
      int i = 1 << this.f_77890_;
      double d2 = (d0 - (double)this.f_77885_) / (double)i;
      double d3 = (d1 - (double)this.f_77886_) / (double)i;
      int j = 63;
      if (d2 >= -63.0D && d3 >= -63.0D && d2 <= 63.0D && d3 <= 63.0D) {
         MapBanner mapbanner = MapBanner.m_77774_(p_77935_, p_77936_);
         if (mapbanner == null) {
            return false;
         }

         if (this.f_77897_.remove(mapbanner.m_77787_(), mapbanner)) {
            this.m_164799_(mapbanner.m_77787_());
            return true;
         }

         if (!this.m_181312_(256)) {
            this.f_77897_.put(mapbanner.m_77787_(), mapbanner);
            this.m_77937_(mapbanner.m_77782_(), p_77935_, mapbanner.m_77787_(), d0, d1, 180.0D, mapbanner.m_77783_());
            return true;
         }
      }

      return false;
   }

   public void m_77930_(BlockGetter p_77931_, int p_77932_, int p_77933_) {
      Iterator<MapBanner> iterator = this.f_77897_.values().iterator();

      while(iterator.hasNext()) {
         MapBanner mapbanner = iterator.next();
         if (mapbanner.m_77773_().m_123341_() == p_77932_ && mapbanner.m_77773_().m_123343_() == p_77933_) {
            MapBanner mapbanner1 = MapBanner.m_77774_(p_77931_, mapbanner.m_77773_());
            if (!mapbanner.equals(mapbanner1)) {
               iterator.remove();
               this.m_164799_(mapbanner.m_77787_());
            }
         }
      }

   }

   public Collection<MapBanner> m_164809_() {
      return this.f_77897_.values();
   }

   public void m_77947_(BlockPos p_77948_, int p_77949_) {
      this.m_164799_("frame-" + p_77949_);
      this.f_77898_.remove(MapFrame.m_77870_(p_77948_));
   }

   public boolean m_164792_(int p_164793_, int p_164794_, byte p_164795_) {
      byte b0 = this.f_77891_[p_164793_ + p_164794_ * 128];
      if (b0 != p_164795_) {
         this.m_164803_(p_164793_, p_164794_, p_164795_);
         return true;
      } else {
         return false;
      }
   }

   public void m_164803_(int p_164804_, int p_164805_, byte p_164806_) {
      this.f_77891_[p_164804_ + p_164805_ * 128] = p_164806_;
      this.m_164789_(p_164804_, p_164805_);
   }

   public boolean m_164810_() {
      for(MapDecoration mapdecoration : this.f_77894_.values()) {
         if (mapdecoration.m_77803_() == MapDecoration.Type.MANSION || mapdecoration.m_77803_() == MapDecoration.Type.MONUMENT) {
            return true;
         }
      }

      return false;
   }

   public void m_164801_(List<MapDecoration> p_164802_) {
      this.f_77894_.clear();
      this.f_181308_ = 0;

      for(int i = 0; i < p_164802_.size(); ++i) {
         MapDecoration mapdecoration = p_164802_.get(i);
         this.f_77894_.put("icon-" + i, mapdecoration);
         if (mapdecoration.m_77803_().m_181306_()) {
            ++this.f_181308_;
         }
      }

   }

   public Iterable<MapDecoration> m_164811_() {
      return this.f_77894_.values();
   }

   public boolean m_181312_(int p_181313_) {
      return this.f_181308_ >= p_181313_;
   }

   public class HoldingPlayer {
      public final Player f_77959_;
      private boolean f_77962_ = true;
      private int f_77963_;
      private int f_77964_;
      private int f_77965_ = 127;
      private int f_77966_ = 127;
      private boolean f_164813_ = true;
      private int f_77967_;
      public int f_77960_;

      HoldingPlayer(Player p_77970_) {
         this.f_77959_ = p_77970_;
      }

      private MapItemSavedData.MapPatch m_164814_() {
         int i = this.f_77963_;
         int j = this.f_77964_;
         int k = this.f_77965_ + 1 - this.f_77963_;
         int l = this.f_77966_ + 1 - this.f_77964_;
         byte[] abyte = new byte[k * l];

         for(int i1 = 0; i1 < k; ++i1) {
            for(int j1 = 0; j1 < l; ++j1) {
               abyte[i1 + j1 * k] = MapItemSavedData.this.f_77891_[i + i1 + (j + j1) * 128];
            }
         }

         return new MapItemSavedData.MapPatch(i, j, k, l, abyte);
      }

      @Nullable
      Packet<?> m_164815_(int p_164816_) {
         MapItemSavedData.MapPatch mapitemsaveddata$mappatch;
         if (this.f_77962_) {
            this.f_77962_ = false;
            mapitemsaveddata$mappatch = this.m_164814_();
         } else {
            mapitemsaveddata$mappatch = null;
         }

         Collection<MapDecoration> collection;
         if (this.f_164813_ && this.f_77967_++ % 5 == 0) {
            this.f_164813_ = false;
            collection = MapItemSavedData.this.f_77894_.values();
         } else {
            collection = null;
         }

         return collection == null && mapitemsaveddata$mappatch == null ? null : new ClientboundMapItemDataPacket(p_164816_, MapItemSavedData.this.f_77890_, MapItemSavedData.this.f_77892_, collection, mapitemsaveddata$mappatch);
      }

      void m_164817_(int p_164818_, int p_164819_) {
         if (this.f_77962_) {
            this.f_77963_ = Math.min(this.f_77963_, p_164818_);
            this.f_77964_ = Math.min(this.f_77964_, p_164819_);
            this.f_77965_ = Math.max(this.f_77965_, p_164818_);
            this.f_77966_ = Math.max(this.f_77966_, p_164819_);
         } else {
            this.f_77962_ = true;
            this.f_77963_ = p_164818_;
            this.f_77964_ = p_164819_;
            this.f_77965_ = p_164818_;
            this.f_77966_ = p_164819_;
         }

      }

      private void m_164820_() {
         this.f_164813_ = true;
      }
   }

   public static class MapPatch {
      public final int f_164821_;
      public final int f_164822_;
      public final int f_164823_;
      public final int f_164824_;
      public final byte[] f_164825_;

      public MapPatch(int p_164827_, int p_164828_, int p_164829_, int p_164830_, byte[] p_164831_) {
         this.f_164821_ = p_164827_;
         this.f_164822_ = p_164828_;
         this.f_164823_ = p_164829_;
         this.f_164824_ = p_164830_;
         this.f_164825_ = p_164831_;
      }

      public void m_164832_(MapItemSavedData p_164833_) {
         for(int i = 0; i < this.f_164823_; ++i) {
            for(int j = 0; j < this.f_164824_; ++j) {
               p_164833_.m_164803_(this.f_164821_ + i, this.f_164822_ + j, this.f_164825_[i + j * this.f_164823_]);
            }
         }

      }
   }
}