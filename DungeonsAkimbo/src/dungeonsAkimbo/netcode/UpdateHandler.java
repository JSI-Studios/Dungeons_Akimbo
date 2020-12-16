package dungeonsAkimbo.netcode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class UpdateHandler {
	
	UpdatePacket previousIn, currentIn;
	byte [] previousOut, currentOut;
	
	public UpdateHandler() {
		
	}

	public UpdatePacket recieveUpdate(byte[] updateBytes) throws IOException, ClassNotFoundException {
		try(ByteArrayInputStream bis = new ByteArrayInputStream(updateBytes);
	            ObjectInput in = new ObjectInputStream(bis);) {
		 
				currentIn = (UpdatePacket) in.readObject();

	    } finally {

	    }
		try(ByteArrayInputStream bis = new ByteArrayInputStream(updateBytes);
	            ObjectInput in = new ObjectInputStream(bis);) {
		 
				previousIn = (UpdatePacket) in.readObject();

	    } finally {

	    }
		return currentIn;
	}

	public byte[] sendUpdate(UpdatePacket update) throws IOException {
		byte[] updateBytes;
		String prefix = "/u/";
		String postfix = "/e/";
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(bos);){
			out.writeObject(update);
			out.flush();
			updateBytes = bos.toByteArray();			
			
		} finally {
		
		}
		
		try(ByteArrayOutputStream fbos = new ByteArrayOutputStream();){
			fbos.write(prefix.getBytes());
			fbos.write(updateBytes);
			fbos.write(postfix.getBytes());
			
			currentOut = fbos.toByteArray();
			previousOut = fbos.toByteArray();
		}
		
		
		System.out.println(currentOut.length);
		return currentOut;
	}

}
