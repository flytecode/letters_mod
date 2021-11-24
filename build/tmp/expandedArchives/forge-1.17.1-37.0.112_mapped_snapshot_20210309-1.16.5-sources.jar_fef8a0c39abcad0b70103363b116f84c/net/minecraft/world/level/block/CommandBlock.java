package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandBlock extends BaseEntityBlock implements GameMasterBlock {
   private static final Logger f_51795_ = LogManager.getLogger();
   public static final DirectionProperty f_51793_ = DirectionalBlock.f_52588_;
   public static final BooleanProperty f_51794_ = BlockStateProperties.f_61428_;
   private final boolean f_153078_;

   public CommandBlock(BlockBehaviour.Properties p_153080_, boolean p_153081_) {
      super(p_153080_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_51793_, Direction.NORTH).m_61124_(f_51794_, Boolean.valueOf(false)));
      this.f_153078_ = p_153081_;
   }

   public BlockEntity m_142194_(BlockPos p_153083_, BlockState p_153084_) {
      CommandBlockEntity commandblockentity = new CommandBlockEntity(p_153083_, p_153084_);
      commandblockentity.m_59137_(this.f_153078_);
      return commandblockentity;
   }

   public void m_6861_(BlockState p_51838_, Level p_51839_, BlockPos p_51840_, Block p_51841_, BlockPos p_51842_, boolean p_51843_) {
      if (!p_51839_.f_46443_) {
         BlockEntity blockentity = p_51839_.m_7702_(p_51840_);
         if (blockentity instanceof CommandBlockEntity) {
            CommandBlockEntity commandblockentity = (CommandBlockEntity)blockentity;
            boolean flag = p_51839_.m_46753_(p_51840_);
            boolean flag1 = commandblockentity.m_59142_();
            commandblockentity.m_59135_(flag);
            if (!flag1 && !commandblockentity.m_59143_() && commandblockentity.m_59148_() != CommandBlockEntity.Mode.SEQUENCE) {
               if (flag) {
                  commandblockentity.m_59146_();
                  p_51839_.m_6219_().m_5945_(p_51840_, this, 1);
               }

            }
         }
      }
   }

   public void m_7458_(BlockState p_51816_, ServerLevel p_51817_, BlockPos p_51818_, Random p_51819_) {
      BlockEntity blockentity = p_51817_.m_7702_(p_51818_);
      if (blockentity instanceof CommandBlockEntity) {
         CommandBlockEntity commandblockentity = (CommandBlockEntity)blockentity;
         BaseCommandBlock basecommandblock = commandblockentity.m_59141_();
         boolean flag = !StringUtil.m_14408_(basecommandblock.m_45438_());
         CommandBlockEntity.Mode commandblockentity$mode = commandblockentity.m_59148_();
         boolean flag1 = commandblockentity.m_59145_();
         if (commandblockentity$mode == CommandBlockEntity.Mode.AUTO) {
            commandblockentity.m_59146_();
            if (flag1) {
               this.m_51831_(p_51816_, p_51817_, p_51818_, basecommandblock, flag);
            } else if (commandblockentity.m_59151_()) {
               basecommandblock.m_45410_(0);
            }

            if (commandblockentity.m_59142_() || commandblockentity.m_59143_()) {
               p_51817_.m_6219_().m_5945_(p_51818_, this, 1);
            }
         } else if (commandblockentity$mode == CommandBlockEntity.Mode.REDSTONE) {
            if (flag1) {
               this.m_51831_(p_51816_, p_51817_, p_51818_, basecommandblock, flag);
            } else if (commandblockentity.m_59151_()) {
               basecommandblock.m_45410_(0);
            }
         }

         p_51817_.m_46717_(p_51818_, this);
      }

   }

   private void m_51831_(BlockState p_51832_, Level p_51833_, BlockPos p_51834_, BaseCommandBlock p_51835_, boolean p_51836_) {
      if (p_51836_) {
         p_51835_.m_45414_(p_51833_);
      } else {
         p_51835_.m_45410_(0);
      }

      m_51809_(p_51833_, p_51834_, p_51832_.m_61143_(f_51793_));
   }

   public InteractionResult m_6227_(BlockState p_51825_, Level p_51826_, BlockPos p_51827_, Player p_51828_, InteractionHand p_51829_, BlockHitResult p_51830_) {
      BlockEntity blockentity = p_51826_.m_7702_(p_51827_);
      if (blockentity instanceof CommandBlockEntity && p_51828_.m_36337_()) {
         p_51828_.m_7698_((CommandBlockEntity)blockentity);
         return InteractionResult.m_19078_(p_51826_.f_46443_);
      } else {
         return InteractionResult.PASS;
      }
   }

   public boolean m_7278_(BlockState p_51814_) {
      return true;
   }

   public int m_6782_(BlockState p_51821_, Level p_51822_, BlockPos p_51823_) {
      BlockEntity blockentity = p_51822_.m_7702_(p_51823_);
      return blockentity instanceof CommandBlockEntity ? ((CommandBlockEntity)blockentity).m_59141_().m_45436_() : 0;
   }

   public void m_6402_(Level p_51804_, BlockPos p_51805_, BlockState p_51806_, LivingEntity p_51807_, ItemStack p_51808_) {
      BlockEntity blockentity = p_51804_.m_7702_(p_51805_);
      if (blockentity instanceof CommandBlockEntity) {
         CommandBlockEntity commandblockentity = (CommandBlockEntity)blockentity;
         BaseCommandBlock basecommandblock = commandblockentity.m_59141_();
         if (p_51808_.m_41788_()) {
            basecommandblock.m_45423_(p_51808_.m_41786_());
         }

         if (!p_51804_.f_46443_) {
            if (p_51808_.m_41737_("BlockEntityTag") == null) {
               basecommandblock.m_45428_(p_51804_.m_46469_().m_46207_(GameRules.f_46144_));
               commandblockentity.m_59137_(this.f_153078_);
            }

            if (commandblockentity.m_59148_() == CommandBlockEntity.Mode.SEQUENCE) {
               boolean flag = p_51804_.m_46753_(p_51805_);
               commandblockentity.m_59135_(flag);
            }
         }

      }
   }

   public RenderShape m_7514_(BlockState p_51853_) {
      return RenderShape.MODEL;
   }

   public BlockState m_6843_(BlockState p_51848_, Rotation p_51849_) {
      return p_51848_.m_61124_(f_51793_, p_51849_.m_55954_(p_51848_.m_61143_(f_51793_)));
   }

   public BlockState m_6943_(BlockState p_51845_, Mirror p_51846_) {
      return p_51845_.m_60717_(p_51846_.m_54846_(p_51845_.m_61143_(f_51793_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51851_) {
      p_51851_.m_61104_(f_51793_, f_51794_);
   }

   public BlockState m_5573_(BlockPlaceContext p_51800_) {
      return this.m_49966_().m_61124_(f_51793_, p_51800_.m_7820_().m_122424_());
   }

   private static void m_51809_(Level p_51810_, BlockPos p_51811_, Direction p_51812_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_51811_.m_122032_();
      GameRules gamerules = p_51810_.m_46469_();

      int i;
      BlockState blockstate;
      for(i = gamerules.m_46215_(GameRules.f_46152_); i-- > 0; p_51812_ = blockstate.m_61143_(f_51793_)) {
         blockpos$mutableblockpos.m_122173_(p_51812_);
         blockstate = p_51810_.m_8055_(blockpos$mutableblockpos);
         Block block = blockstate.m_60734_();
         if (!blockstate.m_60713_(Blocks.f_50448_)) {
            break;
         }

         BlockEntity blockentity = p_51810_.m_7702_(blockpos$mutableblockpos);
         if (!(blockentity instanceof CommandBlockEntity)) {
            break;
         }

         CommandBlockEntity commandblockentity = (CommandBlockEntity)blockentity;
         if (commandblockentity.m_59148_() != CommandBlockEntity.Mode.SEQUENCE) {
            break;
         }

         if (commandblockentity.m_59142_() || commandblockentity.m_59143_()) {
            BaseCommandBlock basecommandblock = commandblockentity.m_59141_();
            if (commandblockentity.m_59146_()) {
               if (!basecommandblock.m_45414_(p_51810_)) {
                  break;
               }

               p_51810_.m_46717_(blockpos$mutableblockpos, block);
            } else if (commandblockentity.m_59151_()) {
               basecommandblock.m_45410_(0);
            }
         }
      }

      if (i <= 0) {
         int j = Math.max(gamerules.m_46215_(GameRules.f_46152_), 0);
         f_51795_.warn("Command Block chain tried to execute more than {} steps!", (int)j);
      }

   }
}