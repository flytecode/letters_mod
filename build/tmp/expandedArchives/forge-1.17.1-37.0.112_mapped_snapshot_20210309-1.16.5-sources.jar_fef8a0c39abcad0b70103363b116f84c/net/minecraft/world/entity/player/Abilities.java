package net.minecraft.world.entity.player;

import net.minecraft.nbt.CompoundTag;

public class Abilities {
   public boolean f_35934_;
   public boolean f_35935_;
   public boolean f_35936_;
   public boolean f_35937_;
   public boolean f_35938_ = true;
   private float f_35939_ = 0.05F;
   private float f_35940_ = 0.1F;

   public void m_35945_(CompoundTag p_35946_) {
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128379_("invulnerable", this.f_35934_);
      compoundtag.m_128379_("flying", this.f_35935_);
      compoundtag.m_128379_("mayfly", this.f_35936_);
      compoundtag.m_128379_("instabuild", this.f_35937_);
      compoundtag.m_128379_("mayBuild", this.f_35938_);
      compoundtag.m_128350_("flySpeed", this.f_35939_);
      compoundtag.m_128350_("walkSpeed", this.f_35940_);
      p_35946_.m_128365_("abilities", compoundtag);
   }

   public void m_35950_(CompoundTag p_35951_) {
      if (p_35951_.m_128425_("abilities", 10)) {
         CompoundTag compoundtag = p_35951_.m_128469_("abilities");
         this.f_35934_ = compoundtag.m_128471_("invulnerable");
         this.f_35935_ = compoundtag.m_128471_("flying");
         this.f_35936_ = compoundtag.m_128471_("mayfly");
         this.f_35937_ = compoundtag.m_128471_("instabuild");
         if (compoundtag.m_128425_("flySpeed", 99)) {
            this.f_35939_ = compoundtag.m_128457_("flySpeed");
            this.f_35940_ = compoundtag.m_128457_("walkSpeed");
         }

         if (compoundtag.m_128425_("mayBuild", 1)) {
            this.f_35938_ = compoundtag.m_128471_("mayBuild");
         }
      }

   }

   public float m_35942_() {
      return this.f_35939_;
   }

   public void m_35943_(float p_35944_) {
      this.f_35939_ = p_35944_;
   }

   public float m_35947_() {
      return this.f_35940_;
   }

   public void m_35948_(float p_35949_) {
      this.f_35940_ = p_35949_;
   }
}