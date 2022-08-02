package net.rubi.blobs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.rubi.blobs.entitites.BlobEntity;
import net.rubi.blobs.entitites.BlobEntityModel;
import net.rubi.blobs.entitites.BlobEntityRenderer;
import net.rubi.blobs.items.BlobItem;

public class BlobsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ColorProviderRegistry.ITEM.register(BlobItem::getItemColor, BlobItem.ITEM);
		EntityRendererRegistry.register(BlobEntity.ENTITY, BlobEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(BlobEntityModel.LAYER, BlobEntityModel::getTexturedModelData);
	}
}
