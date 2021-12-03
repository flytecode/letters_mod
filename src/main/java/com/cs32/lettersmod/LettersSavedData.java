package com.cs32.lettersmod;

import java.util.function.Supplier;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

// from forums https://forums.minecraftforge.net/topic/83420-solved-1152-saving-and-loading-data-per-world/
public class LettersSavedData extends WorldSavedData implements Supplier {
  public CompoundNBT data = new CompoundNBT();

  public LettersSavedData() {
    super(LettersMod.MOD_ID);
  }

  public LettersSavedData(String name) {
    super(name);
  }

  @Override
  public void read(CompoundNBT nbt) {
    data = nbt.getCompound("MyCompound");
  }

  @Override
  public CompoundNBT write(CompoundNBT nbt) {
    nbt.put("MyCompound", data);
    return nbt;
  }

  public static LettersSavedData forWorld(ServerWorld world) {
    DimensionSavedDataManager storage = world.getSavedData();
    Supplier<LettersSavedData> sup = new LettersSavedData();
    LettersSavedData saver = (LettersSavedData) storage.getOrCreate(sup, LettersMod.MOD_ID);

    if (saver == null) {
      saver = new LettersSavedData();
      storage.set(saver);
    }
    return saver;
  }

  @Override
  public Object get() {
    return this;
  }
}