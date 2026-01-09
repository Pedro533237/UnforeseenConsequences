package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.WindowFramebuffer;
import org.featherwhisker.rendereng.main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

@Mixin(WindowFramebuffer.class)
public class WindowFBMixin {

    @Redirect(method = "createColorAttachment", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTexParameteri(III)V"))
    private static void texParameterWrap(int i1, int i2, int i3){
        glTexParameteri(i1, i2, i3);
        main.log.info("Tex parameter bound!, err: {}", glGetError());
    }
    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL30;glFramebufferTexture2D(IIIII)V"))
    private static void framebufferWrap(int i1, int i2, int i3, int i4, int i5){
        // TODO: Re-enable depth buffer
        if(i2 == GL_DEPTH_ATTACHMENT){
            main.log.info("Disabling depth framebuffer for the funny!!");
            return;
        }
        glFramebufferTexture2D(i1, i2, i3, i4, i5);

    }
}
