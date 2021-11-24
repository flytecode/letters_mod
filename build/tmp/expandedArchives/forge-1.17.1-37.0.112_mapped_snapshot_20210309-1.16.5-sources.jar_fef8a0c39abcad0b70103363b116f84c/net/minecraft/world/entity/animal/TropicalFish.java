package net.minecraft.world.entity.animal;

import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class TropicalFish extends AbstractSchoolingFish {
   public static final String f_149057_ = "BucketVariantTag";
   private static final EntityDataAccessor<Integer> f_30011_ = SynchedEntityData.m_135353_(TropicalFish.class, EntityDataSerializers.f_135028_);
   public static final int f_149061_ = 0;
   public static final int f_149062_ = 1;
   private static final int f_149058_ = 2;
   private static final ResourceLocation[] f_30012_ = new ResourceLocation[]{new ResourceLocation("textures/entity/fish/tropical_a.png"), new ResourceLocation("textures/entity/fish/tropical_b.png")};
   private static final ResourceLocation[] f_30008_ = new ResourceLocation[]{new ResourceLocation("textures/entity/fish/tropical_a_pattern_1.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_2.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_3.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_4.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_5.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_6.png")};
   private static final ResourceLocation[] f_30009_ = new ResourceLocation[]{new ResourceLocation("textures/entity/fish/tropical_b_pattern_1.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_2.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_3.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_4.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_5.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_6.png")};
   private static final int f_149059_ = 6;
   private static final int f_149060_ = 15;
   public static final int[] f_30007_ = new int[]{m_30018_(TropicalFish.Pattern.STRIPEY, DyeColor.ORANGE, DyeColor.GRAY), m_30018_(TropicalFish.Pattern.FLOPPER, DyeColor.GRAY, DyeColor.GRAY), m_30018_(TropicalFish.Pattern.FLOPPER, DyeColor.GRAY, DyeColor.BLUE), m_30018_(TropicalFish.Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.GRAY), m_30018_(TropicalFish.Pattern.SUNSTREAK, DyeColor.BLUE, DyeColor.GRAY), m_30018_(TropicalFish.Pattern.KOB, DyeColor.ORANGE, DyeColor.WHITE), m_30018_(TropicalFish.Pattern.SPOTTY, DyeColor.PINK, DyeColor.LIGHT_BLUE), m_30018_(TropicalFish.Pattern.BLOCKFISH, DyeColor.PURPLE, DyeColor.YELLOW), m_30018_(TropicalFish.Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.RED), m_30018_(TropicalFish.Pattern.SPOTTY, DyeColor.WHITE, DyeColor.YELLOW), m_30018_(TropicalFish.Pattern.GLITTER, DyeColor.WHITE, DyeColor.GRAY), m_30018_(TropicalFish.Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.ORANGE), m_30018_(TropicalFish.Pattern.DASHER, DyeColor.CYAN, DyeColor.PINK), m_30018_(TropicalFish.Pattern.BRINELY, DyeColor.LIME, DyeColor.LIGHT_BLUE), m_30018_(TropicalFish.Pattern.BETTY, DyeColor.RED, DyeColor.WHITE), m_30018_(TropicalFish.Pattern.SNOOPER, DyeColor.GRAY, DyeColor.RED), m_30018_(TropicalFish.Pattern.BLOCKFISH, DyeColor.RED, DyeColor.WHITE), m_30018_(TropicalFish.Pattern.FLOPPER, DyeColor.WHITE, DyeColor.YELLOW), m_30018_(TropicalFish.Pattern.KOB, DyeColor.RED, DyeColor.WHITE), m_30018_(TropicalFish.Pattern.SUNSTREAK, DyeColor.GRAY, DyeColor.WHITE), m_30018_(TropicalFish.Pattern.DASHER, DyeColor.CYAN, DyeColor.YELLOW), m_30018_(TropicalFish.Pattern.FLOPPER, DyeColor.YELLOW, DyeColor.YELLOW)};
   private boolean f_30010_ = true;

   private static int m_30018_(TropicalFish.Pattern p_30019_, DyeColor p_30020_, DyeColor p_30021_) {
      return p_30019_.m_30088_() & 255 | (p_30019_.m_30092_() & 255) << 8 | (p_30020_.m_41060_() & 255) << 16 | (p_30021_.m_41060_() & 255) << 24;
   }

   public TropicalFish(EntityType<? extends TropicalFish> p_30015_, Level p_30016_) {
      super(p_30015_, p_30016_);
   }

   public static String m_30030_(int p_30031_) {
      return "entity.minecraft.tropical_fish.predefined." + p_30031_;
   }

   public static DyeColor m_30050_(int p_30051_) {
      return DyeColor.m_41053_(m_30060_(p_30051_));
   }

   public static DyeColor m_30052_(int p_30053_) {
      return DyeColor.m_41053_(m_30062_(p_30053_));
   }

   public static String m_30054_(int p_30055_) {
      int i = m_30058_(p_30055_);
      int j = m_30064_(p_30055_);
      return "entity.minecraft.tropical_fish.type." + TropicalFish.Pattern.m_30089_(i, j);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_30011_, 0);
   }

   public void m_7380_(CompoundTag p_30033_) {
      super.m_7380_(p_30033_);
      p_30033_.m_128405_("Variant", this.m_30042_());
   }

   public void m_7378_(CompoundTag p_30029_) {
      super.m_7378_(p_30029_);
      this.m_30056_(p_30029_.m_128451_("Variant"));
   }

   public void m_30056_(int p_30057_) {
      this.f_19804_.m_135381_(f_30011_, p_30057_);
   }

   public boolean m_7296_(int p_30035_) {
      return !this.f_30010_;
   }

   public int m_30042_() {
      return this.f_19804_.m_135370_(f_30011_);
   }

   public void m_142146_(ItemStack p_30049_) {
      super.m_142146_(p_30049_);
      CompoundTag compoundtag = p_30049_.m_41784_();
      compoundtag.m_128405_("BucketVariantTag", this.m_30042_());
   }

   public ItemStack m_142563_() {
      return new ItemStack(Items.f_42459_);
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12526_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12527_;
   }

   protected SoundEvent m_7975_(DamageSource p_30039_) {
      return SoundEvents.f_12529_;
   }

   protected SoundEvent m_5699_() {
      return SoundEvents.f_12528_;
   }

   private static int m_30060_(int p_30061_) {
      return (p_30061_ & 16711680) >> 16;
   }

   public float[] m_30043_() {
      return DyeColor.m_41053_(m_30060_(this.m_30042_())).m_41068_();
   }

   private static int m_30062_(int p_30063_) {
      return (p_30063_ & -16777216) >> 24;
   }

   public float[] m_30044_() {
      return DyeColor.m_41053_(m_30062_(this.m_30042_())).m_41068_();
   }

   public static int m_30058_(int p_30059_) {
      return Math.min(p_30059_ & 255, 1);
   }

   public int m_30045_() {
      return m_30058_(this.m_30042_());
   }

   private static int m_30064_(int p_30065_) {
      return Math.min((p_30065_ & '\uff00') >> 8, 5);
   }

   public ResourceLocation m_30046_() {
      return m_30058_(this.m_30042_()) == 0 ? f_30008_[m_30064_(this.m_30042_())] : f_30009_[m_30064_(this.m_30042_())];
   }

   public ResourceLocation m_30047_() {
      return f_30012_[m_30058_(this.m_30042_())];
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_30023_, DifficultyInstance p_30024_, MobSpawnType p_30025_, @Nullable SpawnGroupData p_30026_, @Nullable CompoundTag p_30027_) {
      p_30026_ = super.m_6518_(p_30023_, p_30024_, p_30025_, p_30026_, p_30027_);
      if (p_30025_ == MobSpawnType.BUCKET && p_30027_ != null && p_30027_.m_128425_("BucketVariantTag", 3)) {
         this.m_30056_(p_30027_.m_128451_("BucketVariantTag"));
         return p_30026_;
      } else {
         int i;
         int j;
         int k;
         int l;
         if (p_30026_ instanceof TropicalFish.TropicalFishGroupData) {
            TropicalFish.TropicalFishGroupData tropicalfish$tropicalfishgroupdata = (TropicalFish.TropicalFishGroupData)p_30026_;
            i = tropicalfish$tropicalfishgroupdata.f_30097_;
            j = tropicalfish$tropicalfishgroupdata.f_30098_;
            k = tropicalfish$tropicalfishgroupdata.f_30099_;
            l = tropicalfish$tropicalfishgroupdata.f_30100_;
         } else if ((double)this.f_19796_.nextFloat() < 0.9D) {
            int i1 = Util.m_137542_(f_30007_, this.f_19796_);
            i = i1 & 255;
            j = (i1 & '\uff00') >> 8;
            k = (i1 & 16711680) >> 16;
            l = (i1 & -16777216) >> 24;
            p_30026_ = new TropicalFish.TropicalFishGroupData(this, i, j, k, l);
         } else {
            this.f_30010_ = false;
            i = this.f_19796_.nextInt(2);
            j = this.f_19796_.nextInt(6);
            k = this.f_19796_.nextInt(15);
            l = this.f_19796_.nextInt(15);
         }

         this.m_30056_(i | j << 8 | k << 16 | l << 24);
         return p_30026_;
      }
   }

   static enum Pattern {
      KOB(0, 0),
      SUNSTREAK(0, 1),
      SNOOPER(0, 2),
      DASHER(0, 3),
      BRINELY(0, 4),
      SPOTTY(0, 5),
      FLOPPER(1, 0),
      STRIPEY(1, 1),
      GLITTER(1, 2),
      BLOCKFISH(1, 3),
      BETTY(1, 4),
      CLAYFISH(1, 5);

      private final int f_30078_;
      private final int f_30079_;
      private static final TropicalFish.Pattern[] f_30080_ = values();

      private Pattern(int p_30086_, int p_30087_) {
         this.f_30078_ = p_30086_;
         this.f_30079_ = p_30087_;
      }

      public int m_30088_() {
         return this.f_30078_;
      }

      public int m_30092_() {
         return this.f_30079_;
      }

      public static String m_30089_(int p_30090_, int p_30091_) {
         return f_30080_[p_30091_ + 6 * p_30090_].m_30093_();
      }

      public String m_30093_() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   static class TropicalFishGroupData extends AbstractSchoolingFish.SchoolSpawnGroupData {
      final int f_30097_;
      final int f_30098_;
      final int f_30099_;
      final int f_30100_;

      TropicalFishGroupData(TropicalFish p_30102_, int p_30103_, int p_30104_, int p_30105_, int p_30106_) {
         super(p_30102_);
         this.f_30097_ = p_30103_;
         this.f_30098_ = p_30104_;
         this.f_30099_ = p_30105_;
         this.f_30100_ = p_30106_;
      }
   }
}