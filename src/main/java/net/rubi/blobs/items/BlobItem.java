package net.rubi.blobs.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import net.rubi.blobs.entitites.BlobEntity;

public class BlobItem extends Item implements DyeableItem {
   public static final String ID = "blobs:blob";
   public static final BlobItem ITEM = new BlobItem(new FabricItemSettings().group(ItemGroup.MISC));

   //

   public BlobItem(Settings settings) {
      super(settings);
   }

   //

   public static int getItemColor(ItemStack itemStack, int tintIndex) {
      return ((DyeableItem) itemStack.getItem()).getColor(itemStack);
   }

   public static void registerDispenser() {
      DispenserBlock.registerBehavior(ITEM, new ItemDispenserBehavior() {
         @Override
         public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            var direction = pointer.getBlockState().get(DispenserBlock.FACING);
            var blockPos = pointer.getPos().offset(direction);

            BlobItem.place(stack, blockPos, pointer.getWorld(), null);
   
            return stack;
         }
      });
   }

   public static boolean place(ItemStack stack, BlockPos pos, ServerWorld world, PlayerEntity player) {
      var entity = BlobEntity.ENTITY.create(
         world,
         stack.getNbt(),
         null,
         player,
         pos,
         SpawnReason.SPAWN_EGG,
         true,
         true
      );

      if (entity == null) {
         return false;
      }

      entity.setOriginItemStack(stack);
      entity.refreshPositionAndAngles(
         entity.getX() + 0.8*(world.random.nextDouble() - 0.5),
         entity.getY(),
         entity.getZ() + 0.8*(world.random.nextDouble() - 0.5),
         world.random.nextBetween(0, 360),
         0.0f
      );
      world.spawnEntityAndPassengers(entity);
      world.playSound(
         null,
         entity.getX(),
         entity.getY(),
         entity.getZ(),
         SoundEvents.ENTITY_SLIME_JUMP_SMALL,
         SoundCategory.BLOCKS,
         0.75f,
         0.8f
      );
      
      if (player != null) {
         entity.emitGameEvent(GameEvent.ENTITY_PLACE, player);
      }

      stack.decrement(1);

      return true;
   }

   //

   public int getColor(ItemStack stack) {
      var nbtCompound = stack.getSubNbt(DISPLAY_KEY);

      if (nbtCompound != null && nbtCompound.contains(COLOR_KEY, NbtElement.NUMBER_TYPE)) {
         return nbtCompound.getInt(COLOR_KEY);
      }
      
      return 9230210;
   }

   //

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      var world = context.getWorld();
      var itemStack = context.getStack();

      if (world instanceof ServerWorld) {
         var itemPlacementContext = new ItemPlacementContext(context);

         BlobItem.place(itemStack, itemPlacementContext.getBlockPos(), (ServerWorld) world, context.getPlayer());
      }

      return ActionResult.success(world.isClient);
   }
}
