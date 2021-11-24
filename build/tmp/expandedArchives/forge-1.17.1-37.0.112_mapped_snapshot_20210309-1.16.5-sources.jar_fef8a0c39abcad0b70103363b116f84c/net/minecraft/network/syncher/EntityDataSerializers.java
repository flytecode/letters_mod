package net.minecraft.network.syncher;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.Rotations;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class EntityDataSerializers {
   private static final CrudeIncrementalIntIdentityHashBiMap<EntityDataSerializer<?>> f_135046_ = new CrudeIncrementalIntIdentityHashBiMap<>(16);
   public static final EntityDataSerializer<Byte> f_135027_ = new EntityDataSerializer<Byte>() {
      public void m_6856_(FriendlyByteBuf p_135062_, Byte p_135063_) {
         p_135062_.writeByte(p_135063_);
      }

      public Byte m_6709_(FriendlyByteBuf p_135068_) {
         return p_135068_.readByte();
      }

      public Byte m_7020_(Byte p_135056_) {
         return p_135056_;
      }
   };
   public static final EntityDataSerializer<Integer> f_135028_ = new EntityDataSerializer<Integer>() {
      public void m_6856_(FriendlyByteBuf p_135107_, Integer p_135108_) {
         p_135107_.m_130130_(p_135108_);
      }

      public Integer m_6709_(FriendlyByteBuf p_135113_) {
         return p_135113_.m_130242_();
      }

      public Integer m_7020_(Integer p_135101_) {
         return p_135101_;
      }
   };
   public static final EntityDataSerializer<Float> f_135029_ = new EntityDataSerializer<Float>() {
      public void m_6856_(FriendlyByteBuf p_135122_, Float p_135123_) {
         p_135122_.writeFloat(p_135123_);
      }

      public Float m_6709_(FriendlyByteBuf p_135128_) {
         return p_135128_.readFloat();
      }

      public Float m_7020_(Float p_135116_) {
         return p_135116_;
      }
   };
   public static final EntityDataSerializer<String> f_135030_ = new EntityDataSerializer<String>() {
      public void m_6856_(FriendlyByteBuf p_135140_, String p_135141_) {
         p_135140_.m_130070_(p_135141_);
      }

      public String m_6709_(FriendlyByteBuf p_135143_) {
         return p_135143_.m_130277_();
      }

      public String m_7020_(String p_135133_) {
         return p_135133_;
      }
   };
   public static final EntityDataSerializer<Component> f_135031_ = new EntityDataSerializer<Component>() {
      public void m_6856_(FriendlyByteBuf p_135153_, Component p_135154_) {
         p_135153_.m_130083_(p_135154_);
      }

      public Component m_6709_(FriendlyByteBuf p_135158_) {
         return p_135158_.m_130238_();
      }

      public Component m_7020_(Component p_135156_) {
         return p_135156_;
      }
   };
   public static final EntityDataSerializer<Optional<Component>> f_135032_ = new EntityDataSerializer<Optional<Component>>() {
      public void m_6856_(FriendlyByteBuf p_135170_, Optional<Component> p_135171_) {
         if (p_135171_.isPresent()) {
            p_135170_.writeBoolean(true);
            p_135170_.m_130083_(p_135171_.get());
         } else {
            p_135170_.writeBoolean(false);
         }

      }

      public Optional<Component> m_6709_(FriendlyByteBuf p_135173_) {
         return p_135173_.readBoolean() ? Optional.of(p_135173_.m_130238_()) : Optional.empty();
      }

      public Optional<Component> m_7020_(Optional<Component> p_135163_) {
         return p_135163_;
      }
   };
   public static final EntityDataSerializer<ItemStack> f_135033_ = new EntityDataSerializer<ItemStack>() {
      public void m_6856_(FriendlyByteBuf p_135182_, ItemStack p_135183_) {
         p_135182_.m_130055_(p_135183_);
      }

      public ItemStack m_6709_(FriendlyByteBuf p_135188_) {
         return p_135188_.m_130267_();
      }

      public ItemStack m_7020_(ItemStack p_135176_) {
         return p_135176_.m_41777_();
      }
   };
   public static final EntityDataSerializer<Optional<BlockState>> f_135034_ = new EntityDataSerializer<Optional<BlockState>>() {
      public void m_6856_(FriendlyByteBuf p_135200_, Optional<BlockState> p_135201_) {
         if (p_135201_.isPresent()) {
            p_135200_.m_130130_(Block.m_49956_(p_135201_.get()));
         } else {
            p_135200_.m_130130_(0);
         }

      }

      public Optional<BlockState> m_6709_(FriendlyByteBuf p_135203_) {
         int i = p_135203_.m_130242_();
         return i == 0 ? Optional.empty() : Optional.of(Block.m_49803_(i));
      }

      public Optional<BlockState> m_7020_(Optional<BlockState> p_135193_) {
         return p_135193_;
      }
   };
   public static final EntityDataSerializer<Boolean> f_135035_ = new EntityDataSerializer<Boolean>() {
      public void m_6856_(FriendlyByteBuf p_135212_, Boolean p_135213_) {
         p_135212_.writeBoolean(p_135213_);
      }

      public Boolean m_6709_(FriendlyByteBuf p_135218_) {
         return p_135218_.readBoolean();
      }

      public Boolean m_7020_(Boolean p_135206_) {
         return p_135206_;
      }
   };
   public static final EntityDataSerializer<ParticleOptions> f_135036_ = new EntityDataSerializer<ParticleOptions>() {
      public void m_6856_(FriendlyByteBuf p_135227_, ParticleOptions p_135228_) {
         p_135227_.m_130130_(Registry.f_122829_.m_7447_(p_135228_.m_6012_()));
         p_135228_.m_7711_(p_135227_);
      }

      public ParticleOptions m_6709_(FriendlyByteBuf p_135236_) {
         return this.m_135229_(p_135236_, Registry.f_122829_.m_7942_(p_135236_.m_130242_()));
      }

      private <T extends ParticleOptions> T m_135229_(FriendlyByteBuf p_135230_, ParticleType<T> p_135231_) {
         return p_135231_.m_123743_().m_6507_(p_135231_, p_135230_);
      }

      public ParticleOptions m_7020_(ParticleOptions p_135221_) {
         return p_135221_;
      }
   };
   public static final EntityDataSerializer<Rotations> f_135037_ = new EntityDataSerializer<Rotations>() {
      public void m_6856_(FriendlyByteBuf p_135245_, Rotations p_135246_) {
         p_135245_.writeFloat(p_135246_.m_123156_());
         p_135245_.writeFloat(p_135246_.m_123157_());
         p_135245_.writeFloat(p_135246_.m_123158_());
      }

      public Rotations m_6709_(FriendlyByteBuf p_135251_) {
         return new Rotations(p_135251_.readFloat(), p_135251_.readFloat(), p_135251_.readFloat());
      }

      public Rotations m_7020_(Rotations p_135239_) {
         return p_135239_;
      }
   };
   public static final EntityDataSerializer<BlockPos> f_135038_ = new EntityDataSerializer<BlockPos>() {
      public void m_6856_(FriendlyByteBuf p_135260_, BlockPos p_135261_) {
         p_135260_.m_130064_(p_135261_);
      }

      public BlockPos m_6709_(FriendlyByteBuf p_135266_) {
         return p_135266_.m_130135_();
      }

      public BlockPos m_7020_(BlockPos p_135254_) {
         return p_135254_;
      }
   };
   public static final EntityDataSerializer<Optional<BlockPos>> f_135039_ = new EntityDataSerializer<Optional<BlockPos>>() {
      public void m_6856_(FriendlyByteBuf p_135278_, Optional<BlockPos> p_135279_) {
         p_135278_.writeBoolean(p_135279_.isPresent());
         if (p_135279_.isPresent()) {
            p_135278_.m_130064_(p_135279_.get());
         }

      }

      public Optional<BlockPos> m_6709_(FriendlyByteBuf p_135281_) {
         return !p_135281_.readBoolean() ? Optional.empty() : Optional.of(p_135281_.m_130135_());
      }

      public Optional<BlockPos> m_7020_(Optional<BlockPos> p_135271_) {
         return p_135271_;
      }
   };
   public static final EntityDataSerializer<Direction> f_135040_ = new EntityDataSerializer<Direction>() {
      public void m_6856_(FriendlyByteBuf p_135290_, Direction p_135291_) {
         p_135290_.m_130068_(p_135291_);
      }

      public Direction m_6709_(FriendlyByteBuf p_135296_) {
         return p_135296_.m_130066_(Direction.class);
      }

      public Direction m_7020_(Direction p_135284_) {
         return p_135284_;
      }
   };
   public static final EntityDataSerializer<Optional<UUID>> f_135041_ = new EntityDataSerializer<Optional<UUID>>() {
      public void m_6856_(FriendlyByteBuf p_135308_, Optional<UUID> p_135309_) {
         p_135308_.writeBoolean(p_135309_.isPresent());
         if (p_135309_.isPresent()) {
            p_135308_.m_130077_(p_135309_.get());
         }

      }

      public Optional<UUID> m_6709_(FriendlyByteBuf p_135311_) {
         return !p_135311_.readBoolean() ? Optional.empty() : Optional.of(p_135311_.m_130259_());
      }

      public Optional<UUID> m_7020_(Optional<UUID> p_135301_) {
         return p_135301_;
      }
   };
   public static final EntityDataSerializer<CompoundTag> f_135042_ = new EntityDataSerializer<CompoundTag>() {
      public void m_6856_(FriendlyByteBuf p_135323_, CompoundTag p_135324_) {
         p_135323_.m_130079_(p_135324_);
      }

      public CompoundTag m_6709_(FriendlyByteBuf p_135326_) {
         return p_135326_.m_130260_();
      }

      public CompoundTag m_7020_(CompoundTag p_135316_) {
         return p_135316_.m_6426_();
      }
   };
   public static final EntityDataSerializer<VillagerData> f_135043_ = new EntityDataSerializer<VillagerData>() {
      public void m_6856_(FriendlyByteBuf p_135335_, VillagerData p_135336_) {
         p_135335_.m_130130_(Registry.f_122868_.m_7447_(p_135336_.m_35560_()));
         p_135335_.m_130130_(Registry.f_122869_.m_7447_(p_135336_.m_35571_()));
         p_135335_.m_130130_(p_135336_.m_35576_());
      }

      public VillagerData m_6709_(FriendlyByteBuf p_135341_) {
         return new VillagerData(Registry.f_122868_.m_7942_(p_135341_.m_130242_()), Registry.f_122869_.m_7942_(p_135341_.m_130242_()), p_135341_.m_130242_());
      }

      public VillagerData m_7020_(VillagerData p_135329_) {
         return p_135329_;
      }
   };
   public static final EntityDataSerializer<OptionalInt> f_135044_ = new EntityDataSerializer<OptionalInt>() {
      public void m_6856_(FriendlyByteBuf p_135080_, OptionalInt p_135081_) {
         p_135080_.m_130130_(p_135081_.orElse(-1) + 1);
      }

      public OptionalInt m_6709_(FriendlyByteBuf p_135083_) {
         int i = p_135083_.m_130242_();
         return i == 0 ? OptionalInt.empty() : OptionalInt.of(i - 1);
      }

      public OptionalInt m_7020_(OptionalInt p_135073_) {
         return p_135073_;
      }
   };
   public static final EntityDataSerializer<Pose> f_135045_ = new EntityDataSerializer<Pose>() {
      public void m_6856_(FriendlyByteBuf p_135092_, Pose p_135093_) {
         p_135092_.m_130068_(p_135093_);
      }

      public Pose m_6709_(FriendlyByteBuf p_135098_) {
         return p_135098_.m_130066_(Pose.class);
      }

      public Pose m_7020_(Pose p_135086_) {
         return p_135086_;
      }
   };

   public static void m_135050_(EntityDataSerializer<?> p_135051_) {
      if (f_135046_.m_13569_(p_135051_) >= 256) throw new RuntimeException("Vanilla DataSerializer ID limit exceeded");
   }

   @Nullable
   public static EntityDataSerializer<?> m_135048_(int p_135049_) {
      return net.minecraftforge.common.ForgeHooks.getSerializer(p_135049_, f_135046_);
   }

   public static int m_135052_(EntityDataSerializer<?> p_135053_) {
      return net.minecraftforge.common.ForgeHooks.getSerializerId(p_135053_, f_135046_);
   }

   private EntityDataSerializers() {
   }

   static {
      m_135050_(f_135027_);
      m_135050_(f_135028_);
      m_135050_(f_135029_);
      m_135050_(f_135030_);
      m_135050_(f_135031_);
      m_135050_(f_135032_);
      m_135050_(f_135033_);
      m_135050_(f_135035_);
      m_135050_(f_135037_);
      m_135050_(f_135038_);
      m_135050_(f_135039_);
      m_135050_(f_135040_);
      m_135050_(f_135041_);
      m_135050_(f_135034_);
      m_135050_(f_135042_);
      m_135050_(f_135036_);
      m_135050_(f_135043_);
      m_135050_(f_135044_);
      m_135050_(f_135045_);
   }
}
