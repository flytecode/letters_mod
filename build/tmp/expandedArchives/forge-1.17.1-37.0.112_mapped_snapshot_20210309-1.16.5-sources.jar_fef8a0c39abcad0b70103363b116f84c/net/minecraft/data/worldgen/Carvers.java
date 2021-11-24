package net.minecraft.data.worldgen;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class Carvers {
   public static final ConfiguredWorldCarver<CaveCarverConfiguration> f_126848_ = m_126855_("cave", WorldCarver.f_64974_.m_65063_(new CaveCarverConfiguration(0.14285715F, BiasedToBottomHeight.m_161931_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158922_(127), 8), ConstantFloat.m_146458_(0.5F), VerticalAnchor.m_158930_(10), false, CarverDebugSettings.m_159136_(false, Blocks.f_50669_.m_49966_()), ConstantFloat.m_146458_(1.0F), ConstantFloat.m_146458_(1.0F), ConstantFloat.m_146458_(-0.7F))));
   public static final ConfiguredWorldCarver<CaveCarverConfiguration> f_176865_ = m_126855_("prototype_cave", WorldCarver.f_64974_.m_65063_(new CaveCarverConfiguration(0.33333334F, UniformHeight.m_162034_(VerticalAnchor.m_158930_(8), VerticalAnchor.m_158922_(126)), UniformFloat.m_146605_(0.1F, 0.9F), VerticalAnchor.m_158930_(8), false, CarverDebugSettings.m_159136_(false, Blocks.f_50669_.m_49966_()), UniformFloat.m_146605_(0.7F, 1.4F), UniformFloat.m_146605_(0.8F, 1.3F), UniformFloat.m_146605_(-1.0F, -0.4F))));
   public static final ConfiguredWorldCarver<CanyonCarverConfiguration> f_126849_ = m_126855_("canyon", WorldCarver.f_64976_.m_65063_(new CanyonCarverConfiguration(0.02F, BiasedToBottomHeight.m_161931_(VerticalAnchor.m_158922_(20), VerticalAnchor.m_158922_(67), 8), ConstantFloat.m_146458_(3.0F), VerticalAnchor.m_158930_(10), false, CarverDebugSettings.m_159136_(false, Blocks.f_50670_.m_49966_()), UniformFloat.m_146605_(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.m_146605_(0.75F, 1.0F), TrapezoidFloat.m_146571_(0.0F, 6.0F, 2.0F), 3, UniformFloat.m_146605_(0.75F, 1.0F), 1.0F, 0.0F))));
   public static final ConfiguredWorldCarver<CanyonCarverConfiguration> f_176866_ = m_126855_("prototype_canyon", WorldCarver.f_64976_.m_65063_(new CanyonCarverConfiguration(0.02F, UniformHeight.m_162034_(VerticalAnchor.m_158922_(10), VerticalAnchor.m_158922_(67)), ConstantFloat.m_146458_(3.0F), VerticalAnchor.m_158930_(8), false, CarverDebugSettings.m_159136_(false, Blocks.f_50670_.m_49966_()), UniformFloat.m_146605_(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.m_146605_(0.75F, 1.0F), TrapezoidFloat.m_146571_(0.0F, 6.0F, 2.0F), 3, UniformFloat.m_146605_(0.75F, 1.0F), 1.0F, 0.0F))));
   public static final ConfiguredWorldCarver<CaveCarverConfiguration> f_126850_ = m_126855_("ocean_cave", WorldCarver.f_64974_.m_65063_(new CaveCarverConfiguration(0.06666667F, BiasedToBottomHeight.m_161931_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158922_(127), 8), ConstantFloat.m_146458_(0.5F), VerticalAnchor.m_158930_(10), false, CarverDebugSettings.m_159136_(false, Blocks.f_50669_.m_49966_()), ConstantFloat.m_146458_(1.0F), ConstantFloat.m_146458_(1.0F), ConstantFloat.m_146458_(-0.7F))));
   public static final ConfiguredWorldCarver<CanyonCarverConfiguration> f_126851_ = m_126855_("underwater_canyon", WorldCarver.f_64977_.m_65063_(new CanyonCarverConfiguration(0.02F, BiasedToBottomHeight.m_161931_(VerticalAnchor.m_158922_(20), VerticalAnchor.m_158922_(67), 8), ConstantFloat.m_146458_(3.0F), VerticalAnchor.m_158930_(10), false, CarverDebugSettings.m_159136_(false, Blocks.f_50670_.m_49966_()), UniformFloat.m_146605_(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.m_146605_(0.75F, 1.0F), TrapezoidFloat.m_146571_(0.0F, 6.0F, 2.0F), 3, UniformFloat.m_146605_(0.75F, 1.0F), 1.0F, 0.0F))));
   public static final ConfiguredWorldCarver<CaveCarverConfiguration> f_126852_ = m_126855_("underwater_cave", WorldCarver.f_64978_.m_65063_(new CaveCarverConfiguration(0.06666667F, BiasedToBottomHeight.m_161931_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158922_(127), 8), ConstantFloat.m_146458_(0.5F), VerticalAnchor.m_158930_(10), false, CarverDebugSettings.m_159136_(false, Blocks.f_50669_.m_49966_()), ConstantFloat.m_146458_(1.0F), ConstantFloat.m_146458_(1.0F), ConstantFloat.m_146458_(-0.7F))));
   public static final ConfiguredWorldCarver<CaveCarverConfiguration> f_126853_ = m_126855_("nether_cave", WorldCarver.f_64975_.m_65063_(new CaveCarverConfiguration(0.2F, UniformHeight.m_162034_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158935_(1)), ConstantFloat.m_146458_(0.5F), VerticalAnchor.m_158930_(10), false, ConstantFloat.m_146458_(1.0F), ConstantFloat.m_146458_(1.0F), ConstantFloat.m_146458_(-0.7F))));
   public static final ConfiguredWorldCarver<CanyonCarverConfiguration> f_176867_ = m_126855_("prototype_crevice", WorldCarver.f_64976_.m_65063_(new CanyonCarverConfiguration(0.00125F, UniformHeight.m_162034_(VerticalAnchor.m_158922_(40), VerticalAnchor.m_158922_(80)), UniformFloat.m_146605_(6.0F, 8.0F), VerticalAnchor.m_158930_(8), false, CarverDebugSettings.m_159136_(false, Blocks.f_50251_.m_49966_()), UniformFloat.m_146605_(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.m_146605_(0.5F, 1.0F), UniformFloat.m_146605_(0.0F, 1.0F), 6, UniformFloat.m_146605_(0.25F, 1.0F), 0.0F, 5.0F))));

   private static <WC extends CarverConfiguration> ConfiguredWorldCarver<WC> m_126855_(String p_126856_, ConfiguredWorldCarver<WC> p_126857_) {
      return BuiltinRegistries.m_123876_(BuiltinRegistries.f_123860_, p_126856_, p_126857_);
   }
}