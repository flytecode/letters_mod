package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPickItemPacket;
import net.minecraft.network.protocol.game.ServerboundPlaceRecipePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.StatsCounter;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class MultiPlayerGameMode {
   private static final Logger f_105188_ = LogManager.getLogger();
   private final Minecraft f_105189_;
   private final ClientPacketListener f_105190_;
   private BlockPos f_105191_ = new BlockPos(-1, -1, -1);
   private ItemStack f_105192_ = ItemStack.f_41583_;
   private float f_105193_;
   private float f_105194_;
   private int f_105195_;
   private boolean f_105196_;
   private GameType f_105197_ = GameType.f_151492_;
   @Nullable
   private GameType f_105198_;
   private final Object2ObjectLinkedOpenHashMap<Pair<BlockPos, ServerboundPlayerActionPacket.Action>, Vec3> f_105199_ = new Object2ObjectLinkedOpenHashMap<>();
   private static final int f_171798_ = 50;
   private int f_105200_;

   public MultiPlayerGameMode(Minecraft p_105203_, ClientPacketListener p_105204_) {
      this.f_105189_ = p_105203_;
      this.f_105190_ = p_105204_;
   }

   public void m_105221_(Player p_105222_) {
      this.f_105197_.m_46398_(p_105222_.m_150110_());
   }

   public void m_171805_(GameType p_171806_, @Nullable GameType p_171807_) {
      this.f_105197_ = p_171806_;
      this.f_105198_ = p_171807_;
      this.f_105197_.m_46398_(this.f_105189_.f_91074_.m_150110_());
   }

   public void m_105279_(GameType p_105280_) {
      if (p_105280_ != this.f_105197_) {
         this.f_105198_ = this.f_105197_;
      }

      this.f_105197_ = p_105280_;
      this.f_105197_.m_46398_(this.f_105189_.f_91074_.m_150110_());
   }

   public boolean m_105205_() {
      return this.f_105197_.m_46409_();
   }

   public boolean m_105267_(BlockPos p_105268_) {
      if (f_105189_.f_91074_.m_21205_().onBlockStartBreak(p_105268_, f_105189_.f_91074_)) return false;
      if (this.f_105189_.f_91074_.m_36187_(this.f_105189_.f_91073_, p_105268_, this.f_105197_)) {
         return false;
      } else {
         Level level = this.f_105189_.f_91073_;
         BlockState blockstate = level.m_8055_(p_105268_);
         if (!this.f_105189_.f_91074_.m_21205_().m_41720_().m_6777_(blockstate, level, p_105268_, this.f_105189_.f_91074_)) {
            return false;
         } else {
            Block block = blockstate.m_60734_();
            if (block instanceof GameMasterBlock && !this.f_105189_.f_91074_.m_36337_()) {
               return false;
            } else if (blockstate.m_60795_()) {
               return false;
            } else {
               FluidState fluidstate = level.m_6425_(p_105268_);
               boolean flag = blockstate.removedByPlayer(level, p_105268_, f_105189_.f_91074_, false, fluidstate);
               if (flag) {
                  block.m_6786_(level, p_105268_, blockstate);
               }

               return flag;
            }
         }
      }
   }

   public boolean m_105269_(BlockPos p_105270_, Direction p_105271_) {
      if (this.f_105189_.f_91074_.m_36187_(this.f_105189_.f_91073_, p_105270_, this.f_105197_)) {
         return false;
      } else if (!this.f_105189_.f_91073_.m_6857_().m_61937_(p_105270_)) {
         return false;
      } else {
         if (this.f_105197_.m_46408_()) {
            BlockState blockstate = this.f_105189_.f_91073_.m_8055_(p_105270_);
            this.f_105189_.m_91301_().m_120581_(this.f_105189_.f_91073_, p_105270_, blockstate, 1.0F);
            this.m_105272_(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, p_105270_, p_105271_);
            if (!net.minecraftforge.common.ForgeHooks.onLeftClickBlock(this.f_105189_.f_91074_, p_105270_, p_105271_).isCanceled())
            this.m_105267_(p_105270_);
            this.f_105195_ = 5;
         } else if (!this.f_105196_ || !this.m_105281_(p_105270_)) {
            if (this.f_105196_) {
               this.m_105272_(ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK, this.f_105191_, p_105271_);
            }
            net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock event = net.minecraftforge.common.ForgeHooks.onLeftClickBlock(this.f_105189_.f_91074_, p_105270_, p_105271_);

            BlockState blockstate1 = this.f_105189_.f_91073_.m_8055_(p_105270_);
            this.f_105189_.m_91301_().m_120581_(this.f_105189_.f_91073_, p_105270_, blockstate1, 0.0F);
            this.m_105272_(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, p_105270_, p_105271_);
            boolean flag = !blockstate1.m_60795_();
            if (flag && this.f_105193_ == 0.0F) {
               if (event.getUseBlock() != net.minecraftforge.eventbus.api.Event.Result.DENY)
               blockstate1.m_60686_(this.f_105189_.f_91073_, p_105270_, this.f_105189_.f_91074_);
            }

            if (event.getUseItem() == net.minecraftforge.eventbus.api.Event.Result.DENY) return true;
            if (flag && blockstate1.m_60625_(this.f_105189_.f_91074_, this.f_105189_.f_91074_.f_19853_, p_105270_) >= 1.0F) {
               this.m_105267_(p_105270_);
            } else {
               this.f_105196_ = true;
               this.f_105191_ = p_105270_;
               this.f_105192_ = this.f_105189_.f_91074_.m_21205_();
               this.f_105193_ = 0.0F;
               this.f_105194_ = 0.0F;
               this.f_105189_.f_91073_.m_6801_(this.f_105189_.f_91074_.m_142049_(), this.f_105191_, (int)(this.f_105193_ * 10.0F) - 1);
            }
         }

         return true;
      }
   }

   public void m_105276_() {
      if (this.f_105196_) {
         BlockState blockstate = this.f_105189_.f_91073_.m_8055_(this.f_105191_);
         this.f_105189_.m_91301_().m_120581_(this.f_105189_.f_91073_, this.f_105191_, blockstate, -1.0F);
         this.m_105272_(ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK, this.f_105191_, Direction.DOWN);
         this.f_105196_ = false;
         this.f_105193_ = 0.0F;
         this.f_105189_.f_91073_.m_6801_(this.f_105189_.f_91074_.m_142049_(), this.f_105191_, -1);
         this.f_105189_.f_91074_.m_36334_();
      }

   }

   public boolean m_105283_(BlockPos p_105284_, Direction p_105285_) {
      this.m_105297_();
      if (this.f_105195_ > 0) {
         --this.f_105195_;
         return true;
      } else if (this.f_105197_.m_46408_() && this.f_105189_.f_91073_.m_6857_().m_61937_(p_105284_)) {
         this.f_105195_ = 5;
         BlockState blockstate1 = this.f_105189_.f_91073_.m_8055_(p_105284_);
         this.f_105189_.m_91301_().m_120581_(this.f_105189_.f_91073_, p_105284_, blockstate1, 1.0F);
         this.m_105272_(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, p_105284_, p_105285_);
         if (!net.minecraftforge.common.ForgeHooks.onLeftClickBlock(this.f_105189_.f_91074_, p_105284_, p_105285_).isCanceled())
         this.m_105267_(p_105284_);
         return true;
      } else if (this.m_105281_(p_105284_)) {
         BlockState blockstate = this.f_105189_.f_91073_.m_8055_(p_105284_);
         if (blockstate.m_60795_()) {
            this.f_105196_ = false;
            return false;
         } else {
            this.f_105193_ += blockstate.m_60625_(this.f_105189_.f_91074_, this.f_105189_.f_91074_.f_19853_, p_105284_);
            if (this.f_105194_ % 4.0F == 0.0F) {
               SoundType soundtype = blockstate.getSoundType(this.f_105189_.f_91073_, p_105284_, this.f_105189_.f_91074_);
               this.f_105189_.m_91106_().m_120367_(new SimpleSoundInstance(soundtype.m_56778_(), SoundSource.BLOCKS, (soundtype.m_56773_() + 1.0F) / 8.0F, soundtype.m_56774_() * 0.5F, p_105284_));
            }

            ++this.f_105194_;
            this.f_105189_.m_91301_().m_120581_(this.f_105189_.f_91073_, p_105284_, blockstate, Mth.m_14036_(this.f_105193_, 0.0F, 1.0F));
            if (net.minecraftforge.common.ForgeHooks.onLeftClickBlock(this.f_105189_.f_91074_, p_105284_, p_105285_).getUseItem() == net.minecraftforge.eventbus.api.Event.Result.DENY) return true;
            if (this.f_105193_ >= 1.0F) {
               this.f_105196_ = false;
               this.m_105272_(ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK, p_105284_, p_105285_);
               this.m_105267_(p_105284_);
               this.f_105193_ = 0.0F;
               this.f_105194_ = 0.0F;
               this.f_105195_ = 5;
            }

            this.f_105189_.f_91073_.m_6801_(this.f_105189_.f_91074_.m_142049_(), this.f_105191_, (int)(this.f_105193_ * 10.0F) - 1);
            return true;
         }
      } else {
         return this.m_105269_(p_105284_, p_105285_);
      }
   }

   public float m_105286_() {
      float attrib = (float)f_105189_.f_91074_.m_21051_(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).m_22135_();
      return this.f_105197_.m_46408_() ? attrib : attrib - 0.5F;
   }

   public void m_105287_() {
      this.m_105297_();
      if (this.f_105190_.m_6198_().m_129536_()) {
         this.f_105190_.m_6198_().m_129483_();
      } else {
         this.f_105190_.m_6198_().m_129541_();
      }

   }

   private boolean m_105281_(BlockPos p_105282_) {
      ItemStack itemstack = this.f_105189_.f_91074_.m_21205_();
      boolean flag = this.f_105192_.m_41619_() && itemstack.m_41619_();
      if (!this.f_105192_.m_41619_() && !itemstack.m_41619_()) {
         flag = !this.f_105192_.shouldCauseBlockBreakReset(itemstack);
      }

      return p_105282_.equals(this.f_105191_) && flag;
   }

   private void m_105297_() {
      int i = this.f_105189_.f_91074_.m_150109_().f_35977_;
      if (i != this.f_105200_) {
         this.f_105200_ = i;
         this.f_105190_.m_104955_(new ServerboundSetCarriedItemPacket(this.f_105200_));
      }

   }

   public InteractionResult m_105262_(LocalPlayer p_105263_, ClientLevel p_105264_, InteractionHand p_105265_, BlockHitResult p_105266_) {
      this.m_105297_();
      BlockPos blockpos = p_105266_.m_82425_();
      if (!this.f_105189_.f_91073_.m_6857_().m_61937_(blockpos)) {
         return InteractionResult.FAIL;
      } else {
         ItemStack itemstack = p_105263_.m_21120_(p_105265_);
         net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock event = net.minecraftforge.common.ForgeHooks
                 .onRightClickBlock(p_105263_, p_105265_, blockpos, p_105266_);
         if (event.isCanceled()) {
            this.f_105190_.m_104955_(new ServerboundUseItemOnPacket(p_105265_, p_105266_));
            return event.getCancellationResult();
         }
         if (this.f_105197_ == GameType.SPECTATOR) {
            this.f_105190_.m_104955_(new ServerboundUseItemOnPacket(p_105265_, p_105266_));
            return InteractionResult.SUCCESS;
         } else {
            UseOnContext useoncontext = new UseOnContext(p_105263_, p_105265_, p_105266_);
            if (event.getUseItem() != net.minecraftforge.eventbus.api.Event.Result.DENY) {
               InteractionResult result = itemstack.onItemUseFirst(useoncontext);
               if (result != InteractionResult.PASS) {
                  this.f_105190_.m_104955_(new ServerboundUseItemOnPacket(p_105265_, p_105266_));
                  return result;
               }
            }
            boolean flag = !p_105263_.m_21205_().doesSneakBypassUse(p_105264_,blockpos,p_105263_) || !p_105263_.m_21206_().doesSneakBypassUse(p_105264_,blockpos,p_105263_);
            boolean flag1 = p_105263_.m_36341_() && flag;
            if (event.getUseBlock() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || (event.getUseBlock() != net.minecraftforge.eventbus.api.Event.Result.DENY && !flag1)) {
               InteractionResult interactionresult = p_105264_.m_8055_(blockpos).m_60664_(p_105264_, p_105263_, p_105265_, p_105266_);
               if (interactionresult.m_19077_()) {
                  this.f_105190_.m_104955_(new ServerboundUseItemOnPacket(p_105265_, p_105266_));
                  return interactionresult;
               }
            }

            this.f_105190_.m_104955_(new ServerboundUseItemOnPacket(p_105265_, p_105266_));
            if (event.getUseItem() == net.minecraftforge.eventbus.api.Event.Result.DENY) return InteractionResult.PASS;
            if (event.getUseItem() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || (!itemstack.m_41619_() && !p_105263_.m_36335_().m_41519_(itemstack.m_41720_()))) {
               InteractionResult interactionresult1;
               if (this.f_105197_.m_46408_()) {
                  int i = itemstack.m_41613_();
                  interactionresult1 = itemstack.m_41661_(useoncontext);
                  itemstack.m_41764_(i);
               } else {
                  interactionresult1 = itemstack.m_41661_(useoncontext);
               }

               return interactionresult1;
            } else {
               return InteractionResult.PASS;
            }
         }
      }
   }

   public InteractionResult m_105235_(Player p_105236_, Level p_105237_, InteractionHand p_105238_) {
      if (this.f_105197_ == GameType.SPECTATOR) {
         return InteractionResult.PASS;
      } else {
         this.m_105297_();
         this.f_105190_.m_104955_(new ServerboundMovePlayerPacket.PosRot(p_105236_.m_20185_(), p_105236_.m_20186_(), p_105236_.m_20189_(), p_105236_.m_146908_(), p_105236_.m_146909_(), p_105236_.m_20096_()));
         this.f_105190_.m_104955_(new ServerboundUseItemPacket(p_105238_));
         ItemStack itemstack = p_105236_.m_21120_(p_105238_);
         if (p_105236_.m_36335_().m_41519_(itemstack.m_41720_())) {
            return InteractionResult.PASS;
         } else {
            InteractionResult cancelResult = net.minecraftforge.common.ForgeHooks.onItemRightClick(p_105236_, p_105238_);
            if (cancelResult != null) return cancelResult;
            InteractionResultHolder<ItemStack> interactionresultholder = itemstack.m_41682_(p_105237_, p_105236_, p_105238_);
            ItemStack itemstack1 = interactionresultholder.m_19095_();
            if (itemstack1 != itemstack) {
               p_105236_.m_21008_(p_105238_, itemstack1);
               if (itemstack1.m_41619_()) net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(p_105236_, itemstack, p_105238_);
            }

            return interactionresultholder.m_19089_();
         }
      }
   }

   public LocalPlayer m_105246_(ClientLevel p_105247_, StatsCounter p_105248_, ClientRecipeBook p_105249_) {
      return this.m_105250_(p_105247_, p_105248_, p_105249_, false, false);
   }

   public LocalPlayer m_105250_(ClientLevel p_105251_, StatsCounter p_105252_, ClientRecipeBook p_105253_, boolean p_105254_, boolean p_105255_) {
      return new LocalPlayer(this.f_105189_, p_105251_, this.f_105190_, p_105252_, p_105253_, p_105254_, p_105255_);
   }

   public void m_105223_(Player p_105224_, Entity p_105225_) {
      this.m_105297_();
      this.f_105190_.m_104955_(ServerboundInteractPacket.m_179605_(p_105225_, p_105224_.m_6144_()));
      if (this.f_105197_ != GameType.SPECTATOR) {
         p_105224_.m_5706_(p_105225_);
         p_105224_.m_36334_();
      }

   }

   public InteractionResult m_105226_(Player p_105227_, Entity p_105228_, InteractionHand p_105229_) {
      this.m_105297_();
      this.f_105190_.m_104955_(ServerboundInteractPacket.m_179608_(p_105228_, p_105227_.m_6144_(), p_105229_));
      if (this.f_105197_ == GameType.SPECTATOR) return InteractionResult.PASS; // don't fire for spectators to match non-specific EntityInteract
      InteractionResult cancelResult = net.minecraftforge.common.ForgeHooks.onInteractEntity(p_105227_, p_105228_, p_105229_);
      if(cancelResult != null) return cancelResult;
      return this.f_105197_ == GameType.SPECTATOR ? InteractionResult.PASS : p_105227_.m_36157_(p_105228_, p_105229_);
   }

   public InteractionResult m_105230_(Player p_105231_, Entity p_105232_, EntityHitResult p_105233_, InteractionHand p_105234_) {
      this.m_105297_();
      Vec3 vec3 = p_105233_.m_82450_().m_82492_(p_105232_.m_20185_(), p_105232_.m_20186_(), p_105232_.m_20189_());
      this.f_105190_.m_104955_(ServerboundInteractPacket.m_179612_(p_105232_, p_105231_.m_6144_(), p_105234_, vec3));
      if (this.f_105197_ == GameType.SPECTATOR) return InteractionResult.PASS; // don't fire for spectators to match non-specific EntityInteract
      InteractionResult cancelResult = net.minecraftforge.common.ForgeHooks.onInteractEntityAt(p_105231_, p_105232_, p_105233_, p_105234_);
      if(cancelResult != null) return cancelResult;
      return this.f_105197_ == GameType.SPECTATOR ? InteractionResult.PASS : p_105232_.m_7111_(p_105231_, vec3, p_105234_);
   }

   public void m_171799_(int p_171800_, int p_171801_, int p_171802_, ClickType p_171803_, Player p_171804_) {
      AbstractContainerMenu abstractcontainermenu = p_171804_.f_36096_;
      NonNullList<Slot> nonnulllist = abstractcontainermenu.f_38839_;
      int i = nonnulllist.size();
      List<ItemStack> list = Lists.newArrayListWithCapacity(i);

      for(Slot slot : nonnulllist) {
         list.add(slot.m_7993_().m_41777_());
      }

      abstractcontainermenu.m_150399_(p_171801_, p_171802_, p_171803_, p_171804_);
      Int2ObjectMap<ItemStack> int2objectmap = new Int2ObjectOpenHashMap<>();

      for(int j = 0; j < i; ++j) {
         ItemStack itemstack = list.get(j);
         ItemStack itemstack1 = nonnulllist.get(j).m_7993_();
         if (!ItemStack.m_41728_(itemstack, itemstack1)) {
            int2objectmap.put(j, itemstack1.m_41777_());
         }
      }

      this.f_105190_.m_104955_(new ServerboundContainerClickPacket(p_171800_, abstractcontainermenu.m_182424_(), p_171801_, p_171802_, p_171803_, abstractcontainermenu.m_142621_().m_41777_(), int2objectmap));
   }

   public void m_105217_(int p_105218_, Recipe<?> p_105219_, boolean p_105220_) {
      this.f_105190_.m_104955_(new ServerboundPlaceRecipePacket(p_105218_, p_105219_, p_105220_));
   }

   public void m_105208_(int p_105209_, int p_105210_) {
      this.f_105190_.m_104955_(new ServerboundContainerButtonClickPacket(p_105209_, p_105210_));
   }

   public void m_105241_(ItemStack p_105242_, int p_105243_) {
      if (this.f_105197_.m_46408_()) {
         this.f_105190_.m_104955_(new ServerboundSetCreativeModeSlotPacket(p_105243_, p_105242_));
      }

   }

   public void m_105239_(ItemStack p_105240_) {
      if (this.f_105197_.m_46408_() && !p_105240_.m_41619_()) {
         this.f_105190_.m_104955_(new ServerboundSetCreativeModeSlotPacket(-1, p_105240_));
      }

   }

   public void m_105277_(Player p_105278_) {
      this.m_105297_();
      this.f_105190_.m_104955_(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.RELEASE_USE_ITEM, BlockPos.f_121853_, Direction.DOWN));
      p_105278_.m_21253_();
   }

   public boolean m_105288_() {
      return this.f_105197_.m_46409_();
   }

   public boolean m_105289_() {
      return !this.f_105197_.m_46408_();
   }

   public boolean m_105290_() {
      return this.f_105197_.m_46408_();
   }

   public boolean m_105291_() {
      return this.f_105197_.m_46408_();
   }

   public boolean m_105292_() {
      return this.f_105189_.f_91074_.m_20159_() && this.f_105189_.f_91074_.m_20202_() instanceof AbstractHorse;
   }

   public boolean m_105293_() {
      return this.f_105197_ == GameType.SPECTATOR;
   }

   @Nullable
   public GameType m_105294_() {
      return this.f_105198_;
   }

   public GameType m_105295_() {
      return this.f_105197_;
   }

   public boolean m_105296_() {
      return this.f_105196_;
   }

   public void m_105206_(int p_105207_) {
      this.f_105190_.m_104955_(new ServerboundPickItemPacket(p_105207_));
   }

   private void m_105272_(ServerboundPlayerActionPacket.Action p_105273_, BlockPos p_105274_, Direction p_105275_) {
      LocalPlayer localplayer = this.f_105189_.f_91074_;
      this.f_105199_.put(Pair.of(p_105274_, p_105273_), localplayer.m_20182_());
      this.f_105190_.m_104955_(new ServerboundPlayerActionPacket(p_105273_, p_105274_, p_105275_));
   }

   public void m_105256_(ClientLevel p_105257_, BlockPos p_105258_, BlockState p_105259_, ServerboundPlayerActionPacket.Action p_105260_, boolean p_105261_) {
      Vec3 vec3 = this.f_105199_.remove(Pair.of(p_105258_, p_105260_));
      BlockState blockstate = p_105257_.m_8055_(p_105258_);
      if ((vec3 == null || !p_105261_ || p_105260_ != ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK && blockstate != p_105259_) && blockstate != p_105259_) {
         p_105257_.m_104755_(p_105258_, p_105259_);
         Player player = this.f_105189_.f_91074_;
         if (vec3 != null && p_105257_ == player.f_19853_ && player.m_20039_(p_105258_, p_105259_)) {
            player.m_20248_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
         }
      }

      while(this.f_105199_.size() >= 50) {
         Pair<BlockPos, ServerboundPlayerActionPacket.Action> pair = this.f_105199_.firstKey();
         this.f_105199_.removeFirst();
         f_105188_.error("Too many unacked block actions, dropping {}", (Object)pair);
      }

   }
}
