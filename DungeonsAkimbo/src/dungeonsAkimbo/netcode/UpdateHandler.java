package dungeonsAkimbo.netcode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class UpdateHandler {

	public static UpdatePacket recieveUpdate(byte[] updateBytes) throws IOException, ClassNotFoundException {
		UpdatePacket update;
		try(ByteArrayInputStream bis = new ByteArrayInputStream(updateBytes);
	            ObjectInput in = new ObjectInputStream(bis);) {

	      update = (UpdatePacket) in.readObject(); 

	    } finally {

	    }
		return update;
	}

	public static byte[] sendUpdate(UpdatePacket update) throws IOException {
		byte[] updatePacket;
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
			
			updatePacket = fbos.toByteArray();
		}
		
		
		
		return updatePacket;
	}

}
