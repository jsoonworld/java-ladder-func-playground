import controller.LadderController;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        LadderController controller = new LadderController(inputView, outputView);
        controller.run();
    }
}
