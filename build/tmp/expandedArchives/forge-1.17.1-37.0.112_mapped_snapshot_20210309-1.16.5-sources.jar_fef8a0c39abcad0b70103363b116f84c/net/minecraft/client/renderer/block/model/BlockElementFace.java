package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockElementFace {
   public static final int f_173415_ = -1;
   public final Direction f_111354_;
   public final int f_111355_;
   public final String f_111356_;
   public final BlockFaceUV f_111357_;

   public BlockElementFace(@Nullable Direction p_111359_, int p_111360_, String p_111361_, BlockFaceUV p_111362_) {
      this.f_111354_ = p_111359_;
      this.f_111355_ = p_111360_;
      this.f_111356_ = p_111361_;
      this.f_111357_ = p_111362_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Deserializer implements JsonDeserializer<BlockElementFace> {
      private static final int f_173416_ = -1;

      public BlockElementFace deserialize(JsonElement p_111365_, Type p_111366_, JsonDeserializationContext p_111367_) throws JsonParseException {
         JsonObject jsonobject = p_111365_.getAsJsonObject();
         Direction direction = this.m_111372_(jsonobject);
         int i = this.m_111368_(jsonobject);
         String s = this.m_111370_(jsonobject);
         BlockFaceUV blockfaceuv = p_111367_.deserialize(jsonobject, BlockFaceUV.class);
         return new BlockElementFace(direction, i, s, blockfaceuv);
      }

      protected int m_111368_(JsonObject p_111369_) {
         return GsonHelper.m_13824_(p_111369_, "tintindex", -1);
      }

      private String m_111370_(JsonObject p_111371_) {
         return GsonHelper.m_13906_(p_111371_, "texture");
      }

      @Nullable
      private Direction m_111372_(JsonObject p_111373_) {
         String s = GsonHelper.m_13851_(p_111373_, "cullface", "");
         return Direction.m_122402_(s);
      }
   }
}