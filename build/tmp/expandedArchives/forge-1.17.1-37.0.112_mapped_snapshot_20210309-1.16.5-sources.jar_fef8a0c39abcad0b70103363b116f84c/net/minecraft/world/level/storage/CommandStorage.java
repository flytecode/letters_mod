package net.minecraft.world.level.storage;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.saveddata.SavedData;

public class CommandStorage {
   private static final String f_164834_ = "command_storage_";
   private final Map<String, CommandStorage.Container> f_78032_ = Maps.newHashMap();
   private final DimensionDataStorage f_78033_;

   public CommandStorage(DimensionDataStorage p_78035_) {
      this.f_78033_ = p_78035_;
   }

   private CommandStorage.Container m_164835_(String p_164836_) {
      CommandStorage.Container commandstorage$container = new CommandStorage.Container();
      this.f_78032_.put(p_164836_, commandstorage$container);
      return commandstorage$container;
   }

   public CompoundTag m_78044_(ResourceLocation p_78045_) {
      String s = p_78045_.m_135827_();
      CommandStorage.Container commandstorage$container = this.f_78033_.m_164858_((p_164844_) -> {
         return this.m_164835_(s).m_164849_(p_164844_);
      }, m_78037_(s));
      return commandstorage$container != null ? commandstorage$container.m_78058_(p_78045_.m_135815_()) : new CompoundTag();
   }

   public void m_78046_(ResourceLocation p_78047_, CompoundTag p_78048_) {
      String s = p_78047_.m_135827_();
      this.f_78033_.m_164861_((p_164839_) -> {
         return this.m_164835_(s).m_164849_(p_164839_);
      }, () -> {
         return this.m_164835_(s);
      }, m_78037_(s)).m_78063_(p_78047_.m_135815_(), p_78048_);
   }

   public Stream<ResourceLocation> m_78036_() {
      return this.f_78032_.entrySet().stream().flatMap((p_164841_) -> {
         return p_164841_.getValue().m_78072_(p_164841_.getKey());
      });
   }

   private static String m_78037_(String p_78038_) {
      return "command_storage_" + p_78038_;
   }

   static class Container extends SavedData {
      private static final String f_164847_ = "contents";
      private final Map<String, CompoundTag> f_78055_ = Maps.newHashMap();

      CommandStorage.Container m_164849_(CompoundTag p_164850_) {
         CompoundTag compoundtag = p_164850_.m_128469_("contents");

         for(String s : compoundtag.m_128431_()) {
            this.f_78055_.put(s, compoundtag.m_128469_(s));
         }

         return this;
      }

      public CompoundTag m_7176_(CompoundTag p_78075_) {
         CompoundTag compoundtag = new CompoundTag();
         this.f_78055_.forEach((p_78070_, p_78071_) -> {
            compoundtag.m_128365_(p_78070_, p_78071_.m_6426_());
         });
         p_78075_.m_128365_("contents", compoundtag);
         return p_78075_;
      }

      public CompoundTag m_78058_(String p_78059_) {
         CompoundTag compoundtag = this.f_78055_.get(p_78059_);
         return compoundtag != null ? compoundtag : new CompoundTag();
      }

      public void m_78063_(String p_78064_, CompoundTag p_78065_) {
         if (p_78065_.m_128456_()) {
            this.f_78055_.remove(p_78064_);
         } else {
            this.f_78055_.put(p_78064_, p_78065_);
         }

         this.m_77762_();
      }

      public Stream<ResourceLocation> m_78072_(String p_78073_) {
         return this.f_78055_.keySet().stream().map((p_78062_) -> {
            return new ResourceLocation(p_78073_, p_78062_);
         });
      }
   }
}