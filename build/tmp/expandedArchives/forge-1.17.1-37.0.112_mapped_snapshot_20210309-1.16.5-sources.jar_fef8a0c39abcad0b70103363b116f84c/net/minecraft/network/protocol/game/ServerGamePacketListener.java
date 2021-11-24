package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketListener;

public interface ServerGamePacketListener extends PacketListener {
   void m_7953_(ServerboundSwingPacket p_133781_);

   void m_7388_(ServerboundChatPacket p_133743_);

   void m_6272_(ServerboundClientCommandPacket p_133744_);

   void m_5617_(ServerboundClientInformationPacket p_133745_);

   void m_6557_(ServerboundContainerButtonClickPacket p_133748_);

   void m_5914_(ServerboundContainerClickPacket p_133749_);

   void m_7191_(ServerboundPlaceRecipePacket p_133762_);

   void m_7951_(ServerboundContainerClosePacket p_133750_);

   void m_7423_(ServerboundCustomPayloadPacket p_133751_);

   void m_6946_(ServerboundInteractPacket p_133754_);

   void m_5683_(ServerboundKeepAlivePacket p_133756_);

   void m_7185_(ServerboundMovePlayerPacket p_133758_);

   void m_142110_(ServerboundPongPacket p_179536_);

   void m_6828_(ServerboundPlayerAbilitiesPacket p_133763_);

   void m_7502_(ServerboundPlayerActionPacket p_133764_);

   void m_5681_(ServerboundPlayerCommandPacket p_133765_);

   void m_5918_(ServerboundPlayerInputPacket p_133766_);

   void m_7798_(ServerboundSetCarriedItemPacket p_133774_);

   void m_5964_(ServerboundSetCreativeModeSlotPacket p_133777_);

   void m_5527_(ServerboundSignUpdatePacket p_133780_);

   void m_6371_(ServerboundUseItemOnPacket p_133783_);

   void m_5760_(ServerboundUseItemPacket p_133784_);

   void m_6936_(ServerboundTeleportToEntityPacket p_133782_);

   void m_7529_(ServerboundResourcePackPacket p_133770_);

   void m_5938_(ServerboundPaddleBoatPacket p_133760_);

   void m_5659_(ServerboundMoveVehiclePacket p_133759_);

   void m_7376_(ServerboundAcceptTeleportationPacket p_133740_);

   void m_7411_(ServerboundRecipeBookSeenRecipePacket p_133768_);

   void m_7982_(ServerboundRecipeBookChangeSettingsPacket p_133767_);

   void m_6947_(ServerboundSeenAdvancementsPacket p_133771_);

   void m_7741_(ServerboundCommandSuggestionPacket p_133746_);

   void m_7192_(ServerboundSetCommandBlockPacket p_133775_);

   void m_6629_(ServerboundSetCommandMinecartPacket p_133776_);

   void m_7965_(ServerboundPickItemPacket p_133761_);

   void m_5591_(ServerboundRenameItemPacket p_133769_);

   void m_5712_(ServerboundSetBeaconPacket p_133773_);

   void m_7424_(ServerboundSetStructureBlockPacket p_133779_);

   void m_6321_(ServerboundSelectTradePacket p_133772_);

   void m_6829_(ServerboundEditBookPacket p_133752_);

   void m_7548_(ServerboundEntityTagQuery p_133753_);

   void m_6780_(ServerboundBlockEntityTagQuery p_133741_);

   void m_8019_(ServerboundSetJigsawBlockPacket p_133778_);

   void m_6449_(ServerboundJigsawGeneratePacket p_133755_);

   void m_7477_(ServerboundChangeDifficultyPacket p_133742_);

   void m_7728_(ServerboundLockDifficultyPacket p_133757_);
}