package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.Framebuffer;
import org.featherwhisker.rendereng.util.ReplacementConstants;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.IntBuffer;
import java.util.OptionalInt;

import static org.lwjgl.opengl.GL11.*;

@Mixin(Framebuffer.class)
public class FramebufferMixin {
    @Redirect(method = "initFbo", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTexImage2D(IIIIIIIILjava/nio/IntBuffer;)V"))
    private void wrapTexture2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, @Nullable IntBuffer pixels) {
        // TODO: simplify
        OptionalInt replacementConstant = ReplacementConstants.getReplacementConstant(internalFormat);
        if(replacementConstant.isPresent()) {
            internalFormat = replacementConstant.getAsInt();
        }
        glTexImage2D(target, level, internalFormat, width, height, border, format, type, pixels);
    }
}
