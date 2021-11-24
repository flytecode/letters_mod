package net.minecraft.client.particle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParticleDescription {
   @Nullable
   private final List<ResourceLocation> f_107279_;

   private ParticleDescription(@Nullable List<ResourceLocation> p_107281_) {
      this.f_107279_ = p_107281_;
   }

   @Nullable
   public List<ResourceLocation> m_107282_() {
      return this.f_107279_;
   }

   public static ParticleDescription m_107285_(JsonObject p_107286_) {
      JsonArray jsonarray = GsonHelper.m_13832_(p_107286_, "textures", (JsonArray)null);
      List<ResourceLocation> list;
      if (jsonarray != null) {
         list = Streams.stream(jsonarray).map((p_107284_) -> {
            return GsonHelper.m_13805_(p_107284_, "texture");
         }).map(ResourceLocation::new).collect(ImmutableList.toImmutableList());
      } else {
         list = null;
      }

      return new ParticleDescription(list);
   }
}