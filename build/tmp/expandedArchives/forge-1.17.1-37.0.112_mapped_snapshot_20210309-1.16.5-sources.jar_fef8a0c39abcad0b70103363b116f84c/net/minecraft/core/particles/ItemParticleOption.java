package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class ItemParticleOption implements ParticleOptions {
   public static final ParticleOptions.Deserializer<ItemParticleOption> f_123700_ = new ParticleOptions.Deserializer<ItemParticleOption>() {
      public ItemParticleOption m_5739_(ParticleType<ItemParticleOption> p_123721_, StringReader p_123722_) throws CommandSyntaxException {
         p_123722_.expect(' ');
         ItemParser itemparser = (new ItemParser(p_123722_, false)).m_121032_();
         ItemStack itemstack = (new ItemInput(itemparser.m_121014_(), itemparser.m_121018_())).m_120980_(1, false);
         return new ItemParticleOption(p_123721_, itemstack);
      }

      public ItemParticleOption m_6507_(ParticleType<ItemParticleOption> p_123724_, FriendlyByteBuf p_123725_) {
         return new ItemParticleOption(p_123724_, p_123725_.m_130267_());
      }
   };
   private final ParticleType<ItemParticleOption> f_123701_;
   private final ItemStack f_123702_;

   public static Codec<ItemParticleOption> m_123710_(ParticleType<ItemParticleOption> p_123711_) {
      return ItemStack.f_41582_.xmap((p_123714_) -> {
         return new ItemParticleOption(p_123711_, p_123714_);
      }, (p_123709_) -> {
         return p_123709_.f_123702_;
      });
   }

   public ItemParticleOption(ParticleType<ItemParticleOption> p_123705_, ItemStack p_123706_) {
      this.f_123701_ = p_123705_;
      this.f_123702_ = p_123706_.m_41777_(); //Forge: Fix stack updating after the fact causing particle changes.
   }

   public void m_7711_(FriendlyByteBuf p_123716_) {
      p_123716_.m_130055_(this.f_123702_);
   }

   public String m_5942_() {
      return Registry.f_122829_.m_7981_(this.m_6012_()) + " " + (new ItemInput(this.f_123702_.m_41720_(), this.f_123702_.m_41783_())).m_120988_();
   }

   public ParticleType<ItemParticleOption> m_6012_() {
      return this.f_123701_;
   }

   public ItemStack m_123718_() {
      return this.f_123702_;
   }
}
