package com.walrusone.skywarsreloaded.menus;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CPing {
	private InetSocketAddress host;
	public JSONObject json;
	private int timeoutInt = 5;

	public CPing(String ip, int porta, int tempo) {
		this.host = new InetSocketAddress(ip, porta);
		this.timeoutInt = tempo;
		try {
			fetchData();
		} catch (IOException | java.text.ParseException localIOException) {
		}
	}

	@SuppressWarnings("resource")
	public void fetchData() throws IOException, java.text.ParseException {
		Socket socket = new Socket();

		socket.setSoTimeout(this.timeoutInt);

		socket.connect(this.host, this.timeoutInt);

		OutputStream outputStream = socket.getOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

		InputStream inputStream = socket.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

		ByteArrayOutputStream b = new ByteArrayOutputStream();

		DataOutputStream handshake = new DataOutputStream(b);
		handshake.writeByte(0);
		writeVarInt(handshake, 47);
		writeVarInt(handshake, this.host.getHostString().length());

		handshake.writeBytes(this.host.getHostString());
		handshake.writeShort(this.host.getPort());
		writeVarInt(handshake, 1);

		writeVarInt(dataOutputStream, b.size());
		dataOutputStream.write(b.toByteArray());

		dataOutputStream.writeByte(1);
		dataOutputStream.writeByte(0);
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		readVarInt(dataInputStream);

		int id = readVarInt(dataInputStream);
		if (id == -1) {
			throw new IOException("ERRO");
		}
		int length = readVarInt(dataInputStream);
		if (length == -1) {
			throw new IOException("ERRO");
		}
		byte[] in = new byte[length];

		dataInputStream.readFully(in);
		String json = new String(in);

		long now = System.currentTimeMillis();
		dataOutputStream.writeByte(9);
		dataOutputStream.writeByte(1);
		dataOutputStream.writeLong(now);

		readVarInt(dataInputStream);
		id = readVarInt(dataInputStream);
		if (id == -1) {
			throw new IOException("ERRO");
		}
		try {
			this.json = ((JSONObject) new JSONParser().parse(json));
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		dataOutputStream.close();
		outputStream.close();
		inputStreamReader.close();
		inputStream.close();
		socket.close();
		processarTudo();
	}

	private JSONObject getJson() {
		if (this.json == null) {
			return null;
		}
		return this.json;
	}

	public int pegarJogadoresOnline() {
		return this.JogadoresOnline;
	}

	public int pegarJogadoresMaximo() {
		return this.JogadoresMaximo;
	}

	public String pegarMotd() {
		return this.motd;
	}

	public String motd = null;
	public int JogadoresMaximo = 0;
	public int JogadoresOnline = 0;

	public boolean estaFechado() {
		return this.json == null;
	}

	@SuppressWarnings("unchecked")
	public void processarTudo() {
		this.motd = String.valueOf(getJson().get("description")).replace("§", "");
		JSONArray array = new JSONArray();
		JSONObject site = null;
		array.add(getJson().get("players"));
		for (int i = 0; i < array.size(); i++) {
			site = (JSONObject) array.get(i);
		}
		this.JogadoresMaximo = ((int) ((Long) site.get("max")).longValue());
		this.JogadoresOnline = ((int) ((Long) site.get("online")).longValue());
	}

	private int readVarInt(DataInputStream in) throws IOException {
		int i = 0;
		int j = 0;
		int k;
		do {
			k = in.readByte();
			i |= (k & 0x7F) << j++ * 7;
			if (j > 5) {
				throw new RuntimeException("VarInt muito grande");
			}
		} while ((k & 0x80) == 128);
		return i;
	}

	private void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
		for (;;) {
			if ((paramInt & 0xFFFFFF80) == 0) {
				out.writeByte(paramInt);
				return;
			}
			out.writeByte(paramInt & 0x7F | 0x80);
			paramInt >>>= 7;
		}
	}
}
