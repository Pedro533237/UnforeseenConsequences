package org.featherwhisker.rendereng.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL14.*;

@Mixin(RenderSystem.class)
public class GlStateManagerMixin {
    /**
     * @author Featherwhisker
     * @reason Not supported in OGL ES
     */
    @Overwrite
    public static void _logicOp(int op) {}
    /**
     * @author Featherwhisker
     * @reason Not supported in OGL ES
     */
    @Overwrite
    public static void _getTexImage(int target, int level, int format, int type, long pixels) {}
    /**
     * @author Featherwhisker
     * @reason Not supported in OGL ES
     */
    @Overwrite
    public static void polygonMode(int face, int mode) {}
    /**
     * @author Featherwhisker
     * @reason Not supported in OGL ES
     */
    @Overwrite
    public static void _drawPixels(int width, int height, int format, int type, long pixels) {}

    @Inject(
            method = "texParameter",
            at=@At("HEAD"),
            cancellable = true
    )
    private static void _texParameter(int a, int b, float c, CallbackInfo call) {
        if (a == GL_TEXTURE_2D && b == GL_TEXTURE_LOD_BIAS) {
            call.cancel();
        }
    }
}
