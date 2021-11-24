package net.minecraft.server.bossevents;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;

public class CustomBossEvent extends ServerBossEvent {
   private final ResourceLocation f_136256_;
   private final Set<UUID> f_136257_ = Sets.newHashSet();
   private int f_136258_;
   private int f_136259_ = 100;

   public CustomBossEvent(ResourceLocation p_136261_, Component p_136262_) {
      super(p_136262_, BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.PROGRESS);
      this.f_136256_ = p_136261_;
      this.m_142711_(0.0F);
   }

   public ResourceLocation m_136263_() {
      return this.f_136256_;
   }

   public void m_6543_(ServerPlayer p_136267_) {
      super.m_6543_(p_136267_);
      this.f_136257_.add(p_136267_.m_142081_());
   }

   public void m_136270_(UUID p_136271_) {
      this.f_136257_.add(p_136271_);
   }

   public void m_6539_(ServerPlayer p_136281_) {
      super.m_6539_(p_136281_);
      this.f_136257_.remove(p_136281_.m_142081_());
   }

   public void m_7706_() {
      super.m_7706_();
      this.f_136257_.clear();
   }

   public int m_136282_() {
      return this.f_136258_;
   }

   public int m_136285_() {
      return this.f_136259_;
   }

   public void m_136264_(int p_136265_) {
      this.f_136258_ = p_136265_;
      this.m_142711_(Mth.m_14036_((float)p_136265_ / (float)this.f_136259_, 0.0F, 1.0F));
   }

   public void m_136278_(int p_136279_) {
      this.f_136259_ = p_136279_;
      this.m_142711_(Mth.m_14036_((float)this.f_136258_ / (float)p_136279_, 0.0F, 1.0F));
   }

   public final Component m_136288_() {
      return ComponentUtils.m_130748_(this.m_18861_()).m_130938_((p_136276_) -> {
         return p_136276_.m_131140_(this.m_18862_().m_18883_()).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, new TextComponent(this.m_136263_().toString()))).m_131138_(this.m_136263_().toString());
      });
   }

   public boolean m_136268_(Collection<ServerPlayer> p_136269_) {
      Set<UUID> set = Sets.newHashSet();
      Set<ServerPlayer> set1 = Sets.newHashSet();

      for(UUID uuid : this.f_136257_) {
         boolean flag = false;

         for(ServerPlayer serverplayer : p_136269_) {
            if (serverplayer.m_142081_().equals(uuid)) {
               flag = true;
               break;
            }
         }

         if (!flag) {
            set.add(uuid);
         }
      }

      for(ServerPlayer serverplayer1 : p_136269_) {
         boolean flag1 = false;

         for(UUID uuid2 : this.f_136257_) {
            if (serverplayer1.m_142081_().equals(uuid2)) {
               flag1 = true;
               break;
            }
         }

         if (!flag1) {
            set1.add(serverplayer1);
         }
      }

      for(UUID uuid1 : set) {
         for(ServerPlayer serverplayer3 : this.m_8324_()) {
            if (serverplayer3.m_142081_().equals(uuid1)) {
               this.m_6539_(serverplayer3);
               break;
            }
         }

         this.f_136257_.remove(uuid1);
      }

      for(ServerPlayer serverplayer2 : set1) {
         this.m_6543_(serverplayer2);
      }

      return !set.isEmpty() || !set1.isEmpty();
   }

   public CompoundTag m_136289_() {
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128359_("Name", Component.Serializer.m_130703_(this.f_18840_));
      compoundtag.m_128379_("Visible", this.m_8323_());
      compoundtag.m_128405_("Value", this.f_136258_);
      compoundtag.m_128405_("Max", this.f_136259_);
      compoundtag.m_128359_("Color", this.m_18862_().m_18886_());
      compoundtag.m_128359_("Overlay", this.m_18863_().m_18902_());
      compoundtag.m_128379_("DarkenScreen", this.m_18864_());
      compoundtag.m_128379_("PlayBossMusic", this.m_18865_());
      compoundtag.m_128379_("CreateWorldFog", this.m_18866_());
      ListTag listtag = new ListTag();

      for(UUID uuid : this.f_136257_) {
         listtag.add(NbtUtils.m_129226_(uuid));
      }

      compoundtag.m_128365_("Players", listtag);
      return compoundtag;
   }

   public static CustomBossEvent m_136272_(CompoundTag p_136273_, ResourceLocation p_136274_) {
      CustomBossEvent custombossevent = new CustomBossEvent(p_136274_, Component.Serializer.m_130701_(p_136273_.m_128461_("Name")));
      custombossevent.m_8321_(p_136273_.m_128471_("Visible"));
      custombossevent.m_136264_(p_136273_.m_128451_("Value"));
      custombossevent.m_136278_(p_136273_.m_128451_("Max"));
      custombossevent.m_6451_(BossEvent.BossBarColor.m_18884_(p_136273_.m_128461_("Color")));
      custombossevent.m_5648_(BossEvent.BossBarOverlay.m_18903_(p_136273_.m_128461_("Overlay")));
      custombossevent.m_7003_(p_136273_.m_128471_("DarkenScreen"));
      custombossevent.m_7005_(p_136273_.m_128471_("PlayBossMusic"));
      custombossevent.m_7006_(p_136273_.m_128471_("CreateWorldFog"));
      ListTag listtag = p_136273_.m_128437_("Players", 11);

      for(int i = 0; i < listtag.size(); ++i) {
         custombossevent.m_136270_(NbtUtils.m_129233_(listtag.get(i)));
      }

      return custombossevent;
   }

   public void m_136283_(ServerPlayer p_136284_) {
      if (this.f_136257_.contains(p_136284_.m_142081_())) {
         this.m_6543_(p_136284_);
      }

   }

   public void m_136286_(ServerPlayer p_136287_) {
      super.m_6539_(p_136287_);
   }
}