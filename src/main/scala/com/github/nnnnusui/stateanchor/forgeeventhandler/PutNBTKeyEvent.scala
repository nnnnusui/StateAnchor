package com.github.nnnnusui.stateanchor.forgeeventhandler

import com.github.nnnnusui.stateanchor.AnchorNBTController.Rich.RichItemStack
import com.github.nnnnusui.stateanchor.{AnchorNBTController, KeyBindings, StateAnchor}
import net.minecraft.client.Minecraft
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent

@Mod.EventBusSubscriber(modid = StateAnchor.modId, bus = Mod.EventBusSubscriber.Bus.FORGE)
object PutNBTKeyEvent {
  @SubscribeEvent
  def onKeyInput(event: PlayerTickEvent): Unit ={
    if (!KeyBindings.rotateMode.isKeyDown) return

    val mainHandItemStack = Minecraft.getInstance().player.getHeldItemMainhand
    if (!mainHandItemStack.hasAnchorNBT)
      AnchorNBTController.putAnchorNBT(mainHandItemStack)
  }
}
