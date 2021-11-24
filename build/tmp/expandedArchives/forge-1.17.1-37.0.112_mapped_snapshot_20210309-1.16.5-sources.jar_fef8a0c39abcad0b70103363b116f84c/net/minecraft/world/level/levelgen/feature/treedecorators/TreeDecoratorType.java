package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

public class TreeDecoratorType<P extends TreeDecorator> extends net.minecraftforge.registries.ForgeRegistryEntry<TreeDecoratorType<?>> {
   public static final TreeDecoratorType<TrunkVineDecorator> f_70042_ = m_70052_("trunk_vine", TrunkVineDecorator.f_70055_);
   public static final TreeDecoratorType<LeaveVineDecorator> f_70043_ = m_70052_("leave_vine", LeaveVineDecorator.f_69996_);
   public static final TreeDecoratorType<CocoaDecorator> f_70044_ = m_70052_("cocoa", CocoaDecorator.f_69972_);
   public static final TreeDecoratorType<BeehiveDecorator> f_70045_ = m_70052_("beehive", BeehiveDecorator.f_69954_);
   public static final TreeDecoratorType<AlterGroundDecorator> f_70046_ = m_70052_("alter_ground", AlterGroundDecorator.f_69302_);
   private final Codec<P> f_70047_;

   private static <P extends TreeDecorator> TreeDecoratorType<P> m_70052_(String p_70053_, Codec<P> p_70054_) {
      return Registry.m_122961_(Registry.f_122860_, p_70053_, new TreeDecoratorType<>(p_70054_));
   }

   public TreeDecoratorType(Codec<P> p_70050_) {
      this.f_70047_ = p_70050_;
   }

   public Codec<P> m_70051_() {
      return this.f_70047_;
   }
}
