package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class RenderType extends RenderStateShard {
   private static final int f_173153_ = 4;
   private static final int f_173154_ = 1048576;
   public static final int f_173148_ = 2097152;
   public static final int f_173149_ = 262144;
   public static final int f_173150_ = 131072;
   public static final int f_173151_ = 256;
   private static final RenderType f_110372_ = m_173215_("solid", DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 2097152, true, false, RenderType.CompositeState.m_110628_().m_110671_(f_110152_).m_173292_(f_173105_).m_173290_(f_110145_).m_110691_(true));
   private static final RenderType f_110373_ = m_173215_("cutout_mipped", DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 131072, true, false, RenderType.CompositeState.m_110628_().m_110671_(f_110152_).m_173292_(f_173106_).m_173290_(f_110145_).m_110691_(true));
   private static final RenderType f_110374_ = m_173215_("cutout", DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 131072, true, false, RenderType.CompositeState.m_110628_().m_110671_(f_110152_).m_173292_(f_173107_).m_173290_(f_110146_).m_110691_(true));
   private static final RenderType f_110375_ = m_173215_("translucent", DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 2097152, true, true, m_173207_(f_173108_));
   private static final RenderType f_110376_ = m_173215_("translucent_moving_block", DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 262144, false, true, m_110408_());
   private static final RenderType f_110377_ = m_173215_("translucent_no_crumbling", DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 262144, false, true, m_173207_(f_173110_));
   private static final Function<ResourceLocation, RenderType> f_173155_ = Util.m_143827_((p_173206_) -> {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(f_173111_).m_173290_(new RenderStateShard.TextureStateShard(p_173206_, false, false)).m_110685_(f_110134_).m_110661_(f_110110_).m_110671_(f_110152_).m_110677_(f_110154_).m_110669_(f_110119_).m_110691_(true);
      return m_173215_("armor_cutout_no_cull", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, true, false, rendertype$compositestate);
   });
   private static final Function<ResourceLocation, RenderType> f_173156_ = Util.m_143827_((p_173204_) -> {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(f_173112_).m_173290_(new RenderStateShard.TextureStateShard(p_173204_, false, false)).m_110685_(f_110134_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(true);
      return m_173215_("entity_solid", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, true, false, rendertype$compositestate);
   });
   private static final Function<ResourceLocation, RenderType> f_173157_ = Util.m_143827_((p_173202_) -> {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(f_173113_).m_173290_(new RenderStateShard.TextureStateShard(p_173202_, false, false)).m_110685_(f_110134_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(true);
      return m_173215_("entity_cutout", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, true, false, rendertype$compositestate);
   });
   private static final BiFunction<ResourceLocation, Boolean, RenderType> f_173160_ = Util.m_143821_((p_173233_, p_173234_) -> {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(f_173114_).m_173290_(new RenderStateShard.TextureStateShard(p_173233_, false, false)).m_110685_(f_110134_).m_110661_(f_110110_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(p_173234_);
      return m_173215_("entity_cutout_no_cull", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, true, false, rendertype$compositestate);
   });
   private static final BiFunction<ResourceLocation, Boolean, RenderType> f_173161_ = Util.m_143821_((p_173230_, p_173231_) -> {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(f_173063_).m_173290_(new RenderStateShard.TextureStateShard(p_173230_, false, false)).m_110685_(f_110134_).m_110661_(f_110110_).m_110671_(f_110152_).m_110677_(f_110154_).m_110669_(f_110119_).m_110691_(p_173231_);
      return m_173215_("entity_cutout_no_cull_z_offset", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, true, false, rendertype$compositestate);
   });
   private static final Function<ResourceLocation, RenderType> f_173162_ = Util.m_143827_((p_173200_) -> {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(f_173064_).m_173290_(new RenderStateShard.TextureStateShard(p_173200_, false, false)).m_110685_(f_110139_).m_110675_(f_110129_).m_110671_(f_110152_).m_110677_(f_110154_).m_110687_(RenderStateShard.f_110114_).m_110691_(true);
      return m_173215_("item_entity_translucent_cull", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, true, true, rendertype$compositestate);
   });
   private static final Function<ResourceLocation, RenderType> f_173163_ = Util.m_143827_((p_173198_) -> {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(f_173065_).m_173290_(new RenderStateShard.TextureStateShard(p_173198_, false, false)).m_110685_(f_110139_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(true);
      return m_173215_("entity_translucent_cull", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, true, true, rendertype$compositestate);
   });
   private static final BiFunction<ResourceLocation, Boolean, RenderType> f_173164_ = Util.m_143821_((p_173227_, p_173228_) -> {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(f_173066_).m_173290_(new RenderStateShard.TextureStateShard(p_173227_, false, false)).m_110685_(f_110139_).m_110661_(f_110110_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(p_173228_);
      return m_173215_("entity_translucent", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, true, true, rendertype$compositestate);
   });
   private static final Function<ResourceLocation, RenderType> f_173165_ = Util.m_143827_((p_173196_) -> {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(f_173067_).m_173290_(new RenderStateShard.TextureStateShard(p_173196_, false, false)).m_110661_(f_110110_).m_110671_(f_110152_).m_110691_(true);
      return m_173209_("entity_smooth_cutout", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, rendertype$compositestate);
   });
   private static final BiFunction<ResourceLocation, Boolean, RenderType> f_173166_ = Util.m_143821_((p_173224_, p_173225_) -> {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(f_173068_).m_173290_(new RenderStateShard.TextureStateShard(p_173224_, false, false)).m_110685_(p_173225_ ? f_110139_ : f_110134_).m_110687_(p_173225_ ? f_110115_ : f_110114_).m_110691_(false);
      return m_173215_("beacon_beam", DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 256, false, true, rendertype$compositestate);
   });
   private static final Function<ResourceLocation, RenderType> f_173167_ = Util.m_143827_((p_173194_) -> {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(f_173069_).m_173290_(new RenderStateShard.TextureStateShard(p_173194_, false, false)).m_110663_(f_110112_).m_110661_(f_110110_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(false);
      return m_173209_("entity_decal", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, rendertype$compositestate);
   });
   private static final Function<ResourceLocation, RenderType> f_173168_ = Util.m_143827_((p_173192_) -> {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(f_173070_).m_173290_(new RenderStateShard.TextureStateShard(p_173192_, false, false)).m_110685_(f_110139_).m_110661_(f_110110_).m_110671_(f_110152_).m_110677_(f_110154_).m_110687_(f_110115_).m_110691_(false);
      return m_173215_("entity_no_outline", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, false, true, rendertype$compositestate);
   });
   private static final Function<ResourceLocation, RenderType> f_173169_ = Util.m_143827_((p_173190_) -> {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(f_173071_).m_173290_(new RenderStateShard.TextureStateShard(p_173190_, false, false)).m_110685_(f_110139_).m_110661_(f_110158_).m_110671_(f_110152_).m_110677_(f_110154_).m_110687_(f_110115_).m_110663_(f_110113_).m_110669_(f_110119_).m_110691_(false);
      return m_173215_("entity_shadow", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, false, false, rendertype$compositestate);
   });
   private static final Function<ResourceLocation, RenderType> f_173170_ = Util.m_143827_((p_173188_) -> {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(f_173072_).m_173290_(new RenderStateShard.TextureStateShard(p_173188_, false, false)).m_110661_(f_110110_).m_110691_(true);
      return m_173209_("entity_alpha", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, rendertype$compositestate);
   });
   private static final Function<ResourceLocation, RenderType> f_173171_ = Util.m_143827_((p_173255_) -> {
      RenderStateShard.TextureStateShard renderstateshard$texturestateshard = new RenderStateShard.TextureStateShard(p_173255_, false, false);
      return m_173215_("eyes", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.m_110628_().m_173292_(f_173073_).m_173290_(renderstateshard$texturestateshard).m_110685_(f_110135_).m_110687_(f_110115_).m_110691_(false));
   });
   private static final RenderType f_110378_ = m_173209_("leash", DefaultVertexFormat.f_85816_, VertexFormat.Mode.TRIANGLE_STRIP, 256, RenderType.CompositeState.m_110628_().m_173292_(f_173075_).m_173290_(f_110147_).m_110661_(f_110110_).m_110671_(f_110152_).m_110691_(false));
   private static final RenderType f_110379_ = m_173209_("water_mask", DefaultVertexFormat.f_85814_, VertexFormat.Mode.QUADS, 256, RenderType.CompositeState.m_110628_().m_173292_(f_173076_).m_173290_(f_110147_).m_110687_(f_110116_).m_110691_(false));
   private static final RenderType f_110380_ = m_173209_("armor_glint", DefaultVertexFormat.f_85817_, VertexFormat.Mode.QUADS, 256, RenderType.CompositeState.m_110628_().m_173292_(f_173078_).m_173290_(new RenderStateShard.TextureStateShard(ItemRenderer.f_115092_, true, false)).m_110687_(f_110115_).m_110661_(f_110110_).m_110663_(f_110112_).m_110685_(f_110137_).m_110683_(f_110150_).m_110669_(f_110119_).m_110691_(false));
   private static final RenderType f_110381_ = m_173209_("armor_entity_glint", DefaultVertexFormat.f_85817_, VertexFormat.Mode.QUADS, 256, RenderType.CompositeState.m_110628_().m_173292_(f_173079_).m_173290_(new RenderStateShard.TextureStateShard(ItemRenderer.f_115092_, true, false)).m_110687_(f_110115_).m_110661_(f_110110_).m_110663_(f_110112_).m_110685_(f_110137_).m_110683_(f_110151_).m_110669_(f_110119_).m_110691_(false));
   private static final RenderType f_110382_ = m_173209_("glint_translucent", DefaultVertexFormat.f_85817_, VertexFormat.Mode.QUADS, 256, RenderType.CompositeState.m_110628_().m_173292_(f_173080_).m_173290_(new RenderStateShard.TextureStateShard(ItemRenderer.f_115092_, true, false)).m_110687_(f_110115_).m_110661_(f_110110_).m_110663_(f_110112_).m_110685_(f_110137_).m_110683_(f_110150_).m_110675_(f_110129_).m_110691_(false));
   private static final RenderType f_110383_ = m_173209_("glint", DefaultVertexFormat.f_85817_, VertexFormat.Mode.QUADS, 256, RenderType.CompositeState.m_110628_().m_173292_(f_173081_).m_173290_(new RenderStateShard.TextureStateShard(ItemRenderer.f_115092_, true, false)).m_110687_(f_110115_).m_110661_(f_110110_).m_110663_(f_110112_).m_110685_(f_110137_).m_110683_(f_110150_).m_110691_(false));
   private static final RenderType f_110384_ = m_173209_("glint_direct", DefaultVertexFormat.f_85817_, VertexFormat.Mode.QUADS, 256, RenderType.CompositeState.m_110628_().m_173292_(f_173082_).m_173290_(new RenderStateShard.TextureStateShard(ItemRenderer.f_115092_, true, false)).m_110687_(f_110115_).m_110661_(f_110110_).m_110663_(f_110112_).m_110685_(f_110137_).m_110683_(f_110150_).m_110691_(false));
   private static final RenderType f_110385_ = m_173209_("entity_glint", DefaultVertexFormat.f_85817_, VertexFormat.Mode.QUADS, 256, RenderType.CompositeState.m_110628_().m_173292_(f_173083_).m_173290_(new RenderStateShard.TextureStateShard(ItemRenderer.f_115092_, true, false)).m_110687_(f_110115_).m_110661_(f_110110_).m_110663_(f_110112_).m_110685_(f_110137_).m_110675_(f_110129_).m_110683_(f_110151_).m_110691_(false));
   private static final RenderType f_110386_ = m_173209_("entity_glint_direct", DefaultVertexFormat.f_85817_, VertexFormat.Mode.QUADS, 256, RenderType.CompositeState.m_110628_().m_173292_(f_173084_).m_173290_(new RenderStateShard.TextureStateShard(ItemRenderer.f_115092_, true, false)).m_110687_(f_110115_).m_110661_(f_110110_).m_110663_(f_110112_).m_110685_(f_110137_).m_110683_(f_110151_).m_110691_(false));
   private static final Function<ResourceLocation, RenderType> f_173172_ = Util.m_143827_((p_173253_) -> {
      RenderStateShard.TextureStateShard renderstateshard$texturestateshard = new RenderStateShard.TextureStateShard(p_173253_, false, false);
      return m_173215_("crumbling", DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.m_110628_().m_173292_(f_173085_).m_173290_(renderstateshard$texturestateshard).m_110685_(f_110138_).m_110687_(f_110115_).m_110669_(f_110118_).m_110691_(false));
   });
   private static final Function<ResourceLocation, RenderType> f_173173_ = Util.m_143827_((p_173251_) -> {
      return m_173215_("text", DefaultVertexFormat.f_85820_, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.m_110628_().m_173292_(f_173086_).m_173290_(new RenderStateShard.TextureStateShard(p_173251_, false, false)).m_110685_(f_110139_).m_110671_(f_110152_).m_110691_(false));
   });
   private static final Function<ResourceLocation, RenderType> f_173174_ = Util.m_143827_((p_173249_) -> {
      return m_173215_("text_intensity", DefaultVertexFormat.f_85820_, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.m_110628_().m_173292_(f_173087_).m_173290_(new RenderStateShard.TextureStateShard(p_173249_, false, false)).m_110685_(f_110139_).m_110671_(f_110152_).m_110691_(false));
   });
   private static final Function<ResourceLocation, RenderType> f_181442_ = Util.m_143827_((p_181451_) -> {
      return m_173215_("text_polygon_offset", DefaultVertexFormat.f_85820_, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.m_110628_().m_173292_(f_173086_).m_173290_(new RenderStateShard.TextureStateShard(p_181451_, false, false)).m_110685_(f_110139_).m_110671_(f_110152_).m_110669_(f_110118_).m_110691_(false));
   });
   private static final Function<ResourceLocation, RenderType> f_181443_ = Util.m_143827_((p_181449_) -> {
      return m_173215_("text_intensity_polygon_offset", DefaultVertexFormat.f_85820_, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.m_110628_().m_173292_(f_173087_).m_173290_(new RenderStateShard.TextureStateShard(p_181449_, false, false)).m_110685_(f_110139_).m_110671_(f_110152_).m_110669_(f_110118_).m_110691_(false));
   });
   private static final Function<ResourceLocation, RenderType> f_173175_ = Util.m_143827_((p_173246_) -> {
      return m_173215_("text_see_through", DefaultVertexFormat.f_85820_, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.m_110628_().m_173292_(f_173088_).m_173290_(new RenderStateShard.TextureStateShard(p_173246_, false, false)).m_110685_(f_110139_).m_110671_(f_110152_).m_110663_(f_110111_).m_110687_(f_110115_).m_110691_(false));
   });
   private static final Function<ResourceLocation, RenderType> f_173176_ = Util.m_143827_((p_173244_) -> {
      return m_173215_("text_intensity_see_through", DefaultVertexFormat.f_85820_, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.m_110628_().m_173292_(f_173090_).m_173290_(new RenderStateShard.TextureStateShard(p_173244_, false, false)).m_110685_(f_110139_).m_110671_(f_110152_).m_110663_(f_110111_).m_110687_(f_110115_).m_110691_(false));
   });
   private static final RenderType f_110387_ = m_173215_("lightning", DefaultVertexFormat.f_85815_, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.m_110628_().m_173292_(f_173091_).m_110687_(f_110114_).m_110685_(f_110136_).m_110675_(f_110127_).m_110691_(false));
   private static final RenderType f_110388_ = m_173215_("tripwire", DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 262144, true, true, m_110409_());
   private static final RenderType f_173158_ = m_173215_("end_portal", DefaultVertexFormat.f_85814_, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.m_110628_().m_173292_(f_173093_).m_173290_(RenderStateShard.MultiTextureStateShard.m_173127_().m_173132_(TheEndPortalRenderer.f_112626_, false, false).m_173132_(TheEndPortalRenderer.f_112627_, false, false).m_173131_()).m_110691_(false));
   private static final RenderType f_173159_ = m_173215_("end_gateway", DefaultVertexFormat.f_85814_, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.m_110628_().m_173292_(f_173094_).m_173290_(RenderStateShard.MultiTextureStateShard.m_173127_().m_173132_(TheEndPortalRenderer.f_112626_, false, false).m_173132_(TheEndPortalRenderer.f_112627_, false, false).m_173131_()).m_110691_(false));
   public static final RenderType.CompositeRenderType f_110371_ = m_173209_("lines", DefaultVertexFormat.f_166851_, VertexFormat.Mode.LINES, 256, RenderType.CompositeState.m_110628_().m_173292_(f_173095_).m_110673_(new RenderStateShard.LineStateShard(OptionalDouble.empty())).m_110669_(f_110119_).m_110685_(f_110139_).m_110675_(f_110129_).m_110687_(f_110114_).m_110661_(f_110110_).m_110691_(false));
   public static final RenderType.CompositeRenderType f_173152_ = m_173209_("line_strip", DefaultVertexFormat.f_166851_, VertexFormat.Mode.LINE_STRIP, 256, RenderType.CompositeState.m_110628_().m_173292_(f_173095_).m_110673_(new RenderStateShard.LineStateShard(OptionalDouble.empty())).m_110669_(f_110119_).m_110685_(f_110139_).m_110675_(f_110129_).m_110687_(f_110114_).m_110661_(f_110110_).m_110691_(false));
   private final VertexFormat f_110389_;
   private final VertexFormat.Mode f_110390_;
   private final int f_110391_;
   private final boolean f_110392_;
   private final boolean f_110393_;
   private final Optional<RenderType> f_110394_;

   public static RenderType m_110451_() {
      return f_110372_;
   }

   public static RenderType m_110457_() {
      return f_110373_;
   }

   public static RenderType m_110463_() {
      return f_110374_;
   }

   private static RenderType.CompositeState m_173207_(RenderStateShard.ShaderStateShard p_173208_) {
      return RenderType.CompositeState.m_110628_().m_110671_(f_110152_).m_173292_(p_173208_).m_173290_(f_110145_).m_110685_(f_110139_).m_110675_(f_110125_).m_110691_(true);
   }

   public static RenderType m_110466_() {
      return f_110375_;
   }

   private static RenderType.CompositeState m_110408_() {
      return RenderType.CompositeState.m_110628_().m_110671_(f_110152_).m_173292_(f_173109_).m_173290_(f_110145_).m_110685_(f_110139_).m_110675_(f_110129_).m_110691_(true);
   }

   public static RenderType m_110469_() {
      return f_110376_;
   }

   public static RenderType m_110472_() {
      return f_110377_;
   }

   public static RenderType m_110431_(ResourceLocation p_110432_) {
      return f_173155_.apply(p_110432_);
   }

   public static RenderType m_110446_(ResourceLocation p_110447_) {
      return f_173156_.apply(p_110447_);
   }

   public static RenderType m_110452_(ResourceLocation p_110453_) {
      return f_173157_.apply(p_110453_);
   }

   public static RenderType m_110443_(ResourceLocation p_110444_, boolean p_110445_) {
      return f_173160_.apply(p_110444_, p_110445_);
   }

   public static RenderType m_110458_(ResourceLocation p_110459_) {
      return m_110443_(p_110459_, true);
   }

   public static RenderType m_110448_(ResourceLocation p_110449_, boolean p_110450_) {
      return f_173161_.apply(p_110449_, p_110450_);
   }

   public static RenderType m_110464_(ResourceLocation p_110465_) {
      return m_110448_(p_110465_, true);
   }

   public static RenderType m_110467_(ResourceLocation p_110468_) {
      return f_173162_.apply(p_110468_);
   }

   public static RenderType m_110470_(ResourceLocation p_110471_) {
      return f_173163_.apply(p_110471_);
   }

   public static RenderType m_110454_(ResourceLocation p_110455_, boolean p_110456_) {
      return f_173164_.apply(p_110455_, p_110456_);
   }

   public static RenderType m_110473_(ResourceLocation p_110474_) {
      return m_110454_(p_110474_, true);
   }

   public static RenderType m_110476_(ResourceLocation p_110477_) {
      return f_173165_.apply(p_110477_);
   }

   public static RenderType m_110460_(ResourceLocation p_110461_, boolean p_110462_) {
      return f_173166_.apply(p_110461_, p_110462_);
   }

   public static RenderType m_110479_(ResourceLocation p_110480_) {
      return f_173167_.apply(p_110480_);
   }

   public static RenderType m_110482_(ResourceLocation p_110483_) {
      return f_173168_.apply(p_110483_);
   }

   public static RenderType m_110485_(ResourceLocation p_110486_) {
      return f_173169_.apply(p_110486_);
   }

   public static RenderType m_173235_(ResourceLocation p_173236_) {
      return f_173170_.apply(p_173236_);
   }

   public static RenderType m_110488_(ResourceLocation p_110489_) {
      return f_173171_.apply(p_110489_);
   }

   public static RenderType m_110436_(ResourceLocation p_110437_, float p_110438_, float p_110439_) {
      return m_173215_("energy_swirl", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.m_110628_().m_173292_(f_173074_).m_173290_(new RenderStateShard.TextureStateShard(p_110437_, false, false)).m_110683_(new RenderStateShard.OffsetTexturingStateShard(p_110438_, p_110439_)).m_110685_(f_110135_).m_110661_(f_110110_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(false));
   }

   public static RenderType m_110475_() {
      return f_110378_;
   }

   public static RenderType m_110478_() {
      return f_110379_;
   }

   public static RenderType m_110491_(ResourceLocation p_110492_) {
      return RenderType.CompositeRenderType.f_173256_.apply(p_110492_, f_110110_);
   }

   public static RenderType m_110481_() {
      return f_110380_;
   }

   public static RenderType m_110484_() {
      return f_110381_;
   }

   public static RenderType m_110487_() {
      return f_110382_;
   }

   public static RenderType m_110490_() {
      return f_110383_;
   }

   public static RenderType m_110493_() {
      return f_110384_;
   }

   public static RenderType m_110496_() {
      return f_110385_;
   }

   public static RenderType m_110499_() {
      return f_110386_;
   }

   public static RenderType m_110494_(ResourceLocation p_110495_) {
      return f_173172_.apply(p_110495_);
   }

   public static RenderType m_110497_(ResourceLocation p_110498_) {
      return net.minecraftforge.client.ForgeRenderTypes.getText(p_110498_);
   }

   public static RenderType m_173237_(ResourceLocation p_173238_) {
      return net.minecraftforge.client.ForgeRenderTypes.getTextIntensity(p_173238_);
   }

   public static RenderType m_181444_(ResourceLocation p_181445_) {
      return net.minecraftforge.client.ForgeRenderTypes.getTextPolygonOffset(p_181445_);
   }

   public static RenderType m_181446_(ResourceLocation p_181447_) {
      return net.minecraftforge.client.ForgeRenderTypes.getTextIntensityPolygonOffset(p_181447_);
   }

   public static RenderType m_110500_(ResourceLocation p_110501_) {
      return net.minecraftforge.client.ForgeRenderTypes.getTextSeeThrough(p_110501_);
   }

   public static RenderType m_173240_(ResourceLocation p_173241_) {
      return net.minecraftforge.client.ForgeRenderTypes.getTextIntensitySeeThrough(p_173241_);
   }

   public static RenderType m_110502_() {
      return f_110387_;
   }

   private static RenderType.CompositeState m_110409_() {
      return RenderType.CompositeState.m_110628_().m_110671_(f_110152_).m_173292_(f_173092_).m_173290_(f_110145_).m_110685_(f_110139_).m_110675_(f_110127_).m_110691_(true);
   }

   public static RenderType m_110503_() {
      return f_110388_;
   }

   public static RenderType m_173239_() {
      return f_173158_;
   }

   public static RenderType m_173242_() {
      return f_173159_;
   }

   public static RenderType m_110504_() {
      return f_110371_;
   }

   public static RenderType m_173247_() {
      return f_173152_;
   }

   public RenderType(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
      super(p_173178_, p_173184_, p_173185_);
      this.f_110389_ = p_173179_;
      this.f_110390_ = p_173180_;
      this.f_110391_ = p_173181_;
      this.f_110392_ = p_173182_;
      this.f_110393_ = p_173183_;
      this.f_110394_ = Optional.of(this);
   }

   static RenderType.CompositeRenderType m_173209_(String p_173210_, VertexFormat p_173211_, VertexFormat.Mode p_173212_, int p_173213_, RenderType.CompositeState p_173214_) {
      return m_173215_(p_173210_, p_173211_, p_173212_, p_173213_, false, false, p_173214_);
   }

   public static RenderType.CompositeRenderType m_173215_(String p_173216_, VertexFormat p_173217_, VertexFormat.Mode p_173218_, int p_173219_, boolean p_173220_, boolean p_173221_, RenderType.CompositeState p_173222_) {
      return new RenderType.CompositeRenderType(p_173216_, p_173217_, p_173218_, p_173219_, p_173220_, p_173221_, p_173222_);
   }

   public void m_110412_(BufferBuilder p_110413_, int p_110414_, int p_110415_, int p_110416_) {
      if (p_110413_.m_85732_()) {
         if (this.f_110393_) {
            p_110413_.m_166771_((float)p_110414_, (float)p_110415_, (float)p_110416_);
         }

         p_110413_.m_85721_();
         this.m_110185_();
         BufferUploader.m_85761_(p_110413_);
         this.m_110188_();
      }
   }

   public String toString() {
      return this.f_110133_;
   }

   public static List<RenderType> m_110506_() {
      return ImmutableList.of(m_110451_(), m_110457_(), m_110463_(), m_110466_(), m_110503_());
   }

   public int m_110507_() {
      return this.f_110391_;
   }

   public VertexFormat m_110508_() {
      return this.f_110389_;
   }

   public VertexFormat.Mode m_173186_() {
      return this.f_110390_;
   }

   public Optional<RenderType> m_7280_() {
      return Optional.empty();
   }

   public boolean m_5492_() {
      return false;
   }

   public boolean m_110405_() {
      return this.f_110392_;
   }

   public Optional<RenderType> m_110406_() {
      return this.f_110394_;
   }

   @OnlyIn(Dist.CLIENT)
   static final class CompositeRenderType extends RenderType {
      static final BiFunction<ResourceLocation, RenderStateShard.CullStateShard, RenderType> f_173256_ = Util.m_143821_((p_173272_, p_173273_) -> {
         return RenderType.m_173209_("outline", DefaultVertexFormat.f_85818_, VertexFormat.Mode.QUADS, 256, RenderType.CompositeState.m_110628_().m_173292_(f_173077_).m_173290_(new RenderStateShard.TextureStateShard(p_173272_, false, false)).m_110661_(p_173273_).m_110663_(f_110111_).m_110675_(f_110124_).m_110689_(RenderType.OutlineProperty.IS_OUTLINE));
      });
      private final RenderType.CompositeState f_110511_;
      private final Optional<RenderType> f_110513_;
      private final boolean f_110514_;

      CompositeRenderType(String p_173258_, VertexFormat p_173259_, VertexFormat.Mode p_173260_, int p_173261_, boolean p_173262_, boolean p_173263_, RenderType.CompositeState p_173264_) {
         super(p_173258_, p_173259_, p_173260_, p_173261_, p_173262_, p_173263_, () -> {
            p_173264_.f_110592_.forEach(RenderStateShard::m_110185_);
         }, () -> {
            p_173264_.f_110592_.forEach(RenderStateShard::m_110188_);
         });
         this.f_110511_ = p_173264_;
         this.f_110513_ = p_173264_.f_110591_ == RenderType.OutlineProperty.AFFECTS_OUTLINE ? p_173264_.f_110576_.m_142706_().map((p_173270_) -> {
            return f_173256_.apply(p_173270_, p_173264_.f_110582_);
         }) : Optional.empty();
         this.f_110514_ = p_173264_.f_110591_ == RenderType.OutlineProperty.IS_OUTLINE;
      }

      public Optional<RenderType> m_7280_() {
         return this.f_110513_;
      }

      public boolean m_5492_() {
         return this.f_110514_;
      }

      protected final RenderType.CompositeState m_173265_() {
         return this.f_110511_;
      }

      public String toString() {
         return "RenderType[" + this.f_110133_ + ":" + this.f_110511_ + "]";
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static final class CompositeState {
      final RenderStateShard.EmptyTextureStateShard f_110576_;
      private final RenderStateShard.ShaderStateShard f_173274_;
      private final RenderStateShard.TransparencyStateShard f_110577_;
      private final RenderStateShard.DepthTestStateShard f_110581_;
      final RenderStateShard.CullStateShard f_110582_;
      private final RenderStateShard.LightmapStateShard f_110583_;
      private final RenderStateShard.OverlayStateShard f_110584_;
      private final RenderStateShard.LayeringStateShard f_110586_;
      private final RenderStateShard.OutputStateShard f_110587_;
      private final RenderStateShard.TexturingStateShard f_110588_;
      private final RenderStateShard.WriteMaskStateShard f_110589_;
      private final RenderStateShard.LineStateShard f_110590_;
      final RenderType.OutlineProperty f_110591_;
      final ImmutableList<RenderStateShard> f_110592_;

      CompositeState(RenderStateShard.EmptyTextureStateShard p_173276_, RenderStateShard.ShaderStateShard p_173277_, RenderStateShard.TransparencyStateShard p_173278_, RenderStateShard.DepthTestStateShard p_173279_, RenderStateShard.CullStateShard p_173280_, RenderStateShard.LightmapStateShard p_173281_, RenderStateShard.OverlayStateShard p_173282_, RenderStateShard.LayeringStateShard p_173283_, RenderStateShard.OutputStateShard p_173284_, RenderStateShard.TexturingStateShard p_173285_, RenderStateShard.WriteMaskStateShard p_173286_, RenderStateShard.LineStateShard p_173287_, RenderType.OutlineProperty p_173288_) {
         this.f_110576_ = p_173276_;
         this.f_173274_ = p_173277_;
         this.f_110577_ = p_173278_;
         this.f_110581_ = p_173279_;
         this.f_110582_ = p_173280_;
         this.f_110583_ = p_173281_;
         this.f_110584_ = p_173282_;
         this.f_110586_ = p_173283_;
         this.f_110587_ = p_173284_;
         this.f_110588_ = p_173285_;
         this.f_110589_ = p_173286_;
         this.f_110590_ = p_173287_;
         this.f_110591_ = p_173288_;
         this.f_110592_ = ImmutableList.of(this.f_110576_, this.f_173274_, this.f_110577_, this.f_110581_, this.f_110582_, this.f_110583_, this.f_110584_, this.f_110586_, this.f_110587_, this.f_110588_, this.f_110589_, this.f_110590_);
      }

      public String toString() {
         return "CompositeState[" + this.f_110592_ + ", outlineProperty=" + this.f_110591_ + "]";
      }

      public static RenderType.CompositeState.CompositeStateBuilder m_110628_() {
         return new RenderType.CompositeState.CompositeStateBuilder();
      }

      @OnlyIn(Dist.CLIENT)
      public static class CompositeStateBuilder {
         private RenderStateShard.EmptyTextureStateShard f_110641_ = RenderStateShard.f_110147_;
         private RenderStateShard.ShaderStateShard f_173289_ = RenderStateShard.f_173096_;
         private RenderStateShard.TransparencyStateShard f_110642_ = RenderStateShard.f_110134_;
         private RenderStateShard.DepthTestStateShard f_110646_ = RenderStateShard.f_110113_;
         private RenderStateShard.CullStateShard f_110647_ = RenderStateShard.f_110158_;
         private RenderStateShard.LightmapStateShard f_110648_ = RenderStateShard.f_110153_;
         private RenderStateShard.OverlayStateShard f_110649_ = RenderStateShard.f_110155_;
         private RenderStateShard.LayeringStateShard f_110651_ = RenderStateShard.f_110117_;
         private RenderStateShard.OutputStateShard f_110652_ = RenderStateShard.f_110123_;
         private RenderStateShard.TexturingStateShard f_110653_ = RenderStateShard.f_110148_;
         private RenderStateShard.WriteMaskStateShard f_110654_ = RenderStateShard.f_110114_;
         private RenderStateShard.LineStateShard f_110655_ = RenderStateShard.f_110130_;

         CompositeStateBuilder() {
         }

         public RenderType.CompositeState.CompositeStateBuilder m_173290_(RenderStateShard.EmptyTextureStateShard p_173291_) {
            this.f_110641_ = p_173291_;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_173292_(RenderStateShard.ShaderStateShard p_173293_) {
            this.f_173289_ = p_173293_;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110685_(RenderStateShard.TransparencyStateShard p_110686_) {
            this.f_110642_ = p_110686_;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110663_(RenderStateShard.DepthTestStateShard p_110664_) {
            this.f_110646_ = p_110664_;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110661_(RenderStateShard.CullStateShard p_110662_) {
            this.f_110647_ = p_110662_;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110671_(RenderStateShard.LightmapStateShard p_110672_) {
            this.f_110648_ = p_110672_;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110677_(RenderStateShard.OverlayStateShard p_110678_) {
            this.f_110649_ = p_110678_;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110669_(RenderStateShard.LayeringStateShard p_110670_) {
            this.f_110651_ = p_110670_;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110675_(RenderStateShard.OutputStateShard p_110676_) {
            this.f_110652_ = p_110676_;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110683_(RenderStateShard.TexturingStateShard p_110684_) {
            this.f_110653_ = p_110684_;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110687_(RenderStateShard.WriteMaskStateShard p_110688_) {
            this.f_110654_ = p_110688_;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110673_(RenderStateShard.LineStateShard p_110674_) {
            this.f_110655_ = p_110674_;
            return this;
         }

         public RenderType.CompositeState m_110691_(boolean p_110692_) {
            return this.m_110689_(p_110692_ ? RenderType.OutlineProperty.AFFECTS_OUTLINE : RenderType.OutlineProperty.NONE);
         }

         public RenderType.CompositeState m_110689_(RenderType.OutlineProperty p_110690_) {
            return new RenderType.CompositeState(this.f_110641_, this.f_173289_, this.f_110642_, this.f_110646_, this.f_110647_, this.f_110648_, this.f_110649_, this.f_110651_, this.f_110652_, this.f_110653_, this.f_110654_, this.f_110655_, p_110690_);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   static enum OutlineProperty {
      NONE("none"),
      IS_OUTLINE("is_outline"),
      AFFECTS_OUTLINE("affects_outline");

      private final String f_110696_;

      private OutlineProperty(String p_110702_) {
         this.f_110696_ = p_110702_;
      }

      public String toString() {
         return this.f_110696_;
      }
   }
}
