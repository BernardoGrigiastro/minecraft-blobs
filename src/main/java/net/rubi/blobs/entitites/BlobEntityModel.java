package net.rubi.blobs.entitites;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.TintableCompositeModel;
import net.minecraft.util.Identifier;

public class BlobEntityModel extends TintableCompositeModel<BlobEntity> {
   public static final EntityModelLayer LAYER = new EntityModelLayer(new Identifier("blobs", "blob_entity"), "main");

   private final ModelPart root;

   //

   public BlobEntityModel(ModelPart modelPart) {
      this.root = modelPart.getChild(EntityModelPartNames.CUBE);
   }

   //

   public static TexturedModelData getTexturedModelData() {
      ModelData modelData = new ModelData();

      modelData.getRoot().addChild(EntityModelPartNames.CUBE, ModelPartBuilder.create().uv(0, 0).cuboid(-1.5f, 0f, -1.5f, 3f, 3f, 3f), ModelTransform.pivot(0f, 21f, 0f));
      
      return TexturedModelData.of(modelData, 12, 6);
   }

   //

   @Override
   public void setAngles(BlobEntity var1, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}

   @Override
   public ModelPart getPart() {
      return this.root;
   }
}
