package com.github.nnnnusui.stateanchor

import com.github.nnnnusui.stateanchor.StateAnchor.KeyBind.{Description, category}
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import org.lwjgl.glfw.GLFW

@Mod.EventBusSubscriber(modid = StateAnchor.modId, bus = Mod.EventBusSubscriber.Bus.MOD)
object KeyBindings {
  val rotateMode = new KeyBinding(Description.rotateMode, GLFW.GLFW_KEY_R, category)

  @SubscribeEvent
  def register(event: FMLCommonSetupEvent): Unit ={
    ClientRegistry.registerKeyBinding(rotateMode)
  }
}
