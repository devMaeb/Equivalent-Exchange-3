package com.pahimar.ee3.client.renderer.tileentity;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.pahimar.ee3.client.model.ModelGlassBell;
import com.pahimar.ee3.lib.Textures;
import com.pahimar.ee3.tileentity.TileGlassBell;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Equivalent-Exchange-3
 * 
 * TileEntityGlassBellRenderer
 * 
 * @author pahimar
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
@SideOnly(Side.CLIENT)
public class TileEntityGlassBellRenderer extends TileEntitySpecialRenderer {

    private ModelGlassBell modelGlassBell = new ModelGlassBell();
    private final RenderItem customRenderItem;

    public TileEntityGlassBellRenderer() {

        customRenderItem = new RenderItem() {

            @Override
            public boolean shouldBob() {

                return false;
            };
        };

        customRenderItem.setRenderManager(RenderManager.instance);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {

        if (tileEntity instanceof TileGlassBell) {
            TileGlassBell tileGlassBell = (TileGlassBell) tileEntity;

            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);

            /**
             * Render the Glass Bell
             */
            GL11.glPushMatrix();

            // Scale, Translate, Rotate
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) x + 0.0F, (float) y + -1.0F, (float) z + 1.0F);
            GL11.glRotatef(0F, 0F, 1F, 0F);
            GL11.glRotatef(-90F, 1F, 0F, 0F);

            // Bind texture
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.MODEL_GLASS_BELL);

            modelGlassBell.render();

            GL11.glPopMatrix();

            /**
             * Render the ghost item inside of the Glass Bell, slowly spinning
             */
            GL11.glPushMatrix();

            for (int i = 0; i < tileGlassBell.getSizeInventory(); i++) {
                if (tileGlassBell.getStackInSlot(i) != null) {

                    float scaleFactor = getGhostItemScaleFactor(tileGlassBell.getStackInSlot(i));
                    float rotationAngle = (float) (720.0 * (double) (System.currentTimeMillis() & 0x3FFFL) / (double) 0x3FFFL);
                    
                    if (tileGlassBell.getStackInSlot(i).itemID < 4096) {
                        
                    }
                    
                    EntityItem ghostEntityItem = new EntityItem(tileGlassBell.worldObj);
                    ghostEntityItem.hoverStart = 0.0F;
                    ghostEntityItem.setEntityItemStack(tileGlassBell.getStackInSlot(i));

                    GL11.glTranslatef((float) x + 0.5F, (float) y + getGhostItemYTranslateFactor(tileGlassBell.getStackInSlot(i)), (float) z + 0.5F);
                    GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
                    GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);
                    
                    customRenderItem.doRenderItem(ghostEntityItem, 0, 0, 0, 0, 0);
                }

            }
            
            GL11.glPopMatrix();

            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_LIGHTING);

        }
    }
    
    private float getGhostItemYTranslateFactor(ItemStack itemStack) {
        float scaleFactor = 0.1F;
        
        if (itemStack != null) {
            if (itemStack.itemID < 4096) {
                switch (customRenderItem.getMiniBlockCount(itemStack)) {
                    case 1: return 0.25F;
                    case 2: return 0.25F;
                    case 3: return 0.25F;
                    case 4: return 0.25F;
                    case 5: return 0.25F;
                    default: return 0.1F;
                }
            }
            else {
                switch (customRenderItem.getMiniItemCount(itemStack)) {
                    case 1: return 0.175F;
                    case 2: return 0.15F;
                    case 3: return 0.1F;
                    case 4: return 0.1F;
                    default: return 0.1F;
                }
            }
        }
        
        return scaleFactor;
    }
    
    private float getGhostItemScaleFactor(ItemStack itemStack) {
        float scaleFactor = 1.0F;
        
        if (itemStack != null) {
            if (itemStack.itemID < 4096) {
                switch (customRenderItem.getMiniBlockCount(itemStack)) {
                    case 1: return 0.90F;
                    case 2: return 0.90F;
                    case 3: return 0.90F;
                    case 4: return 0.90F;
                    case 5: return 0.80F;
                    default: return 0.90F;
                }
            }
            else {
                switch (customRenderItem.getMiniItemCount(itemStack)) {
                    case 1: return 0.65F;
                    case 2: return 0.65F;
                    case 3: return 0.65F;
                    case 4: return 0.65F;
                    default: return 0.65F;
                }
            }
        }
        
        return scaleFactor;
    }
}