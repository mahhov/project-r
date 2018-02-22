package modelviewer;

import model.ViewModel;
import model.segment.SegmentEditable;
import shape.CubeInstancedFaces;

class ViewModelProvider {
    enum ViewModelType {
        GOAT("goat.model"),
        FOUR_LEG("fourLeg.model");

        final String file;

        ViewModelType(String file) {
            this.file = file;
        }
    }

    static ViewModel getViewModel(ViewModelType viewModelType, CubeInstancedFaces cubeInstancedFaces) {
        switch (viewModelType) {
            case GOAT:
                return goat(cubeInstancedFaces);
            case FOUR_LEG:
                return fourLeg(cubeInstancedFaces);
            default:
                throw new RuntimeException("view model type not caught in ViewModelProvider.getViewModel");
        }
    }

    private static ViewModel goat(CubeInstancedFaces cubeInstancedFaces) {
        ViewModel viewModel = new ViewModel();

        SegmentEditable body = new SegmentEditable();
        SegmentEditable head = new SegmentEditable();
        SegmentEditable tail = new SegmentEditable();
        SegmentEditable legFR = new SegmentEditable();
        SegmentEditable legFL = new SegmentEditable();
        SegmentEditable legBR = new SegmentEditable();
        SegmentEditable legBL = new SegmentEditable();

        body.init(null, cubeInstancedFaces);
        head.init(body, cubeInstancedFaces);
        tail.init(body, cubeInstancedFaces);
        legFR.init(body, cubeInstancedFaces);
        legFL.init(body, cubeInstancedFaces);
        legBR.init(body, cubeInstancedFaces);
        legBL.init(body, cubeInstancedFaces);

        body.setScale(2, 2.5f, 2);
        head.setScale(1.3f, 1.3f, 1.3f);
        tail.setScale(1.3f, 2f, .3f);
        legFR.setScale(.8f, .8f, .8f);
        legFL.setScale(.8f, .8f, .8f);
        legBR.setScale(.8f, .8f, .8f);
        legBL.setScale(.8f, .8f, .8f);

        body.setTranslation(0, 0, 0);
        head.setTranslation(0, 1.5f, 2);
        tail.setTranslation(0, -1.5f, .3f);
        legFR.setTranslation(1, 1, -1.4f);
        legFL.setTranslation(-1, 1, -1.4f);
        legBR.setTranslation(1, -1, -1.4f);
        legBL.setTranslation(-1, -1, -1.4f);

        viewModel.addSegment(body);
        viewModel.addSegment(head);
        viewModel.addSegment(tail);
        viewModel.addSegment(legFR);
        viewModel.addSegment(legFL);
        viewModel.addSegment(legBR);
        viewModel.addSegment(legBL);

        return viewModel;
    }

    private static ViewModel fourLeg(CubeInstancedFaces cubeInstancedFaces) {
        ViewModel viewModel = new ViewModel();

        SegmentEditable body = new SegmentEditable();
        SegmentEditable head = new SegmentEditable();
        SegmentEditable tail = new SegmentEditable();
        SegmentEditable legFR = new SegmentEditable();
        SegmentEditable legFL = new SegmentEditable();
        SegmentEditable legBR = new SegmentEditable();
        SegmentEditable legBL = new SegmentEditable();

        body.init(null, cubeInstancedFaces);
        head.init(body, cubeInstancedFaces);
        legFR.init(body, cubeInstancedFaces);
        legFL.init(body, cubeInstancedFaces);
        legBR.init(body, cubeInstancedFaces);
        legBL.init(body, cubeInstancedFaces);

        body.setScale(2, 6, 1);
        head.setScale(3, 1, 2);
        legFR.setScale(1, 1, 5);
        legFL.setScale(1, 1, 5);
        legBR.setScale(1, 1, 5);
        legBL.setScale(1, 1, 5);

        body.setTranslation(0, 0, 0);
        head.setTranslation(0, 3.5f, .5f);
        legFR.setTranslation(1.5f, 1.5f, -1);
        legFL.setTranslation(-1.5f, 1.5f, -1);
        legBR.setTranslation(1.5f, -1.5f, -1);
        legBL.setTranslation(-1.5f, -1.5f, -1);

        viewModel.addSegment(body);
        viewModel.addSegment(head);
        viewModel.addSegment(legFR);
        viewModel.addSegment(legFL);
        viewModel.addSegment(legBR);
        viewModel.addSegment(legBL);

        return viewModel;
    }
}