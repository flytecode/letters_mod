package net.minecraft.world.effect;

import com.google.common.collect.ComparisonChain;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobEffectInstance implements Comparable<MobEffectInstance>, net.minecraftforge.common.extensions.IForgeMobEffectInstance {
   private static final Logger f_19501_ = LogManager.getLogger();
   private final MobEffect f_19502_;
   private int f_19503_;
   private int f_19504_;
   private boolean f_19506_;
   private boolean f_19507_;
   private boolean f_19508_;
   private boolean f_19509_;
   @Nullable
   private MobEffectInstance f_19510_;

   public MobEffectInstance(MobEffect p_19513_) {
      this(p_19513_, 0, 0);
   }

   public MobEffectInstance(MobEffect p_19515_, int p_19516_) {
      this(p_19515_, p_19516_, 0);
   }

   public MobEffectInstance(MobEffect p_19518_, int p_19519_, int p_19520_) {
      this(p_19518_, p_19519_, p_19520_, false, true);
   }

   public MobEffectInstance(MobEffect p_19522_, int p_19523_, int p_19524_, boolean p_19525_, boolean p_19526_) {
      this(p_19522_, p_19523_, p_19524_, p_19525_, p_19526_, p_19526_);
   }

   public MobEffectInstance(MobEffect p_19528_, int p_19529_, int p_19530_, boolean p_19531_, boolean p_19532_, boolean p_19533_) {
      this(p_19528_, p_19529_, p_19530_, p_19531_, p_19532_, p_19533_, (MobEffectInstance)null);
   }

   public MobEffectInstance(MobEffect p_19535_, int p_19536_, int p_19537_, boolean p_19538_, boolean p_19539_, boolean p_19540_, @Nullable MobEffectInstance p_19541_) {
      this.f_19502_ = p_19535_;
      this.f_19503_ = p_19536_;
      this.f_19504_ = p_19537_;
      this.f_19506_ = p_19538_;
      this.f_19508_ = p_19539_;
      this.f_19509_ = p_19540_;
      this.f_19510_ = p_19541_;
   }

   public MobEffectInstance(MobEffectInstance p_19543_) {
      this.f_19502_ = p_19543_.f_19502_;
      this.m_19548_(p_19543_);
   }

   void m_19548_(MobEffectInstance p_19549_) {
      this.f_19503_ = p_19549_.f_19503_;
      this.f_19504_ = p_19549_.f_19504_;
      this.f_19506_ = p_19549_.f_19506_;
      this.f_19508_ = p_19549_.f_19508_;
      this.f_19509_ = p_19549_.f_19509_;
      this.curativeItems = p_19549_.curativeItems == null ? null : new java.util.ArrayList<net.minecraft.world.item.ItemStack>(p_19549_.curativeItems);
   }

   public boolean m_19558_(MobEffectInstance p_19559_) {
      if (this.f_19502_ != p_19559_.f_19502_) {
         f_19501_.warn("This method should only be called for matching effects!");
      }

      boolean flag = false;
      if (p_19559_.f_19504_ > this.f_19504_) {
         if (p_19559_.f_19503_ < this.f_19503_) {
            MobEffectInstance mobeffectinstance = this.f_19510_;
            this.f_19510_ = new MobEffectInstance(this);
            this.f_19510_.f_19510_ = mobeffectinstance;
         }

         this.f_19504_ = p_19559_.f_19504_;
         this.f_19503_ = p_19559_.f_19503_;
         flag = true;
      } else if (p_19559_.f_19503_ > this.f_19503_) {
         if (p_19559_.f_19504_ == this.f_19504_) {
            this.f_19503_ = p_19559_.f_19503_;
            flag = true;
         } else if (this.f_19510_ == null) {
            this.f_19510_ = new MobEffectInstance(p_19559_);
         } else {
            this.f_19510_.m_19558_(p_19559_);
         }
      }

      if (!p_19559_.f_19506_ && this.f_19506_ || flag) {
         this.f_19506_ = p_19559_.f_19506_;
         flag = true;
      }

      if (p_19559_.f_19508_ != this.f_19508_) {
         this.f_19508_ = p_19559_.f_19508_;
         flag = true;
      }

      if (p_19559_.f_19509_ != this.f_19509_) {
         this.f_19509_ = p_19559_.f_19509_;
         flag = true;
      }

      return flag;
   }

   public MobEffect m_19544_() {
      return this.f_19502_ == null ? null : this.f_19502_.delegate.get();
   }

   public int m_19557_() {
      return this.f_19503_;
   }

   public int m_19564_() {
      return this.f_19504_;
   }

   public boolean m_19571_() {
      return this.f_19506_;
   }

   public boolean m_19572_() {
      return this.f_19508_;
   }

   public boolean m_19575_() {
      return this.f_19509_;
   }

   public boolean m_19552_(LivingEntity p_19553_, Runnable p_19554_) {
      if (this.f_19503_ > 0) {
         if (this.f_19502_.m_6584_(this.f_19503_, this.f_19504_)) {
            this.m_19550_(p_19553_);
         }

         this.m_19579_();
         if (this.f_19503_ == 0 && this.f_19510_ != null) {
            this.m_19548_(this.f_19510_);
            this.f_19510_ = this.f_19510_.f_19510_;
            p_19554_.run();
         }
      }

      return this.f_19503_ > 0;
   }

   private int m_19579_() {
      if (this.f_19510_ != null) {
         this.f_19510_.m_19579_();
      }

      return --this.f_19503_;
   }

   public void m_19550_(LivingEntity p_19551_) {
      if (this.f_19503_ > 0) {
         this.f_19502_.m_6742_(p_19551_, this.f_19504_);
      }

   }

   public String m_19576_() {
      return this.f_19502_.m_19481_();
   }

   public String toString() {
      String s;
      if (this.f_19504_ > 0) {
         s = this.m_19576_() + " x " + (this.f_19504_ + 1) + ", Duration: " + this.f_19503_;
      } else {
         s = this.m_19576_() + ", Duration: " + this.f_19503_;
      }

      if (!this.f_19508_) {
         s = s + ", Particles: false";
      }

      if (!this.f_19509_) {
         s = s + ", Show Icon: false";
      }

      return s;
   }

   public boolean equals(Object p_19574_) {
      if (this == p_19574_) {
         return true;
      } else if (!(p_19574_ instanceof MobEffectInstance)) {
         return false;
      } else {
         MobEffectInstance mobeffectinstance = (MobEffectInstance)p_19574_;
         return this.f_19503_ == mobeffectinstance.f_19503_ && this.f_19504_ == mobeffectinstance.f_19504_ && this.f_19506_ == mobeffectinstance.f_19506_ && this.f_19502_.equals(mobeffectinstance.f_19502_);
      }
   }

   public int hashCode() {
      int i = this.f_19502_.hashCode();
      i = 31 * i + this.f_19503_;
      i = 31 * i + this.f_19504_;
      return 31 * i + (this.f_19506_ ? 1 : 0);
   }

   public CompoundTag m_19555_(CompoundTag p_19556_) {
      p_19556_.m_128344_("Id", (byte)MobEffect.m_19459_(this.m_19544_()));
      this.m_19567_(p_19556_);
      return p_19556_;
   }

   private void m_19567_(CompoundTag p_19568_) {
      p_19568_.m_128344_("Amplifier", (byte)this.m_19564_());
      p_19568_.m_128405_("Duration", this.m_19557_());
      p_19568_.m_128379_("Ambient", this.m_19571_());
      p_19568_.m_128379_("ShowParticles", this.m_19572_());
      p_19568_.m_128379_("ShowIcon", this.m_19575_());
      if (this.f_19510_ != null) {
         CompoundTag compoundtag = new CompoundTag();
         this.f_19510_.m_19555_(compoundtag);
         p_19568_.m_128365_("HiddenEffect", compoundtag);
      }
      writeCurativeItems(p_19568_);

   }

   @Nullable
   public static MobEffectInstance m_19560_(CompoundTag p_19561_) {
      int i = p_19561_.m_128445_("Id") & 0xFF;
      MobEffect mobeffect = MobEffect.m_19453_(i);
      return mobeffect == null ? null : m_19545_(mobeffect, p_19561_);
   }

   private static MobEffectInstance m_19545_(MobEffect p_19546_, CompoundTag p_19547_) {
      int i = p_19547_.m_128445_("Amplifier");
      int j = p_19547_.m_128451_("Duration");
      boolean flag = p_19547_.m_128471_("Ambient");
      boolean flag1 = true;
      if (p_19547_.m_128425_("ShowParticles", 1)) {
         flag1 = p_19547_.m_128471_("ShowParticles");
      }

      boolean flag2 = flag1;
      if (p_19547_.m_128425_("ShowIcon", 1)) {
         flag2 = p_19547_.m_128471_("ShowIcon");
      }

      MobEffectInstance mobeffectinstance = null;
      if (p_19547_.m_128425_("HiddenEffect", 10)) {
         mobeffectinstance = m_19545_(p_19546_, p_19547_.m_128469_("HiddenEffect"));
      }

      return readCurativeItems(new MobEffectInstance(p_19546_, j, i < 0 ? 0 : i, flag, flag1, flag2, mobeffectinstance), p_19547_);
   }

   public void m_19562_(boolean p_19563_) {
      this.f_19507_ = p_19563_;
   }

   public boolean m_19577_() {
      return this.f_19507_;
   }

   public int compareTo(MobEffectInstance p_19566_) {
      int i = 32147;
      return (this.m_19557_() <= 32147 || p_19566_.m_19557_() <= 32147) && (!this.m_19571_() || !p_19566_.m_19571_()) ? ComparisonChain.start().compare(this.m_19571_(), p_19566_.m_19571_()).compare(this.m_19557_(), p_19566_.m_19557_()).compare(this.m_19544_().getGuiSortColor(this), p_19566_.m_19544_().getGuiSortColor(this)).result() : ComparisonChain.start().compare(this.m_19571_(), p_19566_.m_19571_()).compare(this.m_19544_().getGuiSortColor(this), p_19566_.m_19544_().getGuiSortColor(this)).result();
   }

   //======================= FORGE START ===========================
   private java.util.List<net.minecraft.world.item.ItemStack> curativeItems;

   @Override
   public java.util.List<net.minecraft.world.item.ItemStack> getCurativeItems() {
      if (this.curativeItems == null) //Lazy load this so that we don't create a circular dep on Items.
         this.curativeItems = m_19544_().getCurativeItems();
      return this.curativeItems;
   }
   @Override
   public void setCurativeItems(java.util.List<net.minecraft.world.item.ItemStack> curativeItems) {
      this.curativeItems = curativeItems;
   }
   private static MobEffectInstance readCurativeItems(MobEffectInstance effect, CompoundTag nbt) {
      if (nbt.m_128425_("CurativeItems", net.minecraftforge.common.util.Constants.NBT.TAG_LIST)) {
         java.util.List<net.minecraft.world.item.ItemStack> items = new java.util.ArrayList<net.minecraft.world.item.ItemStack>();
         net.minecraft.nbt.ListTag list = nbt.m_128437_("CurativeItems", net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);
         for (int i = 0; i < list.size(); i++) {
            items.add(net.minecraft.world.item.ItemStack.m_41712_(list.m_128728_(i)));
         }
         effect.setCurativeItems(items);
      }

      return effect;
   }
}
