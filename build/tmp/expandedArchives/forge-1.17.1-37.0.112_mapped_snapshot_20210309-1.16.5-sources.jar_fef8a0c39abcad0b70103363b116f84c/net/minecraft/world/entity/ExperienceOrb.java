package net.minecraft.world.entity;

import java.util.List;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddExperienceOrbPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ExperienceOrb extends Entity {
   private static final int f_147073_ = 6000;
   private static final int f_147074_ = 20;
   private static final int f_147075_ = 8;
   private static final int f_147076_ = 40;
   private static final double f_147077_ = 0.5D;
   private int f_20767_;
   private int f_20769_ = 5;
   public int f_20770_;
   private int f_147072_ = 1;
   private Player f_20771_;

   public ExperienceOrb(Level p_20776_, double p_20777_, double p_20778_, double p_20779_, int p_20780_) {
      this(EntityType.f_20570_, p_20776_);
      this.m_6034_(p_20777_, p_20778_, p_20779_);
      this.m_146922_((float)(this.f_19796_.nextDouble() * 360.0D));
      this.m_20334_((this.f_19796_.nextDouble() * (double)0.2F - (double)0.1F) * 2.0D, this.f_19796_.nextDouble() * 0.2D * 2.0D, (this.f_19796_.nextDouble() * (double)0.2F - (double)0.1F) * 2.0D);
      this.f_20770_ = p_20780_;
   }

   public ExperienceOrb(EntityType<? extends ExperienceOrb> p_20773_, Level p_20774_) {
      super(p_20773_, p_20774_);
   }

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.NONE;
   }

   protected void m_8097_() {
   }

   public void m_8119_() {
      super.m_8119_();
      this.f_19854_ = this.m_20185_();
      this.f_19855_ = this.m_20186_();
      this.f_19856_ = this.m_20189_();
      if (this.m_19941_(FluidTags.f_13131_)) {
         this.m_20803_();
      } else if (!this.m_20068_()) {
         this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.03D, 0.0D));
      }

      if (this.f_19853_.m_6425_(this.m_142538_()).m_76153_(FluidTags.f_13132_)) {
         this.m_20334_((double)((this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F), (double)0.2F, (double)((this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F));
      }

      if (!this.f_19853_.m_45772_(this.m_142469_())) {
         this.m_20314_(this.m_20185_(), (this.m_142469_().f_82289_ + this.m_142469_().f_82292_) / 2.0D, this.m_20189_());
      }

      if (this.f_19797_ % 20 == 1) {
         this.m_147103_();
      }

      if (this.f_20771_ != null && (this.f_20771_.m_5833_() || this.f_20771_.m_21224_())) {
         this.f_20771_ = null;
      }

      if (this.f_20771_ != null) {
         Vec3 vec3 = new Vec3(this.f_20771_.m_20185_() - this.m_20185_(), this.f_20771_.m_20186_() + (double)this.f_20771_.m_20192_() / 2.0D - this.m_20186_(), this.f_20771_.m_20189_() - this.m_20189_());
         double d0 = vec3.m_82556_();
         if (d0 < 64.0D) {
            double d1 = 1.0D - Math.sqrt(d0) / 8.0D;
            this.m_20256_(this.m_20184_().m_82549_(vec3.m_82541_().m_82490_(d1 * d1 * 0.1D)));
         }
      }

      this.m_6478_(MoverType.SELF, this.m_20184_());
      float f = 0.98F;
      if (this.f_19861_) {
         BlockPos pos =new BlockPos(this.m_20185_(), this.m_20186_() - 1.0D, this.m_20189_());
         f = this.f_19853_.m_8055_(pos).getFriction(this.f_19853_, pos, this) * 0.98F;
      }

      this.m_20256_(this.m_20184_().m_82542_((double)f, 0.98D, (double)f));
      if (this.f_19861_) {
         this.m_20256_(this.m_20184_().m_82542_(1.0D, -0.9D, 1.0D));
      }

      ++this.f_20767_;
      if (this.f_20767_ >= 6000) {
         this.m_146870_();
      }

   }

   private void m_147103_() {
      if (this.f_20771_ == null || this.f_20771_.m_20280_(this) > 64.0D) {
         this.f_20771_ = this.f_19853_.m_45930_(this, 8.0D);
      }

      if (this.f_19853_ instanceof ServerLevel) {
         for(ExperienceOrb experienceorb : this.f_19853_.m_142425_(EntityTypeTest.m_156916_(ExperienceOrb.class), this.m_142469_().m_82400_(0.5D), this::m_147086_)) {
            this.m_147100_(experienceorb);
         }
      }

   }

   public static void m_147082_(ServerLevel p_147083_, Vec3 p_147084_, int p_147085_) {
      while(p_147085_ > 0) {
         int i = m_20782_(p_147085_);
         p_147085_ -= i;
         if (!m_147096_(p_147083_, p_147084_, i)) {
            p_147083_.m_7967_(new ExperienceOrb(p_147083_, p_147084_.m_7096_(), p_147084_.m_7098_(), p_147084_.m_7094_(), i));
         }
      }

   }

   private static boolean m_147096_(ServerLevel p_147097_, Vec3 p_147098_, int p_147099_) {
      AABB aabb = AABB.m_165882_(p_147098_, 1.0D, 1.0D, 1.0D);
      int i = p_147097_.m_5822_().nextInt(40);
      List<ExperienceOrb> list = p_147097_.m_142425_(EntityTypeTest.m_156916_(ExperienceOrb.class), aabb, (p_147081_) -> {
         return m_147088_(p_147081_, i, p_147099_);
      });
      if (!list.isEmpty()) {
         ExperienceOrb experienceorb = list.get(0);
         ++experienceorb.f_147072_;
         experienceorb.f_20767_ = 0;
         return true;
      } else {
         return false;
      }
   }

   private boolean m_147086_(ExperienceOrb p_147087_) {
      return p_147087_ != this && m_147088_(p_147087_, this.m_142049_(), this.f_20770_);
   }

   private static boolean m_147088_(ExperienceOrb p_147089_, int p_147090_, int p_147091_) {
      return !p_147089_.m_146910_() && (p_147089_.m_142049_() - p_147090_) % 40 == 0 && p_147089_.f_20770_ == p_147091_;
   }

   private void m_147100_(ExperienceOrb p_147101_) {
      this.f_147072_ += p_147101_.f_147072_;
      this.f_20767_ = Math.min(this.f_20767_, p_147101_.f_20767_);
      p_147101_.m_146870_();
   }

   private void m_20803_() {
      Vec3 vec3 = this.m_20184_();
      this.m_20334_(vec3.f_82479_ * (double)0.99F, Math.min(vec3.f_82480_ + (double)5.0E-4F, (double)0.06F), vec3.f_82481_ * (double)0.99F);
   }

   protected void m_5841_() {
   }

   public boolean m_6469_(DamageSource p_20785_, float p_20786_) {
      if (this.f_19853_.f_46443_ || this.m_146910_()) return false; //Forge: Fixes MC-53850
      if (this.m_6673_(p_20785_)) {
         return false;
      } else {
         this.m_5834_();
         this.f_20769_ = (int)((float)this.f_20769_ - p_20786_);
         if (this.f_20769_ <= 0) {
            this.m_146870_();
         }

         return true;
      }
   }

   public void m_7380_(CompoundTag p_20796_) {
      p_20796_.m_128376_("Health", (short)this.f_20769_);
      p_20796_.m_128376_("Age", (short)this.f_20767_);
      p_20796_.m_128376_("Value", (short)this.f_20770_);
      p_20796_.m_128405_("Count", this.f_147072_);
   }

   public void m_7378_(CompoundTag p_20788_) {
      this.f_20769_ = p_20788_.m_128448_("Health");
      this.f_20767_ = p_20788_.m_128448_("Age");
      this.f_20770_ = p_20788_.m_128448_("Value");
      this.f_147072_ = Math.max(p_20788_.m_128451_("Count"), 1);
   }

   public void m_6123_(Player p_20792_) {
      if (!this.f_19853_.f_46443_) {
         if (p_20792_.f_36101_ == 0) {
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerXpEvent.PickupXp(p_20792_, this))) return;
            p_20792_.f_36101_ = 2;
            p_20792_.m_7938_(this, 1);
            int i = this.m_147092_(p_20792_, this.f_20770_);
            if (i > 0) {
               p_20792_.m_6756_(i);
            }

            --this.f_147072_;
            if (this.f_147072_ == 0) {
               this.m_146870_();
            }
         }

      }
   }

   private int m_147092_(Player p_147093_, int p_147094_) {
      Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.m_44839_(Enchantments.f_44962_, p_147093_, ItemStack::m_41768_);
      if (entry != null) {
         ItemStack itemstack = entry.getValue();
         int i = Math.min((int) (this.f_20770_ * itemstack.getXpRepairRatio()), itemstack.m_41773_());
         itemstack.m_41721_(itemstack.m_41773_() - i);
         int j = p_147094_ - this.m_20793_(i);
         return j > 0 ? this.m_147092_(p_147093_, j) : 0;
      } else {
         return p_147094_;
      }
   }

   private int m_20793_(int p_20794_) {
      return p_20794_ / 2;
   }

   private int m_20798_(int p_20799_) {
      return p_20799_ * 2;
   }

   public int m_20801_() {
      return this.f_20770_;
   }

   public int m_20802_() {
      if (this.f_20770_ >= 2477) {
         return 10;
      } else if (this.f_20770_ >= 1237) {
         return 9;
      } else if (this.f_20770_ >= 617) {
         return 8;
      } else if (this.f_20770_ >= 307) {
         return 7;
      } else if (this.f_20770_ >= 149) {
         return 6;
      } else if (this.f_20770_ >= 73) {
         return 5;
      } else if (this.f_20770_ >= 37) {
         return 4;
      } else if (this.f_20770_ >= 17) {
         return 3;
      } else if (this.f_20770_ >= 7) {
         return 2;
      } else {
         return this.f_20770_ >= 3 ? 1 : 0;
      }
   }

   public static int m_20782_(int p_20783_) {
      if (p_20783_ >= 2477) {
         return 2477;
      } else if (p_20783_ >= 1237) {
         return 1237;
      } else if (p_20783_ >= 617) {
         return 617;
      } else if (p_20783_ >= 307) {
         return 307;
      } else if (p_20783_ >= 149) {
         return 149;
      } else if (p_20783_ >= 73) {
         return 73;
      } else if (p_20783_ >= 37) {
         return 37;
      } else if (p_20783_ >= 17) {
         return 17;
      } else if (p_20783_ >= 7) {
         return 7;
      } else {
         return p_20783_ >= 3 ? 3 : 1;
      }
   }

   public boolean m_6097_() {
      return false;
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddExperienceOrbPacket(this);
   }

   public SoundSource m_5720_() {
      return SoundSource.AMBIENT;
   }
}
