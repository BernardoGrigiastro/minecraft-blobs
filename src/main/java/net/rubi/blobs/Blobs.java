package net.rubi.blobs;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import net.rubi.blobs.entitites.BlobEntity;
import net.rubi.blobs.items.BlobItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Blobs implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("blobs");

	//

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, BlobItem.ID, BlobItem.ITEM);

		BlobEntity.ENTITY = Registry.register(Registry.ENTITY_TYPE, BlobEntity.ID, FabricEntityTypeBuilder.create(SpawnGroup.MISC, BlobEntity::new).dimensions(EntityDimensions.fixed(0.1875f, 0.1875f)).build());
		FabricDefaultAttributeRegistry.register(BlobEntity.ENTITY, BlobEntity.createBlobAttributes());

		BlobItem.registerDispenser();
	}
}
