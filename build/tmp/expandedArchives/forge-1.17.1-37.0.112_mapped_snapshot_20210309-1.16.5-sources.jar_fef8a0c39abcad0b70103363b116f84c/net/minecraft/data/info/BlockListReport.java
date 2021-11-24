package net.minecraft.data.info;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockListReport implements DataProvider {
   private static final Gson f_124033_ = (new GsonBuilder()).setPrettyPrinting().create();
   private final DataGenerator f_124034_;

   public BlockListReport(DataGenerator p_124037_) {
      this.f_124034_ = p_124037_;
   }

   public void m_6865_(HashCache p_124040_) throws IOException {
      JsonObject jsonobject = new JsonObject();

      for(Block block : Registry.f_122824_) {
         ResourceLocation resourcelocation = Registry.f_122824_.m_7981_(block);
         JsonObject jsonobject1 = new JsonObject();
         StateDefinition<Block, BlockState> statedefinition = block.m_49965_();
         if (!statedefinition.m_61092_().isEmpty()) {
            JsonObject jsonobject2 = new JsonObject();

            for(Property<?> property : statedefinition.m_61092_()) {
               JsonArray jsonarray = new JsonArray();

               for(Comparable<?> comparable : property.m_6908_()) {
                  jsonarray.add(Util.m_137453_(property, comparable));
               }

               jsonobject2.add(property.m_61708_(), jsonarray);
            }

            jsonobject1.add("properties", jsonobject2);
         }

         JsonArray jsonarray1 = new JsonArray();

         for(BlockState blockstate : statedefinition.m_61056_()) {
            JsonObject jsonobject3 = new JsonObject();
            JsonObject jsonobject4 = new JsonObject();

            for(Property<?> property1 : statedefinition.m_61092_()) {
               jsonobject4.addProperty(property1.m_61708_(), Util.m_137453_(property1, blockstate.m_61143_(property1)));
            }

            if (jsonobject4.size() > 0) {
               jsonobject3.add("properties", jsonobject4);
            }

            jsonobject3.addProperty("id", Block.m_49956_(blockstate));
            if (blockstate == block.m_49966_()) {
               jsonobject3.addProperty("default", true);
            }

            jsonarray1.add(jsonobject3);
         }

         jsonobject1.add("states", jsonarray1);
         jsonobject.add(resourcelocation.toString(), jsonobject1);
      }

      Path path = this.f_124034_.m_123916_().resolve("reports/blocks.json");
      DataProvider.m_123920_(f_124033_, p_124040_, jsonobject, path);
   }

   public String m_6055_() {
      return "Block List";
   }
}