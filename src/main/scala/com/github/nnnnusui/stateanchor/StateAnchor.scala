package com.github.nnnnusui.stateanchor

import net.minecraftforge.fml.common.Mod

@Mod(StateAnchor.modId)
class  StateAnchor
object StateAnchor{
  final val modId = "stateanchor"
  object KeyBind{
    private final val header = "key.categories."
    final val category = s"$header$modId"
    object Description{
      private final val header = s"key.$modId."
      final val valueInc = s"${header}valueInc"
      final val valueDec = s"${header}valueDec"
      final val propertyInc = s"${header}propertyInc"
      final val propertyDec = s"${header}propertyDec"
    }
  }
  object NBTKey{
    final val root = s"$modId"
      final val state = "State"
      final val selectedProperty = "SelectedProperty"
  }
}
