package com.cs32.lettersmod.saveddata;

import java.util.function.Supplier;

import com.cs32.lettersmod.LettersMod;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

// from forums https://forums.minecraftforge.net/topic/83420-solved-1152-saving-and-loading-data-per-world/
public class SavedDataClass extends WorldSavedData implements Supplier {
  private String nbtKey = "";
  public CompoundNBT data = new CompoundNBT();

  public SavedDataClass(String nbtString) {
    super(LettersMod.MOD_ID);
    this.nbtKey = nbtString;
  }

  @Override
  public void read(CompoundNBT nbt) {
    data = nbt.getCompound(nbtKey);
  }

  @Override
  public CompoundNBT write(CompoundNBT nbt) {
    nbt.put(this.nbtKey, data);
    return nbt;
  }

  public SavedDataClass forWorld(ServerWorld world) {
    DimensionSavedDataManager storage = world.getSavedData();
    Supplier<SavedDataClass> sup = new SavedDataClass(this.nbtKey);

    return (SavedDataClass) storage.getOrCreate(sup, LettersMod.MOD_ID);
  }

  @Override
  public Object get() {
    return this;
  }
}