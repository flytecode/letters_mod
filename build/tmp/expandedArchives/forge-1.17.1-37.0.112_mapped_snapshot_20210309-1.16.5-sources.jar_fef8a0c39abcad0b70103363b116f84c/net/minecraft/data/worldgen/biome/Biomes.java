package net.minecraft.data.worldgen.biome;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public abstract class Biomes {
   private static final Int2ObjectMap<ResourceKey<Biome>> f_127323_ = new Int2ObjectArrayMap<>();
   public static final Biome f_127321_ = m_127327_(1, net.minecraft.world.level.biome.Biomes.f_48202_, VanillaBiomes.m_127439_(false));
   public static final Biome f_127322_ = m_127327_(127, net.minecraft.world.level.biome.Biomes.f_48173_, VanillaBiomes.m_127483_());

   private static Biome m_127327_(int p_127328_, ResourceKey<Biome> p_127329_, Biome p_127330_) {
      f_127323_.put(p_127328_, p_127329_);
      return BuiltinRegistries.m_123871_(BuiltinRegistries.f_123865_, p_127328_, p_127329_, p_127330_);
   }

   public static ResourceKey<Biome> m_127325_(int p_127326_) {
      return ((net.minecraftforge.registries.ForgeRegistry<Biome>)net.minecraftforge.registries.ForgeRegistries.BIOMES).getKey(p_127326_);
   }

   static {
      m_127327_(0, net.minecraft.world.level.biome.Biomes.f_48174_, VanillaBiomes.m_127459_(false));
      m_127327_(2, net.minecraft.world.level.biome.Biomes.f_48203_, VanillaBiomes.m_127407_(0.125F, 0.05F, true, true, true));
      m_127327_(3, net.minecraft.world.level.biome.Biomes.f_48204_, VanillaBiomes.m_127388_(1.0F, 0.5F, SurfaceBuilders.f_127288_, false));
      m_127327_(4, net.minecraft.world.level.biome.Biomes.f_48205_, VanillaBiomes.m_127452_(0.1F, 0.2F));
      m_127327_(5, net.minecraft.world.level.biome.Biomes.f_48206_, VanillaBiomes.m_127413_(0.2F, 0.2F, false, false, true, false));
      m_127327_(6, net.minecraft.world.level.biome.Biomes.f_48207_, VanillaBiomes.m_127462_(-0.2F, 0.1F, false));
      m_127327_(7, net.minecraft.world.level.biome.Biomes.f_48208_, VanillaBiomes.m_127345_(-0.5F, 0.0F, 0.5F, 4159204, false));
      m_127327_(8, net.minecraft.world.level.biome.Biomes.f_48209_, VanillaBiomes.m_127484_());
      m_127327_(9, net.minecraft.world.level.biome.Biomes.f_48210_, VanillaBiomes.m_127474_());
      m_127327_(10, net.minecraft.world.level.biome.Biomes.f_48211_, VanillaBiomes.m_127469_(false));
      m_127327_(11, net.minecraft.world.level.biome.Biomes.f_48212_, VanillaBiomes.m_127345_(-0.5F, 0.0F, 0.0F, 3750089, true));
      m_127327_(12, net.minecraft.world.level.biome.Biomes.f_48213_, VanillaBiomes.m_127402_(0.125F, 0.05F, false, false));
      m_127327_(13, net.minecraft.world.level.biome.Biomes.f_48214_, VanillaBiomes.m_127402_(0.45F, 0.3F, false, true));
      m_127327_(14, net.minecraft.world.level.biome.Biomes.f_48215_, VanillaBiomes.m_127334_(0.2F, 0.3F));
      m_127327_(15, net.minecraft.world.level.biome.Biomes.f_48216_, VanillaBiomes.m_127334_(0.0F, 0.025F));
      m_127327_(16, net.minecraft.world.level.biome.Biomes.f_48217_, VanillaBiomes.m_127337_(0.0F, 0.025F, 0.8F, 0.4F, 4159204, false, false));
      m_127327_(17, net.minecraft.world.level.biome.Biomes.f_48218_, VanillaBiomes.m_127407_(0.45F, 0.3F, false, true, false));
      m_127327_(18, net.minecraft.world.level.biome.Biomes.f_48219_, VanillaBiomes.m_127452_(0.45F, 0.3F));
      m_127327_(19, net.minecraft.world.level.biome.Biomes.f_48220_, VanillaBiomes.m_127413_(0.45F, 0.3F, false, false, false, false));
      m_127327_(20, net.minecraft.world.level.biome.Biomes.f_48221_, VanillaBiomes.m_127388_(0.8F, 0.3F, SurfaceBuilders.f_127285_, true));
      m_127327_(21, net.minecraft.world.level.biome.Biomes.f_48222_, VanillaBiomes.m_127331_());
      m_127327_(22, net.minecraft.world.level.biome.Biomes.f_48223_, VanillaBiomes.m_127468_());
      m_127327_(23, net.minecraft.world.level.biome.Biomes.f_48224_, VanillaBiomes.m_127441_());
      m_127327_(24, net.minecraft.world.level.biome.Biomes.f_48225_, VanillaBiomes.m_127459_(true));
      m_127327_(25, net.minecraft.world.level.biome.Biomes.f_48226_, VanillaBiomes.m_127337_(0.1F, 0.8F, 0.2F, 0.3F, 4159204, false, true));
      m_127327_(26, net.minecraft.world.level.biome.Biomes.f_48148_, VanillaBiomes.m_127337_(0.0F, 0.025F, 0.05F, 0.3F, 4020182, true, false));
      m_127327_(27, net.minecraft.world.level.biome.Biomes.f_48149_, VanillaBiomes.m_127393_(0.1F, 0.2F, false));
      m_127327_(28, net.minecraft.world.level.biome.Biomes.f_48150_, VanillaBiomes.m_127393_(0.45F, 0.3F, false));
      m_127327_(29, net.minecraft.world.level.biome.Biomes.f_48151_, VanillaBiomes.m_127455_(0.1F, 0.2F, false));
      m_127327_(30, net.minecraft.world.level.biome.Biomes.f_48152_, VanillaBiomes.m_127413_(0.2F, 0.2F, true, false, false, true));
      m_127327_(31, net.minecraft.world.level.biome.Biomes.f_48153_, VanillaBiomes.m_127413_(0.45F, 0.3F, true, false, false, false));
      m_127327_(32, net.minecraft.world.level.biome.Biomes.f_48154_, VanillaBiomes.m_127351_(0.2F, 0.2F, 0.3F, false));
      m_127327_(33, net.minecraft.world.level.biome.Biomes.f_48155_, VanillaBiomes.m_127351_(0.45F, 0.3F, 0.3F, false));
      m_127327_(34, net.minecraft.world.level.biome.Biomes.f_48156_, VanillaBiomes.m_127388_(1.0F, 0.5F, SurfaceBuilders.f_127285_, true));
      m_127327_(35, net.minecraft.world.level.biome.Biomes.f_48157_, VanillaBiomes.m_127356_(0.125F, 0.05F, 1.2F, false, false));
      m_127327_(36, net.minecraft.world.level.biome.Biomes.f_48158_, VanillaBiomes.m_127478_());
      m_127327_(37, net.minecraft.world.level.biome.Biomes.f_48159_, VanillaBiomes.m_127445_(0.1F, 0.2F, false));
      m_127327_(38, net.minecraft.world.level.biome.Biomes.f_48160_, VanillaBiomes.m_127442_(1.5F, 0.025F));
      m_127327_(39, net.minecraft.world.level.biome.Biomes.f_48161_, VanillaBiomes.m_127445_(1.5F, 0.025F, true));
      m_127327_(40, net.minecraft.world.level.biome.Biomes.f_48162_, VanillaBiomes.m_127477_());
      m_127327_(41, net.minecraft.world.level.biome.Biomes.f_48163_, VanillaBiomes.m_127475_());
      m_127327_(42, net.minecraft.world.level.biome.Biomes.f_48164_, VanillaBiomes.m_127476_());
      m_127327_(43, net.minecraft.world.level.biome.Biomes.f_48165_, VanillaBiomes.m_127473_());
      m_127327_(44, net.minecraft.world.level.biome.Biomes.f_48166_, VanillaBiomes.m_127480_());
      m_127327_(45, net.minecraft.world.level.biome.Biomes.f_48167_, VanillaBiomes.m_127466_(false));
      m_127327_(46, net.minecraft.world.level.biome.Biomes.f_48168_, VanillaBiomes.m_127449_(false));
      m_127327_(47, net.minecraft.world.level.biome.Biomes.f_48169_, VanillaBiomes.m_127481_());
      m_127327_(48, net.minecraft.world.level.biome.Biomes.f_48170_, VanillaBiomes.m_127466_(true));
      m_127327_(49, net.minecraft.world.level.biome.Biomes.f_48171_, VanillaBiomes.m_127449_(true));
      m_127327_(50, net.minecraft.world.level.biome.Biomes.f_48172_, VanillaBiomes.m_127469_(true));
      m_127327_(129, net.minecraft.world.level.biome.Biomes.f_48176_, VanillaBiomes.m_127439_(true));
      m_127327_(130, net.minecraft.world.level.biome.Biomes.f_48177_, VanillaBiomes.m_127407_(0.225F, 0.25F, false, false, false));
      m_127327_(131, net.minecraft.world.level.biome.Biomes.f_48178_, VanillaBiomes.m_127388_(1.0F, 0.5F, SurfaceBuilders.f_127286_, false));
      m_127327_(132, net.minecraft.world.level.biome.Biomes.f_48179_, VanillaBiomes.m_127482_());
      m_127327_(133, net.minecraft.world.level.biome.Biomes.f_48180_, VanillaBiomes.m_127413_(0.3F, 0.4F, false, true, false, false));
      m_127327_(134, net.minecraft.world.level.biome.Biomes.f_48181_, VanillaBiomes.m_127462_(-0.1F, 0.3F, true));
      m_127327_(140, net.minecraft.world.level.biome.Biomes.f_48182_, VanillaBiomes.m_127402_(0.425F, 0.45000002F, true, false));
      m_127327_(149, net.minecraft.world.level.biome.Biomes.f_48183_, VanillaBiomes.m_127461_());
      m_127327_(151, net.minecraft.world.level.biome.Biomes.f_48184_, VanillaBiomes.m_127451_());
      m_127327_(155, net.minecraft.world.level.biome.Biomes.f_48185_, VanillaBiomes.m_127393_(0.2F, 0.4F, true));
      m_127327_(156, net.minecraft.world.level.biome.Biomes.f_48186_, VanillaBiomes.m_127393_(0.55F, 0.5F, true));
      m_127327_(157, net.minecraft.world.level.biome.Biomes.f_48187_, VanillaBiomes.m_127455_(0.2F, 0.4F, true));
      m_127327_(158, net.minecraft.world.level.biome.Biomes.f_48188_, VanillaBiomes.m_127413_(0.3F, 0.4F, true, true, false, false));
      m_127327_(160, net.minecraft.world.level.biome.Biomes.f_48189_, VanillaBiomes.m_127351_(0.2F, 0.2F, 0.25F, true));
      m_127327_(161, net.minecraft.world.level.biome.Biomes.f_48190_, VanillaBiomes.m_127351_(0.2F, 0.2F, 0.25F, true));
      m_127327_(162, net.minecraft.world.level.biome.Biomes.f_48191_, VanillaBiomes.m_127388_(1.0F, 0.5F, SurfaceBuilders.f_127286_, false));
      m_127327_(163, net.minecraft.world.level.biome.Biomes.f_48192_, VanillaBiomes.m_127356_(0.3625F, 1.225F, 1.1F, true, true));
      m_127327_(164, net.minecraft.world.level.biome.Biomes.f_48193_, VanillaBiomes.m_127356_(1.05F, 1.2125001F, 1.0F, true, true));
      m_127327_(165, net.minecraft.world.level.biome.Biomes.f_48194_, VanillaBiomes.m_127479_());
      m_127327_(166, net.minecraft.world.level.biome.Biomes.f_48195_, VanillaBiomes.m_127442_(0.45F, 0.3F));
      m_127327_(167, net.minecraft.world.level.biome.Biomes.f_48196_, VanillaBiomes.m_127445_(0.45F, 0.3F, true));
      m_127327_(168, net.minecraft.world.level.biome.Biomes.f_48197_, VanillaBiomes.m_127471_());
      m_127327_(169, net.minecraft.world.level.biome.Biomes.f_48198_, VanillaBiomes.m_127472_());
      m_127327_(170, net.minecraft.world.level.biome.Biomes.f_48199_, VanillaBiomes.m_127485_());
      m_127327_(171, net.minecraft.world.level.biome.Biomes.f_48200_, VanillaBiomes.m_127487_());
      m_127327_(172, net.minecraft.world.level.biome.Biomes.f_48201_, VanillaBiomes.m_127488_());
      m_127327_(173, net.minecraft.world.level.biome.Biomes.f_48175_, VanillaBiomes.m_127486_());
      m_127327_(174, net.minecraft.world.level.biome.Biomes.f_151784_, VanillaBiomes.m_177035_());
      m_127327_(175, net.minecraft.world.level.biome.Biomes.f_151785_, VanillaBiomes.m_177034_());
   }
}
