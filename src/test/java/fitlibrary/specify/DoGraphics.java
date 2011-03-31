/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.parser.graphic.DotGraphic;

public class DoGraphics {
    public DotGraphic graph() {
        return new DotGraphic("digraph G {\n"+
                "lotr->luke;\n"+
                "lotr->Anna;\n"+
                "shrek->luke;\n"+
                "shrek->anna;\n"+
                "shrek->madelin;\n"+
        "}\n");
    }
}
