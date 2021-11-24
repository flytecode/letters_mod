package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockEntityRenderers {
   private static final Map<BlockEntityType<?>, BlockEntityRendererProvider<?>> f_173587_ = new java.util.concurrent.ConcurrentHashMap<>();

   public static <T extends BlockEntity> void m_173590_(BlockEntityType<? extends T> p_173591_, BlockEntityRendererProvider<T> p_173592_) {
      f_173587_.put(p_173591_, p_173592_);
   }

   public static Map<BlockEntityType<?>, BlockEntityRenderer<?>> m_173598_(BlockEntityRendererProvider.Context p_173599_) {
      Builder<BlockEntityType<?>, BlockEntityRenderer<?>> builder = ImmutableMap.builder();
      f_173587_.forEach((p_173596_, p_173597_) -> {
         try {
            builder.put(p_173596_, p_173597_.m_173570_(p_173599_));
         } catch (Exception exception) {
            throw new IllegalStateException("Failed to create model for " + Registry.f_122830_.m_7981_(p_173596_), exception);
         }
      });
      return builder.build();
   }

   static {
      m_173590_(BlockEntityType.f_58924_, SignRenderer::new);
      m_173590_(BlockEntityType.f_58925_, SpawnerRenderer::new);
      m_173590_(BlockEntityType.f_58926_, PistonHeadRenderer::new);
      m_173590_(BlockEntityType.f_58918_, ChestRenderer::new);
      m_173590_(BlockEntityType.f_58920_, ChestRenderer::new);
      m_173590_(BlockEntityType.f_58919_, ChestRenderer::new);
      m_173590_(BlockEntityType.f_58928_, EnchantTableRenderer::new);
      m_173590_(BlockEntityType.f_58908_, LecternRenderer::new);
      m_173590_(BlockEntityType.f_58929_, TheEndPortalRenderer::new);
      m_173590_(BlockEntityType.f_58937_, TheEndGatewayRenderer::new);
      m_173590_(BlockEntityType.f_58930_, BeaconRenderer::new);
      m_173590_(BlockEntityType.f_58931_, SkullBlockRenderer::new);
      m_173590_(BlockEntityType.f_58935_, BannerRenderer::new);
      m_173590_(BlockEntityType.f_58936_, StructureBlockRenderer::new);
      m_173590_(BlockEntityType.f_58939_, ShulkerBoxRenderer::new);
      m_173590_(BlockEntityType.f_58940_, BedRenderer::new);
      m_173590_(BlockEntityType.f_58941_, ConduitRenderer::new);
      m_173590_(BlockEntityType.f_58909_, BellRenderer::new);
      m_173590_(BlockEntityType.f_58911_, CampfireRenderer::new);
   }
}
