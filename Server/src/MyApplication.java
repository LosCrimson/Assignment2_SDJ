import javafx.application.Application;
import javafx.stage.Stage;
import mediator.UppercaseConnector;
import model.Model;
import model.ModelManager;
import view.ViewHandler;
import viewmodel.ViewModelFactory;

public class MyApplication extends Application {
    @Override
    public void start(Stage primaryStage){
        Model model = new ModelManager();
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler view = new ViewHandler(viewModelFactory);

        view.start(primaryStage);

        // starting server
        UppercaseConnector server = new UppercaseConnector(model);
        Thread serverThread = new Thread(server);
        serverThread.setDaemon(true);
        serverThread.start();
    }
}
