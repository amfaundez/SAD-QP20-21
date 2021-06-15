import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings("serial")
class PacketSend implements Serializable { //Serializable: para que todas las instancias que pertenezcan a esta clase sean capaces 
											 //de covertirse en una serie de bytes para poder ser enviadas a traves de la red
	private String name,ip,message;
	//private ArrayList<String> Ips;
	private HashMap <String,String> ipname;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HashMap<String, String> getIpname() {
		return ipname;
	}

	public void setIpname(HashMap<String, String> ipname) {
		this.ipname = ipname;
	}
}
