package net.minecraft.world.entity.npc;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractVillager extends AgeableMob implements InventoryCarrier, Npc, Merchant {
   private static final EntityDataAccessor<Integer> f_35262_ = SynchedEntityData.m_135353_(AbstractVillager.class, EntityDataSerializers.f_135028_);
   public static final int f_149991_ = 300;
   private static final int f_149992_ = 8;
   @Nullable
   private Player f_35263_;
   @Nullable
   protected MerchantOffers f_35261_;
   private final SimpleContainer f_35264_ = new SimpleContainer(8);

   public AbstractVillager(EntityType<? extends AbstractVillager> p_35267_, Level p_35268_) {
      super(p_35267_, p_35268_);
      this.m_21441_(BlockPathTypes.DANGER_FIRE, 16.0F);
      this.m_21441_(BlockPathTypes.DAMAGE_FIRE, -1.0F);
   }

   public SpawnGroupData m_6518_(ServerLevelAccessor p_35282_, DifficultyInstance p_35283_, MobSpawnType p_35284_, @Nullable SpawnGroupData p_35285_, @Nullable CompoundTag p_35286_) {
      if (p_35285_ == null) {
         p_35285_ = new AgeableMob.AgeableMobGroupData(false);
      }

      return super.m_6518_(p_35282_, p_35283_, p_35284_, p_35285_, p_35286_);
   }

   public int m_35303_() {
      return this.f_19804_.m_135370_(f_35262_);
   }

   public void m_35319_(int p_35320_) {
      this.f_19804_.m_135381_(f_35262_, p_35320_);
   }

   public int m_7809_() {
      return 0;
   }

   protected float m_6431_(Pose p_35297_, EntityDimensions p_35298_) {
      return this.m_6162_() ? 0.81F : 1.62F;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_35262_, 0);
   }

   public void m_7189_(@Nullable Player p_35314_) {
      this.f_35263_ = p_35314_;
   }

   @Nullable
   public Player m_7962_() {
      return this.f_35263_;
   }

   public boolean m_35306_() {
      return this.f_35263_ != null;
   }

   public MerchantOffers m_6616_() {
      if (this.f_35261_ == null) {
         this.f_35261_ = new MerchantOffers();
         this.m_7604_();
      }

      return this.f_35261_;
   }

   public void m_6255_(@Nullable MerchantOffers p_35276_) {
   }

   public void m_6621_(int p_35322_) {
   }

   public void m_6996_(MerchantOffer p_35274_) {
      p_35274_.m_45374_();
      this.f_21363_ = -this.m_8100_();
      this.m_8058_(p_35274_);
      if (this.f_35263_ instanceof ServerPlayer) {
         CriteriaTriggers.f_10585_.m_70959_((ServerPlayer)this.f_35263_, this, p_35274_.m_45368_());
      }

   }

   protected abstract void m_8058_(MerchantOffer p_35299_);

   public boolean m_7826_() {
      return true;
   }

   public void m_7713_(ItemStack p_35316_) {
      if (!this.f_19853_.f_46443_ && this.f_21363_ > -this.m_8100_() + 20) {
         this.f_21363_ = -this.m_8100_();
         this.m_5496_(this.m_6068_(!p_35316_.m_41619_()), this.m_6121_(), this.m_6100_());
      }

   }

   public SoundEvent m_7596_() {
      return SoundEvents.f_12509_;
   }

   protected SoundEvent m_6068_(boolean p_35323_) {
      return p_35323_ ? SoundEvents.f_12509_ : SoundEvents.f_12507_;
   }

   public void m_35310_() {
      this.m_5496_(SoundEvents.f_12504_, this.m_6121_(), this.m_6100_());
   }

   public void m_7380_(CompoundTag p_35301_) {
      super.m_7380_(p_35301_);
      MerchantOffers merchantoffers = this.m_6616_();
      if (!merchantoffers.isEmpty()) {
         p_35301_.m_128365_("Offers", merchantoffers.m_45388_());
      }

      p_35301_.m_128365_("Inventory", this.f_35264_.m_7927_());
   }

   public void m_7378_(CompoundTag p_35290_) {
      super.m_7378_(p_35290_);
      if (p_35290_.m_128425_("Offers", 10)) {
         this.f_35261_ = new MerchantOffers(p_35290_.m_128469_("Offers"));
      }

      this.f_35264_.m_7797_(p_35290_.m_128437_("Inventory", 10));
   }

   @Nullable
   public Entity changeDimension(ServerLevel p_35295_, net.minecraftforge.common.util.ITeleporter teleporter) {
      this.m_7996_();
      return super.changeDimension(p_35295_, teleporter);
   }

   protected void m_7996_() {
      this.m_7189_((Player)null);
   }

   public void m_6667_(DamageSource p_35270_) {
      super.m_6667_(p_35270_);
      this.m_7996_();
   }

   protected void m_35287_(ParticleOptions p_35288_) {
      for(int i = 0; i < 5; ++i) {
         double d0 = this.f_19796_.nextGaussian() * 0.02D;
         double d1 = this.f_19796_.nextGaussian() * 0.02D;
         double d2 = this.f_19796_.nextGaussian() * 0.02D;
         this.f_19853_.m_7106_(p_35288_, this.m_20208_(1.0D), this.m_20187_() + 1.0D, this.m_20262_(1.0D), d0, d1, d2);
      }

   }

   public boolean m_6573_(Player p_35272_) {
      return false;
   }

   public SimpleContainer m_141944_() {
      return this.f_35264_;
   }

   public SlotAccess m_141942_(int p_149995_) {
      int i = p_149995_ - 300;
      return i >= 0 && i < this.f_35264_.m_6643_() ? SlotAccess.m_147292_(this.f_35264_, i) : super.m_141942_(p_149995_);
   }

   public Level m_7133_() {
      return this.f_19853_;
   }

   protected abstract void m_7604_();

   protected void m_35277_(MerchantOffers p_35278_, VillagerTrades.ItemListing[] p_35279_, int p_35280_) {
      Set<Integer> set = Sets.newHashSet();
      if (p_35279_.length > p_35280_) {
         while(set.size() < p_35280_) {
            set.add(this.f_19796_.nextInt(p_35279_.length));
         }
      } else {
         for(int i = 0; i < p_35279_.length; ++i) {
            set.add(i);
         }
      }

      for(Integer integer : set) {
         VillagerTrades.ItemListing villagertrades$itemlisting = p_35279_[integer];
         MerchantOffer merchantoffer = villagertrades$itemlisting.m_5670_(this, this.f_19796_);
         if (merchantoffer != null) {
            p_35278_.add(merchantoffer);
         }
      }

   }

   public Vec3 m_7398_(float p_35318_) {
      float f = Mth.m_14179_(p_35318_, this.f_20884_, this.f_20883_) * ((float)Math.PI / 180F);
      Vec3 vec3 = new Vec3(0.0D, this.m_142469_().m_82376_() - 1.0D, 0.2D);
      return this.m_20318_(p_35318_).m_82549_(vec3.m_82524_(-f));
   }
}
