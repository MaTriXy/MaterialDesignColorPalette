package fr.hozakan.materialdesigncolorpalette.otto.event;

import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;

/**
 * Created by gimbert on 2014-08-07.
 */
public class ShareMenuClickedEvent {
    private PaletteColor color;

    public ShareMenuClickedEvent(PaletteColor color) {
        this.color = color;
    }

    public PaletteColor getColor() {
        return color;
    }
}
