package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.phys.Vec3;

public class LighthingBoltPredicate {
   public static final LighthingBoltPredicate f_153232_ = new LighthingBoltPredicate(MinMaxBounds.Ints.f_55364_, EntityPredicate.f_36550_);
   private static final String f_153233_ = "blocks_set_on_fire";
   private static final String f_153234_ = "entity_struck";
   private final MinMaxBounds.Ints f_153235_;
   private final EntityPredicate f_153236_;

   private LighthingBoltPredicate(MinMaxBounds.Ints p_153239_, EntityPredicate p_153240_) {
      this.f_153235_ = p_153239_;
      this.f_153236_ = p_153240_;
   }

   public static LighthingBoltPredicate m_153250_(MinMaxBounds.Ints p_153251_) {
      return new LighthingBoltPredicate(p_153251_, EntityPredicate.f_36550_);
   }

   public static LighthingBoltPredicate m_153252_(@Nullable JsonElement p_153253_) {
      if (p_153253_ != null && !p_153253_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_153253_, "lightning");
         return new LighthingBoltPredicate(MinMaxBounds.Ints.m_55373_(jsonobject.get("blocks_set_on_fire")), EntityPredicate.m_36614_(jsonobject.get("entity_struck")));
      } else {
         return f_153232_;
      }
   }

   public JsonElement m_153241_() {
      if (this == f_153232_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("blocks_set_on_fire", this.f_153235_.m_55328_());
         jsonobject.add("entity_struck", this.f_153236_.m_36606_());
         return jsonobject;
      }
   }

   public boolean m_153246_(Entity p_153247_, ServerLevel p_153248_, @Nullable Vec3 p_153249_) {
      if (this == f_153232_) {
         return true;
      } else if (!(p_153247_ instanceof LightningBolt)) {
         return false;
      } else {
         LightningBolt lightningbolt = (LightningBolt)p_153247_;
         return this.f_153235_.m_55390_(lightningbolt.m_147159_()) && (this.f_153236_ == EntityPredicate.f_36550_ || lightningbolt.m_147160_().anyMatch((p_153245_) -> {
            return this.f_153236_.m_36607_(p_153248_, p_153249_, p_153245_);
         }));
      }
   }
}