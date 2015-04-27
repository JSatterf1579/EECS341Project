package eecs341.finalProject;

public class Dobis {

	public static void main(String[] args) {
		if(args.length > 0) {
			if(args[0].equals("-c")) {
				SQLLoader.start();
			}
		} else {
			new DatabaseUI();
		}
	}

}
