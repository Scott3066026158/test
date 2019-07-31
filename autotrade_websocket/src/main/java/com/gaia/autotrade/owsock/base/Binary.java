package com.gaia.autotrade.owsock.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Binary {
	public Binary() {
		m_outputStream = new ByteArrayOutputStream();
		m_writer = new DataOutputStream(m_outputStream);
	}

	private ByteArrayInputStream m_inputStream;

	private ByteArrayOutputStream m_outputStream;

	private DataInputStream m_reader;

	private DataOutputStream m_writer;

	public void Close() throws IOException {
		if (m_reader != null) {
			m_reader.close();
		}
		if (m_writer != null) {
			m_writer.close();
			;
		}
		if (m_inputStream != null) {
			m_inputStream.close();
		}
		if (m_outputStream != null) {
			m_outputStream.close();
		}
	}

	public byte[] GetBytes() {
		return m_outputStream.toByteArray();
	}

	public boolean ReadBool() throws IOException {
		return m_reader.readBoolean();
	}

	public byte ReadByte() throws IOException {
		return m_reader.readByte();
	}

	public void ReadBytes(byte[] bytes) throws IOException {
		m_reader.read(bytes);
	}

	public char ReadChar() throws IOException {
		return (char) m_reader.readByte();
	}

	public double ReadDouble() throws IOException {
		byte[] buffer = new byte[8];
		m_reader.read(buffer);
		long l = (0xffL & (long) buffer[0]) | (0xff00L & ((long) buffer[1] << 8))
				| (0xff0000L & ((long) buffer[2] << 16)) | (0xff000000L & ((long) buffer[3] << 24))
				| (0xff00000000L & ((long) buffer[4] << 32)) | (0xff0000000000L & ((long) buffer[5] << 40))
				| (0xff000000000000L & ((long) buffer[6] << 48)) | (0xff00000000000000L & ((long) buffer[7] << 56));
		return Double.longBitsToDouble(l);
	}

	public float ReadFloat() throws IOException {
		return Float.intBitsToFloat(ReadInt());
	}

	public int ReadInt() throws IOException {
		byte[] buffer = new byte[4];
		m_reader.read(buffer);
		return (int) ((buffer[0] & 0xFF) | ((buffer[1] & 0xFF) << 8) | ((buffer[2] & 0xFF) << 16)
				| ((buffer[3] & 0xFF) << 24));
	}

	public short ReadShort() throws IOException {
		byte[] buffer = new byte[2];
		m_reader.read(buffer);
		return (short) ((0xff & buffer[0]) | (0xff00 & (buffer[1] << 8)));
	}

	public String ReadString() throws IOException {
		String str = "";
		int strSize = ReadInt();
		byte[] bytes = new byte[strSize];
		m_reader.read(bytes);
		if (strSize >= 1) {
			str = new String(bytes, "UTF-8");
		}
		return str;
	}

	public void Write(byte[] bytes, int len) {
		m_inputStream = new ByteArrayInputStream(bytes);
		m_reader = new DataInputStream(m_inputStream);
	}

	public void WriteBool(boolean val) throws IOException {
		m_writer.writeBoolean(val);
	}

	public void WriteByte(byte val) throws IOException {
		m_writer.writeByte(val);
	}

	public void WriteBytes(byte[] val) throws IOException {
		m_writer.write(val);
	}

	public void WriteChar(char val) throws IOException {
		m_writer.writeByte(val);
	}

	public void WriteDouble(double val) throws IOException {
		long intBits = Double.doubleToLongBits(val);
		byte[] buffer = new byte[8];
		buffer[0] = (byte) (intBits & 0xff);
		buffer[1] = (byte) ((intBits >> 8) & 0xff);
		buffer[2] = (byte) ((intBits >> 16) & 0xff);
		buffer[3] = (byte) ((intBits >> 24) & 0xff);
		buffer[4] = (byte) ((intBits >> 32) & 0xff);
		buffer[5] = (byte) ((intBits >> 40) & 0xff);
		buffer[6] = (byte) ((intBits >> 48) & 0xff);
		buffer[7] = (byte) ((intBits >> 56) & 0xff);
		m_writer.write(buffer);
	}

	public void WriteFloat(float val) throws IOException {
		int intBits = Float.floatToIntBits(val);
		byte[] buffer = new byte[4];
		buffer[0] = (byte) (intBits & 0xff);
		buffer[1] = (byte) ((intBits >> 8) & 0xff);
		buffer[2] = (byte) ((intBits >> 16) & 0xff);
		buffer[3] = (byte) (intBits >> 24);
		m_writer.write(buffer);
	}

	public void WriteInt(int val) throws IOException {
		byte[] buffer = new byte[4];
		buffer[0] = (byte) (val & 0xff);
		buffer[1] = (byte) ((val & 0xff00) >> 8);
		buffer[2] = (byte) ((val & 0xff0000) >> 16);
		buffer[3] = (byte) ((val & 0xff000000) >> 24);
		m_writer.write(buffer);
	}

	public void WriteShort(short val) throws IOException {
		byte[] buffer = new byte[2];
		buffer[0] = (byte) (val & 0xff);
		buffer[1] = (byte) ((val & 0xff00) >> 8);
		m_writer.write(buffer);
	}

	public void WriteString(String val) throws IOException {
		if (val == null || val.length() == 0) {
			WriteInt(0);
		} else {
			byte[] bytes = val.getBytes("UTF-8");
			int len = bytes.length;
			WriteInt(len);
			m_writer.write(bytes);
		}
	}

}
