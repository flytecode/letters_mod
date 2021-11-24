package net.minecraft.world.level.block.entity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SkullBlockEntity extends BlockEntity {
   public static final String f_155729_ = "SkullOwner";
   @Nullable
   private static GameProfileCache f_59755_;
   @Nullable
   private static MinecraftSessionService f_59756_;
   @Nullable
   private static Executor f_182457_;
   @Nullable
   private GameProfile f_59757_;
   private int f_59758_;
   private boolean f_59759_;

   public SkullBlockEntity(BlockPos p_155731_, BlockState p_155732_) {
      super(BlockEntityType.f_58931_, p_155731_, p_155732_);
   }

   public static void m_59764_(GameProfileCache p_59765_) {
      f_59755_ = p_59765_;
   }

   public static void m_59771_(MinecraftSessionService p_59772_) {
      f_59756_ = p_59772_;
   }

   public static void m_182462_(Executor p_182463_) {
      f_182457_ = p_182463_;
   }

   public CompoundTag m_6945_(CompoundTag p_59774_) {
      super.m_6945_(p_59774_);
      if (this.f_59757_ != null) {
         CompoundTag compoundtag = new CompoundTag();
         NbtUtils.m_129230_(compoundtag, this.f_59757_);
         p_59774_.m_128365_("SkullOwner", compoundtag);
      }

      return p_59774_;
   }

   public void m_142466_(CompoundTag p_155745_) {
      super.m_142466_(p_155745_);
      if (p_155745_.m_128425_("SkullOwner", 10)) {
         this.m_59769_(NbtUtils.m_129228_(p_155745_.m_128469_("SkullOwner")));
      } else if (p_155745_.m_128425_("ExtraType", 8)) {
         String s = p_155745_.m_128461_("ExtraType");
         if (!StringUtil.m_14408_(s)) {
            this.m_59769_(new GameProfile((UUID)null, s));
         }
      }

   }

   public static void m_155733_(Level p_155734_, BlockPos p_155735_, BlockState p_155736_, SkullBlockEntity p_155737_) {
      if (p_155734_.m_46753_(p_155735_)) {
         p_155737_.f_59759_ = true;
         ++p_155737_.f_59758_;
      } else {
         p_155737_.f_59759_ = false;
      }

   }

   public float m_59762_(float p_59763_) {
      return this.f_59759_ ? (float)this.f_59758_ + p_59763_ : (float)this.f_59758_;
   }

   @Nullable
   public GameProfile m_59779_() {
      return this.f_59757_;
   }

   @Nullable
   public ClientboundBlockEntityDataPacket m_7033_() {
      return new ClientboundBlockEntityDataPacket(this.f_58858_, 4, this.m_5995_());
   }

   public CompoundTag m_5995_() {
      return this.m_6945_(new CompoundTag());
   }

   public void m_59769_(@Nullable GameProfile p_59770_) {
      synchronized(this) {
         this.f_59757_ = p_59770_;
      }

      this.m_59780_();
   }

   private void m_59780_() {
      m_155738_(this.f_59757_, (p_155747_) -> {
         this.f_59757_ = p_155747_;
         this.m_6596_();
      });
   }

   public static void m_155738_(@Nullable GameProfile p_155739_, Consumer<GameProfile> p_155740_) {
      if (p_155739_ != null && !StringUtil.m_14408_(p_155739_.getName()) && (!p_155739_.isComplete() || !p_155739_.getProperties().containsKey("textures")) && f_59755_ != null && f_59756_ != null) {
         f_59755_.m_143967_(p_155739_.getName(), (p_182470_) -> {
            Util.m_137578_().execute(() -> {
               Util.m_137521_(p_182470_, (p_182479_) -> {
                  Property property = Iterables.getFirst(p_182479_.getProperties().get("textures"), (Property)null);
                  if (property == null) {
                     p_182479_ = f_59756_.fillProfileProperties(p_182479_, true);
                  }

                  GameProfile gameprofile = p_182479_;
                  f_182457_.execute(() -> {
                     f_59755_.m_10991_(gameprofile);
                     p_155740_.accept(gameprofile);
                  });
               }, () -> {
                  f_182457_.execute(() -> {
                     p_155740_.accept(p_155739_);
                  });
               });
            });
         });
      } else {
         p_155740_.accept(p_155739_);
      }
   }
}