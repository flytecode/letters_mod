package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockPredicate {
   public static final BlockPredicate f_17902_ = new BlockPredicate((Tag<Block>)null, (Set<Block>)null, StatePropertiesPredicate.f_67658_, NbtPredicate.f_57471_);
   @Nullable
   private final Tag<Block> f_17903_;
   @Nullable
   private final Set<Block> f_146710_;
   private final StatePropertiesPredicate f_17905_;
   private final NbtPredicate f_17906_;

   public BlockPredicate(@Nullable Tag<Block> p_146712_, @Nullable Set<Block> p_146713_, StatePropertiesPredicate p_146714_, NbtPredicate p_146715_) {
      this.f_17903_ = p_146712_;
      this.f_146710_ = p_146713_;
      this.f_17905_ = p_146714_;
      this.f_17906_ = p_146715_;
   }

   public boolean m_17914_(ServerLevel p_17915_, BlockPos p_17916_) {
      if (this == f_17902_) {
         return true;
      } else if (!p_17915_.m_46749_(p_17916_)) {
         return false;
      } else {
         BlockState blockstate = p_17915_.m_8055_(p_17916_);
         if (this.f_17903_ != null && !blockstate.m_60620_(this.f_17903_)) {
            return false;
         } else if (this.f_146710_ != null && !this.f_146710_.contains(blockstate.m_60734_())) {
            return false;
         } else if (!this.f_17905_.m_67667_(blockstate)) {
            return false;
         } else {
            if (this.f_17906_ != NbtPredicate.f_57471_) {
               BlockEntity blockentity = p_17915_.m_7702_(p_17916_);
               if (blockentity == null || !this.f_17906_.m_57483_(blockentity.m_6945_(new CompoundTag()))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static BlockPredicate m_17917_(@Nullable JsonElement p_17918_) {
      if (p_17918_ != null && !p_17918_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_17918_, "block");
         NbtPredicate nbtpredicate = NbtPredicate.m_57481_(jsonobject.get("nbt"));
         Set<Block> set = null;
         JsonArray jsonarray = GsonHelper.m_13832_(jsonobject, "blocks", (JsonArray)null);
         if (jsonarray != null) {
            ImmutableSet.Builder<Block> builder = ImmutableSet.builder();

            for(JsonElement jsonelement : jsonarray) {
               ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13805_(jsonelement, "block"));
               builder.add(Registry.f_122824_.m_6612_(resourcelocation).orElseThrow(() -> {
                  return new JsonSyntaxException("Unknown block id '" + resourcelocation + "'");
               }));
            }

            set = builder.build();
         }

         Tag<Block> tag = null;
         if (jsonobject.has("tag")) {
            ResourceLocation resourcelocation1 = new ResourceLocation(GsonHelper.m_13906_(jsonobject, "tag"));
            tag = SerializationTags.m_13199_().m_144458_(Registry.f_122901_, resourcelocation1, (p_146717_) -> {
               return new JsonSyntaxException("Unknown block tag '" + p_146717_ + "'");
            });
         }

         StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.m_67679_(jsonobject.get("state"));
         return new BlockPredicate(tag, set, statepropertiespredicate, nbtpredicate);
      } else {
         return f_17902_;
      }
   }

   public JsonElement m_17913_() {
      if (this == f_17902_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         if (this.f_146710_ != null) {
            JsonArray jsonarray = new JsonArray();

            for(Block block : this.f_146710_) {
               jsonarray.add(Registry.f_122824_.m_7981_(block).toString());
            }

            jsonobject.add("blocks", jsonarray);
         }

         if (this.f_17903_ != null) {
            jsonobject.addProperty("tag", SerializationTags.m_13199_().m_144454_(Registry.f_122901_, this.f_17903_, () -> {
               return new IllegalStateException("Unknown block tag");
            }).toString());
         }

         jsonobject.add("nbt", this.f_17906_.m_57476_());
         jsonobject.add("state", this.f_17905_.m_67666_());
         return jsonobject;
      }
   }

   public static class Builder {
      @Nullable
      private Set<Block> f_17920_;
      @Nullable
      private Tag<Block> f_146721_;
      private StatePropertiesPredicate f_17921_ = StatePropertiesPredicate.f_67658_;
      private NbtPredicate f_17922_ = NbtPredicate.f_57471_;

      private Builder() {
      }

      public static BlockPredicate.Builder m_17924_() {
         return new BlockPredicate.Builder();
      }

      public BlockPredicate.Builder m_146726_(Block... p_146727_) {
         this.f_17920_ = ImmutableSet.copyOf(p_146727_);
         return this;
      }

      public BlockPredicate.Builder m_146722_(Iterable<Block> p_146723_) {
         this.f_17920_ = ImmutableSet.copyOf(p_146723_);
         return this;
      }

      public BlockPredicate.Builder m_17925_(Tag<Block> p_17926_) {
         this.f_146721_ = p_17926_;
         return this;
      }

      public BlockPredicate.Builder m_146724_(CompoundTag p_146725_) {
         this.f_17922_ = new NbtPredicate(p_146725_);
         return this;
      }

      public BlockPredicate.Builder m_17929_(StatePropertiesPredicate p_17930_) {
         this.f_17921_ = p_17930_;
         return this;
      }

      public BlockPredicate m_17931_() {
         return new BlockPredicate(this.f_146721_, this.f_17920_, this.f_17921_, this.f_17922_);
      }
   }
}