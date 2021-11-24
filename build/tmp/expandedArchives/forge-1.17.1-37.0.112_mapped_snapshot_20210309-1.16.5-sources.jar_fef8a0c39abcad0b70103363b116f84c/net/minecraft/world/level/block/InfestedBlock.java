package net.minecraft.world.level.block;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class InfestedBlock extends Block {
   private final Block f_54174_;
   private static final Map<Block, Block> f_54175_ = Maps.newIdentityHashMap();
   private static final Map<BlockState, BlockState> f_153421_ = Maps.newIdentityHashMap();
   private static final Map<BlockState, BlockState> f_153422_ = Maps.newIdentityHashMap();

   public InfestedBlock(Block p_54178_, BlockBehaviour.Properties p_54179_) {
      super(p_54179_.m_155954_(p_54178_.m_155943_() / 2.0F).m_155956_(0.75F));
      this.f_54174_ = p_54178_;
      f_54175_.put(p_54178_, this);
   }

   public Block m_54192_() {
      return this.f_54174_;
   }

   public static boolean m_54195_(BlockState p_54196_) {
      return f_54175_.containsKey(p_54196_.m_60734_());
   }

   private void m_54180_(ServerLevel p_54181_, BlockPos p_54182_) {
      Silverfish silverfish = EntityType.f_20523_.m_20615_(p_54181_);
      silverfish.m_7678_((double)p_54182_.m_123341_() + 0.5D, (double)p_54182_.m_123342_(), (double)p_54182_.m_123343_() + 0.5D, 0.0F, 0.0F);
      p_54181_.m_7967_(silverfish);
      silverfish.m_21373_();
   }

   public void m_8101_(BlockState p_54188_, ServerLevel p_54189_, BlockPos p_54190_, ItemStack p_54191_) {
      super.m_8101_(p_54188_, p_54189_, p_54190_, p_54191_);
      if (p_54189_.m_46469_().m_46207_(GameRules.f_46136_) && EnchantmentHelper.m_44843_(Enchantments.f_44985_, p_54191_) == 0) {
         this.m_54180_(p_54189_, p_54190_);
      }

   }

   public void m_7592_(Level p_54184_, BlockPos p_54185_, Explosion p_54186_) {
      if (p_54184_ instanceof ServerLevel) {
         this.m_54180_((ServerLevel)p_54184_, p_54185_);
      }

   }

   public static BlockState m_153430_(BlockState p_153431_) {
      return m_153423_(f_153421_, p_153431_, () -> {
         return f_54175_.get(p_153431_.m_60734_()).m_49966_();
      });
   }

   public BlockState m_153432_(BlockState p_153433_) {
      return m_153423_(f_153422_, p_153433_, () -> {
         return this.m_54192_().m_49966_();
      });
   }

   private static BlockState m_153423_(Map<BlockState, BlockState> p_153424_, BlockState p_153425_, Supplier<BlockState> p_153426_) {
      return p_153424_.computeIfAbsent(p_153425_, (p_153429_) -> {
         BlockState blockstate = p_153426_.get();

         for(Property property : p_153429_.m_61147_()) {
            blockstate = blockstate.m_61138_(property) ? blockstate.m_61124_(property, p_153429_.m_61143_(property)) : blockstate;
         }

         return blockstate;
      });
   }
}