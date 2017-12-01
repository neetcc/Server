package constant;


import io.netty.util.AttributeKey;

/**
 * 
 * @author sunchong
 *
 */
public class ProtocolConstant {
	/** 协议包头的长度 */
	public static final int PKG_HEAD_LEN = 10;
	
	/** header的长度 */
	public static final int PKG_MAX_BODY_HEAD_LEN = 1024 * 2;
	
	/** 上行的包体长度 */
	public static final int PKG_MAX_UP_BODY_LEN = 1024 * 3;
	
	/** 下行的包体长度 */
	public static final int PKG_MAX_DOWN_BODY_LEN = 1024 * 256;
	
	/** 不压缩 */
	public static final byte COMPRESSION_NONE = 0;
	
	/** gzip压缩 */
	public static final byte COMPRESSION_GZIP = 1;
	
	/** quicklz压缩 */
	public static final byte COMPRESSION_QUICKLZ = 2;
	
	/** 不加密 */
	public static final byte ENCRYPT_NONE = 0;
	
	/** 使用XXTEA加密 */
	public static final byte ENCRYPT_XXTEA = 1;

	/** 使用DES加密 */
	public static final byte ENCRYPT_DES = 2;
	
	/** thrift协议 */
	public static final byte PROTOCOL_TYPE_THRIFT = 0;
	
	/** msgpack协议 */
	public static final byte PROTOCOL_TYPE_MSGPACK = 1;

	/** protobuf协议 */
	public static final byte PROTOCOL_TYPE_PROTOBUF = 2;

	/** channel中存放ip的key */
	public static final AttributeKey<String> KEY_IPADDR = AttributeKey.valueOf("_ip");

	/** 表示某一个channel正在退出中 */
	public static final AttributeKey<Boolean> KEY_EXIT = AttributeKey.valueOf("_exit");
}
