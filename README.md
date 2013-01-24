Minecraft GUI API
=================

Provides a toolkit for creating widgets through Minecraft. Users can 
create frames and various controls, as well as provide customized 
layout managers and themes. Themes have complete control over the 
rendering process by providing "UIs" for specific component types that 
handle rendering components of their respective type.

Libraries
---------

The only library currently required by the project is Slick2D, which can
be found at [http://www.slick2d.org/]. The dependency upon this library may
be removed by deletion of UnicodeFontRenderer.java and slight modification
to SimpleTheme.java to replace the instantiation of UnicodeFontRenderer
with the instantiation of a normal FontRenderer.

  [http://www.slick2d.org/]: http://www.slick2d.org/