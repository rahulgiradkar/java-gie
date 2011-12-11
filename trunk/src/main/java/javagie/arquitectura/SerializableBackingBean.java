package javagie.arquitectura;

import java.io.ObjectInputStream;
import java.io.Serializable;

public abstract class SerializableBackingBean implements Serializable {

	private void readObject(ObjectInputStream is) {
		try {
			is.defaultReadObject();
			postActivation();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public abstract void postActivation();

}
