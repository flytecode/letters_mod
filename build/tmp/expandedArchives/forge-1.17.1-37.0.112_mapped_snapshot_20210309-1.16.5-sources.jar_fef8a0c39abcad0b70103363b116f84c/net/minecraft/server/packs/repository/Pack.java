package net.minecraft.server.packs.repository;

import com.mojang.brigadier.arguments.StringArgumentType;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Pack implements AutoCloseable {
   private static final Logger f_10399_ = LogManager.getLogger();
   private final String f_10401_;
   private final Supplier<PackResources> f_10402_;
   private final Component f_10403_;
   private final Component f_10404_;
   private final PackCompatibility f_10405_;
   private final Pack.Position f_10406_;
   private final boolean f_10407_;
   private final boolean f_10408_;
   private final boolean hidden; // Forge: Allow packs to be hidden from the UI entirely
   private final PackSource f_10409_;

   @Nullable
   public static Pack m_10430_(String p_10431_, boolean p_10432_, Supplier<PackResources> p_10433_, Pack.PackConstructor p_10434_, Pack.Position p_10435_, PackSource p_10436_) {
      try {
         PackResources packresources = p_10433_.get();

         Pack pack;
         label54: {
            try {
               PackMetadataSection packmetadatasection = packresources.m_5550_(PackMetadataSection.f_10366_);
               if (packmetadatasection != null) {
                  pack = p_10434_.create(p_10431_, new TextComponent(packresources.m_8017_()), p_10432_, p_10433_, packmetadatasection, p_10435_, p_10436_, packresources.isHidden());
                  break label54;
               }

               f_10399_.warn("Couldn't find pack meta for pack {}", (Object)p_10431_);
            } catch (Throwable throwable1) {
               if (packresources != null) {
                  try {
                     packresources.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (packresources != null) {
               packresources.close();
            }

            return null;
         }

         if (packresources != null) {
            packresources.close();
         }

         return pack;
      } catch (IOException ioexception) {
         f_10399_.warn("Couldn't get pack info for: {}", (Object)ioexception.toString());
         return null;
      }
   }

   @Deprecated
   public Pack(String p_10420_, boolean p_10421_, Supplier<PackResources> p_10422_, Component p_10423_, Component p_10424_, PackCompatibility p_10425_, Pack.Position p_10426_, boolean p_10427_, PackSource p_10428_) {
       this(p_10420_, p_10421_, p_10422_, p_10423_, p_10424_, p_10425_, p_10426_, p_10427_, p_10428_, false);
   }

   public Pack(String p_10420_, boolean p_10421_, Supplier<PackResources> p_10422_, Component p_10423_, Component p_10424_, PackCompatibility p_10425_, Pack.Position p_10426_, boolean p_10427_, PackSource p_10428_, boolean hidden) {
      this.f_10401_ = p_10420_;
      this.f_10402_ = p_10422_;
      this.f_10403_ = p_10423_;
      this.f_10404_ = p_10424_;
      this.f_10405_ = p_10425_;
      this.f_10407_ = p_10421_;
      this.f_10406_ = p_10426_;
      this.f_10408_ = p_10427_;
      this.f_10409_ = p_10428_;
      this.hidden = hidden;
   }

   @Deprecated
   public Pack(String p_143865_, Component p_143866_, boolean p_143867_, Supplier<PackResources> p_143868_, PackMetadataSection p_143869_, PackType p_143870_, Pack.Position p_143871_, PackSource p_143872_) {
      this(p_143865_, p_143867_, p_143868_, p_143866_, p_143869_.m_10373_(), PackCompatibility.m_143885_(p_143869_, p_143870_), p_143871_, false, p_143872_, false);
   }

   public Pack(String p_143865_, Component p_143866_, boolean p_143867_, Supplier<PackResources> p_143868_, PackMetadataSection p_143869_, PackType p_143870_, Pack.Position p_143871_, PackSource p_143872_, boolean hidden) {
      this(p_143865_, p_143867_, p_143868_, p_143866_, p_143869_.m_10373_(), PackCompatibility.m_143885_(p_143869_, p_143870_), p_143871_, false, p_143872_, hidden);
   }

   public Component m_10429_() {
      return this.f_10403_;
   }

   public Component m_10442_() {
      return this.f_10404_;
   }

   public Component m_10437_(boolean p_10438_) {
      return ComponentUtils.m_130748_(this.f_10409_.m_10540_(new TextComponent(this.f_10401_))).m_130938_((p_10441_) -> {
         return p_10441_.m_131140_(p_10438_ ? ChatFormatting.GREEN : ChatFormatting.RED).m_131138_(StringArgumentType.escapeIfRequired(this.f_10401_)).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, (new TextComponent("")).m_7220_(this.f_10403_).m_130946_("\n").m_7220_(this.f_10404_)));
      });
   }

   public PackCompatibility m_10443_() {
      return this.f_10405_;
   }

   public PackResources m_10445_() {
      return this.f_10402_.get();
   }

   public String m_10446_() {
      return this.f_10401_;
   }

   public boolean m_10449_() {
      return this.f_10407_;
   }

   public boolean m_10450_() {
      return this.f_10408_;
   }

   public Pack.Position m_10451_() {
      return this.f_10406_;
   }

   public PackSource m_10453_() {
      return this.f_10409_;
   }

   public boolean isHidden() { return hidden; }

   public boolean equals(Object p_10448_) {
      if (this == p_10448_) {
         return true;
      } else if (!(p_10448_ instanceof Pack)) {
         return false;
      } else {
         Pack pack = (Pack)p_10448_;
         return this.f_10401_.equals(pack.f_10401_);
      }
   }

   public int hashCode() {
      return this.f_10401_.hashCode();
   }

   public void close() {
   }

   @FunctionalInterface
   public interface PackConstructor {
      @Deprecated
      @Nullable
      default Pack m_143873_(String p_143874_, Component p_143875_, boolean p_143876_, Supplier<PackResources> p_143877_, PackMetadataSection p_143878_, Pack.Position p_143879_, PackSource p_143880_)
      {
         return create(p_143874_, p_143875_, p_143876_, p_143877_, p_143878_, p_143879_, p_143880_, false);
      }

      @Nullable
      Pack create(String p_143874_, Component p_143875_, boolean p_143876_, Supplier<PackResources> p_143877_, PackMetadataSection p_143878_, Pack.Position p_143879_, PackSource p_143880_, boolean hidden);
   }

   public static enum Position {
      TOP,
      BOTTOM;

      public <T> int m_10470_(List<T> p_10471_, T p_10472_, Function<T, Pack> p_10473_, boolean p_10474_) {
         Pack.Position pack$position = p_10474_ ? this.m_10469_() : this;
         if (pack$position == BOTTOM) {
            int j;
            for(j = 0; j < p_10471_.size(); ++j) {
               Pack pack1 = p_10473_.apply(p_10471_.get(j));
               if (!pack1.m_10450_() || pack1.m_10451_() != this) {
                  break;
               }
            }

            p_10471_.add(j, p_10472_);
            return j;
         } else {
            int i;
            for(i = p_10471_.size() - 1; i >= 0; --i) {
               Pack pack = p_10473_.apply(p_10471_.get(i));
               if (!pack.m_10450_() || pack.m_10451_() != this) {
                  break;
               }
            }

            p_10471_.add(i + 1, p_10472_);
            return i + 1;
         }
      }

      public Pack.Position m_10469_() {
         return this == TOP ? BOTTOM : TOP;
      }
   }
}
