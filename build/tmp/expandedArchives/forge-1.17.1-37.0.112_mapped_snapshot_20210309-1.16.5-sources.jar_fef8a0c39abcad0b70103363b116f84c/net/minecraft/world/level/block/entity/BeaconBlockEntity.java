package net.minecraft.world.level.block.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.LockCode;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

public class BeaconBlockEntity extends BlockEntity implements MenuProvider {
   private static final int f_155085_ = 4;
   public static final MobEffect[][] f_58646_ = new MobEffect[][]{{MobEffects.f_19596_, MobEffects.f_19598_}, {MobEffects.f_19606_, MobEffects.f_19603_}, {MobEffects.f_19600_}, {MobEffects.f_19605_}};
   private static final Set<MobEffect> f_58647_ = Arrays.stream(f_58646_).flatMap(Arrays::stream).collect(Collectors.toSet());
   public static final int f_155081_ = 0;
   public static final int f_155082_ = 1;
   public static final int f_155083_ = 2;
   public static final int f_155084_ = 3;
   private static final int f_155086_ = 10;
   List<BeaconBlockEntity.BeaconBeamSection> f_58648_ = Lists.newArrayList();
   private List<BeaconBlockEntity.BeaconBeamSection> f_58649_ = Lists.newArrayList();
   int f_58650_;
   private int f_58651_;
   @Nullable
   MobEffect f_58652_;
   @Nullable
   MobEffect f_58653_;
   @Nullable
   private Component f_58654_;
   private LockCode f_58655_ = LockCode.f_19102_;
   private final ContainerData f_58656_ = new ContainerData() {
      public int m_6413_(int p_58711_) {
         switch(p_58711_) {
         case 0:
            return BeaconBlockEntity.this.f_58650_;
         case 1:
            return MobEffect.m_19459_(BeaconBlockEntity.this.f_58652_);
         case 2:
            return MobEffect.m_19459_(BeaconBlockEntity.this.f_58653_);
         default:
            return 0;
         }
      }

      public void m_8050_(int p_58713_, int p_58714_) {
         switch(p_58713_) {
         case 0:
            BeaconBlockEntity.this.f_58650_ = p_58714_;
            break;
         case 1:
            if (!BeaconBlockEntity.this.f_58857_.f_46443_ && !BeaconBlockEntity.this.f_58648_.isEmpty()) {
               BeaconBlockEntity.m_155103_(BeaconBlockEntity.this.f_58857_, BeaconBlockEntity.this.f_58858_, SoundEvents.f_11739_);
            }

            BeaconBlockEntity.this.f_58652_ = BeaconBlockEntity.m_58686_(p_58714_);
            break;
         case 2:
            BeaconBlockEntity.this.f_58653_ = BeaconBlockEntity.m_58686_(p_58714_);
         }

      }

      public int m_6499_() {
         return 3;
      }
   };

   public BeaconBlockEntity(BlockPos p_155088_, BlockState p_155089_) {
      super(BlockEntityType.f_58930_, p_155088_, p_155089_);
   }

   public static void m_155107_(Level p_155108_, BlockPos p_155109_, BlockState p_155110_, BeaconBlockEntity p_155111_) {
      int i = p_155109_.m_123341_();
      int j = p_155109_.m_123342_();
      int k = p_155109_.m_123343_();
      BlockPos blockpos;
      if (p_155111_.f_58651_ < j) {
         blockpos = p_155109_;
         p_155111_.f_58649_ = Lists.newArrayList();
         p_155111_.f_58651_ = p_155109_.m_123342_() - 1;
      } else {
         blockpos = new BlockPos(i, p_155111_.f_58651_ + 1, k);
      }

      BeaconBlockEntity.BeaconBeamSection beaconblockentity$beaconbeamsection = p_155111_.f_58649_.isEmpty() ? null : p_155111_.f_58649_.get(p_155111_.f_58649_.size() - 1);
      int l = p_155108_.m_6924_(Heightmap.Types.WORLD_SURFACE, i, k);

      for(int i1 = 0; i1 < 10 && blockpos.m_123342_() <= l; ++i1) {
         BlockState blockstate = p_155108_.m_8055_(blockpos);
         Block block = blockstate.m_60734_();
         float[] afloat = blockstate.getBeaconColorMultiplier(p_155108_, blockpos, p_155109_);
         if (afloat != null) {
            if (p_155111_.f_58649_.size() <= 1) {
               beaconblockentity$beaconbeamsection = new BeaconBlockEntity.BeaconBeamSection(afloat);
               p_155111_.f_58649_.add(beaconblockentity$beaconbeamsection);
            } else if (beaconblockentity$beaconbeamsection != null) {
               if (Arrays.equals(afloat, beaconblockentity$beaconbeamsection.f_58715_)) {
                  beaconblockentity$beaconbeamsection.m_58719_();
               } else {
                  beaconblockentity$beaconbeamsection = new BeaconBlockEntity.BeaconBeamSection(new float[]{(beaconblockentity$beaconbeamsection.f_58715_[0] + afloat[0]) / 2.0F, (beaconblockentity$beaconbeamsection.f_58715_[1] + afloat[1]) / 2.0F, (beaconblockentity$beaconbeamsection.f_58715_[2] + afloat[2]) / 2.0F});
                  p_155111_.f_58649_.add(beaconblockentity$beaconbeamsection);
               }
            }
         } else {
            if (beaconblockentity$beaconbeamsection == null || blockstate.m_60739_(p_155108_, blockpos) >= 15 && !blockstate.m_60713_(Blocks.f_50752_)) {
               p_155111_.f_58649_.clear();
               p_155111_.f_58651_ = l;
               break;
            }

            beaconblockentity$beaconbeamsection.m_58719_();
         }

         blockpos = blockpos.m_7494_();
         ++p_155111_.f_58651_;
      }

      int j1 = p_155111_.f_58650_;
      if (p_155108_.m_46467_() % 80L == 0L) {
         if (!p_155111_.f_58648_.isEmpty()) {
            p_155111_.f_58650_ = m_155092_(p_155108_, i, j, k);
         }

         if (p_155111_.f_58650_ > 0 && !p_155111_.f_58648_.isEmpty()) {
            m_155097_(p_155108_, p_155109_, p_155111_.f_58650_, p_155111_.f_58652_, p_155111_.f_58653_);
            m_155103_(p_155108_, p_155109_, SoundEvents.f_11737_);
         }
      }

      if (p_155111_.f_58651_ >= l) {
         p_155111_.f_58651_ = p_155108_.m_141937_() - 1;
         boolean flag = j1 > 0;
         p_155111_.f_58648_ = p_155111_.f_58649_;
         if (!p_155108_.f_46443_) {
            boolean flag1 = p_155111_.f_58650_ > 0;
            if (!flag && flag1) {
               m_155103_(p_155108_, p_155109_, SoundEvents.f_11736_);

               for(ServerPlayer serverplayer : p_155108_.m_45976_(ServerPlayer.class, (new AABB((double)i, (double)j, (double)k, (double)i, (double)(j - 4), (double)k)).m_82377_(10.0D, 5.0D, 10.0D))) {
                  CriteriaTriggers.f_10578_.m_148029_(serverplayer, p_155111_.f_58650_);
               }
            } else if (flag && !flag1) {
               m_155103_(p_155108_, p_155109_, SoundEvents.f_11738_);
            }
         }
      }

   }

   private static int m_155092_(Level p_155093_, int p_155094_, int p_155095_, int p_155096_) {
      int i = 0;

      for(int j = 1; j <= 4; i = j++) {
         int k = p_155095_ - j;
         if (k < p_155093_.m_141937_()) {
            break;
         }

         boolean flag = true;

         for(int l = p_155094_ - j; l <= p_155094_ + j && flag; ++l) {
            for(int i1 = p_155096_ - j; i1 <= p_155096_ + j; ++i1) {
               if (!p_155093_.m_8055_(new BlockPos(l, k, i1)).m_60620_(BlockTags.f_13079_)) {
                  flag = false;
                  break;
               }
            }
         }

         if (!flag) {
            break;
         }
      }

      return i;
   }

   public void m_7651_() {
      m_155103_(this.f_58857_, this.f_58858_, SoundEvents.f_11738_);
      super.m_7651_();
   }

   private static void m_155097_(Level p_155098_, BlockPos p_155099_, int p_155100_, @Nullable MobEffect p_155101_, @Nullable MobEffect p_155102_) {
      if (!p_155098_.f_46443_ && p_155101_ != null) {
         double d0 = (double)(p_155100_ * 10 + 10);
         int i = 0;
         if (p_155100_ >= 4 && p_155101_ == p_155102_) {
            i = 1;
         }

         int j = (9 + p_155100_ * 2) * 20;
         AABB aabb = (new AABB(p_155099_)).m_82400_(d0).m_82363_(0.0D, (double)p_155098_.m_141928_(), 0.0D);
         List<Player> list = p_155098_.m_45976_(Player.class, aabb);

         for(Player player : list) {
            player.m_7292_(new MobEffectInstance(p_155101_, j, i, true, true));
         }

         if (p_155100_ >= 4 && p_155101_ != p_155102_ && p_155102_ != null) {
            for(Player player1 : list) {
               player1.m_7292_(new MobEffectInstance(p_155102_, j, 0, true, true));
            }
         }

      }
   }

   public static void m_155103_(Level p_155104_, BlockPos p_155105_, SoundEvent p_155106_) {
      p_155104_.m_5594_((Player)null, p_155105_, p_155106_, SoundSource.BLOCKS, 1.0F, 1.0F);
   }

   public List<BeaconBlockEntity.BeaconBeamSection> m_58702_() {
      return (List<BeaconBlockEntity.BeaconBeamSection>)(this.f_58650_ == 0 ? ImmutableList.of() : this.f_58648_);
   }

   @Nullable
   public ClientboundBlockEntityDataPacket m_7033_() {
      return new ClientboundBlockEntityDataPacket(this.f_58858_, 3, this.m_5995_());
   }

   public CompoundTag m_5995_() {
      return this.m_6945_(new CompoundTag());
   }

   @Nullable
   static MobEffect m_58686_(int p_58687_) {
      MobEffect mobeffect = MobEffect.m_19453_(p_58687_);
      return f_58647_.contains(mobeffect) ? mobeffect : null;
   }

   public void m_142466_(CompoundTag p_155113_) {
      super.m_142466_(p_155113_);
      this.f_58652_ = m_58686_(p_155113_.m_128451_("Primary"));
      this.f_58653_ = m_58686_(p_155113_.m_128451_("Secondary"));
      if (p_155113_.m_128425_("CustomName", 8)) {
         this.f_58654_ = Component.Serializer.m_130701_(p_155113_.m_128461_("CustomName"));
      }

      this.f_58655_ = LockCode.m_19111_(p_155113_);
   }

   public CompoundTag m_6945_(CompoundTag p_58680_) {
      super.m_6945_(p_58680_);
      p_58680_.m_128405_("Primary", MobEffect.m_19459_(this.f_58652_));
      p_58680_.m_128405_("Secondary", MobEffect.m_19459_(this.f_58653_));
      p_58680_.m_128405_("Levels", this.f_58650_);
      if (this.f_58654_ != null) {
         p_58680_.m_128359_("CustomName", Component.Serializer.m_130703_(this.f_58654_));
      }

      this.f_58655_.m_19109_(p_58680_);
      return p_58680_;
   }

   public void m_58681_(@Nullable Component p_58682_) {
      this.f_58654_ = p_58682_;
   }

   @Nullable
   public AbstractContainerMenu m_7208_(int p_58696_, Inventory p_58697_, Player p_58698_) {
      return BaseContainerBlockEntity.m_58629_(p_58698_, this.f_58655_, this.m_5446_()) ? new BeaconMenu(p_58696_, p_58697_, this.f_58656_, ContainerLevelAccess.m_39289_(this.f_58857_, this.m_58899_())) : null;
   }

   public Component m_5446_() {
      return (Component)(this.f_58654_ != null ? this.f_58654_ : new TranslatableComponent("container.beacon"));
   }

   public void m_142339_(Level p_155091_) {
      super.m_142339_(p_155091_);
      this.f_58651_ = p_155091_.m_141937_() - 1;
   }

   public static class BeaconBeamSection {
      final float[] f_58715_;
      private int f_58716_;

      public BeaconBeamSection(float[] p_58718_) {
         this.f_58715_ = p_58718_;
         this.f_58716_ = 1;
      }

      protected void m_58719_() {
         ++this.f_58716_;
      }

      public float[] m_58722_() {
         return this.f_58715_;
      }

      public int m_58723_() {
         return this.f_58716_;
      }
   }
}
