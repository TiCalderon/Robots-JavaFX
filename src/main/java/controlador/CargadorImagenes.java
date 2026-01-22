package controlador;

import javafx.scene.image.Image;

import java.util.Objects;

public class CargadorImagenes {
    public static Image getImage(String nombreArchivo){
        return new Image(Objects.requireNonNull(CargadorImagenes.class.getResourceAsStream("/images/" + nombreArchivo)));
    }
}
