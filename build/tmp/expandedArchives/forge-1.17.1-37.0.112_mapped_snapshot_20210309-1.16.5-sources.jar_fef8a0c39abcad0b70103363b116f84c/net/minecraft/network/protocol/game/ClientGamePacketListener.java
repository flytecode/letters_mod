package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketListener;

public interface ClientGamePacketListener extends PacketListener {
   void m_6771_(ClientboundAddEntityPacket p_131367_);

   void m_7708_(ClientboundAddExperienceOrbPacket p_131368_);

   void m_142024_(ClientboundAddVibrationSignalPacket p_178542_);

   void m_6965_(ClientboundAddMobPacket p_131369_);

   void m_7957_(ClientboundSetObjectivePacket p_131438_);

   void m_6433_(ClientboundAddPaintingPacket p_131370_);

   void m_6482_(ClientboundAddPlayerPacket p_131371_);

   void m_7791_(ClientboundAnimatePacket p_131372_);

   void m_7271_(ClientboundAwardStatsPacket p_131373_);

   void m_8076_(ClientboundRecipePacket p_131417_);

   void m_5943_(ClientboundBlockDestructionPacket p_131375_);

   void m_8047_(ClientboundOpenSignEditorPacket p_131410_);

   void m_7545_(ClientboundBlockEntityDataPacket p_131376_);

   void m_7364_(ClientboundBlockEventPacket p_131377_);

   void m_6773_(ClientboundBlockUpdatePacket p_131378_);

   void m_5784_(ClientboundChatPacket p_131381_);

   void m_5771_(ClientboundSectionBlocksUpdatePacket p_131423_);

   void m_7633_(ClientboundMapItemDataPacket p_131404_);

   void m_7776_(ClientboundContainerClosePacket p_131385_);

   void m_6837_(ClientboundContainerSetContentPacket p_131386_);

   void m_6905_(ClientboundHorseScreenOpenPacket p_131397_);

   void m_7257_(ClientboundContainerSetDataPacket p_131387_);

   void m_5735_(ClientboundContainerSetSlotPacket p_131388_);

   void m_7413_(ClientboundCustomPayloadPacket p_131390_);

   void m_6008_(ClientboundDisconnectPacket p_131392_);

   void m_7628_(ClientboundEntityEventPacket p_131393_);

   void m_5599_(ClientboundSetEntityLinkPacket p_131433_);

   void m_6403_(ClientboundSetPassengersPacket p_131439_);

   void m_7345_(ClientboundExplodePacket p_131394_);

   void m_7616_(ClientboundGameEventPacket p_131396_);

   void m_7231_(ClientboundKeepAlivePacket p_131398_);

   void m_5623_(ClientboundLevelChunkPacket p_131399_);

   void m_5729_(ClientboundForgetLevelChunkPacket p_131395_);

   void m_7704_(ClientboundLevelEventPacket p_131400_);

   void m_5998_(ClientboundLoginPacket p_131403_);

   void m_7865_(ClientboundMoveEntityPacket p_131406_);

   void m_5682_(ClientboundPlayerPositionPacket p_131416_);

   void m_7406_(ClientboundLevelParticlesPacket p_131401_);

   void m_141955_(ClientboundPingPacket p_178545_);

   void m_5767_(ClientboundPlayerAbilitiesPacket p_131412_);

   void m_7039_(ClientboundPlayerInfoPacket p_131414_);

   void m_182047_(ClientboundRemoveEntitiesPacket p_182700_);

   void m_6476_(ClientboundRemoveMobEffectPacket p_131419_);

   void m_7992_(ClientboundRespawnPacket p_131421_);

   void m_6176_(ClientboundRotateHeadPacket p_131422_);

   void m_5612_(ClientboundSetCarriedItemPacket p_131427_);

   void m_5556_(ClientboundSetDisplayObjectivePacket p_131431_);

   void m_6455_(ClientboundSetEntityDataPacket p_131432_);

   void m_8048_(ClientboundSetEntityMotionPacket p_131434_);

   void m_7277_(ClientboundSetEquipmentPacket p_131435_);

   void m_6747_(ClientboundSetExperiencePacket p_131436_);

   void m_5547_(ClientboundSetHealthPacket p_131437_);

   void m_5582_(ClientboundSetPlayerTeamPacket p_131440_);

   void m_7519_(ClientboundSetScorePacket p_131441_);

   void m_6571_(ClientboundSetDefaultSpawnPositionPacket p_131430_);

   void m_7885_(ClientboundSetTimePacket p_131442_);

   void m_8068_(ClientboundSoundPacket p_131445_);

   void m_5863_(ClientboundSoundEntityPacket p_131444_);

   void m_6490_(ClientboundCustomSoundPacket p_131391_);

   void m_8001_(ClientboundTakeItemEntityPacket p_131449_);

   void m_6435_(ClientboundTeleportEntityPacket p_131450_);

   void m_7710_(ClientboundUpdateAttributesPacket p_131452_);

   void m_7915_(ClientboundUpdateMobEffectPacket p_131453_);

   void m_5859_(ClientboundUpdateTagsPacket p_131455_);

   void m_142234_(ClientboundPlayerCombatEndPacket p_178546_);

   void m_142058_(ClientboundPlayerCombatEnterPacket p_178547_);

   void m_142747_(ClientboundPlayerCombatKillPacket p_178548_);

   void m_6664_(ClientboundChangeDifficultyPacket p_131380_);

   void m_6447_(ClientboundSetCameraPacket p_131426_);

   void m_142237_(ClientboundInitializeBorderPacket p_178544_);

   void m_142686_(ClientboundSetBorderLerpSizePacket p_178552_);

   void m_142238_(ClientboundSetBorderSizePacket p_178553_);

   void m_142056_(ClientboundSetBorderWarningDelayPacket p_178554_);

   void m_142696_(ClientboundSetBorderWarningDistancePacket p_178555_);

   void m_142612_(ClientboundSetBorderCenterPacket p_178551_);

   void m_6235_(ClientboundTabListPacket p_131447_);

   void m_5587_(ClientboundResourcePackPacket p_131420_);

   void m_7685_(ClientboundBossEventPacket p_131379_);

   void m_7701_(ClientboundCooldownPacket p_131389_);

   void m_7410_(ClientboundMoveVehiclePacket p_131407_);

   void m_5498_(ClientboundUpdateAdvancementsPacket p_131451_);

   void m_7553_(ClientboundSelectAdvancementsTabPacket p_131424_);

   void m_7339_(ClientboundPlaceGhostRecipePacket p_131411_);

   void m_7443_(ClientboundCommandsPacket p_131383_);

   void m_7183_(ClientboundStopSoundPacket p_131446_);

   void m_7589_(ClientboundCommandSuggestionsPacket p_131382_);

   void m_6327_(ClientboundUpdateRecipesPacket p_131454_);

   void m_7244_(ClientboundPlayerLookAtPacket p_131415_);

   void m_6148_(ClientboundTagQueryPacket p_131448_);

   void m_6496_(ClientboundLightUpdatePacket p_131402_);

   void m_6503_(ClientboundOpenBookPacket p_131408_);

   void m_5980_(ClientboundOpenScreenPacket p_131409_);

   void m_7330_(ClientboundMerchantOffersPacket p_131405_);

   void m_7299_(ClientboundSetChunkCacheRadiusPacket p_131429_);

   void m_8065_(ClientboundSetChunkCacheCenterPacket p_131428_);

   void m_6695_(ClientboundBlockBreakAckPacket p_131374_);

   void m_142456_(ClientboundSetActionBarTextPacket p_178550_);

   void m_141913_(ClientboundSetSubtitleTextPacket p_178556_);

   void m_142442_(ClientboundSetTitleTextPacket p_178557_);

   void m_142185_(ClientboundSetTitlesAnimationPacket p_178558_);

   void m_142766_(ClientboundClearTitlesPacket p_178543_);
}