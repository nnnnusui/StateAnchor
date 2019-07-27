package com.github.nnnnusui.stateanchor.forgeeventhandler

import com.github.nnnnusui.stateanchor.{Anchor, KeyBindings, StateAnchor}
import net.minecraft.client.Minecraft
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.gameevent.TickEvent

@Mod.EventBusSubscriber(modid = StateAnchor.modId, bus = Mod.EventBusSubscriber.Bus.FORGE)
object PutNBTKeyEvent {
  @SubscribeEvent
  def onKeyInput(event: TickEvent.ClientTickEvent): Unit ={
    val valueIncKey = KeyBindings.valueInc.isPressed
    val valueDecKey = KeyBindings.valueDec.isPressed
    val propertyIncKey = KeyBindings.propertyInc.isPressed
    val propertyDecKey = KeyBindings.propertyDec.isPressed
    if(!(valueIncKey || valueDecKey || propertyIncKey || propertyDecKey)) return

    val player = Minecraft.getInstance().player
    val itemStack = player.getHeldItemMainhand
    val anchor = Anchor(itemStack)
    true match {
      case _ if valueIncKey    => anchor.shiftValue( 1)
      case _ if valueDecKey    => anchor.shiftValue(-1)
      case _ if propertyIncKey => anchor.shiftProperty( 1)
      case _ if propertyDecKey => anchor.shiftProperty(-1)
    }
    anchor.setAnchor(itemStack)
  }
}