package org.featherwhisker.rendereng.mixins;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gl.ShaderType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.TextureFormat;
import net.minecraft.client.gl.GlBackend;
import net.minecraft.util.Identifier;
import org.featherwhisker.rendereng.main;
import org.featherwhisker.rendereng.util.ShaderConverter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiFunction;

@Mixin(GlBackend.class)
public class GlBackendMixin {

    /**
     * Intercepta a função que busca o código-fonte do shader.
     * Isso substitui o antigo CompiledShaderMixin.
     */
    @ModifyVariable(
            method = "precompilePipeline(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Ljava/util/function/BiFunction;)Lnet/minecraft/client/gl/CompiledShaderPipeline;",
            at = @At("HEAD"),
            argsOnly = true
    )
    private BiFunction<Identifier, ShaderType, String> modifyShaderSourceRetriever(BiFunction<Identifier, ShaderType, String> originalRetriever) {
        return (id, type) -> {
            // Pega o código original do Minecraft
            String source = originalRetriever.apply(id, type);
            
            // Se o modo debug estiver ativo, logamos o ID do shader
            if (main.debugMode) {
                main.log.info("Convertendo shader para GLES: {}", id);
            }

            // Converte para GLSL ES 3.0
            return ShaderConverter.convert(source);
        };
    }

    /**
     * Intercepta a criação de texturas para logar ou modificar formatos incompatíveis.
     * Substitui o antigo FramebufferMixin.
     */
    @Inject(
            method = "createTexture(Ljava/lang/String;ILcom/mojang/blaze3d/textures/TextureFormat;IIII)Lcom/mojang/blaze3d/textures/GpuTexture;",
            at = @At("HEAD")
    )
    private void onCreateTexture(String label, int usage, TextureFormat format, int width, int height, int depth, int mip, CallbackInfoReturnable<?> cir) {
        if (main.debugMode) {
            // main.log.info("Criando textura: {} [Format: {}]", label, format);
        }
        
        // TODO: Se o jogo crashar devido a formatos de textura não suportados (como RGBA8 vs RGBA no GLES),
        // você precisará usar @ModifyVariable aqui para trocar o objeto 'format' por um compatível.
    }
}