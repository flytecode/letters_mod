package com.cs32.lettersmod.saveddata;

import java.util.function.Supplier;

import com.cs32.lettersmod.LettersMod;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

// class that retrieves and stores the contents of the mailbox in world saved data
// from forums https://forums.minecraftforge.net/topic/83420-solved-1152-saving-and-loading-data-per-world/
public class MailboxSavedData extends WorldSavedData implements Supplier {
  public CompoundNBT data = new CompoundNBT();

  public MailboxSavedData() {
    super(LettersMod.MOD_ID);
  }

  public MailboxSavedData(String name) {
    super(name);
  }

  @Override
  public void read(CompoundNBT nbt) {
    data = nbt.getCompound("worldAddress");
  }

  @Override
  public CompoundNBT write(CompoundNBT nbt) {
    nbt.put("worldAddress", data);
    return nbt;
  }

  public static MailboxSavedData forWorld(ServerWorld world) {
    DimensionSavedDataManager storage = world.getSavedData();
    Supplier<MailboxSavedData> sup = new MailboxSavedData();
    MailboxSavedData saver = (MailboxSavedData) storage.getOrCreate(sup, LettersMod.MOD_ID);

    if (saver == null) {
      saver = new MailboxSavedData();
      storage.set(saver);
    }
    return saver;
  }

  @Override
  public Object get() {
    return this;
  }
}