package net.minecraft.world.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SpawnEggItem extends Item {
   private static final Map<EntityType<? extends Mob>, SpawnEggItem> f_43201_ = Maps.newIdentityHashMap();
   private final int f_151200_;
   private final int f_151201_;
   private final EntityType<?> f_43204_;

   /** @deprecated Forge: Use {@link net.minecraftforge.common.ForgeSpawnEggItem} instead for suppliers */
   @Deprecated
   public SpawnEggItem(EntityType<? extends Mob> p_43207_, int p_43208_, int p_43209_, Item.Properties p_43210_) {
      super(p_43210_);
      this.f_43204_ = p_43207_;
      this.f_151200_ = p_43208_;
      this.f_151201_ = p_43209_;
      if (p_43207_ != null)
      f_43201_.put(p_43207_, this);
   }

   public InteractionResult m_6225_(UseOnContext p_43223_) {
      Level level = p_43223_.m_43725_();
      if (!(level instanceof ServerLevel)) {
         return InteractionResult.SUCCESS;
      } else {
         ItemStack itemstack = p_43223_.m_43722_();
         BlockPos blockpos = p_43223_.m_8083_();
         Direction direction = p_43223_.m_43719_();
         BlockState blockstate = level.m_8055_(blockpos);
         if (blockstate.m_60713_(Blocks.f_50085_)) {
            BlockEntity blockentity = level.m_7702_(blockpos);
            if (blockentity instanceof SpawnerBlockEntity) {
               BaseSpawner basespawner = ((SpawnerBlockEntity)blockentity).m_59801_();
               EntityType<?> entitytype1 = this.m_43228_(itemstack.m_41783_());
               basespawner.m_45462_(entitytype1);
               blockentity.m_6596_();
               level.m_7260_(blockpos, blockstate, blockstate, 3);
               itemstack.m_41774_(1);
               return InteractionResult.CONSUME;
            }
         }

         BlockPos blockpos1;
         if (blockstate.m_60812_(level, blockpos).m_83281_()) {
            blockpos1 = blockpos;
         } else {
            blockpos1 = blockpos.m_142300_(direction);
         }

         EntityType<?> entitytype = this.m_43228_(itemstack.m_41783_());
         if (entitytype.m_20592_((ServerLevel)level, itemstack, p_43223_.m_43723_(), blockpos1, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
            itemstack.m_41774_(1);
            level.m_142346_(p_43223_.m_43723_(), GameEvent.f_157810_, blockpos);
         }

         return InteractionResult.CONSUME;
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_43225_, Player p_43226_, InteractionHand p_43227_) {
      ItemStack itemstack = p_43226_.m_21120_(p_43227_);
      HitResult hitresult = m_41435_(p_43225_, p_43226_, ClipContext.Fluid.SOURCE_ONLY);
      if (hitresult.m_6662_() != HitResult.Type.BLOCK) {
         return InteractionResultHolder.m_19098_(itemstack);
      } else if (!(p_43225_ instanceof ServerLevel)) {
         return InteractionResultHolder.m_19090_(itemstack);
      } else {
         BlockHitResult blockhitresult = (BlockHitResult)hitresult;
         BlockPos blockpos = blockhitresult.m_82425_();
         if (!(p_43225_.m_8055_(blockpos).m_60734_() instanceof LiquidBlock)) {
            return InteractionResultHolder.m_19098_(itemstack);
         } else if (p_43225_.m_7966_(p_43226_, blockpos) && p_43226_.m_36204_(blockpos, blockhitresult.m_82434_(), itemstack)) {
            EntityType<?> entitytype = this.m_43228_(itemstack.m_41783_());
            if (entitytype.m_20592_((ServerLevel)p_43225_, itemstack, p_43226_, blockpos, MobSpawnType.SPAWN_EGG, false, false) == null) {
               return InteractionResultHolder.m_19098_(itemstack);
            } else {
               if (!p_43226_.m_150110_().f_35937_) {
                  itemstack.m_41774_(1);
               }

               p_43226_.m_36246_(Stats.f_12982_.m_12902_(this));
               p_43225_.m_151552_(GameEvent.f_157810_, p_43226_);
               return InteractionResultHolder.m_19096_(itemstack);
            }
         } else {
            return InteractionResultHolder.m_19100_(itemstack);
         }
      }
   }

   public boolean m_43230_(@Nullable CompoundTag p_43231_, EntityType<?> p_43232_) {
      return Objects.equals(this.m_43228_(p_43231_), p_43232_);
   }

   public int m_43211_(int p_43212_) {
      return p_43212_ == 0 ? this.f_151200_ : this.f_151201_;
   }

   /** @deprecated Forge: call {@link net.minecraftforge.common.ForgeSpawnEggItem#fromEntityType(EntityType)} instead */
   @Deprecated
   @Nullable
   public static SpawnEggItem m_43213_(@Nullable EntityType<?> p_43214_) {
      return f_43201_.get(p_43214_);
   }

   public static Iterable<SpawnEggItem> m_43233_() {
      return Iterables.unmodifiableIterable(f_43201_.values());
   }

   public EntityType<?> m_43228_(@Nullable CompoundTag p_43229_) {
      if (p_43229_ != null && p_43229_.m_128425_("EntityTag", 10)) {
         CompoundTag compoundtag = p_43229_.m_128469_("EntityTag");
         if (compoundtag.m_128425_("id", 8)) {
            return EntityType.m_20632_(compoundtag.m_128461_("id")).orElse(this.f_43204_);
         }
      }

      return this.f_43204_;
   }

   public Optional<Mob> m_43215_(Player p_43216_, Mob p_43217_, EntityType<? extends Mob> p_43218_, ServerLevel p_43219_, Vec3 p_43220_, ItemStack p_43221_) {
      if (!this.m_43230_(p_43221_.m_41783_(), p_43218_)) {
         return Optional.empty();
      } else {
         Mob mob;
         if (p_43217_ instanceof AgeableMob) {
            mob = ((AgeableMob)p_43217_).m_142606_(p_43219_, (AgeableMob)p_43217_);
         } else {
            mob = p_43218_.m_20615_(p_43219_);
         }

         if (mob == null) {
            return Optional.empty();
         } else {
            mob.m_6863_(true);
            if (!mob.m_6162_()) {
               return Optional.empty();
            } else {
               mob.m_7678_(p_43220_.m_7096_(), p_43220_.m_7098_(), p_43220_.m_7094_(), 0.0F, 0.0F);
               p_43219_.m_47205_(mob);
               if (p_43221_.m_41788_()) {
                  mob.m_6593_(p_43221_.m_41786_());
               }

               if (!p_43216_.m_150110_().f_35937_) {
                  p_43221_.m_41774_(1);
               }

               return Optional.of(mob);
            }
         }
      }
   }
}
