package net.minecraft.core.particles;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.core.Registry;

@net.minecraftforge.registries.ObjectHolder("minecraft")
public class ParticleTypes {
   public static final SimpleParticleType f_123770_ = m_123824_("ambient_entity_effect", false);
   public static final SimpleParticleType f_123792_ = m_123824_("angry_villager", false);
   public static final SimpleParticleType f_123793_ = m_123824_("barrier", false);
   public static final SimpleParticleType f_175835_ = m_123824_("light", false);
   public static final ParticleType<BlockParticleOption> f_123794_ = m_123820_("block", BlockParticleOption.f_123624_, BlockParticleOption::m_123634_);
   public static final SimpleParticleType f_123795_ = m_123824_("bubble", false);
   public static final SimpleParticleType f_123796_ = m_123824_("cloud", false);
   public static final SimpleParticleType f_123797_ = m_123824_("crit", false);
   public static final SimpleParticleType f_123798_ = m_123824_("damage_indicator", true);
   public static final SimpleParticleType f_123799_ = m_123824_("dragon_breath", false);
   public static final SimpleParticleType f_123800_ = m_123824_("dripping_lava", false);
   public static final SimpleParticleType f_123801_ = m_123824_("falling_lava", false);
   public static final SimpleParticleType f_123802_ = m_123824_("landing_lava", false);
   public static final SimpleParticleType f_123803_ = m_123824_("dripping_water", false);
   public static final SimpleParticleType f_123804_ = m_123824_("falling_water", false);
   public static final ParticleType<DustParticleOptions> f_123805_ = m_123820_("dust", DustParticleOptions.f_123658_, (p_123819_) -> {
      return DustParticleOptions.f_123657_;
   });
   public static final ParticleType<DustColorTransitionOptions> f_175836_ = m_123820_("dust_color_transition", DustColorTransitionOptions.f_175754_, (p_175841_) -> {
      return DustColorTransitionOptions.f_175753_;
   });
   public static final SimpleParticleType f_123806_ = m_123824_("effect", false);
   public static final SimpleParticleType f_123807_ = m_123824_("elder_guardian", true);
   public static final SimpleParticleType f_123808_ = m_123824_("enchanted_hit", false);
   public static final SimpleParticleType f_123809_ = m_123824_("enchant", false);
   public static final SimpleParticleType f_123810_ = m_123824_("end_rod", false);
   public static final SimpleParticleType f_123811_ = m_123824_("entity_effect", false);
   public static final SimpleParticleType f_123812_ = m_123824_("explosion_emitter", true);
   public static final SimpleParticleType f_123813_ = m_123824_("explosion", true);
   public static final ParticleType<BlockParticleOption> f_123814_ = m_123820_("falling_dust", BlockParticleOption.f_123624_, BlockParticleOption::m_123634_);
   public static final SimpleParticleType f_123815_ = m_123824_("firework", false);
   public static final SimpleParticleType f_123816_ = m_123824_("fishing", false);
   public static final SimpleParticleType f_123744_ = m_123824_("flame", false);
   public static final SimpleParticleType f_123745_ = m_123824_("soul_fire_flame", false);
   public static final SimpleParticleType f_123746_ = m_123824_("soul", false);
   public static final SimpleParticleType f_123747_ = m_123824_("flash", false);
   public static final SimpleParticleType f_123748_ = m_123824_("happy_villager", false);
   public static final SimpleParticleType f_123749_ = m_123824_("composter", false);
   public static final SimpleParticleType f_123750_ = m_123824_("heart", false);
   public static final SimpleParticleType f_123751_ = m_123824_("instant_effect", false);
   public static final ParticleType<ItemParticleOption> f_123752_ = m_123820_("item", ItemParticleOption.f_123700_, ItemParticleOption::m_123710_);
   public static final ParticleType<VibrationParticleOption> f_175820_ = m_123820_("vibration", VibrationParticleOption.f_175843_, (p_175839_) -> {
      return VibrationParticleOption.f_175842_;
   });
   public static final SimpleParticleType f_123753_ = m_123824_("item_slime", false);
   public static final SimpleParticleType f_123754_ = m_123824_("item_snowball", false);
   public static final SimpleParticleType f_123755_ = m_123824_("large_smoke", false);
   public static final SimpleParticleType f_123756_ = m_123824_("lava", false);
   public static final SimpleParticleType f_123757_ = m_123824_("mycelium", false);
   public static final SimpleParticleType f_123758_ = m_123824_("note", false);
   public static final SimpleParticleType f_123759_ = m_123824_("poof", true);
   public static final SimpleParticleType f_123760_ = m_123824_("portal", false);
   public static final SimpleParticleType f_123761_ = m_123824_("rain", false);
   public static final SimpleParticleType f_123762_ = m_123824_("smoke", false);
   public static final SimpleParticleType f_123763_ = m_123824_("sneeze", false);
   public static final SimpleParticleType f_123764_ = m_123824_("spit", true);
   public static final SimpleParticleType f_123765_ = m_123824_("squid_ink", true);
   public static final SimpleParticleType f_123766_ = m_123824_("sweep_attack", true);
   public static final SimpleParticleType f_123767_ = m_123824_("totem_of_undying", false);
   public static final SimpleParticleType f_123768_ = m_123824_("underwater", false);
   public static final SimpleParticleType f_123769_ = m_123824_("splash", false);
   public static final SimpleParticleType f_123771_ = m_123824_("witch", false);
   public static final SimpleParticleType f_123772_ = m_123824_("bubble_pop", false);
   public static final SimpleParticleType f_123773_ = m_123824_("current_down", false);
   public static final SimpleParticleType f_123774_ = m_123824_("bubble_column_up", false);
   public static final SimpleParticleType f_123775_ = m_123824_("nautilus", false);
   public static final SimpleParticleType f_123776_ = m_123824_("dolphin", false);
   public static final SimpleParticleType f_123777_ = m_123824_("campfire_cosy_smoke", true);
   public static final SimpleParticleType f_123778_ = m_123824_("campfire_signal_smoke", true);
   public static final SimpleParticleType f_123779_ = m_123824_("dripping_honey", false);
   public static final SimpleParticleType f_123780_ = m_123824_("falling_honey", false);
   public static final SimpleParticleType f_123781_ = m_123824_("landing_honey", false);
   public static final SimpleParticleType f_123782_ = m_123824_("falling_nectar", false);
   public static final SimpleParticleType f_175832_ = m_123824_("falling_spore_blossom", false);
   public static final SimpleParticleType f_123783_ = m_123824_("ash", false);
   public static final SimpleParticleType f_123784_ = m_123824_("crimson_spore", false);
   public static final SimpleParticleType f_123785_ = m_123824_("warped_spore", false);
   public static final SimpleParticleType f_175833_ = m_123824_("spore_blossom_air", false);
   public static final SimpleParticleType f_123786_ = m_123824_("dripping_obsidian_tear", false);
   public static final SimpleParticleType f_123787_ = m_123824_("falling_obsidian_tear", false);
   public static final SimpleParticleType f_123788_ = m_123824_("landing_obsidian_tear", false);
   public static final SimpleParticleType f_123789_ = m_123824_("reverse_portal", false);
   public static final SimpleParticleType f_123790_ = m_123824_("white_ash", false);
   public static final SimpleParticleType f_175834_ = m_123824_("small_flame", false);
   public static final SimpleParticleType f_175821_ = m_123824_("snowflake", false);
   public static final SimpleParticleType f_175822_ = m_123824_("dripping_dripstone_lava", false);
   public static final SimpleParticleType f_175823_ = m_123824_("falling_dripstone_lava", false);
   public static final SimpleParticleType f_175824_ = m_123824_("dripping_dripstone_water", false);
   public static final SimpleParticleType f_175825_ = m_123824_("falling_dripstone_water", false);
   public static final SimpleParticleType f_175826_ = m_123824_("glow_squid_ink", true);
   public static final SimpleParticleType f_175827_ = m_123824_("glow", true);
   public static final SimpleParticleType f_175828_ = m_123824_("wax_on", true);
   public static final SimpleParticleType f_175829_ = m_123824_("wax_off", true);
   public static final SimpleParticleType f_175830_ = m_123824_("electric_spark", true);
   public static final SimpleParticleType f_175831_ = m_123824_("scrape", true);
   public static final Codec<ParticleOptions> f_123791_ = Registry.f_122829_.dispatch("type", ParticleOptions::m_6012_, ParticleType::m_7652_);

   private static SimpleParticleType m_123824_(String p_123825_, boolean p_123826_) {
      return Registry.m_122961_(Registry.f_122829_, p_123825_, new SimpleParticleType(p_123826_));
   }

   private static <T extends ParticleOptions> ParticleType<T> m_123820_(String p_123821_, ParticleOptions.Deserializer<T> p_123822_, final Function<ParticleType<T>, Codec<T>> p_123823_) {
      return Registry.m_122961_(Registry.f_122829_, p_123821_, new ParticleType<T>(false, p_123822_) {
         public Codec<T> m_7652_() {
            return p_123823_.apply(this);
         }
      });
   }
}
