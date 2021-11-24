package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.BufferBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.SortedMap;
import net.minecraft.Util;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderBuffers {
   private final ChunkBufferBuilderPack f_110092_ = new ChunkBufferBuilderPack();
   private final SortedMap<RenderType, BufferBuilder> f_110093_ = Util.m_137469_(new Object2ObjectLinkedOpenHashMap<>(), (p_110100_) -> {
      p_110100_.put(Sheets.m_110789_(), this.f_110092_.m_108839_(RenderType.m_110451_()));
      p_110100_.put(Sheets.m_110790_(), this.f_110092_.m_108839_(RenderType.m_110463_()));
      p_110100_.put(Sheets.m_110762_(), this.f_110092_.m_108839_(RenderType.m_110457_()));
      p_110100_.put(Sheets.m_110792_(), this.f_110092_.m_108839_(RenderType.m_110466_()));
      m_110101_(p_110100_, Sheets.m_110782_());
      m_110101_(p_110100_, Sheets.m_110785_());
      m_110101_(p_110100_, Sheets.m_110786_());
      m_110101_(p_110100_, Sheets.m_110787_());
      m_110101_(p_110100_, Sheets.m_110788_());
      m_110101_(p_110100_, RenderType.m_110472_());
      m_110101_(p_110100_, RenderType.m_110481_());
      m_110101_(p_110100_, RenderType.m_110484_());
      m_110101_(p_110100_, RenderType.m_110490_());
      m_110101_(p_110100_, RenderType.m_110493_());
      m_110101_(p_110100_, RenderType.m_110487_());
      m_110101_(p_110100_, RenderType.m_110496_());
      m_110101_(p_110100_, RenderType.m_110499_());
      m_110101_(p_110100_, RenderType.m_110478_());
      ModelBakery.f_119229_.forEach((p_173062_) -> {
         m_110101_(p_110100_, p_173062_);
      });
   });
   private final MultiBufferSource.BufferSource f_110094_ = MultiBufferSource.m_109900_(this.f_110093_, new BufferBuilder(256));
   private final MultiBufferSource.BufferSource f_110095_ = MultiBufferSource.m_109898_(new BufferBuilder(256));
   private final OutlineBufferSource f_110096_ = new OutlineBufferSource(this.f_110094_);

   private static void m_110101_(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> p_110102_, RenderType p_110103_) {
      p_110102_.put(p_110103_, new BufferBuilder(p_110103_.m_110507_()));
   }

   public ChunkBufferBuilderPack m_110098_() {
      return this.f_110092_;
   }

   public MultiBufferSource.BufferSource m_110104_() {
      return this.f_110094_;
   }

   public MultiBufferSource.BufferSource m_110108_() {
      return this.f_110095_;
   }

   public OutlineBufferSource m_110109_() {
      return this.f_110096_;
   }
}