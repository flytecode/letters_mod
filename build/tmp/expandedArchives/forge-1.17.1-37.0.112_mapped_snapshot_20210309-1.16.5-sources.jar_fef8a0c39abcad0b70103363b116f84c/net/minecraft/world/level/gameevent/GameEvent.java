package net.minecraft.world.level.gameevent;

import net.minecraft.core.Registry;

public class GameEvent {
   public static final GameEvent f_157791_ = m_157822_("block_attach");
   public static final GameEvent f_157792_ = m_157822_("block_change");
   public static final GameEvent f_157793_ = m_157822_("block_close");
   public static final GameEvent f_157794_ = m_157822_("block_destroy");
   public static final GameEvent f_157795_ = m_157822_("block_detach");
   public static final GameEvent f_157796_ = m_157822_("block_open");
   public static final GameEvent f_157797_ = m_157822_("block_place");
   public static final GameEvent f_157798_ = m_157822_("block_press");
   public static final GameEvent f_157799_ = m_157822_("block_switch");
   public static final GameEvent f_157800_ = m_157822_("block_unpress");
   public static final GameEvent f_157801_ = m_157822_("block_unswitch");
   public static final GameEvent f_157802_ = m_157822_("container_close");
   public static final GameEvent f_157803_ = m_157822_("container_open");
   public static final GameEvent f_157804_ = m_157822_("dispense_fail");
   public static final GameEvent f_157805_ = m_157822_("drinking_finish");
   public static final GameEvent f_157806_ = m_157822_("eat");
   public static final GameEvent f_157807_ = m_157822_("elytra_free_fall");
   public static final GameEvent f_157808_ = m_157822_("entity_damaged");
   public static final GameEvent f_157809_ = m_157822_("entity_killed");
   public static final GameEvent f_157810_ = m_157822_("entity_place");
   public static final GameEvent f_157811_ = m_157822_("equip");
   public static final GameEvent f_157812_ = m_157822_("explode");
   public static final GameEvent f_157813_ = m_157822_("fishing_rod_cast");
   public static final GameEvent f_157814_ = m_157822_("fishing_rod_reel_in");
   public static final GameEvent f_157815_ = m_157822_("flap");
   public static final GameEvent f_157816_ = m_157822_("fluid_pickup");
   public static final GameEvent f_157769_ = m_157822_("fluid_place");
   public static final GameEvent f_157770_ = m_157822_("hit_ground");
   public static final GameEvent f_157771_ = m_157822_("mob_interact");
   public static final GameEvent f_157772_ = m_157822_("lightning_strike");
   public static final GameEvent f_157773_ = m_157822_("minecart_moving");
   public static final GameEvent f_157774_ = m_157822_("piston_contract");
   public static final GameEvent f_157775_ = m_157822_("piston_extend");
   public static final GameEvent f_157776_ = m_157822_("prime_fuse");
   public static final GameEvent f_157777_ = m_157822_("projectile_land");
   public static final GameEvent f_157778_ = m_157822_("projectile_shoot");
   public static final GameEvent f_157779_ = m_157822_("ravager_roar");
   public static final GameEvent f_157780_ = m_157822_("ring_bell");
   public static final GameEvent f_157781_ = m_157822_("shear");
   public static final GameEvent f_157782_ = m_157822_("shulker_close");
   public static final GameEvent f_157783_ = m_157822_("shulker_open");
   public static final GameEvent f_157784_ = m_157822_("splash");
   public static final GameEvent f_157785_ = m_157822_("step");
   public static final GameEvent f_157786_ = m_157822_("swim");
   public static final GameEvent f_157787_ = m_157822_("wolf_shaking");
   public static final int f_157788_ = 16;
   private final String f_157789_;
   private final int f_157790_;
   private final net.minecraftforge.common.util.ReverseTagWrapper<GameEvent> reverseTags = new net.minecraftforge.common.util.ReverseTagWrapper<>(this, net.minecraft.tags.GameEventTags::m_144306_);

   public GameEvent(String p_157819_, int p_157820_) {
      this.f_157789_ = p_157819_;
      this.f_157790_ = p_157820_;
   }

   public java.util.Set<net.minecraft.resources.ResourceLocation> getTags() {
      return reverseTags.getTagNames();
   }

   public String m_157821_() {
      return this.f_157789_;
   }

   public int m_157827_() {
      return this.f_157790_;
   }

   private static GameEvent m_157822_(String p_157823_) {
      return m_157824_(p_157823_, 16);
   }

   private static GameEvent m_157824_(String p_157825_, int p_157826_) {
      return Registry.m_122961_(Registry.f_175412_, p_157825_, new GameEvent(p_157825_, p_157826_));
   }

   public String toString() {
      return "Game Event{ " + this.f_157789_ + " , " + this.f_157790_ + "}";
   }
}
