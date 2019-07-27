package com.github.nnnnusui.stateanchor

import com.github.nnnnusui.stateanchor.StateAnchor.KeyBind
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import org.lwjgl.glfw.GLFW

@Mod.EventBusSubscriber(modid = StateAnchor.modId, bus = Mod.EventBusSubscriber.Bus.MOD)
object KeyBindings {
  private val category = KeyBind.category
  private val description = KeyBind.Description
  val valueInc = new KeyBinding(description.valueInc, GLFW.GLFW_KEY_UP,   category)
  val valueDec = new KeyBinding(description.valueDec, GLFW.GLFW_KEY_DOWN, category)
  val propertyInc = new KeyBinding(propertyInc, GLFW.GLFW_KEY_RIGHT, category)
  val propertyDec = new KeyBinding(propertyDec, GLFW.GLFW_KEY_LEFT,  category)

  @SubscribeEvent
  def register(event: FMLCommonSetupEvent): Unit ={
    ClientRegistry.registerKeyBinding(valueInc)
    ClientRegistry.registerKeyBinding(valueDec)
    ClientRegistry.registerKeyBinding(propertyInc)
    ClientRegistry.registerKeyBinding(propertyDec)
  }
}