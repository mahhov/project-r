package modelviewer;

import model.ViewModel;
import util.Writer;

import java.io.IOException;

public class ModelGenerator {
    public static void generate() {
        for (ViewModelProvider.ViewModelType viewModelType : ViewModelProvider.ViewModelType.values()) {
            ViewModel model = ViewModelProvider.getViewModel(viewModelType, null);
            try {
                Writer.getWriteStream(viewModelType.file).writeObject(model.getModelData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ModelGenerator.generate();
    }
}