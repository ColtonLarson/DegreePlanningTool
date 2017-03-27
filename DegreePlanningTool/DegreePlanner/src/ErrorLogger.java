import javax.swing.JOptionPane;

public class ErrorLogger{

	public void error(String error){
		String errorMessage = "An error has occured. It is unlikely that you will be able use this program correctly.\n" + 
							  "Please send the following error message to [this email]: \n\n" + error;
		try{
			JOptionPane.showMessageDialog(null, errorMessage,"Scheduler Error",JOptionPane.ERROR_MESSAGE);
		}catch(Exception e){
			System.err.println("GUI ERROR: Printing Error To Console");
			System.err.println(error);
		}	
		System.exit(-1);
	}

	public static void main(String[] args){
		ErrorLogger e = new ErrorLogger();
		e.error("This is a test error!");
	}
}
