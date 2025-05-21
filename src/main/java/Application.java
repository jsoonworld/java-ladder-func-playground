import controller.LadderController;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        LadderController controller = new LadderController(outputView);
        controller.run(4);
    }
}
