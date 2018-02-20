package club.notrighteous.asmrefresher;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;

public final class RefresherUtils {
	
	static final String clientWorkspace = "/home/jordan/asmrefresher/";
	static final String currentClient = clientWorkspace + "test.jar";
    static final String freshClient = clientWorkspace + "testfresh.jar";
	static final String clientLink = "redacted local jar link";
	static final String clientChecksum = "sha256 checksum";
	static final String clientUpdate = "redacted direct vendor jar link";
	
	public static void downloadClient(String url, String path) {
		try {
			URL clientURL = new URL(url);
			File clientFile = new File(path);
            FileUtils.copyURLToFile(clientURL, clientFile, 10000, 10000);
		} catch (MalformedURLException urlException) {
			System.out.println("Malformed URL Exception Raised");
			urlException.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean clientCheck(String path) {
	    File clientFile = new File(path);
        return clientFile.exists();
    }

    // FIXME: 2/10/18 - Doesn't fucking work for some reason.
    public static String getChecksum(String path) {
	    try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(path);

            byte[] dataBytes = new byte[1024];

            int nread = 0;
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            byte[] mdbytes = md.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i=0;i<mdbytes.length;i++) {
                hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
            }

            return hexString.toString();

        } catch (Exception e) {
            return e.getMessage();
        }
    }
	
	private RefresherUtils() {
	}

}
