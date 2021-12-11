import com.mod.envelope.container.EnvelopeCapabilityProvider;
import com.mod.envelope.container.EnvelopeContainer;
import com.mod.envelope.container.EnvelopeItemStackHandler;
import com.mod.envelope.core.init.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class SealedEnvelope extends Item {

    public SealedEnvelope(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide()) {
            INamedContainerProvider ncp = new EnvelopeContainerProvider(this, stack);
            NetworkHooks.openGui((ServerPlayerEntity) player, ncp);
        }

        return ActionResult.success(stack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new EnvelopeCapabilityProvider();
    }

    EnvelopeItemStackHandler getEnvelopeItemStackHandler(ItemStack stack) {
        IItemHandler envelope = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        if (envelope == null || !(envelope instanceof EnvelopeItemStackHandler)) {
            return new EnvelopeItemStackHandler();
        }

        return (EnvelopeItemStackHandler) envelope;
    }

    private static final String BASE_TAG = "base";
    private static final String CAP_TAG = "cap";

    @Nullable
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT baseTag = stack.getTag();
        EnvelopeItemStackHandler envelopeItemStackHandler = getEnvelopeItemStackHandler(stack);
        CompoundNBT capTag = envelopeItemStackHandler.serializeNBT();
        CompoundNBT combinedTag = new CompoundNBT();
        if (baseTag != null) combinedTag.put(BASE_TAG, baseTag);
        if (capTag != null) combinedTag.put(CAP_TAG, capTag);

        return combinedTag;
    }

    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (nbt == null) {
            stack.setTag(null);
            return;
        }
        CompoundNBT baseTag = nbt.getCompound(BASE_TAG);
        stack.setTag(baseTag);
        CompoundNBT capTag = nbt.getCompound(CAP_TAG);
        EnvelopeItemStackHandler envelopeItemStackHandler = getEnvelopeItemStackHandler(stack);
        envelopeItemStackHandler.deserializeNBT(capTag);
    }

    private static class EnvelopeContainerProvider implements INamedContainerProvider {
        private final SealedEnvelope sealedEnvelope;
        private final ItemStack sealedEnvelopeStack;

        public EnvelopeContainerProvider(SealedEnvelope item, ItemStack stack) {
            sealedEnvelope = item;
            sealedEnvelopeStack = stack;
        }

        @Nullable
        @Override
        public Container createMenu(int windowId, PlayerInventory playerInv, PlayerEntity player) {
            return new EnvelopeContainer(
                    windowId,
                    playerInv,
                    sealedEnvelope.getEnvelopeItemStackHandler(sealedEnvelopeStack),
                    sealedEnvelopeStack);
        }


        @Override
        public ITextComponent getDisplayName() {
            return new TranslationTextComponent(ItemInit.SEALED_ENVELOPE.get().getDescriptionId());
        }
    }
}