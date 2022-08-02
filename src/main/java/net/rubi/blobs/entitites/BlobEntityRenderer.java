package net.rubi.blobs.entitites;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.TintableCompositeModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BlobEntityRenderer extends LivingEntityRenderer<BlobEntity, TintableCompositeModel<BlobEntity>> {
   public static final Identifier TEXTURE_PATH = new Identifier("blobs", "textures/entity/blob/blob.png");
   
   //
   
   public BlobEntityRenderer(Context context) {
      super(context, new BlobEntityModel(context.getPart(BlobEntityModel.LAYER)), 0.1875f);
   }

   //

   @Override
   public void render(BlobEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
      var color = entity.getColor();
      var r = (color >> 16 & 0xFF) / 255.0f;
      var g = (color >> 8 & 0xFF) / 255.0f;
      var b = (color & 0xFF) / 255.0f;

      this.model.setColorMultiplier(r, g, b);
      super.render(entity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
      this.model.setColorMultiplier(1f, 1f, 1f);
   }

   @Override
   public Identifier getTexture(BlobEntity entity) {
      return TEXTURE_PATH;
   }

   @Override
   protected boolean hasLabel(BlobEntity entity) {
      if (entity.hasCustomName()) {
         return this.dispatcher.getSquaredDistanceToCamera(entity) < 1024.0;
      }
      else {
         return false;
      }
   }
}
