package modelviewer;

import model.ViewModel;
import model.segment.SegmentEditable;
import shape.CubeInstancedFaces;

class ViewModelProvider {
    enum ViewModelType {
        GOAT("goat.model"),
        FOUR_LEG("fourLeg.model"),
        BIRD("bird.model");

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
            case BIRD:
                return bird(cubeInstancedFaces);
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

        float[] color = new float[] {1, 0, 0, 1};
        body.setColor(color);
        head.setColor(color);
        tail.setColor(color);
        legFR.setColor(color);
        legFL.setColor(color);
        legBR.setColor(color);
        legBL.setColor(color);

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
        SegmentEditable legFR = new SegmentEditable();
        SegmentEditable legFL = new SegmentEditable();
        SegmentEditable legBR = new SegmentEditable();
        SegmentEditable legBL = new SegmentEditable();

        float[] color = new float[] {1, 0, 0, 1};
        body.setColor(color);
        head.setColor(color);
        legFR.setColor(color);
        legFL.setColor(color);
        legBR.setColor(color);
        legBL.setColor(color);

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

    private static ViewModel bird(CubeInstancedFaces cubeInstancedFaces) {
        ViewModel viewModel = new ViewModel();

        SegmentEditable body = new SegmentEditable();
        SegmentEditable legFR = new SegmentEditable();
        SegmentEditable legFL = new SegmentEditable();
        SegmentEditable legBR = new SegmentEditable();
        SegmentEditable legBL = new SegmentEditable();

        float[] color = new float[] {1, 0, 0, 1};
        body.setColor(color);
        legFR.setColor(color);
        legFL.setColor(color);
        legBR.setColor(color);
        legBL.setColor(color);

        body.init(null, cubeInstancedFaces);
        legFR.init(body, cubeInstancedFaces);
        legFL.init(body, cubeInstancedFaces);
        legBR.init(body, cubeInstancedFaces);
        legBL.init(body, cubeInstancedFaces);

        body.setScale(1, 3, 1);
        legFR.setScale(2, 2, 1);
        legFL.setScale(2, 2, 1);
        legBR.setScale(2, 2, 1);
        legBL.setScale(2, 2, 1);

        body.setTranslation(0, 0, 0);
        legFR.setTranslation(1.5f, 1.5f, 0);
        legFL.setTranslation(-1.5f, 1.5f, 0);
        legBR.setTranslation(1.5f, -1.5f, 0);
        legBL.setTranslation(-1.5f, -1.5f, 0);

        viewModel.addSegment(body);
        viewModel.addSegment(legFR);
        viewModel.addSegment(legFL);
        viewModel.addSegment(legBR);
        viewModel.addSegment(legBL);

        return viewModel;
    }
}