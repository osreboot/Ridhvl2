[![Gradle Package](https://github.com/osreboot/Ridhvl2/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/osreboot/Ridhvl2/actions/workflows/gradle-publish.yml)

<p align="center">
  <img src="https://github.com/osreboot/Ridhvl2-Wiki-Resources/blob/master/RIDHVL2.png" width="384" height="384" alt="Ridhvl2">
</p>

# Ridhvl2
Ridhvl2 is a library designed to provide shortcuts and templates for 2D Java game development.

Ridhvl2 is publicly available for commercial use under the [zlib license](https://choosealicense.com/licenses/zlib/).

Pull requests that provide *helpful* and *logical* additions will be accepted. Additionally, questions and ideas can be discussed in our [Discord server](https://discord.gg/E8GTCNH)!

# Dependencies

Ridhvl2 depends on [Gradle](https://github.com/gradle/gradle), [LWJGL3](https://github.com/LWJGL/lwjgl3), [jackson-core](https://github.com/FasterXML/jackson-core), [jackson-databind](https://github.com/FasterXML/jackson-databind), [jackson-annotations](https://github.com/FasterXML/jackson-annotations), and [JUnit 5](https://github.com/junit-team/junit5/). You can view dependency information, licenses, and source code via the supplied links.

---

#### Minimum Viable Product

This code is for demonstrational purposes only. Please see the [wiki](https://github.com/osreboot/Ridhvl2/wiki) for proper documentation.

```java
package com.osreboot.ridhvl2test;

// Statically import HvlStatics for easy access to core Ridhvl2 methods
import static com.osreboot.ridhvl2.HvlStatics.*;

import com.osreboot.ridhvl2.template.HvlDisplayWindowed;
import com.osreboot.ridhvl2.template.HvlTemplateI;

// [Selecting a Template]:
// - There are a number of templates to choose from!
// - HvlTemplateI automatically sets up the utilities for making games.
public class Ridhvl2Test extends HvlTemplateI{

    public static void main(String[] args){
        new Ridhvl2Test(); // Run the game!
    }

    public Ridhvl2Test(){
        // [Display Configuration]:
        // - The constructor needs a HvlDisplay to describe the application window.
        // - This example creates a 144Hz, 1280x720, non-resizable window titled "Ridhvl2 Test!".
        super(144.0, new HvlDisplayWindowed(1280, 720, "Ridhvl2 Test!", false));
    }
  
    //---------------------------------------------------------------------//
  
    // [Initialize]: 
    // - Automatically called once on program startup.
    
    @Override
    public void initialize(){
        // [Resource Loading]:
        // - Ridhvl2 defaults to searching in the "/src/main/resources" folder. 
        // - hvlLoad(String) loads any type of resource (images, sounds, etc.).
        // - Resources are stored in the order that they are loaded.
        hvlLoad("RIDHVL2.png");
    }

    //---------------------------------------------------------------------//

    // [Update]:
    // - Automatically called once every frame update.
    // "delta" is the time in seconds since the last update.
    
    @Override
    public void update(float delta){
        // [Drawing a Quad]:
        // - hvlDraw(polygon, paint) renders a polygon to the display.
        // - hvlQuad(...) creates a four-sided polygon (rectangle) described by screen coordinates.
        // - hvlTexture(int) fetches a loaded texture (using the order in which it was loaded).
        hvlDraw(hvlQuad(10, 10, 64, 64), hvlTexture(0));
    }

}
```
