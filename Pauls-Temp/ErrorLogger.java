import javax.swing.JOptionPane;

public class ErrorLogger{

	public static void error(String error){
		
		try{
			JOptionPane.showMessageDialog(null, error,"Scheduler Error",JOptionPane.ERROR_MESSAGE);
		}catch(Exception e){
			System.err.println("GUI ERROR: Printing Error To Console");
			System.err.println(error);
		}	
		System.exit(-1);
	}

	public static void main(String[] args){
	}
}
